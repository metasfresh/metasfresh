/*
 * #%L
 * metasfresh-material-dispo-service
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.material.dispo.service.event.handler.ppordercandidate;

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.event.handler.pporder.PPOrderHandlerUtils;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.material.event.pporder.PPOrderCandidateCreatedEvent;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Profile(Profiles.PROFILE_MaterialDispo)
public class PPOrderCandidateCreatedEventHandler extends PPOrderCandidateEventHandler implements MaterialEventHandler<PPOrderCandidateCreatedEvent>
{
	public PPOrderCandidateCreatedEventHandler(
			@NonNull final CandidateChangeService candidateChangeService,
			@NonNull final CandidateRepositoryRetrieval candidateRepositoryRetrieval)
	{
		super(candidateChangeService, candidateRepositoryRetrieval);
	}

	@Override
	public Collection<Class<? extends PPOrderCandidateCreatedEvent>> getHandledEventType()
	{
		return ImmutableList.of(PPOrderCandidateCreatedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final PPOrderCandidateCreatedEvent event)
	{
		handleMaterialDispoProductionDetail(event);
	}

	private void handleMaterialDispoProductionDetail(@NonNull final PPOrderCandidateCreatedEvent event)
	{
		final MaterialDispoGroupId groupId = event.getPpOrderCandidate().getPpOrderData().getMaterialDispoGroupId();

		final CandidatesQuery query = CandidatesQuery.builder()
				.type(CandidateType.SUPPLY)
				.businessCase(CandidateBusinessCase.PRODUCTION)
				.groupId(groupId)
				.materialDescriptorQuery(PPOrderHandlerUtils.createMaterialDescriptorQuery(event.getPpOrderCandidate().getPpOrderData().getProductDescriptor()))
				.build();

		createHeaderCandidate(event, query);
	}
}
