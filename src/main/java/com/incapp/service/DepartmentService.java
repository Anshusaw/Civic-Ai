package com.incapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.incapp.entity.Department;
import com.incapp.repo.DepartmentRepository;

@Service
public class DepartmentService {
	@Autowired
	private DepartmentRepository departmentRepository ;

	
	public boolean save(String name) {
		Department d=departmentRepository.findByName(name);
		if(d==null) {
			Department department = new Department();
			department.setName(name);
			
			departmentRepository.save(department);
			return true;
		}
		else {
			return false;
		}
	}


	public List<Department> getAll() {
		
//		return departmentRepository.findAll();
		return departmentRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}

}
