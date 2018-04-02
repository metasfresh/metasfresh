package de.metas.material.cockpit.stock;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Stream;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.util.Check;
import org.adempiere.util.NumberUtils;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.springframework.stereotype.Service;

import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.material.cockpit.model.I_MD_Stock_WarehouseAndProduct_v;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.stock.StockChangedEvent;
import lombok.NonNull;

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
	private final PostMaterialEventService postMaterialEventService;

	public StockRepository(final PostMaterialEventService postMaterialEventService)
	{
		this.postMaterialEventService = postMaterialEventService;
	}

	public void handleDataUpdateRequest(@NonNull final StockDataUpdateRequest dataUpdateRequest)
	{
		final I_MD_Stock dataRecord = retrieveOrCreateDataRecord(dataUpdateRequest.getIdentifier());
		final BigDecimal qtyOnHandOld = dataRecord.getQtyOnHand();

		final BigDecimal qtyOnHandToAdd = dataUpdateRequest.getOnHandQtyChange();
		final BigDecimal qtyOnHandNew = NumberUtils.stripTrailingDecimalZeros(dataRecord.getQtyOnHand().add(qtyOnHandToAdd));
		dataRecord.setQtyOnHand(qtyOnHandNew);
		save(dataRecord);

		fireStockChangedEvent(dataRecord, qtyOnHandOld);
	}

	private I_MD_Stock retrieveOrCreateDataRecord(@NonNull final StockDataRecordIdentifier identifier)
	{
		final IQuery<I_MD_Stock> query = createQueryForIdentifier(identifier);

		final I_MD_Stock existingDataRecord = query.firstOnly(I_MD_Stock.class);
		if (existingDataRecord != null)
		{
			return existingDataRecord;
		}

		final I_MD_Stock newDataRecord = newInstance(I_MD_Stock.class);
		newDataRecord.setM_Product_ID(identifier.getProductDescriptor().getProductId());
		newDataRecord.setAttributesKey(identifier.getProductDescriptor().getStorageAttributesKey().getAsString());
		newDataRecord.setM_Warehouse_ID(identifier.getWarehouseId());

		return newDataRecord;
	}

	private IQuery<I_MD_Stock> createQueryForIdentifier(@NonNull final StockDataRecordIdentifier identifier)
	{
		final ProductDescriptor productDescriptor = identifier.getProductDescriptor();

		final AttributesKey attributesKey = productDescriptor.getStorageAttributesKey();
		attributesKey.assertNotAllOrOther();

		final IQueryBuilder<I_MD_Stock> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_MD_Stock.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MD_Stock.COLUMN_M_Product_ID, productDescriptor.getProductId())
				.addEqualsFilter(I_MD_Stock.COLUMN_AttributesKey, attributesKey.getAsString())
				.addEqualsFilter(I_MD_Stock.COLUMN_M_Warehouse_ID, identifier.getWarehouseId());

		return queryBuilder.create();
	}

	private void fireStockChangedEvent(final I_MD_Stock dataRecord, final BigDecimal qtyOnHandOld)
	{
		final BigDecimal qtyOnHandNew = dataRecord.getQtyOnHand();
		if (qtyOnHandOld.compareTo(qtyOnHandNew) == 0)
		{
			return;
		}

		final StockChangedEvent event = StockChangedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(dataRecord.getAD_Client_ID(), dataRecord.getAD_Org_ID()))
				.productDescriptor(ProductDescriptor.forProductAndAttributes(
						dataRecord.getM_Product_ID(),
						AttributesKey.ofString(dataRecord.getAttributesKey())))
				.warehouseId(dataRecord.getM_Warehouse_ID())
				.qtyOnHand(qtyOnHandNew)
				.qtyOnHandOld(qtyOnHandOld)
				.build();
		postMaterialEventService.postEventNow(event);
	}

	public BigDecimal getQtyOnHandForProductAndWarehouseIds(final int productId, final Set<Integer> warehouseIds)
	{
		Check.assume(productId > 0, "productId > 0");
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

	public Stream<StockDataRecord> streamStockDataRecords(@NonNull final StockDataQuery query)
	{
		return createQueryBuilder(query)
				.create()
				.iterateAndStream()
				.map(this::toStockDataRecord);
	}

	private IQueryBuilder<I_MD_Stock_WarehouseAndProduct_v> createQueryBuilder(final StockDataQuery query)
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

		return queryBuilder;
	}

	private StockDataRecord toStockDataRecord(final I_MD_Stock_WarehouseAndProduct_v record)
	{
		return StockDataRecord.builder()
				.productCategoryId(record.getM_Product_Category_ID())
				.productId(record.getM_Product_ID())
				.productValue(record.getProductValue())
				.warehouseId(record.getM_Warehouse_ID())
				.qtyOnHand(record.getQtyOnHand())
				.build();
	}
}
