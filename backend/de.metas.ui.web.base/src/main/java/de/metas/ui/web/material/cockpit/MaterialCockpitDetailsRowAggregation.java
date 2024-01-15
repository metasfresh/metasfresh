/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.ui.web.material.cockpit;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.annotation.Nullable;

@AllArgsConstructor
@Getter
public enum MaterialCockpitDetailsRowAggregation
{
	PLANT("PLANT"),
	WAREHOUSE("WAREHOUSE"),
	NONE("NONE");

	private final String value;

	public static MaterialCockpitDetailsRowAggregation getValueOrDefault(@Nullable final String value)
	{
		for (final MaterialCockpitDetailsRowAggregation aggregation : MaterialCockpitDetailsRowAggregation.values())
		{
			if (aggregation.name().equalsIgnoreCase(value))
			{
				return aggregation;
			}
		}
		return PLANT;  // default value
	}

	public boolean isNone()
	{
		return this == NONE;
	}

	public boolean isPlant()
	{
		return this == PLANT;
	}

	public boolean isWarehouse()
	{
		return this == WAREHOUSE;
	}
}
