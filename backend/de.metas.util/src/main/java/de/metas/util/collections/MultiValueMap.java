package de.metas.util.collections;

/*
 * #%L
 * de.metas.util
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


import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Extension of the {@link Map} interface that stores multiple values.
 * 
 * @author tsa
 * 
 * @param <K> key type
 * @param <V> value type
 */
// NOTE: this class was inspired by org.springframework.util.MultiValueMap<K, V> 
public class MultiValueMap<K, V> implements Map<K, List<V>>
{
	private final HashMap<K, List<V>> map;

	public MultiValueMap()
	{
		this.map = new HashMap<>();
	}

	@Override
	public int size()
	{
		return map.size();
	}

	@Override
	public boolean isEmpty()
	{
		return map.isEmpty();
	}

	@Override
	public boolean containsKey(@Nullable final Object key)
	{
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(final Object value)
	{
		for (final List<V> values : map.values())
		{
			if (values == null || values.isEmpty())
			{
				continue;
			}

			if (values.contains(value))
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public List<V> get(final Object key)
	{
		return map.get(key);
	}

	@Override
	public List<V> put(final K key, final List<V> value)
	{
		return map.put(key, value);
	}

	/**
	 * Add the given single value to the current list of values for the given key.
	 * 
	 * @param key the key
	 * @param value the value to be added
	 */
	public void add(final K key, final V value)
	{
		List<V> values = map.get(key);
		if (values == null)
		{
			values = newValuesList();
			map.put(key, values);
		}

		values.add(value);
	}

	protected List<V> newValuesList()
	{
		return new ArrayList<V>();
	}

	/**
	 * Set the given single value under the given key.
	 * 
	 * @param key the key
	 * @param value the value to set
	 */
	public void set(final K key, final V value)
	{
		List<V> values = map.get(key);
		if (values == null)
		{
			values = new ArrayList<V>();
			map.put(key, values);
		}
		else
		{
			values.clear();
		}

		values.add(value);
	}

	@Override
	public List<V> remove(final Object key)
	{
		return map.remove(key);
	}

	@Override
	public void putAll(Map<? extends K, ? extends List<V>> m)
	{
		map.putAll(m);
	}

	@Override
	public void clear()
	{
		map.clear();
	}

	@Override
	public Set<K> keySet()
	{
		return map.keySet();
	}

	@Override
	public Collection<List<V>> values()
	{
		return map.values();
	}

	@Override
	public Set<Map.Entry<K, List<V>>> entrySet()
	{
		return map.entrySet();
	}

}
