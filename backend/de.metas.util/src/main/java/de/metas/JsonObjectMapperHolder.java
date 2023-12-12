package de.metas;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.google.common.annotations.VisibleForTesting;
import de.metas.util.Check;
import lombok.experimental.UtilityClass;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	public static ObjectMapper newJsonObjectMapper()
	{
		// important to register the jackson-datatype-jsr310 module which we have in our pom and
		// which is needed to serialize/deserialize java.time.Instant
		Check.assumeNotNull(com.fasterxml.jackson.datatype.jsr310.JavaTimeModule.class, ""); // just to get a compile error if not present

		final ObjectMapper objectMapper = new ObjectMapper()
				.findAndRegisterModules()
				.registerModule(new GuavaModule())
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
				.enable(MapperFeature.USE_ANNOTATIONS);

		logger.info("Created a new JSON ObjectMapper: {} \nRegistered modules: {}", objectMapper, objectMapper.getRegisteredModuleIds());

		return objectMapper;
	}

}
