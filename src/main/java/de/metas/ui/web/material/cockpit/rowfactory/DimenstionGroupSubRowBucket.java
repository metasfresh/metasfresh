package de.metas.ui.web.material.cockpit.rowfactory;

import java.math.BigDecimal;

import de.metas.dimension.DimensionSpecGroup;
import de.metas.material.dispo.model.I_MD_Cockpit;
import de.metas.ui.web.material.cockpit.MaterialCockpitRow;
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
public class DimenstionGroupSubRowBucket
{
	public static DimenstionGroupSubRowBucket create(@NonNull final DimensionSpecGroup dimensionSpecGroup)
	{
		return new DimenstionGroupSubRowBucket(dimensionSpecGroup);
	}

	private final DimensionSpecGroup dimensionSpecGroup;

	// Zusage Lieferant
	private BigDecimal pmmQtyPromised = BigDecimal.ZERO;

	private BigDecimal qtyReserved = BigDecimal.ZERO;

	private BigDecimal qtyOrdered = BigDecimal.ZERO;

	private BigDecimal qtyMaterialentnahme = BigDecimal.ZERO;

	// MRP MEnge
	private BigDecimal qtyMrp = BigDecimal.ZERO;

	// zusagbar Zaehlbestand
	private BigDecimal qtyPromised = BigDecimal.ZERO;

	public DimenstionGroupSubRowBucket(@NonNull final DimensionSpecGroup dimensionSpecGroup)
	{
		this.dimensionSpecGroup = dimensionSpecGroup;
	}

	public void addDataRecord(@NonNull final I_MD_Cockpit dataRecord)
	{
		pmmQtyPromised = pmmQtyPromised.add(dataRecord.getPMM_QtyPromised_OnDate());
		qtyMaterialentnahme = qtyMaterialentnahme.add(dataRecord.getQtyMaterialentnahme());
		qtyMrp = qtyMrp.add(dataRecord.getQtyRequiredForProduction());
		qtyOrdered = qtyOrdered.add(dataRecord.getQtyReserved_Purchase());
		qtyReserved = qtyReserved.add(dataRecord.getQtyReserved_Sale());
		qtyPromised = qtyPromised.add(dataRecord.getQtyAvailableToPromise());
	}

	public MaterialCockpitRow createIncludedRow(@NonNull final MainRowWithSubRows mainRowBucket)
	{
		final MainRowBucketId productIdAndDate = mainRowBucket.getProductIdAndDate();

		return MaterialCockpitRow.attributeSubRowBuilder()
				.date(productIdAndDate.getDate())
				.productId(productIdAndDate.getProductId())
				.dimensionGroup(dimensionSpecGroup)
				.pmmQtyPromised(getPmmQtyPromised())
				.qtyMaterialentnahme(getQtyMaterialentnahme())
				.qtyMrp(getQtyMrp())
				.qtyOrdered(getQtyOrdered())
				.qtyReserved(getQtyReserved())
				.build();
	}
}
