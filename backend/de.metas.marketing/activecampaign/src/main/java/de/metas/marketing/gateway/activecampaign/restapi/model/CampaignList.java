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

package de.metas.marketing.gateway.activecampaign.restapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.metas.marketing.gateway.activecampaign.ActiveCampaignConfig;
import de.metas.marketing.base.model.Campaign;
import de.metas.marketing.base.model.CampaignRemoteUpdate;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = CampaignList.CampaignListBuilder.class)
public class CampaignList
{
	@NonNull
	@JsonProperty("name")
	String name;

	@Nullable
	@JsonProperty("id")
	String id;

	@Nullable
	@JsonProperty("stringid")
	String stringid;

	@Nullable
	@JsonProperty("sender_url")
	String sender_url;

	@Nullable
	@JsonProperty("sender_reminder")
	String sender_reminder;

	@NonNull
	public CampaignRemoteUpdate toCampaignUpdate()
	{
		return CampaignRemoteUpdate.builder()
				.remoteId(String.valueOf(id))
				.name(name)
				.build();
	}

	public static CampaignList of(@NonNull final Campaign campaign, @NonNull final ActiveCampaignConfig config)
	{
		return CampaignList.builder()
				.name(campaign.getName())
				.stringid(campaign.getName())
				.sender_url(config.getBaseUrl())
				.sender_reminder("This list was created from Metasfresh platform")
				.build();
	}
}

