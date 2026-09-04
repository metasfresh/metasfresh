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
import de.metas.material.planning.PlanningUsage;
import de.metas.quantity.Quantity;
import de.metas.util.Loggables;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

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
public class SupplyRequiredHandler implements MaterialEventHandler<SupplyRequiredEvent>
{
	@NonNull private final PostMaterialEventService postMaterialEventService;
	@NonNull private final ImmutableList<SupplyRequiredAdvisor> supplyRequiredAdvisors;
	@NonNull private final SupplyRequiredHandlerHelper helper;

	public SupplyRequiredHandler(
			@NonNull final PostMaterialEventService postMaterialEventService,
			@NonNull final List<SupplyRequiredAdvisor> supplyRequiredAdvisors,
			@NonNull final SupplyRequiredHandlerHelper helper)
	{
		this.postMaterialEventService = postMaterialEventService;
		// Sort advisors by PlanningUsage declaration order so the dispatch below always runs
		// distribution -> manufacturing -> purchasing, independent of Spring bean discovery order.
		this.supplyRequiredAdvisors = supplyRequiredAdvisors.stream()
				.sorted(Comparator.comparing(a -> a.getPlanningUsage().ordinal()))
				.collect(ImmutableList.toImmutableList());
		this.helper = helper;
	}

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

		final Map<PlanningUsage, MaterialPlanningContext> contextsByUsage = helper.createContextsByUsage(descriptor);

		Quantity remainingQty = SupplyRequiredHandlerUtils.extractQtyToSupply(descriptor);
		for (final SupplyRequiredAdvisor advisor : supplyRequiredAdvisors)
		{
			if (remainingQty.signum() <= 0)
			{
				break;
			}
			final MaterialPlanningContext context = contextsByUsage.get(advisor.getPlanningUsage());
			if (context == null)
			{
				continue;
			}

			final SupplyAdvice advice = advisor.createAdvisedEvents(descriptor, context, remainingQty);
			events.addAll(advice.getEvents());
			remainingQty = remainingQty.subtract(advice.getConsumedQty());
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
