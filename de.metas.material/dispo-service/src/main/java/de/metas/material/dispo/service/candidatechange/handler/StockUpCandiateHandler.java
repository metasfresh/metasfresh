package de.metas.material.dispo.service.candidatechange.handler;

import java.math.BigDecimal;
import java.util.Collection;

import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryCommands;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.event.MaterialEventService;
import de.metas.material.event.demandWasFound.SupplyRequiredEvent;
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
 * This handler might create a {@link SupplyRequiredEvent}, but does not decrease the protected stock quantity.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Service
public class StockUpCandiateHandler implements CandidateHandler
{
	@NonNull
	private final CandidateRepositoryRetrieval candidateRepository;

	private final MaterialEventService materialEventService;

	private final CandidateRepositoryCommands candidateRepositoryCommands;

	public StockUpCandiateHandler(
			@NonNull final CandidateRepositoryRetrieval candidateRepository,
			@NonNull final CandidateRepositoryCommands candidateRepositoryCommands,
			@NonNull final MaterialEventService materialEventService)
	{
		this.candidateRepositoryCommands = candidateRepositoryCommands;
		this.candidateRepository = candidateRepository;
		this.materialEventService = materialEventService;
	}

	@Override
	public Collection<CandidateType> getHandeledTypes()
	{
		return ImmutableList.of(CandidateType.STOCK_UP);
	}

	@Override
	public Candidate onCandidateNewOrChange(@NonNull final Candidate candidate)
	{
		Preconditions.checkArgument(candidate.getType() == CandidateType.STOCK_UP,
				"Given parameter 'candidate' has type=%s; demandCandidate=%s",
				candidate.getType(), candidate);

		final Candidate candidateWithQtyDeltaAndId = candidateRepositoryCommands.addOrUpdateOverwriteStoredSeqNo(
				candidate);

		if (candidateWithQtyDeltaAndId.getQuantity().signum() == 0)
		{
			return candidateWithQtyDeltaAndId; // this candidate didn't change anything
		}

		final BigDecimal projectedQty = candidateRepository //
				.retrieveAvailableStock(candidate.getMaterialDescriptor());

		final BigDecimal requiredAdditionalQty = candidateWithQtyDeltaAndId
				.getQuantity()
				.subtract(projectedQty);

		if (requiredAdditionalQty.signum() > 0)
		{
			final SupplyRequiredEvent materialDemandEvent = SupplyRequiredEventCreator //
					.createMaterialDemandEvent(candidateWithQtyDeltaAndId, requiredAdditionalQty);
			materialEventService.fireEvent(materialDemandEvent);
		}

		return candidateWithQtyDeltaAndId;
	}
}
