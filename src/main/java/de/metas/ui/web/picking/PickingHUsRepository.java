package de.metas.ui.web.picking;

import java.util.List;
import java.util.Map;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ListMultimap;

import de.metas.picking.model.I_M_PickingSlot;
import de.metas.picking.model.I_M_Picking_Candidate;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorRowAttributesProvider;
import de.metas.ui.web.handlingunits.HUEditorViewRepository;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Component
/* package */class PickingHUsRepository
{
	private final HUEditorViewRepository huEditorRepo;

	public PickingHUsRepository()
	{
		huEditorRepo = HUEditorViewRepository.builder()
				.windowId(PickingConstants.WINDOWID_PickingSlotView)
				.referencingTableName(I_M_PickingSlot.Table_Name)
				.attributesProvider(HUEditorRowAttributesProvider.builder().readonly(true).build())
				.build();
	}

	public ListMultimap<Integer, HUEditorRow> retrieveHUsIndexedByPickingSlotId(final int shipmentScheduleId)
	{
		final Map<Integer, Integer> huId2pickingSlotId = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Picking_Candidate.class)
				.addEqualsFilter(I_M_Picking_Candidate.COLUMN_M_ShipmentSchedule_ID, shipmentScheduleId)
				.create()
				.stream(I_M_Picking_Candidate.class)
				.collect(ImmutableMap.toImmutableMap(I_M_Picking_Candidate::getM_HU_ID, I_M_Picking_Candidate::getM_PickingSlot_ID));

		final List<HUEditorRow> huRows = huEditorRepo.retrieveHUEditorRows(huId2pickingSlotId.keySet());

		final ListMultimap<Integer, HUEditorRow> huRowsByPickingSlotId = huRows.stream()
				.map(huRow -> GuavaCollectors.entry(huId2pickingSlotId.get(huRow.getM_HU_ID()), huRow))
				.collect(GuavaCollectors.toImmutableListMultimap());

		return huRowsByPickingSlotId;
	}

	public void addHUToPickingSlot(final int huId, final int pickingSlotId, final int shipmentScheduleId)
	{
		final I_M_Picking_Candidate pickingCandidatePO = InterfaceWrapperHelper.newInstance(I_M_Picking_Candidate.class);
		pickingCandidatePO.setM_ShipmentSchedule_ID(shipmentScheduleId);
		pickingCandidatePO.setM_PickingSlot_ID(pickingSlotId);
		pickingCandidatePO.setM_HU_ID(huId);
		InterfaceWrapperHelper.save(pickingCandidatePO);
	}

	public I_M_Picking_Candidate getCreateCandidate(final int huId, final int pickingSlotId, final int shipmentScheduleId)
	{
		I_M_Picking_Candidate pickingCandidatePO = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Picking_Candidate.class)
				.addEqualsFilter(I_M_Picking_Candidate.COLUMN_M_PickingSlot_ID, pickingSlotId)
				.addEqualsFilter(I_M_Picking_Candidate.COLUMNNAME_M_HU_ID, huId)
				.addEqualsFilter(I_M_Picking_Candidate.COLUMNNAME_M_ShipmentSchedule_ID, shipmentScheduleId)
				.create()
				.firstOnly(I_M_Picking_Candidate.class);
		if (pickingCandidatePO == null)
		{
			pickingCandidatePO = InterfaceWrapperHelper.newInstance(I_M_Picking_Candidate.class);
			pickingCandidatePO.setM_ShipmentSchedule_ID(shipmentScheduleId);
			pickingCandidatePO.setM_PickingSlot_ID(pickingSlotId);
			pickingCandidatePO.setM_HU_ID(huId);
			InterfaceWrapperHelper.save(pickingCandidatePO);
		}

		return pickingCandidatePO;
	}

	public void removeHUFromPickingSlot(final int huId, final int pickingSlotId)
	{
		Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Picking_Candidate.class)
				.addEqualsFilter(I_M_Picking_Candidate.COLUMN_M_PickingSlot_ID, pickingSlotId)
				.addEqualsFilter(I_M_Picking_Candidate.COLUMNNAME_M_HU_ID, huId)
				.create()
				.delete();
	}
}
