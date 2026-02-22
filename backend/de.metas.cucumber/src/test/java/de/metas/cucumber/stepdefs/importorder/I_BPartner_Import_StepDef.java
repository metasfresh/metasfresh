package de.metas.cucumber.stepdefs.importorder;

import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_I_BPartner;
import org.compiere.util.DB;

import static org.assertj.core.api.Assertions.*;

/**
 * Step definitions for I_BPartner staging table (BPartner Import).
 * Tests the BPartner resolution SQL from BPartnerImportTableSqlUpdater:
 * ORDER BY with IsCustomer/IsVendor tie-breaker + LIMIT 1.
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
	 * Executes the BPartner resolution SQL from BPartnerImportTableSqlUpdater.
	 * Uses ORDER BY with IsCustomer/IsVendor tie-breaker + LIMIT 1.
	 */
	@When("the BPartnerImportTableSqlUpdater BPartner resolution SQL is executed")
	public void the_BPartnerImportTableSqlUpdater_bpartner_resolution_sql_is_executed()
	{
		final int adClientId = StepDefConstants.CLIENT_ID.getRepoId();
		final int adOrgId = StepDefConstants.ORG_ID.getRepoId();

		final String sql = "UPDATE I_BPartner i "
				+ "SET C_BPartner_ID = ("
				+ "SELECT bp.C_BPartner_ID FROM C_BPartner bp"
				+ " WHERE bp.Value = i.BPValue"
				+ " AND bp.AD_Client_ID = i.AD_Client_ID"
				+ " AND bp.AD_Org_ID = i.AD_Org_ID"
				+ " AND bp.IsActive = 'Y'"
				+ " ORDER BY"
				+ " CASE WHEN i.IsCustomer = 'Y' AND bp.IsCustomer = 'Y' THEN 0"
				+ "      WHEN i.IsVendor = 'Y' AND bp.IsVendor = 'Y' THEN 0"
				+ "      ELSE 1 END,"
				+ " bp.C_BPartner_ID"
				+ " LIMIT 1"
				+ ") "
				+ "WHERE i.C_BPartner_ID IS NULL"
				+ " AND i.BPValue IS NOT NULL"
				+ " AND i.I_IsImported <> 'Y'"
				+ " AND i.AD_Client_ID=" + adClientId;

		final int rowsAffected = DB.executeUpdateAndSaveErrorOnFail(sql, ITrx.TRXNAME_None);
		assertThat(rowsAffected)
				.as("BPartner resolution SQL should affect at least one I_BPartner row")
				.isGreaterThan(0);
	}

	@Then("validate I_BPartner:")
	public void validate_I_BPartner(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final StepDefDataIdentifier rowIdentifier = row.getAsIdentifier();
			final I_I_BPartner record = rowIdentifier.lookupNotNullIn(iBPartnerTable);
			InterfaceWrapperHelper.refresh(record);

			row.getAsOptionalIdentifier(I_I_BPartner.COLUMNNAME_C_BPartner_ID).ifPresent(bpIdentifier -> {
				final I_C_BPartner expectedBP = bpIdentifier.lookupNotNullIn(bPartnerTable);
				assertThat(record.getC_BPartner_ID())
						.as("I_BPartner[%s].C_BPartner_ID should match %s", rowIdentifier, bpIdentifier)
						.isEqualTo(expectedBP.getC_BPartner_ID());
			});
		});
	}
}
