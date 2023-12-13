package com.abhishek.demo.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;

@Service
public class PushNotificationService {

	@Value("${vapid.public.key}")
	private String publicKey;

	@Value("${vapid.private.key}")
	private String privateKey;

	@Autowired
	private PushService pushService;

	private List<Subscription> subscriptions = new ArrayList<>();

	public String getPublicKey() {
		return publicKey;
	}

	public void subscribe(Subscription subscription) {
		System.out.println("Subscribed to " + subscription.endpoint);
		this.subscriptions.add(subscription);
	}

	public void unsubscribe(String endpoint) {
		System.out.println("Unsubscribed from " + endpoint);
		subscriptions = subscriptions.stream().filter(s -> !endpoint.equals(s.endpoint)).collect(Collectors.toList());
	}

	@Scheduled(fixedRate = 15000)
	private void sendNotifications() {
		System.out.println("Sending notifications to all subscribers");

		var json = """
				{
				  "title": "Server says hello!",
				  "body": "It is now: %s"
				}
				""";

		subscriptions.forEach(subscription -> {
			sendNotification(subscription, String.format(json, LocalTime.now()));
		});
	}

	public void sendNotification(Subscription subscription, String messageJson) {
		try {
			pushService.send(new Notification(subscription, messageJson));
		} catch (GeneralSecurityException | IOException | JoseException | ExecutionException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
