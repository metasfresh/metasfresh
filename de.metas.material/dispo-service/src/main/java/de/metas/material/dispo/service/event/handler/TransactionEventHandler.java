package de.metas.material.dispo.service.event.handler;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;

import de.metas.material.dispo.CandidateRepository;
import de.metas.material.dispo.CandidateSpecification.Type;
import de.metas.material.dispo.candidate.Candidate;
import de.metas.material.dispo.candidate.DemandDetail;
import de.metas.material.dispo.candidate.TransactionDetail;
import de.metas.material.dispo.candidate.Candidate.CandidateBuilder;
import de.metas.material.dispo.CandidatesQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.TransactionEvent;
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
public class TransactionEventHandler
{
	private final CandidateChangeService candidateChangeHandler;
	private final CandidateRepository candidateRepository;

	public TransactionEventHandler(
			@NonNull final CandidateChangeService candidateChangeHandler,
			@NonNull final CandidateRepository candidateRepository)
	{
		this.candidateChangeHandler = candidateChangeHandler;
		this.candidateRepository = candidateRepository;
	}

	public void handleTransactionEvent(@NonNull final TransactionEvent event)
	{
		final Candidate candidate = createCandidate(event);
		candidateChangeHandler.onCandidateNewOrChange(candidate);
	}

	@VisibleForTesting
	Candidate createCandidate(@NonNull final TransactionEvent event)
	{
		final TransactionDetail transactionDetail = TransactionDetail.forCandidateOrQuery(event.getMaterialDescr().getQuantity(), event.getTransactionId());

		final Candidate candidate;
		if (event.getShipmentScheduleId() > 0)
		{
			final DemandDetail demandDetail = DemandDetail.forShipmentScheduleIdAndOrderLineId(event.getShipmentScheduleId(), -1);

			final CandidatesQuery query = CandidatesQuery.builder().type(Type.DEMAND)
					.demandDetail(demandDetail)
					.materialDescr(event.getMaterialDescr().withoutQuantity())
					.build();

			final Candidate existingCandidate = candidateRepository.retrieveLatestMatchOrNull(query);
			if (existingCandidate == null)
			{
				candidate = createCommonCandidateBuilder(event)
						.demandDetail(demandDetail)
						.transactionDetail(transactionDetail)
						.build();
			}
			else
			{
				candidate = existingCandidate
						.withTransactionDetail(transactionDetail);
			}
		}
		else
		{
			final CandidatesQuery query = CandidatesQuery.builder()
					.materialDescr(event.getMaterialDescr().withoutQuantity())
					.transactionDetail(TransactionDetail.forQuery(event.getTransactionId()))
					.build();
			final Candidate existingCandidate = candidateRepository.retrieveLatestMatchOrNull(query);
			if (existingCandidate == null)
			{
				candidate = createCommonCandidateBuilder(event)
						.transactionDetail(transactionDetail)
						.build();
			}
			else
			{
				candidate = existingCandidate
						.withTransactionDetail(transactionDetail);
			}
		}
		return candidate;
	}

	@VisibleForTesting
	static CandidateBuilder createCommonCandidateBuilder(@NonNull final TransactionEvent event)
	{
		final BigDecimal eventQuantity = event.getMaterialDescr().getQuantity();

		final CandidateBuilder builder = Candidate
				.builderForEventDescr(event.getEventDescr());
		if (eventQuantity.signum() <= 0)
		{
			return builder.type(Type.UNRELATED_DECREASE)
					.materialDescr(event.getMaterialDescr().withQuantity(eventQuantity.negate()));
		}
		else
		{
			return builder.type(Type.UNRELATED_INCREASE)
					.materialDescr(event.getMaterialDescr());
		}
	}

}
