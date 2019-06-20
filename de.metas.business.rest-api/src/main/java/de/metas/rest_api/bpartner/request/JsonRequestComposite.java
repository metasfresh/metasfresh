package de.metas.rest_api.bpartner.request;

import static de.metas.rest_api.bpartner.SwaggerDocConstants.BPARTER_SYNC_ADVISE_DOC;
import static de.metas.util.Check.isEmpty;
import static de.metas.util.lang.CoalesceUtil.coalesce;

import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import de.metas.rest_api.JsonExternalId;
import de.metas.rest_api.SyncAdvise;
import de.metas.util.Check;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
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

@ApiModel(description = "A BPartner with `n` contacts and `n` locations.\n" //
		+ "Note that given the respective use-case, either `bpartner.code` `bpartner.externalId` might be `null`, but not both at once.")
@Value
public final class JsonRequestComposite
{
	// TODO if an org is given, then verify whether the current user has access to the given org
	@ApiModelProperty(required = false)
	@JsonInclude(Include.NON_NULL)
	String orgCode;

	JsonRequestBPartner bpartner;

	@ApiModelProperty(required = false, value = "The location's GLN can be used to lookup the whole bpartner; if nultiple locations with GLN are provided, then only the first one is used")
	@JsonInclude(Include.NON_EMPTY)
	List<JsonRequestLocation> locations;

	@JsonInclude(Include.NON_EMPTY)
	List<JsonRequestContact> contacts;

	@ApiModelProperty(required = false, value = "Ths advise is applied to this composite's bpartner or any of its contacts\n"
			+ BPARTER_SYNC_ADVISE_DOC)
	@JsonInclude(Include.NON_NULL)
	SyncAdvise syncAdvise;

	@Builder(toBuilder = true)
	@JsonCreator
	private JsonRequestComposite(
			@JsonProperty("orgCode") @Nullable final String orgCode,
			@JsonProperty("bpartner") @NonNull final JsonRequestBPartner bpartner,
			@JsonProperty("locations") @Singular final List<JsonRequestLocation> locations,
			@JsonProperty("contacts") @Singular final List<JsonRequestContact> contacts,
			@JsonProperty("syncAdvise") final SyncAdvise syncAdvise)
	{
		this.orgCode = orgCode;
		this.bpartner = bpartner;
		this.locations = coalesce(locations, ImmutableList.of());
		this.contacts = coalesce(contacts, ImmutableList.of());
		this.syncAdvise = syncAdvise;

		final boolean lokupValuesAreOk = !isEmpty(bpartner.getCode(), true)
				|| bpartner.getExternalId() != null
				|| !extractLocationGlns().isEmpty();
		Check.errorUnless(
				lokupValuesAreOk,
				"At least one of bpartner.code, bpartner.externalId or one location.gln needs to be non-empty; this={}", this);
	}

	public ImmutableList<String> extractLocationGlns()
	{
		return this.locations
				.stream()
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
