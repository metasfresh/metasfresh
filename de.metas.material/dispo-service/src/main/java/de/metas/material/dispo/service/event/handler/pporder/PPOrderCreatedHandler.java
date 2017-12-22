package de.metas.material.dispo.service.event.handler.pporder;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import de.metas.Profiles;
import de.metas.material.dispo.commons.RequestMaterialOrderService;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.pporder.PPOrderCreatedEvent;
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
public class PPOrderCreatedHandler
		extends PPOrderAdvisedOrCreatedHandler<PPOrderCreatedEvent>
{

	public PPOrderCreatedHandler(
			@NonNull final CandidateChangeService candidateChangeHandler,
			@NonNull final RequestMaterialOrderService candidateService)
	{
		super(candidateChangeHandler, candidateService);
	}

	@Override
	public Class<PPOrderCreatedEvent> getHandeledEventType()
	{
		return PPOrderCreatedEvent.class;
	}

	@Override
	public void handleEvent(@NonNull final PPOrderCreatedEvent event)
	{
		final boolean advised = false;
		handlePPOrderAdvisedOrCreatedEvent(event, advised);
	}

}
