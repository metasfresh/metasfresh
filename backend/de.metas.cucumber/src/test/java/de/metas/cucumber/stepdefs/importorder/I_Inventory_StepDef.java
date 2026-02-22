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
import org.compiere.model.I_I_Inventory;
import org.compiere.util.DB;

import static org.assertj.core.api.Assertions.*;

/**
 * Step definitions for I_Inventory staging table.
 * Tests the SubProducer BPartner resolution SQL from MInventoryImportTableSqlUpdater:
 * requires IsVendor='Y', filters by Client/Org, uses ORDER BY + LIMIT 1.
 */
public class I_Inventory_StepDef
{
	private final I_Inventory_StepDefData iInventoryTable;
	private final C_BPartner_StepDefData bPartnerTable;

	public I_Inventory_StepDef(
			@NonNull final I_Inventory_StepDefData iInventoryTable,
			@NonNull final C_BPartner_StepDefData bPartnerTable)
	{
		this.iInventoryTable = iInventoryTable;
		this.bPartnerTable = bPartnerTable;
	}

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
	 * Executes the SubProducer BPartner resolution SQL from MInventoryImportTableSqlUpdater.
	 * Requires IsVendor='Y', filters by Client/Org, uses ORDER BY AD_Org_ID DESC LIMIT 1.
	 */
	@When("the MInventoryImportTableSqlUpdater SubProducer BPartner resolution SQL is executed")
	public void the_MInventoryImportTableSqlUpdater_subproducer_bpartner_resolution_sql_is_executed()
	{
		final int adClientId = StepDefConstants.CLIENT_ID.getRepoId();

		final String sql = "UPDATE I_Inventory i "
				+ "SET SubProducer_BPartner_ID=(SELECT bp.C_BPartner_ID FROM C_BPartner bp"
				+ " WHERE i.SubProducerBPartner_Value=bp.value"
				+ " AND bp.AD_Client_ID=i.AD_Client_ID"
				+ " AND bp.AD_Org_ID IN (i.AD_Org_ID, 0)"
				+ " AND bp.IsVendor='Y'"
				+ " AND bp.IsActive='Y'"
				+ " ORDER BY bp.AD_Org_ID DESC LIMIT 1) "
				+ "WHERE SubProducer_BPartner_ID IS NULL AND SubProducerBPartner_Value IS NOT NULL"
				+ " AND I_IsImported<>'Y'"
				+ " AND AD_Client_ID=" + adClientId;

		DB.executeUpdateAndSaveErrorOnFail(sql, ITrx.TRXNAME_None);
	}

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
