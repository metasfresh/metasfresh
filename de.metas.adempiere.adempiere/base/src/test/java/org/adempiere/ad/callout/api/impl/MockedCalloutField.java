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
import java.util.concurrent.atomic.AtomicInteger;

import org.adempiere.ad.callout.api.ICalloutExecutor;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_ColumnCallout;
import org.compiere.model.I_AD_Table;
import org.compiere.util.Env;
import org.compiere.util.ValueNamePair;
import org.junit.Assert;
import org.junit.Ignore;

@Ignore
public class MockedCalloutField implements ICalloutField
{
	public static MockedCalloutField createNewField()
	{
		final String tableName = "MockedTableName" + nextTableIndex.getAndIncrement();
		final String columnName = "MockedColumnName" + nextColumnIndex.getAndIncrement();
		return createNewField(tableName, columnName);
	}

	private static final AtomicInteger nextTableIndex = new AtomicInteger(1);
	private static final AtomicInteger nextColumnIndex = new AtomicInteger(1);

	public static MockedCalloutField createNewField(final String tableName, final String columnName)
	{
		final Properties ctx = Env.getCtx();
		final I_AD_Table adTable = getCreateAD_Table(ctx, tableName);
		final int adTableId = adTable.getAD_Table_ID();

		final int adColumnId = getCreateAD_Column_ID(ctx, adTableId, columnName);
		
		final MockedCalloutField field = new MockedCalloutField();
		field.setCtx(ctx);
		field.setAD_Table_ID(adTableId);
		field.setColumnName(columnName);
		field.setAD_Column_ID(adColumnId);
		return field;
	}
	
	private static final I_AD_Table getCreateAD_Table(final Properties ctx, final String tableName)
	{
		I_AD_Table adTable = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Table.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_AD_Table.COLUMN_TableName, tableName)
				.create()
				.firstOnly(I_AD_Table.class);
		if(adTable == null)
		{
			// Get the AD_Table_ID because it might be allocated even if there is NO AD_Table record!
			final int tableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);

			adTable = InterfaceWrapperHelper.create(ctx, I_AD_Table.class, ITrx.TRXNAME_None);
			adTable.setTableName(tableName);
			adTable.setName(tableName);
			adTable.setAD_Table_ID(tableId);
			InterfaceWrapperHelper.save(adTable);
		}
		return adTable;
	}

	private static final int getCreateAD_Column_ID(final Properties ctx, final int adTableId, final String columnName)
	{
		final int adColumnId = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Column.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_AD_Column.COLUMNNAME_AD_Table_ID, adTableId)
				.addEqualsFilter(I_AD_Column.COLUMN_ColumnName, columnName)
				.create()
				.firstIdOnly();
		if(adColumnId > 0)
		{
			return adColumnId;
		}

		//
		// Automatically create the AD_Column if is missing... there are a couple of tests which are rellying on this feature
		final IContextAware context = new PlainContextAware(ctx, ITrx.TRXNAME_None);

		final I_AD_Column adColumn = InterfaceWrapperHelper.newInstance(I_AD_Column.class, context);
		adColumn.setAD_Table_ID(adTableId);
		adColumn.setColumnName(columnName);
		InterfaceWrapperHelper.save(adColumn);
		return adColumn.getAD_Column_ID();
	}

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

	private MockedCalloutField()
	{
		super();
	}

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

	public int getAD_Table_ID()
	{
		return tableId;
	}

	@Override
	public String getTableName()
	{
		final int adTableId = getAD_Table_ID();
		return adTableId <= 0 ? null : Services.get(IADTableDAO.class).retrieveTableName(adTableId);
	}

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

	private void setCtx(final Properties ctx)
	{
		this.ctx = ctx;
	}

	public void setTriggerCalloutAllowed(final boolean triggerCalloutAllowed)
	{
		this.triggerCalloutAllowed = triggerCalloutAllowed;
	}

	private void setAD_Table_ID(final int tableId)
	{
		this.tableId = tableId;
	}

	private void setAD_Column_ID(final int columnId)
	{
		this.columnId = columnId;
	}

	private void setColumnName(final String columnName)
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
	
	@Override
	public <T> T getModelBeforeChanges(Class<T> modelClass)
	{
		final T modelConv = InterfaceWrapperHelper.createOld(model, modelClass);
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

	@Override
	public boolean isRecordCopyingModeIncludingDetails()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ICalloutExecutor getCurrentCalloutExecutor()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void fireDataStatusEEvent(String AD_Message, String info, boolean isError)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void fireDataStatusEEvent(ValueNamePair errorLog)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public ICalloutRecord getCalloutRecord()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
	
	public I_AD_ColumnCallout createAD_ColumnCallout(final Class<?> calloutClass, final String methodName)
	{
		final String calloutClassName = calloutClass.getName() + "." + methodName;
		return createAD_ColumnCallout(calloutClassName);
	}

	public I_AD_ColumnCallout createAD_ColumnCallout(final String calloutClassName)
	{
		final int AD_Column_ID = getAD_Column_ID();
		Assert.assertTrue("AD_Column_ID is set for " + this, AD_Column_ID > 0);

		final I_AD_ColumnCallout cc = InterfaceWrapperHelper.create(getCtx(), I_AD_ColumnCallout.class, ITrx.TRXNAME_None);
		cc.setAD_Column_ID(AD_Column_ID);
		cc.setClassname(calloutClassName);
		cc.setSeqNo(0);
		cc.setIsActive(true);
		InterfaceWrapperHelper.save(cc);
		return cc;
	}

}
