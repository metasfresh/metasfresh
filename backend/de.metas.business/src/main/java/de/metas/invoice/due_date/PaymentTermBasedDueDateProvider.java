package de.metas.invoice.due_date;

import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.PaymentTermService;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/*
 * #%L
 * de.metas.dunning
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Component
@RequiredArgsConstructor
public class PaymentTermBasedDueDateProvider implements InvoiceDueDateProvider
{
	@NonNull private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	@NonNull private final PaymentTermService paymentTermService;

	@Override
	public LocalDate provideDueDateOrNull(@NonNull final InvoiceId invoiceId)
	{
		final I_C_Invoice invoiceRecord = invoiceBL.getById(invoiceId);

		return paymentTermService.computeDueDateFromPaymentTerm(
				PaymentTermId.ofRepoId(invoiceRecord.getC_PaymentTerm_ID()),
				TimeUtil.asLocalDate(invoiceRecord.getDateInvoiced())
		);
	}
}
