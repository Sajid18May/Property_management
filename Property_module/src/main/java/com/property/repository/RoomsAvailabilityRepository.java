package com.property.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.property.entity.RoomAvailability;

@Repository
public interface RoomsAvailabilityRepository extends JpaRepository<RoomAvailability, Long>{

}
