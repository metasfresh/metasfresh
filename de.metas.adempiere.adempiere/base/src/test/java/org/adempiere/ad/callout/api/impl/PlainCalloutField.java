package org.adempiere.ad.callout.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.Properties;

import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.util.Env;
import org.junit.Ignore;

@Ignore
public class PlainCalloutField implements ICalloutField
{
	private Properties ctx = Env.getCtx();
	private boolean triggerCalloutAllowed = true;
	private int tableId = -1;
	private int columnId = -1;
	private String columnName = null;
	private Object value;
	private Object oldValue;
	private Object model;
	private int windowNo = 0;
	private int tabNo = 0;
	private boolean recordCopyingMode = false;

	@Override
	public boolean isTriggerCalloutAllowed()
	{
		return triggerCalloutAllowed;
	}

	@Override
	public Properties getCtx()
	{
		return ctx;
	}

	@Override
	public int getAD_Table_ID()
	{
		return tableId;
	}

	@Override
	public int getAD_Column_ID()
	{
		return columnId;
	}

	@Override
	public Object getValue()
	{
		return value;
	}

	@Override
	public Object getOldValue()
	{
		return oldValue;
	}

	@Override
	public String getColumnName()
	{
		return columnName;
	}

	public void setCtx(final Properties ctx)
	{
		this.ctx = ctx;
	}

	public void setTriggerCalloutAllowed(final boolean triggerCalloutAllowed)
	{
		this.triggerCalloutAllowed = triggerCalloutAllowed;
	}

	public void setAD_Table_ID(final int tableId)
	{
		this.tableId = tableId;
	}

	public void setAD_Column_ID(final int columnId)
	{
		this.columnId = columnId;
	}

	public void setColumnName(final String columnName)
	{
		this.columnName = columnName;
	}

	public void setValue(final Object value)
	{
		this.value = value;
	}

	public void setOldValue(final Object oldValue)
	{
		this.oldValue = oldValue;
	}

	@Override
	public <T> T getModel(Class<T> modelClass)
	{
		final T modelConv = InterfaceWrapperHelper.create(model, modelClass);
		Check.assumeNotNull(modelConv, "modelConv not null");
		return modelConv;
	}

	public void setModel(final Object model)
	{
		this.model = model;
	}

	@Override
	public int getWindowNo()
	{
		return windowNo;
	}

	public void setWindowNo(final int windowNo)
	{
		this.windowNo = windowNo;
	}

	@Override
	public int getTabNo()
	{
		return tabNo;
	}

	public void setTabNo(final int tabNo)
	{
		this.tabNo = tabNo;
	}

	@Override
	public boolean isRecordCopyingMode()
	{
		return recordCopyingMode;
	}

	public void setRecordCopyingMode(final boolean recordCopyingMode)
	{
		this.recordCopyingMode = recordCopyingMode;
	}
}
