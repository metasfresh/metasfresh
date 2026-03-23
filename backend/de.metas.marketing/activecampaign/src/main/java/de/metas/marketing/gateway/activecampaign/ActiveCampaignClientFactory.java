/*
 * #%L
 * marketing-activecampaign
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.marketing.gateway.activecampaign;

import de.metas.marketing.base.model.PlatformGatewayId;
import de.metas.marketing.base.model.PlatformId;
import de.metas.marketing.base.spi.PlatformClient;
import de.metas.marketing.base.spi.PlatformClientFactory;
import de.metas.marketing.gateway.activecampaign.restapi.RestService;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class ActiveCampaignClientFactory implements PlatformClientFactory
{
	private final ActiveCampaignConfigRepository activeCampaignConfigRepository;
	private final RestService restService;

	public ActiveCampaignClientFactory(
			@NonNull final ActiveCampaignConfigRepository activeCampaignConfigRepository,
			@NonNull final RestService restService)
	{
		this.activeCampaignConfigRepository = activeCampaignConfigRepository;
		this.restService = restService;
	}

	@NonNull
	@Override
	public PlatformGatewayId getPlatformGatewayId()
	{
		return PlatformGatewayId.ActiveCampaign;
	}

	@NonNull
	@Override
	public PlatformClient newClientForPlatformId(@NonNull final PlatformId platformId)
	{
		final ActiveCampaignConfig config = activeCampaignConfigRepository.getByPlatformId(platformId);

		return new ActiveCampaignClient(restService, config);
	}
}
