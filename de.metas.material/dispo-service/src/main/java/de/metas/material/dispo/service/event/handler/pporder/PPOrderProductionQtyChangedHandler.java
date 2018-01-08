package de.metas.material.dispo.service.event.handler.pporder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.Profiles;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.ProductionDetail;
import de.metas.material.dispo.commons.candidate.ProductionDetail.ProductionDetailBuilder;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidatesQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.pporder.PPOrderProductionQtyChangedEvent;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-material-dispo-service
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

@Service
@Profile(Profiles.PROFILE_MaterialDispo)
public class PPOrderProductionQtyChangedHandler implements MaterialEventHandler<PPOrderProductionQtyChangedEvent>
{
	private final CandidateRepositoryRetrieval candidateRepositoryRetrieval;
	private final CandidateChangeService candidateChangeService;

	public PPOrderProductionQtyChangedHandler(
			@NonNull final CandidateRepositoryRetrieval candidateRepositoryRetrieval,
			@NonNull final CandidateChangeService candidateChangeService)
	{
		this.candidateRepositoryRetrieval = candidateRepositoryRetrieval;
		this.candidateChangeService = candidateChangeService;
	}

	@Override
	public Collection<Class<? extends PPOrderProductionQtyChangedEvent>> getHandeledEventType()
	{
		return ImmutableList.of(PPOrderProductionQtyChangedEvent.class);
	}

	@Override
	public void handleEvent(
			@NonNull final PPOrderProductionQtyChangedEvent productionQtyChangedEvent)
	{
		final ProductionDetailBuilder productionDetailBuilder = ProductionDetail.builder()
				.ppOrderId(productionQtyChangedEvent.getPpOrderId());
		if (productionQtyChangedEvent.getPpOrderLineId() > 0)
		{
			productionDetailBuilder.ppOrderLineId(productionQtyChangedEvent.getPpOrderLineId());
		}

		final CandidatesQuery query = CandidatesQuery.builder()
				.productionDetail(productionDetailBuilder.build())
				.build();

		final List<Candidate> updatedCandidatesToPersist = new ArrayList<>();

		final List<Candidate> candidatesToUpdate = candidateRepositoryRetrieval.retrieveOrderedByDateAndSeqNo(query);
		for (final Candidate candidate : candidatesToUpdate)
		{
			final BigDecimal newProductionQty = productionQtyChangedEvent.getNewQuantity();

			final ProductionDetail productionDetailToUpdate = candidate
					.getProductionDetail();

			final ProductionDetail updatedProductionDetail = productionDetailToUpdate.toBuilder()
					.actualQty(newProductionQty)
					.build();

			final BigDecimal newCandidateQty = newProductionQty.max(productionDetailToUpdate.getActualQty());

			final Candidate updatedCandidate = candidate.toBuilder()
					.productionDetail(updatedProductionDetail)
					.materialDescriptor(candidate.getMaterialDescriptor().withQuantity(newCandidateQty))
					.build();

			updatedCandidatesToPersist.add(updatedCandidate);
		}

		updatedCandidatesToPersist
				.forEach(candidate -> candidateChangeService.onCandidateNewOrChange(candidate));
	}
}
