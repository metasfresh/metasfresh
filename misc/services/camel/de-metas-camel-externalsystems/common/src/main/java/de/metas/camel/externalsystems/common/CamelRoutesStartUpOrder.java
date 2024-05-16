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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Used to keep evidence of the camel routes start up order.
 */
@AllArgsConstructor
@Getter
public enum CamelRoutesStartUpOrder
{
	ONE(1);

	private final Integer value;

	@NonNull
	public static CamelRoutesStartUpOrder ofValue(@NonNull final Integer value)
	{
		return ofValueOptional(value)
				.orElseThrow(() -> new RuntimeException("No CamelRoutesStartUpOrder could be found for value " + value + "!"));
	}

	@NonNull
	public static Optional<CamelRoutesStartUpOrder> ofValueOptional(@Nullable final Integer value)
	{
		if (value == null)
		{
			return Optional.empty();
		}

		return Optional.ofNullable(valueToOrder.getOrDefault(value, null));
	}

	private final static ImmutableMap<Integer, CamelRoutesStartUpOrder> valueToOrder = Stream.of(values())
			.collect(ImmutableMap.toImmutableMap(
					CamelRoutesStartUpOrder::getValue,
					Function.identity()
			));
}
