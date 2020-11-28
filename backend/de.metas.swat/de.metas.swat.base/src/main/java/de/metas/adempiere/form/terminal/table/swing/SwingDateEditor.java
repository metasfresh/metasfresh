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


import java.text.DateFormat;
import java.util.Date;

import org.compiere.grid.ed.VHeaderRenderer;
import org.compiere.util.DisplayType;

import de.metas.adempiere.form.terminal.context.ITerminalContext;

/* package */ class SwingDateEditor extends SwingDefaultRenderer
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3917057685629274816L;

	private final DateFormat dateFormat;
	private final DateFormat timeFormat;

	public SwingDateEditor(ITerminalContext terminalContext)
	{
		super(terminalContext,
				VHeaderRenderer.getHorizontalAlignmentForDisplayType(DisplayType.Date) // alignment
		);

		dateFormat = DisplayType.getDateFormat(DisplayType.Date);
		timeFormat = DisplayType.getDateFormat(DisplayType.Time);
		// TODO: support for Date+Time
	}

	@Override
	protected String valueToString(final Object value)
	{
		if (value == null)
		{
			return null;
		}
		else if (value instanceof java.sql.Time)
		{
			java.sql.Time time = (java.sql.Time)value;
			return timeFormat.format(time);
		}
		else if (value instanceof Date)
		{
			final Date date = (Date)value;
			return dateFormat.format(date);
		}
		else
		{
			return value.toString();
		}
	}
}
