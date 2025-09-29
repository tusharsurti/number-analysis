package com.esource.numberanalysis.controller;

import com.esource.numberanalysis.dto.AnalysisRequest;
import com.esource.numberanalysis.dto.AnalysisResponse;
import com.esource.numberanalysis.service.ArrayGeneratorService;
import com.esource.numberanalysis.service.NumberAvailabilityService;
import com.esource.numberanalysis.service.PrimeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/** A REST controller for performing number analysis. */
@RestController
public class AnalysisController {

  private final ArrayGeneratorService arrayGeneratorService;
  private final NumberAvailabilityService numberAvailabilityService;
  private final PrimeService primeService;

  /**
   * Constructs a new {@link AnalysisController} with the given services.
   *
   * @param arrayGeneratorService the service for generating arrays
   * @param numberAvailabilityService the service for finding available numbers
   * @param primeService the service for finding prime numbers
   */
  public AnalysisController(
      ArrayGeneratorService arrayGeneratorService,
      NumberAvailabilityService numberAvailabilityService,
      PrimeService primeService) {
    this.arrayGeneratorService = arrayGeneratorService;
    this.numberAvailabilityService = numberAvailabilityService;
    this.primeService = primeService;
  }

  /**
   * Analyzes a list of randomly generated arrays to find available numbers and the largest prime
   * among them.
   *
   * @param request the analysis request containing the configuration for array generation
   * @return a {@link Mono} that emits an {@link AnalysisResponse} containing the generated arrays,
   *     the available numbers, and the largest prime number found
   */
  @PostMapping("/analyze")
  public Mono<AnalysisResponse> analyze(@Valid @RequestBody AnalysisRequest request) {
    return arrayGeneratorService
        .generateArrays(
            request.numberOfArrays(), request.arraySize(), request.rangeMin(), request.rangeMax())
        .flatMap(
            generatedArrays ->
                numberAvailabilityService
                    .getAvailableNumbers(generatedArrays, request.rangeMin(), request.rangeMax())
                    .collectList()
                    .flatMap(
                        availableNumbers ->
                            primeService
                                .findLargestPrime(availableNumbers)
                                .map(
                                    largestPrimeOpt ->
                                        new AnalysisResponse(
                                            generatedArrays,
                                            availableNumbers,
                                            largestPrimeOpt
                                                .map(String::valueOf)
                                                .orElse("No primes found")))));
  }
}
