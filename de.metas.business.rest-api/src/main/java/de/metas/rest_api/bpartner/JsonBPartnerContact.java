package de.metas.rest_api.bpartner;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.rest_api.JsonExternalId;
import de.metas.rest_api.MetasfreshId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Value;

/*
 * #%L
 * de.metas.ordercandidate.rest-api
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
public class JsonBPartnerContact
{
	@ApiModelProperty(dataType = "java.lang.Long")
	MetasfreshId metasfreshId;

	@ApiModelProperty(dataType = "java.lang.String")
	JsonExternalId externalId;

	@JsonInclude(Include.NON_NULL)
	String name;

	@JsonInclude(Include.NON_NULL)
	String lastName;

	@JsonInclude(Include.NON_NULL)
	String firstName;

	@JsonInclude(Include.NON_NULL)
	String email;

	@JsonInclude(Include.NON_NULL)
	String phone;

	@Builder
	@JsonCreator
	private JsonBPartnerContact(
			@JsonProperty("externalId") @Nullable final JsonExternalId externalId,
			@JsonProperty("metasfreshId") @Nullable final MetasfreshId metasfreshId,
			@JsonProperty("name") final String name,
			@JsonProperty("firstName") final String firstName,
			@JsonProperty("lastName") final String lastName,
			@JsonProperty("email") final String email,
			@JsonProperty("phone") final String phone)
	{
		this.metasfreshId = metasfreshId;
		this.externalId = externalId;
		this.name = name;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
	}
}
