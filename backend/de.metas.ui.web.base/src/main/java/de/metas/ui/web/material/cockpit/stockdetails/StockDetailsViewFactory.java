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

package de.metas.ui.web.material.cockpit.stockdetails;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.adempiere.mm.attributes.AttributeId;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.model.I_M_HU_Stock_Detail_V;
import de.metas.handlingunits.stock.HUStockInfo;
import de.metas.handlingunits.stock.HUStockInfoQuery;
import de.metas.handlingunits.stock.HUStockInfoQuery.HUStockInfoQueryBuilder;
import de.metas.handlingunits.stock.HUStockInfoQuery.HUStockInfoSingleQuery;
import de.metas.handlingunits.stock.HUStockInfoQuery.HUStockInfoSingleQuery.AttributeValue;
import de.metas.handlingunits.stock.HUStockInfoQuery.HUStockInfoSingleQuery.HUStockInfoSingleQueryBuilder;
import de.metas.handlingunits.stock.HUStockInfoRepository;
import de.metas.product.ProductId;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.provider.ImmutableDocumentFilterDescriptorsProvider;
import de.metas.ui.web.material.cockpit.MaterialCockpitUtil;
import de.metas.ui.web.material.cockpit.filters.ProductFilterUtil;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.IViewsIndexStorage;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewCloseAction;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;
import lombok.NonNull;

import javax.annotation.Nullable;

@ViewFactory(windowId = MaterialCockpitUtil.WINDOW_MaterialCockpit_StockDetail_String)
public class StockDetailsViewFactory implements IViewFactory, IViewsIndexStorage
{
	private final Cache<ViewId, StockDetailsView> views = CacheBuilder.newBuilder()
			.expireAfterAccess(1, TimeUnit.HOURS)
			// .removalListener(notification -> onViewRemoved(notification))
			.build();

	private final transient HUStockInfoRepository huStockInfoRepository;

	public StockDetailsViewFactory(@NonNull final HUStockInfoRepository huStockInfoRepository)
	{
		this.huStockInfoRepository = huStockInfoRepository;
	}

	@Override
	public void setViewsRepository(final IViewsRepository viewsRepository)
	{
	}

	@Override
	public WindowId getWindowId()
	{
		return MaterialCockpitUtil.WINDOW_MaterialCockpit_StockDetailView;
	}

	@Override
	public void put(@NonNull final IView view)
	{
		final StockDetailsView stockDetailsView = StockDetailsView.cast(view);
		views.put(view.getViewId(), stockDetailsView);

	}

	@Nullable
	@Override
	public StockDetailsView getByIdOrNull(final ViewId viewId)
	{
		return views.getIfPresent(viewId);
	}

	@Override
	public void closeById(@NonNull final ViewId viewId, @NonNull final ViewCloseAction closeAction)
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
		final StockDetailsView view = getByIdOrNull(viewId);
		if (view == null)
		{
			return;
		}

		view.invalidateAll();
	}

	@Override
	public IView createView(@NonNull final CreateViewRequest request)
	{
		final StockDetailsRowsData stockDetailsRowData = createStockDetailsRowData(request);

		return new StockDetailsView(
				request.getViewId(),
				request.getParentViewId(),
				stockDetailsRowData,
				ImmutableDocumentFilterDescriptorsProvider.of(createEmptyFilterDescriptor()));
	}

	private DocumentFilterDescriptor createEmptyFilterDescriptor()
	{
		return ProductFilterUtil.createFilterDescriptor();
	}

	private StockDetailsRowsData createStockDetailsRowData(@NonNull final CreateViewRequest request)
	{
		final DocumentFilterList filters = request.getStickyFilters();

		final HUStockInfoQueryBuilder query = HUStockInfoQuery.builder();

		for (final DocumentFilter filter : filters.toList())
		{
			final HUStockInfoSingleQueryBuilder singleQuery = HUStockInfoSingleQuery.builder();
			final int productRepoId = filter.getParameterValueAsInt(I_M_HU_Stock_Detail_V.COLUMNNAME_M_Product_ID, -1);
			singleQuery.productId(ProductId.ofRepoIdOrNull(productRepoId));

			final int attributeRepoId = filter.getParameterValueAsInt(I_M_HU_Stock_Detail_V.COLUMNNAME_M_Attribute_ID, -1);
			singleQuery.attributeId(AttributeId.ofRepoIdOrNull(attributeRepoId));

			final String attributeValue = filter.getParameterValueAsString(I_M_HU_Stock_Detail_V.COLUMNNAME_AttributeValue, null);
			if (MaterialCockpitUtil.DONT_FILTER.equals(attributeValue))
			{
				singleQuery.attributeValue(AttributeValue.DONT_FILTER);
			}
			else
			{
				singleQuery.attributeValue(AttributeValue.NOT_EMPTY);
			}
			query.singleQuery(singleQuery.build());
		}

		final Stream<HUStockInfo> huStockInfos = huStockInfoRepository.getByQuery(query.build());

		return StockDetailsRowsData.of(huStockInfos);
	}

	@Override
	public ViewLayout getViewLayout(
			@NonNull final WindowId windowId,
			@NonNull final JSONViewDataType viewDataType,
			final ViewProfileId profileId)
	{
		return ViewLayout.builder()
				.setWindowId(windowId)
				// .caption(caption)
				.setFilters(ImmutableList.of(createEmptyFilterDescriptor()))
				.addElementsFromViewRowClass(StockDetailsRow.class, viewDataType)
				.build();
	}
}
