/*
 * #%L
 * de.metas.manufacturing
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

package org.eevolution.productioncandidate.service;

import de.metas.material.commons.disposition.SimulatedCleanUpService;
import de.metas.order.OrderLineId;
import lombok.NonNull;
import org.eevolution.productioncandidate.model.dao.DeletePPOrderCandidatesQuery;
import org.eevolution.productioncandidate.model.dao.PPOrderCandidateDAO;
import org.springframework.stereotype.Service;

@Service
public class SimulatedPPOrderCandidateCleanUpService implements SimulatedCleanUpService
{
	private final PPOrderCandidateDAO ppOrderCandidateDAO;

	public SimulatedPPOrderCandidateCleanUpService(@NonNull final PPOrderCandidateDAO ppOrderCandidateDAO)
	{
		this.ppOrderCandidateDAO = ppOrderCandidateDAO;
	}

	@Override
	public void cleanUpSimulated()
	{
		final DeletePPOrderCandidatesQuery deleteQuery = DeletePPOrderCandidatesQuery.builder()
				.onlySimulated(true)
				.build();

		ppOrderCandidateDAO.deletePPOrderCandidates(deleteQuery);
	}

	public void deleteSimulatedPPOrderCandidateFor(final OrderLineId salesOrderLineId)
	{
		final DeletePPOrderCandidatesQuery deleteQuery = DeletePPOrderCandidatesQuery.builder()
				.onlySimulated(true)
				.salesOrderLineId(salesOrderLineId)
				.build();

		ppOrderCandidateDAO.deletePPOrderCandidates(deleteQuery);
	}
}
