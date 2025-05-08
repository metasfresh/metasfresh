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

import de.metas.banking.BankAccountId;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.CoalesceUtil;
import de.metas.invoice.InvoiceId;
import de.metas.payment.PaymentId;
import de.metas.util.Check;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.I_C_PaySelectionLine;

import javax.annotation.concurrent.Immutable;
import java.math.BigDecimal;

/**
 * {@link I_C_PaySelectionLine} candidate used to create or update pay selection lines.
 *
 * @author tsa
 */
@Immutable
class PaySelectionLineCandidate
{
	private final String paymentRule;
	private final BigDecimal payAmt;
	private final BigDecimal discountAmt;
	private final BigDecimal openAmt;
	private final BigDecimal differenceAmt;
	private final InvoiceId invoiceId;
	private final PaymentId paymentId;
	private final boolean isSOTrx;
	private final BPartnerId bPartnerId;
	private final BankAccountId bPartnerBankAccountId;

	private PaySelectionLineCandidate(final Builder builder)
	{
		super();

		this.paymentRule = builder.getPaymentRule();
		this.payAmt = builder.getPayAmt();
		this.discountAmt = builder.getDiscountAmt();
		this.openAmt = builder.getOpenAmt();
		this.differenceAmt = builder.getDifferenceAmt();
		this.invoiceId = builder.getInvoiceId();
		this.paymentId = builder.getPaymentId();
		this.isSOTrx = builder.isSOTrx();
		this.bPartnerId = builder.getBPartnerId();
		this.bPartnerBankAccountId = builder.getBPartnerBankAccountId();
	}

	public static final Builder builder()
	{
		return new Builder();
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

	public PaymentId getPaymentId()
	{
		return paymentId;
	}

	public InvoiceId getInvoiceId()
	{
		return invoiceId;
	}

	public boolean isSOTrx()
	{
		return isSOTrx;
	}

	public BPartnerId getBPartnerId()
	{
		return bPartnerId;
	}

	public BankAccountId getBPartnerBankAccountId()
	{
		return bPartnerBankAccountId;
	}

	public static final class Builder
	{
		private String paymentRule;
		private InvoiceId invoiceId;
		private PaymentId paymentId;
		private Boolean isSOTrx;
		private BPartnerId bpartnerId;
		private BankAccountId bpBankAccountId;
		// Amounts
		private BigDecimal _openAmt;
		private BigDecimal _discountAmt;

		PaySelectionLineCandidate build()
		{
			return new PaySelectionLineCandidate(this);
		}

		public String getPaymentRule()
		{
			Check.assumeNotNull(paymentRule, "paymentRule not null");
			return paymentRule;
		}

		public Builder setPaymentRule(final String paymentRule)
		{
			this.paymentRule = paymentRule;
			return this;
		}

		public InvoiceId getInvoiceId()
		{
			return invoiceId;
		}

		public Builder setInvoiceId(final InvoiceId invoiceId)
		{
			this.invoiceId = invoiceId;
			return this;
		}

		public PaymentId getPaymentId()
		{
			return paymentId;
		}

		public Builder setPaymentId(final PaymentId paymentId)
		{
			this.paymentId = paymentId;
			return this;
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

		public BPartnerId getBPartnerId()
		{
			return bpartnerId;
		}

		public Builder setBPartnerId(final BPartnerId bpartnerId)
		{
			this.bpartnerId = bpartnerId;
			return this;
		}

		public BankAccountId getBPartnerBankAccountId()
		{
			return bpBankAccountId;
		}

		public Builder setBPartnerBankAccountId(final BankAccountId bpBankAccountId)
		{
			this.bpBankAccountId = bpBankAccountId;
			return this;
		}

		public BigDecimal getOpenAmt()
		{
			return CoalesceUtil.coalesce(_openAmt, BigDecimal.ZERO);
		}

		public Builder setOpenAmt(final BigDecimal openAmt)
		{
			this._openAmt = openAmt;
			return this;
		}

		public BigDecimal getPayAmt()
		{
			final BigDecimal openAmt = getOpenAmt();
			final BigDecimal discountAmt = getDiscountAmt();
			final BigDecimal payAmt = openAmt.subtract(discountAmt);
			return payAmt;
		}

		public BigDecimal getDiscountAmt()
		{
			return CoalesceUtil.coalesce(_discountAmt, BigDecimal.ZERO);
		}

		public Builder setDiscountAmt(final BigDecimal discountAmt)
		{
			this._discountAmt = discountAmt;
			return this;
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
