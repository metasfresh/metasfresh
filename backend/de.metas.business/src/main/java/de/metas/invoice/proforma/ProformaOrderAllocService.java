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

import de.metas.i18n.AdMessageKey;
import de.metas.invoice.InvoiceId;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.order.paymentschedule.steps.letter_of_credit.OrderPayScheduleLCStepService;
import de.metas.payment.api.IPaymentDAO;
import de.metas.payment.paymentterm.PaymentTermService;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Order;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProformaOrderAllocService
{
	private static final AdMessageKey MSG_CannotDeallocateWhenPaid = AdMessageKey.of("de.metas.invoice.proforma.CannotDeallocateWhenPaid");

	@NonNull private final IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);
	@NonNull private final IOrderBL orderBL = Services.get(IOrderBL.class);

	@NonNull private final ProformaOrderAllocRepository repository;
	@NonNull private final PaymentTermService paymentTermService;
	@NonNull private final OrderPayScheduleLCStepService orderPayScheduleLCStepService;

	@NonNull
	public ProformaOrderAlloc getById(@NonNull final ProformaOrderAllocId proformaOrderAllocId)
	{
		return repository.getById(proformaOrderAllocId);
	}

	public void allocate(@NonNull final InvoiceId proformaInvoiceId, @NonNull final OrderId purchaseOrderId)
	{
		ProformaOrderAllocateCommand.builder()
				.proformaOrderAllocRepository(repository)
				.paymentTermService(paymentTermService)
				.orderPayScheduleLCStepService(orderPayScheduleLCStepService)
				.proformaInvoiceId(proformaInvoiceId)
				.purchaseOrderId(purchaseOrderId)
				.build()
				.execute();
	}

	public boolean hasAllocations(@NonNull final InvoiceId invoiceId)
	{
		return !repository.getByInvoiceId(invoiceId).isEmpty();
	}

	public void deallocate(@NonNull final ProformaOrderAlloc alloc)
	{
		// Reject if a completed/closed proforma payment still points at this proforma. Removing
		// the allocation would leave the payment with Proforma_Invoice_ID pointing at a now-
		// unallocated proforma — an inconsistent state. The user must reverse the payment first.
		final InvoiceId proformaInvoiceId = alloc.getInvoiceId();
		if (paymentDAO.findCompletedOrClosedByProformaInvoiceId(proformaInvoiceId).isPresent())
		{
			final I_C_Order order = orderBL.getById(alloc.getOrderId());
			throw new AdempiereException(MSG_CannotDeallocateWhenPaid, proformaInvoiceId.getRepoId(), order.getDocumentNo())
					.markAsUserValidationError();
		}

		// Capture orderId before deletion — the alloc row will be gone after deleteById.
		final OrderId orderId = alloc.getOrderId();
		repository.deleteById(alloc.getId());
		orderPayScheduleLCStepService.recomputeLCStep(orderId);
	}

	/**
	 * Removes every active allocation linked to the given proforma invoice. Each row goes through
	 * {@link #deallocate(ProformaOrderAlloc)}, which guards against deallocating when a
	 * completed/closed proforma payment still references the invoice and recomputes the LC step
	 * for the order. No-op when the invoice has zero active allocations.
	 */
	public void deallocateAll(@NonNull final InvoiceId proformaInvoiceId)
	{
		repository.getByInvoiceId(proformaInvoiceId).forEach(this::deallocate);
	}
}
