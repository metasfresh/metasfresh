package de.metas.vertical.pharma.msv3.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import de.metas.vertical.pharma.msv3.server.security.sync.JsonUser;
import de.metas.vertical.pharma.msv3.server.security.sync.JsonUsersUpdateRequest;
import de.metas.vertical.pharma.msv3.server.security.sync.UserSyncRestEndpoint;
import de.metas.vertical.pharma.msv3.server.stockAvailability.sync.JsonStockAvailability;
import de.metas.vertical.pharma.msv3.server.stockAvailability.sync.JsonStockAvailabilityUpdateRequest;
import de.metas.vertical.pharma.msv3.server.stockAvailability.sync.StockAvailabilityBackendSyncRestEndpoint;

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
	private final UserSyncRestEndpoint usersService;
	private final StockAvailabilityBackendSyncRestEndpoint stockAvailabilityService;

	public CreateDummyData(
			final UserSyncRestEndpoint usersService,
			final StockAvailabilityBackendSyncRestEndpoint stockAvailabilityService)
	{
		this.usersService = usersService;
		this.stockAvailabilityService = stockAvailabilityService;

		System.out.println("----------------------------------------------------------------------------------------------------");
		System.out.println("Creating dummy data");
		System.out.println("----------------------------------------------------------------------------------------------------");
		createUsers();
		createStockAvailability();
	}

	private void createUsers()
	{
		usersService.update(JsonUsersUpdateRequest.builder()
				.user(JsonUser.builder().username("user").password("pass").bpartnerId(1234567).build())
				.build());
	}

	private void createStockAvailability()
	{
		stockAvailabilityService.update(JsonStockAvailabilityUpdateRequest.builder()
				.item(JsonStockAvailability.builder().pzn(1112223).qty(30).build())
				.item(JsonStockAvailability.builder().pzn(1112224).qty(30).build())
				.item(JsonStockAvailability.builder().pzn(1112225).qty(30).build())
				.item(JsonStockAvailability.builder().pzn(1112226).qty(30).build())
				.build());
	}

}
