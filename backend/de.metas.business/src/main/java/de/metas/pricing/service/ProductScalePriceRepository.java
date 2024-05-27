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

import de.metas.pricing.ProductPriceId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.I_M_ProductScalePrice;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

@Repository
public class ProductScalePriceRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public ScaleProductPriceList retrieveScalePrices(@NonNull final ProductPriceId productPriceId)
	{
		return queryBL.createQueryBuilder(I_M_ProductScalePrice.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ProductScalePrice.COLUMN_M_ProductPrice_ID, productPriceId)
				.create()
				.stream()
				.map(ProductScalePriceRepository::fromRecord)
				.collect(ScaleProductPriceList.collect());
	}

	private static ScaleProductPrice fromRecord(@NonNull final I_M_ProductScalePrice record)
	{
		return ScaleProductPrice.builder()
				.quantityMin(record.getQty())
				//
				.priceStd(record.getPriceStd())
				.priceLimit(record.getPriceLimit())
				.priceList(record.getPriceList())
				.build();
	}

	private static void updateRecord(final I_M_ProductScalePrice record, final ScaleProductPrice from)
	{
		record.setQty(from.getQuantityMin());
		record.setPriceLimit(from.getPriceLimit());
		record.setPriceList(from.getPriceList());
		record.setPriceStd(from.getPriceStd());
	}

	public void deleteByProductPriceId(@NonNull final ProductPriceId productPriceId)
	{
		queryBL.createQueryBuilder(I_M_ProductScalePrice.class)
				.addEqualsFilter(I_M_ProductScalePrice.COLUMNNAME_M_ProductPrice_ID, productPriceId)
				.create()
				.delete();
	}

	public void createNew(final ProductPriceId productPriceId, final ScaleProductPrice scalePrice)
	{
		final I_M_ProductScalePrice record = InterfaceWrapperHelper.newInstance(I_M_ProductScalePrice.class);
		record.setM_ProductPrice_ID(productPriceId.getRepoId());
		updateRecord(record, scalePrice);
	}
}
