package de.metas.cucumber.stepdefs.data_import;

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
import org.compiere.model.I_I_DiscountSchema;
import org.compiere.process.ImportDiscountSchema;
import org.compiere.util.DB;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

/**
 * Step definitions for I_DiscountSchema staging table operations and ImportDiscountSchema process execution.
 * Used to test BPartner Value ambiguity detection during discount schema import.
 */
public class I_DiscountSchema_StepDef
{
	private final I_DiscountSchema_StepDefData iDiscountSchemaTable;
	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

	public I_DiscountSchema_StepDef(@NonNull final I_DiscountSchema_StepDefData iDiscountSchemaTable)
	{
		this.iDiscountSchemaTable = iDiscountSchemaTable;
	}

	/**
	 * Creates I_DiscountSchema staging records for import testing.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias for cross-step reference<br>
	 *   <b>BPartner_Value</b> — (optional) BPartner search key to resolve<br>
	 * @cucumber.example
	 * <pre>
	 * Given metasfresh contains I_DiscountSchema:
	 *   | Identifier | BPartner_Value     |
	 *   | iDS_1      | SHARED_DS_BP_VAL   |
	 * </pre>
	 */
	@Given("metasfresh contains I_DiscountSchema:")
	public void metasfresh_contains_I_DiscountSchema(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_I_DiscountSchema record = InterfaceWrapperHelper.newInstance(I_I_DiscountSchema.class);
			record.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());

			row.getAsOptionalString(I_I_DiscountSchema.COLUMNNAME_BPartner_Value)
					.ifPresent(record::setBPartner_Value);

			record.setI_IsImported("N");
			InterfaceWrapperHelper.saveRecord(record);

			DB.executeUpdateAndSaveErrorOnFail(
					"UPDATE I_DiscountSchema SET C_BPartner_ID = NULL WHERE I_DiscountSchema_ID = " + record.getI_DiscountSchema_ID(),
					ITrx.TRXNAME_None);

			row.getAsOptionalIdentifier().ifPresent(identifier -> iDiscountSchemaTable.putOrReplace(identifier, record));
		});
	}

	/**
	 * Runs the ImportDiscountSchema process with the current client context.
	 * This executes MDiscountSchemaImportTableSqlUpdater which resolves
	 * foreign keys and marks ambiguous BPartner Values as errors.
	 */
	@When("the ImportDiscountSchema process is invoked")
	public void the_ImportDiscountSchema_process_is_invoked()
	{
		final AdProcessId processId = adProcessDAO.retrieveProcessIdByClass(ImportDiscountSchema.class);

		final int adClientId = StepDefConstants.CLIENT_ID.getRepoId();

		ProcessInfo.builder()
				.setAD_Process_ID(processId.getRepoId())
				.addParameter("AD_Client_ID", BigDecimal.valueOf(adClientId))
				.buildAndPrepareExecution()
				.executeSync()
				.getResult();
	}

	/**
	 * Validates I_DiscountSchema staging records after import process execution.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias referencing a previously created I_DiscountSchema record<br>
	 *   <b>I_ErrorMsg</b> — (optional) expected error message substring; also asserts I_IsImported='E'<br>
	 *   <b>IsResolved</b> — (optional, boolean) if true, asserts C_BPartner_ID &gt; 0<br>
	 * @cucumber.depends StepDefData: I_DiscountSchema_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then validate I_DiscountSchema:
	 *   | Identifier | I_ErrorMsg               |
	 *   | iDS_1      | Multiple BPartners found |
	 * </pre>
	 */
	@Then("validate I_DiscountSchema:")
	public void validate_I_DiscountSchema(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final StepDefDataIdentifier rowIdentifier = row.getAsIdentifier();
			final I_I_DiscountSchema record = rowIdentifier.lookupNotNullIn(iDiscountSchemaTable);
			InterfaceWrapperHelper.refresh(record);

			row.getAsOptionalString(I_I_DiscountSchema.COLUMNNAME_I_ErrorMsg).ifPresent(expectedErr -> {
				assertThat(record.getI_ErrorMsg())
						.as("I_DiscountSchema[%s].I_ErrorMsg should contain '%s'", rowIdentifier, expectedErr)
						.contains(expectedErr);
				assertThat(record.getI_IsImported())
						.as("I_DiscountSchema[%s].I_IsImported should be 'E'", rowIdentifier)
						.isEqualTo("E");
			});

			if (row.getAsOptionalBoolean("IsResolved").orElseFalse())
			{
				assertThat(record.getC_BPartner_ID())
						.as("I_DiscountSchema[%s].C_BPartner_ID should be resolved", rowIdentifier)
						.isGreaterThan(0);
			}
		});
	}
}
