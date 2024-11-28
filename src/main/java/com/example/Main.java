package com.example;

import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.SignedJWT;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Main {
    private static final String PUBLIC_KEY = """
-----BEGIN PUBLIC KEY-----
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAooAgKShO44B433Y1zEQI
QzK497hUM/j+wGamPnOXHcUj19BE1/ArKWM/0JlaDn1h0m4SAkXhJluuwzlmWuBI
OCAaCPS3tJ7uuhgMHTbJI0XKv/V1pIs7pp9qGiMbFgRQ9x5cCqY+06k/Jvb3VuRA
KkIP4m/4i7EDxCSMhSEpuS8Civ4ht58UOHXAXSikSaQ92c2plce+bpOVdpi7EHnT
iCtu4QyrC5dOKkhvFYb8auNtzUm4ZH3KR0wRQWXE8nEyIPkgQZE/SG4O4Wing9xB
rhnhv/jf2G21tFousjuK/u+myIDi5mSEuyWzoGrInYw6PoqJlgMz/tZzLIQrjUNN
eQIDAQAB
-----END PUBLIC KEY-----
""";

    public static boolean validateToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);

            String kid = signedJWT.getHeader().getKeyID();
            System.out.println("Key ID (kid): " + kid);

            RSAPublicKey publicKey = getPublicKey();
            JWSVerifier verifier = new RSASSAVerifier(publicKey);

            return signedJWT.verify(verifier);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static RSAPublicKey getPublicKey() throws Exception {
        String publicKeyPEM = PUBLIC_KEY
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", "");

        byte[] keyBytes = Base64.getDecoder().decode(publicKeyPEM);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) keyFactory.generatePublic(spec);
    }

    public static void main(String[] args) {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGV4YW1wbGUuY29tIiwiYXVkIjoidXNlci1zZXJ2aWNlIiwiZXhwIjoxNzMyNzg2ODg0LCJpYXQiOjE3MzI3MDA0ODQsInJvbGVzIjpbIlJPTEVfVVNFUiJdfQ.FoLCbBGp3flVGo7RDkYbJK2ew07qqwXwPrJkJFnB7Zbs2QTGYjrtdz7ShB7UBuVZOtrabBkQ1i625c1DehBGUYgmDDRLE4yHkYPHTKj4O8Dxueu6RgzrEcboFBJOpxUzX_Hr6RaZ1DpZBstg0VeWiZNFAfjZX0Pl7dLyJfgXpWHJO_Y5NaOfMDcFPA9Jlcs5p6jLqm7zGlbeMxVOtmoXLh27Xr0AKozKX9Xoax3PDt4F-mCjOBIOjuH26xIqOAXQde-KZ2rB2CZpb0evAVm_lFuGwvRElo9OEV0_l4He4VV_80ZHoVumcmPROSFy-7EE6O9b2m27NFH7WiT8OXOO3A";
        boolean isValid = validateToken(token);
        System.out.println("Is Token Valid? " + isValid);
    }

}