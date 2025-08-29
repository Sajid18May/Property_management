package com.property.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomsAvailabilityRepository extends JpaRepository<RoomsAvailabilityRepository, Long>{

}
