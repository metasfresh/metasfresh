package de.metas.material.dispo.service.event.handler;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.Candidate.CandidateBuilder;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.DemandDetail;
import de.metas.material.dispo.commons.candidate.TransactionDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidatesQuery;
import de.metas.material.dispo.commons.repository.MaterialDescriptorQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.transactions.TransactionCreatedEvent;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-material-dispo
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
public class TransactionCreatedHandler
{
	private final CandidateChangeService candidateChangeHandler;
	private final CandidateRepositoryRetrieval candidateRepository;

	public TransactionCreatedHandler(
			@NonNull final CandidateChangeService candidateChangeHandler,
			@NonNull final CandidateRepositoryRetrieval candidateRepository)
	{
		this.candidateChangeHandler = candidateChangeHandler;
		this.candidateRepository = candidateRepository;
	}

	public void handleTransactionCreatedEvent(@NonNull final TransactionCreatedEvent event)
	{
		final Candidate candidate = createCandidateForTransactionEvent(event);
		candidateChangeHandler.onCandidateNewOrChange(candidate);
	}

	@VisibleForTesting
	Candidate createCandidateForTransactionEvent(@NonNull final TransactionCreatedEvent event)
	{
		final TransactionDetail transactionDetailOfEvent = TransactionDetail
				.forCandidateOrQuery(
						event.getMaterialDescriptor().getQuantity(),
						event.getTransactionId());

		final Candidate candidate;
		if (event.getShipmentScheduleId() > 0)
		{
			final DemandDetail demandDetail = DemandDetail.forShipmentScheduleIdAndOrderLineId(event.getShipmentScheduleId(), -1);

			final CandidatesQuery query = CandidatesQuery.builder().type(CandidateType.DEMAND)
					.demandDetail(demandDetail) // only search via demand detail, ..the product and warehouse will also match, but e.g. the date probably won't!
					.build();

			final Candidate existingCandidate = candidateRepository.retrieveLatestMatchOrNull(query);
			if (existingCandidate == null)
			{
				candidate = createCommonCandidateBuilder(event)
						.demandDetail(demandDetail)
						.transactionDetail(transactionDetailOfEvent)
						.build();
			}
			else
			{
				candidate = newCandidateWithAddedTransactionDetail(
						existingCandidate,
						transactionDetailOfEvent);
			}
		}
		else
		{

			final CandidatesQuery query = CandidatesQuery.builder()
					.materialDescriptorQuery(MaterialDescriptorQuery.forDescriptor(event.getMaterialDescriptor()))
					.transactionDetail(TransactionDetail.forQuery(event.getTransactionId()))
					.build();
			final Candidate existingCandidate = candidateRepository.retrieveLatestMatchOrNull(query);
			if (existingCandidate == null)
			{
				candidate = createCommonCandidateBuilder(event)
						.transactionDetail(transactionDetailOfEvent)
						.build();
			}
			else
			{
				candidate = newCandidateWithAddedTransactionDetailAndQuantity(
						existingCandidate,
						transactionDetailOfEvent);
			}
		}
		return candidate;
	}

	private Candidate newCandidateWithAddedTransactionDetailAndQuantity(
			@NonNull final Candidate candidate,
			@NonNull final TransactionDetail transactionDetail)
	{
		final BigDecimal newQuantity = candidate
				.getQuantity()
				.add(transactionDetail.getQuantity());

		Candidate newCandidate = candidate.withQuantity(newQuantity);
		newCandidate = newCandidateWithAddedTransactionDetail(newCandidate, transactionDetail);
		return newCandidate;
	}

	private Candidate newCandidateWithAddedTransactionDetail(
			@NonNull final Candidate candidate,
			@NonNull final TransactionDetail transactionDetail)
	{
		final Builder<TransactionDetail> newTransactionDetailsList = //
				ImmutableList.<TransactionDetail> builder()
						.addAll(candidate.getTransactionDetails())
						.add(transactionDetail);

		return candidate.withTransactionDetails(newTransactionDetailsList.build());
	}

	@VisibleForTesting
	static CandidateBuilder createCommonCandidateBuilder(@NonNull final TransactionCreatedEvent event)
	{
		final BigDecimal eventQuantity = event.getMaterialDescriptor().getQuantity();

		final CandidateBuilder builder = Candidate
				.builderForEventDescr(event.getEventDescriptor());
		if (eventQuantity.signum() <= 0)
		{
			return builder.type(CandidateType.UNRELATED_DECREASE)
					.materialDescriptor(event.getMaterialDescriptor().withQuantity(eventQuantity.negate()));
		}
		else
		{
			return builder.type(CandidateType.UNRELATED_INCREASE)
					.materialDescriptor(event.getMaterialDescriptor());
		}
	}

}
