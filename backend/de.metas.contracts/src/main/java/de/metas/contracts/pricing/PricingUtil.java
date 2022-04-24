/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.pricing;

import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_M_PriceList;

import javax.annotation.Nullable;

@UtilityClass
public class PricingUtil
{
	@NonNull
	public static IEditablePricingContext copyCtxOverridePriceListAndRefObject(
			@NonNull final IPricingContext pricingCtx,
			@NonNull final I_M_PriceList priceList)
	{
		final IEditablePricingContext newPricingContext = pricingCtx.copy();

		newPricingContext.setReferencedObject(null);

		newPricingContext.setPriceListId(PriceListId.ofRepoId(priceList.getM_PriceList_ID()));
		newPricingContext.setPricingSystemId(PricingSystemId.ofRepoId(priceList.getM_PricingSystem_ID()));
		newPricingContext.setPriceListVersionId(null);

		final SOTrx soTrx = SOTrx.ofBoolean(priceList.isSOPriceList());
		newPricingContext.setSOTrx(soTrx);

		return newPricingContext;
	}

	@Nullable
	public static I_M_PriceList retrievePriceListForConditionsAndCountry(
			@NonNull final CountryId countryId,
			@NonNull final PricingSystemId pricingSystemId,
			@NonNull final SOTrx soTrx)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_PriceList.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_PriceList.COLUMN_C_Country_ID, countryId, null)
				.addEqualsFilter(I_M_PriceList.COLUMN_M_PricingSystem_ID, pricingSystemId.getRepoId())
				.addEqualsFilter(I_M_PriceList.COLUMN_IsSOPriceList, soTrx.toBoolean())
				.orderBy()
					.addColumnDescending(I_M_PriceList.COLUMNNAME_C_Country_ID)
				.endOrderBy()
				.create()
				.first();
	}
}
