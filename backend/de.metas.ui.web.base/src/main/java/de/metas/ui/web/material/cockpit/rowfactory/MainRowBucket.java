package de.metas.ui.web.material.cockpit.rowfactory;

import de.metas.material.cockpit.ProductWithDemandSupply;
import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.money.Money;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.compiere.model.I_C_UOM;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static de.metas.quantity.Quantity.addToNullable;

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

@Getter
public class MainRowBucket
{
	@Getter(AccessLevel.NONE)
	private final transient IProductBL productBL = Services.get(IProductBL.class);
	@Getter(AccessLevel.NONE)
	private final transient IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	private Quantity qtyStockCurrentAtDate;

	private Quantity qtyOnHand;

	// Zusage Lieferant
	private Quantity pmmQtyPromisedAtDate;

	private Quantity qtyDemandSalesOrderAtDate;

	private Quantity qtyDemandSalesOrder;

	private Quantity qtyDemandDDOrderAtDate;
	// MRP Menge
	private Quantity qtyDemandPPOrderAtDate;

	private Quantity qtyDemandSumAtDate;

	private Quantity qtySupplyPPOrderAtDate;

	private Quantity qtySupplyPurchaseOrderAtDate;

	private Quantity qtySupplyPurchaseOrder;

	private Quantity qtySupplyDDOrderAtDate;

	private Quantity qtySupplySumAtDate;

	private Quantity qtySupplyRequiredAtDate;

	private Quantity qtySupplyToScheduleAtDate;

	private Quantity qtyMaterialentnahmeAtDate;

	// zusagbar Zaehlbestand
	private Quantity qtyExpectedSurplusAtDate;

	private Quantity qtyStockEstimateCountAtDate;

	@Nullable
	private Integer qtyStockEstimateSeqNoAtDate;

	@Nullable
	private Instant qtyStockEstimateTimeAtDate;

	private Quantity qtyInventoryCountAtDate;

	@Nullable
	private Instant qtyInventoryTimeAtDate;

	@Setter
	@Nullable
	private String procurementStatus;

	@Setter
	@Nullable
	private Money highestPurchasePrice_AtDate;

	private Quantity qtyOrdered_PurchaseOrder_AtDate;

	private Quantity qtyOrdered_SalesOrder_AtDate;

	private Quantity availableQty_AtDate;

	private Quantity remainingStock_AtDate;

	private Quantity pmm_QtyPromised_NextDay;

	private final Set<Integer> cockpitRecordIds = new HashSet<>();

	private final Set<Integer> stockRecordIds = new HashSet<>();

	public void addDataRecord(@NonNull final I_MD_Cockpit cockpitRecord)
	{
		final IProductBL productBL = Services.get(IProductBL.class);

		final ProductId productId = ProductId.ofRepoId(cockpitRecord.getM_Product_ID());
		final I_C_UOM uom = productBL.getStockUOM(productId);

		pmmQtyPromisedAtDate = addToNullable(pmmQtyPromisedAtDate, cockpitRecord.getPMM_QtyPromised_OnDate_AtDate(), uom);
		qtyMaterialentnahmeAtDate = addToNullable(qtyMaterialentnahmeAtDate, cockpitRecord.getQtyMaterialentnahme_AtDate(), uom);

		qtyDemandPPOrderAtDate = addToNullable(qtyDemandPPOrderAtDate, cockpitRecord.getQtyDemand_PP_Order_AtDate(), uom);
		qtyDemandSalesOrderAtDate = addToNullable(qtyDemandSalesOrderAtDate, cockpitRecord.getQtyDemand_SalesOrder_AtDate(), uom);
		qtyDemandDDOrderAtDate = addToNullable(qtyDemandDDOrderAtDate, cockpitRecord.getQtyDemand_DD_Order_AtDate(), uom);
		qtyDemandSumAtDate = addToNullable(qtyDemandSumAtDate, cockpitRecord.getQtyDemandSum_AtDate(), uom);

		qtySupplyPurchaseOrderAtDate = addToNullable(qtySupplyPurchaseOrderAtDate, cockpitRecord.getQtySupply_PurchaseOrder_AtDate(), uom);
		qtySupplyPPOrderAtDate = addToNullable(qtySupplyPPOrderAtDate, cockpitRecord.getQtySupply_PP_Order_AtDate(), uom);
		qtySupplyDDOrderAtDate = addToNullable(qtySupplyDDOrderAtDate, cockpitRecord.getQtySupply_DD_Order_AtDate(), uom);
		qtySupplySumAtDate = addToNullable(qtySupplySumAtDate, cockpitRecord.getQtySupplySum_AtDate(), uom);
		qtySupplyRequiredAtDate = addToNullable(qtySupplyRequiredAtDate, cockpitRecord.getQtySupplyRequired_AtDate(), uom);
		qtySupplyToScheduleAtDate = addToNullable(qtySupplyToScheduleAtDate, cockpitRecord.getQtySupplyToSchedule_AtDate(), uom);

		qtyStockEstimateCountAtDate = addToNullable(qtyStockEstimateCountAtDate, cockpitRecord.getQtyStockEstimateCount_AtDate(), uom);
		qtyStockEstimateTimeAtDate = TimeUtil.max(qtyStockEstimateTimeAtDate, TimeUtil.asInstant(cockpitRecord.getQtyStockEstimateTime_AtDate()));

		// Take the minimum QtyStockEstimateSeqNo_AtDate of all MD_Cockpit records. But take 0 is equivalent to null
		final Integer seqNoToUse = (cockpitRecord.getQtyStockEstimateSeqNo_AtDate() <= 0) ? null : cockpitRecord.getQtyStockEstimateSeqNo_AtDate();
		if (qtyStockEstimateSeqNoAtDate != null && seqNoToUse != null)
		{
			qtyStockEstimateSeqNoAtDate = Integer.min(qtyStockEstimateSeqNoAtDate, seqNoToUse);
		}
		else if(qtyStockEstimateSeqNoAtDate == null && seqNoToUse != null)
		{
			qtyStockEstimateSeqNoAtDate = seqNoToUse;
		}

		qtyInventoryCountAtDate = addToNullable(qtyInventoryCountAtDate, cockpitRecord.getQtyInventoryCount_AtDate(), uom);
		qtyInventoryTimeAtDate = TimeUtil.max(qtyInventoryTimeAtDate, TimeUtil.asInstant(cockpitRecord.getQtyInventoryTime_AtDate()));

		qtyExpectedSurplusAtDate = addToNullable(qtyExpectedSurplusAtDate, cockpitRecord.getQtyExpectedSurplus_AtDate(), uom);
		qtyStockCurrentAtDate = addToNullable(qtyStockCurrentAtDate, cockpitRecord.getQtyStockCurrent_AtDate(), uom);

		qtyOrdered_PurchaseOrder_AtDate = addToNullable(qtyOrdered_PurchaseOrder_AtDate, cockpitRecord.getQtyOrdered_PurchaseOrder_AtDate(), uom);
		qtyOrdered_SalesOrder_AtDate = addToNullable(qtyOrdered_SalesOrder_AtDate, cockpitRecord.getQtyOrdered_SalesOrder_AtDate(), uom);

		availableQty_AtDate = addToNullable(availableQty_AtDate,
											cockpitRecord.getQtyStockEstimateCount_AtDate().add(cockpitRecord.getQtyOrdered_PurchaseOrder_AtDate()),
											uom);
		remainingStock_AtDate = addToNullable(remainingStock_AtDate,
											  cockpitRecord.getQtyStockEstimateCount_AtDate()
													  .add(cockpitRecord.getQtyOrdered_PurchaseOrder_AtDate())
													  .subtract(cockpitRecord.getQtyOrdered_SalesOrder_AtDate()),
											  uom);
		pmm_QtyPromised_NextDay = addToNullable(pmm_QtyPromised_NextDay, cockpitRecord.getPMM_QtyPromised_NextDay(), uom);

		cockpitRecordIds.add(cockpitRecord.getMD_Cockpit_ID());
	}

	public void addStockRecord(@NonNull final I_MD_Stock stockRecord)
	{
		final I_C_UOM uom = productBL.getStockUOM(stockRecord.getM_Product_ID());

		qtyOnHand = addToNullable(qtyOnHand, stockRecord.getQtyOnHand(), uom);
		stockRecordIds.add(stockRecord.getMD_Stock_ID());
	}

	public void addQuantitiesRecord(@NonNull final ProductWithDemandSupply quantitiesRecord)
	{
		final I_C_UOM uom = uomDAO.getById(quantitiesRecord.getUomId());

		qtyDemandSalesOrder = addToNullable(qtyDemandSalesOrder, quantitiesRecord.getQtyReserved(), uom);
		qtySupplyPurchaseOrder = addToNullable(qtySupplyPurchaseOrder, quantitiesRecord.getQtyToMove(), uom);
	}
}
