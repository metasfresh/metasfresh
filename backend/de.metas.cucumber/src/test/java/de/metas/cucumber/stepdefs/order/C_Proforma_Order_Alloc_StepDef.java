/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.order;

import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.proforma.ProformaOrderAlloc;
import de.metas.invoice.proforma.ProformaOrderAllocRepository;
import de.metas.invoice.proforma.ProformaOrderAllocService;
import de.metas.invoice.proforma.ProformaOrderAllocateRequest;
import de.metas.order.OrderId;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

/**
 * Step definitions for proforma-to-order allocation / deallocation.
 *
 * <p>These steps drive the {@link ProformaOrderAllocService} directly, bypassing the UI process
 * classes ({@code C_Invoice_Proforma_Allocate_Order} and {@code C_Proforma_Order_Alloc_DeAllocate}).
 * That is intentional: cucumber integration tests exercise the service layer, not the process wiring.
 *
 * @see ProformaOrderAllocService
 * @see C_Invoice_StepDefData
 * @see C_Order_StepDefData
 */
@RequiredArgsConstructor
public class C_Proforma_Order_Alloc_StepDef
{
	@NonNull private final C_Invoice_StepDefData invoiceTable;
	@NonNull private final C_Order_StepDefData orderTable;

	private final ProformaOrderAllocService proformaOrderAllocService = SpringContextHolder.instance.getBean(ProformaOrderAllocService.class);
	private final ProformaOrderAllocRepository proformaOrderAllocRepository = SpringContextHolder.instance.getBean(ProformaOrderAllocRepository.class);

	/**
	 * Allocates the given proforma invoice to the given purchase order.
	 *
	 * <p>Preconditions (enforced by the service):
	 * <ul>
	 *   <li>The invoice must be a completed Purchase Proforma (APF).</li>
	 *   <li>The order must have exactly one LC break in its payment term.</li>
	 *   <li>Currency and vendor must match between invoice and order.</li>
	 * </ul>
	 *
	 * <pre>{@code
	 * And I allocate proforma 'apf_inv' to order 'po'
	 * }</pre>
	 */
	@And("^I allocate proforma '(.*)' to order '(.*)'$")
	public void allocateProformaToOrder(
			@NonNull final String invoiceIdentifier,
			@NonNull final String orderIdentifier)
	{
		final InvoiceId proformaInvoiceId = invoiceTable.getId(invoiceIdentifier);
		final OrderId purchaseOrderId = orderTable.getId(orderIdentifier);

		final ProformaOrderAllocateRequest request = ProformaOrderAllocateRequest.builder()
				.proformaInvoiceId(proformaInvoiceId)
				.purchaseOrderId(purchaseOrderId)
				.build();

		proformaOrderAllocService.allocate(request);
	}

	/**
	 * Deallocates (removes) the allocation of the given proforma invoice from the given purchase order.
	 *
	 * <p>The active allocation record linking the proforma to the order is located and deleted,
	 * and the LC pay-schedule step is recomputed (LC → Pending, {@code DueAmt_Actual=NULL}, {@code LC_Date=NULL}).
	 *
	 * <pre>{@code
	 * And I deallocate proforma 'apf_inv' from order 'po'
	 * }</pre>
	 */
	@And("^I deallocate proforma '(.*)' from order '(.*)'$")
	public void deallocateProformaFromOrder(
			@NonNull final String invoiceIdentifier,
			@NonNull final String orderIdentifier)
	{
		final InvoiceId proformaInvoiceId = invoiceTable.getId(invoiceIdentifier);
		final OrderId purchaseOrderId = orderTable.getId(orderIdentifier);

		final ProformaOrderAlloc alloc = proformaOrderAllocRepository
				.findActiveByOrderId(purchaseOrderId)
				.filter(a -> InvoiceId.equals(a.getInvoiceId(), proformaInvoiceId))
				.orElseThrow(() -> new AdempiereException("No active proforma-order allocation found for invoiceId=" + proformaInvoiceId + " orderId=" + purchaseOrderId));

		proformaOrderAllocService.deallocate(alloc);
	}
}
