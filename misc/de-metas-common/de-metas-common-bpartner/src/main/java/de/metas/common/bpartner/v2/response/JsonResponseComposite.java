/*
 * #%L
 * de-metas-common-bpartner
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

package de.metas.common.bpartner.v2.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import de.metas.common.util.EmptyUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

import static de.metas.common.util.CoalesceUtil.coalesce;

@Schema(description = "A BPartner with `n` contacts and `n` locations.\n" //
		+ "Note that given the respective use-case, either `bpartner.code` `bpartner.externalId` might be `null`, but not both at once.")
@Value
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class JsonResponseComposite
{
	// TODO if an org is given, then verify whether the current user has access to the given org
	@Schema
	@JsonInclude(Include.NON_NULL)
	String orgCode;

	JsonResponseBPartner bpartner;

	@Schema(description = "The location's GLN can be used to lookup the whole bpartner; if multiple locations with GLN are provided, then only the first one is used")
	@JsonInclude(Include.NON_EMPTY)
	List<JsonResponseLocation> locations;

	@JsonInclude(Include.NON_EMPTY)
	List<JsonResponseContact> contacts;

	@Builder(toBuilder = true)
	@JsonCreator
	private JsonResponseComposite(
			@JsonProperty("orgCode") @Nullable final String orgCode,
			@JsonProperty("bpartner") @NonNull final JsonResponseBPartner bpartner,
			@JsonProperty("locations") @Singular final List<JsonResponseLocation> locations,
			@JsonProperty("contacts") @Singular final List<JsonResponseContact> contacts)
	{
		this.orgCode = orgCode;
		this.bpartner = bpartner;
		this.locations = coalesce(locations, ImmutableList.of());
		this.contacts = coalesce(contacts, ImmutableList.of());
	}

	public ImmutableList<String> extractLocationGlns()
	{
		return this.locations
				.stream()
				.map(JsonResponseLocation::getGln)
				.filter(gln -> !EmptyUtil.isEmpty(gln, true))
				.collect(ImmutableList.toImmutableList());
	}

}
