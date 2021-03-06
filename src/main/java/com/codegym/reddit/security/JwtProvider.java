package com.codegym.reddit.security;

import com.codegym.reddit.exceptions.SpringRedditException;
import com.codegym.reddit.model.User;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

@Service
public class JwtProvider {
    private KeyStore keyStore;
    @PostConstruct
    public void init(){
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/spring.jks");
            keyStore.load(resourceAsStream,"secret".toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
           throw  new SpringRedditException("Exception occured  while loading keystore");
        }
//        try {
//            keyStore = KeyStore.getInstance("JKS");
//            InputStream resourceAsStream = getClass().getResourceAsStream("/spring.jks");
//            keyStore.load(resourceAsStream, "secret".toCharArray());
//        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
//            throw new SpringRedditException("Exception occurred while loading keystore", e);
//        }
    }
    public String generateToken(Authentication authentication){
       User principal =  (User) authentication.getPrincipal();
       return Jwts.builder().setSubject(principal.getUsername()).signWith(getPrivateKey()).compact();
    }

    private PrivateKey getPrivateKey() {
        try{
            return (PrivateKey) keyStore.getKey("spring","secret".toCharArray());

        }catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e){
            throw new SpringRedditException("Exception occured  while retrieving public key from keystore");
        }
//        try {
//            return (PrivateKey) keyStore.getCertificate("spring").getPublicKey();
//        } catch (KeyStoreException e) {
//            throw new SpringRedditException("Exception occured while " +
//                    "retrieving public key from keystore", e);
//        }
    }
}
