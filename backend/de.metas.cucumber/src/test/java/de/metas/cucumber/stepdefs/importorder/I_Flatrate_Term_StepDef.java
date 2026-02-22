package de.metas.cucumber.stepdefs.importorder;

import de.metas.contracts.model.I_I_Flatrate_Term;
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
import org.compiere.util.DB;

import static org.assertj.core.api.Assertions.*;

/**
 * Step definitions for I_Flatrate_Term staging table.
 * Tests the BPartner resolution SQL from FlatrateTermImportTableSqlUpdater:
 * same CASE WHEN count > 1 pattern (error-marking for ambiguity).
 */
public class I_Flatrate_Term_StepDef
{
	private final I_Flatrate_Term_StepDefData iFlatrateTermTable;

	public I_Flatrate_Term_StepDef(@NonNull final I_Flatrate_Term_StepDefData iFlatrateTermTable)
	{
		this.iFlatrateTermTable = iFlatrateTermTable;
	}

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

	@When("the FlatrateTermImportTableSqlUpdater BPartner resolution SQL is executed")
	public void the_FlatrateTermImportTableSqlUpdater_bpartner_resolution_sql_is_executed()
	{
		final int adClientId = StepDefConstants.CLIENT_ID.getRepoId();

		final String sql = "UPDATE I_Flatrate_Term i "
				+ "SET C_BPartner_ID=CASE WHEN "
				+ "(SELECT count(*) FROM C_BPartner bp WHERE bp.Value=i.BPartnerValue AND bp.AD_Client_ID=i.AD_Client_ID AND bp.IsActive='Y') > 1 "
				+ "THEN NULL "
				+ "ELSE (SELECT MAX(bp.C_BPartner_ID) FROM C_BPartner bp WHERE bp.Value=i.BPartnerValue AND bp.AD_Client_ID=i.AD_Client_ID AND bp.IsActive='Y') END "
				+ "WHERE i.C_BPartner_ID IS NULL"
				+ " AND AD_Client_ID=" + adClientId;
		DB.executeUpdateAndSaveErrorOnFail(sql, ITrx.TRXNAME_None);

		final String sqlError = "UPDATE I_Flatrate_Term i "
				+ "SET I_IsImported='E', I_ErrorMsg=COALESCE(I_ErrorMsg,'')||'ERR: Multiple BPartners found for BPartnerValue' "
				+ "WHERE i.C_BPartner_ID IS NULL AND i.BPartnerValue IS NOT NULL"
				+ " AND I_IsImported<>'Y'"
				+ " AND (SELECT count(*) FROM C_BPartner bp WHERE bp.Value=i.BPartnerValue AND bp.AD_Client_ID=i.AD_Client_ID AND bp.IsActive='Y') > 1"
				+ " AND AD_Client_ID=" + adClientId;
		DB.executeUpdateAndSaveErrorOnFail(sqlError, ITrx.TRXNAME_None);
	}

	@Then("validate I_Flatrate_Term:")
	public void validate_I_Flatrate_Term(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final StepDefDataIdentifier rowIdentifier = row.getAsIdentifier();
			final I_I_Flatrate_Term record = rowIdentifier.lookupNotNullIn(iFlatrateTermTable);
			InterfaceWrapperHelper.refresh(record);

			row.getAsOptionalString("ExpectError").ifPresent(expectedErr -> {
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
