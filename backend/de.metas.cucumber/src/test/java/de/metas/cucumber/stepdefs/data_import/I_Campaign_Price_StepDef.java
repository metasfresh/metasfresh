package de.metas.cucumber.stepdefs.data_import;

import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.pricing.rules.campaign_price.process.C_Campaign_Price_Import;
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
import org.compiere.model.I_I_Campaign_Price;
import org.compiere.util.DB;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

/**
 * Step definitions for I_Campaign_Price staging table operations and CampaignPriceImportProcess execution.
 * Used to test BPartner Value disambiguation during campaign price import.
 */
public class I_Campaign_Price_StepDef
{
	private final I_Campaign_Price_StepDefData iCampaignPriceTable;
	private final C_BPartner_StepDefData bPartnerTable;
	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

	public I_Campaign_Price_StepDef(
			@NonNull final I_Campaign_Price_StepDefData iCampaignPriceTable,
			@NonNull final C_BPartner_StepDefData bPartnerTable)
	{
		this.iCampaignPriceTable = iCampaignPriceTable;
		this.bPartnerTable = bPartnerTable;
	}

	/**
	 * Creates I_Campaign_Price staging records for import testing.
	 * Sets mandatory defaults (ISO_Code=EUR, InvoicableQtyBasedOn=Nominal, CountryCode=DE, UOM=PCE).
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias for cross-step reference<br>
	 *   <b>BPartner_Value</b> — (optional) BPartner search key to resolve<br>
	 * @cucumber.example
	 * <pre>
	 * Given metasfresh contains I_Campaign_Price:
	 *   | Identifier | BPartner_Value |
	 *   | iCP_1      | BP_VALUE_1     |
	 * </pre>
	 */
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
	 * Runs the CampaignPriceImportProcess with the current client context.
	 * This executes CampaignPriceImportTableSqlUpdater which resolves
	 * foreign keys (including C_BPartner_ID from BPartner_Value) from staging values.
	 */
	@When("the CampaignPriceImportProcess is invoked")
	public void the_CampaignPriceImportProcess_is_invoked()
	{
		// Mark stale rows from previous test runs as imported so they don't
		// interfere. Only keep rows we explicitly created in this scenario.
		final String currentIdList = iCampaignPriceTable.streamRecords()
				.map(r -> String.valueOf(r.getI_Campaign_Price_ID()))
				.collect(java.util.stream.Collectors.joining(","));

		if (!currentIdList.isEmpty())
		{
			DB.executeUpdateAndSaveErrorOnFail(
					"UPDATE I_Campaign_Price SET I_IsImported='Y' WHERE I_IsImported<>'Y' AND I_Campaign_Price_ID NOT IN (" + currentIdList + ")",
					ITrx.TRXNAME_None);
		}

		final AdProcessId processId = adProcessDAO.retrieveProcessIdByClass(C_Campaign_Price_Import.class);

		final int adClientId = StepDefConstants.CLIENT_ID.getRepoId();

		ProcessInfo.builder()
				.setAD_Process_ID(processId.getRepoId())
				.addParameter("AD_Client_ID", BigDecimal.valueOf(adClientId))
				.buildAndPrepareExecution()
				.executeSync()
				.getResult();
	}

	/**
	 * Validates I_Campaign_Price staging records after import process execution.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias referencing a previously created I_Campaign_Price record<br>
	 *   <b>C_BPartner_ID</b> — (optional, identifier-ref) expected resolved BPartner<br>
	 * @cucumber.depends StepDefData: I_Campaign_Price_StepDefData, C_BPartner_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then validate I_Campaign_Price:
	 *   | Identifier | C_BPartner_ID |
	 *   | iCP_1      | bpartner_cust |
	 * </pre>
	 */
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
