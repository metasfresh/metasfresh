package de.metas.cucumber.stepdefs.data_import;

import de.metas.contracts.flatrate.process.C_Flatrate_Term_Import;
import de.metas.contracts.model.I_I_Flatrate_Term;
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
import org.compiere.util.DB;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

/**
 * Step definitions for I_Flatrate_Term staging table operations and FlatrateTermImportProcess execution.
 * Used to test BPartner Value ambiguity detection during flatrate term import.
 */
public class I_Flatrate_Term_StepDef
{
	private final I_Flatrate_Term_StepDefData iFlatrateTermTable;
	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

	public I_Flatrate_Term_StepDef(@NonNull final I_Flatrate_Term_StepDefData iFlatrateTermTable)
	{
		this.iFlatrateTermTable = iFlatrateTermTable;
	}

	/**
	 * Creates I_Flatrate_Term staging records for import testing.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias for cross-step reference<br>
	 *   <b>BPartnerValue</b> — (optional) BPartner search key to resolve<br>
	 * @cucumber.example
	 * <pre>
	 * Given metasfresh contains I_Flatrate_Term:
	 *   | Identifier | BPartnerValue    |
	 *   | iFT_1      | SHARED_FT_BP_VAL |
	 * </pre>
	 */
	@Given("metasfresh contains I_Flatrate_Term:")
	public void metasfresh_contains_I_Flatrate_Term(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_I_Flatrate_Term record = InterfaceWrapperHelper.newInstance(I_I_Flatrate_Term.class);
			record.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());

			row.getAsOptionalString(I_I_Flatrate_Term.COLUMNNAME_BPartnerValue)
					.ifPresent(record::setBPartnerValue);

			record.setI_IsImported("N");
			InterfaceWrapperHelper.saveRecord(record);

			DB.executeUpdateAndSaveErrorOnFail(
					"UPDATE I_Flatrate_Term SET C_BPartner_ID = NULL WHERE I_Flatrate_Term_ID = " + record.getI_Flatrate_Term_ID(),
					ITrx.TRXNAME_None);

			row.getAsOptionalIdentifier().ifPresent(identifier -> iFlatrateTermTable.putOrReplace(identifier, record));
		});
	}

	/**
	 * Runs the FlatrateTermImportProcess with the current client context.
	 * This executes FlatrateTermImportTableSqlUpdater which resolves
	 * foreign keys and marks ambiguous BPartner Values as errors.
	 */
	@When("the FlatrateTermImportProcess is invoked")
	public void the_FlatrateTermImportProcess_is_invoked()
	{
		// Mark stale rows from previous test runs as imported so they don't
		// interfere. Only keep rows we explicitly created in this scenario.
		final String currentIdList = iFlatrateTermTable.streamRecords()
				.map(r -> String.valueOf(r.getI_Flatrate_Term_ID()))
				.collect(java.util.stream.Collectors.joining(","));

		if (!currentIdList.isEmpty())
		{
			DB.executeUpdateAndSaveErrorOnFail(
					"UPDATE I_Flatrate_Term SET I_IsImported='Y' WHERE I_IsImported<>'Y' AND I_Flatrate_Term_ID NOT IN (" + currentIdList + ")",
					ITrx.TRXNAME_None);
		}

		final AdProcessId processId = adProcessDAO.retrieveProcessIdByClass(C_Flatrate_Term_Import.class);

		final int adClientId = StepDefConstants.CLIENT_ID.getRepoId();

		ProcessInfo.builder()
				.setAD_Process_ID(processId.getRepoId())
				.addParameter("AD_Client_ID", BigDecimal.valueOf(adClientId))
				.buildAndPrepareExecution()
				.executeSync()
				.getResult();
	}

	/**
	 * Validates I_Flatrate_Term staging records after import process execution.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias referencing a previously created I_Flatrate_Term record<br>
	 *   <b>I_ErrorMsg</b> — (optional) expected error message substring; also asserts I_IsImported='E'<br>
	 *   <b>IsResolved</b> — (optional, boolean) if true, asserts C_BPartner_ID &gt; 0<br>
	 * @cucumber.depends StepDefData: I_Flatrate_Term_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then validate I_Flatrate_Term:
	 *   | Identifier | I_ErrorMsg               |
	 *   | iFT_1      | Multiple BPartners found |
	 * </pre>
	 */
	@Then("validate I_Flatrate_Term:")
	public void validate_I_Flatrate_Term(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final StepDefDataIdentifier rowIdentifier = row.getAsIdentifier();
			final I_I_Flatrate_Term record = rowIdentifier.lookupNotNullIn(iFlatrateTermTable);
			InterfaceWrapperHelper.refresh(record);

			row.getAsOptionalString(I_I_Flatrate_Term.COLUMNNAME_I_ErrorMsg).ifPresent(expectedErr -> {
				assertThat(record.getI_ErrorMsg())
						.as("I_Flatrate_Term[%s].I_ErrorMsg should contain '%s'", rowIdentifier, expectedErr)
						.contains(expectedErr);
				assertThat(record.getI_IsImported())
						.as("I_Flatrate_Term[%s].I_IsImported should be 'E'", rowIdentifier)
						.isEqualTo("E");
			});

			if (row.getAsOptionalBoolean("IsResolved").orElseFalse())
			{
				assertThat(record.getC_BPartner_ID())
						.as("I_Flatrate_Term[%s].C_BPartner_ID should be resolved", rowIdentifier)
						.isGreaterThan(0);
			}
		});
	}
}
