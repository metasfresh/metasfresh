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
import de.metas.order.OrderId;
import de.metas.order.paymentschedule.OrderPayScheduleId;
import de.metas.util.Check;
import lombok.Getter;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.I_C_PaySelectionLine;
import org.jetbrains.annotations.Nullable;

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
	@Getter private final String paymentRule;
	@Getter private final BigDecimal payAmt;
	@Getter private final BigDecimal discountAmt;
	@Getter private final BigDecimal openAmt;
	@Getter private final BigDecimal differenceAmt;
	@Getter private final InvoiceId invoiceId;
	@Getter private final OrderId orderId;
	@Getter private final OrderPayScheduleId orderPayScheduleId;
	@Getter private final boolean isSOTrx;
	private final BPartnerId bpartnerId;
	private final BankAccountId bpBankAccountId;

	private PaySelectionLineCandidate(final Builder builder)
	{
		super();

		this.paymentRule = builder.getPaymentRule();
		this.payAmt = builder.getPayAmt();
		this.discountAmt = builder.getDiscountAmt();
		this.openAmt = builder.getOpenAmt();
		this.differenceAmt = builder.getDifferenceAmt();
		this.invoiceId = builder.getInvoiceId();
		this.orderId = builder.getOrderId();
		this.orderPayScheduleId = builder.getOrderPayScheduleId();
		this.isSOTrx = builder.isSOTrx();
		this.bpartnerId = builder.getBPartnerId();
		this.bpBankAccountId = builder.getBPartnerBankAccountId();
	}

	public static Builder builder()
	{
		return new Builder();
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public BPartnerId getBPartnerId()
	{
		return bpartnerId;
	}

	public BankAccountId getBPartnerBankAccountId()
	{
		return bpBankAccountId;
	}

	public static final class Builder
	{
		private String paymentRule;
		@Getter private @Nullable InvoiceId invoiceId;
		@Getter private @Nullable OrderId orderId;
		@Getter private @Nullable OrderPayScheduleId orderPayScheduleId;
		private Boolean isSOTrx;
		private BPartnerId bpartnerId;
		private @Nullable BankAccountId bpBankAccountId;
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

		public Builder setInvoiceId(final @Nullable InvoiceId invoiceId)
		{
			this.invoiceId = invoiceId;
			return this;
		}

		public Builder setOrderPayScheduleId(final @Nullable OrderPayScheduleId orderPayScheduleId)
		{
			this.orderPayScheduleId = orderPayScheduleId;
			return this;
		}

		public Builder setOrderId(final @Nullable OrderId orderId)
		{
			this.orderId = orderId;
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

		public @Nullable BankAccountId getBPartnerBankAccountId()
		{
			return bpBankAccountId;
		}

		public Builder setBPartnerBankAccountId(final @Nullable BankAccountId bpBankAccountId)
		{
			this.bpBankAccountId = bpBankAccountId;
			return this;
		}

		public BigDecimal getOpenAmt()
		{
			return CoalesceUtil.coalesceNotNull(_openAmt, BigDecimal.ZERO);
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
			return openAmt.subtract(discountAmt);
		}

		public BigDecimal getDiscountAmt()
		{
			return CoalesceUtil.coalesceNotNull(_discountAmt, BigDecimal.ZERO);
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
			return openAmt.subtract(payAmt).subtract(discountAmt);
		}

	}
}
