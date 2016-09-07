package de.metas.adempiere.ait.helper;

/*
 * #%L
 * de.metas.adempiere.ait
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


import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.GridTab.DataNewCopyMode;
import org.compiere.model.GridTabMaxRows;
import org.compiere.model.GridWindow;
import org.compiere.model.Lookup;
import org.compiere.model.MQuery;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.compiere.util.NamePair;
import org.junit.Assert;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.logging.MetasfreshLastError;

public class GridWindowHelper
{
	protected final Logger logger = LogManager.getLogger(getClass());

	private final IHelper helper;
	private GridWindow gridWindow;

	private GridTab currentGridTab;

	public GridWindowHelper(IHelper helper)
	{
		this.helper = helper;
	}

	public GridWindowHelper openFor(Object poModel)
	{
		Assert.assertNull("Another window already opened - " + gridWindow, gridWindow);

		logger.info("PO=" + poModel);
		final PO po = InterfaceWrapperHelper.getPO(poModel);
		final String tableName = po.get_TableName();
		String keyColumn = po.get_KeyColumns()[0];
		final int recordId = po.get_ID();

		// figure out whether we has IsSOTrx=Y
		final boolean soTrx;
		if (po.get_ColumnIndex("IsSOTrx") > -1)
		{
			soTrx = po.get_ValueAsBoolean("IsSOTrx");
		}
		else
		{
			soTrx = true;
		}

		currentGridTab = loadWindow(tableName, soTrx);

		final MQuery query = MQuery.getEqualQuery(keyColumn, recordId);
		logger.info("Query=" + query);
		currentGridTab.setQuery(query);

		currentGridTab.query(false);
		Assert.assertEquals("Invalid row count - " + currentGridTab, 1, currentGridTab.getRowCount());

		currentGridTab.setCurrentRow(0);
		Assert.assertEquals("Wrong record selected on " + currentGridTab, recordId, currentGridTab.getValue(keyColumn));

		return this;
	}

	public GridWindowHelper openForGridTab(GridTab gridTab)
	{
		Assert.assertNotNull("gridTab is null", gridTab);
		this.gridWindow = gridTab.getGridWindow();
		this.currentGridTab = gridTab;

		return this;
	}

	public GridWindowHelper openTabForTable(final String tableName, final boolean isSOTrx)
	{
		return openTabForTable(tableName, isSOTrx, null);
	}

	public GridWindowHelper openTabForTable(final String tableName, final boolean isSOTrx, MQuery query)
	{
		loadWindow(tableName, isSOTrx);

		currentGridTab.setQuery(query);

		// onlyCurrentRows = false
		// we don't want to have OutOfMemoryErrors e.g. when opening a table with 800.000 C_OLCands,
		// but still there could be stuff like commission contracts to test against
		// so, pls when opening a table you know to contain bazillions of records, take other measures..(btw, setting max)
		// maybe this method should not be used with a null query.. 
		currentGridTab.query(false, 0, GridTabMaxRows.NO_RESTRICTION); 
		currentGridTab.setCurrentRow(0);

		return this;
	}

	private GridTab loadWindow(final String tableName, final boolean isSOTrx)
	{
		final int AD_Window_ID;
		if (isSOTrx)
		{
			AD_Window_ID = MTable.get(helper.getCtx(), tableName).getAD_Window_ID();
			assertTrue("Expected table " + tableName + " to have an AD_Window_ID", AD_Window_ID > 0);
		}
		else
		{
			AD_Window_ID = MTable.get(helper.getCtx(), tableName).getPO_Window_ID();
			assertTrue("Expected table " + tableName + " to have a PO_Window_ID", AD_Window_ID > 0);
		}
		logger.info("AD_Window_ID=" + AD_Window_ID);

		final int windowNo = Env.createWindowNo(null);
		this.gridWindow = GridWindow.get(helper.getCtx(), windowNo, AD_Window_ID, false);
		if (gridWindow.isSOTrx())
			Env.setContext(Env.getCtx(), windowNo, "IsSOTrx", gridWindow.isSOTrx());
		assertThat("Can not load window AD_Window_ID=" + AD_Window_ID, gridWindow, notNullValue());
		logger.info("GridWindow=" + gridWindow);

		final GridTab gridTab = gridWindow.getTab(0);
		Assert.assertEquals("PO and GridTab tableNames don't match", tableName, gridTab.get_TableName());
		logger.info("GridTab=" + gridTab);

		initTab(gridTab);

		this.currentGridTab = gridTab;

		return gridTab;
	}

	private void initTab(GridTab gridTab)
	{
		Assert.assertNotNull("gridWindow is null", gridWindow);

		gridWindow.initTab(gridTab.getTabNo());
		Assert.assertTrue("GridTab " + gridTab + " should be loaded", gridTab.isLoadComplete());

		int tabLevelActual = Env.getContextAsInt(helper.getCtx(), gridTab.getWindowNo(), gridTab.getTabNo(), GridTab.CTX_TabLevel);
		Assert.assertEquals("TabLevel is not correctly set in context", gridTab.getTabLevel(), tabLevelActual);
	}

	public GridWindowHelper save()
	{
		Assert.assertNotNull("No current grid tab selected", currentGridTab);

		final GridTab gridTab = currentGridTab;
		final String keyColumn = gridTab.getKeyColumnName();
		Assert.assertFalse("No key column found for " + gridTab, Check.isEmpty(keyColumn, true));

		// We save the GridTab because else, even if there is no change it will return false
		boolean manualCmd = false;
		
		boolean ok = gridTab.dataSave(manualCmd);
		
		Assert.assertTrue("dataSave failed on " + gridTab + " with error " + MetasfreshLastError.retrieveError(), ok);

		// If manualCmd=false, the field is not automatically refreshed, so we need to force this
		gridTab.setCurrentRow(gridTab.getCurrentRow());
		gridTab.dataRefresh();
		
		Assert.assertNotNull("New " + keyColumn + " not set", gridTab.getValue(keyColumn));
		final int newRecordId = (Integer)gridTab.getValue(keyColumn);
		Assert.assertNotNull("New " + keyColumn + " not set", newRecordId > 0);

		return this;
	}

	public GridTab getGridTab()
	{
		return currentGridTab;
	}

	public <T> T getGridTabInterface(Class<T> interfaceClass)
	{
		Assert.assertNotNull("No current grid tab selected", currentGridTab);
		return InterfaceWrapperHelper.create(currentGridTab, interfaceClass);
	}

	public GridWindowHelper selectTab(String tableName)
	{
		return selectTab(tableName, null);
	}

	/**
	 * Find grid tab by table name and a piece of its whereClause. Use this method if a window contains two or more tabs
	 * with the same table.
	 * 
	 * @param tableName
	 * @param whereClauseFragment
	 *            may be <code>null</code>. If set, then the tab to select must have a where clause that contains the
	 *            given string.
	 * @return
	 */
	public GridWindowHelper selectTab(final String tableName, final String whereClauseFragment)
	{
		Assert.assertNotNull("tableName is null", tableName);

		Assert.assertNotNull("gridWindow is null", gridWindow);

		for (int i = 0; i < gridWindow.getTabCount(); i++)
		{
			GridTab gridTab = gridWindow.getTab(i);
			if (!gridTab.get_TableName().equals(tableName))
			{
				continue;
			}

			if (Check.isEmpty(whereClauseFragment) || gridTab.getWhereClause().contains(whereClauseFragment))
			{
				return selectTab(gridTab);
			}
		}

		Assert.fail("No gridTab found for " + tableName);
		return null;
	}

	public GridWindowHelper selectTab(GridTab gridTab)
	{
		if (this.currentGridTab == gridTab)
		{
			// already selected => nothing to do
			return this;
		}

		initTab(gridTab);

		gridTab.query(false);
		gridTab.setCurrentRow(0);

		this.currentGridTab = gridTab;

		return this;
	}

	public GridWindowHelper newRecord()
	{
		Assert.assertNotNull("No current grid tab selected", currentGridTab);

		boolean ok = currentGridTab.dataNew(DataNewCopyMode.NoCopy);
		Assert.assertTrue("Error creating a new record", ok);
		return this;
	}

	private GridField getGridField(String columnName)
	{
		Assert.assertNotNull("No current grid tab selected", currentGridTab);

		GridField field = currentGridTab.getField(columnName);
		Assert.assertNotNull("No field found for " + columnName + " in " + currentGridTab, field);

		return field;
	}

	public GridWindowHelper assertFieldDisplayed(String columnName, boolean displayed)
	{
		GridField field = getGridField(columnName);
		boolean displayedActual = field.isDisplayed(true);
		Assert.assertEquals("Field " + field + " has invalid display status", displayed, displayedActual);

		return this;
	}

	public GridWindowHelper assertReadOnly(String columnName, boolean isReadOnly)
	{
		GridField field = getGridField(columnName);
		boolean readOnlyActual = !field.isEditable(true);
		Assert.assertEquals("Field " + field + " has invalid readonly status", isReadOnly, readOnlyActual);

		return this;
	}

	public int getRecordId()
	{
		Assert.assertNotNull("No current grid tab selected", currentGridTab);
		Object value = currentGridTab.getValue(currentGridTab.getKeyColumnName());
		if (value == null)
			return -1;
		return ((Number)value).intValue();
	}

	public GridWindowHelper assertRecordDisplayed(int recordId, boolean displayed)
	{
		Assert.assertNotNull("No current grid tab selected", currentGridTab);
		boolean found = false;
		for (int id : currentGridTab.getKeyIDs())
		{
			if (id == recordId)
			{
				found = true;
			}
		}

		Assert.assertTrue(displayed ? "Record " + recordId + " doesn't exist in the curent search" : "Record " + recordId + " exists in the curent search", found == displayed);
		return this;
	}

	/**
	 * Refresh current record
	 */
	public GridWindowHelper refresh()
	{
		Assert.assertNotNull("No current grid tab selected", currentGridTab);
		currentGridTab.dataRefresh();
		return this;
	}

	/**
	 * Refresh all rows
	 */
	public GridWindowHelper refreshAllRows()
	{
		Assert.assertNotNull("No current grid tab selected", currentGridTab);
		currentGridTab.dataRefreshAll();
		return this;
	}

	public GridWindowHelper selectRecordById(int recordId)
	{currentGridTab.dataRefreshAll();
		Assert.assertTrue("Invalid recordId: " + recordId, recordId >= 0);
		Assert.assertNotNull("No current grid tab selected", currentGridTab);
		int count = currentGridTab.getRowCount();
		for (int row = 0; row < count; row++)
		{
			int id = currentGridTab.getKeyID(row);
			if (recordId == id)
			{
				currentGridTab.setCurrentRow(row);
				return this;
			}
		}

		Assert.fail("No record was found for id " + recordId + " in " + currentGridTab);
		return this;
	}

	public GridWindowHelper setTabWhereClause(final String whereClause)
	{
		Assert.assertTrue("Where clause is not null: " + whereClause, whereClause != null);
		Assert.assertNotNull("No current grid tab selected", currentGridTab);
		
		MQuery  query = new MQuery(currentGridTab.get_TableName());
		query.addRestriction(whereClause);
		currentGridTab.setQuery(query);
		currentGridTab.query(true);
		currentGridTab.setCurrentRow(0);
		return this;
	}

	public ProcessHelper mkProcessHelper(String columnName)
	{
		Assert.assertNotNull("No current grid tab selected", currentGridTab);

		GridField field = currentGridTab.getField(columnName);
		Assert.assertNotNull("ColumnName " + columnName + " not found in " + currentGridTab, currentGridTab);

		final int processId = field.getAD_Process_ID();
		Assert.assertTrue("No AD_Process_ID found for " + columnName + " on " + currentGridTab, processId > 0);

		return helper.mkProcessHelper()
				.setProcessId(processId)
				.setPO(currentGridTab)
				.addListener(new IProcessHelperListener()
				{

					@Override
					public void beforeRun(ProcessHelper process)
					{
						Assert.assertNotNull("No current grid tab selected", currentGridTab);

						save(); // make sure is saved before we are running the process
					}

					@Override
					public void afterRun(ProcessHelper process)
					{
						refresh(); // refresh the grid after running the process
					}
				});
	}

	public GridWindowHelper runProcess(String columnName)
	{
		Assert.assertNotNull("No current grid tab selected", currentGridTab);

		mkProcessHelper(columnName)
				.run();

		return this;
	}

	public GridWindowHelper validateLookups()
	{
		Assert.assertNotNull("No current grid tab selected", currentGridTab);

		for (GridField field : currentGridTab.getFields())
		{
			if (!field.isLookup())
				continue;

			Lookup lookup = field.getLookup();
			if (!field.isDisplayable() && lookup == null)
			{
				// if the field is not displayed, no lookup is loaded
				// (with some exceptions, check org.compiere.model.GridField.loadLookup())
				continue;
			}
			Assert.assertNotNull("Field " + field + " has no lookup (displayType=" + field.getDisplayType() + ", isKey=" + field.isKey() + ")", lookup);

			Object key = field.getValue();
			if (key == null)
				continue;

			NamePair value = lookup.get(key);
			Assert.assertNotNull("Value " + key + " is not valid on " + field, value);
		}

		return this;
	}

}
