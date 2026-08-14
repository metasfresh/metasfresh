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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.cucumber.stepdefs.vatid;

import com.google.common.collect.ImmutableList;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.process.AdProcessId;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.IADProcessDAO;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessExecutor;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessInfoLog;
import de.metas.security.IRoleDAO;
import de.metas.security.Role;
import de.metas.security.RoleId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Runs the {@code C_BPartner_VATaxID_Check} AD_Process — the manual/scheduled check process that runs on a
 * single Business Partner or on a selection, as opposed to the save-time trigger covered by
 * {@link VATaxIDCheck_StepDef} — and asserts what its {@code MaxChecksPerRun} throttle logged.
 *
 * <p>The process is looked up by its {@code AD_Process.Value}, the same way the WebUI resolves it, so this
 * step-def never has a compile-time dependency on the process' Java class.
 */
@RequiredArgsConstructor
public class VATaxIDCheckProcess_StepDef
{
	private static final String PROCESS_VALUE = "C_BPartner_VATaxID_Check";
	private static final String PARA_MaxChecksPerRun = "MaxChecksPerRun";

	@NonNull private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
	@NonNull private final IADPInstanceDAO pInstanceDAO = Services.get(IADPInstanceDAO.class);
	@NonNull private final IRoleDAO roleDAO = Services.get(IRoleDAO.class);

	@NonNull private final C_BPartner_StepDefData bpartnerTable;

	/** The PInstance of the last process run, read by {@link #assertPendingCountLogged(int)}. */
	@Nullable private PInstanceId lastPInstanceId;

	/**
	 * Runs the {@code C_BPartner_VATaxID_Check} process (the "check VAT-ID" action a back-office user
	 * triggers from the Business Partner window) against a selection of partners, the same way the WebUI
	 * runs it: synchronously, under the selection's client and the {@code WebUI} role. A blank
	 * {@code MaxChecksPerRun} leaves the parameter unset entirely — the same as a user who never touched
	 * the field — rather than sending an empty string.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_BPartner_ID</b> — (required, identifier-ref) partner included in the process' selection
	 * @cucumber.depends StepDefData: C_BPartner_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When the C_BPartner_VATaxID_Check process is run for selection with MaxChecksPerRun '2':
	 *   | C_BPartner_ID |
	 *   | bp_check1     |
	 * </pre>
	 */
	@When("the C_BPartner_VATaxID_Check process is run for selection with MaxChecksPerRun {string}:")
	public void runProcessForSelection(@NonNull final String maxChecksPerRunText, @NonNull final DataTable dataTable)
	{
		final ImmutableList<DataTableRow> rows = DataTableRows.of(dataTable).stream().collect(ImmutableList.toImmutableList());

		final ImmutableList<Integer> bpartnerIds = rows.stream()
				.map(row -> row.getAsIdentifier(I_C_BPartner.COLUMNNAME_C_BPartner_ID).lookupNotNullIn(bpartnerTable).getC_BPartner_ID())
				.collect(ImmutableList.toImmutableList());

		final I_C_BPartner firstBPartner = rows.get(0).getAsIdentifier(I_C_BPartner.COLUMNNAME_C_BPartner_ID).lookupNotNullIn(bpartnerTable);

		final AdProcessId processId = adProcessDAO.retrieveProcessIdByValue(PROCESS_VALUE);
		assertThat(processId).as("AD_Process with Value=%s must exist", PROCESS_VALUE).isNotNull();

		// run with the selection's client ctx + WebUI role; the default cucumber ctx (System client/role) would match no records
		final ClientId clientId = ClientId.ofRepoId(firstBPartner.getAD_Client_ID());
		final UserId loggedUserId = Env.getLoggedUserId();
		final RoleId roleId = roleDAO.getUserRoles(loggedUserId)
				.stream()
				.filter(r -> "WebUI".equals(r.getName()))
				.map(Role::getId)
				.findFirst()
				.orElseThrow(() -> new AdempiereException("WebUI role not found for user " + loggedUserId));

		final String idsCsv = bpartnerIds.stream()
				.map(String::valueOf)
				.reduce((a, b) -> a + "," + b)
				.orElseThrow(() -> new AdempiereException("No C_BPartner_ID given for the check-process selection"));
		final String whereClause = I_C_BPartner.COLUMNNAME_C_BPartner_ID + " IN (" + idsCsv + ")";

		final ProcessInfo.ProcessInfoBuilder builder = ProcessInfo.builder()
				.setAD_Process_ID(processId.getRepoId())
				.setClientId(clientId)
				.setRoleId(roleId)
				.setCreateTemporaryCtx()
				.setTableName(I_C_BPartner.Table_Name)
				.setWhereClause(whereClause);

		if (Check.isNotBlank(maxChecksPerRunText))
		{
			builder.addParameter(PARA_MaxChecksPerRun, Integer.parseInt(maxChecksPerRunText.trim()));
		}

		final ProcessExecutor executor = builder
				.buildAndPrepareExecution()
				.switchContextWhenRunning()
				.executeSync();

		executor.getResult().propagateErrorIfAny();

		lastPInstanceId = executor.getProcessInfo().getPinstanceId();
	}

	/**
	 * Runs the {@code C_BPartner_VATaxID_Check} process with NO selection at all — the exact shape
	 * {@code org.compiere.server.Scheduler#createProcessInfo} builds for a nightly {@code AD_Scheduler}
	 * run (no table, no where-clause, no single record), as opposed to
	 * {@link #runProcessForSelection(String, DataTable)}, which always carries one.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * When the C_BPartner_VATaxID_Check process is run as scheduled
	 * </pre>
	 */
	@When("the C_BPartner_VATaxID_Check process is run as scheduled")
	public void runProcessAsScheduled()
	{
		final AdProcessId processId = adProcessDAO.retrieveProcessIdByValue(PROCESS_VALUE);
		assertThat(processId).as("AD_Process with Value=%s must exist", PROCESS_VALUE).isNotNull();

		final UserId loggedUserId = Env.getLoggedUserId();
		final RoleId roleId = roleDAO.getUserRoles(loggedUserId)
				.stream()
				.filter(r -> "WebUI".equals(r.getName()))
				.map(Role::getId)
				.findFirst()
				.orElseThrow(() -> new AdempiereException("WebUI role not found for user " + loggedUserId));

		final ProcessExecutor executor = ProcessInfo.builder()
				.setAD_Process_ID(processId.getRepoId())
				.setClientId(ClientId.METASFRESH)
				.setRoleId(roleId)
				.setCreateTemporaryCtx()
				// deliberately no setTableName/setWhereClause/setRecord_ID: a scheduled run selects nothing,
				// per the class javadoc.
				.buildAndPrepareExecution()
				.switchContextWhenRunning()
				.executeSync();

		executor.getResult().propagateErrorIfAny();

		lastPInstanceId = executor.getProcessInfo().getPinstanceId();
	}

	/**
	 * Asserts that the last {@link #runProcessForSelection(String, DataTable)} run logged the given number
	 * of VAT-IDs left pending because the selection exceeded {@code MaxChecksPerRun}.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the VAT-ID check process run reports 1 pending checks
	 * </pre>
	 */
	@Then("the VAT-ID check process run reports {int} pending checks")
	public void assertPendingCountLogged(final int expectedPendingCount)
	{
		final String expectedSuffix = "pendingCount=" + expectedPendingCount;
		assertLastRunLogContains(expectedSuffix);
	}

	/**
	 * Asserts that the last process run logged exactly the given call count and average response time —
	 * the AC16 run-summary line ("calls made, and average response time").
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the VAT-ID check process run reports 2 calls with average response time 0ms
	 * </pre>
	 */
	@Then("the VAT-ID check process run reports {int} calls with average response time {int}ms")
	public void assertCallStatsLogged(final int expectedCallCount, final int expectedAverageResponseTimeMillis)
	{
		final String expectedSuffix = "calls=" + expectedCallCount + ", averageResponseTimeMillis=" + expectedAverageResponseTimeMillis;
		assertLastRunLogContains(expectedSuffix);
	}

	/**
	 * Asserts that the last process run logged the given member state as skipped because it reported
	 * itself unavailable, and how many VAT-IDs that skip covered.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the VAT-ID check process run reports member state 'EL' skipped 1 VAT-IDs
	 * </pre>
	 */
	@Then("the VAT-ID check process run reports member state {string} skipped {int} VAT-IDs")
	public void assertMemberStateSkipLogged(@NonNull final String countryCode, final int expectedSkippedCount)
	{
		final String expectedSuffix = "member state " + countryCode + " reports itself unavailable, skipped " + expectedSkippedCount + " VAT-IDs";
		assertLastRunLogContains(expectedSuffix);
	}

	/**
	 * Asserts that the last process run logged NO status-changed line for {@code vataxID} — the absence
	 * that proves both "no line for an unchanged record" (a re-check that reconfirms the same status) and
	 * "no status lines at all on a first run" (every record's very first check), per AC16. A test that
	 * only checked the presence of the run-summary lines would pass even against a logger that logs every
	 * record unconditionally; this is the assertion that would actually catch that.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the VAT-ID check process run reports no status-changed line for VATaxID 'DE136695976'
	 * </pre>
	 */
	@Then("the VAT-ID check process run reports no status-changed line for VATaxID {string}")
	public void assertNoStatusChangedLineLogged(@NonNull final String vataxID)
	{
		assertThat(lastPInstanceId).as("a C_BPartner_VATaxID_Check process must have run first").isNotNull();

		final List<ProcessInfoLog> logs = pInstanceDAO.retrieveProcessInfoLogs(lastPInstanceId);
		final String forbiddenInfix = "VAT-ID " + vataxID + ": status ";

		assertThat(logs)
				.as("AD_PInstance_Log of PInstance %s must contain NO status-changed line for VATaxID '%s'", lastPInstanceId, vataxID)
				.noneMatch(log -> log.getP_Msg() != null && log.getP_Msg().contains(forbiddenInfix));
	}

	private void assertLastRunLogContains(@NonNull final String expectedSuffix)
	{
		assertThat(lastPInstanceId).as("a C_BPartner_VATaxID_Check process must have run first").isNotNull();

		final List<ProcessInfoLog> logs = pInstanceDAO.retrieveProcessInfoLogs(lastPInstanceId);

		assertThat(logs)
				.as("AD_PInstance_Log of PInstance %s must contain a line ending with '%s'", lastPInstanceId, expectedSuffix)
				.anyMatch(log -> log.getP_Msg() != null && log.getP_Msg().endsWith(expectedSuffix));
	}
}
