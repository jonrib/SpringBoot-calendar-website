package com.jonrib.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jonrib.auth.model.Calendar;

public interface CalendarRepository extends JpaRepository<Calendar, Long>{

}
