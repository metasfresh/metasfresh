package de.metas.ui.web.window.model.sql;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Repository;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalNotification;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.window.model.DocumentQuery;
import de.metas.ui.web.window.model.DocumentViewsRepository;
import de.metas.ui.web.window.model.IDocumentViewSelection;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Repository
public class SqlDocumentViewsRepository implements DocumentViewsRepository
{
	private final Cache<String, SqlDocumentViewSelection> views = CacheBuilder.newBuilder()
			.expireAfterAccess(1, TimeUnit.HOURS)
			.removalListener(notification -> onViewRemoved(notification))
			.build();

	@Override
	public List<IDocumentViewSelection> getViews()
	{
		return ImmutableList.copyOf(views.asMap().values());
	}

	@Override
	public IDocumentViewSelection createView(final DocumentQuery query)
	{
		final SqlDocumentViewSelection view = SqlDocumentViewSelection.builder()
				.setQuery(query)
				.build();
		views.put(view.getViewId(), view);
		return view;
	}

	@Override
	public IDocumentViewSelection getView(final String viewId)
	{
		final SqlDocumentViewSelection view = views.getIfPresent(viewId);
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
		final SqlDocumentViewSelection view = (SqlDocumentViewSelection)notification.getValue();
		view.close();
	}
}
