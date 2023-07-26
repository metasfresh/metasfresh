package de.metas.marketing.gateway.cleverreach;

import org.springframework.stereotype.Service;

import de.metas.marketing.base.model.PlatformId;
import de.metas.marketing.base.spi.PlatformClient;
import de.metas.marketing.base.spi.PlatformClientFactory;
import lombok.NonNull;

/*
 * #%L
 * marketing-cleverreach
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
public class CleverReachClientFactory implements PlatformClientFactory
{

	private final CleverReachConfigRepository cleverReachConfigRepository;

	public CleverReachClientFactory(@NonNull final CleverReachConfigRepository cleverReachConfigRepository)
	{
		this.cleverReachConfigRepository = cleverReachConfigRepository;
	}

	@Override
	public String getPlatformGatewayId()
	{
		return "CleverReach";
	}

	@Override
	public PlatformClient newClientForPlatformId(@NonNull final PlatformId marketingPlatformId)
	{
		final CleverReachConfig cleverReachConfig = cleverReachConfigRepository.getById(marketingPlatformId);
		return new CleverReachClient(cleverReachConfig);
	}
}
