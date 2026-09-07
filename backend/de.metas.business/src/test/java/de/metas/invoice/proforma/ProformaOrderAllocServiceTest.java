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

import de.metas.bpartner.BPartnerId;
import de.metas.invoice.InvoiceId;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.order.paymentschedule.steps.letter_of_credit.OrderPayScheduleLCStepService;
import de.metas.payment.api.IPaymentDAO;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.PaymentTermService;
import de.metas.payment.paymentterm.ReferenceDateType;
import de.metas.pricing.tax.ProductTaxCategoryRepository;
import de.metas.pricing.tax.ProductTaxCategoryService;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_C_PaymentTerm;
import org.compiere.model.I_C_PaymentTerm_Break;
import org.compiere.model.I_C_Proforma_Order_Alloc;
import org.compiere.model.X_C_DocType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
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

	/**
	 * A purchase payment term with NO Letter-of-Credit break — only an {@code OD}
	 * (order-date/advance) break and a {@code BL} (bill-of-lading/material-receipt) break — must be
	 * a valid allocation target: allocate must not reject a no-LC payment term.
	 */
	@Test
	void allocate_noLcBreak_succeeds()
	{
		// Synthetic vendor ID: not validated, only used for matching with mocked getEffectiveBillPartnerId.
		final BPartnerId vendorId = BPartnerId.ofRepoId(1000000);
		final int currencyId = 318; // EUR

		final PaymentTermId paymentTermId = createNoLcPaymentTerm();

		final I_C_Order order = newInstance(I_C_Order.class);
		order.setIsSOTrx(false);
		// Kept for fixture realism; the vendor match is driven by the mock below.
		order.setC_BPartner_ID(vendorId.getRepoId());
		order.setC_Currency_ID(currencyId);
		order.setC_PaymentTerm_ID(paymentTermId.getRepoId());
		order.setDocumentNo("PO-no-lc");
		saveRecord(order);
		final OrderId orderId = OrderId.ofRepoId(order.getC_Order_ID());

		// Override the class-level IOrderBL mock (registered in beforeEach for the deallocate-guard path only)
		// with stubs matching THIS order/vendor, so validate() sees the real fixture instead of the generic stub.
		final IOrderBL orderBL = mock(IOrderBL.class);
		when(orderBL.getById(orderId)).thenReturn(order);
		when(orderBL.getEffectiveBillPartnerId(order)).thenReturn(vendorId);
		Services.registerService(IOrderBL.class, orderBL);

		final InvoiceId invoiceId = createProformaInvoice(vendorId, currencyId);

		// No-LC payment terms are a valid allocation target — allocate must not reject them.
		assertThatCode(() -> service.allocate(invoiceId, orderId))
				.as("allocate must succeed for a no-LC (OD+BL only) payment term")
				.doesNotThrowAnyException();

		assertThat(repository.getByOrderId(orderId)).hasSize(1);
	}

	/**
	 * A purchase payment term with NO Letter-of-Credit break and TWO non-material-receipt (advance)
	 * breaks — {@code OD} 10% + {@code Invoice} 90% — must be rejected: allocate throws
	 * {@code MSG_MultipleAdvanceBreaksUnsupported} and no allocation row is created.
	 */
	@Test
	void allocate_multipleAdvanceBreaksNoLc_throws()
	{
		final BPartnerId vendorId = BPartnerId.ofRepoId(1000001);
		final int currencyId = 318; // EUR

		final PaymentTermId paymentTermId = createMultipleAdvanceBreaksNoLcPaymentTerm();

		final I_C_Order order = newInstance(I_C_Order.class);
		order.setIsSOTrx(false);
		order.setC_BPartner_ID(vendorId.getRepoId());
		order.setC_Currency_ID(currencyId);
		order.setC_PaymentTerm_ID(paymentTermId.getRepoId());
		order.setDocumentNo("PO-multi-advance");
		saveRecord(order);
		final OrderId orderId = OrderId.ofRepoId(order.getC_Order_ID());

		final IOrderBL orderBL = mock(IOrderBL.class);
		when(orderBL.getById(orderId)).thenReturn(order);
		when(orderBL.getEffectiveBillPartnerId(order)).thenReturn(vendorId);
		Services.registerService(IOrderBL.class, orderBL);

		final InvoiceId invoiceId = createProformaInvoice(vendorId, currencyId);

		assertThatThrownBy(() -> service.allocate(invoiceId, orderId))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("MultipleAdvanceBreaksUnsupported");

		assertThat(repository.getByOrderId(orderId)).isEmpty();
	}

	/**
	 * A purchase payment term WITH a single Letter-of-Credit break ({@code BL} 50% + {@code LC} 50%)
	 * must remain a valid allocation target — confirms the gate rewrite (no-LC handling added) left the
	 * pre-existing LC allocation leg intact.
	 */
	@Test
	void allocate_singleLcBreak_succeeds()
	{
		final BPartnerId vendorId = BPartnerId.ofRepoId(1000002);
		final int currencyId = 318; // EUR

		final PaymentTermId paymentTermId = createSingleLcPaymentTerm();

		final I_C_Order order = newInstance(I_C_Order.class);
		order.setIsSOTrx(false);
		order.setC_BPartner_ID(vendorId.getRepoId());
		order.setC_Currency_ID(currencyId);
		order.setC_PaymentTerm_ID(paymentTermId.getRepoId());
		order.setDocumentNo("PO-single-lc");
		saveRecord(order);
		final OrderId orderId = OrderId.ofRepoId(order.getC_Order_ID());

		final IOrderBL orderBL = mock(IOrderBL.class);
		when(orderBL.getById(orderId)).thenReturn(order);
		when(orderBL.getEffectiveBillPartnerId(order)).thenReturn(vendorId);
		Services.registerService(IOrderBL.class, orderBL);

		final InvoiceId invoiceId = createProformaInvoice(vendorId, currencyId);

		assertThatCode(() -> service.allocate(invoiceId, orderId))
				.as("allocate must succeed for a payment term with a single LC break")
				.doesNotThrowAnyException();

		assertThat(repository.getByOrderId(orderId)).hasSize(1);
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

	/**
	 * Purchase-proforma-invoice (APF) variant of {@link #createProformaInvoice()} — sets the
	 * {@code C_DocType} (DocBaseType=APF) plus vendor/currency, needed to pass
	 * {@code ProformaOrderAllocateCommand.validate}'s {@code isPurchaseProforma}/currency/vendor checks.
	 */
	private InvoiceId createProformaInvoice(final BPartnerId vendorId, final int currencyId)
	{
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType(X_C_DocType.DOCBASETYPE_APProFormaInvoice);
		saveRecord(docType);

		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setC_DocType_ID(docType.getC_DocType_ID());
		invoice.setC_BPartner_ID(vendorId.getRepoId());
		invoice.setC_Currency_ID(currencyId);
		saveRecord(invoice);
		return InvoiceId.ofRepoId(invoice.getC_Invoice_ID());
	}

	/**
	 * OrderDate/no-LC variant of a payment term: two breaks, {@code OD} 10% (advance) + {@code BL} 90%
	 * (material receipt) — no Letter-of-Credit break at all.
	 */
	private PaymentTermId createNoLcPaymentTerm()
	{
		final I_C_PaymentTerm paymentTermRecord = newInstance(I_C_PaymentTerm.class);
		paymentTermRecord.setValue("no-lc-test");
		paymentTermRecord.setName("No-LC test payment term");
		paymentTermRecord.setDiscount(BigDecimal.ZERO);
		paymentTermRecord.setDiscount2(BigDecimal.ZERO);
		saveRecord(paymentTermRecord);
		final PaymentTermId paymentTermId = PaymentTermId.ofRepoId(paymentTermRecord.getC_PaymentTerm_ID());

		createPaymentTermBreak(paymentTermId, ReferenceDateType.OrderDate, 10, 10);
		createPaymentTermBreak(paymentTermId, ReferenceDateType.BillOfLadingDate, 90, 20);

		return paymentTermId;
	}

	/**
	 * No-LC variant with TWO non-material-receipt breaks: {@code OD} 10% (advance) + {@code Invoice} 90%
	 * (also advance, since only {@code BL}/{@code ETA} count as material-receipt) — no LC, no BL/ETA.
	 */
	private PaymentTermId createMultipleAdvanceBreaksNoLcPaymentTerm()
	{
		final I_C_PaymentTerm paymentTermRecord = newInstance(I_C_PaymentTerm.class);
		paymentTermRecord.setValue("multi-advance-test");
		paymentTermRecord.setName("Multiple-advance-breaks test payment term");
		paymentTermRecord.setDiscount(BigDecimal.ZERO);
		paymentTermRecord.setDiscount2(BigDecimal.ZERO);
		saveRecord(paymentTermRecord);
		final PaymentTermId paymentTermId = PaymentTermId.ofRepoId(paymentTermRecord.getC_PaymentTerm_ID());

		createPaymentTermBreak(paymentTermId, ReferenceDateType.OrderDate, 10, 10);
		createPaymentTermBreak(paymentTermId, ReferenceDateType.InvoiceDate, 90, 20);

		return paymentTermId;
	}

	/**
	 * Single-LC variant: {@code BL} 50% (material receipt) + {@code LC} 50% (Letter-of-Credit) — exactly
	 * one LC break, a valid allocation target.
	 */
	private PaymentTermId createSingleLcPaymentTerm()
	{
		final I_C_PaymentTerm paymentTermRecord = newInstance(I_C_PaymentTerm.class);
		paymentTermRecord.setValue("single-lc-test");
		paymentTermRecord.setName("Single-LC test payment term");
		paymentTermRecord.setDiscount(BigDecimal.ZERO);
		paymentTermRecord.setDiscount2(BigDecimal.ZERO);
		saveRecord(paymentTermRecord);
		final PaymentTermId paymentTermId = PaymentTermId.ofRepoId(paymentTermRecord.getC_PaymentTerm_ID());

		createPaymentTermBreak(paymentTermId, ReferenceDateType.BillOfLadingDate, 50, 10);
		createPaymentTermBreak(paymentTermId, ReferenceDateType.LetterOfCreditDate, 50, 20);

		return paymentTermId;
	}

	private void createPaymentTermBreak(
			final PaymentTermId paymentTermId,
			final ReferenceDateType referenceDateType,
			final int percent,
			final int seqNo)
	{
		final I_C_PaymentTerm_Break breakRecord = newInstance(I_C_PaymentTerm_Break.class);
		breakRecord.setC_PaymentTerm_ID(paymentTermId.getRepoId());
		breakRecord.setReferenceDateType(referenceDateType.getCode());
		breakRecord.setPercent(percent);
		breakRecord.setSeqNo(seqNo);
		breakRecord.setOffsetDays(0);
		saveRecord(breakRecord);
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
