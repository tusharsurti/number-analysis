package com.esource.numberanalysis.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class PrimeServiceTest {

  private final PrimeService primeService = new PrimeService();

  @Test
  void findLargestPrime_shouldReturnEmpty_whenListIsEmpty() {
    Mono<Optional<Integer>> largestPrimeMono =
        primeService.findLargestPrime(Collections.emptyList());
    StepVerifier.create(largestPrimeMono).expectNext(Optional.empty()).verifyComplete();
  }

  @Test
  void findLargestPrime_shouldReturnEmpty_whenNoPrimes() {
    Mono<Optional<Integer>> largestPrimeMono = primeService.findLargestPrime(List.of(4, 6, 8, 9));
    StepVerifier.create(largestPrimeMono).expectNext(Optional.empty()).verifyComplete();
  }

  @ParameterizedTest
  @MethodSource("primeTestCases")
  void findLargestPrime_shouldReturnLargestPrime(List<Integer> numbers, int expected) {
    Mono<Optional<Integer>> largestPrimeMono = primeService.findLargestPrime(numbers);
    StepVerifier.create(largestPrimeMono)
        .assertNext(prime -> assertEquals(Optional.of(expected), prime))
        .verifyComplete();
  }

  private static Stream<Arguments> primeTestCases() {
    return Stream.of(
        Arguments.of(List.of(1, 2, 3, 4, 5), 5),
        Arguments.of(List.of(2, 3, 5, 7, 11, 13), 13),
        Arguments.of(List.of(10, 20, 30, 43), 43),
        Arguments.of(List.of(97, 100, 4, 6), 97));
  }

  @ParameterizedTest
  @MethodSource("isPrimeTestCases")
  void isPrime_shouldReturnCorrectResult(int number, boolean expected) {
    assertEquals(expected, primeService.isPrime(number));
  }

  private static Stream<Arguments> isPrimeTestCases() {
    return Stream.of(
        Arguments.of(0, false),
        Arguments.of(1, false),
        Arguments.of(2, true),
        Arguments.of(3, true),
        Arguments.of(4, false),
        Arguments.of(5, true),
        Arguments.of(9, false),
        Arguments.of(15, false),
        Arguments.of(25, false),
        Arguments.of(49, false),
        Arguments.of(97, true));
  }
}
