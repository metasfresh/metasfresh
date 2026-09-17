package de.metas.cucumber.stepdefs.material.dispo;

/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.productCategory.M_Product_Category_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.material.dispo.reconcile.process.MD_Candidate_ATP_Divergence_Report;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessInfo;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions around the read-only {@code AD_Process} {@link MD_Candidate_ATP_Divergence_Report}:
 * running it with an optional warehouse/product/product-category filter and asserting its resolved log
 * text - the process writes nothing, so the log is the only outcome there is to assert on. Structured
 * after the sibling {@code AtpReconciliationBackup_StepDef}'s own process-invocation steps.
 */
@RequiredArgsConstructor
public class AtpDivergenceReport_StepDef
{
	@NonNull private final IADProcessDAO processDAO = Services.get(IADProcessDAO.class);

	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final M_Warehouse_StepDefData warehouseTable;
	@NonNull private final M_Product_Category_StepDefData productCategoryTable;

	/** Maps a scenario-local alias (e.g. {@code report_a}) to the resolved log text of the run it came from. */
	private final Map<String, String> processLogByAlias = new HashMap<>();

	/**
	 * Invokes {@link MD_Candidate_ATP_Divergence_Report}. Every filter column is optional, matching the
	 * process's own parameters; an omitted filter is not restricted on. The resolved log text (every
	 * {@code {}} placeholder already substituted) is stored under {@code runIdAlias} for
	 * {@link #assertProcessLogContains}/{@link #assertProcessLogDoesNotContain} to check afterwards.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Product_ID</b> — (optional, identifier-ref) restricts the run to this product<br>
	 *   <b>M_Warehouse_ID</b> — (optional, identifier-ref) restricts the run to this warehouse<br>
	 *   <b>M_Product_Category_ID</b> — (optional, identifier-ref) restricts the run to this product category<br>
	 * @cucumber.depends StepDefData: M_Product_StepDefData, M_Warehouse_StepDefData, M_Product_Category_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When the MD_Candidate_ATP_Divergence_Report process is run with parameters, storing the run id as "report_a":
	 *   | M_Product_Category_ID |
	 *   | cat_report_a          |
	 * </pre>
	 */
	@When("^the MD_Candidate_ATP_Divergence_Report process is run with parameters, storing the run id as \"([^\"]*)\":$")
	public void runDivergenceReportProcess(
			@NonNull final String runIdAlias,
			@NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRows.of(dataTable).getFirstRow();

		final AdProcessId processId = processDAO.retrieveProcessIdByClass(MD_Candidate_ATP_Divergence_Report.class);
		final ProcessInfo.ProcessInfoBuilder processInfoBuilder = ProcessInfo.builder()
				.setAD_Process_ID(processId.getRepoId());

		row.getAsOptionalIdentifier("M_Warehouse_ID")
				.ifPresent(identifier -> processInfoBuilder.addParameter("M_Warehouse_ID", warehouseTable.getId(identifier).getRepoId()));
		row.getAsOptionalIdentifier("M_Product_ID")
				.ifPresent(identifier -> processInfoBuilder.addParameter("M_Product_ID", productTable.getId(identifier).getRepoId()));
		row.getAsOptionalIdentifier("M_Product_Category_ID")
				.ifPresent(identifier -> processInfoBuilder.addParameter("M_Product_Category_ID", productCategoryTable.getId(identifier).getRepoId()));

		final ProcessExecutionResult result = processInfoBuilder
				.buildAndPrepareExecution()
				.executeSync()
				.getResult();

		assertThat(result).as("MD_Candidate_ATP_Divergence_Report process result").isNotNull();
		assertThat(result.isError()).as("MD_Candidate_ATP_Divergence_Report process failed: %s", result).isFalse();

		processLogByAlias.put(runIdAlias, result.getLogInfo());
	}

	/**
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the divergence report process log for the run id "report_a" contains "1 diverged"
	 * </pre>
	 */
	@Then("^the divergence report process log for the run id \"([^\"]*)\" contains \"([^\"]*)\"$")
	public void assertProcessLogContains(
			@NonNull final String runIdAlias,
			@NonNull final String expectedSubstring)
	{
		final String logText = processLogByAlias.get(runIdAlias);
		assertThat(logText).as("no process log was stored for alias '%s' - the triggering step must run first", runIdAlias).isNotNull();
		assertThat(logText).as("process log for run '%s'", runIdAlias).contains(expectedSubstring);
	}

	/**
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the divergence report process log for the run id "report_a" does not contain "storedAtp=80, difference=0"
	 * </pre>
	 */
	@Then("^the divergence report process log for the run id \"([^\"]*)\" does not contain \"([^\"]*)\"$")
	public void assertProcessLogDoesNotContain(
			@NonNull final String runIdAlias,
			@NonNull final String unexpectedSubstring)
	{
		final String logText = processLogByAlias.get(runIdAlias);
		assertThat(logText).as("no process log was stored for alias '%s' - the triggering step must run first", runIdAlias).isNotNull();
		assertThat(logText).as("process log for run '%s'", runIdAlias).doesNotContain(unexpectedSubstring);
	}
}
