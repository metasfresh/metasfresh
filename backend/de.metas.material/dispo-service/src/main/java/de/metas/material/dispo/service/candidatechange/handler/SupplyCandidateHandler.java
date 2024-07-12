package de.metas.material.dispo.service.candidatechange.handler;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService.DeleteResult;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService.SaveResult;
import de.metas.material.dispo.commons.repository.DateAndSeqNo;
import de.metas.material.dispo.service.candidatechange.StockCandidateService;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;

import static java.math.BigDecimal.ZERO;

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
public class SupplyCandidateHandler implements CandidateHandler
{
	private final CandidateRepositoryWriteService candidateRepositoryWriteService;
	private final StockCandidateService stockCandidateService;

	public SupplyCandidateHandler(
			@NonNull final CandidateRepositoryWriteService candidateRepositoryWriteService,
			@NonNull final StockCandidateService stockCandidateService)
	{
		this.candidateRepositoryWriteService = candidateRepositoryWriteService;
		this.stockCandidateService = stockCandidateService;
	}

	@Override
	public Collection<CandidateType> getHandeledTypes()
	{
		return ImmutableList.of(
				CandidateType.SUPPLY,
				CandidateType.UNEXPECTED_INCREASE,
				CandidateType.INVENTORY_UP,
				CandidateType.ATTRIBUTES_CHANGED_TO);
	}

	/**
	 * Call this one if the system was notified about a new or changed supply candidate.
	 * <p>
	 * Creates a new stock candidate or retrieves and updates an existing one.<br>
	 * The stock candidate is made the <i>parent</i> of the supplyCandidate.<br>
	 * When creating a new candidate, then compute its qty by getting the qty from that stockCandidate that has the same product and locator and is "before" it and add the supply candidate's qty
	 */
	@Override
	public Candidate onCandidateNewOrChange(
			@NonNull final Candidate candidate,
			@NonNull final OnNewOrChangeAdvise advise)
	{
		assertCorrectCandidateType(candidate);

		// store the supply candidate and get both its ID and qty-delta
		// TODO 3034 test: if we add a supplyCandidate that has an unspecified parent-id and and in DB there is an MD_Candidate with parentId > 0,
		// then supplyCandidateDeltaWithId needs to have that parentId
		final SaveResult candidateSaveResult;
		if (advise.isAttemptUpdate())
		{
			candidateSaveResult = candidateRepositoryWriteService.addOrUpdateOverwriteStoredSeqNo(candidate);
		}
		else
		{
			candidateSaveResult = candidateRepositoryWriteService.add(candidate);
		}

		if (!candidateSaveResult.isDateChanged() && !candidateSaveResult.isQtyChanged())
		{
			return candidateSaveResult.toCandidateWithQtyDelta(); // nothing more to do, because the candidate didn't change any ATP quantity.
		}

		final Candidate savedCandidate = candidateSaveResult.getCandidate();

		final SaveResult stockCandidate = stockCandidateService
				.createStockCandidate(savedCandidate)
				.withCandidateId(savedCandidate.getParentId());

		final Candidate savedStockCandidate = candidateRepositoryWriteService
				.addOrUpdateOverwriteStoredSeqNo(stockCandidate.getCandidate())
				.getCandidate();

		final SaveResult deltaToApplyToLaterStockCandidates = SaveResult.builder()
				.candidate(savedCandidate)
				.previousQty(candidateSaveResult.getPreviousQty())
				.previousTime(candidateSaveResult.getPreviousTime())
				.build();

		stockCandidateService.applyDeltaToMatchingLaterStockCandidates(deltaToApplyToLaterStockCandidates);

		// set the stock candidate as parent for the supply candidate
		candidateRepositoryWriteService.updateCandidateById(
				savedCandidate
						.withParentId(savedStockCandidate.getId()));

		return candidateSaveResult
				.toCandidateWithQtyDelta()
				.withParentId(savedStockCandidate.getId());
	}

	@Override
	public void onCandidateDelete(@NonNull final Candidate candidate)
	{
		assertCorrectCandidateType(candidate);

		final DeleteResult stockDeleteResult = candidateRepositoryWriteService.deleteCandidateById(candidate.getId());

		final DateAndSeqNo timeOfDeletedStock = stockDeleteResult.getPreviousTime();

		final BigDecimal previousQty = candidate.getQuantity();

		final SaveResult applyDeltaRequest = SaveResult.builder()
				.candidate(candidate
						.withQuantity(ZERO)
						.withDate(timeOfDeletedStock.getDate())
						.withSeqNo(timeOfDeletedStock.getSeqNo()))
				.previousQty(previousQty)
				.build();
		stockCandidateService.applyDeltaToMatchingLaterStockCandidates(applyDeltaRequest);
	}

	private void assertCorrectCandidateType(@NonNull final Candidate supplyCandidate)
	{
		Preconditions.checkArgument(
				getHandeledTypes().contains(supplyCandidate.getType()),
				"Given parameter 'supplyCandidate' has type=%s; supplyCandidate=%s",
				supplyCandidate.getType(), supplyCandidate);
	}

}
