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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.assertj.core.groups.Tuple.tuple;

/**
 * The allocation's write lifecycle: what {@code createAllocations}, {@code deactivateAllocations} and
 * {@code deleteAllocations} leave behind.
 * <p>
 * The two partial unique indexes that make the deactivate-versus-delete distinction matter are a DB
 * guarantee and are therefore not exercised here; what is pinned here is that the repository writes the
 * state those indexes key on - {@code IsActive} - the way each event requires.
 */
class DeliveryPlanningAllocLifecycleTest
{
	private static final int SHIPPER_BPARTNER_ID = 540001;
	private static final int SHIPPER_LOCATION_ID = 540002;
	private static final int SHIPPER_ID = 540003;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

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

	private List<I_M_Delivery_Planning_Alloc> allAllocations()
	{
		return queryBL.createQueryBuilder(I_M_Delivery_Planning_Alloc.class)
				.orderBy().addColumnAscending(I_M_Delivery_Planning_Alloc.COLUMNNAME_LineNo).endOrderBy()
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
	@DisplayName("LineNo is assigned in tens in the order the requests are handed over, starting at 10")
	void lineNoIsAssignedInTensInTheGivenOrder()
	{
		final ShipperTransportationId deliveryInstructionId = createDeliveryInstruction(DocStatus.Drafted, false);
		final DeliveryPlanningId first = createDeliveryPlanning();
		final DeliveryPlanningId second = createDeliveryPlanning();
		final DeliveryPlanningId third = createDeliveryPlanning();

		// deliberately not in id order, so the assertion shows the given order wins over the encounter order
		deliveryPlanningRepository.createAllocations(deliveryInstructionId, ImmutableList.of(
				allocRequestFor(third), allocRequestFor(first), allocRequestFor(second)));

		assertThat(allAllocations())
				.extracting(I_M_Delivery_Planning_Alloc::getLineNo, I_M_Delivery_Planning_Alloc::getM_Delivery_Planning_ID)
				.containsExactly(
						tuple(10, third.getRepoId()),
						tuple(20, first.getRepoId()),
						tuple(30, second.getRepoId()));
	}

	@Test
	@DisplayName("a later allocation continues after the instruction's highest LineNo instead of restarting at 10")
	void lineNoContinuesOnASecondCall()
	{
		final ShipperTransportationId deliveryInstructionId = createDeliveryInstruction(DocStatus.Drafted, false);
		deliveryPlanningRepository.createAllocations(deliveryInstructionId, ImmutableList.of(allocRequestFor(createDeliveryPlanning())));

		deliveryPlanningRepository.createAllocations(deliveryInstructionId, ImmutableList.of(allocRequestFor(createDeliveryPlanning())));

		assertThat(allAllocations()).extracting(I_M_Delivery_Planning_Alloc::getLineNo).containsExactly(10, 20);
	}

	@Test
	@DisplayName("void deactivates the allocation and its package, and mirrors the instruction's DocStatus")
	void deactivateFlipsIsActiveOnBothAndMirrorsDocStatus()
	{
		final ShipperTransportationId deliveryInstructionId = createDeliveryInstruction(DocStatus.Drafted, false);
		deliveryPlanningRepository.createAllocations(deliveryInstructionId, ImmutableList.of(allocRequestFor(createDeliveryPlanning())));
		final I_M_Delivery_Planning_Alloc allocBefore = allAllocations().get(0);
		final int shippingPackageId = allocBefore.getM_ShippingPackage_ID();

		// the document engine has already stamped the void by the time the after-void hook runs
		final I_M_ShipperTransportation deliveryInstruction = InterfaceWrapperHelper.load(deliveryInstructionId, I_M_ShipperTransportation.class);
		deliveryInstruction.setDocStatus(DocStatus.Voided.getCode());
		InterfaceWrapperHelper.save(deliveryInstruction);

		deliveryPlanningRepository.deactivateAllocations(deliveryInstructionId);

		final I_M_Delivery_Planning_Alloc allocAfter = reload(allocBefore);
		assertThat(allocAfter.isActive()).isFalse();
		assertThat(allocAfter.isProcessed()).isTrue();
		assertThat(allocAfter.getDocStatus()).isEqualTo(DocStatus.Voided.getCode());
		assertThat(InterfaceWrapperHelper.load(shippingPackageId, I_M_ShippingPackage.class).isActive()).isFalse();
	}

	@Test
	@DisplayName("a deactivated allocation is left alone by a later deactivate of the same instruction")
	void deactivateSkipsTheAlreadyDeactivatedOnes()
	{
		final ShipperTransportationId deliveryInstructionId = createDeliveryInstruction(DocStatus.Drafted, false);
		deliveryPlanningRepository.createAllocations(deliveryInstructionId, ImmutableList.of(allocRequestFor(createDeliveryPlanning())));
		deliveryPlanningRepository.deactivateAllocations(deliveryInstructionId);

		deliveryPlanningRepository.deactivateAllocations(deliveryInstructionId);

		assertThat(allAllocations()).hasSize(1);
		assertThat(allAllocations().get(0).isActive()).isFalse();
	}

	@Test
	@DisplayName("remove deletes the allocation together with its shipping package")
	void deleteRemovesAllocationAndPackage()
	{
		final ShipperTransportationId deliveryInstructionId = createDeliveryInstruction(DocStatus.Drafted, false);
		final DeliveryPlanningId removed = createDeliveryPlanning();
		final DeliveryPlanningId kept = createDeliveryPlanning();
		deliveryPlanningRepository.createAllocations(deliveryInstructionId, ImmutableList.of(allocRequestFor(removed), allocRequestFor(kept)));
		final int removedPackageId = allAllocations().get(0).getM_ShippingPackage_ID();

		deliveryPlanningRepository.deleteAllocations(ImmutableList.of(removed));

		assertThat(allAllocations())
				.extracting(I_M_Delivery_Planning_Alloc::getM_Delivery_Planning_ID)
				.containsExactly(kept.getRepoId());
		assertThat(queryBL.createQueryBuilder(I_M_ShippingPackage.class)
				.addEqualsFilter(I_M_ShippingPackage.COLUMNNAME_M_ShippingPackage_ID, removedPackageId)
				.create()
				.anyMatch()).isFalse();
	}

	@Test
	@DisplayName("remove leaves a deactivated allocation of the same planning standing - a void is not what is being undone")
	void deleteDoesNotTouchDeactivatedAllocations()
	{
		final DeliveryPlanningId deliveryPlanningId = createDeliveryPlanning();
		final ShipperTransportationId voidedInstructionId = createDeliveryInstruction(DocStatus.Drafted, false);
		deliveryPlanningRepository.createAllocations(voidedInstructionId, ImmutableList.of(allocRequestFor(deliveryPlanningId)));
		deliveryPlanningRepository.deactivateAllocations(voidedInstructionId);

		deliveryPlanningRepository.deleteAllocations(ImmutableList.of(deliveryPlanningId));

		assertThat(allAllocations()).hasSize(1);
		assertThat(allAllocations().get(0).isActive()).isFalse();
	}

	@Test
	@DisplayName("a move deletes the source allocation and its package, and creates a fresh pair on the target")
	void moveReplacesTheAllocationAndItsPackage()
	{
		final ShipperTransportationId source = createDeliveryInstruction(DocStatus.Drafted, false);
		final ShipperTransportationId target = createDeliveryInstruction(DocStatus.Drafted, false);
		final DeliveryPlanningId moving = createDeliveryPlanning();
		final DeliveryPlanningId staying = createDeliveryPlanning();
		deliveryPlanningRepository.createAllocations(source, ImmutableList.of(allocRequestFor(moving), allocRequestFor(staying)));
		final int sourcePackageId = allAllocations().get(0).getM_ShippingPackage_ID();

		// exactly what addTo does per planning, and in that order: the source allocation is DELETED, so the
		// target's insert finds no active row on either partial unique index
		deliveryPlanningRepository.deleteAllocations(ImmutableList.of(moving));
		deliveryPlanningRepository.createAllocations(target, ImmutableList.of(allocRequestFor(moving)));

		assertThat(allAllocations())
				.as("one allocation per planning, the moved one now on the target")
				.extracting(
						I_M_Delivery_Planning_Alloc::getM_Delivery_Planning_ID,
						I_M_Delivery_Planning_Alloc::getM_ShipperTransportation_ID,
						I_M_Delivery_Planning_Alloc::isActive)
				.containsExactlyInAnyOrder(
						tuple(staying.getRepoId(), source.getRepoId(), true),
						tuple(moving.getRepoId(), target.getRepoId(), true));

		assertThat(queryBL.createQueryBuilder(I_M_ShippingPackage.class)
				.addEqualsFilter(I_M_ShippingPackage.COLUMNNAME_M_ShippingPackage_ID, sourcePackageId)
				.create()
				.anyMatch())
				.as("nothing survives to say the cargo was ever on the source document")
				.isFalse();
	}

	@Test
	@DisplayName("a moved allocation continues the TARGET's LineNo, not the source's")
	void moveContinuesTheTargetsLineNo()
	{
		final ShipperTransportationId source = createDeliveryInstruction(DocStatus.Drafted, false);
		final ShipperTransportationId target = createDeliveryInstruction(DocStatus.Drafted, false);
		// three on the source, so the moved one's source LineNo (30) is higher than the target's next (20)
		final DeliveryPlanningId moving = createDeliveryPlanning();
		deliveryPlanningRepository.createAllocations(source, ImmutableList.of(
				allocRequestFor(createDeliveryPlanning()), allocRequestFor(createDeliveryPlanning()), allocRequestFor(moving)));
		deliveryPlanningRepository.createAllocations(target, ImmutableList.of(allocRequestFor(createDeliveryPlanning())));

		deliveryPlanningRepository.deleteAllocations(ImmutableList.of(moving));
		deliveryPlanningRepository.createAllocations(target, ImmutableList.of(allocRequestFor(moving)));

		assertThat(allAllocations().stream()
				.filter(alloc -> alloc.getM_Delivery_Planning_ID() == moving.getRepoId())
				.findFirst()
				.orElseThrow(AssertionError::new)
				.getLineNo())
				.isEqualTo(20);
	}

	@Test
	@DisplayName("getAllocatedInstructionIds reports only plannings with an ACTIVE allocation")
	void allocatedInstructionIdsReportsOnlyActiveOnes()
	{
		final ShipperTransportationId deliveryInstructionId = createDeliveryInstruction(DocStatus.Drafted, false);
		final DeliveryPlanningId allocated = createDeliveryPlanning();
		final DeliveryPlanningId unallocated = createDeliveryPlanning();
		final DeliveryPlanningId deactivated = createDeliveryPlanning();
		deliveryPlanningRepository.createAllocations(deliveryInstructionId, ImmutableList.of(allocRequestFor(allocated)));

		final ShipperTransportationId voidedInstructionId = createDeliveryInstruction(DocStatus.Drafted, false);
		deliveryPlanningRepository.createAllocations(voidedInstructionId, ImmutableList.of(allocRequestFor(deactivated)));
		deliveryPlanningRepository.deactivateAllocations(voidedInstructionId);

		assertThat(deliveryPlanningRepository.getAllocatedInstructionIds(ImmutableList.of(allocated, unallocated, deactivated)))
				.containsExactly(entry(allocated, deliveryInstructionId));
	}
}
