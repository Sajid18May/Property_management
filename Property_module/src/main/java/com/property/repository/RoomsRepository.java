package com.property.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.property.entity.Rooms;

@Repository
public interface RoomsRepository extends JpaRepository<Rooms, Long>{

}
