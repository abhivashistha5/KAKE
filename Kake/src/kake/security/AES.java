/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kake.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Abhijeet
 */
public class AES {
    
    public static final int AES_Key_Size = 256;

    Cipher aesCipher;
    public byte[] aesKey;
    SecretKeySpec aeskeySpec;
    RSA rsaEncryptor;
    
     /**
     * Constructor: creates ciphers
     */
    public AES() throws GeneralSecurityException {
        // create AES shared key cipher
        aesCipher = Cipher.getInstance("AES");
        
        //initialize RSA encryptor
        rsaEncryptor = new RSA();
    }
    
     /**
     * Creates a new AES key
     */
    public void makeKey() throws NoSuchAlgorithmException {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(AES_Key_Size);
        SecretKey key = kgen.generateKey();
        aesKey = key.getEncoded();
        aeskeySpec = new SecretKeySpec(aesKey, "AES");
    }
    
    public void saveKey(File saveFile, PublicKey publicKey) throws IOException{
        byte[] encryptedKey;
        FileOutputStream out = new FileOutputStream(saveFile);
        
        //encrypt aeskey
        encryptedKey = rsaEncryptor.encrypt(aesKey, publicKey);
        
        //write to file
        out.write(encryptedKey);
        
        //close stream
        out.close();
    } 
    
    public byte[] loadKey(File keyFile, PrivateKey privateKey) throws IOException{
        FileInputStream in = new FileInputStream(keyFile);
        byte[] encryptedKey = new byte[256];
        
        //read key;
        in.read(encryptedKey);
        
        //decrypt key;
        aesKey = rsaEncryptor.decrypt(encryptedKey, privateKey);
        
        //close stream
        in.close();
        
        return aesKey;
    }
    

     /**
     * Encrypts and then copies the contents of a given file.
     */
    public void encrypt(File in, File out) throws IOException, InvalidKeyException {
            aesCipher.init(Cipher.ENCRYPT_MODE, aeskeySpec);

            FileInputStream is = new FileInputStream(in);
            CipherOutputStream os = new CipherOutputStream(new FileOutputStream(out), aesCipher);

            copy(is, os);

            os.close();
    }

    /**
     * Decrypts and then copies the contents of a given file.
     */
    public void decrypt(File in, File out) throws IOException, InvalidKeyException {
            aesCipher.init(Cipher.DECRYPT_MODE, aeskeySpec);

            CipherInputStream is = new CipherInputStream(new FileInputStream(in), aesCipher);
            FileOutputStream os = new FileOutputStream(out);

            copy(is, os);

            is.close();
            os.close();
    }

    /**
     * Copies a stream.
     */
    private void copy(InputStream is, OutputStream os) throws IOException {
            int i;
            byte[] b = new byte[1024];
            while((i=is.read(b))!=-1) {
                    os.write(b, 0, i);
            }
    }
    
}
