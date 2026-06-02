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

package de.metas.invoice.process;

import de.metas.invoice.service.IInvoiceDAO;
import de.metas.invoice.service.impl.PlainInvoiceDAO;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.repository.IPaymentTermRepository;
import de.metas.payment.paymentterm.repository.impl.PaymentTermRepository;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_PaymentTerm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.compiere.model.I_C_Invoice.COLUMNNAME_C_Invoice_ID;

/**
 * Unit tests for {@link IInvoiceDAO#retrieveDocNosWithPaymentTermIn(IQueryFilter, java.util.Collection)}
 * and {@link IInvoiceDAO#setDueDateWherePaymentTermIn(IQueryFilter, java.util.Collection, java.sql.Timestamp)}.
 */
class C_Invoice_OverrideDueDateTest
{
	private IInvoiceDAO invoiceDAO;
	private IPaymentTermRepository paymentTermRepository;
	private IQueryBL queryBL;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		invoiceDAO = new PlainInvoiceDAO();
		Services.registerService(IInvoiceDAO.class, invoiceDAO);
		paymentTermRepository = new PaymentTermRepository();
		Services.registerService(IPaymentTermRepository.class, paymentTermRepository);
		queryBL = Services.get(IQueryBL.class);
	}

	private IQueryFilter<I_C_Invoice> filterByInvoiceId(final int invoiceId)
	{
		return queryBL.createCompositeQueryFilter(I_C_Invoice.class)
				.addEqualsFilter(COLUMNNAME_C_Invoice_ID, invoiceId);
	}

	/**
	 * When the selection contains an invoice whose payment term has {@code IsAllowOverrideDueDate='N'},
	 * that invoice's DocumentNo must appear in the result.
	 */
	@Test
	void filterContainsInvoiceWithFlagN_returnsItsDocumentNo()
	{
		// payment term with flag=N (disallows override)
		final I_C_PaymentTerm pt = InterfaceWrapperHelper.newInstance(I_C_PaymentTerm.class);
		pt.setIsAllowOverrideDueDate(false);
		InterfaceWrapperHelper.saveRecord(pt);

		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		invoice.setDocumentNo("INV-001");
		invoice.setC_PaymentTerm_ID(pt.getC_PaymentTerm_ID());
		InterfaceWrapperHelper.saveRecord(invoice);

		final IQueryFilter<I_C_Invoice> filter = filterByInvoiceId(invoice.getC_Invoice_ID());

		final Set<PaymentTermId> disallowing = paymentTermRepository.getPaymentTermIdsByIsAllowOverrideDueDate(false);
		final Collection<String> result = invoiceDAO.retrieveDocNosWithPaymentTermIn(filter, disallowing);

		assertThat(result).containsExactly("INV-001");
	}

	/**
	 * When all invoices in the selection have payment terms with {@code IsAllowOverrideDueDate='Y'},
	 * the result must be empty.
	 */
	@Test
	void allInvoicesWithFlagY_returnsEmpty()
	{
		// payment term with flag=Y (allows override)
		final I_C_PaymentTerm pt = InterfaceWrapperHelper.newInstance(I_C_PaymentTerm.class);
		pt.setIsAllowOverrideDueDate(true);
		InterfaceWrapperHelper.saveRecord(pt);

		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		invoice.setDocumentNo("INV-002");
		invoice.setC_PaymentTerm_ID(pt.getC_PaymentTerm_ID());
		InterfaceWrapperHelper.saveRecord(invoice);

		final IQueryFilter<I_C_Invoice> filter = filterByInvoiceId(invoice.getC_Invoice_ID());

		final Set<PaymentTermId> disallowing = paymentTermRepository.getPaymentTermIdsByIsAllowOverrideDueDate(false);
		final Collection<String> result = invoiceDAO.retrieveDocNosWithPaymentTermIn(filter, disallowing);

		assertThat(result).isEmpty();
	}

	/**
	 * An invoice with no payment term set is not part of the disallowing set: the precondition
	 * does not reject it. The override process's bulk update skips it anyway (it only touches
	 * invoices whose payment term explicitly allows the override).
	 */
	@Test
	void invoiceWithNoPaymentTerm_notIncluded()
	{
		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		invoice.setDocumentNo("INV-003");
		// C_PaymentTerm_ID intentionally left unset
		InterfaceWrapperHelper.saveRecord(invoice);

		final IQueryFilter<I_C_Invoice> filter = filterByInvoiceId(invoice.getC_Invoice_ID());

		final Set<PaymentTermId> disallowing = paymentTermRepository.getPaymentTermIdsByIsAllowOverrideDueDate(false);
		final Collection<String> result = invoiceDAO.retrieveDocNosWithPaymentTermIn(filter, disallowing);

		assertThat(result).isEmpty();
	}

	// -----------------------------------------------------------------------
	// Tests for IInvoiceDAO#setDueDateWherePaymentTermIn
	// -----------------------------------------------------------------------

	private static Timestamp ts(final String isoDate)
	{
		return Timestamp.from(Instant.parse(isoDate + "T00:00:00Z"));
	}

	/**
	 * An invoice whose payment term has {@code IsAllowOverrideDueDate='Y'} must have its
	 * DueDate set to the requested value.
	 */
	@Test
	void flagY_dueDateIsUpdated()
	{
		final I_C_PaymentTerm pt = InterfaceWrapperHelper.newInstance(I_C_PaymentTerm.class);
		pt.setIsAllowOverrideDueDate(true);
		InterfaceWrapperHelper.saveRecord(pt);

		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		invoice.setDocumentNo("INV-Y-001");
		invoice.setC_PaymentTerm_ID(pt.getC_PaymentTerm_ID());
		invoice.setDueDate(ts("2024-01-01"));
		InterfaceWrapperHelper.saveRecord(invoice);

		final Timestamp newDueDate = ts("2025-06-30");
		final IQueryFilter<I_C_Invoice> filter = filterByInvoiceId(invoice.getC_Invoice_ID());

		final Set<PaymentTermId> allowing = paymentTermRepository.getPaymentTermIdsByIsAllowOverrideDueDate(true);
		final int updated = invoiceDAO.setDueDateWherePaymentTermIn(filter, allowing, newDueDate);

		assertThat(updated).isEqualTo(1);
		InterfaceWrapperHelper.refresh(invoice);
		assertThat(invoice.getDueDate()).isEqualTo(newDueDate);
	}

	/**
	 * An invoice whose payment term has {@code IsAllowOverrideDueDate='N'} must NOT have its
	 * DueDate changed.
	 */
	@Test
	void flagN_dueDateIsNotUpdated()
	{
		final I_C_PaymentTerm pt = InterfaceWrapperHelper.newInstance(I_C_PaymentTerm.class);
		pt.setIsAllowOverrideDueDate(false);
		InterfaceWrapperHelper.saveRecord(pt);

		final Timestamp originalDueDate = ts("2024-01-01");
		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		invoice.setDocumentNo("INV-N-001");
		invoice.setC_PaymentTerm_ID(pt.getC_PaymentTerm_ID());
		invoice.setDueDate(originalDueDate);
		InterfaceWrapperHelper.saveRecord(invoice);

		final IQueryFilter<I_C_Invoice> filter = filterByInvoiceId(invoice.getC_Invoice_ID());

		final Set<PaymentTermId> allowing = paymentTermRepository.getPaymentTermIdsByIsAllowOverrideDueDate(true);
		final int updated = invoiceDAO.setDueDateWherePaymentTermIn(filter, allowing, ts("2025-06-30"));

		assertThat(updated).isEqualTo(0);
		InterfaceWrapperHelper.refresh(invoice);
		assertThat(invoice.getDueDate()).isEqualTo(originalDueDate);
	}

	/**
	 * An invoice with no payment term at all must NOT be updated (it fails the
	 * {@code addInSubQueryFilter} join, so it is skipped).
	 */
	@Test
	void noPaymentTerm_dueDateIsNotUpdated()
	{
		final Timestamp originalDueDate = ts("2024-01-01");
		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		invoice.setDocumentNo("INV-NOPT-001");
		// C_PaymentTerm_ID intentionally left unset
		invoice.setDueDate(originalDueDate);
		InterfaceWrapperHelper.saveRecord(invoice);

		final IQueryFilter<I_C_Invoice> filter = filterByInvoiceId(invoice.getC_Invoice_ID());

		final Set<PaymentTermId> allowing = paymentTermRepository.getPaymentTermIdsByIsAllowOverrideDueDate(true);
		final int updated = invoiceDAO.setDueDateWherePaymentTermIn(filter, allowing, ts("2025-06-30"));

		assertThat(updated).isEqualTo(0);
		InterfaceWrapperHelper.refresh(invoice);
		assertThat(invoice.getDueDate()).isEqualTo(originalDueDate);
	}
}
