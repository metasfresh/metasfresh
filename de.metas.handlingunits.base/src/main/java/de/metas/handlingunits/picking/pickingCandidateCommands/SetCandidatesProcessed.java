package de.metas.handlingunits.picking.pickingCandidateCommands;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;

import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Picking_Candidate;
import de.metas.handlingunits.model.X_M_Picking_Candidate;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.sourcehu.HuId2SourceHUsService;
import de.metas.handlingunits.sourcehu.SourceHUsService;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;
import de.metas.picking.service.FreshPackingItemHelper;
import de.metas.picking.service.IFreshPackingItem;
import de.metas.picking.service.IPackingContext;
import de.metas.picking.service.IPackingService;
import de.metas.picking.service.PackingItemsMap;
import de.metas.picking.service.impl.HU2PackingItemsAllocator;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
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

public class SetCandidatesProcessed
{
	private static final Logger logger = LogManager.getLogger(SetCandidatesProcessed.class);

	private final HuId2SourceHUsService sourceHUsRepository;
	private PickingCandidateRepository pickingCandidateRepository;

	public SetCandidatesProcessed(
			@NonNull final HuId2SourceHUsService sourceHUsRepository,
			@NonNull final PickingCandidateRepository pickingCandidateRepository)
	{
		this.sourceHUsRepository = sourceHUsRepository;
		this.pickingCandidateRepository = pickingCandidateRepository;

	}

	public int perform(@NonNull final List<Integer> huIds, final int pickingSlotId, final OptionalInt shipmentScheduleId)
	{
		if (huIds.isEmpty())
		{
			return 0;
		}

		allocateHUsToShipmentSchedule(huIds, pickingSlotId, shipmentScheduleId);

		destroyEmptySourceHUs(huIds);

		return markCandidatesAsProcessed(huIds);
	}

	private void allocateHUsToShipmentSchedule(@NonNull final List<Integer> huIds, final int pickingSlotId, final OptionalInt shipmentScheduleId)
	{
		if (huIds.isEmpty())
		{
			return;
		}

		Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_M_HU.class) // HU2PackingItemsAllocator wants them to be out of trx
				.addInArrayFilter(I_M_HU.COLUMN_M_HU_ID, huIds)
				.create()
				.list(I_M_HU.class)
				.forEach(hu -> allocateHUToShipmentSchedule(hu, pickingSlotId, shipmentScheduleId));
	}

	private void allocateHUToShipmentSchedule(@NonNull final I_M_HU hu, final int pickingSlotId, final OptionalInt shipmentScheduleId)
	{
		final IFreshPackingItem itemToPack = createItemToPack(hu, shipmentScheduleId);

		final PackingItemsMap packingItemsMap = new PackingItemsMap();
		packingItemsMap.addUnpackedItem(itemToPack);

		final IPackingService packingService = Services.get(IPackingService.class);
		final IPackingContext packingContext = packingService.createPackingContext(Env.getCtx());
		packingContext.setPackingItemsMap(packingItemsMap); // don't know what to do with it, but i saw that it can't be null
		packingContext.setPackingItemsMapKey(pickingSlotId);

		// Allocate given HUs to "itemToPack"
		new HU2PackingItemsAllocator()
				.setItemToPack(itemToPack)
				.setPackingContext(packingContext)
				.setFromHUs(ImmutableList.of(hu))
				.allocate();
	}

	private IFreshPackingItem createItemToPack(@NonNull final I_M_HU hu, final OptionalInt shipmentScheduleId)
	{
		final Map<I_M_ShipmentSchedule, BigDecimal> scheds2Qtys = new HashMap<>();

		final List<I_M_Picking_Candidate> pickingCandidates = pickingCandidateRepository.retrievePickingCandidates(hu.getM_HU_ID());
		for (final I_M_Picking_Candidate pc : pickingCandidates)
		{
			if(shipmentScheduleId.isPresent() && shipmentScheduleId.getAsInt() != pc.getM_ShipmentSchedule_ID())
			{
				continue;
			}
			
			final I_M_ShipmentSchedule shipmentSchedule = pc.getM_ShipmentSchedule();
			final BigDecimal qty = pc.getQtyPicked();
			scheds2Qtys.put(shipmentSchedule, qty);
		}

		final IFreshPackingItem itemToPack = FreshPackingItemHelper.create(scheds2Qtys);
		return itemToPack;
	}

	private void destroyEmptySourceHUs(@NonNull final List<Integer> huIds)
	{
		final Collection<I_M_HU> sourceHUs = sourceHUsRepository.retrieveActualSourceHUs(huIds);

		final IHUStorageFactory storageFactory = Services.get(IHandlingUnitsBL.class).getStorageFactory();

		// clean up and unselect used up source HUs
		for (final I_M_HU sourceHU : sourceHUs)
		{
			if (!storageFactory.getStorage(sourceHU).isEmpty())
			{
				return;
			}
			takeSnapShotAndDestroyHU(sourceHU);
		}
	}

	private void takeSnapShotAndDestroyHU(@NonNull final I_M_HU sourceHU)
	{
		final SourceHUsService sourceHuService = SourceHUsService.get();
		sourceHuService.snapshotHuIfMarkedAsSourceHu(sourceHU);

		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		handlingUnitsBL.destroyIfEmptyStorage(sourceHU);

		Check.errorUnless(handlingUnitsBL.isDestroyed(sourceHU), "We invoked IHandlingUnitsBL.destroyIfEmptyStorage on an HU with empty storage, but its not destroyed; hu={}", sourceHU);
		logger.info("Source M_HU with M_HU_ID={} is now destroyed", sourceHU.getM_HU_ID());
	}

	private static int markCandidatesAsProcessed(final List<Integer> huIds)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQuery<I_M_Picking_Candidate> query = queryBL.createQueryBuilder(I_M_Picking_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Picking_Candidate.COLUMNNAME_Status, X_M_Picking_Candidate.STATUS_IP)
				.addInArrayFilter(I_M_Picking_Candidate.COLUMNNAME_M_HU_ID, huIds)
				.create();

		final ICompositeQueryUpdater<I_M_Picking_Candidate> updater = queryBL.createCompositeQueryUpdater(I_M_Picking_Candidate.class)
				.addSetColumnValue(I_M_Picking_Candidate.COLUMNNAME_Status, X_M_Picking_Candidate.STATUS_PR);

		return query.updateDirectly(updater);
	}
}
