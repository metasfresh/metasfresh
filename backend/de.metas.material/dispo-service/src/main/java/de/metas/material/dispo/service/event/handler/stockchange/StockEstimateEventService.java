/*
 * #%L
 * metasfresh-material-dispo-service
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

package de.metas.material.dispo.service.event.handler.stockchange;

import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.DateAndSeqNo;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.MaterialDescriptorQuery;
import de.metas.material.dispo.commons.repository.query.StockChangeDetailQuery;
import de.metas.material.event.stockestimate.AbstractStockEstimateEvent;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

@Service
public class StockEstimateEventService
{
	@NonNull
	private final CandidateRepositoryRetrieval candidateRepositoryRetrieval;

	public StockEstimateEventService(@NonNull final CandidateRepositoryRetrieval candidateRepositoryRetrieval)
	{
		this.candidateRepositoryRetrieval = candidateRepositoryRetrieval;
	}

	@Nullable
	public Candidate retrieveExistingStockEstimateCandidateOrNull(@NonNull final AbstractStockEstimateEvent event)
	{
		final CandidatesQuery query = createCandidatesQuery(event);
		return candidateRepositoryRetrieval.retrieveLatestMatchOrNull(query);
	}

	@Nullable
	public Candidate retrievePreviousStockCandidateOrNull(@NonNull final AbstractStockEstimateEvent event)
	{
		final CandidatesQuery query = createPreviousStockCandidatesQuery(event);
		return candidateRepositoryRetrieval.retrieveLatestMatchOrNull(query);
	}

	@NonNull
	private CandidatesQuery createCandidatesQuery(@NonNull final AbstractStockEstimateEvent event)
	{
		final StockChangeDetailQuery stockChangeDetailQuery = StockChangeDetailQuery.builder()
				.freshQuantityOnHandLineRepoId(event.getFreshQtyOnHandLineId())
				.build();

		return CandidatesQuery.builder()
				.businessCase(CandidateBusinessCase.STOCK_CHANGE)
				.materialDescriptorQuery(MaterialDescriptorQuery.forDescriptor(event.getMaterialDescriptor()))
				.stockChangeDetailQuery(stockChangeDetailQuery)
				.build();
	}

	@NonNull
	private CandidatesQuery createPreviousStockCandidatesQuery(@NonNull final AbstractStockEstimateEvent event)
	{
		final MaterialDescriptorQuery materialDescriptorQuery = MaterialDescriptorQuery.forDescriptor(event.getMaterialDescriptor())
				.toBuilder()
				.timeRangeEnd(DateAndSeqNo.builder()
									  .date(event.getDate())
									  .operator(DateAndSeqNo.Operator.EXCLUSIVE)
									  .build())
				.build();

		return CandidatesQuery.builder()
				.materialDescriptorQuery(materialDescriptorQuery)
				.type(CandidateType.STOCK)
				.matchExactStorageAttributesKey(true)
				.parentId(CandidateId.UNSPECIFIED)
				.build();
	}
}
