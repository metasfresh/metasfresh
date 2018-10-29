package de.metas.material.cockpit.stock;

import lombok.NonNull;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.IQuery;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.material.cockpit.model.I_MD_Stock_WarehouseAndProduct_v;
import de.metas.material.commons.AttributesKeyQueryHelper;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;

/*
 * #%L
 * metasfresh-webui-api
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

@Service
public class StockRepository
{

	public BigDecimal getQtyOnHandForProductAndWarehouseIds(
			@NonNull final ProductId productId,
			final Set<WarehouseId> warehouseIds)
	{
		Check.assumeNotEmpty(warehouseIds, "warehouseIds is not empty");

		final BigDecimal qtyOnHand = Services.get(IQueryBL.class)
				.createQueryBuilder(I_MD_Stock.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MD_Stock.COLUMN_M_Product_ID, productId)
				.addInArrayFilter(I_MD_Stock.COLUMN_M_Warehouse_ID, warehouseIds)
				.create()
				.aggregate(I_MD_Stock.COLUMN_QtyOnHand, IQuery.Aggregate.SUM, BigDecimal.class);
		return qtyOnHand != null ? qtyOnHand : BigDecimal.ZERO;
	}

	public Stream<StockDataAggregateItem> streamStockDataAggregateItems(@NonNull final StockDataAggregateQuery query)
	{
		return createStockDataAggregateItemQuery(query)
				.iterateAndStream()
				.map(this::recordToStockDataItem);
	}

	private IQuery<I_MD_Stock_WarehouseAndProduct_v> createStockDataAggregateItemQuery(@NonNull final StockDataAggregateQuery query)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_MD_Stock_WarehouseAndProduct_v> queryBuilder = queryBL.createQueryBuilder(I_MD_Stock_WarehouseAndProduct_v.class);
		if (!query.getProductCategoryIds().isEmpty())
		{
			queryBuilder.addInArrayFilter(I_MD_Stock_WarehouseAndProduct_v.COLUMN_M_Product_Category_ID, query.getProductCategoryIds());
		}
		if (!query.getWarehouseIds().isEmpty())
		{
			queryBuilder.addInArrayFilter(I_MD_Stock_WarehouseAndProduct_v.COLUMN_M_Warehouse_ID, query.getWarehouseIds());
		}

		query.getOrderBys().forEach(orderBy -> queryBuilder.orderBy(orderBy.getColumnName()));

		return queryBuilder.create();
	}

	private StockDataAggregateItem recordToStockDataItem(@NonNull final I_MD_Stock_WarehouseAndProduct_v record)
	{
		return StockDataAggregateItem.builder()
				.productCategoryId(record.getM_Product_Category_ID())
				.productId(record.getM_Product_ID())
				.productValue(record.getProductValue())
				.warehouseId(record.getM_Warehouse_ID())
				.qtyOnHand(record.getQtyOnHand())
				.build();
	}

	public Stream<StockDataItem> streamStockDatatems(@NonNull final StockDataMultiQuery multiQuery)
	{
		final Optional<IQuery<I_MD_Stock>> query = multiQuery
				.getStockDataQueries()
				.stream()
				.map(this::createStockDatItemQuery)
				.reduce(IQuery.unionDistict());

		if (!query.isPresent())
		{
			return Stream.empty();
		}
		return query.get()
				.iterateAndStream()
				.map(this::recordToStockDataItem);
	}

	private IQuery<I_MD_Stock> createStockDatItemQuery(@NonNull final StockDataQuery query)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_MD_Stock> queryBuilder = queryBL.createQueryBuilder(I_MD_Stock.class);

		queryBuilder.addEqualsFilter(I_MD_Stock.COLUMN_M_Product_ID, query.getProductId());

		if (!query.getWarehouseIds().isEmpty())
		{
			queryBuilder.addInArrayFilter(I_MD_Stock.COLUMN_M_Warehouse_ID, query.getWarehouseIds());
		}

		final AttributesKeyQueryHelper<I_MD_Stock> helper = AttributesKeyQueryHelper.createFor(I_MD_Stock.COLUMN_AttributesKey);
		final ICompositeQueryFilter<I_MD_Stock> attributesKeysFilter = helper.createORFilterForStorageAttributesKeys(ImmutableList.of(query.getStorageAttributesKey()));
		queryBuilder.filter(attributesKeysFilter);

		return queryBuilder.create();
	}

	private StockDataItem recordToStockDataItem(@NonNull final I_MD_Stock record)
	{
		return StockDataItem.builder()
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.warehouseId(WarehouseId.ofRepoId(record.getM_Warehouse_ID()))
				.storageAttributesKey(AttributesKey.ofString(record.getAttributesKey()))
				.qtyOnHand(record.getQtyOnHand())
				.build();
	}
}
