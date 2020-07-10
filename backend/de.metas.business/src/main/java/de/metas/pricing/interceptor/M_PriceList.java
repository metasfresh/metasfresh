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

package de.metas.pricing.interceptor;

import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.service.IPriceListBL;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_M_PriceList.class)
@Component
public class M_PriceList
{
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void assertBasePricingIsValid(final I_M_PriceList priceList)
	{
		final IPriceListDAO priceListsRepo = Services.get(IPriceListDAO.class);

		// final PriceListVersionId basePriceListVersionId = priceListsRepo.getBasePriceListVersionIdForPricingCalculationOrNull(plv);
		// if (basePriceListVersionId != null)
		// {

		final int basePriceListId = priceList.getBasePriceList_ID();
		if (basePriceListId <= 0)
		{
			// nothing to do
			return;
		}
		final I_M_PriceList basePriceList = priceListsRepo.getById(basePriceListId);

		//
		final CurrencyId baseCurrencyId = CurrencyId.ofRepoId(basePriceList.getC_Currency_ID());
		final CurrencyId currencyId = CurrencyId.ofRepoId(priceList.getC_Currency_ID());
		if (!CurrencyId.equals(baseCurrencyId, currencyId))
		{
			throw new AdempiereException("@PriceListAndBasePriceListCurrencyMismatchError@")
					.markAsUserValidationError();
		}

		final CountryId baseCountryId = CountryId.ofRepoIdOrNull(basePriceList.getC_Country_ID());
		final CountryId countryId = CountryId.ofRepoIdOrNull(priceList.getC_Country_ID());
		if (!CountryId.equals(baseCountryId, countryId))
		{
			throw new AdempiereException("@PriceListAndBasePriceListCountryMismatchError@")
					.markAsUserValidationError();
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = { I_M_PriceList.COLUMNNAME_Name })
	public void updatePLVName(@NonNull final I_M_PriceList priceList)
	{
		final IPriceListBL priceListBL = Services.get(IPriceListBL.class);
		priceListBL.updateAllPLVName(priceList.getName(), PriceListId.ofRepoId(priceList.getM_PriceList_ID()));
	}
}
