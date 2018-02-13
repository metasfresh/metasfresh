package org.adempiere.ad.dao.cache;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

import org.adempiere.util.Check;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableSet;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class CacheInvalidateMultiRequest
{
	public static final CacheInvalidateMultiRequest of(@NonNull final CacheInvalidateRequest request)
	{
		if (request == CacheInvalidateRequest.all())
		{
			return ALL;
		}
		return new CacheInvalidateMultiRequest(ImmutableSet.of(request));
	}

	public static final CacheInvalidateMultiRequest of(@NonNull final Collection<CacheInvalidateRequest> requests)
	{
		return new CacheInvalidateMultiRequest(ImmutableSet.copyOf(requests));
	}

	public static CacheInvalidateMultiRequest all()
	{
		return ALL;
	}

	public static CacheInvalidateMultiRequest allRecordsForTable(@NonNull final String rootTableName)
	{
		return of(CacheInvalidateRequest.allRecordsForTable(rootTableName));
	}

	public static CacheInvalidateMultiRequest rootRecord(@NonNull final String rootTableName, final int rootRecordId)
	{
		return of(CacheInvalidateRequest.rootRecord(rootTableName, rootRecordId));
	}

	public static CacheInvalidateMultiRequest allChildRecords(@NonNull final String rootTableName, final int rootRecordId, @NonNull final String childTableName)
	{
		return of(CacheInvalidateRequest.allChildRecords(rootTableName, rootRecordId, childTableName));
	}

	public static CacheInvalidateMultiRequest fromTableNameAndRecordId(final String tableName, final int recordId)
	{
		if (tableName == null)
		{
			return all();
		}
		else if (recordId < 0)
		{
			return allRecordsForTable(tableName);
		}
		else
		{
			return rootRecord(tableName, recordId);
		}
	}

	private static final CacheInvalidateMultiRequest ALL = new CacheInvalidateMultiRequest(ImmutableSet.of(CacheInvalidateRequest.all()));

	@JsonProperty("requests")
	private final Set<CacheInvalidateRequest> requests;

	@Builder
	@JsonCreator
	private CacheInvalidateMultiRequest(@JsonProperty("requests") @Singular @NonNull final Set<CacheInvalidateRequest> requests)
	{
		Check.assumeNotEmpty(requests, "requests is not empty");
		this.requests = ImmutableSet.copyOf(requests);
	}

	public boolean isResetAll()
	{
		return requests.stream().anyMatch(CacheInvalidateRequest::isAll);
	}

	public boolean matchesTableNameEffective(final String tableName)
	{
		return requests.stream().anyMatch(request -> matchesTableNameEffective(request, tableName));
	}

	private static boolean matchesTableNameEffective(final CacheInvalidateRequest request, final String tableName)
	{
		final String tableNameEffective = request.getTableNameEffective();
		return Objects.equals(tableName, tableNameEffective);
	}
}
