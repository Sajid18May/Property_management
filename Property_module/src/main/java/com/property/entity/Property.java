package com.property.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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
	
	@
	private State state;
	private City city;
	private Area area;
	
	private List<Rooms> rooms;
	
}
