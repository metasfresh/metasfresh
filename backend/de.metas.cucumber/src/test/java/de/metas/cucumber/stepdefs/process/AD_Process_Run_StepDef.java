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
import org.compiere.util.Env;

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
 */
@RequiredArgsConstructor
public class AD_Process_Run_StepDef
{
	@NonNull private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
	@NonNull private final IRoleDAO roleDAO = Services.get(IRoleDAO.class);

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

		ProcessInfo.builder()
				.setAD_Process_ID(processId.getRepoId())
				.setClientId(clientId)
				.setRoleId(roleId)
				.setCreateTemporaryCtx()
				.buildAndPrepareExecution()
				.switchContextWhenRunning()
				.executeSync()
				.getResult()
				.propagateErrorIfAny();
	}
}
