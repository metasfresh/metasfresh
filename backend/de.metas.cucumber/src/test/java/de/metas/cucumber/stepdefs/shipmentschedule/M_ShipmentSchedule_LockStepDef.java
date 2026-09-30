/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.cucumber.stepdefs.shipmentschedule;

import com.google.common.collect.ImmutableSet;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.lock.ShipmentScheduleLockRepository;
import de.metas.inoutcandidate.lock.ShipmentScheduleLockRequest;
import de.metas.inoutcandidate.lock.ShipmentScheduleLockType;
import de.metas.inoutcandidate.lock.ShipmentScheduleLocksMap;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class M_ShipmentSchedule_LockStepDef
{
	private final IUserDAO userDAO = Services.get(IUserDAO.class);
	private final ShipmentScheduleLockRepository lockRepository = SpringContextHolder.instance.getBean(ShipmentScheduleLockRepository.class);

	@NonNull private final M_ShipmentSchedule_StepDefData shipmentScheduleTable;

	/**
	 * Creates a PICKING lock on the given shipment schedule(s) on behalf of the specified user.
	 * Used to simulate a concurrent picker already holding the lock, e.g. to verify that
	 * mass printing skips schedules locked by another user.
	 *
	 * <p>Required columns:
	 * <ul>
	 *   <li>{@code M_ShipmentSchedule_ID} — identifier of the shipment schedule to lock</li>
	 *   <li>{@code Login} — login of the user who should own the lock</li>
	 * </ul>
	 *
	 * <p>Example:
	 * <pre>
	 * Given M_ShipmentSchedule_Lock exists for:
	 *   | M_ShipmentSchedule_ID | Login   |
	 *   | sched1                | picker2 |
	 * </pre>
	 */
	@Given("M_ShipmentSchedule_Lock exists for:")
	public void createLockForShipmentScheduleAndUser(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach((tableRow) -> {
			final ShipmentScheduleId shipmentScheduleId = tableRow.getAsIdentifier(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID)
					.lookupIdIn(shipmentScheduleTable);

			final String login = tableRow.getAsString(I_AD_User.COLUMNNAME_Login);
			final UserId userId = userDAO.retrieveUserIdByLogin(login);
			assertThat(userId).as("User exists for login=" + login).isNotNull();

			lockRepository.lock(ShipmentScheduleLockRequest.builder()
					.shipmentScheduleId(shipmentScheduleId)
					.lockedBy(userId)
					.lockType(ShipmentScheduleLockType.PICKING)
					.build());
		});
	}

	/**
	 * Asserts whether a PICKING lock exists (or does not exist) for the given shipment schedule and user.
	 *
	 * <p>Required columns:
	 * <ul>
	 *   <li>{@code M_ShipmentSchedule_ID} — identifier of the shipment schedule</li>
	 *   <li>{@code Login} — login of the user expected to hold (or not hold) the lock</li>
	 *   <li>{@code Exists} — {@code true} if the lock is expected to exist; {@code false} if it should be absent</li>
	 * </ul>
	 *
	 * <p>Example:
	 * <pre>
	 * Then validate M_ShipmentSchedule_Lock record for:
	 *   | M_ShipmentSchedule_ID | Login   | Exists |
	 *   | sched1                | picker2 | true   |
	 * </pre>
	 */
	@Then("validate M_ShipmentSchedule_Lock record for:")
	public void validateLockForShipmentScheduleAndUser(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach((tableRow) -> {
			final ShipmentScheduleId shipmentScheduleId = tableRow.getAsIdentifier(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID)
					.lookupIdIn(shipmentScheduleTable);

			final String username = tableRow.getAsString(I_AD_User.COLUMNNAME_Login);
			final UserId userId = userDAO.retrieveUserIdByLogin(username);
			assertThat(userId).as("User exists for login=" + username).isNotNull();

			final ShipmentScheduleLocksMap locks = lockRepository.getByShipmentScheduleIds(ImmutableSet.of(shipmentScheduleId));

			final boolean lockExists = locks.isLockedBy(userId);
			final boolean expecting_lockExists = tableRow.getAsBoolean("Exists");

			assertThat(lockExists)
					.as("Expecting M_ShipmentSchedule_Lock exists=" + expecting_lockExists + " for " + shipmentScheduleId + "; username: " + username)
					.isEqualTo(expecting_lockExists);
		});
	}
}
