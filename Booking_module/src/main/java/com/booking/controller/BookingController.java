package com.booking.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.booking.client.BookingClient;
import com.booking.dto.APIResponse;
import com.booking.dto.BookingsDto;
import com.booking.dto.PropertyDto;
import com.booking.dto.Room;
import com.booking.dto.RoomAvailability;
import com.booking.entity.BookingDate;
import com.booking.entity.Bookings;
import com.booking.repository.BookingDateRepository;
import com.booking.repository.BookingRepository;

@RestController
public class BookingController {

	private BookingClient bookingClient;
	private BookingRepository bookingRepository;
	private BookingDateRepository bookingDateRepository;

	public BookingController(BookingClient bookingClient, BookingRepository bookingRepository,
			BookingDateRepository bookingDateRepository) {
		this.bookingClient = bookingClient;
		this.bookingRepository = bookingRepository;
		this.bookingDateRepository = bookingDateRepository;
	}



	@PostMapping("/add-to-cart")
	public APIResponse<List<String>> cart(@RequestBody BookingsDto bookingDto) {
		APIResponse<List<String>> apiResponse = new APIResponse<>();

		List<String> messages = new ArrayList<>();
		
		APIResponse<PropertyDto> response = bookingClient.getProperty(bookingDto.getPropertyId());
		APIResponse<Room> roomType = bookingClient.getRoom(bookingDto.getRoomId());
		APIResponse<List<RoomAvailability>> roomAvailibility = bookingClient
				.getRoomAvailibility(bookingDto.getRoomAvailabilityId());
		
		List<RoomAvailability> availableRooms=roomAvailibility.getData();
		
		//Logic to check available rooms based on date and count
				for(LocalDate date: bookingDto.getDate()) {
					boolean isAvailable = availableRooms.stream()
					        .anyMatch(ra -> ra.getAvailableDate().equals(date) && ra.getAvailableCount()>0);
					
					    
					    System.out.println("Date " + date + " available: " + isAvailable);
					    
					    if (!isAvailable) {
					    	 messages.add("Room not available on: " + date);
					    	 apiResponse.setMessage("Sold Out");
					 		 apiResponse.setStatus(500);
					 		 apiResponse.setData(messages);
					 		 return apiResponse;
					    }
					    
				}
				//Save it to Booking Table with status pending
				Bookings bookings = new Bookings();
				bookings.setName(bookingDto.getName());
				bookings.setEmail(bookingDto.getEmail());
				bookings.setMobile(bookingDto.getMobile());
				bookings.setPropertyName(response.getData().getName());
				bookings.setStatus("pending");
				bookings.setTotalPrice(roomType.getData().getBasePrice()*bookingDto.getTotalNigths());
				Bookings savedBooking = bookingRepository.save(bookings);
				
				for(LocalDate date: bookingDto.getDate()) {
					BookingDate  bookingDate = new BookingDate();
					bookingDate.setDate(date);
					bookingDate.setBookings(savedBooking);
					bookingDateRepository.save(bookingDate);
				}
				
				
				
				
				return null;
	}

}
