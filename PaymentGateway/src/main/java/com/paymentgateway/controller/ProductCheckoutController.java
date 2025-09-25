package com.paymentgateway.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paymentgateway.client.BookingClient;
import com.paymentgateway.dto.ProductRequest;
import com.paymentgateway.dto.StripeResponse;
import com.paymentgateway.service.GatewayService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

@RestController
@RequestMapping("/product/v1")
public class ProductCheckoutController {

	private GatewayService stripeService;
	private BookingClient bookingClient;
	@Value("${stripe.secretKey}")
	private String STRIPE_KEY;

	public ProductCheckoutController(GatewayService stripeService,BookingClient bookingClient) {
		this.stripeService = stripeService;
		this.bookingClient = bookingClient;
	}

	@PostMapping("/checkout")
	public ResponseEntity<StripeResponse> checkoutProducts(@RequestBody ProductRequest productRequest) {
		StripeResponse stripeResponse = stripeService.checkoutProducts(productRequest);
		return ResponseEntity.status(HttpStatus.OK).body(stripeResponse);
	}

	@GetMapping("/success")
	public String handleSuccess(@RequestParam("session_id") String sessionId, @RequestParam("booking_id") long id) {
		try {
			Session session=Session.retrieve(sessionId);
			String paymentStatus=session.getPaymentStatus();
			System.out.println(sessionId);
			
			if(paymentStatus.equalsIgnoreCase("paid")) {
				System.out.println("Payment Successful");
				boolean result=bookingClient.updateBooking(id);
				
				if(result) {
					//send Email
					
					return "Payment Successful";
				}
				else
					return "Payment Failed";
			}
		} catch (StripeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "Payment not Completed";

	}
}
