package de.metas.material.dispo.service.event.handler.pporder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import org.adempiere.util.Check;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.Profiles;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateStatus;
import de.metas.material.dispo.commons.candidate.ProductionDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.event.EventUtil;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.pporder.PPOrderDocStatusChangedEvent;
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
@Profile(Profiles.PROFILE_MaterialDispo)
public class PPOrderDocStatusChangedHandler implements MaterialEventHandler<PPOrderDocStatusChangedEvent>
{
	private final CandidateRepositoryRetrieval candidateRepositoryRetrieval;
	private final CandidateChangeService candidateChangeService;

	public PPOrderDocStatusChangedHandler(
			@NonNull final CandidateRepositoryRetrieval candidateRepositoryRetrieval,
			@NonNull final CandidateChangeService candidateChangeService)
	{
		this.candidateChangeService = candidateChangeService;
		this.candidateRepositoryRetrieval = candidateRepositoryRetrieval;
	}

	@Override
	public Collection<Class<? extends PPOrderDocStatusChangedEvent>> getHandeledEventType()
	{
		return ImmutableList.of(PPOrderDocStatusChangedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final PPOrderDocStatusChangedEvent ppOrderChangedDocStatusEvent)
	{
		final List<Candidate> candidatesForPPOrderId = PPOrderUtil
				.retrieveCandidatesForPPOrderId(
						candidateRepositoryRetrieval,
						ppOrderChangedDocStatusEvent.getPpOrderId());

		Check.errorIf(candidatesForPPOrderId.isEmpty(),
				"No Candidates found for PP_Order_ID={} ",
				ppOrderChangedDocStatusEvent.getPpOrderId());

		final String newDocStatusFromEvent = ppOrderChangedDocStatusEvent.getNewDocStatus();
		final CandidateStatus newCandidateStatus = EventUtil
				.getCandidateStatus(newDocStatusFromEvent);

		final Function<Candidate, BigDecimal> produtionDetail2Qty = //
				EventUtil.deriveDistributionDetail2QtyProvider(newCandidateStatus);

		final List<Candidate> updatedCandidatesToPersist = new ArrayList<>();

		for (final Candidate candidateForPPOrderId : candidatesForPPOrderId)
		{
			final BigDecimal newQuantity = produtionDetail2Qty.apply(candidateForPPOrderId);
			final ProductionDetail productionDetail = //
					ProductionDetail.cast(candidateForPPOrderId.getBusinessCaseDetail());

			final Candidate updatedCandidateToPersist = candidateForPPOrderId.toBuilder()
					.status(newCandidateStatus)
					.materialDescriptor(candidateForPPOrderId.getMaterialDescriptor().withQuantity(newQuantity))
					.businessCaseDetail(productionDetail.toBuilder().ppOrderDocStatus(newDocStatusFromEvent).build())
					.build();

			updatedCandidatesToPersist.add(updatedCandidateToPersist);
		}

		updatedCandidatesToPersist.forEach(candidate -> candidateChangeService.onCandidateNewOrChange(candidate));
	}
}
