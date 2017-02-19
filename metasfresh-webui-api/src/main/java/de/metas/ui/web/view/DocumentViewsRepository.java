package de.metas.ui.web.view;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.adempiere.util.Check;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalNotification;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.view.json.JSONCreateDocumentViewRequest;

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
	@Autowired
	private CompositeDocumentViewSelectionFactory documentViewSelectionFactory;

	private final Cache<String, IDocumentViewSelection> views = CacheBuilder.newBuilder()
			.expireAfterAccess(1, TimeUnit.HOURS)
			.removalListener(notification -> onViewRemoved(notification))
			.build();

	@Override
	public List<IDocumentViewSelection> getViews()
	{
		return ImmutableList.copyOf(views.asMap().values());
	}

	@Override
	public IDocumentViewSelection createView(final JSONCreateDocumentViewRequest jsonRequest)
	{
		final IDocumentViewSelection view = documentViewSelectionFactory.createView(jsonRequest);
		views.put(view.getViewId(), view);
		return view;
	}

	@Override
	public IDocumentViewSelection getView(final String viewId)
	{
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
	public void notifyRecordChanged(final TableRecordReference recordRef)
	{
		Check.assumeNotNull(recordRef, "Parameter recordRef is not null");

		views.asMap().values().stream()
				.forEach(view -> view.notifyRecordChanged(recordRef));
	}
}
