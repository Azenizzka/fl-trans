package ru.azenizzka.fltrans.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter

@NoArgsConstructor
public class FileDTO {
	private Long id;
	private String name;
	private String contentType;
}
