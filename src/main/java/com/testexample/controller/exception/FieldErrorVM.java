package com.testexample.controller.exception;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class FieldErrorVM implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String message;
    private final String field;
    private final String objectName;



}