package com.incapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incapp.entity.Citizen;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen, String>{
	
}
