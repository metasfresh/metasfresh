package de.metas.material.dispo.service.event.handler.ddorder;

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
import de.metas.material.dispo.commons.candidate.DistributionDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.event.EventUtil;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.ddorder.DDOrderDocStatusChangedEvent;
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
public class DDOrderDocStatusChangedHandler implements MaterialEventHandler<DDOrderDocStatusChangedEvent>
{
	private final CandidateRepositoryRetrieval candidateRepositoryRetrieval;
	private final CandidateChangeService candidateChangeService;

	public DDOrderDocStatusChangedHandler(
			@NonNull final CandidateRepositoryRetrieval candidateRepositoryRetrieval,
			@NonNull final CandidateChangeService candidateChangeService)
	{
		this.candidateChangeService = candidateChangeService;
		this.candidateRepositoryRetrieval = candidateRepositoryRetrieval;
	}

	@Override
	public Collection<Class<? extends DDOrderDocStatusChangedEvent>> getHandeledEventType()
	{
		return ImmutableList.of(DDOrderDocStatusChangedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final DDOrderDocStatusChangedEvent ddOrderChangedDocStatusEvent)
	{
		final List<Candidate> candidatesForDDOrderId = DDOrderUtil
				.retrieveCandidatesForDDOrderId(
						candidateRepositoryRetrieval,
						ddOrderChangedDocStatusEvent.getDdOrderId());

		Check.errorIf(candidatesForDDOrderId.isEmpty(),
				"No Candidates found for PP_Order_ID={} ",
				ddOrderChangedDocStatusEvent.getDdOrderId());

		final String newDocStatusFromEvent = ddOrderChangedDocStatusEvent.getNewDocStatus();
		final CandidateStatus newCandidateStatus = EventUtil
				.getCandidateStatus(newDocStatusFromEvent);

		final Function<Candidate, BigDecimal> masterialQtyFunktion = //
				EventUtil.deriveCandiadte2QtyProvider(newCandidateStatus);

		final List<Candidate> updatedCandidatesToPersist = new ArrayList<>();

		for (final Candidate candidateForDDOrderId : candidatesForDDOrderId)
		{
			final BigDecimal newQuantity = masterialQtyFunktion.apply(candidateForDDOrderId);
			final DistributionDetail distributionDetail = //
					DistributionDetail.cast(candidateForDDOrderId.getBusinessCaseDetail());

			final Candidate updatedCandidateToPersist = candidateForDDOrderId.toBuilder()
					.status(newCandidateStatus)
					.materialDescriptor(candidateForDDOrderId.getMaterialDescriptor().withQuantity(newQuantity))
					.businessCaseDetail(distributionDetail.toBuilder().ddOrderDocStatus(newDocStatusFromEvent).build())
					.build();

			updatedCandidatesToPersist.add(updatedCandidateToPersist);
		}
		updatedCandidatesToPersist.forEach(candidate -> candidateChangeService.onCandidateNewOrChange(candidate));
	}
}
