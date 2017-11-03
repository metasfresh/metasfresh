package de.metas.material.dispo.service.candidatechange.handler;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import de.metas.material.dispo.commons.CandidatesQuery;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryCommands;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.service.candidatechange.StockCandidateService;
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
public class SupplyCandiateHandler implements CandidateHandler
{
	private final CandidateRepositoryCommands candidateRepositoryCommands;

	private final StockCandidateService stockCandidateService;

	private final CandidateRepositoryRetrieval candidateRepository;

	public SupplyCandiateHandler(
			@NonNull final CandidateRepositoryRetrieval candidateRepositoryRetrieval,
			@NonNull final CandidateRepositoryCommands candidateRepository,
			@NonNull final StockCandidateService stockCandidateService)
	{
		this.candidateRepository = candidateRepositoryRetrieval;
		this.candidateRepositoryCommands = candidateRepository;
		this.stockCandidateService = stockCandidateService;
	}

	@Override
	public Collection<CandidateType> getHandeledTypes()
	{
		return ImmutableList.of(CandidateType.SUPPLY, CandidateType.UNRELATED_INCREASE);
	}

	/**
	 * Call this one if the system was notified about a new or changed supply candidate.
	 * <p>
	 * Creates a new stock candidate or retrieves and updates an existing one.<br>
	 * The stock candidate is made the <i>parent</i> of the supplyCandidate.<br>
	 * When creating a new candidate, then compute its qty by getting the qty from that stockCandidate that has the same product and locator and is "before" it and add the supply candidate's qty
	 *
	 * @param supplyCandidate
	 */

	public Candidate onCandidateNewOrChange(@NonNull final Candidate supplyCandidate)
	{
		assertCorrectCandidateType(supplyCandidate);

		// store the supply candidate and get both it's ID and qty-delta
		final Candidate supplyCandidateDeltaWithId = candidateRepositoryCommands.addOrUpdateOverwriteStoredSeqNo(supplyCandidate);

		if (supplyCandidateDeltaWithId.getQuantity().signum() == 0)
		{
			return supplyCandidateDeltaWithId; // nothing to do
		}

		final Candidate parentStockCandidateWithId;
		if (supplyCandidateDeltaWithId.getParentId() > 0)
		{
			// this supply candidate is not new and already has a stock candidate as its parent. be sure to update exactly *that* stock candidate
			parentStockCandidateWithId = stockCandidateService.updateStock(
					supplyCandidateDeltaWithId,
					() -> {
						// don't check if we might create a new stock candidate, because we know we don't. Get the one that already exists and just update its quantity
						final Candidate stockCandidate = candidateRepository.retrieveLatestMatchOrNull(CandidatesQuery.fromId(supplyCandidateDeltaWithId.getParentId()));
						return candidateRepositoryCommands.updateQty(
								stockCandidate.withQuantity(
										stockCandidate.getQuantity().add(supplyCandidateDeltaWithId.getQuantity())));
					});
		}
		else
		{
			// update (or add) the stock with the delta
			parentStockCandidateWithId = stockCandidateService.addOrUpdateStock(supplyCandidateDeltaWithId
					// but don't provide the supply's SeqNo, because there might already be a stock record which we might override (even if the supply candidate is not yet linked to it);
					// plus, the supply's seqNo shall depend on the stock's anyways
					.withSeqNo(-1));
		}

		// set the stock candidate as parent for the supply candidate
		// the return value would have qty=0, but in the repository we updated the parent-ID
		candidateRepositoryCommands.addOrUpdateOverwriteStoredSeqNo(
				supplyCandidate
						.withParentId(parentStockCandidateWithId.getId())
						.withSeqNo(parentStockCandidateWithId.getSeqNo() + 1));

		return supplyCandidateDeltaWithId
				.withParentId(parentStockCandidateWithId.getId())
				.withSeqNo(parentStockCandidateWithId.getSeqNo() + 1);

		// e.g.
		// supply-candidate with 23 (i.e. +23)
		// parent-stockCandidate used to have -44 (because of "earlier" candidate)
		// now has -21
	}

	private void assertCorrectCandidateType(@NonNull final Candidate supplyCandidate)
	{
		Preconditions.checkArgument(
				supplyCandidate.getType() == CandidateType.SUPPLY || supplyCandidate.getType() == CandidateType.UNRELATED_INCREASE,
				"Given parameter 'supplyCandidate' has type=%s; supplyCandidate=%s",
				supplyCandidate.getType(), supplyCandidate);
	}
}
