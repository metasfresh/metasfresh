package de.metas.ui.web.material.cockpit;

import java.util.stream.Stream;

import org.compiere.model.I_M_Product;
import org.compiere.util.CacheMgt;
import org.springframework.stereotype.Service;

import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.ui.web.view.DefaultViewsRepositoryStorage;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsIndexStorage;
import de.metas.ui.web.view.ViewId;
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

/**
 * This {@link IViewsIndexStorage} implementation is dedicated to storing {@link MaterialCockpitView}.
 * The actual work is done by an internal instance of {@link DefaultViewsRepositoryStorage}.
 * We store {@link MaterialCockpitView}s inside this dedicated storage,
 * because we need to invalidate those views on changes in two different tables (the standard framework could handle only one).
 *
 */
@Service
public class MaterialCockpitViewsIndexStorage implements IViewsIndexStorage
{

	private final IViewsIndexStorage defaultViewsRepositoryStorage = new DefaultViewsRepositoryStorage();;

	public MaterialCockpitViewsIndexStorage()
	{
		CacheMgt.get().addCacheResetListener(I_MD_Cockpit.Table_Name, cacheInvalidateRequest -> {
			streamAllViews().forEach(IView::invalidateAll);
			return 0;
		});
		CacheMgt.get().addCacheResetListener(I_MD_Stock.Table_Name, cacheInvalidateRequest -> {
			streamAllViews().forEach(IView::invalidateAll);
			return 0;
		});
		CacheMgt.get().addCacheResetListener(I_M_Product.Table_Name, cacheInvalidateRequest -> {
			streamAllViews().forEach(IView::invalidateAll);
			return 0;
		});
	}

	/**
	 * @return {@link MaterialCockpitConstants#WINDOWID_MaterialCockpitView} since that'S what we want to store inside this service.
	 */
	@Override
	public WindowId getWindowId()
	{
		return MaterialCockpitConstants.WINDOWID_MaterialCockpitView;
	}

	@Override
	public void put(IView view)
	{
		defaultViewsRepositoryStorage.put(view);

	}

	@Override
	public IView getByIdOrNull(ViewId viewId)
	{
		return defaultViewsRepositoryStorage.getByIdOrNull(viewId);
	}

	@Override
	public void removeById(ViewId viewId)
	{
		defaultViewsRepositoryStorage.removeById(viewId);
	}

	@Override
	public Stream<IView> streamAllViews()
	{
		return defaultViewsRepositoryStorage.streamAllViews();
	}

	@Override
	public void invalidateView(ViewId viewId)
	{
		defaultViewsRepositoryStorage.invalidateView(viewId);
	}

}
