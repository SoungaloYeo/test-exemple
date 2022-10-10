package com.testexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Objects;

@SpringBootApplication
public class TestexampleApplication {

	public static void main(String[] args) {


		SpringApplication.run(TestexampleApplication.class, args);

		String str = null;
		String str2 = "";

		//Objects.requireNonNull(str, "new Exception");


		System.out.println("Hello  %s et %s".formatted("toi", "moi"));


		String str1 ="""
				###
				###
				""";

		System.out.println(str1);
	}

}
