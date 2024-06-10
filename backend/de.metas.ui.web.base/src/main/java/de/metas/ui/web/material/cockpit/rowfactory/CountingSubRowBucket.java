package de.metas.ui.web.material.cockpit.rowfactory;

import de.metas.material.cockpit.ProductWithDemandSupply;
import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.product.IProductBL;
import de.metas.quantity.Quantity;
import de.metas.ui.web.material.cockpit.MaterialCockpitDetailsRowAggregationIdentifier;
import de.metas.ui.web.material.cockpit.MaterialCockpitRow;
import de.metas.ui.web.material.cockpit.MaterialCockpitRowLookups;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.compiere.model.I_C_UOM;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static de.metas.quantity.Quantity.addToNullable;
import static de.metas.util.Check.assumeNotNull;

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

/**
 * Mutable row representation that is used during the rows' loading
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */

@ToString
@RequiredArgsConstructor
public class CountingSubRowBucket
{
	@Getter(AccessLevel.NONE)
	private final IProductBL productBL = Services.get(IProductBL.class);
	@Getter(AccessLevel.NONE)
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	@NonNull private final MaterialCockpitRowLookups rowLookups;
	@NonNull private final MaterialCockpitDetailsRowAggregationIdentifier detailsRowAggregationIdentifier;

	// Zaehlbestand
	private Quantity qtyStockEstimateCountAtDate;

	@Nullable
	private Instant qtyStockEstimateTimeAtDate;

	private Quantity qtyInventoryCountAtDate;

	@Nullable
	private Instant qtyInventoryTimeAtDate;

	private Quantity qtyStockCurrentAtDate;

	private Quantity qtyOnHandStock;

	private Quantity qtySupplyPurchaseOrder;

	private Quantity qtyDemandSalesOrder;

	private final Set<Integer> cockpitRecordIds = new HashSet<>();

	private final Set<Integer> stockRecordIds = new HashSet<>();

	public void addCockpitRecord(@NonNull final I_MD_Cockpit cockpitRecord)
	{
		final I_C_UOM uom = productBL.getStockUOM(cockpitRecord.getM_Product_ID());

		qtyStockEstimateCountAtDate = addToNullable(qtyStockEstimateCountAtDate, cockpitRecord.getQtyStockEstimateCount_AtDate(), uom);
		qtyStockEstimateTimeAtDate = TimeUtil.max(qtyStockEstimateTimeAtDate, TimeUtil.asInstant(cockpitRecord.getQtyStockEstimateTime_AtDate()));

		qtyInventoryCountAtDate = addToNullable(qtyInventoryCountAtDate, cockpitRecord.getQtyInventoryCount_AtDate(), uom);
		qtyInventoryTimeAtDate = TimeUtil.max(qtyInventoryTimeAtDate, TimeUtil.asInstant(cockpitRecord.getQtyInventoryTime_AtDate()));

		qtyStockCurrentAtDate = addToNullable(qtyStockCurrentAtDate, cockpitRecord.getQtyStockCurrent_AtDate(), uom);

		cockpitRecordIds.add(cockpitRecord.getMD_Cockpit_ID());
	}

	public void addStockRecord(@NonNull final I_MD_Stock stockRecord)
	{
		final I_C_UOM uom = productBL.getStockUOM(stockRecord.getM_Product_ID());

		qtyOnHandStock = addToNullable(qtyOnHandStock, stockRecord.getQtyOnHand(), uom);

		stockRecordIds.add(stockRecord.getMD_Stock_ID());
	}

	public void addQuantitiesRecord(@NonNull final ProductWithDemandSupply quantitiesRecord)
	{
		final I_C_UOM uom = uomDAO.getById(quantitiesRecord.getUomId());

		qtyDemandSalesOrder = addToNullable(qtyDemandSalesOrder, quantitiesRecord.getQtyReserved(), uom);
		qtySupplyPurchaseOrder = addToNullable(qtySupplyPurchaseOrder, quantitiesRecord.getQtyToMove(), uom);
	}

	@NonNull
	public MaterialCockpitRow createIncludedRow(@NonNull final MainRowWithSubRows mainRowBucket)
	{
		final MainRowBucketId productIdAndDate = assumeNotNull(
				mainRowBucket.getProductIdAndDate(),
				"productIdAndDate may not be null; mainRowBucket={}", mainRowBucket);

		return MaterialCockpitRow.countingSubRowBuilder()
				.lookups(rowLookups)
				.date(productIdAndDate.getDate())
				.productId(productIdAndDate.getProductId().getRepoId())
				.detailsRowAggregationIdentifier(detailsRowAggregationIdentifier)
				.qtyDemandSalesOrder(qtyDemandSalesOrder)
				.qtySupplyPurchaseOrder(qtySupplyPurchaseOrder)
				.qtyStockEstimateCountAtDate(qtyStockEstimateCountAtDate)
				.qtyStockEstimateTimeAtDate(qtyStockEstimateTimeAtDate)
				.qtyInventoryCountAtDate(qtyInventoryCountAtDate)
				.qtyInventoryTimeAtDate(qtyInventoryTimeAtDate)
				.qtyStockCurrentAtDate(qtyStockCurrentAtDate)
				.qtyOnHandStock(qtyOnHandStock)
				.allIncludedCockpitRecordIds(cockpitRecordIds)
				.allIncludedStockRecordIds(stockRecordIds)
				.build();
	}
}
