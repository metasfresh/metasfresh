package org.adempiere.ad.dao.impl;

/*
 * #%L
 * de.metas.swat.ait
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


import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.IQuery.Aggregate;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_M_Cost;
import org.compiere.model.I_Test;
import org.compiere.model.MTable;
import org.compiere.model.MTest;
import org.compiere.model.POResultSet;
import org.compiere.model.Query;
import org.compiere.model.X_AD_Element;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnable;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class QueryTest
{
	@BeforeClass
	public static void init()
	{
		final String username = System.getProperty("user.name");
		System.setProperty("PropertyFile", new File(new File("../de.metas.endcustomer./"), "Adempiere.properties_" + username).getAbsolutePath());
		org.compiere.Adempiere.startup(true);

		// Cleanup T_Query_Selection table
		DB.executeUpdateEx("delete from t_query_selection", new Object[0], ITrx.TRXNAME_None);
	}

	private Properties getCtx()
	{
		return Env.getCtx();
	}

	private String getTrxName()
	{
		return ITrx.TRXNAME_None;
	}

	@Test(expected = AdempiereException.class)
	public void testQuery_NoTable() throws Exception
	{
		new Query(getCtx(), "NO_TABLE_DEFINED", null, getTrxName());
	}

	@Test
	public void testList() throws Exception
	{
		final List<MTable> list = new Query(getCtx(), "AD_Table", "TableName IN (?,?)", getTrxName())
				.setParameters(new Object[] { "C_Invoice", "M_InOut" })
				.setOrderBy("TableName")
				.list();
		assertEquals("Invalid list size", 2, list.size());
		assertEquals("Invalid object 1", list.get(0).getTableName(), "C_Invoice");
		assertEquals("Invalid object 2", list.get(1).getTableName(), "M_InOut");
	}

	@Test
	public void testScroll() throws Exception
	{
		POResultSet<MTable> rs = new Query(getCtx(), "AD_Table", "TableName IN (?,?)", getTrxName())
				.setParameters(new Object[] { "C_Invoice", "M_InOut" })
				.setOrderBy("TableName")
				.scroll();
		try
		{
			int i = 0;
			while (rs.hasNext())
			{
				MTable t = rs.next();
				if (i == 0)
				{
					assertEquals("Invalid object " + i, "C_Invoice", t.getTableName());
				}
				else if (i == 1)
				{
					assertEquals("Invalid object " + i, "M_InOut", t.getTableName());
				}
				else
				{
					assertFalse("More objects retrived than expected", true);
				}
				i++;
			}
		}
		finally
		{
			DB.close(rs);
			rs = null;
		}

	}

	@Test
	public void testIterate() throws Exception
	{
		final Iterator<MTable> it = new Query(getCtx(), "AD_Table", "TableName IN (?,?)", getTrxName())
				.setParameters(new Object[] { "C_Invoice", "M_InOut" })
				.setOrderBy("TableName")
				.iterate(null, false); // guaranteed=false
		int i = 0;
		while (it.hasNext())
		{
			MTable t = it.next();
			if (i == 0)
			{
				assertEquals("Invalid object " + i, "C_Invoice", t.getTableName());
			}
			else if (i == 1)
			{
				assertEquals("Invalid object " + i, "M_InOut", t.getTableName());
			}
			else
			{
				assertFalse("More objects retrived than expected", true);
			}
			i++;
		}

	}

	@Test
	public void testCount() throws Exception
	{
		int count = new Query(getCtx(), "AD_Table", "TableName IN (?,?)", getTrxName())
				.setParameters(new Object[] { "C_Invoice", "M_InOut" })
				.setOrderBy("TableName")
				.count();
		assertEquals("Invalid count", 2, count);
	}

	@Test(expected = DBException.class)
	public void testCount_BadSQL() throws Exception
	{
		new Query(getCtx(), "AD_Table", "TableName IN (?,?) AND BAD_SQL", getTrxName())
				.setParameters(new Object[] { "C_Invoice", "M_InOut" })
				.setOrderBy("TableName")
				.count();
	}

	@Test
	public void testCount_NoValues() throws Exception
	{
		int count = new Query(getCtx(), "AD_Table", "1=2", getTrxName()).count();
		assertEquals("Counter should be ZERO", 0, count);
	}

	@Test
	public void testFirst() throws Exception
	{
		MTable t = new Query(getCtx(), "AD_Table", "TableName IN (?,?)", getTrxName())
				.setParameters(new Object[] { "C_Invoice", "M_InOut" })
				.setOrderBy("TableName")
				.first();
		assertEquals("Invalid object", "C_Invoice", t.getTableName());
	}

	@Test
	public void testFirstId() throws Exception
	{
		int id = new Query(getCtx(), "AD_Table", "TableName IN (?,?)", getTrxName())
				.setParameters(new Object[] { "C_Invoice", "M_InOut" })
				.setOrderBy("TableName")
				.firstId();
		int expectedId = 318; // C_Invoice
		assertEquals("Invalid ID", expectedId, id);
	}

	@Test
	public void testFirstOnly() throws Exception
	{
		MTable t = new Query(getCtx(), "AD_Table", "AD_Table_ID=?", getTrxName())
				.setParameters(new Object[] { 318 })
				.firstOnly();
		assertEquals("Invalid table ID", 318, t.get_ID());
	}

	@Test(expected = DBException.class)
	public void testFirstOnly_Exception() throws Exception
	{
		new Query(getCtx(), "AD_Table", "TableName IN (?,?)", getTrxName())
				.setParameters(new Object[] { "C_Invoice", "M_InOut" })
				.setOrderBy("TableName")
				.firstOnly();
	}

	@Test
	public void testFirstIdOnly() throws Exception
	{
		int expectedId = 318; // C_Invoice
		int id = new Query(getCtx(), "AD_Table", "AD_Table_ID=?", getTrxName())
				.setParameters(new Object[] { expectedId })
				.firstIdOnly();
		assertEquals("Invalid table ID", expectedId, id);
	}

	@Test(expected = DBException.class)
	public void testFirstIdOnly_Exception() throws Exception
	{
		new Query(getCtx(), "AD_Table", "TableName IN (?,?)", getTrxName())
				.setParameters(new Object[] { "C_Invoice", "M_InOut" })
				.setOrderBy("TableName")
				.firstIdOnly();
	}

	@Test
	public void testSetClient_ID() throws Exception
	{
		int AD_Client_ID = Env.getAD_Client_ID(getCtx());
		String sql = "SELECT COUNT(*) FROM C_Invoice WHERE IsActive='Y' AND AD_Client_ID=" + AD_Client_ID;
		int targetCount = DB.getSQLValue(null, sql);
		//
		int count = new Query(getCtx(), "C_Invoice", "1=1", getTrxName())
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.count();
		assertEquals("Invoice # not match", targetCount, count);
	}

	@Test
	public void testGet_IDs() throws Exception
	{
		final String whereClause = "AD_Element_ID IN (101, 102)";
		int[] ids = new Query(getCtx(), "AD_Element", whereClause, getTrxName())
				.setOrderBy("AD_Element_ID")
				.getIDs();
		assertNotNull(ids);
		assertEquals(2, ids.length);
		assertEquals(101, ids[0]);
		assertEquals(102, ids[1]);
	}

	@Test
	public void testAggregate() throws Exception
	{
		final String sqlFrom = "FROM C_InvoiceLine WHERE IsActive='Y'";
		final Query query = new Query(getCtx(), "C_InvoiceLine", null, getTrxName())
				.setOnlyActiveRecords(true);
		//
		// Test COUNT:
		assertEquals("COUNT not match",
				DB.getSQLValueBDEx(getTrxName(), "SELECT COUNT(*) " + sqlFrom),
				query.aggregate(null, Aggregate.COUNT));
		//
		// Test SUM:
		assertEquals("SUM not match",
				DB.getSQLValueBDEx(getTrxName(), "SELECT COALESCE(SUM(LineNetAmt+TaxAmt),0) " + sqlFrom),
				query.aggregate("LineNetAmt+TaxAmt", Query.Aggregate.SUM));
		//
		// Test MIN:
		assertEquals("MIN not match",
				DB.getSQLValueBDEx(getTrxName(), "SELECT MIN(LineNetAmt) " + sqlFrom),
				query.aggregate("LineNetAmt", Aggregate.MIN));
		//
		// Test MAX:
		assertEquals("MAX not match",
				DB.getSQLValueBDEx(getTrxName(), "SELECT MAX(LineNetAmt) " + sqlFrom),
				query.aggregate("LineNetAmt", Aggregate.MAX));
		//
		// Test aggregate (String) - FR [ 2726447 ]
		assertEquals("MAX not match (String)",
				DB.getSQLValueStringEx(getTrxName(), "SELECT MAX(Description) " + sqlFrom),
				query.aggregate("Description", Aggregate.MAX, String.class));
		//
		// Test aggregate (Timestamp) - FR [ 2726447 ]
		assertEquals("MAX not match (Timestamp)",
				DB.getSQLValueTSEx(getTrxName(), "SELECT MAX(Updated) " + sqlFrom),
				query.aggregate("Updated", Aggregate.MAX, Timestamp.class));
	}

	/**
	 * Test Exception : No Aggregate Function defined
	 *
	 * @throws Exception
	 */
	@Test(expected = DBException.class)
	public void testAggregate_NoAggregateFunctionDefined() throws Exception
	{
		new Query(getCtx(), "C_InvoiceLine", null, getTrxName())
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.aggregate("*", null);
	}

	/**
	 * Test Exception : No Expression defined
	 *
	 * @throws Exception
	 */
	@Test(expected = DBException.class)
	public void testAggregate_SUM_NoExpressionDefined() throws Exception
	{
		new Query(getCtx(), "C_InvoiceLine", null, getTrxName())
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.aggregate(null, Query.Aggregate.SUM);
	}

	@Test
	public void testOnlySelection() throws Exception
	{
		// Get one AD_PInstance_ID
		int AD_PInstance_ID = DB.getSQLValueEx(null, "SELECT MAX(AD_PInstance_ID) FROM AD_PInstance");
		assertTrue(AD_PInstance_ID > 0);

		// Create selection list
		List<Integer> elements = new ArrayList<Integer>();
		elements.add(102); // AD_Element_ID=102 => AD_Client_ID
		elements.add(104); // AD_Element_ID=104 => AD_Column_ID
		DB.executeUpdateEx("DELETE FROM T_Selection WHERE AD_PInstance_ID=" + AD_PInstance_ID, getTrxName());
		DB.createT_Selection(AD_PInstance_ID, elements, getTrxName());

		String whereClause = "1=1"; // some dummy where clause
		int[] ids = new Query(getCtx(), X_AD_Element.Table_Name, whereClause, getTrxName())
				.setOnlySelection(AD_PInstance_ID)
				.setOrderBy(X_AD_Element.COLUMNNAME_AD_Element_ID)
				.getIDs();
		assertEquals("Resulting number of elements differ", elements.size(), ids.length);

		for (int i = 0; i < elements.size(); i++)
		{
			int expected = elements.get(i);
			assertEquals("Element " + i + " not equals", expected, ids[i]);
		}
	}

	@Test
	public void test_GuaranteedPOBufferedIterator_simple()
	{
		final Properties ctx = Env.getCtx();
		final String trxName = ITrx.TRXNAME_None;

		final TypedSqlQuery<I_AD_Table> query = new TypedSqlQuery<I_AD_Table>(ctx, I_AD_Table.class, "TableName like ?", trxName)
				.setParameters("AD%")
				.setOrderBy(I_AD_Table.COLUMNNAME_TableName)
				.setLimit(100, 20);

		final List<I_AD_Table> expectedList = query.list(I_AD_Table.class);
		final Iterator<I_AD_Table> it = query.iterate(I_AD_Table.class, true); // guaranteed=true

		final List<I_AD_Table> actualList = getList(it);
		Assert.assertEquals("Not same list", expectedList, actualList);
	}

	@Test
	public void test_GuaranteedPOBufferedIterator_changingData() throws Exception
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final String trxName = trxManager.createTrxName("Test");
		trxManager.run(trxName, new TrxRunnable()
		{

			@Override
			public void run(final String localTrxName) throws Exception
			{
				test_GuaranteedPOBufferedIterator_changingData(localTrxName);
			}
		});

		DB.rollback(true, trxName);
	}

	private void test_GuaranteedPOBufferedIterator_changingData(final String trxName) throws Exception
	{
		final Properties ctx = Env.getCtx();

		for (int i = 1; i <= 100; i++)
		{
			createTestRecord(i, trxName);
		}

		final int iteratorBufferSize = 10;

		final TypedSqlQuery<I_Test> query = new TypedSqlQuery<I_Test>(ctx, I_Test.class, I_Test.COLUMNNAME_T_Integer + " BETWEEN ? AND ?", trxName)
				.setParameters(20, 60)
				.setOption(Query.OPTION_IteratorBufferSize, iteratorBufferSize)
				.setOrderBy(I_Test.COLUMNNAME_T_Integer);

		final List<I_Test> expectedList = query.list(I_Test.class);
		Assert.assertEquals(60 - 20 + 1, expectedList.size());
		for (int i = 0; i < expectedList.size(); i++)
		{
			final I_Test item = expectedList.get(i);
			final int expectedInt = 20 + i;
			Assert.assertEquals("Invalid item: " + item, expectedInt, item.getT_Integer());
		}

		@SuppressWarnings("unchecked")
		final POBufferedIterator<I_Test, I_Test> itNotGuaranteed = (POBufferedIterator<I_Test, I_Test>)query.iterate(I_Test.class, false);
		Assert.assertEquals("BufferSize not set for " + itNotGuaranteed, iteratorBufferSize, itNotGuaranteed.getBufferSize());

		@SuppressWarnings("unchecked")
		final GuaranteedPOBufferedIterator<I_Test, I_Test> itGuaranteed = (GuaranteedPOBufferedIterator<I_Test, I_Test>)query.iterate(I_Test.class, true);
		Assert.assertEquals("BufferSize not set for " + itGuaranteed, iteratorBufferSize, itGuaranteed.getBufferSize());

		// Change item value (which is included in where clause) and item is not in first buffer:
		{
			final I_Test item = expectedList.get(30);
			item.setT_Integer(10000 + item.getT_Integer());
			InterfaceWrapperHelper.save(item);
		}

		final List<I_Test> itGuaranteedList = getList(itGuaranteed);
		Assert.assertEquals("Guaranteed list shall not be changed", expectedList, itGuaranteedList);

		final List<I_Test> itNotGuaranteedList = getList(itNotGuaranteed);
		Assert.assertThat("Not guaranteed list shall be changed", expectedList, not(equalTo(itNotGuaranteedList)));
	}

	private <T> List<T> getList(Iterator<T> it)
	{
		final List<T> list = new ArrayList<T>();
		while (it.hasNext())
		{
			T item = it.next();
			list.add(item);
		}

		return list;
	}

	private I_Test createTestRecord(int testNo, final String trxName)
	{
		final Properties ctx = Env.getCtx();
		final MTest test = new MTest(ctx, "Test" + testNo, testNo, trxName);
		test.setT_Integer(testNo);
		test.setDescription(trxName);
		test.saveEx();
		return test;
	}

	@Test
	public void test_addWhereClause() throws Exception
	{
		final String whereClause = "TableName IN (?,?)";
		final Query query = new Query(getCtx(), "AD_Table", whereClause, getTrxName())
				.setParameters(new Object[] { "C_Invoice", "M_InOut" })
				.setOrderBy("TableName");
		{
			final I_AD_Table table = query.first(I_AD_Table.class);
			assertEquals("Invalid object", "C_Invoice", table.getTableName());
		}

		Assert.assertNotSame("Query shall be cloned", query, query.addWhereClause(true, null));
		Assert.assertNotSame("Query shall be cloned", query, query.addWhereClause(false, null));
		Assert.assertNotSame("Query shall be cloned", query, query.addWhereClause(true, ""));
		Assert.assertNotSame("Query shall be cloned", query, query.addWhereClause(true, "   "));

		{
			final String whereClause2 = "TableName='M_InOut'";
			final Query query2 = query.addWhereClause(true, whereClause2);
			Assert.assertNotSame("New query shall be created", query, query2);

			Assert.assertEquals("Invalid whereClause was build",
					"(" + whereClause + ") AND (" + whereClause2 + ")",
					query2.getWhereClause());

			final I_AD_Table table = query2.first(I_AD_Table.class);
			assertEquals("Invalid object", "M_InOut", table.getTableName());
		}

		{
			final String whereClause2 = "TableName='M_InOut'";
			final Query query2 = query.addWhereClause(false, whereClause2);
			Assert.assertNotSame("New query shall be created", query, query2);

			Assert.assertEquals("Invalid whereClause was build",
					"(" + whereClause + ") OR (" + whereClause2 + ")",
					query2.getWhereClause());

			final I_AD_Table table = query2.first(I_AD_Table.class);
			assertEquals("Invalid object", "C_Invoice", table.getTableName());
		}
	}

	@Test
	public void test_getKeyColumnName()
	{
		final TypedSqlQuery<I_C_Invoice> query = new TypedSqlQuery<I_C_Invoice>(getCtx(), I_C_Invoice.class, null, getTrxName());
		Assert.assertEquals("Invalid key column for " + query,
				"C_Invoice_ID",
				query.getKeyColumnName());
	}

	@Test(expected = DBException.class)
	public void test_getKeyColumnName_CompositePrimaryKey()
	{
		final TypedSqlQuery<I_M_Cost> query = new TypedSqlQuery<I_M_Cost>(getCtx(), I_M_Cost.class, null, getTrxName());

		// shall throw exception
		query.getKeyColumnName();
	}
}
