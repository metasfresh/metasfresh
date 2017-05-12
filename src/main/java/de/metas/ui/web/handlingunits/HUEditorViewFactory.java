package de.metas.ui.web.handlingunits;

import java.util.Collection;
import java.util.Set;
import java.util.function.Supplier;

import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.compiere.util.CCache;
import org.compiere.util.Util.ArrayKey;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.ViewCreateRequest;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.descriptor.SqlViewBinding;
import de.metas.ui.web.view.descriptor.SqlViewRowFieldBinding;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;
import de.metas.ui.web.window.descriptor.factory.standard.LayoutFactory;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;

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

@ViewFactory(windowId = WEBUI_HU_Constants.WEBUI_HU_Window_ID_String, viewTypes = { JSONViewDataType.grid, JSONViewDataType.includedView })
public class HUEditorViewFactory implements IViewFactory
{
	@Autowired
	private DocumentDescriptorFactory documentDescriptorFactory;

	private static final Supplier<SqlViewBinding> sqlViewBindingSupplier = ExtendedMemorizingSupplier.of(() -> createSqlViewBinding());
	private final transient CCache<ArrayKey, ViewLayout> layouts = CCache.newLRUCache("HUEditorViewFactory#Layouts", 10, 0);

	private SqlViewBinding getSqlViewBinding()
	{
		return sqlViewBindingSupplier.get();
	}
	
	private static SqlViewBinding createSqlViewBinding()
	{
		return SqlViewBinding.builder()
				.setTableName(I_M_HU.Table_Name)
				.setDisplayFieldNames(I_M_HU.COLUMNNAME_M_HU_ID)
				.setSqlWhereClause("M_HU_Item_Parent_ID is null AND IsActive='Y'") // top levels, active
				//
				.addField(SqlViewRowFieldBinding.builder()
						.fieldName(I_M_HU.COLUMNNAME_M_HU_ID)
						.keyColumn(true)
						.widgetType(DocumentFieldWidgetType.Integer)
						.sqlValueClass(Integer.class)
						.fieldLoader((rs, adLanguage) -> null) // shall not be used
						.build())
				//
				.setOrderBys(ImmutableList.of(DocumentQueryOrderBy.byFieldName(I_M_HU.COLUMNNAME_M_HU_ID, true)))
				.build();
	}

	@Override
	public ViewLayout getViewLayout(final WindowId windowId, final JSONViewDataType viewDataType)
	{
		final ArrayKey key = ArrayKey.of(windowId, viewDataType);
		return layouts.getOrLoad(key, () -> createHUViewLayout(windowId, viewDataType));
	}

	@Override
	public Collection<DocumentFilterDescriptor> getViewFilterDescriptors(final WindowId windowId, final JSONViewDataType viewType)
	{
		return getSqlViewBinding().getViewFilterDescriptors().getAll();
	}
	
	private final ViewLayout createHUViewLayout(final WindowId windowId, JSONViewDataType viewDataType)
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

	private final ViewLayout createHUViewLayout_IncludedView(final WindowId windowId)
	{
		return ViewLayout.builder()
				.setWindowId(windowId)
				.setCaption("HU Editor")
				.setEmptyResultText(LayoutFactory.HARDCODED_TAB_EMPTY_RESULT_TEXT)
				.setEmptyResultHint(LayoutFactory.HARDCODED_TAB_EMPTY_RESULT_HINT)
				.setIdFieldName(IHUEditorRow.COLUMNNAME_M_HU_ID)
				//
				.setHasAttributesSupport(true)
				.setHasTreeSupport(true)
				//
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setCaption("Code")
						.setWidgetType(DocumentFieldWidgetType.Text)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(IHUEditorRow.COLUMNNAME_Value)))
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setCaptionFromAD_Message("M_Product_ID")
						.setWidgetType(DocumentFieldWidgetType.Lookup)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(IHUEditorRow.COLUMNNAME_M_Product_ID)))
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setCaptionFromAD_Message("M_HU_PI_Item_Product_ID")
						.setWidgetType(DocumentFieldWidgetType.Text)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(IHUEditorRow.COLUMNNAME_PackingInfo)))
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setCaptionFromAD_Message("QtyCU")
						.setWidgetType(DocumentFieldWidgetType.Quantity)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(IHUEditorRow.COLUMNNAME_QtyCU)))
				//
				.build();
	}

	private final ViewLayout createHUViewLayout_Grid(final WindowId windowId)
	{
		return ViewLayout.builder()
				.setWindowId(windowId)
				.setCaption("HU Editor")
				.setEmptyResultText(LayoutFactory.HARDCODED_TAB_EMPTY_RESULT_TEXT)
				.setEmptyResultHint(LayoutFactory.HARDCODED_TAB_EMPTY_RESULT_HINT)
				.setIdFieldName(IHUEditorRow.COLUMNNAME_M_HU_ID)
				//
				.setHasAttributesSupport(true)
				.setHasTreeSupport(true)
				//
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setCaption("Code")
						.setWidgetType(DocumentFieldWidgetType.Text)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(IHUEditorRow.COLUMNNAME_Value)))
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setCaptionFromAD_Message("M_Product_ID")
						.setWidgetType(DocumentFieldWidgetType.Lookup)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(IHUEditorRow.COLUMNNAME_M_Product_ID)))
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setCaptionFromAD_Message("HU_UnitType")
						.setWidgetType(DocumentFieldWidgetType.Text)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(IHUEditorRow.COLUMNNAME_HU_UnitType)))
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setCaptionFromAD_Message("M_HU_PI_Item_Product_ID")
						.setWidgetType(DocumentFieldWidgetType.Text)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(IHUEditorRow.COLUMNNAME_PackingInfo)))
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setCaptionFromAD_Message("QtyCU")
						.setWidgetType(DocumentFieldWidgetType.Quantity)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(IHUEditorRow.COLUMNNAME_QtyCU)))
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setCaptionFromAD_Message("C_UOM_ID")
						.setWidgetType(DocumentFieldWidgetType.Lookup)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(IHUEditorRow.COLUMNNAME_C_UOM_ID)))
				//
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setCaptionFromAD_Message(IHUEditorRow.COLUMNNAME_HUStatus)
						.setWidgetType(DocumentFieldWidgetType.Lookup)
						.setGridElement()
						.addField(DocumentLayoutElementFieldDescriptor.builder(IHUEditorRow.COLUMNNAME_HUStatus)))
				//
				.build();
	}

	@Override
	public HUEditorView createView(final ViewCreateRequest request)
	{
		final WindowId windowId = request.getWindowId();
		if (!WEBUI_HU_Constants.WEBUI_HU_Window_ID.equals(windowId))
		{
			throw new IllegalArgumentException("Invalid request's windowId: " + request);
		}

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
		
		final Set<Integer> huIds = request.getFilterOnlyIds();
		return HUEditorView.builder(getSqlViewBinding())
				.setParentViewId(request.getParentViewId())
				.setWindowId(windowId)
				.setHUIds(huIds)
				.setHighVolume(huIds.isEmpty() || huIds.size() >= HUEditorViewBuffer_HighVolume.HIGHVOLUME_THRESHOLD)
				.setReferencingDocumentPaths(referencingTableName, referencingDocumentPaths)
				.setActions(request.getActions())
				.build();
	}
}
