package de.metas.material.cockpit.view.eventhandler;

import java.util.Collection;
import java.util.List;

import org.adempiere.util.Loggables;
import org.compiere.util.TimeUtil;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.Profiles;
import de.metas.material.cockpit.view.MainDataRecordIdentifier;
import de.metas.material.cockpit.view.mainrecord.MainDataRequestHandler;
import de.metas.material.cockpit.view.mainrecord.UpdateMainDataRequest;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.pporder.AbstractPPOrderEvent;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderAdvisedEvent;
import de.metas.material.event.pporder.PPOrderCreatedEvent;
import de.metas.material.event.pporder.PPOrderLine;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-material-cockpit
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
@Profile(Profiles.PROFILE_App) // it's important to have just *one* instance of this listener, because on each event needs to be handled exactly once.
public class PPOrderCreatedOrAdvisedEventHandler implements MaterialEventHandler<AbstractPPOrderEvent>
{
	private final MainDataRequestHandler dataUpdateRequestHandler;

	public PPOrderCreatedOrAdvisedEventHandler(@NonNull final MainDataRequestHandler dataUpdateRequestHandler)
	{
		this.dataUpdateRequestHandler = dataUpdateRequestHandler;
	}

	@Override
	public Collection<Class<? extends AbstractPPOrderEvent>> getHandeledEventType()
	{
		return ImmutableList.of(PPOrderCreatedEvent.class, PPOrderAdvisedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final AbstractPPOrderEvent ppOrderEvent)
	{
		if (ppOrderEvent instanceof PPOrderCreatedEvent)
		{
			final PPOrderCreatedEvent ppOrderCreatedEvent = (PPOrderCreatedEvent)ppOrderEvent;
			if (ppOrderCreatedEvent.getPpOrder().getMaterialDispoGroupId() > 0)
			{
				Loggables.get().addLog("This PPOrderCreatedEvent has a PPOrder with MaterialDispoGroupId > 0, so we already processed its respective PPOrderAdvisedEvent; skipping");
				return;
			}
		}

		final PPOrder ppOrder = ppOrderEvent.getPpOrder();
		final List<PPOrderLine> lines = ppOrder.getLines();

		final ImmutableList.Builder<UpdateMainDataRequest> requests = ImmutableList.builder();
		for (final PPOrderLine line : lines)
		{
			final MainDataRecordIdentifier identifier = MainDataRecordIdentifier.builder()
					.productDescriptor(line.getProductDescriptor())
					.date(TimeUtil.getDay(ppOrder.getDatePromised()))
					.build();

			final UpdateMainDataRequest request = UpdateMainDataRequest.builder()
					.identifier(identifier)
					.requiredForProductionQty(line.getQtyRequired())
					.build();
			requests.add(request);
		}

		requests.build()
				.forEach(request -> dataUpdateRequestHandler.handleDataUpdateRequest(request));
	}

}
