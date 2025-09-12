package com.booking.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.booking.client.BookingClient;
import com.booking.dto.APIResponse;
import com.booking.dto.BookingsDto;
import com.booking.dto.PropertyDto;
import com.booking.dto.Room;
import com.booking.dto.RoomAvailability;

@RestController
public class BookingController {

	@Autowired
	BookingClient bookingClient;

	@PostMapping("/add-to-cart")
	public APIResponse<List<String>> cart(@RequestBody BookingsDto bookingDto) {
		APIResponse<List<String>> apiResponse = new APIResponse<>();

		List<String> messages = new ArrayList<>();

		APIResponse<PropertyDto> property = bookingClient.getProperty(bookingDto.getPropertyId());
		APIResponse<Room> room = bookingClient.getRoom(bookingDto.getRoomId());
		APIResponse<List<RoomAvailability>> roomAvailibility = bookingClient
				.getRoomAvailibility(bookingDto.getRoomAvailabilityId());
	}

}
