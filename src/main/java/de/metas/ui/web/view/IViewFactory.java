package de.metas.ui.web.view;

import java.util.List;

import com.google.common.collect.ImmutableList;

import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONFilterViewRequest;
import de.metas.ui.web.view.json.JSONViewDataType;
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

public interface IViewFactory
{
	IView createView(CreateViewRequest request);

	ViewLayout getViewLayout(WindowId windowId, JSONViewDataType viewDataType, ViewProfileId profileId);

	default List<ViewProfile> getAvailableProfiles(final WindowId windowId)
	{
		return ImmutableList.of();
	}

	default IView filterView(final IView view, final JSONFilterViewRequest filterViewRequest)
	{
		final CreateViewRequest createViewRequest = CreateViewRequest.filterViewBuilder(view, filterViewRequest).build();
		return createView(createViewRequest);
	}

	default IView deleteStickyFilter(final IView view, final String filterId)
	{
		final CreateViewRequest createViewRequest = CreateViewRequest.deleteStickyFilterBuilder(view, filterId).build();
		return createView(createViewRequest);
	}
}
