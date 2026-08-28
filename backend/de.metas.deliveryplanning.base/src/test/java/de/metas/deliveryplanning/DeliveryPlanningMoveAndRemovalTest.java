/*
 * #%L
 * de.metas.deliveryplanning.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.deliveryplanning;

import com.google.common.collect.ImmutableList;
import de.metas.document.dimension.DimensionService;
import de.metas.document.engine.DocStatus;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.shipping.ShipperRepository;
import de.metas.shipping.ShipperTransportationDocSubTypeGuard;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.model.I_M_Delivery_Planning_Alloc;
import org.compiere.model.X_M_Delivery_Planning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

/**
 * What {@code addTo} and {@code removeFrom} leave behind, driven through the SERVICE rather than the repository.
 * <p>
 * Deliberately asserts nothing that {@link DeliveryPlanningBatchLoadingTest} already pins. That one counts round
 * trips and covers the plain add of an unallocated planning; this one covers the things neither it nor the
 * repository-level tests reach through the orchestration:
 * <ol>
 *     <li>the MOVE off a source draft instruction - the clause that makes add-to more than an add</li>
 *     <li>its idempotency - a planning already on the target must not be taken off and put back</li>
 *     <li>removal leaving the instruction's OTHER plannings alone, which needs a partial selection</li>
 *     <li>a moved allocation continuing the TARGET's LineNo rather than the source's</li>
 *     <li>a removed planning is immediately re-allocatable, and its retired allocation does not leak into an
 *     	active-filtered lookup</li>
 * </ol>
 * <p>
 * The repository is the real one and the selection is a real query filter over the in-memory store - nothing is
 * stubbed, so {@code getAllocatedInstructionIds} genuinely reads the allocation rows. That matters: it is what
 * populates {@code DeliveryPlanning.deliveryInstructionId}, which is what the already-on-target filter and the
 * completed-instruction rule branch on. A mocked repository would make (1) and (2) partly self-fulfilling.
 */
class DeliveryPlanningMoveAndRemovalTest
{
	/** Only ever read back as a {@code ProductId}: the planning carries its own UOM, so no product record is needed. */
	private static final int PRODUCT_ID = 540010;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private DeliveryPlanningRepository deliveryPlanningRepository;
	private DeliveryPlanningService deliveryPlanningService;
	private I_C_UOM uom;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		deliveryPlanningRepository = new DeliveryPlanningRepository(Mockito.mock(DimensionService.class));
		deliveryPlanningService = new DeliveryPlanningService(
				Mockito.mock(ShipperRepository.class),
				deliveryPlanningRepository,
				Mockito.mock(DeliveryStatusColorPaletteService.class),
				Mockito.mock(DimensionService.class),
				Mockito.mock(MeansOfTransportationService.class),
				new ShipperTransportationDocSubTypeGuard());

		uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		InterfaceWrapperHelper.save(uom);
	}

	// ------------------------------------------------------------------ helpers

	private I_M_Delivery_Planning deliveryPlanning()
	{
		final I_M_Delivery_Planning record = InterfaceWrapperHelper.newInstance(I_M_Delivery_Planning.class);
		record.setTransportDirection(X_M_Delivery_Planning.TRANSPORTDIRECTION_Outgoing);
		record.setM_Product_ID(PRODUCT_ID);
		record.setC_UOM_ID(uom.getC_UOM_ID());
		record.setPlannedLoadedQuantity(BigDecimal.TEN);
		record.setPlannedDischargeQuantity(BigDecimal.ONE);
		InterfaceWrapperHelper.save(record);
		return record;
	}

	private ShipperTransportationId draftDeliveryInstruction(@NonNull final String documentNo)
	{
		final I_M_ShipperTransportation record = InterfaceWrapperHelper.newInstance(I_M_ShipperTransportation.class);
		record.setDocumentNo(documentNo);
		record.setDocStatus(DocStatus.Drafted.getCode());
		InterfaceWrapperHelper.save(record);
		return ShipperTransportationId.ofRepoId(record.getM_ShipperTransportation_ID());
	}

	/**
	 * A REAL selection filter over the given rows - not a stub. Naming a subset is what lets a removal assert that
	 * the plannings the planner did NOT select came through untouched.
	 */
	private IQueryFilter<I_M_Delivery_Planning> selectionOf(final I_M_Delivery_Planning... records)
	{
		return queryBL.createCompositeQueryFilter(I_M_Delivery_Planning.class)
				.addInArrayFilter(
						I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID,
						Arrays.stream(records)
								.map(DeliveryPlanningMoveAndRemovalTest::idOf)
								.collect(ImmutableList.toImmutableList()));
	}

	/** Puts the given plannings on the given instruction the way a previous action would have left them. */
	private void allocateTo(@NonNull final ShipperTransportationId deliveryInstructionId, final I_M_Delivery_Planning... records)
	{
		final ImmutableList<DeliveryPlanningId> ids = Arrays.stream(records)
				.map(DeliveryPlanningMoveAndRemovalTest::idOf)
				.collect(ImmutableList.toImmutableList());

		deliveryPlanningRepository.createAllocations(
				deliveryInstructionId,
				ids.stream()
						.map(id -> DeliveryPlanningAllocCreateRequest.builder()
								.deliveryPlanningId(id)
								.productId(ProductId.ofRepoId(PRODUCT_ID))
								.qtyLoaded(Quantity.of(BigDecimal.TEN, uom))
								.qtyDischarged(Quantity.of(BigDecimal.ONE, uom))
								.build())
						.collect(ImmutableList.toImmutableList()));

		deliveryPlanningRepository.updateDeliveryPlanningsFromInstruction(ids, deliveryInstructionId);
	}

	/** Like {@link #allocateTo}, but stamps the given order line onto the planning's shipping package. */
	private void allocateToWithOrderLine(
			@NonNull final ShipperTransportationId deliveryInstructionId,
			@NonNull final OrderLineId orderLineId,
			@NonNull final I_M_Delivery_Planning record)
	{
		final DeliveryPlanningId id = idOf(record);

		deliveryPlanningRepository.createAllocations(
				deliveryInstructionId,
				ImmutableList.of(DeliveryPlanningAllocCreateRequest.builder()
						.deliveryPlanningId(id)
						.productId(ProductId.ofRepoId(PRODUCT_ID))
						.qtyLoaded(Quantity.of(BigDecimal.TEN, uom))
						.qtyDischarged(Quantity.of(BigDecimal.ONE, uom))
						.orderLineId(orderLineId)
						.build()));

		deliveryPlanningRepository.updateDeliveryPlanningsFromInstruction(ImmutableList.of(id), deliveryInstructionId);
	}

	private static void setETD(@NonNull final ShipperTransportationId deliveryInstructionId, @NonNull final Timestamp etd)
	{
		final I_M_ShipperTransportation record = InterfaceWrapperHelper.load(deliveryInstructionId, I_M_ShipperTransportation.class);
		record.setETD(etd);
		InterfaceWrapperHelper.save(record);
	}

	private static DeliveryPlanningId idOf(@NonNull final I_M_Delivery_Planning record)
	{
		return DeliveryPlanningId.ofRepoId(record.getM_Delivery_Planning_ID());
	}

	private static I_M_Delivery_Planning reload(@NonNull final I_M_Delivery_Planning record)
	{
		return InterfaceWrapperHelper.load(idOf(record), I_M_Delivery_Planning.class);
	}

	private List<I_M_Delivery_Planning_Alloc> allocationsInLineNoOrder()
	{
		return queryBL.createQueryBuilder(I_M_Delivery_Planning_Alloc.class)
				.orderBy().addColumnAscending(I_M_Delivery_Planning_Alloc.COLUMNNAME_LineNo).endOrderBy()
				.create()
				.list();
	}

	/**
	 * The planning's CURRENT (active) allocation - filtered on {@code IsActive}, because after a move the
	 * planning's retired source-side row also matches on {@code M_Delivery_Planning_ID} and would otherwise
	 * make this lookup ambiguous.
	 */
	private I_M_Delivery_Planning_Alloc allocationOf(@NonNull final I_M_Delivery_Planning record)
	{
		return allocationsInLineNoOrder().stream()
				.filter(alloc -> alloc.getM_Delivery_Planning_ID() == record.getM_Delivery_Planning_ID())
				.filter(I_M_Delivery_Planning_Alloc::isActive)
				.findFirst()
				.orElseThrow(() -> new AssertionError("no ACTIVE allocation for delivery planning " + record.getM_Delivery_Planning_ID()));
	}

	private boolean shippingPackageIsActive(final int shippingPackageId)
	{
		return InterfaceWrapperHelper.load(shippingPackageId, I_M_ShippingPackage.class).isActive();
	}

	/**
	 * The general form of the leak: every shipping package belongs to exactly one allocation, so an action that
	 * deleted an allocation and left its package behind strands a row here.
	 * <p>
	 * Worth more than naming a single expected id, because it also catches a leak on a path neither this test nor
	 * its author anticipated.
	 * <p>
	 * Robust across a void as well: a void deactivates the allocation and its shipping package together rather
	 * than deleting either, and neither count filters on {@code IsActive}, so the two stay in lockstep. None of
	 * these scenarios voids anything - but the invariant would survive one, so it needs no protecting from it.
	 */
	private void assertNoOrphanedShippingPackages()
	{
		assertThat(queryBL.createQueryBuilder(I_M_ShippingPackage.class).create().list())
				.as("one shipping package per allocation - a stranded package means a delete took only half the pair")
				.hasSameSizeAs(allocationsInLineNoOrder());
	}

	// ------------------------------------------------------------------ tests

	@Test
	@DisplayName("add-to MOVES a planning off the draft instruction it was on, deactivating its source allocation and shipping package")
	void addToMovesThePlanningOffItsSourceInstruction()
	{
		final ShipperTransportationId source = draftDeliveryInstruction("SOURCE-1");
		final ShipperTransportationId target = draftDeliveryInstruction("TARGET-1");
		// distinct dates on source and target: the move deactivates the source allocation (which resets the
		// planning's dates from its order/schedule) and then immediately re-syncs it from the target, so what
		// survives must be the TARGET's date, never the source's - proving the intermediate reset does not leak
		setETD(target, Timestamp.valueOf("2026-03-25 00:00:00"));
		final I_M_Delivery_Planning moving = deliveryPlanning();
		allocateTo(source, moving);

		final int sourcePackageId = allocationOf(moving).getM_ShippingPackage_ID();
		assertThat(reload(moving).getReleaseNo()).startsWith("SOURCE-1-");

		deliveryPlanningService.addTo(selectionOf(moving), target);

		assertThat(allocationsInLineNoOrder())
				.as("the source row survives DEACTIVATED, and a fresh ACTIVE row sits on the target - nothing was left standing active")
				.extracting(I_M_Delivery_Planning_Alloc::getM_Delivery_Planning_ID, I_M_Delivery_Planning_Alloc::getM_ShipperTransportation_ID, I_M_Delivery_Planning_Alloc::isActive)
				.containsExactlyInAnyOrder(
						tuple(moving.getM_Delivery_Planning_ID(), source.getRepoId(), false),
						tuple(moving.getM_Delivery_Planning_ID(), target.getRepoId(), true));

		assertThat(shippingPackageIsActive(sourcePackageId))
				.as("the source allocation's shipping package went with it, deactivated rather than deleted")
				.isFalse();
		assertNoOrphanedShippingPackages();

		final I_M_Delivery_Planning moved = reload(moving);
		assertThat(moved.getReleaseNo())
				.as("re-stamped from the target, with nothing of the source surviving in it")
				.startsWith("TARGET-1-")
				.doesNotContain("SOURCE-1");
		assertThat(moved.getM_ShipperTransportation_ID()).isEqualTo(target.getRepoId());
		assertThat(moved.getETD())
				.as("the source-side reset is only ever transient here: the target's own sync-down has the final word")
				.isEqualTo(Timestamp.valueOf("2026-03-25 00:00:00"));
	}

	@Test
	@DisplayName("add-to is a no-op for a planning already on the target - not a delete and re-create")
	void addToLeavesAPlanningAlreadyOnTheTargetAlone()
	{
		final ShipperTransportationId target = draftDeliveryInstruction("TARGET-2");
		final I_M_Delivery_Planning alreadyThere = deliveryPlanning();
		allocateTo(target, alreadyThere);

		final I_M_Delivery_Planning_Alloc before = allocationOf(alreadyThere);
		final int allocationId = before.getM_Delivery_Planning_Alloc_ID();
		final int shippingPackageId = before.getM_ShippingPackage_ID();
		final int lineNo = before.getLineNo();

		deliveryPlanningService.addTo(selectionOf(alreadyThere), target);

		final I_M_Delivery_Planning_Alloc after = allocationOf(alreadyThere);
		assertThat(allocationsInLineNoOrder()).hasSize(1);
		assertThat(after.getM_Delivery_Planning_Alloc_ID())
				.as("the same allocation row - re-adding must not delete and re-create it")
				.isEqualTo(allocationId);
		assertThat(after.getM_ShippingPackage_ID())
				.as("and therefore the same shipping package, not a second one")
				.isEqualTo(shippingPackageId);
		assertThat(after.getLineNo())
				.as("the printed line order is unchanged - a re-create would push it to the end")
				.isEqualTo(lineNo);
		assertNoOrphanedShippingPackages();
	}

	@Test
	@DisplayName("remove-from takes only the SELECTED planning off, leaving the instruction's others untouched")
	void removeFromLeavesTheInstructionsOtherPlanningsAlone()
	{
		final ShipperTransportationId deliveryInstructionId = draftDeliveryInstruction("SHARED-3");
		final I_M_Delivery_Planning leaving = deliveryPlanning();
		final I_M_Delivery_Planning staying = deliveryPlanning();
		allocateTo(deliveryInstructionId, leaving, staying);

		final I_M_Delivery_Planning_Alloc stayingAllocBefore = allocationOf(staying);
		final int stayingAllocationId = stayingAllocBefore.getM_Delivery_Planning_Alloc_ID();
		final int stayingLineNo = stayingAllocBefore.getLineNo();
		final String stayingReleaseNo = reload(staying).getReleaseNo();
		final I_M_Delivery_Planning_Alloc leavingAllocBefore = allocationOf(leaving);
		final int leavingAllocationId = leavingAllocBefore.getM_Delivery_Planning_Alloc_ID();
		final int leavingPackageId = leavingAllocBefore.getM_ShippingPackage_ID();

		// only one of the two is selected - which is the whole point of the assertion below
		deliveryPlanningService.removeFrom(selectionOf(leaving));

		final I_M_Delivery_Planning removed = reload(leaving);
		assertThat(removed.getReleaseNo()).isNull();
		assertThat(removed.getM_ShipperTransportation_ID()).isLessThanOrEqualTo(0);
		assertThat(shippingPackageIsActive(leavingPackageId))
				.as("removed - deactivated, not deleted")
				.isFalse();
		assertThat(InterfaceWrapperHelper.load(leavingAllocationId, I_M_Delivery_Planning_Alloc.class).isActive())
				.as("the removed planning's own allocation row survives, deactivated")
				.isFalse();
		assertThat(deliveryPlanningRepository.getAllocatedInstructionIds(ImmutableList.of(idOf(leaving))))
				.as("the retired allocation must not leak into an active-filtered lookup")
				.isEmpty();

		assertThat(allocationsInLineNoOrder())
				.as("both rows survive - the removed one deactivated, the staying one still active")
				.hasSize(2);
		final I_M_Delivery_Planning_Alloc stayingAllocAfter = allocationOf(staying);
		assertThat(stayingAllocAfter.getM_Delivery_Planning_Alloc_ID()).isEqualTo(stayingAllocationId);
		assertThat(stayingAllocAfter.getLineNo())
				.as("gaps are tolerated - the surviving lines are NOT renumbered, which would change a printed document")
				.isEqualTo(stayingLineNo);
		assertThat(reload(staying).getReleaseNo())
				.as("the forwarder already holds this number for the rest of the consignment")
				.isEqualTo(stayingReleaseNo);
		assertThat(reload(staying).getM_ShipperTransportation_ID()).isEqualTo(deliveryInstructionId.getRepoId());
		assertNoOrphanedShippingPackages();
	}

	@Test
	@DisplayName("remove-from releases the planning for IMMEDIATE re-allocation - proving the partial unique indexes only key on IsActive='Y'")
	void removeFromThenAddToSucceedsImmediately()
	{
		final ShipperTransportationId source = draftDeliveryInstruction("SOURCE-7");
		final I_M_Delivery_Planning planning = deliveryPlanning();
		allocateTo(source, planning);

		deliveryPlanningService.removeFrom(selectionOf(planning));

		final ShipperTransportationId target = draftDeliveryInstruction("TARGET-7");
		deliveryPlanningService.addTo(selectionOf(reload(planning)), target);

		final I_M_Delivery_Planning reAllocated = reload(planning);
		assertThat(reAllocated.getM_ShipperTransportation_ID())
				.as("the same planning is allocated again, right away, with no leftover row blocking it")
				.isEqualTo(target.getRepoId());
		assertThat(allocationOf(planning).getM_ShipperTransportation_ID()).isEqualTo(target.getRepoId());
		assertNoOrphanedShippingPackages();
	}

	@Test
	@DisplayName("remove-from succeeds for a CLOSED planning - the one deliberate exception to the closed guard")
	void removeFromSucceedsForAClosedPlanning()
	{
		final ShipperTransportationId deliveryInstructionId = draftDeliveryInstruction("SHARED-5");
		final I_M_Delivery_Planning closedAndAllocated = deliveryPlanning();
		closedAndAllocated.setIsClosed(true);
		InterfaceWrapperHelper.save(closedAndAllocated);
		allocateTo(deliveryInstructionId, closedAndAllocated);
		final I_M_Delivery_Planning_Alloc allocBefore = allocationOf(closedAndAllocated);
		final int allocationId = allocBefore.getM_Delivery_Planning_Alloc_ID();
		final int packageId = allocBefore.getM_ShippingPackage_ID();

		deliveryPlanningService.removeFrom(selectionOf(closedAndAllocated));

		final I_M_Delivery_Planning removed = reload(closedAndAllocated);
		assertThat(removed.isClosed()).as("removing does not reopen it - closing is a separate decision").isTrue();
		assertThat(removed.getReleaseNo()).isNull();
		assertThat(removed.getM_ShipperTransportation_ID()).isLessThanOrEqualTo(0);
		assertThat(shippingPackageIsActive(packageId)).as("deactivated, not deleted").isFalse();
		assertThat(InterfaceWrapperHelper.load(allocationId, I_M_Delivery_Planning_Alloc.class).isActive())
				.as("the allocation row survives, deactivated")
				.isFalse();
		assertThat(allocationsInLineNoOrder()).hasSize(1);
		assertNoOrphanedShippingPackages();
	}

	@Test
	@DisplayName("a moved allocation continues the TARGET's LineNo, not the one it had on the source")
	void aMovedAllocationContinuesTheTargetsLineNo()
	{
		final ShipperTransportationId source = draftDeliveryInstruction("SOURCE-4");
		final ShipperTransportationId target = draftDeliveryInstruction("TARGET-4");

		// third on the source, so its source LineNo (30) is deliberately HIGHER than the target's next one (20):
		// a move that carried the old number over, or continued the source's sequence, would land on 30 or 40
		final I_M_Delivery_Planning moving = deliveryPlanning();
		allocateTo(source, deliveryPlanning(), deliveryPlanning(), moving);
		assertThat(allocationOf(moving).getLineNo()).isEqualTo(30);

		allocateTo(target, deliveryPlanning());

		deliveryPlanningService.addTo(selectionOf(moving), target);

		assertThat(allocationOf(moving).getLineNo())
				.as("the target had one line at 10, so the moved planning takes 20")
				.isEqualTo(20);
		assertThat(allocationOf(moving).getM_ShipperTransportation_ID()).isEqualTo(target.getRepoId());
		assertNoOrphanedShippingPackages();
	}

	@Test
	@DisplayName("void unlinks only the just-deactivated allocation's own package - an earlier removal's retired package keeps its order-line link")
	void voidDoesNotWipeAnEarlierRemovedPlanningsOrderLineLink()
	{
		final ShipperTransportationId deliveryInstructionId = draftDeliveryInstruction("SHARED-9");

		// P1: allocated, then removed from the still-DRAFT instruction. Removal deactivates rather than deletes, so
		// the retired package survives, still carrying this instruction's id AND its order-line link
		final I_M_Delivery_Planning removedEarlier = deliveryPlanning();
		final OrderLineId removedEarlierOrderLineId = OrderLineId.ofRepoId(540100);
		allocateToWithOrderLine(deliveryInstructionId, removedEarlierOrderLineId, removedEarlier);
		final int removedEarlierPackageId = allocationOf(removedEarlier).getM_ShippingPackage_ID();

		deliveryPlanningService.removeFrom(selectionOf(removedEarlier));
		assertThat(shippingPackageIsActive(removedEarlierPackageId)).isFalse();
		assertThat(InterfaceWrapperHelper.load(removedEarlierPackageId, I_M_ShippingPackage.class).getC_OrderLine_ID())
				.as("removal deactivates the package - it must not touch the order-line link")
				.isEqualTo(removedEarlierOrderLineId.getRepoId());

		// P2: allocated to the SAME instruction afterwards
		final I_M_Delivery_Planning laterAllocated = deliveryPlanning();
		final OrderLineId laterOrderLineId = OrderLineId.ofRepoId(540101);
		allocateToWithOrderLine(deliveryInstructionId, laterOrderLineId, laterAllocated);
		final int laterPackageId = allocationOf(laterAllocated).getM_ShippingPackage_ID();

		// the instruction is voided - unlinkDeliveryPlannings runs, exactly what the TIMING_AFTER_VOID
		// interceptor triggers
		deliveryPlanningService.unlinkDeliveryPlannings(deliveryInstructionId);

		assertThat(InterfaceWrapperHelper.load(laterPackageId, I_M_ShippingPackage.class).getC_OrderLine_ID())
				.as("the voided instruction's own, just-deactivated package loses its order-line link")
				.isLessThanOrEqualTo(0);
		assertThat(InterfaceWrapperHelper.load(removedEarlierPackageId, I_M_ShippingPackage.class).getC_OrderLine_ID())
				.as("P1 has nothing to do with this void - its retired package's order-line link must survive")
				.isEqualTo(removedEarlierOrderLineId.getRepoId());
	}
}
