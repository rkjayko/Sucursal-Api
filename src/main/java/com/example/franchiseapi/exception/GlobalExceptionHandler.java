package com.example.franchiseapi.exception;

import com.example.franchiseapi.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.FranchiseAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleFranchiseAlreadyExists(CustomException.FranchiseAlreadyExistsException e) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CustomException.FranchiseIdNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleFranchiseIdNotFoundException(CustomException.FranchiseIdNotFoundException e) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomException.BranchAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleBranchAlreadyExists(CustomException.BranchAlreadyExistsException e) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CustomException.BranchIdNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleFranchiseIdNotFoundException(CustomException.BranchIdNotFoundException e) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomException.ProductAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleProductAlreadyExists(CustomException.ProductAlreadyExistsException e) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CustomException.ProductIdNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleProductIdNotFoundException(CustomException.ProductIdNotFoundException e) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomException.BrandBelongToFranchiseException.class)
    public ResponseEntity<ErrorResponseDTO> handleBrandBelongToFranchiseException(CustomException.BrandBelongToFranchiseException e) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }
}