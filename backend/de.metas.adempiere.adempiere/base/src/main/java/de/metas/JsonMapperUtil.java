/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
public class JsonMapperUtil
{
	/**
	 * Uses {@link JsonObjectMapperHolder} to make sure that the given {@code target} is serialized/deserialized into an instance of the given {@code type}.
	 * If this fails, it does not throw an exception, but returns an empty optional.
	 */
	@NonNull
	public <T> Optional<T> tryDeserializeToType(@NonNull final Object target, @NonNull final Class<T> type)
	{
		if (type.isInstance(target))
		{
			return Optional.of((T)target);
		}

		final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

		try
		{
			if (target instanceof String)
			{
				return Optional.of(objectMapper.readValue((String)target, type));
			}

			final String targetAsString = objectMapper.writeValueAsString(target);

			return Optional.of(objectMapper.readValue(targetAsString, type));
		}
		catch (final JsonProcessingException exception)
		{
			return Optional.empty();
		}
	}
}
