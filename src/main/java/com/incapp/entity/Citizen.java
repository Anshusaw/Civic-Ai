package com.incapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "citizens")
public class Citizen {
	@Id
	private String email;
	private String name;
	private String gender;
	private String phone;
	private String address;
}
