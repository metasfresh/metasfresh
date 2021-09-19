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
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.plan.CreatePickingPlanRequest;
import de.metas.handlingunits.picking.plan.PickFromHU;
import de.metas.handlingunits.picking.plan.PickingPlan;
import de.metas.handlingunits.picking.plan.PickingPlanLine;
import de.metas.handlingunits.picking.plan.PickingPlanLineType;
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
import de.metas.picking.workflow.model.PickingJobLine;
import de.metas.picking.workflow.model.PickingJobStep;
import de.metas.picking.workflow.model.PickingJobStepId;
import de.metas.picking.workflow.model.PickingWFProcessStartParams;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
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
			final @NonNull UserId invokerId)
	{
		final ImmutableList<Packageable> items = packagingDAO.stream(PackageableQuery.builder()
						.onlyFromSalesOrder(true)
						.salesOrderId(params.getSalesOrderId())
						.customerLocationId(params.getCustomerLocationId())
						.warehouseTypeId(params.getWarehouseTypeId())
						.lockedBy(params.isLocked() ? invokerId : null)
						.includeNotLocked(!params.isLocked())
						.build())
				.collect(ImmutableList.toImmutableList());
		if (items.isEmpty())
		{
			throw new AdempiereException("Nothing to pick");
		}

		final ImmutableList<PickingJobLine> lines = GuavaCollectors.groupByAndStream(items.stream(), Packageable::getProductId)
				.map(this::createPickingJobLine)
				.collect(ImmutableList.toImmutableList());

		final PickingJob pickingJob = PickingJob.builder()
				.salesOrderDocumentNo(Packageable.extractSingleValue(items, Packageable::getSalesOrderDocumentNo)
						.orElseThrow(() -> new AdempiereException("Not an unique sales order documentNo")))
				.customerName(Packageable.extractSingleValue(items, Packageable::getCustomerName)
						.orElseThrow(() -> new AdempiereException("Not an unique sales order customerName")))
				.preparationDate(Packageable.extractSingleValue(items, Packageable::getPreparationDate)
						.orElseThrow(() -> new AdempiereException("Not an unique sales order preparationDate")))
				.deliveryAddress(Packageable.extractSingleValue(items, Packageable::getCustomerAddress)
						.orElseThrow(() -> new AdempiereException("Not an unique sales order delivery address")))
				.lines(lines)
				.lockedBy(Packageable.extractSingleLockedBy(items).orElse(null))
				.build();

		lockIfNeeded(pickingJob, invokerId);

		return pickingJob;
	}

	private PickingJobLine createPickingJobLine(@NonNull final List<Packageable> items)
	{
		Check.assumeNotEmpty(items, "items");

		final ProductId productId = Packageable.extractSingleValue(items, Packageable::getProductId)
				.orElseThrow(() -> new AdempiereException("Not an unique product"));

		final Packageable firstItem = items.get(0);

		final PickingPlan plan = pickingCandidateService.createPlan(CreatePickingPlanRequest.builder()
				.packageables(items)
				.considerAttributes(false) // TODO make it configurable
				.build());

		return PickingJobLine.builder()
				.productId(productId)
				.productName(TranslatableStrings.anyLanguage(firstItem.getProductName()))
				.steps(plan.getLines()
						.stream()
						.map(this::createPickingJobStep)
						.filter(Objects::nonNull)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	@Nullable
	private PickingJobStep createPickingJobStep(@NonNull final PickingPlanLine planLine)
	{
		if (PickingPlanLineType.PICK_FROM_HU.equals(planLine.getType()))
		{
			final PickFromHU pickFromHU = Objects.requireNonNull(planLine.getPickFromHU());

			final LocatorId locatorId = pickFromHU.getLocatorId();
			final String locatorName = warehouseDAO.getLocatorById(locatorId).getValue();

			final ProductId productId = planLine.getProductId();
			final ITranslatableString productName = productBL.getProductNameTrl(productId);

			return PickingJobStep.builder()
					.id(PickingJobStepId.random())
					.shipmentScheduleId(planLine.getSourceDocumentInfo().getShipmentScheduleId())
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
		// TODO: reverse/remove created picking candidates
		unlockIfNeeded(pickingJob);
	}

	private void lockIfNeeded(@NonNull final PickingJob pickingJob, @NonNull final UserId lockBy)
	{
		if (pickingJob.getLockedBy() == null)
		{
			shipmentScheduleLockRepository.lock(
					ShipmentScheduleLockRequest.builder()
							.shipmentScheduleIds(pickingJob.getShipmentScheduleIds())
							.lockType(ShipmentScheduleLockType.PICKING)
							.lockedBy(lockBy)
							.build());

			pickingJob.setLockedBy(lockBy);
		}
		else if (!UserId.equals(pickingJob.getLockedBy(), lockBy))
		{
			throw new AdempiereException("Already locked by " + pickingJob.getLockedBy());
		}
	}

	private void unlockIfNeeded(@NonNull final PickingJob pickingJob)
	{
		final UserId lockedBy = pickingJob.getLockedBy();
		if (lockedBy == null)
		{
			// already unlocked
			return;
		}

		shipmentScheduleLockRepository.unlock(
				ShipmentScheduleUnLockRequest.builder()
						.shipmentScheduleIds(pickingJob.getShipmentScheduleIds())
						.lockType(ShipmentScheduleLockType.PICKING)
						.lockedBy(lockedBy)
						.build());
	}

}
