package de.metas.ui.web.view;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Evaluatee;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;

import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.view.event.ViewChangesCollector;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.ui.web.window.model.DocumentQueryOrderBys;
import de.metas.ui.web.window.model.sql.SqlOptions;
import lombok.Getter;
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

/**
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 * @param <T> type of the {@link IViewRow}s that this instance deals with e.g. in {@link #getRows()}.
 */
public abstract class AbstractCustomView<T extends IViewRow> implements IView
{
	private final ViewId viewId;
	private final ITranslatableString description;

	@Getter
	private final IRowsData<T> rowsData;

	private final DocumentFilterDescriptorsProvider viewFilterDescriptors;

	/**
	 *
	 * @param viewId
	 * @param description may not be {@code null} either; if you don't have one, please use {@link ITranslatableString#empty()}.
	 * @param rowsListSupplier
	 */
	protected AbstractCustomView(
			@NonNull final ViewId viewId,
			@Nullable final ITranslatableString description,
			@NonNull final IRowsData<T> rowsData,
			@NonNull final DocumentFilterDescriptorsProvider viewFilterDescriptors)
	{
		this.viewId = viewId;
		this.description = description != null ? description : ITranslatableString.empty();

		this.rowsData = rowsData;
		
		this.viewFilterDescriptors = viewFilterDescriptors;
	}

	@Override
	public final ViewId getViewId()
	{
		return viewId;
	}

	@Override
	public final ITranslatableString getDescription()
	{
		return description;
	}

	@Override
	public final JSONViewDataType getViewType()
	{
		return JSONViewDataType.grid;
	}

	@Override
	public Set<DocumentPath> getReferencingDocumentPaths()
	{
		return ImmutableSet.of();
	}

	@Override
	public ViewId getParentViewId()
	{
		return null;
	}

	@Override
	public DocumentId getParentRowId()
	{
		return null;
	}

	protected final Map<DocumentId, T> getRows()
	{
		return rowsData.getDocumentId2TopLevelRows();
	}

	public Map<DocumentId, T> getMainRowsAndSubRows()
	{
		return rowsData.getDocumentId2AllRows();
	}

	@Override
	public final long size()
	{
		return getRows().size();
	}

	@Override
	public void invalidateAll()
	{
		rowsData.invalidateAll();
		ViewChangesCollector
				.getCurrentOrAutoflush()
				.collectFullyChanged(this);
	}

	@Override
	public int getQueryLimit()
	{
		return -1;
	}

	@Override
	public boolean isQueryLimitHit()
	{
		return false;
	}

	@Override
	public LookupValuesList getFilterParameterDropdown(final String filterId, final String filterParameterName, final Evaluatee ctx)
	{
		return viewFilterDescriptors.getByFilterId(filterId)
				.getParameterByName(filterParameterName)
				.getLookupDataSource()
				.findEntities(ctx);
	}

	/**
	 * Just throws an {@link UnsupportedOperationException}.
	 */
	@Override
	public LookupValuesList getFilterParameterTypeahead(final String filterId, final String filterParameterName, final String query, final Evaluatee ctx)
	{
		return viewFilterDescriptors.getByFilterId(filterId)
				.getParameterByName(filterParameterName)
				.getLookupDataSource()
				.findEntities(ctx, query);
	}

	/**
	 * Just returns an empty list.
	 */
	@Override
	public List<DocumentFilter> getStickyFilters()
	{
		return ImmutableList.of();
	}

	/**
	 * Just returns an empty list.
	 */
	@Override
	public List<DocumentFilter> getFilters()
	{
		return ImmutableList.of();
	}

	/**
	 * Just returns an empty list.
	 */
	@Override
	public List<DocumentQueryOrderBy> getDefaultOrderBys()
	{
		return ImmutableList.of();
	}

	/**
	 * Just returns {@code null}.
	 */
	@Override
	public String getSqlWhereClause(final DocumentIdsSelection rowIds, final SqlOptions sqlOpts)
	{
		return null;
	}

	/**
	 * Returns {@code false}.
	 */
	@Override
	public boolean hasAttributesSupport()
	{
		return false;
	}

	/**
	 * Simple in-memory implementation with paging and ordering.
	 */
	@Override
	public final ViewResult getPage(final int firstRow, final int pageLength, @NonNull final List<DocumentQueryOrderBy> orderBys)
	{
		final List<IViewRow> pageRows = getRows().values().stream()
				.sorted(DocumentQueryOrderBys.asComparator(orderBys))
				.skip(firstRow >= 0 ? firstRow : 0)
				.limit(pageLength > 0 ? pageLength : 30)
				.collect(ImmutableList.toImmutableList());

		return ViewResult.ofViewAndPage(this, firstRow, pageLength, orderBys, pageRows);
	}

	@Override
	public final T getById(@NonNull final DocumentId rowId) throws EntityNotFoundException
	{
		final T row = getMainRowsAndSubRows().get(rowId);
		if (row == null)
		{
			throw new EntityNotFoundException("Row not found")
					.appendParametersToMessage().setParameter("rowId", rowId);
		}
		return row;
	}

	@Override
	public <MT> List<MT> retrieveModelsByIds(final DocumentIdsSelection rowIds, final Class<MT> modelClass)
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Also supports {@link DocumentIdsSelection#ALL}, because there won't be too many lines at one time.
	 */
	@Override
	public final Stream<? extends IViewRow> streamByIds(final DocumentIdsSelection rowIds)
	{
		if (rowIds.isAll())
		{
			return getRows().values().stream();
		}
		return rowIds.stream().map(this::getById);
	}

	@Override
	public final void notifyRecordsChanged(@NonNull final Set<TableRecordReference> recordRefs)
	{
		final ImmutableList<DocumentId> affectedRowIds = recordRefs.stream()
				.filter(this::isEligibleInvalidateEvent)
				.flatMap(this::extractDocumentIdsToInvalidate)
				.collect(ImmutableList.toImmutableList());
		if (affectedRowIds.isEmpty())
		{
			return; // nothing to do
		}

		rowsData.invalidateAll();
		ViewChangesCollector
				.getCurrentOrAutoflush()
				.collectRowsChanged(this, DocumentIdsSelection.of(affectedRowIds));
	}

	protected boolean isEligibleInvalidateEvent(final TableRecordReference recordRef)
	{
		return true;
	}

	protected Stream<DocumentId> extractDocumentIdsToInvalidate(final TableRecordReference recordRef)
	{
		return rowsData.getTableRecordReference2rows()
				.get(recordRef)
				.stream()
				.map(IViewRow::getId);
	}

	private static class RowsDataTool
	{
		private static <T extends IViewRow> Map<DocumentId, T> extractAllRows(Collection<T> topLevelRows)
		{
			final ImmutableMap.Builder<DocumentId, T> allRows = ImmutableMap.builder();
			topLevelRows.forEach(topLevelRow -> {

				allRows.put(topLevelRow.getId(), topLevelRow);
				allRows.putAll(extractAllIncludedRows(topLevelRow));
			});

			return allRows.build();
		}

		private static <T extends IViewRow> Map<DocumentId, T> extractAllIncludedRows(@NonNull final T topLevelRow)
		{
			@SuppressWarnings("unchecked")
			final List<T> includedRows = (List<T>)topLevelRow.getIncludedRows();

			final ImmutableMap.Builder<DocumentId, T> resultOfThisInvocation = ImmutableMap.builder();
			for (final T includedRow : includedRows)
			{
				resultOfThisInvocation.put(includedRow.getId(), includedRow);
				resultOfThisInvocation.putAll(extractAllIncludedRows(includedRow));
			}

			return resultOfThisInvocation.build();
		}
	}

	public interface IRowsData<T extends IViewRow>
	{
		Map<DocumentId, T> getDocumentId2TopLevelRows();

		default Map<DocumentId, T> getDocumentId2AllRows()
		{
			return RowsDataTool.extractAllRows(getDocumentId2TopLevelRows().values());
		}

		ListMultimap<TableRecordReference, T> getTableRecordReference2rows();

		void invalidateAll();
	}
}
