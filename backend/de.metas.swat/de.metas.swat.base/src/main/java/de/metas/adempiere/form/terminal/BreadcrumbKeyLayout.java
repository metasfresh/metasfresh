package de.metas.adempiere.form.terminal;

/*
 * #%L
 * de.metas.swat.base
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


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.compiere.util.Util;

import de.metas.adempiere.form.terminal.context.ITerminalContext;

/**
 * {@link KeyLayout} implementation which behaves like a breadcrumb
 *
 * @author tsa
 *
 */
public class BreadcrumbKeyLayout extends KeyLayout implements IKeyLayoutSelectionModelAware
{
	private final String keyLayoutId;

	private final int DEFAULT_Columns = 5;

	public BreadcrumbKeyLayout(final ITerminalContext tc)
	{
		super(tc);

		final IKeyLayoutSelectionModel selectionModel = getKeyLayoutSelectionModel();
		selectionModel.setAllowKeySelection(true);

		this.keyLayoutId = getClass().getName() + "-" + UUID.randomUUID();

		setRows(1);
		setColumnsMin(3);
		setColumns(DEFAULT_Columns);
	}

	@Override
	public String getId()
	{
		return keyLayoutId;
	}

	@Override
	public final boolean isText()
	{
		return false;
	}

	@Override
	public final boolean isNumeric()
	{
		return false;
	}

	@Override
	protected final List<ITerminalKey> createKeys()
	{
		return Collections.emptyList();
	}

	public final void setLastItem(final ITerminalKey lastItem)
	{
		if (lastItem == null)
		{
			return;
		}

		// FIXME: handle the case when current items count > getColumns()

		//
		// Take a copy of current items to work on
		final List<ITerminalKey> items = getKeysOrNull();
		final List<ITerminalKey> itemsNew;
		if (items == null)
		{
			itemsNew = new ArrayList<ITerminalKey>();
		}
		else
		{
			itemsNew = new ArrayList<ITerminalKey>(items);
		}

		boolean itemsChanged = false;
		boolean lastItemFound = false;
		for (final Iterator<ITerminalKey> it = itemsNew.iterator(); it.hasNext();)
		{
			final ITerminalKey key = it.next();

			//
			// Our last breadcrumb element was already found
			// => delete all after it
			if (lastItemFound)
			{
				it.remove();
				itemsChanged = true;
			}
			//
			// We just found now our lastItem which shall be the last element
			else if (Util.same(key, lastItem))
			{
				lastItemFound = true;
			}
			//
			// Our last breadcrumb element was not found yet
			else
			{
				// nothing to do
			}
		}

		//
		// Our last breadcrumb element was not found at all
		// => We are adding it
		if (!lastItemFound)
		{
			itemsNew.add(lastItem);
			itemsChanged = true;
		}

		//
		// We just changed the items
		if (itemsChanged)
		{
			setKeys(itemsNew);
			lastItemFound = true;
		}

		final IKeyLayoutSelectionModel selectionModel = getKeyLayoutSelectionModel();
		selectionModel.onKeySelected(lastItem);
	}
}
