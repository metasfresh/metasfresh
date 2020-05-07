package de.metas.banking.payment.paymentallocation.model;

/*
 * #%L
 * de.metas.banking.swingui
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
import java.util.Date;

import org.adempiere.util.lang.ObjectUtils;

public class InvoiceCandidateRow implements IInvoiceCandidateRow
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private boolean selected;
	private final int C_Invoice_Candidate_ID;
	private final String BPartnerName;
	private final Date documentDate;
	// task 09643
	private final Date dateAcct;
	private final Date dateToInvoice;
	private final Date dateInvoiced;
	private final BigDecimal netAmtToInvoice;
	private final BigDecimal netAmtInvoiced;
	private final BigDecimal discount;
	private final String currencyISOCode;
	private final String invoiceRuleName;
	private final String headerAggregationKey;

	private InvoiceCandidateRow(Builder builder)
	{
		super();
		this.selected = false;
		this.C_Invoice_Candidate_ID = builder.C_Invoice_Candidate_ID;
		this.BPartnerName = builder.BPartnerName;
		this.documentDate = builder.documentDate;
		//task 09643
		this.dateAcct = builder.dateAcct;
		this.dateToInvoice = builder.dateToInvoice;
		this.dateInvoiced = builder.dateInvoiced;
		this.netAmtToInvoice = builder.netAmtToInvoice;
		this.netAmtInvoiced = builder.netAmtInvoiced;
		this.discount = builder.discount;
		this.currencyISOCode = builder.currencyISOCode;
		this.invoiceRuleName = builder.invoiceRuleName;
		this.headerAggregationKey = builder.headerAggregationKey;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public boolean isSelected()
	{
		return selected;
	}

	@Override
	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}

	@Override
	public int getC_Invoice_Candidate_ID()
	{
		return C_Invoice_Candidate_ID;
	}

	@Override
	public String getBPartnerName()
	{
		return BPartnerName;
	}

	@Override
	public Date getDocumentDate()
	{
		return documentDate;
	}
	
	@Override
	public Date getDateAcct()
	{
		return dateAcct;
	}

	@Override
	public Date getDateToInvoice()
	{
		return dateToInvoice;
	}

	@Override
	public Date getDateInvoiced()
	{
		return dateInvoiced;
	}

	@Override
	public BigDecimal getNetAmtToInvoice()
	{
		return netAmtToInvoice;
	}

	@Override
	public BigDecimal getNetAmtInvoiced()
	{
		return netAmtInvoiced;
	}

	@Override
	public BigDecimal getDiscount()
	{
		return discount;
	}

	@Override
	public String getCurrencyISOCode()
	{
		return currencyISOCode;
	}

	@Override
	public String getInvoiceRuleName()
	{
		return invoiceRuleName;
	}

	@Override
	public String getHeaderAggregationKey()
	{
		return headerAggregationKey;
	}

	public static class Builder
	{
		private int C_Invoice_Candidate_ID;
		private String BPartnerName;
		private Date documentDate;
		// task 09643
		private Date dateAcct;
		private Date dateToInvoice;
		private Date dateInvoiced;
		private BigDecimal netAmtToInvoice;
		private BigDecimal netAmtInvoiced;
		private BigDecimal discount;
		private String currencyISOCode;
		private String invoiceRuleName;
		private String headerAggregationKey;

		private Builder()
		{
			super();
		}

		public InvoiceCandidateRow build()
		{
			return new InvoiceCandidateRow(this);
		}

		public Builder setC_Invoice_Candidate_ID(int C_Invoice_Candidate_ID)
		{
			this.C_Invoice_Candidate_ID = C_Invoice_Candidate_ID;
			return this;
		}

		public Builder setBPartnerName(String BPartnerName)
		{
			this.BPartnerName = BPartnerName;
			return this;
		}

		public Builder setDocumentDate(Date documentDate)
		{
			this.documentDate = documentDate;
			return this;
		}

		/**
		 * task 09643: separate accounting date from the transaction date
		 * 
		 * @param dateAcct
		 * @return
		 */
		public Builder setDateAcct(Date dateAcct)
		{
			this.dateAcct = dateAcct;
			return this;
		}

		public Builder setDateToInvoice(Date dateToInvoice)
		{
			this.dateToInvoice = dateToInvoice;
			return this;
		}

		public Builder setDateInvoiced(Date dateInvoiced)
		{
			this.dateInvoiced = dateInvoiced;
			return this;
		}

		public Builder setNetAmtToInvoice(BigDecimal netAmtToInvoice)
		{
			this.netAmtToInvoice = netAmtToInvoice;
			return this;
		}

		public Builder setNetAmtInvoiced(BigDecimal netAmtInvoiced)
		{
			this.netAmtInvoiced = netAmtInvoiced;
			return this;
		}

		public Builder setDiscount(BigDecimal discount)
		{
			this.discount = discount;
			return this;
		}

		public Builder setCurrencyISOCode(String currencyISOCode)
		{
			this.currencyISOCode = currencyISOCode;
			return this;
		}

		public Builder setInvoiceRuleName(String invoiceRuleName)
		{
			this.invoiceRuleName = invoiceRuleName;
			return this;
		}

		public Builder setHeaderAggregationKey(String headerAggregationKey)
		{
			this.headerAggregationKey = headerAggregationKey;
			return this;
		}
	}
}
