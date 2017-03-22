package org.adempiere.pricing.api;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_ProductPrice;

public interface IPriceListDAO extends ISingletonService
{
	I_M_PriceList retrievePriceList(Properties ctx, int priceListId);

	/**
	 * Retrieves <b>all</b> (including inactive) {@link I_M_ProductPrice} record of the given price list version
	 *
	 * @param plv
	 * @return iterator of {@link I_M_ProductPrice} ordered by SeqNo and Name
	 */
	Iterator<I_M_ProductPrice> retrieveAllProductPricesOrderedBySeqNOandProductName(I_M_PriceList_Version plv);

	/**
	 * Returns a list containing all the PO price lists for a given pricing system and a country.<br>
	 * The method returns both price lists with the given country and without any country. The price list
	 * which has a country (if any) is ordered first.
	 *
	 * @param pricingSystem
	 * @param country
	 * @param isSoTrx true is sales, false if purchase
	 * @return
	 */
	Iterator<I_M_PriceList> retrievePriceLists(I_M_PricingSystem pricingSystem, I_C_Country country, boolean isSOTrx);


	/**
	 * Retrieves the plv for the given price list and date. Never returns <code>null</code>
	 *
	 * @param priceList
	 * @param date
	 * @param processed optional, can be <code>null</code>. Allow to filter by <code>I_M_PriceList.Processed</code>
	 * @return
	 */
	I_M_PriceList_Version retrievePriceListVersionOrNull(org.compiere.model.I_M_PriceList priceList, Date date, Boolean processed);

	/**
	 * Retrieve the price list version that has <code>Processed='Y'</code> and and was valid before after the the given <code>plv</code>.
	 *
	 * @param plv
	 * @return
	 */
	I_M_PriceList_Version retrieveNextVersionOrNull(I_M_PriceList_Version plv);

	/**
	 * Retrieve the price list version that has <code>Processed='Y'</code> and and was valid before before the the given <code>plv</code> .
	 *
	 * @param plv
	 * @return
	 */
	I_M_PriceList_Version retrievePreviousVersionOrNull(I_M_PriceList_Version plv);
	
	/** @return next product price's MatchSeqNo */
	int retrieveNextMatchSeqNo(final I_M_ProductPrice productPrice);
}
