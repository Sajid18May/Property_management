package com.property.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.property.dto.APIResponse;
import com.property.dto.PropertyDto;
import com.property.service.PropertyService;

@RestController
@RequestMapping("/api/v1/property")
public class PropertyController {

	private PropertyService propertyService;

	public PropertyController(PropertyService propertyService) {
		this.propertyService = propertyService;
	}

	@PostMapping(value = "/add-property" ,
		    consumes = MediaType.MULTIPART_FORM_DATA_VALUE,  // Ensures the endpoint accepts multipart/form-data
		    produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<APIResponse<PropertyDto>> addProperty(@RequestParam("property") String propertyJson,
			@RequestParam("files") MultipartFile[] files) {
		System.out.println("Incoming JSON: " + propertyJson);

		ObjectMapper objectMapper = new ObjectMapper();
		PropertyDto dto = null;
		try {
			dto = objectMapper.readValue(propertyJson, PropertyDto.class);
		} catch (JsonProcessingException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		System.out.println(dto.toString());
		PropertyDto property = propertyService.addProperty(dto,files);

		// Create response object
		APIResponse<PropertyDto> response = new APIResponse<>();
		response.setMessage("Property added");
		response.setStatus(201);
		response.setData(property);

		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
	}

}
