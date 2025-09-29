package com.esource.numberanalysis.service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/** A service to find numbers that are not present in a given list of arrays. */
@Service
public class NumberAvailabilityService {

  /**
   * Finds all numbers within a given range that are not present in any of the provided arrays.
   *
   * @param arrays a list of integer arrays to check against
   * @param rangeMin the minimum value of the range to check (inclusive)
   * @param rangeMax the maximum value of the range to check (exclusive)
   * @return a {@link Flux} that emits the available numbers in sorted order
   */
  public Flux<Integer> getAvailableNumbers(List<int[]> arrays, int rangeMin, int rangeMax) {
    Set<Integer> allNumbers =
        arrays.stream().flatMapToInt(Arrays::stream).boxed().collect(Collectors.toSet());

    return Flux.range(rangeMin, rangeMax - rangeMin)
        .parallel()
        .runOn(Schedulers.parallel())
        .filter(number -> !allNumbers.contains(number))
        .sequential()
        .sort();
  }
}
