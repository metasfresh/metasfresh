package de.metas.ui.web.pickingslotsClearing;

import java.util.Optional;
import java.util.stream.Stream;

import org.adempiere.util.Services;
import org.springframework.beans.factory.annotation.Autowired;

import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.process.IADProcessDAO;
import de.metas.process.JavaProcess;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.handlingunits.DefaultHUEditorViewFactory;
import de.metas.ui.web.handlingunits.HUEditorView;
import de.metas.ui.web.handlingunits.HUIdsFilterHelper;
import de.metas.ui.web.pickingslotsClearing.process.WEBUI_PackingHUsView_AddHUsToShipperTransportation;
import de.metas.ui.web.pickingslotsClearing.process.WEBUI_PackingHUsView_AddHUsToShipperTransportationShipAndInvoice;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.IViewsIndexStorage;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
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

@ViewFactory(windowId = PackingHUsViewFactory.WINDOW_ID_STRING)
public class PackingHUsViewFactory implements IViewFactory, IViewsIndexStorage
{
	static final String WINDOW_ID_STRING = "packingHUs";
	static final WindowId WINDOW_ID = WindowId.fromJson(WINDOW_ID_STRING);

	// services
	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
	@Autowired
	private DefaultHUEditorViewFactory huEditorViewFactory;
	private IViewsRepository viewsRepo;

	@Override
	public ViewLayout getViewLayout(final WindowId windowId, final JSONViewDataType viewDataType, final ViewProfileId profileId)
	{
		return huEditorViewFactory.getViewLayout(windowId, viewDataType, profileId);
	}

	@Override
	@Deprecated // shall not be called directly
	public HUEditorView createView(final CreateViewRequest request)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public WindowId getWindowId()
	{
		return WINDOW_ID;
	}

	@Override
	public void setViewsRepository(IViewsRepository viewsRepository)
	{
		this.viewsRepo = viewsRepository;
	}

	@Override
	public void put(final IView view)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public IView getByIdOrNull(final ViewId packingHUsViewId)
	{
		return getOrCreatePackingHUsView(packingHUsViewId);
	}

	@Override
	public void removeById(final ViewId packingHUsViewId)
	{
		final PickingSlotsClearingView pickingSlotsClearingView = getPickingSlotsClearingView(packingHUsViewId);
		pickingSlotsClearingView.closePackingHUsView(packingHUsViewId);
	}

	private PickingSlotsClearingView getPickingSlotsClearingView(final ViewId packingHUsViewId)
	{
		final ViewId pickingSlotsClearingViewId = PackingHUsViewKey.extractPickingSlotClearingViewId(packingHUsViewId);
		final PickingSlotsClearingView pickingSlotsClearingView = viewsRepo.getView(pickingSlotsClearingViewId, PickingSlotsClearingView.class);
		return pickingSlotsClearingView;
	}

	@Override
	public Stream<IView> streamAllViews()
	{
		return Stream.empty();
	}

	@Override
	public void invalidateView(final ViewId packingHUsViewId)
	{
		getPackingHUsViewIfExists(packingHUsViewId)
				.ifPresent(packingHUsView -> packingHUsView.invalidateAll());
	}

	private Optional<HUEditorView> getPackingHUsViewIfExists(final ViewId packingHUsViewId)
	{
		final PickingSlotsClearingView pickingSlotsClearingView = getPickingSlotsClearingView(packingHUsViewId);

		return pickingSlotsClearingView.getPackingHUsViewIfExists(packingHUsViewId);
	}

	private HUEditorView getOrCreatePackingHUsView(final ViewId packingHUsViewId)
	{
		final PickingSlotsClearingView pickingSlotsClearingView = getPickingSlotsClearingView(packingHUsViewId);

		return pickingSlotsClearingView.computePackingHUsViewIfAbsent(packingHUsViewId, this::createPackingHUsView);
	}

	private HUEditorView createPackingHUsView(final PackingHUsViewKey key)
	{
		final IHUQueryBuilder huQuery = createHUQuery(key);

		final ViewId packingHUsViewId = key.getPackingHUsViewId();
		final ViewId pickingSlotsClearingViewId = key.getPickingSlotsClearingViewId();
		final CreateViewRequest request = CreateViewRequest.builder(packingHUsViewId, JSONViewDataType.includedView)
				.setParentViewId(pickingSlotsClearingViewId)
				.addStickyFilters(HUIdsFilterHelper.createFilter(huQuery))
				.addAdditionalRelatedProcessDescriptor(createProcessDescriptor(WEBUI_PackingHUsView_AddHUsToShipperTransportation.class))
				.addAdditionalRelatedProcessDescriptor(createProcessDescriptor(WEBUI_PackingHUsView_AddHUsToShipperTransportationShipAndInvoice.class))
				.build();

		return huEditorViewFactory.createView(request);
	}

	private RelatedProcessDescriptor createProcessDescriptor(Class<? extends JavaProcess> processClass)
	{
		return RelatedProcessDescriptor.builder()
				.processId(adProcessDAO.retrieveProcessIdByClass(processClass))
				.anyTable().anyWindow()
				.webuiQuickAction(true)
				.build();
	}

	private IHUQueryBuilder createHUQuery(final PackingHUsViewKey key)
	{
		final IHUQueryBuilder huQuery = Services.get(IHandlingUnitsDAO.class)
				.createHUQueryBuilder()
				.setIncludeAfterPickingLocator(true)
				.setExcludeHUsOnPickingSlot(true)
				.onlyNotLocked() // not already locked (NOTE: those which were enqueued to Transportation Order are locked)
		;

		if (key.getBpartnerId() > 0)
		{
			huQuery.addOnlyInBPartnerId(key.getBpartnerId());
		}
		if (key.getBpartnerLocationId() > 0)
		{
			huQuery.addOnlyWithBPartnerLocationId(key.getBpartnerLocationId());
		}
		return huQuery;
	}
}
