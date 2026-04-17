package com.franchise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
public class MsFranchiseApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsFranchiseApplication.class, args);
	}

	@GetMapping("/health")
	public Mono<String> health() {
		return Mono.just("OK");
	}

}
