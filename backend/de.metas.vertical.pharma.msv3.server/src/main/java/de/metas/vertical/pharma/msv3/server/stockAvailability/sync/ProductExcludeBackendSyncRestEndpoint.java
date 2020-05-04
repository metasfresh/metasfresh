package de.metas.vertical.pharma.msv3.server.stockAvailability.sync;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.metas.vertical.pharma.msv3.server.MSV3ServerConstants;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3ProductExcludesUpdateEvent;
import de.metas.vertical.pharma.msv3.server.stockAvailability.StockAvailabilityService;

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

@RestController
@RequestMapping(ProductExcludeBackendSyncRestEndpoint.ENDPOINT)
public class ProductExcludeBackendSyncRestEndpoint
{
	public static final String ENDPOINT = MSV3ServerConstants.BACKEND_SYNC_REST_ENDPOINT + "/productExclude";

	@Autowired
	private StockAvailabilityService stockAvailabilityService;

	@PostMapping
	public void onEvent(@RequestBody final MSV3ProductExcludesUpdateEvent event)
	{
		stockAvailabilityService.handleEvent(event);
	}

}
