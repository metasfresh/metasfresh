package org.adempiere.util.comparator;

import java.util.Comparator;
import java.util.List;

import org.adempiere.util.Check;

import com.google.common.base.Function;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;

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
@ToString
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

	private final List<K> fixedOrderKeys;
	private final int notMatchedMarkerIndex;
	private final Function<V, K> keyMapper;

	@Builder
	private FixedOrderByKeyComparator(
			@NonNull @Singular final List<K> fixedOrderKeys,
			final int notMatchedMarkerIndex,
			@NonNull final Function<V, K> keyMapper)
	{
		// Check.assume(!fixedOrderKeys.isEmpty(), "fixedOrderList not empty"); // empty list shall be OK

		this.fixedOrderKeys = fixedOrderKeys;
		this.notMatchedMarkerIndex = notMatchedMarkerIndex;
		this.keyMapper = keyMapper;
	}

	@Override
	public int compare(final V o1, final V o2)
	{
		final int idx1 = getIndexOf(o1);
		final int idx2 = getIndexOf(o2);
		return idx1 - idx2;
	}

	private int getIndexOf(final V obj)
	{
		final K key = keyMapper.apply(obj);
		int idx = fixedOrderKeys.indexOf(key);
		if (idx < 0)
		{
			idx = notMatchedMarkerIndex;
		}
		return idx;
	}
}
