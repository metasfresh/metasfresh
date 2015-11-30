package de.metas.materialtracking.qualityBasedInvoicing.ic.spi.impl;

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

import java.util.ArrayList;
import java.util.List;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;

import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.flatrate.model.I_C_Invoice_Clearing_Alloc;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityInspectionHandlerDAO;
import de.metas.materialtracking.qualityBasedInvoicing.IVendorInvoicingInfo;

/**
 * Wraps an {@link I_C_Invoice_Candidate} and make it behave like {@link IVendorInvoicingInfo}.
 *
 * @author tsa
 *
 */
/* package */class InvoiceCandidateAsVendorInvoicingInfo implements IVendorInvoicingInfo
{
	// Services
	private final IQualityInspectionHandlerDAO qualityInspectionHandlerDAO = Services.get(IQualityInspectionHandlerDAO.class);

	// Parameters
	private final List<I_C_Invoice_Candidate> invoiceCandidates = new ArrayList<I_C_Invoice_Candidate>();

	// Loaded values
	private int _pricingSystemId = -1;
	private I_M_PricingSystem _pricingSystem = null;
	private I_C_Flatrate_Term _flatrateTerm = null;
	private String _invoiceRule = null;
	private boolean _invoiceRuleSet = false;

	private I_M_PriceList_Version _priceListVersion = null;

	public InvoiceCandidateAsVendorInvoicingInfo()
	{
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
		return invoiceCandidates.get(0).getBill_BPartner_ID();
	}

	@Override
	public int getBill_Location_ID()
	{
		return invoiceCandidates.get(0).getBill_Location_ID();
	}

	@Override
	public int getBill_User_ID()
	{
		return invoiceCandidates.get(0).getBill_User_ID();
	}

	@Override
	public int getC_Currency_ID()
	{
		return invoiceCandidates.get(0).getC_Currency_ID();
	}

	@Override
	public I_M_PriceList_Version getM_PriceList_Version()
	{
		return _priceListVersion;
	}

	@Override
	public int getM_PricingSystem_ID()
	{
		if (_pricingSystemId <= 0)
		{
			loadPricingSystem();
		}
		return _pricingSystemId;
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
		final I_C_Flatrate_Term flatrateTerm = getC_Flatrate_Term();
		final int contractPricingSystemID = flatrateTerm.getM_PricingSystem_ID();

		if (contractPricingSystemID > 0)
		{
			_pricingSystemId = contractPricingSystemID;
			_pricingSystem = flatrateTerm.getM_PricingSystem();
		}
		else
		{
			// the contract doesn't come with an explicit pricing system, so use the original IC's pricing system.
			// note: we know the original IC is coming from a C_OrderLine
			final I_C_OrderLine orderLine = invoiceCandidates.get(0).getC_OrderLine();
			Check.assumeNotNull(orderLine, "orderLine not null");

			final I_C_Order order = orderLine.getC_Order();

			_pricingSystemId = order.getM_PricingSystem_ID();
			_pricingSystem = order.getM_PricingSystem();
		}
	}

	@Override
	public I_C_Flatrate_Term getC_Flatrate_Term()
	{
		if (_flatrateTerm == null)
		{
			final I_C_Invoice_Clearing_Alloc invoiceClearingAlloc = qualityInspectionHandlerDAO.retrieveInitialInvoiceClearingAlloc(invoiceCandidates.get(0));
			_flatrateTerm = invoiceClearingAlloc.getC_Flatrate_Term();
			Check.assumeNotNull(_flatrateTerm, "_flatrateTerm not null");
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
			String invoiceRule = flatrateTerm.getC_Flatrate_Conditions().getInvoiceRule();

			//
			// Fallback: get the InvoiceRule from original invoice candidate
			if (Check.isEmpty(invoiceRule, true))
			{
				invoiceRule = Services.get(IInvoiceCandBL.class).getInvoiceRule(invoiceCandidates.get(0));
			}

			_invoiceRule = invoiceRule;
			_invoiceRuleSet = true;
		}

		return _invoiceRule;
	}

	@Override
	public void add(final I_C_Invoice_Candidate invoiceCandidate)
	{
		Check.assumeNotNull(invoiceCandidate, "invoiceCandidate not null");
		Check.assume(!invoiceCandidate.isToRecompute(), "invoiceCandidate is valid (IsToRecompute=false)");
		invoiceCandidates.add(invoiceCandidate);

	}

	public void setM_PriceList_Version(final I_M_PriceList_Version priceListVersion)
	{
		_priceListVersion = priceListVersion;
	}
}
