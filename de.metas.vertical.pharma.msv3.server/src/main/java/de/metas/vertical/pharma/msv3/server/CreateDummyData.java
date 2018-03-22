package de.metas.vertical.pharma.msv3.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3StockAvailability;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3StockAvailabilityUpdatedEvent;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3UserChangedEvent;
import de.metas.vertical.pharma.msv3.server.peer.service.CustomerConfigEventsQueue;
import de.metas.vertical.pharma.msv3.server.peer.service.StockAvailabilityEventsQueue;

/*
 * #%L
 * metasfresh-pharma.msv3.server
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

@Configuration
@Profile("dummy_data")
public class CreateDummyData
{
	private final CustomerConfigEventsQueue customerConfigEventsQueue;
	private final StockAvailabilityEventsQueue stockAvailabilityEventsQueue;

	public CreateDummyData(
			final CustomerConfigEventsQueue customerConfigEventsQueue,
			final StockAvailabilityEventsQueue stockAvailabilityEventsQueue)
	{
		this.customerConfigEventsQueue = customerConfigEventsQueue;
		this.stockAvailabilityEventsQueue = stockAvailabilityEventsQueue;

		System.out.println("----------------------------------------------------------------------------------------------------");
		System.out.println("Creating dummy data");
		System.out.println("----------------------------------------------------------------------------------------------------");
		createUsers();
		createStockAvailability();
	}

	private void createUsers()
	{
		customerConfigEventsQueue.publish(MSV3UserChangedEvent.prepareCreatedEvent().username("user").password("pass").bpartnerId(2156425).bpartnerLocationId(2205175).build());
	}

	private void createStockAvailability()
	{
		stockAvailabilityEventsQueue.publish(MSV3StockAvailabilityUpdatedEvent.builder()
				.item(MSV3StockAvailability.builder().pzn(1112223).qty(30).build())
				.item(MSV3StockAvailability.builder().pzn(1112224).qty(30).build())
				.item(MSV3StockAvailability.builder().pzn(1112225).qty(30).build())
				.item(MSV3StockAvailability.builder().pzn(1112226).qty(30).build())
				.build());
	}

}
