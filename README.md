# CertificateBasedAuth
Using certificate to sign and verify data

At first need to create the key store file. For that, you need to go to the JAVA_HOME/bin.

Then run this command(for windows):
keytool -genkey -alias aliasName -keyalg EC -keystore {Directory}\keystore.jks -keysize 256

It will create a keystore.jks file in the given "Directory".

