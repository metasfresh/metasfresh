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

import org.adempiere.util.Check;
import org.adempiere.util.lang.ITableRecordReference;

import de.metas.banking.payment.paymentallocation.service.IPayableDocument.PayableDocumentType;

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class PurchaseInvoiceAsPaymentDocument implements IPaymentDocument
{
	public static final PurchaseInvoiceAsPaymentDocument wrap(final IPayableDocument creditMemoPayableDoc)
	{
		return new PurchaseInvoiceAsPaymentDocument(creditMemoPayableDoc);
	}

	private final IPayableDocument purchaseInvoicePayableDoc;

	private PurchaseInvoiceAsPaymentDocument(final IPayableDocument purchasePayableDoc)
	{
		super();
		Check.assumeNotNull(purchasePayableDoc, "purchasePayableDoc not null");
		Check.assume(!purchasePayableDoc.isCreditMemo(), "is not credit memo: {}", purchasePayableDoc);
		Check.assume(purchasePayableDoc.isVendorDocument(), "is vendor document: {}", purchasePayableDoc);
		this.purchaseInvoicePayableDoc = purchasePayableDoc;
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + "[" + purchaseInvoicePayableDoc.toString() + "]";
	}

	@Override
	public int getC_BPartner_ID()
	{
		return purchaseInvoicePayableDoc.getC_BPartner_ID();
	}

	@Override
	public String getDocumentNo()
	{
		return purchaseInvoicePayableDoc.getDocumentNo();
	}

	@Override
	public ITableRecordReference getReference()
	{
		return purchaseInvoicePayableDoc.getReference();
	}

	@Override
	public BigDecimal getAmountToAllocateInitial()
	{
		return purchaseInvoicePayableDoc.getAmountToAllocateInitial().negate();
	}

	@Override
	public BigDecimal getAmountToAllocate()
	{
		return purchaseInvoicePayableDoc.getAmountToAllocate().negate();
	}

	@Override
	public void addAllocatedAmt(BigDecimal allocatedAmtToAdd)
	{
		purchaseInvoicePayableDoc.addAllocatedAmounts(allocatedAmtToAdd.negate(), BigDecimal.ZERO, BigDecimal.ZERO);
	}

	@Override
	public boolean isFullyAllocated()
	{
		return purchaseInvoicePayableDoc.isFullyAllocated();
	}

	@Override
	public BigDecimal calculateProjectedOverUnderAmt(BigDecimal amountToAllocate)
	{
		return purchaseInvoicePayableDoc.calculateProjectedOverUnderAmt(amountToAllocate.negate(), BigDecimal.ZERO, BigDecimal.ZERO);
	}

	@Override
	public boolean canPay(IPayableDocument payable)
	{
		if (payable.getType() != PayableDocumentType.Invoice)
		{
			return false;
		}
		if (payable.isCustomerDocument() != purchaseInvoicePayableDoc.isVendorDocument())
		{
			return false;
		}

		// A purchase invoice cannot pay another purchase invoice
		if (payable.isVendorDocument())
		{
			return false;
		}

		// if currency differs, do not allow payment
		if (payable.getC_Currency_ID() != purchaseInvoicePayableDoc.getC_Currency_ID())
		{
			return false;
		}

		return true;
	}

	@Override
	public boolean isCustomerDocument()
	{
		return purchaseInvoicePayableDoc.isCustomerDocument();
	}

	@Override
	public boolean isVendorDocument()
	{
		return purchaseInvoicePayableDoc.isVendorDocument();
	}
	
	@Override
	public int getC_Currency_ID()
	{
		return purchaseInvoicePayableDoc.getC_Currency_ID();
	}
	

}
