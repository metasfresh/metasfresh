package de.metas.handlingunits.picking;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;
import org.springframework.stereotype.Service;

import de.metas.handlingunits.model.I_M_Picking_Candidate;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

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
}
