package kake.security;

import java.math.BigInteger;

public class RSA{

    public byte[] encrypt(byte[] secretText, PublicKey publicKey){
        byte[] cipherText;
        cipherText = (new BigInteger(secretText)).modPow(publicKey.gete(), publicKey.getn()).toByteArray();
        return cipherText;
    }

    public byte[] decrypt(byte[] cipherText, PrivateKey privateKey){
        byte[] secretText;
        secretText = (new BigInteger(cipherText)).modPow(privateKey.getd(), privateKey.getn()).toByteArray();
        return secretText;
    }

}
