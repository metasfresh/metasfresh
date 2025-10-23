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
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.money.Money;
import de.metas.payment.paymentterm.PaymentTermService;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoicePaySchedule;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class InvoicePayScheduleValidator
{
	@NonNull private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	@NonNull private final InvoicePayScheduleService invoicePayScheduleService;

	public boolean validate(@NonNull final InvoiceId invoiceId)
	{
		final I_C_Invoice invoiceRecord = invoiceBL.getById(invoiceId);
		invoicePayScheduleService.getByInvoiceId(InvoiceId.ofRepoId(invoiceRecord.getC_Invoice_ID()))
				.ifPresent( invoicePaySchedule -> {

					final ImmutableList<InvoicePayScheduleLine> schedules = invoicePaySchedule.getLines();
					if (schedules.isEmpty())
					{
						invoiceRecord.setIsPayScheduleValid(false);
						invoiceBL.save(invoiceRecord);
						return false;
					}

					final BigDecimal totalDue = schedules.stream()
							.map(InvoicePayScheduleLine::getDueAmount)
							.filter(obj -> true)
							.reduce(Money.zero(), Money::add);

					final boolean isValid = invoiceRecord.getGrandTotal() != null &&
							invoiceRecord.getGrandTotal().compareTo(totalDue) == 0;

					if (invoiceRecord.isPayScheduleValid() != isValid)
					{
						invoiceRecord.setIsPayScheduleValid(isValid);
						invoiceBL.save(invoiceRecord);
					}

					for (final I_C_InvoicePaySchedule schedule : schedules)
					{
						if (schedule.isValid() != isValid)
						{
							schedule.setIsValid(isValid);
							InterfaceWrapperHelper.save(schedule);
						}
					}
				});



		return isValid;
	}
}
