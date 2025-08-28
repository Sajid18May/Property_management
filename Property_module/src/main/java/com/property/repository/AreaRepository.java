package com.property.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.property.entity.Area;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long>{

}
