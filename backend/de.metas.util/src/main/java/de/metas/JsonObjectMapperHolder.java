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
				// JDK25/Spring-Boot-3 migration note: the e2e masterdata seed sends a few JSON extras
				// the backend never models (a hard-coded price `currencyCode` and a mobile
				// `JsonExpectations.title` that is only a Playwright test.step() label leaking via a
				// request spread). Jackson's own FAIL_ON_UNKNOWN_PROPERTIES default is `true` (strict);
				// on base new_dawn_uat these payloads were nonetheless tolerated (the SB2-era REST stack
				// was lenient) and the identical e2e jobs ran GREEN, failing with UnrecognizedPropertyException
				// only on this SB3/JDK25 branch. We disable it explicitly here to restore that long-standing
				// base leniency (and to match the codebase convention — e.g. SimpleObjectSerializer,
				// NShiftClientConfig already configure FAIL_ON_UNKNOWN_PROPERTIES=false) rather than
				// introduce a new tolerance. The fields are confirmed spurious (consumed nowhere; born in
				// metasfresh PR #21830 / #21765 with no backend side), so there is nothing to bind.
				.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
				.enable(MapperFeature.USE_ANNOTATIONS);

		logger.info("Created a new JSON ObjectMapper: {} \nRegistered modules: {}", objectMapper, objectMapper.getRegisteredModuleIds());

		return objectMapper;
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
