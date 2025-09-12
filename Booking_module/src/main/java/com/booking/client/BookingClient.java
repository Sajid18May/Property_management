package com.booking.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.booking.dto.APIResponse;
import com.booking.dto.PropertyDto;
import com.booking.dto.Room;
import com.booking.dto.RoomAvailability;

@FeignClient(name="PROPERTY_MODULE")
public interface BookingClient {
	
	@GetMapping("/api/v1/property/property-id")
	public APIResponse<PropertyDto> getProperty(long id);
	
	@GetMapping("/api/v1/property/room-id")
	public APIResponse<Room> getRoom(long id);
	
	@GetMapping("/api/v1/property/roomAvailibility-id")
	public APIResponse<List<RoomAvailability>> getRoomAvailibility(long id);

}
