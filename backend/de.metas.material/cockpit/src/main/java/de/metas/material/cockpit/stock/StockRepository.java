package de.metas.material.cockpit.stock;

import com.google.common.collect.ImmutableList;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.material.cockpit.model.I_MD_Stock_WarehouseAndProduct_v;
import de.metas.material.cockpit.model.I_T_MD_Stock_WarehouseAndProduct;
import org.adempiere.mm.attributes.keys.AttributesKeyPatternsUtil;
import org.adempiere.mm.attributes.keys.AttributesKeyQueryHelper;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.TypedSqlQuery;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Table;
import org.compiere.util.DB;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

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
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public BigDecimal getQtyOnHandForProductAndWarehouseIds(
			@NonNull final ProductId productId,
			final Set<WarehouseId> warehouseIds)
	{
		Check.assumeNotEmpty(warehouseIds, "warehouseIds is not empty");

		final BigDecimal qtyOnHand = Services.get(IQueryBL.class)
				.createQueryBuilder(I_MD_Stock.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MD_Stock.COLUMNNAME_M_Product_ID, productId)
				.addInArrayFilter(I_MD_Stock.COLUMNNAME_M_Warehouse_ID, warehouseIds)
				.create()
				.aggregate(I_MD_Stock.COLUMN_QtyOnHand, IQuery.Aggregate.SUM, BigDecimal.class);
		return qtyOnHand != null ? qtyOnHand : BigDecimal.ZERO;
	}

	/** Please use this stream within a try-with-resources statement, because it's supposed to do cleanup. */
	public Stream<StockDataAggregateItem> streamStockDataAggregateItems(
			@NonNull final StockDataAggregateQuery query)
	{
		final IQuery<I_MD_Stock_WarehouseAndProduct_v> stockDataAggregateItemViewQuery = createStockDataAggregateItemQuery(query);

		if (stockDataAggregateItemViewQuery instanceof TypedSqlQuery)
		{
			final TypedSqlQuery<I_MD_Stock_WarehouseAndProduct_v>//
			sqlQuery = (TypedSqlQuery<I_MD_Stock_WarehouseAndProduct_v>)stockDataAggregateItemViewQuery;

			final String uuid = UUID.randomUUID().toString();

			final String insertSQL = createInsertSqlStatement(sqlQuery, uuid);

			final int insertCount = DB.executeUpdateAndThrowExceptionOnFail(
					insertSQL,
					sqlQuery.getParametersEffective().toArray(),
					ITrx.TRXNAME_ThreadInherited);
			Loggables.addLog("Inserted {} records with UUID={} into table {}", insertCount, uuid, I_T_MD_Stock_WarehouseAndProduct.Table_Name);

			return Services
					.get(IQueryBL.class)
					.createQueryBuilder(I_T_MD_Stock_WarehouseAndProduct.class)
					.addEqualsFilter(I_T_MD_Stock_WarehouseAndProduct.COLUMN_UUID, uuid)
					.orderBy(I_T_MD_Stock_WarehouseAndProduct.COLUMN_Line)
					.create()
					.setOption(IQuery.OPTION_GuaranteedIteratorRequired, true)
					.setOption(IQuery.OPTION_IteratorBufferSize, query.getIteratorBatchSize())
					.setOption(IQuery.OPTION_ReturnReadOnlyRecords, true)
					.iterateAndStream()

					// cleanup when the stream is closed, *if* the stream's close method is called
					.onClose(() -> deleteTemporaryRecords(uuid))
					.map(this::recordRowToStockDataItem);
		}
		else
		{
			// we are in unit test mode
			return stockDataAggregateItemViewQuery.list().stream().map(this::viewRowToStockDataItem);
		}
	}

	private void deleteTemporaryRecords(@NonNull final String uuid)
	{
		final int deleteCount = Services
				.get(IQueryBL.class)
				.createQueryBuilder(I_T_MD_Stock_WarehouseAndProduct.class)
				.addEqualsFilter(I_T_MD_Stock_WarehouseAndProduct.COLUMN_UUID, uuid)
				.create()
				.deleteDirectly();
		Loggables.addLog("Deleted {} records with UUID={} from table {}", deleteCount, uuid, I_T_MD_Stock_WarehouseAndProduct.Table_Name);
	}

	/**
	 * Creates an {@code INSERT} statement that select the rows from the given {@code sqlQuery} into the {@link I_T_MD_Stock_WarehouseAndProduct} table
	 *
	 * @param uuid the inserted records all have this UUID
	 */
	private String createInsertSqlStatement(
			@NonNull final TypedSqlQuery<I_MD_Stock_WarehouseAndProduct_v> sqlQuery,
			@NonNull final String uuid)
	{
		final StringBuilder insertClause = new StringBuilder("INSERT INTO " + I_T_MD_Stock_WarehouseAndProduct.Table_Name);
		final StringBuilder selectClause = new StringBuilder("SELECT ");
		final I_AD_Table viewTable = Services.get(IADTableDAO.class).retrieveTable(I_MD_Stock_WarehouseAndProduct_v.Table_Name);
		final List<I_AD_Column> viewColumns = Services.get(IADTableDAO.class).retrieveColumnsForTable(viewTable);

		for (int i = 0; i < viewColumns.size(); i++)
		{
			if (i == 0)
			{
				insertClause.append(" (\n\t");
				selectClause.append(" \n\t");
			}
			else
			{
				insertClause.append("\t, ");
				selectClause.append("\t, ");
			}
			final String currentColumnName = viewColumns.get(i).getColumnName();
			insertClause.append(currentColumnName).append("\n");
			selectClause.append(currentColumnName).append("\n");
		}

		insertClause.append("\t, ").append(I_T_MD_Stock_WarehouseAndProduct.COLUMNNAME_UUID).append("\n");
		selectClause.append("\t, '").append(uuid).append("'").append("\n");

		insertClause.append("\t, ").append(I_T_MD_Stock_WarehouseAndProduct.COLUMNNAME_T_MD_Stock_WarehouseAndProduct_ID).append("\n)");
		selectClause.append("\t, ").append("nextval('T_MD_Stock_WarehouseAndProduct_seq')");

		return insertClause + "\n"
				+ selectClause + "\n"
				+ "FROM " + I_MD_Stock_WarehouseAndProduct_v.Table_Name + "\n"
				+ "WHERE " + sqlQuery.getWhereClause();
	}

	private IQuery<I_MD_Stock_WarehouseAndProduct_v> createStockDataAggregateItemQuery(@NonNull final StockDataAggregateQuery query)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_MD_Stock_WarehouseAndProduct_v> queryBuilder = queryBL.createQueryBuilder(I_MD_Stock_WarehouseAndProduct_v.class);
		if (!query.getProductCategoryIds().isEmpty())
		{
			queryBuilder.addInArrayFilter(I_MD_Stock_WarehouseAndProduct_v.COLUMNNAME_M_Product_Category_ID, query.getProductCategoryIds());
		}
		if (!query.getWarehouseIds().isEmpty())
		{
			queryBuilder.addInArrayFilter(I_MD_Stock_WarehouseAndProduct_v.COLUMNNAME_M_Warehouse_ID, query.getWarehouseIds());
		}

		query.getOrderBys().forEach(orderBy -> queryBuilder.orderBy(orderBy.getColumnName()));

		return queryBuilder.create();
	}

	/** Used in unit tests */
	private StockDataAggregateItem viewRowToStockDataItem(@NonNull final I_MD_Stock_WarehouseAndProduct_v record)
	{
		return StockDataAggregateItem.builder()
				.productCategoryId(record.getM_Product_Category_ID())
				.productId(record.getM_Product_ID())
				.productValue(record.getProductValue())
				.warehouseId(record.getM_Warehouse_ID())
				.qtyOnHand(record.getQtyOnHand())
				.build();
	}

	/** Used when running against a real DB */
	private StockDataAggregateItem recordRowToStockDataItem(@NonNull final I_T_MD_Stock_WarehouseAndProduct record)
	{
		return StockDataAggregateItem.builder()
				.productCategoryId(record.getM_Product_Category_ID())
				.productId(record.getM_Product_ID())
				.productValue(record.getProductValue())
				.warehouseId(record.getM_Warehouse_ID())
				.qtyOnHand(record.getQtyOnHand())
				.build();
	}

	public Stream<StockDataItem> streamStockDataItems(@NonNull final StockDataMultiQuery multiQuery)
	{
		final IQuery<I_MD_Stock> query = multiQuery
				.getStockDataQueries()
				.stream()
				.map(this::createStockDataItemQuery)
				.reduce(IQuery.unionDistict())
				.orElse(null);

		if (query == null)
		{
			return Stream.empty();
		}
		return query
				.iterateAndStream()
				.map(this::recordToStockDataItem);
	}

	private IQuery<I_MD_Stock> createStockDataItemQuery(@NonNull final StockDataQuery query)
	{
		final IQueryBuilder<I_MD_Stock> queryBuilder = queryBL.createQueryBuilder(I_MD_Stock.class);

		queryBuilder.addEqualsFilter(I_MD_Stock.COLUMNNAME_M_Product_ID, query.getProductId());

		if (!query.getWarehouseIds().isEmpty())
		{
			queryBuilder.addInArrayFilter(I_MD_Stock.COLUMNNAME_M_Warehouse_ID, query.getWarehouseIds());
		}

		//
		// Storage Attributes Key
		{
			final AttributesKeyQueryHelper<I_MD_Stock> helper = AttributesKeyQueryHelper.createFor(I_MD_Stock.COLUMN_AttributesKey);
			final IQueryFilter<I_MD_Stock> attributesKeysFilter = helper.createFilter(ImmutableList.of(AttributesKeyPatternsUtil.ofAttributeKey(query.getStorageAttributesKey())));
			queryBuilder.filter(attributesKeysFilter);
		}

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
