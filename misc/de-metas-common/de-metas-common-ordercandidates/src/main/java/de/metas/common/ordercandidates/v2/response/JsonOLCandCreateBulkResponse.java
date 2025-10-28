package de.metas.common.ordercandidates.v2.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import de.metas.common.rest_api.v2.JsonErrorItem;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.List;

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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@ToString(doNotUseGetters = true)
@EqualsAndHashCode(doNotUseGetters = true)
public final class JsonOLCandCreateBulkResponse
{
	@NonNull
	public static JsonOLCandCreateBulkResponse multiStatus(@NonNull final List<JsonOLCand> olCands, @NonNull @Singular final List<JsonErrorItem> errors)
	{
		return new JsonOLCandCreateBulkResponse(olCands, errors);
	}

	@NonNull
	public static JsonOLCandCreateBulkResponse ok(@NonNull final List<JsonOLCand> olCands)
	{
		return new JsonOLCandCreateBulkResponse(olCands, null);
	}

	@NonNull
	public static JsonOLCandCreateBulkResponse error(@NonNull final JsonErrorItem error)
	{
		return new JsonOLCandCreateBulkResponse(null, ImmutableList.of(error));
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Getter
	private final List<JsonOLCand> result;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Getter
	private final List<JsonErrorItem> errors;

	@JsonCreator
	private JsonOLCandCreateBulkResponse(
			@JsonProperty("result") @Nullable final List<JsonOLCand> olCands,
			@JsonProperty("errors") @Nullable @Singular final List<JsonErrorItem> errors)
	{
		this.result = olCands != null ? ImmutableList.copyOf(olCands) : ImmutableList.of();
		this.errors = errors != null ? ImmutableList.copyOf(errors) : ImmutableList.of();
	}
}
