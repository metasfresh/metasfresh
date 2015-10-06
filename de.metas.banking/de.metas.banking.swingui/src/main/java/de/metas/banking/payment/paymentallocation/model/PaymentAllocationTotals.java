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

import org.adempiere.util.lang.ObjectUtils;

/**
 * Allocation total amounts
 * 
 * @author tsa
 *
 */
public final class PaymentAllocationTotals
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final BigDecimal invoicedAmt;
	//
	private final BigDecimal paymentTotalAmt;
	private final BigDecimal paymentExistingAmt;
	private final BigDecimal paymentCandidatesAmt;
	//
	private final BigDecimal diffInvoiceMinusPay;

	private PaymentAllocationTotals(final Builder builder)
	{
		super();
		this.invoicedAmt = builder.invoicedAmt;
		this.paymentExistingAmt = builder.paymentExistingAmt;
		this.paymentCandidatesAmt = builder.paymentCandidatesAmt;

		this.paymentTotalAmt = paymentExistingAmt.add(paymentCandidatesAmt);
		this.diffInvoiceMinusPay = invoicedAmt.subtract(paymentTotalAmt);
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public BigDecimal getInvoicedAmt()
	{
		return invoicedAmt;
	}

	public BigDecimal getPaymentExistingAmt()
	{
		return paymentExistingAmt;
	}

	public BigDecimal getPaymentCandidatesAmt()
	{
		return paymentCandidatesAmt;
	}

	public BigDecimal getDiffInvoiceMinusPay()
	{
		return diffInvoiceMinusPay;
	}

	public boolean hasDifferences()
	{
		return diffInvoiceMinusPay.signum() != 0;
	}

	public boolean isMoreInvoicedThanPaid()
	{
		final int invoicedAmtSign = invoicedAmt.signum();
		final int paymentAmtSign = paymentTotalAmt.signum();

		if (invoicedAmtSign >= 0)
		{
			// Positive invoiced, positive paid
			if (paymentAmtSign >= 0)
			{
				return invoicedAmt.compareTo(paymentTotalAmt) > 0;
			}
			// Positive invoiced, negative paid
			else
			{
				return true; // more invoiced than paid
			}
		}
		else
		{
			// Negative invoiced, positive paid
			if (paymentAmtSign >= 0)
			{
				return false; // more paid than invoiced
			}
			// Negative invoiced, negative paid
			else
			{
				return invoicedAmt.abs().compareTo(paymentTotalAmt.abs()) > 0;
			}
		}
	}

	//
	//
	// ------------------------------------------------------------------------------------------------------------------------------
	//
	//
	public static final class Builder
	{
		private BigDecimal invoicedAmt = BigDecimal.ZERO;
		private BigDecimal paymentExistingAmt = BigDecimal.ZERO;
		private BigDecimal paymentCandidatesAmt = BigDecimal.ZERO;

		private Builder()
		{
			super();
		}

		public PaymentAllocationTotals build()
		{
			return new PaymentAllocationTotals(this);
		}

		public Builder setInvoicedAmt(final BigDecimal invoicedAmt)
		{
			this.invoicedAmt = invoicedAmt;
			return this;
		}

		public Builder setPaymentExistingAmt(final BigDecimal paymentExistingAmt)
		{
			this.paymentExistingAmt = paymentExistingAmt;
			return this;
		}

		public Builder setPaymentCandidatesAmt(final BigDecimal paymentCandidatesAmt)
		{
			this.paymentCandidatesAmt = paymentCandidatesAmt;
			return this;
		}
	}

}
