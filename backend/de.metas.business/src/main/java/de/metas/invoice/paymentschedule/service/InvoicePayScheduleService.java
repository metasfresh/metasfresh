/*
 * #%L
 * de.metas.business
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

package de.metas.invoice.paymentschedule.service;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.paymentschedule.InvoicePaySchedule;
import de.metas.invoice.paymentschedule.repository.InvoicePayScheduleRepository;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.money.Money;
import de.metas.order.paymentschedule.service.OrderPayScheduleService;
import de.metas.payment.paymentterm.PaymentTermService;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.I_C_Invoice;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class InvoicePayScheduleService
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	@NonNull private final InvoicePayScheduleRepository invoicePayScheduleRepository;
	@NonNull private final OrderPayScheduleService orderPayScheduleService;
	@NonNull private final PaymentTermService paymentTermService;

	public Optional<InvoicePaySchedule> getByInvoiceId(@NonNull final InvoiceId invoiceId)
	{
		return invoicePayScheduleRepository.getByInvoiceId(invoiceId);
	}

	public void createInvoicePaySchedules(final I_C_Invoice invoice)
	{
		InvoicePayScheduleCreateCommand.builder()
				.invoicePayScheduleRepository(invoicePayScheduleRepository)
				.orderPayScheduleService(orderPayScheduleService)
				.paymentTermService(paymentTermService)
				.invoiceRecord(invoice)
				.build()
				.execute();
	}

	public void validateInvoiceBeforeCommit(@NonNull final InvoiceId invoiceId)
	{
		trxManager.accumulateAndProcessBeforeCommit(
				"InvoicePayScheduleService.validateInvoiceBeforeCommit",
				Collections.singleton(invoiceId),
				invoiceIds -> validateInvoicesNow(ImmutableSet.copyOf(invoiceIds))
		);
	}

	private void validateInvoicesNow(final Set<InvoiceId> invoiceIds)
	{
		if (invoiceIds.isEmpty()) {return;}

		final ImmutableMap<InvoiceId, I_C_Invoice> invoicesById = Maps.uniqueIndex(
				invoiceBL.getByIds(invoiceIds),
				invoice -> InvoiceId.ofRepoId(invoice.getC_Invoice_ID())
		);

		invoicePayScheduleRepository.updateByIds(invoicesById.keySet(), invoicePaySchedule -> {
			final I_C_Invoice invoice = invoicesById.get(invoicePaySchedule.getInvoiceId());
			final Money grandTotal = invoiceBL.extractGrandTotal(invoice).toMoney();
			boolean isValid = invoicePaySchedule.validate(grandTotal);
			invoice.setIsPayScheduleValid(isValid);
			invoiceBL.save(invoice);
		});
	}

}
