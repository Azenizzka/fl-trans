package ru.azenizzka.fltrans.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@EqualsAndHashCode
@Getter
@Setter

@Entity
@Table
public class File {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String contentType;

	private final LocalDateTime createdTime;

	@Setter(AccessLevel.PRIVATE)
	private LocalDateTime lastModifiedTime;

	@Lob
	private byte[] data;

	public void setName(String name) {
		this.name = name;
		lastModifiedTime = LocalDateTime.now();
	}

	public void setData(byte[] data) {
		this.data = data;
		lastModifiedTime = LocalDateTime.now();
	}


	public File() {
		this.createdTime = LocalDateTime.now();
		this.lastModifiedTime = LocalDateTime.now();
	}
}
