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

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.invoice.InvoiceId;
import de.metas.payment.paymentterm.PaymentTermService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.Adempiere;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvoicePayScheduleService
{
	@NonNull private final InvoicePayScheduleRepository invoicePayScheduleRepository;
	@NonNull private final PaymentTermService paymentTermService;

	public static InvoicePayScheduleService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();

		return new InvoicePayScheduleService(
				new InvoicePayScheduleRepository(),
				new PaymentTermService()
		);
	}

	public void createInvoicePaySchedules(final I_C_Invoice invoice)
	{
		InvoicePayScheduleCreateCommand.builder()
				.invoicePayScheduleService(this)
				.paymentTermService(paymentTermService)
				.invoiceRecord(invoice)
				.build()
				.execute();
	}

	public void deleteByInvoiceId(@NonNull final InvoiceId invoiceId) {invoicePayScheduleRepository.deleteByInvoiceId(invoiceId);}

	public void create(@NonNull final InvoicePayScheduleCreateRequest request)
	{
		invoicePayScheduleRepository.create(request);
	}

}
