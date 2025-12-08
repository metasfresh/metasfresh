package de.metas.ui.web.order.sales.purchasePlanning.view;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.view.IEditableView;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.ViewFilterParameterLookupEvaluationCtx;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewResult;
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
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;

import java.util.List;
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

public class PurchaseView implements IEditableView
{
	public static PurchaseView cast(final Object view)
	{
		return (PurchaseView)view;
	}

	private final ViewId viewId;
	private final PurchaseRowsCollection rows;
	private final List<RelatedProcessDescriptor> additionalRelatedProcessDescriptors;

	@Builder
	private PurchaseView(
			@NonNull final ViewId viewId,
			@NonNull final PurchaseRowsSupplier rowsSupplier,
			@Singular final List<RelatedProcessDescriptor> additionalRelatedProcessDescriptors)
	{
		this.viewId = viewId;
		this.rows = PurchaseRowsCollection.ofSupplier(rowsSupplier);
		this.additionalRelatedProcessDescriptors = ImmutableList.copyOf(additionalRelatedProcessDescriptors);
	}

	@Override
	public ViewId getViewId()
	{
		return viewId;
	}

	@Override
	public JSONViewDataType getViewType()
	{
		return JSONViewDataType.grid;
	}

	@Override
	public Set<DocumentPath> getReferencingDocumentPaths()
	{
		return ImmutableSet.of();
	}

	@Override
	public String getTableNameOrNull(final DocumentId documentId)
	{
		return null;
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

	@Override
	public long size()
	{
		return rows.size();
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
	public void invalidateAll()
	{
		ViewChangesCollector.getCurrentOrAutoflush().collectFullyChanged(this);
	}

	@Override
	public void invalidateRowById(final DocumentId rowId)
	{
		ViewChangesCollector.getCurrentOrAutoflush().collectRowChanged(this, rowId);
	}

	@Override
	public ViewResult getPage(
			final int firstRow,
			final int pageLength,
			@NonNull final ViewRowsOrderBy orderBys)
	{
		if (!orderBys.isEmpty())
		{
			throw new AdempiereException("orderBys is not supported");
		}
		final List<PurchaseRow> pageRows = rows.getPage(firstRow, pageLength);
		return ViewResult.ofViewAndPage(this, firstRow, pageLength, orderBys.toDocumentQueryOrderByList(), pageRows);
	}

	@Override
	public PurchaseRow getById(final DocumentId rowId) throws EntityNotFoundException
	{
		return rows.getById(rowId);
	}

	public PurchaseRow getById(final PurchaseRowId rowId) throws EntityNotFoundException
	{
		return rows.getById(rowId);
	}

	@Override
	public LookupValuesPage getFilterParameterDropdown(final String filterId, final String filterParameterName, final ViewFilterParameterLookupEvaluationCtx ctx)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public LookupValuesPage getFilterParameterTypeahead(final String filterId, final String filterParameterName, final String query, final ViewFilterParameterLookupEvaluationCtx ctx)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public DocumentFilterList getStickyFilters()
	{
		return DocumentFilterList.EMPTY;
	}

	@Override
	public DocumentFilterList getFilters()
	{
		return DocumentFilterList.EMPTY;
	}

	@Override
	public DocumentQueryOrderByList getDefaultOrderBys()
	{
		return DocumentQueryOrderByList.EMPTY;
	}

	@Override
	public SqlViewRowsWhereClause getSqlWhereClause(final DocumentIdsSelection rowIds, final SqlOptions sqlOpts)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> List<T> retrieveModelsByIds(final DocumentIdsSelection rowIds, final Class<T> modelClass)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Stream<? extends IViewRow> streamByIds(final DocumentIdsSelection rowIds)
	{
		return rows.streamTopLevelRowsByIds(rowIds);
	}

	public List<PurchaseRow> getRows()
	{
		return rows.getAll();
	}

	@Override
	public void notifyRecordsChanged(
			@NonNull final TableRecordReferenceSet recordRefs,
			final boolean watchedByFrontend)
	{
	}

	@Override
	public void patchViewRow(
			@NonNull final RowEditingContext ctx,
			@NonNull final List<JSONDocumentChangedEvent> fieldChangeRequests)
	{
		final PurchaseRowId idOfChangedRow = PurchaseRowId.fromDocumentId(ctx.getRowId());
		final PurchaseRowChangeRequest rowChangeRequest = PurchaseRowChangeRequest.of(fieldChangeRequests);
		patchViewRow(idOfChangedRow, rowChangeRequest);
	}

	public void patchViewRow(
			@NonNull final PurchaseRowId idOfChangedRow,
			@NonNull final PurchaseRowChangeRequest rowChangeRequest)
	{
		rows.patchRow(idOfChangedRow, rowChangeRequest);

		// notify the frontend
		final DocumentId groupRowDocumentId = idOfChangedRow.toGroupRowId().toDocumentId();

		ViewChangesCollector
				.getCurrentOrAutoflush()
				.collectRowChanged(this, groupRowDocumentId);
	}

	@Override
	public List<RelatedProcessDescriptor> getAdditionalRelatedProcessDescriptors()
	{
		return additionalRelatedProcessDescriptors;
	}
}
