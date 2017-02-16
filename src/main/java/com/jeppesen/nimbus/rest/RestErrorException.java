package com.jeppesen.nimbus.rest;


public class RestErrorException extends Exception {

    public RestErrorException(String message) {
        super(message);
    }


    public RestErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
