package com.twilioservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.twilioservice.service.SmsService;
import com.twilioservice.service.WhatsappService;

@RestController
public class SmsContrller {

	private SmsService smsService;
	private WhatsappService whatsappService;
	
	public SmsContrller(SmsService smsService, WhatsappService whatsappService) {
		this.smsService = smsService;
		this.whatsappService = whatsappService;
	}

	@GetMapping(value = "/sendSMS")
    public ResponseEntity<String> sendSMS(@RequestParam String to,@RequestParam String message) {

            String result = smsService.sendSMS(to, message);

            return new ResponseEntity<String>(result, HttpStatus.OK);
    }
	
	@PostMapping("/send")
    public ResponseEntity<String> sendWhatsAppMessage(@RequestParam String toPhoneNumber, @RequestParam String messageBody) {
		String whatsAppMessage = whatsappService.sendWhatsAppMessage(toPhoneNumber, messageBody);
        return new ResponseEntity<String>(whatsAppMessage, HttpStatus.OK);
    }
}
