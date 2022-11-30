package de.metas.marketing.base.model;

import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.marketing
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

@Value
public class Campaign implements DataRecord
{
	String name;

	/**
	 * the remote system's ID which we can use to sync with the campaign on the remote marketing tool
	 */
	String remoteId;

	@NonNull
	PlatformId platformId;

	/**
	 * might be null, if the campaign wasn't stored yet
	 */
	CampaignId campaignId;

	@Builder(toBuilder = true)
	public Campaign(
			@NonNull final String name,
			@Nullable final String remoteId,
			@NonNull final PlatformId platformId,
			@Nullable final CampaignId campaignId)
	{
		this.name = name;
		this.remoteId = StringUtils.trimBlankToNull(remoteId);
		this.platformId = platformId;
		this.campaignId = campaignId;
	}

	public static Campaign cast(@Nullable final DataRecord dataRecord)
	{
		return (Campaign)dataRecord;
	}
}
