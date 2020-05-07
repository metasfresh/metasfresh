package de.metas.fresh.picking.form;

/*
 * #%L
 * de.metas.fresh.base
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


import java.util.Comparator;
import java.util.Objects;
import java.util.Set;

import org.adempiere.util.Check;

import de.metas.picking.legacy.form.ITableRowSearchSelectionMatcher;
import de.metas.picking.legacy.form.TableRowKey;

/**
 * 
 * Comparator for TableRowKey keys. Compares by
 * <ul>
 * <li>First: Put selected rows first</li>
 * <li>Second: Put matched records first</li>
 * <li>Third: preserve the order (<code>SeqNo</code>) as we retrieved from database</li>
 * </ul>
 * 
 */
public class FreshPackingMdLinesComparator implements Comparator<TableRowKey>
{

	private final FreshPackingMd model;

	public FreshPackingMdLinesComparator(FreshPackingMd model)
	{
		super();

		Check.assumeNotNull(model, "model not null");
		this.model = model;
	}

	@Override
	public int compare(final TableRowKey o1, final TableRowKey o2)
	{
		if (Objects.equals(o1, o2))
		{
			return 0;
		}

		//
		// First: Put selected rows first
		{
			final Set<TableRowKey> selectedTableRowKeys = model.getSelectedTableRowKeys();
			final int selectedSeqNo1 = selectedTableRowKeys.contains(o1) ? 0 : 1;
			final int selectedSeqNo2 = selectedTableRowKeys.contains(o2) ? 0 : 1;
			if (selectedSeqNo1 != selectedSeqNo2)
			{
				return selectedSeqNo1 - selectedSeqNo2;
			}
		}

		//
		// Second: Put matched records first
		final ITableRowSearchSelectionMatcher tableRowSearchSelectionMatcher = model.getTableRowSearchSelectionMatcher();
		if (!tableRowSearchSelectionMatcher.isNull())
		{
			final int matcherSeqNo1 = tableRowSearchSelectionMatcher.match(o1) ? 0 : 1;
			final int matcherSeqNo2 = tableRowSearchSelectionMatcher.match(o2) ? 0 : 1;
			if (matcherSeqNo1 != matcherSeqNo2)
			{
				return matcherSeqNo1 - matcherSeqNo2;
			}
		}

		//
		// Third: preserve the order as we retrieved from database
		return o1.getSeqNo() - o2.getSeqNo();
	}

}
