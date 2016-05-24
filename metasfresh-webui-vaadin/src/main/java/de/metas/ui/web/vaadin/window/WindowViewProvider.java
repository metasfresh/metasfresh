package de.metas.ui.web.vaadin.window;

import java.util.concurrent.ExecutionException;

import com.google.common.base.Throwables;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewProvider;

/*
 * #%L
 * metasfresh-webui
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@SuppressWarnings("serial")
public class WindowViewProvider implements ViewProvider
{
	public static final String createViewNameAndParameters(final int windowId)
	{
		return PREFIX + "/" + windowId;
	}

	private static final String PREFIX = "/window";

	private final LoadingCache<String, WindowNavigationView> views = CacheBuilder.newBuilder()
			.maximumSize(10)
			.removalListener(new RemovalListener<String, WindowNavigationView>()
			{
				@Override
				public void onRemoval(final RemovalNotification<String, WindowNavigationView> notification)
				{
					final WindowNavigationView view = notification.getValue();
					view.dispose();
				}
			})
			.build(new CacheLoader<String, WindowNavigationView>()
			{
				@Override
				public WindowNavigationView load(final String viewNameAndParameters) throws Exception
				{
					return createWindowNavigationView(viewNameAndParameters);
				}
			});

	@Override
	public String getViewName(final String viewAndParameters)
	{
		if (viewAndParameters != null && viewAndParameters.startsWith(PREFIX))
		{
			return viewAndParameters;
		}
		return null;
	}

	@Override
	public View getView(final String viewNameAndParameters)
	{
		try
		{
			return views.get(viewNameAndParameters);
		}
		catch (final ExecutionException e)
		{
			throw Throwables.propagate(e);
		}
	}

	private final WindowNavigationView createWindowNavigationView(final String viewNameAndParameters)
	{
		final int windowId = extractWindowId(viewNameAndParameters);
		return new WindowNavigationView(windowId);
	}

	private final int extractWindowId(final String viewNameAndParameters)
	{
		final String[] pathInfoParts = viewNameAndParameters.split("/");
		final String windowIdStr = pathInfoParts[2];
		final int windowId = Integer.parseInt(windowIdStr);
		return windowId;

	}

}
