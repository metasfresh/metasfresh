package de.metas.material.dispo.commons.repository.atp;

import static de.metas.material.event.EventTestHelper.ATTRIBUTE_SET_INSTANCE_ID;
import static de.metas.material.event.EventTestHelper.BPARTNER_ID;
import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.PRODUCT_ID;
import static de.metas.material.event.EventTestHelper.WAREHOUSE_ID;
import static de.metas.testsupport.MetasfreshAssertions.assertThat;
import static de.metas.testsupport.QueryFilterTestUtil.extractSingleFilter;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;

import de.metas.material.commons.attributes.AttributesKeyPatterns;
import de.metas.material.dispo.model.I_MD_Candidate_ATP_QueryResult;
import de.metas.material.event.EventTestHelper;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.util.Services;

/*
 * #%L
 * metasfresh-material-dispo-commons
 * %%
 * Copyright (C) 2017 metas GmbH
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

@SuppressWarnings({ "rawtypes" })
public class AvailableToPromiseSqlHelperTest
{
	private static final AttributesKey STORAGE_ATTRIBUTES_KEY = AttributesKey.ofAttributeValueIds(1, 2);

	private IQueryBL queryBL;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		queryBL = Services.get(IQueryBL.class);
	}

	private MaterialDescriptor createMaterialDescriptor()
	{
		final ProductDescriptor productDescriptor = ProductDescriptor.forProductAndAttributes(
				PRODUCT_ID,
				STORAGE_ATTRIBUTES_KEY,
				ATTRIBUTE_SET_INSTANCE_ID);
		final MaterialDescriptor materialDescriptor = EventTestHelper.createMaterialDescriptor()
				.withProductDescriptor(productDescriptor);
		return materialDescriptor;
	}

	private ICompositeQueryFilter<I_MD_Candidate_ATP_QueryResult> newCompositeFilter()
	{
		return queryBL.createCompositeQueryFilter(I_MD_Candidate_ATP_QueryResult.class);
	}

	@Test
	public void createDBQuery_for_simple_stock_query()
	{
		final MaterialDescriptor materialDescriptor = createMaterialDescriptor();
		final AvailableToPromiseQuery query = AvailableToPromiseQuery.forMaterialDescriptor(materialDescriptor);
		// MaterialQuery(warehouseIds=[51], date=Thu Nov 30 13:25:21 EET 2017, productIds=[24], storageAttributesKeys=[1§&§2], bpartnerId=-1)

		final IQueryBuilder<I_MD_Candidate_ATP_QueryResult> dbQuery = AvailableToPromiseSqlHelper.createDBQueryForStockQueryBuilder(query);

		final ICompositeQueryFilter<I_MD_Candidate_ATP_QueryResult> dbFilter = dbQuery.getCompositeFilter();

		final ICompositeQueryFilter<I_MD_Candidate_ATP_QueryResult> expectedFilter = newCompositeFilter()
				.addCompareFilter(I_MD_Candidate_ATP_QueryResult.COLUMNNAME_DateProjected, Operator.LESS_OR_EQUAL, TimeUtil.asTimestamp(NOW))
				.addInArrayFilter(I_MD_Candidate_ATP_QueryResult.COLUMNNAME_M_Warehouse_ID, WAREHOUSE_ID)
				.addInArrayFilter(I_MD_Candidate_ATP_QueryResult.COLUMNNAME_M_Product_ID, PRODUCT_ID)
				.addInArrayFilter(I_MD_Candidate_ATP_QueryResult.COLUMNNAME_C_BPartner_Customer_ID, BPARTNER_ID, null)
				.addStringLikeFilter(I_MD_Candidate_ATP_QueryResult.COLUMN_StorageAttributesKey, "%1%2%", /* ignoreCase */false);

		assertThat(dbFilter.toString()).isEqualTo(expectedFilter.toString());

	}

	@Test
	public void createDBQuery_multiple_products()
	{
		final AvailableToPromiseQuery query = AvailableToPromiseQuery.builder()
				.productId(10)
				.productId(20)
				.storageAttributesKeyPattern(AttributesKeyPatterns.ofAttributeKey(STORAGE_ATTRIBUTES_KEY))
				.date(TimeUtil.asZonedDateTime(NOW))
				.build();

		final IQueryBuilder<I_MD_Candidate_ATP_QueryResult> dbQuery = AvailableToPromiseSqlHelper.createDBQueryForStockQueryBuilder(query);
		final ICompositeQueryFilter<I_MD_Candidate_ATP_QueryResult> dbFilter = dbQuery.getCompositeFilter();

		final ICompositeQueryFilter<I_MD_Candidate_ATP_QueryResult> expectedFilter = newCompositeFilter()
				.addCompareFilter(I_MD_Candidate_ATP_QueryResult.COLUMNNAME_DateProjected, Operator.LESS_OR_EQUAL, TimeUtil.asTimestamp(NOW))
				.addInArrayFilter(I_MD_Candidate_ATP_QueryResult.COLUMNNAME_M_Product_ID, 10, 20)
				.addEqualsFilter(I_MD_Candidate_ATP_QueryResult.COLUMNNAME_C_BPartner_Customer_ID, null)
				.addStringLikeFilter(I_MD_Candidate_ATP_QueryResult.COLUMN_StorageAttributesKey, "%1%2%", /* ignoreCase */false);

		assertThat(dbFilter.toString()).isEqualTo(expectedFilter.toString());
	}

	@Test
	public void createDBQuery_multiple_storageAttributesKeys()
	{
		final AvailableToPromiseQuery query = AvailableToPromiseQuery.builder()
				.productId(PRODUCT_ID)
				.storageAttributesKeyPattern(AttributesKeyPatterns.ofAttributeKey(STORAGE_ATTRIBUTES_KEY))
				.storageAttributesKeyPattern(AttributesKeyPatterns.ofAttributeKey(AttributesKey.ofAttributeValueIds(3)))
				.date(TimeUtil.asZonedDateTime(NOW))
				.build();

		final IQueryBuilder<I_MD_Candidate_ATP_QueryResult> dbQuery = AvailableToPromiseSqlHelper.createDBQueryForStockQueryBuilder(query);

		final ICompositeQueryFilter<I_MD_Candidate_ATP_QueryResult> dbFilter = dbQuery.getCompositeFilter();
		assertThat(dbFilter).hasNoFilterRegarding(I_MD_Candidate_ATP_QueryResult.COLUMN_M_Warehouse_ID);

		assertThat(dbFilter).hasInArrayFilter(I_MD_Candidate_ATP_QueryResult.COLUMN_M_Product_ID, PRODUCT_ID);

		{
			final ICompositeQueryFilter attributesFilter = extractSingleFilter(dbFilter, ICompositeQueryFilter.class);

			final ICompositeQueryFilter<I_MD_Candidate_ATP_QueryResult> expectedAttributesFilter = newCompositeFilter()
					.setJoinOr()
					.addStringLikeFilter(I_MD_Candidate_ATP_QueryResult.COLUMN_StorageAttributesKey, "%1%2%", /* ignoreCase */false)
					.addStringLikeFilter(I_MD_Candidate_ATP_QueryResult.COLUMN_StorageAttributesKey, "%3%", /* ignoreCase */false);
			assertThat(attributesFilter.toString()).isEqualTo(expectedAttributesFilter.toString());
		}
	}

	@Test
	public void createDBQuery_multiple_storageAttributesKeys_including_OtherKeys()
	{
		final AvailableToPromiseQuery query = AvailableToPromiseQuery.builder()
				.productId(PRODUCT_ID)
				.storageAttributesKeyPattern(AttributesKeyPatterns.ofAttributeKey(STORAGE_ATTRIBUTES_KEY))
				.storageAttributesKeyPattern(AttributesKeyPatterns.ofAttributeKey(AttributesKey.ofAttributeValueIds(3)))
				.storageAttributesKeyPattern(AttributesKeyPatterns.ofAttributeKey(AttributesKey.OTHER))
				.date(TimeUtil.asZonedDateTime(NOW))
				.build();

		// invoke the method under test
		final IQueryBuilder<I_MD_Candidate_ATP_QueryResult> dbQuery = AvailableToPromiseSqlHelper.createDBQueryForStockQueryBuilder(query);

		final ICompositeQueryFilter<I_MD_Candidate_ATP_QueryResult> dbFilter = dbQuery.getCompositeFilter();
		assertThat(dbFilter).hasNoFilterRegarding(I_MD_Candidate_ATP_QueryResult.COLUMN_M_Warehouse_ID);

		assertThat(dbFilter).hasInArrayFilter(I_MD_Candidate_ATP_QueryResult.COLUMN_M_Product_ID, PRODUCT_ID);

		//
		// Attributes related filter
		{
			assertThat(dbFilter).hasCompositeOrFilter();
			final ICompositeQueryFilter attributesFilter = extractSingleFilter(dbFilter, ICompositeQueryFilter.class);

			final ICompositeQueryFilter<I_MD_Candidate_ATP_QueryResult> expectedAttributesFilter = newCompositeFilter()
					.setJoinOr()
					.addStringLikeFilter(I_MD_Candidate_ATP_QueryResult.COLUMN_StorageAttributesKey, "%1%2%", /* ignoreCase */false)
					.addStringLikeFilter(I_MD_Candidate_ATP_QueryResult.COLUMN_StorageAttributesKey, "%3%", /* ignoreCase */false)
					.addFilter(newCompositeFilter()
							.setJoinAnd()
							.addStringNotLikeFilter(I_MD_Candidate_ATP_QueryResult.COLUMN_StorageAttributesKey, "%1%2%", /* ignoreCase */false)
							.addStringNotLikeFilter(I_MD_Candidate_ATP_QueryResult.COLUMN_StorageAttributesKey, "%3%", /* ignoreCase */false));
			assertThat(attributesFilter.toString()).isEqualTo(expectedAttributesFilter.toString());
		}
	}
}
