package org.compiere.swing.table;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.MenuElement;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import org.adempiere.util.Check;
import org.compiere.swing.PopupMenuListenerAdapter;

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
 * Popup menu for {@link AnnotatedJXTable}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
final class AnnotatedTablePopupMenu extends JPopupMenu
{
	/**
	 *
	 */
	private static final long serialVersionUID = 6618408337499167870L;

	private final WeakReference<JTable> tableRef;

	private final PopupMenuListener popupListener = new PopupMenuListenerAdapter()
	{
		@Override
		public void popupMenuWillBecomeVisible(final PopupMenuEvent e)
		{
			AnnotatedTablePopupMenu.this.popupMenuWillBecomeVisible(e);
		}

		@Override
		public void popupMenuWillBecomeInvisible(final PopupMenuEvent e)
		{
			AnnotatedTablePopupMenu.this.popupMenuWillBecomeInvisible(e);
		}
	};

	public AnnotatedTablePopupMenu(final JTable table)
	{
		super();

		Check.assumeNotNull(table, "table not null");
		tableRef = new WeakReference<>(table);

		addPopupMenuListener(popupListener);
	}

	private final JTable getTable()
	{
		final JTable table = tableRef.get();
		Check.assumeNotNull(table, "table not null");
		return table;
	}

	private final List<AnnotatedTableAction> getTableActions()
	{
		final List<AnnotatedTableAction> tableActions = new ArrayList<>();

		for (final MenuElement me : getSubElements())
		{
			if (!(me instanceof AbstractButton))
			{
				continue;
			}

			final AbstractButton button = (AbstractButton)me;
			final Action action = button.getAction();

			if (action instanceof AnnotatedTableAction)
			{
				final AnnotatedTableAction tableAction = (AnnotatedTableAction)action;
				tableActions.add(tableAction);
			}
		}

		return tableActions;
	}

	private final void popupMenuWillBecomeVisible(final PopupMenuEvent e)
	{
		for (final AnnotatedTableAction tableAction : getTableActions())
		{
			tableAction.setTable(getTable());
			tableAction.updateBeforeDisplaying();
		}
	}

	private final void popupMenuWillBecomeInvisible(final PopupMenuEvent e)
	{
		// nothing atm
	}
}
