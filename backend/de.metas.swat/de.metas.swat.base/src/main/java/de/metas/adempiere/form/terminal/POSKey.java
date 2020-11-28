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

import org.compiere.model.Query;
import org.compiere.model.X_C_POSKey;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.model.I_C_POSKey;

/**
 * @author tsa
 * 
 */
public class POSKey extends TerminalKey
{
	public static final String KEY_Backspace = "BS";
	
	private I_C_POSKey key;
	private KeyNamePair value;
	private String tableName;
	private ITerminalKeyStatus status;

	private class DefaultStatus implements ITerminalKeyStatus
	{

		@Override
		public int getAD_Image_ID()
		{
			return key.getAD_Image_ID();
		}

		@Override
		public int getAD_PrintColor_ID()
		{
			return key.getAD_PrintColor_ID();
		}

		@Override
		public int getAD_PrintFont_ID()
		{
			return key.getAD_PrintFont_ID();
		}

		@Override
		public String getName()
		{
			return "default";
		}

		@Override
		public Color getColor()
		{
			return null;
		}
	}

	protected POSKey(ITerminalContext tc, I_C_POSKey key, String tableName, int idValue)
	{
		this(tc, key, tableName, new KeyNamePair(idValue, key.getName()));
	}

	protected POSKey(ITerminalContext tc, I_C_POSKey key, String tableName, KeyNamePair value)
	{
		super(tc);
		
		this.key = key;
		this.tableName = tableName;
		this.value = value;
		this.status = new DefaultStatus();
	}

	public static I_C_POSKey getC_POSKey(int C_POSKey_ID)
	{
		String whereClause = X_C_POSKey.COLUMNNAME_C_POSKey_ID + " = ?";
		return new Query(Env.getCtx(), I_C_POSKey.Table_Name, whereClause, null)
							.setParameters(C_POSKey_ID)
							.firstOnly(I_C_POSKey.class);
	}

	@Override
	public ITerminalKeyStatus getStatus()
	{
		return this.status;
	}

	@Override
	public String getId()
	{
		return "" + key.getC_POSKey_ID()
				+ (value != null ? "#" + value.getKey() : "");
	}

	@Override
	public int getSpanX()
	{
		return key.getSpanX();
	}

	@Override
	public int getSpanY()
	{
		return key.getSpanY();
	}

	@Override
	public boolean isActive()
	{
		return key.isActive();
	}

	@Override
	public IKeyLayout getSubKeyLayout()
	{
		if (key.getSubKeyLayout_ID() <= 0)
			return null;

		return new POSKeyLayout(getTerminalContext(), key.getSubKeyLayout_ID());

	}

	@Override
	public Object getName()
	{
		return value.getName();
	}

	@Override
	public String getTableName()
	{
		return this.tableName;
	}

	@Override
	public KeyNamePair getValue()
	{
		return value;
	}

	@Override
	public String getText()
	{
		return key.getText();
	}
}
