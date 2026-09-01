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
import com.google.common.collect.ImmutableListMultimap;
import de.metas.common.util.time.SystemTime;
import de.metas.document.dimension.DimensionService;
import de.metas.document.engine.DocStatus;
import de.metas.order.OrderId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.model.I_M_Delivery_Planning_Alloc;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.assertj.core.groups.Tuple.tuple;

/**
 * The allocation's write lifecycle: what {@code createAllocations} and the two {@code deactivateAllocations}
 * overloads (by instruction, and by planning ids) leave behind.
 */
class DeliveryPlanningAllocLifecycleTest
{
	private static final int SHIPPER_BPARTNER_ID = 540001;
	private static final int SHIPPER_LOCATION_ID = 540002;
	private static final int SHIPPER_ID = 540003;
	private static final ZonedDateTime REMOVED_AT = ZonedDateTime.parse("2026-08-27T10:15:30+02:00[Europe/Berlin]");

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private DeliveryPlanningRepository deliveryPlanningRepository;
	private I_C_UOM uom;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		deliveryPlanningRepository = new DeliveryPlanningRepository(Mockito.mock(DimensionService.class));

		uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		InterfaceWrapperHelper.save(uom);
	}

	@AfterEach
	void tearDown()
	{
		SystemTime.resetTimeSource();
	}

	// ------------------------------------------------------------------ helpers

	private ShipperTransportationId createDeliveryInstruction(@NonNull final DocStatus docStatus, final boolean processed)
	{
		final I_M_ShipperTransportation record = InterfaceWrapperHelper.newInstance(I_M_ShipperTransportation.class);
		record.setShipper_BPartner_ID(SHIPPER_BPARTNER_ID);
		record.setShipper_Location_ID(SHIPPER_LOCATION_ID);
		record.setM_Shipper_ID(SHIPPER_ID);
		record.setDocStatus(docStatus.getCode());
		record.setProcessed(processed);
		InterfaceWrapperHelper.save(record);
		return ShipperTransportationId.ofRepoId(record.getM_ShipperTransportation_ID());
	}

	private DeliveryPlanningId createDeliveryPlanning()
	{
		final I_M_Delivery_Planning record = InterfaceWrapperHelper.newInstance(I_M_Delivery_Planning.class);
		InterfaceWrapperHelper.save(record);
		return DeliveryPlanningId.ofRepoId(record.getM_Delivery_Planning_ID());
	}

	private DeliveryPlanningAllocCreateRequest allocRequestFor(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		return DeliveryPlanningAllocCreateRequest.builder()
				.deliveryPlanningId(deliveryPlanningId)
				.productId(ProductId.ofRepoId(540010))
				.qtyLoaded(Quantity.of(BigDecimal.TEN, uom))
				.qtyDischarged(Quantity.of(BigDecimal.ONE, uom))
				.build();
	}

	/**
	 * In CREATION order: the allocations of one call are saved one after the other, so their ids follow the
	 * order the requests were handed over in - which is what the order assertions read.
	 */
	private List<I_M_Delivery_Planning_Alloc> allAllocations()
	{
		return queryBL.createQueryBuilder(I_M_Delivery_Planning_Alloc.class)
				.orderBy().addColumnAscending(I_M_Delivery_Planning_Alloc.COLUMNNAME_M_Delivery_Planning_Alloc_ID).endOrderBy()
				.create()
				.list();
	}

	private static I_M_Delivery_Planning_Alloc reload(@NonNull final I_M_Delivery_Planning_Alloc record)
	{
		return InterfaceWrapperHelper.load(record.getM_Delivery_Planning_Alloc_ID(), I_M_Delivery_Planning_Alloc.class);
	}

	// ------------------------------------------------------------------ tests

	@Test
	@DisplayName("each allocation gets its own shipping package, and the package exists before the allocation points at it")
	void createGivesEachAllocationItsOwnPackage()
	{
		final ShipperTransportationId deliveryInstructionId = createDeliveryInstruction(DocStatus.Drafted, false);

		deliveryPlanningRepository.createAllocations(deliveryInstructionId, ImmutableList.of(
				allocRequestFor(createDeliveryPlanning()),
				allocRequestFor(createDeliveryPlanning())));

		final List<I_M_Delivery_Planning_Alloc> allocations = allAllocations();
		assertThat(allocations).hasSize(2);
		assertThat(allocations).allSatisfy(alloc -> assertThat(alloc.getM_ShippingPackage_ID()).isGreaterThan(0));
		assertThat(allocations.stream().map(I_M_Delivery_Planning_Alloc::getM_ShippingPackage_ID)).doesNotHaveDuplicates();
	}

	@Test
	@DisplayName("the request's orderId lands on the shipping package created for it - unset when the request carries none")
	void createStampsTheRequestsOrderIdOntoItsShippingPackage()
	{
		final ShipperTransportationId deliveryInstructionId = createDeliveryInstruction(DocStatus.Drafted, false);
		final OrderId orderId = OrderId.ofRepoId(540099);

		final DeliveryPlanningId withOrderPlanningId = createDeliveryPlanning();
		final DeliveryPlanningId withoutOrderPlanningId = createDeliveryPlanning();

		deliveryPlanningRepository.createAllocations(deliveryInstructionId, ImmutableList.of(
				DeliveryPlanningAllocCreateRequest.builder()
						.deliveryPlanningId(withOrderPlanningId)
						.productId(ProductId.ofRepoId(540010))
						.qtyLoaded(Quantity.of(BigDecimal.TEN, uom))
						.qtyDischarged(Quantity.of(BigDecimal.ONE, uom))
						.orderId(orderId)
						.build(),
				allocRequestFor(withoutOrderPlanningId)));

		final I_M_Delivery_Planning_Alloc withOrderAlloc = allAllocations().stream()
				.filter(alloc -> alloc.getM_Delivery_Planning_ID() == withOrderPlanningId.getRepoId())
				.findFirst().orElseThrow(() -> new AssertionError("no allocation for withOrderPlanningId"));
		final I_M_Delivery_Planning_Alloc withoutOrderAlloc = allAllocations().stream()
				.filter(alloc -> alloc.getM_Delivery_Planning_ID() == withoutOrderPlanningId.getRepoId())
				.findFirst().orElseThrow(() -> new AssertionError("no allocation for withoutOrderPlanningId"));

		assertThat(InterfaceWrapperHelper.load(withOrderAlloc.getM_ShippingPackage_ID(), I_M_ShippingPackage.class).getC_Order_ID())
				.as("the request's orderId must land on the package it created")
				.isEqualTo(orderId.getRepoId());
		assertThat(InterfaceWrapperHelper.load(withoutOrderAlloc.getM_ShippingPackage_ID(), I_M_ShippingPackage.class).getC_Order_ID())
				.as("a request carrying no orderId must not fabricate one on the package")
				.isLessThanOrEqualTo(0);
	}

	@Test
	@DisplayName("the allocations are created in the order the requests are handed over")
	void allocationsAreCreatedInTheGivenOrder()
	{
		final ShipperTransportationId deliveryInstructionId = createDeliveryInstruction(DocStatus.Drafted, false);
		final DeliveryPlanningId first = createDeliveryPlanning();
		final DeliveryPlanningId second = createDeliveryPlanning();
		final DeliveryPlanningId third = createDeliveryPlanning();

		// not in id order, so the assertion shows the given order wins over the encounter order
		deliveryPlanningRepository.createAllocations(deliveryInstructionId, ImmutableList.of(
				allocRequestFor(third), allocRequestFor(first), allocRequestFor(second)));

		assertThat(allAllocations())
				.extracting(I_M_Delivery_Planning_Alloc::getM_Delivery_Planning_ID)
				.containsExactly(third.getRepoId(), first.getRepoId(), second.getRepoId());
	}

	@Test
	@DisplayName("void deactivates both the allocation and its package")
	void deactivateFlipsIsActiveOnBoth()
	{
		final ShipperTransportationId deliveryInstructionId = createDeliveryInstruction(DocStatus.Drafted, false);
		deliveryPlanningRepository.createAllocations(deliveryInstructionId, ImmutableList.of(allocRequestFor(createDeliveryPlanning())));
		final I_M_Delivery_Planning_Alloc allocBefore = allAllocations().get(0);
		final int shippingPackageId = allocBefore.getM_ShippingPackage_ID();

		// the document engine has already stamped the void by the time the after-void hook runs
		final I_M_ShipperTransportation deliveryInstruction = InterfaceWrapperHelper.load(deliveryInstructionId, I_M_ShipperTransportation.class);
		deliveryInstruction.setDocStatus(DocStatus.Voided.getCode());
		InterfaceWrapperHelper.save(deliveryInstruction);

		deliveryPlanningRepository.deactivateAllocations(deliveryInstructionId, REMOVED_AT.toInstant());

		final I_M_Delivery_Planning_Alloc allocAfter = reload(allocBefore);
		assertThat(allocAfter.isActive()).isFalse();
		assertThat(InterfaceWrapperHelper.load(shippingPackageId, I_M_ShippingPackage.class).isActive()).isFalse();
	}

	@Test
	@DisplayName("remove deactivates the allocation together with its shipping package - neither is deleted")
	void deactivateByPlanningIdsDeactivatesAllocationAndPackage()
	{
		final ShipperTransportationId deliveryInstructionId = createDeliveryInstruction(DocStatus.Drafted, false);
		final DeliveryPlanningId removed = createDeliveryPlanning();
		final DeliveryPlanningId kept = createDeliveryPlanning();
		deliveryPlanningRepository.createAllocations(deliveryInstructionId, ImmutableList.of(allocRequestFor(removed), allocRequestFor(kept)));
		final int removedPackageId = allAllocations().get(0).getM_ShippingPackage_ID();

		deliveryPlanningRepository.deactivateAllocations(ImmutableList.of(removed), REMOVED_AT.toInstant());

		assertThat(allAllocations())
				.as("both rows survive - the removed one deactivated, the kept one untouched")
				.extracting(I_M_Delivery_Planning_Alloc::getM_Delivery_Planning_ID, I_M_Delivery_Planning_Alloc::isActive)
				.containsExactlyInAnyOrder(
						tuple(removed.getRepoId(), false),
						tuple(kept.getRepoId(), true));
		assertThat(InterfaceWrapperHelper.load(removedPackageId, I_M_ShippingPackage.class).isActive())
				.as("its shipping package is deactivated too, not deleted")
				.isFalse();
	}

	@Test
	@DisplayName("a move deactivates the source allocation and its package, and creates a fresh active pair on the target")
	void moveReplacesTheAllocationAndItsPackage()
	{
		final ShipperTransportationId source = createDeliveryInstruction(DocStatus.Drafted, false);
		final ShipperTransportationId target = createDeliveryInstruction(DocStatus.Drafted, false);
		final DeliveryPlanningId moving = createDeliveryPlanning();
		final DeliveryPlanningId staying = createDeliveryPlanning();
		deliveryPlanningRepository.createAllocations(source, ImmutableList.of(allocRequestFor(moving), allocRequestFor(staying)));
		final int sourcePackageId = allAllocations().get(0).getM_ShippingPackage_ID();

		// exactly what addTo does per planning, and in that order: the source allocation is DEACTIVATED, so the
		// target's insert finds no ACTIVE row on either partial unique index
		deliveryPlanningRepository.deactivateAllocations(ImmutableList.of(moving), REMOVED_AT.toInstant());
		deliveryPlanningRepository.createAllocations(target, ImmutableList.of(allocRequestFor(moving)));

		assertThat(allAllocations())
				.as("the source row for the moved planning survives deactivated; the staying row and the new target row are active")
				.extracting(
						I_M_Delivery_Planning_Alloc::getM_Delivery_Planning_ID,
						I_M_Delivery_Planning_Alloc::getM_ShipperTransportation_ID,
						I_M_Delivery_Planning_Alloc::isActive)
				.containsExactlyInAnyOrder(
						tuple(moving.getRepoId(), source.getRepoId(), false),
						tuple(staying.getRepoId(), source.getRepoId(), true),
						tuple(moving.getRepoId(), target.getRepoId(), true));

		assertThat(InterfaceWrapperHelper.load(sourcePackageId, I_M_ShippingPackage.class).isActive())
				.as("the source document's shipping package survives, deactivated - not deleted")
				.isFalse();
	}

	@Test
	@DisplayName("a deactivated planning can be allocated again immediately - the partial unique indexes only key on IsActive='Y'")
	void deactivatedPlanningCanBeAllocatedAgainImmediately()
	{
		final ShipperTransportationId source = createDeliveryInstruction(DocStatus.Drafted, false);
		final DeliveryPlanningId planningId = createDeliveryPlanning();
		deliveryPlanningRepository.createAllocations(source, ImmutableList.of(allocRequestFor(planningId)));

		deliveryPlanningRepository.deactivateAllocations(ImmutableList.of(planningId), REMOVED_AT.toInstant());

		final ShipperTransportationId target = createDeliveryInstruction(DocStatus.Drafted, false);
		final ImmutableList<DeliveryPlanningAllocId> newAllocIds =
				deliveryPlanningRepository.createAllocations(target, ImmutableList.of(allocRequestFor(planningId)));

		assertThat(newAllocIds).hasSize(1);
		assertThat(deliveryPlanningRepository.getAllocationsByPlanningId(ImmutableList.of(planningId)).get(planningId))
				.as("the fresh allocation on the target is the only ACTIVE one reported for this planning")
				.extracting(DeliveryPlanningAlloc::getDeliveryInstructionId)
				.containsExactly(target);
	}

	@Test
	@DisplayName("getAllocationsByPlanningId reports only plannings with an ACTIVE allocation")
	void allocationsByPlanningIdReportsOnlyActiveOnes()
	{
		final ShipperTransportationId deliveryInstructionId = createDeliveryInstruction(DocStatus.Drafted, false);
		final DeliveryPlanningId allocated = createDeliveryPlanning();
		final DeliveryPlanningId unallocated = createDeliveryPlanning();
		final DeliveryPlanningId deactivated = createDeliveryPlanning();
		deliveryPlanningRepository.createAllocations(deliveryInstructionId, ImmutableList.of(allocRequestFor(allocated)));

		final ShipperTransportationId voidedInstructionId = createDeliveryInstruction(DocStatus.Drafted, false);
		deliveryPlanningRepository.createAllocations(voidedInstructionId, ImmutableList.of(allocRequestFor(deactivated)));
		deliveryPlanningRepository.deactivateAllocations(voidedInstructionId, REMOVED_AT.toInstant());

		final ImmutableListMultimap<DeliveryPlanningId, DeliveryPlanningAlloc> allocations =
				deliveryPlanningRepository.getAllocationsByPlanningId(ImmutableList.of(allocated, unallocated, deactivated));

		assertThat(allocations.keySet()).containsExactly(allocated);
		assertThat(allocations.get(allocated))
				.extracting(DeliveryPlanningAlloc::getDeliveryInstructionId)
				.containsExactly(deliveryInstructionId);
	}

	@Test
	@DisplayName("getAllocatedPlanningIds reports what an instruction holds NOW - not what it held before a void or a move")
	void allocatedPlanningIdsReportsOnlyActiveOnes()
	{
		final ShipperTransportationId deliveryInstructionId = createDeliveryInstruction(DocStatus.Drafted, false);
		final DeliveryPlanningId held = createDeliveryPlanning();
		final DeliveryPlanningId movedAway = createDeliveryPlanning();
		final DeliveryPlanningId deactivated = createDeliveryPlanning();
		deliveryPlanningRepository.createAllocations(deliveryInstructionId, ImmutableList.of(
				allocRequestFor(held), allocRequestFor(movedAway), allocRequestFor(deactivated)));

		// a move deactivates the allocation, a void deactivates it too - neither leaves the planning active on this document
		deliveryPlanningRepository.deactivateAllocations(ImmutableList.of(movedAway), REMOVED_AT.toInstant());
		final I_M_Delivery_Planning_Alloc deactivatedAlloc = allAllocations().stream()
				.filter(alloc -> alloc.getM_Delivery_Planning_ID() == deactivated.getRepoId())
				.findFirst()
				.orElseThrow(AssertionError::new);
		deactivatedAlloc.setIsActive(false);
		InterfaceWrapperHelper.save(deactivatedAlloc);

		assertThat(deliveryPlanningRepository.getAllocatedPlanningIds(deliveryInstructionId)).containsExactly(held);
	}

	@Test
	@DisplayName("DateRemoved is stamped when the allocation is deactivated, and is NOT moved by a later unrelated write")
	void dateRemovedIsStampedOnceAndSurvivesLaterWrites()
	{
		final ShipperTransportationId deliveryInstructionId = createDeliveryInstruction(DocStatus.Drafted, false);
		final DeliveryPlanningId deliveryPlanningId = createDeliveryPlanning();
		deliveryPlanningRepository.createAllocations(deliveryInstructionId, ImmutableList.of(allocRequestFor(deliveryPlanningId)));

		final I_M_Delivery_Planning_Alloc alloc = allAllocations().get(0);
		assertThat(alloc.getDateRemoved())
				.as("an allocation that is still on the instruction has not been removed from it")
				.isNull();

		// the clock is pinned and later ADVANCED on purpose for the SECOND half of this test: the unrelated
		// write below moves Updated from the ambient clock, and against a real clock a re-stamp landing in the
		// same millisecond would satisfy the "did not move" assertion while the bug was fully present.
		SystemTime.setFixedTimeSource(REMOVED_AT);
		deliveryPlanningRepository.deactivateAllocations(ImmutableList.of(deliveryPlanningId), REMOVED_AT.toInstant());

		final Timestamp stampedAt = reload(alloc).getDateRemoved();
		assertThat(stampedAt)
				.as("deactivation is the business event this column records")
				.isEqualTo(Timestamp.from(REMOVED_AT.toInstant()));

		// the whole point of the column: any later touch of the row moves Updated, and must NOT move DateRemoved
		SystemTime.setFixedTimeSource(REMOVED_AT.plusDays(1));
		final I_M_Delivery_Planning_Alloc retired = reload(alloc);
		retired.setAD_Org_ID(retired.getAD_Org_ID() + 1);
		InterfaceWrapperHelper.save(retired);

		final I_M_Delivery_Planning_Alloc afterUnrelatedWrite = reload(alloc);
		assertThat(afterUnrelatedWrite.getAD_Org_ID())
				.as("guard on the guard: the unrelated write must really have hit the row")
				.isEqualTo(retired.getAD_Org_ID());
		assertThat(afterUnrelatedWrite.getDateRemoved())
				.as("an unrelated write a day later must not re-date the removal")
				.isEqualTo(stampedAt);
	}

	@Test
	@DisplayName("a void stamps DateRemoved on every allocation it retires, and re-voiding does not re-date them")
	void dateRemovedIsStampedByVoidAndNotRewrittenByASecondVoid()
	{
		final ShipperTransportationId deliveryInstructionId = createDeliveryInstruction(DocStatus.Drafted, false);
		deliveryPlanningRepository.createAllocations(deliveryInstructionId, ImmutableList.of(
				allocRequestFor(createDeliveryPlanning()), allocRequestFor(createDeliveryPlanning())));

		deliveryPlanningRepository.deactivateAllocations(deliveryInstructionId, REMOVED_AT.toInstant());
		final List<Timestamp> stampedAt = allAllocations().stream().map(I_M_Delivery_Planning_Alloc::getDateRemoved).collect(ImmutableList.toImmutableList());
		assertThat(stampedAt).allSatisfy(dateRemoved -> assertThat(dateRemoved).isNotNull());

		// the second void finds nothing ACTIVE to retire, so it must leave the first void's dates alone
		deliveryPlanningRepository.deactivateAllocations(deliveryInstructionId, REMOVED_AT.toInstant());

		assertThat(allAllocations()).extracting(I_M_Delivery_Planning_Alloc::getDateRemoved).containsExactlyElementsOf(stampedAt);
	}
}
