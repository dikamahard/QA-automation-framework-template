package helper;
import org.testng.IExecutionListener;

import java.security.Security;

public class EarlyTLSInit implements IExecutionListener {
    @Override
    public void onExecutionStart() {
        Security.setProperty("jdk.tls.disabledAlgorithms",
                "SSLv3, RC4, DH keySize < 1024, RSA keySize < 1024");
        System.setProperty("jdk.tls.client.protocols", "TLSv1,TLSv1.1,TLSv1.2");
        System.out.println("tls.disabled=" + Security.getProperty("jdk.tls.disabledAlgorithms"));
    }
}
