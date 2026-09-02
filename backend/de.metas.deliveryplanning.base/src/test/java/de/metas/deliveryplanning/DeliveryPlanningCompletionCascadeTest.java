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
import de.metas.document.DocBaseType;
import de.metas.document.DocSubType;
import de.metas.document.dimension.DimensionService;
import de.metas.document.engine.DocStatus;
import de.metas.i18n.ITranslatableString;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.shipping.ShipperRepository;
import de.metas.shipping.ShipperTransportationDocSubTypeGuard;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Services;
import java.time.Instant;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Delivery_Planning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * What completing - and re-activating, and voiding - a delivery instruction costs, and what each refuses.
 * <p>
 * A transport order shares {@code M_ShipperTransportation} with a delivery instruction and must be unaffected
 * by any of these rules; it behaves here exactly like a delivery instruction with zero allocations.
 */
class DeliveryPlanningCompletionCascadeTest
{
	/** A fixed "removed at" stamp: the repository takes it as a parameter, so tests do not depend on wall-clock time. */
	private static final Instant REMOVED_AT = Instant.parse("2026-08-31T10:00:00Z");

	private static final int SHIPPER_BPARTNER_ID = 540001;
	private static final int SHIPPER_LOCATION_ID = 540002;
	private static final int SHIPPER_ID = 540003;

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

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
				Mockito.mock(MeansOfTransportationService.class),
				new ShipperTransportationDocSubTypeGuard());

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

	/**
	 * Same table, distinguished only by the {@code C_DocType.DocSubType}: never re-derive "is this a delivery
	 * instruction" from direction, {@code IsSOTrx} or allocations.
	 */
	private ShipperTransportationId createInstructionWithDocSubType(@Nullable final String docSubType)
	{
		final I_C_DocType docType = InterfaceWrapperHelper.newInstance(I_C_DocType.class);
		docType.setName("docType-" + docSubType);
		docType.setDocBaseType(DocBaseType.ShipperTransportation.getCode());
		docType.setDocSubType(docSubType);
		InterfaceWrapperHelper.save(docType);

		final I_M_ShipperTransportation record = InterfaceWrapperHelper.newInstance(I_M_ShipperTransportation.class);
		record.setShipper_BPartner_ID(SHIPPER_BPARTNER_ID);
		record.setShipper_Location_ID(SHIPPER_LOCATION_ID);
		record.setM_Shipper_ID(SHIPPER_ID);
		record.setDocStatus(DocStatus.Drafted.getCode());
		record.setC_DocType_ID(docType.getC_DocType_ID());
		InterfaceWrapperHelper.save(record);
		return ShipperTransportationId.ofRepoId(record.getM_ShipperTransportation_ID());
	}

	private ShipperTransportationId createDeliveryInstructionWithDocType()
	{
		return createInstructionWithDocSubType(DocSubType.DeliveryInstruction.getCode());
	}

	private ShipperTransportationId createTransportOrder()
	{
		return createInstructionWithDocSubType(null);
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
		// the full sentence, not three contains(): queryActiveAllocationsByInstructionId orders on the
		// allocation id, so the ids are named in that order
		assertThat(reason.get().translate("en_US"))
				.isEqualTo(DeliveryPlanningService.MSG_M_Delivery_Planning_ClosedAllocatedPlannings.toAD_Message()
						+ " - " + closedOne.getRepoId() + ", " + closedTwo.getRepoId());
	}

	@Test
	@DisplayName("complete is refused for a delivery instruction with zero active allocations - the degenerate state Remove-from-instruction can now create")
	void completeIsRefusedForEmptyDeliveryInstruction()
	{
		final ShipperTransportationId deliveryInstructionId = createDeliveryInstructionWithDocType();

		final Optional<ITranslatableString> reason = deliveryPlanningService.getCompleteRejectionReason(deliveryInstructionId);

		assertThat(reason).as("a rejection reason").isPresent();
		assertThat(reason.get().translate("en_US")).isEqualTo(DeliveryPlanningService.MSG_M_Delivery_Planning_EmptyDeliveryInstruction.toAD_Message());
	}

	@Test
	@DisplayName("complete is refused for a delivery instruction whose only allocation was deactivated - zero ACTIVE allocations too")
	void completeIsRefusedForDeliveryInstructionWithOnlyDeactivatedAllocation()
	{
		final ShipperTransportationId deliveryInstructionId = createDeliveryInstructionWithDocType();
		allocate(deliveryInstructionId, createDeliveryPlanning(false));
		deliveryPlanningRepository.deactivateAllocations(deliveryInstructionId, REMOVED_AT);

		final Optional<ITranslatableString> reason = deliveryPlanningService.getCompleteRejectionReason(deliveryInstructionId);

		assertThat(reason).as("a rejection reason").isPresent();
		assertThat(reason.get().translate("en_US")).isEqualTo(DeliveryPlanningService.MSG_M_Delivery_Planning_EmptyDeliveryInstruction.toAD_Message());
	}

	@Test
	@DisplayName("complete is accepted for a delivery instruction with one active allocation - no regression")
	void completeIsAcceptedForDeliveryInstructionWithOneActiveAllocation()
	{
		final ShipperTransportationId deliveryInstructionId = createDeliveryInstructionWithDocType();
		allocate(deliveryInstructionId, createDeliveryPlanning(false));

		assertThat(deliveryPlanningService.getCompleteRejectionReason(deliveryInstructionId)).isEmpty();
	}

	@Test
	@DisplayName("complete is accepted for a transport order with zero allocations - the case the guard must keep as a no-op")
	void completeIsAcceptedForTransportOrderWithNoAllocations()
	{
		final ShipperTransportationId transportOrderId = createTransportOrder();

		assertThat(deliveryPlanningService.getCompleteRejectionReason(transportOrderId)).isEmpty();
	}

	// ------------------------------------------------------------------ getReActivateRejectionReason

	@Test
	@DisplayName("re-activate is accepted when none of the allocated plannings are closed")
	void reActivateIsAcceptedWhenNoneClosed()
	{
		final ShipperTransportationId deliveryInstructionId = createDeliveryInstruction();
		allocate(deliveryInstructionId, createDeliveryPlanning(false));
		allocate(deliveryInstructionId, createDeliveryPlanning(false));

		assertThat(deliveryPlanningService.getReActivateRejectionReason(deliveryInstructionId)).isEmpty();
	}

	@Test
	@DisplayName("re-activate is refused and names every closed allocated planning, not just the first one")
	void reActivateIsRefusedAndNamesTheClosedOnes()
	{
		final ShipperTransportationId deliveryInstructionId = createDeliveryInstruction();
		final DeliveryPlanningId open = createDeliveryPlanning(false);
		final DeliveryPlanningId closedOne = createDeliveryPlanning(true);
		final DeliveryPlanningId closedTwo = createDeliveryPlanning(true);
		allocate(deliveryInstructionId, open);
		allocate(deliveryInstructionId, closedOne);
		allocate(deliveryInstructionId, closedTwo);

		final Optional<ITranslatableString> reason = deliveryPlanningService.getReActivateRejectionReason(deliveryInstructionId);

		assertThat(reason).as("a rejection reason").isPresent();
		// the full sentence, not three contains(): queryActiveAllocationsByInstructionId orders on the
		// allocation id, so the ids are named in that order
		assertThat(reason.get().translate("en_US"))
				.isEqualTo(DeliveryPlanningService.MSG_M_Delivery_Planning_ReActivateClosedAllocatedPlannings.toAD_Message()
						+ " - " + closedOne.getRepoId() + ", " + closedTwo.getRepoId());
	}

	@Test
	@DisplayName("re-activate is accepted for a delivery instruction with zero active allocations - the empty rule is COMPLETE-only")
	void reActivateIsAcceptedForEmptyDeliveryInstruction()
	{
		final ShipperTransportationId deliveryInstructionId = createDeliveryInstructionWithDocType();

		assertThat(deliveryPlanningService.getCompleteRejectionReason(deliveryInstructionId))
				.as("sanity: completing the very same instruction IS refused")
				.isPresent();
		assertThat(deliveryPlanningService.getReActivateRejectionReason(deliveryInstructionId)).isEmpty();
	}

	// ------------------------------------------------------------------ getVoidRejectionReason

	@Test
	@DisplayName("void is accepted when none of the allocated plannings are closed")
	void voidIsAcceptedWhenNoneClosed()
	{
		final ShipperTransportationId deliveryInstructionId = createDeliveryInstruction();
		allocate(deliveryInstructionId, createDeliveryPlanning(false));
		allocate(deliveryInstructionId, createDeliveryPlanning(false));

		assertThat(deliveryPlanningService.getVoidRejectionReason(deliveryInstructionId)).isEmpty();
	}

	@Test
	@DisplayName("void is refused and names every closed allocated planning, not just the first one")
	void voidIsRefusedAndNamesTheClosedOnes()
	{
		final ShipperTransportationId deliveryInstructionId = createDeliveryInstruction();
		final DeliveryPlanningId open = createDeliveryPlanning(false);
		final DeliveryPlanningId closedOne = createDeliveryPlanning(true);
		final DeliveryPlanningId closedTwo = createDeliveryPlanning(true);
		allocate(deliveryInstructionId, open);
		allocate(deliveryInstructionId, closedOne);
		allocate(deliveryInstructionId, closedTwo);

		final Optional<ITranslatableString> reason = deliveryPlanningService.getVoidRejectionReason(deliveryInstructionId);

		assertThat(reason).as("a rejection reason").isPresent();
		// the full sentence, not three contains(): queryActiveAllocationsByInstructionId orders on the
		// allocation id, so the ids are named in that order
		assertThat(reason.get().translate("en_US"))
				.isEqualTo(DeliveryPlanningService.MSG_M_Delivery_Planning_VoidClosedAllocatedPlannings.toAD_Message()
						+ " - " + closedOne.getRepoId() + ", " + closedTwo.getRepoId());
	}

	@Test
	@DisplayName("void is accepted for a delivery instruction with zero active allocations - the empty rule is COMPLETE-only")
	void voidIsAcceptedForEmptyDeliveryInstruction()
	{
		final ShipperTransportationId deliveryInstructionId = createDeliveryInstructionWithDocType();

		assertThat(deliveryPlanningService.getCompleteRejectionReason(deliveryInstructionId))
				.as("sanity: completing the very same instruction IS refused")
				.isPresent();
		assertThat(deliveryPlanningService.getVoidRejectionReason(deliveryInstructionId)).isEmpty();
	}

	// ------------------------------------------------------------------ invalidateInvoiceCandidatesFor(instruction)

	@Test
	@DisplayName("invalidating invoice candidates for a whole instruction reads its allocated plannings in ONE batch load")
	void invalidateInvoiceCandidatesBatchLoadsAllAllocations()
	{
		final ShipperTransportationId deliveryInstructionId = createDeliveryInstruction();
		final DeliveryPlanningId first = createDeliveryPlanning(false);
		final DeliveryPlanningId second = createDeliveryPlanning(false);
		final DeliveryPlanningId third = createDeliveryPlanning(false);
		allocate(deliveryInstructionId, first);
		allocate(deliveryInstructionId, second);
		allocate(deliveryInstructionId, third);
		Mockito.clearInvocations(deliveryPlanningRepository);

		deliveryPlanningService.invalidateInvoiceCandidatesFor(deliveryInstructionId);

		// exactly ONE batch load, carrying ALL THREE allocated plannings - not a per-planning loop, and not a
		// batch that silently drops the instruction's other lines
		@SuppressWarnings("unchecked")
		final ArgumentCaptor<Collection<DeliveryPlanningId>> batchLoadedIds = ArgumentCaptor.forClass(Collection.class);
		Mockito.verify(deliveryPlanningRepository, Mockito.times(1)).getByIds(batchLoadedIds.capture());
		assertThat(batchLoadedIds.getValue()).containsExactlyInAnyOrder(first, second, third);
		Mockito.verify(deliveryPlanningRepository, Mockito.never()).getById(Mockito.any());
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
		// BEFORE the deactivation and carried into the deferred batch load, not re-derived after it.
		// TWO calls over the same 2 ids are expected: the deactivation resets those plannings' dates (one batch
		// load), and the deferred invalidation reads them again afterwards (a second, unrelated batch load)
		Mockito.verify(deliveryPlanningRepository, Mockito.times(2))
				.getByIds(Mockito.argThat(ids -> ((java.util.Collection<?>) ids).size() == 2));
	}
}
