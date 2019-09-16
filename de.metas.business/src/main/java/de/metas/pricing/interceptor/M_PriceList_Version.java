package de.metas.pricing.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Interceptor(I_M_PriceList_Version.class)
@Component
public class M_PriceList_Version
{
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void assertBasePricingIsValid(final I_M_PriceList_Version plv)
	{
		final IPriceListDAO priceListsRepo = Services.get(IPriceListDAO.class);

		final PriceListVersionId basePriceListVersionId = priceListsRepo.getBasePriceListVersionIdForPricingCalculationOrNull(plv);
		if (basePriceListVersionId != null)
		{
			final I_M_PriceList basePriceList = priceListsRepo.getPriceListByPriceListVersionId(basePriceListVersionId);

			final PriceListId priceListId = PriceListId.ofRepoId(plv.getM_PriceList_ID());
			final I_M_PriceList priceList = priceListsRepo.getById(priceListId);

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
	}


}
