package de.metas.rest_api.bpartner.request;

import static de.metas.rest_api.bpartner.SwaggerDocConstants.READ_ONLY_SYNC_ADVISE_DOC;
import static de.metas.util.Check.isEmpty;
import static de.metas.util.lang.CoalesceUtil.coalesce;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import de.metas.rest_api.SyncAdvise;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
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

@ApiModel(description = "A BPartner with `n` contacts and `n` locations.\n")
@Value
public final class JsonRequestComposite
{
	// TODO if an org is given, then verify whether the current user has access to the given org
	@ApiModelProperty(required = false)
	@JsonInclude(Include.NON_NULL)
	String orgCode;

	JsonRequestBPartner bpartner;

	@ApiModelProperty(required = false, value = "The location's GLN can be used to lookup the whole bpartner; if multiple locations with GLN are provided, then only the first one is used")
	@JsonInclude(Include.NON_EMPTY)
	@JsonProperty("locations")
	@Getter(AccessLevel.PRIVATE)
	JsonRequestLocationUpsert locations;

	@JsonInclude(Include.NON_EMPTY)
	@JsonProperty("contacts")
	@Getter(AccessLevel.PRIVATE)
	JsonRequestContactUpsert contacts;

	@ApiModelProperty(required = false, value = "Ths advise is applied to this composite's bpartner or any of its contacts\n"
			+ READ_ONLY_SYNC_ADVISE_DOC)
	@JsonInclude(Include.NON_NULL)
	SyncAdvise syncAdvise;

	@Builder(toBuilder = true)
	@JsonCreator
	private JsonRequestComposite(
			@JsonProperty("orgCode") @Nullable final String orgCode,
			@JsonProperty("bpartner") @Nullable final JsonRequestBPartner bpartner,
			@JsonProperty("locations") @Nullable final JsonRequestLocationUpsert locations,
			@JsonProperty("contacts") @Nullable final JsonRequestContactUpsert contacts,
			@JsonProperty("syncAdvise") final SyncAdvise syncAdvise)
	{
		this.orgCode = orgCode;
		this.bpartner = bpartner;
		this.locations = locations;
		this.contacts = contacts;
		this.syncAdvise = syncAdvise;
	}

	public ImmutableList<String> extractLocationGlns()
	{
		return this.locations.getRequestItems().stream()
				.map(JsonRequestLocationUpsertItem::getLocation)
				.map(JsonRequestLocation::getGln)
				.filter(gln -> !isEmpty(gln, true))
				.collect(ImmutableList.toImmutableList());
	}

	@JsonIgnore
	public JsonRequestLocationUpsert getLocationsNotNull()
	{
		return coalesce(locations, JsonRequestLocationUpsert.builder().build());
	}

	@JsonIgnore
	public JsonRequestContactUpsert getContactsNotNull()
	{
		return coalesce(contacts, JsonRequestContactUpsert.builder().build());
	}
}
