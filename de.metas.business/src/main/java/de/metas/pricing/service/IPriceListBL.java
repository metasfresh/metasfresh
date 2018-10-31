package de.metas.pricing.service;

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

import java.sql.Timestamp;

import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;

import de.metas.lang.SOTrx;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.util.ISingletonService;

/**
 * @author RC
 *
 */
public interface IPriceListBL extends ISingletonService
{
	int getPricePrecision(PriceListId priceListId);

	default int getPricePrecision(final int priceListId)
	{
		return getPricePrecision(PriceListId.ofRepoIdOrNull(priceListId));
	}

	/**
	 * @param pricingSystem
	 * @param countryId
	 * @param date
	 * @param soTrx
	 *
	 * @return the current price list for vendor if any (for the giver pricing system), null otherwise
	 */
	I_M_PriceList getCurrentPricelistOrNull(
			PricingSystemId pricingSystemId,
			int countryId,
			Timestamp date,
			SOTrx soTrx);

	/**
	 * Find the current version from a pricing system based on the given parameters.
	 *
	 * @param pricingSystem
	 * @param country
	 * @param date
	 * @param soTrx SO/PO or null
	 * @param processedPLVFiltering if not <code>null</code>, then only PLVs which have the give value in their <code>Processed</code> column are considered.
	 *            task 09533: the user doesn't know about PLV's processed flag, so in most cases we can't filter by it
	 * @return
	 */
	I_M_PriceList_Version getCurrentPriceListVersionOrNull(
			PricingSystemId pricingSystemId,
			int countryId,
			Timestamp date,
			SOTrx soTrx,
			Boolean processedPLVFiltering);
}
