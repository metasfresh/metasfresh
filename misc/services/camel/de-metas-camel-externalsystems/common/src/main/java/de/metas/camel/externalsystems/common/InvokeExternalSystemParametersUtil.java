/*
 * #%L
 * de-metas-camel-externalsystems-common
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.camel.externalsystems.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.common.util.Check;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.util.Map;

@UtilityClass
public class InvokeExternalSystemParametersUtil
{
	@Nullable
	public <T> T getDeserializedParameter(
			@NonNull final Map<String, String> parameters,
			@NonNull final String paramName,
			@NonNull final Class<T> parameterType)
	{
		final String value = parameters.get(paramName);

		if (Check.isBlank(value))
		{
			return null;
		}

		final ObjectMapper mapper = JsonObjectMapperHolder.sharedJsonObjectMapper();
		try
		{
			return mapper.readValue(value, parameterType);
		}
		catch (final JsonProcessingException e)
		{
			throw new RuntimeException(e);
		}
	}
}