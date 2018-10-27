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

import de.metas.banking.payment.paymentallocation.service.IPayableDocument.PayableDocumentType;
import de.metas.util.Check;

/**
 * Wraps a credit memo {@link IPayableDocument} and behaves like a {@link IPaymentDocument}.
 * 
 * @author tsa
 *
 */
public class CreditMemoInvoiceAsPaymentDocument implements IPaymentDocument
{
	public static final CreditMemoInvoiceAsPaymentDocument wrap(final IPayableDocument creditMemoPayableDoc)
	{
		return new CreditMemoInvoiceAsPaymentDocument(creditMemoPayableDoc);
	}

	private final IPayableDocument creditMemoPayableDoc;

	private CreditMemoInvoiceAsPaymentDocument(final IPayableDocument creditMemoPayableDoc)
	{
		super();
		Check.assumeNotNull(creditMemoPayableDoc, "creditMemoPayableDoc not null");
		Check.assume(creditMemoPayableDoc.isCreditMemo(), "is credit memo: {}", creditMemoPayableDoc);
		this.creditMemoPayableDoc = creditMemoPayableDoc;
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + "[" + creditMemoPayableDoc.toString() + "]";
	}

	@Override
	public int getC_BPartner_ID()
	{
		return creditMemoPayableDoc.getC_BPartner_ID();
	}

	@Override
	public String getDocumentNo()
	{
		return creditMemoPayableDoc.getDocumentNo();
	}

	@Override
	public ITableRecordReference getReference()
	{
		return creditMemoPayableDoc.getReference();
	}

	@Override
	public BigDecimal getAmountToAllocateInitial()
	{
		return creditMemoPayableDoc.getAmountToAllocateInitial().negate();
	}

	@Override
	public BigDecimal getAmountToAllocate()
	{
		return creditMemoPayableDoc.getAmountToAllocate().negate();
	}

	@Override
	public void addAllocatedAmt(BigDecimal allocatedAmtToAdd)
	{
		creditMemoPayableDoc.addAllocatedAmounts(allocatedAmtToAdd.negate(), BigDecimal.ZERO, BigDecimal.ZERO);
	}

	@Override
	public boolean isFullyAllocated()
	{
		return creditMemoPayableDoc.isFullyAllocated();
	}

	@Override
	public BigDecimal calculateProjectedOverUnderAmt(BigDecimal amountToAllocate)
	{
		return creditMemoPayableDoc.calculateProjectedOverUnderAmt(amountToAllocate.negate(), BigDecimal.ZERO, BigDecimal.ZERO);
	}

	@Override
	public boolean canPay(IPayableDocument payable)
	{
		if (payable.getType() != PayableDocumentType.Invoice)
		{
			return false;
		}
		if (payable.isCustomerDocument() != creditMemoPayableDoc.isCustomerDocument())
		{
			return false;
		}

		// A credit memo cannot pay another credit memo
		if (payable.isCreditMemo())
		{
			return false;
		}
		
		// if currency differs, do not allow payment
		if (payable.getC_Currency_ID() != creditMemoPayableDoc.getC_Currency_ID())
		{
			return false;
		}

		return true;
	}

	@Override
	public boolean isCustomerDocument()
	{
		return creditMemoPayableDoc.isCustomerDocument();
	}

	@Override
	public boolean isVendorDocument()
	{
		return creditMemoPayableDoc.isVendorDocument();
	}

	@Override
	public int getC_Currency_ID()
	{
		return creditMemoPayableDoc.getC_Currency_ID();
	}
	
}
