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


import java.awt.event.ActionEvent;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.ListSelectionModel;

import org.adempiere.util.Services;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

import de.metas.i18n.IMsgBL;

/**
 * Base class for implementing actions which:
 * <ul>
 * <li>take the rows user selected in table (NOT the ones that have the flag selected set, but those which are in {@link ListSelectionModel} so they are colored)
 * <li>update the row's model selected flag (see {@link ColumnInfo#selectionColumn()}).
 * </ul>
 * 
 * @author tsa
 *
 */
public abstract class AbstractManageSelectableRowsAction extends AnnotatedTableAction
{
	private static final long serialVersionUID = 1L;

	public AbstractManageSelectableRowsAction(final String nameADMessage)
	{
		super(Services.get(IMsgBL.class).translate(Env.getCtx(), nameADMessage));
	}

	/** @return true if there is something to do on given row and so this action shall be enabled */
	protected abstract boolean isEnabledForRow(SelectableRowAdapter row);

	/** Execute the action of given row */
	protected abstract void executeActionOnRow(final SelectableRowAdapter row);

	@Override
	public final void updateBeforeDisplaying()
	{
		final AtomicBoolean actionEnabled = new AtomicBoolean(false);

		processTableSelectedRows(new SelectableRowProcessor()
		{
			@Override
			public boolean isContinue()
			{
				// continue checking rows until we find out that the action shall be enabled
				return !actionEnabled.get();
			}

			@Override
			public void process(final SelectableRowAdapter row)
			{
				// enable the action if there is at least one row on which we can do something
				if (isEnabledForRow(row))
				{
					actionEnabled.set(true);
				}
			}
		});

		setEnabled(actionEnabled.get());
	}

	@Override
	public final void actionPerformed(final ActionEvent e)
	{
		processTableSelectedRows(new SelectableRowProcessor()
		{

			@Override
			public void process(final SelectableRowAdapter row)
			{
				executeActionOnRow(row);
			}
		});
	}

	private final void processTableSelectedRows(final SelectableRowProcessor processor)
	{
		final AnnotatedTableModel<?> tableModel = getTableModelOrNull();
		if (tableModel == null)
		{
			return;
		}
		final TableColumnInfo selectionColumnInfo = tableModel.getMetaInfo().getSelectionTableColumnInfoOrNull();
		if (selectionColumnInfo == null)
		{
			return;
		}
		final int selectionColumnIndex = tableModel.getColumnIndexByColumnName(selectionColumnInfo.getColumnName());
		if (selectionColumnIndex < 0)
		{
			return;
		}

		final ListSelectionModel selectionModel = getSelectionModelOrNull();
		if (selectionModel == null)
		{
			return;
		}

		final int selectionRowIndexMin = selectionModel.getMinSelectionIndex();
		final int selectionRowIndexMax = selectionModel.getMaxSelectionIndex();
		for (int selectionRowIndex = selectionRowIndexMin; selectionRowIndex <= selectionRowIndexMax; selectionRowIndex++)
		{
			if (!processor.isContinue())
			{
				break;
			}

			if (!selectionModel.isSelectedIndex(selectionRowIndex))
			{
				continue;
			}

			SelectableRowAdapter row = new SelectableRowAdapter(tableModel, selectionRowIndex, selectionColumnIndex);
			processor.process(row);
		}

	}

	protected static abstract class SelectableRowProcessor
	{
		public abstract void process(SelectableRowAdapter row);

		public boolean isContinue()
		{
			return true;
		}
	}

	protected static final class SelectableRowAdapter
	{
		private final AnnotatedTableModel<?> tableModel;
		private final int rowModelIndex;
		private final int selectedColumnIndex;

		public SelectableRowAdapter(final AnnotatedTableModel<?> tableModel, final int rowModelIndex, final int selectedColumnIndex)
		{
			super();
			this.tableModel = tableModel;
			this.rowModelIndex = rowModelIndex;
			this.selectedColumnIndex = selectedColumnIndex;
		}

		public void setSelected(final boolean selected)
		{
			tableModel.setValueAt(selected, rowModelIndex, selectedColumnIndex);
		}

		public boolean isSelected()
		{
			final Object valueObj = tableModel.getValueAt(rowModelIndex, selectedColumnIndex);
			return DisplayType.toBoolean(valueObj);
		}
	}
}
