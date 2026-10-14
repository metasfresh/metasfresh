package de.metas.distribution.ddorder.replenishment;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Sets;
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
import de.metas.document.engine.DocStatus;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_DD_OrderLine_PickingJobSchedule;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.job_schedule.service.PickingJobScheduleService;
import de.metas.handlingunits.storage.LocatorIdAndQty;
import de.metas.handlingunits.storage.ProductAvailableStockPerLocator;
import de.metas.handlingunits.storage.ProductQtyOnHandByLocator;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.PriorityRule;
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
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_C_DocType;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.X_DD_Order;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

	/**
	 * Refuses re-planning a workstation assignment while any contributor of its product group is already being replenished.
	 */
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

		final ImmutableList<I_DD_Order> ddOrders = findLiveDDOrdersOfAffectedGroups(jobSchedule);
		if (ddOrders.isEmpty())
		{
			return;
		}

		final ImmutableMap<DDOrderId, BlockingWork> blockingByDDOrderId = findBlockingPickingWork(ddOrders);
		final ImmutableListMultimap<Integer, I_DD_OrderLine> linesByOrderId = Multimaps.index(
				ddOrderLowLevelDAO.retrieveLines(ImmutableSet.copyOf(ddOrders)),
				I_DD_OrderLine::getDD_Order_ID);

		for (final I_DD_Order ddOrder : ddOrders)
		{
			final DDOrderId ddOrderId = DDOrderId.ofRepoId(ddOrder.getDD_Order_ID());

			final BlockingWork busyPicker = blockingByDDOrderId.get(ddOrderId);
			if (busyPicker != null)
			{
				throw newPickerBusyException(ddOrderId, busyPicker);
			}
			// DEAD GUARD: no production flow writes DD_OrderLine.QtyInTransit or QtyDelivered — the only writers are
			// MDDOrderLine's zero-initialisers — so this refusal can only fire for a caller that seeds those columns itself.
			// The live "goods are already moving" signal is an in-progress DD_Order_MoveSchedule, see ordersHoldingMovedGoods below;
			// re-keying this guard onto it is tracked separately and deliberately not done here.
			for (final I_DD_OrderLine line : linesByOrderId.get(ddOrder.getDD_Order_ID()))
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
		for (final DDOrderReplenishmentRequest request : affectedReplenishmentRequests(jobSchedule))
		{
			final DDOrderReplenishmentGroupKey groupKey = request.getGroupKey();
			final List<I_DD_Order> ddOrders = ddOrderLowLevelDAO.findActiveDDOrdersForReplenishmentGroup(
					groupKey.getProductId(),
					groupKey.getLocatorToId(),
					groupKey.getUomId(),
					contributorRepository.queryAll());
			ddOrders.forEach(ddOrder -> ddOrdersById.putIfAbsent(DDOrderId.ofRepoId(ddOrder.getDD_Order_ID()), ddOrder));
		}
		return ImmutableList.copyOf(ddOrdersById.values());
	}

	/**
	 * The assignment's current group plus, when a group-key column changed, the group it is moving OUT of. Requires the CHANGED record: only it carries the old values.
	 */
	private ImmutableSet<DDOrderReplenishmentRequest> affectedReplenishmentRequests(@NonNull final I_M_Picking_Job_Schedule jobSchedule)
	{
		final ImmutableSet.Builder<DDOrderReplenishmentRequest> requests = ImmutableSet.builder();
		requests.add(toReplenishmentRequest(PickingJobScheduleRepository.fromRecord(jobSchedule)));

		final I_M_Picking_Job_Schedule oldRecord = InterfaceWrapperHelper.createOld(jobSchedule, I_M_Picking_Job_Schedule.class);
		requests.add(toReplenishmentRequest(PickingJobScheduleRepository.fromRecord(oldRecord)));

		return requests.build();
	}

	public void scheduleReconcileAfterCommit(@NonNull final PickingJobSchedule jobSchedule)
	{
		trxManager.accumulateAndProcessAfterCommit(
				TRX_PROPERTY_ScheduleReconcile,
				ImmutableSet.of(toReplenishmentRequest(jobSchedule)),
				reconciliationEventPublisher::publishAll);
	}

	/**
	 * For a CHANGED assignment: the group it left is reconciled too, else that group's order keeps a line sized for a contributor that has moved away.
	 */
	public void scheduleReconcileOfAffectedGroupsAfterCommit(@NonNull final I_M_Picking_Job_Schedule jobSchedule)
	{
		trxManager.accumulateAndProcessAfterCommit(
				TRX_PROPERTY_ScheduleReconcile,
				affectedReplenishmentRequests(jobSchedule),
				reconciliationEventPublisher::publishAll);
	}

	private DDOrderReplenishmentRequest toReplenishmentRequest(@NonNull final PickingJobSchedule jobSchedule)
	{
		return toReplenishmentRequest(jobSchedule, shipmentScheduleBL.getById(jobSchedule.getShipmentScheduleId()));
	}

	/**
	 * For callers that have already batch-loaded the shipment schedules.
	 */
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

	/**
	 * Reconciles one product group: its summed demand is planned as one DD_Order per contributing source locator. The caller must provide the transaction.
	 */
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
				final ImmutableSet<DDOrderLineId> disconnectedLineIds = disposeVoidOut(existingDDOrders);
				// A voided order must not still answer "which DD_Order serves this delivery?" — a DISCONNECTED one must,
				// else the delivery loses its navigation to the move its worker is finishing.
				contributorRepository.deleteByLineIds(Sets.difference(lineIds, disconnectedLineIds));
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

	/**
	 * Entry point for tests and operations; production goes through the event topic, whose payload already carries the group key.
	 */
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
	@VisibleForTesting
	Comparator<PickingJobSchedule> attributionOrder(@NonNull final Map<ShipmentScheduleId, I_M_ShipmentSchedule> schedules)
	{
		return Comparator
				.comparing(
						(final PickingJobSchedule contributor) -> shipmentScheduleEffectiveBL.getPriorityRule(schedules.get(contributor.getShipmentScheduleId())),
						PriorityRule.HIGH_TO_LOW)
				.thenComparing(contributor -> shipmentScheduleEffectiveBL.getPreparationDate(schedules.get(contributor.getShipmentScheduleId())))
				.thenComparing(contributor -> contributor.getId());
	}

	/**
	 * An empty set means the group's orders carry no alloc row at all, which is not a close-out.
	 */
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
		return ddOrderLowLevelDAO.retrieveLines(ImmutableSet.copyOf(ddOrders))
				.stream()
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

		// Every contributor of the group delivers to the same locator, so any of them resolves the same target warehouse.
		final I_M_ShipmentSchedule firstSchedule = shipmentScheduleBL.getById(contributorsInOrder.get(0).getShipmentScheduleId());

		final WarehouseId targetWarehouseId = shipmentScheduleEffectiveBL.getWarehouseId(firstSchedule);
		final Warehouse targetWarehouse = warehouseRepository.getById(targetWarehouseId);
		final WarehouseId sourceWarehouseId = getFirstSourceWarehouseIdOrThrow(targetWarehouse, productId);

		// Running the greedy once over the SUM is what makes over-allocation impossible: two deliveries can no longer
		// each claim the same on-hand units.
		final AllocationResult allocation = computeRequiredAllocation(sourceWarehouseId, productId, groupDemand);
		final Map<LocatorId, Quantity> requiredByLocator = allocation.getAllocation();

		// Refuse the whole reconcile before mutating anything; every void below relies on this verdict.
		final ImmutableMap<DDOrderId, BlockingWork> blockingByDDOrderId = findBlockingPickingWork(existingDDOrders);
		if (!blockingByDDOrderId.isEmpty())
		{
			final Map.Entry<DDOrderId, BlockingWork> blocked = blockingByDDOrderId.entrySet().iterator().next();
			throw newPickerBusyException(blocked.getKey(), blocked.getValue());
		}

		// ONE query for every existing order's lines: the index, the disposal decisions below and the cleanup all read it.
		final ImmutableListMultimap<Integer, I_DD_OrderLine> linesByOrderId = Multimaps.index(
				ddOrderLowLevelDAO.retrieveLines(ImmutableSet.copyOf(existingDDOrders)),
				I_DD_OrderLine::getDD_Order_ID);

		final ExistingLineIndex existingLines = indexExistingBySourceLocator(existingDDOrders, linesByOrderId);
		final Map<LocatorId, I_DD_OrderLine> existingLineByLocator = existingLines.getByLocator();

		final HashSet<DDOrderLineId> obsoleteLineIds = new HashSet<>();

		// A disconnected duplicate is a move the worker still finishes: its share is netted off the re-plan, its alloc row survives the cleanup.
		final HashSet<DDOrderLineId> disconnectedLineIds = new HashSet<>(
				ddOrderLowLevelDAO.findDisconnectedLineIdsForReplenishmentGroup(
						productId, locatorToId, groupKey.getUomId(), contributorRepository.queryAll()));

		// ONE query for the whole pass: both disposal loops below draw their candidates from existingDDOrders.
		final ImmutableSet<DDOrderId> ordersHoldingMovedGoods = ordersHoldingMovedGoods(ddOrderIdsOf(existingDDOrders));

		// Left live, an unkeyable order would lose its alloc rows to the cleanup below and become unreachable.
		for (final I_DD_Order unkeyableDDOrder : existingLines.getUnkeyable())
		{
			disposeObsoleteDDOrder(
					DDOrderId.ofRepoId(unkeyableDDOrder.getDD_Order_ID()),
					linesByOrderId.get(unkeyableDDOrder.getDD_Order_ID()),
					ordersHoldingMovedGoods,
					obsoleteLineIds,
					disconnectedLineIds);
		}

		disposeOrdersOfLocatorsNoLongerRequired(
				existingLineByLocator,
				requiredByLocator.keySet(),
				linesByOrderId,
				ordersHoldingMovedGoods,
				obsoleteLineIds,
				disconnectedLineIds);

		final Map<PickingJobScheduleId, Quantity> disconnectedServedByContributor = sumSharesByContributor(disconnectedLineIds);
		logUncoveredRemainder(groupKey, sourceWarehouseId, allocation.getUncovered(), disconnectedServedByContributor);
		final FrozenSplit split = computeFrozenSplit(contributorsInOrder, requiredByLocator, existingLineByLocator, disconnectedServedByContributor);
		final Map<LocatorId, Quantity> refusedQtyByLocator = split.getRefusedQtyByLocator();
		final ImmutableMap<LocatorId, ImmutableList<DDOrderLineContributor>> attribution = split.getAttribution();

		final DocTypeId docTypeId = docTypeDAO.getDocTypeId(
				DocTypeQuery.builder()
						.docBaseType(X_C_DocType.DOCBASETYPE_DistributionOrder)
						.adClientId(clientAndOrgId.getClientId().getRepoId())
						.adOrgId(orgId.getRepoId())
						.build());
		final WarehouseId inTransitWarehouseId = warehouseRepository.getInTransitWarehouseId(orgId);

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
					voidDDOrder(DDOrderId.ofRepoId(existingLine.getDD_Order_ID()));
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
		obsoleteLineIds.addAll(findLineIdsOfDeadDDOrders(
				contributorRepository.getLineIdsByPickingJobScheduleIds(
						contributorsInOrder.stream()
								.map(PickingJobSchedule::getId)
								.collect(ImmutableSet.toImmutableSet())),
				groupKey));
		obsoleteLineIds.removeAll(survivingLineIds);
		// The disconnect deliberately KEEPS its association, else the delivery loses its navigation to the in-progress move.
		obsoleteLineIds.removeAll(disconnectedLineIds);
		contributorRepository.deleteByLineIds(obsoleteLineIds);
	}

	/**
	 * The locators the fresh allocation no longer needs: their order is obsolete, so it is disposed of. Runs BEFORE the
	 * frozen split, so an order that turns out to be merely DISCONNECTED has its shares netted off the re-plan instead
	 * of being planned a second time.
	 *
	 * @param ordersHoldingMovedGoods the pass's {@link #ordersHoldingMovedGoods(Collection)} verdict
	 * @param obsoleteLineIds         collects the lines of every VOIDED order: their alloc rows are dropped
	 * @param disconnectedLineIds     collects the lines of every DISCONNECTED order: their alloc rows survive
	 */
	@VisibleForTesting
	void disposeOrdersOfLocatorsNoLongerRequired(
			@NonNull final Map<LocatorId, I_DD_OrderLine> existingLineByLocator,
			@NonNull final Set<LocatorId> requiredLocatorIds,
			@NonNull final ImmutableListMultimap<Integer, I_DD_OrderLine> linesByOrderId,
			@NonNull final Set<DDOrderId> ordersHoldingMovedGoods,
			@NonNull final Set<DDOrderLineId> obsoleteLineIds,
			@NonNull final Set<DDOrderLineId> disconnectedLineIds)
	{
		for (final Map.Entry<LocatorId, I_DD_OrderLine> entry : existingLineByLocator.entrySet())
		{
			if (requiredLocatorIds.contains(entry.getKey()))
			{
				continue;
			}

			final int ddOrderRepoId = entry.getValue().getDD_Order_ID();
			disposeObsoleteDDOrder(
					DDOrderId.ofRepoId(ddOrderRepoId),
					linesByOrderId.get(ddOrderRepoId),
					ordersHoldingMovedGoods,
					obsoleteLineIds,
					disconnectedLineIds);
		}
	}

	/**
	 * Disposes of ONE order the reconcile no longer wants, and files its lines under the outcome: a DISCONNECTED order
	 * keeps its alloc rows, a VOIDED one loses them.
	 */
	private void disposeObsoleteDDOrder(
			@NonNull final DDOrderId ddOrderId,
			@NonNull final Collection<I_DD_OrderLine> ddOrderLines,
			@NonNull final Set<DDOrderId> ordersHoldingMovedGoods,
			@NonNull final Set<DDOrderLineId> obsoleteLineIds,
			@NonNull final Set<DDOrderLineId> disconnectedLineIds)
	{
		final Set<DDOrderLineId> disposedLineIds = disposeObsoleteDDOrder(ddOrderId, ordersHoldingMovedGoods)
				? disconnectedLineIds
				: obsoleteLineIds;
		addLineIdsTo(ddOrderLines, disposedLineIds);
	}

	/**
	 * The flavour for a caller that only needs to know about the disconnected outcome.
	 *
	 * @return {@code true} when the order was DISCONNECTED — its alloc rows must be KEPT; {@code false} when it was voided.
	 */
	private boolean disposeObsoleteDDOrder(
			@NonNull final DDOrderId ddOrderId,
			@NonNull final Set<DDOrderId> ordersHoldingMovedGoods)
	{
		if (ordersHoldingMovedGoods.contains(ddOrderId))
		{
			disconnectDDOrderFor(ddOrderId);
			return true;
		}

		voidDDOrder(ddOrderId);
		return false;
	}

	private static void addLineIdsTo(@NonNull final Collection<I_DD_OrderLine> lines, @NonNull final Set<DDOrderLineId> target)
	{
		lines.forEach(line -> target.add(DDOrderLineId.ofRepoId(line.getDD_OrderLine_ID())));
	}

	/**
	 * The void-or-disconnect decision every disposal site shares: voiding an order whose goods a mover already has in his
	 * hands would strand that stock, so such an order is DISCONNECTED instead — out of the reconcile lookups, still live
	 * for him to finish the move.
	 *
	 * <p>The signal is an IN_PROGRESS {@code DD_Order_MoveSchedule} (what the mobile mover actually writes), and it is the
	 * SAME predicate the {@code DD_Order} interceptor's {@code BEFORE_VOID} / {@code BEFORE_CLOSE} veto uses
	 * ({@code clearSchedules}) — so this decision can never try to void an order the document engine will refuse to void.
	 * Asked per ORDER, like that veto, and answered for the whole candidate set in ONE query.</p>
	 *
	 * @return the subset of {@code ddOrderIds} that must be DISCONNECTED rather than voided or closed.
	 */
	private ImmutableSet<DDOrderId> ordersHoldingMovedGoods(@NonNull final Collection<DDOrderId> ddOrderIds)
	{
		return ddOrderMoveScheduleService.retrieveIdsOfOrdersWithInProgressSchedules(ImmutableSet.copyOf(ddOrderIds));
	}

	private static ImmutableSet<DDOrderId> ddOrderIdsOf(@NonNull final Collection<I_DD_Order> ddOrders)
	{
		return ddOrders.stream()
				.map(ddOrder -> DDOrderId.ofRepoId(ddOrder.getDD_Order_ID()))
				.collect(ImmutableSet.toImmutableSet());
	}

	/**
	 * The group's lines whose DD_Order is gone. A CLOSED or still-live order is NOT gone: dropping its alloc rows would
	 * cost the delivery its navigation to that movement.
	 */
	@VisibleForTesting
	ImmutableSet<DDOrderLineId> findLineIdsOfDeadDDOrders(
			@NonNull final Set<DDOrderLineId> lineIds,
			@NonNull final DDOrderReplenishmentGroupKey groupKey)
	{
		if (lineIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		// A contributor that arrived from ANOTHER group still has a row there, and that group's line is still
		// serving its own remaining contributors; dropping it by line id would wipe them too.
		final ImmutableList<I_DD_OrderLine> linesOfGroup = ddOrderLowLevelDAO.getLinesByIds(ImmutableSet.copyOf(lineIds))
				.stream()
				.filter(line -> isLineOfGroup(line, groupKey))
				.collect(ImmutableList.toImmutableList());
		if (linesOfGroup.isEmpty())
		{
			return ImmutableSet.of();
		}

		final ImmutableSet<DDOrderId> deadDDOrderIds = ddOrderLowLevelDAO.getByIds(ddOrderIdsOfLines(linesOfGroup))
				.stream()
				.filter(DDOrderPickingReplenishmentService::isDeadDDOrder)
				.map(ddOrder -> DDOrderId.ofRepoId(ddOrder.getDD_Order_ID()))
				.collect(ImmutableSet.toImmutableSet());

		return linesOfGroup.stream()
				.filter(line -> deadDDOrderIds.contains(DDOrderId.ofRepoId(line.getDD_Order_ID())))
				.map(line -> DDOrderLineId.ofRepoId(line.getDD_OrderLine_ID()))
				.collect(ImmutableSet.toImmutableSet());
	}

	/**
	 * Voided, reversed or deactivated: only Post is left as a doc action, so it can never again serve the delivery.
	 */
	private static boolean isDeadDDOrder(@NonNull final I_DD_Order ddOrder)
	{
		return !ddOrder.isActive()
				|| DocStatus.ofNullableCodeOrUnknown(ddOrder.getDocStatus()).isReversedOrVoided();
	}

	private static ImmutableSet<DDOrderId> ddOrderIdsOfLines(@NonNull final Collection<I_DD_OrderLine> lines)
	{
		return lines.stream()
				.map(line -> DDOrderId.ofRepoId(line.getDD_Order_ID()))
				.collect(ImmutableSet.toImmutableSet());
	}

	private static boolean isLineOfGroup(@NonNull final I_DD_OrderLine line, @NonNull final DDOrderReplenishmentGroupKey groupKey)
	{
		return line.getM_Product_ID() == groupKey.getProductId().getRepoId()
				&& line.getM_LocatorTo_ID() == groupKey.getLocatorToId().getRepoId()
				&& line.getC_UOM_ID() == groupKey.getUomId().getRepoId();
	}

	@Value
	@VisibleForTesting
	static class FrozenSplit
	{
		/**
		 * Frozen source locator → the quantity {@link #updateDDOrderLineQtyInPlace} refuses to write there; only the refusal log reads it.
		 */
		@NonNull Map<LocatorId, Quantity> refusedQtyByLocator;

		@NonNull ImmutableMap<LocatorId, ImmutableList<DDOrderLineContributor>> attribution;
	}

	/**
	 * Iterated to a fixed point: freezing one locator nets its shares off their contributors, which can turn another locator's growth into a shrink.
	 */
	@VisibleForTesting
	FrozenSplit computeFrozenSplit(
			@NonNull final List<PickingJobSchedule> contributorsInOrder,
			@NonNull final Map<LocatorId, Quantity> requiredByLocator,
			@NonNull final Map<LocatorId, I_DD_OrderLine> existingLineByLocator,
			@NonNull final Map<PickingJobScheduleId, Quantity> disconnectedServedByContributor)
	{
		final LinkedHashMap<LocatorId, Quantity> refusedQtyByLocator = new LinkedHashMap<>();

		while (true)
		{
			final Map<PickingJobScheduleId, Quantity> alreadyServedByContributor = mergeServed(
					disconnectedServedByContributor,
					sharesOfFrozenLines(refusedQtyByLocator.keySet(), existingLineByLocator));
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

		return sumSharesByContributor(frozenLineIds);
	}

	/**
	 * Summed, because one contributor can hold a share on more than one line.
	 */
	private Map<PickingJobScheduleId, Quantity> sumSharesByContributor(@NonNull final Set<DDOrderLineId> lineIds)
	{
		if (lineIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		final LinkedHashMap<PickingJobScheduleId, Quantity> result = new LinkedHashMap<>();
		for (final DDOrderLineContributor share : contributorRepository.getByLineIds(lineIds))
		{
			result.merge(share.getPickingJobScheduleId(), share.getQty(), Quantity::add);
		}
		return result;
	}

	/**
	 * A contributor can be served by both a disconnected order and a frozen line, so the two maps add rather than one winning.
	 */
	private static Map<PickingJobScheduleId, Quantity> mergeServed(
			@NonNull final Map<PickingJobScheduleId, Quantity> a,
			@NonNull final Map<PickingJobScheduleId, Quantity> b)
	{
		if (a.isEmpty())
		{
			return b;
		}
		if (b.isEmpty())
		{
			return a;
		}

		final LinkedHashMap<PickingJobScheduleId, Quantity> result = new LinkedHashMap<>(a);
		b.forEach((contributorId, qty) -> result.merge(contributorId, qty, Quantity::add));
		return result;
	}

	/**
	 * {@code chunkQty} only supplies the UOM for the empty case.
	 */
	private static Quantity sumOfShares(@NonNull final List<DDOrderLineContributor> shares, @NonNull final Quantity chunkQty)
	{
		return shares.stream()
				.map(DDOrderLineContributor::getQty)
				.reduce(Quantity::add)
				.orElseGet(chunkQty::toZero);
	}

	/**
	 * Partial coverage is allowed: an uncovered remainder is left unfulfilled rather than routed to a fallback locator; the caller logs it.
	 */
	private AllocationResult computeRequiredAllocation(
			@NonNull final WarehouseId sourceWarehouseId,
			@NonNull final ProductId productId,
			@NonNull final Quantity demandQty)
	{
		final Warehouse sourceWarehouse = warehouseRepository.getById(sourceWarehouseId);
		final List<LocatorId> sourceLocatorIds = sourceWarehouse.getGroundFloorLocatorIdsOrderedByPriority();
		if (sourceLocatorIds.isEmpty())
		{
			Loggables.addLog("No ground floor locators found for {}", sourceWarehouse.getName());
			return new AllocationResult(ImmutableMap.of(), demandQty);
		}

		// Sorted BEFORE the on-hand stream is opened, so the greedy loop can stop pulling chunks once the demand is covered.
		final List<LocatorId> sourceLocatorIdsInPickOrder = sourceLocatorIds.stream()
				.sorted(Comparator.comparing(this::getLocatorSortKey))
				.collect(ImmutableList.toImmutableList());

		final UOMConversionContext conversionCtx = UOMConversionContext.of(productId);

		final Stream<LocatorIdAndQty> orderedNonEmpty = ProductAvailableStockPerLocator.newInstance(handlingUnitsBL)
				.streamLocatorQtyOnHandOrdered(productId, 50, sourceLocatorIdsInPickOrder);

		return greedyAllocateOrdered(
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
	}

	/**
	 * Reported net of what the disconnected duplicates already serve, else it overstates a shortfall the final line quantities do not have.
	 */
	private static void logUncoveredRemainder(
			@NonNull final DDOrderReplenishmentGroupKey groupKey,
			@NonNull final WarehouseId sourceWarehouseId,
			@NonNull final Quantity uncoveredQty,
			@NonNull final Map<PickingJobScheduleId, Quantity> alreadyServedByContributor)
	{
		final Quantity uncoveredNetQty = alreadyServedByContributor.values().stream()
				.reduce(uncoveredQty, Quantity::subtract)
				.toZeroIfNegative();
		if (uncoveredNetQty.signum() <= 0)
		{
			return;
		}

		Loggables.addLog(
				"DD_Order picking replenishment: on-hand stock for M_Product_ID={0} in source M_Warehouse_ID={1}"
						+ " covers only part of the summed demand of the product group targeting M_Locator_ID={2};"
						+ " uncovered remainder={3} left unfulfilled (watchdog will retry)",
				groupKey.getProductId().getRepoId(),
				sourceWarehouseId.getRepoId(),
				groupKey.getLocatorToId().getRepoId(),
				uncoveredNetQty.toBigDecimal());
	}

	/**
	 * The pre-materialised-map variant of {@link #greedyAllocateOrdered}; only the UOM conversion is injected, so a cross-UOM case can be unit-tested.
	 */
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

	/**
	 * Consumed through an iterator so the stream's lazy chunked stock fetch is short-circuited once the demand is covered.
	 */
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

	/**
	 * Converts a locator's on-hand qty (product stocking UOM) into the demand UOM; may throw {@link NoUOMConversionException}.
	 */
	@FunctionalInterface
	interface ConvertToDemandUom
	{
		Quantity convert(@NonNull Quantity availableStockingUom);
	}

	/**
	 * The per-locator allocation (insertion-ordered) and the uncovered demand remainder.
	 */
	@Value
	@VisibleForTesting
	static class AllocationResult
	{
		@NonNull Map<LocatorId, Quantity> allocation;
		@NonNull Quantity uncovered;
	}

	/**
	 * Splits the allocation chunks back across the contributors, sequentially rather than proportionally: fractional shares are not wanted on piece goods.
	 */
	@VisibleForTesting
	ImmutableMap<LocatorId, ImmutableList<DDOrderLineContributor>> attribute(
			@NonNull final List<PickingJobSchedule> contributorsInOrder,
			@NonNull final Map<LocatorId, Quantity> allocation)
	{
		return attribute(contributorsInOrder, allocation, ImmutableMap.of());
	}

	/**
	 * {@code alreadyServedByContributor} is subtracted first, so a frozen line's shares are not attributed a second time on the next locator.
	 */
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

	/**
	 * Floored at zero: a frozen line can carry MORE than the contributor still demands.
	 */
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

	/**
	 * Yields PriorityNo ASC then Value ASC under plain lexicographic order; the pad width 10 matches {@code M_Locator.PriorityNo numeric(10,0)}, and negative values are not supported.
	 */
	@VisibleForTesting
	static String buildLocatorSortKey(final int priorityNo, @NonNull final String value)
	{
		return Strings.padStart(Integer.toString(priorityNo), 10, '0') + "|" + value;
	}

	@Value
	private static class ExistingLineIndex
	{
		@NonNull Map<LocatorId, I_DD_OrderLine> byLocator;

		/**
		 * Orders no source locator could be resolved for; the caller must still dispose of them, or they outlive their alloc rows unreachably.
		 */
		@NonNull List<I_DD_Order> unkeyable;
	}

	/**
	 * On a locator collision the OLDER order keeps the locator — the one a mover may already be working.
	 */
	private ExistingLineIndex indexExistingBySourceLocator(
			@NonNull final List<I_DD_Order> existingDDOrders,
			@NonNull final ImmutableListMultimap<Integer, I_DD_OrderLine> linesByOrderId)
	{
		final LinkedHashMap<LocatorId, I_DD_OrderLine> byLocator = new LinkedHashMap<>();
		final ImmutableList.Builder<I_DD_Order> unkeyable = ImmutableList.builder();

		for (final I_DD_Order ddOrder : existingDDOrders)
		{
			final ImmutableList<I_DD_OrderLine> lines = linesByOrderId.get(ddOrder.getDD_Order_ID());
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

	/**
	 * Shared with {@link #computeFrozenSplit}, so the attribution and the write cannot disagree about which lines are frozen.
	 * <p>
	 * SUPERSEDED: keys on {@code QtyDelivered}, which no production flow writes (only MDDOrderLine's zero-initialiser), so it
	 * never freezes anything on a live instance. The freeze is being re-keyed onto {@link #ordersHoldingMovedGoods} — the same live
	 * movement signal the disposal sites already use — in a follow-up change; left as-is here to keep that swap in one place.
	 */
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
		// Header
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
		ddOrderLine.setIsInvoiced(false);
		ddOrderLowLevelDAO.save(ddOrderLine);

		return ddOrderLine;
	}

	/**
	 * Unguarded: every caller takes the picker-busy verdict for its whole batch of orders beforehand, since a delete→void caller has to take it before the delete.
	 */
	private void voidDDOrder(@NonNull final DDOrderId existingDDOrderId)
	{
		ddOrderService.voidIt(existingDDOrderId);
		Loggables.addLog("DD_Order picking replenishment: voided DD_Order_ID={0}", existingDDOrderId.getRepoId());
	}

	/**
	 * Runs in the delete transaction: the alloc rows' {@code M_Picking_Job_Schedule_ID} is DEFERRABLE INITIALLY DEFERRED, so anything left pointing at the assignment fails at commit.
	 */
	public void voidDDOrdersForDeletedAssignment(@NonNull final PickingJobSchedule deletedAssignment)
	{
		final ImmutableSet<PickingJobScheduleId> jobScheduleIds = ImmutableSet.of(deletedAssignment.getId());
		final ImmutableSet<DDOrderLineId> servedLineIds = contributorRepository.getLineIdsByPickingJobScheduleIds(jobScheduleIds);

		// Captured while the departing assignment is still a contributor: after the delete its own delivery — very
		// often the one being picked — is no longer resolvable, and the set would report nobody busy.
		final ImmutableMap<DDOrderLineId, BlockingWork> blockingBeforeDeparture = findBlockingPickingWorkByLineId(servedLineIds, deletedAssignment);

		final boolean sharedOrderSurvived = servesContributorsOtherThan(servedLineIds, deletedAssignment.getId());
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
	 * Asked while the departing assignment's own alloc rows are still there, which is why it has to be excluded explicitly.
	 *
	 * @return whether at least one served line keeps a contributor, i.e. its order outlives the departure and now carries more than the group still demands.
	 */
	private boolean servesContributorsOtherThan(
			@NonNull final Set<DDOrderLineId> servedLineIds,
			@NonNull final PickingJobScheduleId departingAssignmentId)
	{
		return contributorRepository.getPickingJobScheduleIds(servedLineIds)
				.stream()
				.anyMatch(contributorId -> !contributorId.equals(departingAssignmentId));
	}

	/**
	 * {@code blockingBeforeDeparture} cannot be recomputed here: reaching a line at all means its contributor set is empty, and an empty set has nobody busy in it.
	 */
	private void voidDDOrdersLeftWithoutContributor(
			@NonNull final Set<DDOrderLineId> lineIds,
			@NonNull final ImmutableMap<DDOrderLineId, BlockingWork> blockingBeforeDeparture)
	{
		if (lineIds.isEmpty())
		{
			return;
		}

		final ImmutableSet<DDOrderLineId> stillServedLineIds = contributorRepository.getPickingJobScheduleIdsByLineId(lineIds).keySet();
		final ImmutableMap<DDOrderLineId, I_DD_OrderLine> linesById = Maps.uniqueIndex(
				ddOrderLowLevelDAO.getLinesByIds(ImmutableSet.copyOf(lineIds)),
				line -> DDOrderLineId.ofRepoId(line.getDD_OrderLine_ID()));
		final ImmutableMap<DDOrderId, I_DD_Order> ddOrdersById = Maps.uniqueIndex(
				ddOrderLowLevelDAO.getByIds(ddOrderIdsOfLines(linesById.values())),
				ddOrder -> DDOrderId.ofRepoId(ddOrder.getDD_Order_ID()));
		final ImmutableSet<DDOrderId> ordersHoldingMovedGoods = ordersHoldingMovedGoods(ddOrdersById.keySet());

		for (final DDOrderLineId lineId : lineIds)
		{
			if (stillServedLineIds.contains(lineId))
			{
				continue;
			}

			final DDOrderId ddOrderId = DDOrderId.ofRepoId(linesById.get(lineId).getDD_Order_ID());
			final I_DD_Order ddOrder = ddOrdersById.get(ddOrderId);
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

			if (ordersHoldingMovedGoods.contains(ddOrderId))
			{
				// Nothing to file either way: reaching this line means its alloc rows are already gone — that is the
				// very condition that got us here.
				disconnectDDOrderFor(ddOrderId);
			}
			else
			{
				voidDDOrder(ddOrderId);
			}
		}
	}

	/**
	 * The group lost every contributor and is voided out — except for an order that already holds moved goods, which is
	 * DISCONNECTED instead.
	 *
	 * @return the lines of the DISCONNECTED orders, whose alloc rows the caller must keep.
	 */
	private ImmutableSet<DDOrderLineId> disposeVoidOut(@NonNull final List<I_DD_Order> ddOrders)
	{
		final ImmutableMap<DDOrderId, BlockingWork> blockingByDDOrderId = findBlockingPickingWork(ddOrders);
		final ImmutableListMultimap<Integer, I_DD_OrderLine> linesByOrderId = Multimaps.index(
				ddOrderLowLevelDAO.retrieveLines(ImmutableSet.copyOf(ddOrders)),
				I_DD_OrderLine::getDD_Order_ID);

		final ImmutableSet<DDOrderId> ordersHoldingMovedGoods = ordersHoldingMovedGoods(ddOrderIdsOf(ddOrders));

		final HashSet<DDOrderLineId> obsoleteLineIds = new HashSet<>();
		final HashSet<DDOrderLineId> disconnectedLineIds = new HashSet<>();
		for (final I_DD_Order ddOrder : ddOrders)
		{
			final DDOrderId ddOrderId = DDOrderId.ofRepoId(ddOrder.getDD_Order_ID());
			final BlockingWork busyPicker = blockingByDDOrderId.get(ddOrderId);
			if (busyPicker != null)
			{
				throw newPickerBusyException(ddOrderId, busyPicker);
			}

			disposeObsoleteDDOrder(ddOrderId, linesByOrderId.get(ddOrder.getDD_Order_ID()), ordersHoldingMovedGoods, obsoleteLineIds, disconnectedLineIds);
		}

		return ImmutableSet.copyOf(disconnectedLineIds);
	}

	/**
	 * An in-progress move is DISCONNECTed rather than closed: closing would hit the {@code BEFORE_CLOSE clearSchedules} guard and corrupt the half-done move.
	 */
	private void disposeCloseOut(@NonNull final List<I_DD_Order> ddOrders)
	{
		final ImmutableSet<DDOrderId> ordersHoldingMovedGoods = ordersHoldingMovedGoods(ddOrderIdsOf(ddOrders));

		for (final I_DD_Order ddOrder : ddOrders)
		{
			final DDOrderId ddOrderId = DDOrderId.ofRepoId(ddOrder.getDD_Order_ID());
			if (ordersHoldingMovedGoods.contains(ddOrderId))
			{
				disconnectDDOrderFor(ddOrderId);
			}
			else
			{
				closeDDOrderFor(ddOrderId);
			}
		}
	}

	/**
	 * The responsible user is released too, so the DD_Order-backed mobile DistributionJob retires from the launcher.
	 */
	private void closeDDOrderFor(@NonNull final DDOrderId ddOrderId)
	{
		ddOrderService.close(ddOrderId);
		ddOrderService.unassignFromResponsible(ddOrderId);

		Loggables.addLog(
				"DD_Order picking replenishment: closed obsolete replenishment DD_Order_ID={0} on shipment close-out"
						+ " and released AD_User_Responsible_ID",
				ddOrderId.getRepoId());
	}

	/**
	 * The guard/reconcile lookups stop seeing it, while its contributor rows and DistributionJob assignment are retained for the worker to finish.
	 */
	private void disconnectDDOrderFor(@NonNull final DDOrderId ddOrderId)
	{
		ddOrderService.markAsPickingDisconnected(ddOrderId);

		Loggables.addLog(
				"DD_Order picking replenishment: disconnected (IsPickingDisconnected=Y) in-progress replenishment"
						+ " DD_Order_ID={0}; contributor rows retained, DistributionJob stays live for the worker",
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

	/**
	 * Which of {@code assignmentIds} the watchdog still considers unserved. Restricted to the caller's ids so the result stays bounded.
	 */
	public ImmutableSet<PickingJobScheduleId> retainAssignmentsNeedingDDOrder(@NonNull final Set<PickingJobScheduleId> assignmentIds)
	{
		if (assignmentIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		try (final Stream<PickingJobSchedule> assignments = pickingJobScheduleService.streamAssignmentsNeedingDDOrder(
				queryServedAssignments(),
				assignmentIds))
		{
			return assignments.map(PickingJobSchedule::getId)
					.collect(ImmutableSet.toImmutableSet());
		}
	}

	/**
	 * Served-ness is resolved through the contributor association; a back-reference column names only one contributor of a consolidated order.
	 */
	private Stream<PickingJobSchedule> streamAssignmentsNeedingDDOrder()
	{
		return pickingJobScheduleService.streamAssignmentsNeedingDDOrder(queryServedAssignments());
	}

	private IQuery<I_DD_OrderLine_PickingJobSchedule> queryServedAssignments()
	{
		return contributorRepository.queryByLines(ddOrderLowLevelDAO.queryCompletedDDOrderLines());
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

	/**
	 * Evaluated over each order's COMPLETE contributor set; an order carrying no contributor row at all is absent from the result.
	 */
	private ImmutableMap<DDOrderId, BlockingWork> findBlockingPickingWork(@NonNull final Collection<I_DD_Order> ddOrders)
	{
		if (ddOrders.isEmpty())
		{
			return ImmutableMap.of();
		}

		final ImmutableListMultimap<Integer, I_DD_OrderLine> linesByOrderId = Multimaps.index(
				ddOrderLowLevelDAO.retrieveLines(ImmutableSet.copyOf(ddOrders)),
				I_DD_OrderLine::getDD_Order_ID);
		final ImmutableListMultimap<DDOrderLineId, PickingJobSchedule> contributorsByLineId = contributorsByLineId(
				linesByOrderId.values().stream()
						.map(line -> DDOrderLineId.ofRepoId(line.getDD_OrderLine_ID()))
						.collect(ImmutableSet.toImmutableSet()));
		if (contributorsByLineId.isEmpty())
		{
			return ImmutableMap.of();
		}

		final ImmutableSet<ShipmentScheduleId> busyScheduleIds = pickingJobRepository.retrieveScheduleIdsWithActivePickingJobLine(
				contributorsByLineId.values().stream()
						.map(PickingJobSchedule::getShipmentScheduleId)
						.collect(ImmutableSet.toImmutableSet()));
		if (busyScheduleIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		final LinkedHashMap<DDOrderId, BlockingWork> result = new LinkedHashMap<>();
		for (final I_DD_Order ddOrder : ddOrders)
		{
			final DDOrderId ddOrderId = DDOrderId.ofRepoId(ddOrder.getDD_Order_ID());
			firstBlocking(
					linesByOrderId.get(ddOrder.getDD_Order_ID()).stream()
							.flatMap(line -> contributorsByLineId.get(DDOrderLineId.ofRepoId(line.getDD_OrderLine_ID())).stream()),
					busyScheduleIds)
					.ifPresent(blocking -> result.putIfAbsent(ddOrderId, blocking));
		}
		return ImmutableMap.copyOf(result);
	}

	/**
	 * The per-line form of {@link #findBlockingPickingWork(Collection)}; {@code departingAssignment} is folded in because its own row is already deleted and no longer resolvable.
	 */
	private ImmutableMap<DDOrderLineId, BlockingWork> findBlockingPickingWorkByLineId(
			@NonNull final Set<DDOrderLineId> lineIds,
			@NonNull final PickingJobSchedule departingAssignment)
	{
		if (lineIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		final ImmutableListMultimap<DDOrderLineId, PickingJobSchedule> contributorsByLineId = contributorsByLineId(lineIds);

		final ImmutableSet<ShipmentScheduleId> busyScheduleIds = pickingJobRepository.retrieveScheduleIdsWithActivePickingJobLine(
				Stream.concat(contributorsByLineId.values().stream(), Stream.of(departingAssignment))
						.map(PickingJobSchedule::getShipmentScheduleId)
						.collect(ImmutableSet.toImmutableSet()));
		if (busyScheduleIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		final ImmutableMap.Builder<DDOrderLineId, BlockingWork> result = ImmutableMap.builder();
		for (final DDOrderLineId lineId : lineIds)
		{
			firstBlocking(
					Stream.concat(contributorsByLineId.get(lineId).stream(), Stream.of(departingAssignment)),
					busyScheduleIds)
					.ifPresent(blocking -> result.put(lineId, blocking));
		}
		return result.build();
	}

	/**
	 * Ordered by id so that the contributor a refusal names is the same on every run — an operator cannot report a message that changes between two identical situations.
	 */
	private static Optional<BlockingWork> firstBlocking(
			@NonNull final Stream<PickingJobSchedule> contributors,
			@NonNull final Set<ShipmentScheduleId> busyScheduleIds)
	{
		return contributors
				.sorted(Comparator.comparing(PickingJobSchedule::getId))
				.filter(contributor -> busyScheduleIds.contains(contributor.getShipmentScheduleId()))
				.findFirst()
				.map(contributor -> BlockingWork.of(contributor.getId(), contributor.getShipmentScheduleId()));
	}

	/**
	 * A contributor whose assignment no longer loads was deleted and is left out.
	 */
	private ImmutableListMultimap<DDOrderLineId, PickingJobSchedule> contributorsByLineId(@NonNull final Set<DDOrderLineId> lineIds)
	{
		final ImmutableSetMultimap<DDOrderLineId, PickingJobScheduleId> contributorIdsByLineId =
				contributorRepository.getPickingJobScheduleIdsByLineId(lineIds);
		if (contributorIdsByLineId.isEmpty())
		{
			return ImmutableListMultimap.of();
		}

		final ImmutableMap<PickingJobScheduleId, PickingJobSchedule> contributorsById = Maps.uniqueIndex(
				pickingJobScheduleService.getByIds(ImmutableSet.copyOf(contributorIdsByLineId.values())),
				PickingJobSchedule::getId);

		final ImmutableListMultimap.Builder<DDOrderLineId, PickingJobSchedule> result = ImmutableListMultimap.builder();
		contributorIdsByLineId.forEach((lineId, contributorId) -> {
			final PickingJobSchedule contributor = contributorsById.get(contributorId);
			if (contributor != null)
			{
				result.put(lineId, contributor);
			}
		});
		return result.build();
	}

	/**
	 * A contributor that is NOT the assignment being edited is preferred, because telling the editor about their own assignment says nothing about who else is on the document.
	 */
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

	/**
	 * Ordered so that the contributor a refusal names is the same on every run — an operator cannot report a message that changes between two identical situations.
	 */
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

	/**
	 * A record id goes into an AD_Message as text: {@code MessageFormat} would push a number through the reader's locale format and render it as "1.234.567".
	 */
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
