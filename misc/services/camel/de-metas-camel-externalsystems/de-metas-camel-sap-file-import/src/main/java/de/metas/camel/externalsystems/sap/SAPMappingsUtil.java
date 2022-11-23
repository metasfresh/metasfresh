/*
 * #%L
 * de-metas-camel-sap-file-import
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

package de.metas.camel.externalsystems.sap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.common.externalsystem.JsonExternalMapping;
import de.metas.common.util.Check;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.camel.RuntimeCamelException;

import java.util.Map;
import java.util.function.Function;

@UtilityClass
public class SAPMappingsUtil
{
	@NonNull
	public ImmutableMap<String, JsonExternalMapping> getExternalValue2ExternalMapping(@NonNull final Map<String, String> parameters, @NonNull final String param)
	{
		final ImmutableList<JsonExternalMapping> externalMappings = getMappings(parameters, param);

		return externalMappings.stream()
				.collect(ImmutableMap.toImmutableMap(JsonExternalMapping::getExternalValue, Function.identity()));
	}

	@NonNull
	private ImmutableList<JsonExternalMapping> getMappings(@NonNull final Map<String, String> parameters, @NonNull final String param)
	{
		final String mappings = parameters.get(param);

		if (Check.isBlank(mappings))
		{
			throw new RuntimeCamelException("Parameter " + param + " cannot be missing!");
		}

		final ObjectMapper mapper = new ObjectMapper();
		try
		{
			return ImmutableList.copyOf(mapper.readValue(mappings, JsonExternalMapping[].class));
		}
		catch (final JsonProcessingException e)
		{
			throw new RuntimeException(e);
		}
	}
}
