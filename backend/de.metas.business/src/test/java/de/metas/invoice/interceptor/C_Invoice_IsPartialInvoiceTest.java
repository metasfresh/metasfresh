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

package de.metas.invoice.interceptor;

import de.metas.allocation.api.IAllocationBL;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.document.engine.DocStatus;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.invoice.IsPartialInvoice;
import de.metas.invoice.due_date.InvoiceDueDateProviderService;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.order.IOrderBL;
import de.metas.organization.IOrgDAO;
import de.metas.payment.api.IPaymentBL;
import de.metas.payment.api.IPaymentDAO;
import de.metas.payment.reservation.PaymentReservationService;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_DocType;
import de.metas.adempiere.model.I_C_Invoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for {@link C_Invoice} interceptor — {@code IsPartialInvoice} defaulting (AC #11)
 * and readonly-after-Complete enforcement (AC #14).
 * <p>
 * Tests exercise the helpers {@link C_Invoice#defaultIsPartialInvoiceFromDocType}
 * (instance) and {@link C_Invoice#assertIsPartialInvoiceEditableForDocStatus} (static).
 * The interceptor's {@code @ModelChange} methods forward to those helpers; the helpers
 * carry all the logic.
 */
class C_Invoice_IsPartialInvoiceTest
{
	private C_Invoice interceptor;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		// C_Invoice declares ~10 service fields initialised via Services.get(...). For these tests
		// we only exercise the two helpers; register Mockito stand-ins so the service locator
		// returns lightweight mocks instead of trying to instantiate real BLs (which pull in
		// IPostingService and other modules not on the test classpath). IDocTypeDAO is left
		// to resolve to its real impl (DocTypeDAO) — it uses InterfaceWrapperHelper.loadOutOfTrx
		// which works against the POJO in-memory store populated by these tests.
		Services.registerService(IOrgDAO.class, Mockito.mock(IOrgDAO.class));
		Services.registerService(IPriceListDAO.class, Mockito.mock(IPriceListDAO.class));
		Services.registerService(IPaymentDAO.class, Mockito.mock(IPaymentDAO.class));
		Services.registerService(IPaymentBL.class, Mockito.mock(IPaymentBL.class));
		Services.registerService(IAllocationBL.class, Mockito.mock(IAllocationBL.class));
		Services.registerService(IInvoiceBL.class, Mockito.mock(IInvoiceBL.class));
		Services.registerService(IInvoiceDAO.class, Mockito.mock(IInvoiceDAO.class));
		Services.registerService(IBPartnerDAO.class, Mockito.mock(IBPartnerDAO.class));
		Services.registerService(IAllocationDAO.class, Mockito.mock(IAllocationDAO.class));
		Services.registerService(IOrderBL.class, Mockito.mock(IOrderBL.class));

		interceptor = new C_Invoice(
				Mockito.mock(PaymentReservationService.class),
				Mockito.mock(IDocumentLocationBL.class),
				Mockito.mock(InvoiceDueDateProviderService.class));
	}

	// -------------------------------------------------------------------------
	// AC #11 — IsPartialInvoice defaults from C_DocType.IsPartialInvoice
	// -------------------------------------------------------------------------

	/**
	 * When the doctype has {@code IsPartialInvoice='Y'} and the invoice has no value yet
	 * (represented as NA from the unset tri-state), the interceptor must copy
	 * the doctype flag to the invoice.
	 * <p>
	 * AC #11: default-direction safety — a new invoice without an explicit flag gets Partial.
	 */
	@Test
	void newInvoice_defaultsFromDocType_Y()
	{
		final I_C_DocType docType = InterfaceWrapperHelper.newInstance(I_C_DocType.class);
		InterfaceWrapperHelper.setValue(docType, org.compiere.model.I_C_DocType.COLUMNNAME_IsPartialInvoice, "Y");
		InterfaceWrapperHelper.saveRecord(docType);

		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		invoice.setC_DocType_ID(docType.getC_DocType_ID());
		// IsPartialInvoice intentionally NOT set on the invoice — simulates a brand-new record

		interceptor.defaultIsPartialInvoiceFromDocType(invoice);

		assertThat(IsPartialInvoice.fromCode(invoice.getIsPartialInvoice())).isEqualTo(IsPartialInvoice.Yes);
	}

	/**
	 * When the doctype has {@code IsPartialInvoice='N'}, the interceptor must propagate that
	 * to the new invoice.
	 */
	@Test
	void newInvoice_defaultsFromDocType_N()
	{
		final I_C_DocType docType = InterfaceWrapperHelper.newInstance(I_C_DocType.class);
		InterfaceWrapperHelper.setValue(docType, org.compiere.model.I_C_DocType.COLUMNNAME_IsPartialInvoice, "N");
		InterfaceWrapperHelper.saveRecord(docType);

		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		invoice.setC_DocType_ID(docType.getC_DocType_ID());

		interceptor.defaultIsPartialInvoiceFromDocType(invoice);

		assertThat(IsPartialInvoice.fromCode(invoice.getIsPartialInvoice())).isEqualTo(IsPartialInvoice.No);
	}

	/**
	 * When the caller already set {@code IsPartialInvoice} explicitly (simulated by calling the
	 * setter before invoking the helper), the helper must NOT overwrite it — even if the doctype
	 * says something different.
	 *
	 * <p>Real-life case: Cucumber step def sets {@code IsPartialInvoice=Y} and then calls
	 * {@code invoiceDAO.save()} which triggers BEFORE_NEW. Without this guard the BEFORE_NEW
	 * interceptor would overwrite the flag with the doctype's value ("Eingangsrechnung" = N).
	 */
	@Test
	void newInvoice_doesNotOverride_whenExplicitlySet()
	{
		final I_C_DocType docType = InterfaceWrapperHelper.newInstance(I_C_DocType.class);
		InterfaceWrapperHelper.setValue(docType, org.compiere.model.I_C_DocType.COLUMNNAME_IsPartialInvoice, "N"); // doctype says "Final"
		InterfaceWrapperHelper.saveRecord(docType);

		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		invoice.setC_DocType_ID(docType.getC_DocType_ID());
		invoice.setIsPartialInvoice(IsPartialInvoice.Yes.toCode());

		interceptor.defaultIsPartialInvoiceFromDocType(invoice);

		// must remain Y (caller wins, doctype is ignored)
		assertThat(IsPartialInvoice.fromCode(invoice.getIsPartialInvoice())).isEqualTo(IsPartialInvoice.Yes);
	}

	// -------------------------------------------------------------------------
	// AC #14 — IsPartialInvoice is editable while DR/IP, readonly otherwise
	// -------------------------------------------------------------------------

	/**
	 * A drafted invoice must allow {@code IsPartialInvoice} to be changed — no exception.
	 */
	@Test
	void editFlag_whileDraft_allowed()
	{
		assertThatCode(() -> C_Invoice.assertIsPartialInvoiceEditableForDocStatus(DocStatus.Drafted))
				.doesNotThrowAnyException();
	}

	/**
	 * An in-progress invoice must also allow the flag to be changed — no exception.
	 */
	@Test
	void editFlag_whileInProgress_allowed()
	{
		assertThatCode(() -> C_Invoice.assertIsPartialInvoiceEditableForDocStatus(DocStatus.InProgress))
				.doesNotThrowAnyException();
	}

	/**
	 * A completed invoice must reject any change to {@code IsPartialInvoice} with an
	 * {@link AdempiereException} whose message references the field name.
	 * <p>
	 * AC #14: readonly after Complete is enforced server-side.
	 */
	@Test
	void editFlag_afterComplete_rejected()
	{
		assertThatThrownBy(() -> C_Invoice.assertIsPartialInvoiceEditableForDocStatus(DocStatus.Completed))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("IsPartialInvoice");
	}
}
