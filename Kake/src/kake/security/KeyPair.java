package kake.security;

public class KeyPair{

    private PublicKey publicKey;
    private PrivateKey privateKey;

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

}
