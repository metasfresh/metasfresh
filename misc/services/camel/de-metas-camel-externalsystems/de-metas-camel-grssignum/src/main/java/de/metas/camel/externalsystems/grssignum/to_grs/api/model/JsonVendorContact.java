/*
 * #%L
 * metas
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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

@Value
@JsonDeserialize(builder = JsonVendorContact.JsonVendorContactBuilder.class)
public class JsonVendorContact
{
	@NonNull
	@JsonProperty("METASFRESHID")
	JsonMetasfreshId metasfreshId;

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

	@JsonProperty("ROLLEN")
	List<String> contactRoles;

	@Builder
	public JsonVendorContact(
			@JsonProperty("METASFRESHID") final @NonNull JsonMetasfreshId metasfreshId,
			@JsonProperty("NACHNAME") final @Nullable String lastName,
			@JsonProperty("VORNAME") final @Nullable String firstName,
			@JsonProperty("ANREDE") final @Nullable String greeting,
			@JsonProperty("TITEL") final @Nullable String title,
			@JsonProperty("POSITION") final @Nullable String position,
			@JsonProperty("EMAIL") final @Nullable String email,
			@JsonProperty("TELEFON") final @Nullable String phone,
			@JsonProperty("MOBIL") final @Nullable String phone2,
			@JsonProperty("FAX") final @Nullable String fax,
			@JsonProperty("ROLLEN") final @Nullable List<String> contactRoles)
	{
		this.metasfreshId = metasfreshId;
		this.lastName = lastName;
		this.firstName = firstName;
		this.greeting = greeting;
		this.title = title;
		this.position = position;
		this.email = email;
		this.phone = phone;
		this.phone2 = phone2;
		this.fax = fax;
		this.contactRoles = contactRoles;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonPOJOBuilder(withPrefix = "")
	public static class JsonVendorContactBuilder
	{
	}
}
