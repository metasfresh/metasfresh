package de.metas.adempiere.form.terminal.table.swing;

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


import java.math.BigDecimal;
import java.util.Date;

import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellEditor;

import org.adempiere.util.Check;
import org.compiere.util.KeyNamePair;
import org.jdesktop.swingx.JXTable;

import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.ITerminalScrollPane;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.table.ITableColumnInfo;
import de.metas.adempiere.form.terminal.table.ITerminalTable2;
import de.metas.adempiere.form.terminal.table.ITerminalTableModel;
import de.metas.adempiere.form.terminal.table.ITerminalTableModel.SelectionMode;
import de.metas.adempiere.form.terminal.table.ITerminalTableModelListener;

public class SwingTerminalTable2<T> implements ITerminalTable2<T>
{
	private final ITerminalContext terminalContext;
	private ITerminalTableModel<T> model;

	//
	// UI
	private JXTable tableSwing;
	private ITerminalScrollPane tableScroll;
	private SwingSelectionModelSynchronizer<T> selectionModelSync;

	private boolean disposed = false;

	public SwingTerminalTable2(final ITerminalContext terminalContext)
	{
		Check.assumeNotNull(terminalContext, "terminalContext not null");
		this.terminalContext = terminalContext;

		this.tableSwing = new JXTable()
		{
			private static final long serialVersionUID = 1971296677096025546L;

			/**
			 * @author http://www.jroller.com/ethdsy/entry/jtable_selection_toggle
			 */
			@Override
			public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend)
			{
				// Always toggle on single selection
				final boolean toggleToUse = !extend;
				super.changeSelection(rowIndex, columnIndex, toggleToUse, extend);
			}
		};
		tableSwing.setColumnFactory(SwingTerminalTable2ColumnFactory.instance);
		tableSwing.setAutoCreateColumnsFromModel(true);

		final ITerminalFactory factory = terminalContext.getTerminalFactory();
		this.tableScroll = factory.createScrollPane(factory.wrapComponent(tableSwing));

		tableSwing.getTableHeader().setDefaultRenderer(new SwingTableHeaderRenderer(terminalContext, tableSwing));

		// Register default editors
		{
			final SwingStringEditor stringEditor = new SwingStringEditor(terminalContext);
			tableSwing.setDefaultRenderer(String.class, stringEditor);
			tableSwing.setDefaultEditor(String.class, stringEditor);

			final SwingNumericEditor numericEditor = new SwingNumericEditor(terminalContext);
			tableSwing.setDefaultRenderer(BigDecimal.class, numericEditor);
			tableSwing.setDefaultEditor(BigDecimal.class, numericEditor);
			//
			tableSwing.setDefaultRenderer(Integer.class, numericEditor);
			tableSwing.setDefaultEditor(Integer.class, numericEditor);

			tableSwing.setDefaultRenderer(Date.class, new SwingDateEditor(terminalContext));
			tableSwing.setDefaultEditor(Date.class, null);

			final SwingKeyNamePairEditor<T> keyNamePairLookupEditor = new SwingKeyNamePairEditor<T>(terminalContext, this);
			tableSwing.setDefaultRenderer(KeyNamePair.class, keyNamePairLookupEditor);
			tableSwing.setDefaultEditor(KeyNamePair.class, keyNamePairLookupEditor);

			// Default in case nothing found
			tableSwing.setDefaultRenderer(Object.class, new SwingDefaultRenderer(terminalContext, SwingConstants.LEFT));
		}

		setRowHeight(60);// FIXME: hardcoded

		terminalContext.addToDisposableComponents(this);
	}

	@Override
	public ITerminalContext getTerminalContext()
	{
		return terminalContext;
	}

	@Override
	public Object getComponent()
	{
		return tableScroll.getComponent();
	}

	@Override
	public void setModel(final ITerminalTableModel<T> model)
	{
		final ITerminalTableModel<T> modelOld = this.model;
		if (modelOld == model)
		{
			// nothing changed
			return;
		}

		setModelUI(model, modelOld);
		this.model = model;
	}

	protected void setModelUI(final ITerminalTableModel<T> model, final ITerminalTableModel<T> modelOld)
	{
		if (modelOld == model)
		{
			// nothing changed
			return;
		}
		if (modelOld != null)
		{
			// NOTE: not supported because we need to unregister first the current listeners from old model
			throw new IllegalStateException("Cannot set a new model after a model was already set. This is not supported yet");
		}

		final SwingTerminalTableModelAdapter<T> modelSwing = new SwingTerminalTableModelAdapter<T>(model);
		tableSwing.setModel(modelSwing);

		tableSwing.setEditable(model.isEditable());

		//
		// Selection Model
		{
			final SelectionMode selectionMode = model.getSelectionMode();
			final int selectionModeSwing = getSwingSelectionMode(selectionMode);

			final ListSelectionModel selectionModelSwing = tableSwing.getSelectionModel();
			selectionModelSwing.setSelectionMode(selectionModeSwing);

			this.selectionModelSync = new SwingSelectionModelSynchronizer<T>(model, tableSwing);
			selectionModelSwing.addListSelectionListener(selectionModelSync);

			model.addTerminalTableModelListener(selectionModelSync);
		}

		//
		// Model requests stop editing
		ITerminalTableModelListener modelListener = new TerminalTableModelListenerAdapter()
		{
			@Override
			public void fireRequestStopEditing()
			{
				stopEditing();
			}
		};
		model.addTerminalTableModelListener(modelListener);
	}

	private int getSwingSelectionMode(final SelectionMode selectionMode)
	{
		if (selectionMode == null)
		{
			return ListSelectionModel.SINGLE_SELECTION;
		}
		else if (selectionMode == SelectionMode.SINGLE)
		{
			return ListSelectionModel.SINGLE_SELECTION;
		}
		else if (selectionMode == SelectionMode.MULTIPLE)
		{
			return ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;
		}
		else
		{
			throw new IllegalArgumentException("Unknown selection mode: " + selectionMode);
		}

	}

	@Override
	public final ITerminalTableModel<T> getModel()
	{
		return model;
	}

	@Override
	public void stopEditing()
	{
		final TableCellEditor editor = tableSwing.getCellEditor();
		if (editor != null)
		{
			editor.stopCellEditing();
		}
	}

	/**
	 * Converts column index from View coordinates to Model coordinates
	 *
	 * @param viewColumnIndex
	 * @return model column index
	 */
	protected int convertColumnIndexToModel(final int viewColumnIndex)
	{
		return tableSwing.convertColumnIndexToModel(viewColumnIndex);
	}

	protected ITableColumnInfo getTableColumnInfo(final int columnIndexView)
	{
		final ITerminalTableModel<T> model = getModel();
		if (model == null)
		{
			return null;
		}
		final int columnIndexModel = convertColumnIndexToModel(columnIndexView);
		return model.getTableColumnInfo(columnIndexModel);
	}

	@Override
	public void dispose()
	{
		if (tableSwing != null)
		{
			tableSwing.removeAll();
			tableSwing = null;
		}

		if (model != null)
		{
			if (selectionModelSync != null)
			{
				model.removeTerminalTableModelListener(selectionModelSync);
				selectionModelSync = null;
			}
			model = null; // remove the link... we did not created, we are not destroying it
		}

		disposed = true;
	}

	@Override
	public boolean isDisposed()
	{
		return disposed ;
	}

	@Override
	public void setRowHeight(final int rowHeight)
	{
		tableSwing.setRowHeight(rowHeight);
	}
}
