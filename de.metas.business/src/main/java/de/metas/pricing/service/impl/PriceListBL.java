package de.metas.pricing.service.impl;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Iterator;

import javax.annotation.Nullable;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.currency.CurrencyPrecision;
import de.metas.currency.ICurrencyDAO;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListBL;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.util.Services;
import lombok.NonNull;

@SuppressWarnings("unused")
public class PriceListBL implements IPriceListBL
{

	private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);

	@Override
	public CurrencyPrecision getPricePrecision(@NonNull final PriceListId priceListId)
	{
		final I_M_PriceList priceList = priceListDAO.getById(priceListId);
		return CurrencyPrecision.ofInt(priceList.getPricePrecision());
	}

	@Override
	public CurrencyPrecision getAmountPrecision(@NonNull final PriceListId priceListId)
	{
		final I_M_PriceList priceList = priceListDAO.getById(priceListId);
		if (priceList.isRoundNetAmountToCurrencyPrecision())
		{
			return Services.get(ICurrencyDAO.class).getStdPrecision(CurrencyId.ofRepoId(priceList.getC_Currency_ID()));
		}
		else
		{
			return CurrencyPrecision.ofInt(priceList.getPricePrecision());
		}
	}

	@Override
	public CurrencyPrecision getTaxPrecision(@NonNull final PriceListId priceListId)
	{
		final I_M_PriceList priceList = priceListDAO.getById(priceListId);
		return Services.get(ICurrencyDAO.class).getStdPrecision(CurrencyId.ofRepoId(priceList.getC_Currency_ID()));
	}

	@Nullable @Override
	public I_M_PriceList getCurrentPricelistOrNull(
			final PricingSystemId pricingSystemId,
			final CountryId countryId,
			final LocalDate date,
			@NonNull final SOTrx soTrx)
	{
		final Boolean processedPLVFiltering = null;
		final I_M_PriceList_Version currentVersion = getCurrentPriceListVersionOrNull(pricingSystemId, countryId, date, soTrx, processedPLVFiltering);
		if (currentVersion == null)
		{
			return null;
		}

		return InterfaceWrapperHelper.create(currentVersion.getM_PriceList(), I_M_PriceList.class);
	}

	@SuppressWarnings("UnusedAssignment") @Nullable @Override
	public I_M_PriceList_Version getCurrentPriceListVersionOrNull(
			final PricingSystemId pricingSystemId,
			final CountryId countryId,
			@NonNull final LocalDate date,
			final SOTrx soTrx,
			@Nullable final Boolean processedPLVFiltering)
	{
		if (countryId == null)
		{
			return null;
		}

		if (pricingSystemId == null)
		{
			return null;
		}

		final Iterator<I_M_PriceList> pricelists = priceListDAO.retrievePriceLists(pricingSystemId, countryId, soTrx)
				.iterator();
		if (!pricelists.hasNext())
		{
			return null;
		}

		// This will be the most "fresh" pricelist (check the closest dateFrom)
		I_M_PriceList currentPricelist = null;

		Timestamp currentValidFrom = null;
		I_M_PriceList_Version lastPriceListVersion = null;

		currentPricelist = pricelists.next();

		lastPriceListVersion = priceListDAO.retrievePriceListVersionOrNull(currentPricelist, date, processedPLVFiltering);

		if (lastPriceListVersion != null)
		{
			currentValidFrom = lastPriceListVersion.getValidFrom();
		}

		while (pricelists.hasNext())
		{
			final I_M_PriceList priceListToCheck = pricelists.next();

			final I_M_PriceList_Version plvToCkeck = priceListDAO.retrievePriceListVersionOrNull(priceListToCheck, date, processedPLVFiltering);

			if (plvToCkeck == null)
			{
				// there may the case of no version fitting our requirements
				continue;
			}
			final Timestamp dateToCheck = plvToCkeck.getValidFrom();

			if (lastPriceListVersion == null || currentValidFrom.before(dateToCheck))
			{
				currentPricelist = priceListToCheck;
				currentValidFrom = dateToCheck;
				lastPriceListVersion = plvToCkeck;

			}
		}

		return lastPriceListVersion;
	}
}
