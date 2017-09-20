package de.metas.handlingunits.picking;

import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import de.metas.handlingunits.model.I_M_Picking_Candidate;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;

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

/**
 * Dedicated DAO'ish class centered around {@link I_M_Picking_Candidate}s
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Service
public class PickingCandidateRepository
{
	private static final Logger logger = LogManager.getLogger(PickingCandidateRepository.class);
	
	public List<I_M_ShipmentSchedule> retrieveShipmentSchedulesViaPickingCandidates(final int huId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final List<I_M_ShipmentSchedule> scheds = queryBL.createQueryBuilder(I_M_Picking_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Picking_Candidate.COLUMN_M_HU_ID, huId)
				.andCollect(I_M_Picking_Candidate.COLUMN_M_ShipmentSchedule_ID)
				.create()
				.list();
		return scheds;
	}

	public List<I_M_Picking_Candidate> retrievePickingCandidates(final int huId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final List<I_M_Picking_Candidate> result = queryBL.createQueryBuilder(I_M_Picking_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Picking_Candidate.COLUMN_M_HU_ID, huId)
				
				.create()
				.list();
		return result;
	}
	
	public I_M_Picking_Candidate getCreateCandidate(final int huId, final int pickingSlotId, final int shipmentScheduleId)
	{
		I_M_Picking_Candidate pickingCandidate = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Picking_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Picking_Candidate.COLUMN_M_PickingSlot_ID, pickingSlotId)
				.addEqualsFilter(I_M_Picking_Candidate.COLUMNNAME_M_HU_ID, huId)
				.addEqualsFilter(I_M_Picking_Candidate.COLUMNNAME_M_ShipmentSchedule_ID, shipmentScheduleId)
				.create()
				.firstOnly(I_M_Picking_Candidate.class);

		if (pickingCandidate == null)
		{
			pickingCandidate = InterfaceWrapperHelper.newInstance(I_M_Picking_Candidate.class);
			pickingCandidate.setM_ShipmentSchedule_ID(shipmentScheduleId);
			pickingCandidate.setM_PickingSlot_ID(pickingSlotId);
			pickingCandidate.setM_HU_ID(huId);
			pickingCandidate.setQtyPicked(BigDecimal.ZERO); // will be updated later
			pickingCandidate.setC_UOM(null); // will be updated later
			save(pickingCandidate);

			logger.info("Created new M_Picking_Candidate for M_HU_ID={}, M_PickingSlot_ID={}, M_ShipmentSchedule_ID={}; candidate={}",
					huId, pickingSlotId, shipmentScheduleId, pickingCandidate);
		}

		return pickingCandidate;
	}
	
	public void deletePickingCandidate(final int huId)
	{
		Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Picking_Candidate.class)

				.addEqualsFilter(I_M_Picking_Candidate.COLUMNNAME_M_HU_ID, huId)
				.create()
				.delete();
	}
}
