package de.metas.cucumber.stepdefs.process;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_AD_Process_Para;

/**
 * Step definitions for creating AD_Process_Para records in tests.
 *
 * <p>Required columns: {@code AD_Process_ID} (identifier), {@code ColumnName}, {@code Name}
 * <p>Optional columns: {@code DefaultValue}, {@code AD_Reference_ID}, {@code Description}
 */
@RequiredArgsConstructor
public class AD_Process_Para_StepDef
{
	@NonNull private final AD_Process_StepDefData processTable;
	@NonNull private final AD_Process_Para_StepDefData processParaTable;

	@Given("metasfresh contains AD_Process_Paras:")
	public void metasfresh_contains_ad_process_paras(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createAD_ProcessPara);
	}

	private void createAD_ProcessPara(@NonNull final DataTableRow row)
	{
		final I_AD_Process process = processTable.get(row.getAsIdentifier(I_AD_Process_Para.COLUMNNAME_AD_Process_ID));

		final I_AD_Process_Para para = InterfaceWrapperHelper.newInstance(I_AD_Process_Para.class);
		para.setAD_Process_ID(process.getAD_Process_ID());
		para.setColumnName(row.getAsString(I_AD_Process_Para.COLUMNNAME_ColumnName));
		para.setName(row.getAsString(I_AD_Process_Para.COLUMNNAME_Name));
		row.getAsOptionalString(I_AD_Process_Para.COLUMNNAME_DefaultValue).ifPresent(para::setDefaultValue);
		row.getAsOptionalString(I_AD_Process_Para.COLUMNNAME_Description).ifPresent(para::setDescription);
		row.getAsOptionalInt(I_AD_Process_Para.COLUMNNAME_AD_Reference_ID).ifPresent(para::setAD_Reference_ID);
		InterfaceWrapperHelper.saveRecord(para);

		row.getAsOptionalIdentifier().ifPresent(id -> processParaTable.putOrReplace(id, para));
	}
}
