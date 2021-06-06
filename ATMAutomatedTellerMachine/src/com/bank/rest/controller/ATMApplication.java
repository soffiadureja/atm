package com.bank.rest.controller;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan(value = "com.bank")
@EnableAutoConfiguration
@PropertySource("classpath:/properties/env.properties")
public class ATMApplication {

    public static void main(String[] args) {
		SpringApplication.run(ATMApplication.class, args);
    }
}