package de.metas.ui.web.material.cockpit.rowfactory;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.cockpit.model.I_MD_Stock;
import lombok.Getter;
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

@Getter
public class MainRowBucket
{
	private BigDecimal qtyOnHandEstimate = BigDecimal.ZERO;

	private BigDecimal qtyOnHand = BigDecimal.ZERO;

	// Zusage Lieferant
	private BigDecimal pmmQtyPromised = BigDecimal.ZERO;

	private BigDecimal qtyReservedSale = BigDecimal.ZERO;

	private BigDecimal qtyReservedPurchase = BigDecimal.ZERO;

	private BigDecimal qtyMaterialentnahme = BigDecimal.ZERO;

	// MRP MEnge
	private BigDecimal qtyRequiredForProduction = BigDecimal.ZERO;

	// zusagbar Zaehlbestand
	private BigDecimal qtyAvailableToPromise = BigDecimal.ZERO;

	private final Set<Integer> cockpitRecordIds = new HashSet<>();

	private final Set<Integer> stockRecordIds = new HashSet<>();

	public void addDataRecord(@NonNull final I_MD_Cockpit cockpitRecord)
	{
		pmmQtyPromised = pmmQtyPromised.add(cockpitRecord.getPMM_QtyPromised_OnDate());
		qtyMaterialentnahme = qtyMaterialentnahme.add(cockpitRecord.getQtyMaterialentnahme());
		qtyRequiredForProduction = qtyRequiredForProduction.add(cockpitRecord.getQtyRequiredForProduction());
		qtyReservedPurchase = qtyReservedPurchase.add(cockpitRecord.getQtyReserved_Purchase());
		qtyReservedSale = qtyReservedSale.add(cockpitRecord.getQtyReserved_Sale());
		qtyAvailableToPromise = qtyAvailableToPromise.add(cockpitRecord.getQtyAvailableToPromise());

		qtyOnHandEstimate = qtyOnHandEstimate.add(cockpitRecord.getQtyOnHandEstimate());

		cockpitRecordIds.add(cockpitRecord.getMD_Cockpit_ID());
	}

	public void addStockRecord(@NonNull final I_MD_Stock stockRecord)
	{
		qtyOnHand = qtyOnHand.add(stockRecord.getQtyOnHand());

		stockRecordIds.add(stockRecord.getMD_Stock_ID());
	}
}
