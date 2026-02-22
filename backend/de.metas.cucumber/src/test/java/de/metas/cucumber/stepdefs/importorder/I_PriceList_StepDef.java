package de.metas.cucumber.stepdefs.importorder;

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
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.process.ImportPriceList;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_I_PriceList;
import org.compiere.util.DB;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

public class I_PriceList_StepDef
{
	private final I_PriceList_StepDefData iPriceListTable;
	private final C_BPartner_StepDefData bPartnerTable;
	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

	public I_PriceList_StepDef(
			@NonNull final I_PriceList_StepDefData iPriceListTable,
			@NonNull final C_BPartner_StepDefData bPartnerTable)
	{
		this.iPriceListTable = iPriceListTable;
		this.bPartnerTable = bPartnerTable;
	}

	@Given("metasfresh contains I_PriceList:")
	public void metasfresh_contains_I_PriceList(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_I_PriceList record = InterfaceWrapperHelper.newInstance(I_I_PriceList.class);

			record.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());

			row.getAsOptionalString(I_I_PriceList.COLUMNNAME_BPartner_Value)
					.ifPresent(record::setBPartner_Value);

			final boolean isSOPriceList = row.getAsOptionalBoolean(I_I_PriceList.COLUMNNAME_IsSOPriceList).orElse(true);
			record.setIsSOPriceList(isSOPriceList);

			record.setI_IsImported(false);

			InterfaceWrapperHelper.saveRecord(record);

			DB.executeUpdateAndSaveErrorOnFail(
					"UPDATE I_PriceList SET C_BPartner_ID = NULL WHERE I_PriceList_ID = " + record.getI_PriceList_ID(),
					ITrx.TRXNAME_None);

			row.getAsOptionalIdentifier().ifPresent(identifier -> iPriceListTable.putOrReplace(identifier, record));
		});
	}

	@When("the ImportPriceList process is invoked")
	public void the_ImportPriceList_process_is_invoked()
	{
		final AdProcessId processId = adProcessDAO.retrieveProcessIdByClass(ImportPriceList.class);

		final int adClientId = StepDefConstants.CLIENT_ID.getRepoId();

		ProcessInfo.builder()
				.setAD_Process_ID(processId.getRepoId())
				.addParameter("AD_Client_ID", BigDecimal.valueOf(adClientId))
				.buildAndPrepareExecution()
				.executeSync()
				.getResult();
	}

	@Then("validate I_PriceList:")
	public void validate_I_PriceList(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final StepDefDataIdentifier rowIdentifier = row.getAsIdentifier();
			final I_I_PriceList record = rowIdentifier.lookupNotNullIn(iPriceListTable);

			InterfaceWrapperHelper.refresh(record);

			row.getAsOptionalIdentifier(I_I_PriceList.COLUMNNAME_C_BPartner_ID).ifPresent(bpIdentifier -> {
				final I_C_BPartner expectedBP = bpIdentifier.lookupNotNullIn(bPartnerTable);
				assertThat(record.getC_BPartner_ID())
						.as("I_PriceList[%s].C_BPartner_ID should match %s", rowIdentifier, bpIdentifier)
						.isEqualTo(expectedBP.getC_BPartner_ID());
			});

			row.getAsOptionalString(I_I_PriceList.COLUMNNAME_I_ErrorMsg).ifPresent(expectedSubstring -> {
				assertThat(record.getI_ErrorMsg())
						.as("I_PriceList[%s].I_ErrorMsg should contain '%s'", rowIdentifier, expectedSubstring)
						.contains(expectedSubstring);
			});
		});
	}
}
