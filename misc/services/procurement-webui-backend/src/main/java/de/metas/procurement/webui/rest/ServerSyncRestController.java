/*
 * #%L
 * procurement-webui-backend
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

package de.metas.procurement.webui.rest;

import de.metas.procurement.webui.Constants;
import de.metas.procurement.webui.sync.IServerSyncService;
import lombok.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ServerSyncRestController.ENDPOINT)
public class ServerSyncRestController
{
	public static final String ENDPOINT = Constants.ENDPOINT_ROOT + "/serverSync";
	private final IServerSyncService serverSyncService;

	public ServerSyncRestController(@NonNull final IServerSyncService serverSyncService)
	{
		this.serverSyncService = serverSyncService;
	}

	private void assertUserAllowed()
	{
		// TODO
	}

	@GetMapping("/pullFromAppServer")
	public void pullFromAppServer()
	{
		assertUserAllowed();
		serverSyncService.syncAllAsync();
	}

	@GetMapping("/pushReportProductSupplyById")
	public void pushReportProductSupplyById(@RequestParam("product_supply_id") final long product_supply_id)
	{
		assertUserAllowed();
		serverSyncService.pushReportProductSupplyById(product_supply_id);
	}
}
