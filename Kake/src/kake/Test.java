package kake;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import kake.security.RSA;
import kake.security.RSAKeyGen;
import kake.security.KeyPair;
import kake.security.PublicKey;
import kake.security.PrivateKey;

import java.util.Scanner;
import kake.security.AES;

public class Test{

    public static void main(String[] args) {
            try{
                //testAESFileEncryption();
                //testRSAEncryption();
                testAES();
            }
            catch(Exception e){
                e.printStackTrace();
            }
    }

    public static String bytesToString(byte[] bytes){
        StringBuilder builder = new StringBuilder();
        for( byte b : bytes){
            builder.append(Byte.toString(b));
        }
        return builder.toString();
    }
    
    
    public static void testRSAEncryption(){
        /*
        * This is to test the encryption using rsa algorithm
         */
        
        Scanner scanner = new Scanner(System.in);
        KeyPair pair = RSAKeyGen.generate();
        RSA encryptor = new RSA();
        String secretText, cipherText;
        try{
            System.out.println("Enter a secret text: ");
            secretText = scanner.nextLine();
            System.out.println("Secret text in bytes is: \n" + bytesToString(secretText.getBytes("UTF-8")));
            byte[] encrypted = encryptor.encrypt( secretText.getBytes("UTF-8"), pair.getPublicKey() );
            System.out.println("Encrypted bytes are: ");
            System.out.println(bytesToString(encrypted));
            /*System.out.println("\n\n\n\n");
            for(byte b : encrypted){
                System.out.println(b);
            }*/
            byte[] decrypted = encryptor.decrypt(encrypted, pair.getPrivateKey() );
            System.out.println("Decrypted bytes are: ");
            System.out.println(bytesToString(decrypted));
            System.out.println("Decrypted string: ");
            System.out.println(new String(decrypted, "UTF-8"));
        }catch(Exception e){

        }
    }
    
    public static void testAESFileEncryption() throws GeneralSecurityException, IOException{
        /*
        * this method test file encryption using aes algorithm
        * ***only for windows***
        */
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a path of secret file>>");
        String secretFilePath = scanner.nextLine();

        AES aes = new AES();
        File saveFile = new File(secretFilePath);
        KeyPair pair = RSAKeyGen.generate();
        
        
        aes.makeKey();
        
        System.out.println("Aes key before encryption- " + new String(aes.aesKey) );
        String key1 = new String(aes.aesKey);
        
        aes.saveKey(saveFile, pair.getPublicKey());
        
        aes.loadKey(saveFile, pair.getPrivateKey());
        String key2 = new String(aes.aesKey);
        
        System.out.println("Aes key after encryption- " + new String(aes.aesKey) );
        
        if( key1.equals(key2)){
            System.out.println("pass");
        }else{
            System.out.println("Fail");
        }
    }
    
    public static void testAES() throws GeneralSecurityException, IOException{
        /*
        * this method test file encryption using aes algorithm
        * ***only for windows***
        */
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter path of secret file>>");
        String secretFilePath = scanner.nextLine();
        System.out.println("Enter path of output file>>");
        String outputFilePath = scanner.nextLine();
        System.out.println("Enter path to key>>");
        String keyFilePath = scanner.nextLine();
        
        AES encryptor = new AES();
        KeyPair keyPair = RSAKeyGen.generate();
        
        File secretFile = new File(secretFilePath);
        File outputFile = new File(outputFilePath);
        File keyFile = new File(keyFilePath);
        
        
        encryptor.makeKey();
        encryptor.saveKey(keyFile, keyPair.getPublicKey());
        encryptor.encrypt(secretFile, outputFile);
        
        encryptor.loadKey(keyFile, keyPair.getPrivateKey());
        encryptor.decrypt(outputFile, new File(secretFile.getPath() + "decrypted-" + secretFile.getName()));
    }

}
