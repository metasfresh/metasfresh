package de.metas.ui.web.order.sales.purchasePlanning.view;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalNotification;

import de.metas.purchasecandidate.PurchaseCandidateRepository;
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

		final PurchaseView view = PurchaseView.builder()
				.viewId(ViewId.random(WINDOW_ID))
				.rowsSupplier(() -> loadRows(salesOrderLineIds))
				.onViewClosed(this::onViewClosed)
				.build();

		return view;
	}

	private final void onViewRemoved(final RemovalNotification<Object, Object> notification)
	{
		final PurchaseView view = PurchaseView.cast(notification.getValue());
		final ViewCloseReason closeReason = ViewCloseReason.fromCacheEvictedFlag(notification.wasEvicted());
		view.close(closeReason);
	}

	private void onViewClosed(final PurchaseView purchaseView, final ViewCloseReason reason)
	{
		if (reason == ViewCloseReason.USER_REQUEST)
		{
			saveRows(purchaseView);
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

	private void saveRows(final PurchaseView purchaseView)
	{
		PurchaseRowsSaver.builder()
				.grouppingRows(purchaseView.getRows())
				.purchaseCandidatesRepo(purchaseCandidatesRepo)
				.build()
				.save();
	}
}
