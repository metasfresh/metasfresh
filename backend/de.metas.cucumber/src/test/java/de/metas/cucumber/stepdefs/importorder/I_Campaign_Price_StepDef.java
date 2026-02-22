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
import org.compiere.model.I_I_Campaign_Price;
import org.compiere.util.DB;

import static org.assertj.core.api.Assertions.*;

/**
 * Step definitions for I_Campaign_Price staging table.
 * Tests the BPartner resolution SQL from CampaignPriceImportTableSqlUpdater:
 * ORDER BY with IsCustomer='Y' tie-breaker + LIMIT 1.
 */
public class I_Campaign_Price_StepDef
{
	private final I_Campaign_Price_StepDefData iCampaignPriceTable;
	private final C_BPartner_StepDefData bPartnerTable;

	public I_Campaign_Price_StepDef(
			@NonNull final I_Campaign_Price_StepDefData iCampaignPriceTable,
			@NonNull final C_BPartner_StepDefData bPartnerTable)
	{
		this.iCampaignPriceTable = iCampaignPriceTable;
		this.bPartnerTable = bPartnerTable;
	}

	@Given("metasfresh contains I_Campaign_Price:")
	public void metasfresh_contains_I_Campaign_Price(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_I_Campaign_Price record = InterfaceWrapperHelper.newInstance(I_I_Campaign_Price.class);
			record.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());

			row.getAsOptionalString(I_I_Campaign_Price.COLUMNNAME_BPartner_Value)
					.ifPresent(record::setBPartner_Value);

			record.setI_IsImported("N");
			record.setISO_Code("EUR");
			record.setValidFrom(de.metas.common.util.time.SystemTime.asDayTimestamp());
			record.setValidTo(de.metas.common.util.time.SystemTime.asDayTimestamp());
			record.setInvoicableQtyBasedOn("Nominal");
			record.setCountryCode("DE");
			record.setUOM("PCE");
			InterfaceWrapperHelper.saveRecord(record);

			DB.executeUpdateAndSaveErrorOnFail(
					"UPDATE I_Campaign_Price SET C_BPartner_ID = NULL WHERE I_Campaign_Price_ID = " + record.getI_Campaign_Price_ID(),
					ITrx.TRXNAME_None);

			row.getAsOptionalIdentifier().ifPresent(identifier -> iCampaignPriceTable.putOrReplace(identifier, record));
		});
	}

	/**
	 * Executes the BPartner resolution SQL from CampaignPriceImportTableSqlUpdater.
	 * Uses ORDER BY with IsCustomer='Y' preference + LIMIT 1.
	 */
	@When("the CampaignPriceImportTableSqlUpdater BPartner resolution SQL is executed")
	public void the_CampaignPriceImportTableSqlUpdater_bpartner_resolution_sql_is_executed()
	{
		final int adClientId = StepDefConstants.CLIENT_ID.getRepoId();

		final String sql = "UPDATE I_Campaign_Price i "
				+ "SET C_BPartner_ID=(SELECT C_BPartner_ID FROM C_BPartner p"
				+ " WHERE p.isActive='Y' AND i.BPartner_Value=p.Value"
				+ " AND p.AD_Client_ID IN (i.AD_Client_ID, 0)"
				+ " AND p.AD_Org_ID IN (0, i.AD_Org_ID)"
				+ " ORDER BY p.AD_Client_ID DESC, p.AD_Org_ID DESC,"
				+ " CASE WHEN p.IsCustomer='Y' THEN 0 ELSE 1 END,"
				+ " p.C_BPartner_ID DESC LIMIT 1) "
				+ "WHERE C_BPartner_ID IS NULL"
				+ " AND I_IsImported<>'Y'"
				+ " AND AD_Client_ID=" + adClientId;

		final int rowsAffected = DB.executeUpdateAndSaveErrorOnFail(sql, ITrx.TRXNAME_None);
		assertThat(rowsAffected)
				.as("BPartner resolution SQL should affect at least one I_Campaign_Price row")
				.isGreaterThan(0);
	}

	@Then("validate I_Campaign_Price:")
	public void validate_I_Campaign_Price(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final StepDefDataIdentifier rowIdentifier = row.getAsIdentifier();
			final I_I_Campaign_Price record = rowIdentifier.lookupNotNullIn(iCampaignPriceTable);
			InterfaceWrapperHelper.refresh(record);

			row.getAsOptionalIdentifier(I_I_Campaign_Price.COLUMNNAME_C_BPartner_ID).ifPresent(bpIdentifier -> {
				final I_C_BPartner expectedBP = bpIdentifier.lookupNotNullIn(bPartnerTable);
				assertThat(record.getC_BPartner_ID())
						.as("I_Campaign_Price[%s].C_BPartner_ID should match %s", rowIdentifier, bpIdentifier)
						.isEqualTo(expectedBP.getC_BPartner_ID());
			});
		});
	}
}
