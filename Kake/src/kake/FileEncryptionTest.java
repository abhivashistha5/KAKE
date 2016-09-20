/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kake;

import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;
import kake.security.KeyPair;
import kake.FileEncryptor;
import kake.security.RSAKeyGen;

/**
 *
 * @author abhi
 */
public class FileEncryptionTest {
    
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        KeyPair pair = RSAKeyGen.generate();
        String dataFile, encryptedFile;
        FileEncryptor encryptor = new FileEncryptor();
        int ch;
        //System.out.println(pair.toString());

        do{
            System.out.print("\n\n\nSelect:\n1.Encrypt\n2.Decrypt\n0.Exit\nEnter a choice>>");
            ch = Integer.parseInt(scanner.nextLine());
            switch(ch){
                case 1: System.out.println("\nEnter data file path>>");
                    dataFile = scanner.nextLine();
                    try{
                        encryptor.encrypt(dataFile, "/home/abhi/encrypted.kake", pair.getPublicKey());
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    System.out.println("\nEnter encrypted file path>>");
                    encryptedFile = scanner.nextLine();
                    try{
                        encryptor.decrpyt(encryptedFile, "/home/abhi/decryptedFile.txt", pair.getPrivateKey());
                    }catch(Exception e){
                        e.printStackTrace();
                    }
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
