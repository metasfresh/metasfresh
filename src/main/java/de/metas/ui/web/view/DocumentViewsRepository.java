package de.metas.ui.web.view;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalNotification;
import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.view.json.JSONDocumentViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
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
public class DocumentViewsRepository implements IDocumentViewsRepository
{
	private static final Logger logger = LogManager.getLogger(DocumentViewsRepository.class);

	private final ConcurrentHashMap<ArrayKey, IDocumentViewSelectionFactory> factories = new ConcurrentHashMap<>();
	@Autowired
	private SqlDocumentViewSelectionFactory defaultFactory;

	private final Cache<String, IDocumentViewSelection> views = CacheBuilder.newBuilder()
			.expireAfterAccess(1, TimeUnit.HOURS)
			.removalListener(notification -> onViewRemoved(notification))
			.build();

	@Autowired
	public DocumentViewsRepository(final ApplicationContext context)
	{
		//
		// Discover context factories
		for (final Object factoryObj : context.getBeansWithAnnotation(DocumentViewFactory.class).values())
		{
			final IDocumentViewSelectionFactory factory = (IDocumentViewSelectionFactory)factoryObj;
			final DocumentViewFactory annotation = factoryObj.getClass().getAnnotation(DocumentViewFactory.class);
			registerFactory(annotation.windowId(), annotation.viewType(), factory);
		}
	}

	public void registerFactory(final int windowId, final JSONViewDataType viewType, final IDocumentViewSelectionFactory factory)
	{
		Preconditions.checkNotNull(factory, "factory is null");
		factories.put(mkFactoryKey(windowId, viewType), factory);
		logger.info("Registered {} for windowId={}, viewType={}", factory, windowId, viewType);
	}

	private final IDocumentViewSelectionFactory getFactory(final int adWindowId, final JSONViewDataType viewType)
	{
		IDocumentViewSelectionFactory factory = factories.get(mkFactoryKey(adWindowId, viewType));
		if (factory != null)
		{
			return factory;
		}

		factory = factories.get(mkFactoryKey(adWindowId, null));
		if (factory != null)
		{
			return factory;
		}

		return defaultFactory;
	}

	private static final ArrayKey mkFactoryKey(final int adWindowId, final JSONViewDataType viewType)
	{
		return ArrayKey.of(adWindowId <= 0 ? 0 : adWindowId, viewType);
	}

	@Override
	public JSONDocumentViewLayout getViewLayout(final int adWindowId, final JSONViewDataType viewDataType, final JSONOptions jsonOpts)
	{
		return getFactory(adWindowId, viewDataType).getViewLayout(adWindowId, viewDataType, jsonOpts);
	}

	@Override
	public List<IDocumentViewSelection> getViews()
	{
		return ImmutableList.copyOf(views.asMap().values());
	}

	@Override
	public IDocumentViewSelection createView(final DocumentViewCreateRequest request)
	{
		final int adWindowId = request.getAD_Window_ID();
		final JSONViewDataType viewType = request.getViewType();
		final IDocumentViewSelectionFactory factory = getFactory(adWindowId, viewType);
		final IDocumentViewSelection view = factory.createView(request);
		if (view == null)
		{
			throw new AdempiereException("Failed creating view")
					.setParameter("request", request)
					.setParameter("factory", factory.toString());
		}

		views.put(view.getViewId(), view);

		return view;
	}

	@Override
	public boolean hasView(final String viewId)
	{
		Preconditions.checkNotNull(viewId, "viewId cannot be null");
		final IDocumentViewSelection view = views.getIfPresent(viewId);
		return view != null;
	}

	@Override
	public IDocumentViewSelection getViewIfExists(final String viewId)
	{
		Preconditions.checkNotNull(viewId, "viewId cannot be null");
		return views.getIfPresent(viewId);
	}

	@Override
	public IDocumentViewSelection getView(final String viewId)
	{
		Preconditions.checkNotNull(viewId, "viewId cannot be null");

		final IDocumentViewSelection view = views.getIfPresent(viewId);
		if (view == null)
		{
			throw new EntityNotFoundException("No view found for viewId=" + viewId);
		}
		return view;
	}

	@Override
	public void deleteView(final String viewId)
	{
		views.invalidate(viewId);
	}

	private final void onViewRemoved(final RemovalNotification<Object, Object> notification)
	{
		final IDocumentViewSelection view = (IDocumentViewSelection)notification.getValue();
		view.close();
	}

	@Override
	public void notifyRecordsChanged(final Set<TableRecordReference> recordRefs)
	{
		Check.assumeNotEmpty(recordRefs, "Parameter recordRefs is not empty");

		views.asMap().values().stream()
				.forEach(view -> view.notifyRecordsChanged(recordRefs));
	}
}
