package de.metas.ui.web.view;

import java.util.concurrent.ConcurrentHashMap;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import de.metas.logging.LogManager;
import de.metas.ui.web.view.json.JSONDocumentViewCreateRequest;
import de.metas.ui.web.view.json.JSONDocumentViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.json.JSONOptions;

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

@Service
@Primary
public class CompositeDocumentViewSelectionFactory
{
	private static final transient Logger logger = LogManager.getLogger(CompositeDocumentViewSelectionFactory.class);

	private final ConcurrentHashMap<ArrayKey, IDocumentViewSelectionFactory> factories = new ConcurrentHashMap<>();
	@Autowired
	private SqlDocumentViewSelectionFactory defaultFactory;

	@Autowired
	public CompositeDocumentViewSelectionFactory(final ApplicationContext context)
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

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("factories", factories)
				.add("defaultFactory", defaultFactory)
				.toString();
	}

	public void registerFactory(final int windowId, final JSONViewDataType viewType, final IDocumentViewSelectionFactory factory)
	{
		Preconditions.checkNotNull(factory, "factory is null");
		factories.put(mkKey(windowId, viewType), factory);
		logger.info("Registered {} for windowId={}, viewType={}", factory, windowId, viewType);
	}

	private final IDocumentViewSelectionFactory getFactory(final int adWindowId, final JSONViewDataType viewType)
	{
		IDocumentViewSelectionFactory factory = factories.get(mkKey(adWindowId, viewType));
		if (factory != null)
		{
			return factory;
		}

		factory = factories.get(mkKey(adWindowId, null));
		if (factory != null)
		{
			return factory;
		}

		return defaultFactory;
	}

	private static final ArrayKey mkKey(final int adWindowId, final JSONViewDataType viewType)
	{
		return ArrayKey.of(adWindowId <= 0 ? 0 : adWindowId, viewType);
	}

	public JSONDocumentViewLayout getViewLayout(final int adWindowId, final JSONViewDataType viewDataType, final JSONOptions jsonOpts)
	{
		return getFactory(adWindowId, viewDataType).getViewLayout(adWindowId, viewDataType, jsonOpts);
	}

	public IDocumentViewSelection createView(final JSONDocumentViewCreateRequest jsonRequest)
	{
		final int adWindowId = jsonRequest.getAD_Window_ID();
		final JSONViewDataType viewType = jsonRequest.getViewType();
		final IDocumentViewSelectionFactory factory = getFactory(adWindowId, viewType);
		final IDocumentViewSelection view = factory.createView(jsonRequest);
		if (view == null)
		{
			throw new AdempiereException("Failed creating view")
					.setParameter("request", jsonRequest)
					.setParameter("factory", factory.toString());
		}
		return view;
	}
}
