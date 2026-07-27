package de.metas.distribution.ddorder.replenishment;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.common.util.time.SystemTime;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.distribution.ddorder.DDOrderService;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelDAO;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleService;
import de.metas.distribution.ddorder.replenishment.alloc.DDOrderLineContributor;
import de.metas.distribution.ddorder.replenishment.alloc.DDOrderLineContributorRepository;
import de.metas.distribution.ddorder.replenishment.event.DDOrderReplenishmentEventPublisher;
import de.metas.distribution.ddorder.replenishment.event.DDOrderReplenishmentRequest;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.job_schedule.service.PickingJobScheduleService;
import de.metas.handlingunits.storage.LocatorIdAndQty;
import de.metas.handlingunits.storage.ProductAvailableStockPerLocator;
import de.metas.handlingunits.storage.ProductQtyOnHandByLocator;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.model.I_M_Picking_Job_Schedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.material.planning.ddorder.DistributionNetwork;
import de.metas.material.planning.ddorder.DistributionNetworkId;
import de.metas.material.planning.ddorder.DistributionNetworkRepository;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.picking.api.PickingJobScheduleId;
import de.metas.picking.job_schedule.model.PickingJobSchedule;
import de.metas.picking.job_schedule.repository.PickingJobScheduleRepository;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UOMConversionContext;
import de.metas.util.Loggables;
import de.metas.util.ProgressLogger;
import de.metas.util.Services;
import de.metas.util.lang.RepoIdAware;
import de.metas.workplace.Workplace;
import de.metas.workplace.WorkplaceId;
import de.metas.workplace.WorkplaceService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.NoUOMConversionException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.Locator;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.Warehouse;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.WarehouseRepository;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_C_DocType;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.X_DD_Order;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class DDOrderPickingReplenishmentService
{
	private static final AdMessageKey MSG_DDOrderPickingReplenishment_PickerBusy = AdMessageKey.of("DDOrderPickingReconcile_PickerBusy");
	private static final AdMessageKey MSG_DDOrderPickingReplenishment_MovementStarted = AdMessageKey.of("DDOrderPickingReconcile_MovementStarted");
	private static final AdMessageKey MSG_DDOrderPickingReplenishment_NetworkGap = AdMessageKey.of("DDOrderPickingReconcile_NetworkGap");
	private static final AdMessageKey MSG_DDOrderPickingReplenishment_MandatoryNetwork = AdMessageKey.of("DDOrderPickingReconcile_MandatoryNetwork");
	@VisibleForTesting
	static final AdMessageKey MSG_DDOrderPickingReplenishment_QtyZero = AdMessageKey.of("DDOrderPickingReconcile_QtyZero");

	private static final String TRX_PROPERTY_ScheduleReconcile = "de.metas.distribution.ddorder.replenishment.DDOrderPickingReplenishment";

	@NonNull private final PickingJobRepository pickingJobRepository;
	@NonNull private final DDOrderLowLevelDAO ddOrderLowLevelDAO;
	@NonNull private final DDOrderService ddOrderService;
	@NonNull private final DistributionNetworkRepository distributionNetworkRepository;
	@NonNull private final ITrxManager trxManager;
	@NonNull private final DDOrderReplenishmentEventPublisher reconciliationEventPublisher;
	@NonNull private final PickingJobScheduleService pickingJobScheduleService;
	@NonNull private final WorkplaceService workplaceService;
	@NonNull private final DDOrderMoveScheduleService ddOrderMoveScheduleService;
	@NonNull private final WarehouseRepository warehouseRepository;
	@NonNull private final DDOrderLineContributorRepository contributorRepository;
	@NonNull private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	@NonNull private final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
	@NonNull private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	@NonNull private final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

	/** Refuses re-planning a workstation assignment while any contributor of its product group is already being replenished. */
	public void assertCanChange(@NonNull final I_M_Picking_Job_Schedule jobSchedule)
	{
		if (!isOnAutoDistributionOrder(jobSchedule))
		{
			return;
		}

		// The shipment close-out (Processed->true) is a fulfilment event, not a user re-plan: exempt it from the guard.
		if (InterfaceWrapperHelper.isValueChanged(jobSchedule, I_M_Picking_Job_Schedule.COLUMNNAME_Processed)
				&& jobSchedule.isProcessed())
		{
			return;
		}

		final BlockingWork changedAssignment = BlockingWork.of(
				PickingJobScheduleId.ofRepoId(jobSchedule.getM_Picking_Job_Schedule_ID()),
				ShipmentScheduleId.ofRepoId(jobSchedule.getM_ShipmentSchedule_ID()));

		for (final I_DD_Order ddOrder : findLiveDDOrdersOfAffectedGroups(jobSchedule))
		{
			final DDOrderId ddOrderId = DDOrderId.ofRepoId(ddOrder.getDD_Order_ID());

			final BlockingWork busyPicker = findBlockingPickingWork(ddOrder);
			if (busyPicker != null)
			{
				throw newPickerBusyException(ddOrderId, busyPicker);
			}
			for (final I_DD_OrderLine line : ddOrderLowLevelDAO.retrieveLines(ddOrder))
			{
				final BigDecimal qtyMoved = line.getQtyInTransit().add(line.getQtyDelivered());
				if (qtyMoved.signum() > 0)
				{
					final DDOrderLineId lineId = DDOrderLineId.ofRepoId(line.getDD_OrderLine_ID());
					final BlockingWork blocking = findBlockingMovedWork(lineId, changedAssignment);
					throw new AdempiereException(
							MSG_DDOrderPickingReplenishment_MovementStarted,
							asMessageValue(ddOrderId),
							qtyMoved.stripTrailingZeros().toPlainString(),
							asMessageValue(blocking.getJobScheduleId()),
							asMessageValue(blocking.getShipmentScheduleId()));
				}
			}
		}
	}

	private ImmutableList<I_DD_Order> findLiveDDOrdersOfAffectedGroups(@NonNull final I_M_Picking_Job_Schedule jobSchedule)
	{
		final LinkedHashMap<DDOrderId, I_DD_Order> ddOrdersById = new LinkedHashMap<>();
		for (final DDOrderReplenishmentGroupKey groupKey : affectedGroupKeys(jobSchedule))
		{
			final List<I_DD_Order> ddOrders = ddOrderLowLevelDAO.findActiveDDOrdersForReplenishmentGroup(
					groupKey.getProductId(),
					groupKey.getLocatorToId(),
					groupKey.getUomId(),
					contributorRepository.queryAll());
			ddOrders.forEach(ddOrder -> ddOrdersById.putIfAbsent(DDOrderId.ofRepoId(ddOrder.getDD_Order_ID()), ddOrder));
		}
		return ImmutableList.copyOf(ddOrdersById.values());
	}

	private ImmutableSet<DDOrderReplenishmentGroupKey> affectedGroupKeys(@NonNull final I_M_Picking_Job_Schedule jobSchedule)
	{
		final ImmutableSet.Builder<DDOrderReplenishmentGroupKey> groupKeys = ImmutableSet.builder();
		groupKeys.add(toReplenishmentRequest(PickingJobScheduleRepository.fromRecord(jobSchedule)).getGroupKey());

		// The group a changed group-key column moves the assignment OUT of may already be under way, so guard it too.
		if (InterfaceWrapperHelper.isValueChanged(jobSchedule,
				I_M_Picking_Job_Schedule.COLUMNNAME_M_ShipmentSchedule_ID,
				I_M_Picking_Job_Schedule.COLUMNNAME_C_Workplace_ID,
				I_M_Picking_Job_Schedule.COLUMNNAME_C_UOM_ID))
		{
			final I_M_Picking_Job_Schedule oldRecord = InterfaceWrapperHelper.createOld(jobSchedule, I_M_Picking_Job_Schedule.class);
			groupKeys.add(toReplenishmentRequest(PickingJobScheduleRepository.fromRecord(oldRecord)).getGroupKey());
		}

		return groupKeys.build();
	}

	public void scheduleReconcileAfterCommit(@NonNull final PickingJobSchedule jobSchedule)
	{
		trxManager.accumulateAndProcessAfterCommit(
				TRX_PROPERTY_ScheduleReconcile,
				ImmutableSet.of(toReplenishmentRequest(jobSchedule)),
				reconciliationEventPublisher::publishAll);
	}

	private DDOrderReplenishmentRequest toReplenishmentRequest(@NonNull final PickingJobSchedule jobSchedule)
	{
		return toReplenishmentRequest(jobSchedule, shipmentScheduleBL.getById(jobSchedule.getShipmentScheduleId()));
	}

	/** For callers that have already batch-loaded the shipment schedules. */
	private DDOrderReplenishmentRequest toReplenishmentRequest(
			@NonNull final PickingJobSchedule jobSchedule,
			@NonNull final I_M_ShipmentSchedule schedule)
	{
		final Workplace workplace = workplaceService.getById(jobSchedule.getWorkplaceId());

		final LocatorId locatorToId = workplaceService.getPickFromLocatorIdOrWarehouseDefault(workplace);
		if (workplace.getPickFromLocatorId() == null)
		{
			Loggables.addLog(
					"DD_Order picking replenishment: C_Workplace_ID={0} has no PickFrom_Locator_ID for"
							+ " M_Picking_Job_Schedule_ID={1}; falling back to the warehouse default M_Locator_ID={2}",
					jobSchedule.getWorkplaceId().getRepoId(),
					jobSchedule.getId().getRepoId(),
					locatorToId.getRepoId());
		}

		return DDOrderReplenishmentRequest.builder()
				.groupKey(DDOrderReplenishmentGroupKey.builder()
						.productId(ProductId.ofRepoId(schedule.getM_Product_ID()))
						.locatorToId(locatorToId)
						.uomId(jobSchedule.getQtyToPick().getUomId())
						.build())
				.clientAndOrgId(jobSchedule.getClientAndOrgId())
				.triggeredBy(jobSchedule.getId())
				.build();
	}

	/** Reconciles one product group: its summed demand is planned as one DD_Order per contributing source locator. The caller must provide the transaction. */
	public void reconcile(
			@NonNull final DDOrderReplenishmentGroupKey groupKey,
			@NonNull final ClientAndOrgId clientAndOrgId)
	{
		final ImmutableList<PickingJobSchedule> contributors = listContributorsInAttributionOrder(groupKey);
		final List<I_DD_Order> existingDDOrders = ddOrderLowLevelDAO.findActiveDDOrdersForReplenishmentGroup(
				groupKey.getProductId(),
				groupKey.getLocatorToId(),
				groupKey.getUomId(),
				contributorRepository.queryAll());

		final DDOrderReplenishmentAction action = classifyGroupAction(contributors, existingDDOrders);
		switch (action)
		{
			case NONE:
				return;
			case CREATE:
			case RECREATE:
				reconcileRequiredVsExisting(groupKey, clientAndOrgId, contributors, existingDDOrders);
				return;
			case CLOSE:
				disposeCloseOut(existingDDOrders);
				// Alloc rows are kept so the close-out stays traceable and an in-progress move stays pickable.
				return;
			case VOID:
			{
				final ImmutableSet<DDOrderLineId> lineIds = lineIdsOf(existingDDOrders);
				voidAllDDOrders(existingDDOrders);
				// A voided order must not still answer "which DD_Order serves this delivery?".
				contributorRepository.deleteByLineIds(lineIds);
				return;
			}
			default:
				throw new AdempiereException("Unexpected action: " + action);
		}
	}

	private DDOrderReplenishmentAction classifyGroupAction(
			@NonNull final List<PickingJobSchedule> contributors,
			@NonNull final List<I_DD_Order> existingDDOrders)
	{
		if (!contributors.isEmpty())
		{
			return existingDDOrders.isEmpty()
					? DDOrderReplenishmentAction.CREATE
					: DDOrderReplenishmentAction.RECREATE;
		}

		if (existingDDOrders.isEmpty())
		{
			return DDOrderReplenishmentAction.NONE;
		}

		return isEveryFormerContributorProcessed(contributorRepository.getPickingJobScheduleIds(lineIdsOf(existingDDOrders)))
				? DDOrderReplenishmentAction.CLOSE
				: DDOrderReplenishmentAction.VOID;
	}

	/** Entry point for tests and operations; production goes through the event topic, whose payload already carries the group key. */
	public void reconcileGroupOf(@NonNull final PickingJobScheduleId jobScheduleId)
	{
		final DDOrderReplenishmentRequest request = toReplenishmentRequest(pickingJobScheduleService.getById(jobScheduleId));
		reconcile(request.getGroupKey(), request.getClientAndOrgId());
	}

	private ImmutableList<PickingJobSchedule> listContributorsInAttributionOrder(@NonNull final DDOrderReplenishmentGroupKey groupKey)
	{
		final ImmutableSet<WorkplaceId> workplaceIds = workplaceService.getWorkplaceIdsByEffectivePickFromLocatorId(groupKey.getLocatorToId());
		final List<PickingJobSchedule> candidates = pickingJobScheduleService.listContributorsOfGroup(groupKey, workplaceIds);
		if (candidates.isEmpty())
		{
			return ImmutableList.of();
		}

		final Map<ShipmentScheduleId, I_M_ShipmentSchedule> schedules = shipmentScheduleBL.getByIds(
				candidates.stream()
						.map(PickingJobSchedule::getShipmentScheduleId)
						.collect(ImmutableSet.toImmutableSet()));

		return candidates.stream()
				.filter(this::hasDemandToPlan)
				.filter(candidate -> isOnAutoDistributionOrder(schedules.get(candidate.getShipmentScheduleId())))
				.sorted(attributionOrder(schedules))
				.collect(ImmutableList.toImmutableList());
	}

	private boolean hasDemandToPlan(@NonNull final PickingJobSchedule candidate)
	{
		final BigDecimal qtyToPick = candidate.getQtyToPick().toBigDecimal();
		if (qtyToPick.signum() > 0)
		{
			return true;
		}

		Loggables.addLog(
				"{0}: QtyToPick={1} for M_Picking_Job_Schedule_ID={2}; it contributes nothing to the product group",
				MSG_DDOrderPickingReplenishment_QtyZero.toAD_Message(),
				qtyToPick,
				candidate.getId().getRepoId());
		return false;
	}

	/** The id tiebreak keeps the order stable across passes, so a group whose demand did not change does not rewrite its alloc rows. */
	private Comparator<PickingJobSchedule> attributionOrder(@NonNull final Map<ShipmentScheduleId, I_M_ShipmentSchedule> schedules)
	{
		return Comparator
				.comparing((final PickingJobSchedule contributor) -> shipmentScheduleEffectiveBL.getPriorityRule(schedules.get(contributor.getShipmentScheduleId())).getCode())
				.thenComparing(contributor -> shipmentScheduleEffectiveBL.getPreparationDate(schedules.get(contributor.getShipmentScheduleId())))
				.thenComparing(contributor -> contributor.getId());
	}

	/** An empty set means the group's orders carry no alloc row at all, which is not a close-out. */
	private boolean isEveryFormerContributorProcessed(@NonNull final Set<PickingJobScheduleId> formerContributorIds)
	{
		if (formerContributorIds.isEmpty())
		{
			return false;
		}

		final List<PickingJobSchedule> formerContributors = pickingJobScheduleService.getByIds(ImmutableSet.copyOf(formerContributorIds));
		// A former contributor that no longer loads was DELETED — an un-assignment, not a close-out.
		return formerContributors.size() == formerContributorIds.size()
				&& formerContributors.stream().allMatch(PickingJobSchedule::isProcessed);
	}

	private ImmutableSet<DDOrderLineId> lineIdsOf(@NonNull final List<I_DD_Order> ddOrders)
	{
		return ddOrders.stream()
				.flatMap(ddOrder -> ddOrderLowLevelDAO.retrieveLines(ddOrder).stream())
				.map(line -> DDOrderLineId.ofRepoId(line.getDD_OrderLine_ID()))
				.collect(ImmutableSet.toImmutableSet());
	}

	private boolean isOnAutoDistributionOrder(@NonNull final I_M_Picking_Job_Schedule jobScheduleRecord)
	{
		final I_M_ShipmentSchedule schedule = shipmentScheduleBL.getById(ShipmentScheduleId.ofRepoId(jobScheduleRecord.getM_ShipmentSchedule_ID()));
		return isOnAutoDistributionOrder(schedule);
	}

	private boolean isOnAutoDistributionOrder(@NonNull final I_M_ShipmentSchedule schedule)
	{
		final WarehouseId warehouseId = shipmentScheduleEffectiveBL.getWarehouseId(schedule);
		final Warehouse warehouse = warehouseRepository.getById(warehouseId);
		return warehouse.isAutoDistributionOrder();
	}

	private void reconcileRequiredVsExisting(
			@NonNull final DDOrderReplenishmentGroupKey groupKey,
			@NonNull final ClientAndOrgId clientAndOrgId,
			@NonNull final List<PickingJobSchedule> contributorsInOrder,
			@NonNull final List<I_DD_Order> existingDDOrders)
	{
		final ProductId productId = groupKey.getProductId();
		final LocatorId locatorToId = groupKey.getLocatorToId();
		final OrgId orgId = clientAndOrgId.getOrgId();

		// Every contributor is in groupKey's UOM by construction, so the addition cannot hit a UOM mismatch.
		final Quantity groupDemand = contributorsInOrder.stream()
				.map(PickingJobSchedule::getQtyToPick)
				.reduce(Quantity::add)
				.orElseThrow(() -> new AdempiereException("Caller guarantees at least one contributor"));

		// A consolidated order has no single owning delivery, so delivery-scoped header columns come from the first
		// contributor in the attribution order: arbitrary, but deterministic.
		// TODO: stop writing M_Picking_Job_Schedule_ID / M_ShipmentSchedule_ID here once their last reader is gone.
		final PickingJobSchedule firstContributor = contributorsInOrder.get(0);
		final I_M_ShipmentSchedule firstSchedule = shipmentScheduleBL.getById(firstContributor.getShipmentScheduleId());

		final WarehouseId targetWarehouseId = shipmentScheduleEffectiveBL.getWarehouseId(firstSchedule);
		final Warehouse targetWarehouse = warehouseRepository.getById(targetWarehouseId);
		final WarehouseId sourceWarehouseId = getFirstSourceWarehouseIdOrThrow(targetWarehouse, productId);

		// Running the greedy once over the SUM is what makes over-allocation impossible: two deliveries can no longer
		// each claim the same on-hand units.
		final Map<LocatorId, Quantity> requiredByLocator = computeRequiredAllocation(groupKey, sourceWarehouseId, productId, groupDemand);

		// Refuse the whole reconcile before mutating anything.
		for (final I_DD_Order existingDDOrder : existingDDOrders)
		{
			final BlockingWork busyPicker = findBlockingPickingWork(existingDDOrder);
			if (busyPicker != null)
			{
				throw newPickerBusyException(DDOrderId.ofRepoId(existingDDOrder.getDD_Order_ID()), busyPicker);
			}
		}

		final ExistingLineIndex existingLines = indexExistingBySourceLocator(existingDDOrders);
		final Map<LocatorId, I_DD_OrderLine> existingLineByLocator = existingLines.getByLocator();

		final HashSet<DDOrderLineId> obsoleteLineIds = new HashSet<>();

		// Left live, an unkeyable order would lose its alloc rows to the cleanup below and become unreachable, while
		// still sitting in the mover's list.
		for (final I_DD_Order unkeyableDDOrder : existingLines.getUnkeyable())
		{
			voidDDOrderFor(DDOrderId.ofRepoId(unkeyableDDOrder.getDD_Order_ID()));
			ddOrderLowLevelDAO.retrieveLines(unkeyableDDOrder)
					.forEach(line -> obsoleteLineIds.add(DDOrderLineId.ofRepoId(line.getDD_OrderLine_ID())));
		}

		final FrozenSplit split = computeFrozenSplit(contributorsInOrder, requiredByLocator, existingLineByLocator);
		final Map<LocatorId, Quantity> refusedQtyByLocator = split.getRefusedQtyByLocator();
		final ImmutableMap<LocatorId, ImmutableList<DDOrderLineContributor>> attribution = split.getAttribution();

		final DocTypeId docTypeId = docTypeDAO.getDocTypeId(
				DocTypeQuery.builder()
						.docBaseType(X_C_DocType.DOCBASETYPE_DistributionOrder)
						.adClientId(clientAndOrgId.getClientId().getRepoId())
						.adOrgId(orgId.getRepoId())
						.build());
		final WarehouseId inTransitWarehouseId = warehouseRepository.getInTransitWarehouseId(orgId);

		for (final Map.Entry<LocatorId, I_DD_OrderLine> entry : existingLineByLocator.entrySet())
		{
			if (!requiredByLocator.containsKey(entry.getKey()))
			{
				voidDDOrderFor(DDOrderId.ofRepoId(entry.getValue().getDD_Order_ID()));
				obsoleteLineIds.add(DDOrderLineId.ofRepoId(entry.getValue().getDD_OrderLine_ID()));
			}
		}

		final HashSet<DDOrderLineId> survivingLineIds = new HashSet<>();

		for (final Map.Entry<LocatorId, Quantity> entry : requiredByLocator.entrySet())
		{
			final LocatorId sourceLocatorId = entry.getKey();
			final I_DD_OrderLine existingLine = existingLineByLocator.get(sourceLocatorId);

			final Quantity refusedQty = refusedQtyByLocator.get(sourceLocatorId);
			if (refusedQty != null)
			{
				// Re-run the guard with the quantity it was refused for, so the refusal reaches the log with its real numbers.
				updateDDOrderLineQtyInPlace(existingLine, refusedQty);
				survivingLineIds.add(DDOrderLineId.ofRepoId(existingLine.getDD_OrderLine_ID()));
				continue;
			}

			// The line quantity is the sum of its shares, not the raw chunk: they differ exactly when a frozen line
			// already serves part of the demand, and then the chunk is more than anybody still needs.
			final ImmutableList<DDOrderLineContributor> shares = attribution.get(sourceLocatorId);
			final Quantity locatorQty = sumOfShares(shares, entry.getValue());
			if (locatorQty.signum() <= 0)
			{
				// A frozen line already covers everything this chunk was computed for: nothing left to plan here.
				if (existingLine != null)
				{
					voidDDOrderFor(DDOrderId.ofRepoId(existingLine.getDD_Order_ID()));
					obsoleteLineIds.add(DDOrderLineId.ofRepoId(existingLine.getDD_OrderLine_ID()));
				}
				continue;
			}

			final DDOrderLineId lineId;
			final boolean lineCarriesTheRequiredQty;
			if (existingLine != null)
			{
				lineCarriesTheRequiredQty = updateDDOrderLineQtyInPlace(existingLine, locatorQty);
				lineId = DDOrderLineId.ofRepoId(existingLine.getDD_OrderLine_ID());
			}
			else
			{
				final I_DD_OrderLine createdLine = saveDraftDDOrder(CreateDDOrderReplenishmentRequest.builder()
						.pickingJobScheduleId(firstContributor.getId())
						.shipmentScheduleId(firstContributor.getShipmentScheduleId())
						.sourceWarehouseId(sourceWarehouseId)
						.targetWarehouseId(targetWarehouseId)
						.inTransitWarehouseId(inTransitWarehouseId)
						.locatorFromId(sourceLocatorId)
						.locatorToId(locatorToId)
						.docTypeId(docTypeId)
						.productId(productId)
						.qty(locatorQty)
						.orgId(orgId)
						.datePromised(SystemTime.asInstant())
						.build());
				lineId = DDOrderLineId.ofRepoId(createdLine.getDD_OrderLine_ID());
				lineCarriesTheRequiredQty = true;
				ddOrderService.complete(DDOrderId.ofRepoId(createdLine.getDD_Order_ID()));
				Loggables.addLog(
						"DD_Order picking replenishment: created DD_Order_ID={0} qty={1} from source M_Locator_ID={2}"
								+ " to target M_Locator_ID={3} for the product group of M_Product_ID={4} ({5} contributor(s))",
						createdLine.getDD_Order_ID(),
						locatorQty.toBigDecimal(),
						sourceLocatorId.getRepoId(),
						locatorToId.getRepoId(),
						productId.getRepoId(),
						contributorsInOrder.size());
			}

			if (lineCarriesTheRequiredQty)
			{
				contributorRepository.replaceByLineId(lineId, shares);
			}
			survivingLineIds.add(lineId);
		}

		// A DD_Order voided OUTSIDE the reconcile leaves its alloc rows behind; without this drop, the contributor
		// would keep resolving to that dead order alongside the fresh one.
		obsoleteLineIds.addAll(contributorRepository.getLineIdsByPickingJobScheduleIds(
				contributorsInOrder.stream()
						.map(PickingJobSchedule::getId)
						.collect(ImmutableSet.toImmutableSet())));
		obsoleteLineIds.removeAll(survivingLineIds);
		contributorRepository.deleteByLineIds(obsoleteLineIds);
	}

	@lombok.Value
	@VisibleForTesting
	static class FrozenSplit
	{
		/** Frozen source locator → the quantity {@link #updateDDOrderLineQtyInPlace} refuses to write there; only the refusal log reads it. */
		@NonNull Map<LocatorId, Quantity> refusedQtyByLocator;

		@NonNull ImmutableMap<LocatorId, ImmutableList<DDOrderLineContributor>> attribution;
	}

	/** Iterated to a fixed point: freezing one locator nets its shares off their contributors, which can turn another locator's growth into a shrink. */
	@VisibleForTesting
	FrozenSplit computeFrozenSplit(
			@NonNull final List<PickingJobSchedule> contributorsInOrder,
			@NonNull final Map<LocatorId, Quantity> requiredByLocator,
			@NonNull final Map<LocatorId, I_DD_OrderLine> existingLineByLocator)
	{
		final LinkedHashMap<LocatorId, Quantity> refusedQtyByLocator = new LinkedHashMap<>();

		while (true)
		{
			final Map<PickingJobScheduleId, Quantity> alreadyServedByContributor =
					sharesOfFrozenLines(refusedQtyByLocator.keySet(), existingLineByLocator);
			final ImmutableMap<LocatorId, Quantity> attributableByLocator = requiredByLocator.entrySet().stream()
					.filter(entry -> !refusedQtyByLocator.containsKey(entry.getKey()))
					.collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, Map.Entry::getValue));

			final ImmutableMap<LocatorId, ImmutableList<DDOrderLineContributor>> attribution =
					attribute(contributorsInOrder, attributableByLocator, alreadyServedByContributor);

			final LinkedHashMap<LocatorId, Quantity> newlyRefused = new LinkedHashMap<>();
			for (final Map.Entry<LocatorId, Quantity> entry : attributableByLocator.entrySet())
			{
				final I_DD_OrderLine existingLine = existingLineByLocator.get(entry.getKey());
				if (existingLine == null)
				{
					continue;
				}

				final Quantity plannedQty = sumOfShares(attribution.get(entry.getKey()), entry.getValue());
				if (isShrinkRefusedByDeliveredQty(existingLine, plannedQty))
				{
					newlyRefused.put(entry.getKey(), plannedQty);
				}
			}

			if (newlyRefused.isEmpty())
			{
				return new FrozenSplit(ImmutableMap.copyOf(refusedQtyByLocator), attribution);
			}
			refusedQtyByLocator.putAll(newlyRefused);
		}
	}

	/** Summed, because one contributor can hold a share on more than one frozen line. */
	private Map<PickingJobScheduleId, Quantity> sharesOfFrozenLines(
			@NonNull final Set<LocatorId> frozenLocatorIds,
			@NonNull final Map<LocatorId, I_DD_OrderLine> existingLineByLocator)
	{
		if (frozenLocatorIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		final ImmutableSet<DDOrderLineId> frozenLineIds = frozenLocatorIds.stream()
				.map(locatorId -> DDOrderLineId.ofRepoId(existingLineByLocator.get(locatorId).getDD_OrderLine_ID()))
				.collect(ImmutableSet.toImmutableSet());

		final LinkedHashMap<PickingJobScheduleId, Quantity> result = new LinkedHashMap<>();
		for (final DDOrderLineContributor share : contributorRepository.getByLineIds(frozenLineIds))
		{
			result.merge(share.getPickingJobScheduleId(), share.getQty(), Quantity::add);
		}
		return result;
	}

	/** {@code chunkQty} only supplies the UOM for the empty case. */
	private static Quantity sumOfShares(@NonNull final List<DDOrderLineContributor> shares, @NonNull final Quantity chunkQty)
	{
		return shares.stream()
				.map(DDOrderLineContributor::getQty)
				.reduce(Quantity::add)
				.orElseGet(chunkQty::toZero);
	}

	/** Partial coverage is allowed: an uncovered remainder is logged and left unfulfilled rather than routed to a fallback locator. */
	private Map<LocatorId, Quantity> computeRequiredAllocation(
			@NonNull final DDOrderReplenishmentGroupKey groupKey,
			@NonNull final WarehouseId sourceWarehouseId,
			@NonNull final ProductId productId,
			@NonNull final Quantity demandQty)
	{
		final Warehouse sourceWarehouse = warehouseRepository.getById(sourceWarehouseId);
		final List<LocatorId> sourceLocatorIds = sourceWarehouse.getGroundFloorLocatorIdsOrderedByPriority();
		if (sourceLocatorIds.isEmpty())
		{
			Loggables.addLog("No ground floor locators found for {}", sourceWarehouse.getName());
			return ImmutableMap.of();
		}

		// Sorted BEFORE the on-hand stream is opened, so the greedy loop can stop pulling chunks once the demand is covered.
		final List<LocatorId> sourceLocatorIdsInPickOrder = sourceLocatorIds.stream()
				.sorted(Comparator.comparing(this::getLocatorSortKey))
				.collect(ImmutableList.toImmutableList());

		final UOMConversionContext conversionCtx = UOMConversionContext.of(productId);

		final Stream<LocatorIdAndQty> orderedNonEmpty = ProductAvailableStockPerLocator.newInstance(handlingUnitsBL)
				.streamLocatorQtyOnHandOrdered(productId, 50, sourceLocatorIdsInPickOrder);

		final AllocationResult result = greedyAllocateOrdered(
				demandQty,
				orderedNonEmpty,
				availableStockingUom -> uomConversionBL.convertQuantityTo(availableStockingUom, conversionCtx, demandQty.getUomId()),
				(locatorId, availableStockingUom) -> Loggables.addLog(
						"DD_Order picking replenishment: skipping source M_Locator_ID={0} for M_Product_ID={1}:"
								+ " its on-hand qty {2} cannot be converted to the demand UOM (UomId={3}); treated as non-contributing",
						locatorId.getRepoId(),
						productId.getRepoId(),
						availableStockingUom,
						demandQty.getUomId().getRepoId()));

		if (result.getUncovered().signum() > 0)
		{
			Loggables.addLog(
					"DD_Order picking replenishment: on-hand stock for M_Product_ID={0} in source M_Warehouse_ID={1}"
							+ " covers only part of the summed demand of the product group targeting M_Locator_ID={2};"
							+ " uncovered remainder={3} left unfulfilled (watchdog will retry)",
					productId.getRepoId(),
					sourceWarehouseId.getRepoId(),
					groupKey.getLocatorToId().getRepoId(),
					result.getUncovered().toBigDecimal());
		}

		return result.getAllocation();
	}

	/** The pre-materialised-map variant of {@link #greedyAllocateOrdered}; only the UOM conversion is injected, so a cross-UOM case can be unit-tested. */
	@VisibleForTesting
	AllocationResult greedyAllocate(
			@NonNull final Quantity demandQty,
			@NonNull final ProductQtyOnHandByLocator qtyOnHandByLocator,
			@NonNull final ConvertToDemandUom convertToDemandUom,
			@NonNull final java.util.function.BiConsumer<LocatorId, Quantity> onSkippedLocator)
	{
		final Stream<LocatorIdAndQty> orderedNonEmpty = qtyOnHandByLocator.streamNonEmptyLocatorIds()
				.sorted(Comparator.comparing(this::getLocatorSortKey))
				.map(locatorId -> LocatorIdAndQty.of(locatorId, qtyOnHandByLocator.getQty(locatorId)));
		return greedyAllocateOrdered(demandQty, orderedNonEmpty, convertToDemandUom, onSkippedLocator);
	}

	/** Consumed through an iterator so the stream's lazy chunked stock fetch is short-circuited once the demand is covered. */
	private AllocationResult greedyAllocateOrdered(
			@NonNull final Quantity demandQty,
			@NonNull final Stream<LocatorIdAndQty> orderedNonEmpty,
			@NonNull final ConvertToDemandUom convertToDemandUom,
			@NonNull final java.util.function.BiConsumer<LocatorId, Quantity> onSkippedLocator)
	{
		final LinkedHashMap<LocatorId, Quantity> allocation = new LinkedHashMap<>();
		Quantity remaining = demandQty;

		try (Stream<LocatorIdAndQty> stream = orderedNonEmpty)
		{
			final Iterator<LocatorIdAndQty> iter = stream.iterator();
			while (remaining.signum() > 0 && iter.hasNext())
			{
				final LocatorIdAndQty locatorAndQty = iter.next();

				final Quantity availableStockingUom = locatorAndQty.getQty();
				final Quantity available;
				try
				{
					available = convertToDemandUom.convert(availableStockingUom);
				}
				catch (final NoUOMConversionException ex)
				{
					onSkippedLocator.accept(locatorAndQty.getLocatorId(), availableStockingUom);
					continue;
				}

				final Quantity allocated = remaining.min(available);
				allocation.put(locatorAndQty.getLocatorId(), allocated);
				remaining = remaining.subtract(allocated);
			}
		}

		return new AllocationResult(allocation, remaining);
	}

	/** Converts a locator's on-hand qty (product stocking UOM) into the demand UOM; may throw {@link NoUOMConversionException}. */
	@FunctionalInterface
	interface ConvertToDemandUom
	{
		Quantity convert(@NonNull Quantity availableStockingUom);
	}

	/** The per-locator allocation (insertion-ordered) and the uncovered demand remainder. */
	@lombok.Value
	@VisibleForTesting
	static class AllocationResult
	{
		@NonNull Map<LocatorId, Quantity> allocation;
		@NonNull Quantity uncovered;
	}

	/** Splits the allocation chunks back across the contributors, sequentially rather than proportionally: fractional shares are not wanted on piece goods. */
	@VisibleForTesting
	ImmutableMap<LocatorId, ImmutableList<DDOrderLineContributor>> attribute(
			@NonNull final List<PickingJobSchedule> contributorsInOrder,
			@NonNull final Map<LocatorId, Quantity> allocation)
	{
		return attribute(contributorsInOrder, allocation, ImmutableMap.of());
	}

	/** {@code alreadyServedByContributor} is subtracted first, so a frozen line's shares are not attributed a second time on the next locator. */
	@VisibleForTesting
	ImmutableMap<LocatorId, ImmutableList<DDOrderLineContributor>> attribute(
			@NonNull final List<PickingJobSchedule> contributorsInOrder,
			@NonNull final Map<LocatorId, Quantity> allocation,
			@NonNull final Map<PickingJobScheduleId, Quantity> alreadyServedByContributor)
	{
		final ImmutableMap.Builder<LocatorId, ImmutableList<DDOrderLineContributor>> result = ImmutableMap.builder();

		final Iterator<PickingJobSchedule> contributors = contributorsInOrder.iterator();
		PickingJobSchedule current = contributors.hasNext() ? contributors.next() : null;
		Quantity currentRemaining = current != null ? remainingDemandOf(current, alreadyServedByContributor) : null;

		for (final Map.Entry<LocatorId, Quantity> chunk : allocation.entrySet())
		{
			final ImmutableList.Builder<DDOrderLineContributor> rows = ImmutableList.builder();
			Quantity chunkRemaining = chunk.getValue();

			while (chunkRemaining.signum() > 0 && current != null)
			{
				if (currentRemaining.signum() <= 0)
				{
					current = contributors.hasNext() ? contributors.next() : null;
					currentRemaining = current != null ? remainingDemandOf(current, alreadyServedByContributor) : null;
					continue;
				}

				final Quantity taken = chunkRemaining.min(currentRemaining);
				rows.add(DDOrderLineContributor.of(current.getId(), taken));
				chunkRemaining = chunkRemaining.subtract(taken);
				currentRemaining = currentRemaining.subtract(taken);
			}

			result.put(chunk.getKey(), rows.build());
		}

		return result.build();
	}

	/** Floored at zero: a frozen line can carry MORE than the contributor still demands. */
	private static Quantity remainingDemandOf(
			@NonNull final PickingJobSchedule contributor,
			@NonNull final Map<PickingJobScheduleId, Quantity> alreadyServedByContributor)
	{
		final Quantity qtyToPick = contributor.getQtyToPick();
		final Quantity alreadyServed = alreadyServedByContributor.get(contributor.getId());
		if (alreadyServed == null)
		{
			return qtyToPick;
		}

		return qtyToPick.subtract(alreadyServed).toZeroIfNegative();
	}

	private String getLocatorSortKey(@NonNull final LocatorId locatorId)
	{
		final Locator loc = warehouseRepository.getLocatorById(locatorId);
		return buildLocatorSortKey(loc.getPriorityNo(), loc.getValue());
	}

	/** Yields PriorityNo ASC then Value ASC under plain lexicographic order; the pad width 10 matches {@code M_Locator.PriorityNo numeric(10,0)}, and negative values are not supported. */
	@VisibleForTesting
	static String buildLocatorSortKey(final int priorityNo, @NonNull final String value)
	{
		return Strings.padStart(Integer.toString(priorityNo), 10, '0') + "|" + value;
	}

	@lombok.Value
	private static class ExistingLineIndex
	{
		@NonNull Map<LocatorId, I_DD_OrderLine> byLocator;

		/** Orders no source locator could be resolved for; the caller must still dispose of them, or they outlive their alloc rows unreachably. */
		@NonNull List<I_DD_Order> unkeyable;
	}

	/** On a locator collision the OLDER order keeps the locator — the one a mover may already be working. */
	private ExistingLineIndex indexExistingBySourceLocator(@NonNull final List<I_DD_Order> existingDDOrders)
	{
		final LinkedHashMap<LocatorId, I_DD_OrderLine> byLocator = new LinkedHashMap<>();
		final ImmutableList.Builder<I_DD_Order> unkeyable = ImmutableList.builder();

		for (final I_DD_Order ddOrder : existingDDOrders)
		{
			final List<I_DD_OrderLine> lines = ddOrderLowLevelDAO.retrieveLines(ddOrder);
			if (lines.isEmpty())
			{
				unkeyable.add(ddOrder);
				continue;
			}
			final I_DD_OrderLine line = lines.get(0);
			// Resolved from the locator record rather than the line's M_Warehouse_ID, which a programmatically-built
			// DD_OrderLine does not reliably carry.
			final int sourceLocatorRepoId = line.getM_Locator_ID();
			if (sourceLocatorRepoId <= 0)
			{
				unkeyable.add(ddOrder);
				continue;
			}

			final Locator sourceLocator = warehouseRepository.getLocatorByRepoId(sourceLocatorRepoId);
			final I_DD_OrderLine alreadyIndexed = byLocator.putIfAbsent(sourceLocator.getLocatorId(), line);
			if (alreadyIndexed != null)
			{
				Loggables.addLog(
						"DD_Order picking replenishment: DD_Order_ID={0} is a SECOND live order on source M_Locator_ID={1}"
								+ " (DD_Order_ID={2} already holds it); disposing of it",
						ddOrder.getDD_Order_ID(),
						sourceLocatorRepoId,
						alreadyIndexed.getDD_Order_ID());
				unkeyable.add(ddOrder);
			}
		}

		return new ExistingLineIndex(byLocator, unkeyable.build());
	}

	/**
	 * @return {@code false} when the already-delivered guard left the line at its old quantity; the caller must then
	 * leave the line's contributor set alone too, so line quantity and shares stay in sync.
	 */
	private boolean updateDDOrderLineQtyInPlace(@NonNull final I_DD_OrderLine line, @NonNull final Quantity newQty)
	{
		final BigDecimal newQtyBD = newQty.toBigDecimal();
		if (alreadyCarriesQty(line, newQtyBD))
		{
			return true;
		}

		if (isShrinkRefusedByDeliveredQty(line, newQty))
		{
			Loggables.addLog(
					"DD_Order picking replenishment: not shrinking DD_OrderLine_ID={0} (DD_Order_ID={1}) in place:"
							+ " QtyDelivered={2} > 0 and the new qty {3} is lower than the ordered qty {4}; left untouched"
							+ " (its contributor shares are left untouched too)",
					line.getDD_OrderLine_ID(),
					line.getDD_Order_ID(),
					line.getQtyDelivered(),
					newQtyBD,
					line.getQtyOrdered());
			return false;
		}

		line.setQtyEntered(newQtyBD);
		line.setQtyOrdered(newQtyBD);
		line.setTargetQty(newQtyBD);
		ddOrderLowLevelDAO.save(line);
		Loggables.addLog(
				"DD_Order picking replenishment: updated DD_OrderLine_ID={0} (DD_Order_ID={1}) qty in place to {2}",
				line.getDD_OrderLine_ID(),
				line.getDD_Order_ID(),
				newQtyBD);
		return true;
	}

	private static boolean alreadyCarriesQty(@NonNull final I_DD_OrderLine line, @NonNull final BigDecimal qty)
	{
		return line.getQtyEntered().compareTo(qty) == 0
				&& line.getQtyOrdered().compareTo(qty) == 0
				&& line.getTargetQty().compareTo(qty) == 0;
	}

	/** Shared with {@link #computeFrozenSplit}, so the attribution and the write cannot disagree about which lines are frozen. */
	private static boolean isShrinkRefusedByDeliveredQty(@NonNull final I_DD_OrderLine line, @NonNull final Quantity newQty)
	{
		final BigDecimal newQtyBD = newQty.toBigDecimal();
		return !alreadyCarriesQty(line, newQtyBD)
				&& line.getQtyDelivered().signum() > 0
				&& newQtyBD.compareTo(line.getQtyOrdered()) < 0;
	}

	private I_DD_OrderLine saveDraftDDOrder(@NonNull final CreateDDOrderReplenishmentRequest request)
	{
		final OrgId orgId = request.getOrgId();

		//
		// Header — built here rather than in DDOrderLowLevelDAO, whose module cannot see I_M_Picking_Job_Schedule.
		final I_DD_Order ddOrder = InterfaceWrapperHelper.newInstance(I_DD_Order.class);
		ddOrder.setAD_Org_ID(orgId.getRepoId());
		// The order serves several customers at once, so it belongs to the org itself - as for every other internal DD_Order.
		final BPartnerLocationId orgBPLocationId = bpartnerOrgBL.retrieveOrgBPLocationId(orgId);
		ddOrder.setC_BPartner_ID(orgBPLocationId != null ? orgBPLocationId.getBpartnerId().getRepoId() : -1);
		ddOrder.setC_BPartner_Location_ID(BPartnerLocationId.toRepoId(orgBPLocationId));
		ddOrder.setC_DocType_ID(DocTypeId.toRepoId(request.getDocTypeId()));
		ddOrder.setM_Warehouse_ID(request.getInTransitWarehouseId().getRepoId());
		ddOrder.setM_Warehouse_From_ID(request.getSourceWarehouseId().getRepoId());
		ddOrder.setM_Warehouse_To_ID(request.getTargetWarehouseId().getRepoId());
		// Without it the libero DD_OrderLine interceptor logs a benign "@NotFound@ @PP_Plant_ID@" WARN.
		warehouseRepository.getPlantId(request.getTargetWarehouseId())
				.ifPresent(plantId -> ddOrder.setPP_Plant_ID(plantId.getRepoId()));
		ddOrder.setM_Picking_Job_Schedule_ID(request.getPickingJobScheduleId().getRepoId());
		ddOrder.setM_ShipmentSchedule_ID(request.getShipmentScheduleId().getRepoId());
		ddOrder.setDateOrdered(TimeUtil.asTimestamp(request.getDatePromised()));
		ddOrder.setDatePromised(TimeUtil.asTimestamp(request.getDatePromised()));
		ddOrder.setMRP_Generated(true);
		ddOrder.setMRP_AllowCleanup(true);
		ddOrder.setIsSOTrx(false);
		ddOrder.setIsInDispute(false);
		ddOrder.setIsInTransit(false);
		ddOrder.setDocStatus(X_DD_Order.DOCSTATUS_Drafted);
		ddOrder.setDocAction(X_DD_Order.DOCACTION_Complete);
		ddOrderLowLevelDAO.save(ddOrder);

		//
		// Line
		final I_DD_OrderLine ddOrderLine = InterfaceWrapperHelper.newInstance(I_DD_OrderLine.class);
		ddOrderLine.setDD_Order_ID(ddOrder.getDD_Order_ID());
		ddOrderLine.setAD_Org_ID(orgId.getRepoId());
		ddOrderLine.setDateOrdered(ddOrder.getDateOrdered());
		ddOrderLine.setDatePromised(ddOrder.getDatePromised());
		ddOrderLine.setM_Product_ID(request.getProductId().getRepoId());
		ddOrderLine.setC_UOM_ID(request.getQty().getUomId().getRepoId());
		// This flow operates in the assignment's UOM, so QtyEntered == QtyOrdered == TargetQty.
		ddOrderLine.setQtyEntered(request.getQty().toBigDecimal());
		ddOrderLine.setQtyOrdered(request.getQty().toBigDecimal());
		ddOrderLine.setTargetQty(request.getQty().toBigDecimal());
		ddOrderLine.setM_Locator_ID(request.getLocatorFromId().getRepoId());
		ddOrderLine.setM_LocatorTo_ID(request.getLocatorToId().getRepoId());
		ddOrderLine.setM_Picking_Job_Schedule_ID(request.getPickingJobScheduleId().getRepoId());
		ddOrderLine.setM_ShipmentSchedule_ID(request.getShipmentScheduleId().getRepoId());
		ddOrderLine.setIsInvoiced(false);
		ddOrderLowLevelDAO.save(ddOrderLine);

		return ddOrderLine;
	}

	/** Refuses while ANY contributor of the order is being picked. The verdict is read as it stands NOW, so the delete→void callers must take their own beforehand. */
	private void voidDDOrderFor(@NonNull final DDOrderId existingDDOrderId)
	{
		final BlockingWork busyPicker = findBlockingPickingWork(ddOrderLowLevelDAO.getById(existingDDOrderId));
		if (busyPicker != null)
		{
			throw newPickerBusyException(existingDDOrderId, busyPicker);
		}
		ddOrderService.voidIt(existingDDOrderId);
		Loggables.addLog("DD_Order picking replenishment: voided DD_Order_ID={0}", existingDDOrderId.getRepoId());

		detachFromPickingJobSchedule(ddOrderLowLevelDAO.getById(existingDDOrderId));
	}

	/** Mandatory for every order naming an assignment being deleted: the deferrable FK {@code mpickingjobschedule_ddorder} is checked at commit of the delete transaction. */
	private void detachFromPickingJobSchedule(@NonNull final I_DD_Order ddOrder)
	{
		ddOrder.setM_Picking_Job_Schedule_ID(-1);
		ddOrderLowLevelDAO.save(ddOrder);
		for (final I_DD_OrderLine ddOrderLine : ddOrderLowLevelDAO.retrieveLines(ddOrder))
		{
			ddOrderLine.setM_Picking_Job_Schedule_ID(-1);
			ddOrderLowLevelDAO.save(ddOrderLine);
		}
	}

	/** Runs in the delete transaction: both {@code mpickingjobschedule_ddorder} and the alloc rows' FK are DEFERRABLE INITIALLY DEFERRED, so anything left pointing at the assignment fails at commit. */
	public void voidDDOrdersForDeletedAssignment(@NonNull final PickingJobSchedule deletedAssignment)
	{
		final ImmutableSet<PickingJobScheduleId> jobScheduleIds = ImmutableSet.of(deletedAssignment.getId());
		final ImmutableSet<DDOrderLineId> servedLineIds = contributorRepository.getLineIdsByPickingJobScheduleIds(jobScheduleIds);

		// Captured while the departing assignment is still a contributor: after the delete its own delivery — very
		// often the one being picked — is no longer resolvable, and the set would report nobody busy.
		final ImmutableMap<DDOrderLineId, BlockingWork> blockingBeforeDeparture = findBlockingPickingWorkByLineId(servedLineIds, deletedAssignment);

		final boolean sharedOrderSurvived = disposeOfOrDetachBackReferencingDDOrders(deletedAssignment, blockingBeforeDeparture);
		contributorRepository.deleteByPickingJobScheduleIds(jobScheduleIds);

		voidDDOrdersLeftWithoutContributor(servedLineIds, blockingBeforeDeparture);

		if (sharedOrderSurvived)
		{
			// The watchdog only looks for deliveries with NO document, so a surviving order that is merely too big for
			// what is left of the group would stay too big.
			scheduleReconcileAfterCommit(deletedAssignment);
		}
	}

	/**
	 * Whether an order survives the departure is decided by its contributor set, not by the back-reference — that
	 * column names one arbitrary contributor, usually not the last one to go.
	 *
	 * @return whether at least one order outlived the departure and now carries more than the group still demands.
	 */
	private boolean disposeOfOrDetachBackReferencingDDOrders(
			@NonNull final PickingJobSchedule deletedAssignment,
			@NonNull final ImmutableMap<DDOrderLineId, BlockingWork> blockingBeforeDeparture)
	{
		boolean anySurvived = false;
		for (final I_DD_Order ddOrder : ddOrderLowLevelDAO.findActiveDDOrdersForPickingJobSchedule(deletedAssignment.getId()))
		{
			if (servesContributorsOtherThan(ddOrder, deletedAssignment.getId()))
			{
				detachFromPickingJobSchedule(ddOrder);
				anySurvived = true;
				Loggables.addLog(
						"DD_Order picking replenishment: DD_Order_ID={0} still serves other contributors, so the departure of"
								+ " M_Picking_Job_Schedule_ID={1} only cleared its back-reference; the order lives on",
						ddOrder.getDD_Order_ID(),
						deletedAssignment.getId().getRepoId());
			}
			else
			{
				final DDOrderId ddOrderId = DDOrderId.ofRepoId(ddOrder.getDD_Order_ID());
				final BlockingWork busyPicker = firstBlockingWorkOf(ddOrder, blockingBeforeDeparture);
				if (busyPicker != null)
				{
					throw newPickerBusyException(ddOrderId, busyPicker);
				}

				voidDDOrderFor(ddOrderId);
			}
		}
		return anySurvived;
	}

	@Nullable
	private BlockingWork firstBlockingWorkOf(
			@NonNull final I_DD_Order ddOrder,
			@NonNull final ImmutableMap<DDOrderLineId, BlockingWork> blockingBeforeDeparture)
	{
		return lineIdsOf(ImmutableList.of(ddOrder)).stream()
				.map(blockingBeforeDeparture::get)
				.filter(Objects::nonNull)
				.findFirst()
				.orElse(null);
	}

	/** Asked while the departing assignment's own alloc rows are still there, which is why it has to be excluded explicitly. */
	private boolean servesContributorsOtherThan(
			@NonNull final I_DD_Order ddOrder,
			@NonNull final PickingJobScheduleId departingAssignmentId)
	{
		return contributorRepository.getPickingJobScheduleIds(lineIdsOf(ImmutableList.of(ddOrder)))
				.stream()
				.anyMatch(contributorId -> !contributorId.equals(departingAssignmentId));
	}

	/** {@code blockingBeforeDeparture} cannot be recomputed here: reaching a line at all means its contributor set is empty, and an empty set has nobody busy in it. */
	private void voidDDOrdersLeftWithoutContributor(
			@NonNull final Set<DDOrderLineId> lineIds,
			@NonNull final ImmutableMap<DDOrderLineId, BlockingWork> blockingBeforeDeparture)
	{
		for (final DDOrderLineId lineId : lineIds)
		{
			if (!contributorRepository.getByLineId(lineId).isEmpty())
			{
				continue;
			}

			final DDOrderId ddOrderId = DDOrderId.ofRepoId(ddOrderLowLevelDAO.getLineById(lineId).getDD_Order_ID());
			final I_DD_Order ddOrder = ddOrderLowLevelDAO.getById(ddOrderId);
			// Already voided by the FK-resolved pass above, or a close-out disposition the reconcile must not touch.
			if (!X_DD_Order.DOCSTATUS_Completed.equals(ddOrder.getDocStatus())
					|| !ddOrder.isActive()
					|| ddOrder.isPickingDisconnected())
			{
				continue;
			}

			final BlockingWork busyPicker = blockingBeforeDeparture.get(lineId);
			if (busyPicker != null)
			{
				throw newPickerBusyException(ddOrderId, busyPicker);
			}

			voidDDOrderFor(ddOrderId);
		}
	}

	private void voidAllDDOrders(@NonNull final List<I_DD_Order> ddOrders)
	{
		for (final I_DD_Order ddOrder : ddOrders)
		{
			voidDDOrderFor(DDOrderId.ofRepoId(ddOrder.getDD_Order_ID()));
		}
	}

	/** An in-progress move is DISCONNECTed rather than closed: closing would hit the {@code BEFORE_CLOSE clearSchedules} guard and corrupt the half-done move. */
	private void disposeCloseOut(@NonNull final List<I_DD_Order> ddOrders)
	{
		for (final I_DD_Order ddOrder : ddOrders)
		{
			final DDOrderId ddOrderId = DDOrderId.ofRepoId(ddOrder.getDD_Order_ID());
			if (ddOrderMoveScheduleService.hasInProgressSchedules(ddOrderId))
			{
				disconnectDDOrderFor(ddOrderId);
			}
			else
			{
				closeDDOrderFor(ddOrderId);
			}
		}
	}

	/** The responsible user is released too, so the DD_Order-backed mobile DistributionJob retires from the launcher. */
	private void closeDDOrderFor(@NonNull final DDOrderId ddOrderId)
	{
		ddOrderService.close(ddOrderId);
		ddOrderService.unassignFromResponsible(ddOrderId);

		Loggables.addLog(
				"DD_Order picking replenishment: closed obsolete replenishment DD_Order_ID={0} on shipment close-out"
						+ " and released AD_User_Responsible_ID",
				ddOrderId.getRepoId());
	}

	/** The guard/reconcile lookups stop seeing it, while its FKs and DistributionJob assignment are retained for the worker to finish. */
	private void disconnectDDOrderFor(@NonNull final DDOrderId ddOrderId)
	{
		ddOrderService.markAsPickingDisconnected(ddOrderId);

		Loggables.addLog(
				"DD_Order picking replenishment: disconnected (IsPickingDisconnected=Y) in-progress replenishment"
						+ " DD_Order_ID={0} on shipment close-out; FKs retained, DistributionJob stays live for the worker",
				ddOrderId.getRepoId());
	}

	public void rebuildDrift()
	{
		final ProgressLogger progress = Loggables.get().newProgress();

		// The shipment schedules are loaded in ONE batch, because the request needs the schedule's product and a
		// per-assignment load would be one round-trip per open assignment on every pass.
		final ImmutableList<PickingJobSchedule> assignments = streamAssignmentsNeedingDDOrder().collect(ImmutableList.toImmutableList());
		if (!assignments.isEmpty())
		{
			final Map<ShipmentScheduleId, I_M_ShipmentSchedule> schedules = shipmentScheduleBL.getByIds(
					assignments.stream()
							.map(PickingJobSchedule::getShipmentScheduleId)
							.collect(ImmutableSet.toImmutableSet()));

			assignments.stream()
					.map(assignment -> toReplenishmentRequest(assignment, schedules.get(assignment.getShipmentScheduleId())))
					.distinct()
					.peek(progress::itemProcessed)
					.forEach(reconciliationEventPublisher::publishOne);
		}

		progress.done("Enqueued {} requests");
	}

	/** Which of {@code assignmentIds} the watchdog still considers unserved. Restricted to the caller's ids so the result stays bounded. */
	public ImmutableSet<PickingJobScheduleId> retainAssignmentsNeedingDDOrder(@NonNull final Set<PickingJobScheduleId> assignmentIds)
	{
		if (assignmentIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		try (final Stream<PickingJobSchedule> assignments = streamAssignmentsNeedingDDOrder())
		{
			return assignments.map(PickingJobSchedule::getId)
					.filter(assignmentIds::contains)
					.collect(ImmutableSet.toImmutableSet());
		}
	}

	/** Served-ness is resolved through the contributor association; a back-reference column names only one contributor of a consolidated order. */
	private Stream<PickingJobSchedule> streamAssignmentsNeedingDDOrder()
	{
		return pickingJobScheduleService.streamAssignmentsNeedingDDOrder(
				contributorRepository.queryByLines(ddOrderLowLevelDAO.queryCompletedDDOrderLines()));
	}

	public void assertWarehouseConfigurationIsValid(@NonNull final I_M_Warehouse warehouse)
	{
		if (warehouse.isAutoDistributionOrder() && warehouse.getDD_NetworkDistribution_ID() <= 0)
		{
			throw new AdempiereException(MSG_DDOrderPickingReplenishment_MandatoryNetwork);
		}
	}

	@Value(staticConstructor = "of")
	private static class BlockingWork
	{
		@NonNull PickingJobScheduleId jobScheduleId;
		@NonNull ShipmentScheduleId shipmentScheduleId;
	}

	/** Evaluated over the order's COMPLETE contributor set; an order carrying no contributor row at all reports nobody busy. */
	@Nullable
	private BlockingWork findBlockingPickingWork(@NonNull final I_DD_Order ddOrder)
	{
		final ImmutableList<PickingJobSchedule> contributors = contributorsOf(lineIdsOf(ImmutableList.of(ddOrder)));
		if (contributors.isEmpty())
		{
			return null;
		}

		final ImmutableSet<ShipmentScheduleId> busyScheduleIds = pickingJobRepository.retrieveScheduleIdsWithActivePickingJobLine(
				contributors.stream()
						.map(PickingJobSchedule::getShipmentScheduleId)
						.collect(ImmutableSet.toImmutableSet()));

		for (final PickingJobSchedule contributor : contributors)
		{
			if (busyScheduleIds.contains(contributor.getShipmentScheduleId()))
			{
				return BlockingWork.of(contributor.getId(), contributor.getShipmentScheduleId());
			}
		}
		return null;
	}

	/** The per-line form of {@link #findBlockingPickingWork(I_DD_Order)}; {@code departingAssignment} is folded in because its own row is already deleted and no longer resolvable. */
	private ImmutableMap<DDOrderLineId, BlockingWork> findBlockingPickingWorkByLineId(
			@NonNull final Set<DDOrderLineId> lineIds,
			@NonNull final PickingJobSchedule departingAssignment)
	{
		if (lineIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		final ImmutableMap<DDOrderLineId, ImmutableList<PickingJobSchedule>> contributorsByLineId = lineIds.stream()
				.collect(ImmutableMap.toImmutableMap(
						lineId -> lineId,
						lineId -> Stream.concat(contributorsOf(ImmutableSet.of(lineId)).stream(), Stream.of(departingAssignment))
								.sorted(Comparator.comparing(PickingJobSchedule::getId))
								.collect(ImmutableList.toImmutableList())));

		final ImmutableSet<ShipmentScheduleId> shipmentScheduleIds = contributorsByLineId.values().stream()
				.flatMap(List::stream)
				.map(PickingJobSchedule::getShipmentScheduleId)
				.collect(ImmutableSet.toImmutableSet());
		if (shipmentScheduleIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		final ImmutableSet<ShipmentScheduleId> busyScheduleIds = pickingJobRepository.retrieveScheduleIdsWithActivePickingJobLine(shipmentScheduleIds);
		if (busyScheduleIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		final ImmutableMap.Builder<DDOrderLineId, BlockingWork> result = ImmutableMap.builder();
		contributorsByLineId.forEach((lineId, contributors) -> contributors.stream()
				.filter(contributor -> busyScheduleIds.contains(contributor.getShipmentScheduleId()))
				.findFirst()
				.ifPresent(contributor -> result.put(lineId, BlockingWork.of(contributor.getId(), contributor.getShipmentScheduleId()))));
		return result.build();
	}

	/** A contributor that is NOT the assignment being edited is preferred, because telling the editor about their own assignment says nothing about who else is on the document. */
	@NonNull
	private BlockingWork findBlockingMovedWork(
			@NonNull final DDOrderLineId lineId,
			@NonNull final BlockingWork changedAssignment)
	{
		BlockingWork blocking = null;
		for (final PickingJobSchedule contributor : contributorsOf(ImmutableSet.of(lineId)))
		{
			if (blocking == null)
			{
				blocking = BlockingWork.of(contributor.getId(), contributor.getShipmentScheduleId());
			}
			if (!contributor.getId().equals(changedAssignment.getJobScheduleId()))
			{
				return BlockingWork.of(contributor.getId(), contributor.getShipmentScheduleId());
			}
		}
		return blocking != null ? blocking : changedAssignment;
	}

	/** Ordered so that the contributor a refusal names is the same on every run — an operator cannot report a message that changes between two identical situations. */
	private ImmutableList<PickingJobSchedule> contributorsOf(@NonNull final Set<DDOrderLineId> lineIds)
	{
		final ImmutableSet<PickingJobScheduleId> contributorIds = contributorRepository.getPickingJobScheduleIds(lineIds);
		if (contributorIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return pickingJobScheduleService.getByIds(contributorIds).stream()
				.sorted(Comparator.comparing(PickingJobSchedule::getId))
				.collect(ImmutableList.toImmutableList());
	}

	private AdempiereException newPickerBusyException(@NonNull final DDOrderId ddOrderId, @NonNull final BlockingWork blocking)
	{
		return new AdempiereException(
				MSG_DDOrderPickingReplenishment_PickerBusy,
				asMessageValue(ddOrderId),
				asMessageValue(blocking.getJobScheduleId()),
				asMessageValue(blocking.getShipmentScheduleId()));
	}

	/** A record id goes into an AD_Message as text: {@code MessageFormat} would push a number through the reader's locale format and render it as "1.234.567". */
	private static String asMessageValue(@NonNull final RepoIdAware id)
	{
		return String.valueOf(id.getRepoId());
	}

	private WarehouseId getFirstSourceWarehouseIdOrThrow(
			@NonNull final Warehouse targetWarehouse,
			@NonNull final ProductId productId)
	{
		final DistributionNetworkId networkId = targetWarehouse.getDistributionNetworkId();
		return resolveSourceWarehouse(targetWarehouse.getWarehouseId(), networkId)
				.orElseThrow(() -> new AdempiereException(MSG_DDOrderPickingReplenishment_NetworkGap, networkId, productId));
	}

	@VisibleForTesting
	Optional<WarehouseId> resolveSourceWarehouse(
			@NonNull final WarehouseId targetWarehouseId,
			@Nullable final DistributionNetworkId networkId)
	{
		if (networkId == null)
		{
			return Optional.empty();
		}

		final DistributionNetwork network = distributionNetworkRepository.getById(networkId);
		return network.getFirstSourceWarehouseIdByTargetWarehouse(targetWarehouseId);
	}
}
