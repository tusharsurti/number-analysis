package com.esource.numberanalysis.dto;

import java.time.Instant;
import java.util.Map;

public record ErrorResponse(
    Instant timestamp, int status, String error, String message, Map<String, String> errors) {}
