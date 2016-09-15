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
import java.util.ArrayList;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_Val_Rule;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.Query;
import org.compiere.util.DisplayType;
import org.compiere.util.KeyNamePair;

import de.metas.adempiere.form.terminal.context.ITerminalContext;

/**
 * @author tsa
 * 
 */
public class TableRefKeyLayout extends KeyLayout
{
	public static final Color DEFAULT_Color = Color.GRAY;

	private int adReferenceId;
	private String validationCode = "";

	public TableRefKeyLayout(ITerminalContext tc, int AD_Reference_ID)
	{
		this(tc, AD_Reference_ID, -1);
	}
	public TableRefKeyLayout(ITerminalContext tc, int AD_Reference_ID, int AD_Val_Rule_ID)
	{
		super(tc);
		setColumns(4);
		setDefaultColor(DEFAULT_Color);
		
		this.adReferenceId = AD_Reference_ID;
		setValidationCode(AD_Val_Rule_ID);
		
	}
	
	private void setValidationCode(int AD_Val_Rule_ID)
	{
		if (AD_Val_Rule_ID <= 0)
		{
			this.validationCode = "";
			return;
		}
		I_AD_Val_Rule valRule = new Query(getCtx(), I_AD_Val_Rule.Table_Name, I_AD_Val_Rule.COLUMNNAME_AD_Val_Rule_ID+"=?", null)
		.setParameters(AD_Val_Rule_ID)
		.firstOnly(I_AD_Val_Rule.class);
		
		this.validationCode = valRule.getCode(); //Env.parseContext(getCtx(), getTerminalContext().getWindowNo(), valRule.getCode(), false);
	}

	@Override
	public String getId()
	{
		return getClass().getCanonicalName();
	}

	@Override
	protected List<ITerminalKey> createKeys()
	{
		final MLookup lookup;
		try
		{
			lookup = MLookupFactory.get(getCtx(),
					getTerminalContext().getWindowNo(), // WindowNo,
					0, // Column_ID,
					DisplayType.Table, // AD_Reference_ID,
					"Record_ID", // ColumnName,
					adReferenceId, // AD_Reference_Value_ID,
					false, // IsParent,
					validationCode);
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}

		List<ITerminalKey> list = new ArrayList<ITerminalKey>();
		final String tableName = lookup.getTableName();
		List<Object> values = lookup.getData(true, true, true, false);
		for (Object v : values)
		{
			KeyNamePair knp = (KeyNamePair)v;
			ITerminalKey terminalKey = new TableRefTerminalKey(getTerminalContext(), tableName, knp);
			list.add(terminalKey);
		}

		return list;
	}

	@Override
	public boolean isNumeric()
	{
		return false;
	}

	@Override
	public boolean isText()
	{
		return false;
	}
}
