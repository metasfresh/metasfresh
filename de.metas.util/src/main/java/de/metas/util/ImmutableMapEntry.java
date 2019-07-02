package de.metas.util;

import java.util.Map;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2019 metas GmbH
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

@lombok.Value(staticConstructor = "of")
public final class ImmutableMapEntry<K, V> implements Map.Entry<K, V>
{
	@Nullable
	private final K key;
	@Nullable
	private final V value;

	@Override
	public V setValue(final V value)
	{
		throw new UnsupportedOperationException();
	}

	public boolean isKeyNotNull()
	{
		return key != null;
	}

	public boolean isValueNotNull()
	{
		return key != null;
	}

	public boolean isKeyAndValueNotNull()
	{
		return key != null && value != null;
	}
}
