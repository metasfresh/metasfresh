package de.metas.materialtracking.qualityBasedInvoicing.impl;

/*
 * #%L
 * de.metas.materialtracking
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

import java.math.BigDecimal;

import org.adempiere.pricing.api.IEditablePricingContext;
import org.adempiere.pricing.api.IPricingBL;
import org.adempiere.pricing.api.IPricingContext;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;

import de.metas.materialtracking.qualityBasedInvoicing.IVendorInvoicingInfo;

/**
 * Helper class used to create the inital pricing context when invoicing.
 *
 * @author tsa
 *
 */
public class PricingContextBuilder
{
	// Services
	private final IPricingBL pricingBL = Services.get(IPricingBL.class);

	//
	// Parameters
	private IVendorInvoicingInfo _vendorInvoicingInfo = null;

	public PricingContextBuilder setVendorInvoicingInfo(final IVendorInvoicingInfo vendorInvoicingInfo)
	{
		Check.assumeNotNull(vendorInvoicingInfo, "Param 'vendorInvoicingInfo' not null");

		// these checks are also intended to guard against poorly set up AITs
		final I_M_PriceList_Version plv = vendorInvoicingInfo.getM_PriceList_Version();
		Check.assumeNotNull(plv,
				"Param 'vendorInvoicingInfo.M_PriceList_Version' not null; vendorInvoicingInfo={}", vendorInvoicingInfo);
		final I_M_PriceList pl = plv.getM_PriceList();
		Check.assumeNotNull(pl,
				"Param 'vendorInvoicingInfo.M_PriceList_Version.M_PriceList' not null; vendorInvoicingInfo={}", vendorInvoicingInfo);
		Check.assumeNotNull(pl.getM_PricingSystem(),
				"Param 'vendorInvoicingInfo.M_PriceList_Version.M_PriceList.M_PricingSystem' not null; vendorInvoicingInfo={}", vendorInvoicingInfo);

		_vendorInvoicingInfo = vendorInvoicingInfo;
		return this;
	}

	/**
	 * @return vendor invoicing info; never return null
	 */
	private final IVendorInvoicingInfo getVendorInvoicingInfo()
	{
		Check.assumeNotNull(_vendorInvoicingInfo, "_vendorInvoicingInfo not null");
		return _vendorInvoicingInfo;
	}

	/**
	 * Creates {@link IPricingContext} for parameters set.
	 *
	 * Following informations will be set:
	 * <ul>
	 * <li>bill bpartner
	 * <li>pricing system and price list
	 * <li>currency
	 * <li>IsSOTrx to <code>false</code>
	 * <li>Price Date - as quality order's production date
	 * <li>will NOT be set: product, qty, uom
	 * </ul>
	 *
	 * @return pricing context; never return null.
	 */
	public final IPricingContext create()
	{
		//
		// Create Pricing Context
		final IEditablePricingContext pricingCtx = pricingBL.createInitialContext(
				-1, // M_Product_ID - will be set later
				-1, // billBPartnerId - will be set later
				-1, // C_UOM_ID - will be set later
				BigDecimal.ZERO, // Qty - will be set later
				false // IsSOTrx=false => we are on purchasing side
				);

		//
		// Update pricing context
		updatePricingContextFromVendorInvoicingInfo(pricingCtx);

		return pricingCtx;
	}

	/**
	 * Updates the pricing context from original invoice candidate. Following informations will be set:
	 * <ul>
	 * <li>bill bpartner
	 * <li>pricing system and price list
	 * <li>currency
	 * <li>IsSOTrx to <code>false</code>
	 * <li>will NOT be set: product
	 * </ul>
	 *
	 * @param pricingCtx
	 */
	private void updatePricingContextFromVendorInvoicingInfo(final IEditablePricingContext pricingCtx)
	{
		final IVendorInvoicingInfo vendorInvoicingInfo = getVendorInvoicingInfo();

		//
		// Extract infos from original invoice candidate
		final int billBPartnerId = vendorInvoicingInfo.getBill_BPartner_ID();
		final int pricingSytemId = vendorInvoicingInfo.getM_PricingSystem().getM_PricingSystem_ID();
		final int currencyId = vendorInvoicingInfo.getC_Currency_ID();
		final I_M_PriceList_Version priceListVersion = vendorInvoicingInfo.getM_PriceList_Version();
		final boolean isSOTrx = false; // we are always on purchase side

		//
		// Update pricing context
		pricingCtx.setSOTrx(isSOTrx);
		pricingCtx.setC_BPartner_ID(billBPartnerId);
		pricingCtx.setC_Currency_ID(currencyId);
		pricingCtx.setM_PricingSystem_ID(pricingSytemId);
		pricingCtx.setM_PriceList_Version_ID(priceListVersion.getM_PriceList_Version_ID());
		pricingCtx.setPriceDate(priceListVersion.getValidFrom()); // just to drive home this point
	}

}
