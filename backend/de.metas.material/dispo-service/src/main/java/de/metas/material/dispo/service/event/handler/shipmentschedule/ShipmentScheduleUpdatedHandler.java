/*
 * #%L
 * metasfresh-material-dispo-service
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.material.dispo.service.event.handler.shipmentschedule;

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.DemandDetailsQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.shipmentschedule.ShipmentScheduleUpdatedEvent;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Profile(Profiles.PROFILE_MaterialDispo)
public class ShipmentScheduleUpdatedHandler implements MaterialEventHandler<ShipmentScheduleUpdatedEvent>
{
	private final CandidateChangeService candidateChangeHandler;
	private final CandidateRepositoryRetrieval candidateRepository;

	public ShipmentScheduleUpdatedHandler(
			@NonNull final CandidateChangeService candidateChangeHandler,
			@NonNull final CandidateRepositoryRetrieval candidateRepository)
	{
		this.candidateChangeHandler = candidateChangeHandler;
		this.candidateRepository = candidateRepository;
	}

	@Override
	public Collection<Class<? extends ShipmentScheduleUpdatedEvent>> getHandledEventType()
	{
		return ImmutableList.of(ShipmentScheduleUpdatedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final ShipmentScheduleUpdatedEvent event)
	{
		final DemandDetailsQuery demandDetailsQuery = DemandDetailsQuery.ofShipmentScheduleId(event.getShipmentScheduleId());
		final CandidatesQuery candidatesQuery = CandidatesQuery
				.builder()
				.type(CandidateType.DEMAND)
				.businessCase(CandidateBusinessCase.SHIPMENT)
				.demandDetailsQuery(demandDetailsQuery)
				.build();

		final Candidate candidate = candidateRepository.retrieveLatestMatchOrNull(candidatesQuery);
		final Candidate updatedCandidate;

		final DemandDetail demandDetail = DemandDetail.forDocumentLine(
				event.getShipmentScheduleId(),
				event.getDocumentLineDescriptor(),
				event.getMaterialDescriptor().getQuantity());

		if (candidate == null)
		{
			updatedCandidate = Candidate
					.builderForEventDescr(event.getEventDescriptor())
					.materialDescriptor(event.getMaterialDescriptor())
					.minMaxDescriptor(event.getMinMaxDescriptor())
					.type(CandidateType.DEMAND)
					.businessCase(CandidateBusinessCase.SHIPMENT)
					.businessCaseDetail(demandDetail)
					.build();
		}
		else
		{
			updatedCandidate = candidate
					.withMaterialDescriptor(event.getMaterialDescriptor())
					.withMinMaxDescriptor(event.getMinMaxDescriptor())
					.withBusinessCaseDetail(demandDetail);
		}

		candidateChangeHandler.onCandidateNewOrChange(updatedCandidate);
	}
}