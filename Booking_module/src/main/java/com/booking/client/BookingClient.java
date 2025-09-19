package com.booking.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.booking.dto.APIResponse;
import com.booking.dto.PropertyDto;
import com.booking.dto.Room;
import com.booking.dto.RoomAvailability;

@FeignClient(name="PROPERTY-MODULE")
public interface BookingClient {
	
	@GetMapping("/api/v1/property/property-id")
	public APIResponse<PropertyDto> getProperty(@RequestParam long id);
	
	@GetMapping("/api/v1/property/room-id")
	public APIResponse<Room> getRoom(@RequestParam long id);
	
	@GetMapping("/api/v1/property/roomAvailibility-id")
	public APIResponse<List<RoomAvailability>> getRoomAvailibility(@RequestParam long id);

}
