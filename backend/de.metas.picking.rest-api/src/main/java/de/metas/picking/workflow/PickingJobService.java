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
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimaps;
import de.metas.handlingunits.picking.PickFrom;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.PickingCandidateSnapshot;
import de.metas.handlingunits.picking.candidate.commands.PickHUResult;
import de.metas.handlingunits.picking.plan.CreatePickingPlanRequest;
import de.metas.handlingunits.picking.plan.PickFromHU;
import de.metas.handlingunits.picking.plan.PickingPlan;
import de.metas.handlingunits.picking.plan.PickingPlanLine;
import de.metas.handlingunits.picking.plan.PickingPlanLineType;
import de.metas.handlingunits.picking.requests.PickRequest;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.inoutcandidate.lock.ShipmentScheduleLockRepository;
import de.metas.inoutcandidate.lock.ShipmentScheduleLockRequest;
import de.metas.inoutcandidate.lock.ShipmentScheduleLockType;
import de.metas.inoutcandidate.lock.ShipmentScheduleUnLockRequest;
import de.metas.picking.api.IPackagingDAO;
import de.metas.picking.api.Packageable;
import de.metas.picking.api.PackageableQuery;
import de.metas.picking.workflow.model.PickingJob;
import de.metas.picking.workflow.model.PickingJobCandidate;
import de.metas.picking.workflow.model.PickingJobHeader;
import de.metas.picking.workflow.model.PickingJobLine;
import de.metas.picking.workflow.model.PickingJobStep;
import de.metas.picking.workflow.model.PickingJobStepId;
import de.metas.picking.workflow.model.PickingWFProcessStartParams;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_UOM;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class PickingJobService
{
	private final IPackagingDAO packagingDAO = Services.get(IPackagingDAO.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final ShipmentScheduleLockRepository shipmentScheduleLockRepository;
	private final PickingCandidateService pickingCandidateService;

	public PickingJobService(
			@NonNull final ShipmentScheduleLockRepository shipmentScheduleLockRepository,
			@NonNull final PickingCandidateService pickingCandidateService)
	{
		this.shipmentScheduleLockRepository = shipmentScheduleLockRepository;
		this.pickingCandidateService = pickingCandidateService;
	}

	public Stream<PickingJobCandidate> streamPickingJobCandidates(
			@NonNull final UserId userId,
			@NonNull final Set<ShipmentScheduleId> excludeShipmentScheduleIds)
	{
		return packagingDAO
				.stream(PackageableQuery.builder()
						.onlyFromSalesOrder(true)
						.lockedBy(userId)
						.includeNotLocked(true)
						.excludeShipmentScheduleIds(excludeShipmentScheduleIds)
						.build())
				.map(PickingJobService::extractPickingJobCandidate)
				.distinct();
	}

	private static PickingJobCandidate extractPickingJobCandidate(@NonNull final Packageable item)
	{
		return PickingJobCandidate.builder()
				.wfProcessStartParams(PickingWFProcessStartParams.builder()
						.salesOrderId(Objects.requireNonNull(item.getSalesOrderId()))
						.customerLocationId(item.getCustomerLocationId())
						.warehouseTypeId(item.getWarehouseTypeId())
						.locked(item.getLockedBy() != null)
						.build())
				.salesOrderDocumentNo(Objects.requireNonNull(item.getSalesOrderDocumentNo()))
				.customerName(item.getCustomerName())
				.build();
	}

	public PickingJob createPickingJob(
			final @NonNull PickingWFProcessStartParams params,
			final @NonNull UserId invokerId,
			final @NonNull Set<ShipmentScheduleId> excludeShipmentScheduleIds)
	{
		final ImmutableList<Packageable> items = packagingDAO
				.stream(toPackageableQuery(params, invokerId, excludeShipmentScheduleIds))
				.collect(ImmutableList.toImmutableList());
		if (items.isEmpty())
		{
			throw new AdempiereException("Nothing to pick");
		}

		final ImmutableSet<ShipmentScheduleId> shipmentScheduleIds = Packageable.extractShipmentScheduleIds(items);
		lockShipmentSchedules(shipmentScheduleIds, invokerId);
		try
		{
			final PickingJobHeader header = items.stream()
					.map(item -> extractPickingJobHeader(item, invokerId))
					.collect(GuavaCollectors.singleElementOrThrow(() -> new AdempiereException("More than one job found")));

			final ImmutableList<PickingJobLine> lines = createPickingJobLines(items);

			return PickingJob.builder()
					.header(header)
					.lines(lines)
					.build();
		}
		catch (final Exception ex)
		{
			try
			{
				unlockShipmentSchedules(shipmentScheduleIds, invokerId);
			}
			catch (Exception unlockException)
			{
				ex.addSuppressed(unlockException);
			}

			throw AdempiereException.wrapIfNeeded(ex);
		}
	}

	private static PackageableQuery toPackageableQuery(
			final @NonNull PickingWFProcessStartParams params,
			final @NonNull UserId invokerId,
			final @NonNull Set<ShipmentScheduleId> excludeShipmentScheduleIds)
	{
		return PackageableQuery.builder()
				.onlyFromSalesOrder(true)
				.salesOrderId(params.getSalesOrderId())
				.customerLocationId(params.getCustomerLocationId())
				.warehouseTypeId(params.getWarehouseTypeId())
				.lockedBy(params.isLocked() ? invokerId : null)
				.includeNotLocked(!params.isLocked())
				.excludeShipmentScheduleIds(excludeShipmentScheduleIds)
				.build();
	}

	private static PickingJobHeader extractPickingJobHeader(@NonNull final Packageable item, @NonNull final UserId lockedBy)
	{
		return PickingJobHeader.builder()
				.salesOrderDocumentNo(Objects.requireNonNull(item.getSalesOrderDocumentNo()))
				.customerName(item.getCustomerName())
				.preparationDate(item.getPreparationDate())
				.deliveryAddress(item.getCustomerAddress())
				.lockedBy(lockedBy)
				.build();
	}

	@Value
	@Builder
	private static class PickingJobLineKey
	{
		ProductId productId;
		String productName;
	}

	public static PickingJobLineKey extractPickingJobLineKey(@NonNull Packageable item)
	{
		return PickingJobLineKey.builder()
				.productId(item.getProductId())
				.productName(item.getProductName())
				.build();
	}

	private ImmutableList<PickingJobLine> createPickingJobLines(final ImmutableList<Packageable> items)
	{
		return Multimaps.index(items, PickingJobService::extractPickingJobLineKey)
				.asMap().entrySet().stream()
				.map(e -> createPickingJobLine(e.getKey(), e.getValue()))
				.collect(ImmutableList.toImmutableList());
	}

	private PickingJobLine createPickingJobLine(
			@NonNull final PickingJobLineKey key,
			@NonNull final Collection<Packageable> items)
	{
		Check.assumeNotEmpty(items, "items");

		PickingPlan plan = pickingCandidateService.createPlan(CreatePickingPlanRequest.builder()
				.packageables(items)
				.considerAttributes(false) // TODO make it configurable
				.build());

		plan = pickingCandidateService.createPickingCandidatesFromPlan(plan);

		return toPickingJobLine(
				plan,
				key.getProductId(),
				key.getProductName());
	}

	private PickingJobLine toPickingJobLine(
			@NonNull final PickingPlan plan,
			@NonNull final ProductId productId,
			@NonNull final String productName)
	{
		return PickingJobLine.builder()
				.productId(productId)
				.productName(TranslatableStrings.anyLanguage(productName))
				.steps(plan.getLines()
						.stream()
						.map(this::toPickingJobStep)
						.filter(Objects::nonNull)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	@Nullable
	private PickingJobStep toPickingJobStep(@NonNull final PickingPlanLine planLine)
	{
		if (PickingPlanLineType.PICK_FROM_HU.equals(planLine.getType()))
		{
			final PickFromHU pickFromHU = Objects.requireNonNull(planLine.getPickFromHU());

			final LocatorId locatorId = pickFromHU.getLocatorId();
			final String locatorName = warehouseDAO.getLocatorById(locatorId).getValue();

			final ProductId productId = planLine.getProductId();
			final ITranslatableString productName = productBL.getProductNameTrl(productId);

			final PickingCandidateSnapshot existingPickingCandidate = planLine.getSourceDocumentInfo().getExistingPickingCandidate();
			if (existingPickingCandidate == null)
			{
				throw new AdempiereException("Plan line was not saved: " + planLine);
			}

			return PickingJobStep.builder()
					.id(PickingJobStepId.random())
					.shipmentScheduleId(planLine.getSourceDocumentInfo().getShipmentScheduleId())
					.pickingCandidateId(Objects.requireNonNull(existingPickingCandidate.getId()))
					.locatorId(locatorId)
					.locatorName(locatorName)
					.productId(productId)
					.productName(productName)
					.huId(pickFromHU.getHuId())
					.huBarcode(pickFromHU.getHuCode()) // TODO is it right? shall we add SSCC18 too?
					.qtyToPick(planLine.getQty())
					.build();
		}
		else
		{
			// TODO return some unsupported/unallocable line
			return null;
		}
	}

	public void abort(@NonNull final PickingJob pickingJob)
	{
		pickingCandidateService.deleteDraftPickingCandidatesByShipmentScheduleId(pickingJob.getShipmentScheduleIds());
		unlockShipmentSchedules(pickingJob.getShipmentScheduleIds(), pickingJob.getLockedBy());
	}

	private void lockShipmentSchedules(
			final @NonNull ImmutableSet<ShipmentScheduleId> shipmentScheduleIds,
			final @NonNull UserId lockedBy)
	{
		shipmentScheduleLockRepository.lock(
				ShipmentScheduleLockRequest.builder()
						.shipmentScheduleIds(shipmentScheduleIds)
						.lockType(ShipmentScheduleLockType.PICKING)
						.lockedBy(lockedBy)
						.build());
	}

	private void unlockShipmentSchedules(
			final @NonNull ImmutableSet<ShipmentScheduleId> shipmentScheduleIds,
			final @NonNull UserId lockedBy)
	{
		shipmentScheduleLockRepository.unlock(
				ShipmentScheduleUnLockRequest.builder()
						.shipmentScheduleIds(shipmentScheduleIds)
						.lockType(ShipmentScheduleLockType.PICKING)
						.lockedBy(lockedBy)
						.build());
	}

	public PickingJob processStepEvents(
			@NonNull final PickingJob pickingJob,
			@NonNull final Collection<PickingJobStepEvent> events)
	{
		final ImmutableList<PickRequest> pickRequests = aggregateByStepId(events)
				.stream()
				.map(event -> toPickRequest(event, pickingJob))
				.collect(ImmutableList.toImmutableList());

		final List<PickHUResult> pickResults = pickingCandidateService.pickHUsBulk(pickRequests);

		final ImmutableMap<PickingCandidateId, PickingCandidate> changedPickingCandidates = pickResults.stream()
				.map(PickHUResult::getPickingCandidate)
				.collect(ImmutableMap.toImmutableMap(
						PickingCandidate::getId,
						pickResult -> pickResult));

		return pickingJob.withChangedSteps(step -> updateStepFromPickingCandidates(step, changedPickingCandidates));
	}

	private PickingJobStep updateStepFromPickingCandidates(
			PickingJobStep step,
			final ImmutableMap<PickingCandidateId, PickingCandidate> pickingCandidates)
	{
		final PickingCandidate pickingCandidate = pickingCandidates.get(step.getPickingCandidateId());
		if (pickingCandidate == null)
		{
			return step;
		}

		@NonNull final Quantity qtyPicked = pickingCandidate.getQtyPicked();
		return step.withQtyPickedAndConfirmed(qtyPicked);
	}

	private Collection<PickingJobStepEvent> aggregateByStepId(@NonNull final Collection<PickingJobStepEvent> events)
	{
		return events
				.stream()
				.collect(ImmutableMap.toImmutableMap(PickingJobStepEvent::getPickingStepId, event -> event, PickingJobStepEvent::latest))
				.values();
	}

	private static PickRequest toPickRequest(
			final PickingJobStepEvent event,
			final PickingJob pickingJob)
	{
		final PickingJobStepId stepId = event.getPickingStepId();
		final PickingJobStep step = pickingJob.getStepById(stepId);
		final I_C_UOM uom = step.getUOM();

		final Quantity qtyToPick;
		switch (event.getEventType())
		{
			case PICK:
				qtyToPick = event.getQtyPicked() != null
						? Quantity.of(event.getQtyPicked(), uom)
						: Quantity.zero(uom);
				break;
			case UNPICK:
				qtyToPick = Quantity.zero(uom);
				break;
			default:
				throw new AdempiereException("Event type not handled for " + event);
		}

		return PickRequest.builder()
				.existingPickingCandidateId(step.getPickingCandidateId())
				.shipmentScheduleId(step.getShipmentScheduleId())
				.pickFrom(PickFrom.ofHuId(step.getHuId()))
				.qtyToPick(qtyToPick)
				.pickingSlotId(pickingJob.getPickingSlotId().orElse(null))
				.autoReview(true)
				.build();
	}

	public PickingJob process(@NonNull final PickingJob pickingJob)
	{
		final ImmutableSet<PickingCandidateId> pickingCandidateIds = pickingJob.getPickingCandidateIds();
		pickingCandidateService.process(pickingCandidateIds);

		return pickingJob.withProcessedTrue();
	}
}
