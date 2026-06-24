package helper;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class MailSacHelper {

    public static HttpResponse<String> getAllEmail(String emailAddress, String API_KEY) throws IOException, InterruptedException {
        TestLogger.startFunc();

        // call list messages API
        String inboxPath = URLEncoder.encode(emailAddress, StandardCharsets.UTF_8);
        String url = "https://mailsac.com/api/addresses/" + inboxPath + "/messages";
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        HttpRequest listRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Mailsac-Key", API_KEY)
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(listRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println("Message list:");
        System.out.println(response.body());
        return  response;
    }

    public static String getMessageHtml(String email, String messageId, String API_KEY) throws IOException, InterruptedException {
        TestLogger.startFunc();

        HttpClient client = HttpClient.newHttpClient();

        String encodedEmail = URLEncoder.encode(email, StandardCharsets.UTF_8);
        String encodedMessageId = URLEncoder.encode(messageId, StandardCharsets.UTF_8);

        String url = "https://mailsac.com/api/body/" + encodedEmail + "/" + encodedMessageId;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Mailsac-Key", API_KEY)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Message :");
        System.out.println(response.body());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to fetch message text. Status: " + response.statusCode()
                    + ", body: " + response.body());
        }

        return response.body();
    }

    public static void deleteAllEmails(String email, String apiKey) throws IOException, InterruptedException {
        TestLogger.startFunc();

        HttpClient client = HttpClient.newHttpClient();

        String url = "https://mailsac.com/api/addresses/"
                + URLEncoder.encode(email, StandardCharsets.UTF_8)
                + "/messages";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Mailsac-Key", apiKey)
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 204) {
            throw new RuntimeException(
                    "Failed to delete inbox messages. Status: "
                            + response.statusCode()
                            + ", body: " + response.body()
            );
        }

        System.out.println("All emails deleted for inbox: " + email);
        TestLogger.endFunc();
    }
}
