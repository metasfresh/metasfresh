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


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import org.adempiere.util.lang.WeakReference;
import org.compiere.util.DisplayType;
import org.jdesktop.swingx.table.ColumnFactory;
import org.jdesktop.swingx.table.TableColumnExt;

class AnnotatedTableColumnFactory extends ColumnFactory
{
	public static final transient AnnotatedTableColumnFactory instance = new AnnotatedTableColumnFactory();

	private AnnotatedTableColumnFactory()
	{
		super();
	}

	@Override
	public void configureTableColumn(final TableModel model, final TableColumnExt columnExt)
	{
		super.configureTableColumn(model, columnExt);

		final TableModelMetaInfo<?> modelMetaInfo = getTableModelMetaInfoOrNull(model);
		if (modelMetaInfo == null)
		{
			return;
		}

		final int columnIndex = columnExt.getModelIndex();
		final TableColumnInfo columnMetaInfo = modelMetaInfo.getTableColumnInfo(columnIndex);

		columnExt.setCellRenderer(createTableCellRenderer(columnMetaInfo));
		columnExt.setCellEditor(createTableCellEditor(columnMetaInfo));

		if (columnMetaInfo.isSelectionColumn())
		{
			columnExt.setMaxWidth(25);
			columnExt.putClientProperty(AnnotatedColumnControlButton.PROPERTY_DisableColumnControl, true);
		}

		updateColumnExtFromMetaInfo(columnExt, columnMetaInfo);

		//
		// Synchronize: columnMetaInfo -> columnExt
		columnMetaInfo.addPropertyChangeListener(new TableColumnInfo2TableColumnExtSynchronizer(columnExt));
	}

	private static final void updateColumnExtFromMetaInfo(final TableColumnExt columnExt, final TableColumnInfo columnMetaInfo)
	{
		columnExt.setEditable(columnMetaInfo.isEditable());
		columnExt.setVisible(columnMetaInfo.isVisible());
		columnExt.setPrototypeValue(columnMetaInfo.getPrototypeValue());
	}

	private static final TableModelMetaInfo<?> getTableModelMetaInfoOrNull(final TableModel model)
	{
		if (!(model instanceof AnnotatedTableModel<?>))
		{
			return null;
		}

		final TableModelMetaInfo<?> metaInfo = ((AnnotatedTableModel<?>)model).getMetaInfo();
		return metaInfo;
	}

	private TableCellRenderer createTableCellRenderer(final TableColumnInfo columnMetaInfo)
	{
		if (columnMetaInfo.isSelectionColumn())
		{
			return new SelectionColumnCellRenderer();
		}

		final int displayTypeToUse = columnMetaInfo.getDisplayType(DisplayType.String);
		return new AnnotatedTableCellRenderer(displayTypeToUse);
	}

	private TableCellEditor createTableCellEditor(final TableColumnInfo columnMetaInfo)
	{
		if (columnMetaInfo.isSelectionColumn())
		{
			return new SelectionColumnCellEditor();
		}

		return new AnnotatedTableCellEditor(columnMetaInfo);
	}

	/**
	 * Synchronize changes from {@link TableColumnInfo} to {@link TableColumnExt}.
	 * 
	 * @author tsa
	 *
	 */
	private static final class TableColumnInfo2TableColumnExtSynchronizer implements PropertyChangeListener
	{
		private final WeakReference<TableColumnExt> columnExtRef;
		private boolean running = false;

		public TableColumnInfo2TableColumnExtSynchronizer(final TableColumnExt columnExt)
		{
			super();
			columnExtRef = new WeakReference<>(columnExt);
		}

		private TableColumnExt getTableColumnExt()
		{
			return columnExtRef.getValue();
		}

		@Override
		public void propertyChange(final PropertyChangeEvent evt)
		{
			// Avoid recursion
			if (running)
			{
				return;
			}

			running = true;
			try
			{
				final TableColumnInfo columnMetaInfo = (TableColumnInfo)evt.getSource();

				final TableColumnExt columnExt = getTableColumnExt();
				if (columnExt == null)
				{
					// ColumnExt reference expired:
					// * remove this listener because there is no point to have this listener invoked in future
					// * exit quickly because there is nothing to do
					columnMetaInfo.removePropertyChangeListener(this);
					return;
				}

				updateColumnExtFromMetaInfo(columnExt, columnMetaInfo);
			}
			finally
			{
				running = false;
			}
		}
	}
}
