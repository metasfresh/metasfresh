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

import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.ObjectUtils;

import de.metas.util.Check;

/**
 * Mutable invoice allocation candidate.
 *
 * Used by {@link PaymentAllocationBuilder} internally.
 *
 * @author tsa
 *
 */
public class PayableDocument implements IPayableDocument
{
	public static Builder builder()
	{
		return new Builder();
	}

	private final int bpartnerId;
	private final String documentNo;
	private final boolean isSOTrx;
	private final ITableRecordReference reference;
	private final PayableDocumentType type;
	private final boolean creditMemo;
	//
	private final BigDecimal openAmtInitial;
	private final BigDecimal amountToAllocateInitial;
	//
	private BigDecimal allocatedAmt;
	private BigDecimal amountToAllocate;
	//
	private BigDecimal discountAmtAllocated;
	private BigDecimal discountAmtToAllocate;
	//
	private BigDecimal writeOffAmtAllocated;
	private BigDecimal writeOffAmtToAllocate;
	//
	private int C_Currency_ID;

	protected PayableDocument(final Builder builder)
	{
		super();

		bpartnerId = builder.getC_BPartner_ID();
		documentNo = builder.getDocumentNo();
		isSOTrx = builder.isSOTrx;
		type = builder.getType();
		reference = builder.getReference();
		creditMemo = builder.creditMemo;
		C_Currency_ID = builder.C_Currency_ID;
		//
		//
		openAmtInitial = builder.openAmt;
		//
		amountToAllocateInitial = builder.amountToAllocate;
		amountToAllocate = amountToAllocateInitial;
		allocatedAmt = BigDecimal.ZERO;
		//
		discountAmtToAllocate = builder.discountAmt;
		discountAmtAllocated = BigDecimal.ZERO;
		//
		writeOffAmtToAllocate = builder.writeOffAmt;
		writeOffAmtAllocated = BigDecimal.ZERO;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public PayableDocumentType getType()
	{
		return type;
	}

	@Override
	public ITableRecordReference getReference()
	{
		return reference;
	}

	@Override
	public String getDocumentNo()
	{
		return documentNo;
	}

	@Override
	public boolean isCustomerDocument()
	{
		return isSOTrx;
	}

	@Override
	public boolean isVendorDocument()
	{
		return !isSOTrx;
	}

	@Override
	public boolean isCreditMemo()
	{
		return creditMemo;
	}

	@Override
	public int getC_BPartner_ID()
	{
		return bpartnerId;
	}

	private BigDecimal getOpenAmtRemaining()
	{
		final BigDecimal totalAllocated = allocatedAmt.add(discountAmtAllocated).add(writeOffAmtAllocated);
		final BigDecimal openAmtRemaining = openAmtInitial.subtract(totalAllocated);
		return openAmtRemaining;
	}

	@Override
	public BigDecimal getAmountToAllocateInitial()
	{
		return amountToAllocateInitial;
	}

	@Override
	public BigDecimal getAmountToAllocate()
	{
		return amountToAllocate;
	}

	@Override
	public void addAllocatedAmounts(final BigDecimal allocatedAmtToAdd, final BigDecimal discountAmtToAdd, final BigDecimal writeOffAmtToAdd)
	{
		allocatedAmt = allocatedAmt.add(allocatedAmtToAdd);
		amountToAllocate = amountToAllocate.subtract(allocatedAmtToAdd);

		discountAmtAllocated = discountAmtAllocated.add(discountAmtToAdd);
		discountAmtToAllocate = discountAmtToAllocate.subtract(discountAmtToAdd);

		writeOffAmtAllocated = writeOffAmtAllocated.add(writeOffAmtToAdd);
		writeOffAmtToAllocate = writeOffAmtToAllocate.subtract(writeOffAmtToAdd);
	}

	@Override
	public BigDecimal getDiscountAmtToAllocate()
	{
		return discountAmtToAllocate;
	}

	@Override
	public BigDecimal getWriteOffAmtToAllocate()
	{
		return writeOffAmtToAllocate;
	}
	
	@Override
	public int getC_Currency_ID()
	{
		return C_Currency_ID;
	}

	@Override
	public BigDecimal calculateProjectedOverUnderAmt(final BigDecimal amountToAllocate, final BigDecimal discountAmtToAllocate, final BigDecimal writeOffAmtToAllocate)
	{
		final BigDecimal projectedOverUnderAmt = getOpenAmtRemaining()
				.subtract(amountToAllocate)
				.subtract(discountAmtToAllocate)
				.subtract(writeOffAmtToAllocate);
		return projectedOverUnderAmt;
	}

	@Override
	public boolean isFullyAllocated()
	{
		return getAmountToAllocate().signum() == 0
				&& getDiscountAmtToAllocate().signum() == 0
				&& getWriteOffAmtToAllocate().signum() == 0;
	}

	public static class Builder
	{
		private int bpartnerId;
		private String documentNo;
		private Boolean isSOTrx;
		private ITableRecordReference reference;
		private PayableDocumentType type;
		private boolean creditMemo = false;
		//
		private int C_Currency_ID;
		
		//
		private BigDecimal openAmt = BigDecimal.ZERO;
		private BigDecimal amountToAllocate = BigDecimal.ZERO;
		private BigDecimal discountAmt = BigDecimal.ZERO;
		private BigDecimal writeOffAmt = BigDecimal.ZERO;

		private Builder()
		{
			super();
		}

		public PayableDocument build()
		{
			return new PayableDocument(this);
		}

		public Builder setC_BPartner_ID(final int bpartnerId)
		{
			this.bpartnerId = bpartnerId;
			return this;
		}
		
		private int getC_BPartner_ID()
		{
			return bpartnerId > 0 ? bpartnerId : -1;
		}

		public Builder setC_Currency_ID(final int C_Currency_ID)
		{
			this.C_Currency_ID = C_Currency_ID;
			return this;
		}
		
		public int getC_Currency_ID()
		{
			return C_Currency_ID > 0 ? C_Currency_ID : -1;
		}
		
		private PayableDocumentType getType()
		{
			Check.assumeNotNull(type, "type not null");
			return type;
		}

		public Builder setReference(final PayableDocumentType type, final ITableRecordReference reference)
		{
			this.type = type;
			this.reference = reference;
			return this;
		}

		private final ITableRecordReference getReference()
		{
			Check.assumeNotNull(reference, "reference not null");
			return reference;
		}

		public Builder setDocumentNo(String documentNo)
		{
			this.documentNo = documentNo;
			return this;
		}

		private String getDocumentNo()
		{
			if (!Check.isEmpty(documentNo, true))
			{
				return documentNo;
			}

			final ITableRecordReference reference = getReference();
			if (reference != null)
			{
				return "<" + reference.getRecord_ID() + ">";
			}

			return "?";
		}

		public Builder setIsSOTrx(boolean isSOTrx)
		{
			this.isSOTrx = isSOTrx;
			return this;
		}

		public Builder setCreditMemo(final boolean creditMemo)
		{
			this.creditMemo = creditMemo;
			return this;
		}

		public Builder setOpenAmt(final BigDecimal openAmt)
		{
			Check.assumeNotNull(openAmt, "openAmt not null");
			this.openAmt = openAmt;
			return this;
		}

		public Builder setAmountToAllocate(final BigDecimal amountToAllocate)
		{
			Check.assumeNotNull(amountToAllocate, "amountToAllocate not null");
			this.amountToAllocate = amountToAllocate;
			return this;
		}

		public Builder setDiscountAmt(BigDecimal discountAmt)
		{
			this.discountAmt = discountAmt;
			return this;
		}

		public Builder setWriteOffAmt(BigDecimal writeOffAmt)
		{
			this.writeOffAmt = writeOffAmt;
			return this;
		}
	}

}
