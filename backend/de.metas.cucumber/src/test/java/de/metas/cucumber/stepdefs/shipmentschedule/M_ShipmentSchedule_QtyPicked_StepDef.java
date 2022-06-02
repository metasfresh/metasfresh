/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

import de.metas.common.util.Check;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.hu.M_HU_StepDefData;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_IsAnonymousHuPickedOnTheFly;
import static org.assertj.core.api.Assertions.*;

public class M_ShipmentSchedule_QtyPicked_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO = Services.get(IShipmentScheduleAllocDAO.class);

	private final M_ShipmentSchedule_StepDefData shipmentScheduleTable;
	private final M_HU_StepDefData huTable;

	public M_ShipmentSchedule_QtyPicked_StepDef(
			@NonNull final M_ShipmentSchedule_StepDefData shipmentScheduleTable,
			@NonNull final M_HU_StepDefData huTable)
	{
		this.shipmentScheduleTable = shipmentScheduleTable;
		this.huTable = huTable;
	}

	@And("there are no M_ShipmentSchedule_QtyPicked records created for the following shipment schedules")
	public void validate_no_m_shipmentSchedule_qtyPicked_records(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			validateNoShipmentScheduleQtyPickedRecords(tableRow);
		}
	}

	@And("validate created M_ShipmentSchedule_QtyPicked records")
	public void validate_m_shipmentSchedule_qtyPicked_records(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			validateShipmentScheduleQtyPickedRecord(tableRow);
		}
	}

	private void validateShipmentScheduleQtyPickedRecord(@NonNull final Map<String, String> tableRow)
	{
		final String shipmentScheduleIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID + ".Identifier");
		final I_M_ShipmentSchedule shipmentSchedule = shipmentScheduleTable.get(shipmentScheduleIdentifier);

		final BigDecimal qtyPicked = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_QtyPicked);
		final boolean isProcessed = DataTableUtil.extractBooleanForColumnName(tableRow, I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_Processed);
		final boolean isAnonymousHuPickedOnTheFly = DataTableUtil.extractBooleanForColumnName(tableRow, I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_IsAnonymousHuPickedOnTheFly);

		final List<I_M_ShipmentSchedule_QtyPicked> shipmentScheduleQtyPickedRecords = shipmentScheduleAllocDAO.retrieveAllQtyPickedRecords(shipmentSchedule, I_M_ShipmentSchedule_QtyPicked.class);
		assertThat(shipmentScheduleQtyPickedRecords.size()).isEqualTo(1);

		final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPickedRecord = shipmentScheduleQtyPickedRecords.get(0);
		assertThat(shipmentScheduleQtyPickedRecord.getQtyPicked()).isEqualTo(qtyPicked);
		assertThat(shipmentScheduleQtyPickedRecord.isProcessed()).isEqualTo(isProcessed);
		assertThat(shipmentScheduleQtyPickedRecord.isAnonymousHuPickedOnTheFly()).isEqualTo(isAnonymousHuPickedOnTheFly);
	}

	@And("validate M_ShipmentSchedule_QtyPicked:")
	public void validate_picked_HU(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> table = dataTable.asMaps();
		for (final Map<String, String> row : table)
		{
			final String shipmentScheduleIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_ShipmentSchedule_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_ShipmentSchedule shipmentSchedule = shipmentScheduleTable.get(shipmentScheduleIdentifier);

			final I_M_ShipmentSchedule_QtyPicked ssQtyPicked = queryBL.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class)
					.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_ShipmentSchedule_ID, shipmentSchedule.getM_ShipmentSchedule_ID())
					.create()
					.firstOnly(I_M_ShipmentSchedule_QtyPicked.class);

			assertThat(ssQtyPicked).isNotNull();

			final String huIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_VHU_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(huIdentifier))
			{
				final I_M_HU hu = huTable.get(huIdentifier);
				assertThat(ssQtyPicked.getVHU_ID()).isEqualTo(hu.getM_HU_ID());
			}
			else
			{
				assertThat(ssQtyPicked.getVHU_ID()).isEqualTo(0);
			}

			final boolean isAnonymousHuPickedOnTheFly = DataTableUtil.extractBooleanForColumnNameOr(row, COLUMNNAME_IsAnonymousHuPickedOnTheFly, false);
			assertThat(ssQtyPicked.isAnonymousHuPickedOnTheFly()).isEqualTo(isAnonymousHuPickedOnTheFly);
		}
	}

	private void validateNoShipmentScheduleQtyPickedRecords(@NonNull final Map<String, String> tableRow)
	{
		final String shipmentScheduleIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID + ".Identifier");
		final I_M_ShipmentSchedule shipmentSchedule = shipmentScheduleTable.get(shipmentScheduleIdentifier);

		final List<I_M_ShipmentSchedule_QtyPicked> shipmentScheduleQtyPickedRecords = shipmentScheduleAllocDAO.retrieveAllQtyPickedRecords(shipmentSchedule, I_M_ShipmentSchedule_QtyPicked.class);

		assertThat(shipmentScheduleQtyPickedRecords.size()).isEqualTo(0);
	}
}