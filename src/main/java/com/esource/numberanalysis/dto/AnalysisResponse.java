package com.esource.numberanalysis.dto;

import java.util.List;

/**
 * Represents the response of a number analysis operation.
 *
 * @param generatedArrays The list of randomly generated integer arrays.
 * @param availableNumbers The list of numbers that were not found in any of the generated arrays.
 * @param largestPrime The largest prime number found among the available numbers. This will be a
 *     string representation of the number or a message indicating that no primes were found.
 */
public record AnalysisResponse(
    List<int[]> generatedArrays, List<Integer> availableNumbers, String largestPrime) {}
