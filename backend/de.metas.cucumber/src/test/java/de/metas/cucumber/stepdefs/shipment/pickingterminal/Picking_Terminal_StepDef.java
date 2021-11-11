/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.cucumber.stepdefs.shipment.pickingterminal;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Picking_Candidate;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.picking.candidate.commands.ProcessPickingCandidatesCommand;
import de.metas.uom.UomId;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;

import java.math.BigDecimal;
import java.util.Map;

import static de.metas.handlingunits.model.X_M_Picking_Candidate.APPROVALSTATUS_Approved;
import static de.metas.handlingunits.model.X_M_Picking_Candidate.PICKSTATUS_Picked;
import static de.metas.handlingunits.model.X_M_Picking_Candidate.STATUS_InProgress;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class Picking_Terminal_StepDef
{
	private final StepDefData<I_M_ShipmentSchedule> shipmentScheduleTable;
	private final StepDefData<I_M_HU> huTable;
	private final PickingCandidateRepository pickingCandidateRepository;

	public Picking_Terminal_StepDef(
			@NonNull final StepDefData<I_M_ShipmentSchedule> shipmentScheduleTable,
			@NonNull final StepDefData<I_M_HU> huTable,
			@NonNull final PickingCandidateRepository pickingCandidateRepository)
	{
		this.shipmentScheduleTable = shipmentScheduleTable;
		this.huTable = huTable;
		this.pickingCandidateRepository = pickingCandidateRepository;
	}

	@And("the following qty is picked")
	public void pickQty(@NonNull final DataTable dataTable)
	{
		final Map<String, String> row = dataTable.asMaps().get(0);

		final String shipmentScheduleIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentSchedule = shipmentScheduleTable.get(shipmentScheduleIdentifier);

		final String huIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_HU.COLUMNNAME_M_HU_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_M_HU hu = huTable.get(huIdentifier);

		final BigDecimal qtyPicked = DataTableUtil.extractBigDecimalForColumnName(row, I_M_Picking_Candidate.COLUMNNAME_QtyPicked);

		final I_M_Picking_Candidate pickingCandidate = InterfaceWrapperHelper.newInstance(I_M_Picking_Candidate.class);
		pickingCandidate.setStatus(STATUS_InProgress);
		pickingCandidate.setPickStatus(PICKSTATUS_Picked);
		pickingCandidate.setApprovalStatus(APPROVALSTATUS_Approved);
		pickingCandidate.setPickFrom_HU_ID(hu.getM_HU_ID());
		pickingCandidate.setQtyPicked(qtyPicked);
		pickingCandidate.setM_ShipmentSchedule_ID(shipmentSchedule.getM_ShipmentSchedule_ID());
		pickingCandidate.setC_UOM_ID(UomId.EACH.getRepoId());
		pickingCandidate.setPackTo_HU_PI_ID(101);

		saveRecord(pickingCandidate);

		final ProcessPickingCandidatesCommand processPickingCandidatesCommand = ProcessPickingCandidatesCommand.builder()
				.pickingCandidateRepository(pickingCandidateRepository)
				.pickingCandidateId(PickingCandidateId.ofRepoId(pickingCandidate.getM_Picking_Candidate_ID()))
				.build();

		processPickingCandidatesCommand.execute();
	}
}
