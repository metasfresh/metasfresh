/*
 * #%L
 * de.metas.device.api
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

package de.metas.device.api.hook;

import de.metas.common.util.NumberUtils;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Value(staticConstructor = "of")
public class RunParameters
{
	@NonNull
	Map<String, List<String>> parameters;

	@NonNull
	public Optional<BigDecimal> getSingleAsBigDecimal(@NonNull final String paramName)
	{
		return getSingle(paramName)
				.map(value -> NumberUtils.asBigDecimal(value, null));
	}

	@NonNull
	public Optional<Integer> getSingleAsIntegerForSuffix(@NonNull final String suffix)
	{
		return CollectionUtils.singleElementOrEmpty(parameters.keySet(), key -> key.endsWith(suffix))
				.flatMap(this::getSingle)
				.map(NumberUtils::asInt);
	}

	@NonNull
	public Optional<String> getSingle(@NonNull final String paramName)
	{
		return Optional.ofNullable(parameters.get(paramName))
				.filter(list -> list.size() == 1)
				.map(list -> list.get(0));
	}
}
