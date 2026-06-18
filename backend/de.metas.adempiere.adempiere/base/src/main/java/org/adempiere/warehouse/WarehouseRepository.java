/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package org.adempiere.warehouse;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import de.metas.cache.CCache;
import de.metas.material.planning.ddorder.DistributionNetworkId;
import de.metas.organization.OrgId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ResourceId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_M_Warehouse_SourceHUConfig;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository Tables: M_Warehouse, M_Warehouse_SourceHUConfig, M_Locator
 * Repository Cluster: WarehouseRepository, WarehouseDAO
 */
@Repository
public class WarehouseRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@VisibleForTesting
	public static WarehouseRepository newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return SpringContextHolder.getBeanOrSupply(WarehouseRepository.class, WarehouseRepository::new);
	}

	private final CCache<Integer, WarehouseMap> cache = CCache.<Integer, WarehouseMap>builder()
			.tableName(I_M_Warehouse.Table_Name)
			.additionalTableNameToResetFor(I_M_Warehouse_SourceHUConfig.Table_Name)
			.additionalTableNameToResetFor(I_M_Locator.Table_Name)
			.build();

	@NonNull
	public Warehouse getById(@NonNull final WarehouseId warehouseId)
	{
		return getWarehouseMap().getById(warehouseId);
	}

	private WarehouseMap getWarehouseMap()
	{
		return cache.getOrLoadNonNull(0, this::retrieveWarehouseMap);
	}

	@NonNull
	private WarehouseMap retrieveWarehouseMap()
	{
		final ImmutableList<I_M_Warehouse> warehouseRecords = retrieveWarehouseRecords();
		final Multimap<WarehouseId, Locator> locators = retrieveLocatorRecords();
		final Multimap<WarehouseId, WarehouseSourceHUConfig> sourceHUs = retrieveWarehouseSourceHUConfigRecords();

		final ImmutableList<Warehouse> warehouses = warehouseRecords.stream()
				.map(record -> fromRecord(record, locators, sourceHUs))
				.collect(ImmutableList.toImmutableList());

		return new WarehouseMap(warehouses);
	}

	private ImmutableList<I_M_Warehouse> retrieveWarehouseRecords()
	{
		return queryBL.createQueryBuilder(I_M_Warehouse.class)
				//.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.collect(ImmutableList.toImmutableList());
	}

	private Multimap<WarehouseId, WarehouseSourceHUConfig> retrieveWarehouseSourceHUConfigRecords()
	{
		return queryBL.createQueryBuilder(I_M_Warehouse_SourceHUConfig.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.collect(Multimaps.toMultimap(
						record -> WarehouseId.ofRepoId(record.getM_Warehouse_ID()),
						WarehouseRepository::fromRecord,
						HashMultimap::create
				));
	}

	private Multimap<WarehouseId, Locator> retrieveLocatorRecords()
	{
		return queryBL.createQueryBuilder(I_M_Locator.class)
				//.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.collect(Multimaps.toMultimap(
						record -> WarehouseId.ofRepoId(record.getM_Warehouse_ID()),
						WarehouseRepository::fromRecord,
						HashMultimap::create
				));
	}

	private static WarehouseSourceHUConfig fromRecord(@NonNull final I_M_Warehouse_SourceHUConfig record)
	{
		return WarehouseSourceHUConfig.builder()
				.id(WarehouseSourceHUConfigId.ofRepoId(record.getM_Warehouse_SourceHUConfig_ID()))
				.warehouseId(WarehouseId.ofRepoId(record.getM_Warehouse_ID()))
				.productCategoryId(ProductCategoryId.ofRepoId(record.getM_Product_Category_ID()))
				.build();
	}

	private static Locator fromRecord(final I_M_Locator record)
	{
		final LocatorId locatorId = LocatorId.ofRecord(record);

		// M_Locator.Value is not a mandatory column, so it may be null/blank in real databases.
		// Locator.value is @NonNull (used as display caption / QR caption), so fall back to the locator id
		// rather than letting the whole warehouse-map load fail with an NPE.
		final String value = StringUtils.trimBlankToNull(record.getValue());

		return Locator.builder()
				.locatorId(locatorId)
				.active(record.isActive())
				.value(value != null ? value : "<" + locatorId.getRepoId() + ">")
				.priorityNo(record.getPriorityNo())
				.isGroundFloor(record.isGroundLocator())
				.build();
	}

	private static Warehouse fromRecord(
			@NonNull final I_M_Warehouse record,
			@NonNull final Multimap<WarehouseId, Locator> locatorsMap,
			@NonNull final Multimap<WarehouseId, WarehouseSourceHUConfig> sourceHUConfigsMap)
	{
		final WarehouseId warehouseId = WarehouseId.ofRepoId(record.getM_Warehouse_ID());
		return Warehouse.builder()
				.warehouseId(warehouseId)
				.active(record.isActive())
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.name(record.getName())
				.plantId(ResourceId.ofRepoIdOrNull(record.getPP_Plant_ID()))
				.isInTransit(record.isInTransit())
				.isReceiveAsSourceHU(record.isReceiveAsSourceHU())
				.isAutoDistributionOrder(record.isAutoDistributionOrder())
				.distributionNetworkId(DistributionNetworkId.ofRepoIdOrNull(record.getDD_NetworkDistribution_ID()))
				.locators(locatorsMap.get(warehouseId))
				.warehouseSourceHUConfigs(WarehouseSourceHUConfigList.ofCollection(sourceHUConfigsMap.get(warehouseId)))
				.build();
	}

	@NonNull
	public ImmutableSet<WarehouseId> getAllActiveIds()
	{
		return getWarehouseMap().getAllActiveIds();
	}

	public String getWarehouseName(@NonNull final WarehouseId warehouseId)
	{
		return getWarehouseMap().getWarehouseName(warehouseId);
	}

	public String getLocatorNameById(final @NonNull LocatorId locatorId)
	{
		return getLocatorById(locatorId).getValue();
	}

	public Locator getLocatorByRepoId(final int locatorRepoId)
	{
		return getWarehouseMap().getLocatorByRepoId(locatorRepoId);
	}

	public Locator getLocatorById(@NonNull final LocatorId locatorId)
	{
		return getWarehouseMap().getLocatorById(locatorId);
	}

	@NonNull
	public WarehouseId getInTransitWarehouseId(@NonNull final OrgId adOrgId)
	{
		return getInTransitWarehouseIdIfExists(adOrgId)
				.orElseThrow(() -> new AdempiereException("@NotFound@ @M_Warehouse_ID@ (@IsInTransit@, @AD_Org_ID@:" + adOrgId.getRepoId() + ")"));
	}

	public Optional<WarehouseId> getInTransitWarehouseIdIfExists(@NonNull final OrgId orgId)
	{
		return getWarehouseMap().getInTransitWarehouseIdIfExists(orgId);
	}

	public Optional<ResourceId> getPlantId(final WarehouseId warehouseId)
	{
		return Optional.ofNullable(getById(warehouseId).getPlantId());
	}

}
