package org.adempiere.warehouse.api.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import de.metas.cache.CCache;
import de.metas.cache.annotation.CacheCtx;
import de.metas.i18n.ITranslatableString;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.proxy.Cached;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.WarehousePickingGroup;
import org.adempiere.warehouse.WarehousePickingGroupId;
import org.adempiere.warehouse.WarehouseType;
import org.adempiere.warehouse.WarehouseTypeId;
import org.adempiere.warehouse.api.CreateOrUpdateLocatorRequest;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_M_Warehouse_PickingGroup;
import org.compiere.model.I_M_Warehouse_Type;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_M_Warehouse_Routing;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import static de.metas.util.Check.isEmpty;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadByIdsOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.loadByRepoIdAwaresOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

public class WarehouseDAO implements IWarehouseDAO
{
	private static final Logger logger = LogManager.getLogger(WarehouseDAO.class);

	private final CCache<WarehouseId, ImmutableList<LocatorId>> locatorIdsByWarehouseId = CCache.newCache(I_M_Locator.Table_Name + "#by#M_Warehouse_ID", 10, CCache.EXPIREMINUTES_Never);
	private final CCache<Integer, WarehouseRoutingsIndex> allWarehouseRoutings = CCache.newCache(I_M_Warehouse_Routing.Table_Name, 1, CCache.EXPIREMINUTES_Never);
	private final CCache<Integer, WarehouseTypesIndex> allWarehouseTypes = CCache.newCache(I_M_Warehouse_Type.Table_Name, 1, CCache.EXPIREMINUTES_Never);

	@Override
	public I_M_Warehouse getById(@NonNull final WarehouseId warehouseId)
	{
		return getById(warehouseId, I_M_Warehouse.class);
	}

	@Override
	public <T extends I_M_Warehouse> T getById(@NonNull final WarehouseId warehouseId, @NonNull final Class<T> modelType)
	{
		final T outOfTrxWarehouseRecord = loadOutOfTrx(warehouseId, modelType);
		if (outOfTrxWarehouseRecord != null)
		{
			return outOfTrxWarehouseRecord; // with is almost always the case
		}

		return load(warehouseId, modelType); // this fallback is needed if the WH was just created itself, within this very trx
	}

	@Override
	public List<I_M_Warehouse> getByIds(final Collection<WarehouseId> warehouseIds)
	{
		return getByIds(warehouseIds, I_M_Warehouse.class);
	}

	@Override
	public <T extends I_M_Warehouse> List<T> getByIds(final Collection<WarehouseId> warehouseIds, final Class<T> modelType)
	{
		return loadByRepoIdAwaresOutOfTrx(warehouseIds, modelType);
	}

	@Override
	public boolean isAllowAnyDocType(final WarehouseId warehouseId)
	{
		return getWarehouseRoutings().isAllowAnyDocType(warehouseId);
	}

	private WarehouseRoutingsIndex getWarehouseRoutings()
	{
		return allWarehouseRoutings.getOrLoad(0, this::retrieveWarehouseRoutingIndex);
	}

	private WarehouseRoutingsIndex retrieveWarehouseRoutingIndex()
	{
		final List<WarehouseRouting> routings = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_M_Warehouse_Routing.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream(I_M_Warehouse_Routing.class)
				.map(WarehouseDAO::toWarehouseRouting)
				.collect(ImmutableList.toImmutableList());

		return WarehouseRoutingsIndex.of(routings);
	}

	private static WarehouseRouting toWarehouseRouting(final I_M_Warehouse_Routing record)
	{
		return WarehouseRouting.builder()
				.warehouseId(WarehouseId.ofRepoId(record.getM_Warehouse_ID()))
				.docBaseType(record.getDocBaseType())
				.build();
	}

	@Override
	public boolean isDocTypeAllowed(@NonNull final WarehouseId warehouseId, @NonNull final String docBaseType)
	{
		return getWarehouseRoutings().isDocTypeAllowed(warehouseId, docBaseType);
	}

	@Override
	public I_M_Locator getOrCreateLocatorByCoordinates(@NonNull final WarehouseId warehouseId, final String value, final String x, final String y, final String z)
	{
		final I_M_Locator existingLocator = getLocators(warehouseId)
				.stream()
				.filter(locator -> isLocatorMatchingCoordinates(locator, x, y, z))
				.findFirst()
				.orElse(null);

		if (existingLocator != null)
		{
			return existingLocator;
		}

		final I_M_Warehouse warehouse = getById(warehouseId);
		final I_M_Locator newLocator = newInstanceOutOfTrx(I_M_Locator.class);
		newLocator.setAD_Org_ID(warehouse.getAD_Org_ID());
		newLocator.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());
		newLocator.setValue(value);
		newLocator.setX(x);
		newLocator.setY(y);
		newLocator.setZ(z);
		saveRecord(newLocator);
		return newLocator;
	}

	private static boolean isLocatorMatchingCoordinates(final I_M_Locator locator, final String x, final String y, final String z)
	{
		return locator.isActive()
				&& Objects.equals(x, locator.getX())
				&& Objects.equals(y, locator.getY())
				&& Objects.equals(z, locator.getZ());
	}

	@Override
	public LocatorId createDefaultLocator(@NonNull final WarehouseId warehouseId)
	{
		final I_M_Warehouse warehouse = getById(warehouseId);
		final I_M_Locator locatorNew = newInstance(I_M_Locator.class, warehouse);

		locatorNew.setAD_Org_ID(warehouse.getAD_Org_ID());
		locatorNew.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());
		locatorNew.setValue("Standard");
		locatorNew.setX("0");
		locatorNew.setY("0");
		locatorNew.setZ("0");
		locatorNew.setIsDefault(true);
		saveRecord(locatorNew);
		if (logger.isInfoEnabled())
		{
			logger.info("Created default locator for " + warehouse.getName());
		}

		return LocatorId.ofRecordOrNull(locatorNew);
	}

	@Override
	public WarehouseId getWarehouseIdByValue(@NonNull final String value)
	{
		final WarehouseId warehouseId = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_M_Warehouse.class)
				.addEqualsFilter(I_M_Warehouse.COLUMN_Value, value)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstIdOnly(WarehouseId::ofRepoIdOrNull);

		if (warehouseId == null)
		{
			throw new AdempiereException("@NotFound@ @M_Warehouse_ID@")
					.setParameter("value", value);
		}

		return warehouseId;
	}

	@Override
	public WarehouseId getWarehouseIdByLocatorRepoId(final int locatorId)
	{
		final I_M_Locator locator = getLocatorByRepoId(locatorId);
		if (locator == null)
		{
			return null;
		}

		return WarehouseId.ofRepoId(locator.getM_Warehouse_ID());
	}

	@Override
	public Set<WarehouseId> getWarehouseIdsForLocatorRepoIds(@NonNull final Set<Integer> locatorRepoIds)
	{
		if (locatorRepoIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		return getLocatorsByRepoIds(locatorRepoIds)
				.stream()
				.map(I_M_Locator::getM_Warehouse_ID)
				.map(WarehouseId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	public List<I_M_Locator> getLocatorByIds(final Collection<LocatorId> locatorIds)
	{
		return getLocatorByIds(locatorIds, I_M_Locator.class);
	}

	public <T extends I_M_Locator> List<T> getLocatorByIds(final Collection<LocatorId> locatorIds, final Class<T> modelType)
	{
		return loadByRepoIdAwaresOutOfTrx(locatorIds, modelType);
	}

	@Override
	public I_M_Locator getLocatorByRepoId(final int locatorId)
	{
		return loadOutOfTrx(locatorId, I_M_Locator.class);
	}

	@Override
	public LocatorId getLocatorIdByRepoIdOrNull(final int locatorId)
	{
		if (locatorId <= 0)
		{
			return null;
		}
		return getLocatorIdByRepoId(locatorId);
	}

	@Override
	public LocatorId getLocatorIdByRepoId(final int locatorId)
	{
		final I_M_Locator locator = getLocatorByRepoId(locatorId);
		return LocatorId.ofRecord(locator);
	}

	private List<I_M_Locator> getLocatorsByRepoIds(final Set<Integer> locatorIds)
	{
		return loadByIdsOutOfTrx(locatorIds, I_M_Locator.class);
	}

	@Override
	public I_M_Locator getLocatorById(@NonNull final LocatorId locatorId)
	{
		return getLocatorById(locatorId, I_M_Locator.class);
	}

	@Override
	public <T extends I_M_Locator> T getLocatorById(@NonNull final LocatorId locatorId, @NonNull Class<T> modelClass)
	{
		return loadOutOfTrx(locatorId, modelClass);
	}

	@Override
	public <T extends I_M_Locator> T getLocatorByIdInTrx(@NonNull final LocatorId locatorId, @NonNull Class<T> modelClass)
	{
		return load(locatorId, modelClass);
	}

	@Override
	public List<I_M_Locator> getLocators(@NonNull final WarehouseId warehouseId)
	{
		final List<LocatorId> locatorIds = getLocatorIds(warehouseId);
		return getLocatorByIds(locatorIds);
	}

	@Override
	public <T extends I_M_Locator> List<T> getLocators(@NonNull final WarehouseId warehouseId, final Class<T> modelType)
	{
		final List<LocatorId> locatorIds = getLocatorIds(warehouseId);
		return getLocatorByIds(locatorIds, modelType);
	}

	@Override
	public List<LocatorId> getLocatorIds(@NonNull final WarehouseId warehouseId)
	{
		return locatorIdsByWarehouseId.getOrLoad(warehouseId, this::retrieveLocatorIds);
	}

	private ImmutableList<LocatorId> retrieveLocatorIds(final WarehouseId warehouseId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_M_Locator.class)
				.addEqualsFilter(I_M_Locator.COLUMN_M_Warehouse_ID, warehouseId)
				.orderBy(I_M_Locator.COLUMN_X)
				.orderBy(I_M_Locator.COLUMN_Y)
				.orderBy(I_M_Locator.COLUMN_Z)
				.orderBy(I_M_Locator.COLUMN_M_Locator_ID)
				.create()
				.listIds()
				.stream()
				.map(locatorRepoId -> LocatorId.ofRepoId(warehouseId, locatorRepoId))
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public List<I_M_Warehouse> getWarehousesAllowedForDocBaseType(@NonNull final String docBaseType)
	{
		final Set<WarehouseId> allWarehouseIds = getAllWarehouseIds();
		final Set<WarehouseId> warehouseIds = getWarehouseRoutings().getWarehouseIdsAllowedForDocType(allWarehouseIds, docBaseType);
		return getByIds(warehouseIds);
	}

	@Override
	public WarehouseId getInTransitWarehouseId(@NonNull final OrgId adOrgId)
	{
		return getInTransitWarehouseIdIfExists(adOrgId)
				.orElseThrow(() -> new AdempiereException("@NotFound@ @M_Warehouse_ID@ (@IsInTransit@, @AD_Org_ID@:" + adOrgId.getRepoId() + ")"));
	}

	@Override
	@Cached(cacheName = I_M_Warehouse.Table_Name + "#InTransitForOrg")
	public Optional<WarehouseId> getInTransitWarehouseIdIfExists(@NonNull final OrgId adOrgId)
	{
		final WarehouseId warehouseId = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_M_Warehouse.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Warehouse.COLUMNNAME_AD_Org_ID, adOrgId)
				.addEqualsFilter(I_M_Warehouse.COLUMNNAME_IsInTransit, true)
				.orderBy(I_M_Warehouse.COLUMNNAME_M_Warehouse_ID)
				.create()
				.firstId(WarehouseId::ofRepoIdOrNull);

		return Optional.ofNullable(warehouseId);
	}

	@Override
	public List<I_M_Warehouse> getByOrgId(@NonNull final OrgId orgId)
	{
		final Set<WarehouseId> warehouseIds = getWarehouseIdsByOrgId(orgId);

		return getByIds(warehouseIds);
	}

	private Set<WarehouseId> getWarehouseIdsByOrgId(@NonNull final OrgId orgId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_M_Warehouse.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Warehouse.COLUMNNAME_AD_Org_ID, orgId)
				.orderBy(I_M_Warehouse.COLUMNNAME_M_Warehouse_ID)
				.create()
				.listIds(WarehouseId::ofRepoId);
	}

	@Override
	public List<I_M_Warehouse> getAllWarehouses()
	{
		final Set<WarehouseId> warehouseIds = getAllWarehouseIds();
		return getByIds(warehouseIds);
	}

	private Set<WarehouseId> getAllWarehouseIds()
	{
		final Set<WarehouseId> warehouseIds = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_M_Warehouse.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.listIds(WarehouseId::ofRepoId);
		return warehouseIds;
	}

	// TODO: implement a way to reset a cache when I_M_Warehouse_PickingGroup is changed
	@Cached(cacheName = I_M_Warehouse.Table_Name)
	public WarehousePickingGroupsIndex retrieveWarehouseGroups()
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final ImmutableSetMultimap<Integer, WarehouseId> warehouseIdsByPickingGroupId = queryBL.createQueryBuilder(I_M_Warehouse.class)
				.addOnlyActiveRecordsFilter()
				.addNotNull(I_M_Warehouse.COLUMNNAME_M_Warehouse_PickingGroup_ID)
				.create()
				.listDistinct(I_M_Warehouse.COLUMNNAME_M_Warehouse_ID, I_M_Warehouse.COLUMNNAME_M_Warehouse_PickingGroup_ID)
				.stream()
				.map(record -> {
					final WarehouseId warehouseId = WarehouseId.ofRepoId((int)record.get(I_M_Warehouse.COLUMNNAME_M_Warehouse_ID));
					final int warehousePickingGroupId = (int)record.get(I_M_Warehouse.COLUMNNAME_M_Warehouse_PickingGroup_ID);
					return GuavaCollectors.entry(warehousePickingGroupId, warehouseId);
				})
				.collect(GuavaCollectors.toImmutableSetMultimap());

		final List<WarehousePickingGroup> groups = queryBL.createQueryBuilder(I_M_Warehouse_PickingGroup.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(warehousePickingGroupPO -> WarehousePickingGroup.builder()
						.id(WarehousePickingGroupId.ofRepoId(warehousePickingGroupPO.getM_Warehouse_PickingGroup_ID()))
						.name(warehousePickingGroupPO.getName())
						.description(warehousePickingGroupPO.getDescription())
						.warehouseIds(warehouseIdsByPickingGroupId.get(warehousePickingGroupPO.getM_Warehouse_PickingGroup_ID()))
						.build())
				.collect(ImmutableList.toImmutableList());

		return WarehousePickingGroupsIndex.of(groups);
	}

	@Override
	public WarehousePickingGroup getWarehousePickingGroupById(@NonNull final WarehousePickingGroupId warehousePickingGroupId)
	{
		return retrieveWarehouseGroups().getById(warehousePickingGroupId);
	}

	@Override
	public Set<WarehouseId> getWarehouseIdsOfSamePickingGroup(@NonNull final WarehouseId warehouseId)
	{
		return retrieveWarehouseGroups().getWarehouseIdsOfSamePickingGroup(warehouseId);
	}

	@Override
	public int retrieveLocatorIdByBarcode(final String barcode)
	{
		if (Check.isEmpty(barcode, true))
		{
			throw new AdempiereException("Invalid locator barcode: " + barcode);
		}

		try
		{
			final int locatorId = Integer.parseInt(barcode);
			Check.assume(locatorId > 0, "locatorId > 0");
			return locatorId;
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Invalid locator barcode: " + barcode, ex);
		}
	}

	@Override
	public OrgId retrieveOrgIdByLocatorId(final int locatorId)
	{
		final String sql = "SELECT AD_Org_ID FROM M_Locator WHERE M_Locator_ID=?";
		final int orgIdInt = DB.getSQLValueEx(ITrx.TRXNAME_ThreadInherited, sql, locatorId);
		if (orgIdInt < 0)
		{
			throw new AdempiereException("No Org found for locatorId=" + locatorId);
		}
		return OrgId.ofRepoId(orgIdInt);
	}

	@Override
	public LocatorId createOrUpdateLocator(@NonNull final CreateOrUpdateLocatorRequest request)
	{
		final WarehouseId warehouseId = request.getWarehouseId();
		final String locatorValue = request.getLocatorValue();
		final LocatorId locatorId = retrieveLocatorIdByValueAndWarehouseId(locatorValue, warehouseId);
		final I_M_Locator locator;
		if (locatorId != null)
		{
			locator = getLocatorByIdInTrx(locatorId, I_M_Locator.class);
		}
		else
		{
			locator = InterfaceWrapperHelper.newInstance(I_M_Locator.class);
		}

		if (request.getOrgId() != null)
		{
			locator.setAD_Org_ID(request.getOrgId().getRepoId());
		}
		locator.setM_Warehouse_ID(warehouseId.getRepoId());
		locator.setValue(locatorValue);
		locator.setX(request.getX());
		locator.setY(request.getY());
		locator.setZ(request.getZ());
		locator.setX1(request.getX1());
		locator.setDateLastInventory(TimeUtil.asTimestamp(request.getDateLastInventory()));
		InterfaceWrapperHelper.saveRecord(locator);

		return LocatorId.ofRepoId(warehouseId, locator.getM_Locator_ID());
	}

	@Override
	@Cached(cacheName = I_M_Locator.Table_Name + "#By#" + I_M_Locator.COLUMNNAME_M_Warehouse_ID + "#" + I_M_Locator.COLUMNNAME_Value)
	public LocatorId retrieveLocatorIdByValueAndWarehouseId(@NonNull final String locatorValue, final WarehouseId warehouseId)
	{
		final int locatorRepoId = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Locator.class)
				.addEqualsFilter(I_M_Locator.COLUMNNAME_M_Warehouse_ID, warehouseId)
				.addEqualsFilter(I_M_Locator.COLUMNNAME_Value, locatorValue)
				.create()
				.firstIdOnly();
		return LocatorId.ofRepoIdOrNull(warehouseId, locatorRepoId);
	}

	@Override
	public String getWarehouseName(final WarehouseId warehouseId)
	{
		if (warehouseId == null)
		{
			return "?";
		}

		final I_M_Warehouse warehouse = getById(warehouseId);
		return warehouse != null ? warehouse.getName() : "<" + warehouseId.getRepoId() + ">";
	}

	@Override
	public WarehouseType getWarehouseTypeById(final WarehouseTypeId id)
	{
		return getWarehouseTypesIndex().getById(id);
	}

	private WarehouseTypesIndex getWarehouseTypesIndex()
	{
		return allWarehouseTypes.getOrLoad(0, this::retrieveWarehouseTypesIndex);
	}

	private WarehouseTypesIndex retrieveWarehouseTypesIndex()
	{
		final List<WarehouseType> warehouseTypes = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_M_Warehouse_Type.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(this::toWarehouseType)
				.collect(ImmutableList.toImmutableList());

		return WarehouseTypesIndex.of(warehouseTypes);
	}

	private WarehouseType toWarehouseType(final I_M_Warehouse_Type record)
	{
		final ITranslatableString name = InterfaceWrapperHelper.getModelTranslationMap(record)
				.getColumnTrl(I_M_Warehouse_Type.COLUMNNAME_Name, record.getName());

		return WarehouseType.builder()
				.id(WarehouseTypeId.ofRepoId(record.getM_Warehouse_Type_ID()))
				.name(name)
				.build();
	}

	public static final String MSG_M_Warehouse_NoQuarantineWarehouse = "M_Warehouse_NoQuarantineWarehouse";

	@Override
	@Cached(cacheName = I_M_Warehouse.Table_Name + "#" + org.adempiere.warehouse.model.I_M_Warehouse.COLUMNNAME_IsIssueWarehouse)
	public I_M_Warehouse retrieveWarehouseForIssuesOrNull(@CacheCtx final Properties ctx)
	{
		final I_M_Warehouse warehouse = Services.get(IQueryBL.class).createQueryBuilder(I_M_Warehouse.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(org.adempiere.warehouse.model.I_M_Warehouse.COLUMNNAME_IsIssueWarehouse, true)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_M_Warehouse.class);
		return warehouse;
	}

	@Override
	public final I_M_Warehouse retrieveWarehouseForIssues(Properties ctx)
	{
		final I_M_Warehouse warehouse = retrieveWarehouseForIssuesOrNull(ctx);
		if (warehouse == null)
		{
			throw new AdempiereException("@NotFound@ @M_Warehouse_ID@ (@IsIssueWarehouse@=@Y@)");
		}
		return warehouse;
	}

	@Override
	public org.adempiere.warehouse.model.I_M_Warehouse retrieveQuarantineWarehouseOrNull()
	{
		return Services.get(IQueryBL.class).createQueryBuilder(org.adempiere.warehouse.model.I_M_Warehouse.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(org.adempiere.warehouse.model.I_M_Warehouse.COLUMNNAME_IsQuarantineWarehouse, true)
				.orderBy(I_M_Warehouse.COLUMNNAME_M_Warehouse_ID)
				.create()
				.first();
	}

	@Nullable
	@Override
	public WarehouseId retrieveWarehouseIdBy(@NonNull final WarehouseQuery query)
	{
		final IQueryBuilder<I_M_Warehouse> queryBuilder;
		if (query.isOutOfTrx())
		{
			queryBuilder = Services
					.get(IQueryBL.class)
					.createQueryBuilderOutOfTrx(I_M_Warehouse.class)
					.setOption(IQuery.OPTION_ReturnReadOnlyRecords, true);
		}
		else
		{
			queryBuilder = Services
					.get(IQueryBL.class)
					.createQueryBuilder(I_M_Warehouse.class);
		}

		if (query.isIncludeAnyOrg())
		{
			queryBuilder
					.addInArrayFilter(I_M_Warehouse.COLUMNNAME_AD_Org_ID, query.getOrgId(), OrgId.ANY)
					.orderByDescending(I_M_Warehouse.COLUMNNAME_AD_Org_ID);
		}
		else
		{
			queryBuilder.addEqualsFilter(I_M_Warehouse.COLUMNNAME_AD_Org_ID, query.getOrgId());
		}

		if (!isEmpty(query.getValue(), true))
		{
			queryBuilder.addEqualsFilter(I_M_Warehouse.COLUMNNAME_Value, query.getValue().trim());
		}
		if (query.getExternalId() != null)
		{
			queryBuilder.addEqualsFilter(I_M_Warehouse.COLUMNNAME_ExternalId, query.getExternalId().getValue().trim());
		}

		final int productRepoId = queryBuilder
				.addOnlyActiveRecordsFilter()
				.create()
				.firstId();

		return WarehouseId.ofRepoIdOrNull(productRepoId);
	}
}
