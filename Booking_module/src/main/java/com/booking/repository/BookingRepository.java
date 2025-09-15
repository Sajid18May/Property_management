package com.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booking.entity.Bookings;

@Repository
public interface BookingRepository extends JpaRepository<Bookings, Long>{

}
