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

		final Candidate existingCandidate = candidateRepository.retrieveLatestMatchOrNull(candidatesQuery);

		final DemandDetail demandDetail = DemandDetail.forDocumentLine(
				event.getShipmentScheduleId(),
				event.getDocumentLineDescriptor(),
				event.getMaterialDescriptor().getQuantity());

		final boolean materialDescriptorChanged = event.getShipmentScheduleDetail().getOldShipmentScheduleData() != null;

		// When the material descriptor changed (ASI, date, product, or warehouse),
		// delete the old candidate so its STOCK child (keyed to the old attributes)
		// is properly removed and its ATP delta is propagated for the old AttributesKey.
		// Then create a fresh candidate with the new descriptor below.
		if (materialDescriptorChanged && existingCandidate != null)
		{
			candidateChangeHandler.onCandidateDelete(existingCandidate);
		}

		final Candidate updatedCandidate;
		if (existingCandidate == null || materialDescriptorChanged)
		{
			// Create brand new candidate: either no prior candidate existed,
			// or the old one was just deleted due to material descriptor change.
			updatedCandidate = Candidate
					.builderForEventDescriptor(event.getEventDescriptor())
					.materialDescriptor(event.getMaterialDescriptor())
					.minMaxDescriptor(event.getMinMaxDescriptor())
					.type(CandidateType.DEMAND)
					.businessCase(CandidateBusinessCase.SHIPMENT)
					.businessCaseDetail(demandDetail)
					.build();
		}
		else
		{
			// Simple update (no material descriptor change): update qty/minmax in place.
			updatedCandidate = existingCandidate.toBuilder()
					.materialDescriptor(event.getMaterialDescriptor())
					.minMaxDescriptor(event.getMinMaxDescriptor())
					.businessCaseDetail(demandDetail)
					.build();
		}

		candidateChangeHandler.onCandidateNewOrChange(updatedCandidate);
	}
}