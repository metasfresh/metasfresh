package de.metas.ui.web.handlingunits;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.IHUDisplayNameBuilder;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.view.DocumentView;
import de.metas.ui.web.view.DocumentViewAttributesProviderFactory;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
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

	public List<HUDocumentView> retrieveDocumentViews()
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

	private HUDocumentView createDocumentView(final I_M_HU hu)
	{
		final String huUnitTypeCode = hu.getM_HU_PI_Version().getHU_UnitType();

		final String huUnitTypeDisplayName;
		final HUDocumentViewType huRecordType;
		final boolean aggregateHU = Services.get(IHandlingUnitsBL.class).isAggregateHU(hu);
		if (aggregateHU)
		{
			huUnitTypeDisplayName = "TU";
			huRecordType = HUDocumentViewType.TU;
		}
		else
		{
			huUnitTypeDisplayName = huUnitTypeCode;
			huRecordType = HUDocumentViewType.ofHU_UnitType(huUnitTypeCode);
		}
		final JSONLookupValue huUnitTypeLookupValue = JSONLookupValue.of(huUnitTypeCode, huUnitTypeDisplayName);

		final String huStatusKey = hu.getHUStatus();
		final String huStatusDisplayName = Services.get(IADReferenceDAO.class).retriveListName(Env.getCtx(), I_WEBUI_HU_View.HUSTATUS_AD_Reference_ID, huStatusKey);
		final JSONLookupValue huStatus = JSONLookupValue.of(huStatusKey, huStatusDisplayName);

		final DocumentView.Builder huViewRecord = DocumentView.builder(adWindowId)
				.setIdFieldName(I_WEBUI_HU_View.COLUMNNAME_M_HU_ID)
				.setType(huRecordType)
				.setAttributesProvider(getAttributesProvider())
				//
				.putFieldValue(I_WEBUI_HU_View.COLUMNNAME_M_HU_ID, hu.getM_HU_ID())
				.putFieldValue(I_WEBUI_HU_View.COLUMNNAME_Value, hu.getValue())
				.putFieldValue(I_WEBUI_HU_View.COLUMNNAME_HU_UnitType, huUnitTypeLookupValue)
				.putFieldValue(I_WEBUI_HU_View.COLUMNNAME_HUStatus, huStatus)
				.putFieldValue(I_WEBUI_HU_View.COLUMNNAME_PackingInfo, extractPackingInfo(hu));

		//
		// Product/UOM/Qty if there is only one product stored
		final IHUProductStorage singleProductStorage = getSingleProductStorage(hu);
		if (singleProductStorage != null)
		{
			huViewRecord
					.putFieldValue(I_WEBUI_HU_View.COLUMNNAME_M_Product_ID, createLookupValue(singleProductStorage.getM_Product()))
					.putFieldValue(I_WEBUI_HU_View.COLUMNNAME_C_UOM_ID, createLookupValue(singleProductStorage.getC_UOM()))
					.putFieldValue(I_WEBUI_HU_View.COLUMNNAME_QtyCU, singleProductStorage.getQty());
		}

		//
		// Included HUs
		if (aggregateHU)
		{
			streamProductStorageDocumentViews(hu)
					.forEach(huViewRecord::addIncludedDocument);
		}
		else if (X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit.equals(huUnitTypeCode))
		{
			Services.get(IHandlingUnitsDAO.class).retrieveIncludedHUs(hu)
					.stream()
					.map(includedHU -> createDocumentView(includedHU))
					.forEach(huViewRecord::addIncludedDocument);
		}
		else if (X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit.equals(huUnitTypeCode)
				|| X_M_HU_PI_Version.HU_UNITTYPE_VirtualPI.equals(huUnitTypeCode))
		{
			streamProductStorageDocumentViews(hu)
					.forEach(huViewRecord::addIncludedDocument);
		}
		else
		{
			throw new HUException("Unknown HU_UnitType=" + huUnitTypeCode + " for " + hu);
		}

		return HUDocumentView.of(huViewRecord.build());
	}

	private static final String extractPackingInfo(final I_M_HU hu)
	{
		final IHUDisplayNameBuilder helper = Services.get(IHandlingUnitsBL.class).buildDisplayName(hu)
				.setShowIncludedHUCount(true);

		final StringBuilder packingInfo = new StringBuilder();

		final String piName = helper.getPIName();
		packingInfo.append(Check.isEmpty(piName, true) ? "?" : piName);

		final int includedHUsCount = helper.getIncludedHUsCount();
		if (includedHUsCount > 0)
		{
			packingInfo.append(" x ").append(includedHUsCount);
		}

		return packingInfo.toString();
	}

	private Stream<HUDocumentView> streamProductStorageDocumentViews(final I_M_HU hu)
	{
		return Services.get(IHandlingUnitsBL.class).getStorageFactory()
				.getStorage(hu)
				.getProductStorages()
				.stream()
				.map(huStorage -> createDocumentView(huStorage));
	}

	private IHUProductStorage getSingleProductStorage(final I_M_HU hu)
	{
		final IHUStorage huStorage = Services.get(IHandlingUnitsBL.class).getStorageFactory()
				.getStorage(hu);

		final I_M_Product product = huStorage.getSingleProductOrNull();
		if (product == null)
		{
			return null;
		}

		final IHUProductStorage productStorage = huStorage.getProductStorage(product);
		return productStorage;
	}

	private HUDocumentView createDocumentView(final IHUProductStorage huStorage)
	{
		final I_M_HU hu = huStorage.getM_HU();
		final I_M_Product product = huStorage.getM_Product();

		final JSONLookupValue huUnitTypeLookupValue = JSONLookupValue.of(X_M_HU_PI_Version.HU_UNITTYPE_VirtualPI, "CU");

		final DocumentView storageDocument = DocumentView.builder(adWindowId)
				.setDocumentId(DocumentId.ofString(I_M_HU_Storage.Table_Name + "_" + hu.getM_HU_ID() + "_" + product.getM_Product_ID()))
				.setIdFieldName(null) // N/A
				.setType(HUDocumentViewType.HUStorage)
				//
				.putFieldValue(I_WEBUI_HU_View.COLUMNNAME_M_HU_ID, hu.getM_HU_ID())
				//.putFieldValue(I_WEBUI_HU_View.COLUMNNAME_Value, hu.getValue()) // NOTE: don't show value on storage level
				.putFieldValue(I_WEBUI_HU_View.COLUMNNAME_HU_UnitType, huUnitTypeLookupValue)
				//
				.putFieldValue(I_WEBUI_HU_View.COLUMNNAME_M_Product_ID, createLookupValue(product))
				.putFieldValue(I_WEBUI_HU_View.COLUMNNAME_C_UOM_ID, createLookupValue(huStorage.getC_UOM()))
				.putFieldValue(I_WEBUI_HU_View.COLUMNNAME_QtyCU, huStorage.getQty())
				//
				.build();
		return HUDocumentView.of(storageDocument);
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
