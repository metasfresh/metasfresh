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


import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import de.metas.adempiere.form.terminal.context.ITerminalContext;

public class SwingTableHeaderRenderer implements TableCellRenderer
{
	private final ITerminalContext terminalContext;
	private final TableCellRenderer rendererDefault;

	public SwingTableHeaderRenderer(final ITerminalContext terminalContext, final JTable table)
	{
		super();
		this.terminalContext = terminalContext;
		this.rendererDefault = table.getTableHeader().getDefaultRenderer();
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	{
		
		final Component comp = rendererDefault.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		final float fontSize = terminalContext.getDefaultFontSize();
		final Font font = comp.getFont();
		final Font fontNew = font.deriveFont(Font.BOLD, (float)fontSize);
		comp.setFont(fontNew);
		
		return comp;
	}

}
