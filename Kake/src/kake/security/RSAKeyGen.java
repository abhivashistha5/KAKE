package kake.security;

import java.math.BigInteger;

import java.util.Random;

public class RSAKeyGen{

    public static KeyPair generate(){

        BigInteger p, q, n, phi, e, d;
        int bitLength = Key.KEY_SIZE;
        Random r;

        r = new Random();
        p = BigInteger.probablePrime(bitLength, r);
        q = BigInteger.probablePrime(bitLength, r);
        n = p.multiply(q); // calculate n = p * q
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)); // calculate phi = (p-1)*(q-1)

        e = BigInteger.probablePrime(bitLength / 2 , r);

        while( phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0 ){
            e.add(BigInteger.ONE);
        }

        d = e.modInverse(phi);

        PublicKey publicKey = new PublicKey(n, e);
        PrivateKey privateKey = new PrivateKey(n, d);

        KeyPair pair = new KeyPair(publicKey, privateKey);

        return pair;
    }
}
