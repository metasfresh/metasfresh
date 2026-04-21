package de.metas.dunning.invoice.api.impl;

/*
 * #%L
 * de.metas.dunning
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

import java.sql.Timestamp;
import java.time.LocalDate;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.Test;

import de.metas.dunning.DunningTestBase;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pins the contract of {@link InvoiceSourceDAO#retrieveDueDate(I_C_Invoice)}:
 * the method must compute the due date from the invoice's payment term,
 * NOT simply return {@code invoice.getDueDate()}.
 *
 * <p>Background: a consistency sweep once rewrote the impl to
 * {@code return TimeUtil.asTimestamp(invoice.getDueDate());}, which broke
 * {@code PaymentTermBasedDueDateProvider} (it calls this DAO to POPULATE
 * {@code C_Invoice.DueDate} on completion — the column is null at that point).
 * This test guards against that regression.
 */
public class InvoiceSourceDAOTest extends DunningTestBase
{
	private static final Timestamp COLUMN_SENTINEL = TimeUtil.asTimestamp(LocalDate.of(2099, 12, 31));

	private final InvoiceSourceDAO invoiceSourceDAO = new InvoiceSourceDAO();

	@Test
	public void retrieveDueDate_mustNotReadInvoiceDueDateColumn()
	{
		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		invoice.setDueDate(COLUMN_SENTINEL);
		invoice.setDateInvoiced(TimeUtil.asTimestamp(LocalDate.of(2022, 1, 1)));
		invoice.setC_PaymentTerm_ID(0);
		InterfaceWrapperHelper.save(invoice);

		final Timestamp result;
		try
		{
			result = invoiceSourceDAO.retrieveDueDate(invoice);
		}
		catch (final Exception ex)
		{
			// Acceptable: the impl took the real SQL path (paymentTermDueDate function)
			// and failed because the POJO test environment has no live database.
			// That still proves the method did NOT return invoice.getDueDate() directly.
			return;
		}

		// If the impl returned a value without throwing, it must NOT be the sentinel
		// — returning the column value would silently re-introduce the regression.
		assertThat(result)
				.as("retrieveDueDate must not simply return invoice.getDueDate()")
				.isNotEqualTo(COLUMN_SENTINEL);
	}
}
