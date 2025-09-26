package com.notificationservice.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.notificationservice.dto.EmailRequest;

@Service
public class EmailConsumer {
	
	@Autowired
	JavaMailSender javaMailSender;

    @KafkaListener(topics = "send_email", groupId = "notification-group")
	public void consume(EmailRequest emailRequest) {
		SimpleMailMessage message=new SimpleMailMessage();
		message.setTo(emailRequest.getTo());
		message.setSubject(emailRequest.getSubject());
		message.setText(emailRequest.getBody());
		
		javaMailSender.send(message);
	}
}
