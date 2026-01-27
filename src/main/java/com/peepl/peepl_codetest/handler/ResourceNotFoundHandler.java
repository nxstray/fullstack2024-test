package com.peepl.peepl_codetest.handler;

public class ResourceNotFoundHandler extends RuntimeException {
        public ResourceNotFoundHandler(String message) {
        super(message);
    }
}
