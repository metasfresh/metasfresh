package org.compiere.swing.table;

import javax.swing.AbstractAction;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableModel;

import org.adempiere.util.Check;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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
 * Base class for table popup actions.
 *
 * To be used with {@link AnnotatedTableFactory#addPopupAction(javax.swing.Action)}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public abstract class AnnotatedTableAction extends AbstractAction
{
	private static final long serialVersionUID = 1L;

	private JTable _table;

	public AnnotatedTableAction(final String name)
	{
		super(name);
	}

	/**
	 * Sets the table for which this action will be called.
	 * 
	 * NOTE: don't call it. It will be called by API.
	 * 
	 * @param table
	 */
	public void setTable(final JTable table)
	{
		_table = table;
	}

	public final JTable getTable()
	{
		Check.assumeNotNull(_table, "table configured for action {}", this);
		return _table;
	}

	/**
	 * Method called by API right before the popup is about to be displayed.
	 * 
	 * Feel free to extend and implement your custom business logic here.
	 */
	public void updateBeforeDisplaying()
	{
		// nothing
	}

	protected final AnnotatedTableModel<?> getTableModelOrNull()
	{
		final JTable table = getTable();
		if (table == null)
		{
			return null;
		}
		final TableModel tableModel = table.getModel();
		if (tableModel instanceof AnnotatedTableModel<?>)
		{
			return (AnnotatedTableModel<?>)tableModel;
		}

		return null;
	}

	protected final ListSelectionModel getSelectionModelOrNull()
	{
		final JTable table = getTable();
		if (table == null)
		{
			return null;
		}
		final ListSelectionModel selectionModel = table.getSelectionModel();
		return selectionModel;
	}

}
