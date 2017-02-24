package de.metas.ui.web.handlingunits;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import org.compiere.util.CCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.metas.ui.web.process.descriptor.ProcessDescriptorsFactory;
import de.metas.ui.web.view.IDocumentViewSelection;
import de.metas.ui.web.view.IDocumentViewSelectionFactory;
import de.metas.ui.web.view.descriptor.DocumentViewLayout;
import de.metas.ui.web.view.json.JSONCreateDocumentViewRequest;
import de.metas.ui.web.view.json.JSONDocumentViewLayout;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.datatypes.json.JSONViewDataType;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;
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

@Service
public class HUDocumentViewSelectionFactory implements IDocumentViewSelectionFactory
{
	@Autowired
	private DocumentDescriptorFactory documentDescriptorFactory;
	@Autowired
	private ProcessDescriptorsFactory processDescriptorsFactory;

	private final transient CCache<Integer, DocumentViewLayout> layouts = CCache.newLRUCache("HUDocumentViewSelectionFactory#Layouts", 100, 0);

	@Override
	public JSONDocumentViewLayout getViewLayout(final int adWindowId, final JSONViewDataType viewDataType_NOTUSED, final JSONOptions jsonOpts)
	{
		final DocumentViewLayout huViewLayout = layouts.getOrLoad(adWindowId, () -> createHUViewLayout(adWindowId));

		// final DocumentEntityDescriptor entityDescriptor = documentDescriptorFactory.getDocumentEntityDescriptor(adWindowId);
		final Collection<DocumentFilterDescriptor> filters = null; // filters are not supported yet

		return JSONDocumentViewLayout.of(huViewLayout, filters, jsonOpts);
	}

	private final DocumentViewLayout createHUViewLayout(final int adWindowId)
	{
		return DocumentViewLayout.builder()
				.setAD_Window_ID(adWindowId)
				.setEmptyResultText(LayoutFactory.HARDCODED_TAB_EMPTY_RESULT_TEXT)
				.setEmptyResultHint(LayoutFactory.HARDCODED_TAB_EMPTY_RESULT_HINT)
				.setIdFieldName(I_WEBUI_HU_View.COLUMNNAME_M_HU_ID)
				//
				.setHasAttributesSupport(true)
				.setHasTreeSupport(true)
				//
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setCaption("Code")
						.setWidgetType(DocumentFieldWidgetType.Text)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(I_WEBUI_HU_View.COLUMNNAME_Value)))
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setCaptionFromAD_Message("M_Product_ID")
						.setWidgetType(DocumentFieldWidgetType.Lookup)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(I_WEBUI_HU_View.COLUMNNAME_M_Product_ID)))
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setCaptionFromAD_Message("HU_UnitType")
						.setWidgetType(DocumentFieldWidgetType.Text)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(I_WEBUI_HU_View.COLUMNNAME_HU_UnitType)))
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setCaptionFromAD_Message("M_HU_PI_Item_Product_ID")
						.setWidgetType(DocumentFieldWidgetType.Text)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(I_WEBUI_HU_View.COLUMNNAME_PackingInfo)))
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setCaptionFromAD_Message("QtyCU")
						.setWidgetType(DocumentFieldWidgetType.Quantity)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(I_WEBUI_HU_View.COLUMNNAME_QtyCU)))
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setCaptionFromAD_Message("C_UOM_ID")
						.setWidgetType(DocumentFieldWidgetType.Lookup)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(I_WEBUI_HU_View.COLUMNNAME_C_UOM_ID)))
				//
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setCaptionFromAD_Message(I_WEBUI_HU_View.COLUMNNAME_HUStatus)
						.setWidgetType(DocumentFieldWidgetType.Lookup)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(I_WEBUI_HU_View.COLUMNNAME_HUStatus)))
				//
				.build();
	}

	@Override
	public IDocumentViewSelection createView(final JSONCreateDocumentViewRequest jsonRequest)
	{
		final String viewId = UUID.randomUUID().toString();
		final int adWindowId = jsonRequest.getAD_Window_ID();

		//
		// Referencing path and tableName (i.e. from where are we coming)
		final Set<DocumentPath> referencingDocumentPaths = jsonRequest.getReferencingDocumentPaths();
		final String referencingTableName;
		if (!referencingDocumentPaths.isEmpty())
		{
			final int referencingWindowId = referencingDocumentPaths.iterator().next().getAD_Window_ID(); // assuming all document paths have the same window
			referencingTableName = documentDescriptorFactory.getDocumentEntityDescriptor(referencingWindowId)
					.getTableNameOrNull();
		}
		else
		{
			referencingTableName = null;
		}

		final HUDocumentViewLoader documentViewsLoader = HUDocumentViewLoader.of(jsonRequest, referencingTableName);

		return HUDocumentViewSelection.builder()
				.setViewId(viewId)
				.setAD_Window_ID(adWindowId)
				.setRecords(documentViewsLoader)
				.setReferencingDocumentPaths(referencingDocumentPaths)
				.setServices(processDescriptorsFactory)
				.build();
	}
}
