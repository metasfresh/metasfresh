package de.metas.adempiere.form.terminal;

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

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.NamePair;

import de.metas.adempiere.form.terminal.context.ITerminalContext;

public abstract class AbstractTerminalComboboxField
		extends AbstractTerminalField<NamePair>
		implements ITerminalComboboxField
{
	private final String name;

	public AbstractTerminalComboboxField(final ITerminalContext terminalContext, final String name)
	{
		super(terminalContext);

		this.name = name;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	protected NamePair convertValueToType(final Object value)
	{
		final NamePair valueConv;
		if (value == null)
		{
			valueConv = null;
		}
		else if (value instanceof NamePair)
		{
			valueConv = (NamePair)value;
		}
		else if (value instanceof String)
		{
			final String valueStr = value.toString();
			valueConv = getValueByKeyOrNull(valueStr);
		}
		else if (value instanceof Integer)
		{
			final String valueStr = value.toString();
			valueConv = getValueByKeyOrNull(valueStr);
		}
		else if (value instanceof BigDecimal)
		{
			final String valueStr = String.valueOf(((BigDecimal)value).intValueExact());
			valueConv = getValueByKeyOrNull(valueStr);
		}
		else
		{
			throw new AdempiereException("Value not supported: " + value + " (class=" + value.getClass() + ")");
		}

		return valueConv;
	}

	public abstract NamePair getValueByKeyOrNull(String key);

	@Override
	public abstract NamePair getSelectedValue();
}
