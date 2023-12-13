package com.abhishek.demo.config;

import java.security.GeneralSecurityException;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import nl.martijndwars.webpush.PushService;


@Configuration
public class Config {

	@Value("${vapid.public.key}")
	private String publicKey;
	
	@Value("${vapid.private.key}")
	private String privateKey;

    @Bean
    public PushService pushService() throws GeneralSecurityException {
        Security.addProvider(new BouncyCastleProvider());
        return new PushService(publicKey, privateKey);
    }
}
