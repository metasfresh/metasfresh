package de.metas.ui.web.view.template;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import org.adempiere.util.lang.SynchronizedMutable;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;

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

/**
 * Mutable/synchronized {@link ImmutableRowsIndex} holder.
 */
public class SynchronizedRowsIndexHolder<T extends IViewRow>
{
	public static <T extends IViewRow> SynchronizedRowsIndexHolder<T> of(@NonNull final List<T> rows)
	{
		final ImmutableRowsIndex<T> initialRowIndex = ImmutableRowsIndex.of(rows);
		return new SynchronizedRowsIndexHolder<>(initialRowIndex);
	}

	private final SynchronizedMutable<ImmutableRowsIndex<T>> holder;

	private SynchronizedRowsIndexHolder(@NonNull final ImmutableRowsIndex<T> initialRowIndex)
	{
		holder = SynchronizedMutable.of(initialRowIndex);
	}

	public ImmutableMap<DocumentId, T> getDocumentId2TopLevelRows()
	{
		return holder.getValue().getDocumentId2TopLevelRows();
	}

	public <ID extends RepoIdAware> ImmutableSet<ID> getRecordIdsToRefresh(
			@NonNull final DocumentIdsSelection rowIds,
			@NonNull final Function<DocumentId, ID> idMapper)
	{
		return holder.getValue().getRecordIdsToRefresh(rowIds, idMapper);
	}

	public void compute(@NonNull final UnaryOperator<ImmutableRowsIndex<T>> remappingFunction)
	{
		holder.compute(remappingFunction);
	}

	public Predicate<DocumentId> isRelevantForRefreshingByDocumentId()
	{
		final ImmutableRowsIndex<T> rows = holder.getValue();
		return rows::isRelevantForRefreshing;
	}
}
