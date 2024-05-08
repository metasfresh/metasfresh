package de.metas.dunning.invoice;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.sql.Timestamp;
import java.time.LocalDate;

import org.compiere.model.I_C_Invoice;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import de.metas.dunning.invoice.api.IInvoiceSourceDAO;
import de.metas.invoice.InvoiceId;
import de.metas.util.Services;
import lombok.NonNull;

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
public class PaymentTermBasedDueDateProvider
		implements InvoiceDueDateProvider
{
	@Override
	public LocalDate provideDueDateOrNull(@NonNull final InvoiceId invoiceId)
	{
		final I_C_Invoice invoiceRecord = load(invoiceId, I_C_Invoice.class);

		final IInvoiceSourceDAO invoiceSourceDAO = Services.get(IInvoiceSourceDAO.class);
		final Timestamp dueDate = invoiceSourceDAO.retrieveDueDate(invoiceRecord);

		return TimeUtil.asLocalDate(dueDate);
	}
}
