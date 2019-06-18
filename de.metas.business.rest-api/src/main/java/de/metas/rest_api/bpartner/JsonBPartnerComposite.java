package de.metas.rest_api.bpartner;

import static de.metas.rest_api.bpartner.SwaggerDocConstants.BPARTER_SYNC_ADVISE_DOC;
import static de.metas.util.Check.isEmpty;
import static de.metas.util.lang.CoalesceUtil.coalesce;

import java.util.List;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import de.metas.rest_api.JsonExternalId;
import de.metas.rest_api.SyncAdvise;
import de.metas.rest_api.ordercandidates.JsonOrganization;
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
public final class JsonBPartnerComposite
{
	public static JsonBPartnerComposite ofOrNull(@Nullable final JsonBPartnerInfo jsonBPartnerInfo)
	{
		if (jsonBPartnerInfo == null)
		{
			return null;
		}
		return JsonBPartnerComposite.builder()
				.bpartner(jsonBPartnerInfo.getBpartner())
				.contact(jsonBPartnerInfo.getContact())
				.location(jsonBPartnerInfo.getLocation())
				.syncAdvise(jsonBPartnerInfo.getSyncAdvise())
				.build();

	}

	// TODO if an org is given, then verify whether the current user has access to the given org
	@ApiModelProperty(required = false)
	@JsonInclude(Include.NON_NULL)
	JsonOrganization org;

	JsonBPartner bpartner;

	@ApiModelProperty(required = false, value = "The location's GLN can be used to lookup the whole bpartner; if nultiple locations with GLN are provided, then only the first one is used")
	@JsonInclude(Include.NON_EMPTY)
	List<JsonBPartnerLocation> locations;

	@JsonInclude(Include.NON_EMPTY)
	List<JsonContact> contacts;

	@ApiModelProperty(required = false, value = BPARTER_SYNC_ADVISE_DOC)
	@JsonInclude(Include.NON_NULL)
	SyncAdvise syncAdvise;

	@Builder(toBuilder = true)
	@JsonCreator
	private JsonBPartnerComposite(
			@JsonProperty("org") @Nullable final JsonOrganization org,
			@JsonProperty("bpartner") @NonNull final JsonBPartner bpartner,
			@JsonProperty("locations") @Singular final List<JsonBPartnerLocation> locations,
			@JsonProperty("contacts") @Singular final List<JsonContact> contacts,
			@JsonProperty("syncAdvise") final SyncAdvise syncAdvise)
	{
		this.org = org;
		this.bpartner = bpartner;
		this.locations = coalesce(locations, ImmutableList.of());
		this.contacts = coalesce(contacts, ImmutableList.of());
		this.syncAdvise = coalesce(syncAdvise, SyncAdvise.READ_ONLY);

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
				.map(JsonBPartnerLocation::getGln)
				.filter(gln -> !isEmpty(gln, true))
				.collect(ImmutableList.toImmutableList());
	}

	public JsonBPartnerComposite withExternalId(@NonNull final JsonExternalId externalId)
	{
		return toBuilder()
				.bpartner(bpartner.toBuilder().externalId(externalId).build())
				.build();
	}
}
