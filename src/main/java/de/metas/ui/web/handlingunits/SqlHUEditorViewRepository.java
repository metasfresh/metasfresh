package de.metas.ui.web.handlingunits;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.util.collections.PagedIterator.Page;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.DB;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Locator;
import de.metas.handlingunits.model.I_M_Warehouse;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.logging.LogManager;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverters;
import de.metas.ui.web.handlingunits.HUIdsFilterHelper.HUIdsFilterData;
import de.metas.ui.web.handlingunits.util.HUPackingInfoFormatter;
import de.metas.ui.web.handlingunits.util.HUPackingInfos;
import de.metas.ui.web.view.SqlViewRowIdsOrderedSelectionFactory;
import de.metas.ui.web.view.ViewEvaluationCtx;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewRowIdsOrderedSelection;
import de.metas.ui.web.view.ViewRowIdsOrderedSelectionFactory;
import de.metas.ui.web.view.descriptor.SqlAndParams;
import de.metas.ui.web.view.descriptor.SqlViewBinding;
import de.metas.ui.web.view.descriptor.SqlViewRowIdsConverter;
import de.metas.ui.web.view.descriptor.SqlViewSelectData;
import de.metas.ui.web.view.descriptor.SqlViewSelectionQueryBuilder;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.ui.web.window.model.sql.SqlOptions;
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

public class SqlHUEditorViewRepository implements HUEditorViewRepository
{
	private static final transient Logger logger = LogManager.getLogger(SqlHUEditorViewRepository.class);

	private final WindowId windowId;

	private final HUEditorRowAttributesProvider attributesProvider;
	private final HUEditorRowIsProcessedPredicate rowProcessedPredicate;
	private final boolean showBestBeforeDate;
	private final boolean showLocator;

	private final SqlViewBinding sqlViewBinding;
	private final ViewRowIdsOrderedSelectionFactory viewSelectionFactory;
	private final SqlViewSelectData sqlViewSelect;

	@Builder
	private SqlHUEditorViewRepository(
			@NonNull final WindowId windowId,
			@NonNull final SqlViewBinding sqlViewBinding,
			@Nullable final HUEditorRowAttributesProvider attributesProvider,
			@Nullable final HUEditorRowIsProcessedPredicate rowProcessedPredicate,
			final boolean showBestBeforeDate,
			final boolean showLocator)
	{
		this.windowId = windowId;

		this.attributesProvider = attributesProvider;
		this.rowProcessedPredicate = rowProcessedPredicate != null ? rowProcessedPredicate : HUEditorRowIsProcessedPredicates.NEVER;
		this.showBestBeforeDate = showBestBeforeDate;
		this.showLocator = showLocator;

		this.sqlViewBinding = sqlViewBinding;
		viewSelectionFactory = SqlViewRowIdsOrderedSelectionFactory.of(sqlViewBinding);
		sqlViewSelect = sqlViewBinding.getSqlViewSelect();
	}

	@Override
	public void invalidateCache()
	{
		if (attributesProvider != null)
		{
			attributesProvider.invalidateAll();
		}
	}

	private SqlViewRowIdsConverter getRowIdsConverter()
	{
		return sqlViewBinding.getRowIdsConverter();
	}

	@Override
	public List<HUEditorRow> retrieveHUEditorRows(@NonNull final Set<Integer> huIds, @NonNull final HUEditorRowFilter filter)
	{
		final int topLevelHUId = -1;
		return retrieveTopLevelHUs(huIds, filter)
				.stream()
				.map(hu -> createHUEditorRow(hu, topLevelHUId))
				.collect(GuavaCollectors.toImmutableList());
	}

	@Override
	public HUEditorRow retrieveForHUId(final int huId)
	{
		if (huId <= 0)
		{
			return null;
		}

		// TODO: check if the huId is part of our collection

		final I_M_HU hu = loadOutOfTrx(huId, I_M_HU.class);
		final int topLevelHUId = -1; // assume given huId is a top level HU
		return createHUEditorRow(hu, topLevelHUId);
	}

	private static List<I_M_HU> retrieveTopLevelHUs(@NonNull final Collection<Integer> huIds, @NonNull final HUEditorRowFilter filter)
	{
		if (huIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final IQueryBuilder<I_M_HU> queryBuilder = HUEditorRowFilters.toHUQueryBuilderPart(filter)
				.setContext(PlainContextAware.newOutOfTrx())
				.setOnlyTopLevelHUs()
				.setOnlyActiveHUs(false) // retrieve ALL HUs, see https://github.com/metasfresh/metasfresh-webui-api/issues/563
				.createQueryBuilder();

		if (huIds != null && !huIds.isEmpty())
		{
			queryBuilder.addInArrayFilter(I_M_HU.COLUMN_M_HU_ID, huIds);
		}

		return queryBuilder
				.create()
				.list();
	}

	private HUEditorRow createHUEditorRow(
			@NonNull final I_M_HU hu,
			final int topLevelHUId)
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
		final boolean processed = rowProcessedPredicate.isProcessed(hu);
		final int huId = hu.getM_HU_ID();
		final HUEditorRowId rowId = HUEditorRowId.ofHU(huId, topLevelHUId);

		final HUEditorRow.Builder huEditorRow = HUEditorRow.builder(windowId)
				.setRowId(rowId)
				.setType(huRecordType)
				.setTopLevel(topLevelHUId <= 0)
				.setProcessed(processed)
				.setBPartnerId(hu.getC_BPartner_ID())
				.setAttributesProvider(attributesProvider)
				//
				.setCode(hu.getValue())
				.setHUUnitType(huUnitTypeLookupValue)
				.setHUStatus(huStatus)
				.setPackingInfo(extractPackingInfo(hu, huRecordType));

		//
		// Acquire Best Before Date if required
		if (showBestBeforeDate)
		{
			huEditorRow.setBestBeforeDate(extractBestBeforeDate(attributesProvider, rowId));
		}

		//
		// Locator
		if (showLocator)
		{
			huEditorRow.setLocator(createLocatorLookupValue(hu.getM_Locator_ID()));
		}

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
		final int topLevelHUIdEffective = topLevelHUId > 0 ? topLevelHUId : huId;
		if (aggregatedTU)
		{
			final IHUStorageFactory storageFactory = Services.get(IHandlingUnitsBL.class).getStorageFactory();
			storageFactory
					.getStorage(hu)
					.getProductStorages()
					.stream()
					.map(huStorage -> createHUEditorRow(huId, topLevelHUIdEffective, huStorage, processed))
					.forEach(huEditorRow::addIncludedRow);

		}
		else if (X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit.equals(huUnitTypeCode))
		{
			final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
			handlingUnitsDAO.retrieveIncludedHUs(hu)
					.stream()
					.map(includedHU -> createHUEditorRow(includedHU, topLevelHUIdEffective))
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
					.map(vhuProductStorage -> createHUEditorRow(huId, topLevelHUIdEffective, vhuProductStorage, processed))
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
		catch (final Exception ex)
		{
			logger.warn("Failed extracting packing info for {}", hu, ex);
			return "?";
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

	private HUEditorRow createHUEditorRow(
			final int parent_HU_ID,
			final int topLevelHUId,
			@NonNull final IHUProductStorage huStorage,
			final boolean processed)
	{
		final I_M_HU hu = huStorage.getM_HU();
		final int huId = hu.getM_HU_ID();
		final I_M_Product product = huStorage.getM_Product();
		final HUEditorRowAttributesProvider attributesProviderEffective = huId != parent_HU_ID ? attributesProvider : null;

		return HUEditorRow.builder(windowId)
				.setRowId(HUEditorRowId.ofHUStorage(huId, topLevelHUId, product.getM_Product_ID()))
				.setType(HUEditorRowType.HUStorage)
				.setTopLevel(false)
				.setProcessed(processed)
				.setAttributesProvider(attributesProviderEffective)
				//
				// .setHUId(huId)
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
		final String huStatusDisplayName = Services.get(IADReferenceDAO.class).retrieveListNameTrl(HUEditorRow.HUSTATUS_AD_Reference_ID, huStatusKey);
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

	private static Date extractBestBeforeDate(final HUEditorRowAttributesProvider attributesProvider, final HUEditorRowId rowId)
	{
		if (attributesProvider == null)
		{
			return null;
		}

		final DocumentId attributesKey = attributesProvider.createAttributeKey(rowId.getHuId());
		final HUEditorRowAttributes attributes = attributesProvider.getAttributes(rowId.toDocumentId(), attributesKey);
		return attributes.getBestBeforeDate().orElse(null);
	}

	private static JSONLookupValue createLocatorLookupValue(final int locatorId)
	{
		if (locatorId <= 0)
		{
			return null;
		}

		final I_M_Locator locator = loadOutOfTrx(locatorId, I_M_Locator.class);
		if (locator == null)
		{
			return JSONLookupValue.unknown(locatorId);
		}

		final I_M_Warehouse warehouse = loadOutOfTrx(locator.getM_Warehouse_ID(), I_M_Warehouse.class);

		final String caption = Stream.of(warehouse.getName(), locator.getValue(), locator.getX(), locator.getX1(), locator.getY(), locator.getZ())
				.filter(part -> !Check.isEmpty(part, true))
				.map(String::trim)
				.collect(Collectors.joining("_"));

		return JSONLookupValue.of(locatorId, caption);
	}

	@Override
	public List<Integer> retrieveHUIdsEffective(
			@NonNull final HUIdsFilterData huIdsFilter,
			@NonNull final List<DocumentFilter> filters)
	{
		final ImmutableList<Integer> onlyHUIds = extractHUIds(huIdsFilter);

		if (filters.isEmpty() && !huIdsFilter.hasInitialHUQuery() && onlyHUIds != null)
		{
			// shortcut: don't bother the DB but return the list of IDs that we already have
			return onlyHUIds;
		}

		// Create HU query
		IHUQueryBuilder huQuery = huIdsFilter.getInitialHUQueryOrNull();
		if (huQuery == null)
		{
			huQuery = Services.get(IHandlingUnitsDAO.class).createHUQueryBuilder();
		}
		huQuery.setContext(PlainContextAware.newOutOfTrx());

		// Only HUs
		if (onlyHUIds != null)
		{
			huQuery.addOnlyHUIds(onlyHUIds);
		}

		// Exclude HUs
		huQuery.addHUIdsToExclude(huIdsFilter.getShallNotHUIds());

		//
		// Convert the "filters" to SQL
		if (!filters.isEmpty())
		{
			final SqlDocumentFilterConverter sqlFilterConverter = SqlDocumentFilterConverters.createEntityBindingEffectiveConverter(sqlViewBinding);
			huQuery.addFilter(sqlFilterConverter.createQueryFilter(filters, SqlOptions.usingTableAlias(sqlViewBinding.getTableAlias())));
		}

		return huQuery.createQuery().listIds();
	}

	private static ImmutableList<Integer> extractHUIds(@NonNull final HUIdsFilterData huIdsFilter)
	{
		final Set<Integer> initialHUIds = huIdsFilter.getInitialHUIds();
		final Set<Integer> huIdsToInclude = huIdsFilter.getMustHUIds();
		final Set<Integer> huIdsToExclude = huIdsFilter.getShallNotHUIds();

		if (initialHUIds == null && huIdsToInclude.isEmpty() && huIdsToExclude.isEmpty())
		{
			return null; // no restrictions
		}

		final Set<Integer> initialHUIdsOrEmpty = initialHUIds != null ? initialHUIds : ImmutableSet.of();

		return Stream.concat(initialHUIdsOrEmpty.stream(), huIdsToInclude.stream())
				.filter(huId -> !huIdsToExclude.contains(huId))
				.distinct()
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public Page<Integer> retrieveHUIdsPage(final ViewEvaluationCtx viewEvalCtx, final ViewRowIdsOrderedSelection selection, final int firstRow, final int maxRows)
	{
		final SqlAndParams sqlAndParams = sqlViewSelect.selectByPage()
				.viewEvalCtx(viewEvalCtx)
				.viewId(selection.getViewId())
				.firstRowZeroBased(firstRow)
				.pageLength(maxRows)
				.build();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sqlAndParams.getSql(), ITrx.TRXNAME_ThreadInherited);
			pstmt.setMaxRows(maxRows);
			DB.setParameters(pstmt, sqlAndParams.getSqlParams());

			rs = pstmt.executeQuery();

			final Set<Integer> huIds = new LinkedHashSet<>();
			int lastRowMax = -1;
			while (rs.next())
			{
				final int huId = rs.getInt(I_M_HU.COLUMNNAME_M_HU_ID);
				if (huId <= 0)
				{
					continue;
				}
				huIds.add(huId);

				final int lastRow = rs.getInt(SqlViewSelectData.COLUMNNAME_Paging_SeqNo_OneBased);
				lastRowMax = Math.max(lastRowMax, lastRow);
			}

			if (huIds.isEmpty())
			{
				// shall not happen
				return null;
			}
			else
			{
				final int lastRowZeroBased = lastRowMax - 1;
				return Page.ofRowsAndLastRowIndex(ImmutableList.copyOf(huIds), lastRowZeroBased);
			}
		}
		catch (final SQLException ex)
		{
			throw DBException.wrapIfNeeded(ex)
					.setSqlIfAbsent(sqlAndParams.getSql(), sqlAndParams.getSqlParams());
		}
		finally
		{
			DB.close(rs, pstmt);
		}

	}

	@Override
	public ViewRowIdsOrderedSelection createSelection(
			@NonNull final ViewEvaluationCtx viewEvalCtx,
			final ViewId viewId,
			final List<DocumentFilter> filters,
			final List<DocumentQueryOrderBy> orderBys)
	{
		return viewSelectionFactory.createOrderedSelection(viewEvalCtx, viewId, filters, orderBys);
	}

	@Override
	public ViewRowIdsOrderedSelection createSelectionFromSelection(
			@NonNull final ViewEvaluationCtx viewEvalCtx,
			final ViewRowIdsOrderedSelection fromSelection,
			final List<DocumentQueryOrderBy> orderBys)
	{
		return viewSelectionFactory.createOrderedSelectionFromSelection(viewEvalCtx, fromSelection, orderBys);
	}

	@Override
	public ViewRowIdsOrderedSelection addRowIdsToSelection(final ViewRowIdsOrderedSelection selection, final DocumentIdsSelection rowIdsToAdd)
	{
		return viewSelectionFactory.addRowIdsToSelection(selection, rowIdsToAdd);
	}

	@Override
	public ViewRowIdsOrderedSelection removeRowIdsFromSelection(final ViewRowIdsOrderedSelection selection, final DocumentIdsSelection rowIdsToRemove)
	{
		return viewSelectionFactory.removeRowIdsFromSelection(selection, rowIdsToRemove);
	}

	@Override
	public boolean containsAnyOfRowIds(final ViewRowIdsOrderedSelection selection, final DocumentIdsSelection rowIds)
	{
		return viewSelectionFactory.containsAnyOfRowIds(selection, rowIds);
	}

	@Override
	public void deleteSelection(final ViewRowIdsOrderedSelection selection)
	{
		viewSelectionFactory.deleteSelection(selection.getViewId());
	}

	@Override
	public Set<Integer> convertToRecordIds(final DocumentIdsSelection rowIds)
	{
		return getRowIdsConverter().convertToRecordIds(rowIds);
	}

	@Override
	public String buildSqlWhereClause(final ViewRowIdsOrderedSelection selection, final DocumentIdsSelection rowIds)
	{
		final String sqlKeyColumnNameFK = I_M_HU.Table_Name + "." + I_M_HU.COLUMNNAME_M_HU_ID;
		final String selectionId = selection.getSelectionId();
		return SqlViewSelectionQueryBuilder.buildSqlWhereClause(sqlKeyColumnNameFK, selectionId, rowIds, getRowIdsConverter());
	}

}
