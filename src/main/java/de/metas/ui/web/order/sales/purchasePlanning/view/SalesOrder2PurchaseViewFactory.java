package de.metas.ui.web.order.sales.purchasePlanning.view;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.compiere.model.I_C_Order;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalNotification;
import com.google.common.collect.ImmutableSet;

import de.metas.document.engine.IDocument;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateRepository;
import de.metas.purchasecandidate.async.C_PurchaseCandidates_GeneratePurchaseOrders;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.IViewsIndexStorage;
import de.metas.ui.web.view.ViewCloseReason;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;

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
	private final PurchaseCandidateRepository purchaseCandidatesRepo;

	//
	private final Cache<ViewId, PurchaseView> views = CacheBuilder.newBuilder()
			.expireAfterAccess(1, TimeUnit.HOURS)
			.removalListener(notification -> onViewRemoved(notification))
			.build();

	public SalesOrder2PurchaseViewFactory(final PurchaseCandidateRepository purchaseCandidatesRepo)
	{
		this.purchaseCandidatesRepo = purchaseCandidatesRepo;
	}

	@Override
	public WindowId getWindowId()
	{
		return WINDOW_ID;
	}

	@Override
	public ViewLayout getViewLayout(final WindowId windowId, final JSONViewDataType viewDataType)
	{
		return ViewLayout.builder()
				.setWindowId(windowId)
				//
				.setHasAttributesSupport(false)
				.setHasTreeSupport(true)
				//
				.addElementsFromViewRowClass(PurchaseRow.class, viewDataType)
				//
				.build();
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
	public PurchaseView createView(final CreateViewRequest request)
	{
		final Set<Integer> salesOrderLineIds = request.getFilterOnlyIds();
		Check.assumeNotEmpty(salesOrderLineIds, "salesOrderLineIds is not empty");

		final PurchaseView view = PurchaseView.builder()
				.viewId(ViewId.random(WINDOW_ID))
				.rowsSupplier(() -> loadRows(salesOrderLineIds))
				.build();

		return view;
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

	private List<PurchaseRow> loadRows(final Set<Integer> salesOrderLineIds)
	{
		return PurchaseRowsLoader.builder()
				.salesOrderLineIds(salesOrderLineIds)
				.purchaseCandidatesRepo(purchaseCandidatesRepo)
				.build()
				.load();
	}

	private void saveRowsAndEnqueueIfOrderCompleted(final PurchaseView purchaseView)
	{
		final List<PurchaseCandidate> purchaseCandidates = saveRows(purchaseView);

		//
		// If the sales order was already completed, enqueue the purchase candidates
		final I_C_Order salesOrder = getSingleSalesOrder(purchaseCandidates);
		if (IDocument.STATUS_Completed.equals(salesOrder.getDocStatus()))
		{
			final Set<Integer> purchaseCandidateIds = purchaseCandidates.stream()
					.filter(purchaseCandidate -> !purchaseCandidate.isProcessed())
					.map(PurchaseCandidate::getRepoId)
					.collect(ImmutableSet.toImmutableSet());
			C_PurchaseCandidates_GeneratePurchaseOrders.enqueue(purchaseCandidateIds);
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

	private final I_C_Order getSingleSalesOrder(final List<PurchaseCandidate> purchaseCandidates)
	{
		final int salesOrderId = purchaseCandidates.stream()
				.map(PurchaseCandidate::getSalesOrderId)
				.distinct()
				.collect(GuavaCollectors.singleElementOrThrow(() -> new AdempiereException("more than one salesOrderId found")));
		final I_C_Order salesOrder = load(salesOrderId, I_C_Order.class);
		return salesOrder;
	}
}
