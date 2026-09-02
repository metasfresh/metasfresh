package de.metas.cucumber.stepdefs.data_import;

import de.metas.bpartner.impexp.BPartnerImportTableSqlUpdater;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.impexp.processing.ImportRecordsSelection;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_I_BPartner;
import org.compiere.util.DB;

import static org.assertj.core.api.Assertions.*;

/**
 * Step definitions for I_BPartner staging table operations and BPartnerImportProcess execution.
 * Used to test BPartner Value disambiguation during BPartner import.
 */
public class I_BPartner_Import_StepDef
{
	private final I_BPartner_Import_StepDefData iBPartnerTable;
	private final C_BPartner_StepDefData bPartnerTable;

	public I_BPartner_Import_StepDef(
			@NonNull final I_BPartner_Import_StepDefData iBPartnerTable,
			@NonNull final C_BPartner_StepDefData bPartnerTable)
	{
		this.iBPartnerTable = iBPartnerTable;
		this.bPartnerTable = bPartnerTable;
	}

	/**
	 * Creates I_BPartner staging records for import testing.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias for cross-step reference<br>
	 *   <b>BPValue</b> — (optional) BPartner search key to resolve<br>
	 *   <b>IsCustomer</b> — (optional) Customer flag (Y/N), defaults to N<br>
	 *   <b>IsVendor</b> — (optional) Vendor flag (Y/N), defaults to N<br>
	 * @cucumber.example
	 * <pre>
	 * Given metasfresh contains I_BPartner:
	 *   | Identifier | BPValue     | IsCustomer | IsVendor |
	 *   | iBP_1      | BP_VALUE_1  | Y          | N        |
	 * </pre>
	 */
	@Given("metasfresh contains I_BPartner:")
	public void metasfresh_contains_I_BPartner(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_I_BPartner record = InterfaceWrapperHelper.newInstance(I_I_BPartner.class);
			record.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());

			row.getAsOptionalString(I_I_BPartner.COLUMNNAME_BPValue)
					.ifPresent(record::setBPValue);

			record.setIsCustomer(row.getAsOptionalBoolean(I_I_BPartner.COLUMNNAME_IsCustomer).orElseFalse());
			record.setIsVendor(row.getAsOptionalBoolean(I_I_BPartner.COLUMNNAME_IsVendor).orElseFalse());

			record.setI_IsImported(false);
			InterfaceWrapperHelper.saveRecord(record);

			DB.executeUpdateAndSaveErrorOnFail(
					"UPDATE I_BPartner SET C_BPartner_ID = NULL WHERE I_BPartner_ID = " + record.getI_BPartner_ID(),
					ITrx.TRXNAME_None);

			row.getAsOptionalIdentifier().ifPresent(identifier -> iBPartnerTable.putOrReplace(identifier, record));
		});
	}

	/**
	 * Runs BPartnerImportTableSqlUpdater directly (synchronous) to resolve
	 * foreign keys (including C_BPartner_ID from BPValue) on staging rows.
	 *
	 * We call the SQL updater directly instead of the full ImportBPartner process
	 * because the process runs asynchronously via workpackage scheduling,
	 * making it impossible to validate results immediately.
	 * The SQL updater is the component that performs the BPValue→C_BPartner_ID
	 * disambiguation we need to test.
	 */
	@When("the BPartnerImportProcess is invoked")
	public void the_BPartnerImportProcess_is_invoked()
	{
		// Mark stale I_BPartner rows from previous test runs as imported
		// so the SQL updater only processes our test rows
		final String currentIdList = iBPartnerTable.streamRecords()
				.map(r -> String.valueOf(r.getI_BPartner_ID()))
				.collect(java.util.stream.Collectors.joining(","));

		if (!currentIdList.isEmpty())
		{
			DB.executeUpdateAndSaveErrorOnFail(
					"UPDATE I_BPartner SET I_IsImported='Y' WHERE I_IsImported<>'Y' AND I_BPartner_ID NOT IN (" + currentIdList + ")",
					ITrx.TRXNAME_None);
		}

		// Deactivate old C_BPartners with the same Values created by previous
		// test runs so the HAVING count(*)=1 disambiguation can work.
		final String currentBPIdList = bPartnerTable.streamRecords()
				.map(r -> String.valueOf(r.getC_BPartner_ID()))
				.collect(java.util.stream.Collectors.joining(","));

		if (!currentBPIdList.isEmpty())
		{
			final String bpValues = iBPartnerTable.streamRecords()
					.map(I_I_BPartner::getBPValue)
					.filter(java.util.Objects::nonNull)
					.distinct()
					.map(v -> "'" + v.replace("'", "''") + "'")
					.collect(java.util.stream.Collectors.joining(","));

			if (!bpValues.isEmpty())
			{
				DB.executeUpdateAndSaveErrorOnFail(
						"UPDATE C_BPartner SET IsActive='N' WHERE Value IN (" + bpValues + ") AND C_BPartner_ID NOT IN (" + currentBPIdList + ")",
						ITrx.TRXNAME_None);
			}
		}

		// Call the SQL updater directly — synchronous, no async workpackage
		final ImportRecordsSelection selection = ImportRecordsSelection.builder()
				.importTableName(I_I_BPartner.Table_Name)
				.importKeyColumnName(I_I_BPartner.COLUMNNAME_I_BPartner_ID)
				.clientId(ClientId.ofRepoId(StepDefConstants.CLIENT_ID.getRepoId()))
				.build();

		BPartnerImportTableSqlUpdater.updateBPartnerImportTable(selection);
	}

	/**
	 * Validates I_BPartner staging records after import process execution.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias referencing a previously created I_BPartner record<br>
	 *   <b>C_BPartner_ID</b> — (optional, identifier-ref) expected resolved BPartner<br>
	 * @cucumber.depends StepDefData: I_BPartner_Import_StepDefData, C_BPartner_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then validate I_BPartner:
	 *   | Identifier | C_BPartner_ID |
	 *   | iBP_1      | bpartner_cust |
	 * </pre>
	 */
	@Then("validate I_BPartner:")
	public void validate_I_BPartner(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final StepDefDataIdentifier rowIdentifier = row.getAsIdentifier();
			final I_I_BPartner storedRecord = rowIdentifier.lookupNotNullIn(iBPartnerTable);
			final int iBPartnerId = storedRecord.getI_BPartner_ID();

			row.getAsOptionalIdentifier(I_I_BPartner.COLUMNNAME_C_BPartner_ID).ifPresent(bpIdentifier -> {
				final I_C_BPartner expectedBP = bpIdentifier.lookupNotNullIn(bPartnerTable);

				// Use direct SQL to bypass PO caching — the import process commits
				// in its own transaction and InterfaceWrapperHelper may return stale data.
				final int actualBPartnerId = DB.getSQLValueEx(
						ITrx.TRXNAME_None,
						"SELECT COALESCE(C_BPartner_ID, 0) FROM I_BPartner WHERE I_BPartner_ID = ?",
						iBPartnerId);

				assertThat(actualBPartnerId)
						.as("I_BPartner[%s].C_BPartner_ID should match %s (I_BPartner_ID=%d)", rowIdentifier, bpIdentifier, iBPartnerId)
						.isEqualTo(expectedBP.getC_BPartner_ID());
			});
		});
	}
}
