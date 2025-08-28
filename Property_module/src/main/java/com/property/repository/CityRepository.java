package com.property.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.property.entity.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long>{

}
