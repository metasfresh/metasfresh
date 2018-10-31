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

import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import org.jdesktop.swingx.JXTable;

import de.metas.util.Check;

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

	private boolean columnControlVisible = true;
	private TableModel model;
	private Integer selectionMode;
	private final List<ListSelectionListener> selectionListeners = new ArrayList<>();
	private boolean createStandardSelectActions = true;

	private final List<Action> popupActions = new ArrayList<>();

	private AnnotatedTableFactory()
	{
		super();
	}

	public AnnotatedJXTable create()
	{
		final AnnotatedJXTable table = new AnnotatedJXTable();
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); // enable multi-selection because actions like Select/Deselect needs that.

		// Disable "USE_DTCR_COLORMEMORY_HACK" because if enabled, the colors set on cell renderer are somehow overridden.
		table.putClientProperty(JXTable.USE_DTCR_COLORMEMORY_HACK, false);

		table.setColumnControlVisible(columnControlVisible);

		table.setColumnFactory(AnnotatedTableColumnFactory.instance);

		//
		// Set the table model if any
		if (model != null)
		{
			table.setModel(model);
		}

		//
		// Customize the selection listener
		final ListSelectionModel selectionModel = table.getSelectionModel();
		if (selectionMode != null)
		{
			selectionModel.setSelectionMode(selectionMode);
		}
		for (final ListSelectionListener listener : selectionListeners)
		{
			selectionModel.addListSelectionListener(listener);
		}

		//
		// Context menu popup, displayed when user right clicks somewhere inside the table
		{
			final AnnotatedTablePopupMenu popup = new AnnotatedTablePopupMenu(table);

			if (createStandardSelectActions)
			{
				popup.add(new SelectAction());
				popup.add(new DeselectAction());
			}

			for (final Action popupAction : popupActions)
			{
				popup.add(popupAction);
			}

			table.setComponentPopupMenu(popup);
		}

		return table;
	}

	public AnnotatedTableFactory setColumnControlVisible(final boolean columnControlVisible)
	{
		this.columnControlVisible = columnControlVisible;
		return this;
	}

	public AnnotatedTableFactory setModel(final TableModel model)
	{
		this.model = model;
		return this;
	}

	/**
	 * @param selectionMode
	 * @see ListSelectionModel#setSelectionMode(int)
	 */
	public AnnotatedTableFactory setSelectionMode(final int selectionMode)
	{
		this.selectionMode = selectionMode;
		return this;
	}

	public AnnotatedTableFactory addListSelectionListener(final ListSelectionListener listener)
	{
		Check.assumeNotNull(listener, "listener not null");
		selectionListeners.add(listener);
		return this;
	}

	public AnnotatedTableFactory setCreateStandardSelectActions(final boolean createStandardSelectActions)
	{
		this.createStandardSelectActions = createStandardSelectActions;
		return this;
	}

	/**
	 * Adds an popup action.
	 * 
	 * NOTE to developer: you can easily implement table popup actions by extending {@link AnnotatedTableAction}.
	 * 
	 * @param action
	 * @return this
	 */
	public AnnotatedTableFactory addPopupAction(final Action action)
	{
		Check.assumeNotNull(action, "action not null");
		if (!popupActions.contains(action))
		{
			popupActions.add(action);
		}

		return this;
	}

}
