package com.esource.numberanalysis.service;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class NumberAvailabilityServiceTest {

  private NumberAvailabilityService numberAvailabilityService;

  @BeforeEach
  void setUp() {
    numberAvailabilityService = new NumberAvailabilityService();
  }

  @Test
  void getAvailableNumbers_shouldReturnCorrectNumbers() {
    List<int[]> arrays = List.of(new int[] {1, 2, 3}, new int[] {4, 5, 6});

    Flux<Integer> availableNumbers = numberAvailabilityService.getAvailableNumbers(arrays, 0, 10);

    StepVerifier.create(availableNumbers).expectNext(0, 7, 8, 9).verifyComplete();
  }

  @Test
  void getAvailableNumbers_shouldReturnAllNumbers_whenNoArrays() {
    List<int[]> arrays = List.of();

    Flux<Integer> availableNumbers = numberAvailabilityService.getAvailableNumbers(arrays, 0, 5);

    StepVerifier.create(availableNumbers).expectNext(0, 1, 2, 3, 4).verifyComplete();
  }

  @Test
  void getAvailableNumbers_shouldReturnEmptyList_whenAllNumbersArePresent() {
    List<int[]> arrays = List.of(new int[] {0, 1, 2, 3, 4});

    Flux<Integer> availableNumbers = numberAvailabilityService.getAvailableNumbers(arrays, 0, 5);

    StepVerifier.create(availableNumbers).verifyComplete();
  }

  @Test
  void getAvailableNumbers_shouldReturnEmpty_whenRangeIsInvalid() {
    List<int[]> arrays = List.of(new int[] {1, 2, 3});

    Flux<Integer> availableNumbers = numberAvailabilityService.getAvailableNumbers(arrays, 5, 5);

    StepVerifier.create(availableNumbers).verifyComplete();
  }
}
