package org.adempiere.pricing.api.impl;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IPriceListBL;
import org.adempiere.pricing.api.IPriceListDAO;
import org.adempiere.pricing.api.IPricingBL;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_M_DiscountSchemaLine;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Product;
import org.compiere.util.CLogger;

import de.metas.adempiere.model.I_M_PriceList;
import de.metas.adempiere.model.I_M_ProductPrice;

public class PriceListBL implements IPriceListBL
{
	private static final CLogger logger = CLogger.getCLogger(PriceListBL.class);

	@Override
	public I_M_PriceList getCurrentPricelistOrNull(final I_M_PricingSystem pricingSystem, final I_C_BPartner_Location partnerLocation, final Timestamp date, final boolean isSoTrx)
	{
		final I_M_PriceList_Version currentVersion = getLastPriceListVersionOrNull(pricingSystem, partnerLocation, date, isSoTrx);

		if (currentVersion == null)
		{
			return null;
		}

		final I_M_PriceList currentPricelist = InterfaceWrapperHelper.create(currentVersion.getM_PriceList(), I_M_PriceList.class);
		return currentPricelist;
	}

	private static final Comparator<IPlvCreationListener> listenerComparator = new Comparator<IPlvCreationListener>()
	{
		@Override
		public int compare(final IPlvCreationListener o1, final IPlvCreationListener o2)
		{
			if (o1.getExecutionOrderSeqNo() < o2.getExecutionOrderSeqNo())
			{
				return -1;
			}
			if (o1.getExecutionOrderSeqNo() == o2.getExecutionOrderSeqNo())
			{
				return 0;
			}
			return 1;
		}
	};

	@Override
	public void finishPlvCreation(final IContextAware ctxAware,
			final Iterator<I_M_ProductPrice> oldProductPrices,
			final I_M_PriceList_Version targetPriceListVersion,
			final I_M_DiscountSchemaLine dsl,
			final int adPinstanceId)
	{
		if (targetPriceListVersion.getM_Pricelist_Version_Base_ID() <= 0)
		{
			logger.info(targetPriceListVersion + " has M_Pricelist_Version_Base_ID=0; nothing to do");
			return;
		}

		final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
		priceListDAO.updateTaxCategory(targetPriceListVersion, dsl, adPinstanceId, ctxAware.getTrxName());
		priceListDAO.updateScalePrices(targetPriceListVersion, dsl, adPinstanceId, ctxAware.getTrxName());

		Collections.sort(plvCreationListeners, listenerComparator);

		//
		// Update Attribute pricing records
		for (final IPlvCreationListener l : plvCreationListeners)
		{
			l.onPlvCreation(
					ctxAware,
					targetPriceListVersion,
					oldProductPrices,
					dsl,
					adPinstanceId);
		}

		//
		// Retrieve new Product Prices
		final Iterator<I_M_ProductPrice> newProductPrices = priceListDAO.retrieveProductPricesQuery(targetPriceListVersion)
				// Only those M_ProducePrice records where M_ProductPrice.M_Product_ID is in our selection
				.filter(priceListDAO.createProductPriceQueryFilterForProductInSelection(adPinstanceId))
				// Only those M_ProducePrice records which are matching our Discount Schema Line
				.filter(priceListDAO.createQueryFilter(dsl))
				// Filter our M_ProductPrice records where IsSeasonFixedPrice=Y
				.addNotEqualsFilter(I_M_ProductPrice.COLUMNNAME_IsSeasonFixedPrice, true)
				// Iterate
				.create()
				.iterate(I_M_ProductPrice.class);

		//
		// Update tables that depend on product price based on a pricing rule.
		if (newProductPrices.hasNext())
		{
			Services.get(IPricingBL.class)
					.getAggregatedPricingRule(ctxAware.getCtx())
					.updateFromDiscounLine(targetPriceListVersion, newProductPrices, dsl);
		}
	}

	private final List<IPlvCreationListener> plvCreationListeners = new ArrayList<IPriceListBL.IPlvCreationListener>();

	@Override
	public void addPlvCreationListener(final IPlvCreationListener l)
	{
		plvCreationListeners.add(l);
	}

	@Override
	public I_M_ProductPrice getCurrentProductPrice(
			final IContextAware contextProvider,
			final I_M_PricingSystem pricingSystem,
			final I_M_Product product,
			final I_C_BPartner_Location location,
			final boolean isSOTrx)
	{
		final I_M_PriceList_Version currentVersion = getLastPriceListVersionOrNull(pricingSystem, location, SystemTime.asDayTimestamp(), isSOTrx);

		final I_M_ProductPrice productPrice = Services.get(IPriceListDAO.class)
				.retrieveProductPriceOrNull(
						currentVersion,
						product.getM_Product_ID());

		return productPrice;
	}

	/**
	 * find the newest version from a pricing system based on bp location
	 */
	private I_M_PriceList_Version getLastPriceListVersionOrNull(final I_M_PricingSystem pricingSystem, final I_C_BPartner_Location partnerLocation, final Timestamp date, final boolean isSoTrx)
	{
		if (partnerLocation == null)
		{
			return null;
		}

		final I_C_BPartner partner = partnerLocation.getC_BPartner();

		if (partner == null)
		{
			return null;
		}

		if (pricingSystem == null)
		{
			return null;
		}

		if (!partner.isVendor() && !isSoTrx)
		{
			return null;
		}

		if (!partner.isCustomer() && isSoTrx)
		{
			return null;
		}

		final I_C_Country country = partnerLocation.getC_Location().getC_Country();
		final Iterator<I_M_PriceList> pricelists = Services.get(IPriceListDAO.class).retrievePriceLists(pricingSystem, country, isSoTrx);

		if (pricelists == null)
		{
			return null;
		}

		// This will be the most "fresh" pricelist (check the closest dateFrom)
		I_M_PriceList currentPricelist = null;

		Timestamp currentValidFrom = null;

		I_M_PriceList_Version lastPriceListVersion = null;

		if (pricelists.hasNext())
		{
			currentPricelist = pricelists.next();
			lastPriceListVersion = Services.get(IPriceListDAO.class).retrieveLastVersion(currentPricelist, date);

			if (lastPriceListVersion != null)
			{
				currentValidFrom = lastPriceListVersion.getValidFrom();
			}
		}

		while (pricelists.hasNext())
		{
			final I_M_PriceList priceListToCheck = pricelists.next();

			final I_M_PriceList_Version plvToCkeck = Services.get(IPriceListDAO.class).retrieveLastVersion(priceListToCheck, date);

			if (plvToCkeck == null)
			{
				// there may the case of no version fitting our requirements
				continue;
			}
			final Timestamp dateToCheck = plvToCkeck.getValidFrom();

			if (currentValidFrom.before(dateToCheck))
			{
				currentPricelist = priceListToCheck;
				currentValidFrom = dateToCheck;
				lastPriceListVersion = plvToCkeck;

			}
		}

		return lastPriceListVersion;
	}
}
