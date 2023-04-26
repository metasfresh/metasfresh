package de.metas.ui.web.view;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalNotification;
import de.metas.logging.LogManager;
import de.metas.ui.web.view.event.ViewChangesCollector;
import de.metas.ui.web.window.datatypes.WindowId;
import lombok.NonNull;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

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

// NOTE: don't add it to spring context! i.e. don't annotate it with @Component or similar
public final class DefaultViewsRepositoryStorage implements IViewsIndexStorage
{
	private static transient final Logger logger = LogManager.getLogger(DefaultViewsRepositoryStorage.class);

	private final Cache<ViewId, IView> views;

	public DefaultViewsRepositoryStorage(@NonNull final Duration viewExpirationTimeout)
	{
		views = CacheBuilder.newBuilder()
				.expireAfterAccess(viewExpirationTimeout.toNanos(), TimeUnit.NANOSECONDS)
				.removalListener(this::onViewRemoved)
				.build();
	}

	@Override
	public WindowId getWindowId()
	{
		throw new UnsupportedOperationException("windowId not available");
	}

	@Override
	public void put(@NonNull final IView view)
	{
		views.put(view.getViewId(), view);
	}

	@Nullable
	@Override
	public IView getByIdOrNull(@NonNull final ViewId viewId)
	{
		return views.getIfPresent(viewId);
	}

	@Override
	public void closeById(@NonNull final ViewId viewId, @NonNull final ViewCloseAction closeAction)
	{
		// Don't remove the view if not allowed.
		// Will be removed when it will expire.
		final IView view = views.getIfPresent(viewId);
		if (view == null || !view.isAllowClosingPerUserRequest())
		{
			return;
		}

		//
		// Notify the view that the user requested to be closed
		// IMPORTANT: fire this event before removing the view from storage.
		view.close(closeAction);

		//
		// Remove the view from storage
		// => will fire #onViewRemoved
		views.invalidate(viewId);
		views.cleanUp(); // also cleanup to prevent views cache to grow.
	}

	private void onViewRemoved(final RemovalNotification<Object, Object> notification)
	{
		final IView view = (IView)notification.getValue();
		logger.debug("View `{}` removed from cache. Cause: {}", view.getViewId(), notification.getCause());
		view.afterDestroy();
	}

	@Override
	public void invalidateView(final ViewId viewId)
	{
		final IView view = getByIdOrNull(viewId);
		if (view == null)
		{
			return;
		}

		view.invalidateAll();

		ViewChangesCollector.getCurrentOrAutoflush()
				.collectFullyChanged(view);
	}

	@Override
	public Stream<IView> streamAllViews()
	{
		return views.asMap().values().stream();
	}

}
