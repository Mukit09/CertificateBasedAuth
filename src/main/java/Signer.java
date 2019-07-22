import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.security.PrivateKey;
import java.security.Signature;

@Slf4j
@Getter
@Setter
public class Signer {
    private final static String SIGNATURE_ALGORITHM = "SHA256withECDSA";
    private final static String SIGNATURE_PROVIDER = "SunEC";
    private PrivateKey privateKey;
    private byte[] byteDataArray;
    private byte[] byteSignatureArray;

    public void signData() {
        try {
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM, SIGNATURE_PROVIDER);
            signature.initSign(this.privateKey);
            signature.update(this.byteDataArray);
            this.byteSignatureArray = signature.sign();
        } catch (Exception e) {
            log.error("Exception: ", e);
        }
    }

    public void loadData() {
        String data = "My name is Mukit";
        this.byteDataArray = data.getBytes();
    }
}
