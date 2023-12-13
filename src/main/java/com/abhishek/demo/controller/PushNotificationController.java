package com.abhishek.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abhishek.demo.service.PushNotificationService;

import nl.martijndwars.webpush.Subscription;

@RestController
public class PushNotificationController {

	@Autowired
	PushNotificationService pushNotificationService;

	@GetMapping("/api/v1/getKey")
	public ResponseEntity<?> getKey() {
		return new ResponseEntity<>(pushNotificationService.getPublicKey(), HttpStatus.OK);
	}

	@PostMapping("/api/v1/subscribe")
	public ResponseEntity<?> subscribe(@RequestBody Subscription subscription) {
		pushNotificationService.subscribe(subscription);
		return new ResponseEntity<>(true, HttpStatus.OK);
	}

	@GetMapping("/api/v1/unsubscribe")
	public ResponseEntity<?> subscribe(@RequestParam String endpoint) {
		pushNotificationService.unsubscribe(endpoint);
		return new ResponseEntity<>(true, HttpStatus.OK);
	}

}
