/*
 * #%L
 * de.metas.business.rest-api
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.rest_api.bpartner.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.rest_api.common.JsonBPRelationRole;
import de.metas.rest_api.common.JsonExternalId;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@ApiModel(description = "A BPartner to BPartner relation")
@Value
public class JsonResponseBPRelationItem
{
	@JsonProperty
	Integer bpartnerId;

	@JsonProperty
	@Nullable
	Integer locationId;

	@JsonProperty
	Integer targetBPartnerId;

	@JsonProperty
	Integer targetBPLocationId;

	@JsonProperty
	String name;

	@JsonProperty
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String description;

	@JsonProperty
	JsonExternalId externalId;

	@JsonProperty
	JsonBPRelationRole role;

	@JsonProperty
	boolean billTo;

	@JsonProperty
	boolean fetchedFrom;

	@JsonProperty
	boolean handoverLocation;

	@JsonProperty
	boolean payFrom;

	@JsonProperty
	boolean remitTo;

	@JsonProperty
	boolean shipTo;

	@JsonProperty
	boolean active;

	@Builder(toBuilder = true)
	@JsonCreator
	private JsonResponseBPRelationItem(
			@JsonProperty("bpartnerId") @NonNull final Integer bpartnerId,
			@JsonProperty("locationId") @Nullable final Integer locationId,
			@JsonProperty("targetBPartnerId") @NonNull final Integer targetBPartnerId,
			@JsonProperty("targetBPLocationId") @NonNull final Integer targetBPLocationId,
			@JsonProperty("name") @NonNull final String name,
			@JsonProperty("description") final String description,
			@JsonProperty("externalId") final JsonExternalId externalId,
			@JsonProperty("role") final JsonBPRelationRole role,
			@JsonProperty("shipTo") final Boolean shipTo,
			@JsonProperty("payFrom") final Boolean payFrom,
			@JsonProperty("billTo") final Boolean billTo,
			@JsonProperty("handoverLocation") final Boolean handoverLocation,
			@JsonProperty("fetchedFrom") final Boolean fetchedFrom,
			@JsonProperty("remitTo") final Boolean remitTo,
			@JsonProperty("active") final boolean active)
	{
		this.bpartnerId = bpartnerId;
		this.locationId = locationId;
		this.targetBPartnerId = targetBPartnerId;
		this.targetBPLocationId = targetBPLocationId;
		this.name = name;
		this.description = description;
		this.externalId = externalId;
		this.role = role;
		this.shipTo = shipTo;
		this.payFrom = payFrom;
		this.billTo = billTo;
		this.handoverLocation = handoverLocation;
		this.fetchedFrom = fetchedFrom;
		this.remitTo = remitTo;
		this.active = active;
	}
}
