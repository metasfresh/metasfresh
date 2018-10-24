package de.metas.handlingunits.impl;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadByRepoIdAwares;
import static org.adempiere.model.InterfaceWrapperHelper.loadByRepoIdAwaresOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.math.BigDecimal;

/*
 * #%L
 * de.metas.handlingunits.base
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

import javax.annotation.Nullable;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.OrgId;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.adempiere.util.proxy.Cached;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.Adempiere;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.IHUAndItemsDAO;
import de.metas.handlingunits.IHUBuilder;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_DD_NetworkDistribution;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.handlingunits.reservation.HUReservationRepository;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.IteratorUtils;
import lombok.NonNull;

public class HandlingUnitsDAO implements IHandlingUnitsDAO
{
	private final transient Logger logger = LogManager.getLogger(getClass());

	// NOTE: it's public only for testing purposes
	public static final int PACKING_ITEM_TEMPLATE_HU_PI_Item_ID = 540004;

	public static final int VIRTUAL_HU_PI_Item_ID = 101;

	private final IHUAndItemsDAO defaultHUAndItemsDAO;

	public HandlingUnitsDAO()
	{
		defaultHUAndItemsDAO = new CachedIfInTransactionHUAndItemsDAO();
	}

	private final IHUAndItemsDAO getHUAndItemsDAO()
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
	@Cached
	public I_M_HU_PI_Item retrievePackingItemTemplatePIItem(@CacheCtx final Properties ctx)
	{
		final I_M_HU_PI_Item noPIItem = InterfaceWrapperHelper.create(ctx, PACKING_ITEM_TEMPLATE_HU_PI_Item_ID, I_M_HU_PI_Item.class, ITrx.TRXNAME_None);
		if (noPIItem == null)
		{
			throw new AdempiereException("@NotFound@ @M_HU_PI_Item_ID@ NoPI (ID=" + PACKING_ITEM_TEMPLATE_HU_PI_Item_ID + ")");
		}

		return noPIItem;
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
		final I_M_HU_PI_Item virtualPIItem = InterfaceWrapperHelper.create(ctx, VIRTUAL_HU_PI_Item_ID, I_M_HU_PI_Item.class, ITrx.TRXNAME_None);
		if (virtualPIItem == null)
		{
			throw new AdempiereException("@NotFound@ @M_HU_PI_Item_ID@ virtual (ID=" + VIRTUAL_HU_PI_Item_ID + ")");
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
		final I_M_HU_PI pi = Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_PI.class, ctx, ITrx.TRXNAME_None)
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
	public int getPackingItemTemplate_HU_PI_Item_ID()
	{
		return PACKING_ITEM_TEMPLATE_HU_PI_Item_ID;
	}

	@Override
	public int getVirtual_HU_PI_Item_ID()
	{
		return VIRTUAL_HU_PI_Item_ID;
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
	public void setParentItem(final I_M_HU hu, final I_M_HU_Item parentItem)
	{
		getHUAndItemsDAO().setParentItem(hu, parentItem);
	}

	@Override
	public List<I_M_HU> retrieveIncludedHUs(final I_M_HU_Item item)
	{
		return getHUAndItemsDAO().retrieveIncludedHUs(item);
	}

	@Override
	public List<I_M_HU> retrieveIncludedHUs(final I_M_HU hu)
	{
		Check.assumeNotNull(hu, "hu not null");

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
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

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
	public List<IPair<I_M_HU_PackingMaterial, Integer>> retrievePackingMaterialAndQtys(final I_M_HU hu)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

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

			final I_M_HU_PackingMaterial packingMaterial = handlingUnitsBL.getHUPackingMaterial(huItem);

			packingMaterials.add(ImmutablePair.of(packingMaterial, qty.intValueExact()));
		}
		return packingMaterials;
	}

	@Override
	public List<I_M_HU_PI_Item> retrievePIItems(final I_M_HU_PI handlingUnit, final I_C_BPartner partner)
	{
		final I_M_HU_PI_Version version = retrievePICurrentVersion(handlingUnit);
		return retrievePIItems(version, partner);
	}

	@Override
	public List<I_M_HU_PI_Item> retrievePIItems(
			@NonNull final I_M_HU_PI_Version version,
			@Nullable final I_C_BPartner partner)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(version);
		final String trxName = InterfaceWrapperHelper.getTrxName(version);
		final int huPIVersionId = version.getM_HU_PI_Version_ID();

		final List<I_M_HU_PI_Item> piItemsAll = retrieveAllPIItems(ctx, huPIVersionId, trxName);

		final int bpartnerId = partner == null ? -1 : partner.getC_BPartner_ID();
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
				final int itemBPartnerId = piItem.getC_BPartner_ID();

				//
				// Item is for a specific BPartner
				if (itemBPartnerId > 0)
				{
					// ... we were asked for a specific partner
					if (bpartnerId > 0)
					{
						// ... skip item if BPartners does not match
						if (itemBPartnerId != bpartnerId)
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
	 *
	 * @param ctx
	 * @param huPIVersionId M_HU_PI_Version_ID
	 * @param trxName
	 * @return
	 */
	@Cached(cacheName = I_M_HU_Item.Table_Name + "#by"
			+ "#" + I_M_HU_PI_Item.COLUMNNAME_M_HU_PI_Version_ID)
	/* package */List<I_M_HU_PI_Item> retrieveAllPIItems(
			final @CacheCtx Properties ctx,
			final int huPIVersionId,
			final @CacheTrx String trxName)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final List<I_M_HU_PI_Item> piItems = queryBL.createQueryBuilder(I_M_HU_PI_Item.class, ctx, trxName)
				.addEqualsFilter(I_M_HU_PI_Item.COLUMN_M_HU_PI_Version_ID, huPIVersionId)
				.create()
				// .setOnlyActiveRecords(true) // retrieve all records, not only the active ones
				.list(I_M_HU_PI_Item.class);

		return Collections.unmodifiableList(piItems);
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
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_PI_Version.class, ctx, trxName)
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
	public HuPackingInstructionsVersionId retrievePICurrentVersionId(I_M_HU_PI pi)
	{
		final I_M_HU_PI_Version piVersion = retrievePICurrentVersion(pi);
		return HuPackingInstructionsVersionId.ofRepoId(piVersion.getM_HU_PI_Version_ID());
	}

	@Override
	public I_M_HU_PI_Version retrievePICurrentVersion(final I_M_HU_PI pi)
	{
		final I_M_HU_PI_Version piVersion = retrievePICurrentVersionOrNull(pi);
		Check.assumeNotNull(piVersion, HUException.class, "No current version found for {}", pi);

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
	/* package */I_M_HU_PI_Version retrievePICurrentVersionOrNull(
			final @CacheCtx Properties ctx,
			final HuPackingInstructionsId piId,
			final @CacheTrx String trxName)
	{
		final I_M_HU_PI_Version version = Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_PI_Version.class, ctx, trxName)
				.addEqualsFilter(I_M_HU_PI_Version.COLUMN_M_HU_PI_ID, piId)
				.addEqualsFilter(I_M_HU_PI_Version.COLUMN_IsCurrent, true)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_M_HU_PI_Version.class);

		return version;
	}

	@Override
	public List<I_M_HU_PI_Item> retrievePIItemsForPackingMaterial(final I_M_HU_PackingMaterial pm)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_PI_Item.class, pm)
				.addEqualsFilter(I_M_HU_PI_Item.COLUMN_M_HU_PackingMaterial_ID, pm.getM_HU_PackingMaterial_ID())
				.addOnlyActiveRecordsFilter()
				.create()
				.list(I_M_HU_PI_Item.class);
	}

	@Override
	@Cached(cacheName = I_M_HU_PI.Table_Name + "#By#ctx")
	public List<I_M_HU_PI> retrieveAvailablePIs(@CacheCtx final Properties ctx)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_PI.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.create()
				.list(I_M_HU_PI.class);
	}

	@Override
	public List<I_M_HU_PI> retrieveAvailablePIsForOrg(final Properties ctx, final int adOrgId)
	{
		final int clientOrgID = Env.CTXVALUE_AD_Org_ID_System;
		final ImmutableList.Builder<I_M_HU_PI> huPIsForOrg = ImmutableList.builder();
		for (final I_M_HU_PI huPI : retrieveAvailablePIs(ctx))
		{
			if (huPI.getAD_Org_ID() != adOrgId && huPI.getAD_Org_ID() != clientOrgID)
			{
				continue;
			}
			huPIsForOrg.add(huPI);
		}

		return huPIsForOrg.build();
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

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_M_HU> queryBuilder = queryBL.createQueryBuilder(I_M_HU.class, ctx, trxName);

		final ICompositeQueryFilter<I_M_HU> filters = queryBuilder.getCompositeFilter();
		filters.addInArrayOrAllFilter(I_M_HU.COLUMN_M_Locator_ID, locatorIds);

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
		final IQueryBL queryBL = Services.get(IQueryBL.class);
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
			@Nullable final I_C_BPartner bpartner)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(huPI);
		final String trxName = InterfaceWrapperHelper.getTrxName(huPI);
		final HuPackingInstructionsId packingInstructionsId = HuPackingInstructionsId.ofRepoId(huPI.getM_HU_PI_ID());
		final BPartnerId bpartnerId = bpartner != null ? BPartnerId.ofRepoId(bpartner.getC_BPartner_ID()) : null;

		return retrieveParentPIItemsForParentPI(ctx, packingInstructionsId, huUnitType, bpartnerId, trxName);
	}

	@Cached
	List<I_M_HU_PI_Item> retrieveParentPIItemsForParentPI(
			@CacheCtx final Properties ctx,
			@NonNull final HuPackingInstructionsId packingInstructionsId,
			@Nullable final String huUnitType,
			final BPartnerId bpartnerId,
			@CacheTrx final String trxName)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

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
		piItemsQueryBuilder.addInArrayOrAllFilter(I_M_HU_PI_Item.COLUMN_C_BPartner_ID, null, bpartnerId);
		piItemsQueryBuilder.orderBy()
				.addColumn(I_M_HU_PI_Item.COLUMN_C_BPartner_ID, Direction.Descending, Nulls.Last) // lines with BPartner set, first
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
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final I_M_HU_PI_Item piItemForChild = queryBL.createQueryBuilder(I_M_HU_PI_Item.class, ctx)
				.addOnlyActiveRecordsFilter()

				// it's an PI-item of the the parent's PI
				.addEqualsFilter(I_M_HU_PI_Item.COLUMN_M_HU_PI_Version_ID, parentHU.getM_HU_PI_Version_ID())

				// it includes the childs's HU PI as one of its "child" PI
				.addEqualsFilter(I_M_HU_PI_Item.COLUMN_Included_HU_PI_ID, piOfChildHU.getM_HU_PI_ID())

				// it either has no C_BPartner_ID or a matching one
				.addInArrayFilter(I_M_HU_PI_Item.COLUMN_C_BPartner_ID, null, parentHU.getC_BPartner_ID())

				// order by C_BPartner_ID descending to favor any piItem with a matching C_BPartner_ID
				.orderBy().addColumn(I_M_HU_PI_Item.COLUMNNAME_C_BPartner_ID, false).endOrderBy()

				.create()
				.first(); // get the first one (favoring the one with C_BPartner_ID = parentHU.getC_BPartner_ID() if it exists)

		return piItemForChild;
	}

	@Override
	public I_M_HU_PackingMaterial retrievePackingMaterial(final I_M_HU_PI pi, final I_C_BPartner bpartner)
	{
		final I_M_HU_PI_Version piVersion = retrievePICurrentVersion(pi);
		return retrievePackingMaterial(piVersion, bpartner);
	}

	@Override
	public I_M_HU_PackingMaterial retrievePackingMaterial(final I_M_HU_PI_Version piVersion, final I_C_BPartner bpartner)
	{
		I_M_HU_PI_Item itemPM = null;
		for (final I_M_HU_PI_Item item : retrievePIItems(piVersion, bpartner))
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

		final I_M_HU_PackingMaterial packingMaterial = itemPM.getM_HU_PackingMaterial();
		return packingMaterial;
	}

	@Override
	public I_M_HU_PackingMaterial retrievePackingMaterial(final I_M_HU hu)
	{
		Check.assumeNotNull(hu, "hu not null");
		final I_M_HU_PI_Version piVersion = Services.get(IHandlingUnitsBL.class).getPIVersion(hu);
		final I_C_BPartner bpartner = hu.getC_BPartner();
		final I_M_HU_PackingMaterial pm = retrievePackingMaterial(piVersion, bpartner);
		return pm;
	}

	@Override
	public I_M_HU retrieveVirtualHU(final I_M_HU_Item itemMaterial)
	{
		final List<I_M_HU> vhus = retrieveVirtualHUs(itemMaterial);
		if (vhus == null || vhus.isEmpty())
		{
			return null;
		}
		else if (vhus.size() == 1)
		{
			return vhus.get(0);
		}
		else
		{
			throw new HUException("More than one VHU found for " + itemMaterial + ": " + vhus);
		}
	}

	@Override
	public List<I_M_HU> retrieveVirtualHUs(final I_M_HU_Item itemMaterial)
	{
		final List<I_M_HU> includedHUs = retrieveIncludedHUs(itemMaterial);
		return includedHUs;
	}

	@Override
	public List<I_M_HU> retrieveHUsForWarehouse(final Properties ctx, final WarehouseId warehouseId, final String trxName)
	{
		return createHUQueryBuilder()
				.setContext(ctx, trxName)
				.setOnlyTopLevelHUs()
				.addOnlyInWarehouseId(warehouseId)
				.list();
	}

	@Override
	public List<I_M_HU> retrieveHUsForWarehouses(final Properties ctx, final Collection<WarehouseId> warehouseIds, final String trxName)
	{
		return createHUQueryBuilder()
				.setContext(ctx, trxName)
				.setOnlyTopLevelHUs()
				.addOnlyInWarehouseIds(warehouseIds)
				.list();
	}

	@Override
	public List<I_M_HU> retrieveHUsForWarehousesAndProductId(final Properties ctx, final Collection<WarehouseId> warehouseIds, final int productId, final String trxName)
	{
		return createHUQueryBuilder()
				.setContext(ctx, trxName)
				.setOnlyTopLevelHUs()
				.addOnlyInWarehouseIds(warehouseIds)
				.addOnlyWithProductId(productId)
				.list();
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
		final HUReservationRepository huReservationRepository = Adempiere.getBean(HUReservationRepository.class);
		return huReservationRepository;
	}

	@Override
	public I_M_HU_PI_Item retrieveDefaultParentPIItem(
			@NonNull final I_M_HU_PI huPI,
			@Nullable final String huUnitType,
			@Nullable final I_C_BPartner bpartner)
	{
		//
		// Fetch all eligible parent PI Items
		final List<I_M_HU_PI_Item> parentPIItems = retrieveParentPIItemsForParentPI(huPI, huUnitType, bpartner);

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
					new Object[] { huPI, huUnitType, bpartner, defaultLUPIItems, parentPIItems });

			return parentPIItems.get(0);
		}
	}

	@Override
	@Cached(cacheName = I_M_HU_PI.Table_Name + "#by#AD_Org_ID#IsDefaultLU")
	public I_M_HU_PI retrieveDefaultLUOrNull(@CacheCtx final Properties ctx, final int adOrgId)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_PI.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClientOrSystem()
				.addEqualsFilter(I_M_HU_PI.COLUMN_IsDefaultLU, true)
				.addInArrayOrAllFilter(I_M_HU_PI.COLUMN_AD_Org_ID, Env.CTXVALUE_AD_Org_ID_System, adOrgId)
				.orderBy()
				.addColumn(I_M_HU_PI.COLUMN_AD_Client_ID, Direction.Descending, Nulls.Last)
				.addColumn(I_M_HU_PI.COLUMN_AD_Org_ID, Direction.Descending, Nulls.Last)
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
	I_DD_NetworkDistribution retrieveEmptiesDistributionNetwork(
			final @CacheCtx Properties ctx,
			final @CacheTrx String trxName)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_DD_NetworkDistribution.class, ctx, trxName)
				.addOnlyActiveRecordsFilter()
				.filter(new EqualsQueryFilter<I_DD_NetworkDistribution>(I_DD_NetworkDistribution.COLUMNNAME_IsHUDestroyed, true))
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

		final List<org.compiere.model.I_M_Warehouse> warehouses = Services.get(IWarehouseDAO.class).getByOrgId(orgId)
				.stream()
				.filter(warehouse -> !huWarehouseIds.contains(WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID())))
				.collect(ImmutableList.toImmutableList());

		return warehouses;
	}

	@Override
	public List<I_M_HU> retrieveByIds(@NonNull final Collection<HuId> huIds)
	{
		if (huIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU.class)
				.addInArrayFilter(I_M_HU.COLUMN_M_HU_ID, huIds)
				.create()
				.listImmutable(I_M_HU.class);
	}
}
