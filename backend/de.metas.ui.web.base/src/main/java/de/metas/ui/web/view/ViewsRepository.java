/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.ui.web.view;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Streams;
import de.metas.elasticsearch.model.I_T_ES_FTS_Search_Result;
import de.metas.logging.LogManager;
import de.metas.security.IUserRolePermissions;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.UserRolePermissionsKey;
import de.metas.ui.web.base.model.I_T_WEBUI_ViewSelection;
import de.metas.ui.web.base.model.I_T_WEBUI_ViewSelectionLine;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.menu.MenuNode;
import de.metas.ui.web.menu.MenuTreeRepository;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.event.ViewChangesCollector;
import de.metas.ui.web.view.json.JSONFilterViewRequest;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.websocket.WebsocketTopicNames;
import de.metas.ui.web.window.controller.DocumentPermissionsHelper;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.websocket.WebsocketTopicName;
import de.metas.websocket.producers.WebsocketActiveSubscriptionsIndex;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.MutableInt;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.compiere.util.DB;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

@Service
public class ViewsRepository implements IViewsRepository
{
	private static final Logger logger = LogManager.getLogger(ViewsRepository.class);
	private final IUserRolePermissionsDAO userRolePermissionsDAO = Services.get(IUserRolePermissionsDAO.class);

	private static final String SYSCONFIG_ViewExpirationTimeoutInMinutes = "de.metas.ui.web.view.ViewExpirationTimeoutInMinutes";

	private final ImmutableMap<ViewFactoryKey, IViewFactory> factories;
	private final SqlViewFactory defaultFactory;
	private final MenuTreeRepository menuTreeRepo;
	private final WebsocketActiveSubscriptionsIndex websocketActiveSubscriptionsIndex;

	@Value("${metasfresh.webui.view.truncateOnStartUp:true}")
	private boolean truncateSelectionOnStartUp;

	private final ImmutableMap<WindowId, IViewsIndexStorage> viewsIndexStorages;
	private final IViewsIndexStorage defaultViewsIndexStorage;

	private final Executor async;

	public ViewsRepository(
			@NonNull final List<IViewFactory> viewFactories,
			@SuppressWarnings("OptionalUsedAsFieldOrParameterType") @NonNull final Optional<List<IViewsIndexStorage>> viewIndexStorages,
			@NonNull final SqlViewFactory defaultFactory,
			@NonNull final MenuTreeRepository menuTreeRepo,
			@NonNull final WebsocketActiveSubscriptionsIndex websocketActiveSubscriptionsIndex)
	{
		factories = createFactoriesMap(viewFactories);
		factories.values().forEach(viewFactory -> viewFactory.setViewsRepository(this));
		logger.info("Registered following view factories: {}", factories);

		this.viewsIndexStorages = createViewIndexStoragesMap(viewIndexStorages.orElseGet(ImmutableList::of));
		this.viewsIndexStorages.values().forEach(viewsIndexStorage -> viewsIndexStorage.setViewsRepository(this));
		logger.info("Registered following view index storages: {}", this.viewsIndexStorages);
		this.defaultFactory = defaultFactory;
		this.menuTreeRepo = menuTreeRepo;
		this.websocketActiveSubscriptionsIndex = websocketActiveSubscriptionsIndex;

		final Duration viewExpirationTimeout = Duration.ofMinutes(Services.get(ISysConfigBL.class).getIntValue(SYSCONFIG_ViewExpirationTimeoutInMinutes, 60));
		logger.info("viewExpirationTimeout: {} (see `{}` sysconfig)", viewExpirationTimeout, SYSCONFIG_ViewExpirationTimeoutInMinutes);
		defaultViewsIndexStorage = new DefaultViewsRepositoryStorage(viewExpirationTimeout);

		async = createAsyncExecutor();
	}

	private static Executor createAsyncExecutor()
	{
		final CustomizableThreadFactory asyncThreadFactory = new CustomizableThreadFactory(ViewsRepository.class.getSimpleName());
		asyncThreadFactory.setDaemon(true);
		return Executors.newSingleThreadExecutor(asyncThreadFactory);
	}

	@PostConstruct
	private void truncateTempTablesIfAllowed()
	{
		if (truncateSelectionOnStartUp)
		{
			truncateTable(I_T_WEBUI_ViewSelection.Table_Name);
			truncateTable(I_T_WEBUI_ViewSelectionLine.Table_Name);
			truncateTable(I_T_ES_FTS_Search_Result.Table_Name);
		}
		else
		{
			logger.info("Skip truncating selection tables on startup because not configured");
		}
	}

	private static void truncateTable(final String tableName)
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();
		try
		{
			final int no = DB.executeUpdateAndThrowExceptionOnFail("TRUNCATE TABLE " + tableName, ITrx.TRXNAME_NoneNotNull);
			logger.info("Deleted {} records(all) from table {} (Took: {})", no, tableName, stopwatch);
		}
		catch (final Exception ex)
		{
			logger.warn("Failed deleting all from {} (Took: {})", tableName, stopwatch, ex);
		}
	}

	private static ImmutableMap<ViewFactoryKey, IViewFactory> createFactoriesMap(final Collection<IViewFactory> viewFactories)
	{
		final Map<ViewFactoryKey, IViewFactory> factories = new HashMap<>();
		for (final IViewFactory factory : viewFactories)
		{
			final ViewFactory annotation = factory.getClass().getAnnotation(ViewFactory.class);
			if (annotation == null)
			{
				// Don't need to warn about this one. It's a known case.
				if (!SqlViewFactory.class.equals(factory.getClass()))
				{
					// this might be a development bug
					logger.warn("Skip {} because it's not annotated with {}", factory, ViewFactory.class);
				}
				continue;
			}

			final WindowId windowId = WindowId.fromJson(annotation.windowId());
			factory.setWindowId(windowId);

			JSONViewDataType[] viewTypes = annotation.viewTypes();
			if (viewTypes.length == 0)
			{
				viewTypes = JSONViewDataType.values();
			}

			for (final JSONViewDataType viewType : viewTypes)
			{
				factories.put(ViewFactoryKey.of(windowId, viewType), factory);
			}
		}

		return ImmutableMap.copyOf(factories);
	}

	private static ImmutableMap<WindowId, IViewsIndexStorage> createViewIndexStoragesMap(final List<IViewsIndexStorage> viewsIndexStorages)
	{
		final ImmutableMap.Builder<WindowId, IViewsIndexStorage> map = ImmutableMap.builder();

		for (final IViewsIndexStorage viewsIndexStorage : viewsIndexStorages)
		{
			if (viewsIndexStorage instanceof DefaultViewsRepositoryStorage)
			{
				logger.warn("Skipping {} because it shall not be in spring context", viewsIndexStorage);
				continue;
			}

			final WindowId windowId = viewsIndexStorage.getWindowId();
			Check.assumeNotNull(windowId, "{} shall not have windowId null", viewsIndexStorage);

			map.put(windowId, viewsIndexStorage);
		}

		return map.build();
	}

	@Override
	public boolean isWatchedByFrontend(final ViewId viewId)
	{
		final WebsocketTopicName topicName = WebsocketTopicNames.buildViewNotificationsTopicName(viewId.toJson());
		return websocketActiveSubscriptionsIndex.hasSubscriptionsForTopicName(topicName);
	}

	private IViewFactory getFactory(final WindowId windowId, final JSONViewDataType viewType)
	{
		IViewFactory factory = factories.get(ViewFactoryKey.of(windowId, viewType));
		if (factory != null)
		{
			return factory;
		}

		factory = factories.get(ViewFactoryKey.of(windowId, null));
		if (factory != null)
		{
			return factory;
		}

		return defaultFactory;
	}

	@Override
	public IViewsIndexStorage getViewsStorageFor(@NonNull final ViewId viewId)
	{
		final IViewsIndexStorage viewIndexStorage = viewsIndexStorages.get(viewId.getWindowId());
		if (viewIndexStorage != null)
		{
			return viewIndexStorage;
		}

		return defaultViewsIndexStorage;
	}

	private Stream<IView> streamAllViews()
	{
		return Streams.concat(viewsIndexStorages.values().stream(), Stream.of(defaultViewsIndexStorage))
				.flatMap(IViewsIndexStorage::streamAllViews);
	}

	@Override
	public List<ViewProfile> getAvailableProfiles(final WindowId windowId, final JSONViewDataType viewDataType)
	{
		final IViewFactory factory = getFactory(windowId, viewDataType);
		return factory.getAvailableProfiles(windowId);
	}

	@Override
	public ViewLayout getViewLayout(
			@NonNull final WindowId windowId,
			@NonNull final JSONViewDataType viewDataType,
			@Nullable final ViewProfileId profileId,
			@Nullable final UserRolePermissionsKey permissionsKey)
	{
		if (permissionsKey != null)
		{
			final IUserRolePermissions permissions = userRolePermissionsDAO.getUserRolePermissions(permissionsKey);
			DocumentPermissionsHelper.assertViewAccess(windowId, null, permissions);
		}

		final IViewFactory factory = getFactory(windowId, viewDataType);
		ViewLayout viewLayout = factory.getViewLayout(windowId, viewDataType, profileId);

		// Enable AllowNew if we have a menu node to create new records.
		if (permissionsKey != null)
		{
			viewLayout = viewLayout.withAllowNewRecordIfPresent(menuTreeRepo.getMenuTree(permissionsKey)
					.getNewRecordNodeForWindowId(windowId)
					.map(MenuNode::getCaption));
		}

		return viewLayout;
	}

	@Override
	public List<IView> getViews()
	{
		return streamAllViews().collect(ImmutableList.toImmutableList());
	}

	@Override
	public IView createView(@NonNull final CreateViewRequest request)
	{
		logger.trace("Creating new view from {}", request);

		final WindowId windowId = request.getViewId().getWindowId();
		final JSONViewDataType viewType = request.getViewType();
		final IViewFactory factory = getFactory(windowId, viewType);
		final IView view = factory.createView(request);
		if (view == null)
		{
			throw new AdempiereException("Failed creating view")
					.setParameter("request", request)
					.setParameter("factory", factory.toString());
		}

		getViewsStorageFor(view.getViewId()).put(view);
		logger.trace("Created view {}", view);

		return view;
	}

	@Override
	public IView filterView(final ViewId viewId, final JSONFilterViewRequest jsonRequest)
	{
		logger.trace("Creating filtered view from {} using {}", viewId, jsonRequest);

		// Get current view
		final IView view = getView(viewId);

		//
		// Create the new view
		final IViewFactory factory = getFactory(view.getViewId().getWindowId(), view.getViewType());
		final IView newView = factory.filterView(view, jsonRequest, () -> this);
		if (newView == null)
		{
			throw new AdempiereException("Failed filtering view")
					.setParameter("viewId", viewId)
					.setParameter("request", jsonRequest)
					.setParameter("factory", factory.toString());
		}

		//
		// Add the new view to our internal map
		// NOTE: avoid adding if the factory returned the same view.
		if (view != newView)
		{
			getViewsStorageFor(newView.getViewId()).put(newView);
			logger.trace("Created filtered view {}", newView);
		}
		else
		{
			logger.trace("Filtered view is the same as the ordiginal. Returning the original {}", view);
		}

		// Return the newly created view
		return newView;
	}

	@Override
	public IView deleteStickyFilter(final ViewId viewId, final String filterId)
	{
		logger.trace("Deleting sticky filter {} from {}", filterId, viewId);

		// Get current view
		final IView view = getView(viewId);

		//
		// Create the new view
		final IViewFactory factory = getFactory(view.getViewId().getWindowId(), view.getViewType());
		final IView newView = factory.deleteStickyFilter(view, filterId);
		if (newView == null)
		{
			throw new AdempiereException("Failed deleting sticky/static filter")
					.setParameter("viewId", viewId)
					.setParameter("filterId", filterId)
					.setParameter("factory", factory.toString());
		}

		//
		// Add the new view to our internal map
		// NOTE: avoid adding if the factory returned the same view.
		if (view != newView)
		{
			getViewsStorageFor(newView.getViewId()).put(newView);
			logger.trace("Sticky filter deleted. Returning new view {}", newView);
		}
		else
		{
			logger.trace("Sticky filter NOT deleted. Returning current view {}", view);
		}

		// Return the newly created view
		return newView;
	}

	@Override
	public IView getView(@NonNull final String viewIdStr)
	{
		final ViewId viewId = ViewId.ofViewIdString(viewIdStr);
		final IView view = getViewIfExists(viewId);
		if (view == null)
		{
			throw new EntityNotFoundException("No view found for viewId=" + viewId);
		}
		return view;
	}

	@Override
	public IView getViewIfExists(final ViewId viewId)
	{
		final IView view = getViewsStorageFor(viewId).getByIdOrNull(viewId);
		if (view == null)
		{
			throw new EntityNotFoundException("View not found: " + viewId.toJson());
		}

		DocumentPermissionsHelper.assertViewAccess(viewId.getWindowId(), viewId.getViewId(), UserSession.getCurrentPermissions());

		return view;
	}

	@Override
	public void closeView(@NonNull final ViewId viewId, @NonNull final ViewCloseAction closeAction)
	{
		getViewsStorageFor(viewId).closeById(viewId, closeAction);
		logger.trace("Closed/Removed view {} using close action {}", viewId, closeAction);
	}

	@Override
	public void invalidateView(final ViewId viewId)
	{
		getViewsStorageFor(viewId).invalidateView(viewId);
		logger.trace("Invalided view {}", viewId);
	}

	@Override
	public void invalidateView(final IView view)
	{
		invalidateView(view.getViewId());
	}

	@Override
	public void notifyRecordsChangedAsync(@NonNull final TableRecordReferenceSet recordRefs)
	{
		if (recordRefs.isEmpty())
		{
			logger.trace("No changed records provided. Skip notifying views.");
			return;
		}

		async.execute(() -> notifyRecordsChangedNow(recordRefs));
	}

	@Override
	public void notifyRecordsChangedNow(@NonNull final TableRecordReferenceSet recordRefs)
	{
		if (recordRefs.isEmpty())
		{
			logger.trace("No changed records provided. Skip notifying views.");
			return;
		}

		try (final IAutoCloseable ignored = ViewChangesCollector.currentOrNewThreadLocalCollector())
		{
			for (final IViewsIndexStorage viewsIndexStorage : viewsIndexStorages.values())
			{
				notifyRecordsChangedNow(recordRefs, viewsIndexStorage);
			}

			notifyRecordsChangedNow(recordRefs, defaultViewsIndexStorage);
		}
	}

	private void notifyRecordsChangedNow(
			@NonNull final TableRecordReferenceSet recordRefs,
			@NonNull final IViewsIndexStorage viewsIndexStorage)
	{
		final ImmutableList<IView> views = viewsIndexStorage.getAllViews();
		if (views.isEmpty())
		{
			return;
		}

		final MutableInt notifiedCount = MutableInt.zero();
		for (final IView view : views)
		{
			try
			{
				final boolean watchedByFrontend = isWatchedByFrontend(view.getViewId());
				view.notifyRecordsChanged(recordRefs, watchedByFrontend);
				notifiedCount.incrementAndGet();
			}
			catch (final Exception ex)
			{
				logger.warn("Failed calling notifyRecordsChanged on view={} with recordRefs={}. Ignored.", view, recordRefs, ex);
			}
		}

		logger.debug("Notified {} views in {} about changed records: {}", notifiedCount, viewsIndexStorage, recordRefs);
	}

	@lombok.Value(staticConstructor = "of")
	private static class ViewFactoryKey
	{
		WindowId windowId;
		JSONViewDataType viewType;
	}

}
