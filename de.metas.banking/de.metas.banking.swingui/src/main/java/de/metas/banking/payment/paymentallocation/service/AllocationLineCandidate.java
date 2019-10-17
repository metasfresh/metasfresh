package de.metas.banking.payment.paymentallocation.service;

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
import java.util.Objects;

import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.ObjectUtils;

import de.metas.util.Check;

/**
 * Immutable allocation candidate.
 *
 * @author tsa
 *
 */
final class AllocationLineCandidate
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final int C_BPartner_ID;

	private final ITableRecordReference payableDocumentRef;
	private final ITableRecordReference paymentDocumentRef;

	//
	// Amounts
	private final BigDecimal amount;
	private final BigDecimal discountAmt;
	private final BigDecimal writeOffAmt;
	private final BigDecimal payableOverUnderAmt;
	private final BigDecimal paymentOverUnderAmt;

	private AllocationLineCandidate(final Builder builder)
	{
		super();

		amount = builder.amount;
		discountAmt = builder.discountAmt;
		writeOffAmt = builder.writeOffAmt;
		payableOverUnderAmt = builder.payableOverUnderAmt;
		paymentOverUnderAmt = builder.paymentOverUnderAmt;

		C_BPartner_ID = builder.C_BPartner_ID > 0 ? builder.C_BPartner_ID : -1;

		payableDocumentRef = builder.getPayableDocumentRef();
		paymentDocumentRef = builder.getPaymentDocumentRef();
		Check.errorIf(Objects.equals(payableDocumentRef, paymentDocumentRef), "payable and payment shall not be the same but there are: {}", payableDocumentRef);
		if (amount.signum() != 0)
		{
			Check.assumeNotNull(paymentDocumentRef, "paymentDocumentRef not null when amount is not zero");
		}

	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		final AllocationLineCandidate other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}
		return new EqualsBuilder()
				.append(C_BPartner_ID, other.C_BPartner_ID)
				//
				.append(payableDocumentRef, other.payableDocumentRef)
				.append(paymentDocumentRef, other.paymentDocumentRef)
				//
				.append(amount, other.amount)
				.append(discountAmt, other.discountAmt)
				.append(writeOffAmt, other.writeOffAmt)
				.append(payableOverUnderAmt, other.payableOverUnderAmt)
				.append(paymentOverUnderAmt, other.paymentOverUnderAmt)
				.isEqual();
	}

	@Override
	public int hashCode()
	{
		return new HashcodeBuilder()
				.append(C_BPartner_ID)
				//
				.append(payableDocumentRef)
				.append(paymentDocumentRef)
				//
				.append(amount)
				.append(discountAmt)
				.append(writeOffAmt)
				.append(payableOverUnderAmt)
				.append(paymentOverUnderAmt)
				.toHashcode();
	}

	public ITableRecordReference getPayableDocument()
	{
		return payableDocumentRef;
	}

	public ITableRecordReference getPaymentDocument()
	{
		return paymentDocumentRef;
	}

	public int getC_BPartner_ID()
	{
		return C_BPartner_ID;
	}

	public BigDecimal getAmount()
	{
		return amount;
	}

	public BigDecimal getDiscountAmt()
	{
		return discountAmt;
	}

	public BigDecimal getWriteOffAmt()
	{
		return writeOffAmt;
	}

	public BigDecimal getOverUnderAmt()
	{
		return payableOverUnderAmt;
	}

	public BigDecimal getPaymentOverUnderAmt()
	{
		return paymentOverUnderAmt;
	}

	public boolean isZeroToAllocate()
	{
		return amount.signum() == 0
				&& discountAmt.signum() == 0
				&& writeOffAmt.signum() == 0
		// NOTE: don't check the OverUnderAmt because that amount is not affecting allocation,
		// so an allocation is Zero with our without the over/under amount.
		// && overUnderAmt.signum() == 0
		;
	}

	//
	//
	// ------------------------------------------------------------------------------------------------------------------------------------------------------
	//
	//
	public static final class Builder
	{
		public int C_BPartner_ID;
		//
		private ITableRecordReference payableDocumentRef;
		private ITableRecordReference paymentDocumentRef;
		//
		//
		private BigDecimal amount = BigDecimal.ZERO;
		private BigDecimal discountAmt = BigDecimal.ZERO;
		private BigDecimal writeOffAmt = BigDecimal.ZERO;
		public BigDecimal payableOverUnderAmt = BigDecimal.ZERO;
		private BigDecimal paymentOverUnderAmt = BigDecimal.ZERO;

		private Builder()
		{
			super();
		}

		public AllocationLineCandidate build()
		{
			return new AllocationLineCandidate(this);
		}

		@Override
		public String toString()
		{
			return ObjectUtils.toString(this);
		}

		public Builder setPayableDocument(final ITableRecordReference payableDocumentRef)
		{
			this.payableDocumentRef = payableDocumentRef;
			return this;
		}

		private ITableRecordReference getPayableDocumentRef()
		{
			Check.assumeNotNull(payableDocumentRef, "payableDocumentRef not null");
			return payableDocumentRef;
		}

		public Builder setPaymentDocument(final ITableRecordReference paymentDocumentRef)
		{
			this.paymentDocumentRef = paymentDocumentRef;
			return this;
		}

		public ITableRecordReference getPaymentDocumentRef()
		{
			return paymentDocumentRef;
		}

		public Builder setC_BPartner_ID(final int bpartnerId)
		{
			C_BPartner_ID = bpartnerId;
			return this;
		}

		public Builder setAmount(final BigDecimal amount)
		{
			this.amount = amount;
			return this;
		}

		public Builder setDiscountAmt(final BigDecimal discountAmt)
		{
			this.discountAmt = discountAmt;
			return this;
		}

		public Builder setWriteOffAmt(final BigDecimal writeOffAmt)
		{
			this.writeOffAmt = writeOffAmt;
			return this;
		}

		public Builder setOverUnderAmt(final BigDecimal overUnderAmt)
		{
			payableOverUnderAmt = overUnderAmt;
			return this;
		}

		public Builder setPaymentOverUnderAmt(final BigDecimal paymentOverUnderAmt)
		{
			this.paymentOverUnderAmt = paymentOverUnderAmt;
			return this;
		}
	}
}
