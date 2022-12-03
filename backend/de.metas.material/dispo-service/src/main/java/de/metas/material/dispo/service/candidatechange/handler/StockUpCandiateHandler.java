package de.metas.material.dispo.service.candidatechange.handler;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService.SaveResult;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseMultiQuery;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseRepository;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.supplyrequired.SupplyRequiredEvent;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;

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
@Profile(Profiles.PROFILE_MaterialDispo)
public class StockUpCandiateHandler implements CandidateHandler
{

	private final PostMaterialEventService materialEventService;

	private final CandidateRepositoryWriteService candidateRepositoryWriteService;

	private final AvailableToPromiseRepository availableToPromiseRepository;

	public StockUpCandiateHandler(
			@NonNull final CandidateRepositoryWriteService candidateRepositoryWriteService,
			@NonNull final PostMaterialEventService materialEventService,
			@NonNull final AvailableToPromiseRepository availableToPromiseRepository)
	{
		this.availableToPromiseRepository = availableToPromiseRepository;
		this.candidateRepositoryWriteService = candidateRepositoryWriteService;
		this.materialEventService = materialEventService;
	}

	@Override
	public Collection<CandidateType> getHandeledTypes()
	{
		return ImmutableList.of(CandidateType.STOCK_UP);
	}

	@Override
	public Candidate onCandidateNewOrChange(
			@NonNull final Candidate candidate,
			@NonNull final OnNewOrChangeAdvise advise)
	{
		if (!advise.isAttemptUpdate())
		{
			throw new AdempiereException("This handler does not how to deal with isAttemptUpdate=false").appendParametersToMessage()
					.setParameter("handler", candidate)
					.setParameter("candidate", candidate);
		}

		assertCorrectCandidateType(candidate);

		final SaveResult candidateSaveResult = candidateRepositoryWriteService
				.addOrUpdateOverwriteStoredSeqNo(candidate);
		final Candidate candidateWithQtyDeltaAndId = candidateSaveResult.toCandidateWithQtyDelta();

		if (!candidateSaveResult.isQtyChanged() && !candidateSaveResult.isDateChanged())
		{
			return candidateWithQtyDeltaAndId; // this candidate didn't change anything
		}

		final AvailableToPromiseMultiQuery query = AvailableToPromiseMultiQuery.forDescriptorAndAllPossibleBPartnerIds(candidate.getMaterialDescriptor());
		final BigDecimal projectedQty = availableToPromiseRepository.retrieveAvailableStockQtySum(query);

		final BigDecimal requiredAdditionalQty = candidateSaveResult
				.getQtyDelta()
				.subtract(projectedQty);

		if (requiredAdditionalQty.signum() > 0)
		{
			final SupplyRequiredEvent supplyRequiredEvent = SupplyRequiredEventCreator //
					.createSupplyRequiredEvent(candidateWithQtyDeltaAndId, requiredAdditionalQty, null);
			materialEventService.postEventAsync(supplyRequiredEvent);
		}

		return candidateWithQtyDeltaAndId;
	}

	@Override
	public void onCandidateDelete(@NonNull final Candidate candidate)
	{
		assertCorrectCandidateType(candidate);

		candidateRepositoryWriteService.deleteCandidateById(candidate.getId());
	}

	private void assertCorrectCandidateType(@NonNull final Candidate candidate)
	{
		Preconditions.checkArgument(candidate.getType() == CandidateType.STOCK_UP,
				"Given parameter 'candidate' has type=%s; demandCandidate=%s",
				candidate.getType(), candidate);
	}
}
