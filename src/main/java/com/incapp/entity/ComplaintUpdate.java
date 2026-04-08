package com.incapp.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "complaint_updates")
public class ComplaintUpdate {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "complaint_id")
    private Complaint complaint;

    @Column(length = 50)
    private String status;

    @Column(columnDefinition = "TEXT")
    private String remark;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


}
