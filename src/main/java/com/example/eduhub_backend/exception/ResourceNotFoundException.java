package com.example.eduhub_backend.exception;

public class ResourceNotFoundException extends RuntimeException {
      public ResourceNotFoundException(String resource, String field, String error){
      super(String.format("%S not founf with %s : '%s'", resource, field, error));
      }
}
