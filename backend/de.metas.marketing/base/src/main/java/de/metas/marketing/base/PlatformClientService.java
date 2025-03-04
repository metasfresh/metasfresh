package de.metas.marketing.base;

import de.metas.marketing.base.model.Platform;
import de.metas.marketing.base.model.PlatformId;
import de.metas.marketing.base.model.PlatformRepository;
import de.metas.marketing.base.spi.PlatformClient;
import de.metas.util.Check;
import lombok.NonNull;
import org.springframework.stereotype.Service;

/*
 * #%L
 * marketing-base
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
public class PlatformClientService
{
	private final PlatformRepository platformRepository;

	private final PlatformClientFactoryRegistry platformClientFactoryRegistry;

	public PlatformClientService(
			@NonNull final PlatformRepository platformRepository,
			@NonNull final PlatformClientFactoryRegistry platformClientFactoryRegistry)
	{
		this.platformRepository = platformRepository;
		this.platformClientFactoryRegistry = platformClientFactoryRegistry;
	}

	public PlatformClient createPlatformClient(@NonNull final PlatformId platformId)
	{
		final Platform platform = platformRepository.getById(platformId);
		final String platformGatewayId = platform.getPlatformGatewayId();

		Check.errorUnless(platformClientFactoryRegistry.hasGatewaySupport(platformGatewayId),
				"There is no support for the platformGatewayId={} of this platform; platform={}",
				platformGatewayId, platform);

		return platformClientFactoryRegistry
				.getPlatformClientFactory(platformGatewayId)
				.newClientForPlatformId(platformId);
	}
}
