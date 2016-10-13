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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.collections.ListUtils;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.I_M_Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class QueryBuilderTests
{
	private I_M_Product product0;
	private I_M_Product product1_NotActive;
	private I_M_Product product2;
	/** all products */
	private List<I_M_Product> products;
	private Properties ctx;

	/**
	 * Sets up two POJOs with different AD_Client_IDs.
	 */
	@Before
	public void beforeTest()
	{
		AdempiereTestHelper.get().init();

		ctx = new Properties();

		products = new ArrayList<I_M_Product>();

		ctx.setProperty("#AD_Client_ID", "99");
		product0 = InterfaceWrapperHelper.create(ctx, I_M_Product.class, ITrx.TRXNAME_None);
		product0.setIsActive(true);
		InterfaceWrapperHelper.save(product0);
		products.add(product0);

		ctx.setProperty("#AD_Client_ID", "100");
		product1_NotActive = InterfaceWrapperHelper.create(ctx, I_M_Product.class, ITrx.TRXNAME_None);
		product1_NotActive.setIsActive(false);
		InterfaceWrapperHelper.save(product1_NotActive);
		products.add(product1_NotActive);

		ctx.setProperty("#AD_Client_ID", "100");
		product2 = InterfaceWrapperHelper.create(ctx, I_M_Product.class, ITrx.TRXNAME_None);
		product2.setIsActive(true);
		InterfaceWrapperHelper.save(product2);
		products.add(product2);

		assertThat(product0.getAD_Client_ID(), is(99));
		assertThat(product1_NotActive.getAD_Client_ID(), is(100));
		assertThat(product2.getAD_Client_ID(), is(100));
	}

	@Test
	public void testFilterByClientId_setCtx_First()
	{
		assertThat(product1_NotActive, notNullValue()); // simple guard;

		// Configure the products
		for (I_M_Product p : products)
		{
			p.setIsActive(true);
			POJOWrapper.getWrapper(p).setValue("AD_Client_ID", 99999);
			InterfaceWrapperHelper.save(p);
		}
		ctx.setProperty("#AD_Client_ID", "12345");
		product1_NotActive.setIsActive(true);
		POJOWrapper.getWrapper(product1_NotActive).setValue("AD_Client_ID", 12345);
		InterfaceWrapperHelper.save(product1_NotActive);

		final IQueryBuilder<I_M_Product> builder = new QueryBuilder<I_M_Product>(I_M_Product.class, null) // tableName=null
				.setContext(ctx, ITrx.TRXNAME_None)
				.filterByClientId();

		final IQuery<I_M_Product> query = builder.create();

		assertThat(query.firstOnly(I_M_Product.class), is(product1_NotActive));
	}

	@Test
	public void testFilterByClientId_setCtx_Last()
	{
		assertThat(product1_NotActive, notNullValue()); // simple guard;

		// Configure the products
		for (I_M_Product p : products)
		{
			p.setIsActive(true);
			POJOWrapper.getWrapper(p).setValue("AD_Client_ID", 99999);
			InterfaceWrapperHelper.save(p);
		}
		ctx.setProperty("#AD_Client_ID", "12345");
		product1_NotActive.setIsActive(true);
		POJOWrapper.getWrapper(product1_NotActive).setValue("AD_Client_ID", 12345);
		InterfaceWrapperHelper.save(product1_NotActive);

		final IQueryBuilder<I_M_Product> builder = new QueryBuilder<I_M_Product>(I_M_Product.class, null) // tableName=null
				.setContext(ctx, ITrx.TRXNAME_None)
				.filterByClientId();

		final IQuery<I_M_Product> query = builder.create();

		assertThat(query.firstOnly(I_M_Product.class), is(product1_NotActive));
	}

	/**
	 * Even if we call {@link QueryBuilder#filter(org.adempiere.ad.dao.IQueryFilter)}, it needs to detect the clientID filter.
	 */
	@Test
	public void testFilter_with_ClientID_Filter()
	{
		assertThat(product1_NotActive, notNullValue()); // simple guard;

		// Configure the products
		for (I_M_Product p : products)
		{
			p.setIsActive(true);
			POJOWrapper.getWrapper(p).setValue("AD_Client_ID", 99999);
			InterfaceWrapperHelper.save(p);
		}
		ctx.setProperty("#AD_Client_ID", "12345");
		product1_NotActive.setIsActive(true);
		POJOWrapper.getWrapper(product1_NotActive).setValue("AD_Client_ID", 12345);
		InterfaceWrapperHelper.save(product1_NotActive);

		final IQueryBuilder<I_M_Product> builder = new QueryBuilder<I_M_Product>(I_M_Product.class, null) // tableName=null
				.setContext(ctx, ITrx.TRXNAME_None)
				.filter(new ContextClientQueryFilter<I_M_Product>());

		final IQuery<I_M_Product> query = builder.create();

		assertThat(query.firstOnly(I_M_Product.class), is(product1_NotActive));
	}

	@Test
	public void testSetOnlyActiveRecords()
	{
		assertThat(product0, notNullValue()); // simple guard;
		assertThat(product1_NotActive, notNullValue()); // simple guard;
		assertThat(product2, notNullValue()); // simple guard;

		final IQueryBuilder<I_M_Product> builder = new QueryBuilder<I_M_Product>(I_M_Product.class, null); // tableName=null

		//
		// Retrieve active records and test
		final IQuery<I_M_Product> query = builder.create()
				.setOnlyActiveRecords(true);
		{
			final List<I_M_Product> list = query.list(I_M_Product.class);
			assertThat(list.size(), is(2));

			assertThat(list.get(0), is(product0));
			assertThat(product0.isActive(), is(true));

			assertThat(list.get(1), is(product2));
			assertThat(product2.isActive(), is(true));
		}

		//
		// Retrieve all records and test
		{
			final List<I_M_Product> list = query
					.setOnlyActiveRecords(false)
					.list(I_M_Product.class);
			assertThat(list.size(), is(3));

			assertThat(list.get(0), is(product0));
			assertThat(product0.isActive(), is(true));

			assertThat(list.get(1), is(product1_NotActive));
			assertThat(product1_NotActive.isActive(), is(false));

			assertThat(list.get(2), is(product2));
			assertThat(product2.isActive(), is(true));
		}
	}

	@Test
	public void test_Query_SetOnlySelection()
	{
		assertThat(product0, notNullValue()); // simple guard;
		assertThat(product1_NotActive, notNullValue()); // simple guard;
		assertThat(product2, notNullValue()); // simple guard;

		// Create selection containing product1 and product2
		final I_AD_PInstance adPInstance = POJOLookupMap.get().createSelectionFromModels(product1_NotActive, product2);
		final int selectionId = adPInstance.getAD_PInstance_ID();

		final IQueryBuilder<I_M_Product> builder = new QueryBuilder<I_M_Product>(I_M_Product.class, null); // tableName=null)
		final IQuery<I_M_Product> query = builder.create();

		//
		// Query selection
		{
			query.setOnlySelection(selectionId);
			final List<I_M_Product> list = query.list();
			assertThat(list.size(), is(2));

			assertThat(list.get(0), is(product1_NotActive));
			assertThat(list.get(1), is(product2));
		}

		//
		// Reset query's selection and query again
		{
			query.setOnlySelection(-1);
			final List<I_M_Product> list = query.list();
			assertThat(list.size(), is(3));

			assertThat(list.get(0), is(product0));
			assertThat(list.get(1), is(product1_NotActive));
			assertThat(list.get(2), is(product2));
		}
	}

	@Test
	public void test_SetOnlySelection()
	{
		assertThat(product0, notNullValue()); // simple guard;
		assertThat(product1_NotActive, notNullValue()); // simple guard;
		assertThat(product2, notNullValue()); // simple guard;

		// Create selection containing product1 and product2
		final I_AD_PInstance adPInstance = POJOLookupMap.get().createSelectionFromModels(product1_NotActive, product2);
		final int selectionId = adPInstance.getAD_PInstance_ID();

		// Query selection and test
		final List<I_M_Product> list = new QueryBuilder<I_M_Product>(I_M_Product.class, null) // tableName=null
				.setOnlySelection(selectionId)
				.create()
				.list();
		assertThat(list.size(), is(2));
		assertThat(list.get(0), is(product1_NotActive));
		assertThat(list.get(1), is(product2));
	}

	/**
	 * NOTE: this test won't make sure that the {@link IQueryBuilder#OPTION_Explode_OR_Joins_To_SQL_Unions} will work on actual database, but it will just check if it works with in-memory database
	 */
	@Test
	public void test_Explode_OR_Joins_To_SQL_Unions()
	{
		final IQueryBuilder<I_M_Product> queryBuilder = new QueryBuilder<>(I_M_Product.class, null) // tableName=null
				.setJoinOr()
				.setOption(IQueryBuilder.OPTION_Explode_OR_Joins_To_SQL_Unions);

		// Add the same filter, several times
		final int filtersCount = 10;
		for (int i = 1; i <= filtersCount; i++)
		{
			queryBuilder.addEqualsFilter(I_M_Product.COLUMN_M_Product_ID, product0.getM_Product_ID());
		}

		//
		// Create the query and check it
		final POJOQuery<I_M_Product> query = (POJOQuery<I_M_Product>)queryBuilder.create();
		Assert.assertEquals("Query unions count for " + query, filtersCount - 1, query.getUnions().size());

		//
		// Execute the query and check the result
		final List<I_M_Product> result = query.list();
		final I_M_Product productActual = ListUtils.singleElement(result); // NOTE: we expect ONLY ONE result, even if we had 1000 filters about same thing because the unions shall be DISTINCT
		Assert.assertEquals("Retrieved product", product0.getM_Product_ID(), productActual.getM_Product_ID());
	}
}
