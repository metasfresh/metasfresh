package de.metas.ui.web.material.cockpit.rowfactory;

import de.metas.dimension.DimensionSpecGroup;
import de.metas.material.cockpit.ProductWithDemandSupply;
import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.product.IProductBL;
import de.metas.quantity.Quantity;
import de.metas.ui.web.material.cockpit.MaterialCockpitRow;
import de.metas.ui.web.material.cockpit.MaterialCockpitRowLookups;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.I_C_UOM;

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
 */
@Data
@RequiredArgsConstructor
public class DimensionGroupSubRowBucket
{
	@Getter(AccessLevel.NONE) private final IProductBL productBL = Services.get(IProductBL.class);
	@Getter(AccessLevel.NONE) private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	@NonNull private final MaterialCockpitRowLookups rowLookups;
	@NonNull private final DimensionSpecGroup dimensionSpecGroup;

	// Zusage Lieferant
	private Quantity pmmQtyPromisedAtDate;

	private Quantity qtyDemandSalesOrderAtDate;

	private Quantity qtyDemandSalesOrder;

	private Quantity qtyDemandDDOrderAtDate;

	private Quantity qtyDemandSumAtDate;
	// MRP MEnge
	private Quantity qtyDemandPPOrderAtDate;

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

	private Quantity qtyOnHandStock;

	private final Set<Integer> cockpitRecordIds = new HashSet<>();

	private final Set<Integer> stockRecordIds = new HashSet<>();

	public void addCockpitRecord(@NonNull final I_MD_Cockpit cockpitRecord)
	{
		final I_C_UOM uom = productBL.getStockUOM(cockpitRecord.getM_Product_ID());

		pmmQtyPromisedAtDate = addToNullable(pmmQtyPromisedAtDate, cockpitRecord.getPMM_QtyPromised_OnDate_AtDate(), uom);
		qtyMaterialentnahmeAtDate = addToNullable(qtyMaterialentnahmeAtDate, cockpitRecord.getQtyMaterialentnahme_AtDate(), uom);

		qtyDemandPPOrderAtDate = addToNullable(qtyDemandPPOrderAtDate, cockpitRecord.getQtyDemand_PP_Order_AtDate(), uom);
		qtyDemandSalesOrderAtDate = addToNullable(qtyDemandSalesOrderAtDate, cockpitRecord.getQtyDemand_SalesOrder_AtDate(), uom);
		qtyDemandDDOrderAtDate = addToNullable(qtyDemandDDOrderAtDate, cockpitRecord.getQtyDemand_DD_Order_AtDate(), uom);
		qtyDemandSumAtDate = addToNullable(qtyDemandSumAtDate, cockpitRecord.getQtyDemandSum_AtDate(), uom);

		qtySupplyPPOrderAtDate = addToNullable(qtySupplyPPOrderAtDate, cockpitRecord.getQtySupply_PP_Order_AtDate(), uom);
		qtySupplyPurchaseOrderAtDate = addToNullable(qtySupplyPurchaseOrderAtDate, cockpitRecord.getQtySupply_PurchaseOrder_AtDate(), uom);
		qtySupplyDDOrderAtDate = addToNullable(qtySupplyDDOrderAtDate, cockpitRecord.getQtySupply_DD_Order_AtDate(), uom);
		qtySupplySumAtDate = addToNullable(qtySupplySumAtDate, cockpitRecord.getQtySupplySum_AtDate(), uom);
		qtySupplyRequiredAtDate = addToNullable(qtySupplyRequiredAtDate, cockpitRecord.getQtySupplyRequired_AtDate(), uom);
		qtySupplyToScheduleAtDate = addToNullable(qtySupplyToScheduleAtDate, cockpitRecord.getQtySupplyToSchedule_AtDate(), uom);

		qtyExpectedSurplusAtDate = addToNullable(qtyExpectedSurplusAtDate, cockpitRecord.getQtyExpectedSurplus_AtDate(), uom);

		cockpitRecordIds.add(cockpitRecord.getMD_Cockpit_ID());
	}

	public void addQuantitiesRecord(@NonNull final ProductWithDemandSupply quantitiesRecord)
	{
		final I_C_UOM uom = uomDAO.getById(quantitiesRecord.getUomId());

		qtyDemandSalesOrder = addToNullable(qtyDemandSalesOrder, quantitiesRecord.getQtyReserved(), uom);
		qtySupplyPurchaseOrder = addToNullable(qtySupplyPurchaseOrder, quantitiesRecord.getQtyToMove(), uom);
	}

	public void addStockRecord(@NonNull final I_MD_Stock stockRecord)
	{
		final I_C_UOM uom = productBL.getStockUOM(stockRecord.getM_Product_ID());

		qtyOnHandStock = addToNullable(qtyOnHandStock, stockRecord.getQtyOnHand(), uom);

		stockRecordIds.add(stockRecord.getMD_Stock_ID());
	}

	public MaterialCockpitRow createIncludedRow(@NonNull final MainRowWithSubRows mainRowBucket)
	{
		final MainRowBucketId productIdAndDate = assumeNotNull(
				mainRowBucket.getProductIdAndDate(),
				"productIdAndDate may not be null; mainRowBucket={}", mainRowBucket);

		return MaterialCockpitRow.attributeSubRowBuilder()
				.lookups(rowLookups)
				.date(productIdAndDate.getDate())
				.productId(productIdAndDate.getProductId().getRepoId())

				.dimensionGroup(dimensionSpecGroup)
				.pmmQtyPromisedAtDate(getPmmQtyPromisedAtDate())
				.qtyMaterialentnahmeAtDate(getQtyMaterialentnahmeAtDate())
				.qtyDemandPPOrderAtDate(getQtyDemandPPOrderAtDate())
				.qtySupplyPurchaseOrderAtDate(getQtySupplyPurchaseOrderAtDate())
				.qtySupplyPurchaseOrder(getQtySupplyPurchaseOrder())
				.qtyDemandSalesOrderAtDate(getQtyDemandSalesOrderAtDate())
				.qtyDemandSalesOrder(getQtyDemandSalesOrder())
				.qtyDemandDDOrderAtDate(getQtyDemandDDOrderAtDate())
				.qtyDemandSumAtDate(getQtyDemandSumAtDate())
				.qtySupplyPPOrderAtDate(getQtySupplyPPOrderAtDate())
				.qtySupplyDDOrderAtDate(getQtySupplyDDOrderAtDate())
				.qtySupplySumAtDate(getQtySupplySumAtDate())
				.qtySupplyRequiredAtDate(getQtySupplyRequiredAtDate())
				.qtySupplyToScheduleAtDate(getQtySupplyToScheduleAtDate())
				.qtyOnHandStock(getQtyOnHandStock())
				.qtyExpectedSurplusAtDate(getQtyExpectedSurplusAtDate())
				.allIncludedCockpitRecordIds(cockpitRecordIds)
				.allIncludedStockRecordIds(stockRecordIds)
				.build();
	}
}
