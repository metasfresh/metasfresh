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
import de.metas.i18n.ITranslatableString;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.shipping.ShipperRepository;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Delivery_Planning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * What completing a delivery instruction costs, and what it refuses.
 * <p>
 * Both rules read the instruction's allocated plannings, so both are exercised here against a REAL
 * {@link DeliveryPlanningRepository} SPIED on the unit-test in-memory store, the same setup as
 * {@link DeliveryPlanningBatchLoadingTest} - so the batch-versus-per-row call pattern stays countable
 * while the loads actually happen.
 * <p>
 * A transport order shares {@code M_ShipperTransportation} with a delivery instruction and must be
 * unaffected by both rules, so it is exercised here the same way any delivery instruction with zero
 * allocations is: the repository never even reaches for the batch load, which
 * {@code Mockito.verify(..., never())} proves rather than merely assumes.
 */
class DeliveryPlanningCompletionCascadeTest
{
	private static final int SHIPPER_BPARTNER_ID = 540001;
	private static final int SHIPPER_LOCATION_ID = 540002;
	private static final int SHIPPER_ID = 540003;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private DeliveryPlanningRepository deliveryPlanningRepository;
	private DeliveryPlanningService deliveryPlanningService;
	private I_C_UOM uom;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		deliveryPlanningRepository = Mockito.spy(new DeliveryPlanningRepository(Mockito.mock(DimensionService.class)));
		deliveryPlanningService = new DeliveryPlanningService(
				Mockito.mock(ShipperRepository.class),
				deliveryPlanningRepository,
				Mockito.mock(DeliveryStatusColorPaletteService.class),
				Mockito.mock(DimensionService.class),
				Mockito.mock(MeansOfTransportationService.class));

		uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		InterfaceWrapperHelper.save(uom);
	}

	// ------------------------------------------------------------------ helpers

	private ShipperTransportationId createDeliveryInstruction()
	{
		final I_M_ShipperTransportation record = InterfaceWrapperHelper.newInstance(I_M_ShipperTransportation.class);
		record.setShipper_BPartner_ID(SHIPPER_BPARTNER_ID);
		record.setShipper_Location_ID(SHIPPER_LOCATION_ID);
		record.setM_Shipper_ID(SHIPPER_ID);
		record.setDocStatus(DocStatus.Drafted.getCode());
		InterfaceWrapperHelper.save(record);
		return ShipperTransportationId.ofRepoId(record.getM_ShipperTransportation_ID());
	}

	private DeliveryPlanningId createDeliveryPlanning(final boolean closed)
	{
		final I_M_Delivery_Planning record = InterfaceWrapperHelper.newInstance(I_M_Delivery_Planning.class);
		record.setIsClosed(closed);
		InterfaceWrapperHelper.save(record);
		return DeliveryPlanningId.ofRepoId(record.getM_Delivery_Planning_ID());
	}

	private void allocate(@NonNull final ShipperTransportationId deliveryInstructionId, @NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		deliveryPlanningRepository.createAllocations(deliveryInstructionId, ImmutableList.of(
				DeliveryPlanningAllocCreateRequest.builder()
						.deliveryPlanningId(deliveryPlanningId)
						.productId(ProductId.ofRepoId(540010))
						.qtyLoaded(Quantity.of(BigDecimal.TEN, uom))
						.qtyDischarged(Quantity.of(BigDecimal.ONE, uom))
						.build()));
	}

	// ------------------------------------------------------------------ getCompleteRejectionReason

	@Test
	@DisplayName("complete is accepted when none of the allocated plannings are closed")
	void completeIsAcceptedWhenNoneClosed()
	{
		final ShipperTransportationId deliveryInstructionId = createDeliveryInstruction();
		allocate(deliveryInstructionId, createDeliveryPlanning(false));
		allocate(deliveryInstructionId, createDeliveryPlanning(false));

		assertThat(deliveryPlanningService.getCompleteRejectionReason(deliveryInstructionId)).isEmpty();
	}

	@Test
	@DisplayName("complete is refused and names every closed allocated planning, not just the first one")
	void completeIsRefusedAndNamesTheClosedOnes()
	{
		final ShipperTransportationId deliveryInstructionId = createDeliveryInstruction();
		final DeliveryPlanningId open = createDeliveryPlanning(false);
		final DeliveryPlanningId closedOne = createDeliveryPlanning(true);
		final DeliveryPlanningId closedTwo = createDeliveryPlanning(true);
		allocate(deliveryInstructionId, open);
		allocate(deliveryInstructionId, closedOne);
		allocate(deliveryInstructionId, closedTwo);

		final Optional<ITranslatableString> reason = deliveryPlanningService.getCompleteRejectionReason(deliveryInstructionId);

		assertThat(reason).as("a rejection reason").isPresent();
		final String text = reason.get().translate("en_US");
		assertThat(text).contains(DeliveryPlanningService.MSG_M_Delivery_Planning_ClosedAllocatedPlannings.toAD_Message());
		assertThat(text).contains(String.valueOf(closedOne.getRepoId()));
		assertThat(text).contains(String.valueOf(closedTwo.getRepoId()));
		assertThat(text).doesNotContain(String.valueOf(open.getRepoId()));
	}

	@Test
	@DisplayName("complete-rejection is a no-op for an instruction with no allocations - never reads planning records")
	void completeRejectionReasonIsANoOpWhenUnallocated()
	{
		// stands in for BOTH cases a no-op must cover: a transport order (which never gets an
		// allocation) and a delivery instruction nothing has been combined onto yet share this exact code path
		final ShipperTransportationId deliveryInstructionId = createDeliveryInstruction();

		assertThat(deliveryPlanningService.getCompleteRejectionReason(deliveryInstructionId)).isEmpty();

		Mockito.verify(deliveryPlanningRepository, Mockito.never()).getByIds(Mockito.any());
	}

	// ------------------------------------------------------------------ invalidateInvoiceCandidatesFor(instruction)

	@Test
	@DisplayName("invalidating invoice candidates for a whole instruction reads its allocated plannings in ONE batch load")
	void invalidateInvoiceCandidatesBatchLoadsAllAllocations()
	{
		final ShipperTransportationId deliveryInstructionId = createDeliveryInstruction();
		allocate(deliveryInstructionId, createDeliveryPlanning(false));
		allocate(deliveryInstructionId, createDeliveryPlanning(false));
		allocate(deliveryInstructionId, createDeliveryPlanning(false));
		Mockito.clearInvocations(deliveryPlanningRepository);

		deliveryPlanningService.invalidateInvoiceCandidatesFor(deliveryInstructionId);

		// exactly ONE batch load for all three allocated plannings - not a per-planning loop, which is the
		// defect this test pins: updateDeliveryPlanning used to read only the legacy single M_Delivery_Planning_ID
		// header FK, so on an aggregated instruction every other allocated planning's invoice candidates were
		// silently never invalidated
		Mockito.verify(deliveryPlanningRepository, Mockito.times(1)).getByIds(Mockito.any());
		Mockito.verify(deliveryPlanningRepository, Mockito.never()).getById(Mockito.any());
	}

	@Test
	@DisplayName("invalidating invoice candidates for an instruction with no allocations is a no-op - never batch-loads")
	void invalidateInvoiceCandidatesIsANoOpWhenUnallocated()
	{
		final ShipperTransportationId deliveryInstructionId = createDeliveryInstruction();
		Mockito.clearInvocations(deliveryPlanningRepository);

		deliveryPlanningService.invalidateInvoiceCandidatesFor(deliveryInstructionId);

		Mockito.verify(deliveryPlanningRepository, Mockito.never()).getByIds(Mockito.any());
	}

	// ------------------------------------------------------------------ unlinkDeliveryPlannings(instruction) - void

	@Test
	@DisplayName("void invalidates invoice candidates for every planning that WAS allocated, using ids captured before deactivation")
	void unlinkDeliveryPlanningsInvalidatesInvoiceCandidatesDespiteDeactivation()
	{
		final ShipperTransportationId deliveryInstructionId = createDeliveryInstruction();
		allocate(deliveryInstructionId, createDeliveryPlanning(false));
		allocate(deliveryInstructionId, createDeliveryPlanning(false));
		Mockito.clearInvocations(deliveryPlanningRepository);

		deliveryPlanningService.unlinkDeliveryPlannings(deliveryInstructionId);

		// unlinkDeliveryPlannings deactivates the allocations SYNCHRONOUSLY, before the invalidation the
		// invoice-candidate side effect defers to after-commit; a re-query of "active" allocations at that
		// later point would see none left and silently invalidate nothing, so the ids must be resolved
		// BEFORE the deactivation and carried into the deferred batch load, not re-derived after it
		Mockito.verify(deliveryPlanningRepository, Mockito.times(1))
				.getByIds(Mockito.argThat(ids -> ((java.util.Collection<?>) ids).size() == 2));
	}

	@Test
	@DisplayName("unlinking an instruction with no allocations is a no-op - never batch-loads")
	void unlinkDeliveryPlanningsIsANoOpWhenUnallocated()
	{
		final ShipperTransportationId deliveryInstructionId = createDeliveryInstruction();
		Mockito.clearInvocations(deliveryPlanningRepository);

		deliveryPlanningService.unlinkDeliveryPlannings(deliveryInstructionId);

		Mockito.verify(deliveryPlanningRepository, Mockito.never()).getByIds(Mockito.any());
	}
}
