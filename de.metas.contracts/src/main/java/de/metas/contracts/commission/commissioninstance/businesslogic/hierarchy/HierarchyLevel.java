package de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy;

import static de.metas.util.Check.assumeGreaterOrEqualToZero;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;

/*
 * #%L
 * de.metas.commission
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

@Value
public class HierarchyLevel
{
	public static final HierarchyLevel ZERO = of(0);

	@JsonCreator
	public static HierarchyLevel of(int level)
	{
		return new HierarchyLevel(level);
	}

	@Getter(AccessLevel.NONE)
	int level;

	@JsonValue
	public int toInt()
	{
		return level;
	}

	private HierarchyLevel(final int level)
	{
		this.level = assumeGreaterOrEqualToZero(level, "level");
	}

	public HierarchyLevel incByOne()
	{
		return of(this.toInt() + 1);
	}
}
