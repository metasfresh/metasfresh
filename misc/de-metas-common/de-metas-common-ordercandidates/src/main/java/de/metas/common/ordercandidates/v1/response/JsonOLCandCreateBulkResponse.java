package de.metas.common.ordercandidates.v1.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import de.metas.common.rest_api.v1.JsonErrorItem;
import de.metas.common.util.Check;
import lombok.EqualsAndHashCode;
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
	public static JsonOLCandCreateBulkResponse ok(@NonNull final List<JsonOLCand> olCands)
	{
		return new JsonOLCandCreateBulkResponse(olCands, null);
	}

	public static JsonOLCandCreateBulkResponse error(@NonNull final JsonErrorItem error)
	{
		final List<JsonOLCand> olCands = null;
		return new JsonOLCandCreateBulkResponse(olCands, ImmutableList.of(error));
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final List<JsonOLCand> result;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final List<JsonErrorItem> errors;

	@JsonCreator
	private JsonOLCandCreateBulkResponse(
			@JsonProperty("result") @Nullable final List<JsonOLCand> olCands,
			@JsonProperty("errors") @Nullable @Singular final List<JsonErrorItem> errors)
	{
		if (errors == null || errors.isEmpty())
		{
			this.result = olCands != null ? ImmutableList.copyOf(olCands) : ImmutableList.of();
			this.errors = ImmutableList.of();
		}
		else
		{
			Check.assume(olCands == null || olCands.isEmpty(), "No olCands shall be provided when error");
			this.result = null;
			this.errors = ImmutableList.copyOf(errors);
		}
	}

	public boolean isError()
	{
		return !errors.isEmpty();
	}

	public JsonErrorItem getError()
	{
		if (errors.isEmpty())
		{
			throw new IllegalStateException("Not an error result: " + this);
		}
		return Check.singleElement(errors);
	}

	public List<JsonOLCand> getResult()
	{
		if (!errors.isEmpty())
		{
			throw new IllegalStateException("Not a successful result: " + this, getError().getThrowable());
		}
		return result;
	}
}
