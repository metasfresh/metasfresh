package de.metas.cucumber.stepdefs.process;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Process;
import org.compiere.model.X_AD_Process;

import java.util.List;

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
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

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

	/**
	 * Sets {@code IsPdfA3Output = Y} on all {@link I_AD_Process} records whose {@code JasperReport}
	 * path contains the given substring.  This is used by ZUGFeRD E2E tests to tell the mock report
	 * service to return a valid PDF/A-3 fixture rather than the 4-byte stub, so that
	 * {@code ZugferdAssembler.embed()} (which calls PDFBox) receives parseable PDF/A-3 bytes.
	 *
	 * <p>Required columns:
	 * <ul>
	 *   <li>{@code JasperReport} — substring of {@code AD_Process.JasperReport} used to identify the process(es)</li>
	 * </ul>
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And set IsPdfA3Output for AD_Process with JasperReport containing:
	 *   | JasperReport            |
	 *   | de/metas/docs/sales/invoice |
	 * </pre>
	 */
	@And("set IsPdfA3Output for AD_Process with JasperReport containing:")
	public void setIsPdfA3OutputForJasperReport(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final String jasperReportSubstring = row.getAsString(I_AD_Process.COLUMNNAME_JasperReport);

			final List<I_AD_Process> processes = queryBL.createQueryBuilder(I_AD_Process.class)
					.addStringLikeFilter(I_AD_Process.COLUMNNAME_JasperReport, jasperReportSubstring, true)
					.create()
					.list();

			if (processes.isEmpty())
			{
				throw new AdempiereException("No AD_Process found with JasperReport containing: " + jasperReportSubstring);
			}

			for (final I_AD_Process process : processes)
			{
				process.setIsPdfA3Output(true);
				InterfaceWrapperHelper.saveRecord(process);
			}
		});
	}
}
