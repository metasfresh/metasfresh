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
import de.metas.order.OrderId;
import de.metas.order.paymentschedule.steps.letter_of_credit.OrderPayScheduleLCStepService;
import de.metas.payment.api.IPaymentDAO;
import de.metas.payment.paymentterm.PaymentTermService;
import de.metas.pricing.tax.ProductTaxCategoryRepository;
import de.metas.pricing.tax.ProductTaxCategoryService;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_C_Proforma_Order_Alloc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * JUnit for {@link ProformaOrderAllocService#deallocateAll(InvoiceId)}.
 *
 * @see <a href="https://github.com/metasfresh/me03/issues/29369">me03 #29369 Split-Payment Iter 3</a>
 */
class ProformaOrderAllocServiceTest
{
	private ProformaOrderAllocRepository repository;
	private ProformaOrderAllocService service;
	private IPaymentDAO paymentDAO;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(new ProductTaxCategoryService(new ProductTaxCategoryRepository()));

		// Mock IPaymentDAO so individual tests can pretend the proforma has a completed/closed payment.
		// Default behaviour: no completed payment exists — deallocate guard is silent.
		paymentDAO = mock(IPaymentDAO.class);
		when(paymentDAO.findCompletedOrClosedByProformaInvoiceId(any(InvoiceId.class))).thenReturn(Optional.empty());
		Services.registerService(IPaymentDAO.class, paymentDAO);

		// IOrderBL is only consulted on the guard-trip path (to fetch documentNo for the error message).
		// Provide a mock that returns a stub I_C_Order for any orderId so the third test does not blow up
		// reaching for an unregistered service.
		final IOrderBL orderBL = mock(IOrderBL.class);
		final I_C_Order stubOrder = newInstance(I_C_Order.class);
		stubOrder.setDocumentNo("test-doc");
		saveRecord(stubOrder);
		when(orderBL.getById(any(OrderId.class))).thenReturn(stubOrder);
		Services.registerService(IOrderBL.class, orderBL);

		repository = ProformaOrderAllocRepository.newInstanceForUnitTesting();
		final OrderPayScheduleLCStepService lcService = OrderPayScheduleLCStepService.newInstanceForUnitTesting();
		service = new ProformaOrderAllocService(
				repository,
				new PaymentTermService(),
				lcService);
	}

	@Test
	void deallocateAll_removesAllRows()
	{
		final InvoiceId invoiceId = createProformaInvoice();
		createAlloc(invoiceId, createOrder());
		createAlloc(invoiceId, createOrder());
		createAlloc(invoiceId, createOrder());

		assertThat(service.hasAllocations(invoiceId)).isTrue();
		assertThat(repository.getByInvoiceId(invoiceId)).hasSize(3);

		service.deallocateAll(invoiceId);

		assertThat(service.hasAllocations(invoiceId)).isFalse();
		assertThat(repository.getByInvoiceId(invoiceId)).isEmpty();
	}

	@Test
	void deallocateAll_isNoOp_whenNoRows()
	{
		final InvoiceId invoiceId = createProformaInvoice();

		assertThat(service.hasAllocations(invoiceId)).isFalse();

		// must not throw
		service.deallocateAll(invoiceId);

		assertThat(service.hasAllocations(invoiceId)).isFalse();
	}

	/**
	 * Set up two allocations for the same proforma invoice, then make the paymentDAO mock report that
	 * a completed payment exists for that proforma. {@link ProformaOrderAllocService#deallocate} throws
	 * {@code AdempiereException} on the first row it inspects, so {@code deallocateAll} fails fast and
	 * neither row is removed (the guard fires before the row is deleted).
	 */
	@Test
	void deallocateAll_propagatesGuardException_whenAnyAllocHasCompletedPayment()
	{
		final InvoiceId invoiceId = createProformaInvoice();
		createAlloc(invoiceId, createOrder());
		createAlloc(invoiceId, createOrder());

		// Mock: a completed payment exists for this proforma → deallocate must reject.
		// Real production code path also looks up the order's documentNo via IOrderBL — that runs against
		// the in-memory POJO store and does not need extra mocking.
		final I_C_Payment completedPayment = newInstance(I_C_Payment.class);
		saveRecord(completedPayment);
		when(paymentDAO.findCompletedOrClosedByProformaInvoiceId(invoiceId)).thenReturn(Optional.of(completedPayment));

		assertThatThrownBy(() -> service.deallocateAll(invoiceId))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("CannotDeallocateWhenPaid");

		// Both rows still present — the guard fires before deleteById, so failure on the first
		// iteration leaves all rows intact.
		assertThat(repository.getByInvoiceId(invoiceId)).hasSize(2);
	}

	// -----------------------------------------------------------------------
	// Fixture helpers
	// -----------------------------------------------------------------------

	private OrderId createOrder()
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		saveRecord(order);
		return OrderId.ofRepoId(order.getC_Order_ID());
	}

	private InvoiceId createProformaInvoice()
	{
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		saveRecord(invoice);
		return InvoiceId.ofRepoId(invoice.getC_Invoice_ID());
	}

	private void createAlloc(final InvoiceId invoiceId, final OrderId orderId)
	{
		final I_C_Proforma_Order_Alloc alloc = newInstance(I_C_Proforma_Order_Alloc.class);
		alloc.setC_Invoice_ID(invoiceId.getRepoId());
		alloc.setC_Order_ID(orderId.getRepoId());
		alloc.setIsActive(true);
		saveRecord(alloc);
	}
}
