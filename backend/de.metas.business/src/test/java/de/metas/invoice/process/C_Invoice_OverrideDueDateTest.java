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
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_PaymentTerm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.compiere.model.I_C_Invoice.COLUMNNAME_C_Invoice_ID;

/**
 * Unit tests for {@link IInvoiceDAO#retrieveDocNosWithPaymentTermDisallowingOverride(IQueryFilter)}.
 * <p>
 * Uses the POJO in-memory query engine via {@link PlainInvoiceDAO}.
 */
class C_Invoice_OverrideDueDateTest
{
	private IInvoiceDAO invoiceDAO;
	private IQueryBL queryBL;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		invoiceDAO = new PlainInvoiceDAO();
		Services.registerService(IInvoiceDAO.class, invoiceDAO);
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

		final Collection<String> result = invoiceDAO.retrieveDocNosWithPaymentTermDisallowingOverride(filter);

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

		final Collection<String> result = invoiceDAO.retrieveDocNosWithPaymentTermDisallowingOverride(filter);

		assertThat(result).isEmpty();
	}
}
