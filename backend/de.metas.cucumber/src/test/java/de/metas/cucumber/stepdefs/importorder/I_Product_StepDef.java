package de.metas.cucumber.stepdefs.importorder;

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
import org.compiere.model.I_I_Product;
import org.compiere.process.ImportProduct;
import org.compiere.util.DB;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

/**
 * Step definitions for I_Product staging table operations and ImportProduct process execution.
 * Used to test BPartner Value ambiguity detection during product import.
 */
public class I_Product_StepDef
{
	private final I_Product_StepDefData iProductTable;
	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

	public I_Product_StepDef(@NonNull final I_Product_StepDefData iProductTable)
	{
		this.iProductTable = iProductTable;
	}

	@Given("metasfresh contains I_Product:")
	public void metasfresh_contains_I_Product(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_I_Product record = InterfaceWrapperHelper.newInstance(I_I_Product.class);
			record.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());

			row.getAsOptionalString(I_I_Product.COLUMNNAME_BPartner_Value)
					.ifPresent(record::setBPartner_Value);

			record.setI_IsImported(false);
			InterfaceWrapperHelper.saveRecord(record);

			DB.executeUpdateAndSaveErrorOnFail(
					"UPDATE I_Product SET C_BPartner_ID = NULL WHERE I_Product_ID = " + record.getI_Product_ID(),
					ITrx.TRXNAME_None);

			row.getAsOptionalIdentifier().ifPresent(identifier -> iProductTable.putOrReplace(identifier, record));
		});
	}

	/**
	 * Runs the ImportProduct process with the current client context.
	 * This executes MProductImportTableSqlUpdater which resolves
	 * foreign keys and marks ambiguous BPartner Values as errors.
	 */
	@When("the ImportProduct process is invoked")
	public void the_ImportProduct_process_is_invoked()
	{
		final AdProcessId processId = adProcessDAO.retrieveProcessIdByClass(ImportProduct.class);

		final int adClientId = StepDefConstants.CLIENT_ID.getRepoId();

		ProcessInfo.builder()
				.setAD_Process_ID(processId.getRepoId())
				.addParameter("AD_Client_ID", BigDecimal.valueOf(adClientId))
				.buildAndPrepareExecution()
				.executeSync()
				.getResult();
	}

	@Then("validate I_Product:")
	public void validate_I_Product(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final StepDefDataIdentifier rowIdentifier = row.getAsIdentifier();
			final I_I_Product record = rowIdentifier.lookupNotNullIn(iProductTable);
			InterfaceWrapperHelper.refresh(record);

			row.getAsOptionalString("ExpectError").ifPresent(expectedErr -> {
				final String dbErrorMsg = DB.getSQLValueStringEx(ITrx.TRXNAME_None,
						"SELECT I_ErrorMsg FROM I_Product WHERE I_Product_ID=?", record.getI_Product_ID());
				final String dbImported = DB.getSQLValueStringEx(ITrx.TRXNAME_None,
						"SELECT I_IsImported FROM I_Product WHERE I_Product_ID=?", record.getI_Product_ID());
				assertThat(dbErrorMsg)
						.as("I_Product[%s].I_ErrorMsg should contain '%s'", rowIdentifier, expectedErr)
						.contains(expectedErr);
				assertThat(dbImported)
						.as("I_Product[%s].I_IsImported should be 'E'", rowIdentifier)
						.isEqualTo("E");
				assertThat(record.getC_BPartner_ID())
						.as("I_Product[%s].C_BPartner_ID should be 0 (NULL)", rowIdentifier)
						.isEqualTo(0);
			});

			if (row.getAsOptionalBoolean("IsResolved").orElseFalse())
			{
				assertThat(record.getC_BPartner_ID())
						.as("I_Product[%s].C_BPartner_ID should be resolved (non-zero)", rowIdentifier)
						.isGreaterThan(0);
			}
		});
	}
}
