package de.metas.ui.web.material.cockpit.rowfactory;

import java.math.BigDecimal;

import de.metas.fresh.model.I_Fresh_QtyOnHand_Line;
import de.metas.ui.web.material.cockpit.MaterialCockpitRow;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(of = "plantId")
public class CountingSubRowBucket
{
	public static CountingSubRowBucket create(final int plantId)
	{
		return new CountingSubRowBucket(plantId);
	}

	private final int plantId;

	// Zaehlbestand
	private BigDecimal qtyOnHand = BigDecimal.ZERO;

	public CountingSubRowBucket(final int plantId)
	{
		this.plantId = plantId;
	}

	public void addDataRecord(@NonNull final I_Fresh_QtyOnHand_Line dataRecord)
	{
		qtyOnHand = qtyOnHand.add(dataRecord.getQtyCount());
	}

	public MaterialCockpitRow createIncludedRow(@NonNull final MaterialCockpitMainRowBucket materialCockpitMainRowBucket)
	{
		final MaterialCockpitMainRowId productIdAndDate = materialCockpitMainRowBucket.getProductIdAndDate();

		return MaterialCockpitRow.countingSubRowBuilder()
				.date(productIdAndDate.getDate())
				.productId(productIdAndDate.getProductId())
				.plantId(plantId)
				.qtyOnHand(getQtyOnHand())
				.build();

	}

}
