package com.cloudbees.assessment;

import com.cloudbees.assessment.domain.entity.Train;
import com.cloudbees.assessment.domain.entity.User;
import com.cloudbees.assessment.infrastructure.repository.TrainRepository;
import com.cloudbees.assessment.infrastructure.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class AssessmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssessmentApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(UserRepository userRepository, TrainRepository trainRepository) {
		return args -> {
			//add data from startup
			userRepository.save(new User(null, "Jack", "Bauer", "Jack@Bauer.com"));
			trainRepository.save(new Train(null, "London", "France", 20.0));
		};
	}
}
