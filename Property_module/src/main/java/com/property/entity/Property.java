package com.property.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Property {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private int noOfRooms;
	private int noOfBeds;
	private int noOfBathRooms;
	private int noOfGuestsAllowed;
	
	@ManyToOne
	@JoinColumn(name="state_id")
	private State state;
	
	@ManyToOne
	@JoinColumn(name="city_id")
	private City city;
	
	@ManyToOne
	@JoinColumn(name="area_id")
	private Area area;
	
	@OneToMany(mappedBy = "property",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Room> rooms;
	
	@OneToMany(mappedBy = "property",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<PropertyPhotos> photos;

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

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public List<PropertyPhotos> getPhotos() {
		return photos;
	}

	public void setPhotos(List<PropertyPhotos> photos) {
		this.photos = photos;
	}
	
}
