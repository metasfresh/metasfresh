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

import org.adempiere.util.Check;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.materialtracking.model.I_C_Invoice_Candidate;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.qualityBasedInvoicing.IVendorInvoicingInfo;

/**
 * Wraps an {@link I_C_Invoice_Candidate} and make it behave like {@link IVendorInvoicingInfo}.
 *
 * @author tsa
 */
/* package */class MaterialTrackingAsVendorInvoicingInfo implements IVendorInvoicingInfo
{
	// Loaded values
	private int _pricingSystemId = -1;
	private I_M_PricingSystem _pricingSystem = null;
	private I_C_Flatrate_Term _flatrateTerm = null;
	private String _invoiceRule = null;
	private boolean _invoiceRuleSet = false;

	private I_M_PriceList_Version _priceListVersion = null;
	private final I_M_Material_Tracking materialTracking;

	public MaterialTrackingAsVendorInvoicingInfo(I_M_Material_Tracking materialTracking)
	{
		this.materialTracking = materialTracking;
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + " ["
				+ "Bill_BPartner_ID=" + getBill_BPartner_ID()
				+ ", Bill_Location_ID=" + getBill_Location_ID()
				+ ", Bill_User_ID=" + getBill_User_ID()
				+ ", C_Currency_ID=" + getC_Currency_ID()
				+ ", C_Flatrate_Term=" + (_flatrateTerm == null ? "not-loaded" : _flatrateTerm)
				+ ", InvoiceRule=" + (_invoiceRuleSet ? _invoiceRule : "not-loaded")
				+ ", M_PricingSystem_ID=" + (_pricingSystemId < 0 ? "not-loaded" : _pricingSystemId)
				+ ", M_PriceList_Version_ID=" + (_priceListVersion == null ? "not-set" : _priceListVersion.getM_PriceList_Version_ID())
				+ "]";
	}

	@Override
	public int getBill_BPartner_ID()
	{
		return getC_Flatrate_Term().getBill_BPartner_ID();
	}

	@Override
	public int getBill_Location_ID()
	{
		return getC_Flatrate_Term().getBill_Location_ID();
	}

	@Override
	public int getBill_User_ID()
	{
		return getC_Flatrate_Term().getBill_User_ID();
	}

	@Override
	public int getC_Currency_ID()
	{
		return getM_PriceList_Version().getM_PriceList().getC_Currency_ID();
	}

	@Override
	public I_M_PriceList_Version getM_PriceList_Version()
	{
		return _priceListVersion;
	}

	@Override
	public I_M_PricingSystem getM_PricingSystem()
	{
		if (_pricingSystem == null)
		{
			loadPricingSystem();
		}
		return _pricingSystem;
	}

	private void loadPricingSystem()
	{
		_pricingSystemId = getC_Flatrate_Term().getM_PricingSystem_ID();
		_pricingSystem = getC_Flatrate_Term().getM_PricingSystem();
	}

	@Override
	public I_C_Flatrate_Term getC_Flatrate_Term()
	{
		if (_flatrateTerm == null)
		{
			_flatrateTerm = materialTracking.getC_Flatrate_Term();

			// shouldn't be null because we prevent even material-tracking purchase orders without a flatrate term.
			Check.errorIf(_flatrateTerm == null, "M_Material_Tracking {} has no flatrate term", materialTracking);
		}
		return _flatrateTerm;
	}

	@Override
	public String getInvoiceRule()
	{
		if (!_invoiceRuleSet)
		{
			//
			// Try getting the InvoiceRule from Flatrate Term
			final I_C_Flatrate_Term flatrateTerm = getC_Flatrate_Term();
			final String invoiceRule = flatrateTerm
					.getC_Flatrate_Conditions()
					.getInvoiceRule();

			Check.assumeNotEmpty(invoiceRule, "Unable to retrieve invoiceRule from materialTracking {}", materialTracking);
			_invoiceRule = invoiceRule;
			_invoiceRuleSet = true;
		}

		return _invoiceRule;
	}

	/* package */void setM_PriceList_Version(final I_M_PriceList_Version priceListVersion)
	{
		_priceListVersion = priceListVersion;
	}
}
