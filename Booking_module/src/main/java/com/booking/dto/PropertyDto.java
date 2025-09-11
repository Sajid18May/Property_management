package com.booking.dto;

import java.util.List;


public class PropertyDto {
	private long id;
	private String name;
	private int noOfRooms;
	private int noOfBeds;
	private int noOfBathRooms;
	private int noOfGuestsAllowed;
	private String state;
	private String city;
	private String area;
	private List<RoomsDto> rooms;
	private List<String> imageUrls;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNoOfRooms() {
		return noOfRooms;
	}
	public void setNoOfRooms(int noOfRooms) {
		this.noOfRooms = noOfRooms;
	}
	public int getNoOfBeds() {
		return noOfBeds;
	}
	public void setNoOfBeds(int noOfBeds) {
		this.noOfBeds = noOfBeds;
	}
	public int getNoOfBathRooms() {
		return noOfBathRooms;
	}
	public void setNoOfBathRooms(int noOfBathRooms) {
		this.noOfBathRooms = noOfBathRooms;
	}
	public int getNoOfGuestsAllowed() {
		return noOfGuestsAllowed;
	}
	public void setNoOfGuestsAllowed(int noOfGuestsAllowed) {
		this.noOfGuestsAllowed = noOfGuestsAllowed;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public List<RoomsDto> getRooms() {
		return rooms;
	}
	public void setRooms(List<RoomsDto> rooms) {
		this.rooms = rooms;
	}
	public List<String> getImageUrls() {
		return imageUrls;
	}
	public void setImageUrls(List<String> imageUrls) {
		this.imageUrls = imageUrls;
	}
	
}
