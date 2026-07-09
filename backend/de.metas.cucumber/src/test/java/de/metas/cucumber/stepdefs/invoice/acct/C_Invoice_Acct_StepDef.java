package de.metas.cucumber.stepdefs.invoice.acct;

import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.accounting.C_ElementValue_StepDefData;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.invoice.C_InvoiceLine_StepDefData;
import de.metas.invoice.InvoiceId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import org.adempiere.service.ClientId;
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
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IAcctSchemaDAO acctSchemaDAO = Services.get(IAcctSchemaDAO.class);

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
	 *   <b>OPT.AssertAcctSchemaResolvedFromOrg</b> — (optional, boolean) when {@code Y}, assert the row's
	 *     {@code C_AcctSchema_ID} equals {@code getC_AcctSchema_ID(AD_Client_ID, AD_Org_ID)} for the row's own
	 *     client+org — i.e. the override was materialized into the accounting schema resolved from the invoice
	 *     line's org, not spread across every client schema.<br>
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

		// Query the C_Invoice_Acct table for the exact (invoice, line, accountName) tuple.
		// Active-only is intentional and load-bearing: surgical materialization deactivates the
		// contradicting rows it replaces, so we assert the live materialized override row — a
		// wrongly-deactivated materialized row must fail this assertion, not silently satisfy it.
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
			final I_C_Invoice_Acct acctRow = found.get(0);
			softly.assertThat(ElementValueId.ofRepoIdOrNull(acctRow.getC_ElementValue_ID()))
					.as("C_ElementValue_ID on C_Invoice_Acct row for accountName=" + accountName)
					.isEqualTo(expectedElementValueId);

			final boolean assertSchemaFromOrg = Boolean.TRUE.equals(
					row.getAsOptionalBoolean("AssertAcctSchemaResolvedFromOrg").toBooleanOrNull());
			if (assertSchemaFromOrg)
			{
				// The materialized row must live in the schema resolved from its own org
				// (getC_AcctSchema_ID(client, org)), NOT be spread across every client schema.
				final ClientId clientId = ClientId.ofRepoId(acctRow.getAD_Client_ID());
				final OrgId orgId = OrgId.ofRepoId(acctRow.getAD_Org_ID());
				final AcctSchemaId expectedAcctSchemaId = acctSchemaDAO.getAcctSchemaIdByClientAndOrgOrNull(clientId, orgId);
				softly.assertThat(expectedAcctSchemaId)
						.as("getC_AcctSchema_ID(client=%s, org=%s) must resolve a schema", clientId, orgId)
						.isNotNull();
				softly.assertThat(AcctSchemaId.ofRepoIdOrNull(acctRow.getC_AcctSchema_ID()))
						.as("C_Invoice_Acct.C_AcctSchema_ID must be the org-resolved schema for accountName=" + accountName)
						.isEqualTo(expectedAcctSchemaId);
			}
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
