package com.twilioservice.service;

import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.twilioservice.config.TwilioConfig;

@Service
public class SmsService {
	private final TwilioConfig twilioConfig;
	
	public SmsService(TwilioConfig twilioConfig) {
		this.twilioConfig = twilioConfig;
		Twilio.init(twilioConfig.getSid(), twilioConfig.getToken());
	}

	public String sendSMS(String number,String messageBody) {
		Message message = Message.creator(
                new PhoneNumber("+91"+number),     // To phone number
                new PhoneNumber(twilioConfig.getNumber()),   // From Twilio number
                messageBody)                        // Message content
                .create();
		return "SMS send Succesfully";
	}
}
