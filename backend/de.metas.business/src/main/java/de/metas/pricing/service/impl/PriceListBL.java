package de.metas.pricing.service.impl;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Optional;

import javax.annotation.Nullable;

import de.metas.common.util.time.SystemTime;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.cache.CacheMgt;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.ICurrencyDAO;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListBL;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;

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

public class PriceListBL implements IPriceListBL
{
	private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	private final ICurrencyDAO currenciesRepo = Services.get(ICurrencyDAO.class);

	@Override
	public CurrencyPrecision getPricePrecision(@NonNull final PriceListId priceListId)
	{
		final I_M_PriceList priceList = priceListDAO.getById(priceListId);
		return getPricePrecision(priceList);
	}

	private CurrencyPrecision getPricePrecision(final I_M_PriceList priceList)
	{
		return CurrencyPrecision.ofInt(priceList.getPricePrecision());
	}

	@Override
	public CurrencyPrecision getAmountPrecision(@NonNull final PriceListId priceListId)
	{
		final I_M_PriceList priceList = priceListDAO.getById(priceListId);

		final CurrencyPrecision taxPrecision = getTaxPrecision(priceList);

		if (priceList.isRoundNetAmountToCurrencyPrecision())
		{
			return taxPrecision;
		}
		else
		{
			final CurrencyPrecision pricePrecision = getPricePrecision(priceList);
			return pricePrecision.min(taxPrecision);
		}
	}

	@Override
	public CurrencyPrecision getTaxPrecision(@NonNull final PriceListId priceListId)
	{
		final I_M_PriceList priceList = priceListDAO.getById(priceListId);
		return getTaxPrecision(priceList);
	}

	private CurrencyPrecision getTaxPrecision(@NonNull final I_M_PriceList priceList)
	{
		return currenciesRepo.getStdPrecision(CurrencyId.ofRepoId(priceList.getC_Currency_ID()));
	}

	@Override
	public Optional<I_M_PriceList> getCurrentPriceList(
			@NonNull final PricingSystemId pricingSystemId,
			@NonNull final CountryId countryId,
			@NonNull final ZonedDateTime date,
			@NonNull final SOTrx soTrx)
	{
		final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);

		return getCurrentPriceListId(pricingSystemId, countryId, date, soTrx)
				.map(priceListDAO::getById);
	}

	@Override
	public Optional<PriceListId> getCurrentPriceListId(
			@NonNull final PricingSystemId pricingSystemId,
			@NonNull final CountryId countryId,
			@NonNull final ZonedDateTime date,
			@NonNull final SOTrx soTrx)
	{
		final Boolean processedPLVFiltering = null;
		final I_M_PriceList_Version currentVersion = getCurrentPriceListVersionOrNull(pricingSystemId, countryId, date, soTrx, processedPLVFiltering);
		return currentVersion != null
				? Optional.of(PriceListId.ofRepoId(currentVersion.getM_PriceList_ID()))
				: Optional.empty();
	}

	@SuppressWarnings("UnusedAssignment")
	@Nullable
	@Override
	public I_M_PriceList_Version getCurrentPriceListVersionOrNull(
			final PricingSystemId pricingSystemId,
			final CountryId countryId,
			@NonNull final ZonedDateTime date,
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

	@Override
	public String createPLVName(final @NonNull String priceListName, final @NonNull LocalDate date)
	{
		final String formattedDate = date.format(DateTimeFormatter.ISO_LOCAL_DATE);

		return priceListName + " " + formattedDate;
	}

	@Override
	public int updateAllPLVName(final String namePrefix, final PriceListId priceListId)
	{
		final String dateFormat = "YYYY-MM-DD"; // equivalent of DateTimeFormatter.ISO_LOCAL_DATE

		final UserId updatedBy = Env.getLoggedUserId();
		final Timestamp now = SystemTime.asTimestamp();

		final String sqlStr = ""
				+ " UPDATE " + I_M_PriceList_Version.Table_Name + " plv "
				+ " SET " + I_M_PriceList_Version.COLUMNNAME_Name + "      = ? || ' ' || to_char(plv." + I_M_PriceList_Version.COLUMNNAME_ValidFrom + ", '" + dateFormat + "'), "
				+ "     " + I_M_PriceList_Version.COLUMNNAME_UpdatedBy + " = ?, "
				+ "     " + I_M_PriceList_Version.COLUMNNAME_Updated + "   = ? "
				+ " WHERE plv." + I_M_PriceList_Version.COLUMNNAME_M_PriceList_ID + " = ? ";

		final int recordsUpdated = DB.executeUpdateEx(sqlStr, new Object[] { namePrefix, updatedBy, now, priceListId }, ITrx.TRXNAME_ThreadInherited);

		final CacheInvalidateMultiRequest cacheInvalidateRequest = CacheInvalidateMultiRequest.allChildRecords(I_M_PriceList.Table_Name, priceListId.getRepoId(), I_M_PriceList_Version.Table_Name);
		CacheMgt.get().reset(cacheInvalidateRequest);

		return recordsUpdated;
	}
}
