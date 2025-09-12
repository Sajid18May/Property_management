package com.property.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.property.dto.APIResponse;
import com.property.dto.PropertyDto;
import com.property.dto.RoomsDto;
import com.property.entity.Area;
import com.property.entity.City;
import com.property.entity.Property;
import com.property.entity.PropertyPhotos;
import com.property.entity.Room;
import com.property.entity.RoomAvailability;
import com.property.entity.State;
import com.property.repository.AreaRepository;
import com.property.repository.CityRepository;
import com.property.repository.PropertyPhotosRepository;
import com.property.repository.PropertyRepository;
import com.property.repository.RoomRepository;
import com.property.repository.RoomsAvailabilityRepository;
import com.property.repository.StateRepository;

@Service
public class PropertyService {
	private PropertyRepository propertyRepository;
	private AreaRepository areaRepository;
	private CityRepository cityRepository;
	private StateRepository stateRepository;
	private RoomRepository roomsRepository;
	private RoomsAvailabilityRepository roomsAvailabilityRepository;
	private PropertyPhotosRepository photosRepository;
	private S3Service s3Service;

	public PropertyService(PropertyRepository propertyRepository, AreaRepository areaRepository,
			CityRepository cityRepository, StateRepository stateRepository, RoomRepository roomsRepository,
			RoomsAvailabilityRepository roomsAvailabilityRepository, PropertyPhotosRepository photosRepository,
			S3Service s3Service) {
		this.propertyRepository = propertyRepository;
		this.areaRepository = areaRepository;
		this.cityRepository = cityRepository;
		this.stateRepository = stateRepository;
		this.roomsRepository = roomsRepository;
		this.roomsAvailabilityRepository = roomsAvailabilityRepository;
		this.photosRepository = photosRepository;
		this.s3Service = s3Service;
	}

	public PropertyDto addProperty(PropertyDto propertyDto, MultipartFile[] files) {
		State state = stateRepository.findByName(propertyDto.getState());
		if(state==null) {
			State stateNew=new State();
			stateNew.setName(propertyDto.getState());
			stateRepository.save(stateNew);
			state=stateNew;
		}
		City city = cityRepository.findByName(propertyDto.getCity());
		if(city==null) {
			City cityNew=new City();
			cityNew.setName(propertyDto.getCity());
			cityRepository.save(cityNew);
			city=cityNew;
		}
		Area area = areaRepository.findByName(propertyDto.getArea());
		if(area==null) {
			Area areaNew=new Area();
			areaNew.setName(propertyDto.getArea());
			areaRepository.save(areaNew);
			area=areaNew;
		}
		Property property = new Property();
		BeanUtils.copyProperties(propertyDto, property);
		property.setArea(area);
		property.setCity(city);
		property.setState(state);

		Property savedProperty = propertyRepository.save(property);
		List<String> fileUrls=s3Service.uploadFiles(files);
		List<PropertyPhotos> propertyphotos=new ArrayList<>();
		for(String url:fileUrls) {
			PropertyPhotos photos=new PropertyPhotos();
			photos.setUrl(url);
			photos.setProperty(savedProperty);
			photosRepository.save(photos);
			propertyphotos.add(photos);
		}
		PropertyDto dto = new PropertyDto();
		
		BeanUtils.copyProperties(savedProperty, dto);
		dto.setArea(savedProperty.getArea().getName());
		dto.setCity(savedProperty.getCity().getName());
		dto.setState(savedProperty.getState().getName());
		dto.setImgUrls(fileUrls);
		return dto;
	}
	
	public APIResponse<List<Property>> searchProperty(String name,LocalDate date) {
		List<Property> properties = propertyRepository.searchProperty(name, date);
		APIResponse<List<Property>> response=new APIResponse<>();
		if(properties.isEmpty()) {
			response.setMessage("No Property Found");
			response.setStatus(404);
			response.setData(null);
			return response;
		}
		response.setMessage("Property Found");
		response.setStatus(200);
		response.setData(properties);
		return response;
	}
	public APIResponse<PropertyDto> findProperty(long id) {
		APIResponse<PropertyDto> response =new APIResponse<>();
		Optional<Property> property = propertyRepository.findById(id);
		PropertyDto dto=new PropertyDto();
		if(property.isPresent()) {
			BeanUtils.copyProperties(property.get(), dto);
			dto.setArea(property.get().getArea().getName());
			dto.setCity(property.get().getCity().getName());
			dto.setState(property.get().getState().getName());
			List<Room> rooms=property.get().getRooms();
			List<RoomsDto> roomsDto=new ArrayList<>();
			for(Room room:rooms) {
				RoomsDto roomDto=new RoomsDto();
				BeanUtils.copyProperties(room, roomDto);
				roomsDto.add(roomDto);
			}
			dto.setRooms(roomsDto);
			response.setMessage("Matching Record");
			response.setStatus(200);
			response.setData(dto);
			return response;
		}
		
		return null;
	}
	
	public APIResponse<List<RoomAvailability>> findRoomAvailibility(long id){

		APIResponse<List<RoomAvailability>> response =new APIResponse<>();
		List<RoomAvailability> roomAvailibility = roomsAvailabilityRepository.findByRoomId(id);
		response.setData(roomAvailibility);
		response.setMessage("Got Availibility");
		response.setStatus(200);
		
		return response;
	}
	
	public APIResponse<Room> findRoom(long id){
		APIResponse<Room> response=new APIResponse<>();
		Optional<Room> room = roomsRepository.findById(id);
		
		response.setData(room.get());
		response.setMessage("Got Room");
		response.setStatus(200);
		
		return response;
	}

}
