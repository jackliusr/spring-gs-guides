package com.example.reactiverest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ReactiverestApplicationTests {


	  // Spring Boot will create a `WebTestClient` for you,
	  // already configure and ready to issue requests against "localhost:RANDOM_PORT"
	  @Autowired
	  private WebTestClient webTestClient;

	  @Test
	  public void testHello() {
	    webTestClient
	      // Create a GET request to test an endpoint
	      .get().uri("/hello")
	      .accept(MediaType.APPLICATION_JSON)
	      .exchange()
	      // and use the dedicated DSL to test assertions against the response
	      .expectStatus().isOk()
	      .expectBody(Greeting.class).value(greeting -> {
	        assertThat(greeting.getMessage()).isEqualTo("Hello, Spring!");
	    });
	  }
}
