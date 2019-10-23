package org.compiere.model;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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


/**
 * {@link GridTab} maximum rows restriction definition.
 * 
 * To create a new instance, please use {@link #of(int)}.
 * 
 * Technically speaking, this is an implementation of flyweight design pattern.
 * 
 * @author tsa
 */
public final class GridTabMaxRows
{
	private static final int DEFAULT_MaxRows = -1000;
	public static final GridTabMaxRows DEFAULT = new GridTabMaxRows(DEFAULT_MaxRows);
	public static final GridTabMaxRows NO_RESTRICTION = new GridTabMaxRows(0);

	/**
	 * Creates a new restrictiction.
	 * 
	 * Based on given <code>maxRows</code> this method can return a new restriction or {@link #DEFAULT}, {@link #NO_RESTRICTION}.
	 * 
	 * @param maxRows
	 * @return max rows restriction.
	 */
	public static final GridTabMaxRows of(final int maxRows)
	{
		if (maxRows == DEFAULT_MaxRows)
		{
			return DEFAULT;
		}
		if (maxRows <= 0)
		{
			return NO_RESTRICTION;
		}

		return new GridTabMaxRows(maxRows);
	}

	private final int maxRows;

	private GridTabMaxRows(final int maxRows)
	{
		super();
		this.maxRows = maxRows;
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder(getClass().getName()).append("[");
		if (isNoRestriction())
		{
			sb.append("NO_RESTRICTION");
		}
		else if (isDefault())
		{
			sb.append("DEFAULT");
		}
		else
		{
			sb.append(maxRows);
		}

		sb.append("]");

		return sb.toString();
	}

	/**
	 * Gets the maximum rows allowed.
	 * 
	 * The returned number makes sense only if it's not {@link #isDefault()} or {@link #isNoRestriction()}.
	 * 
	 * @return max rows allowed.
	 */
	public int getMaxRows()
	{
		return maxRows;
	}

	/**
	 * @return true if this is a "no restrictions".
	 */
	public boolean isNoRestriction()
	{
		return this == NO_RESTRICTION;
	}

	/**
	 * @return true if this restriction asks that context defaults (i.e. defined on role level, tab level etc) to be applied.
	 */
	public boolean isDefault()
	{
		return this == DEFAULT;
	}
}
