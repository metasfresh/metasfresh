package de.metas.distribution.ddorder.replenishment;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
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
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UOMConversionContext;
import de.metas.util.Loggables;
import de.metas.util.ProgressLogger;
import de.metas.util.Services;
import de.metas.workplace.Workplace;
import de.metas.workplace.WorkplaceId;
import de.metas.workplace.WorkplaceService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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

	// FQN trx-property key: avoids collisions with any other service that might register an
	// after-commit accumulator under a shorter, easier-to-clash name.
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
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

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

		final PickingJobScheduleId jobScheduleId = PickingJobScheduleId.ofRepoId(jobSchedule.getM_Picking_Job_Schedule_ID());
		for (final I_DD_Order ddOrder : ddOrderLowLevelDAO.findActiveDDOrdersForPickingJobSchedule(jobScheduleId))
		{
			final DDOrderId ddOrderId = DDOrderId.ofRepoId(ddOrder.getDD_Order_ID());
			if (isPickerBusy(ddOrderId))
			{
				throw new AdempiereException(MSG_DDOrderPickingReplenishment_PickerBusy, ddOrderId);
			}
			for (final I_DD_OrderLine line : ddOrderLowLevelDAO.retrieveLines(ddOrder))
			{
				if (line.getQtyInTransit().signum() > 0 || line.getQtyDelivered().signum() > 0)
				{
					throw new AdempiereException(MSG_DDOrderPickingReplenishment_MovementStarted, ddOrderId);
				}
			}
		}
	}

	public void scheduleReconcileAfterCommit(@NonNull final PickingJobSchedule jobSchedule)
	{
		trxManager.accumulateAndProcessAfterCommit(
				TRX_PROPERTY_ScheduleReconcile,
				ImmutableSet.of(toReplenishmentRequest(jobSchedule)),
				reconciliationEventPublisher::publishAll);
	}

	/**
	 * Builds the group-keyed reconcile request of the assignment's product group. Only this service can do it:
	 * the group key needs the assignment's shipment schedule (product) and its workplace (target locator), both
	 * of which the assignment merely points at.
	 */
	private DDOrderReplenishmentRequest toReplenishmentRequest(@NonNull final PickingJobSchedule jobSchedule)
	{
		final I_M_ShipmentSchedule schedule = shipmentScheduleBL.getById(jobSchedule.getShipmentScheduleId());
		final Workplace workplace = workplaceService.getById(jobSchedule.getWorkplaceId());

		// Target locator = the workstation's configured pick-from locator; if unset, fall back to the workplace
		// warehouse's default locator (getOrCreateDefaultLocatorId always yields one). There is therefore no
		// "no pick-from locator" skip — the goods always have a delivery target. The fallback is logged so an
		// operator can see the goods were routed to the warehouse default rather than a configured pick shelf.
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
	 * Reconciles ONE picking-replenishment product group: the demand that shares
	 * {@code (product, target locator, UOM)} is planned as a single {@code DD_Order} per contributing source locator,
	 * carrying the <b>summed</b> quantity of the group's contributing assignments (AC1).
	 *
	 * <p><b>Every contributor of the group is processed, not just the assignment that triggered the event.</b> The
	 * reconcile request identifies the group, so N assignments of one group collapse into ONE request; the contributor
	 * set is therefore re-read here, from the database. A reconcile keyed on a single assignment would leave every
	 * other member of a dedup'd group with no DD_Order, no exception and no log line.</p>
	 *
	 * <p><b>Aggregate first, then split</b> (DESIGN.md §4): the group's demand is summed BEFORE the unchanged
	 * stock-aware greedy runs over it, so one pass cannot claim the same on-hand units twice — that is what makes
	 * over-allocation impossible by construction rather than by a new guard (AC6 / D1).</p>
	 *
	 * <p>The action follows today's four-outcome truth table, now evaluated over the group's SURVIVING contributor
	 * set instead of one assignment's state (DESIGN.md §6):</p>
	 * <pre>
	 * live contributors | group has a live DD_Order | action
	 * some              | no                        | CREATE
	 * some              | yes                       | RECREATE (recompute the group)
	 * none              | no                        | NONE
	 * none              | yes                       | CLOSE when every FORMER contributor is Processed=Y
	 *                   |                           | (shipment close-out), else VOID
	 * </pre>
	 *
	 * <p>A contributor drops out of the live set exactly where today's per-assignment classification returned
	 * something other than CREATE/RECREATE: deleted, {@code IsActive=N}, {@code Processed=Y}, its warehouse is not
	 * {@code IsAutoDistributionOrder}, or {@code QtyToPick <= 0}. So for the single-contributor case — the only case
	 * that exists before this change — the outcome is identical to today's.</p>
	 *
	 * <p><b>No transaction boundary here.</b> The VOID-then-CREATE of the RECREATE branch, and the line quantity
	 * together with its contributor rows, are only atomic if the caller wraps this call in a transaction. The caller
	 * ({@code DDOrderReplenishmentEventHandler}) wraps it in {@code trxManager.runInThreadInheritedTrx(...)}.</p>
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
				// Nothing demands the replenishment and nothing exists to dispose of.
				return;
			case CREATE:
			case RECREATE:
				// Both are the same per-locator reconcile: compute the required (source locator -> qty) split from
				// current stock, then update / void / create per locator. With no existing DD_Order this degenerates
				// to pure creation.
				reconcileRequiredVsExisting(groupKey, clientAndOrgId, contributors, existingDDOrders);
				return;
			case CLOSE:
				disposeCloseOut(existingDDOrders);
				// Alloc rows are deliberately KEPT on this path, mirroring the FK retention of closeDDOrderFor /
				// disconnectDDOrderFor: the close-out stays traceable and an in-progress move stays pickable.
				return;
			case VOID:
			{
				final ImmutableSet<DDOrderLineId> lineIds = lineIdsOf(existingDDOrders);
				voidAllDDOrders(existingDDOrders);
				// The orders are dead, so their contributor rows are too — a voided order must not still answer
				// "which DD_Order serves this delivery?" (AC4).
				contributorRepository.deleteByLineIds(lineIds);
				return;
			}
			default:
				throw new AdempiereException("Unexpected action: " + action);
		}
	}

	/**
	 * Decides what the group reconcile does, per the truth table in {@link #reconcile}.
	 *
	 * <p>Unlike the per-assignment {@code classifyAction} it replaces, this is NOT a pure decision method: telling a
	 * shipment close-out (CLOSE) apart from a genuine un-assignment (VOID) requires the group's <b>former</b>
	 * contributors — the assignments that still hold alloc rows on the group's live orders but are no longer
	 * contributors — because a group reconcile does not know which single event fired (DESIGN.md §6). That question
	 * only the database can answer, and it is asked only on the branch that needs it.</p>
	 */
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

		return isEveryFormerContributorProcessed(contributorRepository.getContributorIds(lineIdsOf(existingDDOrders)))
				? DDOrderReplenishmentAction.CLOSE
				: DDOrderReplenishmentAction.VOID;
	}

	/**
	 * Reconciles the product group the given assignment belongs to.
	 *
	 * <p>The deterministic entry point for tests and operations; production always goes through the event topic, whose
	 * payload already carries the group key. Since the reconcile is group-keyed, this serves EVERY contributor of that
	 * assignment's group, not only the assignment named here.</p>
	 */
	public void reconcileGroupOf(@NonNull final PickingJobScheduleId jobScheduleId)
	{
		final DDOrderReplenishmentRequest request = toReplenishmentRequest(pickingJobScheduleService.getById(jobScheduleId));
		reconcile(request.getGroupKey(), request.getClientAndOrgId());
	}

	/**
	 * The group's live contributors, in the attribution order of {@link #attributionOrder(Map)}.
	 *
	 * <p>The lookup itself ({@code PickingJobScheduleService.listContributorsOfGroup}) is deliberately unordered and
	 * already excludes deleted, {@code IsActive=N} and {@code Processed=Y} assignments. Two further drop-out reasons
	 * are applied here because they need the shipment schedule: a warehouse that is no longer
	 * {@code IsAutoDistributionOrder}, and a non-positive {@code QtyToPick}.</p>
	 */
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

	/**
	 * Zero-qty soft no-op: an assignment whose {@code QtyToPick} is {@code <= 0} demands nothing, so it drops out of
	 * the group entirely and its alloc row is DELETED rather than kept at {@code Qty=0} (DESIGN.md §6). An
	 * informational entry goes to the Event Log so operators can see why that delivery got no share.
	 */
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

	/**
	 * The order in which the group's contributors are served (AC7 / DESIGN.md §4.2): effective {@code PriorityRule},
	 * then effective {@code PreparationDate}, then the older assignment. Contributors ahead in it are covered in full,
	 * so when stock is short the shortfall falls on those last.
	 *
	 * <p>Both date and priority MUST be resolved through {@link IShipmentScheduleEffectiveBL} — it is what applies
	 * {@code COALESCE(<x>_Override, <x>)} plus the {@code PriorityRule.Medium} default. Reading
	 * {@code M_ShipmentSchedule.PriorityRule} directly would silently ignore an override and order the contributors
	 * wrongly, with no error and no failing test. The codes are {@code Urgent=1 … Minor=9}, so ascending code IS
	 * ascending urgency — the same direction {@code PackagingDAO} orders the picking queue in.</p>
	 *
	 * <p>The {@code M_Picking_Job_Schedule_ID} tiebreak is load-bearing, not insurance: 1799 of 2100 measured open
	 * assignments tie on the first two keys, so without it the hourly rebuild would rewrite the alloc rows of 86% of
	 * all contributors on every pass while the line quantities stayed correct.</p>
	 */
	private Comparator<PickingJobSchedule> attributionOrder(@NonNull final Map<ShipmentScheduleId, I_M_ShipmentSchedule> schedules)
	{
		return Comparator
				.comparing((final PickingJobSchedule contributor) -> shipmentScheduleEffectiveBL.getPriorityRule(schedules.get(contributor.getShipmentScheduleId())).getCode())
				.thenComparing(contributor -> shipmentScheduleEffectiveBL.getPreparationDate(schedules.get(contributor.getShipmentScheduleId())))
				.thenComparing(contributor -> contributor.getId().getRepoId());
	}

	/**
	 * Whether every former contributor of the group left because its shipment was closed out ({@code Processed = Y}) —
	 * the group's replenishment is then obsolete and its orders are closed rather than voided. An empty set means the
	 * group's orders carry no alloc row at all, which is not a close-out.
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

	private boolean isOnAutoDistributionOrder(final I_M_ShipmentSchedule schedule)
	{
		final WarehouseId warehouseId = shipmentScheduleEffectiveBL.getWarehouseId(schedule);
		final Warehouse warehouse = warehouseRepository.getById(warehouseId);
		return warehouse.isAutoDistributionOrder();
	}

	/**
	 * Reconciles the required per-locator stock-aware split against the product group's existing live DD_Orders.
	 *
	 * <p>Sums the group's demand, computes the required {@code (source locator -> qty)} allocation from current on-hand
	 * stock over that <b>sum</b> (greedy, in the locator pick order defined by {@link #buildLocatorSortKey}), then for
	 * each locator:</p>
	 * <ul>
	 *   <li>in BOTH required and existing → <b>update the existing DD_Order line qty in place</b> (no void/recreate);</li>
	 *   <li>in EXISTING only (no longer contributes) → <b>void</b> that DD_Order (+ unlink back-ref);</li>
	 *   <li>in REQUIRED only (newly contributes) → <b>create</b> a new Completed DD_Order + line.</li>
	 * </ul>
	 * and rewrites that line's complete contributor set — the per-delivery shares of {@link #attribute} — in the SAME
	 * transaction as the line quantity.
	 *
	 * <p>The picker-busy guard is checked once up front: if any existing DD_Order's picker is busy, the whole
	 * reconcile is refused (nothing is mutated).</p>
	 */
	private void reconcileRequiredVsExisting(
			@NonNull final DDOrderReplenishmentGroupKey groupKey,
			@NonNull final ClientAndOrgId clientAndOrgId,
			@NonNull final List<PickingJobSchedule> contributorsInOrder,
			@NonNull final List<I_DD_Order> existingDDOrders)
	{
		final ProductId productId = groupKey.getProductId();
		final LocatorId locatorToId = groupKey.getLocatorToId();
		final OrgId orgId = clientAndOrgId.getOrgId();

		// Header attributes that belong to a single DELIVERY rather than to the group are taken from the first
		// contributor in the attribution order: a consolidated order has no single owning delivery, so the choice is
		// arbitrary but deterministic. The four FK columns are dropped by Task 15, and C_BPartner_ID / M_Warehouse_To_ID
		// are decoration on an internal stock move.
		// TODO(Task 15): stop writing M_Picking_Job_Schedule_ID / M_ShipmentSchedule_ID here — the complete contributor
		// set now lives in DD_OrderLine_PickingJobSchedule, which is what the navigation and the guards read.
		final PickingJobSchedule firstContributor = contributorsInOrder.get(0);
		final I_M_ShipmentSchedule firstSchedule = shipmentScheduleBL.getById(firstContributor.getShipmentScheduleId());

		final WarehouseId targetWarehouseId = shipmentScheduleEffectiveBL.getWarehouseId(firstSchedule);
		final Warehouse targetWarehouse = warehouseRepository.getById(targetWarehouseId);
		final WarehouseId sourceWarehouseId = getFirstSourceWarehouseIdOrThrow(targetWarehouse, productId);

		// AGGREGATE FIRST: the group's summed demand. Every contributor is in groupKey's UOM by construction of the
		// group key (the contributor lookup filters C_UOM_ID), so the addition can never hit a UOM mismatch.
		final Quantity groupDemand = contributorsInOrder.stream()
				.map(PickingJobSchedule::getQtyToPick)
				.reduce(Quantity::add)
				.orElseThrow(() -> new AdempiereException("Caller guarantees at least one contributor"));

		// Stock-aware split of the SUM: required (source locator -> qty), greedy in the locator pick order, partial
		// coverage allowed. Running once over the sum is what makes over-allocation impossible (AC6 / D1) — two
		// deliveries can no longer each claim the same on-hand units.
		final Map<LocatorId, Quantity> requiredByLocator = computeRequiredAllocation(groupKey, sourceWarehouseId, productId, groupDemand);

		// Picker-busy guard: refuse the whole reconcile before mutating anything if any existing DD_Order is busy.
		for (final I_DD_Order existingDDOrder : existingDDOrders)
		{
			final DDOrderId existingDDOrderId = DDOrderId.ofRepoId(existingDDOrder.getDD_Order_ID());
			if (isPickerBusy(existingDDOrderId))
			{
				throw new AdempiereException(MSG_DDOrderPickingReplenishment_PickerBusy, existingDDOrderId);
			}
		}

		// Index the existing live DD_Orders' (single) lines by their source locator. The line is kept (not just the
		// header) so the update-in-place path does not re-fetch it.
		final Map<LocatorId, I_DD_OrderLine> existingLineByLocator = indexExistingBySourceLocator(existingDDOrders);

		// THEN SPLIT: attribute each source-locator chunk back across the contributors, in the attribution order.
		final ImmutableMap<LocatorId, ImmutableList<DDOrderLineContributor>> attribution = attribute(contributorsInOrder, requiredByLocator);

		final DocTypeId docTypeId = docTypeDAO.getDocTypeId(
				DocTypeQuery.builder()
						.docBaseType(X_C_DocType.DOCBASETYPE_DistributionOrder)
						.adClientId(clientAndOrgId.getClientId().getRepoId())
						.adOrgId(orgId.getRepoId())
						.build());
		final WarehouseId inTransitWarehouseId = warehouseRepository.getInTransitWarehouseId(orgId);

		final HashSet<DDOrderLineId> obsoleteLineIds = new HashSet<>();

		// EXISTING-only locators (no longer contribute) → void.
		for (final Map.Entry<LocatorId, I_DD_OrderLine> entry : existingLineByLocator.entrySet())
		{
			if (!requiredByLocator.containsKey(entry.getKey()))
			{
				voidDDOrderFor(DDOrderId.ofRepoId(entry.getValue().getDD_Order_ID()));
				obsoleteLineIds.add(DDOrderLineId.ofRepoId(entry.getValue().getDD_OrderLine_ID()));
			}
		}

		final HashSet<DDOrderLineId> survivingLineIds = new HashSet<>();

		// REQUIRED locators → update-in-place (if still contributing) or create (if newly contributing).
		for (final Map.Entry<LocatorId, Quantity> entry : requiredByLocator.entrySet())
		{
			final LocatorId sourceLocatorId = entry.getKey();
			final Quantity locatorQty = entry.getValue();
			final I_DD_OrderLine existingLine = existingLineByLocator.get(sourceLocatorId);
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
						.bpartnerId(BPartnerId.ofRepoIdOrNull(firstSchedule.getC_BPartner_ID()))
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

			// The line quantity and its contributor shares are one fact written in one transaction: a line whose
			// contributors say something else is a settlement that resolves nobody (AC2 / AC3). When the
			// already-delivered guard left the line at its old quantity, its old shares stay too — see
			// updateDDOrderLineQtyInPlace.
			if (lineCarriesTheRequiredQty)
			{
				contributorRepository.replaceContributors(lineId, attribution.getOrDefault(sourceLocatorId, ImmutableList.of()));
			}
			survivingLineIds.add(lineId);
		}

		// Finally drop every alloc row of a live contributor that does NOT sit on one of the group's surviving lines.
		// Concrete scenario this exists for: the drift the watchdog exists for — a DD_Order voided OUTSIDE the reconcile
		// (event lost between commit and publish, node restart, an operator voiding by hand) leaves its alloc rows
		// behind, so without this the contributor would keep resolving to that dead order alongside the fresh one and
		// the shipment-schedule -> DD_Order navigation (AC4) would answer with two documents.
		for (final PickingJobSchedule contributor : contributorsInOrder)
		{
			obsoleteLineIds.addAll(contributorRepository.getLineIdsByPickingJobScheduleId(contributor.getId()));
		}
		obsoleteLineIds.removeAll(survivingLineIds);
		contributorRepository.deleteByLineIds(obsoleteLineIds);
	}

	/**
	 * Computes the stock-aware per-locator allocation of {@code demandQty}.
	 *
	 * <p>Gets the source warehouse's locators, queries Active on-hand per locator via the shared
	 * {@link ProductAvailableStockPerLocator} helper, keeps the contributing locators (qty &gt; 0) in the locator
	 * pick order (see {@link #buildLocatorSortKey}), and greedily allocates the demand across them. Returns an
	 * insertion-ordered map {@code (source locator -> allocated qty)}. Partial coverage is allowed: if total on-hand
	 * &lt; demand, the uncovered remainder is logged and left unfulfilled (no fallback default-locator line). Empty
	 * map if no locator has stock.</p>
	 *
	 * <p>The on-hand quantities returned by {@link ProductAvailableStockPerLocator} are in the product's stocking UOM,
	 * whereas {@code demandQty} is in the assignment's UOM. Each locator's available qty is therefore converted into
	 * the demand UOM (via the product UOM conversion) before it is compared/subtracted, so the allocation and the
	 * created lines stay in the demand UOM. When the two UOMs already match the conversion is a no-op. A locator whose
	 * available qty cannot be converted is skipped (logged, treated as non-contributing) rather than aborting the whole
	 * reconcile.</p>
	 */
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

		// Sort the source locators in the locator pick order BEFORE streaming their on-hand qty, so the
		// lazy chunked stream yields the highest-priority locators first; once the greedy loop has covered
		// the demand it stops pulling, and no further locator chunks are queried for stock.
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
			// Partial coverage: the uncovered remainder is left unfulfilled (no fallback default-locator line);
			// the drift watchdog retries the assignment as stock becomes available.
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

	/**
	 * Greedy allocation of {@code demandQty} across the contributing locators of {@code qtyOnHandByLocator}.
	 *
	 * <p>Orders the positive-on-hand locators in the locator pick order ({@link #buildLocatorSortKey}, via
	 * {@link #getLocatorSortKey}), converts each locator's on-hand qty (in the product stocking UOM) into the
	 * demand UOM via {@code convertToDemandUom} (a no-op when the UOMs already match), and greedily fills the
	 * demand. A locator whose qty cannot be converted ({@link NoUOMConversionException}) is skipped (reported via
	 * {@code onSkippedLocator}, treated as non-contributing). Returns the insertion-ordered allocation and the
	 * uncovered remainder. Only the UOM conversion is injected — so a contrived cross-UOM case the real warehouse
	 * workflow could not produce can be unit-tested deterministically; locator ordering uses the real warehouse data.</p>
	 */
	@VisibleForTesting
	AllocationResult greedyAllocate(
			@NonNull final Quantity demandQty,
			@NonNull final ProductQtyOnHandByLocator qtyOnHandByLocator,
			@NonNull final ConvertToDemandUom convertToDemandUom,
			@NonNull final java.util.function.BiConsumer<LocatorId, Quantity> onSkippedLocator)
	{
		// Pre-materialised map variant: sort contributing locators in the locator pick order, then delegate
		// to the streaming core. Production goes through greedyAllocateOrdered directly with a lazy stream.
		final Stream<LocatorIdAndQty> orderedNonEmpty = qtyOnHandByLocator.streamNonEmptyLocatorIds()
				.sorted(Comparator.comparing(this::getLocatorSortKey))
				.map(locatorId -> LocatorIdAndQty.of(locatorId, qtyOnHandByLocator.getQty(locatorId)));
		return greedyAllocateOrdered(demandQty, orderedNonEmpty, convertToDemandUom, onSkippedLocator);
	}

	/**
	 * Greedy allocation core: consumes {@code orderedNonEmpty} (already in the locator pick order and only
	 * positive-on-hand entries) via an iterator so the stream's lazy upstream (chunked locator-stock fetch)
	 * is short-circuited as soon as {@code remaining <= 0}.
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

				// Convert the locator's on-hand qty (product stocking UOM) into the demand UOM before comparing/allocating.
				// A no-op when the UOMs already match. If no conversion exists, skip this locator (non-contributing).
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
	 * Result of {@link #greedyAllocate}: the per-locator allocation (insertion-ordered) and the uncovered demand remainder.
	 */
	@lombok.Value
	@VisibleForTesting
	static class AllocationResult
	{
		@NonNull Map<LocatorId, Quantity> allocation;
		@NonNull Quantity uncovered;
	}

	/**
	 * Splits the group's per-source-locator allocation chunks back across the contributing assignments — the "then
	 * split" half of DESIGN.md §4. Returns, per source locator, the complete contributor set of the {@code DD_OrderLine}
	 * that locator produces.
	 *
	 * <p><b>Sequential, not proportional</b> (decided 2026-07-24): the chunks are walked in the locator pick order
	 * ({@code allocation} is insertion-ordered by the greedy) and filled from {@code contributorsInOrder}, advancing to
	 * the next contributor once the current one's demand is exhausted. That is what today's per-assignment behaviour
	 * already does — each assignment takes greedily in locator pick order; only the netting between contributors is
	 * new. Proportional splitting was rejected: it yields fractional quantities on piece goods and has no precedent
	 * here.</p>
	 *
	 * <p>Two consequences worth stating, both intended:</p>
	 * <ul>
	 *   <li>one contributor can span two lines (its demand runs past the end of a locator's chunk), and one line can
	 *       serve several contributors — the two axes cross, they do not nest;</li>
	 *   <li>a contributor the chunks never reach (stock ran out ahead of it) gets NO row rather than a {@code Qty=0}
	 *       row, so the shortfall stays derived and is recomputed by the next reconcile (DESIGN.md §4.1).</li>
	 * </ul>
	 *
	 * <p>Per line the shares sum EXACTLY to the chunk; per contributor they sum to at most its {@code QtyToPick}
	 * (partial coverage is allowed today). Every quantity is in the group key's UOM.</p>
	 */
	@VisibleForTesting
	ImmutableMap<LocatorId, ImmutableList<DDOrderLineContributor>> attribute(
			@NonNull final List<PickingJobSchedule> contributorsInOrder,
			@NonNull final Map<LocatorId, Quantity> allocation)
	{
		final ImmutableMap.Builder<LocatorId, ImmutableList<DDOrderLineContributor>> result = ImmutableMap.builder();

		final Iterator<PickingJobSchedule> contributors = contributorsInOrder.iterator();
		PickingJobSchedule current = contributors.hasNext() ? contributors.next() : null;
		Quantity currentRemaining = current != null ? current.getQtyToPick() : null;

		for (final Map.Entry<LocatorId, Quantity> chunk : allocation.entrySet())
		{
			final ImmutableList.Builder<DDOrderLineContributor> rows = ImmutableList.builder();
			Quantity chunkRemaining = chunk.getValue();

			while (chunkRemaining.signum() > 0 && current != null)
			{
				if (currentRemaining.signum() <= 0)
				{
					current = contributors.hasNext() ? contributors.next() : null;
					currentRemaining = current != null ? current.getQtyToPick() : null;
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

	private String getLocatorSortKey(@NonNull final LocatorId locatorId)
	{
		final Locator loc = warehouseRepository.getLocatorById(locatorId);
		return buildLocatorSortKey(loc.getPriorityNo(), loc.getValue());
	}

	/**
	 * Single source of truth for the DD_Order picking-replenishment locator pick order. Builds a composite sort key
	 * that yields PriorityNo ASC, then Value ASC under plain lexicographic order. To change the pick order, change
	 * this method only — every caller orders by the key it returns and does not restate the order itself.
	 *
	 * <p>The key format is {@code zeroPad(priorityNo, 10) + "|" + value}. The pad width 10 matches
	 * {@code M_Locator.PriorityNo numeric(10,0)}. Both inputs are NOT NULL in production
	 * ({@code M_Locator.Value} NOT NULL; {@code PriorityNo numeric(10,0) NOT NULL DEFAULT 50}).
	 * Non-negative PriorityNo values are assumed (negative values would sort incorrectly as strings).</p>
	 */
	@VisibleForTesting
	static String buildLocatorSortKey(final int priorityNo, @NonNull final String value)
	{
		return Strings.padStart(Integer.toString(priorityNo), 10, '0') + "|" + value;
	}

	/**
	 * Indexes the given live DD_Orders' single line by its source locator ({@code DD_OrderLine.M_Locator_ID}).
	 * Each reconcile DD_Order has exactly one line, so the mapping is 1:1. The line (which carries its parent
	 * {@code DD_Order} via {@link DDOrderLowLevelDAO#retrieveLines}) is kept so the update path does not re-fetch it.
	 */
	private Map<LocatorId, I_DD_OrderLine> indexExistingBySourceLocator(@NonNull final List<I_DD_Order> existingDDOrders)
	{
		final LinkedHashMap<LocatorId, I_DD_OrderLine> byLocator = new LinkedHashMap<>();
		for (final I_DD_Order ddOrder : existingDDOrders)
		{
			final List<I_DD_OrderLine> lines = ddOrderLowLevelDAO.retrieveLines(ddOrder);
			if (lines.isEmpty())
			{
				continue;
			}
			final I_DD_OrderLine line = lines.get(0);
			// Resolve the source LocatorId from the locator record (authoritative warehouse) rather than the line's
			// M_Warehouse_ID, which is not reliably populated on a programmatically-built DD_OrderLine.
			// Skip lines whose source locator is unset: getLocatorByRepoId throws on a 0/unknown id, and a
			// reconcile line with no source locator must be skipped (matches the prior ofRecordOrNull behaviour).
			final int sourceLocatorRepoId = line.getM_Locator_ID();
			if (sourceLocatorRepoId > 0)
			{
				final Locator sourceLocator = warehouseRepository.getLocatorByRepoId(sourceLocatorRepoId);
				byLocator.put(sourceLocator.getLocatorId(), line);
			}
		}
		return byLocator;
	}

	/**
	 * Updates the given existing (Completed) DD_Order line's quantity in place to {@code newQty}: a still-contributing
	 * locator keeps its DD_Order, only the qty is adjusted — no void/recreate. Header quantities live on the line;
	 * {@code QtyEntered == QtyOrdered == TargetQty} as in the create path.
	 *
	 * <p>Data-consistency guard: if the line already has a positive {@code QtyDelivered} (goods have started moving),
	 * the qty is NOT shrunk in place — that would silently strand the already-delivered surplus. Such a line is left
	 * untouched and the situation is logged; the operator resolves it (the watchdog/reconcile retries once delivery
	 * progresses or the demand is restored).</p>
	 *
	 * @return {@code true} when the line now carries {@code newQty}, {@code false} when the guard above left it at its
	 * old quantity. The caller must then leave the line's contributor set alone as well: rewriting it against a
	 * quantity the line does not carry would break the "line quantity == sum of contributor shares" invariant
	 * (DESIGN.md §4.1) and hand the mover a document nobody's demand adds up to.
	 */
	private boolean updateDDOrderLineQtyInPlace(@NonNull final I_DD_OrderLine line, @NonNull final Quantity newQty)
	{
		final BigDecimal newQtyBD = newQty.toBigDecimal();
		if (line.getQtyEntered().compareTo(newQtyBD) == 0
				&& line.getQtyOrdered().compareTo(newQtyBD) == 0
				&& line.getTargetQty().compareTo(newQtyBD) == 0)
		{
			return true; // already at the target qty; nothing to do
		}

		// Guard: never shrink a line that already has delivered qty in place — leave it untouched and log.
		if (line.getQtyDelivered().signum() > 0 && newQtyBD.compareTo(line.getQtyOrdered()) < 0)
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

	/**
	 * Builds exactly one {@link I_DD_Order} (with a single {@link I_DD_OrderLine}) for the picking-reconcile flow,
	 * persists both records (header then line) via {@link DDOrderLowLevelDAO}, and returns the saved (Drafted) LINE —
	 * the caller needs its id to write the line's contributor set, and the header is reachable through it.
	 */
	private I_DD_OrderLine saveDraftDDOrder(@NonNull final CreateDDOrderReplenishmentRequest request)
	{
		final OrgId orgId = request.getOrgId();

		//
		// Header — newInstance() is here (not in DDOrderLowLevelDAO) because this service is in
		// de.metas.handlingunits.base (which has de.metas.swat.base and can see I_M_Picking_Job_Schedule),
		// while DDOrderLowLevelDAO is in de.metas.manufacturing (which does NOT have that dependency).
		// The creation half stays here; save() is delegated to the DAO.
		final I_DD_Order ddOrder = InterfaceWrapperHelper.newInstance(I_DD_Order.class);
		ddOrder.setAD_Org_ID(orgId.getRepoId());
		if (request.getBpartnerId() != null)
		{
			ddOrder.setC_BPartner_ID(request.getBpartnerId().getRepoId());
		}
		ddOrder.setC_DocType_ID(DocTypeId.toRepoId(request.getDocTypeId()));
		ddOrder.setM_Warehouse_ID(request.getInTransitWarehouseId().getRepoId());
		ddOrder.setM_Warehouse_From_ID(request.getSourceWarehouseId().getRepoId());
		ddOrder.setM_Warehouse_To_ID(request.getTargetWarehouseId().getRepoId());
		// PP_Plant from the target warehouse (mirrors HUs2DDOrderProducer) — the libero DD_OrderLine interceptor
		// derives PP_Plant_From_ID from it; without it that interceptor logs a benign "@NotFound@ @PP_Plant_ID@" WARN.
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
		// This flow operates in the assignment's UOM (internal pick-to-packing move):
		// QtyEntered == QtyOrdered == TargetQty intentionally.
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

	private void voidDDOrderFor(@NonNull final DDOrderId existingDDOrderId)
	{
		if (isPickerBusy(existingDDOrderId))
		{
			throw new AdempiereException(MSG_DDOrderPickingReplenishment_PickerBusy, existingDDOrderId);
		}
		ddOrderService.voidIt(existingDDOrderId);
		Loggables.addLog("DD_Order picking replenishment: voided DD_Order_ID={0}", existingDDOrderId.getRepoId());

		// Null the back-reference to M_Picking_Job_Schedule on the (now voided) DD_Order header and its lines.
		// voidIt does NOT clear it, so when the void runs synchronously inside the assignment's delete transaction
		// (afterDelete -> voidDDOrdersForDeletedAssignment) the deferrable FK mpickingjobschedule_ddorder would still
		// point at the about-to-be-deleted assignment and fail at commit. Unlinking here makes that delete clean.
		final I_DD_Order ddOrder = ddOrderLowLevelDAO.getById(existingDDOrderId);
		ddOrder.setM_Picking_Job_Schedule_ID(-1);
		ddOrderLowLevelDAO.save(ddOrder);
		for (final I_DD_OrderLine ddOrderLine : ddOrderLowLevelDAO.retrieveLines(ddOrder))
		{
			ddOrderLine.setM_Picking_Job_Schedule_ID(-1);
			ddOrderLowLevelDAO.save(ddOrderLine);
		}
	}

	/**
	 * Synchronous VOID + unlink for the delete→void path (afterDelete of {@link I_M_Picking_Job_Schedule}).
	 *
	 * <p>Unlike the create/update flow (which reconciles after-commit), the delete must void and unlink the linked
	 * DD_Order(s) <b>in the current (delete) transaction</b>, before the assignment row is flushed. Otherwise the
	 * deferrable FK {@code mpickingjobschedule_ddorder} (DD_Order/DD_OrderLine → M_Picking_Job_Schedule) still
	 * references the deleted assignment and fails at commit.</p>
	 *
	 * <p>Its {@code DD_OrderLine_PickingJobSchedule} alloc rows must go in that same transaction and for the same
	 * reason: {@code DD_OrderLine_PickingJobSchedule.M_Picking_Job_Schedule_ID} is a second DEFERRABLE INITIALLY
	 * DEFERRED foreign key to the assignment, so a surviving alloc row makes the delete fail at commit.</p>
	 *
	 * <p>No live DD_Order linked → still deletes the alloc rows, then a clean no-op.</p>
	 */
	public void voidDDOrdersForDeletedAssignment(@NonNull final PickingJobScheduleId jobScheduleId)
	{
		voidAllDDOrders(ddOrderLowLevelDAO.findActiveDDOrdersForPickingJobSchedule(jobScheduleId));
		contributorRepository.deleteByPickingJobScheduleIds(ImmutableSet.of(jobScheduleId));
	}

	/**
	 * Voids every DD_Order in the given list (and unlinks each one's {@code M_Picking_Job_Schedule_ID} back-ref on
	 * header + lines, so the deferrable FK passes when this runs synchronously inside the assignment's delete trx).
	 * Empty list → clean no-op.
	 */
	private void voidAllDDOrders(@NonNull final List<I_DD_Order> ddOrders)
	{
		for (final I_DD_Order ddOrder : ddOrders)
		{
			voidDDOrderFor(DDOrderId.ofRepoId(ddOrder.getDD_Order_ID()));
		}
	}

	/**
	 * Close-out disposition: CLOSE when no replenishment move is in progress, or DISCONNECT when a move is in
	 * progress (closing would hit the {@code BEFORE_CLOSE clearSchedules} guard and corrupt the half-done move).
	 */
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

	/**
	 * Closes the obsolete replenishment DD_Order (moved stock preserved, open remainder closed off) and releases the
	 * picker so the DD_Order-backed mobile DistributionJob retires from the launcher.
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
	 * Disconnects the in-progress replenishment DD_Order ({@code IsPickingDisconnected=Y}) instead of closing it, so
	 * the guard/reconcile lookup stops seeing it while the FKs and the DistributionJob assignment are retained for
	 * the worker to finish.
	 */
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

		streamAssignmentsNeedingDDOrder()
				.map(this::toReplenishmentRequest)
				.distinct()
				.peek(progress::itemProcessed)
				.forEach(reconciliationEventPublisher::publishOne);

		progress.done("Enqueued {} requests");
	}

	private Stream<PickingJobSchedule> streamAssignmentsNeedingDDOrder()
	{
		return pickingJobScheduleService.streamAssignmentsNeedingDDOrder(ddOrderLowLevelDAO.queryCompletedDDOrders());
	}

	public void assertWarehouseConfigurationIsValid(@NonNull final I_M_Warehouse warehouse)
	{
		if (warehouse.isAutoDistributionOrder() && warehouse.getDD_NetworkDistribution_ID() <= 0)
		{
			throw new AdempiereException(MSG_DDOrderPickingReplenishment_MandatoryNetwork);
		}
	}

	public boolean isPickerBusy(@NonNull final DDOrderId ddOrderId)
	{
		final ShipmentScheduleId scheduleId = ddOrderLowLevelDAO.getShipmentScheduleId(ddOrderId);
		return pickingJobRepository.existsActivePickingJobLineForSchedule(scheduleId);
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
