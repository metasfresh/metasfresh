package de.metas.ui.web.order.sales.purchasePlanning.view;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Order;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalNotification;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.document.engine.IDocument;
import de.metas.i18n.ITranslatableString;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.process.IADProcessDAO;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateRepository;
import de.metas.purchasecandidate.PurchaseDemand;
import de.metas.purchasecandidate.PurchaseDemandId;
import de.metas.purchasecandidate.PurchaseDemandWithCandidates;
import de.metas.purchasecandidate.PurchaseDemandWithCandidatesService;
import de.metas.purchasecandidate.SalesOrder;
import de.metas.purchasecandidate.SalesOrderLine;
import de.metas.purchasecandidate.SalesOrderLineRepository;
import de.metas.purchasecandidate.async.C_PurchaseCandidates_GeneratePurchaseOrders;
import de.metas.purchasecandidate.availability.AvailabilityCheckService;
import de.metas.quantity.Quantity;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.order.sales.purchasePlanning.process.WEBUI_SalesOrder_Apply_Availability_Row;
import de.metas.ui.web.order.sales.purchasePlanning.process.WEBUI_SalesOrder_PurchaseView_Launcher;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.IViewsIndexStorage;
import de.metas.ui.web.view.ViewCloseReason;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;
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

@ViewFactory(windowId = SalesOrder2PurchaseViewFactory.WINDOW_ID_STRING)
public class SalesOrder2PurchaseViewFactory implements IViewFactory, IViewsIndexStorage
{
	public static final String WINDOW_ID_STRING = "SO2PO";
	public static final WindowId WINDOW_ID = WindowId.fromJson(WINDOW_ID_STRING);

	// services
	private final PurchaseDemandWithCandidatesService purchaseDemandWithCandidatesService;
	private final AvailabilityCheckService availabilityCheckService;
	private final PurchaseCandidateRepository purchaseCandidatesRepo;
	private final PurchaseRowFactory purchaseRowFactory;
	private final PurchaseViewLayoutFactory viewLayoutFactory;
	private final SalesOrderLineRepository salesOrderLineRepository;

	//
	private final Cache<ViewId, PurchaseView> views = CacheBuilder.newBuilder()
			.expireAfterAccess(1, TimeUnit.HOURS)
			.removalListener(notification -> onViewRemoved(notification))
			.build();

	public SalesOrder2PurchaseViewFactory(
			@NonNull final PurchaseDemandWithCandidatesService purchaseDemandWithCandidatesService,
			@NonNull final AvailabilityCheckService availabilityCheckService,
			@NonNull final PurchaseCandidateRepository purchaseCandidatesRepo,
			@NonNull final PurchaseRowFactory purchaseRowFactory,
			@NonNull final SalesOrderLineRepository salesOrderLineRepository)
	{
		this.purchaseDemandWithCandidatesService = purchaseDemandWithCandidatesService;
		this.availabilityCheckService = availabilityCheckService;
		this.purchaseCandidatesRepo = purchaseCandidatesRepo;
		this.purchaseRowFactory = purchaseRowFactory;
		this.salesOrderLineRepository = salesOrderLineRepository;

		final IADProcessDAO adProcessRepo = Services.get(IADProcessDAO.class);
		final ITranslatableString caption = adProcessRepo
				.retrieveProcessNameByClassIfUnique(WEBUI_SalesOrder_PurchaseView_Launcher.class)
				.orElse(null);
		viewLayoutFactory = PurchaseViewLayoutFactory.builder()
				.caption(caption)
				.build();
	}

	@Override
	public WindowId getWindowId()
	{
		return WINDOW_ID;
	}

	@Override
	public ViewLayout getViewLayout(
			@NonNull final WindowId windowId,
			@NonNull final JSONViewDataType viewDataType,
			@Nullable final ViewProfileId profileId)
	{
		return viewLayoutFactory.getViewLayout(windowId, viewDataType);
	}

	@Override
	public void put(final IView view)
	{
		views.put(view.getViewId(), PurchaseView.cast(view));
	}

	@Override
	public PurchaseView getByIdOrNull(final ViewId viewId)
	{
		return views.getIfPresent(viewId);
	}

	public PurchaseView getById(final ViewId viewId)
	{
		final PurchaseView view = getByIdOrNull(viewId);
		if (view == null)
		{
			throw new EntityNotFoundException("View " + viewId + " was not found");
		}
		return view;
	}

	@Override
	public void removeById(final ViewId viewId)
	{
		views.invalidate(viewId);
		views.cleanUp(); // also cleanup to prevent views cache to grow.
	}

	@Override
	public Stream<IView> streamAllViews()
	{
		return Stream.empty();
	}

	@Override
	public void invalidateView(final ViewId viewId)
	{
		final IView view = getByIdOrNull(viewId);
		if (view == null)
		{
			return;
		}

		view.invalidateAll();
	}

	@Override
	public PurchaseView createView(@NonNull final CreateViewRequest request)
	{
		final Set<OrderLineId> salesOrderLineIds = extractSalesOrderLineIds(request);
		Check.assumeNotEmpty(salesOrderLineIds, "salesOrderLineIds is not empty");

		final ViewId viewId = ViewId.random(WINDOW_ID);

		final List<PurchaseDemand> purchaseDemands = retrievePurchaseDemands(salesOrderLineIds);
		final List<PurchaseDemandWithCandidates> purchaseDemandWithCandidatesList = purchaseDemandWithCandidatesService.getOrCreatePurchaseCandidates(purchaseDemands);

		final PurchaseRowsSupplier rowsSupplier = PurchaseRowsLoader.builder()
				.purchaseDemandWithCandidatesList(purchaseDemandWithCandidatesList)
				.viewSupplier(() -> getByIdOrNull(viewId)) // needed for async stuff
				.purchaseRowFactory(purchaseRowFactory)
				.availabilityCheckService(availabilityCheckService)
				.build()
				.createPurchaseRowsSupplier();

		final PurchaseView view = PurchaseView.builder()
				.viewId(viewId)
				.rowsSupplier(rowsSupplier)
				.additionalRelatedProcessDescriptor(createProcessDescriptor(WEBUI_SalesOrder_Apply_Availability_Row.class))
				.build();

		return view;
	}

	private static Set<OrderLineId> extractSalesOrderLineIds(final CreateViewRequest request)
	{
		return request
				.getFilterOnlyIds()
				.stream()
				.map(OrderLineId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	private List<PurchaseDemand> retrievePurchaseDemands(final Collection<OrderLineId> salesOrderLineIds)
	{
		return salesOrderLineRepository.getByIds(salesOrderLineIds)
				.stream()
				.map(salesOrderLine -> createDemand(salesOrderLine))
				.collect(ImmutableList.toImmutableList());
	}

	@VisibleForTesting
	static PurchaseDemand createDemand(final SalesOrderLine salesOrderLine)
	{
		final SalesOrder salesOrder = salesOrderLine.getOrder();

		final Quantity qtyOrdered = salesOrderLine.getOrderedQty();
		final Quantity qtyDelivered = salesOrderLine.getDeliveredQty();
		final Quantity qtyToPurchase = qtyOrdered.subtract(qtyDelivered);

		return PurchaseDemand.builder()
				.id(PurchaseDemandId.ofOrderLineId(salesOrderLine.getId()))
				//
				.orgId(salesOrderLine.getOrgId())
				.warehouseId(salesOrderLine.getWarehouseId())
				//
				.productId(salesOrderLine.getProductId())
				.attributeSetInstanceId(salesOrderLine.getAsiId())
				//
				.qtyToDeliverTotal(qtyOrdered)
				.qtyToDeliver(qtyToPurchase)
				//
				.currency(salesOrderLine.getPriceActual().getCurrency())
				//
				.datePromised(TimeUtil.asLocalDateTime(salesOrderLine.getDatePromised()))
				.preparationDate(salesOrder.getPreparationDate())
				//
				.salesOrderId(salesOrderLine.getOrderId())
				.salesOrderLineId(salesOrderLine.getId())
				//
				.build();
	}

	private final void onViewRemoved(final RemovalNotification<Object, Object> notification)
	{
		final PurchaseView view = PurchaseView.cast(notification.getValue());
		final ViewCloseReason closeReason = ViewCloseReason.fromCacheEvictedFlag(notification.wasEvicted());
		view.close(closeReason);

		if (closeReason == ViewCloseReason.USER_REQUEST)
		{
			saveRowsAndEnqueueIfOrderCompleted(view);
		}
	}

	private void saveRowsAndEnqueueIfOrderCompleted(final PurchaseView purchaseView)
	{
		final List<PurchaseCandidate> purchaseCandidates = saveRows(purchaseView);
		if (purchaseCandidates.isEmpty())
		{
			return;
		}
		//
		// If the sales order was already completed, enqueue the purchase candidates
		final I_C_Order salesOrder = getSingleSalesOrder(purchaseCandidates);
		if (IDocument.STATUS_Completed.equals(salesOrder.getDocStatus()))
		{
			final Set<Integer> purchaseCandidateIds = purchaseCandidates.stream()
					.filter(purchaseCandidate -> !purchaseCandidate.isProcessedOrLocked())
					.filter(purchaseCandidate -> purchaseCandidate.getQtyToPurchase().signum() > 0)
					.map(PurchaseCandidate::getPurchaseCandidateId)
					.collect(ImmutableSet.toImmutableSet());
			if (purchaseCandidateIds.size() > 0)
			{
				C_PurchaseCandidates_GeneratePurchaseOrders.enqueue(purchaseCandidateIds);
			}
		}
	}

	private List<PurchaseCandidate> saveRows(final PurchaseView purchaseView)
	{
		final List<PurchaseRow> rows = purchaseView.getRows();

		return PurchaseRowsSaver.builder()
				.grouppingRows(rows)
				.purchaseCandidatesRepo(purchaseCandidatesRepo)
				.build()
				.save();
	}

	private final I_C_Order getSingleSalesOrder(@NonNull final List<PurchaseCandidate> purchaseCandidates)
	{
		Check.assumeNotEmpty(purchaseCandidates, "purchaseCandidates not empty");

		final int salesOrderId = purchaseCandidates.stream()
				.map(PurchaseCandidate::getSalesOrderId)
				.filter(Predicates.notNull())
				.map(OrderId::getRepoId)
				.distinct()
				.collect(GuavaCollectors.singleElementOrThrow(() -> new AdempiereException("More or less than one salesOrderId found in the given purchaseCandidates")
						.appendParametersToMessage()
						.setParameter("purchaseCandidates", purchaseCandidates)));

		final I_C_Order salesOrder = load(salesOrderId, I_C_Order.class);
		return salesOrder;
	}

	private static RelatedProcessDescriptor createProcessDescriptor(@NonNull final Class<?> processClass)
	{
		final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

		final int processId = adProcessDAO.retriveProcessIdByClassIfUnique(Env.getCtx(), processClass);
		Preconditions.checkArgument(processId > 0, "No AD_Process_ID found for %s", processClass);

		return RelatedProcessDescriptor.builder()
				.processId(processId)
				.webuiQuickAction(true)
				.build();
	}
}
