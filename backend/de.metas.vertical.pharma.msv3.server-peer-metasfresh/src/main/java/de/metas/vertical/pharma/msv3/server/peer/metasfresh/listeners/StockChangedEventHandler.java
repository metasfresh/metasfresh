package de.metas.vertical.pharma.msv3.server.peer.metasfresh.listeners;

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.stock.StockChangedEvent;
import de.metas.vertical.pharma.msv3.server.peer.metasfresh.services.MSV3StockAvailabilityService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Collection;

/*
 * #%L
 * metasfresh-pharma.msv3.server-peer-metasfresh
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Profile(Profiles.PROFILE_App)
@Component
public class StockChangedEventHandler implements MaterialEventHandler<StockChangedEvent>
{
	@Autowired
	private MSV3StockAvailabilityService stockAvailabilityService;

	@Override
	public Collection<Class<? extends StockChangedEvent>> getHandledEventType()
	{
		return ImmutableList.of(StockChangedEvent.class);
	}

	@Override
	public void validateEvent(@NonNull final StockChangedEvent event)
	{
		event.validate();
	}

	@Override
	public void handleEvent(@NonNull final StockChangedEvent event)
	{
		stockAvailabilityService.handleStockChangedEvent(event);
	}

}
