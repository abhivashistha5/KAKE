package kake.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KeyPair{

    private PublicKey publicKey;
    private PrivateKey privateKey;
    
    public KeyPair(){
        this.publicKey = null;
        this.privateKey = null;
    }

    public KeyPair(PublicKey publicKey, PrivateKey privateKey){
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public PublicKey getPublicKey(){
        return this.publicKey;
    }

    public PrivateKey getPrivateKey(){
        return this.privateKey;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("Public key: ").append(publicKey.toString());
        builder.append("\nPrivate Key: ").append(privateKey.toString());
        return builder.toString();
    }
    
    public void loadFromFile(String filename){
        InputStream inputStream = null;
        try {
            Properties prop = new Properties();
            try{
                inputStream = new FileInputStream(filename);
            }catch(FileNotFoundException e){
                File file = new File(filename);
                file.createNewFile();
                inputStream = new FileInputStream(file);
            }
            
            prop.load(inputStream);
            
            String d = prop.getProperty("d", null);
            String e = prop.getProperty("e", null);
            String n = prop.getProperty("n", null);
            
            if(d == null || e == null || n == null){
                KeyPair keypair = RSAKeyGen.generate();
                keypair.saveToFile(filename);
                this.privateKey = keypair.getPrivateKey();
                this.publicKey = keypair.getPublicKey();
            }else{
                this.publicKey = new PublicKey( new BigInteger(n), new BigInteger(e) );
                this.privateKey = new PrivateKey( new BigInteger(n), new BigInteger(d) );
            }
            
        } catch (Exception e) {
			System.out.println("Exception: " + e);
	} finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                }catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    public void saveToFile(String filename){
        Properties prop = new Properties();
	OutputStream outputStream = null;

	try {

		outputStream = new FileOutputStream(filename);

		// set the properties value
		prop.setProperty("d", privateKey.getd().toString());
		prop.setProperty("n", publicKey.getn().toString());
		prop.setProperty("e", publicKey.gete().toString());

		// save properties to project root folder
		prop.store(outputStream, null);

	} catch (IOException io) {
		io.printStackTrace();
	} finally {
		if (outputStream != null) {
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

    }
}
