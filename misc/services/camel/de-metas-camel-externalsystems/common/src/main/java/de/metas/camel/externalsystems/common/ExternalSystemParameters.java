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

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.Value;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Value
public class ExternalSystemParameters
{
	@NonNull
	ImmutableMap<String, String> nameToValue;

	@NonNull
	public static ExternalSystemParameters of(@NonNull final Map<String, String> parameters)
	{
		return new ExternalSystemParameters(parameters);
	}

	private ExternalSystemParameters(@NonNull final Map<String, String> parameters)
	{
		this.nameToValue = parameters.entrySet()
				.stream()
				.filter(entry -> entry.getValue() != null)
				.collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	@NonNull
	public <T> T getNonNull(@NonNull final String name, @NonNull final Function<String, @NonNull T> transformer)
	{
		return getOptional(name, transformer)
				.orElseThrow(() -> new RuntimeException("Missing mandatory parameter! name = " + name));
	}

	@NonNull
	public String getNonNull(@NonNull final String name)
	{
		return getNonNull(name, Function.identity());
	}

	@NonNull
	public <T> Optional<T> getOptional(@NonNull final String name, @NonNull final Function<String, @NonNull T> transformer)
	{
		return Optional.ofNullable(nameToValue.get(name))
				.map(transformer);
	}
}
