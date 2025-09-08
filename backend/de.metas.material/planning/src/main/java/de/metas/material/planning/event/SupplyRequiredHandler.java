package de.metas.material.planning.event;

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.supplyrequired.NoSupplyAdviceEvent;
import de.metas.material.event.supplyrequired.SupplyRequiredEvent;
import de.metas.material.planning.MaterialPlanningContext;
import de.metas.util.Loggables;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
 * #%L
 * metasfresh-material-planning
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
@Profile(Profiles.PROFILE_App) // we want only one component to bother itself with SupplyRequiredEvents
@RequiredArgsConstructor
public class SupplyRequiredHandler implements MaterialEventHandler<SupplyRequiredEvent>
{
	@NonNull private final PostMaterialEventService postMaterialEventService;
	@NonNull private final List<SupplyRequiredAdvisor> supplyRequiredAdvisors;
	@NonNull private final SupplyRequiredHandlerHelper helper;

	@Override
	public Collection<Class<? extends SupplyRequiredEvent>> getHandledEventType()
	{
		return ImmutableList.of(SupplyRequiredEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final SupplyRequiredEvent event)
	{
		handleSupplyRequiredEvent(event.getSupplyRequiredDescriptor());
	}

	private void handleSupplyRequiredEvent(@NonNull final SupplyRequiredDescriptor descriptor)
	{
		final ArrayList<MaterialEvent> events = new ArrayList<>();

		final MaterialPlanningContext context = helper.createContextOrNull(descriptor);
		if (context != null)
		{
			for (final SupplyRequiredAdvisor advisor : supplyRequiredAdvisors)
			{
				events.addAll(advisor.createAdvisedEvents(descriptor, context));
			}
		}

		if (events.isEmpty())
		{
			final NoSupplyAdviceEvent noSupplyAdviceEvent = NoSupplyAdviceEvent.of(descriptor.withNewEventId());
			Loggables.addLog("No advice events were created. Firing {}", noSupplyAdviceEvent);

			postMaterialEventService.enqueueEventNow(noSupplyAdviceEvent);
		}
		else
		{
			events.forEach(postMaterialEventService::enqueueEventNow);
		}
	}
}