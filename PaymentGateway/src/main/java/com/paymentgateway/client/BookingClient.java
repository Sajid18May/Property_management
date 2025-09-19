package com.paymentgateway.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="BOOKINGSERVICE")
public class BookingClient {

}
