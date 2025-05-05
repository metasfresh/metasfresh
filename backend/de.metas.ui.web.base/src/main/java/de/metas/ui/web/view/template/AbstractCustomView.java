package de.metas.ui.web.view.template;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.view.IEditableView.RowEditingContext;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.ViewFilterParameterLookupEvaluationCtx;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewResult;
import de.metas.ui.web.view.ViewRowIdsSelection;
import de.metas.ui.web.view.ViewRowsOrderBy;
import de.metas.ui.web.view.descriptor.SqlViewRowsWhereClause;
import de.metas.ui.web.view.event.ViewChangesCollector;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.model.DocumentQueryOrderByList;
import de.metas.ui.web.window.model.sql.SqlOptions;
import de.metas.util.Check;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

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
 * Convenient template to be used for all other {@link IView} custom implementations.
 *
 * @param <T> type of the {@link IViewRow}s that this instance deals with e.g. in {@link #getRows()}.
 */
public abstract class AbstractCustomView<T extends IViewRow> implements IView
{
	@Getter
	private final ViewId viewId;
	@Getter
	private final ITranslatableString description;

	@Getter(AccessLevel.PROTECTED)
	private final IRowsData<T> rowsData;

	private final DocumentFilterDescriptorsProvider viewFilterDescriptors;

	/**
	 * @param description may not be {@code null} either; if you don't have one, please use {@link TranslatableStrings#empty()}
	 */
	protected AbstractCustomView(
			@NonNull final ViewId viewId,
			@Nullable final ITranslatableString description,
			@NonNull final IRowsData<T> rowsData,
			@NonNull final DocumentFilterDescriptorsProvider viewFilterDescriptors)
	{
		this.viewId = viewId;
		this.description = description != null ? description : TranslatableStrings.empty();

		this.rowsData = rowsData;

		this.viewFilterDescriptors = viewFilterDescriptors;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("viewId", getViewId())
				.toString();
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

	protected final Collection<T> getRows()
	{
		return rowsData.getTopLevelRows();
	}

	@Override
	public final long size()
	{
		return rowsData.size();
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
	public LookupValuesPage getFilterParameterDropdown(final String filterId, final String filterParameterName, final ViewFilterParameterLookupEvaluationCtx ctx)
	{
		return viewFilterDescriptors.getByFilterId(filterId)
				.getParameterByName(filterParameterName)
				.getLookupDataSource()
				.orElseThrow(() -> new AdempiereException("No lookup found for filterId=" + filterId + ", filterParameterName=" + filterParameterName))
				.findEntities(ctx.toEvaluatee());
	}

	@Override
	public LookupValuesPage getFilterParameterTypeahead(final String filterId, final String filterParameterName, final String query, final ViewFilterParameterLookupEvaluationCtx ctx)
	{
		return viewFilterDescriptors.getByFilterId(filterId)
				.getParameterByName(filterParameterName)
				.getLookupDataSource()
				.orElseThrow(() -> new AdempiereException("No lookup found for filterId=" + filterId + ", filterParameterName=" + filterParameterName))
				.findEntities(ctx.toEvaluatee(), query);
	}

	/**
	 * Just returns an empty list.
	 */
	@Override
	public DocumentFilterList getStickyFilters()
	{
		return DocumentFilterList.EMPTY;
	}

	protected final DocumentFilterDescriptorsProvider getFilterDescriptors()
	{
		return viewFilterDescriptors;
	}

	/**
	 * Just returns an empty list.
	 */
	@Override
	public DocumentFilterList getFilters()
	{
		return DocumentFilterList.EMPTY;
	}

	/**
	 * Just returns an empty list.
	 */
	@Override
	public DocumentQueryOrderByList getDefaultOrderBys()
	{
		return DocumentQueryOrderByList.EMPTY;
	}

	/**
	 * Just returns {@code null}.
	 */
	@Override
	public SqlViewRowsWhereClause getSqlWhereClause(final DocumentIdsSelection rowIds, final SqlOptions sqlOpts)
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
	public final ViewResult getPage(
			final int firstRow,
			final int pageLength,
			@NonNull final ViewRowsOrderBy orderBys)
	{
		final ViewRowsOrderBy orderBysEffective = !orderBys.isEmpty()
				? orderBys
				: orderBys.withOrderBys(getDefaultOrderBys());

		final List<IViewRow> pageRows = getRows()
				.stream()
				.sorted(orderBysEffective.toComparator())
				.skip(Math.max(firstRow, 0))
				.limit(pageLength > 0 ? pageLength : 30)
				.collect(ImmutableList.toImmutableList());

		return ViewResult.ofViewAndPage(this, firstRow, pageLength, orderBysEffective.toDocumentQueryOrderByList(), pageRows);
	}

	@Override
	public final T getById(@NonNull final DocumentId rowId) throws EntityNotFoundException
	{
		return rowsData.getById(rowId);
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
	public final Stream<T> streamByIds(@NonNull final DocumentIdsSelection rowIds)
	{
		if (rowIds.isAll())
		{
			return getRows().stream();
		}
		else if (rowIds.isEmpty())
		{
			return Stream.empty();
		}
		else
		{
			return rowIds.stream().map(rowsData::getByIdOrNull).filter(Objects::nonNull);
		}
	}

	public final Stream<T> streamByIds(@NonNull final ViewRowIdsSelection selection)
	{
		if (!Objects.equals(getViewId(), selection.getViewId()))
		{
			throw new AdempiereException("Selection has invalid viewId: " + selection
					+ "\nExpected viewId: " + getViewId());
		}
		return streamByIds(selection.getRowIds());
	}

	@Override
	public final void notifyRecordsChanged(
			@NonNull final TableRecordReferenceSet recordRefs,
			final boolean watchedByFrontend)
	{
		if (recordRefs.isEmpty())
		{
			return; // nothing to do, but shall not happen
		}

		final TableRecordReferenceSet recordRefsEligible = recordRefs.filter(this::isEligibleInvalidateEvent);
		if (recordRefsEligible.isEmpty())
		{
			return; // nothing to do
		}

		final DocumentIdsSelection documentIdsToInvalidate = getDocumentIdsToInvalidate(recordRefsEligible);
		if (documentIdsToInvalidate.isEmpty())
		{
			return; // nothing to do
		}

		rowsData.invalidate(documentIdsToInvalidate);
		ViewChangesCollector.getCurrentOrAutoflush()
				.collectRowsChanged(this, documentIdsToInvalidate);
	}

	protected boolean isEligibleInvalidateEvent(final TableRecordReference recordRef)
	{
		return true;
	}

	protected final DocumentIdsSelection getDocumentIdsToInvalidate(@NonNull final TableRecordReferenceSet recordRefs)
	{
		return rowsData.getDocumentIdsToInvalidate(recordRefs);
	}

	// extends IEditableView.patchViewRow
	public final void patchViewRow(final RowEditingContext ctx, final List<JSONDocumentChangedEvent> fieldChangeRequests)
	{
		Check.assumeNotEmpty(fieldChangeRequests, "fieldChangeRequests is not empty");

		if (rowsData instanceof IEditableRowsData)
		{
			final IEditableRowsData<T> editableRowsData = (IEditableRowsData<T>)rowsData;
			editableRowsData.patchRow(ctx, fieldChangeRequests);
		}
		else
		{
			throw new AdempiereException("View is not editable");
		}
	}
}
