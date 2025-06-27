package de.metas.handlingunits.picking.slot.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.SetMultimap;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_PickingSlot;
import de.metas.handlingunits.model.I_M_PickingSlot_HU;
import de.metas.handlingunits.picking.slot.IHUPickingSlotDAO;
import de.metas.handlingunits.picking.slot.PickingSlotQuery;
import de.metas.picking.api.PickingSlotId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class HUPickingSlotDAO implements IHUPickingSlotDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public I_M_PickingSlot_HU retrievePickingSlotHU(@NonNull final de.metas.picking.model.I_M_PickingSlot pickingSlot, @NonNull final HuId huId)
	{
		return queryBL.createQueryBuilder(I_M_PickingSlot_HU.class, pickingSlot)
				.addEqualsFilter(I_M_PickingSlot_HU.COLUMNNAME_M_PickingSlot_ID, pickingSlot.getM_PickingSlot_ID())
				.addEqualsFilter(I_M_PickingSlot_HU.COLUMNNAME_M_HU_ID, huId)
				.create()
				.firstOnly(I_M_PickingSlot_HU.class);
	}

	@Override
	public List<I_M_PickingSlot_HU> retrievePickingSlotHUs(@NonNull final PickingSlotId pickingSlotId, @NonNull final Set<HuId> huIds)
	{
		if (huIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return queryBL.createQueryBuilder(I_M_PickingSlot_HU.class)
				.addEqualsFilter(I_M_PickingSlot_HU.COLUMNNAME_M_PickingSlot_ID, pickingSlotId)
				.addInArrayFilter(I_M_PickingSlot_HU.COLUMNNAME_M_HU_ID, huIds)
				.create()
				.list(I_M_PickingSlot_HU.class);
	}

	@Override
	public List<I_M_PickingSlot_HU> retrieveAllPickingSlotHUs(@NonNull final PickingSlotQuery query)
	{
		return queryAllPickingSlotHUs(query).list();
	}

	private IQueryBuilder<I_M_PickingSlot_HU> queryAllPickingSlotHUs(final @NonNull PickingSlotQuery query)
	{
		final IQueryBuilder<I_M_PickingSlot_HU> queryBuilder = queryBL.createQueryBuilder(I_M_PickingSlot_HU.class)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_M_PickingSlot_HU.COLUMNNAME_M_PickingSlot_ID)
				.orderBy(I_M_PickingSlot_HU.COLUMNNAME_M_PickingSlot_HU_ID);

		if (query.getOnlyPickingSlotIds() != null && !query.getOnlyPickingSlotIds().isEmpty())
		{
			queryBuilder.addInArrayFilter(I_M_PickingSlot_HU.COLUMNNAME_M_PickingSlot_ID, query.getOnlyPickingSlotIds());
		}
		if (query.getExcludePickingSlotIds() != null && !query.getExcludePickingSlotIds().isEmpty())
		{
			queryBuilder.addNotInArrayFilter(I_M_PickingSlot_HU.COLUMNNAME_M_PickingSlot_ID, query.getExcludePickingSlotIds());
		}
		return queryBuilder;
	}

	@Override
	public List<I_M_PickingSlot_HU> retrievePickingSlotHUs(@NonNull final Set<PickingSlotId> pickingSlotIds)
	{
		if (pickingSlotIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return queryBL.createQueryBuilder(I_M_PickingSlot_HU.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_PickingSlot_HU.COLUMNNAME_M_PickingSlot_ID, pickingSlotIds)
				.orderBy(I_M_PickingSlot_HU.COLUMNNAME_M_PickingSlot_HU_ID)
				.create()
				.list(I_M_PickingSlot_HU.class);
	}

	@Override
	public List<I_M_HU> retrieveAllHUs(final de.metas.picking.model.I_M_PickingSlot pickingSlot)
	{
		final List<I_M_HU> result = queryAllHUsOfPickingSlot(pickingSlot)
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

	private IQueryBuilder<I_M_HU> queryAllHUsOfPickingSlot(final de.metas.picking.model.I_M_PickingSlot pickingSlot)
	{
		return queryBL.createQueryBuilder(I_M_HU.class, pickingSlot)
				.addOnlyActiveRecordsFilter()
				.addInSubQueryFilter(I_M_HU.COLUMNNAME_M_HU_ID, I_M_PickingSlot_HU.COLUMNNAME_M_HU_ID, queryPickingSlotHUs(pickingSlot))
				.orderBy(I_M_HU.COLUMNNAME_M_HU_ID);
	}

	@Override
	public SetMultimap<PickingSlotId, HuId> retrieveAllHUIdsIndexedByPickingSlotId(
			final Collection<? extends de.metas.picking.model.I_M_PickingSlot> pickingSlots)
	{
		if (pickingSlots.isEmpty())
		{
			return ImmutableSetMultimap.of();
		}

		final Set<Integer> pickingSlotIds = pickingSlots.stream().map(de.metas.picking.model.I_M_PickingSlot::getM_PickingSlot_ID).collect(ImmutableSet.toImmutableSet());

		final LinkedHashMultimap<PickingSlotId, HuId> pickingSlotId2huIds = LinkedHashMultimap.create();

		// Retrieve current picking slot HUs
		pickingSlots.stream()
				.map(pickingSlot -> InterfaceWrapperHelper.create(pickingSlot, I_M_PickingSlot.class))
				.filter(pickingSlot -> pickingSlot.getM_HU_ID() > 0)
				.forEach(pickingSlot -> pickingSlotId2huIds.put(
						PickingSlotId.ofRepoId(pickingSlot.getM_PickingSlot_ID()),
						HuId.ofRepoId(pickingSlot.getM_HU_ID())));

		// Retrieve the HUs from picking slot queue.
		queryBL.createQueryBuilder(I_M_PickingSlot_HU.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_PickingSlot_HU.COLUMN_M_PickingSlot_ID, pickingSlotIds)
				.orderBy(I_M_PickingSlot_HU.COLUMN_M_PickingSlot_HU_ID)
				.create()
				.stream(I_M_PickingSlot_HU.class)
				.forEach(pickingSlotHU -> pickingSlotId2huIds.put(
						PickingSlotId.ofRepoId(pickingSlotHU.getM_PickingSlot_ID()),
						HuId.ofRepoId(pickingSlotHU.getM_HU_ID())));

		return ImmutableSetMultimap.copyOf(pickingSlotId2huIds);
	}

	@Override
	public I_M_PickingSlot retrievePickingSlotForHU(@NonNull final HuId huId)
	{
		final IQueryBuilder<I_M_PickingSlot> queryBuilder = queryBL.createQueryBuilder(I_M_PickingSlot.class);

		final ICompositeQueryFilter<I_M_PickingSlot> filters = queryBL.createCompositeQueryFilter(I_M_PickingSlot.class);
		filters.setJoinOr();

		//
		// Picking Slot has given HU as current HU
		filters.addEqualsFilter(I_M_PickingSlot.COLUMNNAME_M_HU_ID, huId);

		//
		// or given HU is in Picking Slot queue
		final IQuery<I_M_PickingSlot_HU> pickingSlotHUQuery = queryBL.createQueryBuilder(I_M_PickingSlot_HU.class)
				.addEqualsFilter(I_M_PickingSlot_HU.COLUMNNAME_M_HU_ID, huId)
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
		final ICompositeQueryFilter<I_M_HU> filters = queryBL.createCompositeQueryFilter(I_M_HU.class);
		filters.setJoinOr();

		//
		// Filter HUs which are open on Picking Slot
		{
			final IQuery<I_M_PickingSlot> pickingSlotsQuery = queryBL.createQueryBuilder(I_M_PickingSlot.class, contextProvider)
					.addOnlyActiveRecordsFilter()
					// NOTE: make sure that we are considering only those picking slots where M_HU_ID is set
					// If not, well, postgresql will be confused and will evaluate this expression like always false
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
		return !queryPickingSlotHUs(pickingSlot).anyMatch();
	}

	private IQuery<I_M_PickingSlot_HU> queryPickingSlotHUs(final de.metas.picking.model.I_M_PickingSlot pickingSlot)
	{
		return queryBL.createQueryBuilder(I_M_PickingSlot_HU.class, pickingSlot)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_PickingSlot_HU.COLUMNNAME_M_PickingSlot_ID, pickingSlot.getM_PickingSlot_ID())
				.create();
	}
}
