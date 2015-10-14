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

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.pricing.exceptions.PriceListVersionNotFoundException;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_M_DiscountSchemaLine;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;

import de.metas.adempiere.model.I_M_PriceList;
import de.metas.adempiere.model.I_M_ProductPrice;

public interface IPriceListDAO extends ISingletonService
{
	void updateScalePrices(I_M_PriceList_Version plv, I_M_DiscountSchemaLine dsl, int adPinstanceId, String trxName);

	/**
	 * Updates following fields by taking them from base <code>plv</code>:
	 * <ul>
	 * <li> {@link I_M_ProductPrice#COLUMNNAME_C_TaxCategory_ID}
	 * <li> {@link I_M_ProductPrice#COLUMNNAME_UseScalePrice}
	 * <li> {@link I_M_ProductPrice#COLUMNNAME_IsSeasonFixedPrice}
	 * </ul>
	 *
	 * Only those products will be updated which have a record in T_Selection for given <code>adPInstanceId</code>.
	 *
	 * @param plv
	 * @param adPinstanceId
	 * @param trxName transaction name that shall be used for updating
	 */
	void updateTaxCategory(I_M_PriceList_Version plv, I_M_DiscountSchemaLine discountSchemaLine, int adPinstanceId, String trxName);

	I_M_PriceList retrievePriceList(Properties ctx, int priceListId);

	/**
	 * Retrieves the plv for the given price list and date. Never returns <code>null</code>
	 * 
	 * @param priceList
	 * @param date
	 * @return
	 * @throws PriceListVersionNotFoundException if there is no PLV for the given date and PL
	 */
	I_M_PriceList_Version retrievePriceListVersion(org.compiere.model.I_M_PriceList priceList, Date date);

	I_M_ProductPrice retrieveProductPrice(org.compiere.model.I_M_PriceList_Version plv, int productId);

	I_M_ProductPrice retrieveProductPriceOrNull(I_M_PriceList_Version plv, int productId);

	I_M_ProductPrice retrieveProductPriceOrNull(Properties ctx, int priceListVersionId, int productId, String trxName);

	Iterator<I_M_ProductPrice> retrieveAllProductPrices(I_M_PriceList_Version plv);

	IQueryBuilder<I_M_ProductPrice> retrieveProductPricesQuery(I_M_PriceList_Version plv);

	/**
	 * Retrieves <b>all</b> (including inactive) {@link I_M_ProductPrice} record of the given price list version
	 *
	 * @param plv
	 * @return iterator of {@link I_M_ProductPrice} ordered by SeqNo and Name
	 */
	Iterator<I_M_ProductPrice> retrieveAllProductPricesOrderedBySeqNOandProductName(I_M_PriceList_Version plv);

	/**
	 * Returns a list containing all the PO price lists for a given pricing system and a country
	 *
	 * @param pricingSystem
	 * @param country
	 * @param isSoTrx true is sales, false if purchase
	 * @return
	 */
	Iterator<I_M_PriceList> retrievePriceLists(I_M_PricingSystem pricingSystem, I_C_Country country, boolean isSOTrx);

	/**
	 * Retrieve the latest (according to the date parameter) version for the given pricelist
	 *
	 * @param pricelist
	 * @param date
	 * @return
	 */
	I_M_PriceList_Version retrieveLastVersion(I_M_PriceList pricelist, Timestamp date);

	/**
	 * Creates query filter for the given <code>dsl</code>. The includes the following column-values form the given dsl (if they are set):
	 * <ul>
	 * <li><code>M_Product_ID</code>
	 * <li><code>M_Product_Category_ID</code>
	 * <li><code>C_TaxCategory_ID</code>
	 * </ul>
	 * 
	 * @param dsl
	 * @return
	 */
	IQueryFilter<I_M_ProductPrice> createQueryFilter(I_M_DiscountSchemaLine dsl);

	IQueryFilter<I_M_ProductPrice> createProductPriceQueryFilterForProductInSelection(int adPInstanceId);
}
