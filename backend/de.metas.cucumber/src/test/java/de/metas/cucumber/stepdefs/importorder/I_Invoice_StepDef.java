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
import org.compiere.model.I_I_Invoice;
import org.compiere.util.DB;

import static org.assertj.core.api.Assertions.*;

/**
 * Step definitions for I_Invoice staging table operations.
 * Tests the BPartner resolution SQL from ImportInvoice directly,
 * without running the full process (which has a second pass that
 * can roll back the first pass's SQL changes).
 */
public class I_Invoice_StepDef
{
	private final I_Invoice_StepDefData iInvoiceTable;
	private final C_BPartner_StepDefData bPartnerTable;

	public I_Invoice_StepDef(
			@NonNull final I_Invoice_StepDefData iInvoiceTable,
			@NonNull final C_BPartner_StepDefData bPartnerTable)
	{
		this.iInvoiceTable = iInvoiceTable;
		this.bPartnerTable = bPartnerTable;
	}

	@Given("metasfresh contains I_Invoice:")
	public void metasfresh_contains_I_Invoice(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_I_Invoice record = InterfaceWrapperHelper.newInstance(I_I_Invoice.class);

			record.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());

			row.getAsOptionalString(I_I_Invoice.COLUMNNAME_BPartnerValue)
					.ifPresent(record::setBPartnerValue);

			final boolean isSOTrx = row.getAsOptionalBoolean(I_I_Invoice.COLUMNNAME_IsSOTrx).orElse(true);
			record.setIsSOTrx(isSOTrx);

			record.setI_IsImported(false);

			InterfaceWrapperHelper.saveRecord(record);

			// Ensure C_BPartner_ID is NULL (not 0) in the DB so the
			// "WHERE C_BPartner_ID IS NULL" condition matches the row.
			DB.executeUpdateAndSaveErrorOnFail(
					"UPDATE I_Invoice SET C_BPartner_ID = NULL WHERE I_Invoice_ID = " + record.getI_Invoice_ID(),
					ITrx.TRXNAME_None);

			row.getAsOptionalIdentifier().ifPresent(identifier -> iInvoiceTable.putOrReplace(identifier, record));
		});
	}

	/**
	 * Executes the BPartner resolution SQL from ImportInvoice directly.
	 * This is the exact SQL that ImportInvoice.java uses in its first pass
	 * (with our ORDER BY + LIMIT 1 fix for duplicate-Value disambiguation),
	 * but without the full process overhead that can roll back on unrelated errors.
	 */
	@When("the ImportInvoice BPartner resolution SQL is executed")
	public void the_ImportInvoice_bpartner_resolution_sql_is_executed()
	{
		final int adClientId = StepDefConstants.CLIENT_ID.getRepoId();

		final String bpResolutionSql = "UPDATE I_Invoice o "
				+ "SET C_BPartner_ID=(SELECT bp.C_BPartner_ID FROM C_BPartner bp"
				+ " WHERE o.BPartnerValue=bp.Value AND o.AD_Client_ID=bp.AD_Client_ID"
				+ " AND bp.IsActive='Y'"
				+ " ORDER BY"
				+ " CASE WHEN o.IsSOTrx='Y' AND bp.IsCustomer='Y' THEN 0"
				+ "      WHEN o.IsSOTrx='N' AND bp.IsVendor='Y' THEN 0"
				+ "      ELSE 1 END,"
				+ " bp.C_BPartner_ID DESC"
				+ " LIMIT 1"
				+ " ) "
				+ "WHERE C_BPartner_ID IS NULL AND BPartnerValue IS NOT NULL"
				+ " AND I_IsImported<>'Y' AND AD_Client_ID=" + adClientId;

		final int rowsAffected = DB.executeUpdateAndSaveErrorOnFail(bpResolutionSql, ITrx.TRXNAME_None);
		assertThat(rowsAffected)
				.as("BPartner resolution SQL should affect at least one I_Invoice row")
				.isGreaterThan(0);
	}

	@Then("validate I_Invoice:")
	public void validate_I_Invoice(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final StepDefDataIdentifier rowIdentifier = row.getAsIdentifier();
			final I_I_Invoice record = rowIdentifier.lookupNotNullIn(iInvoiceTable);

			// Refresh from DB to get latest values after SQL execution
			InterfaceWrapperHelper.refresh(record);

			row.getAsOptionalIdentifier(I_I_Invoice.COLUMNNAME_C_BPartner_ID).ifPresent(bpIdentifier -> {
				final I_C_BPartner expectedBP = bpIdentifier.lookupNotNullIn(bPartnerTable);
				assertThat(record.getC_BPartner_ID())
						.as("I_Invoice[%s].C_BPartner_ID should match %s", rowIdentifier, bpIdentifier)
						.isEqualTo(expectedBP.getC_BPartner_ID());
			});

			row.getAsOptionalString(I_I_Invoice.COLUMNNAME_I_ErrorMsg).ifPresent(expectedSubstring -> {
				assertThat(record.getI_ErrorMsg())
						.as("I_Invoice[%s].I_ErrorMsg should contain '%s'", rowIdentifier, expectedSubstring)
						.contains(expectedSubstring);
			});
		});
	}
}
