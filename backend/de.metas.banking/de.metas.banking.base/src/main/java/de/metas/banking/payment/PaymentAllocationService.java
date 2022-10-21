/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.banking.payment;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.banking.payment.paymentallocation.InvoiceToAllocate;
import de.metas.banking.payment.paymentallocation.InvoiceToAllocateQuery;
import de.metas.banking.payment.paymentallocation.PaymentAllocationRepository;
import de.metas.currency.Amount;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.UnpaidInvoiceMatchingAmtQuery;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PaymentAllocationService
{
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

	private final PaymentAllocationRepository paymentAllocationRepository;

	public PaymentAllocationService(@NonNull final PaymentAllocationRepository paymentAllocationRepository)
	{
		this.paymentAllocationRepository = paymentAllocationRepository;
	}

	@NonNull
	public ImmutableList<I_C_Invoice> retrieveUnpaidInvoices(@NonNull final UnpaidInvoiceMatchingAmtQuery unpaidInvoiceMatchingAmtQuery)
	{
		final ImmutableList<I_C_Invoice> unpaidInvoices = invoiceDAO.retrieveUnpaid(unpaidInvoiceMatchingAmtQuery.getUnpaidInvoiceQuery());

		if (unpaidInvoiceMatchingAmtQuery.getOpenAmountAtDate() == null || unpaidInvoiceMatchingAmtQuery.getOpenAmountEvaluationDate() == null)
		{
			return unpaidInvoices;
		}

		final Map<InvoiceId, I_C_Invoice> id2Invoice = Maps.uniqueIndex(unpaidInvoices, inv -> InvoiceId.ofRepoId(inv.getC_Invoice_ID()));

		final InvoiceToAllocateQuery query = InvoiceToAllocateQuery.builder()
				.evaluationDate(unpaidInvoiceMatchingAmtQuery.getOpenAmountEvaluationDate())
				.onlyInvoiceIds(id2Invoice.keySet())
				.build();

		return paymentAllocationRepository.retrieveInvoicesToAllocate(query)
				.stream()
				.filter(invoiceToAllocate -> isOpenAmtWithDiscountMatching(invoiceToAllocate, unpaidInvoiceMatchingAmtQuery.getOpenAmountAtDate()))
				.map(InvoiceToAllocate::getInvoiceId)
				.map(id2Invoice::get)
				.collect(ImmutableList.toImmutableList());
	}

	private boolean isOpenAmtWithDiscountMatching(
			@NonNull final InvoiceToAllocate invoiceToAllocate,
			@NonNull final Amount openAmountToMatch)
	{
		final InvoiceId invoiceId = invoiceToAllocate.getInvoiceId();
		if (invoiceId == null)
		{
			return false;
		}

		final Amount openAmtWithDiscount = invoiceToAllocate.getOpenAmountConverted().subtract(invoiceToAllocate.getDiscountAmountConverted());

		final boolean isSoTrx = invoiceToAllocate.getDocBaseType().isSales();
		if (isSoTrx)
		{
			return openAmountToMatch.equals(openAmtWithDiscount);
		}
		else
		{
			return openAmountToMatch.abs().equals(openAmtWithDiscount);
		}
	}
}
