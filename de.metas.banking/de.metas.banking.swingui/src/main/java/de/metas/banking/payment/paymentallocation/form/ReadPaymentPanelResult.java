package de.metas.banking.payment.paymentallocation.form;

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

import org.adempiere.util.lang.ObjectUtils;

import de.metas.payment.model.I_C_Payment_Request;

public class ReadPaymentPanelResult
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final int C_BPartner_ID;
	private final BigDecimal totalPaymentCandidatesAmt;
	private final I_C_Payment_Request paymentRequestTemplate;
	private final String paymentReference;

	private ReadPaymentPanelResult(final Builder builder)
	{
		super();
		this.C_BPartner_ID = builder.C_BPartner_ID;
		this.totalPaymentCandidatesAmt = builder.totalPaymentCandidatesAmt;
		this.paymentRequestTemplate = builder.paymentRequestTemplate;
		this.paymentReference = builder.paymentReference;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public int getC_BPartner_ID()
	{
		return C_BPartner_ID;
	}

	public BigDecimal getTotalPaymentCandidatesAmt()
	{
		return totalPaymentCandidatesAmt;
	}

	public I_C_Payment_Request getPaymentRequestTemplate()
	{
		return paymentRequestTemplate;
	}

	public String getPaymentReference()
	{
		return paymentReference;
	}

	public static final class Builder
	{
		private int C_BPartner_ID = -1;
		private BigDecimal totalPaymentCandidatesAmt = null;
		private I_C_Payment_Request paymentRequestTemplate;
		private String paymentReference;

		public ReadPaymentPanelResult build()
		{
			return new ReadPaymentPanelResult(this);
		}

		public Builder setC_BPartner_ID(int C_BPartner_ID)
		{
			this.C_BPartner_ID = C_BPartner_ID;
			return this;
		}

		public Builder setTotalPaymentCandidatesAmt(BigDecimal totalPaymentCandidatesAmt)
		{
			this.totalPaymentCandidatesAmt = totalPaymentCandidatesAmt;
			return this;
		}

		public Builder setPaymentRequestTemplate(I_C_Payment_Request paymentRequestTemplate)
		{
			this.paymentRequestTemplate = paymentRequestTemplate;
			return this;
		}

		public Builder setPaymentReference(String paymentReference)
		{
			this.paymentReference = paymentReference;
			return this;
		}
	}

}
