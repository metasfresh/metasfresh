package de.metas.ui.web.handlingunits;

import java.util.Collection;
import java.util.Set;

import org.compiere.util.CCache;
import org.compiere.util.Util.ArrayKey;
import org.springframework.beans.factory.annotation.Autowired;

import de.metas.ui.web.view.DocumentViewCreateRequest;
import de.metas.ui.web.view.DocumentViewFactory;
import de.metas.ui.web.view.IDocumentViewSelectionFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.descriptor.DocumentViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.WindowId;
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

@DocumentViewFactory(windowId = WEBUI_HU_Constants.WEBUI_HU_Window_ID_String, viewTypes = { JSONViewDataType.grid, JSONViewDataType.includedView })
public class HUDocumentViewSelectionFactory implements IDocumentViewSelectionFactory
{
	@Autowired
	private DocumentDescriptorFactory documentDescriptorFactory;

	private final transient CCache<ArrayKey, DocumentViewLayout> layouts = CCache.newLRUCache("HUDocumentViewSelectionFactory#Layouts", 10, 0);

	@Override
	public DocumentViewLayout getViewLayout(final WindowId windowId, final JSONViewDataType viewDataType)
	{
		final ArrayKey key = ArrayKey.of(windowId, viewDataType);
		return layouts.getOrLoad(key, () -> createHUViewLayout(windowId, viewDataType));
	}

	@Override
	public Collection<DocumentFilterDescriptor> getViewFilters(final WindowId windowId)
	{
		return null; // not supported
	}

	private final DocumentViewLayout createHUViewLayout(final WindowId windowId, JSONViewDataType viewDataType)
	{
		if (viewDataType == JSONViewDataType.includedView)
		{
			return createHUViewLayout_IncludedView(windowId);
		}
		else
		{
			return createHUViewLayout_Grid(windowId);
		}
	}

	private final DocumentViewLayout createHUViewLayout_IncludedView(final WindowId windowId)
	{
		return DocumentViewLayout.builder()
				.setWindowId(windowId)
				.setCaption("HU Editor")
				.setEmptyResultText(LayoutFactory.HARDCODED_TAB_EMPTY_RESULT_TEXT)
				.setEmptyResultHint(LayoutFactory.HARDCODED_TAB_EMPTY_RESULT_HINT)
				.setIdFieldName(IHUDocumentView.COLUMNNAME_M_HU_ID)
				//
				.setHasAttributesSupport(true)
				.setHasTreeSupport(true)
				//
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setCaption("Code")
						.setWidgetType(DocumentFieldWidgetType.Text)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(IHUDocumentView.COLUMNNAME_Value)))
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setCaptionFromAD_Message("M_Product_ID")
						.setWidgetType(DocumentFieldWidgetType.Lookup)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(IHUDocumentView.COLUMNNAME_M_Product_ID)))
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setCaptionFromAD_Message("M_HU_PI_Item_Product_ID")
						.setWidgetType(DocumentFieldWidgetType.Text)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(IHUDocumentView.COLUMNNAME_PackingInfo)))
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setCaptionFromAD_Message("QtyCU")
						.setWidgetType(DocumentFieldWidgetType.Quantity)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(IHUDocumentView.COLUMNNAME_QtyCU)))
				//
				.build();
	}

	private final DocumentViewLayout createHUViewLayout_Grid(final WindowId windowId)
	{
		return DocumentViewLayout.builder()
				.setWindowId(windowId)
				.setCaption("HU Editor")
				.setEmptyResultText(LayoutFactory.HARDCODED_TAB_EMPTY_RESULT_TEXT)
				.setEmptyResultHint(LayoutFactory.HARDCODED_TAB_EMPTY_RESULT_HINT)
				.setIdFieldName(IHUDocumentView.COLUMNNAME_M_HU_ID)
				//
				.setHasAttributesSupport(true)
				.setHasTreeSupport(true)
				//
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setCaption("Code")
						.setWidgetType(DocumentFieldWidgetType.Text)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(IHUDocumentView.COLUMNNAME_Value)))
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setCaptionFromAD_Message("M_Product_ID")
						.setWidgetType(DocumentFieldWidgetType.Lookup)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(IHUDocumentView.COLUMNNAME_M_Product_ID)))
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setCaptionFromAD_Message("HU_UnitType")
						.setWidgetType(DocumentFieldWidgetType.Text)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(IHUDocumentView.COLUMNNAME_HU_UnitType)))
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setCaptionFromAD_Message("M_HU_PI_Item_Product_ID")
						.setWidgetType(DocumentFieldWidgetType.Text)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(IHUDocumentView.COLUMNNAME_PackingInfo)))
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setCaptionFromAD_Message("QtyCU")
						.setWidgetType(DocumentFieldWidgetType.Quantity)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(IHUDocumentView.COLUMNNAME_QtyCU)))
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setCaptionFromAD_Message("C_UOM_ID")
						.setWidgetType(DocumentFieldWidgetType.Lookup)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(IHUDocumentView.COLUMNNAME_C_UOM_ID)))
				//
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setCaptionFromAD_Message(IHUDocumentView.COLUMNNAME_HUStatus)
						.setWidgetType(DocumentFieldWidgetType.Lookup)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(IHUDocumentView.COLUMNNAME_HUStatus)))
				//
				.build();
	}

	@Override
	public HUDocumentViewSelection createView(final DocumentViewCreateRequest request)
	{
		final WindowId windowId = request.getWindowId();
		if (!WEBUI_HU_Constants.WEBUI_HU_Window_ID.equals(windowId))
		{
			throw new IllegalArgumentException("Invalid request's windowId: " + request);
		}

		final ViewId viewId = ViewId.random(windowId);

		//
		// Referencing path and tableName (i.e. from where are we coming, e.g. receipt schedule)
		final Set<DocumentPath> referencingDocumentPaths = request.getReferencingDocumentPaths();
		final String referencingTableName;
		if (!referencingDocumentPaths.isEmpty())
		{
			final WindowId referencingWindowId = referencingDocumentPaths.iterator().next().getWindowId(); // assuming all document paths have the same window
			referencingTableName = documentDescriptorFactory.getDocumentEntityDescriptor(referencingWindowId)
					.getTableNameOrNull();
		}
		else
		{
			referencingTableName = null;
		}

		final HUDocumentViewLoader documentViewsLoader = HUDocumentViewLoader.of(request, referencingTableName);

		return HUDocumentViewSelection.builder()
				.setParentViewId(request.getParentViewId())
				.setViewId(viewId)
				.setRecords(documentViewsLoader)
				.setReferencingDocumentPaths(referencingDocumentPaths)
				.setActions(request.getActions())
				.build();
	}
}
