import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.util.Enumeration;

@Slf4j
@Getter
@Data
public class CertificateGenerator {
    private KeyStore keyStore;
    private final static String KEY_STORE_PASSWORD = "123456";
    private final static String PRIVATE_KEY_PASSWORD = "654321";
    private final static String KEY_STORE_FILE_NAME = "keystore.jks";
    public final static String CERTIFICATE_FILE_NAME = "certificate.crt";

    CertificateGenerator() {
        try {
            this.keyStore = KeyStore.getInstance("JKS");
        } catch (Exception e) {
            log.error("Exception: ", e);
        }
    }

    public void loadKeyStore() {
        char passwordCharArray[] = KEY_STORE_PASSWORD.toCharArray();
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(KEY_STORE_FILE_NAME);
            this.keyStore.load(inputStream, passwordCharArray);
        } catch (Exception e) {
            log.error("Exception: ", e);
        } finally {
            CloserUtility.getInstance().closeFileInputStream(inputStream);
        }
    }

    public void logAllAliasesName() {
        try {
            log.debug("Size found: " + keyStore.size());
            Enumeration<String> enumeration = keyStore.aliases();
            while(enumeration.hasMoreElements()) {
                log.debug(enumeration.nextElement());
            }
        } catch (Exception e) {
            log.error("Exception: ", e);
        }
    }

    public PrivateKey getPrivateKey() {
        char[] passwordCharArray = PRIVATE_KEY_PASSWORD.toCharArray();
        PrivateKey privateKey;
        try {
            privateKey = (PrivateKey) keyStore.getKey("aliasmukit", passwordCharArray);
            log.debug(new String(privateKey.getEncoded()));
            return privateKey;
        } catch (Exception e) {
            log.error("Exception: ", e);
        }
        return null;
    }

    public void generateCertificateAndWriteInFile() {
        Certificate certificate;
        FileOutputStream outputStream = null;
        try {
            certificate = keyStore.getCertificate("aliasmukit");
            byte[] encodedCertificate = certificate.getEncoded();
            outputStream = new FileOutputStream(CERTIFICATE_FILE_NAME);
            outputStream.write(encodedCertificate);
        } catch (Exception e) {
            log.error("Exception: ", e);
        } finally {
            CloserUtility.getInstance().closeFileOutputStream(outputStream);
        }
    }
}
