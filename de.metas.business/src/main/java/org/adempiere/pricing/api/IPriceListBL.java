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

import org.adempiere.model.IContextAware;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_M_DiscountSchemaLine;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;

/**
 * @author RC
 *
 */
public interface IPriceListBL extends ISingletonService
{
	/**
	 * @param pricingSystem
	 * @param country
	 * @param date
	 * @param isSOTrx: true is SO, false if PO
	 *
	 * @return the current pricelist for vendor if any (for the giver pricing system), null otherwise
	 */
	I_M_PriceList getCurrentPricelistOrNull(
			I_M_PricingSystem pricingSystem,
			I_C_Country country,
			Timestamp date,
			boolean isSOTrx);

	/**
	 * This method is called from <code>org.compiere.process.M_PriceList_Create</code> do to additional things. Among some hardcoded things, it invokes the {@link IPlvCreationListener}s that were
	 * previously registered.
	 * <p>
	 * Note that the concrete motivation behind this method is to replace the jboss-aop aspect <code>de.metas.adempiere.aop.PriceListCreate</code> which we depended on to do scale price and attribute
	 * price related things when a new price list is created.
	 *
	 * @param ctxAware
	 * @param targetPriceListVersion
	 * @param dsl
	 * @param adPinstanceId
	 *
	 * @task http://dewiki908/mediawiki/index.php/07286_get_rid_of_jboss-aop_for_good_%28104432455599%29
	 */
	void finishPlvCreation(IContextAware ctxAware, I_M_PriceList_Version targetPriceListVersion, I_M_DiscountSchemaLine dsl, int adPinstanceId);

	/**
	 * Adds another listener to be called from within {@link #finishPlvCreation(IContextAware, I_M_PriceList_Version, I_M_DiscountSchemaLine, int)}.
	 *
	 * @param listener
	 */
	void addPlvCreationListener(IPlvCreationListener listener);

	/**
	 *
	 * @see IPriceListBL#addPlvCreationListener(IPlvCreationListener)
	 * @see IPriceListBL#finishPlvCreation(IContextAware, I_M_PriceList_Version, I_M_DiscountSchemaLine, int)
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
				I_M_DiscountSchemaLine dsl,
				int adPinstanceId);

		/**
		 *
		 * @return a seqNo to establish an order in which the registered listeners are invoked.
		 */
		int getExecutionOrderSeqNo();
	}

	/**
	 * Find the current version from a pricing system based on the given parameters.
	 *
	 * @param pricingSystem
	 * @param country
	 * @param date
	 * @param isSoTrx
	 * @param processedPLVFiltering if not <code>null</code>, then only PLVs which have the give value in their <code>Processed</code> column are considered.
	 *            task 09533: the user doesn't know about PLV's processed flag, so in most cases we can't filter by it
	 * @return
	 */
	I_M_PriceList_Version getCurrentPriceListVersionOrNull(I_M_PricingSystem pricingSystem,
			I_C_Country country,
			Timestamp date,
			boolean isSoTrx,
			Boolean processedPLVFiltering);
}
