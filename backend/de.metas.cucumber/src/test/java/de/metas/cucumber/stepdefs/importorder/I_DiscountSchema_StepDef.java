package de.metas.cucumber.stepdefs.importorder;

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
import org.compiere.model.I_I_DiscountSchema;
import org.compiere.util.DB;

import static org.assertj.core.api.Assertions.*;

/**
 * Step definitions for I_DiscountSchema staging table.
 * Tests the BPartner resolution SQL from MDiscountSchemaImportTableSqlUpdater:
 * same CASE WHEN count > 1 pattern as MProductImportTableSqlUpdater.
 */
public class I_DiscountSchema_StepDef
{
	private final I_DiscountSchema_StepDefData iDiscountSchemaTable;

	public I_DiscountSchema_StepDef(@NonNull final I_DiscountSchema_StepDefData iDiscountSchemaTable)
	{
		this.iDiscountSchemaTable = iDiscountSchemaTable;
	}

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

	@When("the MDiscountSchemaImportTableSqlUpdater BPartner resolution SQL is executed")
	public void the_MDiscountSchemaImportTableSqlUpdater_bpartner_resolution_sql_is_executed()
	{
		final int adClientId = StepDefConstants.CLIENT_ID.getRepoId();

		final String sql = "UPDATE I_DiscountSchema i "
				+ "SET C_BPartner_ID=CASE WHEN (SELECT count(*) FROM C_BPartner bp"
				+ " WHERE i.BPartner_Value=bp.Value AND i.AD_Client_ID=bp.AD_Client_ID AND bp.IsActive='Y') > 1 THEN NULL"
				+ " ELSE (SELECT MAX(C_BPartner_ID) FROM C_BPartner bp"
				+ " WHERE i.BPartner_Value=bp.Value AND i.AD_Client_ID=bp.AD_Client_ID AND bp.IsActive='Y') END "
				+ "WHERE C_BPartner_ID IS NULL AND BPartner_Value IS NOT NULL "
				+ "AND I_IsImported<>'Y' "
				+ "AND AD_Client_ID=" + adClientId;
		DB.executeUpdateAndSaveErrorOnFail(sql, ITrx.TRXNAME_None);

		final String sqlError = "UPDATE I_DiscountSchema i "
				+ "SET I_IsImported='E', I_ErrorMsg=COALESCE(I_ErrorMsg,'')"
				+ "||'ERR: Multiple BPartners found for BPartner_Value=\"'||i.BPartner_Value||'\"' "
				+ "WHERE C_BPartner_ID IS NULL AND BPartner_Value IS NOT NULL "
				+ "AND I_IsImported<>'Y' "
				+ "AND (SELECT count(*) FROM C_BPartner bp WHERE i.BPartner_Value=bp.Value AND i.AD_Client_ID=bp.AD_Client_ID AND bp.IsActive='Y') > 1 "
				+ "AND AD_Client_ID=" + adClientId;
		DB.executeUpdateAndSaveErrorOnFail(sqlError, ITrx.TRXNAME_None);
	}

	@Then("validate I_DiscountSchema:")
	public void validate_I_DiscountSchema(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final StepDefDataIdentifier rowIdentifier = row.getAsIdentifier();
			final I_I_DiscountSchema record = rowIdentifier.lookupNotNullIn(iDiscountSchemaTable);
			InterfaceWrapperHelper.refresh(record);

			row.getAsOptionalString("ExpectError").ifPresent(expectedErr -> {
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
