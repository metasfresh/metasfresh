package de.metas.ui.web.test.util;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import org.junit.Assert;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Ignore
public class EnumTestUtils
{
	public static <JSONEnumType, EnumType> void assertMappingFullyCovered(final EnumType[] values, final Function<EnumType, JSONEnumType> toJson)
	{
		Assert.assertNull(toJson.apply(null));

		final Set<JSONEnumType> jsonValuesAlreadyMatched = new HashSet<>();
		for (final EnumType value : values)
		{
			final JSONEnumType jsonValue = toJson.apply(value);
			Assert.assertNotNull("JSON shall not be null for " + value, jsonValue);

			if (!jsonValuesAlreadyMatched.add(jsonValue))
			{
				Assert.fail("JSON value " + jsonValue + " was already matched");
			}
		}
	}

}
