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
import de.metas.shipping.model.ShipperTransportationId;
import java.time.Instant;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Delivery_Planning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * How a planning is resolved to the delivery instruction(s) it is on, via its {@code M_Delivery_Planning_Alloc}
 * rows: one instruction may carry several plannings, which the aggregation case below pins.
 */
class DeliveryPlanningInstructionLookupTest
{
	private static final Instant REMOVED_AT = Instant.parse("2026-08-31T10:00:00Z");

	private static final int SHIPPER_BPARTNER_ID = 540001;
	private static final int SHIPPER_LOCATION_ID = 540002;
	private static final int SHIPPER_ID = 540003;

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

	private static List<I_M_ShipperTransportation> toList(@NonNull final Iterator<I_M_ShipperTransportation> iterator)
	{
		final ImmutableList.Builder<I_M_ShipperTransportation> builder = ImmutableList.builder();
		iterator.forEachRemaining(builder::add);
		return builder.build();
	}

	// ------------------------------------------------------------------ tests

	@Nested
	@DisplayName("hasCompleteDeliveryInstruction")
	class HasCompleteDeliveryInstruction
	{
		@Test
		@DisplayName("is true when the planning's active allocation points at a completed instruction")
		void completedInstruction()
		{
			final ShipperTransportationId instructionId = createDeliveryInstruction(DocStatus.Completed, true);
			final DeliveryPlanningId planningId = createDeliveryPlanning();
			deliveryPlanningRepository.createAllocations(instructionId, ImmutableList.of(allocRequestFor(planningId)));

			assertThat(deliveryPlanningRepository.hasCompleteDeliveryInstruction(planningId)).isTrue();
		}

		@Test
		@DisplayName("is false when the planning's allocated instruction is only drafted")
		void draftedInstructionOnly()
		{
			final ShipperTransportationId instructionId = createDeliveryInstruction(DocStatus.Drafted, false);
			final DeliveryPlanningId planningId = createDeliveryPlanning();
			deliveryPlanningRepository.createAllocations(instructionId, ImmutableList.of(allocRequestFor(planningId)));

			assertThat(deliveryPlanningRepository.hasCompleteDeliveryInstruction(planningId)).isFalse();
		}

		@Test
		@DisplayName("is false when the only allocation onto the completed instruction is inactive")
		void inactiveAllocation()
		{
			final ShipperTransportationId instructionId = createDeliveryInstruction(DocStatus.Completed, true);
			final DeliveryPlanningId planningId = createDeliveryPlanning();
			deliveryPlanningRepository.createAllocations(instructionId, ImmutableList.of(allocRequestFor(planningId)));
			deliveryPlanningRepository.deactivateAllocations(instructionId, REMOVED_AT);

			assertThat(deliveryPlanningRepository.hasCompleteDeliveryInstruction(planningId)).isFalse();
		}
	}

	@Test
	@DisplayName("an unallocated planning resolves to NO instruction even while another instruction exists - the empty-id-list filter must not degenerate to match-all")
	void unallocatedPlanning_seesNoForeignInstruction()
	{
		// both lookups now build their filter from the planning's allocated instruction ids. For an unallocated
		// planning that list is empty, and an in-array filter over an empty list must match nothing - not everything.
		// Without a second, foreign instruction in the database this cannot fail, which is why one is created here.
		final ShipperTransportationId foreignInstructionId = createDeliveryInstruction(DocStatus.Completed, true);
		final DeliveryPlanningId allocatedPlanningId = createDeliveryPlanning();
		deliveryPlanningRepository.createAllocations(foreignInstructionId, ImmutableList.of(allocRequestFor(allocatedPlanningId)));

		final DeliveryPlanningId unallocatedPlanningId = createDeliveryPlanning();

		assertThat(toList(deliveryPlanningRepository.retrieveForDeliveryPlanning(unallocatedPlanningId))).isEmpty();
		assertThat(deliveryPlanningRepository.hasCompleteDeliveryInstruction(unallocatedPlanningId)).isFalse();
	}

	@Test
	@DisplayName("an instruction carrying two plannings reports complete, and resolves, for BOTH - the aggregation the old header FK could not express")
	void twoPlanningsOnOneCompletedInstruction()
	{
		final ShipperTransportationId instructionId = createDeliveryInstruction(DocStatus.Completed, true);
		final DeliveryPlanningId first = createDeliveryPlanning();
		final DeliveryPlanningId second = createDeliveryPlanning();
		deliveryPlanningRepository.createAllocations(instructionId, ImmutableList.of(allocRequestFor(first), allocRequestFor(second)));

		assertThat(deliveryPlanningRepository.hasCompleteDeliveryInstruction(first)).isTrue();
		assertThat(deliveryPlanningRepository.hasCompleteDeliveryInstruction(second)).isTrue();

		assertThat(toList(deliveryPlanningRepository.retrieveForDeliveryPlanning(first)))
				.extracting(I_M_ShipperTransportation::getM_ShipperTransportation_ID)
				.containsExactly(instructionId.getRepoId());
		assertThat(toList(deliveryPlanningRepository.retrieveForDeliveryPlanning(second)))
				.extracting(I_M_ShipperTransportation::getM_ShipperTransportation_ID)
				.containsExactly(instructionId.getRepoId());
	}
}
