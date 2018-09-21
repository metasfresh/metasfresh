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
import java.util.EnumSet;
import java.util.Set;

import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.ObjectUtils;
import org.adempiere.util.lang.impl.TableRecordReference;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import de.metas.banking.payment.paymentallocation.service.IPayableDocument.PayableDocumentType;
import de.metas.util.Check;

/**
 * Mutable payment allocation candidate.
 *
 * Used by {@link PaymentAllocationBuilder} internally.
 *
 * @author tsa
 *
 */
public class PaymentDocument implements IPaymentDocument
{
	public static final Builder builder()
	{
		return new Builder();
	}
	
	private final int bpartnerId;
	private final String documentNo;
	private final ITableRecordReference reference;
	private final boolean isSOTrx;
	private final Set<PayableDocumentType> allowedPayableDocumentType;
	//
	private final BigDecimal openAmtInitial;
	private final BigDecimal amountToAllocateInitial;
	
	//
	private int C_Currency_ID;
	
	//
	private BigDecimal allocatedAmt;
	private BigDecimal amountToAllocate;

	private PaymentDocument(final Builder builder)
	{
		super();
		bpartnerId = builder.getC_BPartner_ID();
		documentNo = builder.getDocumentNo();
		reference = builder.getReference();
		isSOTrx = builder.isSOTrx;
		C_Currency_ID = builder.getC_Currency_ID();
		allowedPayableDocumentType = builder.getAllowedPayableDocumentType();

		//
		openAmtInitial = builder.getOpenAmt();
		amountToAllocateInitial = builder.getAmountToAllocate();
		//
		amountToAllocate = amountToAllocateInitial;
		allocatedAmt = BigDecimal.ZERO;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public int getC_BPartner_ID()
	{
		return bpartnerId;
	}
	
	@Override
	public int getC_Currency_ID()
	{
		return C_Currency_ID;
	}

	@Override
	public String getDocumentNo()
	{
		return documentNo;
	}

	@Override
	public ITableRecordReference getReference()
	{
		return reference;
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
	public void addAllocatedAmt(final BigDecimal allocatedAmtToAdd)
	{
		allocatedAmt = allocatedAmt.add(allocatedAmtToAdd);
		amountToAllocate = amountToAllocate.subtract(allocatedAmtToAdd);
	}

	@Override
	public boolean isFullyAllocated()
	{
		return getAmountToAllocate().signum() == 0;
	}

	private BigDecimal getOpenAmtRemaining()
	{
		final BigDecimal totalAllocated = allocatedAmt;
		final BigDecimal openAmtRemaining = openAmtInitial.subtract(totalAllocated);
		return openAmtRemaining;
	}

	@Override
	public BigDecimal calculateProjectedOverUnderAmt(final BigDecimal amountToAllocate)
	{
		final BigDecimal projectedOverUnderAmt = getOpenAmtRemaining()
				.subtract(amountToAllocate);
		return projectedOverUnderAmt;
	}
	
	

	public static class Builder
	{
		private int bpartnerId;
		private String documentNo;
		private ITableRecordReference reference;
		private boolean isSOTrx;
		//
		private int C_Currency_ID;
		//
		
		public static final Set<PayableDocumentType> AllowedPayableDocumentTypes_ALL = Sets.immutableEnumSet(EnumSet.allOf(PayableDocumentType.class));
		private Set<PayableDocumentType> allowedPayableDocumentType = AllowedPayableDocumentTypes_ALL;
		//
		private BigDecimal openAmt = BigDecimal.ZERO;
		private BigDecimal amountToAllocate = BigDecimal.ZERO;

		private Builder()
		{
			super();
		}

		public PaymentDocument build()
		{
			return new PaymentDocument(this);
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
		
		public Builder setIsSOTrx(boolean isSOTrx)
		{
			this.isSOTrx = isSOTrx;
			return this;
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

		public Builder setDocumentNo(String documentNo)
		{
			this.documentNo = documentNo;
			return this;
		}

		private final String getDocumentNo()
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

		public Builder setOpenAmt(final BigDecimal openAmt)
		{
			this.openAmt = openAmt;
			return this;
		}

		private final BigDecimal getOpenAmt()
		{
			Check.assumeNotNull(openAmt, "openAmt not null");
			return openAmt;
		}

		public Builder setAmountToAllocate(final BigDecimal amountToAllocate)
		{
			this.amountToAllocate = amountToAllocate;
			return this;
		}

		private final BigDecimal getAmountToAllocate()
		{
			Check.assumeNotNull(amountToAllocate, "amountToAllocate not null");
			return amountToAllocate;
		}

		public Builder setReference(final ITableRecordReference reference)
		{
			this.reference = reference;
			return this;
		}

		public Builder setReference(final String tableName, final int recordId)
		{
			return setReference(new TableRecordReference(tableName, recordId));
		}

		private final ITableRecordReference getReference()
		{
			Check.assumeNotNull(reference, "reference not null");
			return reference;
		}

		public Builder setAllowedPayableDocumentType(final Set<PayableDocumentType> allowedPayableDocumentType)
		{
			Check.assumeNotNull(allowedPayableDocumentType, "allowedPayableDocumentType not null");
			this.allowedPayableDocumentType = allowedPayableDocumentType;
			return this;
		}

		public Builder setAllowedPayableDocumentType(final PayableDocumentType... allowedPayableDocumentType)
		{
			Check.assumeNotEmpty(allowedPayableDocumentType, "allowedPayableDocumentType not empty");
			return setAllowedPayableDocumentType(ImmutableSet.copyOf(allowedPayableDocumentType));
		}

		private final Set<PayableDocumentType> getAllowedPayableDocumentType()
		{
			return ImmutableSet.copyOf(allowedPayableDocumentType);
		}
	}

	@Override
	public boolean canPay(final IPayableDocument payable)
	{
		return allowedPayableDocumentType.contains(payable.getType());
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
}
