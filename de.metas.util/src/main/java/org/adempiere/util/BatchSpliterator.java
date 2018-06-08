package org.adempiere.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

import lombok.NonNull;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2018 metas GmbH
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
 *
 * @author metas-dev <dev@metasfresh.com>
 * @author https://stackoverflow.com/a/41748361/2410079
 */
final class BatchSpliterator<E> implements Spliterator<List<E>>
{
	private final Spliterator<E> base;
	private final int batchSize;

	public BatchSpliterator(@NonNull final Spliterator<E> base, final int batchSize)
	{
		Check.assumeGreaterThanZero(batchSize, "batchSize");

		this.base = base;
		this.batchSize = batchSize;
	}

	@Override
	public boolean tryAdvance(final Consumer<? super List<E>> action)
	{
		final List<E> batch = new ArrayList<>(batchSize);
		for (int i = 0; i < batchSize && base.tryAdvance(batch::add); i++)
		{
			;
		}
		if (batch.isEmpty())
		{
			return false;
		}
		action.accept(batch);
		return true;
	}

	@Override
	public Spliterator<List<E>> trySplit()
	{
		if (base.estimateSize() <= batchSize)
		{
			return null;
		}
		final Spliterator<E> splitBase = this.base.trySplit();
		return splitBase == null ? null : new BatchSpliterator<>(splitBase, batchSize);
	}

	@Override
	public long estimateSize()
	{
		final double baseSize = base.estimateSize();
		return baseSize == 0 ? 0 : (long)Math.ceil(baseSize / batchSize);
	}

	@Override
	public int characteristics()
	{
		return base.characteristics();
	}

}
