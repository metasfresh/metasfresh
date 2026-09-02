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

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.allocation.api.IAllocationBL;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.due_date.InvoiceDueDateProviderService;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.order.IOrderBL;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.payment.api.IPaymentBL;
import de.metas.payment.api.IPaymentDAO;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.repository.IPaymentTermRepository;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.payment.reservation.PaymentReservationService;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the {@code setDueDate} logic in {@link C_Invoice}.
 * <p>
 * Tests exercise the package-private helper {@link C_Invoice#setDueDateOnInvoice}
 * which carries all the decision logic.
 */
class C_Invoice_SetDueDateTest
{
	private static final OrgId ORG_ID = OrgId.ofRepoId(1);
	private static final ZoneId ORG_ZONE = ZoneId.of("Europe/Berlin");
	private static final PaymentTermId PAYMENT_TERM_ID = PaymentTermId.ofRepoId(10);

	private InvoiceDueDateProviderService invoiceDueDateProviderService;
	private IPaymentTermRepository paymentTermRepository;
	private IOrgDAO orgDAO;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

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
		Services.registerService(IPaymentTermRepository.class, Mockito.mock(IPaymentTermRepository.class));

		invoiceDueDateProviderService = mock(InvoiceDueDateProviderService.class);
		paymentTermRepository = mock(IPaymentTermRepository.class);
		orgDAO = mock(IOrgDAO.class);

		when(orgDAO.getTimeZone(ORG_ID)).thenReturn(ORG_ZONE);
	}

	// -------------------------------------------------------------------------
	// Case (a): DueDate is null → compute via provider chain, write with org zone
	// -------------------------------------------------------------------------

	/**
	 * When no DueDate is set on the invoice, the provider chain computes it and the result
	 * is stored using the org's timezone, not the JVM default.
	 */
	@Test
	void dueDateNull_setsComputedDueDate_inOrgTimezone()
	{
		final LocalDate computed = LocalDate.of(2026, 3, 15);
		final InvoiceId invoiceId = InvoiceId.ofRepoId(100);

		final I_C_Invoice invoice = newInvoice(invoiceId, PAYMENT_TERM_ID, null);
		when(invoiceDueDateProviderService.provideDueDateFor(invoiceId)).thenReturn(computed);

		C_Invoice.setDueDateOnInvoice(invoice, invoiceDueDateProviderService, paymentTermRepository, orgDAO);

		final Timestamp expected = TimeUtil.asTimestamp(computed, ORG_ZONE);
		assertThat(invoice.getDueDate()).isEqualTo(expected);

		// the override-flag must not be consulted when DueDate is null
		verify(paymentTermRepository, never()).isAllowOverrideDueDate(Mockito.any());
	}

	// -------------------------------------------------------------------------
	// Case (b): DueDate set, flag=N, value differs → force computed value
	// -------------------------------------------------------------------------

	/**
	 * When DueDate is already set but the payment term does not allow overriding it
	 * (IsAllowOverrideDueDate='N'), the interceptor must silently replace the value with
	 * the one computed from the payment term.
	 * <p>
	 * UI readonly logic is client-side only; REST calls, EDI imports, and stale drafts can
	 * bypass it and write a different value. Server-side enforcement ensures the stored date
	 * is always consistent with the payment term.
	 */
	@Test
	void dueDateSet_flagN_valuesDiffer_forcesComputedValue()
	{
		final LocalDate computed = LocalDate.of(2026, 3, 15);
		final LocalDate overridden = LocalDate.of(2026, 4, 30); // user-supplied, different from computed
		final InvoiceId invoiceId = InvoiceId.ofRepoId(101);

		final Timestamp overriddenTs = TimeUtil.asTimestamp(overridden, ORG_ZONE);
		final I_C_Invoice invoice = newInvoice(invoiceId, PAYMENT_TERM_ID, overriddenTs);

		when(invoiceDueDateProviderService.provideDueDateFor(invoiceId)).thenReturn(computed);
		when(paymentTermRepository.isAllowOverrideDueDate(PAYMENT_TERM_ID)).thenReturn(false);

		C_Invoice.setDueDateOnInvoice(invoice, invoiceDueDateProviderService, paymentTermRepository, orgDAO);

		final Timestamp expectedTs = TimeUtil.asTimestamp(computed, ORG_ZONE);
		assertThat(invoice.getDueDate())
				.as("DueDate must be forced to the computed value when flag=N")
				.isEqualTo(expectedTs);
	}

	/**
	 * Same as the previous test but the stored DueDate already matches the computed date.
	 * The normalisation is a no-op — the value stays identical and no override occurs.
	 */
	@Test
	void dueDateSet_flagN_valuesSame_noChange()
	{
		final LocalDate computed = LocalDate.of(2026, 3, 15);
		final InvoiceId invoiceId = InvoiceId.ofRepoId(102);

		final Timestamp computedTs = TimeUtil.asTimestamp(computed, ORG_ZONE);
		final I_C_Invoice invoice = newInvoice(invoiceId, PAYMENT_TERM_ID, computedTs);

		when(invoiceDueDateProviderService.provideDueDateFor(invoiceId)).thenReturn(computed);
		when(paymentTermRepository.isAllowOverrideDueDate(PAYMENT_TERM_ID)).thenReturn(false);

		C_Invoice.setDueDateOnInvoice(invoice, invoiceDueDateProviderService, paymentTermRepository, orgDAO);

		assertThat(invoice.getDueDate())
				.as("DueDate must remain the same when flag=N and values match")
				.isEqualTo(computedTs);
	}

	// -------------------------------------------------------------------------
	// Case (c): DueDate set, flag=Y → leave untouched
	// -------------------------------------------------------------------------

	/**
	 * When IsAllowOverrideDueDate='Y', the user-supplied DueDate is intentional and must
	 * not be replaced.
	 */
	@Test
	void dueDateSet_flagY_leavesUntouched()
	{
		final LocalDate overridden = LocalDate.of(2026, 4, 30);
		final InvoiceId invoiceId = InvoiceId.ofRepoId(103);

		final Timestamp overriddenTs = TimeUtil.asTimestamp(overridden, ORG_ZONE);
		final I_C_Invoice invoice = newInvoice(invoiceId, PAYMENT_TERM_ID, overriddenTs);

		when(paymentTermRepository.isAllowOverrideDueDate(PAYMENT_TERM_ID)).thenReturn(true);

		C_Invoice.setDueDateOnInvoice(invoice, invoiceDueDateProviderService, paymentTermRepository, orgDAO);

		assertThat(invoice.getDueDate())
				.as("DueDate must be left untouched when flag=Y")
				.isEqualTo(overriddenTs);

		// provider chain must not be consulted when the override is allowed
		verify(invoiceDueDateProviderService, never()).provideDueDateFor(Mockito.any());
	}

	// -------------------------------------------------------------------------
	// Defensive: no payment term → skip flag enforcement
	// -------------------------------------------------------------------------

	/**
	 * When the invoice has no payment term set, the override-flag enforcement is skipped.
	 * The provider chain owns all fallbacks for the case where DueDate is null.
	 */
	@Test
	void dueDateSet_noPaymentTerm_skipsEnforcement()
	{
		final LocalDate overridden = LocalDate.of(2026, 4, 30);
		final InvoiceId invoiceId = InvoiceId.ofRepoId(104);

		final Timestamp overriddenTs = TimeUtil.asTimestamp(overridden, ORG_ZONE);
		// no payment term
		final I_C_Invoice invoice = newInvoice(invoiceId, /*paymentTermId=*/null, overriddenTs);

		C_Invoice.setDueDateOnInvoice(invoice, invoiceDueDateProviderService, paymentTermRepository, orgDAO);

		// value must be left unchanged
		assertThat(invoice.getDueDate()).isEqualTo(overriddenTs);
		verify(paymentTermRepository, never()).isAllowOverrideDueDate(Mockito.any());
		verify(invoiceDueDateProviderService, never()).provideDueDateFor(Mockito.any());
	}

	// -------------------------------------------------------------------------
	// Helpers
	// -------------------------------------------------------------------------

	private static I_C_Invoice newInvoice(
			final InvoiceId invoiceId,
			final PaymentTermId paymentTermId,
			final Timestamp dueDate)
	{
		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		InterfaceWrapperHelper.setValue(invoice, org.compiere.model.I_C_Invoice.COLUMNNAME_C_Invoice_ID, invoiceId.getRepoId());
		invoice.setAD_Org_ID(ORG_ID.getRepoId());
		if (paymentTermId != null)
		{
			invoice.setC_PaymentTerm_ID(paymentTermId.getRepoId());
		}
		invoice.setDueDate(dueDate);
		return invoice;
	}
}
