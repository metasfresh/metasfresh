package org.adempiere.util.comparator;

import java.util.Comparator;
import java.util.List;

import org.adempiere.util.Check;

import com.google.common.base.Function;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Like {@link FixedOrderComparator} but it uses a <code>keyMapper</code> to extract the sorting keys.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 * @param <V> comparing value type
 * @param <K> key type
 */
public class FixedOrderByKeyComparator<V, K> implements Comparator<V>
{
	public static final <V, K> FixedOrderByKeyComparator<V, K> of(final K notMatchedMarker, final List<K> fixedOrderList, final Function<V, K> keyMapper)
	{
		final int notMatchedMarkerIndex = fixedOrderList.indexOf(notMatchedMarker);
		final int notMatchedMarkerIndexEffective = notMatchedMarkerIndex >= 0 ? notMatchedMarkerIndex : Integer.MAX_VALUE;
		return new FixedOrderByKeyComparator<>(fixedOrderList, notMatchedMarkerIndexEffective, keyMapper);
	}

	public static final <V, K> FixedOrderByKeyComparator<V, K> notMatchedAtTheEnd(final List<K> fixedOrderList, final Function<V, K> keyMapper)
	{
		final int notMatchedMarkerIndex = Integer.MAX_VALUE;
		return new FixedOrderByKeyComparator<>(fixedOrderList, notMatchedMarkerIndex, keyMapper);
	}

	private final List<K> fixedOrderList;
	private final int notMatchedMarkerIndex;
	private final Function<V, K> keyMapper;

	private FixedOrderByKeyComparator(final List<K> fixedOrderList, final int notMatchedMarkerIndex, final Function<V, K> keyMapper)
	{
		Check.assumeNotNull(fixedOrderList, "fixedOrderList not null");
		Check.assume(!fixedOrderList.isEmpty(), "fixedOrderList not empty");
		Check.assumeNotNull(keyMapper, "Parameter keyMapper is not null");

		this.fixedOrderList = fixedOrderList;
		this.notMatchedMarkerIndex = notMatchedMarkerIndex;
		this.keyMapper = keyMapper;
	}

	@Override
	public int compare(final V o1, final V o2)
	{
		final K key1 = keyMapper.apply(o1);
		int idx1 = fixedOrderList.indexOf(key1);
		if (idx1 < 0)
		{
			idx1 = notMatchedMarkerIndex;
		}

		final K key2 = keyMapper.apply(o2);
		int idx2 = fixedOrderList.indexOf(key2);
		if (idx2 < 0)
		{
			idx2 = notMatchedMarkerIndex;
		}

		return idx1 - idx2;
	}

	@Override
	public String toString()
	{
		return "FixedOrderComparator [fixedOrderList=" + fixedOrderList + ", notMatchedMarkerIndex=" + notMatchedMarkerIndex + "]";
	}

}
