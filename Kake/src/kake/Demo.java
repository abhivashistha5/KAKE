package kake;

import kake.security.RSA;
import kake.security.RSAKeyGen;
import kake.security.KeyPair;

import java.util.Scanner;
import java.util.StringTokenizer;

public class Demo{

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        KeyPair pair = RSAKeyGen.generate();
        RSA encryptor = new RSA();
        String secretText, cipherText;
        int ch;
        //System.out.println(pair.toString());

        do{
            System.out.print("\n\n\nSelect:\n1.Encrypt\n2.Decrypt\n0.Exit\nEnter a choice>>");
            ch = Integer.parseInt(scanner.nextLine());
            switch(ch){
                case 1: System.out.println("\nEnter secret message>>");
                    secretText = scanner.nextLine();
                    //System.out.println("Entered string in bytes: " + bytesToString(secretText.getBytes()));
                    cipherText = bytesToString(encryptor.encrypt(secretText.getBytes(), pair.getPublicKey()));
                    System.out.println("Encrypted text : ");
                    System.out.println(cipherText);
                    break;
                case 2:
                    System.out.println("\nEnter encrypted message>>");
                    cipherText = scanner.nextLine();
                    //secretText = bytesToString(encryptor.decrypt(cipherText.getBytes(), pair.getPrivateKey()));
                    //System.out.println("Decrypted string in bytes: " + encryptor.decrypt(cipherText.getBytes(), pair.getPrivateKey()));
                    //secretText = new String(encryptor.decrypt(cipherText.getBytes(), pair.getPrivateKey()));
                    byte[] cipherBytes = stringToBytes(cipherText);
                    /*System.out.println("\n\nConverted bytes>\n");
                    for(byte b : cipherBytes){
                        System.out.println(b);
                    }*/
                    byte[] secretBytes = encryptor.decrypt(cipherBytes, pair.getPrivateKey());
                    System.out.println("Decrypted text : ");
                    System.out.println(new String(secretBytes));
                    break;
            }
        }while(ch != 0 );
    }


    public static String bytesToString(byte[] bytes){
        StringBuilder builder = new StringBuilder();
        for( byte b : bytes){
            builder.append(Byte.toString(b)).append(" ");
        }
        return builder.toString();
    }

    public static byte[] stringToBytes(String byteArray){
        StringTokenizer tokenizer = new StringTokenizer(byteArray, " ");
        byte[] bytes = new byte[tokenizer.countTokens()];
        int i = 0;
        while(tokenizer.hasMoreTokens()){
            try{
                bytes[i] = Byte.parseByte(tokenizer.nextToken(), 10);
            }catch(NumberFormatException e){
                e.printStackTrace();
                break;
            }
            i++;
        }
        return bytes;
    }

}
