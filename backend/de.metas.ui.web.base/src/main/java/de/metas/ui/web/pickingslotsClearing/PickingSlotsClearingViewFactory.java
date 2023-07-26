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

package de.metas.ui.web.pickingslotsClearing;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.cache.CCache;
import de.metas.picking.api.PickingSlotQuery;
import de.metas.picking.api.PickingSlotQuery.PickingSlotQueryBuilder;
import de.metas.process.IADProcessDAO;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.process.RelatedProcessDescriptor.DisplayPlace;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.picking.pickingslot.PickingSlotRow;
import de.metas.ui.web.picking.pickingslot.PickingSlotViewRepository;
import de.metas.ui.web.pickingslotsClearing.process.WEBUI_PickingSlotsClearingView_TakeOutCUsAndAddToTU;
import de.metas.ui.web.pickingslotsClearing.process.WEBUI_PickingSlotsClearingView_TakeOutHU;
import de.metas.ui.web.pickingslotsClearing.process.WEBUI_PickingSlotsClearingView_TakeOutHUAndAddToNewHU;
import de.metas.ui.web.pickingslotsClearing.process.WEBUI_PickingSlotsClearingView_TakeOutMultiHUsAndAddToNewHU;
import de.metas.ui.web.pickingslotsClearing.process.WEBUI_PickingSlotsClearingView_TakeOutTUAndAddToLU;
import de.metas.ui.web.pickingslotsClearing.process.WEBUI_PickingSlotsClearingView_TakeOutTUsAndAddToNewLUs;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.descriptor.IncludedViewLayout;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Browse Picking slots
 *
 * @author metas-dev <dev@metasfresh.com>
 * @task https://github.com/metasfresh/metasfresh/issues/518
 */
@ViewFactory(windowId = PickingSlotsClearingViewFactory.WINDOW_ID_STRING)
public class PickingSlotsClearingViewFactory implements IViewFactory
{
	static final String WINDOW_ID_STRING = "540371"; // Picking Tray Clearing
	public static final WindowId WINDOW_ID = WindowId.fromJson(WINDOW_ID_STRING);

	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
	@Autowired
	private PickingSlotViewRepository pickingSlotRepo;

	private final CCache<Integer, DocumentFilterDescriptorsProvider> filterDescriptorsProviderCache = CCache.newCache("PickingSlotsClearingViewFactory.FilterDescriptorsProvider", 1, CCache.EXPIREMINUTES_Never);

	@Override
	public ViewLayout getViewLayout(final WindowId windowId, final JSONViewDataType viewDataType, @Nullable final ViewProfileId profileId)
	{
		// TODO: cache it

		return ViewLayout.builder()
				.setWindowId(WINDOW_ID)
				.setCaption(Services.get(IADWindowDAO.class).retrieveWindowName(WINDOW_ID.toAdWindowId()))
				.addElementsFromViewRowClass(PickingSlotRow.class, viewDataType)
				.setHasTreeSupport(true)
				.setFilters(getFilterDescriptorsProvider().getAll())
				.setIncludedViewLayout(IncludedViewLayout.builder()
						.openOnSelect(true)
						.blurWhenOpen(false)
						.build())
				.build();
	}

	private DocumentFilterDescriptorsProvider getFilterDescriptorsProvider()
	{
		return filterDescriptorsProviderCache.getOrLoad(0, () -> PickingSlotsClearingViewFilters.createFilterDescriptorsProvider());
	}

	@Override
	public PickingSlotsClearingView createView(final @NonNull CreateViewRequest request)
	{
		request.assertNoParentViewOrRow();

		final DocumentFilterDescriptorsProvider filterDescriptors = getFilterDescriptorsProvider();
		final DocumentFilterList filters = request.getFiltersUnwrapped(filterDescriptors);

		final ViewId viewId = ViewId.random(PickingSlotsClearingViewFactory.WINDOW_ID);

		final PickingSlotQuery query = createPickingSlotQuery(filters);

		return PickingSlotsClearingView.builder()
				.viewId(viewId)
				.rows(() -> pickingSlotRepo.retrievePickingSlotsRows(query))
				.additionalRelatedProcessDescriptors(createAdditionalRelatedProcessDescriptors())
				.filterDescriptors(filterDescriptors)
				.filters(filters)
				.build();
	}

	private static final PickingSlotQuery createPickingSlotQuery(@NonNull final DocumentFilterList filters)
	{
		final PickingSlotQueryBuilder queryBuilder = PickingSlotQuery.builder();

		final BPartnerId bpartnerId = PickingSlotsClearingViewFilters.getBPartnerId(filters);
		if (bpartnerId != null)
		{
			queryBuilder.assignedToBPartnerId(bpartnerId);
		}

		final String barcode = PickingSlotsClearingViewFilters.getPickingSlotBarcode(filters);
		if (!Check.isEmpty(barcode, true))
		{
			queryBuilder.barcode(barcode);
		}

		return queryBuilder.build();
	}

	private List<RelatedProcessDescriptor> createAdditionalRelatedProcessDescriptors()
	{
		// TODO: cache it

		return ImmutableList.of(
				createProcessDescriptor(WEBUI_PickingSlotsClearingView_TakeOutHU.class),
				createProcessDescriptor(WEBUI_PickingSlotsClearingView_TakeOutCUsAndAddToTU.class),
				createProcessDescriptor(WEBUI_PickingSlotsClearingView_TakeOutTUAndAddToLU.class),
				createProcessDescriptor(WEBUI_PickingSlotsClearingView_TakeOutHUAndAddToNewHU.class),
				createProcessDescriptor(WEBUI_PickingSlotsClearingView_TakeOutMultiHUsAndAddToNewHU.class),
				createProcessDescriptor(de.metas.ui.web.process.adprocess.WEBUI_TestParentChildViewParams.class),
				createProcessDescriptor(WEBUI_PickingSlotsClearingView_TakeOutTUsAndAddToNewLUs.class));
	}

	private RelatedProcessDescriptor createProcessDescriptor(final Class<?> processClass)
	{
		return RelatedProcessDescriptor.builder()
				.processId(adProcessDAO.retrieveProcessIdByClass(processClass))
				.anyTable().anyWindow()
				.displayPlace(DisplayPlace.ViewQuickActions)
				.build();
	}

}
