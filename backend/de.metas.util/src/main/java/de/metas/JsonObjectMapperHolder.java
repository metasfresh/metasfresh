package de.metas;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.google.common.annotations.VisibleForTesting;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@UtilityClass
public class JsonObjectMapperHolder
{
	private static final Logger logger = LoggerFactory.getLogger(JsonObjectMapperHolder.class);

	public static ObjectMapper sharedJsonObjectMapper()
	{
		return sharedJsonObjectMapper.get();
	}

	@VisibleForTesting
	public static void resetSharedJsonObjectMapper()
	{
		sharedJsonObjectMapper.forget();
	}

	private static final ExtendedMemorizingSupplier<ObjectMapper> sharedJsonObjectMapper = ExtendedMemorizingSupplier.of(JsonObjectMapperHolder::newJsonObjectMapper);

	/**
	 * Fully-qualified name of jackson-module-parameter-names' module. We deliberately do NOT register
	 * it; see {@link #newJsonObjectMapper()}.
	 */
	private static final String PARAMETER_NAMES_MODULE_CLASSNAME = "com.fasterxml.jackson.module.paramnames.ParameterNamesModule";

	public static ObjectMapper newJsonObjectMapper()
	{
		// important to register the jackson-datatype-jsr310 module which we have in our pom and
		// which is needed to serialize/deserialize java.time.Instant
		Check.assumeNotNull(com.fasterxml.jackson.datatype.jsr310.JavaTimeModule.class, ""); // just to get a compile error if not present

		// JDK25/Spring-Boot-3 migration note (commit 4243e12a976 added `javac -parameters` to the
		// shared parent for Spring 6.1 constructor bean-by-name injection):
		// with -parameters, jackson-module-parameter-names (auto-discovered via the ServiceLoader /
		// Spring-Boot auto-config) exposes constructor & factory *parameter names* to Jackson's
		// creator binding. That silently changed deserialization behaviour across the codebase:
		//  - single-arg @JsonValue + @JsonCreator value objects / enums (e.g. AttributesKey,
		//    HUQRCodeUnitType) flip from DELEGATING to PROPERTIES and can no longer be read from a
		//    bare JSON scalar (MismatchedInputException), and
		//  - @JsonProperty("x") on a creator param whose getter serializes as "y" stops round-tripping.
		// This affects REST + RabbitMQ payloads, not just tests. Spring reads -parameters through its
		// own reflection discoverer (StandardReflectionParameterNameDiscoverer), independent of this
		// Jackson module, so we restore the exact pre-migration Jackson behaviour by registering every
		// ServiceLoader-discovered module EXCEPT ParameterNamesModule. (Confirmed empirically on
		// jackson 2.18.2: this is the only option that matches base behaviour for delegating,
		// multi-arg-properties AND single-arg-explicit-@JsonProperty creators; both
		// ParameterNamesModule(Mode.DELEGATING) and ConstructorDetector.USE_DELEGATING regress the
		// latter shapes.)
		final List<Module> modulesWithoutParameterNames = ObjectMapper.findModules().stream()
				.filter(module -> !PARAMETER_NAMES_MODULE_CLASSNAME.equals(module.getClass().getName()))
				.collect(Collectors.toList());

		final ObjectMapper objectMapper = new ObjectMapper()
				.registerModules(modulesWithoutParameterNames)
				.registerModule(new GuavaModule())
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
				.enable(MapperFeature.USE_ANNOTATIONS);

		logger.info("Created a new JSON ObjectMapper: {} \nRegistered modules: {}", objectMapper, objectMapper.getRegisteredModuleIds());

		return objectMapper;
	}

	/**
	 * Mapper for the Spring MVC {@code MappingJackson2HttpMessageConverter} that binds REST request bodies and writes REST responses.
	 * <p>
	 * On base (pre-JDK25), the springfox {@code @EnableWebMvc} made Spring MVC use its own default converter mapper, built by
	 * {@code Jackson2ObjectMapperBuilder.json()}; the shared {@code @Primary} mapper was not used for HTTP. With springdoc,
	 * {@code @EnableWebMvc} is gone and Spring Boot's converter would use our {@code @Primary} mapper instead.
	 * This mapper is {@link #newJsonObjectMapper()} plus the settings in which that builder mapper differed and which matter on the wire:
	 * <ul>
	 * <li>{@code FAIL_ON_UNKNOWN_PROPERTIES} disabled: unknown properties in a request body are ignored instead of rejected;</li>
	 * <li>{@code WRITE_DATES_AS_TIMESTAMPS} enabled: responses write {@code Instant} / {@code ZonedDateTime} / {@code OffsetDateTime} as
	 * epoch seconds with nanoseconds, {@code LocalDate} / {@code LocalDateTime} / {@code LocalTime} as arrays and {@code java.util.Date} as epoch milliseconds;</li>
	 * <li>{@code ADJUST_DATES_TO_CONTEXT_TIME_ZONE} enabled: a zoned/offset date-time in a request body is adjusted to UTC.</li>
	 * </ul>
	 * Not taken over: the builder mapper had no {@code GuavaModule} and disabled {@code DEFAULT_VIEW_INCLUSION} (no {@code @JsonView} in use).
	 * <p>
	 * These settings are deliberately limited to the HTTP message converter: {@link #newJsonObjectMapper()} / {@link #sharedJsonObjectMapper()}
	 * stay as they are (strict, ISO dates), because code like {@code JsonMapperUtil.tryDeserializeToType} relies on an unknown property failing in
	 * order to tell JSON types apart, and the shared mapper's other users (e.g. RabbitMQ, the app server's websocket, {@link #toJson(Object)}) write ISO dates.
	 */
	public static ObjectMapper newHttpMessageConverterJsonObjectMapper()
	{
		return newJsonObjectMapper()
				.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
				.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.enable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
	}

	@Nullable
	public static String toJson(@Nullable final Object object)
	{
		if (object == null)
		{
			return null;
		}
		try
		{
			return sharedJsonObjectMapper().writeValueAsString(object);
		}
		catch (JsonProcessingException e)
		{
			throw Check.mkEx("Failed converting object to JSON: " + object, e);
		}
	}

	@Nullable
	public static <T> T fromJson(@Nullable final String json, @NonNull final Class<T> valueType)
	{
		if (json == null)
		{
			return null;
		}

		return fromJsonNonNull(json, valueType);
	}

	@NonNull
	public static <T> T fromJsonNonNull(@NonNull final String json, @NonNull final Class<T> valueType)
	{
		try
		{
			return sharedJsonObjectMapper().readValue(json, valueType);
		}
		catch (final JsonProcessingException e)
		{
			throw Check.mkEx("Failed converting JSON to " + valueType.getSimpleName() + ": `" + json + "`", e);
		}
	}

	@NonNull
	public static String toJsonNonNull(@NonNull final Object object)
	{
		try
		{
			return sharedJsonObjectMapper().writeValueAsString(object);
		}
		catch (final JsonProcessingException e)
		{
			throw Check.mkEx("Failed converting object to JSON: " + object, e);
		}
	}
}
