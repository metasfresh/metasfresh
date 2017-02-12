package de.metas.invoicecandidate.api.impl;

/*
 * #%L
 * de.metas.swat.base
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
import java.sql.Timestamp;
import java.util.List;

import org.adempiere.util.Check;
import org.compiere.model.I_C_DocType;

import de.metas.invoicecandidate.api.IInvoiceCandAggregate;
import de.metas.invoicecandidate.api.IInvoiceHeader;
import de.metas.invoicecandidate.api.IInvoiceLineRW;

/**
 * Default implementation for {@link IInvoiceHeader}
 *
 * @author tsa
 *
 */
/* package */class InvoiceHeaderImpl implements IInvoiceHeader
{
	/** @return builder */
	public static final InvoiceHeaderImplBuilder builder()
	{
		return new InvoiceHeaderImplBuilder();
	}

	private List<IInvoiceCandAggregate> lines;

	private String docBaseType;

	private String poReference;

	private Timestamp dateInvoiced;

	private Timestamp dateAcct;

	private int AD_Org_ID;

	private int C_Order_ID;

	private int M_PriceList_ID;

	private int Bill_Location_ID;

	private int Bill_BPartner_ID;

	private int Bill_User_ID;

	// 03805: add attribute C_Currency_ID
	private int C_Currency_ID;

	// 04258
	private String Description;
	private String DescriptionBottom;

	private boolean isSOTrx;

	// 06630
	private int M_InOut_ID = -1;

	private I_C_DocType docTypeInvoice;

	private boolean taxIncluded;

	/* package */InvoiceHeaderImpl()
	{
		super();
	}

	@Override
	public String toString()
	{
		return "InvoiceHeaderImpl ["
				+ "docBaseType=" + docBaseType
				+ ", dateInvoiced=" + dateInvoiced
				+ ", AD_Org_ID=" + AD_Org_ID
				+ ", M_PriceList_ID=" + M_PriceList_ID
				+ ", isSOTrx=" + isSOTrx
				+ ", Bill_BPartner_ID=" + Bill_BPartner_ID
				+ ", Bill_Location_ID=" + Bill_Location_ID
				+ ", Bill_User_ID=" + Bill_User_ID
				+ ", C_Currency_ID=" + C_Currency_ID
				+ ", C_Order_ID=" + C_Order_ID
				+ ", docTypeInvoiceId=" + docTypeInvoice
				+ ", lines=" + lines
				+ "]";
	}

	@Override
	public List<IInvoiceCandAggregate> getLines()
	{
		return lines;
	}

	@Override
	public String getDocBaseType()
	{
		return docBaseType;
	}

	@Override
	public String getPOReference()
	{
		return poReference;
	}

	@Override
	public Timestamp getDateInvoiced()
	{
		return dateInvoiced;
	}

	@Override
	public Timestamp getDateAcct()
	{
		return dateAcct;
	}

	@Override
	public int getC_Order_ID()
	{
		return C_Order_ID;
	}

	@Override
	public int getM_PriceList_ID()
	{
		return M_PriceList_ID;
	}

	@Override
	public int getBill_Location_ID()
	{
		return Bill_Location_ID;
	}

	@Override
	public int getBill_BPartner_ID()
	{
		return Bill_BPartner_ID;
	}

	@Override
	public int getBill_User_ID()
	{
		return Bill_User_ID;
	}

	@Override
	public int getAD_Org_ID()
	{
		return AD_Org_ID;
	}

	@Override
	public int getC_Currency_ID()
	{
		return C_Currency_ID;
	}

	public void setLines(final List<IInvoiceCandAggregate> lines)
	{
		this.lines = lines;
	}

	public void setDocBaseType(final String docBaseType)
	{
		this.docBaseType = docBaseType;
	}

	public void setPOReference(final String poReference)
	{
		this.poReference = poReference;
	}

	public void setDateInvoiced(final Timestamp dateInvoiced)
	{
		this.dateInvoiced = dateInvoiced;
	}

	public void setDateAcct(final Timestamp dateAcct)
	{
		this.dateAcct = dateAcct;
	}

	public void setAD_Org_ID(final int ad_Org_ID)
	{
		AD_Org_ID = ad_Org_ID;
	}

	public void setC_Order_ID(final int c_Order_ID)
	{
		C_Order_ID = c_Order_ID;
	}

	public void setM_PriceList_ID(final int M_PriceList_ID)
	{
		this.M_PriceList_ID = M_PriceList_ID;
	}

	public void setBill_Location_ID(final int bill_Location_ID)
	{
		Bill_Location_ID = bill_Location_ID;
	}

	public void setBill_BPartner_ID(final int bill_BPartner_ID)
	{
		Bill_BPartner_ID = bill_BPartner_ID;
	}

	public void setBill_User_ID(final int bill_User_ID)
	{
		Bill_User_ID = bill_User_ID;
	}

	public void setC_Currency_ID(final int currency_ID)
	{
		C_Currency_ID = currency_ID;
	}

	@Override
	public String getDescriptionBottom()
	{
		return DescriptionBottom;
	}

	@Override
	public String getDescription()
	{
		return Description;
	}

	public void setDescription(final String description)
	{
		Description = description;
	}

	public void setDescriptionBottom(final String descriptionBottom)
	{
		DescriptionBottom = descriptionBottom;
	}

	@Override
	public boolean isSOTrx()
	{
		return isSOTrx;
	}

	public void setIsSOTrx(final boolean isSOTrx)
	{
		this.isSOTrx = isSOTrx;
	}

	@Override
	public int getM_InOut_ID()
	{
		return M_InOut_ID;
	}

	public void setM_InOut_ID(final int M_InOut_ID)
	{
		this.M_InOut_ID = M_InOut_ID;
	}

	@Override
	public I_C_DocType getC_DocTypeInvoice()
	{
		return docTypeInvoice;
	}

	public void setC_DocTypeInvoice(final I_C_DocType docType)
	{
		this.docTypeInvoice = docType;
	}

	@Override
	public boolean isTaxIncluded()
	{
		return taxIncluded;
	}

	public void setTaxIncluded(boolean taxIncluded)
	{
		this.taxIncluded = taxIncluded;
	}

	/**
	 * Negate all line amounts
	 */
	public void negateAllLineAmounts()
	{
		for (final IInvoiceCandAggregate lineAgg : getLines())
		{
			lineAgg.negateLineAmounts();
		}
	}

	/**
	 * Calculates total net amount by summing up all {@link IInvoiceLineRW#getNetLineAmt()}s.
	 *
	 * @return total net amount
	 */
	public BigDecimal calculateTotalNetAmtFromLines()
	{
		final List<IInvoiceCandAggregate> lines = getLines();
		Check.assume(lines != null && !lines.isEmpty(), "Invoice {} was not aggregated yet", this);

		BigDecimal totalNetAmt = BigDecimal.ZERO;
		for (final IInvoiceCandAggregate lineAgg : lines)
		{
			for (final IInvoiceLineRW line : lineAgg.getAllLines())
			{
				final BigDecimal lineNetAmt = line.getNetLineAmt();
				totalNetAmt = totalNetAmt.add(lineNetAmt);
			}
		}

		return totalNetAmt;
	}
}
