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
import org.compiere.model.I_I_Inventory;
import org.compiere.process.ImportInventory;
import org.compiere.util.DB;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

/**
 * Step definitions for I_Inventory staging table operations and ImportInventory process execution.
 * Used to test SubProducer BPartner Value disambiguation during inventory import.
 */
public class I_Inventory_StepDef
{
	private final I_Inventory_StepDefData iInventoryTable;
	private final C_BPartner_StepDefData bPartnerTable;
	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

	public I_Inventory_StepDef(
			@NonNull final I_Inventory_StepDefData iInventoryTable,
			@NonNull final C_BPartner_StepDefData bPartnerTable)
	{
		this.iInventoryTable = iInventoryTable;
		this.bPartnerTable = bPartnerTable;
	}

	/**
	 * Creates I_Inventory staging records for import testing.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias for cross-step reference<br>
	 *   <b>SubProducerBPartner_Value</b> — (optional) SubProducer BPartner search key to resolve<br>
	 * @cucumber.example
	 * <pre>
	 * Given metasfresh contains I_Inventory:
	 *   | Identifier | SubProducerBPartner_Value |
	 *   | iInv_1     | SHARED_INV_SUB_VAL       |
	 * </pre>
	 */
	@Given("metasfresh contains I_Inventory:")
	public void metasfresh_contains_I_Inventory(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_I_Inventory record = InterfaceWrapperHelper.newInstance(I_I_Inventory.class);
			record.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());

			row.getAsOptionalString(I_I_Inventory.COLUMNNAME_SubProducerBPartner_Value)
					.ifPresent(record::setSubProducerBPartner_Value);

			record.setI_IsImported(false);
			InterfaceWrapperHelper.saveRecord(record);

			DB.executeUpdateAndSaveErrorOnFail(
					"UPDATE I_Inventory SET SubProducer_BPartner_ID = NULL WHERE I_Inventory_ID = " + record.getI_Inventory_ID(),
					ITrx.TRXNAME_None);

			row.getAsOptionalIdentifier().ifPresent(identifier -> iInventoryTable.putOrReplace(identifier, record));
		});
	}

	/**
	 * Runs the ImportInventory process with the current client context.
	 * This executes MInventoryImportTableSqlUpdater which resolves
	 * foreign keys (including SubProducer_BPartner_ID from SubProducerBPartner_Value) from staging values.
	 */
	@When("the ImportInventory process is invoked")
	public void the_ImportInventory_process_is_invoked()
	{
		final AdProcessId processId = adProcessDAO.retrieveProcessIdByClass(ImportInventory.class);

		final int adClientId = StepDefConstants.CLIENT_ID.getRepoId();

		ProcessInfo.builder()
				.setAD_Process_ID(processId.getRepoId())
				.addParameter("AD_Client_ID", BigDecimal.valueOf(adClientId))
				.buildAndPrepareExecution()
				.executeSync()
				.getResult();
	}

	/**
	 * Validates I_Inventory staging records after import process execution.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias referencing a previously created I_Inventory record<br>
	 *   <b>SubProducer_BPartner_ID</b> — (optional, identifier-ref) expected resolved SubProducer BPartner<br>
	 *   <b>IsUnresolved</b> — (optional, boolean) if true, asserts SubProducer_BPartner_ID = 0 (unresolved)<br>
	 * @cucumber.depends StepDefData: I_Inventory_StepDefData, C_BPartner_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then validate I_Inventory:
	 *   | Identifier | SubProducer_BPartner_ID |
	 *   | iInv_1     | bp_inv_vendor           |
	 * </pre>
	 */
	@Then("validate I_Inventory:")
	public void validate_I_Inventory(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final StepDefDataIdentifier rowIdentifier = row.getAsIdentifier();
			final I_I_Inventory record = rowIdentifier.lookupNotNullIn(iInventoryTable);
			InterfaceWrapperHelper.refresh(record);

			row.getAsOptionalIdentifier(I_I_Inventory.COLUMNNAME_SubProducer_BPartner_ID).ifPresent(bpIdentifier -> {
				final I_C_BPartner expectedBP = bpIdentifier.lookupNotNullIn(bPartnerTable);
				assertThat(record.getSubProducer_BPartner_ID())
						.as("I_Inventory[%s].SubProducer_BPartner_ID should match %s", rowIdentifier, bpIdentifier)
						.isEqualTo(expectedBP.getC_BPartner_ID());
			});

			if (row.getAsOptionalBoolean("IsUnresolved").orElseFalse())
			{
				assertThat(record.getSubProducer_BPartner_ID())
						.as("I_Inventory[%s].SubProducer_BPartner_ID should be 0 (unresolved)", rowIdentifier)
						.isEqualTo(0);
			}
		});
	}
}
