package de.metas.material.dispo.commons.repository;

import static de.metas.material.event.EventTestHelper.ATTRIBUTE_SET_INSTANCE_ID;
import static de.metas.material.event.EventTestHelper.BEFORE_NOW;
import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.PRODUCT_ID;
import static de.metas.material.event.EventTestHelper.WAREHOUSE_ID;
import static de.metas.testsupport.MetasfreshAssertions.assertThat;
import static de.metas.testsupport.QueryFilterTestUtil.extractFilters;
import static de.metas.testsupport.QueryFilterTestUtil.extractSingleFilter;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.NotQueryFilter;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.collections.ListUtils;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import de.metas.material.dispo.commons.repository.AvailableStockResult.ResultGroup;
import de.metas.material.dispo.model.I_MD_Candidate_Stock_v;
import de.metas.material.event.EventTestHelper;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import lombok.NonNull;

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
public class StockRepositoryTest
{
	private static final String STORAGE_ATTRIBUTES_KEY = "Key1" + ProductDescriptor.STORAGE_ATTRIBUTES_KEY_DELIMITER + "Key2";

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void retrieveAvailableStock_for_material_descriptor()
	{
		createStockRecord(WAREHOUSE_ID);

		final MaterialDescriptor materialDescriptor = createMaterialDescriptor();

		final MaterialQuery query = MaterialQuery.forMaterialDescriptor(materialDescriptor);

		final BigDecimal result = new StockRepository().retrieveAvailableStockQtySum(query);
		assertThat(result).isEqualByComparingTo("10");
	}

	private I_MD_Candidate_Stock_v createStockRecord(int warehouseId)
	{
		final I_MD_Candidate_Stock_v viewRecord = newInstance(I_MD_Candidate_Stock_v.class);
		viewRecord.setM_Product_ID(PRODUCT_ID);
		viewRecord.setM_Warehouse_ID(warehouseId);
		viewRecord.setDateProjected(new Timestamp(BEFORE_NOW.getTime()));
		viewRecord.setStorageAttributesKey(STORAGE_ATTRIBUTES_KEY);
		viewRecord.setQty(BigDecimal.TEN);

		save(viewRecord);
		return viewRecord;
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

	@Test
	public void createDBQuery_for_simple_stock_query()
	{
		final MaterialDescriptor materialDescriptor = createMaterialDescriptor();
		final MaterialQuery query = MaterialQuery.forMaterialDescriptor(materialDescriptor);

		final IQueryBuilder<I_MD_Candidate_Stock_v> dbQuery = new StockRepository().createDBQueryForMaterialQuery(query);

		final ICompositeQueryFilter<I_MD_Candidate_Stock_v> dbFilter = dbQuery.getCompositeFilter();
		assertThat(dbFilter).hasEqualsFilter(I_MD_Candidate_Stock_v.COLUMN_M_Warehouse_ID, WAREHOUSE_ID);

		final ICompositeQueryFilter includedCompositeOrFilter = extractSingleFilter(dbFilter, ICompositeQueryFilter.class);
		assertThat(includedCompositeOrFilter).isJoinOr();

		assertHasANDFiltersThatAllHaveInArrayForProductIds(includedCompositeOrFilter, ImmutableList.of(PRODUCT_ID));
		assertHasOneANDFilterWithLikeExpression(includedCompositeOrFilter, "%Key1%Key2%");
	}

	@Test
	public void createDBQuery_multiple_products()
	{
		final MaterialQuery query = MaterialQuery.builder()
				.productId(10)
				.productId(20)
				.storageAttributesKey(STORAGE_ATTRIBUTES_KEY)
				.date(NOW)
				.build();

		final IQueryBuilder<I_MD_Candidate_Stock_v> dbQuery = new StockRepository().createDBQueryForMaterialQuery(query);

		final ICompositeQueryFilter<I_MD_Candidate_Stock_v> dbFilter = dbQuery.getCompositeFilter();
		assertThat(dbFilter).hasNoFilterRegarding(I_MD_Candidate_Stock_v.COLUMN_M_Warehouse_ID);
		assertThat(dbFilter).hasEqualsFilter(I_MD_Candidate_Stock_v.COLUMN_DateProjected, NOW);

		final ICompositeQueryFilter includedCompositeOrFilter = extractSingleFilter(dbFilter, ICompositeQueryFilter.class);
		assertThat(includedCompositeOrFilter).isJoinOr();

		assertHasANDFiltersThatAllHaveInArrayForProductIds(includedCompositeOrFilter, ImmutableList.of(10, 20));
		assertHasOneANDFilterWithLikeExpression(includedCompositeOrFilter, "%Key1%Key2%");
	}

	@Test
	public void createDBQuery_multiple_storageAttributesKeys()
	{
		final MaterialQuery query = MaterialQuery.builder()
				.productId(PRODUCT_ID)
				.storageAttributesKey(STORAGE_ATTRIBUTES_KEY)
				.storageAttributesKey("Key3")
				.date(NOW)
				.build();

		final IQueryBuilder<I_MD_Candidate_Stock_v> dbQuery = new StockRepository().createDBQueryForMaterialQuery(query);

		final ICompositeQueryFilter<I_MD_Candidate_Stock_v> dbFilter = dbQuery.getCompositeFilter();
		assertThat(dbFilter).hasNoFilterRegarding(I_MD_Candidate_Stock_v.COLUMN_M_Warehouse_ID);
		assertThat(dbFilter).hasEqualsFilter(I_MD_Candidate_Stock_v.COLUMN_DateProjected, NOW);

		final ICompositeQueryFilter includedCompositeOrFilter = extractSingleFilter(dbFilter, ICompositeQueryFilter.class);
		assertThat(includedCompositeOrFilter).isJoinOr();

		assertHasANDFiltersThatAllHaveInArrayForProductIds(includedCompositeOrFilter, ImmutableList.of(PRODUCT_ID));

		assertHasOneANDFilterWithLikeExpression(includedCompositeOrFilter, "%Key3%");
		assertHasOneANDFilterWithLikeExpression(includedCompositeOrFilter, "%Key1%Key2%");
	}

	@Test
	public void createDBQuery_multiple_storageAttributesKeys_including_OtherKeys()
	{
		final MaterialQuery query = MaterialQuery.builder()
				.productId(PRODUCT_ID)
				.storageAttributesKey(STORAGE_ATTRIBUTES_KEY)
				.storageAttributesKey("Key3")
				.storageAttributesKey(ProductDescriptor.STORAGE_ATTRIBUTES_KEY_OTHER)
				.date(NOW)
				.build();

		// invoke the method under test
		final IQueryBuilder<I_MD_Candidate_Stock_v> dbQuery = new StockRepository().createDBQueryForMaterialQuery(query);

		final ICompositeQueryFilter<I_MD_Candidate_Stock_v> dbFilter = dbQuery.getCompositeFilter();
		assertThat(dbFilter).hasNoFilterRegarding(I_MD_Candidate_Stock_v.COLUMN_M_Warehouse_ID);
		assertThat(dbFilter).hasEqualsFilter(I_MD_Candidate_Stock_v.COLUMN_DateProjected, NOW);

		assertThat(dbFilter).hasCompositeOrFilter();
		final ICompositeQueryFilter includedCompositeOrFilter = extractSingleFilter(dbFilter, ICompositeQueryFilter.class);
		assertThat(includedCompositeOrFilter).isJoinOr();

		assertHasANDFiltersThatAllHaveInArrayForProductIds(includedCompositeOrFilter, ImmutableList.of(PRODUCT_ID));

		assertHasOneANDFilterWithLikeExpression(includedCompositeOrFilter, "%Key3%");
		assertHasOneANDFilterWithLikeExpression(includedCompositeOrFilter, "%Key1%Key2%");

		assertThat(includedCompositeOrFilter).hasCompositeAndFilter();
		final List<ICompositeQueryFilter> includedCompositeAndFilters = extractFilters(includedCompositeOrFilter, ICompositeQueryFilter.class);

		assertHasOneFilterWithNotLikeExpressions(includedCompositeAndFilters);
	}

	private void assertHasOneANDFilterWithLikeExpression(
			@NonNull final ICompositeQueryFilter compositeFilter,
			@NonNull final String likeExpression)
	{
		assertThat(compositeFilter).hasCompositeAndFilter();

		final List<ICompositeQueryFilter> includedCompositeAndFilters = extractFilters(compositeFilter, ICompositeQueryFilter.class);
		assertThat(includedCompositeAndFilters).anySatisfy(filter -> {
			assertThat(filter).isJoinAnd();
			assertThat(filter).hasStringLikeFilter(I_MD_Candidate_Stock_v.COLUMN_StorageAttributesKey, likeExpression);
		});

	}

	private void assertHasANDFiltersThatAllHaveInArrayForProductIds(
			@NonNull final ICompositeQueryFilter compositeFilter,
			@NonNull final Collection<?> productIds)
	{
		assertThat(compositeFilter).hasCompositeAndFilter();

		final List<ICompositeQueryFilter> includedCompositeAndFilters = extractFilters(compositeFilter, ICompositeQueryFilter.class);
		assertThat(includedCompositeAndFilters).allSatisfy(filter -> {
			assertThat(filter).isJoinAnd();
			assertThat(filter).hasInArrayFilter(I_MD_Candidate_Stock_v.COLUMN_M_Product_ID, productIds);
		});
	}

	private void assertHasOneFilterWithNotLikeExpressions(
			@NonNull final List<ICompositeQueryFilter> includedCompositeAndFilters)
	{
		assertThat(includedCompositeAndFilters).anySatisfy(includedCompositeAndFilter -> {

			assertThat(includedCompositeAndFilter).isJoinAnd();
			assertThat(includedCompositeAndFilter).hasNotQueryFilter();
			final List<NotQueryFilter> notQueryFilters = extractFilters(includedCompositeAndFilter, NotQueryFilter.class);
			assertThat(notQueryFilters).hasSize(2);

			assertThat(notQueryFilters).anySatisfy(notQueryFilter -> {
				assertThat(notQueryFilter.getFilter()).isStringLikeFilter(I_MD_Candidate_Stock_v.COLUMN_StorageAttributesKey, "%Key3%");
			});
			assertThat(notQueryFilters).anySatisfy(notQueryFilter -> {
				assertThat(notQueryFilter.getFilter()).isStringLikeFilter(I_MD_Candidate_Stock_v.COLUMN_StorageAttributesKey, "%Key1%Key2%");
			});
		});
	}

	@Test
	public void applyStockRecordsToEmptyResult()
	{
		final I_MD_Candidate_Stock_v stockRecord = createStockRecord(WAREHOUSE_ID);

		final AvailableStockResult result = new AvailableStockResult(ImmutableList.of(
				ResultGroup.builder()
						.productId(PRODUCT_ID)
						.storageAttributesKey(STORAGE_ATTRIBUTES_KEY)
						.build()));

		new StockRepository().applyStockRecordsToEmptyResult(
				result,
				ImmutableList.of(stockRecord));

		final ResultGroup singleElement = ListUtils.singleElement(result.getResultGroups());
		assertThat(singleElement.getProductId()).isEqualTo(stockRecord.getM_Product_ID());
		assertThat(singleElement.getQty()).isEqualByComparingTo(stockRecord.getQty());
		assertThat(singleElement.getStorageAttributesKey()).isEqualTo(STORAGE_ATTRIBUTES_KEY);
	}
}
