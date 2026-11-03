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

package de.metas.cucumber.stepdefs.shipmentschedule.process;

import com.google.common.collect.ImmutableList;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.shipmentschedule.M_ShipmentSchedule_StepDefData;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.process.M_ShipmentSchedule_CloseShipmentSchedules;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.ProcessInfo;
import de.metas.security.IRoleDAO;
import de.metas.security.Role;
import de.metas.security.RoleId;
import de.metas.user.UserId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.compiere.util.Env;

import javax.annotation.Nullable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

/**
 * Runs the user-initiated {@code M_ShipmentSchedule_CloseShipmentSchedules} AD_Process (the "Close shipment
 * schedules" action a warehouse/back-office user triggers from the shipment-schedule window) against a
 * selection of shipment schedules, and asserts whether it is rejected.
 */
@RequiredArgsConstructor
public class M_ShipmentSchedule_CloseShipmentSchedules_StepDef
{
	@NonNull private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
	@NonNull private final IRoleDAO roleDAO = Services.get(IRoleDAO.class);

	@NonNull private final M_ShipmentSchedule_StepDefData shipmentScheduleTable;

	@Nullable private Throwable lastCloseProcessException;

	/**
	 * Runs the {@code M_ShipmentSchedule_CloseShipmentSchedules} process (the user-initiated "Close shipment
	 * schedules" action) on the given selection of shipment schedules, the same way the WebUI runs it: as a
	 * synchronous, user-triggered process under the record's client and the {@code WebUI} role. Any error the
	 * process throws is caught (not propagated) so a later step can assert on the rejection; a following
	 * {@code Then the M_ShipmentSchedule_CloseShipmentSchedules process is rejected} step reads it.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_ShipmentSchedule_ID</b> — (required, identifier-ref) shipment schedule included in the close selection<br>
	 * @cucumber.depends StepDefData: M_ShipmentSchedule_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When the M_ShipmentSchedule_CloseShipmentSchedules process is run for selection:
	 *   | M_ShipmentSchedule_ID |
	 *   | shipmentSchedule      |
	 * </pre>
	 */
	@When("^the M_ShipmentSchedule_CloseShipmentSchedules process is run for selection:$")
	public void runCloseShipmentSchedulesProcess(@NonNull final DataTable dataTable)
	{
		final ImmutableList<DataTableRow> rows = DataTableRows.of(dataTable).stream().collect(ImmutableList.toImmutableList());

		final ImmutableList<ShipmentScheduleId> scheduleIds = rows.stream()
				.map(row -> row.getAsIdentifier(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID).lookupNotNullIdIn(shipmentScheduleTable))
				.collect(ImmutableList.toImmutableList());

		final I_M_ShipmentSchedule firstSchedule = rows.get(0).getAsIdentifier(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID).lookupNotNullIn(shipmentScheduleTable);

		final AdProcessId processId = adProcessDAO.retrieveProcessIdByClass(M_ShipmentSchedule_CloseShipmentSchedules.class);

		// run with the schedule's client ctx + WebUI role; the default cucumber ctx (System client/role) would match no records
		final ClientId clientId = ClientId.ofRepoId(firstSchedule.getAD_Client_ID());
		final UserId loggedUserId = Env.getLoggedUserId();
		final RoleId roleId = roleDAO.getUserRoles(loggedUserId)
				.stream()
				.filter(r -> "WebUI".equals(r.getName()))
				.map(Role::getId)
				.findFirst()
				.orElseThrow(() -> new AdempiereException("WebUI role not found for user " + loggedUserId));

		final String idsCsv = scheduleIds.stream()
				.map(id -> String.valueOf(id.getRepoId()))
				.reduce((a, b) -> a + "," + b)
				.orElseThrow(() -> new AdempiereException("No M_ShipmentSchedule_ID given for the close-process selection"));
		final String whereClause = I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID + " IN (" + idsCsv + ")";

		lastCloseProcessException = catchThrowable(() -> ProcessInfo.builder()
				.setAD_Process_ID(processId.getRepoId())
				.setClientId(clientId)
				.setRoleId(roleId)
				.setCreateTemporaryCtx()
				.setTableName(I_M_ShipmentSchedule.Table_Name)
				.setWhereClause(whereClause)
				.buildAndPrepareExecution()
				.switchContextWhenRunning()
				.executeSync()
				.getResult()
				.propagateErrorIfAny());
	}

	/**
	 * Asserts that the last {@code M_ShipmentSchedule_CloseShipmentSchedules} process run was rejected AND that the
	 * rejection carries the given {@code ErrorCode}. The error code identifies which rejection message was raised:
	 * {@code ShipmentSchedule_UnfinishedPicking} (exactly one offending schedule → the specific, order-naming message)
	 * vs {@code ShipmentSchedule_UnfinishedPickings} (two or more offending schedules → the generic message that does
	 * not enumerate the schedules) vs {@code ShipmentSchedule_NotEligibleToClose} (no unfinished picking, but the whole
	 * selection is ineligible: every schedule is already processed or still has a picked-but-unshipped qty).
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: none (reads the exception captured by the previous step)
	 * @cucumber.example
	 * <pre>
	 * Then the M_ShipmentSchedule_CloseShipmentSchedules process is rejected with error code "ShipmentSchedule_UnfinishedPicking"
	 * </pre>
	 */
	@Then("^the M_ShipmentSchedule_CloseShipmentSchedules process is rejected with error code \"([^\"]+)\"$")
	public void assertCloseShipmentSchedulesProcessRejectedWithErrorCode(@NonNull final String expectedErrorCode)
	{
		assertThat(lastCloseProcessException)
				.as("Closing a shipment schedule with an unfinished (Drafted) picking job must be rejected")
				.isNotNull()
				.isInstanceOf(AdempiereException.class);

		assertThat(AdempiereException.extractErrorCodeOrNull(lastCloseProcessException))
				.as("Close rejection must carry the expected ErrorCode")
				.isEqualTo(expectedErrorCode);
	}

	/**
	 * Asserts that the last {@code M_ShipmentSchedule_CloseShipmentSchedules} process run
	 * ({@link #runCloseShipmentSchedulesProcess(DataTable)}) completed WITHOUT error (i.e. the close was accepted,
	 * not refused).
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: none (reads the exception captured by the previous step)
	 * @cucumber.example
	 * <pre>
	 * Then the M_ShipmentSchedule_CloseShipmentSchedules process is not rejected
	 * </pre>
	 */
	@Then("^the M_ShipmentSchedule_CloseShipmentSchedules process is not rejected$")
	public void assertCloseShipmentSchedulesProcessNotRejected()
	{
		assertThat(lastCloseProcessException)
				.as("Closing a shipment schedule with no unfinished picking job must NOT be rejected")
				.isNull();
	}
}
