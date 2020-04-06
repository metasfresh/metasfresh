package de.metas.marketing.gateway.cleverreach.restapi.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.marketing.base.model.Campaign;
import de.metas.marketing.base.model.Campaign.CampaignBuilder;
import de.metas.marketing.base.model.CampaignRemoteUpdate;
import lombok.NonNull;
import lombok.Value;

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

@JsonIgnoreProperties(ignoreUnknown = true)
@Value
public class Group
{
	public Campaign toCampaign()
	{
		return finishAndBuild(Campaign.builder());
	}

	public Campaign updateCampaign(@NonNull final Campaign campaign)
	{
		return finishAndBuild(campaign.toBuilder());
	}

	private Campaign finishAndBuild(@NonNull final CampaignBuilder builder)
	{
		return builder
				.name(name)
				.remoteId(String.valueOf(id))
				.build();
	}

	String name;
	int id;
	long stamp;
	long last_mailing;
	long last_changed;
	boolean isLocked;

	@JsonCreator
	public Group(
			@JsonProperty("name") @NonNull final String name,
			@JsonProperty("id") int id,
			@JsonProperty("stamp") long stamp,
			@JsonProperty("last_mailing") long last_mailing,
			@JsonProperty("last_changed") long last_changed,
			@JsonProperty("isLocked") boolean isLocked)
	{
		this.name = name;
		this.id = id;
		this.stamp = stamp;
		this.last_mailing = last_mailing;
		this.last_changed = last_changed;
		this.isLocked = isLocked;
	}

	public CampaignRemoteUpdate toCampaignUpdate()
	{
		return CampaignRemoteUpdate.builder()
				.remoteId(String.valueOf(id))
				.name(name)
				.build();
	}

}
