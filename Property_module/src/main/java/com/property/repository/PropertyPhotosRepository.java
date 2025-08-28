package com.property.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.property.entity.PropertyPhotos;

@Repository
public interface PropertyPhotosRepository extends JpaRepository<PropertyPhotos, Long>{

}
