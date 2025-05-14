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

package de.metas.cache;

import com.google.common.collect.ImmutableSet;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
public class CCacheStats
{
	//
	// Cache Info
	long cacheId;
	@NonNull String name;
	@NonNull ImmutableSet<CacheLabel> labels;
	@NonNull CCacheConfig config;
	@Nullable String debugAcquireStacktrace;

	//
	// Actual Stats
	long size;
	long hitCount;
	long missCount;
	@NonNull Percent hitRate;
	@NonNull Percent missRate;

	@Builder
	@Jacksonized
	private CCacheStats(
			final long cacheId,
			@NonNull final String name,
			@Nullable final ImmutableSet<CacheLabel> labels,
			@NonNull final CCacheConfig config,
			@Nullable final String debugAcquireStacktrace,
			final long size,
			final long hitCount,
			final long missCount)
	{
		this.cacheId = cacheId;
		this.name = name;
		this.labels = labels != null ? labels : ImmutableSet.of();
		this.config = config;
		this.debugAcquireStacktrace = debugAcquireStacktrace;
		this.size = size;
		this.hitCount = hitCount;
		this.missCount = missCount;

		this.hitRate = Percent.of(hitCount, hitCount + missCount);
		this.missRate = Percent.ONE_HUNDRED.subtract(hitRate);

	}
}
