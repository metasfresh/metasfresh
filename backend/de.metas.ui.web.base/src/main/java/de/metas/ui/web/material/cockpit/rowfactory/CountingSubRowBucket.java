package de.metas.ui.web.material.cockpit.rowfactory;

<<<<<<< HEAD
=======
import de.metas.material.cockpit.ProductWithDemandSupply;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.product.IProductBL;
import de.metas.quantity.Quantity;
<<<<<<< HEAD
import de.metas.ui.web.material.cockpit.MaterialCockpitRow;
import de.metas.ui.web.material.cockpit.QtyConvertorService;
import de.metas.util.Services;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
=======
import de.metas.ui.web.material.cockpit.MaterialCockpitDetailsRowAggregationIdentifier;
import de.metas.ui.web.material.cockpit.MaterialCockpitRow;
import de.metas.ui.web.material.cockpit.MaterialCockpitRowCache;
import de.metas.ui.web.material.cockpit.MaterialCockpitRowLookups;
import de.metas.ui.web.material.cockpit.QtyConvertorService;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import lombok.ToString;
import org.compiere.SpringContextHolder;
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
<<<<<<< HEAD
@EqualsAndHashCode(of = "plantId")
@ToString
public class CountingSubRowBucket
{

	private final IProductBL productBL = Services.get(IProductBL.class);
	private final QtyConvertorService qtyConvertorService = SpringContextHolder.instance.getBean(QtyConvertorService.class);

	public static CountingSubRowBucket create(final int plantId)
	{
		return new CountingSubRowBucket(plantId);
	}

	private final int plantId;

	// Zaehlbestand
	private Quantity qtyStockEstimateCount;

	@Nullable
	private Instant qtyStockEstimateTime;

	private Quantity qtyInventoryCount;

	@Nullable
	private Instant qtyInventoryTime;

	private Quantity qtyStockCurrent;

	private Quantity qtyOnHandStock;

=======
@ToString
@RequiredArgsConstructor
public class CountingSubRowBucket
{
	@Getter(AccessLevel.NONE)
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final QtyConvertorService qtyConvertorService = SpringContextHolder.instance.getBean(QtyConvertorService.class);
	@Getter(AccessLevel.NONE)
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	@NonNull private final MaterialCockpitRowCache cache;
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

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	private final Set<Integer> cockpitRecordIds = new HashSet<>();

	private final Set<Integer> stockRecordIds = new HashSet<>();

<<<<<<< HEAD
	public CountingSubRowBucket(final int plantId)
	{
		this.plantId = plantId;
	}

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	public void addCockpitRecord(@NonNull final I_MD_Cockpit cockpitRecord)
	{
		final I_C_UOM uom = productBL.getStockUOM(cockpitRecord.getM_Product_ID());

<<<<<<< HEAD
		qtyStockEstimateCount = addToNullable(qtyStockEstimateCount, cockpitRecord.getQtyStockEstimateCount(), uom);
		qtyStockEstimateTime = TimeUtil.max(qtyStockEstimateTime, TimeUtil.asInstant(cockpitRecord.getQtyStockEstimateTime()));

		qtyInventoryCount = addToNullable(qtyInventoryCount, cockpitRecord.getQtyInventoryCount(), uom);
		qtyInventoryTime = TimeUtil.max(qtyInventoryTime, TimeUtil.asInstant(cockpitRecord.getQtyInventoryTime()));

		qtyStockCurrent = addToNullable(qtyStockCurrent, cockpitRecord.getQtyStockCurrent(), uom);
=======
		qtyStockEstimateCountAtDate = addToNullable(qtyStockEstimateCountAtDate, cockpitRecord.getQtyStockEstimateCount_AtDate(), uom);
		qtyStockEstimateTimeAtDate = TimeUtil.max(qtyStockEstimateTimeAtDate, TimeUtil.asInstant(cockpitRecord.getQtyStockEstimateTime_AtDate()));

		qtyInventoryCountAtDate = addToNullable(qtyInventoryCountAtDate, cockpitRecord.getQtyInventoryCount_AtDate(), uom);
		qtyInventoryTimeAtDate = TimeUtil.max(qtyInventoryTimeAtDate, TimeUtil.asInstant(cockpitRecord.getQtyInventoryTime_AtDate()));

		qtyStockCurrentAtDate = addToNullable(qtyStockCurrentAtDate, cockpitRecord.getQtyStockCurrent_AtDate(), uom);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

		cockpitRecordIds.add(cockpitRecord.getMD_Cockpit_ID());
	}

	public void addStockRecord(@NonNull final I_MD_Stock stockRecord)
	{
		final I_C_UOM uom = productBL.getStockUOM(stockRecord.getM_Product_ID());

		qtyOnHandStock = addToNullable(qtyOnHandStock, stockRecord.getQtyOnHand(), uom);

		stockRecordIds.add(stockRecord.getMD_Stock_ID());
	}

<<<<<<< HEAD
=======
	public void addQuantitiesRecord(@NonNull final ProductWithDemandSupply quantitiesRecord)
	{
		final I_C_UOM uom = uomDAO.getById(quantitiesRecord.getUomId());

		qtyDemandSalesOrder = addToNullable(qtyDemandSalesOrder, quantitiesRecord.getQtyReserved(), uom);
		qtySupplyPurchaseOrder = addToNullable(qtySupplyPurchaseOrder, quantitiesRecord.getQtyToMove(), uom);
	}

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	@NonNull
	public MaterialCockpitRow createIncludedRow(@NonNull final MainRowWithSubRows mainRowBucket)
	{
		final MainRowBucketId productIdAndDate = assumeNotNull(
				mainRowBucket.getProductIdAndDate(),
				"productIdAndDate may not be null; mainRowBucket={}", mainRowBucket);

		return MaterialCockpitRow.countingSubRowBuilder()
<<<<<<< HEAD
				.date(productIdAndDate.getDate())
				.productId(productIdAndDate.getProductId().getRepoId())
				.plantId(plantId)
				.qtyStockEstimateCount(qtyStockEstimateCount)
				.qtyStockEstimateTime(qtyStockEstimateTime)
				.qtyInventoryCount(qtyInventoryCount)
				.qtyInventoryTime(qtyInventoryTime)
				.qtyStockCurrent(qtyStockCurrent)
=======
				.cache(cache)
				.date(productIdAndDate.getDate())
				.productId(productIdAndDate.getProductId())
				.detailsRowAggregationIdentifier(detailsRowAggregationIdentifier)
				.qtyDemandSalesOrder(qtyDemandSalesOrder)
				.qtySupplyPurchaseOrder(qtySupplyPurchaseOrder)
				.qtyStockEstimateCountAtDate(qtyStockEstimateCountAtDate)
				.qtyStockEstimateTimeAtDate(qtyStockEstimateTimeAtDate)
				.qtyInventoryCountAtDate(qtyInventoryCountAtDate)
				.qtyInventoryTimeAtDate(qtyInventoryTimeAtDate)
				.qtyStockCurrentAtDate(qtyStockCurrentAtDate)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				.qtyOnHandStock(qtyOnHandStock)
				.allIncludedCockpitRecordIds(cockpitRecordIds)
				.allIncludedStockRecordIds(stockRecordIds)
				.qtyConvertor(qtyConvertorService.getQtyConvertorIfConfigured(productIdAndDate))
				.build();
	}
}
