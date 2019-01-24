package de.metas.pricing.service;

import java.time.LocalDate;

import org.adempiere.location.CountryId;
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
			CountryId countryId,
			LocalDate date,
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
			CountryId countryId,
			LocalDate date,
			SOTrx soTrx,
			Boolean processedPLVFiltering);
}
