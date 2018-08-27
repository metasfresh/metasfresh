package de.metas.vertical.pharma.msv3.server.security.sync;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableList;

import de.metas.vertical.pharma.msv3.server.MSV3ServerConstants;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3UserChangedBatchEvent;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3UserChangedEvent;
import de.metas.vertical.pharma.msv3.server.peer.service.MSV3ServerPeerService;
import de.metas.vertical.pharma.msv3.server.security.MSV3ServerAuthenticationService;
import de.metas.vertical.pharma.msv3.server.security.MSV3User;

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
@RequestMapping(UserSyncRestEndpoint.ENDPOINT)
public class UserSyncRestEndpoint
{
	public static final String ENDPOINT = MSV3ServerConstants.BACKEND_SYNC_REST_ENDPOINT + "/users";

	@Autowired
	private MSV3ServerAuthenticationService authService;
	@Autowired
	private MSV3ServerPeerService msv3ServerPeerService;

	@PostMapping
	public void processBatchEvent(@RequestBody final MSV3UserChangedBatchEvent batchEvent)
	{
		authService.handleEvent(batchEvent);
	}

	// TODO: remove this endpoint, it's only for testing/debugging
	@PostMapping("/toRabbitMQ")
	public void forwardEventToRabbitMQ(@RequestBody final MSV3UserChangedEvent event)
	{
		msv3ServerPeerService.publishUserChangedEvent(event);
	}

	@GetMapping
	public List<MSV3UserChangedEvent> getAllUsers()
	{
		return authService.getAllUsers()
				.stream()
				.map(this::toMSV3UserChangedEvent)
				.collect(ImmutableList.toImmutableList());
	}

	private MSV3UserChangedEvent toMSV3UserChangedEvent(final MSV3User user)
	{
		return MSV3UserChangedEvent.prepareCreatedOrUpdatedEvent()
				.username(user.getUsername())
				.password("N/A")
				.bpartnerId(user.getBpartnerId().getBpartnerId())
				.bpartnerLocationId(user.getBpartnerId().getBpartnerLocationId())
				.build();
	}
}
