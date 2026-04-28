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

package de.metas.payment.paymentterm.interceptor;

import de.metas.invoice.InvoiceId;
import de.metas.invoice.paymentschedule.service.InvoicePayScheduleService;
import de.metas.order.OrderId;
import de.metas.order.paymentschedule.service.OrderPayScheduleService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_InvoicePaySchedule;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_InvoicePaySchedule.class)
@Component
@RequiredArgsConstructor
public class C_InvoicePaySchedule
{
	@NonNull private final InvoicePayScheduleService invoicePayScheduleService;
	@NonNull private final OrderPayScheduleService orderPayScheduleService;

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = { I_C_InvoicePaySchedule.COLUMNNAME_Processed })
	public void updateOrderPaySchedules(@NonNull final I_C_InvoicePaySchedule record, @NonNull final ModelChangeType changeType)
	{
		final OrderId orderId = OrderId.ofRepoIdOrNull(record.getC_Order_ID());
		if (orderId != null)
		{
			orderPayScheduleService.updatePayScheduleStatusesBeforeCommit(orderId);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = { I_C_InvoicePaySchedule.COLUMNNAME_DueAmt })
	public void validateInvoicePaySchedules(@NonNull final I_C_InvoicePaySchedule record)
	{
		final InvoiceId invoiceId = InvoiceId.ofRepoId(record.getC_Invoice_ID());
		invoicePayScheduleService.validateInvoiceBeforeCommit(invoiceId);
	}
}