package de.metas.ui.web.pporder;

import java.util.Collection;

import org.compiere.util.CCache;
import org.springframework.beans.factory.annotation.Autowired;

import de.metas.ui.web.pattribute.ASIRepository;
import de.metas.ui.web.view.ASIDocumentViewAttributesProvider;
import de.metas.ui.web.view.DocumentViewCreateRequest;
import de.metas.ui.web.view.DocumentViewFactory;
import de.metas.ui.web.view.IDocumentViewSelectionFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.descriptor.DocumentViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.ui.web.window.descriptor.factory.standard.LayoutFactory;
import de.metas.ui.web.window.descriptor.filters.DocumentFilterDescriptor;

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

@DocumentViewFactory(windowId = WebPPOrderConfig.AD_WINDOW_ID_IssueReceipt_String, viewTypes = {})
public class PPOrderLinesViewFactory implements IDocumentViewSelectionFactory
{
	@Autowired
	private ASIRepository asiRepository;

	private final transient CCache<WindowId, DocumentViewLayout> layouts = CCache.newLRUCache("PPOrderLinesViewFactory#Layouts", 10, 0);

	@Override
	public PPOrderLinesView createView(final DocumentViewCreateRequest request)
	{
		final ViewId viewId = ViewId.random(request.getWindowId());
		
		return PPOrderLinesView.builder()
				.parentViewId(request.getParentViewId())
				.viewId(viewId)
				.ppOrderId(request.getSingleFilterOnlyId())
				.asiAttributesProvider(ASIDocumentViewAttributesProvider.newInstance(asiRepository))
				.build();
	}

	@Override
	public DocumentViewLayout getViewLayout(final WindowId windowId, final JSONViewDataType viewDataType_NOTUSED)
	{
		return layouts.getOrLoad(windowId, () -> createViewLayout(windowId));
	}

	@Override
	public Collection<DocumentFilterDescriptor> getViewFilters(final WindowId windowId)
	{
		return null; // not supported
	}

	private final DocumentViewLayout createViewLayout(final WindowId windowId)
	{
		return DocumentViewLayout.builder()
				.setWindowId(windowId)
				.setCaption("PP Order Issue/Receipt")
				.setEmptyResultText(LayoutFactory.HARDCODED_TAB_EMPTY_RESULT_TEXT)
				.setEmptyResultHint(LayoutFactory.HARDCODED_TAB_EMPTY_RESULT_HINT)
				//
				.setHasAttributesSupport(true)
				.setHasTreeSupport(true)
				.setHasIncludedViewSupport(true)
				//
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setWidgetType(DocumentFieldWidgetType.Lookup)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(IPPOrderBOMLine.COLUMNNAME_M_Product_ID)))
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setWidgetType(DocumentFieldWidgetType.Text)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(IPPOrderBOMLine.COLUMNNAME_Value)))
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setWidgetType(DocumentFieldWidgetType.Text)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(IPPOrderBOMLine.COLUMNNAME_Type)))
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setWidgetType(DocumentFieldWidgetType.Text)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(IPPOrderBOMLine.COLUMNNAME_PackingInfo)))
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setWidgetType(DocumentFieldWidgetType.Quantity)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(IPPOrderBOMLine.COLUMNNAME_QtyPlan)))
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setWidgetType(DocumentFieldWidgetType.Quantity)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(IPPOrderBOMLine.COLUMNNAME_Qty)))
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setWidgetType(DocumentFieldWidgetType.Lookup)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(IPPOrderBOMLine.COLUMNNAME_C_UOM_ID)))
				//
				.build();
	}
}
