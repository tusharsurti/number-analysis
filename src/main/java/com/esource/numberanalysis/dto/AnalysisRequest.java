package com.esource.numberanalysis.dto;

import jakarta.validation.constraints.Min;

/**
 * Represents the request for a number analysis operation.
 *
 * @param numberOfArrays The number of arrays to generate. Must be at least 1.
 * @param arraySize The size of each generated array. Must be at least 1.
 * @param rangeMin The minimum value for the random numbers (inclusive). Must be non-negative.
 * @param rangeMax The maximum value for the random numbers (exclusive). Must be non-negative.
 */
public record AnalysisRequest(
    @Min(1) int numberOfArrays,
    @Min(1) int arraySize,
    @Min(0) int rangeMin,
    @Min(0) int rangeMax) {}
