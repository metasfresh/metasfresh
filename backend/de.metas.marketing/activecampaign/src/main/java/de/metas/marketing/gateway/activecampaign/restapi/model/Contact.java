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
import de.metas.marketing.base.model.ContactPerson;
import de.metas.marketing.base.model.ContactPersonRemoteUpdate;
import de.metas.marketing.base.model.DeactivatedOnRemotePlatform;
import de.metas.marketing.base.model.EmailAddress;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = Contact.ContactBuilder.class)
public class Contact
{
	@NonNull
	@JsonProperty("email")
	String email;

	@Nullable
	@JsonProperty("id")
	String id;

	@Nullable
	@JsonProperty("firstName")
	String firstName;

	@Nullable
	@JsonProperty("lastName")
	String lastName;

	@NonNull
	public ContactPersonRemoteUpdate toContactPersonUpdate()
	{
		return ContactPersonRemoteUpdate.builder()
				.remoteId(String.valueOf(id))
				.address(EmailAddress.of(email, DeactivatedOnRemotePlatform.NO))
				.build();
	}

	@NonNull
	public static Contact of(@NonNull final ContactPerson contactPerson)
	{
		return Contact.builder()
				.email(contactPerson.getEmailAddressStringOrNull())
				.build();
	}
}
