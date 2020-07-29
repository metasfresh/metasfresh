package de.metas.handlingunits.picking.candidate.commands;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimaps;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.sourcehu.HuId2SourceHUsService;
import de.metas.handlingunits.sourcehu.SourceHUsService;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;
import de.metas.picking.service.IPackingItem;
import de.metas.picking.service.PackingItemGroupingKey;
import de.metas.picking.service.PackingItemPart;
import de.metas.picking.service.PackingItemPartId;
import de.metas.picking.service.PackingItems;
import de.metas.picking.service.impl.HU2PackingItemsAllocator;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
public class ProcessHUsAndPickingCandidateCommand
{

	private static final Logger logger = LogManager.getLogger(ProcessHUsAndPickingCandidateCommand.class);
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final transient IHandlingUnitsDAO handlingUnitsRepo = Services.get(IHandlingUnitsDAO.class);
	private final transient IShipmentSchedulePA shipmentSchedulesRepo = Services.get(IShipmentSchedulePA.class);
	private final HuId2SourceHUsService sourceHUsRepository;
	private final PickingCandidateRepository pickingCandidateRepository;

	private final ImmutableListMultimap<HuId, PickingCandidate> pickingCandidatesByPickFromHUId;
	private final ImmutableSet<HuId> pickFromHuIds;
	private final boolean allowOverDelivery;

	@Builder
	private ProcessHUsAndPickingCandidateCommand(
			@NonNull final HuId2SourceHUsService sourceHUsRepository,
			@NonNull final PickingCandidateRepository pickingCandidateRepository,
			//
			@NonNull final List<PickingCandidate> pickingCandidates,
			@NonNull @Singular final Set<HuId> additionalPickFromHuIds,
			final boolean allowOverDelivery)
	{
		Check.assumeNotEmpty(pickingCandidates, "pickingCandidates is not empty");
		for (PickingCandidate pickingCandidate : pickingCandidates)
		{
			pickingCandidate.assertDraft();
			if (!pickingCandidate.getPickFrom().isPickFromHU())
			{
				throw new AdempiereException("Only picking from HU is allowed");
			}
		}

		this.sourceHUsRepository = sourceHUsRepository;
		this.pickingCandidateRepository = pickingCandidateRepository;

		this.pickingCandidatesByPickFromHUId = Multimaps.index(
				pickingCandidates,
				pickingCandidate -> pickingCandidate.getPickFrom().getHuId());

		this.pickFromHuIds = ImmutableSet.<HuId> builder()
				.addAll(pickingCandidatesByPickFromHUId.keySet())
				.addAll(additionalPickFromHuIds)
				.build();

		this.allowOverDelivery = allowOverDelivery;
	}

	public ImmutableList<PickingCandidate> perform()
	{
		allocateHUsToShipmentSchedule();
		destroyEmptySourceHUs();

		final ImmutableList<PickingCandidate> processedPickingCandidates = changeStatusToProcessedAndSave();

		return processedPickingCandidates;
	}

	private void allocateHUsToShipmentSchedule()
	{
		final List<I_M_HU> pickFromHUs = retrievePickFromHUsOutOfTrx(); // HU2PackingItemsAllocator wants them to be out of trx
		pickFromHUs.forEach(this::allocateHUToShipmentSchedule);
	}

	private List<I_M_HU> retrievePickFromHUsOutOfTrx()
	{
		return handlingUnitsRepo.getByIdsOutOfTrx(pickFromHuIds);
	}

	private void allocateHUToShipmentSchedule(@NonNull final I_M_HU hu)
	{
		final List<IPackingItem> itemsToPack = createItemsToPack(HuId.ofRepoId(hu.getM_HU_ID()));

		itemsToPack.forEach(itemToPack -> {
			HU2PackingItemsAllocator.builder()
					.itemToPack(itemToPack)
					.allowOverDelivery(allowOverDelivery)
					.pickFromHU(hu)
					.allocate();
		});
	}

	private ImmutableList<IPackingItem> createItemsToPack(final HuId huId)
	{
		final Map<PackingItemGroupingKey, IPackingItem> packingItems = new LinkedHashMap<>();

		getPickingCandidatesForHUId(huId)
				.stream()
				.map(this::createPackingItemPart)
				.map(PackingItems::newPackingItem)
				.forEach(item -> {
					packingItems.merge(item.getGroupingKey(), item, IPackingItem::addPartsAndReturn);
				});

		return ImmutableList.copyOf(packingItems.values());
	}

	private PackingItemPart createPackingItemPart(final PickingCandidate pc)
	{
		final ShipmentScheduleId shipmentScheduleId = pc.getShipmentScheduleId();
		final I_M_ShipmentSchedule sched = shipmentSchedulesRepo.getById(shipmentScheduleId);

		return PackingItems.newPackingItemPart(sched)
				.id(PackingItemPartId.of(shipmentScheduleId)) // TODO: include some picking candidate ID in partId
				.qty(pc.getQtyPicked())
				.build();
	}

	private ImmutableList<PickingCandidate> getPickingCandidatesForHUId(final HuId huId)
	{
		return pickingCandidatesByPickFromHUId.get(huId);
	}

	private ImmutableList<PickingCandidate> getPickingCandidates()
	{
		return ImmutableList.copyOf(pickingCandidatesByPickFromHUId.values());
	}

	private void destroyEmptySourceHUs()
	{
		final Collection<I_M_HU> sourceHUs = sourceHUsRepository.retrieveActualSourceHUs(pickFromHuIds);

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

	private ImmutableList<PickingCandidate> changeStatusToProcessedAndSave()
	{
		final ImmutableList<PickingCandidate> pickingCandidates = getPickingCandidates();

		pickingCandidates.forEach(PickingCandidate::changeStatusToProcessed);
		pickingCandidateRepository.saveAll(pickingCandidates);

		return pickingCandidates;
	}
}
