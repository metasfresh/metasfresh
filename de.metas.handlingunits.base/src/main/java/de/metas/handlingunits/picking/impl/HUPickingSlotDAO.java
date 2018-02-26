package de.metas.handlingunits.picking.impl;

import java.util.Collection;
import java.util.HashSet;

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

import java.util.List;
import java.util.Set;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Locator;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.SetMultimap;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_PickingSlot;
import de.metas.handlingunits.model.I_M_PickingSlot_HU;
import de.metas.handlingunits.model.I_M_Picking_Candidate;
import de.metas.handlingunits.picking.IHUPickingSlotDAO;

public class HUPickingSlotDAO implements IHUPickingSlotDAO
{
	@Override
	public I_M_PickingSlot_HU retrievePickingSlotHU(final de.metas.picking.model.I_M_PickingSlot pickingSlot, final I_M_HU hu)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_PickingSlot_HU.class, pickingSlot)
				.filter(new EqualsQueryFilter<I_M_PickingSlot_HU>(I_M_PickingSlot_HU.COLUMNNAME_M_PickingSlot_ID, pickingSlot.getM_PickingSlot_ID()))
				.filter(new EqualsQueryFilter<I_M_PickingSlot_HU>(I_M_PickingSlot_HU.COLUMNNAME_M_HU_ID, hu.getM_HU_ID()))
				.create()
				.firstOnly(I_M_PickingSlot_HU.class);
	}

	@Override
	public I_M_PickingSlot_HU retrievePickingSlotHU(final I_M_HU hu)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_PickingSlot_HU.class, hu)
				.filter(new EqualsQueryFilter<I_M_PickingSlot_HU>(I_M_PickingSlot_HU.COLUMNNAME_M_HU_ID, hu.getM_HU_ID()))
				.create()
				.firstOnly(I_M_PickingSlot_HU.class);
	}

	@Override
	public List<I_M_PickingSlot_HU> retrievePickingSlotHUsForBPartner(final I_C_BPartner bPartner)
	{
		final IQueryOrderBy orderBy = Services.get(IQueryBL.class).createQueryOrderByBuilder(I_M_PickingSlot_HU.class)
				.addColumn(I_M_PickingSlot_HU.COLUMNNAME_M_PickingSlot_HU_ID)
				.createQueryOrderBy();

		final IQuery<I_M_HU> subQuery = Services.get(IQueryBL.class).createQueryBuilder(I_M_HU.class, bPartner)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU.COLUMN_C_BPartner_ID, bPartner.getC_BPartner_ID())
				.create();

		return Services.get(IQueryBL.class).createQueryBuilder(I_M_PickingSlot_HU.class, bPartner)
				.addOnlyActiveRecordsFilter()
				.addInSubQueryFilter(I_M_PickingSlot_HU.COLUMNNAME_M_HU_ID, I_M_HU.COLUMNNAME_M_HU_ID, subQuery)
				.create()
				.setOrderBy(orderBy)
				.list(I_M_PickingSlot_HU.class);
	}

	private static IQueryBuilder<I_M_HU> retrieveAllHUsQuery(final de.metas.picking.model.I_M_PickingSlot pickingSlot)
	{
		final IQuery<I_M_PickingSlot_HU> queryPickingSlotHU = getPickingSlotHUQuery(pickingSlot);

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU.class, pickingSlot)
				.addOnlyActiveRecordsFilter()
				.addInSubQueryFilter(I_M_HU.COLUMNNAME_M_HU_ID, I_M_PickingSlot_HU.COLUMNNAME_M_HU_ID, queryPickingSlotHU)
				//
				.orderBy()
				.addColumn(I_M_HU.COLUMNNAME_M_HU_ID)
				.endOrderBy();

	}

	@Override
	public List<I_M_HU> retrieveAllHUs(final de.metas.picking.model.I_M_PickingSlot pickingSlot)
	{
		final List<I_M_HU> result = retrieveAllHUsQuery(pickingSlot)
				.create()
				.list(I_M_HU.class);

		final I_M_PickingSlot huPickingSlot = InterfaceWrapperHelper.create(pickingSlot, I_M_PickingSlot.class);
		if (huPickingSlot.getM_HU_ID() > 0)
		{
			final I_M_HU currentHU = huPickingSlot.getM_HU();
			result.add(currentHU);
		}

		return result;
	}

	@Override
	public Set<Integer> retrieveAllHUIds(final int pickingSlotId)
	{
		final I_M_PickingSlot pickingSlot = InterfaceWrapperHelper.load(pickingSlotId, I_M_PickingSlot.class);
		final Set<Integer> huIds = new HashSet<>(retrieveAllHUsQuery(pickingSlot).create().listIds());

		final int currentHUId = pickingSlot.getM_HU_ID();
		if (currentHUId > 0)
		{
			huIds.add(currentHUId);
		}

		return huIds;
	}

	@Override
	public SetMultimap<Integer, Integer> retrieveAllHUIdsIndexedByPickingSlotId(final Collection<? extends de.metas.picking.model.I_M_PickingSlot> pickingSlots)
	{
		if (pickingSlots.isEmpty())
		{
			return ImmutableSetMultimap.of();
		}

		final Set<Integer> pickingSlotIds = pickingSlots.stream().map(pickingSlot -> pickingSlot.getM_PickingSlot_ID()).collect(ImmutableSet.toImmutableSet());

		final LinkedHashMultimap<Integer, Integer> pickingSlotId2huIds = LinkedHashMultimap.create();

		// Retrieve current picking slot HUs
		pickingSlots.stream()
				.map(pickingSlot -> InterfaceWrapperHelper.create(pickingSlot, I_M_PickingSlot.class))
				.filter(pickingSlot -> pickingSlot.getM_HU_ID() > 0)
				.forEach(pickingSlot -> pickingSlotId2huIds.put(pickingSlot.getM_PickingSlot_ID(), pickingSlot.getM_HU_ID()));

		// Retrieve the HUs from picking slot queue.
		Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_PickingSlot_HU.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_PickingSlot_HU.COLUMN_M_PickingSlot_ID, pickingSlotIds)
				.orderBy(I_M_PickingSlot_HU.COLUMN_M_PickingSlot_HU_ID)
				.create()
				.stream(I_M_PickingSlot_HU.class)
				.forEach(pickingSlotHU -> pickingSlotId2huIds.put(pickingSlotHU.getM_PickingSlot_ID(), pickingSlotHU.getM_HU_ID()));

		return ImmutableSetMultimap.copyOf(pickingSlotId2huIds);
	}

	@Override
	public I_M_PickingSlot retrievePickingSlotForCurrentHU(final I_M_HU hu)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_PickingSlot.class, hu)
				.addEqualsFilter(I_M_PickingSlot.COLUMNNAME_M_HU_ID, hu.getM_HU_ID())
				.create()
				.firstOnly(I_M_PickingSlot.class);
	}

	@Override
	public I_M_PickingSlot retrievePickingSlotForHU(final I_M_HU hu)
	{
		final IQueryBuilder<I_M_PickingSlot> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_PickingSlot.class, hu);

		final ICompositeQueryFilter<I_M_PickingSlot> filters = Services.get(IQueryBL.class).createCompositeQueryFilter(I_M_PickingSlot.class);
		filters.setJoinOr();

		//
		// Picking Slot has given HU as current HU
		filters.addEqualsFilter(I_M_PickingSlot.COLUMNNAME_M_HU_ID, hu.getM_HU_ID());

		//
		// or given HU is in Picking Slot queue
		final IQuery<I_M_PickingSlot_HU> pickingSlotHUQuery = Services.get(IQueryBL.class).createQueryBuilder(I_M_PickingSlot_HU.class, hu)
				.addEqualsFilter(I_M_PickingSlot_HU.COLUMNNAME_M_HU_ID, hu.getM_HU_ID())
				.create();
		filters.addInSubQueryFilter(de.metas.picking.model.I_M_PickingSlot.COLUMNNAME_M_PickingSlot_ID,
				I_M_PickingSlot_HU.COLUMNNAME_M_PickingSlot_ID,
				pickingSlotHUQuery);

		//
		// Retrieve Picking Slot
		return queryBuilder
				.filter(filters)
				.create()
				.firstOnly(I_M_PickingSlot.class);
	}

	@Override
	public IQueryFilter<I_M_HU> createHUOnPickingSlotQueryFilter(final Object contextProvider)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final ICompositeQueryFilter<I_M_HU> filters = queryBL.createCompositeQueryFilter(I_M_HU.class);
		filters.setJoinOr();

		//
		// Filter HUs which are open on Picking Slot
		{
			final IQuery<I_M_PickingSlot> pickingSlotsQuery = queryBL.createQueryBuilder(I_M_PickingSlot.class, contextProvider)
					.addOnlyActiveRecordsFilter()
					// NOTE: make sure that we are considering only those picking slots where M_HU_ID is set
					// If not, well, postgresql will be confused and it will look at this query like the virgin to a black cock
					// ... and will evaluate this expression like always false
					.addNotEqualsFilter(I_M_PickingSlot.COLUMNNAME_M_HU_ID, null)
					.create();
			filters.addInSubQueryFilter(I_M_HU.COLUMNNAME_M_HU_ID,
					I_M_PickingSlot.COLUMNNAME_M_HU_ID,
					pickingSlotsQuery);
		}

		//
		// Filter HUs which are in Picking Slot queue
		{
			final IQuery<I_M_PickingSlot_HU> pickingSlotsQueueQuery = queryBL.createQueryBuilder(I_M_PickingSlot_HU.class, contextProvider)
					.addOnlyActiveRecordsFilter()
					.addNotEqualsFilter(I_M_PickingSlot_HU.COLUMN_M_HU_ID, null) // M_HU_ID IS NOT NULL
					.create();
			filters.addInSubQueryFilter(I_M_HU.COLUMNNAME_M_HU_ID,
					I_M_PickingSlot_HU.COLUMNNAME_M_HU_ID,
					pickingSlotsQueueQuery);
		}

		return filters;
	}

	@Override
	public boolean isEmpty(final de.metas.picking.model.I_M_PickingSlot pickingSlot)
	{
		final I_M_PickingSlot huPickingSlot = InterfaceWrapperHelper.create(pickingSlot, I_M_PickingSlot.class);
		final IQuery<I_M_PickingSlot_HU> queryPickingSlotHU = getPickingSlotHUQuery(huPickingSlot);

		//
		// PickingSlot-HU assignments are empty if there's no entry found (d'uh)
		final boolean isEmpty = !queryPickingSlotHU.match();
		return isEmpty;
	}

	private static IQuery<I_M_PickingSlot_HU> getPickingSlotHUQuery(final de.metas.picking.model.I_M_PickingSlot pickingSlot)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_PickingSlot_HU.class, pickingSlot)
				.filter(new EqualsQueryFilter<I_M_PickingSlot_HU>(I_M_PickingSlot_HU.COLUMNNAME_M_PickingSlot_ID, pickingSlot.getM_PickingSlot_ID()))
				.create()
				.setOnlyActiveRecords(true);
	}

	@Override
	public List<I_M_PickingSlot> retrievePickingSlots(final I_C_BPartner partner, final I_M_Locator locator)
	{
		Check.assumeNotNull(partner, "partner not null");
		Check.assumeNotNull(locator, "locator not null");

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_PickingSlot.class, partner)
				.addEqualsFilter(de.metas.picking.model.I_M_PickingSlot.COLUMNNAME_C_BPartner_ID, partner.getC_BPartner_ID())
				.addEqualsFilter(I_M_PickingSlot.COLUMNNAME_M_Locator_ID, locator.getM_Locator_ID())
				.create()
				.setOnlyActiveRecords(true)
				.list(I_M_PickingSlot.class);
	}

	@Override
	@Cached(cacheName = I_M_Picking_Candidate.Table_Name + "#by#" + I_M_HU.COLUMNNAME_M_HU_ID)
	public boolean isHuIdPicked(final int huId)
	{
		final boolean isAlreadyPicked = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Picking_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Picking_Candidate.COLUMNNAME_M_HU_ID, huId)
				.create()
				.match();
		return isAlreadyPicked;
	}

	@Override
	public boolean isPickingRackSystem(final int pickingSlotId)
	{
		if (pickingSlotId <= 0)
		{
			return false;
		}

		return retrieveAllPickingSlotIdsWhichAreRackSystems().contains(pickingSlotId);
	}

	@Cached(cacheName = I_M_PickingSlot.Table_Name + "#by#" + I_M_PickingSlot.COLUMNNAME_IsPickingRackSystem, expireMinutes = Cached.EXPIREMINUTES_Never)
	@Override
	public Set<Integer> retrieveAllPickingSlotIdsWhichAreRackSystems()
	{
		final List<Integer> pickingSlotIds = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_M_PickingSlot.class)
				.addEqualsFilter(I_M_PickingSlot.COLUMNNAME_IsPickingRackSystem, true)
				.addOnlyActiveRecordsFilter()
				.create()
				.listIds();

		return ImmutableSet.copyOf(pickingSlotIds);
	}
}
