package de.metas.ui.web.order.sales.purchasePlanning.view;

import static org.adempiere.model.InterfaceWrapperHelper.load;

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
import org.compiere.util.CCache;
import org.compiere.util.Env;
import org.compiere.util.Util.ArrayKey;

import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalNotification;
import com.google.common.collect.ImmutableSet;

import de.metas.document.engine.IDocument;
import de.metas.i18n.ITranslatableString;
import de.metas.process.IADProcessDAO;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateRepository;
import de.metas.purchasecandidate.SalesOrderLines;
import de.metas.purchasecandidate.async.C_PurchaseCandidates_GeneratePurchaseOrders;
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
	private final PurchaseCandidateRepository purchaseCandidatesRepo;
	private final PurchaseRowFactory purchaseRowsFactory;

	private final CCache<ArrayKey, ViewLayout> viewLayoutCache = //
			CCache.newCache(SalesOrder2PurchaseViewFactory.class + "#ViewLayout", 1, 0);

	//
	private final Cache<ViewId, PurchaseView> views = CacheBuilder.newBuilder()
			.expireAfterAccess(1, TimeUnit.HOURS)
			.removalListener(notification -> onViewRemoved(notification))
			.build();

	public SalesOrder2PurchaseViewFactory(
			@NonNull final PurchaseCandidateRepository purchaseCandidatesRepo,
			@NonNull final PurchaseRowFactory purchaseRowsFactory)
	{
		this.purchaseCandidatesRepo = purchaseCandidatesRepo;
		this.purchaseRowsFactory = purchaseRowsFactory;
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
		final ArrayKey key = ArrayKey.of(windowId, viewDataType);
		return viewLayoutCache.getOrLoad(key, () -> createViewLayout(windowId, viewDataType));
	}

	private ViewLayout createViewLayout(final WindowId windowId, final JSONViewDataType viewDataType)
	{
		final ITranslatableString caption = Services.get(IADProcessDAO.class)
				.retrieveProcessNameByClassIfUnique(WEBUI_SalesOrder_PurchaseView_Launcher.class)
				.orElse(null);

		return ViewLayout.builder()
				.setWindowId(windowId)
				.setCaption(caption)
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

		final ViewId viewId = ViewId.random(WINDOW_ID);

		final PurchaseRowsLoader rowsLoader = PurchaseRowsLoader.builder()
				.salesOrderLines(SalesOrderLines.builder()
						.salesOrderLineIds(salesOrderLineIds)
						.purchaseCandidateRepository(purchaseCandidatesRepo)
						.build())
				.viewSupplier(() -> this.getByIdOrNull(viewId)) // needed for async stuff
				.purchaseRowsFactory(purchaseRowsFactory)
				.build();

		final PurchaseRowsSupplier rowsSupplier = () -> {

			final List<PurchaseRow> loadResult = rowsLoader.load();
			rowsLoader.createAndAddAvailabilityResultRowsAsync();

			return loadResult;
		};

		final PurchaseView view = PurchaseView.builder()
				.viewId(viewId)
				.rowsSupplier(rowsSupplier)
				.additionalRelatedProcessDescriptor(createProcessDescriptor(WEBUI_SalesOrder_Apply_Availability_Row.class))
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
