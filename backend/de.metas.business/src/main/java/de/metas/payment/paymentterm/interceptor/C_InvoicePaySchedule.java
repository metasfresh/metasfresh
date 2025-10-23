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
import de.metas.invoice.service.impl.InvoiceBL;
import de.metas.order.OrderId;
import de.metas.order.paymentschedule.InvoicePayScheduleService;
import de.metas.order.paymentschedule.OrderPayScheduleService;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxListenerManager;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoicePaySchedule;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Interceptor(I_C_InvoicePaySchedule.class)
@Component
@RequiredArgsConstructor
public class C_InvoicePaySchedule
{
	@NonNull private final InvoicePayScheduleService invoicePayScheduleService;
	@NonNull private final OrderPayScheduleService orderPayScheduleService;
	@NonNull private final InvoiceBL invoiceBL = Services.get(InvoiceBL.class);
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);

	@NonNull private static final ThreadLocal<Set<OrderId>> to_validate_OrderIds = ThreadLocal.withInitial(HashSet::new);

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = { I_C_InvoicePaySchedule.COLUMNNAME_Processed })
	public void updateOrderPaySchedules(@NonNull final I_C_InvoicePaySchedule record)
	{
		final OrderId orderId = OrderId.ofRepoIdOrNull(record.getC_Order_ID());
		if (orderId != null)
		{
			final Set<OrderId> idsToValidate = to_validate_OrderIds.get();
			final ITrxListenerManager listenerManager = trxManager.getTrxListenerManager(InterfaceWrapperHelper.getTrxName(record));

			if (idsToValidate.add(orderId))
			{
				listenerManager.runBeforeCommit(() -> orderPayScheduleService.updatePayScheduleStatus(orderId));

				listenerManager.runAfterCommit(to_validate_OrderIds::remove);
			}
		}

	}

	@NonNull private static final ThreadLocal<Set<InvoiceId>> to_validate_invoiceIds = ThreadLocal.withInitial(HashSet::new);

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = { I_C_InvoicePaySchedule.COLUMNNAME_DueAmt })
	public void validateInvoicePaySchedules(@NonNull final I_C_InvoicePaySchedule record)
	{
		final Set<InvoiceId> idsToValidate = to_validate_invoiceIds.get();
		final InvoiceId invoiceId = InvoiceId.ofRepoId(record.getC_Invoice_ID());
		final ITrxListenerManager listenerManager = trxManager.getTrxListenerManager(InterfaceWrapperHelper.getTrxName(record));

		if (idsToValidate.add(invoiceId))
		{
			listenerManager.runBeforeCommit(() -> {

				final I_C_Invoice invoiceRecord = invoiceBL.getById(invoiceId);
				final boolean isValid = invoicePayScheduleService.validate(invoiceId, invoiceRecord.getGrandTotal());

				if (invoiceRecord.isPayScheduleValid() != isValid)
				{
					invoiceRecord.setIsPayScheduleValid(isValid);
					invoiceBL.save(invoiceRecord);
				}

			});

			listenerManager.runAfterCommit(to_validate_invoiceIds::remove);
		}

	}

}