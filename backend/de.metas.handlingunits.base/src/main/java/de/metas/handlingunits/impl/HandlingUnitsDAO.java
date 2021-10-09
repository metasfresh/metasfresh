/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.handlingunits.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.cache.annotation.CacheCtx;
import de.metas.cache.annotation.CacheTrx;
import de.metas.handlingunits.HUItemType;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuItemId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.IHUAndItemsDAO;
import de.metas.handlingunits.IHUBuilder;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.inout.IHUPackingMaterialDAO;
import de.metas.handlingunits.model.I_DD_NetworkDistribution;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Item_Storage;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.handlingunits.reservation.HUReservationRepository;
import de.metas.logging.LogManager;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.IteratorUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.adempiere.util.proxy.Cached;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadByRepoIdAwares;
import static org.adempiere.model.InterfaceWrapperHelper.loadByRepoIdAwaresOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

public class HandlingUnitsDAO implements IHandlingUnitsDAO
{
	private static final transient Logger logger = LogManager.getLogger(HandlingUnitsDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final IHUAndItemsDAO defaultHUAndItemsDAO;

	public HandlingUnitsDAO()
	{
		defaultHUAndItemsDAO = new CachedIfInTransactionHUAndItemsDAO();
	}

	private IHUAndItemsDAO getHUAndItemsDAO()
	{
		return defaultHUAndItemsDAO;
	}

	@Override
	public I_M_HU getByIdOutOfTrx(@NonNull final HuId huId)
	{
		return loadOutOfTrx(huId, I_M_HU.class);
	}

	@Override
	public I_M_HU getById(@NonNull final HuId huId)
	{
		return load(huId, I_M_HU.class);
	}

	@Override
	public ClientAndOrgId getClientAndOrgId(@NonNull final HuId huId)
	{
		final I_M_HU hu = load(huId, I_M_HU.class);
		return ClientAndOrgId.ofClientAndOrg(hu.getAD_Client_ID(), hu.getAD_Org_ID());
	}

	@Override
	public List<I_M_HU> getByIds(@NonNull final Collection<HuId> huIds)
	{
		return loadByRepoIdAwares(ImmutableSet.copyOf(huIds), I_M_HU.class);
	}

	@Override
	public List<I_M_HU> getByIdsOutOfTrx(@NonNull final Collection<HuId> huIds)
	{
		return loadByRepoIdAwaresOutOfTrx(ImmutableSet.copyOf(huIds), I_M_HU.class);
	}

	@Override
	public I_M_HU_PI retrieveVirtualPI(final Properties ctx)
	{
		return retrievePI(ctx, HuPackingInstructionsId.VIRTUAL);
	}

	@Override
	@Cached
	public I_M_HU_PI_Item retrieveVirtualPIItem(final @CacheCtx Properties ctx)
	{
		final I_M_HU_PI_Item virtualPIItem = InterfaceWrapperHelper.create(ctx, HuPackingInstructionsItemId.VIRTUAL.getRepoId(), I_M_HU_PI_Item.class, ITrx.TRXNAME_None);
		if (virtualPIItem == null)
		{
			throw new AdempiereException("@NotFound@ @M_HU_PI_Item_ID@ virtual (ID=" + HuPackingInstructionsItemId.VIRTUAL + ")");
		}

		return virtualPIItem;
	}

	@Override
	public I_M_HU_PI getPackingInstructionById(@NonNull final HuPackingInstructionsId id)
	{
		return retrievePI(Env.getCtx(), id);
	}

	@Cached(cacheName = I_M_HU_PI.Table_Name + "#by#" + I_M_HU_PI.COLUMNNAME_M_HU_PI_ID)
		// NOTE: for caching to work, don't make it final
		/* package */I_M_HU_PI retrievePI(final @CacheCtx Properties ctx, @NonNull final HuPackingInstructionsId piId)
	{
		final I_M_HU_PI pi = queryBL.createQueryBuilder(I_M_HU_PI.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_M_HU_PI.COLUMNNAME_M_HU_PI_ID, piId)
				.create()
				.firstOnly(I_M_HU_PI.class);
		if (pi == null)
		{
			throw new AdempiereException("@NotFound@ @M_HU_PI_ID@: " + piId);
		}
		return pi;
	}

	@Override
	public I_M_HU_PI_Item getPackingInstructionItemById(@NonNull final HuPackingInstructionsItemId piItemId)
	{
		return loadOutOfTrx(piItemId, I_M_HU_PI_Item.class);
	}

	@Override
	public IHUBuilder createHUBuilder(final IHUContext huContext)
	{
		final IHUBuilder huBuilder = new HUBuilder(huContext);
		huBuilder.setDate(huContext.getDate());
		return huBuilder;
	}

	@Override
	public void saveHU(final I_M_HU hu)
	{
		getHUAndItemsDAO().saveHU(hu);
	}

	@Override
	public void saveHUItem(@NonNull final I_M_HU_Item huItem)
	{
		InterfaceWrapperHelper.save(huItem);
	}

	@Override
	public void delete(final I_M_HU hu)
	{
		getHUAndItemsDAO().delete(hu);
	}

	@Override
	public I_M_HU retrieveParent(final I_M_HU hu)
	{
		return getHUAndItemsDAO().retrieveParent(hu);
	}

	@Override
	public int retrieveParentId(final I_M_HU hu)
	{
		return getHUAndItemsDAO().retrieveParentId(hu);
	}

	@Override
	public I_M_HU_Item retrieveParentItem(final I_M_HU hu)
	{
		return getHUAndItemsDAO().retrieveParentItem(hu);
	}

	@Override
	public void setParentItem(final I_M_HU hu, @Nullable final I_M_HU_Item parentItem)
	{
		getHUAndItemsDAO().setParentItem(hu, parentItem);
	}

	@Override
	public List<I_M_HU> retrieveIncludedHUs(final I_M_HU_Item item)
	{
		return getHUAndItemsDAO().retrieveIncludedHUs(item);
	}

	@Override
	public List<I_M_HU> retrieveIncludedHUs(@NonNull final I_M_HU hu)
	{
		final List<I_M_HU> result = new ArrayList<>();

		final Set<Integer> seenHUIds = new HashSet<>();
		for (final I_M_HU_Item huItem : retrieveItems(hu))
		{
			final List<I_M_HU> includedHUs = retrieveIncludedHUs(huItem);
			for (final I_M_HU includedHU : includedHUs)
			{
				final int huId = includedHU.getM_HU_ID();
				if (!seenHUIds.add(huId))
				{
					continue;
				}
				result.add(includedHU);
			}
		}

		return result;
	}

	@Override
	public I_M_HU_Item createHUItem(final I_M_HU hu, final I_M_HU_PI_Item piItem)
	{
		return getHUAndItemsDAO().createHUItem(hu, piItem);
	}

	@Override
	public IPair<I_M_HU_Item, Boolean> createHUItemIfNotExists(final I_M_HU hu, final I_M_HU_PI_Item piItem)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class); // FIXME

		final I_M_HU_Item existingItem = getHUAndItemsDAO()
				.retrieveItem(
						Preconditions.checkNotNull(hu, "Param 'hu' may not be null"),
						Preconditions.checkNotNull(piItem, "Param 'piItem' may not be nulll"));

		if (existingItem != null && X_M_HU_Item.ITEMTYPE_HandlingUnit.equals(handlingUnitsBL.getItemType(existingItem)))
		{
			// item existed
			return ImmutablePair.of(existingItem, false);
		}

		return ImmutablePair.of(createHUItem(hu, piItem), true);
	}

	@Override
	public I_M_HU_Item createAggregateHUItem(@NonNull final I_M_HU hu)
	{
		return getHUAndItemsDAO().createAggregateHUItem(hu);
	}

	@Override
	public I_M_HU_Item createChildHUItem(@NonNull final I_M_HU hu)
	{
		return getHUAndItemsDAO().createChildHUItem(hu);
	}

	@Override
	public List<I_M_HU_Item> retrieveItems(final I_M_HU hu)
	{
		return getHUAndItemsDAO().retrieveItems(hu);
	}

	@Override
	public List<I_M_HU_Item> retrieveItems(@NonNull final I_M_HU hu, @NonNull final HUItemType type)
	{
		return getHUAndItemsDAO().retrieveItems(hu) /*loads&caches all items in one go*/
				.stream()
				.filter(item -> type.getCode().equals(item.getItemType()))
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public I_M_HU_Item retrieveItem(final I_M_HU hu, final I_M_HU_PI_Item piItem)
	{
		return getHUAndItemsDAO().retrieveItem(hu, piItem);
	}

	@Override
	public I_M_HU_Item retrieveAggregatedItemOrNull(final I_M_HU hu, final I_M_HU_PI_Item piItem)
	{
		return getHUAndItemsDAO().retrieveAggregatedItemOrNull(hu);
	}

	@Override
	public List<I_M_HU_Item> retrieveItemsNoCache(final Collection<HuId> huIds)
	{
		if (huIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return queryBL.createQueryBuilder(I_M_HU_Item.class)
				.addInArrayFilter(I_M_HU_Item.COLUMN_M_HU_ID, huIds)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();
	}

	@Override
	public List<I_M_HU> retrieveIncludedHUsNoCache(final Set<HuItemId> huItemIds)
	{
		if (huItemIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return queryBL.createQueryBuilder(I_M_HU.class)
				.addInArrayFilter(I_M_HU.COLUMNNAME_M_HU_Item_Parent_ID, huItemIds)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();
	}

	@Override
	public List<I_M_HU_Item_Storage> retrieveItemStoragesNoCache(final Set<HuItemId> huItemIds)
	{
		if (huItemIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return queryBL.createQueryBuilder(I_M_HU_Item_Storage.class)
				.addInArrayFilter(I_M_HU_Item_Storage.COLUMNNAME_M_HU_Item_ID, huItemIds)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();
	}

	@Override
	public List<I_M_HU_Storage> retrieveStoragesNoCache(final Set<HuId> huIds)
	{
		if (huIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return queryBL.createQueryBuilder(I_M_HU_Storage.class)
				.addInArrayFilter(I_M_HU_Storage.COLUMNNAME_M_HU_ID, huIds)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();
	}

	@Override
	public List<IPair<I_M_HU_PackingMaterial, Integer>> retrievePackingMaterialAndQtys(final I_M_HU hu)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class); // FIXME
		final IHUPackingMaterialDAO packingMaterialDAO = Services.get(IHUPackingMaterialDAO.class);

		final List<IPair<I_M_HU_PackingMaterial, Integer>> packingMaterials = new ArrayList<>();
		final List<I_M_HU_Item> huItems = retrieveItems(hu);
		for (final I_M_HU_Item huItem : huItems)
		{
			final String itemType = handlingUnitsBL.getItemType(huItem);
			if (!X_M_HU_PI_Item.ITEMTYPE_PackingMaterial.equals(itemType))
			{
				continue; // 'hu' has to have at least one item with type "PackingMaterial"
			}

			// if 'hu'is an aggregate VHU, then it represents not one, but as many HU as its parent item's Qty sais.
			final BigDecimal qty;
			if (handlingUnitsBL.isAggregateHU(hu))
			{
				qty = hu.getM_HU_Item_Parent().getQty();
				if (qty.signum() <= 0)
				{
					// this is an "empty" or "stub" aggregate HU. no packing material
					continue;
				}
			}
			else
			{
				qty = BigDecimal.ONE;
			}

			final I_M_HU_PackingMaterial packingMaterial = packingMaterialDAO.retrieveHUPackingMaterialOrNull(huItem);

			packingMaterials.add(ImmutablePair.of(packingMaterial, qty.intValueExact()));
		}
		return packingMaterials;
	}

	@Override
	public List<I_M_HU_PI_Item> retrievePIItems(final I_M_HU_PI handlingUnit, final BPartnerId bpartnerId)
	{
		final I_M_HU_PI_Version version = retrievePICurrentVersion(handlingUnit);
		return retrievePIItems(version, bpartnerId);
	}

	@Override
	public List<I_M_HU_PI_Item> retrievePIItems(
			@NonNull final I_M_HU_PI_Version version,
			@Nullable final BPartnerId bpartnerId)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(version);
		final String trxName = InterfaceWrapperHelper.getTrxName(version);
		final int huPIVersionId = version.getM_HU_PI_Version_ID();

		final List<I_M_HU_PI_Item> piItemsAll = retrieveAllPIItems(ctx, huPIVersionId, trxName);

		final List<I_M_HU_PI_Item> piItems = new ArrayList<>();
		for (final I_M_HU_PI_Item piItem : piItemsAll)
		{
			// Skip inactive lines
			if (!piItem.isActive())
			{
				continue;
			}

			final String itemType = piItem.getItemType();

			//
			// In case item is of type HandlingUnit, we need to filter by BPartner
			// NOTE: we do this checking only in case of HU itemType to preserve the old logic (maybe it would be good to do it in case of any PI Item, not sure)
			if (X_M_HU_PI_Item.ITEMTYPE_HandlingUnit.equals(itemType))
			{
				final BPartnerId itemBPartnerId = BPartnerId.ofRepoIdOrNull(piItem.getC_BPartner_ID());

				//
				// Item is for a specific BPartner
				if (itemBPartnerId != null)
				{
					// ... we were asked for a specific partner
					if (bpartnerId != null)
					{
						// ... skip item if BPartners does not match
						if (!BPartnerId.equals(itemBPartnerId, bpartnerId))
						{
							continue;
						}
					}
					// ... we were not asked for a specific partner
					else
					{
						// don't accept it
						continue;
					}
				}
				//
				// Item is not for a specific BPartner
				else
				{
					// accept it
					// does not matter if we were asked for a specific partner because this is a generic item
				}
			}

			piItems.add(piItem);
		}

		return Collections.unmodifiableList(piItems);
	}

	@Override
	public List<I_M_HU_PI_Item> retrieveAllPIItems(final I_M_HU_PI_Version piVersion)
	{
		Check.assumeNotNull(piVersion, "version not null");
		final Properties ctx = InterfaceWrapperHelper.getCtx(piVersion);
		final String trxName = InterfaceWrapperHelper.getTrxName(piVersion);
		final int huPIVersionId = piVersion.getM_HU_PI_Version_ID();
		return retrieveAllPIItems(ctx, huPIVersionId, trxName);
	}

	/**
	 * Retrieve all {@link I_M_HU_PI_Item}s (active or inactive) for given M_HU_PI_Version_ID.
	 */
	@Cached(cacheName = I_M_HU_Item.Table_Name + "#by"
			+ "#" + I_M_HU_PI_Item.COLUMNNAME_M_HU_PI_Version_ID)
	/* package */List<I_M_HU_PI_Item> retrieveAllPIItems(
			final @CacheCtx Properties ctx,
			final int huPIVersionId,
			final @CacheTrx String trxName)
	{
		final List<I_M_HU_PI_Item> piItems = queryBL.createQueryBuilder(I_M_HU_PI_Item.class, ctx, trxName)
				.addEqualsFilter(I_M_HU_PI_Item.COLUMN_M_HU_PI_Version_ID, huPIVersionId)
				.create()
				// .setOnlyActiveRecords(true) // retrieve all records, not only the active ones
				.list(I_M_HU_PI_Item.class);

		return Collections.unmodifiableList(piItems);
	}

	@Override
	@Nullable
	public I_M_HU_PI retrievePIDefaultForPicking()
	{
		return queryBL.createQueryBuilder(I_M_HU_PI.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_PI.COLUMNNAME_IsDefaultForPicking, true)
				.create()
				.first();
	}

	@Override
	public List<I_M_HU_PI_Version> retrieveAllPIVersions(final I_M_HU_PI pi)
	{
		final int piId = pi.getM_HU_PI_ID();
		final Properties ctx = InterfaceWrapperHelper.getCtx(pi);
		final String trxName = InterfaceWrapperHelper.getTrxName(pi);
		return retrieveAllPIVersions(ctx, piId, trxName);
	}

	// NOTE: no need to cache because it's used only in one place
	// @Cached(cacheName = I_M_HU_PI_Version.Table_Name
	// + "#by"
	// + "#" + I_M_HU_PI_Version.COLUMNNAME_M_HU_PI_ID)
	private List<I_M_HU_PI_Version> retrieveAllPIVersions(
			@CacheCtx final Properties ctx,
			final int piId,
			@CacheTrx final String trxName)
	{
		return queryBL.createQueryBuilder(I_M_HU_PI_Version.class, ctx, trxName)
				.addEqualsFilter(I_M_HU_PI_Version.COLUMN_M_HU_PI_ID, piId)
				.create()
				.setOnlyActiveRecords(false) // return non-active also
				.list(I_M_HU_PI_Version.class);
	}

	@Override
	public HuPackingInstructionsVersionId retrievePICurrentVersionId(@NonNull final HuPackingInstructionsId piId)
	{
		final I_M_HU_PI pi = getPackingInstructionById(piId);
		return retrievePICurrentVersionId(pi);
	}

	@Override
	public HuPackingInstructionsVersionId retrievePICurrentVersionId(final I_M_HU_PI pi)
	{
		final I_M_HU_PI_Version piVersion = retrievePICurrentVersion(pi);
		return HuPackingInstructionsVersionId.ofRepoId(piVersion.getM_HU_PI_Version_ID());
	}

	@Override
	public I_M_HU_PI_Version retrievePICurrentVersion(final I_M_HU_PI pi)
	{
		final I_M_HU_PI_Version piVersion = retrievePICurrentVersionOrNull(pi);
		if (piVersion == null)
		{
			throw new HUException("No current version found for " + pi);
		}
		return piVersion;
	}

	@Override
	public I_M_HU_PI_Version retrievePICurrentVersionOrNull(@NonNull final I_M_HU_PI pi)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(pi);
		final String trxName = InterfaceWrapperHelper.getTrxName(pi);
		final HuPackingInstructionsId piId = HuPackingInstructionsId.ofRepoId(pi.getM_HU_PI_ID());

		return retrievePICurrentVersionOrNull(ctx, piId, trxName);
	}

	@Override
	public I_M_HU_PI_Version retrievePICurrentVersionOrNull(@NonNull final HuPackingInstructionsId piId)
	{
		return retrievePICurrentVersionOrNull(Env.getCtx(), piId, ITrx.TRXNAME_None);
	}

	@Override
	public I_M_HU_PI_Version retrievePIVersionById(@NonNull final HuPackingInstructionsVersionId id)
	{
		return loadOutOfTrx(id, I_M_HU_PI_Version.class);
	}

	@Cached(cacheName = I_M_HU_PI_Version.Table_Name
			+ "#by"
			+ "#" + I_M_HU_PI_Version.COLUMNNAME_M_HU_PI_ID
			+ "#" + I_M_HU_PI_Version.COLUMNNAME_IsCurrent)
	@Nullable
	I_M_HU_PI_Version retrievePICurrentVersionOrNull(
			final @CacheCtx Properties ctx,
			final HuPackingInstructionsId piId,
			@Nullable final @CacheTrx String trxName)
	{
		return queryBL.createQueryBuilder(I_M_HU_PI_Version.class, ctx, trxName)
				.addEqualsFilter(I_M_HU_PI_Version.COLUMN_M_HU_PI_ID, piId)
				.addEqualsFilter(I_M_HU_PI_Version.COLUMN_IsCurrent, true)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_M_HU_PI_Version.class);
	}

	@Override
	public Iterator<I_M_HU> retrieveTopLevelHUsForLocator(final I_M_Locator locator)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(locator);
		final String trxName = InterfaceWrapperHelper.getTrxName(locator);
		final int locatorId = locator.getM_Locator_ID();

		return retrieveTopLevelHUsForLocators(ctx, Collections.singleton(locatorId), trxName);
	}

	public Iterator<I_M_HU> retrieveTopLevelHUsForLocators(final Properties ctx, final Collection<Integer> locatorIds, final String trxName)
	{
		if (locatorIds.isEmpty())
		{
			return IteratorUtils.emptyIterator();
		}

		final IQueryBuilder<I_M_HU> queryBuilder = queryBL.createQueryBuilder(I_M_HU.class, ctx, trxName);

		final ICompositeQueryFilter<I_M_HU> filters = queryBuilder.getCompositeFilter();
		filters.addInArrayOrAllFilter(I_M_HU.COLUMNNAME_M_Locator_ID, locatorIds);

		// Top Level filter
		filters.addEqualsFilter(I_M_HU.COLUMN_M_HU_Item_Parent_ID, null);

		queryBuilder.orderBy()
				.addColumn(I_M_HU.COLUMN_M_HU_ID);

		return queryBuilder
				.create()
				.setOnlyActiveRecords(true)
				.iterate(I_M_HU.class);
	}

	@Override
	public List<I_M_HU> retrieveChildHUsForItem(final I_M_HU_Item parentItem)
	{
		final IQueryBuilder<I_M_HU> queryBuilder = queryBL.createQueryBuilder(I_M_HU.class, parentItem);

		return queryBuilder
				.addEqualsFilter(I_M_HU.COLUMNNAME_M_HU_Item_Parent_ID, parentItem.getM_HU_Item_ID())
				.create()
				.list();
	}

	@Override
	public List<I_M_HU_PI_Item> retrieveParentPIItemsForParentPI(
			@NonNull final I_M_HU_PI huPI,
			@Nullable final String huUnitType,
			@Nullable final BPartnerId bpartnerId)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(huPI);
		final String trxName = InterfaceWrapperHelper.getTrxName(huPI);
		final HuPackingInstructionsId packingInstructionsId = HuPackingInstructionsId.ofRepoId(huPI.getM_HU_PI_ID());

		return retrieveParentPIItemsForParentPI(ctx, packingInstructionsId, huUnitType, bpartnerId, trxName);
	}

	@Cached
	List<I_M_HU_PI_Item> retrieveParentPIItemsForParentPI(
			@CacheCtx final Properties ctx,
			@NonNull final HuPackingInstructionsId packingInstructionsId,
			@Nullable final String huUnitType,
			@Nullable final BPartnerId bpartnerId,
			@CacheTrx final String trxName)
	{
		final IQueryBuilder<I_M_HU_PI_Item> piItemsQueryBuilder = queryBL.createQueryBuilder(I_M_HU_PI_Item.class, ctx, trxName)
				.addOnlyActiveRecordsFilter();

		//
		// Fetch only those PI Items which have our given huPI included
		// 08254: if Template-PI, don't add included HU filter
		if (!packingInstructionsId.isTemplate())
		{
			piItemsQueryBuilder.addEqualsFilter(I_M_HU_PI_Item.COLUMN_Included_HU_PI_ID, packingInstructionsId);
		}

		//
		// Fetch only those PI Items which have set our BPartner or who don't have set a BPartner at all
		// ... and put first those with specific partner
		piItemsQueryBuilder.addInArrayOrAllFilter(I_M_HU_PI_Item.COLUMNNAME_C_BPartner_ID, null, bpartnerId);
		piItemsQueryBuilder.orderBy()
				.addColumn(I_M_HU_PI_Item.COLUMNNAME_C_BPartner_ID, Direction.Descending, Nulls.Last) // lines with BPartner set, first
				.addColumn(I_M_HU_PI_Item.COLUMN_M_HU_PI_Item_ID, Direction.Ascending, Nulls.Last) // just to have a predictable order
		;

		//
		// Retrieve matching parent PI Items
		final List<I_M_HU_PI_Item> parentItems = piItemsQueryBuilder
				.create()
				.list(I_M_HU_PI_Item.class);

		final List<I_M_HU_PI_Item> parentPIItems = new ArrayList<>();
		final Set<ArrayKey> seen = new HashSet<>();
		for (final I_M_HU_PI_Item parentItem : parentItems)
		{
			// Make sure we are not evaluating again this PI version
			final int parentVersionId = parentItem.getM_HU_PI_Version_ID();
			if (!seen.add(Util.mkKey(I_M_HU_PI_Version.Table_Name, parentVersionId)))
			{
				// already evaluated
				continue;
			}

			// Make sure the PI version is Active and it's the current one
			final I_M_HU_PI_Version parentVersion = parentItem.getM_HU_PI_Version();
			if (!parentVersion.isActive())
			{
				continue;
			}
			if (!parentVersion.isCurrent())
			{
				continue;
			}

			//
			// Make sure the HU UnitType is matching
			final String parentUnitType = parentVersion.getHU_UnitType();
			if (!Check.isEmpty(huUnitType, true) && !Objects.equals(parentUnitType, huUnitType))
			{
				// required unit type not matched
				continue;
			}

			//
			// If we are reaching this point, our Parent PI Item is eligible, so we are collecting it
			parentPIItems.add(parentItem);
		}

		return parentPIItems;
	}

	@Override
	public I_M_HU_PI_Item retrieveParentPIItemForChildHUOrNull(final I_M_HU parentHU, final I_M_HU_PI piOfChildHU, final IContextAware ctx)
	{
		return queryBL.createQueryBuilder(I_M_HU_PI_Item.class, ctx)
				.addOnlyActiveRecordsFilter()

				// it's an PI-item of the the parent's PI
				.addEqualsFilter(I_M_HU_PI_Item.COLUMNNAME_M_HU_PI_Version_ID, parentHU.getM_HU_PI_Version_ID())

				// it includes the childs's HU PI as one of its "child" PI
				.addEqualsFilter(I_M_HU_PI_Item.COLUMNNAME_Included_HU_PI_ID, piOfChildHU.getM_HU_PI_ID())

				// it either has no C_BPartner_ID or a matching one
				.addInArrayFilter(I_M_HU_PI_Item.COLUMNNAME_C_BPartner_ID, null, parentHU.getC_BPartner_ID())

				// order by C_BPartner_ID descending to favor any piItem with a matching C_BPartner_ID
				.orderByDescending(I_M_HU_PI_Item.COLUMNNAME_C_BPartner_ID)

				.create()
				.first();
	}

	@NonNull
	@Override
	public I_M_HU_PI getIncludedPI(@NonNull final I_M_HU_PI_Item piItem)
	{
		final HuPackingInstructionsId includedPIId = HuPackingInstructionsId.ofRepoId(piItem.getIncluded_HU_PI_ID());
		return getPackingInstructionById(includedPIId);
	}

	@Override
	public void save(@NonNull final I_M_HU_PI huPi)
	{
		InterfaceWrapperHelper.save(huPi);
	}

	@Override
	public I_M_HU_PackingMaterial retrievePackingMaterial(final I_M_HU_PI_Version piVersion,
														  @Nullable final BPartnerId bpartnerId)
	{
		I_M_HU_PI_Item itemPM = null;
		for (final I_M_HU_PI_Item item : retrievePIItems(piVersion, bpartnerId))
		{
			final String itemType = item.getItemType();
			if (X_M_HU_PI_Item.ITEMTYPE_PackingMaterial.equals(itemType))
			{
				Check.assumeNull(itemPM,
						"There shall be only one packing material line for {} but we found: {}, {}",
						piVersion, itemPM, item);
				itemPM = item;
			}
		}

		// No Packing Material Line
		if (itemPM == null)
		{
			return null;
		}

		return itemPM.getM_HU_PackingMaterial();
	}

	@Override
	public List<I_M_HU> retrieveVirtualHUs(final I_M_HU_Item itemMaterial)
	{
		return retrieveIncludedHUs(itemMaterial);
	}

	@Override
	public IHUQueryBuilder createHUQueryBuilder()
	{
		final HUReservationRepository huReservationRepository = getHUReservationRepository();
		return new HUQueryBuilder(huReservationRepository);
	}

	private HUReservationRepository getHUReservationRepository()
	{
		if (Adempiere.isUnitTestMode())
		{
			// avoid having to annotate each test that uses HUQueryBuilder with "@RunWith(SpringRunner.class) @SpringBootTest.."
			return new HUReservationRepository();
		}
		return SpringContextHolder.instance.getBean(HUReservationRepository.class);
	}

	@Override
	public I_M_HU_PI_Item retrieveDefaultParentPIItem(
			@NonNull final I_M_HU_PI huPI,
			@Nullable final String huUnitType,
			@Nullable final BPartnerId bpartnerId)
	{
		//
		// Fetch all eligible parent PI Items
		final List<I_M_HU_PI_Item> parentPIItems = retrieveParentPIItemsForParentPI(huPI, huUnitType, bpartnerId);

		//
		// Case: no parent PI items found
		// => return null
		if (parentPIItems.isEmpty())
		{
			return null;
		}
		//
		// Case: only one matching PI item found
		// => perfect, return it
		else if (parentPIItems.size() == 1)
		{
			return parentPIItems.get(0);
		}
		//
		// Case: more than one Parent PI Items found
		// => pick the one which is about the default HU PI
		// NOTE: if we get to this case, we assume we are searching for the parent LU PI
		else
		{
			// Get those PI Items which are about Default LUs
			final List<I_M_HU_PI_Item> defaultLUPIItems = parentPIItems
					.stream()
					.filter(parentPIItem -> parentPIItem.getM_HU_PI_Version().getM_HU_PI().isDefaultLU())
					.collect(Collectors.toList());

			// If we found only one default, we can return it directly
			if (defaultLUPIItems.size() == 1)
			{
				return defaultLUPIItems.get(0);
			}

			logger.warn("More then one parent PI Item found. Returing the first one."
							+ "\n huPI={}"
							+ "\n huUnitType={}"
							+ "\n bpartner={}"
							+ "\n HU PI Items with DefaultLU={}"
							+ "\n => parent HU PI Items={}",
					huPI, huUnitType, bpartnerId, defaultLUPIItems, parentPIItems);

			return parentPIItems.get(0);
		}
	}

	@Override
	@Cached(cacheName = I_M_HU_PI.Table_Name + "#by#AD_Org_ID#IsDefaultLU")
	public I_M_HU_PI retrieveDefaultLUOrNull(@CacheCtx final Properties ctx, final int adOrgId)
	{
		return queryBL.createQueryBuilder(I_M_HU_PI.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClientOrSystem()
				.addEqualsFilter(I_M_HU_PI.COLUMN_IsDefaultLU, true)
				.addInArrayOrAllFilter(I_M_HU_PI.COLUMNNAME_AD_Org_ID, Env.CTXVALUE_AD_Org_ID_System, adOrgId)
				.orderBy()
				.addColumn(I_M_HU_PI.COLUMNNAME_AD_Client_ID, Direction.Descending, Nulls.Last)
				.addColumn(I_M_HU_PI.COLUMNNAME_AD_Org_ID, Direction.Descending, Nulls.Last)
				.endOrderBy()
				.create()
				.firstOnly(I_M_HU_PI.class);

	}

	@Override
	public I_DD_NetworkDistribution retrieveEmptiesDistributionNetwork(
			final @CacheCtx Properties ctx,
			final I_M_Product product_NOTUSED,
			final @CacheTrx String trxName)
	{
		return retrieveEmptiesDistributionNetwork(ctx, trxName);
	}

	@Cached(cacheName = I_DD_NetworkDistribution.Table_Name
			+ "#by"
			+ "#" + I_DD_NetworkDistribution.COLUMNNAME_IsHUDestroyed)
	@Nullable
	I_DD_NetworkDistribution retrieveEmptiesDistributionNetwork(
			final @CacheCtx Properties ctx,
			final @CacheTrx String trxName)
	{
		return queryBL.createQueryBuilder(I_DD_NetworkDistribution.class, ctx, trxName)
				.addOnlyActiveRecordsFilter()
				.filter(new EqualsQueryFilter<>(I_DD_NetworkDistribution.COLUMNNAME_IsHUDestroyed, true))
				.create()
				.firstOnly(I_DD_NetworkDistribution.class);
	}

	private Set<WarehouseId> retrieveWarehouseIdsForHUs(final List<I_M_HU> hus)
	{
		final Set<Integer> locatorRepoIds = hus.stream()
				.map(I_M_HU::getM_Locator_ID)
				.filter(locatorRepoId -> locatorRepoId > 0)
				.collect(ImmutableSet.toImmutableSet());

		return Services.get(IWarehouseDAO.class).getWarehouseIdsForLocatorRepoIds(locatorRepoIds);
	}

	@Override
	public List<org.compiere.model.I_M_Warehouse> retrieveWarehousesWhichContainNoneOf(final List<I_M_HU> hus)
	{
		if (hus.isEmpty())
		{
			// should never happen
			return Collections.emptyList();
		}

		// used for deciding the org and context
		final I_M_HU firstHU = hus.get(0);

		final OrgId orgId = OrgId.ofRepoId(firstHU.getAD_Org_ID());

		final Set<WarehouseId> huWarehouseIds = retrieveWarehouseIdsForHUs(hus);

		return Services.get(IWarehouseDAO.class).getByOrgId(orgId)
				.stream()
				.filter(warehouse -> !huWarehouseIds.contains(WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID())))
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public List<I_M_HU> retrieveByIds(@NonNull final Collection<HuId> huIds)
	{
		if (huIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return queryBL
				.createQueryBuilder(I_M_HU.class)
				.addInArrayFilter(I_M_HU.COLUMN_M_HU_ID, huIds)
				.create()
				.listImmutable(I_M_HU.class);
	}

	@Override
	public void setReservedByHUIds(@NonNull final Collection<HuId> huIds, final boolean reserved)
	{
		if (huIds.isEmpty())
		{
			return;
		}

		for (final I_M_HU hu : getByIds(huIds))
		{
			hu.setIsReserved(reserved);
			saveHU(hu);
		}
	}
}
