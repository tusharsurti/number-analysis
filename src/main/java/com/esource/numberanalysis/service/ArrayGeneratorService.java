package com.esource.numberanalysis.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/** A service for generating arrays of random integers. */
@Service
public class ArrayGeneratorService {

  /**
   * Generates a specified number of arrays, each of a given size, containing unique random integers
   * within a defined range.
   *
   * @param numberOfArrays the number of arrays to generate
   * @param arraySize the size of each array
   * @param rangeMin the minimum value of the random numbers (inclusive)
   * @param rangeMax the maximum value of the random numbers (exclusive)
   * @return a {@link Mono} that emits a list of generated integer arrays
   */
  public Mono<List<int[]>> generateArrays(
      int numberOfArrays, int arraySize, int rangeMin, int rangeMax) {
    return Mono.fromCallable(
            () -> {
              List<int[]> arrays = new ArrayList<>();
              for (int i = 0; i < numberOfArrays; i++) {
                arrays.add(generateUniqueRandomArray(arraySize, rangeMin, rangeMax));
              }
              return arrays;
            })
        .subscribeOn(Schedulers.boundedElastic());
  }

  /**
   * Generates a single array of a given size with unique random integers within a specified range.
   *
   * @param arraySize the size of the array to generate
   * @param rangeMin the minimum value for the random numbers (inclusive)
   * @param rangeMax the maximum value for the random numbers (exclusive)
   * @return an array of unique random integers
   * @throws IllegalArgumentException if the requested array size is larger than the available range
   *     of unique numbers
   */
  private int[] generateUniqueRandomArray(int arraySize, int rangeMin, int rangeMax) {
    if (arraySize > (rangeMax - rangeMin)) {
      throw new IllegalArgumentException(
          "Cannot generate unique numbers, array size is larger than the range.");
    }
    Set<Integer> uniqueNumbers = new HashSet<>();
    Random random = new Random();
    while (uniqueNumbers.size() < arraySize) {
      int number = random.nextInt(rangeMax - rangeMin) + rangeMin;
      uniqueNumbers.add(number);
    }
    return uniqueNumbers.stream().mapToInt(Integer::intValue).toArray();
  }
}
