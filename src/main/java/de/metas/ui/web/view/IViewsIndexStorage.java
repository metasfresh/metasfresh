package de.metas.ui.web.view;

import java.util.stream.Stream;

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

/**
 * Implementations of this interface are responsible of storing {@link IView} references for a particular window ID identified by {@link #getWindowId()}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IViewsIndexStorage
{
	/** @return the window ID for whom this storage is storing the {@link IView} references. This method will be called by API on registration time. */
	WindowId getWindowId();

	/** Don't call it directly. Will be called by API. */
	default void setViewsRepository(IViewsRepository viewsRepository)
	{
	}

	/** Adds given view to the index. If the view already exists, it will be overridden. */
	void put(IView view);

	/** @return the {@link IView} identified by <code>viewId</code> or <code>null</code> if not found. */
	IView getByIdOrNull(ViewId viewId);

	/** Removes the view identified by given <code>viewId</code>. If the view does not exist, the method will do nothing, i.e. not failing. */
	void removeById(ViewId viewId);

	Stream<IView> streamAllViews();

	void invalidateView(ViewId viewId);

}
