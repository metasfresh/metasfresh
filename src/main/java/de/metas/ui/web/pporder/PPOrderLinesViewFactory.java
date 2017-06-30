package de.metas.ui.web.pporder;

import java.util.Collection;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.CCache;
import org.springframework.beans.factory.annotation.Autowired;

import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.pattribute.ASIRepository;
import de.metas.ui.web.view.ASIViewRowAttributesProvider;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONFilterViewRequest;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.factory.standard.LayoutFactory;

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

@ViewFactory(windowId = WebPPOrderConfig.AD_WINDOW_ID_IssueReceipt_String, viewTypes = {})
public class PPOrderLinesViewFactory implements IViewFactory
{
	@Autowired
	private ASIRepository asiRepository;

	private final transient CCache<WindowId, ViewLayout> layouts = CCache.newLRUCache("PPOrderLinesViewFactory#Layouts", 10, 0);

	@Override
	public PPOrderLinesView createView(final CreateViewRequest request)
	{
		final ViewId viewId = ViewId.random(request.getWindowId());

		return PPOrderLinesView.builder()
				.parentViewId(request.getParentViewId())
				.viewId(viewId)
				.viewType(request.getViewType())
				.referencingDocumentPaths(request.getReferencingDocumentPaths())
				.ppOrderId(request.getSingleFilterOnlyId())
				.asiAttributesProvider(ASIViewRowAttributesProvider.newInstance(asiRepository))
				.build();
	}

	@Override
	public IView filterView(final IView view, final JSONFilterViewRequest filterViewRequest)
	{
		throw new AdempiereException("View does not support filtering")
				.setParameter("view", view)
				.setParameter("filterViewRequest", filterViewRequest);
	}

	@Override
	public IView deleteStickyFilter(final IView view, final String filterId)
	{
		throw new AdempiereException("View does not allow removing sticky/static filter")
				.setParameter("view", view)
				.setParameter("filterId", filterId);
	}

	@Override
	public ViewLayout getViewLayout(final WindowId windowId, final JSONViewDataType viewDataType_NOTUSED)
	{
		return layouts.getOrLoad(windowId, () -> createViewLayout(windowId));
	}

	@Override
	public Collection<DocumentFilterDescriptor> getViewFilterDescriptors(final WindowId windowId, final JSONViewDataType viewType)
	{
		return null; // not supported
	}

	private final ViewLayout createViewLayout(final WindowId windowId)
	{
		return ViewLayout.builder()
				.setWindowId(windowId)
				.setCaption("PP Order Issue/Receipt")
				.setEmptyResultText(LayoutFactory.HARDCODED_TAB_EMPTY_RESULT_TEXT)
				.setEmptyResultHint(LayoutFactory.HARDCODED_TAB_EMPTY_RESULT_HINT)
				//
				.setHasAttributesSupport(true)
				.setHasTreeSupport(true)
				.setHasIncludedViewSupport(true)
				//
				.addElementsFromViewRowClass(PPOrderLineRow.class, JSONViewDataType.grid)
				//
				.build();
	}
}
