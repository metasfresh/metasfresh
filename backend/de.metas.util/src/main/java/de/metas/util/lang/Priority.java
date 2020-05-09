package de.metas.util.lang;

import lombok.NonNull;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2020 metas GmbH
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

public enum Priority
{
	HIGHEST(Integer.MIN_VALUE), //
	MEDIUM(100), //
	LOWEST(Integer.MAX_VALUE), //
	;

	private final int value;

	Priority(final int value)
	{
		this.value = value;
	}

	public int toInt()
	{
		return value;
	}

	public boolean isHigherThan(@NonNull final Priority other)
	{
		return this.value < other.value;
	}
}
