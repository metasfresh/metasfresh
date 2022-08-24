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

package de.metas.cucumber.stepdefs.picking;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.shipmentschedule.M_ShipmentSchedule_StepDefData;
import de.metas.handlingunits.model.I_M_Picking_Candidate;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static de.metas.handlingunits.model.I_M_Picking_Candidate.COLUMNNAME_QtyPicked;
import static org.assertj.core.api.Assertions.*;

public class M_Picking_Candidate_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final M_ShipmentSchedule_StepDefData shipmentScheduleTable;

	public M_Picking_Candidate_StepDef(@NonNull final M_ShipmentSchedule_StepDefData shipmentScheduleTable)
	{
		this.shipmentScheduleTable = shipmentScheduleTable;
	}

	@And("validate picking candidate for shipment schedule:")
	public void validate_picking_candidate(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String shipmentScheduleIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID + ".Identifier");
			final I_M_ShipmentSchedule shipmentSchedule = shipmentScheduleTable.get(shipmentScheduleIdentifier);

			final BigDecimal qtyPicked = DataTableUtil.extractBigDecimalForColumnName(tableRow, COLUMNNAME_QtyPicked);

			final String pickStatus = DataTableUtil.extractStringForColumnName(tableRow, I_M_Picking_Candidate.COLUMNNAME_PickStatus);

			final I_M_Picking_Candidate pickingCandidate = queryBL.createQueryBuilder(I_M_Picking_Candidate.class)
					.addEqualsFilter(I_M_Picking_Candidate.COLUMNNAME_M_ShipmentSchedule_ID, shipmentSchedule.getM_ShipmentSchedule_ID())
					.create()
					.firstOnly(I_M_Picking_Candidate.class);

			assertThat(pickingCandidate).isNotNull();
			assertThat(pickingCandidate.getQtyPicked()).isEqualTo(qtyPicked);
			assertThat(pickingCandidate.getPickStatus()).isEqualTo(pickStatus);
		}

	}

}
