package com.esource.numberanalysis.controller;

import com.esource.numberanalysis.dto.AnalysisRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
class AnalysisControllerTest {

  @Autowired private WebTestClient webTestClient;

  @Test
  void analyze_shouldReturnAnalysisResponse() {
    AnalysisRequest request = new AnalysisRequest(5, 20, 0, 100);

    webTestClient
        .post()
        .uri("/analyze")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(request)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.generatedArrays")
        .isArray()
        .jsonPath("$.generatedArrays.length()")
        .isEqualTo(5)
        .jsonPath("$.availableNumbers")
        .isArray()
        .jsonPath("$.largestPrime")
        .exists();
  }

  @Test
  void analyze_shouldReturnBadRequest_whenRequestIsInvalid() {
    AnalysisRequest request = new AnalysisRequest(-1, 20, 0, 100);

    webTestClient
        .post()
        .uri("/analyze")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(request)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .jsonPath("$.status")
        .isEqualTo(400)
        .jsonPath("$.error")
        .isEqualTo("Bad Request")
        .jsonPath("$.errors.numberOfArrays")
        .isEqualTo("must be greater than or equal to 1");
  }
}
