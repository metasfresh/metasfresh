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
 * Step definitions for creating {@link I_AD_Process_Para} records in tests.
 *
 * <p>Required columns:
 * <ul>
 *   <li>{@code AD_Process_ID} — identifier referencing the parent {@link I_AD_Process} (from {@link AD_Process_Create_StepDef})</li>
 *   <li>{@code ColumnName} — parameter column name (e.g. {@code PRINTER_OPTS_IsPrintLogo})</li>
 *   <li>{@code Name} — display name</li>
 * </ul>
 *
 * <p>Optional columns:
 * <ul>
 *   <li>{@code SeqNo} — sequence number (default: auto-incrementing from 10)</li>
 *   <li>{@code FieldLength} — field length (default: 0)</li>
 *   <li>{@code DefaultValue} — default value string; supports {@code @SQL=} expressions</li>
 *   <li>{@code AD_Reference_ID} — reference type (e.g. 20 for Yes-No)</li>
 *   <li>{@code Description} — parameter description</li>
 *   <li>{@code Identifier} — test-local reference for cross-step lookups</li>
 * </ul>
 *
 * <p>Example:
 * <pre>{@code
 * Given metasfresh contains AD_Process_Paras:
 *   | Identifier | AD_Process_ID | ColumnName               | Name       | DefaultValue | AD_Reference_ID |
 *   | param      | process       | PRINTER_OPTS_IsPrintLogo | Print Logo | Y            | 20              |
 * }</pre>
 */
@RequiredArgsConstructor
public class AD_Process_Para_StepDef
{
	@NonNull private final AD_Process_StepDefData processTable;
	@NonNull private final AD_Process_Para_StepDefData processParaTable;

	private int nextSeqNo = 10;

	/**
	 * Creates one or more {@link I_AD_Process_Para} records from the given data table.
	 * The parent process must have been created first via {@link AD_Process_Create_StepDef}.
	 */
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
		para.setSeqNo(row.getAsOptionalInt(I_AD_Process_Para.COLUMNNAME_SeqNo).orElse(nextSeqNo));
		nextSeqNo += 10;
		para.setColumnName(row.getAsString(I_AD_Process_Para.COLUMNNAME_ColumnName));
		para.setName(row.getAsString(I_AD_Process_Para.COLUMNNAME_Name));
		para.setFieldLength(row.getAsOptionalInt(I_AD_Process_Para.COLUMNNAME_FieldLength).orElse(0));
		row.getAsOptionalString(I_AD_Process_Para.COLUMNNAME_DefaultValue).ifPresent(para::setDefaultValue);
		row.getAsOptionalString(I_AD_Process_Para.COLUMNNAME_Description).ifPresent(para::setDescription);
		row.getAsOptionalInt(I_AD_Process_Para.COLUMNNAME_AD_Reference_ID).ifPresent(para::setAD_Reference_ID);
		InterfaceWrapperHelper.saveRecord(para);

		row.getAsOptionalIdentifier().ifPresent(id -> processParaTable.putOrReplace(id, para));
	}
}
