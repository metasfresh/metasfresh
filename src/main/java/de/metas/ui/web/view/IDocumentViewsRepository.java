package de.metas.ui.web.view;

import java.util.List;
import java.util.Set;

import org.adempiere.util.lang.impl.TableRecordReference;

import de.metas.ui.web.view.json.JSONDocumentViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.json.JSONOptions;

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

public interface IDocumentViewsRepository
{
	JSONDocumentViewLayout getViewLayout(int adWindowId, JSONViewDataType viewDataType, JSONOptions jsonOpts);

	boolean hasView(String viewId);
	
	/** @return view or <code>null</code> */
	IDocumentViewSelection getViewIfExists(String viewId);

	/** @return view; never returns null */
	IDocumentViewSelection getView(String viewId);
	
	default IDocumentViewSelection getView(final int adWindowId, final String viewId)
	{
		final IDocumentViewSelection view = getView(viewId);

		// Make sure the adWindowId matches the view's windowId.
		// NOTE: for now, if the windowId is not provided, let's not validate it because deprecate API cannot provide the windowId
		if (adWindowId > 0 && adWindowId != view.getAD_Window_ID())
		{
			throw new IllegalArgumentException("View's windowId is not matching the expected one."
					+ "\n Expected windowId: " + adWindowId
					+ "\n View: " +view);
		}

		return view;
	}

	default <T extends IDocumentViewSelection> T getView(final String viewId, final Class<T> type)
	{
		@SuppressWarnings("unchecked")
		final T view = (T)getView(viewId);
		return view;
	}

	IDocumentViewSelection createView(DocumentViewCreateRequest request);

	void deleteView(String viewId);

	List<IDocumentViewSelection> getViews();

	/**
	 * Notify all views that given records was changed.
	 */
	void notifyRecordsChanged(Set<TableRecordReference> recordRefs);
}
