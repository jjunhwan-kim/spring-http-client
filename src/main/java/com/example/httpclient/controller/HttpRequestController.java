package com.example.httpclient.controller;

import com.example.httpclient.controller.dto.TestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RestController
public class HttpRequestController {

    private final RestTemplate restTemplate;

    private final WebClient webClient;

    private final String url = "http://localhost:9090/request";

    @PostMapping("/request")
    public String request() {

        log.info("HttpRequestController.request started");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("url", "test");
        params.add("content", "test content");

        TestDto testDto = new TestDto("test", "test content");

        try {
            ResponseEntity<Object> response = restTemplate.postForEntity("http://localhost:9090/request", params, Object.class);

        } catch (HttpClientErrorException e) {

            log.info("httpStatusCode={}", e.getStatusCode().toString());

        }

        log.info("HttpRequestController.request ended");

        return "ok";
    }

    @PostMapping("/request/webclient")
    public String requestWebClient() {
        log.info("HttpRequestController.requestWebClient started");


        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("url", "test1");
        params.add("content", "test content");

        Mono<String> stringMono = webClient.post()
                .uri(url)
                .body(BodyInserters.fromFormData(params))
                .retrieve()
                .bodyToMono(String.class);

        stringMono.subscribe(s -> log.info(s));


        log.info("HttpRequestController.requestWebClient ended");

        return "ok";
    }

}
