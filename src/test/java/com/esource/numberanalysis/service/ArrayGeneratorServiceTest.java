package com.esource.numberanalysis.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class ArrayGeneratorServiceTest {

  private ArrayGeneratorService arrayGeneratorService;

  @BeforeEach
  void setUp() {
    arrayGeneratorService = new ArrayGeneratorService();
  }

  @Test
  void generateArrays_shouldReturnCorrectNumberOfArrays() {
    Mono<List<int[]>> arraysMono = arrayGeneratorService.generateArrays(3, 10, 0, 100);

    StepVerifier.create(arraysMono)
        .assertNext(arrays -> assertEquals(3, arrays.size()))
        .verifyComplete();
  }

  @Test
  void generateArrays_shouldHaveCorrectArraySize() {
    Mono<List<int[]>> arraysMono = arrayGeneratorService.generateArrays(3, 10, 0, 100);

    StepVerifier.create(arraysMono)
        .assertNext(
            arrays -> {
              for (int[] array : arrays) {
                assertEquals(10, array.length);
              }
            })
        .verifyComplete();
  }

  @Test
  void generateArrays_shouldHaveNumbersWithinRange() {
    Mono<List<int[]>> arraysMono = arrayGeneratorService.generateArrays(3, 10, 10, 50);

    StepVerifier.create(arraysMono)
        .assertNext(
            arrays -> {
              for (int[] array : arrays) {
                for (int number : array) {
                  assertTrue(number >= 10 && number < 50);
                }
              }
            })
        .verifyComplete();
  }

  @Test
  void generateArrays_shouldHaveUniqueNumbersInEachArray() {
    Mono<List<int[]>> arraysMono = arrayGeneratorService.generateArrays(3, 15, 0, 100);

    StepVerifier.create(arraysMono)
        .assertNext(
            arrays -> {
              for (int[] array : arrays) {
                Set<Integer> uniqueNumbers = new HashSet<>();
                for (int number : array) {
                  uniqueNumbers.add(number);
                }
                assertEquals(array.length, uniqueNumbers.size());
              }
            })
        .verifyComplete();
  }

  @Test
  void generateUniqueRandomArray_shouldThrowException_whenArraySizeIsLargerThanRange() {
    Mono<List<int[]>> arraysMono = arrayGeneratorService.generateArrays(1, 110, 0, 100);

    StepVerifier.create(arraysMono).expectError(IllegalArgumentException.class).verify();
  }
}
