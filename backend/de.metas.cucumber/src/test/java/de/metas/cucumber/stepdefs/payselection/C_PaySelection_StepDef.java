package de.metas.cucumber.stepdefs.payselection;

import de.metas.cucumber.stepdefs.DataTableRow;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class C_PaySelection_StepDef
{
	@NonNull private final C_PaySelection_StepDefData paySelectionTable;

	@And("metasfresh contains C_PaySelections")
	public void addC_PaySelections(@NonNull final DataTable dataTable)
	{
		// DataTableRow
		// final List<Map<String, String>> rows = dataTable.asMaps();
		// for (final Map<String, String> dataTableRow : rows)
		// {
		// 	create_C_Payment(dataTableRow);
		// }
	}

}
