package helper;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.security.Security;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DatabaseHelper {
    static String host = "192.168.202.119";
    static String host154 = "192.168.202.154";
    static String host120 = "192.168.202.120";
    static int    port = 1433;   // default SQL Server port
    static String database = "DbAPIManagement";
    static String databaseGalileo = "DBGalileo";
    static String databasePos = "";
    static String databaseCommGateway = "EMAIL_SMS_GATEWAY";
    static String user = "sa";
    static String pass = "pvs1909~";


    public static String fetchOtp() {

        // Force TLS1
        Security.setProperty("jdk.tls.disabledAlgorithms",
                "SSLv3, RC4, DH keySize < 1024, RSA keySize < 1024");
        Security.setProperty("jdk.certpath.disabledAlgorithms",
                "MD2, MD5, RSA keySize < 1024");

        String connectionUrl = String.format(
                "jdbc:sqlserver://%s:%d;databaseName=%s;encrypt=false;trustServerCertificate=false;sslProtocol=TLSv1;loginTimeout=30;",
                host, port, database);

        // Create and execute a SELECT SQL statement.
        String selectSql = "SELECT TOP (1) [otp]" +
                "  FROM [DbAPIManagement].[dbo].[otp] order by expired_at desc";

        ResultSet resultSet = null;

        try (Connection connection = DriverManager.getConnection(connectionUrl, user, pass);
             Statement statement = connection.createStatement();) {

            resultSet = statement.executeQuery(selectSql);

            // Print results from select statement
            if (resultSet.next()) {
                System.out.println(resultSet.getString("otp"));
                return resultSet.getString("otp");
            }else {
                throw new SQLException("No OTP rows found (valid=1).");
            }

        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getWaContent(String templateName, String phoneNum) {
        // Force TLS1
        Security.setProperty("jdk.tls.disabledAlgorithms",
                "SSLv3, RC4, DH keySize < 1024, RSA keySize < 1024");
        Security.setProperty("jdk.certpath.disabledAlgorithms",
                "MD2, MD5, RSA keySize < 1024");

        String connectionUrl = String.format(
                "jdbc:sqlserver://%s:%d;databaseName=%s;encrypt=false;trustServerCertificate=false;sslProtocol=TLSv1;loginTimeout=30;",
                host154, port, databaseCommGateway);

        // Create and execute a SELECT SQL statement.
        String sqlWa = "SELECT TOP (1) [id]" +
                        ",[template_name]" +
                        ",[request_body]" +
                        ",[status]" +
                        ",[created_at]" +
                        "  FROM [EMAIL_SMS_GATEWAY].[dbo].[whatsapp_requests_V2] where template_name = ? and status = 'SUCCESS' order by created_at desc";

        try (Connection connection = DriverManager.getConnection(connectionUrl, user, pass)) {

            String reqBody;

            // 1) get latest wa
            try (PreparedStatement ps = connection.prepareStatement(sqlWa)) {
                ps.setString(1, templateName);

                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) throw new SQLException("No wa row for template: " + templateName);

                    reqBody = rs.getString("request_body");
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode root = mapper.readTree(reqBody);

                    System.out.println("[DatabaseHelper] reqBody = " + reqBody);

                    String actualPhoneNum =  root.path("data").path(0).path("to").asText();

                    if (!phoneNum.equals(actualPhoneNum)) {
                        return null; // latest is not for this phone
                    }

                    // Extract OTP ONLY from body component
                    for (JsonNode c : root.path("data")
                            .path(0)
                            .path("template")
                            .path("components")) {

                        if ("body".equals(c.path("type").asText())) {
                            String otp = c.path("parameters")
                                    .path(0)
                                    .path("text")
                                    .asText(null);

                            System.out.println("[DatabaseHelper] OTP = " + otp);
                            return otp;
                        }
                    }


                    return null;
                }
            }
        }

        // Handle any errors that may have occurred.
        catch (SQLException | JsonProcessingException e ) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getSmsContent(String sc, String phoneNum) {
        // Force TLS1
        Security.setProperty("jdk.tls.disabledAlgorithms",
                "SSLv3, RC4, DH keySize < 1024, RSA keySize < 1024");
        Security.setProperty("jdk.certpath.disabledAlgorithms",
                "MD2, MD5, RSA keySize < 1024");

        String connectionUrl = String.format(
                "jdbc:sqlserver://%s:%d;databaseName=%s;encrypt=false;trustServerCertificate=false;sslProtocol=TLSv1;loginTimeout=30;",
                host154, port, databaseCommGateway);

        // Create and execute a SELECT SQL statement.
        String sqlSms = "SELECT TOP (1) [id]" +
                        ",[msisdn]" +
                        ",[sc]" +
                        ",[text]" +
                        ",[created_at]" +
                        ",[last_status]" +
                        "  FROM [EMAIL_SMS_GATEWAY].[dbo].[sms_requests] where sc = ? and last_status = 'SUCCESS' order by created_at desc";

        try (Connection connection = DriverManager.getConnection(connectionUrl, user, pass)) {


            // 1) get latest sms
            try (PreparedStatement ps = connection.prepareStatement(sqlSms)) {
                ps.setString(1, sc);

                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) throw new SQLException("No sms row for sc: " + sc);

                    String actualPhoneNum = rs.getString("msisdn");
                    String text = rs.getString("text");

                    System.out.println("[DatabaseHelper] actualPhoneNum = " + actualPhoneNum);
                    System.out.println("[DatabaseHelper] text = " + text);


                    if (!phoneNum.equals(actualPhoneNum)) {
                        System.out.println("[DatabaseHelper] Phone did not match, return null");
                        return null; // latest is not for this phone
                    }

                    // OTP
                    /*
                    Pattern pattern = Pattern.compile("OTP\\D+(\\d{6})");
                    Matcher matcher = pattern.matcher(text);

                    String otp = null;
                    if (matcher.find()) {
                        otp = matcher.group(1);
                    }

                     */

                    // PIN POS (get the last 4 digit of the msg)
                    String digits = text.replaceAll("\\D", "");
                    String pin = digits.substring(digits.length() - 4);
                    System.out.println("[DatabaseHelper] PIN = " + pin);


                    return pin;
                }
            }
        }

        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getEmailContent(String emailSubject, String recipient) {
        // Force TLS1
        Security.setProperty("jdk.tls.disabledAlgorithms",
                "SSLv3, RC4, DH keySize < 1024, RSA keySize < 1024");
        Security.setProperty("jdk.certpath.disabledAlgorithms",
                "MD2, MD5, RSA keySize < 1024");

        String connectionUrl = String.format(
                "jdbc:sqlserver://%s:%d;databaseName=%s;encrypt=false;trustServerCertificate=false;sslProtocol=TLSv1;loginTimeout=30;",
                host154, port, databaseCommGateway);

        // Create and execute a SELECT SQL statement.
        String sqlEmailReq = "SELECT TOP (1) [id]" +
                ",[body]" +
                "  FROM [EMAIL_SMS_GATEWAY].[dbo].[email_requests] WHERE subject = ? ORDER BY created_at DESC";

        String sqlEmailReqRecipient = "SELECT id, email " +
                "FROM [EMAIL_SMS_GATEWAY].[dbo].[email_request_recipients] " +
                "WHERE email_request_id = ? ";


        try (Connection connection = DriverManager.getConnection(connectionUrl, user, pass)) {

            long requestId;
            String body;


            // 1) get latest request id + body by subject
            try (PreparedStatement ps1 = connection.prepareStatement(sqlEmailReq)) {
                ps1.setString(1, emailSubject);

                try (ResultSet rs1 = ps1.executeQuery()) {
                    if (!rs1.next()) throw new SQLException("No email_requests row for subject: " + emailSubject);
                    requestId = rs1.getLong("id");
                    body = rs1.getString("body");
                }
            }


            String recipientEmailActual = "";
            // 2) use requestId for recipients query
            try (PreparedStatement ps2 = connection.prepareStatement(sqlEmailReqRecipient)) {
                ps2.setLong(1, requestId);

                try (ResultSet rs2 = ps2.executeQuery()) {
                    if (!rs2.next()) throw new SQLException("No recipient row for email_request_id: " + requestId);

                    recipientEmailActual = rs2.getString("email");

                    System.out.println("[DatabaseHelper] requestId=" + requestId);
                    System.out.println("[DatabaseHelper] recipientEmail=" + recipientEmailActual);
                }
            }

            if (recipientEmailActual.equals(recipient)) {
                return body;
            }else {
                System.out.println("Recipient not found");
                return null;
            }

        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // If email alert is found get the reqId and the body
    public static String[] getEmailPOSWarningStock(String emailSubject, String recipient) {
        // Force TLS1
        Security.setProperty("jdk.tls.disabledAlgorithms",
                "SSLv3, RC4, DH keySize < 1024, RSA keySize < 1024");
        Security.setProperty("jdk.certpath.disabledAlgorithms",
                "MD2, MD5, RSA keySize < 1024");

        String connectionUrl = String.format(
                "jdbc:sqlserver://%s:%d;databaseName=%s;encrypt=false;trustServerCertificate=false;sslProtocol=TLSv1;loginTimeout=30;",
                host154, port, databaseCommGateway);

        // Create and execute a SELECT SQL statement.
        String sqlEmailReq = "SELECT TOP (1) [id]" +
                ",[body]" +
                ",[subject] " +
                "  FROM [EMAIL_SMS_GATEWAY].[dbo].[email_requests] WHERE last_status = 'SUCCESS' ORDER BY created_at DESC";

        String sqlEmailReqRecipient = "SELECT id, email " +
                "FROM [EMAIL_SMS_GATEWAY].[dbo].[email_request_recipients] " +
                "WHERE email_request_id = ? ";


        try (Connection connection = DriverManager.getConnection(connectionUrl, user, pass)) {

            long requestId;
            String subject;
            String body;


            // 1) get latest request id + body and subject
            try (PreparedStatement ps1 = connection.prepareStatement(sqlEmailReq)) {

                try (ResultSet rs1 = ps1.executeQuery()) {
                    if (!rs1.next()) throw new SQLException("No email_requests row for subject: " + emailSubject);
                    requestId = rs1.getLong("id");
                    subject = rs1.getString("subject");
                    body = rs1.getString("body");

                    System.out.println("[DatabaseHelper] subject=" + subject);
                }
            }


            String recipientEmailActual = "";
            // 2) use requestId for recipients query
            try (PreparedStatement ps2 = connection.prepareStatement(sqlEmailReqRecipient)) {
                ps2.setLong(1, requestId);

                try (ResultSet rs2 = ps2.executeQuery()) {
                    if (!rs2.next()) throw new SQLException("No recipient row for email_request_id: " + requestId);

                    recipientEmailActual = rs2.getString("email");

                    System.out.println("[DatabaseHelper] requestId=" + requestId);
                    System.out.println("[DatabaseHelper] recipientEmail=" + recipientEmailActual);
                }
            }

            if (recipientEmailActual.equals(recipient) && subject.equals(emailSubject) ) {
                return new String[]{String.valueOf(requestId), body};
            }else {
                System.out.println("[INFO] : Stock alert not found");
                return null;
            }

        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void deleteEmailReqById(String emailReqId) {
        System.out.println("[DatabaseHelper] deleteEmailReqById : " + emailReqId);

        // Force TLS1
        Security.setProperty("jdk.tls.disabledAlgorithms",
                "SSLv3, RC4, DH keySize < 1024, RSA keySize < 1024");
        Security.setProperty("jdk.certpath.disabledAlgorithms",
                "MD2, MD5, RSA keySize < 1024");

        String connectionUrl = String.format(
                "jdbc:sqlserver://%s:%d;databaseName=%s;encrypt=false;trustServerCertificate=false;sslProtocol=TLSv1;loginTimeout=30;",
                host154, port, databaseCommGateway);

        // Create and execute a SELECT SQL statement.
        String deleteSql = "delete [EMAIL_SMS_GATEWAY].[dbo].[email_requests] where id = ?";

        try (Connection connection = DriverManager.getConnection(connectionUrl, user, pass);
             PreparedStatement ps = connection.prepareStatement(deleteSql)) {

            ps.setString(1, emailReqId);

            int rows = ps.executeUpdate();

            if (rows != 1) {
                throw new SQLException("Insert affected " + rows + " rows");
            }


        }

        // Handle any errors that may have occurred.
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String getGalileoMemberIdByEmail(String email) {

        // Force TLS1
        Security.setProperty("jdk.tls.disabledAlgorithms",
                "SSLv3, RC4, DH keySize < 1024, RSA keySize < 1024");
        Security.setProperty("jdk.certpath.disabledAlgorithms",
                "MD2, MD5, RSA keySize < 1024");

        String connectionUrl = String.format(
                "jdbc:sqlserver://%s:%d;databaseName=%s;encrypt=false;trustServerCertificate=false;sslProtocol=TLSv1;loginTimeout=30;",
                host120, port, databaseGalileo);

        // Create and execute a SELECT SQL statement.
        String selectSql = "SELECT TOP (1000) [id]" +
                            ",[member_id]" +
                            ",[email]" +
                            "  FROM [DBGalileo].[dbo].[member_email] where email = ?";


        try (Connection connection = DriverManager.getConnection(connectionUrl, user, pass);
             PreparedStatement ps = connection.prepareStatement(selectSql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) throw new SQLException("No result for email : " + email);

                String memberId = rs.getString("member_id");
                System.out.println("[DatabaseHelper] memberId = " + memberId);

                return memberId;
            }
        }

        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void addGalileoMemberEmailById(String memberId, String email) {
        System.out.println("[DatabaseHelper] addGalileoMemberEmailById, memberId : " + memberId + " email : " + email);


        // Force TLS1
        Security.setProperty("jdk.tls.disabledAlgorithms",
                "SSLv3, RC4, DH keySize < 1024, RSA keySize < 1024");
        Security.setProperty("jdk.certpath.disabledAlgorithms",
                "MD2, MD5, RSA keySize < 1024");

        String connectionUrl = String.format(
                "jdbc:sqlserver://%s:%d;databaseName=%s;encrypt=false;trustServerCertificate=false;sslProtocol=TLSv1;loginTimeout=30;",
                host120, port, databaseGalileo);

        // Create and execute a SELECT SQL statement.
        String insertSql = "INSERT INTO DBGalileo.dbo.member_email (member_id, email, created_at)" +
                            "VALUES (?, ?, ?);";

        try (Connection connection = DriverManager.getConnection(connectionUrl, user, pass);
             PreparedStatement ps = connection.prepareStatement(insertSql)) {

            ps.setString(1, memberId);
            ps.setString(2, email);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
            String timeNow = LocalDateTime.now().format(formatter);
            ps.setString(3, timeNow);

            int rows = ps.executeUpdate();

            if (rows != 1) {
                throw new SQLException("Insert affected " + rows + " rows");
            }


        }

        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addGalileoMemberPhoneById(String memberId, String phone) {
        System.out.println("[DatabaseHelper] addGalileoMemberPhoneById, memberId : " + memberId + " phone : " + phone);

        // Force TLS1
        Security.setProperty("jdk.tls.disabledAlgorithms",
                "SSLv3, RC4, DH keySize < 1024, RSA keySize < 1024");
        Security.setProperty("jdk.certpath.disabledAlgorithms",
                "MD2, MD5, RSA keySize < 1024");

        String connectionUrl = String.format(
                "jdbc:sqlserver://%s:%d;databaseName=%s;encrypt=false;trustServerCertificate=false;sslProtocol=TLSv1;loginTimeout=30;",
                host120, port, databaseGalileo);

        // Create and execute a SELECT SQL statement.
        String insertSql = "INSERT INTO DBGalileo.dbo.phone_number (member_id, phone_number)" +
                "VALUES (?, ?);";

        try (Connection connection = DriverManager.getConnection(connectionUrl, user, pass);
             PreparedStatement ps = connection.prepareStatement(insertSql)) {

            ps.setString(1, memberId);
            ps.setString(2, phone);


            int rows = ps.executeUpdate();

            if (rows != 1) {
                throw new SQLException("Insert affected " + rows + " rows");
            }


        }

        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteGalileoMemberEmail(String email) {
        System.out.println("[DatabaseHelper] deleteGalileoMemberEmail : " + email);

        // Force TLS1
        Security.setProperty("jdk.tls.disabledAlgorithms",
                "SSLv3, RC4, DH keySize < 1024, RSA keySize < 1024");
        Security.setProperty("jdk.certpath.disabledAlgorithms",
                "MD2, MD5, RSA keySize < 1024");

        String connectionUrl = String.format(
                "jdbc:sqlserver://%s:%d;databaseName=%s;encrypt=false;trustServerCertificate=false;sslProtocol=TLSv1;loginTimeout=30;",
                host120, port, databaseGalileo);

        // Create and execute a SELECT SQL statement.
        String deleteSql = "delete [DBGalileo].[dbo].[member_email] where email = ?";

        try (Connection connection = DriverManager.getConnection(connectionUrl, user, pass);
             PreparedStatement ps = connection.prepareStatement(deleteSql)) {

            ps.setString(1, email);

            int rows = ps.executeUpdate();

            if (rows != 1) {
                throw new SQLException("Insert affected " + rows + " rows");
            }


        }

        // Handle any errors that may have occurred.
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
