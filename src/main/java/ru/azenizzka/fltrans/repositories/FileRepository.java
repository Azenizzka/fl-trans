package ru.azenizzka.fltrans.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.azenizzka.fltrans.models.File;

public interface FileRepository extends JpaRepository<File, Long> {

}
