import lombok.extern.slf4j.Slf4j;

import java.security.PrivateKey;

/*
 * @Go to C:\Program Files\Java\jdk1.8.0_191\bin
 * @And then execute the below command
 * @command to create keystore.jks in work directory
 * @keytool -genkey -alias aliasMukit -keyalg EC -keystore F:\Code\Java\CertificateBasedAuth\keystore.jks -keysize 256
 *
 * */

@Slf4j
public class AppRunner {
    public static void main(String[] args) {

        CertificateGenerator generator = new CertificateGenerator();
        generator.loadKeyStore();
        generator.logAllAliasesName();
        PrivateKey privateKey = generator.getPrivateKey();
        generator.generateCertificateAndWriteInFile();

        Signer signer = new Signer();
        signer.setPrivateKey(privateKey);
        signer.loadData();
        signer.signData();

        Verifier verifier = new Verifier();
        verifier.setByteDataArray(signer.getByteDataArray());
        verifier.setByteSignatureArray(signer.getByteSignatureArray());
        verifier.loadPublicKeyFromCertificate();
        verifier.getSignature();
        boolean isVerified = verifier.verifyData();
        log.debug("Data verified: " + isVerified);
    }
}
