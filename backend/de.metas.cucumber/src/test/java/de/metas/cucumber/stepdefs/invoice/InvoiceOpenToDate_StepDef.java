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

package de.metas.cucumber.stepdefs.invoice;

import de.metas.allocation.api.IAllocationDAO;
import de.metas.allocation.api.InvoiceOpenRequest;
import de.metas.allocation.api.InvoiceOpenResult;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.invoice.InvoiceId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_Invoice;

/**
 * Step definitions exercising the unified {@code invoiceOpenToDate} SQL function
 * (via {@link IAllocationDAO#retrieveInvoiceOpen}).
 *
 * <p>The function is the canonical "open amount" oracle for both financial invoices
 * (sales / purchase) and proforma invoices (the proforma early-return branch). This
 * step lets a feature file assert the function's return at every observable
 * transition (unpaid → partial → fully paid → reversed) without relying on the
 * indirect signal through {@code C_Invoice.IsPaid} / {@code OpenAmt} or the pay-
 * selection flow.
 */
@RequiredArgsConstructor
public class InvoiceOpenToDate_StepDef
{
	@NonNull private final IAllocationDAO allocationDAO = Services.get(IAllocationDAO.class);

	@NonNull private final C_Invoice_StepDefData invoiceTable;

	/**
	 * Asserts the {@code invoiceOpenToDate} function's return for the named invoice.
	 *
	 * <p>Required column: {@code C_Invoice_ID.Identifier}.
	 *
	 * <p>Optional columns (each is asserted only if the column appears in the table):
	 * <ul>
	 *   <li>{@code OpenAmt} — expected {@code InvoiceOpenResult.openAmt}.
	 *   <li>{@code PaidAmt} — expected {@code InvoiceOpenResult.allocatedAmt} (matches the
	 *       "PaidAmt" vocabulary in REQUIREMENTS.md AC #17 truth table).
	 *   <li>{@code GrandTotal} — expected {@code InvoiceOpenResult.invoiceGrandTotal}.
	 *   <li>{@code HasAllocations} — expected boolean ({@code Y} / {@code N} / {@code true} / {@code false}).
	 * </ul>
	 *
	 * <p>All amount assertions compare against {@code InvoiceTotal.toBigDecimal()} (the relative
	 * value, always positive regardless of AP/AR sign convention). Tests should write the absolute
	 * amount (e.g. {@code 20596.32}, never {@code -20596.32}).
	 *
	 * <p>The function is invoked with {@code DateColumn=DateTrx} (no date filter on the SQL side
	 * beyond the invoice's own DateInvoiced) and the invoice's own currency as the result currency.
	 *
	 * <pre>{@code
	 * Then for invoice the following invoiceOpenToDate result is expected:
	 *   | C_Invoice_ID.Identifier | OpenAmt  | PaidAmt | GrandTotal | HasAllocations |
	 *   | lcInvoice               | 20596.32 | 0       | 20596.32   | false          |
	 * }</pre>
	 */
	@Then("for invoice the following invoiceOpenToDate result is expected:")
	public void assertInvoiceOpenToDate(@NonNull final DataTable table)
	{
		DataTableRows.of(table).forEach(this::assertInvoiceOpenToDateRow);
	}

	private void assertInvoiceOpenToDateRow(@NonNull final DataTableRow row)
	{
		final I_C_Invoice invoice = row.getAsIdentifier(I_C_Invoice.COLUMNNAME_C_Invoice_ID).lookupNotNullIn(invoiceTable);
		final InvoiceId invoiceId = InvoiceId.ofRepoId(invoice.getC_Invoice_ID());

		final InvoiceOpenResult result = allocationDAO.retrieveInvoiceOpen(InvoiceOpenRequest.builder()
				.invoiceId(invoiceId)
				.dateColumn(InvoiceOpenRequest.DateColumn.DateTrx)
				.build());

		final SoftAssertions softly = new SoftAssertions();

		row.getAsOptionalBigDecimal("OpenAmt")
				.ifPresent(expected -> softly.assertThat(result.getOpenAmt().toBigDecimal())
						.as("OpenAmt for Identifier=%s", invoiceId)
						.isEqualByComparingTo(expected));

		row.getAsOptionalBigDecimal("PaidAmt")
				.ifPresent(expected -> softly.assertThat(result.getAllocatedAmt().toBigDecimal())
						.as("PaidAmt for Identifier=%s", invoiceId)
						.isEqualByComparingTo(expected));

		row.getAsOptionalBigDecimal("GrandTotal")
				.ifPresent(expected -> softly.assertThat(result.getInvoiceGrandTotal().toBigDecimal())
						.as("GrandTotal for Identifier=%s", invoiceId)
						.isEqualByComparingTo(expected));

		row.getAsOptionalBoolean("HasAllocations")
				.ifPresent(expected -> softly.assertThat(result.isHasAllocations())
						.as("HasAllocations for Identifier=%s", invoiceId)
						.isEqualTo(expected));

		softly.assertAll();
	}
}
