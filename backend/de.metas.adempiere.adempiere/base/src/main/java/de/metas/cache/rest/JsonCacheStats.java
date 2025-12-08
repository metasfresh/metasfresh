/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.cache.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache.CacheMapType;
import de.metas.cache.CCacheStats;
import de.metas.cache.CacheLabel;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Set;

@Value
@Builder
@Jacksonized
public class JsonCacheStats
{
	//
	// Cache Info
	long cacheId;
	@NonNull String name;
	@NonNull Set<String> labels;
	@NonNull CacheMapType type;
	int initialCapacity;
	int maximumSize;
	int expireMinutes;
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Nullable String debugAcquireStacktrace;

	//
	// Actual Stats
	long size;
	long hitCount;
	@NonNull BigDecimal hitRate;
	long missCount;
	@NonNull BigDecimal missRate;

	public static JsonCacheStats of(@NonNull final CCacheStats stats)
	{
		return JsonCacheStats.builder()
				.cacheId(stats.getCacheId())
				.name(stats.getName())
				.labels(stats.getLabels().stream()
						.map(CacheLabel::getName)
						.collect(ImmutableSet.toImmutableSet()))
				.type(stats.getConfig().getCacheMapType())
				.initialCapacity(stats.getConfig().getInitialCapacity())
				.maximumSize(stats.getConfig().getMaximumSize())
				.expireMinutes(stats.getConfig().getExpireMinutes())
				.debugAcquireStacktrace(stats.getDebugAcquireStacktrace())
				//
				.size(stats.getSize())
				.hitCount(stats.getHitCount())
				.hitRate(stats.getHitRate().toBigDecimal())
				.missCount(stats.getMissCount())
				.missRate(stats.getMissRate().toBigDecimal())
				.build();
	}
}
