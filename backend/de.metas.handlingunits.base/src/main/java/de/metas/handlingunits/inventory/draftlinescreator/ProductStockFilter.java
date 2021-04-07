/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.inventory.draftlinescreator;

import java.util.Objects;

public enum ProductStockFilter
{
	STOCKED("S"),
	NON_STOCKED("N"),
	ALL("A");

	String name;

	ProductStockFilter(final String name)
	{
		this.name = name;
	}

	public static ProductStockFilter of(final String name)
	{
		if (Objects.equals(STOCKED.name, name))
		{
			return STOCKED;
		}
		if (Objects.equals(NON_STOCKED.name, name))
		{
			return NON_STOCKED;
		}
		return ALL;
	}
}
