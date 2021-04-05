/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.dashboard;

import de.metas.common.util.time.SystemTime;
import de.metas.elasticsearch.IESSystem;
import de.metas.logging.LogManager;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class KPIDataProvider
{
	private static final Logger logger = LogManager.getLogger(KPIDataProvider.class);
	private final IESSystem esSystem;
	private final KPIRepository kpiRepository;

	private final ConcurrentHashMap<KPIDataCacheKey, KPIDataCacheValue> cache = new ConcurrentHashMap<>();
	private final Duration CACHE_EXPIRE_AFTER = Duration.ofSeconds(10);
	private final Percent CACHE_CLEANUP_HIT_RATE = Percent.of(20);
	private final Random random = new Random();

	@Builder
	private KPIDataProvider(
			@NonNull final IESSystem esSystem,
			@NonNull final KPIRepository kpiRepository)
	{
		this.esSystem = esSystem;
		this.kpiRepository = kpiRepository;
	}

	public KPIDataResult getKPIData(@NonNull final KPIDataRequest request)
	{
		if (isCacheCleanupRandomHit())
		{
			cleanupCacheNow();
		}

		final Duration maxStaleAccepted = request.getMaxStaleAccepted();
		final KPIDataCacheValue cacheValue = cache.compute(
				extractCacheKey(request),
				(cacheKey, existingCacheValue) -> computeCacheValueIfNeeded(cacheKey, existingCacheValue, maxStaleAccepted));

		return cacheValue.getData();
	}

	private static KPIDataCacheKey extractCacheKey(@NonNull final KPIDataRequest request)
	{
		return KPIDataCacheKey.builder()
				.kpiId(request.getKpiId())
				.timeRangeDefaults(request.getTimeRangeDefaults())
				.from(request.getFrom())
				.to(request.getTo())
				.build();
	}

	private KPIDataCacheValue computeCacheValueIfNeeded(
			@NonNull final KPIDataCacheKey cacheKey,
			@Nullable final KPIDataCacheValue existingValue,
			@NonNull final Duration maxStaleAccepted)
	{
		logger.trace("computeCacheValueIfNeeded: cacheKey={}, maxStaleAccepted={}, existingValue={}", cacheKey, maxStaleAccepted, existingValue);

		if (existingValue == null)
		{
			logger.trace("computeCacheValueIfNeeded: no existingValue. Computing and returning a new value");
			return computeCacheValue(cacheKey);
		}
		else if (existingValue.isExpired(maxStaleAccepted))
		{
			logger.trace("computeCacheValueIfNeeded: existingValue expired. Computing and returning a new value");
			return computeCacheValue(cacheKey);
		}
		else
		{
			logger.trace("computeCacheValueIfNeeded: existingValue is still valid. Returning it.");
			return existingValue;
		}
	}

	private KPIDataCacheValue computeCacheValue(@NonNull final KPIDataCacheKey cacheKey)
	{
		final KPITimeRangeDefaults timeRangeDefaults = cacheKey.getTimeRangeDefaults();
		final TimeRange timeRange = timeRangeDefaults.createTimeRange(cacheKey.getFrom(), cacheKey.getTo());
		final KPI kpi = kpiRepository.getKPI(cacheKey.getKpiId());

		final KPIDataResult data = KPIDataLoader.newInstance(esSystem.elasticsearchClient(), kpi)
				.setTimeRange(timeRange)
				.retrieveData();

		final KPIDataCacheValue cacheValue = new KPIDataCacheValue(data);

		logger.trace("computeCacheValue: {} => {}", cacheKey, cacheValue);
		return cacheValue;
	}

	private boolean isCacheCleanupRandomHit()
	{
		final int randomValue = random.nextInt(100);
		return randomValue <= CACHE_CLEANUP_HIT_RATE.toInt();
	}

	private void cleanupCacheNow()
	{
		logger.trace("cacheCleanupNow: {} entries before cleanup", cache.size());

		cache.values().removeIf(cacheValue -> cacheValue.isExpired(CACHE_EXPIRE_AFTER));

		logger.trace("cacheCleanupNow: {} entries after cleanup", cache.size());
	}

	@Value
	@Builder
	private static class KPIDataCacheKey
	{
		@NonNull KPIId kpiId;
		@NonNull KPITimeRangeDefaults timeRangeDefaults;
		@Nullable Instant from;
		@Nullable Instant to;
	}

	@Value
	@ToString(exclude = "data" /* because it's too big */)
	private static class KPIDataCacheValue
	{
		@NonNull Instant created = SystemTime.asInstant();
		@NonNull KPIDataResult data;

		public boolean isExpired(@NonNull final Duration maxStaleAccepted)
		{
			final Instant now = SystemTime.asInstant();
			final Duration staleActual = Duration.between(created, now);
			final boolean expired = staleActual.compareTo(maxStaleAccepted) > 0;
			logger.trace("cacheValue={}: expired={}, now={}, maxStaleAccepted={}, staleActual={}", this, expired, now, maxStaleAccepted, staleActual);
			return expired;
		}
	}
}
