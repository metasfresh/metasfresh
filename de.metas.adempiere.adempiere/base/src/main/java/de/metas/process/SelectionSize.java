package de.metas.process;

import org.adempiere.exceptions.AdempiereException;

import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Value
public class SelectionSize
{
	int size;

	boolean allSelected;

	public static SelectionSize ofSize(int size)
	{
		return new SelectionSize(size, false);
	}

	public static SelectionSize ofAll()
	{
		return new SelectionSize(-1, true);
	}

	private SelectionSize(final int size, final boolean allSelected)
	{
		this.size = size;
		this.allSelected = allSelected;
	}

	/** @return true if there is no selected rows */
	public boolean isNoSelection()
	{
		return !isAllSelected() && getSize() <= 0;
	}

	/** @return true if only one row is selected */
	public boolean isSingleSelection()
	{
		return !isAllSelected() && getSize() == 1;
	}

	/** @return true if there are more then one selected row */
	public boolean isMoreThanOneSelected()
	{
		return isAllSelected() || getSize() > 1;
	}

	public int getSize()
	{
		if (allSelected)
		{
			throw new AdempiereException("It's illegal to call getSize() if isAllSelected()==true");
		}
		return size;
	}
}
