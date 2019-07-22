import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

@Slf4j
@Setter
public class Verifier {
    private final static String SIGNATURE_ALGORITHM = "SHA256withECDSA";
    private final static String SIGNATURE_PROVIDER = "SunEC";
    private final static String CERTIFICATE_FILE_NAME;
    private Signature signature;
    private PublicKey publicKey;
    private byte[] byteDataArray;
    private byte[] byteSignatureArray;

    static {
        CERTIFICATE_FILE_NAME = CertificateGenerator.CERTIFICATE_FILE_NAME;
    }

    public void loadPublicKeyFromCertificate() {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(CERTIFICATE_FILE_NAME);
            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            Certificate certificate = factory.generateCertificate(inputStream);
            this.publicKey = certificate.getPublicKey();
        } catch (Exception e) {
            log.error("Exception: ", e);
        } finally {
            CloserUtility.getInstance().closeFileInputStream(inputStream);
        }
    }

    public void getSignature() {
        try {
            this.signature = Signature.getInstance(SIGNATURE_ALGORITHM, SIGNATURE_PROVIDER);
            this.signature.initVerify(this.publicKey);
            this.signature.update(this.byteDataArray);
        } catch (Exception e) {
            log.error("Exception: ", e);
        }
    }

    public boolean verifyData() {
        try {
            boolean isVerified = this.signature.verify(this.byteSignatureArray);
            return isVerified;
        } catch (Exception e) {
            log.error("Exception: ", e);
        }
        return false;
    }
}
