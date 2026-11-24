package de.metas.vertical.pharma.msv3.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 * #%L
 * metasfresh-pharma.msv3.server
 * %%
 * Copyright (C) 2026 metas GmbH
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
 * Spring MVC slice (Boot's MVC + Jackson auto-configuration, no app context, no DB) with the msv3 server's {@link JsonConfiguration}.
 * It pins the configuration class's behaviour, not the wiring of the msv3 {@link Application}.
 */
class JsonConfigurationTest
{
	private static final String JSON_WITH_UNKNOWN_PROPERTY = "{\"name\":\"abc\",\"value\":7,\"someUnknownProperty\":\"x\"}";

	private final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(
					JacksonAutoConfiguration.class,
					HttpMessageConvertersAutoConfiguration.class,
					WebMvcAutoConfiguration.class))
			.withUserConfiguration(JsonConfiguration.class, EchoRestController.class, DateTimeRestController.class);

	// plain public-field POJO so Jackson binds by field access
	public static class EchoDto
	{
		public String name;
		public int value;
	}

	@RestController
	static class EchoRestController
	{
		@PostMapping("/echo")
		public EchoDto echo(@RequestBody final EchoDto request)
		{
			return request;
		}
	}

	// plain public-field POJO so Jackson binds by field access
	public static class DateTimesDto
	{
		public Instant instant;
		public ZonedDateTime zoned;
		public OffsetDateTime offset;
		public LocalDate localDate;
		public LocalDateTime localDateTime;
		public LocalTime localTime;
		public Date utilDate;
	}

	private static final Instant INSTANT = Instant.parse("1999-12-31T23:00:00Z");

	@RestController
	static class DateTimeRestController
	{
		@GetMapping("/dateTimes")
		public DateTimesDto dateTimes()
		{
			final DateTimesDto dto = new DateTimesDto();
			dto.instant = INSTANT;
			dto.zoned = ZonedDateTime.of(1999, 12, 31, 23, 0, 0, 0, ZoneId.of("Europe/Berlin"));
			dto.offset = dto.zoned.toOffsetDateTime();
			dto.localDate = LocalDate.of(1999, 12, 31);
			dto.localDateTime = LocalDateTime.of(1999, 12, 31, 23, 0);
			dto.localTime = LocalTime.of(8, 30);
			dto.utilDate = Date.from(INSTANT);
			return dto;
		}

		/**
		 * @return how the request body was bound, as {@code toString()} of each field
		 */
		@PostMapping("/dateTimes/bound")
		public Map<String, String> bound(@RequestBody final DateTimesDto request)
		{
			final Map<String, String> result = new LinkedHashMap<>();
			result.put("instant", String.valueOf(request.instant));
			result.put("zonedInstant", String.valueOf(request.zoned.toInstant()));
			result.put("zonedOffset", String.valueOf(request.zoned.getOffset()));
			result.put("offset", String.valueOf(request.offset));
			result.put("utilDate", String.valueOf(request.utilDate.toInstant()));
			return result;
		}
	}

	/**
	 * REST responses write date/time values as timestamps, as the pre-springdoc converter did:
	 * {@code Instant} / {@code ZonedDateTime} / {@code OffsetDateTime} as epoch seconds with nanoseconds,
	 * {@code LocalDate} / {@code LocalDateTime} / {@code LocalTime} as arrays, {@code java.util.Date} as epoch milliseconds.
	 * The expected body is what Spring 5.3's {@code Jackson2ObjectMapperBuilder.json().build()} mapper (the converter mapper under {@code @EnableWebMvc}) writes.
	 */
	@Test
	void response_writes_dates_as_timestamps()
	{
		contextRunner.run(context -> {
			final MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

			mockMvc.perform(get("/dateTimes"))
					.andExpect(status().isOk())
					.andExpect(content().string("{"
							+ "\"instant\":946681200.000000000,"
							+ "\"zoned\":946677600.000000000,"
							+ "\"offset\":946677600.000000000,"
							+ "\"localDate\":[1999,12,31],"
							+ "\"localDateTime\":[1999,12,31,23,0],"
							+ "\"localTime\":[8,30],"
							+ "\"utilDate\":946681200000"
							+ "}"));
		});
	}

	/**
	 * Zoned/offset request values are adjusted to UTC, as the pre-springdoc converter did.
	 * <p>
	 * The {@code ZonedDateTime}'s zone <i>ID</i> is deliberately not pinned: Jackson 2.11 (pre-migration) kept {@code UTC} ({@code 2019-12-31T23:30Z[UTC]}),
	 * Jackson 2.16+ normalizes it to the offset {@code Z} ({@code 2019-12-31T23:30Z}). Same instant and offset, and no msv3 REST DTO carries a date.
	 */
	@Test
	void requestBody_with_zoned_iso_dates_is_adjusted_to_utc()
	{
		contextRunner.run(context -> {
			final MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

			mockMvc.perform(post("/dateTimes/bound")
							.contentType(MediaType.APPLICATION_JSON)
							.content("{"
									+ "\"instant\":\"2020-01-01T00:30:00+01:00\","
									+ "\"zoned\":\"2020-01-01T00:30:00+01:00\","
									+ "\"offset\":\"2020-01-01T00:30:00+01:00\","
									+ "\"utilDate\":\"2020-01-01T00:30:00+01:00\""
									+ "}"))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.instant").value("2019-12-31T23:30:00Z"))
					.andExpect(jsonPath("$.zonedInstant").value("2019-12-31T23:30:00Z"))
					.andExpect(jsonPath("$.zonedOffset").value("Z"))
					.andExpect(jsonPath("$.offset").value("2019-12-31T23:30Z"))
					.andExpect(jsonPath("$.utilDate").value("2019-12-31T23:30:00Z"));
		});
	}

	/**
	 * Inbound REST tolerates unknown properties, as it did with springfox's {@code @EnableWebMvc} before the springdoc migration.
	 */
	@Test
	void requestBody_with_unknown_property_is_bound()
	{
		contextRunner.run(context -> {
			final MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

			mockMvc.perform(post("/echo")
							.contentType(MediaType.APPLICATION_JSON)
							.content(JSON_WITH_UNKNOWN_PROPERTY))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.name").value("abc"))
					.andExpect(jsonPath("$.value").value(7));
		});
	}

	/**
	 * Pins that, under Boot's MVC auto-configuration, Spring MVC uses the converter bean of {@link JsonConfiguration}.
	 * <p>
	 * This runs in its own small context, not in the msv3 {@link Application} context, so it does not catch an {@code @EnableWebMvc} added
	 * elsewhere in msv3 main code: that is caught by {@code v2.RestMessageConverterWiringIntegrationTest}.
	 */
	@Test
	void springMvc_uses_the_converter_bean()
	{
		contextRunner.run(context -> {
			final MappingJackson2HttpMessageConverter converterBean = context.getBean(MappingJackson2HttpMessageConverter.class);
			final List<HttpMessageConverter<?>> mvcConverters = context.getBean(RequestMappingHandlerAdapter.class).getMessageConverters();

			assertThat(mvcConverters).anySatisfy(converter -> assertThat(converter).isSameAs(converterBean));
		});
	}

	/**
	 * The general-purpose mapper (RabbitMQ) is unchanged: still strict.
	 */
	@Test
	void jsonObjectMapper_rejects_unknown_properties()
	{
		contextRunner.run(context -> {
			final ObjectMapper jsonObjectMapper = context.getBean("jsonObjectMapper", ObjectMapper.class);

			assertThatThrownBy(() -> jsonObjectMapper.readValue(JSON_WITH_UNKNOWN_PROPERTY, EchoDto.class))
					.isInstanceOf(UnrecognizedPropertyException.class);
		});
	}
}
