package de.metas.cucumber.stepdefs.data_import;

import de.metas.cucumber.stepdefs.C_BPartner_StepDef;
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
import java.math.BigDecimal;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_I_Order;
import org.compiere.process.ImportOrder;
import org.compiere.util.DB;

import static org.assertj.core.api.Assertions.*;

/**
 * Step definitions for I_Order staging table operations and ImportOrder process execution.
 * Used to test BPartner Value disambiguation and other import-related scenarios.
 */
public class I_Order_StepDef
{
	private final I_Order_StepDefData iOrderTable;
	private final C_BPartner_StepDefData bPartnerTable;
	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

	public I_Order_StepDef(
			@NonNull final I_Order_StepDefData iOrderTable,
			@NonNull final C_BPartner_StepDefData bPartnerTable)
	{
		this.iOrderTable = iOrderTable;
		this.bPartnerTable = bPartnerTable;
	}

	/**
	 * Drops the unique index on C_BPartner.Value so that multiple BPartners
	 * with the same Value can coexist in the same Org.
	 * This is a prerequisite for testing BPartner Value disambiguation.
	 */
	@Given("the C_BPartner Value unique constraint is relaxed")
	public void the_c_bpartner_value_unique_constraint_is_relaxed()
	{
		DB.executeUpdateAndSaveErrorOnFail(
				"DROP INDEX IF EXISTS C_BPartner_Value_Unique",
				ITrx.TRXNAME_None);
	}

	/**
	 * Creates C_BPartner records directly via InterfaceWrapperHelper.newInstance,
	 * bypassing the lookup-by-Value logic in the standard C_BPartner step def.
	 * This allows creating multiple BPartners with the same Value.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias for cross-step reference<br>
	 *   <b>Name</b> — (required) BPartner name<br>
	 *   <b>Value</b> — (required) BPartner search key<br>
	 *   <b>IsCustomer</b> — (optional) Customer flag (Y/N), defaults to N<br>
	 *   <b>IsVendor</b> — (optional) Vendor flag (Y/N), defaults to N<br>
	 */
	@Given("metasfresh contains C_BPartners with duplicate Values allowed:")
	public void metasfresh_contains_c_bpartners_with_duplicate_values(@NonNull final DataTable dataTable)
	{
		// First pass: deactivate stale BPartners with matching Values from previous test runs.
		// Must happen BEFORE the creation loop to avoid deactivating BPartners created within
		// the same DataTable (multiple rows can share the same Value).
		final java.util.Set<String> deactivatedValues = new java.util.HashSet<>();
		DataTableRows.of(dataTable).forEach(row -> {
			final String value = row.getAsString(I_C_BPartner.COLUMNNAME_Value);
			if (deactivatedValues.add(value))
			{
				DB.executeUpdateAndSaveErrorOnFail(
						"UPDATE C_BPartner SET IsActive='N' WHERE Value=" + DB.TO_STRING(value)
								+ " AND AD_Client_ID=" + StepDefConstants.CLIENT_ID.getRepoId(),
						ITrx.TRXNAME_None);
			}
		});

		// Second pass: create the BPartners
		DataTableRows.of(dataTable).forEach(row -> {
			final I_C_BPartner bp = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
			bp.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
			bp.setValue(row.getAsString(I_C_BPartner.COLUMNNAME_Value));
			bp.setName(row.getAsString(I_C_BPartner.COLUMNNAME_Name));
			bp.setIsCustomer(row.getAsOptionalBoolean(I_C_BPartner.COLUMNNAME_IsCustomer).orElseFalse());
			bp.setIsVendor(row.getAsOptionalBoolean(I_C_BPartner.COLUMNNAME_IsVendor).orElseFalse());

			bp.setC_BP_Group_ID(C_BPartner_StepDef.BP_GROUP_ID);

			InterfaceWrapperHelper.saveRecord(bp);

			row.getAsOptionalIdentifier()
					.ifPresent(identifier -> bPartnerTable.putOrReplace(identifier, bp));
		});
	}

	/**
	 * Creates I_Order staging records for import testing.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias for cross-step reference<br>
	 *   <b>BPartnerValue</b> — (optional) BPartner search key to resolve<br>
	 *   <b>IsSOTrx</b> — (optional) Sales transaction flag (Y/N), defaults to Y<br>
	 *   <b>ProductValue</b> — (optional) Product search key<br>
	 *   <b>Qty</b> — (optional) Order quantity<br>
	 * @cucumber.example
	 * <pre>
	 * Given metasfresh contains I_Order:
	 *   | Identifier | BPartnerValue | IsSOTrx |
	 *   | iOrder_1   | BP_VALUE_1    | Y       |
	 * </pre>
	 */
	@Given("metasfresh contains I_Order:")
	public void metasfresh_contains_I_Order(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_I_Order iOrderRecord = InterfaceWrapperHelper.newInstance(I_I_Order.class);

			iOrderRecord.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());

			row.getAsOptionalString(I_I_Order.COLUMNNAME_BPartnerValue)
					.ifPresent(iOrderRecord::setBPartnerValue);

			final boolean isSOTrx = row.getAsOptionalBoolean(I_I_Order.COLUMNNAME_IsSOTrx).orElse(true);
			iOrderRecord.setIsSOTrx(isSOTrx);

			row.getAsOptionalString(I_I_Order.COLUMNNAME_ProductValue)
					.ifPresent(iOrderRecord::setProductValue);

			row.getAsOptionalBigDecimal(I_I_Order.COLUMNNAME_QtyOrdered)
					.ifPresent(iOrderRecord::setQtyOrdered);

			iOrderRecord.setI_IsImported(false);

			InterfaceWrapperHelper.saveRecord(iOrderRecord);

			// Ensure C_BPartner_ID is NULL (not 0) in the DB so ImportOrder's
			// "WHERE C_BPartner_ID IS NULL" condition matches the row.
			DB.executeUpdateAndSaveErrorOnFail(
					"UPDATE I_Order SET C_BPartner_ID = NULL WHERE I_Order_ID = " + iOrderRecord.getI_Order_ID(),
					ITrx.TRXNAME_None);

			row.getAsOptionalIdentifier().ifPresent(identifier -> iOrderTable.putOrReplace(identifier, iOrderRecord));
		});
	}

	/**
	 * Runs the ImportOrder process with the current client context.
	 * This executes all the SQL updates that resolve
	 * foreign keys (BPartner, Product, DocType, etc.) from staging values.
	 */
	@When("the ImportOrder process is invoked")
	public void the_ImportOrder_process_is_invoked()
	{
		final AdProcessId processId = adProcessDAO.retrieveProcessIdByClass(ImportOrder.class);

		final int adClientId = StepDefConstants.CLIENT_ID.getRepoId();

		ProcessInfo.builder()
				.setAD_Process_ID(processId.getRepoId())
				.addParameter("AD_Client_ID", BigDecimal.valueOf(adClientId))
				.buildAndPrepareExecution()
				.executeSync()
				.getResult();
	}

	/**
	 * Validates I_Order staging records after import process execution.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias referencing a previously created I_Order record<br>
	 *   <b>C_BPartner_ID</b> — (optional, identifier-ref) expected resolved BPartner<br>
	 *   <b>I_ErrorMsg</b> — (optional) expected error message substring<br>
	 * @cucumber.depends StepDefData: I_Order_StepDefData, C_BPartner_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then validate I_Order:
	 *   | Identifier | C_BPartner_ID |
	 *   | iOrder_1   | bpartner_cust |
	 * </pre>
	 */
	@Then("validate I_Order:")
	public void validate_I_Order(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final StepDefDataIdentifier rowIdentifier = row.getAsIdentifier();
			final I_I_Order iOrderRecord = rowIdentifier.lookupNotNullIn(iOrderTable);

			// Refresh from DB to get latest values after process execution
			InterfaceWrapperHelper.refresh(iOrderRecord);

			row.getAsOptionalIdentifier(I_I_Order.COLUMNNAME_C_BPartner_ID).ifPresent(bpIdentifier -> {
				final I_C_BPartner expectedBP = bpIdentifier.lookupNotNullIn(bPartnerTable);
				assertThat(iOrderRecord.getC_BPartner_ID())
						.as("I_Order[%s].C_BPartner_ID should match %s", rowIdentifier, bpIdentifier)
						.isEqualTo(expectedBP.getC_BPartner_ID());
			});

			row.getAsOptionalString(I_I_Order.COLUMNNAME_I_ErrorMsg).ifPresent(expectedSubstring -> {
				assertThat(iOrderRecord.getI_ErrorMsg())
						.as("I_Order[%s].I_ErrorMsg should contain '%s'", rowIdentifier, expectedSubstring)
						.contains(expectedSubstring);
			});
		});
	}
}
