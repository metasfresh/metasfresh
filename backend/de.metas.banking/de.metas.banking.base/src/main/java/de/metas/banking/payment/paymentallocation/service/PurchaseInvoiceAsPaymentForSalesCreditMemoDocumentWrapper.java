/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

import de.metas.money.Money;
import de.metas.util.Check;
import lombok.NonNull;

public class PurchaseInvoiceAsPaymentForSalesCreditMemoDocumentWrapper extends AbstractPurchaseInvoiceAsPaymentDocumentWrapper
{

	private PurchaseInvoiceAsPaymentForSalesCreditMemoDocumentWrapper(@NonNull final PayableDocument purchasePayableDoc)
	{
		super(purchasePayableDoc);
		Check.assume(purchasePayableDoc.getSoTrx().isPurchase(), "is purchase document: {}", purchasePayableDoc);
		Check.assume(!purchasePayableDoc.isCreditMemo(), "is not credit memo: {}", purchasePayableDoc);

	}

	public static PurchaseInvoiceAsPaymentForSalesCreditMemoDocumentWrapper wrap(final PayableDocument creditMemoPayableDoc)
	{
		return new PurchaseInvoiceAsPaymentForSalesCreditMemoDocumentWrapper(creditMemoPayableDoc);
	}

	@Override
	public Money calculateProjectedOverUnderAmt(final Money amountToAllocate)
	{
		// This purchase invoice will be considered "paid" by the payable sales credit memo it is allocated to, Therefore, the amountToAllocate does not have to be negated
		return purchaseInvoicePayableDoc.computeProjectedOverUnderAmt(AllocationAmounts.ofPayAmt(amountToAllocate));
	}

}


