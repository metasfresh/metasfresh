package de.metas.ui.web.order.sales.purchasePlanning.view;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.adempiere.util.Check;
import org.adempiere.util.Services;

import com.google.common.collect.ImmutableList;

import de.metas.i18n.ITranslatableString;
import de.metas.process.IADProcessDAO;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateRepository;
import de.metas.purchasecandidate.purchaseordercreation.localorder.PurchaseCandidateAggregate;
import de.metas.purchasecandidate.purchaseordercreation.localorder.PurchaseCandidateAggregator;
import de.metas.ui.web.order.sales.purchasePlanning.process.WEBUI_PurchaseCandidates_PurchaseView_Launcher;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.DefaultViewsRepositoryStorage;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.IViewsIndexStorage;
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

@ViewFactory(windowId = PurchaseCandidates2PurchaseViewFactory.WINDOW_ID_STRING)
public class PurchaseCandidates2PurchaseViewFactory implements IViewFactory, IViewsIndexStorage
{
	public static final String WINDOW_ID_STRING = "purchaseCandidates2po";
	public static final WindowId WINDOW_ID = WindowId.fromJson(WINDOW_ID_STRING);

	// services
	private final PurchaseCandidateRepository purchaseCandidateRepository;

	private final PurchaseViewLayoutFactory viewLayoutFactory;
	private final IViewsIndexStorage viewsIndexStorage = new DefaultViewsRepositoryStorage();

	public PurchaseCandidates2PurchaseViewFactory(final PurchaseCandidateRepository purchaseCandidateRepository)
	{
		this.purchaseCandidateRepository = purchaseCandidateRepository;

		final IADProcessDAO adProcessRepo = Services.get(IADProcessDAO.class);
		final ITranslatableString caption = adProcessRepo
				.retrieveProcessNameByClassIfUnique(WEBUI_PurchaseCandidates_PurchaseView_Launcher.class)
				.orElse(null);
		viewLayoutFactory = PurchaseViewLayoutFactory.builder()
				.caption(caption)
				.build();
	}

	@Override
	public ViewLayout getViewLayout(final WindowId windowId, final JSONViewDataType viewDataType, final ViewProfileId profileId)
	{
		return viewLayoutFactory.getViewLayout(windowId, viewDataType);
	}

	@Override
	public IView createView(final CreateViewRequest request)
	{
		final Set<Integer> purchaseCandidateIds = request.getFilterOnlyIds();
		Check.assumeNotEmpty(purchaseCandidateIds, "purchaseCandidateIds is not empty");

		final List<PurchaseCandidate> purchaseCandidates = purchaseCandidateRepository.getAllByIds(purchaseCandidateIds);

		final PurchaseCandidateAggregator aggregator = PurchaseCandidateAggregator.newInstance();
		aggregator.addAll(purchaseCandidates.iterator());
		aggregator.closeAllGroups();

		final List<PurchaseRow> rows = aggregator.getClosedGroups()
				.stream()
				.map(this::createRow)
				.collect(ImmutableList.toImmutableList());

		final PurchaseView view = PurchaseView.builder()
				.viewId(ViewId.random(WINDOW_ID))
				.rowsSupplier(() -> rows)
				.build();

		return view;

	}

	private PurchaseRow createRow(final PurchaseCandidateAggregate aggregate)
	{
		// TODO: implement
		return PurchaseRow.builder()
				.build();
	}

	@Override
	public WindowId getWindowId()
	{
		return WINDOW_ID;
	}

	@Override
	public void put(final IView view)
	{
		viewsIndexStorage.put(view);
	}

	@Override
	public IView getByIdOrNull(final ViewId viewId)
	{
		return viewsIndexStorage.getByIdOrNull(viewId);
	}

	@Override
	public void removeById(final ViewId viewId)
	{
		viewsIndexStorage.removeById(viewId);
	}

	@Override
	public Stream<IView> streamAllViews()
	{
		return viewsIndexStorage.streamAllViews();
	}

	@Override
	public void invalidateView(final ViewId viewId)
	{
		viewsIndexStorage.invalidateView(viewId);
	}
}
