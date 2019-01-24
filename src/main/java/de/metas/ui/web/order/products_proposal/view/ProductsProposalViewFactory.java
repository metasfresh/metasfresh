package de.metas.ui.web.order.products_proposal.view;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.compiere.model.I_C_Order;
import org.compiere.util.TimeUtil;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalNotification;

import de.metas.cache.CCache;
import de.metas.i18n.ITranslatableString;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.pricing.PriceListId;
import de.metas.process.IADProcessDAO;
import de.metas.ui.web.order.products_proposal.process.WEBUI_Order_ProductsProposal_Launcher;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.IViewsIndexStorage;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewCloseReason;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

@ViewFactory(windowId = ProductsProposalViewFactory.WINDOW_ID_STRING)
public class ProductsProposalViewFactory implements IViewFactory, IViewsIndexStorage
{
	public static final String WINDOW_ID_STRING = "orderLineProductsProposal";
	public static final WindowId WINDOW_ID = WindowId.fromJson(WINDOW_ID_STRING);

	private final CCache<ViewLayoutKey, ViewLayout> viewLayoutCache = CCache.<ViewLayoutKey, ViewLayout> builder()
			.cacheName(ProductsProposalViewFactory.class.getName() + "#ViewLayout")
			.initialCapacity(1)
			.build();

	private final Cache<ViewId, ProductsProposalView> views = CacheBuilder.newBuilder()
			.expireAfterAccess(1, TimeUnit.HOURS)
			.removalListener(this::onViewRemoved)
			.build();

	@Override
	public void setViewsRepository(final IViewsRepository viewsRepository)
	{
	}

	@Override
	public WindowId getWindowId()
	{
		return WINDOW_ID;
	}

	@Override
	public ViewLayout getViewLayout(final WindowId windowId, final JSONViewDataType viewDataType, final ViewProfileId profileId)
	{
		final ViewLayoutKey key = ViewLayoutKey.of(windowId, viewDataType);
		return viewLayoutCache.getOrLoad(key, this::createViewLayout);
	}

	private ViewLayout createViewLayout(final ViewLayoutKey key)
	{
		final ITranslatableString caption = Services.get(IADProcessDAO.class)
				.retrieveProcessNameByClassIfUnique(WEBUI_Order_ProductsProposal_Launcher.class)
				.orElse(null);

		return ViewLayout.builder()
				.setWindowId(key.getWindowId())
				.setCaption(caption)
				.addElementsFromViewRowClass(ProductsProposalRow.class, key.getViewDataType())
				.build();
	}

	@Override
	public IView createView(@NonNull final CreateViewRequest request)
	{
		final ProductsProposalRowsData rowsData = loadRowsData(request);

		return ProductsProposalView.builder()
				.windowId(getWindowId())
				.rowsData(rowsData)
				.build();
	}

	private ProductsProposalRowsData loadRowsData(final CreateViewRequest request)
	{
		final IOrderDAO ordersRepo = Services.get(IOrderDAO.class);

		final OrderId orderId = OrderId.ofRepoId(CollectionUtils.singleElement(request.getFilterOnlyIds()));
		final I_C_Order orderRecord = ordersRepo.getById(orderId);
		final LocalDate datePromised = TimeUtil.asLocalDate(orderRecord.getDatePromised());
		final PriceListId priceListId = PriceListId.ofRepoId(orderRecord.getM_PriceList_ID());

		return ProductsProposalRowsLoader.builder()
				.priceListId(priceListId)
				.date(datePromised)
				.orderId(orderId)
				.build()
				.load();
	}

	@Override
	public void put(final IView view)
	{
		views.put(view.getViewId(), ProductsProposalView.cast(view));
	}

	@Override
	public IView getByIdOrNull(final ViewId viewId)
	{
		return views.getIfPresent(viewId);
	}

	@Override
	public void removeById(final ViewId viewId)
	{
		views.invalidate(viewId);
		views.cleanUp();
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

	private final void onViewRemoved(final RemovalNotification<Object, Object> notification)
	{
		final ProductsProposalView view = ProductsProposalView.cast(notification.getValue());
		final ViewCloseReason closeReason = ViewCloseReason.fromCacheEvictedFlag(notification.wasEvicted());
		view.close(closeReason);

		if (closeReason == ViewCloseReason.USER_REQUEST)
		{
			onViewClosedByUser(view);
		}
	}

	private void onViewClosedByUser(final ProductsProposalView view)
	{
		OrderLinesFromProductProposalsProducer.builder()
				.orderId(view.getOrderId())
				.rows(view.getRowsWithQtySet())
				.build()
				.produce();
	}

	@lombok.Value(staticConstructor = "of")
	private static final class ViewLayoutKey
	{
		WindowId windowId;
		JSONViewDataType viewDataType;
	}
}
