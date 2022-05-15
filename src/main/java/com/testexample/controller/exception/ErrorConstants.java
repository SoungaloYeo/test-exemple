package com.testexample.controller.exception;

import java.net.URI;

public final class ErrorConstants {

    public static final String ERR_VALIDATION = "Failed to validate received content";
    public static final String E_MAIL_EXIST = "Conflict email %s already used";


    public static final String PROBLEM_BASE_URL = "http://localhost:8080/errors";
    public static final URI DEFAULT_TYPE = URI.create(PROBLEM_BASE_URL + "/problem-with-message");
    public static final URI CONSTRAINT_VIOLATION_TYPE = URI.create(PROBLEM_BASE_URL + "/constraint-violation");

    private ErrorConstants() {}
}
