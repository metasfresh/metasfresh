package de.metas.material.dispo.commons.repository;

import static de.metas.material.dispo.commons.repository.CompositeQueryFilterAssert.assertThat;
import static de.metas.material.event.EventTestHelper.ATTRIBUTE_SET_INSTANCE_ID;
import static de.metas.material.event.EventTestHelper.BEFORE_NOW;
import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.PRODUCT_ID;
import static de.metas.material.event.EventTestHelper.WAREHOUSE_ID;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.sql.RowSet;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.collections.ListUtils;
import org.compiere.util.DB;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import de.metas.material.dispo.commons.repository.AvailableStockResult.ResultGroup;
import de.metas.material.dispo.model.I_MD_Candidate_Stock_v;
import de.metas.material.event.EventTestHelper;
import de.metas.material.event.MaterialDescriptor;
import de.metas.material.event.ProductDescriptor;
import mockit.Mocked;

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

public class StockRepositoryTest
{
	private static final String STORAGE_ATTRIBUTES_KEY = "Key1" + ProductDescriptor.STORAGE_ATTRIBUTES_KEY_DELIMITER + "Key2";

	@Mocked
	DB db;

	@Mocked
	RowSet rowSet;

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

		final BigDecimal result = new StockRepository().retrieveSingleAvailableStockQty(query);
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

	// TODO
	// move query-assertThat to base
	// UNION in case of multiple storage attributes keys
	// exclusion of storage attributes keys (->i.e."none of the previous or sth)
	//

	@Test
	public void createDBQuery_for_simple_stock_query()
	{
		final MaterialDescriptor materialDescriptor = createMaterialDescriptor();
		final MaterialQuery query = MaterialQuery.forMaterialDescriptor(materialDescriptor);

		final IQueryBuilder<I_MD_Candidate_Stock_v> dbQuery = new StockRepository().createDBQueryForMaterialQuery(query);

		final ICompositeQueryFilter<I_MD_Candidate_Stock_v> dbFilter = dbQuery.getCompositeFilter();
		assertThat(dbFilter).hasEqualsFilter(I_MD_Candidate_Stock_v.COLUMN_M_Warehouse_ID, WAREHOUSE_ID);
		assertThat(dbFilter).hasInArrayFilter(I_MD_Candidate_Stock_v.COLUMN_M_Product_ID, PRODUCT_ID);
		assertThat(dbFilter).hasStringLikeFilter(I_MD_Candidate_Stock_v.COLUMN_StorageAttributesKey, "%Key1%Key2%");
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
		assertThat(dbFilter).hasCompareFilter(I_MD_Candidate_Stock_v.COLUMN_DateProjected, Operator.LESS_OR_EQUAL, NOW);
		assertThat(dbFilter).hasInArrayFilter(I_MD_Candidate_Stock_v.COLUMN_M_Product_ID, 10, 20);
		assertThat(dbFilter).hasStringLikeFilter(I_MD_Candidate_Stock_v.COLUMN_StorageAttributesKey, "%Key1%Key2%");
	}

	@Test
	public void createDBQuery_multiple_storageAttributesKeys()
	{
		final MaterialQuery query = MaterialQuery.builder()
				.productId(10)
				.warehouseId(PRODUCT_ID)
				.storageAttributesKey(STORAGE_ATTRIBUTES_KEY)
				.storageAttributesKey("Key3")
				.date(NOW)
				.build();

		final IQueryBuilder<I_MD_Candidate_Stock_v> dbQuery = new StockRepository().createDBQueryForMaterialQuery(query);

		final ICompositeQueryFilter<I_MD_Candidate_Stock_v> dbFilter = dbQuery.getCompositeFilter();
		assertThat(dbFilter).hasCompositeOrFilter();
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
