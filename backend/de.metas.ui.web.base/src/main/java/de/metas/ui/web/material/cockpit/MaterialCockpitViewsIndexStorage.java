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

package de.metas.ui.web.material.cockpit;

import de.metas.cache.CacheMgt;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.ui.web.view.DefaultViewsRepositoryStorage;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsIndexStorage;
import de.metas.ui.web.view.ViewCloseAction;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.WindowId;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.compiere.model.I_M_Product;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.stream.Stream;

/**
 * This {@link IViewsIndexStorage} implementation is dedicated to storing {@link MaterialCockpitView}.
 * The actual work is done by an internal instance of {@link DefaultViewsRepositoryStorage}.
 * We store {@link MaterialCockpitView}s inside this dedicated storage,
 * because we need to invalidate those views on changes in two different tables (the standard framework could handle only one).
 */
@Service
public class MaterialCockpitViewsIndexStorage implements IViewsIndexStorage
{
	private final DefaultViewsRepositoryStorage defaultViewsRepositoryStorage = new DefaultViewsRepositoryStorage(Duration.ofHours(1));

	public MaterialCockpitViewsIndexStorage()
	{
		CacheMgt.get().addCacheResetListener(I_MD_Cockpit.Table_Name, cacheInvalidateRequest -> {
			notifyViewOfCacheReset(cacheInvalidateRequest);
			return 0;
		});
		CacheMgt.get().addCacheResetListener(I_MD_Stock.Table_Name, cacheInvalidateRequest -> {
			notifyViewOfCacheReset(cacheInvalidateRequest);
			return 0;
		});
		CacheMgt.get().addCacheResetListener(I_M_Product.Table_Name, cacheInvalidateRequest -> {
			notifyViewOfCacheReset(cacheInvalidateRequest);
			return 0;
		});
	}

	private void notifyViewOfCacheReset(@NonNull final CacheInvalidateMultiRequest cacheInvalidateRequest)
	{
		for (final IView view : getAllViews())
		{
			if (cacheInvalidateRequest.isResetAll())
			{
				view.invalidateAll();
			}
			else
			{
				final TableRecordReferenceSet recordsEffective = cacheInvalidateRequest.getRecordsEffective();
				view.notifyRecordsChanged(recordsEffective, true);
			}
		}
	}

	/**
	 * @return {@link MaterialCockpitUtil#WINDOWID_MaterialCockpitView} since that'S what we want to store inside this service.
	 */
	@Override
	public WindowId getWindowId()
	{
		return MaterialCockpitUtil.WINDOWID_MaterialCockpitView;
	}

	@Override
	public void put(final IView view)
	{
		defaultViewsRepositoryStorage.put(view);
	}

	@Nullable
	@Override
	public IView getByIdOrNull(final ViewId viewId)
	{
		return defaultViewsRepositoryStorage.getByIdOrNull(viewId);
	}

	@Override
	public void closeById(@NonNull final ViewId viewId, @NonNull final ViewCloseAction closeAction)
	{
		defaultViewsRepositoryStorage.closeById(viewId, closeAction);
	}

	@Override
	public Stream<IView> streamAllViews()
	{
		return defaultViewsRepositoryStorage.streamAllViews();
	}

	@Override
	public void invalidateView(final ViewId viewId)
	{
		defaultViewsRepositoryStorage.invalidateView(viewId);
	}
}
