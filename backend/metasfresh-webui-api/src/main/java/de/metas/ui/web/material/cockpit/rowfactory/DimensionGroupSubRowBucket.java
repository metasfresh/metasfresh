package de.metas.ui.web.material.cockpit.rowfactory;

import static de.metas.quantity.Quantity.addToNullable;
import static de.metas.util.Check.assumeNotNull;

import java.util.HashSet;
import java.util.Set;

import org.compiere.model.I_C_UOM;

import de.metas.dimension.DimensionSpecGroup;
import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.quantity.Quantity;
import de.metas.ui.web.material.cockpit.MaterialCockpitRow;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import lombok.Data;
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

/**
 * Mutable row representation that is used during the rows' loading
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Data
public class DimensionGroupSubRowBucket
{
	public static DimensionGroupSubRowBucket create(@NonNull final DimensionSpecGroup dimensionSpecGroup)
	{
		return new DimensionGroupSubRowBucket(dimensionSpecGroup);
	}

	private final DimensionSpecGroup dimensionSpecGroup;

	// Zusage Lieferant
	private Quantity pmmQtyPromised;

	private Quantity qtyReservedSale;

	private Quantity qtyReservedPurchase;

	private Quantity qtyMaterialentnahme;

	// MRP MEnge
	private Quantity qtyRequiredForProduction;

	// zusagbar Zaehlbestand
	private Quantity qtyAvailableToPromiseEstimate;

	private Quantity qtyOnHandStock;

	private final Set<Integer> cockpitRecordIds = new HashSet<>();

	private final Set<Integer> stockRecordIds = new HashSet<>();

	public DimensionGroupSubRowBucket(@NonNull final DimensionSpecGroup dimensionSpecGroup)
	{
		this.dimensionSpecGroup = dimensionSpecGroup;
	}

	public void addCockpitRecord(@NonNull final I_MD_Cockpit cockpitRecord)
	{
		final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

		final I_C_UOM uom = uomDAO.getById(cockpitRecord.getM_Product().getC_UOM_ID());

		pmmQtyPromised = addToNullable(pmmQtyPromised, cockpitRecord.getPMM_QtyPromised_OnDate(), uom);
		qtyMaterialentnahme = addToNullable(qtyMaterialentnahme, cockpitRecord.getQtyMaterialentnahme(), uom);
		qtyRequiredForProduction = addToNullable(qtyRequiredForProduction, cockpitRecord.getQtyRequiredForProduction(), uom);
		qtyReservedPurchase = addToNullable(qtyReservedPurchase, cockpitRecord.getQtyReserved_Purchase(), uom);
		qtyReservedSale = addToNullable(qtyReservedSale, cockpitRecord.getQtyReserved_Sale(), uom);
		qtyAvailableToPromiseEstimate = addToNullable(qtyAvailableToPromiseEstimate, cockpitRecord.getQtyAvailableToPromiseEstimate(), uom);

		cockpitRecordIds.add(cockpitRecord.getMD_Cockpit_ID());
	}

	public void addStockRecord(@NonNull final I_MD_Stock stockRecord)
	{
		final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

		final I_C_UOM uom = uomDAO.getById(stockRecord.getM_Product().getC_UOM_ID());

		qtyOnHandStock = addToNullable(qtyOnHandStock, stockRecord.getQtyOnHand(), uom);

		stockRecordIds.add(stockRecord.getMD_Stock_ID());
	}

	public MaterialCockpitRow createIncludedRow(@NonNull final MainRowWithSubRows mainRowBucket)
	{
		final MainRowBucketId productIdAndDate = assumeNotNull(
				mainRowBucket.getProductIdAndDate(),
				"productIdAndDate may not be null; mainRowBucket={}", mainRowBucket);

		return MaterialCockpitRow.attributeSubRowBuilder()
				.date(productIdAndDate.getDate())
				.productId(productIdAndDate.getProductId().getRepoId())

				.dimensionGroup(dimensionSpecGroup)
				.pmmQtyPromised(getPmmQtyPromised())
				.qtyMaterialentnahme(getQtyMaterialentnahme())
				.qtyRequiredForProduction(getQtyRequiredForProduction())
				.qtyReservedPurchase(getQtyReservedPurchase())
				.qtyReservedSale(getQtyReservedSale())
				.qtyOnHandStock(getQtyOnHandStock())
				.allIncludedCockpitRecordIds(cockpitRecordIds)
				.allIncludedStockRecordIds(stockRecordIds)
				.build();
	}
}
