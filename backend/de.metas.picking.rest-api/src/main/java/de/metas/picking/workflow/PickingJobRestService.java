/*
 * #%L
 * de.metas.picking.rest-api
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.picking.workflow;

import com.google.common.collect.ImmutableList;
<<<<<<< HEAD
=======
import de.metas.ad_reference.ADRefList;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.config.MobileUIPickingUserProfile;
import de.metas.handlingunits.picking.config.MobileUIPickingUserProfileRepository;
import de.metas.handlingunits.picking.config.PickingLineGroupBy;
import de.metas.handlingunits.picking.config.PickingLineSortBy;
import de.metas.handlingunits.picking.job.model.LUPickingTarget;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobCandidate;
import de.metas.handlingunits.picking.job.model.PickingJobFacets;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.model.PickingJobLineId;
import de.metas.handlingunits.picking.job.model.PickingJobQuery;
import de.metas.handlingunits.picking.job.model.PickingJobReference;
import de.metas.handlingunits.picking.job.model.PickingJobReferenceQuery;
import de.metas.handlingunits.picking.job.model.PickingJobStepEvent;
import de.metas.handlingunits.picking.job.model.TUPickingTarget;
import de.metas.handlingunits.picking.job.service.PickingJobService;
import de.metas.handlingunits.picking.job.service.commands.PickingJobCreateRequest;
import de.metas.picking.qrcode.PickingSlotQRCode;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
import org.adempiere.ad.service.IADReferenceDAO;
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PickingJobRestService
{
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	private final PickingJobService pickingJobService;
	private final MobileUIPickingUserProfileRepository mobileUIPickingUserProfileRepository;

	public PickingJob getPickingJobById(final PickingJobId pickingJobId)
	{
		return pickingJobService.getById(pickingJobId);
	}

	@NonNull
	public Stream<PickingJobReference> streamDraftPickingJobReferences(@NonNull final PickingJobReferenceQuery query)
	{
		return pickingJobService.streamDraftPickingJobReferences(query);
	}

	public Stream<PickingJobCandidate> streamPickingJobCandidates(@NonNull final PickingJobQuery query)
	{
		return pickingJobService.streamPickingJobCandidates(query);
	}

	@NonNull
	public PickingJobFacets getFacets(
			@NonNull final PickingJobQuery query,
			@NonNull final PickingJobFacets.CollectingParameters collectingParameters)
	{
		return pickingJobService.streamPackageable(query)
				.collect(PickingJobFacets.collectFromPackageables(collectingParameters));
	}

	public PickingJob createPickingJob(
			final @NonNull PickingWFProcessStartParams params,
			final @NonNull UserId invokerId)
	{
		return pickingJobService.createPickingJob(PickingJobCreateRequest.builder()
														  .pickerId(invokerId)
														  .salesOrderId(params.getSalesOrderId())
														  .deliveryBPLocationId(params.getDeliveryBPLocationId())
														  .warehouseTypeId(params.getWarehouseTypeId())
														  .isAllowPickingAnyHU(mobileUIPickingUserProfileRepository.getProfile().isAllowPickingAnyHU())
														  .build());
	}

	public PickingJob allocateAndSetPickingSlot(
			@NonNull final PickingJob pickingJob,
			@NonNull final PickingSlotQRCode pickingSlotQRCode)
	{
		return pickingJobService.allocateAndSetPickingSlot(pickingJob, pickingSlotQRCode);
	}

	public PickingJob processStepEvents(
			@NonNull final PickingJob pickingJob,
			@NonNull final List<PickingJobStepEvent> events)
	{
		return pickingJobService.processStepEvents(pickingJob, events);
	}

	public PickingJob closeLine(@NonNull final PickingJob pickingJob, @NonNull final PickingJobLineId pickingLineId)
	{
		return pickingJobService.closeLine(pickingJob, pickingLineId);
	}

	public PickingJob openLine(final PickingJob pickingJob, final PickingJobLineId pickingLineId)
	{
		return pickingJobService.openLine(pickingJob, pickingLineId);
	}

	public void abort(@NonNull final PickingJob pickingJob)
	{
		pickingJobService.abort(pickingJob);
	}

	public void abortAllByUserId(final @NonNull UserId userId)
	{
		pickingJobService.abortAllByUserId(userId);
	}

	public void unassignAllByUserId(final @NonNull UserId userId)
	{
		pickingJobService.unassignAllByUserId(userId);
	}

	public PickingJob assignPickingJob(@NonNull final PickingJobId pickingJobId, @NonNull final UserId newResponsibleId)
	{
		return pickingJobService.assignPickingJob(pickingJobId, newResponsibleId);
	}

	public PickingJob complete(@NonNull final PickingJob pickingJob)
	{
		final MobileUIPickingUserProfile profile = mobileUIPickingUserProfileRepository.getProfile();
		return pickingJobService.prepareToComplete(pickingJob)
				.createShipmentPolicy(profile.getCreateShipmentPolicy())
				.execute();
	}

<<<<<<< HEAD
	public IADReferenceDAO.ADRefList getQtyRejectedReasons()
	{
		return pickingJobService.getQtyRejectedReasons();
	}

=======
	public ADRefList getQtyRejectedReasons()
	{
		return pickingJobService.getQtyRejectedReasons();
	}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	public List<LUPickingTarget> getLUAvailableTargets(@NonNull final PickingJob pickingJob)
	{
		return pickingJobService.getLUAvailableTargets(pickingJob);
	}

	public List<TUPickingTarget> getTUAvailableTargets(@NonNull final PickingJob pickingJob)
	{
		return pickingJobService.getTUAvailableTargets(pickingJob);
	}

	public PickingJob setPickTarget(@NonNull final PickingJob pickingJob, @Nullable final LUPickingTarget target)
	{
		return pickingJobService.setPickTarget(pickingJob, target);
	}

	public PickingJob setPickTarget(@NonNull final PickingJob pickingJob, @Nullable final TUPickingTarget target)
	{
		return pickingJobService.setPickTarget(pickingJob, target);
	}

	public PickingJob closeLUPickTarget(@NonNull final PickingJob pickingJob)
	{
		return pickingJobService.closeLUPickTarget(pickingJob);
	}

	public PickingJob closeTUPickTarget(@NonNull final PickingJob pickingJob)
	{
		return pickingJobService.closeTUPickTarget(pickingJob);
	}

	public boolean isPickWithNewLU()
	{
		return mobileUIPickingUserProfileRepository.getProfile().isPickWithNewLU();
	}

	public boolean isAllowSkippingRejectedReasons()
	{
		return mobileUIPickingUserProfileRepository.getProfile().isAllowSkippingRejectedReason();
	}

	public boolean isAllowNewTU()
	{
		return mobileUIPickingUserProfileRepository.getProfile().isAllowNewTU();
	}

	public boolean isShowPromptWhenOverPicking()
	{
		return mobileUIPickingUserProfileRepository.getProfile().isShowConfirmationPromptWhenOverPick();
	}

	@NonNull
	public PickingLineGroupBy getPickingLineGroupBy()
	{
		return Optional.ofNullable(mobileUIPickingUserProfileRepository.getProfile().getPickingLineGroupBy())
				.orElse(PickingLineGroupBy.NONE);
	}

	@NonNull
	public PickingLineSortBy getPickingLineSortBy()
	{
		return Optional.ofNullable(mobileUIPickingUserProfileRepository.getProfile().getPickingLineSortBy())
				.orElse(PickingLineSortBy.ORDER_LINE_SEQ_NO);
	}

	@NonNull
	public List<HuId> getClosedLUs(@NonNull final PickingJob pickingJob)
	{
		final Set<HuId> pickedHuIds = pickingJob.getPickedHuIds();
		if (pickedHuIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final HuId currentlyOpenedLUId = pickingJob.getLuPickTarget()
				.map(LUPickingTarget::getLuId)
				.orElse(null);

		return handlingUnitsBL.getTopLevelHUs(IHandlingUnitsBL.TopLevelHusQuery.builder()
													  .hus(handlingUnitsBL.getByIds(pickedHuIds))
													  .build())
				.stream()
				.filter(handlingUnitsBL::isLoadingUnit)
				.map(I_M_HU::getM_HU_ID)
				.map(HuId::ofRepoId)
				.filter(huId -> !HuId.equals(huId, currentlyOpenedLUId))
				.collect(ImmutableList.toImmutableList());
	}
}
