/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.ui.web.order.pricingconditions.view;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Stream;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import de.metas.cache.CCache;
import de.metas.i18n.ITranslatableString;
import de.metas.process.IADProcessDAO;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.process.RelatedProcessDescriptor.DisplayPlace;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.order.pricingconditions.process.PricingConditionsView_CopyRowToEditable;
import de.metas.ui.web.order.pricingconditions.process.PricingConditionsView_SaveEditableRow;
import de.metas.ui.web.order.pricingconditions.process.WEBUI_SalesOrder_PricingConditionsView_Launcher;
import de.metas.ui.web.order.pricingconditions.view.PricingConditionsRowsLoader.PricingConditionsRowsLoaderBuilder;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.IViewsIndexStorage;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewCloseAction;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONFilterViewRequest;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.util.Services;
import lombok.NonNull;

import javax.annotation.Nullable;

public abstract class PricingConditionsViewFactoryTemplate implements IViewFactory, IViewsIndexStorage
{
	private final WindowId windowId;

	private final CCache<ViewLayoutKey, ViewLayout> //
	viewLayoutCache = CCache.newCache(OrderLinePricingConditionsViewFactory.class + "#ViewLayout", 1, 0);

	private final Cache<ViewId, PricingConditionsView> views = CacheBuilder.newBuilder()
			.expireAfterAccess(1, TimeUnit.HOURS)
			.build();

	private final PricingConditionsRowLookups lookups = PricingConditionsRowLookups.newInstance();
	private final PricingConditionsViewFilters filtersFactory = new PricingConditionsViewFilters();

	protected PricingConditionsViewFactoryTemplate(@NonNull final WindowId windowId)
	{
		this.windowId = windowId;
	}

	@Override
	public void setViewsRepository(final IViewsRepository viewsRepository)
	{
	}

	@Override
	public final WindowId getWindowId()
	{
		return windowId;
	}

	@Override
	public final ViewLayout getViewLayout(final WindowId windowId, final JSONViewDataType viewDataType, final ViewProfileId profileId)
	{
		final ViewLayoutKey key = ViewLayoutKey.of(windowId, viewDataType);
		return viewLayoutCache.getOrLoad(key, this::createViewLayout);
	}

	private ViewLayout createViewLayout(final ViewLayoutKey key)
	{
		final ITranslatableString caption = Services.get(IADProcessDAO.class)
				.retrieveProcessNameByClassIfUnique(WEBUI_SalesOrder_PricingConditionsView_Launcher.class)
				.orElse(null);

		return ViewLayout.builder()
				.setWindowId(key.getWindowId())
				.setCaption(caption)
				.setFilters(filtersFactory.getFilterDescriptors())
				.addElementsFromViewRowClass(PricingConditionsRow.class, key.getViewDataType())
				.build();
	}

	protected void onViewClosedByUser(final PricingConditionsView view)
	{
		// nothing on this level
	}

	@Override
	public final void put(final IView view)
	{
		views.put(view.getViewId(), PricingConditionsView.cast(view));
	}

	@Nullable
	@Override
	public final PricingConditionsView getByIdOrNull(final ViewId viewId)
	{
		return views.getIfPresent(viewId);
	}

	public final PricingConditionsView getById(final ViewId viewId)
	{
		final PricingConditionsView view = getById(viewId);
		if (view == null)
		{
			throw new EntityNotFoundException("View not found: " + viewId.toJson());
		}
		return view;
	}

	@Override
	public final void closeById(@NonNull final ViewId viewId, @NonNull final ViewCloseAction closeAction)
	{
		final PricingConditionsView view = views.getIfPresent(viewId);
		if (view == null || !view.isAllowClosingPerUserRequest())
		{
			return;
		}

		if (closeAction.isDone())
		{
			onViewClosedByUser(view);
		}

		views.invalidate(viewId);
		views.cleanUp(); // also cleanup to prevent views cache to grow.
	}

	@Override
	public final Stream<IView> streamAllViews()
	{
		return Stream.empty();
	}

	@Override
	public final void invalidateView(final ViewId viewId)
	{
		final PricingConditionsView view = getByIdOrNull(viewId);
		if (view == null)
		{
			return;
		}

		view.invalidateAll();
	}

	@Override
	public final PricingConditionsView createView(@NonNull final CreateViewRequest request)
	{
		final PricingConditionsRowData rowsData = createPricingConditionsRowData(request);
		return createView(rowsData);
	}

	protected abstract PricingConditionsRowData createPricingConditionsRowData(CreateViewRequest request);

	private PricingConditionsView createView(final PricingConditionsRowData rowsData)
	{
		return PricingConditionsView.builder()
				.viewId(ViewId.random(windowId))
				.rowsData(rowsData)
				.relatedProcessDescriptor(createProcessDescriptor(PricingConditionsView_CopyRowToEditable.class))
				.relatedProcessDescriptor(createProcessDescriptor(PricingConditionsView_SaveEditableRow.class))
				.filterDescriptors(filtersFactory.getFilterDescriptorsProvider())
				.build();
	}

	protected final PricingConditionsRowsLoaderBuilder preparePricingConditionsRowData()
	{
		return PricingConditionsRowsLoader.builder()
				.lookups(lookups);
	}

	@Override
	public PricingConditionsView filterView(
			@NonNull final IView view,
			@NonNull final JSONFilterViewRequest filterViewRequest,
			final Supplier<IViewsRepository> viewsRepo_IGNORED)
	{
		return PricingConditionsView.cast(view)
				.filter(filtersFactory.extractFilters(filterViewRequest));
	}

	private final RelatedProcessDescriptor createProcessDescriptor(@NonNull final Class<?> processClass)
	{
		final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
		return RelatedProcessDescriptor.builder()
				.processId(adProcessDAO.retrieveProcessIdByClass(processClass))
				.anyTable()
				.anyWindow()
				.displayPlace(DisplayPlace.ViewQuickActions)
				.build();
	}

	protected final DocumentFilterList extractFilters(final CreateViewRequest request)
	{
		return filtersFactory.extractFilters(request);
	}

	@lombok.Value(staticConstructor = "of")
	private static final class ViewLayoutKey
	{
		WindowId windowId;
		JSONViewDataType viewDataType;
	}
}
