package de.metas.ui.web.view;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.adempiere.util.lang.impl.TableRecordReference;

import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONFilterViewRequest;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;
import lombok.NonNull;

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

/**
 * {@link IView}s repository.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IViewsRepository
{
	List<ViewProfile> getAvailableProfiles(WindowId windowId, JSONViewDataType viewDataType);

	ViewLayout getViewLayout(WindowId windowId, JSONViewDataType viewDataType, final ViewProfileId profileId);

	/** @return view or <code>null</code> */
	IView getViewIfExists(ViewId viewId);

	/** @return view; never returns null */
	IView getView(String viewId);

	default IView getView(@NonNull final ViewId viewId)
	{
		final IView view = getView(viewId.getViewId());

		// Make sure the windowId matches the view's windowId.
		// NOTE: for now, if the windowId is not provided, let's not validate it because deprecate API cannot provide the windowId
		if (!Objects.equals(viewId.getWindowId(), view.getViewId().getWindowId()))
		{
			throw new IllegalArgumentException("View's windowId is not matching the expected one."
					+ "\n Expected windowId: " + viewId.getWindowId()
					+ "\n View: " + view);
		}

		return view;
	}

	default <T extends IView> T getView(final ViewId viewId, final Class<T> type)
	{
		@SuppressWarnings("unchecked")
		final T view = (T)getView(viewId);
		return view;
	}

	IView createView(CreateViewRequest request);

	IView filterView(ViewId viewId, JSONFilterViewRequest jsonRequest);

	IView deleteStickyFilter(ViewId viewId, String filterId);

	void deleteView(ViewId viewId);

	void invalidateView(ViewId viewId);

	List<IView> getViews();

	/**
	 * Notify all views that given records was changed (asynchronously).
	 */
	void notifyRecordsChanged(Set<TableRecordReference> recordRefs);

	default void notifyRecordChanged(final String tableName, final int recordId)
	{
		notifyRecordsChanged(ImmutableSet.of(TableRecordReference.of(tableName, recordId)));
	}
}
