package de.metas.cucumber.stepdefs.invoice.acct;

import de.metas.acct.api.impl.ElementValueId;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.invoice.C_InvoiceLine_StepDefData;
import de.metas.invoice.InvoiceId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_Invoice_Acct;
import org.compiere.model.I_C_InvoiceLine;

import java.util.List;

/**
 * Step definitions for asserting materialized {@code C_Invoice_Acct} rows.
 * These rows are created by the {@code C_Invoice_AcctOverride} interceptor
 * on purchase invoice completion when a per-line GL account override is set.
 */
@RequiredArgsConstructor
public class C_Invoice_Acct_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final C_Invoice_StepDefData invoiceTable;
	private final C_InvoiceLine_StepDefData invoiceLineTable;
	private final C_ElementValue_StepDefData elementValueTable;

	/**
	 * Asserts that the expected {@code C_Invoice_Acct} rows exist after invoice completion.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_Invoice_ID</b> — (required, identifier-ref) the completed invoice<br>
	 *   <b>C_InvoiceLine_ID</b> — (optional, identifier-ref) the specific invoice line (null = header override)<br>
	 *   <b>AccountName</b> — (required) the AccountConceptualName stored on the row (e.g. {@code P_Expense_Acct})<br>
	 *   <b>C_ElementValue_ID</b> — (required, identifier-ref) the expected GL account (override account)<br>
	 * @cucumber.example
	 * <pre>
	 * Then C_Invoice_Acct rows are found for invoice:
	 *   | C_Invoice_ID | C_InvoiceLine_ID | AccountName    | C_ElementValue_ID   |
	 *   | invoice      | invoiceLine      | P_Expense_Acct | overrideAccount     |
	 * </pre>
	 */
	@And("C_Invoice_Acct rows are found for invoice:")
	public void assertC_Invoice_Acct_rows(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::assertRow);
	}

	private void assertRow(@NonNull final DataTableRow row)
	{
		final InvoiceId invoiceId = row.getAsIdentifier(I_C_Invoice_Acct.COLUMNNAME_C_Invoice_ID).lookupNotNullIdIn(invoiceTable);

		final StepDefDataIdentifier invoiceLineIdentifier = row.getAsOptionalIdentifier(I_C_Invoice_Acct.COLUMNNAME_C_InvoiceLine_ID).orElse(null);
		final Integer invoiceLineRepoId = resolveInvoiceLineRepoId(invoiceLineIdentifier);

		final String accountName = row.getAsString(I_C_Invoice_Acct.COLUMNNAME_AccountName);

		final ElementValueId expectedElementValueId = row.getAsIdentifier(I_C_Invoice_Acct.COLUMNNAME_C_ElementValue_ID)
				.lookupNotNullIdIn(elementValueTable);

		// Query the C_Invoice_Acct table for the exact (invoice, line, accountName) tuple
		final List<I_C_Invoice_Acct> found = queryBL.createQueryBuilder(I_C_Invoice_Acct.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Acct.COLUMNNAME_C_Invoice_ID, invoiceId)
				.addEqualsFilter(I_C_Invoice_Acct.COLUMNNAME_C_InvoiceLine_ID, invoiceLineRepoId)
				.addEqualsFilter(I_C_Invoice_Acct.COLUMNNAME_AccountName, accountName)
				.create()
				.list();

		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(found)
				.as("Expected exactly one C_Invoice_Acct row for invoice=%s, line=%s, accountName=%s",
						invoiceId, invoiceLineRepoId, accountName)
				.hasSize(1);

		if (found.size() == 1)
		{
			softly.assertThat(ElementValueId.ofRepoIdOrNull(found.get(0).getC_ElementValue_ID()))
					.as("C_ElementValue_ID on C_Invoice_Acct row for accountName=" + accountName)
					.isEqualTo(expectedElementValueId);
		}

		softly.assertAll();
	}

	private Integer resolveInvoiceLineRepoId(final StepDefDataIdentifier identifier)
	{
		if (identifier == null || identifier.isNullPlaceholder())
		{
			return null;
		}
		final I_C_InvoiceLine line = invoiceLineTable.getOptional(identifier).orElse(null);
		return line != null ? line.getC_InvoiceLine_ID() : null;
	}
}
