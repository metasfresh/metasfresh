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

import com.google.common.collect.ImmutableList;
import de.metas.cucumber.stepdefs.C_Tax_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.tax.api.TaxId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceTax;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for validating {@link I_C_InvoiceTax} records.
 *
 * <p>Validates that the C_InvoiceTax records of a given invoice match the expected values.</p>
 *
 * <p>DataTable columns:</p>
 * <ul>
 *   <li>{@code C_Tax_ID} (required) — identifier referencing {@link C_Tax_StepDefData}</li>
 *   <li>{@code TaxAmt} (optional) — expected tax amount ({@link java.math.BigDecimal})</li>
 *   <li>{@code TaxBaseAmt} (optional) — expected tax base amount ({@link java.math.BigDecimal})</li>
 *   <li>{@code IsReverseCharge} (optional) — expected reverse charge flag (boolean)</li>
 *   <li>{@code ReverseChargeTaxAmt} (optional) — expected reverse charge tax amount ({@link java.math.BigDecimal})</li>
 *   <li>{@code IsTaxIncluded} (optional) — expected tax-included flag (boolean)</li>
 * </ul>
 *
 * <p>Gherkin usage example:</p>
 * <pre>{@code
 * And C_InvoiceTax records for invoice "invoice" are matching
 *   | C_Tax_ID | TaxAmt | TaxBaseAmt | IsReverseCharge | ReverseChargeTaxAmt | IsTaxIncluded |
 *   | tax_19   | 19.00  | 100.00     | false           | 0                   | false         |
 *   | tax_7    | 7.00   | 100.00     | false           | 0                   | false         |
 * }</pre>
 *
 * @see C_Invoice_StepDefData
 * @see C_Tax_StepDefData
 */
@RequiredArgsConstructor
public class C_InvoiceTax_StepDef
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final C_Invoice_StepDefData invoiceTable;
	@NonNull private final C_Tax_StepDefData taxTable;

	/**
	 * Validates that C_InvoiceTax records for the given invoice match the expected DataTable rows.
	 * Each row is matched by C_Tax_ID, and the optional columns are asserted if present.
	 *
	 * @see C_Invoice_StepDefData
	 * @see C_Tax_StepDefData
	 */
	@And("C_InvoiceTax records for invoice {string} are matching")
	public void c_InvoiceTax_records_for_invoice_are_matching(
			@NonNull final String invoiceIdentifier,
			@NonNull final DataTable dataTable)
	{
		final I_C_Invoice invoice = invoiceTable.get(invoiceIdentifier);

		final ImmutableList<I_C_InvoiceTax> invoiceTaxRecords = queryBL.createQueryBuilder(I_C_InvoiceTax.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_InvoiceTax.COLUMNNAME_C_Invoice_ID, invoice.getC_Invoice_ID())
				.orderBy(I_C_InvoiceTax.COLUMNNAME_C_Tax_ID)
				.create()
				.listImmutable(I_C_InvoiceTax.class);

		DataTableRows.of(dataTable)
				.forEach(row -> validateInvoiceTax(invoiceTaxRecords, row));
	}

	private void validateInvoiceTax(
			@NonNull final ImmutableList<I_C_InvoiceTax> invoiceTaxRecords,
			@NonNull final DataTableRow row)
	{
		final StepDefDataIdentifier taxIdentifier = row.getAsIdentifier(I_C_InvoiceTax.COLUMNNAME_C_Tax_ID);
		final TaxId taxId = taxTable.getId(taxIdentifier);

		final I_C_InvoiceTax invoiceTax = invoiceTaxRecords.stream()
				.filter(record -> record.getC_Tax_ID() == taxId.getRepoId())
				.findFirst()
				.orElse(null);

		assertThat(invoiceTax)
				.as("C_InvoiceTax record for C_Tax_ID identifier=%s (repoId=%s)", taxIdentifier, taxId.getRepoId())
				.isNotNull();

		final SoftAssertions softly = new SoftAssertions();

		row.getAsOptionalBigDecimal(I_C_InvoiceTax.COLUMNNAME_TaxAmt)
				.ifPresent(expected -> softly.assertThat(invoiceTax.getTaxAmt()).as("TaxAmt").isEqualByComparingTo(expected));

		row.getAsOptionalBigDecimal(I_C_InvoiceTax.COLUMNNAME_TaxBaseAmt)
				.ifPresent(expected -> softly.assertThat(invoiceTax.getTaxBaseAmt()).as("TaxBaseAmt").isEqualByComparingTo(expected));

		row.getAsOptionalBoolean(I_C_InvoiceTax.COLUMNNAME_IsReverseCharge)
				.ifPresent(expected -> softly.assertThat(invoiceTax.isReverseCharge()).as("IsReverseCharge").isEqualTo(expected));

		row.getAsOptionalBigDecimal(I_C_InvoiceTax.COLUMNNAME_ReverseChargeTaxAmt)
				.ifPresent(expected -> softly.assertThat(invoiceTax.getReverseChargeTaxAmt()).as("ReverseChargeTaxAmt").isEqualByComparingTo(expected));

		row.getAsOptionalBoolean(I_C_InvoiceTax.COLUMNNAME_IsTaxIncluded)
				.ifPresent(expected -> softly.assertThat(invoiceTax.isTaxIncluded()).as("IsTaxIncluded").isEqualTo(expected));

		softly.assertAll();
	}
}
