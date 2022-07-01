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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
@Getter
public enum CamelRoutesStartUpOrderEnum
{
	/*
	 * Group of routes that should be manually started
	 * */
	ONE(1);

	private final Integer code;

	@NonNull
	public static CamelRoutesStartUpOrderEnum ofCode(@NonNull final Integer code)
	{
		return ofCodeOptional(code)
				.orElseThrow(() -> new RuntimeException("No JsonExternalSystemMessageTypeEnum could be found for code " + code + "!"));
	}

	@NonNull
	public static Optional<CamelRoutesStartUpOrderEnum> ofCodeOptional(@Nullable final Integer code)
	{
		if (code == null)
		{
			return Optional.empty();
		}

		return Arrays.stream(values())
				.filter(value -> value.getCode().equals(code))
				.findFirst();
	}
}
