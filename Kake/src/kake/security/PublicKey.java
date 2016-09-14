package kake.security;

import java.math.BigInteger;

public class PublicKey{
    private BigInteger e;
    private BigInteger n;

    public PublicKey(BigInteger n, BigInteger e){
        this.e = e;
        this.n = n;
    }

    public void setKey(BigInteger n, BigInteger e){
        this.e = e;
        this.n = n;
    }

    public BigInteger gete(){
        return this.e;
    }

    public BigInteger getn(){
        return this.n;
    }

    @Override
    public String toString(){
        return "( "+n.toString() + ", " + e.toString() + ")";
    }
}
