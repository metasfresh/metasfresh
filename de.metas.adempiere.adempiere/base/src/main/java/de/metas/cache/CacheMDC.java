package de.metas.cache;

import java.util.Objects;

import javax.annotation.Nullable;

import org.slf4j.MDC;
import org.slf4j.MDC.MDCCloseable;

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
	 * @return {@code null} if the given {@code cache} was put already.
	 *         Thx to https://stackoverflow.com/a/35372185/1012103
	 */
	public <K, V> MDCCloseable putCache(@Nullable final CacheInterface cache)
	{
		if (cache == null)
		{
			return null;
		}

		final String cacheId = Long.toString(cache.getCacheId());
		if (Objects.equals(MDC.get("de.metas.cache.cacheId"), cacheId))
		{
			return null;
		}
		return MDC.putCloseable("de.metas.cache.cacheId", cacheId);
	}

	public MDCCloseable putCacheLabel(@Nullable final CacheLabel cacheLabel)
	{
		if (cacheLabel == null)
		{
			return null;
		}

		if (Objects.equals(MDC.get("de.metas.cache.CCache.cacheLabel"), cacheLabel.getName()))
		{
			return null;
		}
		return MDC.putCloseable("de.metas.cache.CCache.cacheLabel", cacheLabel.getName());
	}
}
