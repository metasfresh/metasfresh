package de.metas.ui.web.material.cockpit.rowfactory;

import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.product.IProductBL;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
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
	
	private Quantity qtyStockCurrent;

	private Quantity qtyOnHand;

	// Zusage Lieferant
	private Quantity pmmQtyPromised;

	private Quantity qtyDemandSalesOrder;

	private Quantity qtyDemandDDOrder;
	// MRP Menge
	private Quantity qtyDemandPPOrder;

	private Quantity qtyDemandSum;

	private Quantity qtySupplyPPOrder;

	private Quantity qtySupplyPurchaseOrder;

	private Quantity qtySupplyDDOrder;

	private Quantity qtySupplySum;

	private Quantity qtySupplyRequired;

	private Quantity qtySupplyToSchedule;

	private Quantity qtyMaterialentnahme;

	// zusagbar Zaehlbestand
	private Quantity qtyExpectedSurplus;

	private Quantity qtyStockEstimateCount;

	@Nullable
	private Integer qtyStockEstimateSeqNo;

	@Nullable
	private Instant qtyStockEstimateTime;

	private Quantity qtyInventoryCount;

	@Nullable
	private Instant qtyInventoryTime;

	private final Set<Integer> cockpitRecordIds = new HashSet<>();

	private final Set<Integer> stockRecordIds = new HashSet<>();

	public void addDataRecord(@NonNull final I_MD_Cockpit cockpitRecord)
	{
		final IProductBL productBL = Services.get(IProductBL.class);

		final I_C_UOM uom = productBL.getStockUOM(cockpitRecord.getM_Product_ID());

		pmmQtyPromised = addToNullable(pmmQtyPromised, cockpitRecord.getPMM_QtyPromised_OnDate(), uom);
		qtyMaterialentnahme = addToNullable(qtyMaterialentnahme, cockpitRecord.getQtyMaterialentnahme(), uom);

		qtyDemandPPOrder = addToNullable(qtyDemandPPOrder, cockpitRecord.getQtyDemand_PP_Order(), uom);
		qtyDemandSalesOrder = addToNullable(qtyDemandSalesOrder, cockpitRecord.getQtyDemand_SalesOrder(), uom);
		qtyDemandDDOrder = addToNullable(qtyDemandDDOrder, cockpitRecord.getQtyDemand_DD_Order(), uom);
		qtyDemandSum = addToNullable(qtyDemandSum, cockpitRecord.getQtyDemandSum(), uom);

		qtySupplyPurchaseOrder = addToNullable(qtySupplyPurchaseOrder, cockpitRecord.getQtySupply_PurchaseOrder(), uom);
		qtySupplyPPOrder = addToNullable(qtySupplyPPOrder, cockpitRecord.getQtySupply_PP_Order(), uom);
		qtySupplyDDOrder = addToNullable(qtySupplyDDOrder, cockpitRecord.getQtySupply_DD_Order(), uom);
		qtySupplySum = addToNullable(qtySupplySum, cockpitRecord.getQtySupplySum(), uom);
		qtySupplyRequired = addToNullable(qtySupplyRequired, cockpitRecord.getQtySupplyRequired(), uom);
		qtySupplyToSchedule = addToNullable(qtySupplyToSchedule, cockpitRecord.getQtySupplyToSchedule(), uom);

		qtyStockEstimateCount = addToNullable(qtyStockEstimateCount, cockpitRecord.getQtyStockEstimateCount(), uom);
		qtyStockEstimateTime = TimeUtil.max(qtyStockEstimateTime, TimeUtil.asInstant(cockpitRecord.getQtyStockEstimateTime()));
		qtyStockEstimateSeqNo = cockpitRecord.getQtyStockEstimateSeqNo();

		qtyInventoryCount = addToNullable(qtyInventoryCount, cockpitRecord.getQtyInventoryCount(), uom);
		qtyInventoryTime = TimeUtil.max(qtyInventoryTime, TimeUtil.asInstant(cockpitRecord.getQtyInventoryTime()));

		qtyExpectedSurplus = addToNullable(qtyExpectedSurplus, cockpitRecord.getQtyExpectedSurplus(), uom);
		qtyStockCurrent = addToNullable(qtyStockCurrent, cockpitRecord.getQtyStockCurrent(), uom);

		cockpitRecordIds.add(cockpitRecord.getMD_Cockpit_ID());
	}

	public void addStockRecord(@NonNull final I_MD_Stock stockRecord)
	{
		final I_C_UOM uom = productBL.getStockUOM(stockRecord.getM_Product_ID());

		qtyOnHand = addToNullable(qtyOnHand, stockRecord.getQtyOnHand(), uom);
		stockRecordIds.add(stockRecord.getMD_Stock_ID());
	}
}
