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

import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobCandidate;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.model.PickingJobReference;
import de.metas.handlingunits.picking.job.model.PickingJobStepEvent;
import de.metas.handlingunits.picking.job.service.PickingJobService;
import de.metas.handlingunits.picking.job.service.commands.PickingJobCreateRequest;
import de.metas.inout.ShipmentScheduleId;
import de.metas.picking.qrcode.PickingSlotQRCode;
import de.metas.user.UserId;
import lombok.NonNull;
import org.adempiere.ad.service.IADReferenceDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class PickingJobRestService
{
	private final PickingJobService pickingJobService;

	public PickingJobRestService(
			@NonNull final PickingJobService pickingJobService)
	{
		this.pickingJobService = pickingJobService;
	}

	public PickingJob getPickingJobById(final PickingJobId pickingJobId)
	{
		return pickingJobService.getById(pickingJobId);
	}

	public Stream<PickingJobReference> streamDraftPickingJobReferences(@NonNull final UserId userId)
	{
		return pickingJobService.streamDraftPickingJobReferences(userId);
	}

	public Stream<PickingJobCandidate> streamPickingJobCandidates(
			@NonNull final UserId userId,
			@NonNull final Set<ShipmentScheduleId> excludeShipmentScheduleIds)
	{
		return pickingJobService.streamPickingJobCandidates(userId, excludeShipmentScheduleIds);
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
		return pickingJobService.complete(pickingJob);
	}

	public IADReferenceDAO.ADRefList getQtyRejectedReasons()
	{
		return pickingJobService.getQtyRejectedReasons();
	}
}
