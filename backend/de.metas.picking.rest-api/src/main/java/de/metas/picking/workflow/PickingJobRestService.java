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

import de.metas.ad_reference.ADRefList;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobCandidate;
import de.metas.handlingunits.picking.job.model.PickingJobFacets;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.model.PickingJobLineId;
import de.metas.handlingunits.picking.job.model.PickingJobQuery;
import de.metas.handlingunits.picking.job.model.PickingJobReference;
import de.metas.handlingunits.picking.job.model.PickingJobReferenceQuery;
import de.metas.handlingunits.picking.job.model.PickingJobStepEvent;
import de.metas.handlingunits.picking.job.service.PickingJobService;
import de.metas.handlingunits.picking.job.service.commands.PickingJobCreateRequest;
import de.metas.picking.config.MobileUIPickingUserProfile;
import de.metas.picking.config.MobileUIPickingUserProfileRepository;
import de.metas.picking.qrcode.PickingSlotQRCode;
import de.metas.user.UserId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PickingJobRestService
{
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

	public PickingJob requestReview(@NonNull final PickingJob pickingJob) {return pickingJobService.requestReview(pickingJob);}

	public ADRefList getQtyRejectedReasons()
	{
		return pickingJobService.getQtyRejectedReasons();
	}
}
