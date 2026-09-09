package de.metas.cucumber.stepdefs.process;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_AD_Process_Para;

import javax.annotation.Nullable;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for creating {@link I_AD_Process_Para} records in tests, and for validating the
 * {@code DefaultValue} an existing one carries in the Application Dictionary.
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

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private int nextSeqNo = 10;

	/**
	 * The parameter the given process carries for the given column, or {@code null} when it carries none. The process
	 * itself must exist - a classname that matches nothing is a broken expectation, not a missing parameter.
	 */
	@Nullable
	public I_AD_Process_Para getProcessParaOrNull(@NonNull final String classname, @NonNull final String columnName)
	{
		final I_AD_Process process = queryBL.createQueryBuilder(I_AD_Process.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Process.COLUMNNAME_Classname, classname)
				.create()
				.firstOnlyNotNull(I_AD_Process.class);

		return queryBL.createQueryBuilder(I_AD_Process_Para.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Process_Para.COLUMNNAME_AD_Process_ID, process.getAD_Process_ID())
				.addEqualsFilter(I_AD_Process_Para.COLUMNNAME_ColumnName, columnName)
				.create()
				.firstOnly(I_AD_Process_Para.class);
	}

	/**
	 * Creates one or more {@link I_AD_Process_Para} records from the given data table.
	 * The parent process must have been created first via {@link AD_Process_Create_StepDef}.
	 */
	@Given("metasfresh contains AD_Process_Paras:")
	public void metasfresh_contains_ad_process_paras(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createAD_ProcessPara);
	}

	/**
	 * Asserts the {@code DefaultValue} an {@code AD_Process_Para} carries in the Application Dictionary - the value the
	 * parameter dialog offers a user who ticks nothing, which is not the same as a process's Java default.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Classname</b> — (required) the {@code AD_Process.Classname} the parameter belongs to<br>
	 *   <b>ColumnName</b> — (required) the parameter's {@code ColumnName}<br>
	 *   <b>DefaultValue</b> — (required) the expected {@code DefaultValue}<br>
	 * @cucumber.example
	 * <pre>
	 * Then validate AD_Process_Para:
	 *   | Classname                       | ColumnName | DefaultValue |
	 *   | de.metas.some.process.SomeClass | IsComplete | N            |
	 * </pre>
	 */
	@Then("validate AD_Process_Para:")
	public void validate_AD_Process_Para(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final String classname = row.getAsString(I_AD_Process.COLUMNNAME_Classname);
			final String columnName = row.getAsString(I_AD_Process_Para.COLUMNNAME_ColumnName);

			final I_AD_Process_Para para = getProcessParaOrNull(classname, columnName);
			assertThat(para)
					.as("the %s parameter of %s", columnName, classname)
					.isNotNull();

			assertThat(para.getDefaultValue())
					.as("%s of the %s parameter of %s", I_AD_Process_Para.COLUMNNAME_DefaultValue, columnName, classname)
					.isEqualTo(row.getAsString(I_AD_Process_Para.COLUMNNAME_DefaultValue));
		});
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
