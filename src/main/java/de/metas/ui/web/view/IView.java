package de.metas.ui.web.view;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Evaluatee;

import com.google.common.collect.ImmutableList;

import de.metas.i18n.ITranslatableString;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.process.view.ViewActionDescriptorsList;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

public interface IView
{
	ViewId getViewId();

	ITranslatableString getDescription();

	JSONViewDataType getViewType();

	Set<DocumentPath> getReferencingDocumentPaths();

	/**
	 * @param documentId can be used by multi-table implementations to return the correct table name for a given row.
	 * 
	 * @return table name for the given row; might also return {@code null}.
	 */
	String getTableNameOrNull(@Nullable DocumentId documentId);

	/**
	 * @return In case this is an included view, this method will return the parent's viewId. Else null will be returned.
	 * @see #isIncludedView()
	 */
	ViewId getParentViewId();

	DocumentId getParentRowId();

	/** @return true if this is an included view */
	default boolean isIncludedView()
	{
		return getParentViewId() != null;
	}

	long size();

	void close();

	int getQueryLimit();

	boolean isQueryLimitHit();

	/**
	 * NOTE: don't call this directly it shall be called by API.
	 */
	void invalidateAll();

	ViewResult getPage(int firstRow, int pageLength, List<DocumentQueryOrderBy> orderBys);

	default ViewResult getPageWithRowIdsOnly(int firstRow, int pageLength, List<DocumentQueryOrderBy> orderBys)
	{
		return getPage(firstRow, pageLength, orderBys);
	}

	IViewRow getById(DocumentId rowId) throws EntityNotFoundException;

	default List<? extends IViewRow> getByIds(final DocumentIdsSelection rowIds)
	{
		return streamByIds(rowIds).collect(ImmutableList.toImmutableList());
	}

	LookupValuesList getFilterParameterDropdown(String filterId, String filterParameterName, Evaluatee ctx);

	LookupValuesList getFilterParameterTypeahead(String filterId, String filterParameterName, String query, Evaluatee ctx);

	/**
	 * Gets the stick filters.
	 * Sticky filters are those filters which cannot be changed by user and which shall be preserved between filterings.
	 * Sticky filters shall never be exported to frontend.
	 */
	List<DocumentFilter> getStickyFilters();

	/**
	 * @return active filters
	 */
	List<DocumentFilter> getFilters();

	List<DocumentQueryOrderBy> getDefaultOrderBys();

	default TableRecordReference getTableRecordReferenceOrNull(@NonNull final DocumentId rowId)
	{
		final int recordId = rowId.toIntOr(-1);
		if (recordId < 0)
		{
			return null;
		}
		return TableRecordReference.of(getTableNameOrNull(rowId), recordId);
	}

	String getSqlWhereClause(DocumentIdsSelection rowIds);

	boolean hasAttributesSupport();

	<T> List<T> retrieveModelsByIds(DocumentIdsSelection rowIds, Class<T> modelClass);

	/**
	 * @return a stream which contains only the {@link IViewRow}s which given <code>rowId</code>s.
	 *         If a {@link IViewRow} was not found for given ID, this method simply ignores it.
	 */
	Stream<? extends IViewRow> streamByIds(DocumentIdsSelection rowIds);

	/**
	 * Notify the view that given record(s) has changed.
	 */
	void notifyRecordsChanged(Set<TableRecordReference> recordRefs);

	/** @return actions which were registered particularly for this view instance */
	default ViewActionDescriptorsList getActions()
	{
		return ViewActionDescriptorsList.EMPTY;
	}

	default List<RelatedProcessDescriptor> getAdditionalRelatedProcessDescriptors()
	{
		return ImmutableList.of();
	}
}
