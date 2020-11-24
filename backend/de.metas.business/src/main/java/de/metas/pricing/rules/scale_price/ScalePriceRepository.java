package de.metas.pricing.rules.scale_price;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.I_M_ProductScalePrice;
import org.springframework.stereotype.Repository;

import de.metas.pricing.ProductPriceId;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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
@Repository
public class ScalePriceRepository
{

	private static final IQueryBL queryBL = Services.get(IQueryBL.class);

	public Map<BigDecimal, BigDecimal> getScales (final ProductPriceId productPriceId )
	{

		final Map<BigDecimal, BigDecimal> scales = new HashMap<>();
		
		final List<I_M_ProductScalePrice> scaleRecords = retrieveScales(productPriceId);
		
		for(I_M_ProductScalePrice scaleRecord :scaleRecords)
		{
			final BigDecimal qty = scaleRecord.getQty();
			final BigDecimal amt =scaleRecord.getPriceStd();
			
			scales.put(qty, amt);
		}
		
		return scales;
	}

	private List<I_M_ProductScalePrice> retrieveScales(final ProductPriceId productPriceId)
	{
		return queryBL.createQueryBuilder(I_M_ProductScalePrice.class)
				.addEqualsFilter(I_M_ProductScalePrice.COLUMNNAME_M_ProductPrice_ID, productPriceId)
				.addOnlyActiveRecordsFilter()
				.orderByDescending(I_M_ProductScalePrice.COLUMNNAME_Qty)
				.create()
				.list();

	}
}
