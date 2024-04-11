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

import de.metas.bpartner.BPartnerId;
import de.metas.lang.SOTrx;
import de.metas.materialtracking.qualityBasedInvoicing.IVendorInvoicingInfo;
import de.metas.money.CurrencyId;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.IPricingBL;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;

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

	public PricingContextBuilder setVendorInvoicingInfo(@NonNull final IVendorInvoicingInfo vendorInvoicingInfo)
	{
		final IPriceListDAO priceListsRepo = Services.get(IPriceListDAO.class);
		// these checks are also intended to guard against poorly set up AITs
		final I_M_PriceList_Version plv = vendorInvoicingInfo.getM_PriceList_Version();
		Check.assumeNotNull(plv,
				"Param 'vendorInvoicingInfo.M_PriceList_Version' not null; vendorInvoicingInfo={}", vendorInvoicingInfo);

		final I_M_PriceList pl = plv.getM_PriceList();
		Check.assumeNotNull(pl,
				"Param 'vendorInvoicingInfo.M_PriceList_Version.M_PriceList' not null; vendorInvoicingInfo={}", vendorInvoicingInfo);

		final PricingSystemId pricingSystemId = PricingSystemId.ofRepoId(pl.getM_PricingSystem_ID());
		final I_M_PricingSystem pricingSystem = priceListsRepo.getPricingSystemById(pricingSystemId);
		Check.assumeNotNull(pricingSystem,
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
				-1, // AD_Org_ID - will be set later
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
		final BPartnerId billBPartnerId = vendorInvoicingInfo.getBill_BPartner_ID();
		final PricingSystemId pricingSytemId = vendorInvoicingInfo.getPricingSystemId();
		final CurrencyId currencyId = CurrencyId.ofRepoId(vendorInvoicingInfo.getC_Currency_ID());
		final I_M_PriceList_Version priceListVersion = vendorInvoicingInfo.getM_PriceList_Version();

		//
		// Update pricing context
		pricingCtx.setSOTrx(SOTrx.PURCHASE); // we are always on purchase side
		pricingCtx.setBPartnerId(billBPartnerId);
		pricingCtx.setCurrencyId(currencyId);
		pricingCtx.setPricingSystemId(pricingSytemId);
		pricingCtx.setPriceListVersionId(PriceListVersionId.ofRepoId(priceListVersion.getM_PriceList_Version_ID()));
		pricingCtx.setPriceDate(TimeUtil.asLocalDate(priceListVersion.getValidFrom())); // just to drive home this point
		pricingCtx.setCountryId(vendorInvoicingInfo.getCountryId());
	}

}
