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
import de.metas.common.util.Check;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum CamelRoutesGroup
{
	START_ON_DEMAND("StartOnDemand"),
	ALWAYS_ON("AlwaysOn");

	private final String code;

	@NonNull
	public static CamelRoutesGroup ofCode(@NonNull final String code)
	{
		return ofCodeOptional(code)
				.orElseThrow(() -> new RuntimeException("No CamelRoutesGroup could be found for code " + code + "!"));
	}

	@NonNull
	public static Optional<CamelRoutesGroup> ofCodeOptional(@Nullable final String code)
	{
		if (Check.isBlank(code))
		{
			return Optional.empty();
		}

		return Optional.ofNullable(code2Group.getOrDefault(code, null));
	}

	public boolean isStartOnDemand()
	{
		return this == START_ON_DEMAND;
	}

	public boolean isAlwaysOn()
	{
		return this == ALWAYS_ON;
	}

	private final static ImmutableMap<String, CamelRoutesGroup> code2Group = Stream.of(values())
			.collect(ImmutableMap.toImmutableMap(
					CamelRoutesGroup::getCode,
					Function.identity()
			));
}
