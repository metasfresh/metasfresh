package de.metas.cache.model;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

import org.adempiere.util.lang.impl.TableRecordReferenceSet;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
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

	public static final CacheInvalidateMultiRequest of(@NonNull final CacheInvalidateRequest... requests)
	{
		return new CacheInvalidateMultiRequest(ImmutableSet.copyOf(requests));
	}

	public static final CacheInvalidateMultiRequest ofMultiRequests(@NonNull final Collection<CacheInvalidateMultiRequest> multiRequests)
	{
		final Set<CacheInvalidateRequest> requests = multiRequests.stream()
				.flatMap(multiRequest -> multiRequest.getRequests().stream())
				.collect(ImmutableSet.toImmutableSet());

		return new CacheInvalidateMultiRequest(requests);
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

	public static CacheInvalidateMultiRequest rootRecord(@NonNull final String rootTableName, @NonNull final RepoIdAware rootRecordId)
	{
		return of(CacheInvalidateRequest.rootRecord(rootTableName, rootRecordId));
	}

	public static <T extends RepoIdAware> CacheInvalidateMultiRequest rootRecords(@NonNull final String rootTableName, @NonNull final Set<? extends RepoIdAware> ids)
	{
		return rootRecords(rootTableName, ids, Function.identity());
	}

	public static <T, ID extends RepoIdAware> CacheInvalidateMultiRequest rootRecords(
			@NonNull final String rootTableName,
			@NonNull final Set<T> idObjs,
			@NonNull final Function<T, ID> idMapper)
	{
		Check.assumeNotEmpty(idObjs, "ids is not empty");

		final ImmutableSet<CacheInvalidateRequest> requests = idObjs.stream()
				.map(idMapper)
				.map(id -> CacheInvalidateRequest.rootRecord(rootTableName, id))
				.collect(ImmutableSet.toImmutableSet());

		return new CacheInvalidateMultiRequest(requests);
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

	public static CacheInvalidateMultiRequest fromTableNameAndRecordIds(
			@NonNull final String tableName,
			@NonNull final Collection<Integer> recordIds)
	{
		final ImmutableSet<CacheInvalidateRequest> requests = recordIds.stream()
				.distinct()
				.map(recordId -> CacheInvalidateRequest.rootRecord(tableName, recordId))
				.collect(ImmutableSet.toImmutableSet());

		return of(requests);
	}

	public static CacheInvalidateMultiRequest fromTableNameAndRepoIdAwares(
			@NonNull final String tableName,
			@NonNull final Collection<? extends RepoIdAware> recordIds)
	{
		final ImmutableSet<CacheInvalidateRequest> requests = recordIds.stream()
				.distinct()
				.filter(Objects::nonNull)
				.map(recordId -> CacheInvalidateRequest.rootRecord(tableName, recordId.getRepoId()))
				.collect(ImmutableSet.toImmutableSet());

		return of(requests);
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

	@Override
	public String toString()
	{
		final int requestsCount = requests.size();
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("requestsCount", requestsCount)
				.add("requests", requestsCount > 0 ? requests : null)
				.toString();
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

	public Set<String> getTableNamesEffective()
	{
		return requests.stream()
				.filter(request -> !request.isAll())
				.map(CacheInvalidateRequest::getTableNameEffective)
				.collect(ImmutableSet.toImmutableSet());
	}

	public TableRecordReferenceSet getRecordsEffective()
	{
		return requests.stream()
				.map(CacheInvalidateRequest::getRecordEffective)
				.collect(TableRecordReferenceSet.collect());
	}

	public TableRecordReferenceSet getRootRecords()
	{
		return requests.stream()
				.map(CacheInvalidateRequest::getRootRecordOrNull)
				.filter(Objects::nonNull)
				.collect(TableRecordReferenceSet.collect());
	}
}
