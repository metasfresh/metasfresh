package de.metas.rest_api.bpartner.request;

import static de.metas.rest_api.bpartner.SwaggerDocConstants.READ_ONLY_SYNC_ADVISE_DOC;
import static de.metas.util.Check.isEmpty;
import static de.metas.util.lang.CoalesceUtil.coalesce;

import java.util.Objects;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import de.metas.rest_api.JsonExternalId;
import de.metas.rest_api.SyncAdvise;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
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
	JsonRequestLocationUpsert locations;

	@JsonInclude(Include.NON_EMPTY)
	JsonRequestContactUpsert contacts;

	@ApiModelProperty(required = false, value = "Ths advise is applied to this composite's bpartner or any of its contacts\n"
			+ READ_ONLY_SYNC_ADVISE_DOC)
	@JsonInclude(Include.NON_NULL)
	SyncAdvise syncAdvise;

	@Builder(toBuilder = true)
	@JsonCreator
	private JsonRequestComposite(
			@JsonProperty("orgCode") @Nullable final String orgCode,
			@JsonProperty("bpartner") @NonNull final JsonRequestBPartner bpartner,
			@JsonProperty("locations") @Nullable final JsonRequestLocationUpsert locations,
			@JsonProperty("contacts") @Nullable final JsonRequestContactUpsert contacts,
			@JsonProperty("syncAdvise") final SyncAdvise syncAdvise)
	{
		this.orgCode = orgCode;
		this.bpartner = bpartner;
		this.locations = coalesce(locations, JsonRequestLocationUpsert.builder().build());
		this.contacts = coalesce(contacts, JsonRequestContactUpsert.builder().build());
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

	public JsonRequestComposite withExternalId(@NonNull final JsonExternalId externalId)
	{
		if (Objects.equals(externalId, bpartner.getExternalId()))
		{
			return this; // nothing to do
		}
		return toBuilder()
				.bpartner(bpartner.toBuilder().externalId(externalId).build())
				.build();
	}
}
