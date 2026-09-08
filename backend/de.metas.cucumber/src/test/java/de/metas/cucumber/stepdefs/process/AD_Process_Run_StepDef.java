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

package de.metas.cucumber.stepdefs.process;

import com.google.common.collect.ImmutableSet;
import de.metas.cucumber.stepdefs.util.IdentifiersResolver;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.ProcessInfo;
import de.metas.security.IRoleDAO;
import de.metas.security.Role;
import de.metas.security.RoleId;
import de.metas.user.UserId;
import de.metas.util.Services;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Generic step definition that runs any {@code AD_Process} by its {@code Value}, the same way the
 * {@code Scheduler} runs it: under the test's client context and the {@code WebUI} role (the default
 * cucumber System client/role context would match no business records). Works for SQL processes
 * (e.g. {@code de.metas.process.ExecuteUpdateSQL}) as well as Java processes that take no parameters.
 *
 * <p>Rationale for direct invocation: in production this process is triggered by an {@code AD_Scheduler}
 * at a configurable interval. Invoking it directly via {@code ProcessInfo.executeSync()} keeps the test
 * deterministic — no dependency on scheduler timing or async queues.
 *
 * <p>The second step runs a process over a USER SELECTION of records, the way a WebUI view quick action
 * invokes a selection process.
 */
@RequiredArgsConstructor
public class AD_Process_Run_StepDef
{
	@NonNull private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
	@NonNull private final IRoleDAO roleDAO = Services.get(IRoleDAO.class);

	@NonNull private final IdentifiersResolver identifiersResolver;

	/**
	 * Runs the {@code AD_Process} identified by its {@code Value}, synchronously, and fails the step if the
	 * process reports an error. The process is executed under the logged-in client and the {@code WebUI}
	 * role, mirroring how the {@code AD_Scheduler} invokes it.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * When the AD_Process with value 'My_AD_Process_Value' is run
	 * </pre>
	 *
	 * @param processValue the {@code AD_Process.Value}
	 */
	@When("the AD_Process with value {string} is run")
	public void run_ad_process_by_value(@NonNull final String processValue)
	{
		runProcess(processValue, null, null);
	}

	/**
	 * Runs the {@code AD_Process} identified by its {@code Value} over the given records as its USER SELECTION -
	 * the way a WebUI view quick action invokes a selection process. The records are handed over as the process's
	 * where clause, exactly as the WebUI does it, so a process that narrows the selection further (by DocStatus,
	 * say) still gets to make that decision itself.
	 *
	 * <p>All identifiers must resolve to the same table.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * When the AD_Process with value 'PP_Order_CloseSelection' is run on the records identified by 'order_1,order_2'
	 * </pre>
	 *
	 * @param processValue the {@code AD_Process.Value}
	 * @param commaSeparatedIdentifiers identifiers of the records forming the selection
	 */
	@When("the AD_Process with value {string} is run on the records identified by {string}")
	public void run_ad_process_on_selection(
			@NonNull final String processValue,
			@NonNull final String commaSeparatedIdentifiers)
	{
		final ImmutableSet<TableRecordReference> recordRefs = identifiersResolver.getTableRecordReferencesOfCommaSeparatedIdentifiers(commaSeparatedIdentifiers);
		assertThat(recordRefs).as("records identified by `%s`", commaSeparatedIdentifiers).isNotEmpty();

		final ImmutableSet<String> tableNames = recordRefs.stream().map(TableRecordReference::getTableName).collect(ImmutableSet.toImmutableSet());
		assertThat(tableNames).as("all records of one selection must belong to the same table").hasSize(1);

		final String tableName = tableNames.iterator().next();
		final String recordIdsCSV = recordRefs.stream()
				.map(recordRef -> String.valueOf(recordRef.getRecord_ID()))
				.collect(Collectors.joining(","));

		runProcess(processValue, tableName, tableName + "_ID IN (" + recordIdsCSV + ")");
	}

	private void runProcess(
			@NonNull final String processValue,
			@Nullable final String tableName,
			@Nullable final String whereClause)
	{
		final AdProcessId processId = adProcessDAO.retrieveProcessIdByValue(processValue);
		assertThat(processId).as("AD_Process with Value=%s must exist", processValue).isNotNull();

		final ClientId clientId = Env.getClientId();
		final UserId loggedUserId = Env.getLoggedUserId();
		final RoleId roleId = roleDAO.getUserRoles(loggedUserId)
				.stream()
				.filter(r -> "WebUI".equals(r.getName()))
				.map(Role::getId)
				.findFirst()
				.orElseThrow(() -> new AdempiereException("WebUI role not found for user " + loggedUserId));

		final ProcessInfo.ProcessInfoBuilder processInfo = ProcessInfo.builder()
				.setAD_Process_ID(processId.getRepoId())
				.setClientId(clientId)
				.setRoleId(roleId)
				.setCreateTemporaryCtx();

		// Only touch the table/selection setters on the selection path. setTableName(null) is NOT the same as
		// never calling it: it pins AD_Table_ID to -1 and kills the AD_PInstance fallback that the plain
		// no-selection step has always relied on.
		if (tableName != null)
		{
			processInfo.setTableName(tableName).setWhereClause(whereClause);
		}

		processInfo.buildAndPrepareExecution()
				.switchContextWhenRunning()
				.executeSync()
				.getResult()
				.propagateErrorIfAny();
	}
}
