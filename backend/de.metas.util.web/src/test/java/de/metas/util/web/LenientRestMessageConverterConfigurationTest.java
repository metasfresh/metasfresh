/*
 * #%L
 * de.metas.util.web
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

package de.metas.util.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.JsonObjectMapperHolder;
import de.metas.common.pricing.v2.productprice.JsonPriceListVersionResponse;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
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
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Spring MVC slice (Boot's MVC + Jackson auto-configuration, no app context, no DB), set up like {@code ServerBoot} / {@code WebRestApiApplication}:
 * a strict {@code @Primary} shared mapper plus the imported {@link LenientRestMessageConverterConfiguration}.
 * It pins the configuration class's behaviour, not the wiring of the real applications (see {@link #springMvc_uses_the_converter_bean()}).
 */
class LenientRestMessageConverterConfigurationTest
{
	private final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(
					JacksonAutoConfiguration.class,
					HttpMessageConvertersAutoConfiguration.class,
					WebMvcAutoConfiguration.class))
			.withUserConfiguration(AppLikeConfig.class, EchoRestController.class, DateTimeRestController.class);

	@Configuration
	@Import(LenientRestMessageConverterConfiguration.class)
	static class AppLikeConfig
	{
		@Bean
		@Primary
		public ObjectMapper jsonObjectMapper()
		{
			return JsonObjectMapperHolder.sharedJsonObjectMapper();
		}
	}

	// plain public-field POJO so Jackson binds by field access, no lombok @Jacksonized
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

	// plain public-field POJO so Jackson binds by field access, no lombok @Jacksonized
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

	private static final Instant VALID_FROM = Instant.parse("1999-12-31T23:00:00Z");

	@RestController
	static class DateTimeRestController
	{
		@GetMapping("/priceListVersion")
		public JsonPriceListVersionResponse priceListVersion()
		{
			return JsonPriceListVersionResponse.builder()
					.metasfreshId(JsonMetasfreshId.of(1))
					.name("plv")
					.validFrom(VALID_FROM)
					.jsonResponsePrices(ImmutableList.of())
					.build();
		}

		@GetMapping("/dateTimes")
		public DateTimesDto dateTimes()
		{
			final DateTimesDto dto = new DateTimesDto();
			dto.instant = VALID_FROM;
			dto.zoned = ZonedDateTime.of(1999, 12, 31, 23, 0, 0, 0, ZoneId.of("Europe/Berlin"));
			dto.offset = dto.zoned.toOffsetDateTime();
			dto.localDate = LocalDate.of(1999, 12, 31);
			dto.localDateTime = LocalDateTime.of(1999, 12, 31, 23, 0);
			dto.localTime = LocalTime.of(8, 30);
			dto.utilDate = Date.from(VALID_FROM);
			return dto;
		}

		/**
		 * @return how the request body was bound, as {@code toString()} of each field
		 */
		@PostMapping("/dateTimes/bound")
		public Map<String, String> bound(@RequestBody final DateTimesDto request)
		{
			return ImmutableMap.<String, String>builder()
					.put("instant", String.valueOf(request.instant))
					.put("zoned", String.valueOf(request.zoned))
					.put("offset", String.valueOf(request.offset))
					.put("localDate", String.valueOf(request.localDate))
					.put("localDateTime", String.valueOf(request.localDateTime))
					.put("localTime", String.valueOf(request.localTime))
					.put("utilDate", String.valueOf(request.utilDate.toInstant()))
					.build();
		}
	}

	/**
	 * REST responses write date/time values as timestamps, exactly as before the JDK25 migration:
	 * {@code Instant} / {@code ZonedDateTime} / {@code OffsetDateTime} as epoch seconds with nanoseconds,
	 * {@code LocalDate} / {@code LocalDateTime} / {@code LocalTime} as arrays, {@code java.util.Date} as epoch milliseconds.
	 * The expected body is what Spring 5.3's {@code Jackson2ObjectMapperBuilder.json().build()} mapper (the pre-migration converter mapper) writes.
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

	@Test
	void priceListVersionResponse_validFrom_is_a_number()
	{
		contextRunner.run(context -> {
			final MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

			mockMvc.perform(get("/priceListVersion"))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.validFrom").isNumber())
					.andExpect(jsonPath("$.validFrom").value(946681200.0));
		});
	}

	/**
	 * Request bodies still accept ISO-8601 strings. Zoned/offset values are adjusted to UTC, as before the JDK25 migration.
	 */
	@Test
	void requestBody_with_iso_dates_is_bound()
	{
		contextRunner.run(context -> {
			final MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

			mockMvc.perform(post("/dateTimes/bound")
							.contentType(MediaType.APPLICATION_JSON)
							.content("{"
									+ "\"instant\":\"2020-01-01T00:30:00+01:00\","
									+ "\"zoned\":\"2020-01-01T00:30:00+01:00\","
									+ "\"offset\":\"2020-01-01T00:30:00+01:00\","
									+ "\"localDate\":\"2020-01-01\","
									+ "\"localDateTime\":\"2020-01-01T00:30:00\","
									+ "\"localTime\":\"08:30:00\","
									+ "\"utilDate\":\"2020-01-01T00:30:00+01:00\""
									+ "}"))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.instant").value("2019-12-31T23:30:00Z"))
					.andExpect(jsonPath("$.zoned").value("2019-12-31T23:30Z[UTC]"))
					.andExpect(jsonPath("$.offset").value("2019-12-31T23:30Z"))
					.andExpect(jsonPath("$.localDate").value("2020-01-01"))
					.andExpect(jsonPath("$.localDateTime").value("2020-01-01T00:30"))
					.andExpect(jsonPath("$.localTime").value("08:30"))
					.andExpect(jsonPath("$.utilDate").value("2019-12-31T23:30:00Z"));
		});
	}

	/**
	 * Request bodies also accept the timestamp shapes that responses are written in.
	 */
	@Test
	void requestBody_with_timestamp_dates_is_bound()
	{
		contextRunner.run(context -> {
			final MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

			mockMvc.perform(post("/dateTimes/bound")
							.contentType(MediaType.APPLICATION_JSON)
							.content("{"
									+ "\"instant\":1577835000.5,"
									+ "\"zoned\":1577835000,"
									+ "\"offset\":1577835000,"
									+ "\"localDate\":[2020,1,1],"
									+ "\"localDateTime\":[2020,1,1,0,30],"
									+ "\"localTime\":[8,30],"
									+ "\"utilDate\":1577835000000"
									+ "}"))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.instant").value("2019-12-31T23:30:00.500Z"))
					.andExpect(jsonPath("$.zoned").value("2019-12-31T23:30Z[UTC]"))
					.andExpect(jsonPath("$.offset").value("2019-12-31T23:30Z"))
					.andExpect(jsonPath("$.localDate").value("2020-01-01"))
					.andExpect(jsonPath("$.localDateTime").value("2020-01-01T00:30"))
					.andExpect(jsonPath("$.localTime").value("08:30"))
					.andExpect(jsonPath("$.utilDate").value("2019-12-31T23:30:00Z"));
		});
	}

	/**
	 * Request bodies with properties the backend does not model (e.g. a price {@code currencyCode}, a mobile {@code title} test-step label)
	 * must be bound, not rejected with a 400.
	 */
	@Test
	void requestBody_with_unknown_property_is_bound()
	{
		contextRunner.run(context -> {
			final MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

			mockMvc.perform(post("/echo")
							.contentType(MediaType.APPLICATION_JSON)
							.content("{\"name\":\"abc\",\"value\":7,\"currencyCode\":\"EUR\",\"title\":\"some step label\"}"))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.name").value("abc"))
					.andExpect(jsonPath("$.value").value(7));
		});
	}

	/**
	 * Pins that, under Boot's MVC auto-configuration, Spring MVC uses the converter bean of {@link LenientRestMessageConverterConfiguration}
	 * and that its mapper is not the strict shared one.
	 * <p>
	 * This runs in its own small context, not in {@code ServerBoot} / {@code WebRestApiApplication}. It does not catch a change to those apps:
	 * a missing {@code @Import} is caught by {@code ServerBootWiringTest} / {@code WebRestApiApplicationWiringTest}, and an
	 * {@code @EnableWebMvc} or {@code WebMvcConfigurationSupport} subclass in main code by the ArchUnit rule
	 * {@code MetasfreshArchRules.noCustomSpringMvcConfiguration()}.
	 */
	@Test
	void springMvc_uses_the_converter_bean()
	{
		contextRunner.run(context -> {
			final MappingJackson2HttpMessageConverter converterBean = context.getBean(MappingJackson2HttpMessageConverter.class);
			final List<HttpMessageConverter<?>> mvcConverters = context.getBean(RequestMappingHandlerAdapter.class).getMessageConverters();

			assertThat(mvcConverters).anySatisfy(converter -> assertThat(converter).isSameAs(converterBean));
			assertThat(converterBean.getObjectMapper())
					.as("the converter must not use the strict shared mapper")
					.isNotSameAs(JsonObjectMapperHolder.sharedJsonObjectMapper());
		});
	}
}
