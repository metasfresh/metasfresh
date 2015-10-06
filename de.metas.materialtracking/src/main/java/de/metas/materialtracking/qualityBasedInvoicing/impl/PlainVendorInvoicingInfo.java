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


import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.materialtracking.qualityBasedInvoicing.IVendorInvoicingInfo;

public class PlainVendorInvoicingInfo implements IVendorInvoicingInfo
{
	private int Bill_BPartner_ID = -1;
	private int Bill_Location_ID = -1;
	private int Bill_User_ID = -1;
	private int C_Currency_ID = -1;
	private I_C_Flatrate_Term C_Flatrate_Term;
	private String invoiceRule;
	private int M_PricingSystem_ID = -1;

	@Override
	public String toString()
	{
		return "PlainVendorInvoicingInfo ["
				+ "Bill_BPartner_ID=" + Bill_BPartner_ID
				+ ", Bill_Location_ID=" + Bill_Location_ID
				+ ", Bill_User_ID=" + Bill_User_ID
				+ ", C_Currency_ID=" + C_Currency_ID
				+ ", C_Flatrate_Term=" + C_Flatrate_Term
				+ ", invoiceRule=" + invoiceRule
				+ ", M_PricingSystem_ID=" + M_PricingSystem_ID
				+ "]";
	}

	@Override
	public int getBill_BPartner_ID()
	{
		return Bill_BPartner_ID;
	}

	public void setBill_BPartner_ID(final int bill_BPartner_ID)
	{
		Bill_BPartner_ID = bill_BPartner_ID;
	}

	@Override
	public int getBill_Location_ID()
	{
		return Bill_Location_ID;
	}

	public void setBill_Location_ID(final int bill_Location_ID)
	{
		Bill_Location_ID = bill_Location_ID;
	}

	@Override
	public int getBill_User_ID()
	{
		return Bill_User_ID;
	}

	public void setBill_User_ID(final int bill_User_ID)
	{
		Bill_User_ID = bill_User_ID;
	}

	@Override
	public int getC_Currency_ID()
	{
		return C_Currency_ID;
	}

	public void setC_Currency_ID(final int c_Currency_ID)
	{
		C_Currency_ID = c_Currency_ID;
	}

	@Override
	public I_C_Flatrate_Term getC_Flatrate_Term()
	{
		return C_Flatrate_Term;
	}

	public void setC_Flatrate_Term(final I_C_Flatrate_Term c_Flatrate_Term)
	{
		C_Flatrate_Term = c_Flatrate_Term;
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
	public int getM_PricingSystem_ID()
	{
		return M_PricingSystem_ID;
	}

	public void setM_PricingSystem_ID(final int m_PricingSystem_ID)
	{
		M_PricingSystem_ID = m_PricingSystem_ID;
	}

	/**
	 * This method does nothing!
	 */
	@Override
	public void add(final I_C_Invoice_Candidate IGNORED)
	{
	}
}
