package com.esource.numberanalysis.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/** A service for finding prime numbers. */
@Service
public class PrimeService {

  /**
   * Finds the largest prime number from a given list of integers.
   *
   * @param numbers the list of integers to search
   * @return a {@link Mono} that emits an {@link Optional} containing the largest prime number, or
   *     an empty Optional if no prime numbers are found
   */
  public Mono<Optional<Integer>> findLargestPrime(List<Integer> numbers) {
    return Mono.fromCallable(
            () -> {
              if (numbers == null || numbers.isEmpty()) {
                return Optional.<Integer>empty();
              }
              List<Integer> reversedNumbers = new java.util.ArrayList<>(numbers);
              Collections.reverse(reversedNumbers);
              return reversedNumbers.stream().filter(this::isPrime).findFirst();
            })
        .subscribeOn(Schedulers.boundedElastic());
  }

  /**
   * Checks if a given number is a prime number.
   *
   * @param n the number to check
   * @return {@code true} if the number is prime, {@code false} otherwise
   */
  boolean isPrime(int n) {
    if (n <= 1) {
      return false;
    }
    for (int i = 2; i * i <= n; i++) {
      if (n % i == 0) {
        return false;
      }
    }
    return true;
  }
}
