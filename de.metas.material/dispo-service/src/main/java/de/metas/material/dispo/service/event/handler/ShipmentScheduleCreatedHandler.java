package de.metas.material.dispo.service.event.handler;

import java.util.Collection;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.Profiles;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.DemandDetail;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.shipmentschedule.ShipmentScheduleCreatedEvent;
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
@Profile(Profiles.PROFILE_MaterialDispo)
public class ShipmentScheduleCreatedHandler implements MaterialEventHandler<ShipmentScheduleCreatedEvent>
{
	private final CandidateChangeService candidateChangeHandler;

	public ShipmentScheduleCreatedHandler(@NonNull final CandidateChangeService candidateChangeHandler)
	{
		this.candidateChangeHandler = candidateChangeHandler;
	}

	@Override
	public Collection<Class<? extends ShipmentScheduleCreatedEvent>> getHandeledEventType()
	{
		return ImmutableList.of(ShipmentScheduleCreatedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final ShipmentScheduleCreatedEvent event)
	{
		final DemandDetail demandDetail = DemandDetail.forDocumentDescriptor(
				event.getShipmentScheduleId(),
				event.getDocumentLineDescriptor());

		final Candidate candidate = Candidate.builderForEventDescr(event.getEventDescriptor())
				.materialDescriptor(event.getMaterialDescriptor())
				.type(CandidateType.DEMAND)
				.businessCase(CandidateBusinessCase.SHIPMENT)
				.demandDetail(demandDetail)
				.build();
		candidateChangeHandler.onCandidateNewOrChange(candidate);
	}
}
