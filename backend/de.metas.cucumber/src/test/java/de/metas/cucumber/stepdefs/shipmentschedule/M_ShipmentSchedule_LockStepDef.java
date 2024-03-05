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

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_Lock;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_AD_User;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class M_ShipmentSchedule_LockStepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final M_ShipmentSchedule_StepDefData shipmentScheduleTable;

	public M_ShipmentSchedule_LockStepDef(final M_ShipmentSchedule_StepDefData shipmentScheduleTable)
	{
		this.shipmentScheduleTable = shipmentScheduleTable;
	}

	@Then("validate M_ShipmentSchedule_Lock record for")
	public void validateLockForShipmentScheduleAndUser(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String shipmentScheduleIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID + ".Identifier");
			final I_M_ShipmentSchedule shipmentSchedule = shipmentScheduleTable.get(shipmentScheduleIdentifier);
			final String username = DataTableUtil.extractStringForColumnName(tableRow, I_AD_User.COLUMNNAME_Login);
			final int userId = queryBL.createQueryBuilder(I_AD_User.class)
					.addEqualsFilter(I_AD_User.COLUMNNAME_Login, username)
					.create()
					.firstId();

			final boolean lockExists = queryBL.createQueryBuilder(I_M_ShipmentSchedule_Lock.class)
					.addEqualsFilter(I_M_ShipmentSchedule_Lock.COLUMNNAME_M_ShipmentSchedule_ID, shipmentSchedule.getM_ShipmentSchedule_ID())
					.addEqualsFilter(I_M_ShipmentSchedule_Lock.COLUMNNAME_LockedBy_User_ID, userId)
					.create()
					.anyMatch();

			final boolean expecting_lockExists = DataTableUtil.extractBooleanForColumnName(tableRow, "Exists");

			assertThat(lockExists)
					.as("Expecting M_ShipmentSchedule_Lock exists=" + expecting_lockExists + " for " + shipmentScheduleIdentifier + "; username: " + username)
					.isEqualTo(expecting_lockExists);
		}
	}
}
