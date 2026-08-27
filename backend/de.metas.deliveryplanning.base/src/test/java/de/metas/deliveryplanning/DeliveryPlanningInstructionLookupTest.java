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
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Delivery_Planning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * How a planning is resolved to the delivery instruction(s) it is on, now that the link is the
 * {@code M_Delivery_Planning_Alloc} rows rather than a header FK on {@code M_ShipperTransportation}.
 * <p>
 * The point of the change under test: a single instruction may carry several plannings, which the old
 * header FK could not express (an instruction has exactly one {@code M_Delivery_Planning_ID}). The
 * aggregation case below is what proves the allocation-based resolution actually supports that.
 */
class DeliveryPlanningInstructionLookupTest
{
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

	@Test
	@DisplayName("hasCompleteDeliveryInstruction is true when the planning's active allocation points at a completed instruction")
	void hasCompleteDeliveryInstruction_completedInstruction()
	{
		final ShipperTransportationId instructionId = createDeliveryInstruction(DocStatus.Completed, true);
		final DeliveryPlanningId planningId = createDeliveryPlanning();
		deliveryPlanningRepository.createAllocations(instructionId, ImmutableList.of(allocRequestFor(planningId)));

		assertThat(deliveryPlanningRepository.hasCompleteDeliveryInstruction(planningId)).isTrue();
	}

	@Test
	@DisplayName("hasCompleteDeliveryInstruction is false when the planning's allocated instruction is only drafted")
	void hasCompleteDeliveryInstruction_draftedInstructionOnly()
	{
		final ShipperTransportationId instructionId = createDeliveryInstruction(DocStatus.Drafted, false);
		final DeliveryPlanningId planningId = createDeliveryPlanning();
		deliveryPlanningRepository.createAllocations(instructionId, ImmutableList.of(allocRequestFor(planningId)));

		assertThat(deliveryPlanningRepository.hasCompleteDeliveryInstruction(planningId)).isFalse();
	}

	@Test
	@DisplayName("hasCompleteDeliveryInstruction is false for a planning that has no allocation at all")
	void hasCompleteDeliveryInstruction_noAllocation()
	{
		final DeliveryPlanningId planningId = createDeliveryPlanning();

		assertThat(deliveryPlanningRepository.hasCompleteDeliveryInstruction(planningId)).isFalse();
	}

	@Test
	@DisplayName("hasCompleteDeliveryInstruction is false when the only allocation onto the completed instruction is inactive")
	void hasCompleteDeliveryInstruction_inactiveAllocation()
	{
		final ShipperTransportationId instructionId = createDeliveryInstruction(DocStatus.Completed, true);
		final DeliveryPlanningId planningId = createDeliveryPlanning();
		deliveryPlanningRepository.createAllocations(instructionId, ImmutableList.of(allocRequestFor(planningId)));
		deliveryPlanningRepository.deactivateAllocations(instructionId);

		assertThat(deliveryPlanningRepository.hasCompleteDeliveryInstruction(planningId)).isFalse();
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
