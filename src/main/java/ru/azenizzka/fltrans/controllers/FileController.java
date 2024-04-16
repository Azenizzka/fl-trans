package ru.azenizzka.fltrans.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.azenizzka.fltrans.exceptions.EmptyFileException;
import ru.azenizzka.fltrans.exceptions.FileNotFoundException;
import ru.azenizzka.fltrans.models.File;
import ru.azenizzka.fltrans.models.FileDTO;
import ru.azenizzka.fltrans.services.FileService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor

@RestController
@RequestMapping("/api/files")
public class FileController {
	private final FileService fileService;

	@GetMapping()
	@CrossOrigin
	public ResponseEntity<List<FileDTO>> getAll() {
		List<FileDTO> fileDTOS = fileService.findAll().stream()
				.map(file -> {
			FileDTO fileDTO = new FileDTO();
			fileDTO.setId(file.getId());
			fileDTO.setName(file.getName());
			fileDTO.setContentType(file.getContentType());
			return fileDTO;
		}).collect(Collectors.toList());

		return ResponseEntity.ok().body(fileDTOS);
	}

	@GetMapping("/{id}")
	public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) throws FileNotFoundException {
		File file = fileService.findById(id);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", file.getContentType());
		headers.setContentDispositionFormData("attachment", file.getName());

		return new ResponseEntity<>(file.getData(), headers, HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<Map<String, Long>> uploadFile(@RequestParam("file") MultipartFile multipartFile) throws IOException, EmptyFileException {
		if (multipartFile.isEmpty()) {
			throw new EmptyFileException();
		}

		File file = new File();
		file.setName(multipartFile.getOriginalFilename());
		file.setData(multipartFile.getBytes());
		file.setContentType(multipartFile.getContentType());

		Map<String, Long> response = new HashMap<>();
		response.put("id", fileService.save(file).getId());

		return ResponseEntity.ok().body(response);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<File> updateFile(@PathVariable("id") Long id, @RequestParam("file") MultipartFile multipartFile) throws EmptyFileException, FileNotFoundException, IOException {
		if (multipartFile.isEmpty()) {
			throw new EmptyFileException();
		}

		File file = fileService.findById(id);
		file.setData(multipartFile.getBytes());
		file.setName(multipartFile.getOriginalFilename());

		return ResponseEntity.ok(fileService.save(file));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Long id) throws FileNotFoundException {
		fileService.deleteById(id);

		return ResponseEntity.ok().body("successful");
	}

	@ExceptionHandler(EmptyFileException.class)
	public ResponseEntity<String> handleEmptyFileException(EmptyFileException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
	}

	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<String> handleFileNotFoundException(FileNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
	}
}
