package de.metas.cache.interceptor;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.ArrayList;
import java.util.List;

import org.adempiere.util.lang.ObjectUtils;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

/**
 * Helper class used to collect key parts and other informations from {@link ICachedMethodPartDescriptor}s,
 * and then builds the cache key.
 * 
 * @author tsa
 *
 */
final class CacheKeyBuilder
{
	private final List<Object> keyParts = new ArrayList<>();
	private String trxName;
	private boolean skipCaching;
	private boolean cacheReload = false;

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public final ArrayKey buildKey()
	{
		return Util.mkKey(keyParts.toArray());
	}

	public void add(final Object keyPart)
	{
		keyParts.add(keyPart);
	}

	public void setTrxName(String trxName)
	{
		this.trxName = trxName;
	}

	public String getTrxName()
	{
		return trxName;
	}

	public void setSkipCaching()
	{
		this.skipCaching = true;
	}

	public boolean isSkipCaching()
	{
		return skipCaching;
	}

	/**
	 * Advices the caching engine to refresh the cached value, instead of checking the cache.
	 * 
	 * NOTE: this option will have NO affect if {@link #isSkipCaching()}.
	 */
	public void setCacheReload()
	{
		this.cacheReload = true;
	}

	/** @return true if instead the underlying method shall be invoked and cache shall be refreshed with that value */
	public boolean isCacheReload()
	{
		return cacheReload;
	}
}
