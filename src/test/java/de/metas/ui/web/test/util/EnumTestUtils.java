package de.metas.ui.web.test.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import org.junit.Ignore;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

@Ignore
public class EnumTestUtils
{
	public static <JSONEnumType, EnumType> void assertMappingFullyCovered(final EnumType[] values, final Function<EnumType, JSONEnumType> toJson)
	{
		final boolean checkAlreadyMatchedValues = true;
		assertMappingFullyCovered(values, toJson, checkAlreadyMatchedValues);
	}

	public static <JSONEnumType, EnumType> void assertMappingFullyCovered(
			final EnumType[] values,
			final Function<EnumType, JSONEnumType> toJson,
			final boolean checkAlreadyMatchedValues)
	{
		assertThat(toJson.apply(null)).isNull();

		final Set<JSONEnumType> jsonValuesAlreadyMatched = new HashSet<>();
		for (final EnumType value : values)
		{
			final JSONEnumType jsonValue = toJson.apply(value);
			assertThat(jsonValue)
					.withFailMessage("JSON shall not be null for " + value)
					.isNotNull();

			if (checkAlreadyMatchedValues && !jsonValuesAlreadyMatched.add(jsonValue))
			{
				fail("JSON value " + jsonValue + " was already matched");
			}
		}
	}
}
