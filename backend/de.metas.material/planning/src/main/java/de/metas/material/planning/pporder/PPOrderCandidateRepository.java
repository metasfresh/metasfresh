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

import de.metas.inout.ShipmentScheduleId;
import de.metas.material.planning.ProductPlanningId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.eevolution.model.I_PP_Order_Candidate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Read access to {@link I_PP_Order_Candidate} for planning/disposition decisions. The write-lifecycle DAO stays in
 * {@code de.metas.manufacturing} ({@code PPOrderCandidateDAO}); this repo only reads, so it is reachable from the
 * dispo layer without pulling in the manufacturing module ({@code I_PP_Order_Candidate} is a base model).
 *
 * Repository Tables: PP_Order_Candidate
 * Repository Cluster: PPOrderCandidateDAO, PPOrderCandidateRepository
 */
@Repository
public class PPOrderCandidateRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Qty already committed to production for the given shipment schedule + planning: the summed {@code QtyEntered}
	 * of the active immovable ({@code Processed} or {@code IsClosed}) candidates. Empty when there is none.
	 */
	public Optional<Quantity> retrieveProcessedQtyByShipmentScheduleAndPlanning(
			@NonNull final ShipmentScheduleId shipmentScheduleId,
			@NonNull final ProductPlanningId productPlanningId)
	{
		return queryBL.createQueryBuilder(I_PP_Order_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_PP_Order_Candidate.COLUMNNAME_M_ShipmentSchedule_ID, shipmentScheduleId)
				.addEqualsFilter(I_PP_Order_Candidate.COLUMNNAME_PP_Product_Planning_ID, productPlanningId)
				.orderBy(I_PP_Order_Candidate.COLUMNNAME_PP_Order_Candidate_ID)
				.create()
				.stream()
				.filter(candidate -> candidate.isProcessed() || candidate.isClosed())
				.map(candidate -> Quantitys.of(candidate.getQtyEntered(), UomId.ofRepoId(candidate.getC_UOM_ID())))
				.reduce(Quantity::add);
	}
}
