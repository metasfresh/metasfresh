package org.adempiere.ad.wrapper;

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


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.POWrapper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class POJOWrapperTests
{
	private PlainContextAware contextProvider;

	public static interface ITable1
	{
		String Table_Name = "Table1";

		String COLUMNNNAME_Table1_ID = "Table1_ID";

		void setTable1_ID(int value);

		int getTable1_ID();

		String COLUMNNNAME_Name = "Name";

		String getName();

		void setName(String name);

		String COLUMNNNAME_ValueInt = "ValueInt";

		int getValueInt();

		void setValueInt(int value);
	}

	public static interface ITable2
	{
		String Table_Name = "Table2";

		String COLUMNNNAME_Table2_ID = "Table2_ID";

		int getTable2_ID();

		String COLUMNNNAME_Table1_ID = "Table1_ID";

		int getTable1_ID();

		void setTable1_ID(int Table1_ID);

		ITable1 getTable1();

		void setTable1(ITable1 table1);
	}

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		POJOLookupMap.get().setCopyOnSave(true);

		// NOTE: By Default we work with strict values
		// Tests will un-set this setting if necessary
		POJOWrapper.setDefaultStrictValues(true);

		contextProvider = new PlainContextAware(Env.getCtx(), ITrx.TRXNAME_None);
	}

	@Test
	public void test_DefaultValues()
	{
		Assert.assertEquals("Invalid Default Value for Integer", POJOWrapper.DEFAULT_VALUE_int, 0);
		Assert.assertEquals("Invalid Default Value for IDs", POJOWrapper.DEFAULT_VALUE_ID, -1);
	}

	@Test
	public void test_SetGet_IDColumn()
	{
		ITable2 table2 = InterfaceWrapperHelper.newInstance(ITable2.class, contextProvider);

		table2.setTable1_ID(123);
		Assert.assertEquals("Invalid Table1_ID", 123, table2.getTable1_ID());

		InterfaceWrapperHelper.save(table2);

		//
		// Reload
		table2 = InterfaceWrapperHelper.create(contextProvider.getCtx(),
				table2.getTable2_ID(),
				ITable2.class,
				contextProvider.getTrxName());

		//
		// Revalidate
		Assert.assertEquals("Invalid Table1_ID", 123, table2.getTable1_ID());
	}

	@Test
	public void test_SetGetModel_Unsaved()
	{
		final ITable1 table1 = InterfaceWrapperHelper.newInstance(ITable1.class, contextProvider);
		ITable2 table2 = InterfaceWrapperHelper.newInstance(ITable2.class, contextProvider);

		table2.setTable1(table1);
		Assert.assertEquals("Invalid Table1_ID", -1, table2.getTable1_ID());
		Assert.assertSame("Invalid Table1", table1, table2.getTable1());
	}

	@Test
	public void test_SetGetModel_Unsaved_ResetID()
	{
		final ITable1 table1 = InterfaceWrapperHelper.newInstance(ITable1.class, contextProvider);
		ITable2 table2 = InterfaceWrapperHelper.newInstance(ITable2.class, contextProvider);

		table2.setTable1(table1);
		Assert.assertEquals("Invalid Table1_ID", -1, table2.getTable1_ID());
		Assert.assertSame("Invalid Table1", table1, table2.getTable1());

		table2.setTable1_ID(-1);
		Assert.assertEquals("Invalid Table1_ID", -1, table2.getTable1_ID());
		Assert.assertEquals("Invalid Table1", null, table2.getTable1());
	}

	@Test
	public void test_SetGetModel_Saved_ResetID()
	{
		final ITable1 table1 = InterfaceWrapperHelper.newInstance(ITable1.class, contextProvider);
		InterfaceWrapperHelper.save(table1);

		ITable2 table2 = InterfaceWrapperHelper.newInstance(ITable2.class, contextProvider);

		table2.setTable1(table1);
		Assert.assertEquals("Invalid Table1_ID", table1.getTable1_ID(), table2.getTable1_ID());
		Assert.assertSame("Invalid Table1", table1, table2.getTable1());

		table2.setTable1_ID(-1);
		Assert.assertEquals("Invalid Table1_ID", -1, table2.getTable1_ID());
		Assert.assertEquals("Invalid Table1", null, table2.getTable1());
	}

	@Test
	public void test_SetGetModel_Unsaved_ResetModel()
	{
		final ITable1 table1 = InterfaceWrapperHelper.newInstance(ITable1.class, contextProvider);
		ITable2 table2 = InterfaceWrapperHelper.newInstance(ITable2.class, contextProvider);

		table2.setTable1(table1);
		Assert.assertEquals("Invalid Table1_ID", -1, table2.getTable1_ID());
		Assert.assertSame("Invalid Table1", table1, table2.getTable1());

		table2.setTable1(null);
		Assert.assertEquals("Invalid Table1_ID", POJOWrapper.DEFAULT_VALUE_ID, table2.getTable1_ID());
		Assert.assertEquals("Invalid Table1", null, table2.getTable1());
	}

	@Test
	public void test_SetGetModel_Saved_ResetModel()
	{
		final ITable1 table1 = InterfaceWrapperHelper.newInstance(ITable1.class, contextProvider);
		InterfaceWrapperHelper.save(table1);

		ITable2 table2 = InterfaceWrapperHelper.newInstance(ITable2.class, contextProvider);

		table2.setTable1(table1);
		Assert.assertEquals("Invalid Table1_ID", table1.getTable1_ID(), table2.getTable1_ID());
		Assert.assertSame("Invalid Table1", table1, table2.getTable1());

		table2.setTable1(null);
		Assert.assertEquals("Invalid Table1_ID", POJOWrapper.DEFAULT_VALUE_ID, table2.getTable1_ID());
		Assert.assertEquals("Invalid Table1", null, table2.getTable1());
	}

	@Test
	public void test_SetGetModel_Changed()
	{
		final ITable1 table1_1 = InterfaceWrapperHelper.newInstance(ITable1.class, contextProvider);
		InterfaceWrapperHelper.save(table1_1);
		final ITable1 table1_2 = InterfaceWrapperHelper.newInstance(ITable1.class, contextProvider);
		InterfaceWrapperHelper.save(table1_2);

		ITable2 table2 = InterfaceWrapperHelper.newInstance(ITable2.class, contextProvider);

		table2.setTable1(table1_1);
		Assert.assertEquals("Invalid Table1_ID", table1_1.getTable1_ID(), table2.getTable1_ID());
		Assert.assertSame("Invalid Table1", table1_1, table2.getTable1());

		table2.setTable1_ID(-1);
		Assert.assertEquals("Invalid Table1_ID", -1, table2.getTable1_ID());
		Assert.assertNull("Invalid Table1", table2.getTable1());
	}

	@Test
	public void test_SetGetModel_ChangedAfterSet()
	{
		final ITable1 table1 = InterfaceWrapperHelper.newInstance(ITable1.class, contextProvider);
		// NOTE: we are not saving it

		final ITable2 table2 = InterfaceWrapperHelper.newInstance(ITable2.class, contextProvider);

		table2.setTable1(table1);
		Assert.assertEquals("Invalid Table1_ID", POJOWrapper.DEFAULT_VALUE_ID, table2.getTable1_ID());
		Assert.assertSame("Invalid Table1", table1, table2.getTable1());

		final ITable1 table1_saved = InterfaceWrapperHelper.newInstance(ITable1.class, contextProvider);
		InterfaceWrapperHelper.save(table1_saved);

		table2.setTable1_ID(table1_saved.getTable1_ID());
		Assert.assertEquals("Invalid Table1_ID", table1_saved.getTable1_ID(), table2.getTable1_ID());
		Assert.assertEquals("Invalid Table1 value", table1_saved, table2.getTable1());
		Assert.assertNotSame("Invalid Table1 value; shall not be same", table1_saved, table2.getTable1());
	}

	@Test
	public void test_SetGet_ValueInt()
	{
		final ITable1 table1 = InterfaceWrapperHelper.newInstance(ITable1.class, contextProvider);

		table1.setValueInt(123);
		Assert.assertEquals("Invalid ValueInt", 123, table1.getValueInt());
	}

	@Test
	public void test_SetGet_ValueInt_Default()
	{
		final ITable1 table1 = InterfaceWrapperHelper.newInstance(ITable1.class, contextProvider);
		POJOWrapper.disableStrictValues(table1);

		Assert.assertEquals("Invalid Default ValueInt", POJOWrapper.DEFAULT_VALUE_int, table1.getValueInt());
	}

	@Test
	public void setId_Again()
	{
		final ITable1 table1 = InterfaceWrapperHelper.newInstance(ITable1.class, contextProvider);
		InterfaceWrapperHelper.save(table1);

		Assert.assertTrue("ID was not set: " + table1, table1.getTable1_ID() > 0);

		// Shall throw an exception because we are not allowed to reset ID
		table1.setTable1_ID(11111);
	}

	@Test
	public void test_GetModel_CachedModelShallRefresh()
	{
		final ITable1 table1 = InterfaceWrapperHelper.newInstance(ITable1.class, contextProvider);
		table1.setValueInt(100);
		InterfaceWrapperHelper.save(table1);

		final ITable2 table2 = InterfaceWrapperHelper.newInstance(ITable2.class, contextProvider);
		table2.setTable1(table1);
		InterfaceWrapperHelper.save(table2);

		// We assume that Table1 has the right ValueInt
		Assert.assertEquals("Table1 shall be refreshed before returned",
				100, // expected
				table2.getTable1().getValueInt());

		final ITable1 table1_reloaded = InterfaceWrapperHelper.create(Env.getCtx(), table1.getTable1_ID(), ITable1.class, ITrx.TRXNAME_None);
		Assert.assertEquals("Invalid Table1 ValueInt", 100, table1_reloaded.getValueInt());

		table1_reloaded.setValueInt(123);
		InterfaceWrapperHelper.save(table1_reloaded);

		// We assume that Table1 is refreshed before retrieved even if it was cached
		Assert.assertEquals("Table1 shall be refreshed before returned",
				123, // expected
				table2.getTable1().getValueInt());
	}

	/**
	 * Making sure that if {@link POJOWrapper#refresh(Object, String)} is called, then the pojo's <code>trxName</code> is updated.
	 * 
	 * We need this in order to make the {@link POJOWrapper} behave similar to the {@link POWrapper},
	 * when they are called by {@link InterfaceWrapperHelper#refresh(Object, String)}.
	 */
	@Test
	public void testRefreshSetsTrxName()
	{
		final I_C_BPartner bp = POJOWrapper.create(Env.getCtx(), I_C_BPartner.class);
		InterfaceWrapperHelper.save(bp);
		assertNull(POJOWrapper.getTrxName(bp)); // guard

		final boolean discardChanges = false;
		POJOWrapper.refresh(bp, discardChanges, "myTrxName");
		assertEquals("myTrxName", POJOWrapper.getTrxName(bp));
	}

}
