package de.metas.ui.web.handlingunits;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.collections.IteratorUtils;
import org.compiere.util.CCache;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;

import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
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

class HUEditorRowsPagedLoadingIterator implements Iterator<HUEditorRow>
{
	private final int DEFAULT_BUFFERSIZE = 100;

	private final HUEditorViewRepository huEditorRepo;
	private final CCache<DocumentId, HUEditorRow> cache;
	private final int bufferSize;
	private final Iterator<HUEditorRowId> rowIds;
	private final HUEditorRowFilter filter;
	private final Predicate<HUEditorRow> filterPredicate;

	private Iterator<HUEditorRow> currentPageIterator;
	private boolean finished = false;


	@Builder
	private HUEditorRowsPagedLoadingIterator(
			@NonNull final HUEditorViewRepository huEditorRepo,
			@NonNull final CCache<DocumentId, HUEditorRow> cache,
			final int bufferSize,
			@NonNull final Iterator<HUEditorRowId> rowIds,
			final HUEditorRowFilter filter)
	{
		this.huEditorRepo = huEditorRepo;
		this.cache = cache;
		this.bufferSize = bufferSize > 0 ? bufferSize : DEFAULT_BUFFERSIZE;
		this.rowIds = rowIds;
		this.filter = filter != null ? filter : HUEditorRowFilter.ALL;
		this.filterPredicate = HUEditorRowFilters.toPredicate(this.filter); 
	}

	@Override
	public boolean hasNext()
	{
		if (finished)
		{
			return false;
		}

		if (currentPageIterator == null || !currentPageIterator.hasNext())
		{
			currentPageIterator = getNextPageIterator();
			if (!currentPageIterator.hasNext())
			{
				finished = true;
				return false;
			}
			else
			{
				return true;
			}
		}
		else
		{
			return currentPageIterator.hasNext();
		}
	}

	@Override
	public HUEditorRow next()
	{
		if (currentPageIterator == null)
		{
			throw new NoSuchElementException();
		}

		return currentPageIterator.next();
	}

	public Stream<HUEditorRow> stream()
	{
		return IteratorUtils.stream(this);
	}

	private Iterator<HUEditorRow> getNextPageIterator()
	{
		final HUEditorRow[] rows = new HUEditorRow[bufferSize];
		final Map<HUEditorRowId, Integer> rowIdToLoad2index = new HashMap<>();

		//
		// Get from cache as much as possible
		{
			int idx = 0;
			while (rowIds.hasNext() && idx < bufferSize)
			{
				final HUEditorRowId rowId = rowIds.next();

				final HUEditorRowId topLevelRowId = rowId.toTopLevelRowId();
				final HUEditorRow topLevelRow = cache.get(topLevelRowId.toDocumentId());

				if (topLevelRow == null)
				{
					// to be loaded
					rowIdToLoad2index.put(rowId, idx);
				}
				else
				{
					if (rowId.equals(topLevelRowId))
					{
						rows[idx] = topLevelRow;
					}
					else
					{
						rows[idx] = topLevelRow.getIncludedRowById(rowId.toDocumentId()).orElse(null);
					}
				}

				idx++;
			}
		}

		//
		// Load missing rows (which were not found in cache)
		if (!rowIdToLoad2index.isEmpty())
		{
			final ListMultimap<HUEditorRowId, HUEditorRowId> topLevelRowId2rowIds = rowIdToLoad2index.keySet()
					.stream()
					.map(rowId -> GuavaCollectors.entry(rowId.toTopLevelRowId(), rowId))
					.collect(GuavaCollectors.toImmutableListMultimap());

			final Set<Integer> topLevelHUIds = topLevelRowId2rowIds.keys().stream().map(HUEditorRowId::getTopLevelHUId).collect(ImmutableSet.toImmutableSet());

			huEditorRepo.retrieveHUEditorRows(topLevelHUIds, filter)
					.forEach(topLevelRow -> {
						final HUEditorRowId topLevelRowId = topLevelRow.getHURowId();
						for (final HUEditorRowId includedRowId : topLevelRowId2rowIds.get(topLevelRowId))
						{
							final Integer idx = rowIdToLoad2index.remove(includedRowId);
							if (idx == null)
							{
								// wtf?! shall not happen
								continue;
							}

							if (topLevelRowId.equals(includedRowId))
							{
								rows[idx] = topLevelRow;
								cache.put(topLevelRow.getId(), topLevelRow);
							}
							else
							{
								rows[idx] = topLevelRow.getIncludedRowById(includedRowId.toDocumentId()).orElse(null);
							}
						}
					});
		}

		return Stream.of(rows)
				.filter(row -> row != null) // IMPORTANT: just to make sure we won't stream some empty gaps (e.g. missing rows because HU was not a top level one)
				.filter(filterPredicate)
				.iterator();
	}

}
