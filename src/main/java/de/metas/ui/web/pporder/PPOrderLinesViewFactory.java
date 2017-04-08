package de.metas.ui.web.pporder;

import java.util.Collection;
import java.util.UUID;

import org.compiere.util.CCache;
import org.springframework.beans.factory.annotation.Autowired;

import de.metas.ui.web.process.descriptor.ProcessDescriptorsFactory;
import de.metas.ui.web.view.DocumentViewCreateRequest;
import de.metas.ui.web.view.DocumentViewFactory;
import de.metas.ui.web.view.IDocumentViewSelection;
import de.metas.ui.web.view.IDocumentViewSelectionFactory;
import de.metas.ui.web.view.descriptor.DocumentViewLayout;
import de.metas.ui.web.view.json.JSONDocumentViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
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

@DocumentViewFactory(windowId = WebPPOrderConfig.AD_WINDOW_ID_IssueReceipt, viewType = JSONViewDataType.grid)
public class PPOrderLinesViewFactory implements IDocumentViewSelectionFactory
{
	@Autowired
	private ProcessDescriptorsFactory processDescriptorsFactory;

	private final transient CCache<Integer, DocumentViewLayout> layouts = CCache.newLRUCache("PPOrderLinesViewFactory#Layouts", 10, 0);
	
	@Override
	public IDocumentViewSelection createView(final DocumentViewCreateRequest request)
	{
		return PPOrderLinesView.builder()
				.setParentViewId(request.getParentViewId())
				.setViewId(UUID.randomUUID().toString())
				.setAD_Window_ID(request.getAD_Window_ID())
				.setRecords(PPOrderLinesLoader.of(request))
				.setServices(processDescriptorsFactory)
				.build();
	}

	@Override
	public JSONDocumentViewLayout getViewLayout(final int adWindowId, final JSONViewDataType viewDataType_NOTUSED, final JSONOptions jsonOpts)
	{
		final DocumentViewLayout viewLayout = layouts.getOrLoad(adWindowId, () -> createViewLayout(adWindowId));
		final Collection<DocumentFilterDescriptor> filters = null; // filters are not supported yet

		return JSONDocumentViewLayout.of(viewLayout, filters, jsonOpts);
	}

	private final DocumentViewLayout createViewLayout(final int adWindowId)
	{
		return DocumentViewLayout.builder()
				.setAD_Window_ID(adWindowId)
				.setCaption("PP Order Issue/Receipt")
				.setEmptyResultText(LayoutFactory.HARDCODED_TAB_EMPTY_RESULT_TEXT)
				.setEmptyResultHint(LayoutFactory.HARDCODED_TAB_EMPTY_RESULT_HINT)
				//
				.setHasTreeSupport(true)
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
						.addField(DocumentLayoutElementFieldDescriptor.builder(IPPOrderBOMLine.COLUMNNAME_BOMType)))
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setWidgetType(DocumentFieldWidgetType.Text)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(IPPOrderBOMLine.COLUMNNAME_HUType)))
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setWidgetType(DocumentFieldWidgetType.Text)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(IPPOrderBOMLine.COLUMNNAME_PackingInfo)))
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setWidgetType(DocumentFieldWidgetType.Quantity)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(IPPOrderBOMLine.COLUMNNAME_Qty)))
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setWidgetType(DocumentFieldWidgetType.Quantity)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(IPPOrderBOMLine.COLUMNNAME_QtyPlan)))
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setWidgetType(DocumentFieldWidgetType.Lookup)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(IPPOrderBOMLine.COLUMNNAME_C_UOM_ID)))
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setWidgetType(DocumentFieldWidgetType.Text)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(IPPOrderBOMLine.COLUMNNAME_StatusInfo)))
				//
				.build();
	}
}
