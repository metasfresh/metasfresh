package de.metas.ui.web.pporder;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.document.engine.DocStatus;
import de.metas.i18n.ITranslatableString;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.order.OrderLineId;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.handlingunits.report.HUReportAwareView;
import de.metas.ui.web.handlingunits.report.HUReportProcessInstancesRepository;
import de.metas.ui.web.process.ProcessHandlerType;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.ViewFilterParameterLookupEvaluationCtx;
import de.metas.ui.web.view.ViewHeaderProperties;
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
import de.metas.ui.web.window.model.DocumentQueryOrderByList;
import de.metas.ui.web.window.model.sql.SqlOptions;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.PPOrderDocBaseType;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.PPOrderPlanningStatus;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
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

public class PPOrderLinesView implements IView, HUReportAwareView
{
	@Getter
	private final ViewId parentViewId;
	@Getter
	private final DocumentId parentRowId;

	@Getter
	private final ViewId viewId;
	@Getter
	private final JSONViewDataType viewType;
	@Getter
	private final ImmutableSet<DocumentPath> referencingDocumentPaths;

	@Getter private final PPOrderId ppOrderId;
	@Getter private final PPOrderDocBaseType docBaseType;
	@Getter private final DocStatus docStatus;
	@Getter private final OrderLineId salesOrderLineId;

	private final PPOrderLinesViewDataSupplier dataSupplier;

	final ImmutableList<RelatedProcessDescriptor> additionalRelatedProcessDescriptors;

	public static PPOrderLinesView cast(final IView view)
	{
		return (PPOrderLinesView)view;
	}

	@Builder
	private PPOrderLinesView(
			@Nullable final ViewId parentViewId,
			@Nullable final DocumentId parentRowId,
			@NonNull final ViewId viewId,
			@NonNull final JSONViewDataType viewType,
			@Nullable final Set<DocumentPath> referencingDocumentPaths,
			@NonNull final PPOrderId ppOrderId,
			@NonNull final PPOrderDocBaseType docBaseType,
			@NonNull final DocStatus docStatus,
			@Nullable final OrderLineId salesOrderLineId,
			@NonNull final PPOrderLinesViewDataSupplier dataSupplier,
			@NonNull final List<RelatedProcessDescriptor> additionalRelatedProcessDescriptors)
	{
		this.parentViewId = parentViewId; // might be null
		this.parentRowId = parentRowId; // might be null
		this.viewId = viewId;
		this.viewType = viewType;
		this.referencingDocumentPaths = referencingDocumentPaths == null ? ImmutableSet.of() : ImmutableSet.copyOf(referencingDocumentPaths);

		this.additionalRelatedProcessDescriptors = ImmutableList.copyOf(additionalRelatedProcessDescriptors);

		this.ppOrderId = ppOrderId;
		this.docBaseType = docBaseType;
		this.docStatus = docStatus;
		this.salesOrderLineId = salesOrderLineId;

		this.dataSupplier = dataSupplier;
	}

	@Override
	public ITranslatableString getDescription()
	{
		return getData().getDescription();
	}

	public PPOrderPlanningStatus getPlanningStatus()
	{
		return getData().getPlanningStatus();
	}

	public boolean isStatusPlanning()
	{
		return PPOrderPlanningStatus.PLANNING.equals(getPlanningStatus());
	}

	public boolean isStatusReview()
	{
		return PPOrderPlanningStatus.REVIEW.equals(getPlanningStatus());
	}

	@Override
	public ViewHeaderProperties getHeaderProperties() {return getData().getHeaderProperties();}

	/**
	 * @param documentId may be {@code null}; in that case, the method also returns {@code null}
	 * @return the table name for the given row
	 */
	@Override
	public String getTableNameOrNull(@Nullable final DocumentId documentId)
	{
		if (documentId == null)
		{
			return null;
		}
		final PPOrderLineRow ppOrderLine = getById(documentId);
		if (ppOrderLine == null)
		{
			return null; // just be sure to avoid an NPE in here
		}
		return ppOrderLine.getType().getTableName();
	}

	@Override
	public long size()
	{
		return getData().size();
	}

	@Override
	public void afterDestroy()
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
	public ViewResult getPage(
			final int firstRow,
			final int pageLength,
			@NonNull final ViewRowsOrderBy orderBys)
	{
		final Stream<PPOrderLineRow> stream = getData().stream()
				.skip(firstRow)
				.limit(pageLength);

		final List<IViewRow> page = stream.collect(GuavaCollectors.toImmutableList());

		return ViewResult.ofViewAndPage(this, firstRow, pageLength, orderBys.toDocumentQueryOrderByList(), page);
	}

	@Override
	public PPOrderLineRow getById(final DocumentId documentId) throws EntityNotFoundException
	{
		final PPOrderLineRowId ppOrderLineRowId = PPOrderLineRowId.fromDocumentId(documentId);
		return getData().getById(ppOrderLineRowId);
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
	public SqlViewRowsWhereClause getSqlWhereClause(final DocumentIdsSelection viewDocumentIds, final SqlOptions sqlOpts)
	{
		return null; // not supported
	}

	@Override
	public boolean hasAttributesSupport()
	{
		return true;
	}

	@Override
	public <T> List<T> retrieveModelsByIds(
			@NonNull final DocumentIdsSelection documentIds,
			@NonNull final Class<T> modelClass)
	{
		return streamByIds(documentIds)
				.map(ppOrderLineRow -> getModel(ppOrderLineRow, modelClass))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toList());
	}

	/**
	 * loads and returns the given {@code ppOrderLineRow}'s {@code PP_Order} or {@code P_Order_BOMLine}, if available.
	 */
	private <T> Optional<T> getModel(
			@NonNull final PPOrderLineRow ppOrderLineRow,
			@NonNull final Class<T> modelClass)
	{
		if (I_PP_Order.class.isAssignableFrom(modelClass))
		{
			if (ppOrderLineRow.getOrderId() == null)
			{
				return Optional.empty();
			}
			else
			{
				final I_PP_Order order = Services.get(IPPOrderDAO.class).getById(ppOrderLineRow.getOrderId());
				return Optional.of(InterfaceWrapperHelper.create(order, modelClass));
			}
		}
		else if (I_PP_Order_BOMLine.class.isAssignableFrom(modelClass))
		{
			if (ppOrderLineRow.getOrderBOMLineId() == null)
			{
				return Optional.empty();
			}
			else
			{
				final I_PP_Order_BOMLine orderBOMLine = Services.get(IPPOrderBOMDAO.class).getOrderBOMLineById(ppOrderLineRow.getOrderBOMLineId());
				return Optional.of(InterfaceWrapperHelper.create(orderBOMLine, modelClass));
			}
		}
		else
		{
			return Optional.empty();
		}
	}

	@Override
	public Stream<PPOrderLineRow> streamByIds(final DocumentIdsSelection documentIds)
	{
		return getData().streamByIds(documentIds);
	}

	@Override
	public void notifyRecordsChanged(
			@NonNull final TableRecordReferenceSet recordRefs,
			final boolean watchedByFrontend)
	{
		// TODO: notifyRecordsChanged: identify the sub-trees which could be affected and invalidate only those
	}

	@Override
	public List<RelatedProcessDescriptor> getAdditionalRelatedProcessDescriptors()
	{
		if (docStatus.isCompleted())
		{
			return additionalRelatedProcessDescriptors;
		}
		else
		{
			return ImmutableList.of();
		}
	}

	@Override
	public void invalidateAll()
	{
		invalidateAllNoNotify();

		ViewChangesCollector.getCurrentOrAutoflush()
				.collectFullyChanged(this);
	}

	private void invalidateAllNoNotify()
	{
		dataSupplier.invalidate();
	}

	private PPOrderLinesViewData getData()
	{
		return dataSupplier.getData();
	}

	@Override
	public boolean isConsiderTableRelatedProcessDescriptors(@NonNull final ProcessHandlerType processHandlerType, final @NonNull DocumentIdsSelection selectedRowIds)
	{
		return ProcessHandlerType.equals(processHandlerType, HUReportProcessInstancesRepository.PROCESS_HANDLER_TYPE);
	}
}
