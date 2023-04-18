package de.metas.marketing.base.model;

import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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

/**
 * Analog to {@link ContactPersonRemoteUpdate}.
 */
@Value
@Builder
public class CampaignRemoteUpdate
{
	String name;

	/** the remote system's ID which we can use to sync with the campaign on the remote marketing tool */
	String remoteId;

	@NonNull
	public Campaign toCampaign(@NonNull final PlatformId platformId, @NonNull final OrgId orgId)
	{
		return Campaign.builder()
				.platformId(platformId)
				.orgId(orgId)
				.name(name)
				.remoteId(remoteId)
				.build();
	}

	public Campaign update(@NonNull final Campaign campaignToUpdate)
	{
		return campaignToUpdate.toBuilder()
				.name(name)
				.remoteId(remoteId)
				.build();
	}
}
