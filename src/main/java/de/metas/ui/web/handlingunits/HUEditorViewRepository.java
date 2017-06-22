package de.metas.ui.web.handlingunits;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.logging.LogManager;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverters;
import de.metas.ui.web.document.filter.sql.SqlParamsCollector;
import de.metas.ui.web.handlingunits.HUIdsFilterHelper.HUIdsFilterData;
import de.metas.ui.web.handlingunits.util.HUPackingInfoFormatter;
import de.metas.ui.web.handlingunits.util.HUPackingInfos;
import de.metas.ui.web.view.descriptor.SqlViewBinding;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import lombok.Builder;
import lombok.NonNull;

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

public class HUEditorViewRepository
{
	private static final transient Logger logger = LogManager.getLogger(HUEditorViewRepository.class);

	private final WindowId windowId;
	private final String referencingTableName;

	private final HUEditorRowAttributesProvider attributesProvider;

	private final SqlViewBinding sqlViewBinding;

	@Builder
	private HUEditorViewRepository(
			@NonNull final WindowId windowId //
			, final String referencingTableName //
			, final HUEditorRowAttributesProvider attributesProvider //
			, final SqlViewBinding sqlViewBinding)
	{
		super();

		this.windowId = windowId;
		this.referencingTableName = referencingTableName;

		this.attributesProvider = attributesProvider;

		this.sqlViewBinding = sqlViewBinding;
	}

	SqlViewBinding getSqlViewBinding()
	{
		return sqlViewBinding;
	}

	public List<HUEditorRow> retrieveHUEditorRows(final Set<Integer> huIds)
	{
		return retrieveTopLevelHUs(huIds)
				.stream()
				.map(hu -> createHUEditorRow(hu, true))
				.collect(GuavaCollectors.toImmutableList());
	}

	/**
	 * Retrieves the {@link HUEditorRow} hierarchy for given M_HU_ID, even if that M_HU_ID is not in scope.
	 * 
	 * @param huId
	 * @return {@link HUEditorRow} or null if the huId negative or zero.
	 */
	public HUEditorRow retrieveForHUId(final int huId)
	{
		if (huId <= 0)
		{
			return null;
		}

		// TODO: check if the huId is part of our collection

		final I_M_HU hu = InterfaceWrapperHelper.create(Env.getCtx(), huId, I_M_HU.class, ITrx.TRXNAME_None);
		return createHUEditorRow(hu, true);
	}

	private static List<I_M_HU> retrieveTopLevelHUs(final Collection<Integer> huIds)
	{
		if (huIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final IQueryBuilder<I_M_HU> queryBuilder = Services.get(IHandlingUnitsDAO.class)
				.createHUQueryBuilder()
				.setContext(PlainContextAware.newOutOfTrx())
				.setOnlyTopLevelHUs()
				.createQueryBuilder();

		if (huIds != null && !huIds.isEmpty())
		{
			queryBuilder.addInArrayFilter(I_M_HU.COLUMN_M_HU_ID, huIds);
		}

		return queryBuilder
				.create()
				.list();
	}

	private HUEditorRow createHUEditorRow(final I_M_HU hu, final boolean topLevel)
	{
		final boolean aggregatedTU = Services.get(IHandlingUnitsBL.class).isAggregateHU(hu);

		final String huUnitTypeCode = hu.getM_HU_PI_Version().getHU_UnitType();
		final HUEditorRowType huRecordType;
		if (aggregatedTU)
		{
			huRecordType = HUEditorRowType.TU;
		}
		else
		{
			huRecordType = HUEditorRowType.ofHU_UnitType(huUnitTypeCode);
		}
		final String huUnitTypeDisplayName = huRecordType.getName();
		final JSONLookupValue huUnitTypeLookupValue = JSONLookupValue.of(huUnitTypeCode, huUnitTypeDisplayName);

		final JSONLookupValue huStatus = createHUStatusLookupValue(hu);
		final boolean processed = extractProcessed(hu);
		final int huId = hu.getM_HU_ID();

		final HUEditorRow.Builder huEditorRow = HUEditorRow.builder(windowId)
				.setRowId(HUEditorRow.rowIdFromM_HU_ID(huId))
				.setType(huRecordType)
				.setTopLevel(topLevel)
				.setProcessed(processed)
				.setAttributesProvider(attributesProvider)
				//
				.setHUId(huId)
				.setCode(hu.getValue())
				.setHUUnitType(huUnitTypeLookupValue)
				.setHUStatus(huStatus)
				.setPackingInfo(extractPackingInfo(hu, huRecordType));

		//
		// Product/UOM/Qty if there is only one product stored
		final IHUProductStorage singleProductStorage = getSingleProductStorage(hu);
		if (singleProductStorage != null)
		{
			huEditorRow
					.setProduct(createProductLookupValue(singleProductStorage.getM_Product()))
					.setUOM(createUOMLookupValue(singleProductStorage.getC_UOM()))
					.setQtyCU(singleProductStorage.getQty());
		}

		//
		// Included HUs
		if (aggregatedTU)
		{
			final IHUStorageFactory storageFactory = Services.get(IHandlingUnitsBL.class).getStorageFactory();
			storageFactory
					.getStorage(hu)
					.getProductStorages()
					.stream()
					.map(huStorage -> createHUEditorRow(huId, huStorage, processed))
					.forEach(huEditorRow::addIncludedRow);

		}
		else if (X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit.equals(huUnitTypeCode))
		{
			final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
			handlingUnitsDAO.retrieveIncludedHUs(hu)
					.stream()
					.map(includedHU -> createHUEditorRow(includedHU, false))
					.forEach(huEditorRow::addIncludedRow);
		}
		else if (X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit.equals(huUnitTypeCode))
		{
			final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
			final IHUStorageFactory storageFactory = Services.get(IHandlingUnitsBL.class).getStorageFactory();
			handlingUnitsDAO.retrieveIncludedHUs(hu)
					.stream()
					.map(includedVHU -> storageFactory.getStorage(includedVHU))
					.flatMap(vhuStorage -> vhuStorage.getProductStorages().stream())
					.map(vhuProductStorage -> createHUEditorRow(huId, vhuProductStorage, processed))
					.forEach(huEditorRow::addIncludedRow);
		}
		else if (X_M_HU_PI_Version.HU_UNITTYPE_VirtualPI.equals(huUnitTypeCode))
		{
			// do nothing
		}
		else
		{
			throw new HUException("Unknown HU_UnitType=" + huUnitTypeCode + " for " + hu);
		}

		return huEditorRow.build();
	}

	private static final String extractPackingInfo(final I_M_HU hu, final HUEditorRowType huUnitType)
	{
		if (!huUnitType.isPureHU())
		{
			return "";
		}
		if (huUnitType == HUEditorRowType.VHU)
		{
			return "";
		}

		try
		{
			return HUPackingInfoFormatter.newInstance()
					.setShowLU(true)
					.format(HUPackingInfos.of(hu));
		}
		catch (Exception ex)
		{
			logger.warn("Failed extracting packing info for {}", hu, ex);
			return "?";
		}
	}

	private final boolean extractProcessed(final I_M_HU hu)
	{
		//
		// Receipt schedule => consider the HU as processed if is not Planning (FIXME HARDCODED)
		if (I_M_ReceiptSchedule.Table_Name.equals(referencingTableName))
		{
			return !X_M_HU.HUSTATUS_Planning.equals(hu.getHUStatus());
		}
		else
		{
			return false;
		}
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

	private HUEditorRow createHUEditorRow(final int parent_HU_ID, final IHUProductStorage huStorage, final boolean processed)
	{
		final I_M_HU hu = huStorage.getM_HU();
		final int huId = hu.getM_HU_ID();
		final I_M_Product product = huStorage.getM_Product();
		final HUEditorRowAttributesProvider attributesProviderEffective = huId != parent_HU_ID ? attributesProvider : null;

		return HUEditorRow.builder(windowId)
				.setRowId(HUEditorRow.rowIdFromM_HU_Storage(huId, product.getM_Product_ID()))
				.setType(HUEditorRowType.HUStorage)
				.setTopLevel(false)
				.setProcessed(processed)
				.setAttributesProvider(attributesProviderEffective)
				//
				.setHUId(huId)
				// .setCode(hu.getValue()) // NOTE: don't show value on storage level
				.setHUUnitType(JSONLookupValue.of(X_M_HU_PI_Version.HU_UNITTYPE_VirtualPI, "CU"))
				.setHUStatus(createHUStatusLookupValue(hu))
				//
				.setProduct(createProductLookupValue(product))
				.setUOM(createUOMLookupValue(huStorage.getC_UOM()))
				.setQtyCU(huStorage.getQty())
				//
				.build();
	}

	private static JSONLookupValue createHUStatusLookupValue(final I_M_HU hu)
	{
		final String huStatusKey = hu.getHUStatus();
		final String huStatusDisplayName = Services.get(IADReferenceDAO.class).retrieveListNameTrl(IHUEditorRow.HUSTATUS_AD_Reference_ID, huStatusKey);
		return JSONLookupValue.of(huStatusKey, huStatusDisplayName);
	}

	private static JSONLookupValue createProductLookupValue(final I_M_Product product)
	{
		if (product == null)
		{
			return null;
		}

		final String displayName = product.getValue() + "_" + product.getName();
		return JSONLookupValue.of(product.getM_Product_ID(), displayName);
	}

	private static JSONLookupValue createUOMLookupValue(final I_C_UOM uom)
	{
		if (uom == null)
		{
			return null;
		}

		return JSONLookupValue.of(uom.getC_UOM_ID(), uom.getUOMSymbol());
	}

	public List<Integer> retrieveHUIdsEffective(final HUIdsFilterData huIdsFilter, final List<DocumentFilter> filters)
	{
		final ImmutableList<Integer> onlyHUIds = ImmutableList.copyOf(Iterables.concat(huIdsFilter.getInitialHUIds(), huIdsFilter.getMustHUIds()));

		if (filters.isEmpty() && !huIdsFilter.hasInitialHUQuery())
		{
			return onlyHUIds;
		}

		//
		// Create HU query
		IHUQueryBuilder huQuery = huIdsFilter.getInitialHUQueryOrNull();
		if (huQuery == null)
		{
			huQuery = Services.get(IHandlingUnitsDAO.class).createHUQueryBuilder();
		}
		huQuery.setContext(PlainContextAware.newOutOfTrx());

		// Only HUs
		if (!onlyHUIds.isEmpty())
		{
			huQuery.addOnlyHUIds(onlyHUIds);
		}

		// Exclude HUs
		huQuery.addHUIdsToExclude(huIdsFilter.getShallNotHUIds());

		//
		// Convert the "filters" to SQL
		if (!filters.isEmpty())
		{
			final SqlParamsCollector sqlFilterParams = SqlParamsCollector.newInstance();
			final String sqlFilter = SqlDocumentFilterConverters.createEntityBindingEffectiveConverter(sqlViewBinding)
					.getSql(sqlFilterParams, filters);
			huQuery.addFilter(TypedSqlQueryFilter.of(sqlFilter, sqlFilterParams.toList()));
		}

		return huQuery.createQuery().listIds();
	}
}
