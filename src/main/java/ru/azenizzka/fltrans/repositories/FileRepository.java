package ru.azenizzka.fltrans.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.azenizzka.fltrans.models.File;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
	boolean existsById(Long id);
}
