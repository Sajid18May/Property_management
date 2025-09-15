package com.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booking.entity.BookingDate;

@Repository
public interface BookingDateRepository extends JpaRepository<BookingDate, Long>{

}
