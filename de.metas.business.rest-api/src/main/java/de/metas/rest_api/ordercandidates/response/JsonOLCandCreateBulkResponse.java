package de.metas.rest_api.ordercandidates.response;

import java.util.List;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import de.metas.rest_api.utils.JsonError;
import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

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
	public static JsonOLCandCreateBulkResponse ok(@NonNull final List<JsonOLCand> olCands)
	{
		final JsonError error = null;
		return new JsonOLCandCreateBulkResponse(olCands, error);
	}

	public static JsonOLCandCreateBulkResponse error(@NonNull final JsonError error)
	{
		final List<JsonOLCand> olCands = null;
		return new JsonOLCandCreateBulkResponse(olCands, error);
	}

	@JsonProperty("result")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final List<JsonOLCand> result;

	@JsonProperty("error")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final JsonError error;

	@JsonCreator
	private JsonOLCandCreateBulkResponse(
			@JsonProperty("result") @Nullable final List<JsonOLCand> olCands,
			@JsonProperty("error") @Nullable final JsonError error)
	{
		this.error = error;
		if (error == null)
		{
			result = olCands != null ? ImmutableList.copyOf(olCands) : ImmutableList.of();
		}
		else
		{
			Check.assume(olCands == null || olCands.isEmpty(), "No olCands shall be provided when error");
			result = null;
		}
	}

	public boolean isError()
	{
		return error != null;
	}

	public JsonError getError()
	{
		if (error == null)
		{
			throw new IllegalStateException("Not an error result: " + this);
		}
		return error;
	}

	public List<JsonOLCand> getResult()
	{
		if (error != null)
		{
			throw new IllegalStateException("Not an successful result: " + this, error.getThrowable());
		}
		return result;
	}
}
