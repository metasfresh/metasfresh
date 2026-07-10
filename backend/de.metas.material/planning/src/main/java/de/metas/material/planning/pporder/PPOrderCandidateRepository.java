/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.material.planning.pporder;

import com.google.common.collect.ImmutableList;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.eevolution.model.I_PP_Order_Candidate;
import org.springframework.stereotype.Repository;

/**
 * Reusable read access to {@link I_PP_Order_Candidate} for planning/disposition decisions. Deliberately kept generic:
 * callers derive whatever they need from the returned records (e.g. the qty already committed to production for a
 * shipment schedule). The write-lifecycle DAO stays in {@code de.metas.manufacturing}
 * ({@code PPOrderCandidateDAO}); this repo only reads, so it is reachable from the dispo layer without pulling in the
 * manufacturing module ({@code I_PP_Order_Candidate} is a base model).
 */
@Repository
public class PPOrderCandidateRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * All active {@link I_PP_Order_Candidate}s bound to the given shipment schedule and product planning
	 * (ordered by id).
	 */
	public ImmutableList<I_PP_Order_Candidate> retrieveActiveByShipmentScheduleAndPlanning(
			final int shipmentScheduleId,
			final int productPlanningId)
	{
		return queryBL.createQueryBuilder(I_PP_Order_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_PP_Order_Candidate.COLUMNNAME_M_ShipmentSchedule_ID, shipmentScheduleId)
				.addEqualsFilter(I_PP_Order_Candidate.COLUMNNAME_PP_Product_Planning_ID, productPlanningId)
				.orderBy(I_PP_Order_Candidate.COLUMNNAME_PP_Order_Candidate_ID)
				.create()
				.listImmutable(I_PP_Order_Candidate.class);
	}
}
