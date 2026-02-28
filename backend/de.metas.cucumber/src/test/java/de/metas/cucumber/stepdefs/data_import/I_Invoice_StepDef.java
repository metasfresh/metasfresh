package de.metas.cucumber.stepdefs.data_import;

import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.ProcessInfo;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_I_Invoice;
import org.compiere.process.ImportInvoice;
import org.compiere.util.DB;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

/**
 * Step definitions for I_Invoice staging table operations and ImportInvoice process execution.
 * Used to test BPartner Value disambiguation and other import-related scenarios.
 */
public class I_Invoice_StepDef
{
	private final I_Invoice_StepDefData iInvoiceTable;
	private final C_BPartner_StepDefData bPartnerTable;
	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

	public I_Invoice_StepDef(
			@NonNull final I_Invoice_StepDefData iInvoiceTable,
			@NonNull final C_BPartner_StepDefData bPartnerTable)
	{
		this.iInvoiceTable = iInvoiceTable;
		this.bPartnerTable = bPartnerTable;
	}

	/**
	 * Creates I_Invoice staging records for import testing.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias for cross-step reference<br>
	 *   <b>BPartnerValue</b> — (optional) BPartner search key to resolve<br>
	 *   <b>IsSOTrx</b> — (optional) Sales transaction flag (Y/N), defaults to Y<br>
	 * @cucumber.example
	 * <pre>
	 * Given metasfresh contains I_Invoice:
	 *   | Identifier | BPartnerValue | IsSOTrx |
	 *   | iInv_1     | BP_VALUE_1    | Y       |
	 * </pre>
	 */
	@Given("metasfresh contains I_Invoice:")
	public void metasfresh_contains_I_Invoice(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_I_Invoice record = InterfaceWrapperHelper.newInstance(I_I_Invoice.class);

			record.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());

			row.getAsOptionalString(I_I_Invoice.COLUMNNAME_BPartnerValue)
					.ifPresent(record::setBPartnerValue);

			final boolean isSOTrx = row.getAsOptionalBoolean(I_I_Invoice.COLUMNNAME_IsSOTrx).orElse(true);
			record.setIsSOTrx(isSOTrx);

			record.setI_IsImported(false);

			InterfaceWrapperHelper.saveRecord(record);

			// Ensure C_BPartner_ID is NULL (not 0) in the DB so the
			// "WHERE C_BPartner_ID IS NULL" condition matches the row.
			DB.executeUpdateAndSaveErrorOnFail(
					"UPDATE I_Invoice SET C_BPartner_ID = NULL WHERE I_Invoice_ID = " + record.getI_Invoice_ID(),
					ITrx.TRXNAME_None);

			row.getAsOptionalIdentifier().ifPresent(identifier -> iInvoiceTable.putOrReplace(identifier, record));
		});
	}

	/**
	 * Runs the ImportInvoice process with the current client context.
	 * This executes all the SQL updates that resolve
	 * foreign keys (BPartner, Product, DocType, etc.) from staging values.
	 *
	 * <p>Before invoking, marks stale unimported I_Invoice rows from previous
	 * test runs as 'Y' so they don't interfere with the current test.</p>
	 */
	@When("the ImportInvoice process is invoked")
	public void the_ImportInvoice_process_is_invoked()
	{
		// Mark stale rows from previous test runs as imported so they don't
		// interfere. Only keep rows we explicitly created in this scenario.
		final String currentIdList = iInvoiceTable.streamRecords()
				.map(r -> String.valueOf(r.getI_Invoice_ID()))
				.collect(java.util.stream.Collectors.joining(","));

		if (!currentIdList.isEmpty())
		{
			DB.executeUpdateAndSaveErrorOnFail(
					"UPDATE I_Invoice SET I_IsImported='Y' WHERE I_IsImported<>'Y' AND I_Invoice_ID NOT IN (" + currentIdList + ")",
					ITrx.TRXNAME_None);
		}

		final AdProcessId processId = adProcessDAO.retrieveProcessIdByClass(ImportInvoice.class);

		final int adClientId = StepDefConstants.CLIENT_ID.getRepoId();

		ProcessInfo.builder()
				.setAD_Process_ID(processId.getRepoId())
				.addParameter("AD_Client_ID", BigDecimal.valueOf(adClientId))
				.buildAndPrepareExecution()
				.executeSync()
				.getResult();
	}

	/**
	 * Validates I_Invoice staging records after import process execution.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias referencing a previously created I_Invoice record<br>
	 *   <b>C_BPartner_ID</b> — (optional, identifier-ref) expected resolved BPartner<br>
	 *   <b>I_ErrorMsg</b> — (optional) expected error message substring<br>
	 * @cucumber.depends StepDefData: I_Invoice_StepDefData, C_BPartner_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then validate I_Invoice:
	 *   | Identifier | C_BPartner_ID |
	 *   | iInv_1     | bpartner_cust |
	 * </pre>
	 */
	@Then("validate I_Invoice:")
	public void validate_I_Invoice(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final StepDefDataIdentifier rowIdentifier = row.getAsIdentifier();
			final I_I_Invoice record = rowIdentifier.lookupNotNullIn(iInvoiceTable);

			// Refresh from DB to get latest values after SQL execution
			InterfaceWrapperHelper.refresh(record);

			row.getAsOptionalIdentifier(I_I_Invoice.COLUMNNAME_C_BPartner_ID).ifPresent(bpIdentifier -> {
				final I_C_BPartner expectedBP = bpIdentifier.lookupNotNullIn(bPartnerTable);
				assertThat(record.getC_BPartner_ID())
						.as("I_Invoice[%s].C_BPartner_ID should match %s", rowIdentifier, bpIdentifier)
						.isEqualTo(expectedBP.getC_BPartner_ID());
			});

			row.getAsOptionalString(I_I_Invoice.COLUMNNAME_I_ErrorMsg).ifPresent(expectedSubstring -> {
				assertThat(record.getI_ErrorMsg())
						.as("I_Invoice[%s].I_ErrorMsg should contain '%s'", rowIdentifier, expectedSubstring)
						.contains(expectedSubstring);
			});
		});
	}
}
