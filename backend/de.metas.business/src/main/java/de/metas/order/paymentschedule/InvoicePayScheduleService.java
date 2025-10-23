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

package de.metas.order.paymentschedule;

import com.google.common.collect.ImmutableList;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.payment.paymentterm.PaymentTermService;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
public class InvoicePayScheduleService
{
	@NonNull private final InvoicePayScheduleRepository invoicePayScheduleRepository;
	@NonNull private final OrderPayScheduleService orderPayScheduleService;
	@NonNull private final PaymentTermService paymentTermService;
	@NonNull private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);

	public void createInvoicePaySchedules(final I_C_Invoice invoice)
	{
		InvoicePayScheduleCreateCommand.builder()
				.invoicePayScheduleService(this)
				.orderPayScheduleService(orderPayScheduleService)
				.paymentTermService(paymentTermService)
				.invoiceRecord(invoice)
				.build()
				.execute();
	}

	public void deleteByInvoiceId(@NonNull final InvoiceId invoiceId) {invoicePayScheduleRepository.deleteByInvoiceId(invoiceId);}

	public void create(@NonNull final InvoicePayScheduleCreateRequest request) {invoicePayScheduleRepository.create(request);}

	public Optional<InvoicePaySchedule> getByInvoiceId(@NonNull final InvoiceId invoiceId) {return invoicePayScheduleRepository.getByInvoiceId(invoiceId);}

	public boolean validate(@NonNull final InvoiceId invoiceId)
	{
		final AtomicBoolean isValid = new AtomicBoolean(false);

		final org.compiere.model.I_C_Invoice invoiceRecord = invoiceBL.getById(invoiceId);
		getByInvoiceId(InvoiceId.ofRepoId(invoiceRecord.getC_Invoice_ID()))
				.ifPresent(invoicePaySchedule -> {

					final ImmutableList<InvoicePayScheduleLine> schedules = invoicePaySchedule.getLines();
					if (schedules.isEmpty())
					{
						invoiceRecord.setIsPayScheduleValid(false);
						invoiceBL.save(invoiceRecord);
						isValid.set(false);
					}

					final BigDecimal totalDue = schedules.stream()
							.map(InvoicePayScheduleLine::getDueAmount)
							.reduce(Money.zero(CurrencyId.ofRepoId(invoiceRecord.getC_Currency_ID())), Money::add)
							.toBigDecimal();

					isValid.set(invoiceRecord.getGrandTotal().compareTo(totalDue) == 0);

					if (invoiceRecord.isPayScheduleValid() != isValid.get())
					{
						invoiceRecord.setIsPayScheduleValid(isValid.get());
						invoiceBL.save(invoiceRecord);
					}

					markAsValid(invoiceId, isValid.get());
				});

		return isValid.get();
	}

	private void markAsValid(@NonNull final InvoiceId invoiceId, final boolean isValid)
	{
		invoicePayScheduleRepository.updateById(invoiceId, invoicePaySchedule -> invoicePaySchedule.markAsValid(isValid));
	}

}
