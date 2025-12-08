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
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.ClientAndOrgId;
import de.metas.payment.PaymentCurrencyContext;
import de.metas.payment.PaymentDirection;
import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;

import java.time.LocalDate;

/**
 * Wraps a credit memo {@link PayableDocument} and behaves like a {@link IPaymentDocument}.
 *
 * @author tsa
 */
@EqualsAndHashCode
final class CreditMemoInvoiceAsPaymentDocumentWrapper implements IPaymentDocument
{
	public static CreditMemoInvoiceAsPaymentDocumentWrapper wrap(final PayableDocument creditMemoPayableDoc)
	{
		return new CreditMemoInvoiceAsPaymentDocumentWrapper(creditMemoPayableDoc);
	}

	private final PayableDocument creditMemoPayableDoc;

	private CreditMemoInvoiceAsPaymentDocumentWrapper(@NonNull final PayableDocument creditMemoPayableDoc)
	{
		Check.assume(creditMemoPayableDoc.isCreditMemo(), "is credit memo: {}", creditMemoPayableDoc);
		this.creditMemoPayableDoc = creditMemoPayableDoc;
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + "[" + creditMemoPayableDoc.toString() + "]";
	}

	@Override
	public PaymentDocumentType getType()
	{
		return PaymentDocumentType.CreditMemoInvoice;
	}

	@Override
	public ClientAndOrgId getClientAndOrgId()
	{
		return creditMemoPayableDoc.getClientAndOrgId();
	}

	@Override
	public BPartnerId getBpartnerId()
	{
		return creditMemoPayableDoc.getBpartnerId();
	}

	@Override
	public String getDocumentNo()
	{
		return creditMemoPayableDoc.getDocumentNo();
	}

	@Override
	public TableRecordReference getReference()
	{
		return creditMemoPayableDoc.getReference();
	}

	@Override
	public Money getAmountToAllocateInitial()
	{
		return creditMemoPayableDoc.getAmountsToAllocateInitial().getPayAmt().negate();
	}

	@Override
	public Money getAmountToAllocate()
	{
		return creditMemoPayableDoc.getAmountsToAllocate().getPayAmt().negate();
	}

	@Override
	public void addAllocatedAmt(final Money allocatedPayAmtToAdd)
	{
		creditMemoPayableDoc.addAllocatedAmounts(AllocationAmounts.ofPayAmt(allocatedPayAmtToAdd.negate()));
	}

	/**
	 * Check only the payAmt as that's the only value we are allocating. see {@link  CreditMemoInvoiceAsPaymentDocumentWrapper#addAllocatedAmt(Money)}
	 */
	@Override
	public boolean isFullyAllocated()
	{
		return creditMemoPayableDoc.getAmountsToAllocate().getPayAmt().isZero();
	}

	/**
	 * Computes projected over under amt taking into account discount.
	 *
	 * @implNote for credit memo as payment, the negated discount needs to be added to the open amount. Negated value is used
	 * as it actually needs to increase the open amount.
	 * 
	 * e.g. Having a credit memo with totalGrandAmount = 10 and paymentTerm.Discount=10% translates to 11 total payment amount available.
	 */
	@Override
	public Money calculateProjectedOverUnderAmt(@NonNull final Money payAmountToAllocate)
	{
		final Money discountAmt = creditMemoPayableDoc.getAmountsToAllocateInitial().getDiscountAmt().negate(); 
		final Money openAmtWithDiscount = creditMemoPayableDoc.getOpenAmtInitial().add(discountAmt);
		
		final Money remainingOpenAmtWithDiscount = openAmtWithDiscount.subtract(creditMemoPayableDoc.getTotalAllocatedAmount());
		
		final Money adjustedPayAmountToAllocate = payAmountToAllocate.negate();

		return remainingOpenAmtWithDiscount.subtract(adjustedPayAmountToAllocate);
	}

	@Override
	public boolean canPay(@NonNull final PayableDocument payable)
	{
		if (payable.getType() != PayableDocumentType.Invoice)
		{
			return false;
		}
		if (payable.getSoTrx() != creditMemoPayableDoc.getSoTrx())
		{
			return false;
		}

		// A credit memo cannot pay another credit memo
		if (payable.isCreditMemo())
		{
			return false;
		}

		return true;
	}

	@Override
	public PaymentDirection getPaymentDirection()
	{
		return PaymentDirection.ofSOTrx(creditMemoPayableDoc.getSoTrx());
	}

	@Override
	public CurrencyId getCurrencyId()
	{
		return creditMemoPayableDoc.getCurrencyId();
	}

	@Override
	public LocalDate getDate()
	{
		return creditMemoPayableDoc.getDate();
	}

	@Override
	public PaymentCurrencyContext getPaymentCurrencyContext()
	{
		return PaymentCurrencyContext.builder()
				.paymentCurrencyId(creditMemoPayableDoc.getCurrencyId())
				.currencyConversionTypeId(creditMemoPayableDoc.getCurrencyConversionTypeId())
				.build();
	}

	@Override
	public Money getPaymentDiscountAmt()
	{
		return creditMemoPayableDoc.getAmountsToAllocate().getDiscountAmt();
	}
}
