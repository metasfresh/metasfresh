package de.metas.material.dispo.service.candidatechange.handler;

import java.math.BigDecimal;
import java.util.Optional;

import com.google.common.base.Preconditions;

import de.metas.material.dispo.Candidate;
import de.metas.material.dispo.Candidate.Type;
import de.metas.material.dispo.CandidateRepository;
import de.metas.material.dispo.CandidatesSegment.DateOperator;
import de.metas.material.event.MaterialDemandEvent;
import de.metas.material.event.MaterialEventService;
import lombok.Builder;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Builder
public class StockUpCandiateCangeHandler
{
	@NonNull
	private final CandidateRepository candidateRepository;
	
	@NonNull
	private final MaterialEventService materialEventService;
	
	public Candidate onStockUpCandidateNewOrChange(@NonNull final Candidate candidate)
	{
		Preconditions.checkArgument(candidate.getType() == Type.STOCK_UP, "Given parameter 'candidate' has type=%s; demandCandidate=%s", candidate.getType(), candidate);

		final Candidate candidateWithQtyDelta = candidateRepository.addOrUpdateOverwriteStoredSeqNo(candidate);

		if (candidateWithQtyDelta.getQuantity().signum() == 0)
		{
			return candidateWithQtyDelta; // this candidate didn't change anything
		}

		final Optional<Candidate> projectedStock = candidateRepository.retrieveLatestMatch(candidate
				.mkSegmentBuilder()
				.dateOperator(DateOperator.until)
				.type(Type.STOCK)
				.build());
		final BigDecimal projectedQty = projectedStock.isPresent() ? projectedStock.get().getQuantity() : BigDecimal.ZERO;

		final BigDecimal requiredAdditionalQty = candidateWithQtyDelta.getQuantity().subtract(projectedQty);
		if (requiredAdditionalQty.signum() > 0)
		{
			final MaterialDemandEvent materialDemandEvent = MaterialDemandEventCreator.createMaterialDemandEvent(candidate, requiredAdditionalQty);
			materialEventService.fireEvent(materialDemandEvent);
		}

		return candidateWithQtyDelta;
	}
}
