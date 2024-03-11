/*
 * #%L
 * de-metas-camel-grssignum
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

package de.metas.camel.externalsystems.grssignum.to_grs.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@JsonDeserialize(builder = JsonCustomerContact.JsonCustomerContactBuilder.class)
public class JsonCustomerContact
{
	@NonNull
	@JsonProperty("METASFRESHID")
	JsonMetasfreshId metasfreshId;

	@JsonProperty("FULLNAME")
	String fullName;

	@JsonProperty("NACHNAME")
	String lastName;

	@JsonProperty("VORNAME")
	String firstName;

	@JsonProperty("ANREDE")
	String greeting;

	@JsonProperty("TITEL")
	String title;

	@JsonProperty("POSITION")
	String position;

	@JsonProperty("EMAIL")
	String email;

	@JsonProperty("TELEFON")
	String phone;

	@JsonProperty("MOBIL")
	String phone2;

	@JsonProperty("FAX")
	String fax;

	@JsonProperty("INAKTIV")
	Integer inactiveBit;

	@Builder
	public JsonCustomerContact(
			@JsonProperty("METASFRESHID") @NonNull final JsonMetasfreshId metasfreshId,
			@JsonProperty("FULLNAME") @Nullable final String fullName,
			@JsonProperty("NACHNAME") @Nullable final String lastName,
			@JsonProperty("VORNAME") @Nullable final String firstName,
			@JsonProperty("ANREDE") @Nullable final String greeting,
			@JsonProperty("TITEL") @Nullable final String title,
			@JsonProperty("POSITION") @Nullable final String position,
			@JsonProperty("EMAIL") @Nullable final String email,
			@JsonProperty("TELEFON") @Nullable final String phone,
			@JsonProperty("MOBIL") @Nullable final String phone2,
			@JsonProperty("FAX") @Nullable final String fax,
			@JsonProperty("INAKTIV") final int inactive)
	{
		this.metasfreshId = metasfreshId;
		this.fullName = fullName;
		this.lastName = lastName;
		this.firstName = firstName;
		this.greeting = greeting;
		this.title = title;
		this.position = position;
		this.email = email;
		this.phone = phone;
		this.phone2 = phone2;
		this.fax = fax;
		this.inactiveBit = inactive;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonPOJOBuilder(withPrefix = "")
	public static class JsonCustomerContactBuilder
	{
	}

	@JsonIgnore
	public boolean isActive()
	{
		return inactiveBit != 1;
	}
}