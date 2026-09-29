package de.metas.cucumber.stepdefs.data_import;

import de.metas.contracts.flatrate.process.C_Flatrate_Term_Import;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_I_Flatrate_Term;
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
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

/**
 * Step definitions for I_Flatrate_Term staging table operations and FlatrateTermImportProcess execution.
 * Used to test BPartner Value ambiguity detection during flatrate term import.
 */
public class I_Flatrate_Term_StepDef
{
	private final I_Flatrate_Term_StepDefData iFlatrateTermTable;
	private final C_BPartner_StepDefData bPartnerTable;
	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

	public I_Flatrate_Term_StepDef(
			@NonNull final I_Flatrate_Term_StepDefData iFlatrateTermTable,
			@NonNull final C_BPartner_StepDefData bPartnerTable)
	{
		this.iFlatrateTermTable = iFlatrateTermTable;
		this.bPartnerTable = bPartnerTable;
	}

	/**
	 * Creates I_Flatrate_Term staging records for import testing.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias for cross-step reference<br>
	 *   <b>BPartnerValue</b> — (optional) BPartner search key to resolve, as a raw literal (e.g. to test ambiguous
	 *     values shared by several partners)<br>
	 *   <b>OPT.C_BPartner_ID</b> — (optional, identifier-ref) a previously-created BPartner; its own Value is used
	 *     as the search key, so a scenario never has to hardcode a Value that could collide with another run<br>
	 *   <b>OPT.C_Flatrate_Conditions_Value</b> — (optional) conditions search key to resolve (matched by Name)<br>
	 *   <b>OPT.StartDate</b> — (optional) the row's start date<br>
	 *   <b>OPT.EndDate</b> — (optional) the row's end date<br>
	 * @cucumber.example
	 * <pre>
	 * Given metasfresh contains I_Flatrate_Term:
	 *   | Identifier | C_BPartner_ID.Identifier | OPT.C_Flatrate_Conditions_Value | OPT.StartDate | OPT.EndDate |
	 *   | iFT_1      | invoicePartner            | Bonus conditions                | 2026-01-01     | 2026-12-31  |
	 * </pre>
	 */
	@Given("metasfresh contains I_Flatrate_Term:")
	public void metasfresh_contains_I_Flatrate_Term(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_I_Flatrate_Term record = InterfaceWrapperHelper.newInstance(I_I_Flatrate_Term.class);
			record.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());

			row.getAsOptionalIdentifier(I_I_Flatrate_Term.COLUMNNAME_C_BPartner_ID)
					.map(identifier -> identifier.lookupNotNullIn(bPartnerTable))
					.map(I_C_BPartner::getValue)
					.ifPresent(record::setBPartnerValue);
			row.getAsOptionalString(I_I_Flatrate_Term.COLUMNNAME_BPartnerValue)
					.ifPresent(record::setBPartnerValue);
			row.getAsOptionalString(I_I_Flatrate_Term.COLUMNNAME_C_Flatrate_Conditions_Value)
					.ifPresent(record::setC_Flatrate_Conditions_Value);
			row.getAsOptionalLocalDateTimestamp(I_I_Flatrate_Term.COLUMNNAME_StartDate)
					.ifPresent(record::setStartDate);
			row.getAsOptionalLocalDateTimestamp(I_I_Flatrate_Term.COLUMNNAME_EndDate)
					.ifPresent(record::setEndDate);

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
	 *   <b>OPT.CreatedTermEndDate</b> — (optional) expected EndDate of the C_Flatrate_Term the row created<br>
	 *   <b>OPT.CreatedTermDocStatus</b> — (optional) expected DocStatus of the C_Flatrate_Term the row created<br>
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

			row.getAsOptionalLocalDate("CreatedTermEndDate").ifPresent(expectedEndDate -> {
				final I_C_Flatrate_Term contract = record.getC_Flatrate_Term();
				assertThat(contract).as("I_Flatrate_Term[%s].C_Flatrate_Term", rowIdentifier).isNotNull();
				assertThat(TimeUtil.asLocalDate(contract.getEndDate()))
						.as("I_Flatrate_Term[%s]'s created C_Flatrate_Term.EndDate", rowIdentifier)
						.isEqualTo(expectedEndDate);
			});

			row.getAsOptionalString("CreatedTermDocStatus").ifPresent(expectedDocStatus -> {
				final I_C_Flatrate_Term contract = record.getC_Flatrate_Term();
				assertThat(contract).as("I_Flatrate_Term[%s].C_Flatrate_Term", rowIdentifier).isNotNull();
				assertThat(contract.getDocStatus())
						.as("I_Flatrate_Term[%s]'s created C_Flatrate_Term.DocStatus", rowIdentifier)
						.isEqualTo(expectedDocStatus);
			});
		});
	}
}
