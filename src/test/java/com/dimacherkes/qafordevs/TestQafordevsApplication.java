package com.dimacherkes.qafordevs;

import org.springframework.boot.SpringApplication;
import org.testcontainers.utility.TestcontainersConfiguration;

public class TestQafordevsApplication {

	public static void main(String[] args) {
		SpringApplication.from(QafordevsApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
