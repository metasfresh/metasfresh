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
import java.util.Iterator;
import java.util.Properties;

import org.adempiere.model.IContextAware;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_M_DiscountSchemaLine;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Product;
import org.compiere.model.MDiscountSchemaLine;

import de.metas.adempiere.model.I_M_PriceList;
import de.metas.adempiere.model.I_M_ProductPrice;

/**
 * @author RC
 *
 */
public interface IPriceListBL extends ISingletonService
{
	/**
	 * @param pricingSystem
	 * @param partnerLocation
	 * @param date
	 * @param isSOTrx: true is SO, false if PO
	 *
	 * @return the current pricelist for vendor if any (for the giver pricing system), null otherwise
	 */
	I_M_PriceList getCurrentPricelistOrNull(I_M_PricingSystem pricingSystem, I_C_BPartner_Location partnerLocation, Timestamp date, boolean isSOTrx);

	/**
	 * This method is called from <code>org.compiere.process.M_PriceList_Create</code> do to additional things. Among some hardcoded things, it invokes the {@link IPlvCreationListener}s that were
	 * previously registered.
	 * <p>
	 * Note that the concrete motivation behind this method is to replace the jboss-aop aspect <code>de.metas.adempiere.aop.PriceListCreate</code> which we depended on to do scale price and attribute
	 * price related things when a new price list is created.
	 *
	 * @param ctxAware
	 * @param oldProductPrices
	 * @param targetPriceListVersion
	 * @param dsl
	 * @param adPinstanceId
	 *
	 * @task http://dewiki908/mediawiki/index.php/07286_get_rid_of_jboss-aop_for_good_%28104432455599%29
	 */
	void finishPlvCreation(IContextAware ctxAware, Iterator<I_M_ProductPrice> oldProductPrices, I_M_PriceList_Version targetPriceListVersion, I_M_DiscountSchemaLine dsl, int adPinstanceId);

	/**
	 * Adds another listener to be called from within {@link #finishPlvCreation(Properties, Iterator, I_M_PriceList_Version, MDiscountSchemaLine, int, String)}.
	 *
	 * @param l
	 * @see #finishPlvCreation(IContextAware, Iterator, I_M_PriceList_Version, MDiscountSchemaLine, int)
	 */
	void addPlvCreationListener(IPlvCreationListener l);

	/**
	 *
	 * @see IPriceListBL#addPlvCreationListener(IPlvCreationListener)
	 * @see IPriceListBL#finishPlvCreation(IContextAware, Iterator, I_M_PriceList_Version, MDiscountSchemaLine, int)
	 * @see #finishPlvCreation(IContextAware, Iterator, I_M_PriceList_Version, MDiscountSchemaLine, int)
	 *
	 */
	interface IPlvCreationListener
	{
		/**
		 * Contains the former jboss-aop advices' code (and ofc future implementations can contain new code ^^)
		 *
		 * @param ctxAware
		 * @param targetPriceListVersion
		 * @param oldProductPrices
		 * @param dsl
		 * @param adPinstanceId
		 */
		void onPlvCreation(IContextAware ctxAware,
				I_M_PriceList_Version targetPriceListVersion,
				Iterator<I_M_ProductPrice> oldProductPrices,
				I_M_DiscountSchemaLine dsl,
				int adPinstanceId);

		/**
		 *
		 * @return a seqNo to establish an order in which the registered listeners are invoked.
		 */
		int getExecutionOrderSeqNo();
	}

	/**
	 * Find the product price from the freshest plv of a pricing system.
	 *
	 * @param contextProvider
	 * @param pricingSystem
	 * @param product - the product we need the price for
	 * @param location - used for getting the country we search the plv for
	 * @param isSOTrx - true is sales, false if purchase
	 *
	 * @return the price if found, null otherwise
	 */
	I_M_ProductPrice getCurrentProductPrice(IContextAware contextProvider, I_M_PricingSystem pricingSystem, I_M_Product product, I_C_BPartner_Location location, boolean isSOTrx);
}
