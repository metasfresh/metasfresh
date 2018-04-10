package de.metas.handlingunits.picking.pickingCandidateCommands;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;

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
import de.metas.picking.api.PickingConfigRepository;
import de.metas.picking.service.FreshPackingItemHelper;
import de.metas.picking.service.IFreshPackingItem;
import de.metas.picking.service.IPackingContext;
import de.metas.picking.service.IPackingService;
import de.metas.picking.service.PackingItemsMap;
import de.metas.picking.service.impl.HU2PackingItemsAllocator;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;

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

/**
 * Process picking candidate.
 *
 * The status will be changed from InProgress to Processed.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class ProcessPickingCandidateCommand
{

	private static final Logger logger = LogManager.getLogger(ProcessPickingCandidateCommand.class);
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);
	private final transient IPackingService packingService = Services.get(IPackingService.class);
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	private final HuId2SourceHUsService sourceHUsRepository;
	private final PickingCandidateRepository pickingCandidateRepository;
	private final PickingConfigRepository pickingConfigRepository;

	private final List<Integer> huIds;
	private final int pickingSlotId;
	private final int shipmentScheduleId;

	private ImmutableListMultimap<Integer, I_M_Picking_Candidate> pickingCandidatesByHUId = null; // lazy

	@Builder
	private ProcessPickingCandidateCommand(
			@NonNull final HuId2SourceHUsService sourceHUsRepository,
			@NonNull final PickingCandidateRepository pickingCandidateRepository,
			@NonNull final PickingConfigRepository pickingConfigRepository,
			@NonNull @Singular final List<Integer> huIds,
			final int pickingSlotId,
			final int shipmentScheduleId)
	{
		this.sourceHUsRepository = sourceHUsRepository;
		this.pickingCandidateRepository = pickingCandidateRepository;
		this.pickingConfigRepository = pickingConfigRepository;

		Preconditions.checkArgument(!huIds.isEmpty(), "huIds not empty");
		this.huIds = ImmutableList.copyOf(huIds);

		Preconditions.checkArgument(pickingSlotId > 0, "pickingSlotId > 0");
		this.pickingSlotId = pickingSlotId;

		this.shipmentScheduleId = shipmentScheduleId; // might not be set
	}

	public void perform()
	{
		allocateHUsToShipmentSchedule();
		destroyEmptySourceHUs();
		markCandidatesAsProcessed();
	}

	private void allocateHUsToShipmentSchedule()
	{
		final List<I_M_HU> hus = retrieveHUsOutOfTrx(); // HU2PackingItemsAllocator wants them to be out of trx
		hus.forEach(this::allocateHUToShipmentSchedule);
	}

	private List<I_M_HU> retrieveHUsOutOfTrx()
	{
		return queryBL.createQueryBuilderOutOfTrx(I_M_HU.class)
				.addInArrayFilter(I_M_HU.COLUMN_M_HU_ID, huIds)
				.create()
				.list(I_M_HU.class);
	}

	private void allocateHUToShipmentSchedule(@NonNull final I_M_HU hu)
	{
		final IFreshPackingItem itemToPack = createItemToPack(hu.getM_HU_ID());

		final PackingItemsMap packingItemsMap = new PackingItemsMap();
		packingItemsMap.addUnpackedItem(itemToPack);

		final IPackingContext packingContext = packingService.createPackingContext(Env.getCtx());
		packingContext.setPackingItemsMap(packingItemsMap); // don't know what to do with it, but i saw that it can't be null
		packingContext.setPackingItemsMapKey(pickingSlotId);

		final boolean isAllowOverdelivery = pickingConfigRepository.getPickingConfig().isAllowOverDelivery();

		// Allocate given HUs to "itemToPack"
		new HU2PackingItemsAllocator()
				.setItemToPack(itemToPack)
				.setAllowOverdelivery(isAllowOverdelivery)
				.setPackingContext(packingContext)
				.setFromHUs(ImmutableList.of(hu))
				.allocate();
	}

	private IFreshPackingItem createItemToPack(final int huId)
	{
		final Map<I_M_ShipmentSchedule, Quantity> scheds2Qtys = new IdentityHashMap<>();

		final List<I_M_Picking_Candidate> pickingCandidates = getPickingCandidatesForHUId(huId);
		for (final I_M_Picking_Candidate pc : pickingCandidates)
		{
			final int shipmentScheduleId = pc.getM_ShipmentSchedule_ID();
			final I_M_ShipmentSchedule shipmentSchedule = load(shipmentScheduleId, I_M_ShipmentSchedule.class);
			final BigDecimal qty = pc.getQtyPicked();
			scheds2Qtys.put(shipmentSchedule, Quantity.of(qty, pc.getC_UOM()));
		}

		final IFreshPackingItem itemToPack = FreshPackingItemHelper.create(scheds2Qtys);
		return itemToPack;
	}

	private List<I_M_Picking_Candidate> getPickingCandidatesForHUId(final int huId)
	{
		return getPickingCandidatesIndexedByHUId().get(huId);
	}

	private ImmutableListMultimap<Integer, I_M_Picking_Candidate> getPickingCandidatesIndexedByHUId()
	{
		if (pickingCandidatesByHUId == null)
		{
			pickingCandidatesByHUId = pickingCandidateRepository.retrievePickingCandidatesByHUIds(huIds)
					.stream()
					.filter(pc -> shipmentScheduleId <= 0 || shipmentScheduleId == pc.getM_ShipmentSchedule_ID())
					.collect(GuavaCollectors.toImmutableListMultimap(I_M_Picking_Candidate::getM_HU_ID));
		}
		return pickingCandidatesByHUId;
	}

	private void destroyEmptySourceHUs()
	{
		final Collection<I_M_HU> sourceHUs = sourceHUsRepository.retrieveActualSourceHUs(huIds);

		final IHUStorageFactory storageFactory = handlingUnitsBL.getStorageFactory();

		// clean up and unselect used up source HUs
		for (final I_M_HU sourceHU : sourceHUs)
		{
			if (!storageFactory.getStorage(sourceHU).isEmpty())
			{
				return;
			}
			takeSnapshotAndDestroyHU(sourceHU);
		}
	}

	private void takeSnapshotAndDestroyHU(@NonNull final I_M_HU sourceHU)
	{
		final SourceHUsService sourceHuService = SourceHUsService.get();
		sourceHuService.snapshotHuIfMarkedAsSourceHu(sourceHU);

		handlingUnitsBL.destroyIfEmptyStorage(sourceHU);

		Check.errorUnless(handlingUnitsBL.isDestroyed(sourceHU), "We invoked IHandlingUnitsBL.destroyIfEmptyStorage on an HU with empty storage, but its not destroyed; hu={}", sourceHU);
		logger.info("Source M_HU with M_HU_ID={} is now destroyed", sourceHU.getM_HU_ID());
	}

	private void markCandidatesAsProcessed()
	{
		getPickingCandidatesIndexedByHUId()
				.values()
				.forEach(this::markCandidateAsProcessed);
	}

	private void markCandidateAsProcessed(final I_M_Picking_Candidate pickingCandidate)
	{
		pickingCandidate.setStatus(X_M_Picking_Candidate.STATUS_PR);
		InterfaceWrapperHelper.save(pickingCandidate);
	}

	public Collection<I_M_Picking_Candidate> getProcessedPickingCandidates()
	{
		return getPickingCandidatesIndexedByHUId().values();
	}
}
