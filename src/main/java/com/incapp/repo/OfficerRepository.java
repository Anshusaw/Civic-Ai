package com.incapp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incapp.entity.Officer;

@Repository
public interface OfficerRepository extends JpaRepository<Officer, Long>{
	Officer findByEmail(String email);

	List<Officer> findByDepartmentId(Long id);
}
