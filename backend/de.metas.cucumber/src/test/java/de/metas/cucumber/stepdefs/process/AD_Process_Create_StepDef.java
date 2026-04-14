package de.metas.cucumber.stepdefs.process;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Process;

/**
 * Step definitions for creating AD_Process records in tests.
 */
@RequiredArgsConstructor
public class AD_Process_Create_StepDef
{
	@NonNull private final AD_Process_StepDefData processTable;

	@Given("metasfresh contains AD_Processes:")
	public void metasfresh_contains_ad_processes(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createAD_Process);
	}

	private void createAD_Process(@NonNull final DataTableRow row)
	{
		final I_AD_Process process = InterfaceWrapperHelper.newInstance(I_AD_Process.class);
		process.setValue(row.getAsString(I_AD_Process.COLUMNNAME_Value));
		process.setName(row.getAsString(I_AD_Process.COLUMNNAME_Name));
		row.getAsOptionalString(I_AD_Process.COLUMNNAME_Classname).ifPresent(process::setClassname);
		InterfaceWrapperHelper.saveRecord(process);

		row.getAsOptionalIdentifier().ifPresent(id -> processTable.putOrReplace(id, process));
	}
}
