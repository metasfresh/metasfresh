package de.metas.ui.web.material.cockpit.rowfactory;

import java.math.BigDecimal;

import de.metas.material.dispo.model.I_MD_Cockpit;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Getter
public class MainRowBucket
{
	private BigDecimal qtyOnHand = BigDecimal.ZERO;

	// Zusage Lieferant
	private BigDecimal pmmQtyPromised = BigDecimal.ZERO;

	private BigDecimal qtyReserved = BigDecimal.ZERO;

	private BigDecimal qtyOrdered = BigDecimal.ZERO;

	private BigDecimal qtyMaterialentnahme = BigDecimal.ZERO;

	// MRP MEnge
	private BigDecimal qtyMrp = BigDecimal.ZERO;

	// zusagbar Zaehlbestand
	private BigDecimal qtyPromised = BigDecimal.ZERO;

	public void addDataRecord(@NonNull final I_MD_Cockpit dataRecord)
	{
		pmmQtyPromised = pmmQtyPromised.add(dataRecord.getPMM_QtyPromised_OnDate());
		qtyMaterialentnahme = qtyMaterialentnahme.add(dataRecord.getQtyMaterialentnahme());
		qtyMrp = qtyMrp.add(dataRecord.getQtyRequiredForProduction());
		qtyOrdered = qtyOrdered.add(dataRecord.getQtyReserved_Purchase());
		qtyReserved = qtyReserved.add(dataRecord.getQtyReserved_Sale());
		qtyPromised = qtyPromised.add(dataRecord.getQtyAvailableToPromise());

		qtyOnHand = qtyOnHand.add(dataRecord.getQtyOnHandEstimate());
	}
}
