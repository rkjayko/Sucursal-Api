package com.example.franchiseapi.exception;

public class CustomException extends RuntimeException {

    private final static String NOT_FOUND = "not found";

    private final static String ALREADY_EXISTS = "already exists";

    public static class FranchiseIdNotFoundException extends RuntimeException {
        public FranchiseIdNotFoundException(Long franchiseId) {
            super("Franchise with ID : " + franchiseId + " " + NOT_FOUND);
        }
    }

    public static class FranchiseAlreadyExistsException extends RuntimeException {
        public FranchiseAlreadyExistsException(String name) {
            super("Franchise with name : " + name + " " + ALREADY_EXISTS);
        }
    }

    public static class BranchAlreadyExistsException extends RuntimeException {
        public BranchAlreadyExistsException(String name) {
            super("Branch with name : " + name + " " + ALREADY_EXISTS);
        }
    }

    public static class BranchIdNotFoundException extends RuntimeException {
        public BranchIdNotFoundException(Long branchId) {
            super("Branch with ID : " + branchId + " " + NOT_FOUND);
        }
    }

    public static class ProductAlreadyExistsException extends RuntimeException {
        public ProductAlreadyExistsException(String name) {
            super("Product with name : " + name + " " + ALREADY_EXISTS);
        }
    }

    public static class ProductIdNotFoundException extends RuntimeException {
        public ProductIdNotFoundException(Long productId) {
            super("Product with ID : " + productId + " " + NOT_FOUND);
        }
    }

    public static class BrandBelongToFranchiseException extends RuntimeException {
        public BrandBelongToFranchiseException() {
            super("Branch does not belong to the given franchise");
        }
    }
}