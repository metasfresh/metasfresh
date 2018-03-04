package de.metas.ui.web.view;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalNotification;

import de.metas.ui.web.window.datatypes.WindowId;
import lombok.NonNull;

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
	private final Cache<ViewId, IView> views = CacheBuilder.newBuilder()
			.expireAfterAccess(1, TimeUnit.HOURS)
			.removalListener(notification -> onViewRemoved(notification))
			.build();

	@Override
	public WindowId getWindowId()
	{
		throw new UnsupportedOperationException("windowId not available");
	}

	private final void onViewRemoved(final RemovalNotification<Object, Object> notification)
	{
		final IView view = (IView)notification.getValue();
		final ViewCloseReason closeReason = ViewCloseReason.fromCacheEvictedFlag(notification.wasEvicted());
		view.close(closeReason);
	}

	@Override
	public void put(@NonNull final IView view)
	{
		views.put(view.getViewId(), view);
	}

	@Override
	public IView getByIdOrNull(@NonNull final ViewId viewId)
	{
		return views.getIfPresent(viewId);
	}

	@Override
	public void removeById(@NonNull final ViewId viewId)
	{
		views.invalidate(viewId);
		views.cleanUp(); // also cleanup to prevent views cache to grow.
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
	}

	@Override
	public Stream<IView> streamAllViews()
	{
		return views.asMap().values().stream();
	}

}
