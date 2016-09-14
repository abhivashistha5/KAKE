package kake.security;

import java.math.BigInteger;

public class PrivateKey{

    private BigInteger n;
    private BigInteger d;

    public PrivateKey(BigInteger n, BigInteger d){
        this.d = d;
        this.n = n;
    }

    public void setKey(BigInteger n, BigInteger d){
        this.d = d;
        this.n = n;
    }

    public BigInteger getd(){
        return this.d;
    }

    public BigInteger getn(){
        return this.n;
    }

    @Override
    public String toString(){
        return "( "+n.toString() + ", " + d.toString() + ")";
    }
}
