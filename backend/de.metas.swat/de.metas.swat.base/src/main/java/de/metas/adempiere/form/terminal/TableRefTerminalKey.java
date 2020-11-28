/**
 * 
 */
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


import java.awt.Color;

import org.compiere.util.KeyNamePair;

import de.metas.adempiere.form.terminal.context.ITerminalContext;

/**
 * @author tsa
 * 
 */
public class TableRefTerminalKey extends TerminalKey
{
	private final String tableName;
	private final KeyNamePair value;

	private final DefaultStatus defaultStatus = new DefaultStatus();

	private class DefaultStatus implements ITerminalKeyStatus
	{
		@Override
		public int getAD_Image_ID()
		{
			return 0;
		}

		@Override
		public int getAD_PrintColor_ID()
		{
			return 0;
		}

		@Override
		public int getAD_PrintFont_ID()
		{
			return 0;
		}

		@Override
		public String getName()
		{
			return value.getName();
		}

		@Override
		public Color getColor()
		{
			return null;
		}
	}

	public TableRefTerminalKey(final ITerminalContext terminalContext, String tableName, KeyNamePair value)
	{
		super(terminalContext);
		this.tableName = tableName;
		this.value = value;
	}

	@Override
	public String getId()
	{
		return tableName + "#" + value.getKey();
	}

	@Override
	public Object getName()
	{
		return value.getName();
	}

	@Override
	public int getSpanX()
	{
		return 1;
	}

	@Override
	public int getSpanY()
	{
		return 1;
	}

	@Override
	public ITerminalKeyStatus getStatus()
	{
		return defaultStatus;
	}

	@Override
	public IKeyLayout getSubKeyLayout()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTableName()
	{
		return tableName;
	}

	@Override
	public String getText()
	{
		return null;
	}

	@Override
	public KeyNamePair getValue()
	{
		return value;
	}

	@Override
	public boolean isActive()
	{
		return true;
	}
}
