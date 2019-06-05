package de.metas.rest_api.bpartner;

import static de.metas.util.Check.isEmpty;
import static de.metas.util.lang.CoalesceUtil.coalesce;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import de.metas.util.Check;
import io.swagger.annotations.ApiModel;
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
	JsonBPartner bpartner;

	@JsonInclude(Include.NON_EMPTY)
	List<JsonBPartnerLocation> locations;

	@JsonInclude(Include.NON_EMPTY)
	List<JsonBPartnerContact> contacts;

	@Builder(toBuilder = true)
	@JsonCreator
	private JsonBPartnerComposite(
			@JsonProperty("bpartner") @NonNull final JsonBPartner bpartner,
			@JsonProperty("locations") @Singular final List<JsonBPartnerLocation> locations,
			@JsonProperty("contacts") @Singular final List<JsonBPartnerContact> contacts)
	{
		this.bpartner = bpartner;
		this.locations = coalesce(locations, ImmutableList.of());
		this.contacts = coalesce(contacts, ImmutableList.of());;

		final boolean lokupValuesAreOk = !isEmpty(bpartner.getCode(), true)
				|| bpartner.getExternalId() != null;
		Check.errorUnless(
				lokupValuesAreOk,
				"At least one of bpartner.code, bpartner.externalId or location.gln needs to be non-empty; this={}", this);
	}

}
