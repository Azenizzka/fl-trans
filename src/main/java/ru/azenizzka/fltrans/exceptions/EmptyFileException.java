package ru.azenizzka.fltrans.exceptions;

public class EmptyFileException extends Exception {
	public EmptyFileException() {
		super("File is empty.");
	}
}
