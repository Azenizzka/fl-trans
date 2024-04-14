package ru.azenizzka.fltrans.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.azenizzka.fltrans.exceptions.FileNotFoundException;
import ru.azenizzka.fltrans.models.File;
import ru.azenizzka.fltrans.repositories.FileRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor

@Service
public class FileService {
	private final FileRepository fileRepository;

	public List<File> findAll() {
		return fileRepository.findAll();
	}

	public File save(File file) {
		return fileRepository.save(file);
	}

	public File findById(Long id) throws FileNotFoundException {
		return fileRepository.findById(id)
				.orElseThrow(FileNotFoundException::new);
	}

	public void deleteById(Long id) throws FileNotFoundException {
		if (!fileRepository.existsById(id)) {
			throw new FileNotFoundException();
		}

		fileRepository.deleteById(id);
	}
}