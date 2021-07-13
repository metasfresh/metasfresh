/*
 * #%L
 * de-metas-common-ordercandidates
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

package de.metas.common.ordercandidates.v1.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import de.metas.common.rest_api.v1.SyncAdvise;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.UnaryOperator;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class JsonOLCandCreateBulkRequest
{
	public static JsonOLCandCreateBulkRequest of(@NonNull final JsonOLCandCreateRequest request)
	{
		return new JsonOLCandCreateBulkRequest(ImmutableList.of(request));
	}

	@JsonProperty("requests")
	List<JsonOLCandCreateRequest> requests;

	@JsonCreator
	@Builder
	private JsonOLCandCreateBulkRequest(@JsonProperty("requests") @Singular final List<JsonOLCandCreateRequest> requests)
	{
		this.requests = ImmutableList.copyOf(requests);
	}

	public JsonOLCandCreateBulkRequest validate()
	{
		for (final JsonOLCandCreateRequest request : requests)
		{
			request.validate();
		}
		return this;
	}

	public JsonOLCandCreateBulkRequest withOrgSyncAdvise(@Nullable final SyncAdvise syncAdvise)
	{
		return syncAdvise != null
				? map(request -> request.withOrgSyncAdvise(syncAdvise))
				: this;
	}

	public JsonOLCandCreateBulkRequest withBPartnersSyncAdvise(@Nullable final SyncAdvise syncAdvise)
	{
		return syncAdvise != null
				? map(request -> request.withBPartnersSyncAdvise(syncAdvise))
				: this;
	}

	public JsonOLCandCreateBulkRequest withProductsSyncAdvise(@Nullable final SyncAdvise syncAdvise)
	{
		return syncAdvise != null
				? map(request -> request.withProductsSyncAdvise(syncAdvise))
				: this;
	}

	private JsonOLCandCreateBulkRequest map(@NonNull final UnaryOperator<JsonOLCandCreateRequest> mapper)
	{
		if (requests.isEmpty())
		{
			return this;
		}

		final ImmutableList<JsonOLCandCreateRequest> newRequests = this.requests.stream()
				.map(mapper)
				.collect(ImmutableList.toImmutableList());

		return new JsonOLCandCreateBulkRequest(newRequests);
	}
}
