package kake;

import kake.security.RSA;
import kake.security.RSAKeyGen;
import kake.security.KeyPair;
import kake.security.PublicKey;
import kake.security.PrivateKey;

import java.util.Scanner;

public class Test{

    public static void main(String[] args) {

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

    public static String bytesToString(byte[] bytes){
        StringBuilder builder = new StringBuilder();
        for( byte b : bytes){
            builder.append(Byte.toString(b));
        }
        return builder.toString();
    }

}
