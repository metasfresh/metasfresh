package de.metas.material.dispo.service.candidatechange.handler;

import java.math.BigDecimal;
import java.util.Collection;

import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import de.metas.material.dispo.CandidateRepository;
import de.metas.material.dispo.CandidateSpecification.Type;
import de.metas.material.dispo.CandidatesQuery.DateOperator;
import de.metas.material.dispo.candidate.Candidate;
import de.metas.material.event.MaterialDemandEvent;
import de.metas.material.event.MaterialEventService;
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

/**
 * This handler might create a {@link MaterialDemandEvent}, but does not decrease the protected stock quantity.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Service
public class StockUpCandiateHandler implements CandidateHandler
{
	@NonNull
	private final CandidateRepository candidateRepository;

	@NonNull
	private final MaterialEventService materialEventService;

	public StockUpCandiateHandler(
			@NonNull final CandidateRepository candidateRepository,
			@NonNull final MaterialEventService materialEventService)
	{
		this.candidateRepository = candidateRepository;
		this.materialEventService = materialEventService;
	}

	@Override
	public Collection<Type> getHandeledTypes()
	{
		return ImmutableList.of(Type.STOCK_UP);
	}

	public Candidate onCandidateNewOrChange(@NonNull final Candidate candidate)
	{
		Preconditions.checkArgument(candidate.getType() == Type.STOCK_UP,
				"Given parameter 'candidate' has type=%s; demandCandidate=%s",
				candidate.getType(), candidate);

		final Candidate candidateWithQtyDeltaAndId = candidateRepository.addOrUpdateOverwriteStoredSeqNo(candidate);

		if (candidateWithQtyDeltaAndId.getQuantity().signum() == 0)
		{
			return candidateWithQtyDeltaAndId; // this candidate didn't change anything
		}

		final Candidate projectedStockOrNull = candidateRepository.retrieveLatestMatchOrNull(candidate
				.mkSegmentBuilder()
				.dateOperator(DateOperator.UNTIL)
				.type(Type.STOCK)
				.build());
		final BigDecimal projectedQty = projectedStockOrNull != null ? projectedStockOrNull.getQuantity() : BigDecimal.ZERO;

		final BigDecimal requiredAdditionalQty = candidateWithQtyDeltaAndId.getQuantity().subtract(projectedQty);
		if (requiredAdditionalQty.signum() > 0)
		{
			final MaterialDemandEvent materialDemandEvent = MaterialDemandEventCreator.createMaterialDemandEvent(candidateWithQtyDeltaAndId, requiredAdditionalQty);
			materialEventService.fireEvent(materialDemandEvent);
		}

		return candidateWithQtyDeltaAndId;
	}
}
