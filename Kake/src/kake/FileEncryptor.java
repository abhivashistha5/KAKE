package kake;

/**
 *
 * @author abhi
 */

import kake.security.Key;
import kake.security.RSA;
import kake.security.PublicKey;
import kake.security.PrivateKey;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
        
public class FileEncryptor {
    
    FileInputStream in;
    FileOutputStream out;
    byte[] readBuffer;
    byte[] writeBuffer;
    RSA encryptor;
    
    public FileEncryptor(){
        out = null;
        in = null;
        encryptor = new RSA();
        readBuffer = new byte[Key.BLOCK_SIZE];
    }
    
    public void encrypt(String inputFilePath, String outputFilePath, PublicKey publicKey) throws IOException{
        int i;
        byte[] temp;
        
        in = new FileInputStream(inputFilePath);                          // input stream for reading from file
        out = new FileOutputStream(outputFilePath);                        // output stream for writing to file
        try{
            /*
            steps:
                1. read a block from file to buffer ( read buffer )
                2. if the read block is smaller that buffer( this case occurs at the end of file)
                    then copy it to smaller temprory buffer
                3. encrypt the data in buffer and save result in another buffer ( writeBuffer )
                4. save the output buffer to file.
            */
            while( (i = in.read(readBuffer)) != -1 ){
                if( i < Key.BLOCK_SIZE ){
                    temp = new byte[i];
                    for(int j=0; j<i; j++){
                        temp[j] = readBuffer[j];
                    }
                    writeBuffer = encryptor.encrypt(temp, publicKey);
                }else{
                    writeBuffer = encryptor.encrypt(readBuffer, publicKey);
                }
                out.write(writeBuffer);
            }
        }finally{
            //closing streams
            in.close();
            out.close();
        }
    }
    
    public void decrpyt(String inputFilePath, String outputFilePath, PrivateKey privateKey) throws IOException{
        int i;
        byte[] temp;
        
        in = new FileInputStream(inputFilePath);
        out = new FileOutputStream(outputFilePath);
        try{
            while( (i = in.read(readBuffer)) != -1 ){
                if( i < Key.BLOCK_SIZE ){
                    temp = new byte[i];
                    for(int j=0; j<i; j++){
                        temp[j] = readBuffer[j];
                    }
                    writeBuffer = encryptor.decrypt(temp, privateKey);
                }else{
                    writeBuffer = encryptor.decrypt(readBuffer, privateKey);
                }
                out.write(writeBuffer);
            }
        }finally{
            in.close();
            out.close();
        }
    }
    
}
