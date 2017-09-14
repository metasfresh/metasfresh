package de.metas.handlingunits.picking.pickingCandidateCommands;

import java.util.List;

import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;

import de.metas.handlingunits.model.I_M_Picking_Candidate;
import de.metas.handlingunits.model.X_M_Picking_Candidate;
import de.metas.handlingunits.picking.IHUPickingSlotBL;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class SetCandidatesClosed
{
	public void perform(@NonNull final List<Integer> shipmentScheduleIds)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQuery<I_M_Picking_Candidate> query = queryBL.createQueryBuilder(I_M_Picking_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Picking_Candidate.COLUMNNAME_Status, X_M_Picking_Candidate.STATUS_PR)
				.addInArrayFilter(I_M_Picking_Candidate.COLUMN_M_ShipmentSchedule_ID, shipmentScheduleIds)
				.create();

		final ICompositeQueryUpdater<I_M_Picking_Candidate> updater = queryBL.createCompositeQueryUpdater(I_M_Picking_Candidate.class)
				.addSetColumnValue(I_M_Picking_Candidate.COLUMNNAME_Status, X_M_Picking_Candidate.STATUS_CL);
		query.updateDirectly(updater);
		// note that we only closed "processed" candidates. what's still open shall stay open.

		final List<I_M_Picking_Candidate> pickingCandidates = query.list();
		for (final I_M_Picking_Candidate pickingCandidate : pickingCandidates)
		{
			final IHUPickingSlotBL huPickingSlotBL = Services.get(IHUPickingSlotBL.class);
			huPickingSlotBL.addToPickingSlotQueue(pickingCandidate.getM_PickingSlot(), pickingCandidate.getM_HU());
		}
	}
}
