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
 * Wraps a credit memo {@link PayableDocument} and behaves like a {@link IPaymentDocument}.
 *
 * @author tsa
 *
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

	@Override
	public boolean isFullyAllocated()
	{
		return creditMemoPayableDoc.isFullyAllocated();
	}

	@Override
	public Money calculateProjectedOverUnderAmt(@NonNull final Money payAmountToAllocate)
	{
		return creditMemoPayableDoc.computeProjectedOverUnderAmt(AllocationAmounts.ofPayAmt(payAmountToAllocate.negate()));
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

	@Nullable
	@Override
	public CurrencyConversionTypeId getCurrencyConversionTypeId()
	{
		return creditMemoPayableDoc.getCurrencyConversionTypeId();
	}
}
