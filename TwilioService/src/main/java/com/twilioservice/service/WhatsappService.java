package com.twilioservice.service;

import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.twilioservice.config.TwilioConfig;

@Service
public class WhatsappService {
	
	    private TwilioConfig twilioConfig;

	    public WhatsappService(TwilioConfig twilioConfig) {
			this.twilioConfig = twilioConfig;
			Twilio.init(twilioConfig.getSid(), twilioConfig.getToken());
		}

		public String sendWhatsAppMessage(String toPhoneNumber, String messageBody) {
	        Message message = Message.creator(
	                new PhoneNumber("whatsapp:+91" + toPhoneNumber),   // Recipient's phone number (in WhatsApp format)
	                new PhoneNumber(twilioConfig.getTwilioWhatsappNumber()), // Twilio WhatsApp number
	                messageBody)
	            .create();

	        System.out.println("Message sent with SID: " + message.getSid());
	        return "Whatsapp Message sent successfully!";
	    }
}
