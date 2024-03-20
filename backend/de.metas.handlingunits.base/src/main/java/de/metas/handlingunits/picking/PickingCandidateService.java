package de.metas.handlingunits.picking;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.ad_reference.ADRefList;
import de.metas.ad_reference.ADReferenceService;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.picking.candidate.commands.AddQtyToHUCommand;
import de.metas.handlingunits.picking.candidate.commands.ClosePickingCandidateCommand;
import de.metas.handlingunits.picking.candidate.commands.CreatePickingCandidatesCommand;
import de.metas.handlingunits.picking.candidate.commands.PickHUCommand;
import de.metas.handlingunits.picking.candidate.commands.PickHUResult;
import de.metas.handlingunits.picking.candidate.commands.ProcessHUsAndPickingCandidateCommand;
import de.metas.handlingunits.picking.candidate.commands.ProcessPickingCandidatesCommand;
import de.metas.handlingunits.picking.candidate.commands.ProcessPickingCandidatesRequest;
import de.metas.handlingunits.picking.candidate.commands.ProcessPickingCandidatesResult;
import de.metas.handlingunits.picking.candidate.commands.RejectPickingCommand;
import de.metas.handlingunits.picking.candidate.commands.RejectPickingResult;
import de.metas.handlingunits.picking.candidate.commands.RemoveHUFromPickingSlotCommand;
import de.metas.handlingunits.picking.candidate.commands.RemoveQtyFromHUCommand;
import de.metas.handlingunits.picking.candidate.commands.ReviewQtyPickedCommand;
import de.metas.handlingunits.picking.candidate.commands.SetHuPackingInstructionIdCommand;
import de.metas.handlingunits.picking.candidate.commands.UnProcessPickingCandidatesAndRestoreSourceHUsCommand;
import de.metas.handlingunits.picking.candidate.commands.UnProcessPickingCandidatesCommand;
import de.metas.handlingunits.picking.candidate.commands.UnProcessPickingCandidatesResult;
import de.metas.handlingunits.picking.plan.generator.CreatePickingPlanCommand;
import de.metas.handlingunits.picking.plan.generator.CreatePickingPlanRequest;
import de.metas.handlingunits.picking.plan.model.PickingPlan;
import de.metas.handlingunits.picking.requests.AddQtyToHURequest;
import de.metas.handlingunits.picking.requests.CloseForShipmentSchedulesRequest;
import de.metas.handlingunits.picking.requests.PickRequest;
import de.metas.handlingunits.picking.requests.ProcessPickingRequest;
import de.metas.handlingunits.picking.requests.RejectPickingRequest;
import de.metas.handlingunits.picking.requests.RemoveQtyFromHURequest;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.sourcehu.HuId2SourceHUsService;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.OrderId;
import de.metas.picking.api.PickingConfigRepository;
import de.metas.picking.api.PickingSlotId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

/*
 * #%L
 * metasfresh-webui-api
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

@Service
@RequiredArgsConstructor
public class PickingCandidateService
{
	private final PickingConfigRepository pickingConfigRepository;
	private final PickingCandidateRepository pickingCandidateRepository;
	private final HuId2SourceHUsService sourceHUsRepository;
	private final IHUAttributesBL huAttributesBL = Services.get(IHUAttributesBL.class);
	private final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
	private final HUReservationService huReservationService;
	private final IBPartnerBL bpartnersService;
	private final ADReferenceService adReferenceService;
	private final InventoryService inventoryService;

	public List<PickingCandidate> getByIds(final Set<PickingCandidateId> pickingCandidateIds)
	{
		return pickingCandidateRepository.getByIds(pickingCandidateIds);
	}

	public List<PickingCandidate> getByShipmentScheduleIdsAndStatus(
			@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds,
			@NonNull final PickingCandidateStatus status)
	{
		return pickingCandidateRepository.getByShipmentScheduleIdsAndStatus(shipmentScheduleIds, status);
	}

	public boolean existsPickingCandidates(@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		return pickingCandidateRepository.existsPickingCandidates(shipmentScheduleIds);
	}

	public PickHUResult pickHU(final PickRequest request)
	{
		return PickHUCommand.builder()
				.pickingCandidateRepository(pickingCandidateRepository)
				.request(request)
				.build()
				.perform();
	}

	public RejectPickingResult rejectPicking(@NonNull final RejectPickingRequest request)
	{
		return RejectPickingCommand.builder()
				.pickingCandidateRepository(pickingCandidateRepository)
				.request(request)
				.build()
				.perform();
	}

	/**
	 * @return the quantity which was effectively added
	 */
	@NonNull
	public Quantity addQtyToHU(@NonNull final AddQtyToHURequest request)
	{
		return AddQtyToHUCommand.builder()
				.pickingCandidateRepository(pickingCandidateRepository)
				.pickingCandidateService(this)
				.request(request)
				.build()
				.performAndGetQtyPicked();
	}

	public void removeQtyFromHU(final RemoveQtyFromHURequest request)
	{
		RemoveQtyFromHUCommand.builder()
				.sourceHUsRepository(sourceHUsRepository)
				.pickingCandidateRepository(pickingCandidateRepository)
				.request(request)
				.build()
				.perform();
	}

	public void removeHUFromPickingSlot(final HuId huId)
	{
		RemoveHUFromPickingSlotCommand.builder()
				.pickingCandidateRepository(pickingCandidateRepository)
				.huId(huId)
				.build()
				.perform();
	}

	/**
	 * For the given {@code huIds}, this method does two things:
	 * <ul>
	 * <li>Retrieves the source HUs (if any) of the the given {@code huIds} and if they are empty creates a snapshot and destroys them</li>
	 * <li>selects the picking candidates that reference those HUs
	 * and have {@code status == 'IP'} (in progress) and updates them to {@code status='PR'} (processed).
	 * No model interceptors etc will be fired.</li>
	 * </ul>
	 */
	public void processForHUIds(
			@NonNull final Set<HuId> pickFromHuIds,
			@Nullable final ShipmentScheduleId shipmentScheduleId)
	{
		final ProcessPickingRequest request = ProcessPickingRequest.builder()
				.huIds(ImmutableSet.copyOf(pickFromHuIds))
				.shipmentScheduleId(shipmentScheduleId)
				.build();

		processForHUIds(request);
	}

	public void processForHUIds(@NonNull final ProcessPickingRequest request)
	{
		final ImmutableSet<HuId> pickFromHuIds = request.getHuIds();
		final ShipmentScheduleId shipmentScheduleId = request.getShipmentScheduleId();

		final List<PickingCandidate> pickingCandidatesToProcess = pickingCandidateRepository.getByHUIds(pickFromHuIds)
				.stream()
				.filter(PickingCandidate::isDraft)
				.filter(pc -> shipmentScheduleId == null || shipmentScheduleId.equals(pc.getShipmentScheduleId()))
				.collect(ImmutableList.toImmutableList());

		for(final PickingCandidate pickingCandidate : pickingCandidatesToProcess)
		{
			validateAttributes(pickingCandidate);
		}
		//
		// Process those picking candidates
		final ImmutableList<PickingCandidate> processedPickingCandidates = ProcessHUsAndPickingCandidateCommand.builder()
				.sourceHUsRepository(sourceHUsRepository)
				.pickingCandidateRepository(pickingCandidateRepository)
				.pickingCandidates(pickingCandidatesToProcess)
				.additionalPickFromHuIds(pickFromHuIds)
				.onOverDelivery(getOnOverDelivery(request.isShouldSplitHUIfOverDelivery()))
				.ppOrderId(request.getPpOrderId())
				.build()
				.perform();

		//
		// Automatically close those processed picking candidates which are NOT on a rack system picking slot. (gh2740)
		ClosePickingCandidateCommand.builder()
				.pickingCandidateRepository(pickingCandidateRepository)
				.pickingCandidates(processedPickingCandidates)
				.pickingSlotIsRackSystem(false)
				.build()
				.perform();
	}

	private void validateAttributes(@NonNull final PickingCandidate pickingCandidate)
	{
		final HuId huId = pickingCandidate.getPickFrom().getHuId();

		if (huId == null)
		{
			// nothing to do
			return;
		}

		final ShipmentScheduleId shipmentScheduleId = pickingCandidate.getShipmentScheduleId();
		final I_M_ShipmentSchedule shipmentScheduleRecord = shipmentSchedulePA.getById(shipmentScheduleId);
		final ProductId productId = ProductId.ofRepoId(shipmentScheduleRecord.getM_Product_ID());

		huAttributesBL.validateMandatoryPickingAttributes(huId, productId);
	}

	public ProcessPickingCandidatesResult process(@NonNull final Set<PickingCandidateId> pickingCandidateIds)
	{
		return process(ProcessPickingCandidatesRequest.builder()
				.pickingCandidateIds(pickingCandidateIds)
				.build());
	}

	public ProcessPickingCandidatesResult process(@NonNull final ProcessPickingCandidatesRequest request)
	{
		return ProcessPickingCandidatesCommand.builder()
				.pickingCandidateRepository(pickingCandidateRepository)
				.inventoryService(inventoryService)
				.request(request)
				.build()
				.execute();
	}

	public UnProcessPickingCandidatesResult unprocess(@NonNull final PickingCandidateId pickingCandidateId)
	{
		return unprocess(ImmutableSet.of(pickingCandidateId));
	}

	public UnProcessPickingCandidatesResult unprocess(@NonNull final Set<PickingCandidateId> pickingCandidateIds)
	{
		return UnProcessPickingCandidatesCommand.builder()
				.pickingCandidateRepository(pickingCandidateRepository)
				.pickingCandidateIds(pickingCandidateIds)
				.build()
				.execute();
	}

	public void unprocessAndRestoreSourceHUsByHUId(final HuId huId)
	{
		UnProcessPickingCandidatesAndRestoreSourceHUsCommand.builder()
				.sourceHUsRepository(sourceHUsRepository)
				.pickingCandidateRepository(pickingCandidateRepository)
				.huId(huId)
				.build()
				.perform();
	}

	public void closeForShipmentSchedules(@NonNull final CloseForShipmentSchedulesRequest request)
	{
		final List<PickingCandidate> pickingCandidates = pickingCandidateRepository.getByShipmentScheduleIdsAndStatus(
				request.getShipmentScheduleIds(),
				PickingCandidateStatus.Processed);

		ClosePickingCandidateCommand.builder()
				.pickingCandidateRepository(pickingCandidateRepository)
				.pickingCandidates(pickingCandidates)
				.pickingSlotIsRackSystem(request.getPickingSlotIsRackSystem())
				.failOnError(request.isFailOnError())
				.build()
				.perform();
	}

	public void inactivateForHUId(@NonNull final HuId huId)
	{
		inactivateForHUIds(ImmutableSet.of(huId));
	}

	public void inactivateForHUIds(final Collection<HuId> huIds)
	{
		// tolerate empty
		if (huIds.isEmpty())
		{
			return;
		}

		pickingCandidateRepository.inactivateForHUIds(huIds);
	}

	public boolean isHuIdPicked(@NonNull final HuId huId)
	{
		return pickingCandidateRepository.isHuIdPicked(huId);
	}

	public PickingCandidate setQtyReviewed(@NonNull final PickingCandidateId pickingCandidateId, @NonNull final BigDecimal qtyReviewed)
	{
		return ReviewQtyPickedCommand.builder()
				.pickingCandidateRepository(pickingCandidateRepository)
				.pickingCandidateId(pickingCandidateId)
				.qtyReviewed(qtyReviewed)
				.build()
				.perform();
	}

	public List<PickingCandidate> setHuPackingInstructionId(final Set<PickingCandidateId> pickingCandidateIds, final PackToSpec packToSpec)
	{
		return SetHuPackingInstructionIdCommand.builder()
				.pickingCandidateRepository(pickingCandidateRepository)
				.pickingCandidateIds(pickingCandidateIds)
				.packToSpec(packToSpec)
				.build()
				.perform();

	}

	public PickHUResult createAndSavePickingCandidates(@NonNull final PickRequest request)
	{
		return CreatePickingCandidatesCommand.builder()
				.pickingCandidateRepository(pickingCandidateRepository)
				.request(request)
				.build()
				.perform();
	}

	public PickingPlan createPlan(@NonNull final CreatePickingPlanRequest request)
	{
		return CreatePickingPlanCommand.builder()
				.bpartnersService(bpartnersService)
				.huReservationService(huReservationService)
				.pickingCandidateRepository(pickingCandidateRepository)
				//
				.request(request)
				//
				.build()
				.execute();
	}

	public void deleteDraftPickingCandidatesByShipmentScheduleId(@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		final List<PickingCandidate> draftCandidates = pickingCandidateRepository.getByShipmentScheduleIdsAndStatus(shipmentScheduleIds, PickingCandidateStatus.Draft);
		deleteDraftPickingCandidates(draftCandidates);
	}

	public void deleteDraftPickingCandidates(final List<PickingCandidate> draftCandidates)
	{
		draftCandidates.forEach(PickingCandidate::assertDraft);
		pickingCandidateRepository.deletePickingCandidates(draftCandidates);
	}

	public ADRefList getQtyRejectedReasons()
	{
		return adReferenceService.getRefListById(QtyRejectedReasonCode.REFERENCE_ID);
	}

	@NonNull
	private OnOverDelivery getOnOverDelivery(final boolean splitHUIfOverDelivery)
	{
		return OnOverDelivery.ofConfigs(splitHUIfOverDelivery, pickingConfigRepository.getPickingConfig().isAllowOverDelivery());
	}

	@NonNull
	public ImmutableMap<HuId, ImmutableSet<OrderId>> getOpenPickingOrderIdsByHuId(@NonNull final ImmutableSet<HuId> huIds)
	{
		final ImmutableList<PickingCandidate> openPickingCandidates = pickingCandidateRepository.getByHUIds(huIds)
				.stream()
				.filter(pickingCandidate -> !pickingCandidate.isProcessed())
				.collect(ImmutableList.toImmutableList());

		final ImmutableListMultimap<HuId, ShipmentScheduleId> huId2ShipmentScheduleIds = openPickingCandidates
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(pickingCandidate -> pickingCandidate.getPickFrom().getHuId(),
																	   PickingCandidate::getShipmentScheduleId));

		final ImmutableMap<ShipmentScheduleId, OrderId> scheduleId2OrderId = shipmentSchedulePA.getByIds(ImmutableSet.copyOf(huId2ShipmentScheduleIds.values()))
				.values()
				.stream()
				.filter(shipmentSchedule -> shipmentSchedule.getC_Order_ID() > 0)
				.collect(ImmutableMap.toImmutableMap(shipSchedule -> ShipmentScheduleId.ofRepoId(shipSchedule.getM_ShipmentSchedule_ID()),
													 shipSchedule -> OrderId.ofRepoId(shipSchedule.getC_Order_ID())));

		return huIds.stream()
				.collect(ImmutableMap.toImmutableMap(Function.identity(),
													 huId -> {
														 final ImmutableList<ShipmentScheduleId> scheduleIds = Optional
																 .ofNullable(huId2ShipmentScheduleIds.get(huId))
																 .orElseGet(ImmutableList::of);

														 return scheduleIds.stream()
																 .map(scheduleId2OrderId::get)
																 .filter(Objects::nonNull)
																 .collect(ImmutableSet.toImmutableSet());
													 }));
	}

	/**
	 * @return true, if all drafted picking candidates have been removed from the slot, false otherwise
	 */
	public boolean clearPickingSlot(@NonNull final PickingSlotId pickingSlotId, final boolean removeUnprocessedHUsFromSlot)
	{
		if (removeUnprocessedHUsFromSlot)
		{
			RemoveHUFromPickingSlotCommand.builder()
					.pickingCandidateRepository(pickingCandidateRepository)
					.pickingSlotId(pickingSlotId)
					.build()
					.perform();
		}

		return !pickingCandidateRepository.hasDraftCandidatesForPickingSlot(pickingSlotId);
	}
}
