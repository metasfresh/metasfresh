package de.metas.fresh.picking;

/*
 * #%L
 * de.metas.fresh.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.text.DateFormat;
import java.time.LocalDate;

import org.compiere.util.DisplayType;
import org.compiere.util.KeyNamePair;
import org.compiere.util.TimeUtil;

import de.metas.adempiere.form.terminal.TerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;

public class DeliveryDateKey extends TerminalKey
{
	private final LocalDate date;
	private String id;
	private String name;

	/* package */ DeliveryDateKey(final ITerminalContext terminalContext, final LocalDate date)
	{
		super(terminalContext);

		if (date == null)
		{
			this.date = null;
			this.name = "Alle";
		}
		else
		{
			this.date = date;

			final DateFormat dateFormat = DisplayType.getDateFormat(DisplayType.Date);
			this.name = dateFormat.format(TimeUtil.asTimestamp(this.date));
		}

		this.id = name;
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public Object getName()
	{
		return name;
	}

	@Override
	public String getTableName()
	{
		return null;
	}

	@Override
	public KeyNamePair getValue()
	{
		return null;
	}

	public LocalDate getDate()
	{
		return date;
	}
}
