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

import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.ProcessInfo;
import de.metas.security.IRoleDAO;
import de.metas.security.Role;
import de.metas.security.RoleId;
import de.metas.user.UserId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.compiere.util.Env;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Runs an {@code AD_Process} synchronously with explicit parameters, the way an {@code AD_Scheduler} runs it
 * with its {@code AD_Scheduler_Para} values: under the test's client context and the {@code WebUI} role.
 * <p>
 * Use it instead of {@code AD_Scheduler for classname ... is ran once} when a scenario only needs the
 * scheduled process to run. That step posts a scheduler RESTART plus RUN_ONCE over RabbitMQ; for a cron
 * scheduler the restarted server thread saves the shared {@code AD_Scheduler} record while the RUN_ONCE
 * saves it too, and when the two saves overlap, RUN_ONCE fails with "trxName shall not be null" before the
 * process runs.
 */
public class AD_Process_RunWithParameters_StepDef
{
	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
	private final IRoleDAO roleDAO = Services.get(IRoleDAO.class);

	/**
	 * Runs the {@code AD_Process} identified by its {@code Value} with the given parameters, synchronously,
	 * and fails the step if the process reports an error. Parameter values are passed as strings; the process
	 * converts them to the parameter's type (e.g. {@code Y}/{@code N} for a boolean).
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And the AD_Process with value 'PP_Order_Candidate_AlreadyMaturedForOrdering' is run with parameters:
	 *   | ParameterName                        | Value |
	 *   | IsDocComplete                        | Y     |
	 *   | AutoProcessCandidatesAfterProduction | Y     |
	 * </pre>
	 *
	 * @param processValue the {@code AD_Process.Value}
	 * @param dataTable    the parameters, with the columns {@code ParameterName} and {@code Value}
	 */
	@When("the AD_Process with value {string} is run with parameters:")
	public void run_ad_process_by_value_with_parameters(
			@NonNull final String processValue,
			@NonNull final DataTable dataTable)
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

		DataTableRows.of(dataTable).forEach(row -> processInfo.addParameter(row.getAsString("ParameterName"), row.getAsString("Value")));

		processInfo.buildAndPrepareExecution()
				.switchContextWhenRunning()
				.executeSync()
				.getResult()
				.propagateErrorIfAny();
	}
}
