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
import java.lang.ref.WeakReference;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.util.Check;

/* package */class SwingDefaultRenderer extends DefaultTableCellRenderer
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -472685779799788499L;
	private final WeakReference<ITerminalContext> terminalContextRef;
	private final int alignment;

	public SwingDefaultRenderer(final ITerminalContext terminalContext, int alignment)
	{
		super();

		Check.assumeNotNull(terminalContext, "terminalContext not null");
		this.terminalContextRef = new WeakReference<>(terminalContext);

		this.alignment = alignment;
		setHorizontalAlignment(alignment);
	}

	public final ITerminalContext getTerminalContext()
	{
		final ITerminalContext terminalContext = terminalContextRef.get();
		if (terminalContext == null)
		{
			throw new RuntimeException("Terminal Context expired");
		}
		return terminalContext;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	{
		final String valueStr = valueToString(value);

		final Component comp = super.getTableCellRendererComponent(table, valueStr, isSelected, hasFocus, row, column);

		//
		// Set Font
		final Font fontOld = comp.getFont();
		final float fontSize = (float)getTerminalContext().getDefaultFontSize();
		final Font fontNew = fontOld.deriveFont(fontSize);
		comp.setFont(fontNew);

		//
		// Set Horizontal alignment
		setHorizontalAlignment(alignment);

		return comp;
	}

	protected String valueToString(final Object value)
	{
		final String valueStr = value == null ? "" : value.toString();
		return valueStr;

	}

}
