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

import de.metas.cache.CCache;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

@Value
@Builder
@Jacksonized
public class JsonCache
{
	@NonNull JsonCacheStats stats;
	@Nullable Map<String, String> sample;

	public static JsonCache of(@NonNull final CCache<?, ?> cache, final int sampleLimit)
	{
		//noinspection unchecked
		return JsonCache.builder()
				.stats(JsonCacheStats.of(cache.stats()))
				.sample(extractSample((CCache<Object, Object>)cache, sampleLimit))
				.build();
	}

	private static Map<String, String> extractSample(final CCache<Object, Object> cache, final int sampleLimit)
	{
		final HashMap<String, String> sample = sampleLimit > 0 ? new HashMap<>(sampleLimit) : new HashMap<>();
		for (final Object key : cache.keySet())
		{
			if (sampleLimit > 0 && sample.size() > sampleLimit)
			{
				break;
			}

			final Object value = cache.get(key);

			sample.put(key != null ? key.toString() : null, value != null ? value.toString() : null);
		}

		return sample;
	}
}
