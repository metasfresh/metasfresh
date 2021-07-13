package de.metas.banking.payment.impl;

/*
 * #%L
 * de.metas.banking.base
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

import javax.annotation.concurrent.Immutable;

import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.I_C_PaySelectionLine;

import de.metas.util.Check;
import de.metas.common.util.CoalesceUtil;

/**
 * {@link I_C_PaySelectionLine} candidate used to create or update pay selection lines.
 * 
 * @author tsa
 *
 */
@Immutable
class PaySelectionLineCandidate
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final String paymentRule;
	private final BigDecimal payAmt;
	private final BigDecimal discountAmt;
	private final BigDecimal openAmt;
	private final BigDecimal differenceAmt;
	private final int C_Invoice_ID;
	private final boolean isSOTrx;
	private final int C_BPartner_ID;
	private final int C_BP_BankAccount_ID;

	private PaySelectionLineCandidate(final Builder builder)
	{
		super();

		this.paymentRule = builder.getPaymentRule();
		this.payAmt = builder.getPayAmt();
		this.discountAmt = builder.getDiscountAmt();
		this.openAmt = builder.getOpenAmt();
		this.differenceAmt = builder.getDifferenceAmt();
		this.C_Invoice_ID = builder.getC_Invoice_ID();
		this.isSOTrx = builder.isSOTrx();
		this.C_BPartner_ID = builder.getC_BPartner_ID();
		this.C_BP_BankAccount_ID = builder.getC_BP_BankAccount_ID();
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public String getPaymentRule()
	{
		return paymentRule;
	}

	public BigDecimal getPayAmt()
	{
		return payAmt;
	}

	public BigDecimal getDiscountAmt()
	{
		return discountAmt;
	}

	public BigDecimal getOpenAmt()
	{
		return openAmt;
	}

	public BigDecimal getDifferenceAmt()
	{
		return differenceAmt;
	}

	public int getC_Invoice_ID()
	{
		return C_Invoice_ID;
	}

	public boolean isSOTrx()
	{
		return isSOTrx;
	}

	public int getC_BPartner_ID()
	{
		return C_BPartner_ID;
	}

	public int getC_BP_BankAccount_ID()
	{
		return C_BP_BankAccount_ID;
	}

	public static final class Builder
	{
		private String paymentRule;
		private Integer invoiceId;
		private Boolean isSOTrx;
		private Integer bpartnerId;
		private Integer bpBankAccountId;
		// Amounts
		private BigDecimal _openAmt;
		private BigDecimal _discountAmt;


		PaySelectionLineCandidate build()
		{
			return new PaySelectionLineCandidate(this);
		}

		public Builder setPaymentRule(final String paymentRule)
		{
			this.paymentRule = paymentRule;
			return this;
		}

		public String getPaymentRule()
		{
			Check.assumeNotNull(paymentRule, "paymentRule not null");
			return paymentRule;
		}

		public Builder setC_Invoice_ID(final int invoiceId)
		{
			this.invoiceId = invoiceId;
			return this;
		}

		public int getC_Invoice_ID()
		{
			return invoiceId;
		}

		public Builder setIsSOTrx(final boolean isSOTrx)
		{
			this.isSOTrx = isSOTrx;
			return this;
		}

		public boolean isSOTrx()
		{
			return isSOTrx;
		}

		public Builder setC_BPartner_ID(final int bpartnerId)
		{
			this.bpartnerId = bpartnerId;
			return this;
		}

		public int getC_BPartner_ID()
		{
			return bpartnerId;
		}

		public Builder setC_BP_BankAccount_ID(final int bpBankAccountId)
		{
			this.bpBankAccountId = bpBankAccountId;
			return this;
		}

		public int getC_BP_BankAccount_ID()
		{
			return bpBankAccountId;
		}

		public Builder setOpenAmt(final BigDecimal openAmt)
		{
			this._openAmt = openAmt;
			return this;
		}

		public BigDecimal getOpenAmt()
		{
			return CoalesceUtil.coalesce(_openAmt, BigDecimal.ZERO);
		}

		public BigDecimal getPayAmt()
		{
			final BigDecimal openAmt = getOpenAmt();
			final BigDecimal discountAmt = getDiscountAmt();
			final BigDecimal payAmt = openAmt.subtract(discountAmt);
			return payAmt;
		}

		public Builder setDiscountAmt(final BigDecimal discountAmt)
		{
			this._discountAmt = discountAmt;
			return this;
		}

		public BigDecimal getDiscountAmt()
		{
			return CoalesceUtil.coalesce(_discountAmt, BigDecimal.ZERO);
		}

		public BigDecimal getDifferenceAmt()
		{
			final BigDecimal openAmt = getOpenAmt();
			final BigDecimal payAmt = getPayAmt();
			final BigDecimal discountAmt = getDiscountAmt();
			final BigDecimal differenceAmt = openAmt.subtract(payAmt).subtract(discountAmt);
			return differenceAmt;
		}

	}
}
