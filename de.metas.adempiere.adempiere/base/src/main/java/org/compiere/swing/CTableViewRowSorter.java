package org.compiere.swing;

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


import java.util.List;

import javax.swing.SortOrder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * {@link CTable}'s view level row sorter.
 * 
 * @author tsa
 *
 */
class CTableViewRowSorter extends TableRowSorter<TableModel>
{
	private CTableModelRowSorter modelRowSorter = null;

	public CTableViewRowSorter()
	{
		super();
		setMaxSortKeys(1);
	}

	public CTableViewRowSorter setModelRowSorter(final CTableModelRowSorter modelRowSorter)
	{
		this.modelRowSorter = modelRowSorter;
		return this;
	}

	@Override
	public void toggleSortOrder(final int modelColumnIndex)
	{
		super.toggleSortOrder(modelColumnIndex);

		// Update our internal map, because that's the place where renderer is checking
		if (modelRowSorter != null)
		{
			modelRowSorter.setSortKeys(getSortKeys());
		}
	}

	public final SortOrder getSortOrder(final int modelColumnIndex)
	{
		final List<? extends SortKey> sortKeys = getSortKeys();
		for (final SortKey sortKey : sortKeys)
		{
			if (sortKey.getColumn() == modelColumnIndex)
			{
				return sortKey.getSortOrder();
			}
		}
		return SortOrder.UNSORTED;
	}
}
