package de.metas.cucumber.stepdefs.process;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Process;
import org.compiere.model.X_AD_Process;

/**
 * Step definitions for creating {@link I_AD_Process} records in tests.
 *
 * <p>Required columns:
 * <ul>
 *   <li>{@code Value} — process value (unique identifier)</li>
 *   <li>{@code Name} — display name</li>
 * </ul>
 *
 * <p>Optional columns:
 * <ul>
 *   <li>{@code Type} — process type (default: {@code Java}). See {@link X_AD_Process} TYPE_* constants.</li>
 *   <li>{@code AccessLevel} — access level (default: {@code 3} = Client+Organization). See {@link X_AD_Process} ACCESSLEVEL_* constants.</li>
 *   <li>{@code Classname} — Java class implementing the process</li>
 *   <li>{@code Identifier} — test-local reference for cross-step lookups</li>
 * </ul>
 *
 * <p>Example:
 * <pre>{@code
 * Given metasfresh contains AD_Processes:
 *   | Identifier | Value            | Name              |
 *   | process    | TestPrintProcess | Test Print Process |
 * }</pre>
 */
@RequiredArgsConstructor
public class AD_Process_Create_StepDef
{
	@NonNull private final AD_Process_StepDefData processTable;

	/**
	 * Creates one or more {@link I_AD_Process} records from the given data table.
	 *
	 * @see AD_Process_Para_StepDef for creating process parameters
	 */
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
		process.setType(row.getAsOptionalString(I_AD_Process.COLUMNNAME_Type).orElse(X_AD_Process.TYPE_Java));
		process.setAccessLevel(row.getAsOptionalString(I_AD_Process.COLUMNNAME_AccessLevel).orElse(X_AD_Process.ACCESSLEVEL_ClientPlusOrganization));
		row.getAsOptionalString(I_AD_Process.COLUMNNAME_Classname).ifPresent(process::setClassname);
		InterfaceWrapperHelper.saveRecord(process);

		row.getAsOptionalIdentifier().ifPresent(id -> processTable.putOrReplace(id, process));
	}
}
