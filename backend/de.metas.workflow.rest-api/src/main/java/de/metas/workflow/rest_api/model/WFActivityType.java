/*
 * #%L
 * de.metas.workflow.rest-api
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.workflow.rest_api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;

@EqualsAndHashCode(doNotUseGetters = true)
public class WFActivityType
{
	@JsonCreator
	public static WFActivityType ofString(@NonNull final String value)
	{
		return interner.intern(new WFActivityType(value));
	}

	private static final Interner<WFActivityType> interner = Interners.newStrongInterner();

	private final String value;

	private WFActivityType(@NonNull final String value)
	{
		this.value = value;
	}

	@Override
	@Deprecated
	public String toString()
	{
		return getAsString();
	}

	@JsonValue
	public String getAsString()
	{
		return value;
	}

	public void assertExpected(@NonNull final WFActivityType expected)
	{
		assertExpected(expected, this);
	}

	public void assertActual(@NonNull final WFActivityType actual)
	{
		assertExpected(this, actual);
	}

	private static void assertExpected(
			@NonNull final WFActivityType expected,
			@NonNull final WFActivityType actual)
	{
		if (!actual.equals(expected))
		{
			throw new AdempiereException("WFActivityType expected to be `" + expected + "` but it was `" + actual + "`");
		}
	}

	public static boolean equals(@Nullable WFActivityType o1, @Nullable WFActivityType o2) {return Objects.equals(o1, o2);}
}
