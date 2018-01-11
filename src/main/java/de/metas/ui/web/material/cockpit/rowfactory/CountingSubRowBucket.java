package de.metas.ui.web.material.cockpit.rowfactory;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.ui.web.material.cockpit.MaterialCockpitRow;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

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
@EqualsAndHashCode(of = "plantId")
@ToString
public class CountingSubRowBucket
{
	public static CountingSubRowBucket create(final int plantId)
	{
		return new CountingSubRowBucket(plantId);
	}

	private final int plantId;

	// Zaehlbestand
	private BigDecimal qtyOnHandEstimate = BigDecimal.ZERO;

	private BigDecimal qtyOnHandStock = BigDecimal.ZERO;

	private final Set<Integer> cockpitRecordIds = new HashSet<>();

	private final Set<Integer> stockRecordIds = new HashSet<>();

	public CountingSubRowBucket(final int plantId)
	{
		this.plantId = plantId;
	}

	public void addCockpitRecord(@NonNull final I_MD_Cockpit cockpitRecord)
	{
		qtyOnHandEstimate = qtyOnHandEstimate.add(cockpitRecord.getQtyOnHandEstimate());

		cockpitRecordIds.add(cockpitRecord.getMD_Cockpit_ID());
	}

	public void addStockRecord(@NonNull final I_MD_Stock stockRecord)
	{
		qtyOnHandStock = qtyOnHandStock.add(stockRecord.getQtyOnHand());

		stockRecordIds.add(stockRecord.getMD_Stock_ID());
	}

	public MaterialCockpitRow createIncludedRow(@NonNull final MainRowWithSubRows mainRowBucket)
	{
		final MainRowBucketId productIdAndDate = mainRowBucket.getProductIdAndDate();

		return MaterialCockpitRow.countingSubRowBuilder()
				.date(productIdAndDate.getDate())
				.productId(productIdAndDate.getProductId())
				.plantId(plantId)
				.qtyOnHandEstimate(qtyOnHandEstimate)
				.qtyOnHandStock(qtyOnHandStock)
				.allIncludedCockpitRecordIds(cockpitRecordIds)
				.allIncludedStockRecordIds(stockRecordIds)
				.build();
	}

}
