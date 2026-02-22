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
import org.compiere.model.I_I_Product;
import org.compiere.util.DB;

import static org.assertj.core.api.Assertions.*;

/**
 * Step definitions for I_Product staging table.
 * Tests the BPartner resolution SQL from MProductImportTableSqlUpdater:
 * when multiple BPartners share the same Value, the row should get
 * C_BPartner_ID=NULL and I_IsImported='E' with an error message.
 */
public class I_Product_StepDef
{
	private final I_Product_StepDefData iProductTable;

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
	 * Executes the BPartner resolution SQL from MProductImportTableSqlUpdater:
	 * CASE WHEN count > 1 → NULL, ELSE MAX(C_BPartner_ID).
	 * Then marks ambiguous rows with I_IsImported='E'.
	 */
	@When("the MProductImportTableSqlUpdater BPartner resolution SQL is executed")
	public void the_MProductImportTableSqlUpdater_bpartner_resolution_sql_is_executed()
	{
		final int adClientId = StepDefConstants.CLIENT_ID.getRepoId();

		// Phase 1: Set C_BPartner_ID (NULL if ambiguous)
		final String sql = "UPDATE I_Product i "
				+ "SET C_BPartner_ID=CASE WHEN (SELECT count(*) FROM C_BPartner p"
				+ " WHERE i.BPartner_Value=p.Value AND i.AD_Client_ID=p.AD_Client_ID AND p.IsActive='Y') > 1 THEN NULL"
				+ " ELSE (SELECT MAX(C_BPartner_ID) FROM C_BPartner p"
				+ " WHERE i.BPartner_Value=p.Value AND i.AD_Client_ID=p.AD_Client_ID AND p.IsActive='Y') END "
				+ "WHERE C_BPartner_ID IS NULL"
				+ " AND I_IsImported<>'Y'"
				+ " AND AD_Client_ID=" + adClientId;
		DB.executeUpdateAndSaveErrorOnFail(sql, ITrx.TRXNAME_None);

		// Phase 2: Mark ambiguous rows as errors
		final String sqlError = "UPDATE I_Product i "
				+ "SET I_IsImported='E', I_ErrorMsg=COALESCE(I_ErrorMsg,'')"
				+ "||'ERR: Multiple BPartners found for BPartner_Value=\"'||i.BPartner_Value||'\"' "
				+ "WHERE C_BPartner_ID IS NULL AND i.BPartner_Value IS NOT NULL"
				+ " AND I_IsImported<>'Y'"
				+ " AND (SELECT count(*) FROM C_BPartner p WHERE i.BPartner_Value=p.Value AND i.AD_Client_ID=p.AD_Client_ID AND p.IsActive='Y') > 1"
				+ " AND AD_Client_ID=" + adClientId;
		DB.executeUpdateAndSaveErrorOnFail(sqlError, ITrx.TRXNAME_None);
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
