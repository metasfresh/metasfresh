package de.metas.ui.web.document.filter.provider.standard;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * metasfresh-webui-api
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

@ToString
public final class FacetFilterViewCacheMap
{
	public static FacetFilterViewCacheMap newInstance()
	{
		return new FacetFilterViewCacheMap();
	}

	private final ConcurrentHashMap<String, FacetFilterViewCache> cachesById = new ConcurrentHashMap<>();

	private FacetFilterViewCacheMap()
	{
	}

	public FacetFilterViewCache computeIfAbsent(
			@NonNull final String id,
			@NonNull final Supplier<FacetFilterViewCache> supplier)
	{
		return cachesById.computeIfAbsent(id, k -> supplier.get());
	}
}
