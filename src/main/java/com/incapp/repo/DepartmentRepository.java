package com.incapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incapp.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>{
	Department findByName(String name);
}
