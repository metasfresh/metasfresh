package de.metas.distribution.ddorder.replenishment;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimaps;
import de.metas.business.BusinessTestHelper;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.distribution.ddorder.DDOrderService;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelDAO;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleService;
import de.metas.distribution.ddorder.replenishment.alloc.DDOrderLineContributor;
import de.metas.distribution.ddorder.replenishment.alloc.DDOrderLineContributorRepository;
import de.metas.distribution.ddorder.replenishment.event.DDOrderReplenishmentEventPublisher;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.job_schedule.service.PickingJobScheduleService;
import de.metas.inout.ShipmentScheduleId;
import de.metas.material.planning.ddorder.DistributionNetworkRepository;
import de.metas.organization.ClientAndOrgId;
import de.metas.picking.api.PickingJobScheduleId;
import de.metas.picking.job_schedule.model.PickingJobSchedule;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.workplace.WorkplaceId;
import de.metas.workplace.WorkplaceService;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseRepository;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.X_DD_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * The disposal decision every reconcile path shares: a replenishment DD_Order whose goods are already on their way —
 * an IN_PROGRESS {@code DD_Order_MoveSchedule}, i.e. the mover has picked from the source but not yet dropped at the
 * workstation — must be DISCONNECTED, never voided, because voiding it strands that in-hand stock (the mover is left
 * holding goods no document accounts for) and would in fact be refused outright by the {@code BEFORE_VOID}
 * {@code clearSchedules} veto. An order with no move under way is still voided, so the guard cannot over-reach.
 * <p>
 * Every order here keeps {@code QtyInTransit=0} and {@code QtyDelivered=0} on purpose: those columns are never written
 * by any production flow, so a disposal decision must not be derived from them.
 * <p>
 * Each test pairs the two cases on one call, so the same pass proves both the disconnect and the plain void.
 */
@ExtendWith(AdempiereTestWatcher.class)
class DDOrderPickingReplenishmentServiceVoidOrDisconnectTest
{
	private static final ProductId PRODUCT_ID = ProductId.ofRepoId(1000001);

	private I_C_UOM uom;
	private UomId uomId;
	private LocatorId sourceLocatorId;
	private LocatorId locatorToId;

	private DDOrderService ddOrderService;
	private PickingJobRepository pickingJobRepository;
	private PickingJobScheduleService pickingJobScheduleService;
	private WorkplaceService workplaceService;
	private DDOrderMoveScheduleService ddOrderMoveScheduleService;
	private DDOrderLineContributorRepository contributorRepository;

	private DDOrderPickingReplenishmentService service;
	private DDOrderReplenishmentGroupKey groupKey;

	/** The orders a mover is under way on, as {@link #createGroupDDOrder} declares them; the batch query is answered from it. */
	private final HashSet<DDOrderId> ordersWithMoveInProgress = new HashSet<>();

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		uom = BusinessTestHelper.createUOM("PCE");
		uomId = UomId.ofRepoId(uom.getC_UOM_ID());
		sourceLocatorId = createLocator("stock");
		locatorToId = createLocator("pick-from");

		// Real DAO + real alloc repository: the disposal paths are all about which records the DB shows them, so only
		// the collaborators the decision is asserted on (DD_Order doc actions) and the picking lookups are mocked.
		ddOrderService = mock(DDOrderService.class);
		pickingJobRepository = mock(PickingJobRepository.class);
		pickingJobScheduleService = mock(PickingJobScheduleService.class);
		workplaceService = mock(WorkplaceService.class);
		// The live "goods are on their way" signal: an IN_PROGRESS DD_Order_MoveSchedule. Production asks it for a whole
		// candidate set in one query, so the mock answers exactly that: the queried set intersected with the orders the
		// scenario declared a mover to be working on. Nothing declared -> empty, so silence never means "moving".
		ordersWithMoveInProgress.clear();
		ddOrderMoveScheduleService = mock(DDOrderMoveScheduleService.class);
		when(ddOrderMoveScheduleService.retrieveIdsOfOrdersWithInProgressSchedules(any()))
				.thenAnswer(invocation -> invocation.<Set<DDOrderId>>getArgument(0)
						.stream()
						.filter(ordersWithMoveInProgress::contains)
						.collect(ImmutableSet.toImmutableSet()));
		contributorRepository = new DDOrderLineContributorRepository();

		service = new DDOrderPickingReplenishmentService(
				pickingJobRepository,
				new DDOrderLowLevelDAO(),
				ddOrderService,
				mock(DistributionNetworkRepository.class),
				mock(ITrxManager.class),
				mock(DDOrderReplenishmentEventPublisher.class),
				pickingJobScheduleService,
				workplaceService,
				ddOrderMoveScheduleService,
				new WarehouseRepository(),
				contributorRepository);

		groupKey = DDOrderReplenishmentGroupKey.builder()
				.productId(PRODUCT_ID)
				.locatorToId(locatorToId)
				.uomId(uomId)
				.build();

		// Nobody is actively picking in these scenarios: the picker-busy verdict is a DIFFERENT, narrower guard, and
		// the point of these tests is that it does not cover goods that are already on their way.
		when(pickingJobRepository.retrieveScheduleIdsWithActivePickingJobLine(any())).thenReturn(ImmutableSet.of());
	}

	private LocatorId createLocator(@NonNull final String value)
	{
		final I_M_Warehouse warehouse = InterfaceWrapperHelper.newInstance(I_M_Warehouse.class);
		warehouse.setName(value);
		warehouse.setValue(value);
		InterfaceWrapperHelper.saveRecord(warehouse);

		final I_M_Locator locator = InterfaceWrapperHelper.newInstance(I_M_Locator.class);
		locator.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());
		locator.setValue(value);
		InterfaceWrapperHelper.saveRecord(locator);

		return LocatorId.ofRepoId(warehouse.getM_Warehouse_ID(), locator.getM_Locator_ID());
	}

	/**
	 * One live replenishment order of the group, sourcing from {@link #sourceLocatorId} and serving {@code contributorId}.
	 *
	 * @param hasMoveInProgress whether a mover has picked from this order's source and not yet dropped — an IN_PROGRESS
	 *                          {@code DD_Order_MoveSchedule}, stubbed on {@link DDOrderMoveScheduleService}.
	 */
	private GroupDDOrder createGroupDDOrder(
			@NonNull final String qtyOrdered,
			final boolean hasMoveInProgress,
			@NonNull final PickingJobScheduleId contributorId)
	{
		return createGroupDDOrder(sourceLocatorId, qtyOrdered, hasMoveInProgress, contributorId);
	}

	/**
	 * The source-locator-carrying flavour: the per-locator disposal decides by source locator, so its scenarios need one
	 * order per locator.
	 */
	private GroupDDOrder createGroupDDOrder(
			@NonNull final LocatorId sourceLocatorId,
			@NonNull final String qtyOrdered,
			final boolean hasMoveInProgress,
			@NonNull final PickingJobScheduleId contributorId)
	{
		final I_DD_Order ddOrder = InterfaceWrapperHelper.newInstance(I_DD_Order.class);
		ddOrder.setDocStatus(X_DD_Order.DOCSTATUS_Completed);
		ddOrder.setIsPickingDisconnected(false);
		ddOrder.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(ddOrder);

		final I_DD_OrderLine line = InterfaceWrapperHelper.newInstance(I_DD_OrderLine.class);
		line.setDD_Order_ID(ddOrder.getDD_Order_ID());
		line.setM_Product_ID(PRODUCT_ID.getRepoId());
		line.setM_Locator_ID(sourceLocatorId.getRepoId());
		line.setM_LocatorTo_ID(locatorToId.getRepoId());
		line.setC_UOM_ID(uomId.getRepoId());
		line.setQtyEntered(new BigDecimal(qtyOrdered));
		line.setQtyOrdered(new BigDecimal(qtyOrdered));
		line.setTargetQty(new BigDecimal(qtyOrdered));
		// Left at zero even for the order a mover is working on: production never writes these two columns, so the
		// disposal decision must hold without them. Pinned rather than omitted, so re-deriving it from them fails here.
		line.setQtyInTransit(BigDecimal.ZERO);
		line.setQtyDelivered(BigDecimal.ZERO);
		InterfaceWrapperHelper.saveRecord(line);

		final DDOrderId ddOrderId = DDOrderId.ofRepoId(ddOrder.getDD_Order_ID());
		if (hasMoveInProgress)
		{
			ordersWithMoveInProgress.add(ddOrderId);
		}

		final DDOrderLineId lineId = DDOrderLineId.ofRepoId(line.getDD_OrderLine_ID());
		contributorRepository.replaceByLineId(
				lineId,
				ImmutableList.of(DDOrderLineContributor.of(contributorId, Quantity.of(qtyOrdered, uom))));

		return new GroupDDOrder(ddOrderId, lineId, line);
	}

	private PickingJobSchedule contributor(final int repoId, @NonNull final String qtyToPick)
	{
		return PickingJobSchedule.builder()
				.id(PickingJobScheduleId.ofRepoId(repoId))
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(1000000, 1000000))
				.shipmentScheduleId(ShipmentScheduleId.ofRepoId(repoId))
				.workplaceId(WorkplaceId.ofRepoId(1000000))
				.qtyToPick(Quantity.of(qtyToPick, uom))
				.active(true)
				.processed(false)
				.build();
	}

	private boolean hasContributorRows(@NonNull final GroupDDOrder ddOrder)
	{
		return !contributorRepository.getByLineIds(ImmutableSet.of(ddOrder.getLineId())).isEmpty();
	}

	@Value
	private static class GroupDDOrder
	{
		@NonNull DDOrderId ddOrderId;
		@NonNull DDOrderLineId lineId;
		@NonNull I_DD_OrderLine line;
	}

	/** The lines the production code hands the per-locator disposal, grouped exactly as {@code retrieveLines} groups them. */
	private static ImmutableListMultimap<Integer, I_DD_OrderLine> lineIndexOf(@NonNull final GroupDDOrder... ddOrders)
	{
		return Multimaps.index(
				Stream.of(ddOrders).map(GroupDDOrder::getLine).collect(ImmutableList.toImmutableList()),
				I_DD_OrderLine::getDD_Order_ID);
	}

	/**
	 * The worst of the three sites: a source locator that DROPS OUT of the freshly recomputed allocation — reached by
	 * ordinary demand shrinkage, since a departing contributor can lower the group's demand enough for the greedy
	 * allocator to stop needing a lower-priority locator. The frozen-split guards do not cover it: they only protect
	 * locators that REMAIN in the required allocation.
	 */
	@Test
	void aDroppedOutLocator_withAMoveInProgress_isDisconnected_whileTheIdleOneIsVoidedAndTheRequiredOneIsUntouched()
	{
		final PickingJobScheduleId contributorId = PickingJobScheduleId.ofRepoId(5000001);
		final LocatorId movingLocatorId = createLocator("dropped-out-with-goods-on-their-way");
		final LocatorId idleLocatorId = createLocator("dropped-out-idle");
		final LocatorId stillRequiredLocatorId = createLocator("still-required");

		final GroupDDOrder movingOrder = createGroupDDOrder(movingLocatorId, "10", true, contributorId);
		final GroupDDOrder idleOrder = createGroupDDOrder(idleLocatorId, "5", false, contributorId);
		final GroupDDOrder requiredOrder = createGroupDDOrder(stillRequiredLocatorId, "7", false, contributorId);

		final HashSet<DDOrderLineId> obsoleteLineIds = new HashSet<>();
		final HashSet<DDOrderLineId> disconnectedLineIds = new HashSet<>();

		service.disposeOrdersOfLocatorsNoLongerRequired(
				ImmutableMap.of(
						movingLocatorId, movingOrder.getLine(),
						idleLocatorId, idleOrder.getLine(),
						stillRequiredLocatorId, requiredOrder.getLine()),
				ImmutableSet.of(stillRequiredLocatorId),
				lineIndexOf(movingOrder, idleOrder, requiredOrder),
				// The pass's batched "which of these orders holds moved goods" verdict, as reconcile computes it.
				ImmutableSet.of(movingOrder.getDdOrderId()),
				obsoleteLineIds,
				disconnectedLineIds);

		verify(ddOrderService).markAsPickingDisconnected(movingOrder.getDdOrderId());
		verify(ddOrderService, never()).voidIt(movingOrder.getDdOrderId());
		verify(ddOrderService).voidIt(idleOrder.getDdOrderId());
		verify(ddOrderService, never()).voidIt(requiredOrder.getDdOrderId());
		verify(ddOrderService, never()).markAsPickingDisconnected(requiredOrder.getDdOrderId());

		// The disconnected line is filed as disconnected, so the trailing cleanup keeps its alloc row (the delivery
		// still navigates to the move in progress) and the frozen split nets its share off the re-plan.
		assertThat(disconnectedLineIds).containsExactly(movingOrder.getLineId());
		assertThat(obsoleteLineIds).containsExactly(idleOrder.getLineId());
	}

	/**
	 * The VOID branch of {@code reconcile}: the group lost every contributor while one of its orders is already moving
	 * goods. That order is disconnected and KEEPS its alloc row (else the delivery can no longer navigate to the move
	 * its worker is finishing); the idle sibling is voided and loses its alloc row.
	 */
	@Test
	void voidOutOfAGroup_disconnectsTheOrderWithAMoveInProgress_andVoidsTheIdleOne()
	{
		final PickingJobScheduleId formerContributorId = PickingJobScheduleId.ofRepoId(5000001);
		final GroupDDOrder movingOrder = createGroupDDOrder("10", true, formerContributorId);
		final GroupDDOrder idleOrder = createGroupDDOrder("5", false, formerContributorId);

		// No contributor left in the group, and the former one is NOT processed -> VOID (not a close-out).
		when(workplaceService.getWorkplaceIdsByEffectivePickFromLocatorId(locatorToId)).thenReturn(ImmutableSet.of());
		when(pickingJobScheduleService.listContributorsOfGroup(any(), any())).thenReturn(ImmutableList.of());
		when(pickingJobScheduleService.getByIds(any())).thenReturn(ImmutableList.of(contributor(5000001, "10")));

		service.reconcile(groupKey, ClientAndOrgId.ofClientAndOrg(1000000, 1000000));

		verify(ddOrderService).markAsPickingDisconnected(movingOrder.getDdOrderId());
		verify(ddOrderService, never()).voidIt(movingOrder.getDdOrderId());
		verify(ddOrderService).voidIt(idleOrder.getDdOrderId());

		assertThat(hasContributorRows(movingOrder)).as("the disconnected order keeps its alloc row").isTrue();
		assertThat(hasContributorRows(idleOrder)).as("the voided order loses its alloc row").isFalse();
	}

	/**
	 * The un-assignment path: the departing assignment was the last contributor of both orders, but a mover is already
	 * under way on one of them — voiding it would strand what he has in his hands.
	 */
	@Test
	void deletedAssignment_disconnectsTheOrderWithAMoveInProgress_andVoidsTheIdleOne()
	{
		final PickingJobSchedule departingAssignment = contributor(5000001, "15");
		final GroupDDOrder movingOrder = createGroupDDOrder("10", true, departingAssignment.getId());
		final GroupDDOrder idleOrder = createGroupDDOrder("5", false, departingAssignment.getId());

		when(pickingJobScheduleService.getByIds(any())).thenReturn(ImmutableList.of(departingAssignment));

		service.voidDDOrdersForDeletedAssignment(departingAssignment);

		verify(ddOrderService).markAsPickingDisconnected(movingOrder.getDdOrderId());
		verify(ddOrderService, never()).voidIt(movingOrder.getDdOrderId());
		verify(ddOrderService).voidIt(idleOrder.getDdOrderId());
	}
}
