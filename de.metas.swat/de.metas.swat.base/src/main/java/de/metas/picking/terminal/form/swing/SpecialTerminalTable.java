/**
 * 
 */
package de.metas.picking.terminal.form.swing;

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


import java.awt.Color;
import java.awt.Component;

import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.swing.TerminalTable;
import de.metas.picking.terminal.IPackingStateProvider;
import de.metas.picking.terminal.Utils.PackingStates;

/**
 * @author cg
 * 
 */
public class SpecialTerminalTable extends TerminalTable
{
	private static final long serialVersionUID = -999401273706990699L;

	private IPackingStateProvider packingStateProvider = null;

	/**
	 * @param tc
	 */
	public SpecialTerminalTable(ITerminalContext tc)
	{
		super(tc);
	}

	public void setColorForIDColumn(final Color color, final int row)
	{
		final Object value = getValueAt(row, 0);
		final boolean isSelected = isCellSelected(row, 0);
		final boolean hasFocus = hasFocus();
		final int column = 0; // ID Column
		final Component comp = getCellRenderer(row, 0).getTableCellRendererComponent(this, value, isSelected, hasFocus, row, column);
		comp.setBackground(color);
	}

	public void setPackingStateProvider(final IPackingStateProvider packingStateProvider)
	{
		this.packingStateProvider = packingStateProvider;
	}

	public IPackingStateProvider getPackingStateProvider()
	{
		return this.packingStateProvider;
	}

	@Override
	public Component prepareRenderer(final TableCellRenderer renderer, int row, int column)
	{
		final Component c = super.prepareRenderer(renderer, row, column);
		if (packingStateProvider == null)
		{
			return c;
		}

		// we need to paint just the first column
		if (column > 0)
		{
			return c;
		}

		final PackingStates state = packingStateProvider.getPackingState(row);
		if (state != null)
		{
			c.setBackground(state.getColor());
		}
		return c;
	}
	
	@Override
	public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend)
	{
		final ListSelectionModel selectionModel = getSelectionModel();
		boolean selected = selectionModel.isSelectedIndex(rowIndex);
		if (selected)
		{
			selectionModel.removeSelectionInterval(rowIndex, rowIndex);
			getValueAt(rowIndex, columnIndex);
		}
		else
		{
			selectionModel.addSelectionInterval(rowIndex, rowIndex);
		}
	}
}
