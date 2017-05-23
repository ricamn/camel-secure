package com.ricamn.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingsController {

	@RequestMapping(value = "/greetings")
	public ResponseEntity<Map<String, String>> sayHello() {
		Map<String, String> messages = new HashMap<>();
		messages.put("greeting", "Hello");
		return new ResponseEntity<Map<String, String>>(messages, HttpStatus.OK);
	}
}
