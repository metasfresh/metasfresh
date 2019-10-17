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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import static org.assertj.core.api.Assertions.assertThat;
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
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.process.PInstanceId;

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
	@BeforeEach
	public void beforeTest()
	{
		AdempiereTestHelper.get().init();

		ctx = new Properties();
		ctx.setProperty("#AD_Client_ID", "99");

		products = new ArrayList<>();

		product0 = createProduct(true);
		products.add(product0);

		ctx.setProperty("#AD_Client_ID", "100");
		product1_NotActive = createProduct(false);
		products.add(product1_NotActive);

		ctx.setProperty("#AD_Client_ID", "100");
		product2 = createProduct(true);
		products.add(product2);

		assertThat(product0.getAD_Client_ID(), is(99));
		assertThat(product1_NotActive.getAD_Client_ID(), is(100));
		assertThat(product2.getAD_Client_ID(), is(100));
	}

	private I_M_Product createProduct(final boolean active)
	{
		final I_M_Product product = InterfaceWrapperHelper.create(ctx, I_M_Product.class, ITrx.TRXNAME_None);
		product.setIsActive(active);
		InterfaceWrapperHelper.save(product);
		return product;
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

		final IQueryBuilder<I_M_Product> builder = new QueryBuilder<>(I_M_Product.class, null) // tableName=null
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

		final IQueryBuilder<I_M_Product> builder = new QueryBuilder<>(I_M_Product.class, null) // tableName=null
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

		final IQueryBuilder<I_M_Product> builder = new QueryBuilder<>(I_M_Product.class, null) // tableName=null
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

		final IQueryBuilder<I_M_Product> builder = new QueryBuilder<>(I_M_Product.class, null); // tableName=null

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
		final PInstanceId selectionId = POJOLookupMap.get().createSelectionFromModels(product1_NotActive, product2);

		final IQueryBuilder<I_M_Product> builder = new QueryBuilder<>(I_M_Product.class, null); // tableName=null)
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
			query.setOnlySelection(null);
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
		final PInstanceId selectionId = POJOLookupMap.get().createSelectionFromModels(product1_NotActive, product2);

		// Query selection and test
		final List<I_M_Product> list = new QueryBuilder<>(I_M_Product.class, null) // tableName=null
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
	public void test_Explode_OR_Joins_To_SQL_Unions_FirstLevel()
	{
		final I_M_Product product0 = createProduct(true);
		final I_M_Product product1 = createProduct(true);
		final I_M_Product product2 = createProduct(true);
		final I_M_Product product3 = createProduct(true);
		final I_M_Product product4 = createProduct(true);

		final IQueryBuilder<I_M_Product> queryBuilder = new QueryBuilder<>(I_M_Product.class, null) // tableName=null
				.setJoinOr()
				.setOption(IQueryBuilder.OPTION_Explode_OR_Joins_To_SQL_Unions)
				.addEqualsFilter(I_M_Product.COLUMN_M_Product_ID, product0.getM_Product_ID())
				.addEqualsFilter(I_M_Product.COLUMN_M_Product_ID, product1.getM_Product_ID())
				.addEqualsFilter(I_M_Product.COLUMN_M_Product_ID, product2.getM_Product_ID())
				.addEqualsFilter(I_M_Product.COLUMN_M_Product_ID, product3.getM_Product_ID())
				.addEqualsFilter(I_M_Product.COLUMN_M_Product_ID, product4.getM_Product_ID());

		//
		// Create the query and check it
		final POJOQuery<I_M_Product> query = (POJOQuery<I_M_Product>)queryBuilder.create();
		assertThat(query.getUnions()).hasSize(5 - 1);

		//
		// Execute the query and check the result
		final List<I_M_Product> result = query.list();
		assertThat(result).containsExactly(product0, product1, product2, product3, product4);
	}

	@Test
	public void test_Explode_OR_Joins_To_SQL_Unions_SecondLevel()
	{
		final I_M_Product product0 = createProduct(true);
		final I_M_Product product1 = createProduct(true);
		final I_M_Product product2 = createProduct(true);
		final I_M_Product product3 = createProduct(true);
		final I_M_Product product4 = createProduct(true);

		final IQueryBuilder<I_M_Product> queryBuilder = new QueryBuilder<>(I_M_Product.class, null) // tableName=null
				.setJoinAnd()
				.setOption(IQueryBuilder.OPTION_Explode_OR_Joins_To_SQL_Unions)
				.filter(new CompositeQueryFilter<>(I_M_Product.class)
						.setJoinOr()
						.addEqualsFilter(I_M_Product.COLUMN_M_Product_ID, product0.getM_Product_ID())
						.addEqualsFilter(I_M_Product.COLUMN_M_Product_ID, product1.getM_Product_ID())
						.addEqualsFilter(I_M_Product.COLUMN_M_Product_ID, product2.getM_Product_ID())
						.addEqualsFilter(I_M_Product.COLUMN_M_Product_ID, product3.getM_Product_ID())
						.addEqualsFilter(I_M_Product.COLUMN_M_Product_ID, product4.getM_Product_ID()));

		//
		// Create the query and check it
		final POJOQuery<I_M_Product> query = (POJOQuery<I_M_Product>)queryBuilder.create();
		assertThat(query.getUnions()).hasSize(5 - 1);

		//
		// Execute the query and check the result
		final List<I_M_Product> result = query.list();
		assertThat(result).containsExactly(product0, product1, product2, product3, product4);
	}
}
