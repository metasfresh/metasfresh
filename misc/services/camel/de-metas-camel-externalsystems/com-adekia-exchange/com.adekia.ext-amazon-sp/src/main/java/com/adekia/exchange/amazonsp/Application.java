/*
 * #%L
 * ext-amazon-sp
 * %%
 * Copyright (C) 2022 Adekia
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
/**
 * package com.adekia.exchange.amazonsp;
 * <p>
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.boot.CommandLineRunner;
 * import org.springframework.boot.SpringApplication;
 * import org.springframework.boot.autoconfigure.SpringBootApplication;
 * import org.springframework.boot.web.client.RestTemplateBuilder;
 * import org.springframework.context.ApplicationContext;
 * import org.springframework.context.annotation.Bean;
 * import org.springframework.web.client.RestTemplate;
 *
 * @SpringBootApplication public class Application
 * {
 * @Autowired private RestTemplate restTemplate;
 * <p>
 * public static void main(String[] args) {
 * SpringApplication.run(Application.class, args);
 * }
 * @Bean public RestTemplate restTemplate(RestTemplateBuilder builder) {
 * return builder.basicAuthentication("apiuser", "WMLF7FxujLV5eEmAJeHtQsu").build();
 * }
 * @Bean public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
 * return args -> {
 * System.out.println("Let's run it");
 * restTemplate.getForObject("http://localhost:8080/camel/api/orders", Object.class);
 * //			restTemplate.getForObject("http://localhost:8080/camel/api/orders/1", Object.class);
 * <p>
 * };
 * }
 * }
 **/