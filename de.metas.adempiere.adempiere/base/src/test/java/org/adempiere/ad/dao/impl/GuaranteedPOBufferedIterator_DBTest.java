package org.adempiere.ad.dao.impl;

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


import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.Adempiere.RunMode;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_Test;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Database coupled test which makes sure the {@link GuaranteedPOBufferedIterator} works OK.
 *
 * @author tsa
 *
 */
@Ignore
// requires database connection
public class GuaranteedPOBufferedIterator_DBTest
{
	public static void main(final String[] args)
	{
		final GuaranteedPOBufferedIterator_DBTest test = new GuaranteedPOBufferedIterator_DBTest();

		test.setupAdempiere();

		test.test_DirectQuery_vs_GuaranteedBufferedIterator();
		test.test_DeleteRecordWhichIsInBuffer();
	}

	private void setupAdempiere()
	{
		//
		// Use hardcoded default PropertyFile if none found
		if (Check.isEmpty(System.getProperty("PropertyFile"), true))
		{
			final String propertyFile =
					new File(".").getAbsolutePath() // e.g. C:\workspaces\\de.metas.adempiere.adempiere\base\
							+ File.separator + ".." + File.separator + ".." // e.g. C:\workspaces\
							+ File.separator + "de.metas.endcustomer."
							+ File.separator + "Adempiere.properties_" + System.getProperty("user.name");
			System.out.println("Set default PropertyFile=" + propertyFile);
			System.setProperty("PropertyFile", propertyFile);
		}

		Env.getSingleAdempiereInstance().startup(RunMode.SWING_CLIENT);
	}

	/**
	 * Tests direct query vs buffered iterator results. i.e.
	 * <ul>
	 * <li>Query {@link I_AD_Table} and fetches all results
	 * <li>for same query, iterate the result using a guaranteed buffered iterator
	 * <li>compare the results and make sure they are the same
	 * </ul>
	 */
	@Test
	public void test_DirectQuery_vs_GuaranteedBufferedIterator()
	{
		System.out.println("Test: test_DirectQuery_vs_GuaranteedBufferedIterator ======================================================================");

		//
		// Define the query which we will use to test the querying with guaranteed buffered iterator
		final Properties ctx = Env.getCtx();
		final String trxName = ITrx.TRXNAME_None;

		final IQueryBuilder<I_AD_Table> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Table.class, ctx, trxName)
				.addOnlyActiveRecordsFilter()
				.addEndsWithQueryFilter(I_AD_Table.COLUMNNAME_TableName, "Line")
				//
				.orderBy()
				.addColumn(I_AD_Table.COLUMN_TableName)
				.endOrderBy()
		//
		;
		System.out.println("Query: " + queryBuilder.create());

		//
		// Execute the query directly and build up the expectations
		final List<String> expectedTableNames = new ArrayList<>();
		for (final I_AD_Table table : queryBuilder.create().list())
		{
			final String tableName = table.getTableName();
			expectedTableNames.add(tableName);
		}
		System.out.println("Expected table names size: " + expectedTableNames.size());
		System.out.println("Expected table names: " + expectedTableNames);

		//
		// Create the guaranteed buffered iterator
		final Iterator<I_AD_Table> tablesIterator = queryBuilder.create()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, true)
				.setOption(IQuery.OPTION_IteratorBufferSize, 50)
				.iterate(I_AD_Table.class);
		System.out.println("Iterator: " + tablesIterator);

		//
		// Load the table names using the iterator
		final List<String> actualTableNames = new ArrayList<>();
		while (tablesIterator.hasNext())
		{
			final I_AD_Table table = tablesIterator.next();
			final String tableName = table.getTableName();
			actualTableNames.add(tableName);
		}
		System.out.println("Actual table names size: " + actualTableNames.size());
		System.out.println("Actual table names: " + actualTableNames);

		//
		// Compare the results
		Assert.assertEquals(expectedTableNames, actualTableNames);
	}

	@Test
	public void test_DeleteRecordWhichIsInBuffer()
	{
		System.out.println("Test: test_DeleteRecordWhichIsInBuffer ======================================================================");

		//
		// Generate records
		final List<I_Test> records = new ArrayList<>();
		final List<Integer> recordIds = new ArrayList<>();
		for (int i = 1; i <= 10; i++)
		{
			final I_Test record = createRecord();
			records.add(record);
			recordIds.add(record.getTest_ID());
		}
		System.out.println("Record IDs: " + recordIds);

		//
		// Define the query which we will use to test the querying with guaranteed buffered iterator
		final Properties ctx = Env.getCtx();
		final String trxName = ITrx.TRXNAME_None;

		final IQueryBuilder<I_Test> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_Test.class, ctx, trxName)
				.addOnlyActiveRecordsFilter()
				.addInArrayOrAllFilter(I_Test.COLUMNNAME_Test_ID, recordIds)
				//
				.orderBy()
				.addColumn(I_Test.COLUMNNAME_Test_ID)
				.endOrderBy();

		//
		// Create the guaranteed buffered iterator
		final Iterator<I_Test> recordsIterator = queryBuilder.create()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, true)
				.setOption(IQuery.OPTION_IteratorBufferSize, 2) // 2 items at time
				.iterate(I_Test.class);
		System.out.println("Iterator: " + recordsIterator);

		// Delete one record, after we created the buffer
		{
			recordIds.remove(4);
			final I_Test recordToDelete = records.remove(4);

			System.out.println("Deleting " + recordToDelete);
			InterfaceWrapperHelper.delete(recordToDelete);
		}

		//
		// Fetch the records
		final List<Integer> actualRecordIds = new ArrayList<>();
		for (int currentIndex = 0; recordsIterator.hasNext(); currentIndex++)
		{
			final I_Test record = recordsIterator.next();
			System.out.println("" + currentIndex + ": " + record);

			actualRecordIds.add(record.getTest_ID());
		}

		//
		// Assert correct results
		Assert.assertEquals(recordIds, actualRecordIds);
	}

	private final I_Test createRecord()
	{
		final I_Test record = InterfaceWrapperHelper.create(Env.getCtx(), I_Test.class, ITrx.TRXNAME_None);
		record.setName("Test_" + UUID.randomUUID());
		record.setDescription("Generated by " + getClass());
		InterfaceWrapperHelper.save(record);
		return record;
	}
}
