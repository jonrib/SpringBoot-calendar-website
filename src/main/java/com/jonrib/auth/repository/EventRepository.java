package com.jonrib.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jonrib.auth.model.Event;

public interface EventRepository extends JpaRepository<Event, Long>{

}
