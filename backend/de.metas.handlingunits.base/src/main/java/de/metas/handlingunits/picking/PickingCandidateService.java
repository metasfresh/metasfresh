package de.metas.handlingunits.picking;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.picking.candidate.commands.AddQtyToHUCommand;
import de.metas.handlingunits.picking.candidate.commands.ClosePickingCandidateCommand;
import de.metas.handlingunits.picking.candidate.commands.CreatePickingCandidatesCommand;
import de.metas.handlingunits.picking.candidate.commands.PickHUCommand;
import de.metas.handlingunits.picking.candidate.commands.PickHUResult;
import de.metas.handlingunits.picking.candidate.commands.ProcessHUsAndPickingCandidateCommand;
import de.metas.handlingunits.picking.candidate.commands.ProcessPickingCandidatesCommand;
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
import de.metas.handlingunits.picking.requests.RejectPickingRequest;
import de.metas.handlingunits.picking.requests.RemoveQtyFromHURequest;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.sourcehu.HuId2SourceHUsService;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.picking.api.PickingConfigRepository;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.service.IADReferenceDAO;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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
public class PickingCandidateService
{
	private final PickingConfigRepository pickingConfigRepository;
	private final PickingCandidateRepository pickingCandidateRepository;
	private final HuId2SourceHUsService sourceHUsRepository;
	private final HUReservationService huReservationService;
	private final IBPartnerBL bpartnersService;
	private final IADReferenceDAO adReferenceDAO = Services.get(IADReferenceDAO.class);

	public PickingCandidateService(
			@NonNull final PickingConfigRepository pickingConfigRepository,
			@NonNull final PickingCandidateRepository pickingCandidateRepository,
			@NonNull final HuId2SourceHUsService sourceHUsRepository,
			@NonNull final HUReservationService huReservationService,
			@NonNull final IBPartnerBL bpartnersService)
	{
		this.pickingConfigRepository = pickingConfigRepository;
		this.pickingCandidateRepository = pickingCandidateRepository;
		this.sourceHUsRepository = sourceHUsRepository;
		this.huReservationService = huReservationService;
		this.bpartnersService = bpartnersService;
	}

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
		final List<PickingCandidate> pickingCandidatesToProcess = pickingCandidateRepository.getByHUIds(pickFromHuIds)
				.stream()
				.filter(PickingCandidate::isDraft)
				.filter(pc -> shipmentScheduleId == null || shipmentScheduleId.equals(pc.getShipmentScheduleId()))
				.collect(ImmutableList.toImmutableList());

		//
		// Process those picking candidates
		final ImmutableList<PickingCandidate> processedPickingCandidates = ProcessHUsAndPickingCandidateCommand.builder()
				.sourceHUsRepository(sourceHUsRepository)
				.pickingCandidateRepository(pickingCandidateRepository)
				.pickingCandidates(pickingCandidatesToProcess)
				.additionalPickFromHuIds(pickFromHuIds)
				.allowOverDelivery(pickingConfigRepository.getPickingConfig().isAllowOverDelivery())
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

	public ProcessPickingCandidatesResult process(@NonNull final Set<PickingCandidateId> pickingCandidateIds)
	{
		return ProcessPickingCandidatesCommand.builder()
				.pickingCandidateRepository(pickingCandidateRepository)
				.pickingCandidateIds(pickingCandidateIds)
				.build()
				.perform();
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

	public List<PickingCandidate> setHuPackingInstructionId(final Set<PickingCandidateId> pickingCandidateIds, final HuPackingInstructionsId huPackingInstructionsId)
	{
		return SetHuPackingInstructionIdCommand.builder()
				.pickingCandidateRepository(pickingCandidateRepository)
				.pickingCandidateIds(pickingCandidateIds)
				.huPackingInstructionsId(huPackingInstructionsId)
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

	public IADReferenceDAO.ADRefList getQtyRejectedReasons()
	{
		return adReferenceDAO.getRefListById(QtyRejectedReasonCode.REFERENCE_ID);
	}
}
