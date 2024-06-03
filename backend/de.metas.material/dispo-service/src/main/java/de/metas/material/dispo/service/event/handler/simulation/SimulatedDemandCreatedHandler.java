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

package de.metas.material.dispo.service.event.handler.simulation;

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.simulation.SimulatedDemandCreatedEvent;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static de.metas.common.util.IdConstants.UNSPECIFIED_REPO_ID;

@Service
@Profile(Profiles.PROFILE_MaterialDispo)
public class SimulatedDemandCreatedHandler implements MaterialEventHandler<SimulatedDemandCreatedEvent>
{
	private final CandidateChangeService candidateChangeHandler;

	public SimulatedDemandCreatedHandler(@NonNull final CandidateChangeService candidateChangeHandler)
	{
		this.candidateChangeHandler = candidateChangeHandler;
	}

	@Override
	public Collection<Class<? extends SimulatedDemandCreatedEvent>> getHandledEventType()
	{
		return ImmutableList.of(SimulatedDemandCreatedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final SimulatedDemandCreatedEvent event)
	{
		final DemandDetail demandDetail = DemandDetail.forDocumentLine(
				UNSPECIFIED_REPO_ID,
				event.getDocumentLineDescriptor(),
				event.getMaterialDescriptor().getQuantity())
				.withTraceId(event.getEventDescriptor().getTraceId());

		final Candidate.CandidateBuilder candidateBuilder = Candidate
				.builderForEventDescr(event.getEventDescriptor())
				.materialDescriptor(event.getMaterialDescriptor())
				.type(CandidateType.DEMAND)
				.businessCase(CandidateBusinessCase.SHIPMENT)
				.businessCaseDetail(demandDetail)
				.simulated(true);

		candidateChangeHandler.onCandidateNewOrChange(candidateBuilder.build());
	}
}
