package com.twilioservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class TwilioConfig {
	@Value("${twilio.account.sid}")
	private String sid;
	@Value("${twilio.account.token}")
	private String token;
	@Value("${twilio.account.number}")
	private String number;
	@Value("${twilio.account.whatsapp.number}")
    private String twilioWhatsappNumber;
	
	public String getSid() {
		return sid;
	}
	public String getToken() {
		return token;
	}
	public String getNumber() {
		return number;
	}
	public String getTwilioWhatsappNumber() {
		return twilioWhatsappNumber;
	}

}
