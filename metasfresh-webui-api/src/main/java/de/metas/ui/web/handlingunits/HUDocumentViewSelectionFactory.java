package de.metas.ui.web.handlingunits;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.CCache;
import org.compiere.util.Env;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.ui.web.process.descriptor.ProcessDescriptorsFactory;
import de.metas.ui.web.view.DocumentView;
import de.metas.ui.web.view.DocumentViewAttributesProviderFactory;
import de.metas.ui.web.view.IDocumentView;
import de.metas.ui.web.view.IDocumentViewAttributesProvider;
import de.metas.ui.web.view.IDocumentViewSelection;
import de.metas.ui.web.view.IDocumentViewSelectionFactory;
import de.metas.ui.web.view.descriptor.DocumentViewLayout;
import de.metas.ui.web.view.json.JSONCreateDocumentViewRequest;
import de.metas.ui.web.view.json.JSONDocumentViewLayout;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.datatypes.json.JSONViewDataType;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
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

		final DocumentEntityDescriptor entityDescriptor = documentDescriptorFactory.getDocumentEntityDescriptor(adWindowId);
		final Collection<DocumentFilterDescriptor> filters = entityDescriptor.getFiltersProvider().getAll();

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

		final ImmutableList<IDocumentView> records = retrieveTopLevelHUs(jsonRequest)
				.stream()
				.map(hu -> createDocumentView(adWindowId, hu))
				.collect(GuavaCollectors.toImmutableList());

		return HUDocumentViewSelection.builder()
				.setViewId(viewId)
				.setAD_Window_ID(adWindowId)
				.setRecords(records)
				.setReferencingDocumentPath(jsonRequest.getReferencingDocumentPathOrNull())
				.setServices(processDescriptorsFactory)
				.build();
	}

	private IDocumentView createDocumentView(final int adWindowId, final I_M_HU hu)
	{
		final IDocumentViewAttributesProvider attributesProvider = DocumentViewAttributesProviderFactory.instance.createProviderOrNull(DocumentType.Window, adWindowId);

		final String huUnitType = hu.getM_HU_PI_Version().getHU_UnitType();
		
		final String huStatusKey = hu.getHUStatus();
		final String huStatusDisplayName = Services.get(IADReferenceDAO.class).retriveListName(Env.getCtx(), I_WEBUI_HU_View.HUSTATUS_AD_Reference_ID, huStatusKey);
		final JSONLookupValue huStatus = JSONLookupValue.of(huStatusKey, huStatusDisplayName);
		
		
		final DocumentView.Builder huViewRecord = DocumentView.builder(adWindowId)
				.setIdFieldName(I_WEBUI_HU_View.COLUMNNAME_M_HU_ID)
				.setAttributesProvider(attributesProvider)
				//
				.putFieldValue(I_WEBUI_HU_View.COLUMNNAME_M_HU_ID, hu.getM_HU_ID())
				.putFieldValue(I_WEBUI_HU_View.COLUMNNAME_Value, hu.getValue())
				.putFieldValue(I_WEBUI_HU_View.COLUMNNAME_HU_UnitType, huUnitType)
				.putFieldValue(I_WEBUI_HU_View.COLUMNNAME_HUStatus, huStatus);

		if (X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit.equals(huUnitType))
		{
			Services.get(IHandlingUnitsDAO.class).retrieveIncludedHUs(hu)
					.stream()
					.map(includedHU -> createDocumentView(adWindowId, includedHU))
					.forEach(huViewRecord::addIncludedDocument);
		}
		else if (X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit.equals(huUnitType)
				|| X_M_HU_PI_Version.HU_UNITTYPE_VirtualPI.equals(huUnitType))
		{
			Services.get(IHandlingUnitsBL.class).getStorageFactory()
					.getStorage(hu)
					.getProductStorages()
					.stream()
					.map(huStorage -> createDocumentView(adWindowId, huStorage))
					.forEach(huViewRecord::addIncludedDocument);
		}
		else
		{
			throw new IllegalStateException("Unknown HU_UnitType=" + huUnitType + " for " + hu);
		}

		return huViewRecord.build();
	}

	private IDocumentView createDocumentView(final int adWindowId, final IHUProductStorage huStorage)
	{
		final I_M_HU hu = huStorage.getM_HU();
		final I_M_Product product = huStorage.getM_Product();

		return DocumentView.builder(adWindowId)
				.setDocumentId(DocumentId.ofString(I_M_HU_Storage.Table_Name + "#" + hu.getM_HU_ID() + "#" + product.getM_Product_ID()))
				.setIdFieldName(null) // N/A
				//
				.putFieldValue(I_WEBUI_HU_View.COLUMNNAME_M_HU_ID, hu.getM_HU_ID())
				.putFieldValue(I_WEBUI_HU_View.COLUMNNAME_Value, hu.getValue())
				.putFieldValue(I_WEBUI_HU_View.COLUMNNAME_HU_UnitType, X_M_HU_PI_Version.HU_UNITTYPE_VirtualPI)
				//
				.putFieldValue(I_WEBUI_HU_View.COLUMNNAME_M_Product_ID, createLookupValue(product))
				.putFieldValue(I_WEBUI_HU_View.COLUMNNAME_C_UOM_ID, createLookupValue(huStorage.getC_UOM()))
				.putFieldValue(I_WEBUI_HU_View.COLUMNNAME_QtyCU, huStorage.getQty())
				//
				.build();
	}

	private JSONLookupValue createLookupValue(final I_M_Product product)
	{
		if (product == null)
		{
			return null;
		}

		final String displayName = product.getValue() + "_" + product.getName();
		return JSONLookupValue.of(product.getM_Product_ID(), displayName);
	}

	private JSONLookupValue createLookupValue(final I_C_UOM uom)
	{
		if (uom == null)
		{
			return null;
		}

		return JSONLookupValue.of(uom.getC_UOM_ID(), uom.getUOMSymbol());
	}

	private List<I_M_HU> retrieveTopLevelHUs(final JSONCreateDocumentViewRequest jsonRequest)
	{
		boolean haveFilters = false;

		final IQueryBuilder<I_M_HU> queryBuilder = Services.get(IHandlingUnitsDAO.class)
				.createHUQueryBuilder()
				.setContext(Env.getCtx(), ITrx.TRXNAME_None)
				.setOnlyTopLevelHUs()
				.createQueryBuilder();

		final Set<Integer> filterOnlyIds = jsonRequest.getFilterOnlyIds();
		if (filterOnlyIds != null && !filterOnlyIds.isEmpty())
		{
			queryBuilder.addInArrayFilter(I_M_HU.COLUMN_M_HU_ID, filterOnlyIds);
			haveFilters = true;
		}

		if (!haveFilters)
		{
			throw new IllegalArgumentException("No filters specified for " + jsonRequest);
		}

		return queryBuilder
				.create()
				.list();
	}

}
