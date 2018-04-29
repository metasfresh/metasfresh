package de.metas.ui.web.order.sales.pricingConditions.view;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.adempiere.util.Services;
import org.adempiere.util.collections.ListUtils;
import org.compiere.util.CCache;
import org.compiere.util.Util.ArrayKey;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalNotification;

import de.metas.i18n.ITranslatableString;
import de.metas.process.IADProcessDAO;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.DocumentFiltersList;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.order.sales.pricingConditions.process.PricingConditionsView_CopyRowToEditable;
import de.metas.ui.web.order.sales.pricingConditions.process.PricingConditionsView_SaveEditableRow;
import de.metas.ui.web.order.sales.pricingConditions.process.WEBUI_SalesOrder_PricingConditionsView_Launcher;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.IViewsIndexStorage;
import de.metas.ui.web.view.ViewCloseReason;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONFilterViewRequest;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

@ViewFactory(windowId = PricingConditionsViewFactory.WINDOW_ID_STRING)
public class PricingConditionsViewFactory implements IViewFactory, IViewsIndexStorage
{
	public static final String WINDOW_ID_STRING = "soPricingConditions";
	public static final WindowId WINDOW_ID = WindowId.fromJson(WINDOW_ID_STRING);

	private final CCache<ArrayKey, ViewLayout> //
	viewLayoutCache = CCache.newCache(PricingConditionsViewFactory.class + "#ViewLayout", 1, 0);

	private final Cache<ViewId, PricingConditionsView> views = CacheBuilder.newBuilder()
			.expireAfterAccess(1, TimeUnit.HOURS)
			.removalListener(notification -> onViewRemoved(notification))
			.build();

	private final PricingConditionsViewFilters filtersFactory = new PricingConditionsViewFilters();

	@Override
	public WindowId getWindowId()
	{
		return WINDOW_ID;
	}

	@Override
	public ViewLayout getViewLayout(final WindowId windowId, final JSONViewDataType viewDataType, final ViewProfileId profileId)
	{
		final ArrayKey key = ArrayKey.of(windowId, viewDataType);
		return viewLayoutCache.getOrLoad(key, () -> createViewLayout(windowId, viewDataType));
	}

	private ViewLayout createViewLayout(final WindowId windowId, final JSONViewDataType viewDataType)
	{
		final ITranslatableString caption = Services.get(IADProcessDAO.class)
				.retrieveProcessNameByClassIfUnique(WEBUI_SalesOrder_PricingConditionsView_Launcher.class)
				.orElse(null);

		return ViewLayout.builder()
				.setWindowId(windowId)
				.setCaption(caption)
				.setFilters(filtersFactory.getFilterDescriptors())
				.addElementsFromViewRowClass(PricingConditionsRow.class, viewDataType)
				.build();
	}

	private final void onViewRemoved(final RemovalNotification<Object, Object> notification)
	{
		final PricingConditionsView view = PricingConditionsView.cast(notification.getValue());
		final ViewCloseReason closeReason = ViewCloseReason.fromCacheEvictedFlag(notification.wasEvicted());
		view.close(closeReason);

		if (closeReason == ViewCloseReason.USER_REQUEST)
		{
			view.updateSalesOrderLineIfPossible();
		}
	}

	@Override
	public void put(final IView view)
	{
		views.put(view.getViewId(), PricingConditionsView.cast(view));
	}

	@Override
	public PricingConditionsView getByIdOrNull(final ViewId viewId)
	{
		return views.getIfPresent(viewId);
	}

	public PricingConditionsView getById(final ViewId viewId)
	{
		final PricingConditionsView view = getByIdOrNull(viewId);
		if (view == null)
		{
			throw new EntityNotFoundException(viewId.toJson());
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
	public IView createView(final CreateViewRequest request)
	{
		final ViewId viewId = ViewId.random(WINDOW_ID);

		final int salesOrderLineId = ListUtils.singleElement(request.getFilterOnlyIds());
		final PricingConditionsRowData rowsData = PricingConditionsRowsLoader.builder()
				.salesOrderLineId(salesOrderLineId)
				.filters(filtersFactory.extractFilters(request))
				.build()
				.load();

		return PricingConditionsView.builder()
				.viewId(viewId)
				.rowsData(rowsData)
				.relatedProcessDescriptor(createProcessDescriptor(PricingConditionsView_CopyRowToEditable.class))
				.relatedProcessDescriptor(createProcessDescriptor(PricingConditionsView_SaveEditableRow.class))
				.filterDescriptors(filtersFactory.getFilterDescriptorsProvider())
				.build();
	}

	@Override
	public IView filterView(final IView viewObj, final JSONFilterViewRequest filterViewRequest)
	{
		final DocumentFilterDescriptorsProvider filtersDescriptors = filtersFactory.getFilterDescriptorsProvider();
		final DocumentFiltersList filters = DocumentFiltersList.ofJSONFilters(filterViewRequest.getFilters())
				.unwrapAndCopy(filtersDescriptors);

		return PricingConditionsView.cast(viewObj).filter(filters);
	}

	private final RelatedProcessDescriptor createProcessDescriptor(@NonNull final Class<?> processClass)
	{
		final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
		return RelatedProcessDescriptor.builder()
				.processId(adProcessDAO.retrieveProcessIdByClass(processClass))
				.anyTable().anyWindow()
				.webuiQuickAction(true)
				.build();
	}
}
