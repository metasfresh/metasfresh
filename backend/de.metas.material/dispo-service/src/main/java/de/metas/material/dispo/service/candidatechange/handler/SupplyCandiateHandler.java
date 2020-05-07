package de.metas.material.dispo.service.candidatechange.handler;

import java.math.BigDecimal;
import java.util.Collection;

import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.MaterialDescriptorQuery;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
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
	private final CandidateRepositoryWriteService candidateRepositoryWriteService;

	private final StockCandidateService stockCandidateService;

	private final CandidateRepositoryRetrieval candidateRepository;

	public SupplyCandiateHandler(
			@NonNull final CandidateRepositoryRetrieval candidateRepositoryRetrieval,
			@NonNull final CandidateRepositoryWriteService candidateRepository,
			@NonNull final StockCandidateService stockCandidateService)
	{
		this.candidateRepository = candidateRepositoryRetrieval;
		this.candidateRepositoryWriteService = candidateRepository;
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
	@Override
	public Candidate onCandidateNewOrChange(@NonNull final Candidate supplyCandidate)
	{
		assertCorrectCandidateType(supplyCandidate);

		// store the supply candidate and get both it's ID and qty-delta
		// TODO 3034 test: if we add a supplyCandidate that has an unspecified parent-id and and in DB there is an MD_Candidate with parentId > 0,
		// then supplyCandidateDeltaWithId needs ton have that parentId
		final Candidate supplyCandidateDeltaWithId = candidateRepositoryWriteService.addOrUpdateOverwriteStoredSeqNo(supplyCandidate);
		final Candidate supplyCandidateWithIdAndParentId = supplyCandidateDeltaWithId.withQuantity(supplyCandidate.getQuantity());

		if (supplyCandidateDeltaWithId.getQuantity().signum() == 0)
		{
			return supplyCandidateDeltaWithId; // nothing to do
		}

		final Candidate parentStockCandidateWithIdAndDelta;

		final boolean alreadyHasParentStockCandidate = supplyCandidateWithIdAndParentId.getParentId() > 0;
		if (alreadyHasParentStockCandidate)
		{
			final Candidate parentStockCandidate = candidateRepository
					.retrieveLatestMatchOrNull(CandidatesQuery.fromId(supplyCandidateWithIdAndParentId.getParentId()));
			parentStockCandidateWithIdAndDelta = stockCandidateService.updateQty(
					parentStockCandidate.withQuantity(supplyCandidate.getQuantity()));
		}
		else
		{
			// Figure out if there is stock candidate that is a child of a demand candidate with has the same demand details that we have.
			final CandidatesQuery queryForExistingDemandChild;
			if (extractDemandCandidateId(supplyCandidate) > 0)
			{
				queryForExistingDemandChild = CandidatesQuery.builder()
						.type(CandidateType.STOCK)
						.parentId(extractDemandCandidateId(supplyCandidate))
						.build();
			}
			else
			{
				queryForExistingDemandChild = CandidatesQuery.builder()
						.type(CandidateType.STOCK)
						.parentDemandDetail(supplyCandidateWithIdAndParentId.getDemandDetail())
						.materialDescriptorQuery(MaterialDescriptorQuery.forDescriptor(supplyCandidate.getMaterialDescriptor()))
						.build();
			}

			final Candidate existingDemandChildStock = candidateRepository.retrieveLatestMatchOrNull(queryForExistingDemandChild);
			if (existingDemandChildStock != null)
			{
				final BigDecimal newStockQuantity = existingDemandChildStock.getQuantity()
						.add(supplyCandidateDeltaWithId.getQuantity());

				final Candidate existingDemandChildWithUpdatedQty = existingDemandChildStock
						.withQuantity(newStockQuantity);

				parentStockCandidateWithIdAndDelta = candidateRepositoryWriteService
						.addOrUpdatePreserveExistingSeqNo(existingDemandChildWithUpdatedQty);
			}
			else
			{
				final Candidate newSupplyCandidateParent = stockCandidateService.createStockCandidate(supplyCandidateWithIdAndParentId);
				parentStockCandidateWithIdAndDelta = candidateRepositoryWriteService.addOrUpdatePreserveExistingSeqNo(newSupplyCandidateParent);
			}
		}

		stockCandidateService.applyDeltaToMatchingLaterStockCandidates(parentStockCandidateWithIdAndDelta);

		// set the stock candidate as parent for the supply candidate
		// the return value would have qty=0, but in the repository we updated the parent-ID
		candidateRepositoryWriteService.updateCandidateById(
				supplyCandidateWithIdAndParentId
						.withParentId(parentStockCandidateWithIdAndDelta.getId())
						.withSeqNo(parentStockCandidateWithIdAndDelta.getSeqNo() + 1));

		return supplyCandidateDeltaWithId
				.withParentId(parentStockCandidateWithIdAndDelta.getId())
				.withSeqNo(parentStockCandidateWithIdAndDelta.getSeqNo() + 1);
	}

	private int extractDemandCandidateId(@NonNull final Candidate supplyCandidate)
	{
		if (supplyCandidate.getDemandDetail() == null)
		{
			return 0;
		}
		return supplyCandidate.getDemandDetail().getDemandCandidateId();
	}

	private void assertCorrectCandidateType(@NonNull final Candidate supplyCandidate)
	{
		Preconditions.checkArgument(
				supplyCandidate.getType() == CandidateType.SUPPLY || supplyCandidate.getType() == CandidateType.UNRELATED_INCREASE,
				"Given parameter 'supplyCandidate' has type=%s; supplyCandidate=%s",
				supplyCandidate.getType(), supplyCandidate);
	}
}
