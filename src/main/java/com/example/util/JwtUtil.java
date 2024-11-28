package com.example.util;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jwt.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.List;

public class JwtUtil {

    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);
    private static final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds

    private static final String PRIVATE_KEY = """
-----BEGIN PRIVATE KEY-----
MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCigCApKE7jgHjf
djXMRAhDMrj3uFQz+P7AZqY+c5cdxSPX0ETX8CspYz/QmVoOfWHSbhICReEmW67D
OWZa4Eg4IBoI9Le0nu66GAwdNskjRcq/9XWkizumn2oaIxsWBFD3HlwKpj7TqT8m
9vdW5EAqQg/ib/iLsQPEJIyFISm5LwKK/iG3nxQ4dcBdKKRJpD3ZzamVx75uk5V2
mLsQedOIK27hDKsLl04qSG8Vhvxq423NSbhkfcpHTBFBZcTycTIg+SBBkT9Ibg7h
aKeD3EGuGeG/+N/YbbW0Wi6yO4r+76bIgOLmZIS7JbOgasidjDo+iomWAzP+1nMs
hCuNQ015AgMBAAECggEAAorxteMpFNtCSPDfF6Kq5G5LQakTPDr1tYoFqO2DHFak
4HAkW6Ko8j1Y1slHhPmGSdYqFvoD82c03V4tEVf4GVS7Q4+cvklMYAIXUm+0hwZF
DWroW9pH5SwSa3VIK7iGkc9aTlpamlJIKDJuHWB19pNRD1CEOZ9b+To/LqgdsIWa
4vF0+fGCLZLr2WBKekyOQZmXJpSSk7uvVRjSCJMm1RT8oiDoRIk9I9lJfbaABNF4
F6dejbZFtoqTaU0YgwE599zOtBLHBhRh0NfzZwdEO4kdXPBETEdkdYc2EiUi9I1s
mIhwO6A0iKUzHdQBx1eu0Jw255McvlHimKdAxP9AdwKBgQDeTHO28a71GbbrOQM1
iwfiXZc0+hcwnFF/GNIYG9QuNh+eM2qcnxMsAJESMSaiMvpV8v0FnPX11mDxY4br
lQs9VRAzMGFU69zI9gN0Chto8yTcD7xu8rSy0ASdhimyM9x5rKMUZlQp/Fy2BhuX
W6bGY8si0jQDuFV8c2QnTt6VIwKBgQC7It7QRkD5B8EZN5UuMQ9M173xwdP2d3Nf
sD3t30HQstMYK3BHuqZqCSg80GhoXjt+q+COEBfDQ0m/aenhNAY6bBs1/XiI6kIq
R1GrDj5Y94gVBxORFrpzH1SLN4BI9OsynItcZr3PfJLhIzP0okPGaQgqh+ST5X0d
iBwKq/pCswKBgGQHXuJpamLcCVVbODh1sTyJBKtQBfNXw8w/LPqwOw/4Rs33MPf9
FLFuJ9lZyJqf+cmHJ5fJA1x19PVp5aJIVOBqUHnYQVPnNlgva2phyee10VY8tnvu
NqVnuUvwvP51G7acml1plmG2LzmxEeVP1oHbEeM/Vyx9W+o+Okj90malAoGAYi2e
NijVh5Fg5mj51D0a6Na7KW3mVYU/u3EMXJLnxaU8CI8pngg3rKPXP2ibjj35+rAT
5JRe0yunMkxPzr3rkpULBZ1QsowsYGAAYbzcSvK6q0WGJ3WEJkfGpC9fF2efSXNF
zhl89bFGoeVFKdBDvIF4LlATROB+bpLtG1A0wO8CgYAppUcVO+pU+rHGnDu/i7Lw
2EOCLW4VZXKM4X9v1CxY58L4u860wIPs5EaENtwqJp84Rs/Wk/RoqGRd3i4raYN3
TUe/DOqoO8v1MsUzdM4849huKcRDxRgWuAltdlu6US1ePu3Ri3nKIRW4+ZnhZMc5
TLUQg/qNxzrvXSkQdaKfaw==
-----END PRIVATE KEY-----
""";

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

    /**
     * Retrieves the private key for signing the JWT.
     * @return RSAPrivateKey
     * @throws Exception if key parsing fails
     */
    private static RSAPrivateKey getPrivateKey() throws Exception {
        String privateKeyPEM = PRIVATE_KEY
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", ""); // Removes newlines and spaces

        log.debug("Decoded private key: {}", privateKeyPEM);

        try {
            byte[] keyBytes = Base64.getDecoder().decode(privateKeyPEM);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(spec);
        } catch (Exception e) {
            log.error("Failed to parse private key. Ensure the key is in PKCS8 format.", e);
            throw e;
        }
    }

    /**
     * Retrieves the public key for validating the JWT.
     * @return RSAPublicKey
     * @throws Exception if key parsing fails
     */
    private static RSAPublicKey getPublicKey() throws Exception {
        String publicKeyPEM = PUBLIC_KEY
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", ""); // Removes newlines and spaces

        log.debug("Decoded public key: {}", publicKeyPEM);

        try {
            byte[] keyBytes = Base64.getDecoder().decode(publicKeyPEM);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPublicKey) keyFactory.generatePublic(spec);
        } catch (Exception e) {
            log.error("Failed to parse public key. Ensure the key is in X.509 format.", e);
            throw e;
        }
    }

    /**
     * Generates a signed JWT token with the given email and roles.
     * @param email The user's email (subject)
     * @param roles List of roles to be included in the JWT claims
     * @return Signed JWT token
     */
    public static String generateToken(String email, List<String> roles) {
        try {
            log.debug("Generating token for email: {}, roles: {}", email, roles);

            JWSSigner signer = new RSASSASigner(getPrivateKey());

            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(email)
                    .claim("roles", roles)
                    .audience("user-service") // Add audience claim
                    .issueTime(new Date())
                    .expirationTime(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .build();

            SignedJWT signedJWT = new SignedJWT(
                    new JWSHeader.Builder(JWSAlgorithm.RS256).build(),
                    claimsSet
            );

            signedJWT.sign(signer);
            log.debug("Token generated successfully");

            return signedJWT.serialize();
        } catch (Exception e) {
            log.error("Error generating token", e);
            throw new IllegalStateException("Failed to generate token", e);
        }
    }

    /**
     * Validates a JWT token using the public key.
     * @param token JWT token to validate
     * @return true if the token is valid; false otherwise
     */
    public static boolean validateToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new RSASSAVerifier(getPublicKey());
            return signedJWT.verify(verifier);
        } catch (Exception e) {
            log.error("Token validation failed", e);
            return false;
        }
    }

    /**
     * Extracts the email (subject) from a JWT token.
     * @param token JWT token
     * @return Email extracted from the token
     */
    public static String getEmailFromToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getSubject();
        } catch (Exception e) {
            log.error("Failed to extract email from token", e);
            throw new IllegalStateException("Failed to extract email from token", e);
        }
    }

    /**
     * Extracts the roles from a JWT token.
     * @param token JWT token
     * @return List of roles extracted from the token
     */
    public static List<String> getRolesFromToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return (List<String>) signedJWT.getJWTClaimsSet().getClaim("roles");
        } catch (Exception e) {
            log.error("Failed to extract roles from token", e);
            throw new IllegalStateException("Failed to extract roles from token", e);
        }
    }
}
