package de.metas.product;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import de.metas.util.Check;
import lombok.EqualsAndHashCode;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

@EqualsAndHashCode
public final class ProductPlanningSchemaSelector
{
	@JsonCreator
	public static ProductPlanningSchemaSelector ofString(final String value)
	{
		return new ProductPlanningSchemaSelector(value);
	}

	public static ProductPlanningSchemaSelector ofStringOrNull(final String value)
	{
		if (Check.isEmpty(value, true))
		{
			return null;
		}
		else
		{
			return new ProductPlanningSchemaSelector(value);
		}
	}

	private final String value;

	private ProductPlanningSchemaSelector(final String value)
	{
		this.value = value.trim();
	}

	@Override
	@Deprecated
	public String toString()
	{
		return getValueAsString();
	}

	@JsonValue
	public String getValueAsString()
	{
		return value;
	}

	public static boolean equals(final ProductPlanningSchemaSelector o1, final ProductPlanningSchemaSelector o2)
	{
		return Objects.equals(o1, o2);
	}
}
