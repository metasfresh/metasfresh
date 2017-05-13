package de.metas.ui.web.view;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalNotification;
import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.menu.MenuTreeRepository;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONFilterViewRequest;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.view.json.JSONViewLayout;
import de.metas.ui.web.window.controller.DocumentPermissionsHelper;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.JSONOptions;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

@Repository
public class ViewsRepository implements IViewsRepository
{
	private static final Logger logger = LogManager.getLogger(ViewsRepository.class);

	private final ConcurrentHashMap<ArrayKey, IViewFactory> factories = new ConcurrentHashMap<>();
	@Autowired
	private SqlViewFactory defaultFactory;

	@Autowired
	private MenuTreeRepository menuTreeRepo;

	private final Cache<String, IView> views = CacheBuilder.newBuilder()
			.expireAfterAccess(1, TimeUnit.HOURS)
			.removalListener(notification -> onViewRemoved(notification))
			.build();

	@Autowired
	public ViewsRepository(final ApplicationContext context)
	{
		//
		// Discover context factories
		for (final Object factoryObj : context.getBeansWithAnnotation(ViewFactory.class).values())
		{
			final IViewFactory factory = (IViewFactory)factoryObj;
			final ViewFactory annotation = factoryObj.getClass().getAnnotation(ViewFactory.class);
			final WindowId windowId = WindowId.fromJson(annotation.windowId());

			JSONViewDataType[] viewTypes = annotation.viewTypes();
			if (viewTypes.length == 0)
			{
				viewTypes = JSONViewDataType.values();
			}

			for (final JSONViewDataType viewType : viewTypes)
			{
				factories.put(mkFactoryKey(windowId, viewType), factory);
				logger.info("Registered {} for windowId={}, viewType={}", factory, windowId, viewTypes);
			}
		}
	}

	private final IViewFactory getFactory(final WindowId windowId, final JSONViewDataType viewType)
	{
		IViewFactory factory = factories.get(mkFactoryKey(windowId, viewType));
		if (factory != null)
		{
			return factory;
		}

		factory = factories.get(mkFactoryKey(windowId, null));
		if (factory != null)
		{
			return factory;
		}

		return defaultFactory;
	}

	private static final ArrayKey mkFactoryKey(final WindowId windowId, final JSONViewDataType viewType)
	{
		return ArrayKey.of(windowId, viewType);
	}

	@Override
	public JSONViewLayout getViewLayout(final WindowId windowId, final JSONViewDataType viewDataType, final JSONOptions jsonOpts)
	{
		DocumentPermissionsHelper.assertWindowAccess(windowId, null, UserSession.getCurrentPermissions());

		final IViewFactory factory = getFactory(windowId, viewDataType);
		final ViewLayout viewLayout = factory.getViewLayout(windowId, viewDataType);
		final Collection<DocumentFilterDescriptor> viewFilterDescriptors = factory.getViewFilterDescriptors(windowId, viewDataType);

		final JSONViewLayout jsonLayout = JSONViewLayout.of(viewLayout, viewFilterDescriptors, jsonOpts);
		//
		// Enable new record if supported
		menuTreeRepo.getUserSessionMenuTree()
				.getNewRecordNodeForWindowId(viewLayout.getWindowId())
				.ifPresent(newRecordMenuNode -> jsonLayout.enableNewRecord(newRecordMenuNode.getCaption()));

		return jsonLayout;
	}

	@Override
	public List<IView> getViews()
	{
		return ImmutableList.copyOf(views.asMap().values());
	}

	@Override
	public IView createView(final CreateViewRequest request)
	{
		final WindowId windowId = request.getWindowId();
		final JSONViewDataType viewType = request.getViewType();
		final IViewFactory factory = getFactory(windowId, viewType);
		final IView view = factory.createView(request);
		if (view == null)
		{
			throw new AdempiereException("Failed creating view")
					.setParameter("request", request)
					.setParameter("factory", factory.toString());
		}

		views.put(view.getViewId().getViewId(), view);

		return view;
	}

	@Override
	public IView filterView(final ViewId viewId, final JSONFilterViewRequest jsonRequest)
	{
		// Get current view
		final IView view = getView(viewId);

		//
		// Create the new view
		final IViewFactory factory = getFactory(view.getViewId().getWindowId(), view.getViewType());
		final IView newView = factory.filterView(view, jsonRequest);
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
		if(view != newView)
		{
			final ViewId newViewId = newView.getViewId();
			views.put(newViewId.getViewId(), newView);
		}

		// Return the newly created view
		return newView;
	}

	@Override
	public IView getViewIfExists(final ViewId viewId)
	{
		Preconditions.checkNotNull(viewId, "viewId cannot be null");
		return views.getIfPresent(viewId.getViewId());
	}

	@Override
	public IView getView(final String viewId)
	{
		Preconditions.checkNotNull(viewId, "viewId cannot be null");

		final IView view = views.getIfPresent(viewId);
		if (view == null)
		{
			throw new EntityNotFoundException("No view found for viewId=" + viewId);
		}

		DocumentPermissionsHelper.assertWindowAccess(view.getViewId().getWindowId(), viewId, UserSession.getCurrentPermissions());

		return view;
	}

	@Override
	public void deleteView(final ViewId viewId)
	{
		Preconditions.checkNotNull(viewId, "viewId cannot be null");
		views.invalidate(viewId.getViewId());
	}

	private final void onViewRemoved(final RemovalNotification<Object, Object> notification)
	{
		final IView view = (IView)notification.getValue();
		view.close();
	}

	@Override
	@Async
	public void notifyRecordsChanged(final Set<TableRecordReference> recordRefs)
	{
		if (recordRefs.isEmpty())
		{
			return;
		}

		final Collection<IView> views = this.views.asMap().values();

		if (logger.isDebugEnabled())
		{
			logger.debug("Notifing {} views about changed records: {}", views.size(), recordRefs);
		}

		views.forEach(view -> view.notifyRecordsChanged(recordRefs));
	}
}
