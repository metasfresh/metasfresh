package de.metas.dao.selection.pagination;

import java.time.Instant;
import java.util.function.Function;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;

import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

@Value
public class QueryResultPage<T>
{
	@NonNull
	PageDescriptor currentPageDescriptor;

	@Nullable
	PageDescriptor nextPageDescriptor;

	/** Number of all items that are part of the selection which this page is a part of. */
	int totalSize;

	/** Point in time when the selection was done */
	@NonNull
	Instant resultTimestamp;

	@NonNull
	ImmutableList<T> items;

	public <R> QueryResultPage<R> mapTo(@NonNull final Function<T, R> mapper)
	{
		final ImmutableList<R> mappedItems = items.stream()
				.map(mapper)
				.collect(ImmutableList.toImmutableList());

		return new QueryResultPage<>(currentPageDescriptor, nextPageDescriptor, totalSize, resultTimestamp, mappedItems);
	}

	public <R> QueryResultPage<R> mapAllTo(@NonNull final Function<ImmutableList<T>, ImmutableList<R>> mapper)
	{
		return withItems(mapper.apply(getItems()));
	}

	public <R> QueryResultPage<R> withItems(@NonNull final ImmutableList<R> replacementItems)
	{
		return new QueryResultPage<>(
				currentPageDescriptor.withSize(replacementItems.size()),
				nextPageDescriptor,
				totalSize,
				resultTimestamp,
				replacementItems);
	}
}
