/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.invoice.proforma;

import de.metas.invoice.InvoiceId;
import de.metas.order.IOrderBL;
import de.metas.order.paymentschedule.service.OrderPayScheduleService;
import de.metas.payment.paymentterm.PaymentTermService;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.I_C_Order;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProformaOrderAllocService
{
	@NonNull private final ProformaOrderAllocRepository repository;
	@NonNull private final PaymentTermService paymentTermService;
	@NonNull private final OrderPayScheduleService orderPayScheduleService;
	@NonNull private final IOrderBL orderBL = Services.get(IOrderBL.class);

	@NonNull
	public ProformaOrderAlloc getById(@NonNull final ProformaOrderAllocId proformaOrderAllocId)
	{
		return repository.getById(proformaOrderAllocId);
	}

	public void allocate(@NonNull final ProformaOrderAllocateRequest request)
	{
		ProformaOrderAllocateCommand.builder()
				.proformaOrderAllocRepository(repository)
				.paymentTermService(paymentTermService)
				.orderPayScheduleService(orderPayScheduleService)
				.request(request)
				.build()
				.execute();
	}

	public boolean hasAllocations(@NonNull final InvoiceId invoiceId)
	{
		return !repository.getByInvoiceId(invoiceId).isEmpty();
	}

	public void deallocate(@NonNull final ProformaOrderAlloc alloc)
	{
		// TODO: check additional steps needed (e.g. check and handling of payments)

		repository.deleteById(alloc.getId());

		final I_C_Order order = orderBL.getById(alloc.getOrderId());
		order.setLC_Date(null);
		orderBL.save(order);
	}
}
