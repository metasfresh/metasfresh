/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.hu.M_HU_PI_Item_Product_StepDefData;
import de.metas.cucumber.stepdefs.hu.M_HU_PI_StepDefData;
import de.metas.cucumber.stepdefs.hu.M_HU_StepDefData;
import de.metas.cucumber.stepdefs.shipmentschedule.M_ShipmentSchedule_StepDefData;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_Picking_Candidate;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.picking.IHUPickingSlotBL;
import de.metas.handlingunits.picking.PackToSpec;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.candidate.commands.ProcessPickingCandidatesCommand;
import de.metas.handlingunits.picking.candidate.commands.ProcessPickingCandidatesRequest;
import de.metas.inout.ShipmentScheduleId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.Assertions;
import org.compiere.SpringContextHolder;
import org.testcontainers.shaded.com.google.common.collect.ImmutableSet;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.handlingunits.model.I_M_Picking_Candidate.COLUMNNAME_M_HU_ID;
import static de.metas.handlingunits.model.I_M_Picking_Candidate.COLUMNNAME_M_ShipmentSchedule_ID;
import static de.metas.handlingunits.model.X_M_Picking_Candidate.APPROVALSTATUS_Approved;
import static de.metas.handlingunits.model.X_M_Picking_Candidate.PICKSTATUS_Picked;
import static de.metas.handlingunits.model.X_M_Picking_Candidate.STATUS_InProgress;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Picking_Terminal_StepDef
{
	private final M_ShipmentSchedule_StepDefData shipmentScheduleTable;
	private final M_HU_StepDefData huTable;
	private final M_HU_PI_StepDefData huPiTable;
	private final M_HU_PI_Item_Product_StepDefData huPiProductTable;

	private final PickingCandidateRepository pickingCandidateRepository = SpringContextHolder.instance.getBean(PickingCandidateRepository.class);
	private final PickingCandidateService pickingCandidateService = SpringContextHolder.instance.getBean(PickingCandidateService.class);
	private final IHUPickingSlotBL huPickingSlotBL = Services.get(IHUPickingSlotBL.class);
	private final InventoryService inventoryService = SpringContextHolder.instance.getBean(InventoryService.class);

	public Picking_Terminal_StepDef(
			@NonNull final M_ShipmentSchedule_StepDefData shipmentScheduleTable,
			@NonNull final M_HU_StepDefData huTable,
			@NonNull final M_HU_PI_StepDefData huPiTable,
			@NonNull final M_HU_PI_Item_Product_StepDefData huPiProductTable)
	{
		this.shipmentScheduleTable = shipmentScheduleTable;
		this.huTable = huTable;
		this.huPiTable = huPiTable;
		this.huPiProductTable = huPiProductTable;
	}

	@And("the following qty is picked")
	public void pickQty(@NonNull final DataTable dataTable)
	{
		final Map<String, String> row = dataTable.asMaps().get(0);

		final String shipmentScheduleIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID + "." + TABLECOLUMN_IDENTIFIER);
		final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentSchedule = shipmentScheduleTable.get(shipmentScheduleIdentifier);

		final String huIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_HU.COLUMNNAME_M_HU_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_HU hu = huTable.get(huIdentifier);

		final BigDecimal qtyPicked = DataTableUtil.extractBigDecimalForColumnName(row, I_M_Picking_Candidate.COLUMNNAME_QtyPicked);

		final HuPackingInstructionsId huPackingInstructionsId = Optional.ofNullable(DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_Picking_Candidate.COLUMNNAME_PackTo_HU_PI_ID + "." + TABLECOLUMN_IDENTIFIER))
				.map(huPiTable::get)
				.map(I_M_HU_PI::getM_HU_PI_ID)
				.map(HuPackingInstructionsId::ofRepoId)
				.orElse(HuPackingInstructionsId.VIRTUAL);

		final HUPIItemProductId huPiItemProductId = Optional.ofNullable(DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_Picking_Candidate.COLUMNNAME_PackTo_HU_PI_Item_Product_ID + "." + TABLECOLUMN_IDENTIFIER))
				.map(huPiProductTable::get)
				.map(I_M_HU_PI_Item_Product::getM_HU_PI_Item_Product_ID)
				.map(HUPIItemProductId::ofRepoId)
				.orElse(null);

		final PackToSpec packToSpec = huPiItemProductId != null ?
				PackToSpec.ofTUPackingInstructionsId(huPiItemProductId)
				: PackToSpec.ofGenericPackingInstructionsId(huPackingInstructionsId);

		final I_M_Picking_Candidate pickingCandidate = InterfaceWrapperHelper.newInstance(I_M_Picking_Candidate.class);
		pickingCandidate.setStatus(STATUS_InProgress);
		pickingCandidate.setPickStatus(PICKSTATUS_Picked);
		pickingCandidate.setApprovalStatus(APPROVALSTATUS_Approved);
		pickingCandidate.setPickFrom_HU_ID(hu.getM_HU_ID());
		pickingCandidate.setQtyPicked(qtyPicked);
		pickingCandidate.setM_ShipmentSchedule_ID(shipmentSchedule.getM_ShipmentSchedule_ID());
		pickingCandidate.setC_UOM_ID(UomId.EACH.getRepoId());
		pickingCandidate.setPackTo_HU_PI_ID(HuPackingInstructionsId.toRepoId(packToSpec.getGenericPackingInstructionsId()));
		pickingCandidate.setPackTo_HU_PI_Item_Product_ID(HUPIItemProductId.toRepoId(packToSpec.getTuPackingInstructionsId()));


		saveRecord(pickingCandidate);

		final ProcessPickingCandidatesCommand processPickingCandidatesCommand = ProcessPickingCandidatesCommand.builder()
				.pickingCandidateRepository(pickingCandidateRepository)
				.inventoryService(inventoryService)
				.request(ProcessPickingCandidatesRequest.builder()
						.pickingCandidateId(PickingCandidateId.ofRepoId(pickingCandidate.getM_Picking_Candidate_ID()))
						.build())
				.build();

		processPickingCandidatesCommand.execute();
	}

	@And("create M_PickingCandidate for M_HU")
	public void create_M_PickingCand_for_M_HU(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			final String shipmentScheduleIdentifier = DataTableUtil.extractStringForColumnName(row, de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID + "." + TABLECOLUMN_IDENTIFIER);
			final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentSchedule = shipmentScheduleTable.get(shipmentScheduleIdentifier);

			final String huIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_HU.COLUMNNAME_M_HU_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_HU hu = huTable.get(huIdentifier);

			final BigDecimal qtyPicked = DataTableUtil.extractBigDecimalForColumnName(row, I_M_Picking_Candidate.COLUMNNAME_QtyPicked);
			final String status = DataTableUtil.extractStringForColumnName(row, I_M_Picking_Candidate.COLUMNNAME_Status);
			final String pickStatus = DataTableUtil.extractStringForColumnName(row, I_M_Picking_Candidate.COLUMNNAME_PickStatus);
			final String approvalStatus = DataTableUtil.extractStringForColumnName(row, I_M_Picking_Candidate.COLUMNNAME_ApprovalStatus);

			final I_M_Picking_Candidate pickingCandidate = InterfaceWrapperHelper.newInstance(I_M_Picking_Candidate.class);
			pickingCandidate.setStatus(status);
			pickingCandidate.setPickStatus(pickStatus);
			pickingCandidate.setApprovalStatus(approvalStatus);
			pickingCandidate.setPickFrom_HU_ID(hu.getM_HU_ID());
			pickingCandidate.setQtyPicked(qtyPicked);
			pickingCandidate.setM_ShipmentSchedule_ID(shipmentSchedule.getM_ShipmentSchedule_ID());
			pickingCandidate.setC_UOM_ID(UomId.EACH.getRepoId());

			saveRecord(pickingCandidate);
		}
	}

	@And("process picking")
	public void process_picking(@NonNull final DataTable dataTable) throws Exception
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			final String huIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_HU_ID + "." + TABLECOLUMN_IDENTIFIER);
			final String shipmentScheduleIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_ShipmentSchedule_ID + "." + TABLECOLUMN_IDENTIFIER);

			final I_M_HU hu = huTable.get(huIdentifier);
			assertThat(hu).isNotNull();

			final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentSchedule = shipmentScheduleTable.get(shipmentScheduleIdentifier);
			assertThat(shipmentSchedule).isNotNull();

			final String errorMessage = DataTableUtil.extractStringOrNullForColumnName(row, "OPT.ErrorMessage");

			try
			{
				pickingCandidateService.processForHUIds(ImmutableSet.of(HuId.ofRepoId(hu.getM_HU_ID())), ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID()));

				Assertions.assertThat(errorMessage).as("ErrorMessage should be null if pickingCandidateService.processForHUIds() finished with no error!").isNull();
			}
			catch (final Exception e)
			{
				StepDefUtil.validateErrorMessage(e, errorMessage);
			}
		}
	}

	@And("^validate M_HUs are available to pick for shipmentSchedule identified by (.*)$")
	public void validate_M_HUs_available_to_pick_for_SS(@NonNull final String shipmentScheduleIdentifier, @NonNull final DataTable table)
	{
		final I_M_ShipmentSchedule shipmentSchedule = InterfaceWrapperHelper.create(shipmentScheduleTable.get(shipmentScheduleIdentifier), I_M_ShipmentSchedule.class);
		assertThat(shipmentSchedule).isNotNull();

		final IHUPickingSlotBL.PickingHUsQuery query = IHUPickingSlotBL.PickingHUsQuery.builder()
				.onlyTopLevelHUs(false)
				.onlyIfAttributesMatchWithShipmentSchedules(true)
				.shipmentSchedule(shipmentSchedule)
				.build();

		final List<I_M_HU> availableHUsToPick = huPickingSlotBL.retrieveAvailableHUsToPick(query);
		assertThat(availableHUsToPick).isNotNull();

		final List<Map<String, String>> rows = table.asMaps();
		assertThat(availableHUsToPick.size()).isEqualTo(rows.size());

		for (int huIndex = 0; huIndex < availableHUsToPick.size(); huIndex++)
		{
			final I_M_HU actualHU = availableHUsToPick.get(huIndex);
			final Map<String, String> expectedHU = rows.get(huIndex);

			final String huIdentifier = DataTableUtil.extractStringForColumnName(expectedHU, I_M_HU.COLUMNNAME_M_HU_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_HU hu = huTable.get(huIdentifier);
			assertThat(hu).isNotNull();

			assertThat(actualHU.getM_HU_ID()).isEqualTo(hu.getM_HU_ID());
		}
	}

	@And("^validate that there are no M_HUs available to pick for shipmentSchedule identified by (.*)$")
	public void validate_no_M_HUs_available_to_pick_for_ShipmentSched(@NonNull final String shipmentScheduleIdentifier)
	{
		final I_M_ShipmentSchedule shipmentSchedule = InterfaceWrapperHelper.create(shipmentScheduleTable.get(shipmentScheduleIdentifier), I_M_ShipmentSchedule.class);
		assertThat(shipmentSchedule).isNotNull();

		final IHUPickingSlotBL.PickingHUsQuery query = IHUPickingSlotBL.PickingHUsQuery.builder()
				.onlyTopLevelHUs(false)
				.onlyIfAttributesMatchWithShipmentSchedules(true)
				.shipmentSchedule(shipmentSchedule)
				.build();

		final List<I_M_HU> availableHUsToPick = huPickingSlotBL.retrieveAvailableHUsToPick(query);
		assertThat(availableHUsToPick.size()).isEqualTo(0);
	}
}
