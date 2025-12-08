package de.metas.marketing.gateway.cleverreach.restapi.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.marketing.base.model.ContactPerson;
import de.metas.marketing.base.model.ContactPersonRemoteUpdate;
import de.metas.marketing.base.model.DeactivatedOnRemotePlatform;
import de.metas.marketing.base.model.EmailAddress;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.util.Map;

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

@JsonIgnoreProperties(ignoreUnknown = true)
@Value
public class Receiver
{
	/**
	 * Note that for now we don't include the email's active status, because the remote system is the source of truth for that.
	 */
	public static Receiver of(@NonNull final ContactPerson contactPerson)
	{
		final int id = StringUtils.trimBlankToOptional(contactPerson.getRemoteId()).map(Integer::parseInt).orElse(0);

		return builder()
				.id(id)
				.email(contactPerson.getEmailAddressStringOrNull())
				.build();
	}

	int id;

	String email;

	int imported;
	int points;
	int bounced;
	String last_ip;
	String last_location;
	String last_client;
	int groups_id;
	long activated;
	long registered;
	long deactivated;
	String source;
	boolean active;
	int stars;
	Map<String, String> global_attributes;
	Map<String, String> attributes;

	@JsonCreator
	@Builder
	public Receiver(
			@JsonProperty("id") int id,
			@JsonProperty("email") String email,
			@JsonProperty("imported") int imported,
			@JsonProperty("points") int points,
			@JsonProperty("bounced") int bounced,
			@JsonProperty("last_ip") String last_ip,
			@JsonProperty("last_location") String last_location,
			@JsonProperty("last_client") String last_client,
			@JsonProperty("groups_id") int groups_id,
			@JsonProperty("activated") long activated,
			@JsonProperty("registered") long registered,
			@JsonProperty("deactivated") long deactivated,
			@JsonProperty("source") String source,
			@JsonProperty("active") boolean active,
			@JsonProperty("stars") int stars,
			@JsonProperty("global_attributes") @Singular Map<String, String> global_attributes,
			@JsonProperty("attributes") @Singular Map<String, String> attributes)
	{
		this.id = id;
		this.email = email;
		this.imported = imported;
		this.points = points;
		this.bounced = bounced;
		this.last_ip = last_ip;
		this.last_location = last_location;
		this.last_client = last_client;
		this.groups_id = groups_id;
		this.activated = activated;
		this.registered = registered;
		this.deactivated = deactivated;
		this.source = source;
		this.active = active;
		this.stars = stars;
		this.global_attributes = global_attributes;
		this.attributes = attributes;
	}

	public ContactPersonRemoteUpdate toContactPersonUpdate()
	{
		return ContactPersonRemoteUpdate.builder()
				.remoteId(String.valueOf(id))
				.address(EmailAddress.of(email, DeactivatedOnRemotePlatform.ofIsActiveFlag(active)))
				.build();
	}

}
