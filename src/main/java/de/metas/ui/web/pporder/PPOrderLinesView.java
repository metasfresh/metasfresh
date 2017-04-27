package de.metas.ui.web.pporder;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.util.collections.ListUtils;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.util.Evaluatee;
import org.eevolution.model.X_PP_Order;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.handlingunits.WEBUI_HU_Constants;
import de.metas.ui.web.process.ProcessInstanceResult.OpenIncludedViewAction;
import de.metas.ui.web.process.view.ViewAction;
import de.metas.ui.web.view.ASIDocumentViewAttributesProvider;
import de.metas.ui.web.view.DocumentViewCreateRequest;
import de.metas.ui.web.view.DocumentViewResult;
import de.metas.ui.web.view.IDocumentView;
import de.metas.ui.web.view.IDocumentViewSelection;
import de.metas.ui.web.view.IDocumentViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.event.DocumentViewChangesCollector;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.ui.web.window.model.filters.DocumentFilter;
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

public class PPOrderLinesView implements IDocumentViewSelection
{
	public static PPOrderLinesView cast(final IDocumentViewSelection view)
	{
		return (PPOrderLinesView)view;
	}
	
	private final ViewId parentViewId;

	private final ViewId viewId;
	private final int ppOrderId;

	private final ASIDocumentViewAttributesProvider asiAttributesProvider;
	private final ExtendedMemorizingSupplier<PPOrderLinesViewData> dataSupplier;

	@Builder
	private PPOrderLinesView( //
			final ViewId parentViewId //
			, @NonNull final ViewId viewId //
			, final int ppOrderId //
			, final ASIDocumentViewAttributesProvider asiAttributesProvider //
	)
	{
		this.parentViewId = parentViewId; // might be null
		this.viewId = viewId;

		Preconditions.checkArgument(ppOrderId > 0, "PP_Order_ID not provided");
		this.ppOrderId = ppOrderId;

		this.asiAttributesProvider = asiAttributesProvider;

		final WindowId windowId = viewId.getWindowId();
		dataSupplier = ExtendedMemorizingSupplier.of(() -> PPOrderLinesLoader.builder(windowId)
				.asiAttributesProvider(asiAttributesProvider)
				.build()
				.retrieveData(ppOrderId));
	}

	public String getPlanningStatus()
	{
		return getData().getPlanningStatus();
	}
	
	public boolean isStatusPlanning()
	{
		return X_PP_Order.PLANNINGSTATUS_Planning.equals(getPlanningStatus());
	}
	
	public boolean isStatusReview()
	{
		return X_PP_Order.PLANNINGSTATUS_Review.equals(getPlanningStatus());
	}

	@Override
	public ViewId getParentViewId()
	{
		return parentViewId;
	}

	@Override
	public ViewId getViewId()
	{
		return viewId;
	}

	@Override
	public String getTableName()
	{
		return null; // no particular table (i.e. we have more)
	}

	public int getPP_Order_ID()
	{
		return ppOrderId;
	}

	@Override
	public long size()
	{
		return getData().size();
	}

	@Override
	public void close()
	{
		invalidateAllNoNotify();
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
	public DocumentViewResult getPage(final int firstRow, final int pageLength, final List<DocumentQueryOrderBy> orderBys)
	{
		final Stream<PPOrderLineRow> stream = getData().stream()
				.skip(firstRow)
				.limit(pageLength);

		final List<IDocumentView> page = stream.collect(GuavaCollectors.toImmutableList());

		return DocumentViewResult.ofViewAndPage(this, firstRow, pageLength, orderBys, page);
	}

	@Override
	public PPOrderLineRow getById(final DocumentId documentId) throws EntityNotFoundException
	{
		return getData().getById(documentId);
	}

	@Override
	public LookupValuesList getFilterParameterDropdown(final String filterId, final String filterParameterName, final Evaluatee ctx)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public LookupValuesList getFilterParameterTypeahead(final String filterId, final String filterParameterName, final String query, final Evaluatee ctx)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public List<DocumentFilter> getStickyFilters()
	{
		return ImmutableList.of();
	}

	@Override
	public List<DocumentFilter> getFilters()
	{
		return ImmutableList.of();
	}

	@Override
	public List<DocumentQueryOrderBy> getDefaultOrderBys()
	{
		return ImmutableList.of();
	}

	@Override
	public String getSqlWhereClause(final Collection<DocumentId> viewDocumentIds)
	{
		return null; // not supported
	}

	@Override
	public boolean hasAttributesSupport()
	{
		return true;
	}

	@Override
	public <T> List<T> retrieveModelsByIds(final Collection<DocumentId> documentIds, final Class<T> modelClass)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Stream<PPOrderLineRow> streamByIds(final Collection<DocumentId> documentIds)
	{
		return getData().streamByIds(documentIds);
	}

	/** @return top level rows and included rows recursive stream */
	public Stream<PPOrderLineRow> streamAllRecursive()
	{
		return getData().streamRecursive();
	}

	@Override
	public void notifyRecordsChanged(final Set<TableRecordReference> recordRefs)
	{
		// TODO: notifyRecordsChanged: identify the sub-trees which could be affected and invalidate only those
	}

	public void invalidateAll()
	{
		invalidateAllNoNotify();

		DocumentViewChangesCollector.getCurrentOrAutoflush()
				.collectFullyChanged(this);
	}

	private void invalidateAllNoNotify()
	{
		if (asiAttributesProvider != null)
		{
			asiAttributesProvider.invalidateAll();
		}
		
		dataSupplier.forget();
	}

	private PPOrderLinesViewData getData()
	{
		return dataSupplier.get();
	}

	@ViewAction(caption = "PPOrderLinesView.openViewsToIssue", precondition = IsSingleIssueLine.class)
	public OpenIncludedViewAction actionOpenViewForHUsToIssue(final Set<DocumentId> selectedDocumentIds)
	{
		final DocumentId selectedRowId = ListUtils.singleElement(selectedDocumentIds);
		final PPOrderLineRow selectedRow = getById(selectedRowId);

		if (!selectedRow.isIssue())
		{
			throw new IllegalStateException("Only issue lines are supported");
		}
		if(selectedRow.isProcessed())
		{
			throw new IllegalStateException("Row processed");
		}

		final List<Integer> huIdsToAvailableToIssue = Services.get(IHUPPOrderBL.class).retrieveHUsAvailableToIssue(selectedRow.getM_Product_ID());
		if (huIdsToAvailableToIssue.isEmpty())
		{
			// NOTE: don't throw EntityNotFoundException because that one would be ignored by frontend.
			throw new AdempiereException("No HUs to issue found");
		}

		final IDocumentViewsRepository viewsRepo = Adempiere.getSpringApplicationContext().getBean(IDocumentViewsRepository.class); // TODO dirty workaround
		final IDocumentViewSelection husToIssueView = viewsRepo.createView(DocumentViewCreateRequest.builder(WEBUI_HU_Constants.WEBUI_HU_Window_ID, JSONViewDataType.includedView)
				.setParentViewId(getViewId())
				.setFilterOnlyIds(huIdsToAvailableToIssue)
				.addActionsFromUtilityClass(PPOrderHUsToIssueActions.class)
				.build());

		return OpenIncludedViewAction.builder()
				.viewId(husToIssueView.getViewId())
				.build();
	}

	public static final class IsSingleIssueLine implements ViewAction.Precondition
	{
		@Override
		public ProcessPreconditionsResolution matches(final IDocumentViewSelection view, final Set<DocumentId> selectedDocumentIds)
		{
			if (selectedDocumentIds.size() != 1)
			{
				return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
			}
			
			final PPOrderLinesView ppOrder = cast(view);
			if (!(ppOrder.isStatusPlanning() || ppOrder.isStatusReview()))
			{
				return ProcessPreconditionsResolution.rejectWithInternalReason("not in planning or in review");
			}

			final DocumentId selectedDocumentId = ListUtils.singleElement(selectedDocumentIds);
			final PPOrderLineRow ppOrderLine = ppOrder.getById(selectedDocumentId);
			if (!ppOrderLine.isIssue())
			{
				return ProcessPreconditionsResolution.reject("not an issue line");
			}
			if(ppOrderLine.isProcessed())
			{
				return ProcessPreconditionsResolution.rejectWithInternalReason("row processed");
			}
			return ProcessPreconditionsResolution.accept();
		}
	}
}
