/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.pricing.service;

import com.google.common.collect.ImmutableList;
import de.metas.pricing.ProductPriceId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.I_M_ProductScalePrice;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProductScalePriceRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public List<ScaleProductPrice> retrieveScalePrices(@NonNull final ProductPriceId productPriceId)
	{
		return queryBL.createQueryBuilder(I_M_ProductScalePrice.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ProductScalePrice.COLUMN_M_ProductPrice_ID, productPriceId)
				.create()
				.stream()
				.map(ProductScalePriceRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	private static ScaleProductPrice fromRecord(@NonNull final I_M_ProductScalePrice record)
	{
		return ScaleProductPrice.builder()
				.priceStd(record.getPriceStd())
				.priceLimit(record.getPriceLimit())
				.priceList(record.getPriceList())
				.quantity(record.getQty())
				.build();
	}

}
