package de.metas.handlingunits.picking.candidate.commands;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.slf4j.Logger;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.picking.PickingCandidateStatus;
import de.metas.handlingunits.sourcehu.HuId2SourceHUsService;
import de.metas.handlingunits.sourcehu.SourceHUsService;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;
import de.metas.picking.api.PickingConfigRepository;
import de.metas.picking.service.IPackingItem;
import de.metas.picking.service.PackingItemPart;
import de.metas.picking.service.PackingItemPartId;
import de.metas.picking.service.PackingItemParts;
import de.metas.picking.service.PackingItems;
import de.metas.picking.service.impl.HU2PackingItemsAllocator;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
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
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final transient IShipmentSchedulePA shipmentSchedulesRepo = Services.get(IShipmentSchedulePA.class);

	private final HuId2SourceHUsService sourceHUsRepository;
	private final PickingCandidateRepository pickingCandidateRepository;
	private final PickingConfigRepository pickingConfigRepository;

	private final Set<HuId> huIds;
	private final ShipmentScheduleId shipmentScheduleId;

	private ImmutableListMultimap<HuId, PickingCandidate> pickingCandidatesByHUId = null; // lazy
	private ImmutableList<PickingCandidate> processedPickingCandidates = null;

	@Builder
	private ProcessPickingCandidateCommand(
			@NonNull final HuId2SourceHUsService sourceHUsRepository,
			@NonNull final PickingCandidateRepository pickingCandidateRepository,
			@NonNull final PickingConfigRepository pickingConfigRepository,
			@NonNull @Singular final List<HuId> huIds,
			@Nullable final ShipmentScheduleId shipmentScheduleId)
	{
		Preconditions.checkArgument(!huIds.isEmpty(), "huIds not empty");

		this.sourceHUsRepository = sourceHUsRepository;
		this.pickingCandidateRepository = pickingCandidateRepository;

		this.pickingConfigRepository = pickingConfigRepository;

		this.huIds = ImmutableSet.copyOf(huIds);
		this.shipmentScheduleId = shipmentScheduleId; // might not be set
	}

	public void perform()
	{
		allocateHUsToShipmentSchedule();
		destroyEmptySourceHUs();
		changeStatusToProcessedAndSave();
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
		final IPackingItem itemToPack = createItemToPack(HuId.ofRepoId(hu.getM_HU_ID()));

		final boolean allowOverDelivery = pickingConfigRepository.getPickingConfig().isAllowOverDelivery();

		HU2PackingItemsAllocator.builder()
				.itemToPack(itemToPack)
				.allowOverDelivery(allowOverDelivery)
				.fromHU(hu)
				.allocate();
	}

	private IPackingItem createItemToPack(final HuId huId)
	{
		final PackingItemParts parts = getPickingCandidatesForHUId(huId)
				.stream()
				.map(this::createPackingItemPart)
				.collect(PackingItemParts.collect());

		return PackingItems.newPackingItem(parts);
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
		return getPickingCandidatesIndexedByHUId().get(huId);
	}

	private ImmutableListMultimap<HuId, PickingCandidate> getPickingCandidatesIndexedByHUId()
	{
		if (pickingCandidatesByHUId == null)
		{
			pickingCandidatesByHUId = pickingCandidateRepository.getByHUIds(huIds)
					.stream()
					.filter(pc -> shipmentScheduleId == null || Objects.equals(shipmentScheduleId, pc.getShipmentScheduleId()))
					.collect(GuavaCollectors.toImmutableListMultimap(PickingCandidate::getHuId));
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

	private void changeStatusToProcessedAndSave()
	{
		this.processedPickingCandidates = getPickingCandidatesIndexedByHUId()
				.values()
				.stream()
				.peek(this::changeStatusToProcessed)
				.collect(ImmutableList.toImmutableList());

		pickingCandidateRepository.saveAll(processedPickingCandidates);
	}

	private void changeStatusToProcessed(final PickingCandidate pickingCandidate)
	{
		pickingCandidate.setStatus(PickingCandidateStatus.Processed);
	}

	public Collection<PickingCandidate> getProcessedPickingCandidates()
	{
		Check.assumeNotEmpty(processedPickingCandidates, "processedPickingCandidates is not empty");
		return processedPickingCandidates;
	}
}
