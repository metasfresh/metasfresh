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

package de.metas.ui.web.order.sales.purchasePlanning.view;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableList;

import de.metas.i18n.ITranslatableString;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.JavaProcess;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.process.RelatedProcessDescriptor.DisplayPlace;
import de.metas.purchasecandidate.PurchaseDemand;
import de.metas.purchasecandidate.PurchaseDemandWithCandidates;
import de.metas.purchasecandidate.PurchaseDemandWithCandidatesService;
import de.metas.purchasecandidate.availability.AvailabilityCheckService;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.IViewsIndexStorage;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewCloseAction;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.util.Services;
import lombok.NonNull;

public abstract class PurchaseViewFactoryTemplate implements IViewFactory, IViewsIndexStorage
{
	// services
	private final PurchaseDemandWithCandidatesService purchaseDemandWithCandidatesService;
	private final AvailabilityCheckService availabilityCheckService;
	private final PurchaseRowFactory purchaseRowFactory;
	private final PurchaseViewLayoutFactory viewLayoutFactory;
	private final IADProcessDAO adProcessRepo = Services.get(IADProcessDAO.class);

	// parameters
	private final WindowId windowId;

	//
	private final Cache<ViewId, PurchaseView> views = CacheBuilder.newBuilder()
			.expireAfterAccess(1, TimeUnit.HOURS)
			.build();

	public PurchaseViewFactoryTemplate(
			@NonNull final WindowId windowId,
			@NonNull Class<? extends JavaProcess> launcherProcessClass,
			// services:
			@NonNull final PurchaseDemandWithCandidatesService purchaseDemandWithCandidatesService,
			@NonNull final AvailabilityCheckService availabilityCheckService,
			@NonNull final PurchaseRowFactory purchaseRowFactory)
	{
		this.windowId = windowId;

		this.purchaseDemandWithCandidatesService = purchaseDemandWithCandidatesService;
		this.availabilityCheckService = availabilityCheckService;
		this.purchaseRowFactory = purchaseRowFactory;

		// caption
		final ITranslatableString caption = adProcessRepo
				.retrieveProcessNameByClassIfUnique(launcherProcessClass)
				.orElse(null);

		viewLayoutFactory = PurchaseViewLayoutFactory.builder()
				.caption(caption)
				.build();
	}

	@Override
	public void setViewsRepository(final IViewsRepository viewsRepository)
	{
	}

	protected abstract List<PurchaseDemand> getDemands(CreateViewRequest request);

	protected abstract void onViewClosedByUser(PurchaseView purchaseView);

	@Override
	public final WindowId getWindowId()
	{
		return windowId;
	}

	private final ViewId newViewId()
	{
		return ViewId.random(getWindowId());
	}

	@Override
	public final ViewLayout getViewLayout(
			@NonNull final WindowId windowId,
			@NonNull final JSONViewDataType viewDataType,
			@Nullable final ViewProfileId profileId)
	{
		return viewLayoutFactory.getViewLayout(windowId, viewDataType);
	}

	@Override
	public final void put(final IView view)
	{
		views.put(view.getViewId(), PurchaseView.cast(view));
	}

	@Nullable
	@Override
	public final PurchaseView getByIdOrNull(final ViewId viewId)
	{
		return views.getIfPresent(viewId);
	}

	public final PurchaseView getById(final ViewId viewId)
	{
		final PurchaseView view = getByIdOrNull(viewId);
		if (view == null)
		{
			throw new EntityNotFoundException("View " + viewId + " was not found");
		}
		return view;
	}

	@Override
	public final void closeById(@NonNull final ViewId viewId, @NonNull final ViewCloseAction closeAction)
	{
		final PurchaseView view = views.getIfPresent(viewId);
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
		final IView view = getByIdOrNull(viewId);
		if (view == null)
		{
			return;
		}

		view.invalidateAll();
	}

	@Override
	public final PurchaseView createView(@NonNull final CreateViewRequest request)
	{
		final ViewId viewId = newViewId();

		final List<PurchaseDemand> demands = getDemands(request);
		final List<PurchaseDemandWithCandidates> purchaseDemandWithCandidatesList = purchaseDemandWithCandidatesService.getOrCreatePurchaseCandidatesGroups(demands);

		final PurchaseRowsSupplier rowsSupplier = createRowsSupplier(viewId, purchaseDemandWithCandidatesList);

		final PurchaseView view = PurchaseView.builder()
				.viewId(viewId)
				.rowsSupplier(rowsSupplier)
				.additionalRelatedProcessDescriptors(getAdditionalProcessDescriptors())
				.build();

		return view;
	}

	protected List<RelatedProcessDescriptor> getAdditionalProcessDescriptors()
	{
		return ImmutableList.of();
	}

	private final PurchaseRowsSupplier createRowsSupplier(
			final ViewId viewId,
			final List<PurchaseDemandWithCandidates> purchaseDemandWithCandidatesList)
	{
		final PurchaseRowsSupplier rowsSupplier = PurchaseRowsLoader.builder()
				.purchaseDemandWithCandidatesList(purchaseDemandWithCandidatesList)
				.viewSupplier(() -> getByIdOrNull(viewId)) // needed for async stuff
				.purchaseRowFactory(purchaseRowFactory)
				.availabilityCheckService(availabilityCheckService)
				.build()
				.createPurchaseRowsSupplier();
		return rowsSupplier;
	}

	protected final RelatedProcessDescriptor createProcessDescriptor(@NonNull final Class<?> processClass)
	{
		final AdProcessId processId = adProcessRepo.retrieveProcessIdByClassIfUnique(processClass);
		Preconditions.checkArgument(processId != null, "No AD_Process_ID found for %s", processClass);

		return RelatedProcessDescriptor.builder()
				.processId(processId)
				.displayPlace(DisplayPlace.ViewQuickActions)
				.build();
	}
}
