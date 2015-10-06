package org.compiere.swing.table;

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


import javax.swing.JPopupMenu;
import javax.swing.ListSelectionModel;
import javax.swing.event.PopupMenuEvent;

import org.compiere.swing.PopupMenuListenerAdapter;
import org.jdesktop.swingx.JXTable;

/**
 * Factory used to create swing {@link JXTable}s which supports {@link AnnotatedTableModel} implementations.
 * 
 * @author tsa
 *
 */
public class AnnotatedTableFactory
{
	public static final AnnotatedTableFactory newInstance()
	{
		return new AnnotatedTableFactory();
	}

	private AnnotatedTableFactory()
	{
		super();
	}

	public JXTable create()
	{
		final AnnotatedJXTable table = new AnnotatedJXTable();
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); // enable multi-selection because actions like Select/Deselect needs that.

		// Disable "USE_DTCR_COLORMEMORY_HACK" because if enabled, the colors set on cell renderer are somehow overridden.
		table.putClientProperty(JXTable.USE_DTCR_COLORMEMORY_HACK, false);

		table.setColumnControlVisible(true);

		table.setColumnFactory(AnnotatedTableColumnFactory.instance);

		//
		// Context menu popup, displayed when user right clicks somewhere inside the table
		{
			final JPopupMenu popup = new JPopupMenu();

			final SelectAction actionSelect = new SelectAction(table);
			popup.add(actionSelect);
			//
			final DeselectAction actionDeselect = new DeselectAction(table);
			popup.add(actionDeselect);
			//
			popup.addPopupMenuListener(new PopupMenuListenerAdapter()
			{
				@Override
				public void popupMenuWillBecomeVisible(PopupMenuEvent e)
				{
					actionSelect.updateBeforeDisplaying();
					actionDeselect.updateBeforeDisplaying();
				}
			});

			table.setComponentPopupMenu(popup);
		}

		return table;
	}
}
