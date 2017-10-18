package de.metas.material.dispo.service.event.handler;

import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;

import de.metas.material.dispo.Candidate;
import de.metas.material.dispo.CandidateRepository;
import de.metas.material.dispo.Candidate.CandidateBuilder;
import de.metas.material.dispo.Candidate.Type;
import de.metas.material.dispo.DemandCandidateDetail;
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
			@NonNull final CandidateRepository candidateRepository
			)
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
		final Candidate candidate;
		if (event.getShipmentScheduleId() > 0)
		{
			candidate = createCommonCandidateBuilder(event)
					.type(Type.DEMAND)
					.demandDetail(DemandCandidateDetail.forShipmentScheduleIdAndOrderLineId(event.getShipmentScheduleId(), -1))
					.build();
			
		}
		else
		{
			candidate = createCommonCandidateBuilder(event)
					.type(Type.UNRELATED_TRANSACTION)
					.build();
		}
		return candidate;
	}

	private CandidateBuilder createCommonCandidateBuilder(@NonNull final TransactionEvent event)
	{
		return Candidate.builderForEventDescr(event.getEventDescr())
				.materialDescr(event.getMaterialDescr());
	}

}
