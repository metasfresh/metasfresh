package de.metas.marketing.gateway.cleverreach.restapi.models;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.metas.marketing.base.model.ContactPerson;
import de.metas.marketing.base.model.ContactPersonRemoteUpdate;
import de.metas.marketing.base.model.EmailAddress;
import de.metas.util.lang.CoalesceUtil;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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
@Builder
public class Receiver
{
	/**
	 * Note that for now we don't include the email's active status, because the remote system is the source of truth for that.
	 */
	public static Receiver of(@NonNull final ContactPerson contactPerson)
	{
		final String idString = CoalesceUtil.firstNotEmptyTrimmed(contactPerson.getRemoteId(), "0");
		return Receiver.builder()
				.id(Integer.parseInt(idString))
				.email(contactPerson.getEmailAddessStringOrNull())
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

	public ContactPersonRemoteUpdate toContactPersonUpdate()
	{
		return ContactPersonRemoteUpdate.builder()
				.remoteId(String.valueOf(id))
				.address(EmailAddress.of(email, !active))
				.build();
	}
}
