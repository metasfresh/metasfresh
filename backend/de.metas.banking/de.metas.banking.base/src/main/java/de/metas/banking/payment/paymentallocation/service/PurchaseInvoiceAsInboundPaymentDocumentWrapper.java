/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.banking.payment.paymentallocation.service;

import de.metas.banking.payment.paymentallocation.service.PayableDocument.PayableDocumentType;
import de.metas.bpartner.BPartnerId;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.ClientAndOrgId;
import de.metas.payment.PaymentDirection;
import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;
import java.time.LocalDate;

/**
 * Wraps a given purchase invoice as an inbound payment document which we will try to allocate to some other sales invoice that we issued.
 * <p>
 * In other words, if we issued a sales invoice to one of our customers,
 * but the "customer" issued an purchase invoice to us, that purchase invoice can be used to compensate our customer invoice.
 */
@EqualsAndHashCode
final class PurchaseInvoiceAsInboundPaymentDocumentWrapper implements IPaymentDocument
{
	public static PurchaseInvoiceAsInboundPaymentDocumentWrapper wrap(final PayableDocument creditMemoPayableDoc)
	{
		return new PurchaseInvoiceAsInboundPaymentDocumentWrapper(creditMemoPayableDoc);
	}

	private final PayableDocument purchaseInvoicePayableDoc;

	private PurchaseInvoiceAsInboundPaymentDocumentWrapper(@NonNull final PayableDocument purchasePayableDoc)
	{
		Check.assume(purchasePayableDoc.getSoTrx().isPurchase(), "is purchase document: {}", purchasePayableDoc);
		Check.assume(!purchasePayableDoc.isCreditMemo(), "is not credit memo: {}", purchasePayableDoc);

		this.purchaseInvoicePayableDoc = purchasePayableDoc;
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + "[" + purchaseInvoicePayableDoc.toString() + "]";
	}

	@Override
	public PaymentDocumentType getType()
	{
		return PaymentDocumentType.PurchaseInvoice;
	}

	@Override
	public PaymentDirection getPaymentDirection()
	{
		return PaymentDirection.INBOUND;
	}

	@Override
	public ClientAndOrgId getClientAndOrgId()
	{
		return purchaseInvoicePayableDoc.getClientAndOrgId();
	}

	@Override
	public BPartnerId getBpartnerId()
	{
		return purchaseInvoicePayableDoc.getBpartnerId();
	}

	@Override
	public String getDocumentNo()
	{
		return purchaseInvoicePayableDoc.getDocumentNo();
	}

	@Override
	public TableRecordReference getReference()
	{
		return purchaseInvoicePayableDoc.getReference();
	}

	@Override
	public Money getAmountToAllocateInitial()
	{
		return purchaseInvoicePayableDoc.getAmountsToAllocateInitial().getPayAmt().negate();
	}

	@Override
	public Money getAmountToAllocate()
	{
		return purchaseInvoicePayableDoc.getAmountsToAllocate().getPayAmt().negate();
	}

	@Override
	public void addAllocatedAmt(final Money allocatedAmtToAdd)
	{
		purchaseInvoicePayableDoc.addAllocatedAmounts(AllocationAmounts.ofPayAmt(allocatedAmtToAdd.negate()));
	}

	@Override
	public boolean isFullyAllocated()
	{
		return purchaseInvoicePayableDoc.isFullyAllocated();
	}

	@Override
	public Money calculateProjectedOverUnderAmt(final Money amountToAllocate)
	{
		return purchaseInvoicePayableDoc.computeProjectedOverUnderAmt(AllocationAmounts.ofPayAmt(amountToAllocate.negate()));
	}

	@Override
	public boolean canPay(@NonNull final PayableDocument payable)
	{
		// A purchase invoice can compensate only on a sales invoice
		if (payable.getType() != PayableDocumentType.Invoice)
		{
			return false;
		}
		if (!payable.getSoTrx().isSales())
		{
			return false;
		}

		// if currency differs, do not allow payment
		return CurrencyId.equals(payable.getCurrencyId(), purchaseInvoicePayableDoc.getCurrencyId());
	}

	@Override
	public CurrencyId getCurrencyId()
	{
		return purchaseInvoicePayableDoc.getCurrencyId();
	}

	@Override
	public LocalDate getDate()
	{
		return purchaseInvoicePayableDoc.getDate();
	}

	@Nullable
	@Override
	public CurrencyConversionTypeId getCurrencyConversionTypeId()
	{
		return purchaseInvoicePayableDoc.getCurrencyConversionTypeId();
	}
}
