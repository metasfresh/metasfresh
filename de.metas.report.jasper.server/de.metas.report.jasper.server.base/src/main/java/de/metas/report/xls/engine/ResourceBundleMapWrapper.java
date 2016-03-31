package de.metas.report.xls.engine;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.slf4j.Logger;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMap;

import de.metas.logging.LogManager;

/*
 * #%L
 * de.metas.report.jasper.server.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class ResourceBundleMapWrapper implements Map<String, String>
{
	public static final ResourceBundleMapWrapper of(final ResourceBundle bundle)
	{
		return new ResourceBundleMapWrapper(bundle);
	}

	private static final Logger logger = LogManager.getLogger(ResourceBundleMapWrapper.class);


	private final ResourceBundle bundle;

	private final Supplier<Map<String, String>> mapSupplier = Suppliers.memoize(new Supplier<Map<String, String>>()
	{

		@Override
		public Map<String, String> get()
		{
			try
			{
				return createMap();
			}
			catch (final Exception e)
			{
				logger.warn("Failed fetching the data from resource bundle. Continuing without it.", e);
				return ImmutableMap.of();
			}
		}
	});

	private ResourceBundleMapWrapper(final ResourceBundle bundle)
	{
		super();
		this.bundle = bundle;
	}

	private Map<String, String> createMap()
	{
		if (bundle == null)
		{
			return ImmutableMap.of();
		}

		final Set<String> keysSet = bundle.keySet();
		if (keysSet == null || keysSet.isEmpty())
		{
			return ImmutableMap.of();
		}

		final Map<String, String> map = new HashMap<>();
		for (final String key : keysSet)
		{
			final Object valueObj = bundle.getObject(key);
			if (valueObj == null)
			{
				continue;
			}
			if (!(valueObj instanceof String))
			{
				continue;
			}

			final String valueStr = valueObj.toString();
			map.put(key, valueStr);
		}

		return ImmutableMap.copyOf(map);
	}

	private final Map<String, String> getMap()
	{
		return mapSupplier.get();
	}

	@Override
	public int size()
	{
		return getMap().size();
	}

	@Override
	public boolean isEmpty()
	{
		return getMap().isEmpty();
	}

	@Override
	public boolean containsKey(final Object key)
	{
		return getMap().containsKey(key);
	}

	@Override
	public boolean containsValue(final Object value)
	{
		return getMap().containsValue(value);
	}

	@Override
	public String get(final Object key)
	{
		return getMap().get(key);
	}

	@Override
	public String put(final String key, final String value)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public String remove(final Object key)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void putAll(final Map<? extends String, ? extends String> m)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<String> keySet()
	{
		return getMap().keySet();
	}

	@Override
	public Collection<String> values()
	{
		return getMap().values();
	}

	@Override
	public Set<java.util.Map.Entry<String, String>> entrySet()
	{
		return getMap().entrySet();
	}
}
