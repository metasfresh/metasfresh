package de.metas.ordercandidate.rest;

import java.util.List;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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

@Value
public class JsonOLCandCreateBulkRequest
{
	public static JsonOLCandCreateBulkRequest of(@NonNull final JsonOLCandCreateRequest request)
	{
		return builder().request(request).build();
	}

	List<JsonOLCandCreateRequest> requests;

	public JsonOLCandCreateBulkRequest validate()
	{
		for (final JsonOLCandCreateRequest request : requests)
		{
			request.validate();
		}
		return this;
	}

	@Builder(toBuilder = true)
	@JsonCreator
	private JsonOLCandCreateBulkRequest(@Singular @JsonProperty("requests") final List<JsonOLCandCreateRequest> requests)
	{
		this.requests = requests;
	}

	public JsonOLCandCreateBulkRequest withOrgSyncAdvise(@Nullable final SyncAdvise syncAdvise)
	{
		if (syncAdvise == null || requests.isEmpty())
		{
			return this;
		}

		final JsonOLCandCreateBulkRequestBuilder builder = toBuilder().clearRequests();
		for (final JsonOLCandCreateRequest request : requests)
		{
			builder.request(request.withOrgSyncAdvise(syncAdvise));
		}
		return builder.build();
	}

	public JsonOLCandCreateBulkRequest withBPartnersSyncAdvise(@Nullable final SyncAdvise syncAdvise)
	{
		if (syncAdvise == null || requests.isEmpty())
		{
			return this;
		}

		final JsonOLCandCreateBulkRequestBuilder builder = toBuilder().clearRequests();
		for (final JsonOLCandCreateRequest request : requests)
		{
			builder.request(request.withBPartnersSyncAdvise(syncAdvise));
		}
		return builder.build();
	}

	public JsonOLCandCreateBulkRequest withProductsSyncAdvise(@Nullable final SyncAdvise syncAdvise)
	{
		if (syncAdvise == null || requests.isEmpty())
		{
			return this;
		}

		final JsonOLCandCreateBulkRequestBuilder builder = toBuilder().clearRequests();
		for (final JsonOLCandCreateRequest request : requests)
		{
			builder.request(request.withProductsSyncAdvise(syncAdvise));
		}
		return builder.build();
	}

}
