package de.metas.marketing.base.model;

import org.springframework.stereotype.Repository;

import static org.adempiere.model.InterfaceWrapperHelper.load;

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
@Repository
public class PlatformRepository
{
	public Platform getById(final PlatformId plaformId)
	{
		final I_MKTG_Platform platformRecord = load(plaformId.getRepoId(), I_MKTG_Platform.class);

		return Platform.builder()
				.name(platformRecord.getName())
				.platformGatewayId(PlatformGatewayId.ofCodeOrNull(platformRecord.getMarketingPlatformGatewayId()))
				.requiredLocation(platformRecord.isRequiredLocation())
				.requiredMailAddress(platformRecord.isRequiredMailAddres())
				.platformId(PlatformId.ofRepoId(platformRecord.getMKTG_Platform_ID()))
				.build();
	}
}
