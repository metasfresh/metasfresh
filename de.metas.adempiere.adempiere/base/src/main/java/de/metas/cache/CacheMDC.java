package de.metas.cache;

import java.util.Objects;

import javax.annotation.Nullable;

import org.adempiere.util.lang.IAutoCloseable;
import org.slf4j.MDC;
import org.slf4j.MDC.MDCCloseable;

import com.google.common.collect.ImmutableList;

import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

@UtilityClass
public class CacheMDC
{
	/**
	 * Creates one composite closable for both cache id and name.
	 *
	 * @return {@code null} if the given {@code cache} was put already.
	 *         Thx to https://stackoverflow.com/a/35372185/1012103
	 */
	public <K, V> IAutoCloseable putCache(@Nullable final CacheInterface cache)
	{
		if (cache == null)
		{
			return null;
		}

		final ImmutableList.Builder<MDCCloseable> closables = ImmutableList.builder();

		final String cacheId = Long.toString(cache.getCacheId());
		if (!Objects.equals(MDC.get("de.metas.cache.cacheId"), cacheId))
		{
			closables.add(MDC.putCloseable("de.metas.cache.cacheId", cacheId));
		}

		if (cache instanceof CCache)
		{
			final CCache<?, ?> ccache = (CCache<?, ?>)cache;
			final String cacheName = ccache.getCacheName();
			if (!Objects.equals(MDC.get("de.metas.cache.cacheName"), cacheName))
			{
				closables.add(MDC.putCloseable("de.metas.cache.cacheName", cacheName));
			}
		}

		final ImmutableList<MDCCloseable> composite = closables.build();
		return () -> composite.forEach(MDCCloseable::close);
	}

	public MDCCloseable putCacheLabel(@Nullable final CacheLabel cacheLabel)
	{
		if (cacheLabel == null)
		{
			return null;
		}

		if (Objects.equals(MDC.get("de.metas.cache.cacheLabel"), cacheLabel.getName()))
		{
			return null;
		}
		return MDC.putCloseable("de.metas.cache.cacheLabel", cacheLabel.getName());
	}
}
