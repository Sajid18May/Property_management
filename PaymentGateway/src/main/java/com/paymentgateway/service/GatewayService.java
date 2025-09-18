package com.paymentgateway.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.paymentgateway.dto.ProductRequest;
import com.paymentgateway.dto.StripeResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionCreateParams.LineItem;
import com.stripe.param.checkout.SessionCreateParams.LineItem.PriceData;
import com.stripe.param.checkout.SessionCreateParams.LineItem.PriceData.ProductData;
import com.stripe.param.checkout.SessionCreateParams.Mode;

@Service
public class GatewayService {

	@Value("${stripe.secretKey}")
	private String STRIPE_KEY;

	public StripeResponse checkoutProducts(ProductRequest productRequest) {

		Stripe.apiKey = STRIPE_KEY;

		ProductData productData = ProductData.builder().setName(productRequest.getName()).build();

		PriceData priceData = PriceData.builder()
				.setCurrency(productRequest.getCurrency() != null ? productRequest.getCurrency() : "USD")
				.setUnitAmount(productRequest.getAmount()).setProductData(productData).build();

		LineItem lineItem = LineItem.builder().setQuantity(productRequest.getQuantity()).setPriceData(priceData)
				.build();
		
		SessionCreateParams params = SessionCreateParams.builder().setMode(Mode.PAYMENT)
				.setSuccessUrl("http://localhost:8080/success")
                .setCancelUrl("http://localhost:8080/cancel")
                .addLineItem(lineItem)
                .build();
		
		   // Create new session
        Session session = null;
        try {
            session = Session.create(params);
        } catch (StripeException e) {
            //log the error
        	System.out.println(e);
        }
        StripeResponse response = new StripeResponse();
        response.setStatus("SUCCESS");
        response.setMessage("Payment session created");
        response.setSessionId(session.getId());
        response.setSessionUrl(session.getUrl());

        return response;
 
    }
}
