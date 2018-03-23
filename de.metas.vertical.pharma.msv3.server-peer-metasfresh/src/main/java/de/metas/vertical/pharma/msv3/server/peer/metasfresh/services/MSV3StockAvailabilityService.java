package de.metas.vertical.pharma.msv3.server.peer.metasfresh.services;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3StockAvailabilityUpdatedEvent;
import de.metas.vertical.pharma.msv3.server.peer.service.MSV3ServerPeerService;

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

@Service
public class MSV3StockAvailabilityService
{
	@Autowired
	private MSV3ServerPeerService msv3ServerPeerService;

	public void publishAll()
	{
		streamAllStockAvailabilityUpdatedEvents().forEach(msv3ServerPeerService::publishStockAvailabilityUpdatedEvent);
	}

	private Stream<MSV3StockAvailabilityUpdatedEvent> streamAllStockAvailabilityUpdatedEvents()
	{
		// TODO: implement streamAllStockAvailabilityUpdatedEvents
		return Stream.empty();
	}

}
