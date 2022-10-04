/*
 * #%L
 * de-metas-common-externalsystem
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

package de.metas.common.externalsystem.leichundmehl;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.NonNull;

import java.util.Arrays;

//dev-note to be kept in sync with AD_Reference_ID=541598
public enum JsonReplacementSource
{
	Product("P"),
	PPOrder("PP");

	@Getter
	private final String code;

	JsonReplacementSource(@NonNull final String code)
	{
		this.code = code;
	}

	@NonNull
	public static JsonReplacementSource ofCode(@NonNull final String code)
	{
		final JsonReplacementSource replacementSource = typesByCode.get(code);

		if (replacementSource == null)
		{
			throw new IllegalArgumentException("JsonReplacementSource does not support code: " + code);
		}
		return replacementSource;
	}

	private static final ImmutableMap<String, JsonReplacementSource> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), JsonReplacementSource::getCode);
}
