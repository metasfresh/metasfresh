package de.metas.ui.web.handlingunits;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.ui.web.view.DocumentView;
import de.metas.ui.web.view.DocumentViewAttributesProviderFactory;
import de.metas.ui.web.view.IDocumentView;
import de.metas.ui.web.view.IDocumentViewAttributesProvider;
import de.metas.ui.web.view.json.JSONCreateDocumentViewRequest;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;

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

public class HUDocumentViewLoader
{
	public static final HUDocumentViewLoader of(final JSONCreateDocumentViewRequest request)
	{
		return new HUDocumentViewLoader(request);
	}

	private final int adWindowId;
	private final Set<Integer> filterOnlyIds;

	private final IDocumentViewAttributesProvider _attributesProvider;

	private HUDocumentViewLoader(final JSONCreateDocumentViewRequest request)
	{
		super();

		adWindowId = request.getAD_Window_ID();

		boolean haveFilters = false;

		final Set<Integer> filterOnlyIds = request.getFilterOnlyIds();
		if (filterOnlyIds != null && !filterOnlyIds.isEmpty())
		{
			this.filterOnlyIds = ImmutableSet.copyOf(filterOnlyIds);
			haveFilters = true;
		}
		else
		{
			this.filterOnlyIds = ImmutableSet.of();
		}

		if (!haveFilters)
		{
			throw new IllegalArgumentException("No filters specified for " + request);
		}

		_attributesProvider = DocumentViewAttributesProviderFactory.instance.createProviderOrNull(DocumentType.Window, adWindowId);
	}

	public IDocumentViewAttributesProvider getAttributesProvider()
	{
		return _attributesProvider;
	}

	public List<IDocumentView> retrieveDocumentViews()
	{
		return retrieveTopLevelHUs(filterOnlyIds)
				.stream()
				.map(hu -> createDocumentView(hu))
				.collect(GuavaCollectors.toImmutableList());
	}

	private static List<I_M_HU> retrieveTopLevelHUs(final Collection<Integer> filterOnlyIds)
	{
		final IQueryBuilder<I_M_HU> queryBuilder = Services.get(IHandlingUnitsDAO.class)
				.createHUQueryBuilder()
				.setContext(Env.getCtx(), ITrx.TRXNAME_None)
				.setOnlyTopLevelHUs()
				.createQueryBuilder();

		if (filterOnlyIds != null && !filterOnlyIds.isEmpty())
		{
			queryBuilder.addInArrayFilter(I_M_HU.COLUMN_M_HU_ID, filterOnlyIds);
		}

		return queryBuilder
				.create()
				.list();
	}

	private IDocumentView createDocumentView(final I_M_HU hu)
	{
		final String huUnitType = hu.getM_HU_PI_Version().getHU_UnitType();

		final String huStatusKey = hu.getHUStatus();
		final String huStatusDisplayName = Services.get(IADReferenceDAO.class).retriveListName(Env.getCtx(), I_WEBUI_HU_View.HUSTATUS_AD_Reference_ID, huStatusKey);
		final JSONLookupValue huStatus = JSONLookupValue.of(huStatusKey, huStatusDisplayName);

		final DocumentView.Builder huViewRecord = DocumentView.builder(adWindowId)
				.setIdFieldName(I_WEBUI_HU_View.COLUMNNAME_M_HU_ID)
				.setAttributesProvider(getAttributesProvider())
				//
				.putFieldValue(I_WEBUI_HU_View.COLUMNNAME_M_HU_ID, hu.getM_HU_ID())
				.putFieldValue(I_WEBUI_HU_View.COLUMNNAME_Value, hu.getValue())
				.putFieldValue(I_WEBUI_HU_View.COLUMNNAME_HU_UnitType, huUnitType)
				.putFieldValue(I_WEBUI_HU_View.COLUMNNAME_HUStatus, huStatus)
				.putFieldValue(I_WEBUI_HU_View.COLUMNNAME_PackingInfo, extractPackingInfo(hu));

		if (X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit.equals(huUnitType))
		{
			Services.get(IHandlingUnitsDAO.class).retrieveIncludedHUs(hu)
					.stream()
					.map(includedHU -> createDocumentView(includedHU))
					.forEach(huViewRecord::addIncludedDocument);
		}
		else if (X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit.equals(huUnitType)
				|| X_M_HU_PI_Version.HU_UNITTYPE_VirtualPI.equals(huUnitType))
		{
			Services.get(IHandlingUnitsBL.class).getStorageFactory()
					.getStorage(hu)
					.getProductStorages()
					.stream()
					.map(huStorage -> createDocumentView(huStorage))
					.forEach(huViewRecord::addIncludedDocument);
		}
		else
		{
			throw new IllegalStateException("Unknown HU_UnitType=" + huUnitType + " for " + hu);
		}

		return huViewRecord.build();
	}

	private static final String extractPackingInfo(final I_M_HU hu)
	{
		return hu.getM_HU_PI_Version().getName();
	}

	private IDocumentView createDocumentView(final IHUProductStorage huStorage)
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

}
