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

import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.materialtracking.qualityBasedInvoicing.IVendorInvoicingInfo;

public class PlainVendorInvoicingInfo implements IVendorInvoicingInfo
{
	private int billBPartnerId = -1;
	private int billLocationId = -1;
	private int billUserId = -1;
	private int currencyId = -1;
	private I_C_Flatrate_Term flatrateTerm;
	private String invoiceRule;
	private int pricingSystemId = -1;
	private I_M_PricingSystem pricingSystem = null;
	private I_M_PriceList_Version priceListVersion;

	@Override
	public String toString()
	{
		return "PlainVendorInvoicingInfo ["
				+ "Bill_BPartner_ID=" + this.billBPartnerId
				+ ", Bill_Location_ID=" + billLocationId
				+ ", Bill_User_ID=" + billUserId
				+ ", C_Currency_ID=" + currencyId
				+ ", C_Flatrate_Term=" + flatrateTerm
				+ ", invoiceRule=" + invoiceRule
				+ ", M_PricingSystem_ID=" + pricingSystemId
				+ "]";
	}

	@Override
	public int getBill_BPartner_ID()
	{
		return billBPartnerId;
	}

	public void setBill_BPartner_ID(final int bill_BPartner_ID)
	{
		this.billBPartnerId = bill_BPartner_ID;
	}

	@Override
	public int getBill_Location_ID()
	{
		return billLocationId;
	}

	public void setBill_Location_ID(final int bill_Location_ID)
	{
		this.billLocationId = bill_Location_ID;
	}

	@Override
	public int getBill_User_ID()
	{
		return billUserId;
	}

	public void setBill_User_ID(final int bill_User_ID)
	{
		this.billUserId = bill_User_ID;
	}

	@Override
	public int getC_Currency_ID()
	{
		return currencyId;
	}

	public void setC_Currency_ID(final int c_Currency_ID)
	{
		this.currencyId = c_Currency_ID;
	}

	@Override
	public I_C_Flatrate_Term getC_Flatrate_Term()
	{
		return flatrateTerm;
	}

	public void setC_Flatrate_Term(final I_C_Flatrate_Term c_Flatrate_Term)
	{
		this.flatrateTerm = c_Flatrate_Term;
	}

	@Override
	public String getInvoiceRule()
	{
		return invoiceRule;
	}

	public void setInvoiceRule(final String invoiceRule)
	{
		this.invoiceRule = invoiceRule;
	}

	@Override
	public I_M_PricingSystem getM_PricingSystem()
	{
		return pricingSystem;
	}

	public void setM_PricingSystem(final I_M_PricingSystem m_PricingSystem)
	{
		this.pricingSystemId = m_PricingSystem.getM_PricingSystem_ID();
		this.pricingSystem = m_PricingSystem;
	}

	@Override
	public I_M_PriceList_Version getM_PriceList_Version()
	{
		return priceListVersion;
	}

	public void setM_PriceList_Version(final I_M_PriceList_Version m_PriceList_Version)
	{
		this.priceListVersion = m_PriceList_Version;
	}
}
