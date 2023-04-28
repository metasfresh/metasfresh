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
import de.metas.material.dispo.commons.SimulatedCandidateService;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.simulation.DeactivateAllSimulatedCandidatesEvent;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Profile(Profiles.PROFILE_MaterialDispo)
public class DeactivateAllSimulatedCandidatesHandler implements MaterialEventHandler<DeactivateAllSimulatedCandidatesEvent>
{
	@NonNull
	private final SimulatedCandidateService simulatedCandidateService;

	public DeactivateAllSimulatedCandidatesHandler(final @NonNull SimulatedCandidateService simulatedCandidateService)
	{
		this.simulatedCandidateService = simulatedCandidateService;
	}

	@Override
	public Collection<Class<? extends DeactivateAllSimulatedCandidatesEvent>> getHandledEventType()
	{
		return ImmutableList.of(DeactivateAllSimulatedCandidatesEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final DeactivateAllSimulatedCandidatesEvent event)
	{
		simulatedCandidateService.deactivateAllSimulatedCandidates();
	}
}