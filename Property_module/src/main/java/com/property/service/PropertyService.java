package com.property.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.property.dto.PropertyDto;
import com.property.entity.Area;
import com.property.entity.City;
import com.property.entity.Property;
import com.property.entity.State;
import com.property.repository.AreaRepository;
import com.property.repository.CityRepository;
import com.property.repository.PropertyPhotosRepository;
import com.property.repository.PropertyRepository;
import com.property.repository.RoomsAvailabilityRepository;
import com.property.repository.RoomsRepository;
import com.property.repository.StateRepository;

@Service
public class PropertyService {
	private PropertyRepository propertyRepository;
	private AreaRepository areaRepository;
	private CityRepository cityRepository;
	private StateRepository stateRepository;
	private RoomsRepository roomsRepository;
	private RoomsAvailabilityRepository roomsAvailabilityRepository;
	private PropertyPhotosRepository photosRepository;

	public PropertyService(PropertyRepository propertyRepository, AreaRepository areaRepository,
			CityRepository cityRepository, StateRepository stateRepository, RoomsRepository roomsRepository,
			RoomsAvailabilityRepository roomsAvailabilityRepository, PropertyPhotosRepository photosRepository) {
		this.propertyRepository = propertyRepository;
		this.areaRepository = areaRepository;
		this.cityRepository = cityRepository;
		this.stateRepository = stateRepository;
		this.roomsRepository = roomsRepository;
		this.roomsAvailabilityRepository = roomsAvailabilityRepository;
		this.photosRepository = photosRepository;
	}
	
	public PropertyDto addProperty(PropertyDto propertyDto) {
		State state=stateRepository.findByName(propertyDto.getState());
		City city=cityRepository.findByName(propertyDto.getCity());
		Area area=areaRepository.findByName(propertyDto.getArea());
		Property property=new Property();
		BeanUtils.copyProperties(propertyDto, property);
		property.setArea(area);
		property.setCity(city);
		property.setState(state);
		
		Property savedProperty = propertyRepository.save(property);
		PropertyDto dto=new PropertyDto();
		
		BeanUtils.copyProperties(savedProperty, dto);
		dto.setArea(savedProperty.getArea().getName());
		dto.setCity(savedProperty.getCity().getName());
		dto.setState(savedProperty.getState().getName());
		
		return dto;
	}

}
