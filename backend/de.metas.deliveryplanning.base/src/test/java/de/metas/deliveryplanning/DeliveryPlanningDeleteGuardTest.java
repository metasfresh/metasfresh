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
import de.metas.deliveryplanning.interceptor.M_Delivery_Planning;
import de.metas.document.dimension.DimensionService;
import de.metas.document.engine.DocStatus;
import de.metas.i18n.AdMessageKey;
import de.metas.product.ProductId;
import de.metas.shipping.MPackageRepository;
import de.metas.shipping.ShipperRepository;
import de.metas.shipping.ShipperTransportationDocSubTypeGuard;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.uom.UomId;
import java.time.Instant;
import lombok.NonNull;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.exceptions.AdempiereException;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * The {@code M_Delivery_Planning} BEFORE_DELETE guard, and the retired-history cleanup that follows it.
 * <p>
 * {@code M_Delivery_Planning_Alloc}'s foreign keys are plain {@code NO ACTION}, so the database refuses to let
 * any allocation's planning be deleted - which is right for a LIVE booking and wrong for retired history, and
 * the database cannot tell the two apart. Both halves of that split therefore live in the interceptor: it
 * refuses the live case with a translatable message, then deletes the retired rows itself.
 */
class DeliveryPlanningDeleteGuardTest
{
	private static final Instant REMOVED_AT = Instant.parse("2026-08-31T10:00:00Z");

	private static final int PRODUCT_ID = 540010;

	private DeliveryPlanningRepository deliveryPlanningRepository;
	private DeliveryPlanningAllocRepository deliveryPlanningAllocRepository;
	private DeliveryInstructionRepository deliveryInstructionRepository;
	private DeliveryInstructionService deliveryInstructionService;
	private I_C_UOM uom;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		deliveryPlanningRepository = new DeliveryPlanningRepository(Mockito.mock(DimensionService.class));
		deliveryPlanningAllocRepository = new DeliveryPlanningAllocRepository();
		deliveryInstructionRepository = new DeliveryInstructionRepository(Mockito.mock(DimensionService.class));
		deliveryInstructionService = new DeliveryInstructionService(
				deliveryPlanningRepository, deliveryPlanningAllocRepository, deliveryInstructionRepository, new MPackageRepository());
		final DeliveryPlanningService deliveryPlanningService = new DeliveryPlanningService(
				Mockito.mock(ShipperRepository.class),
				deliveryPlanningRepository,
				deliveryPlanningAllocRepository,
				deliveryInstructionService,
				Mockito.mock(DimensionService.class),
				Mockito.mock(MeansOfTransportationService.class),
				new ShipperTransportationDocSubTypeGuard());

		// the REAL interceptor, so a non-UI delete genuinely goes through onDelete()
		POJOLookupMap.get().addModelValidator(new M_Delivery_Planning(deliveryPlanningService));

		uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		InterfaceWrapperHelper.save(uom);
	}

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

	private void allocateTo(@NonNull final ShipperTransportationId deliveryInstructionId, @NonNull final I_M_Delivery_Planning record)
	{
		final DeliveryPlanningId id = DeliveryPlanningId.ofRepoId(record.getM_Delivery_Planning_ID());

		deliveryInstructionService.createAllocations(
				deliveryInstructionId,
				ImmutableList.of(DeliveryPlanningAllocCreateRequest.builder()
						.deliveryPlanningId(id)
						.shippingPackage(DeliveryPlanningAllocCreateRequest.ShippingPackageData.builder()
								.productId(ProductId.ofRepoId(PRODUCT_ID))
								.uomId(UomId.ofRepoId(uom.getC_UOM_ID()))
								.build())
						.build()));

		deliveryInstructionService.updateDeliveryPlanningsFromInstruction(ImmutableList.of(id), deliveryInstructionId);
	}

	private static String keyOf(@NonNull final AdMessageKey adMessageKey)
	{
		return adMessageKey.toAD_Message();
	}

	@Test
	@DisplayName("a NON-UI delete of a currently-allocated planning is refused")
	void nonUiDeleteOfAnActivelyAllocatedPlanningIsRefused()
	{
		final ShipperTransportationId instruction = draftDeliveryInstruction("SCHEDULE-DELETE-1");
		final I_M_Delivery_Planning allocated = deliveryPlanning();
		allocateTo(instruction, allocated);
		// allocateTo saved a freshly-LOADED copy (ReleaseNo stamped from the instruction); re-load so the record
		// this test deletes actually carries that ReleaseNo, exactly as the schedule-delete interceptor would see it
		final I_M_Delivery_Planning reloaded = InterfaceWrapperHelper.load(
				DeliveryPlanningId.ofRepoId(allocated.getM_Delivery_Planning_ID()), I_M_Delivery_Planning.class);

		// this is exactly what M_ReceiptSchedule/M_ShipmentSchedule's BEFORE_DELETE interceptors do when their
		// schedule is deleted: a programmatic, non-UI InterfaceWrapperHelper.delete() of the planning - never
		// through validateDeletion(), which only runs for isUIAction() deletes
		assertThatThrownBy(() -> InterfaceWrapperHelper.delete(reloaded))
				.as("deleting a planning that is still on a delivery instruction must never succeed, even from a "
						+ "non-UI caller - the instruction's cargo would lose the record of ever having been booked")
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(keyOf(DeliveryPlanningService.MSG_M_Delivery_Planning_AlreadyReferenced));
	}

	@Test
	@DisplayName("deleting a planning whose allocation is RETIRED succeeds and takes the retired allocation with it")
	void deleteOfAPlanningWithOnlyARetiredAllocationRemovesTheAllocation()
	{
		final ShipperTransportationId instruction = draftDeliveryInstruction("SCHEDULE-DELETE-2");
		final I_M_Delivery_Planning allocated = deliveryPlanning();
		allocateTo(instruction, allocated);

		final DeliveryPlanningId planningId = DeliveryPlanningId.ofRepoId(allocated.getM_Delivery_Planning_ID());
		// exactly what DeliveryPlanningService.removeFrom does, both halves of it: the allocation is retired
		// AND the planning loses its release number
		deliveryInstructionService.deactivateAllocations(ImmutableList.of(planningId), REMOVED_AT);
		deliveryPlanningRepository.clearInstructionReference(ImmutableList.of(planningId));

		InterfaceWrapperHelper.delete(InterfaceWrapperHelper.load(planningId, I_M_Delivery_Planning.class));

		assertThat(POJOLookupMap.get().getRecords(I_M_Delivery_Planning_Alloc.class))
				.as("the retired allocation records history FOR this planning; once the planning itself is gone "
						+ "there is nothing left for it to be a history of, so the interceptor must remove it "
						+ "explicitly - the NO ACTION foreign key would otherwise refuse the delete outright")
				.isEmpty();
	}

	@Test
	@DisplayName("a NON-UI delete of a planning that was never allocated succeeds")
	void nonUiDeleteOfAnUnallocatedPlanningSucceeds()
	{
		final I_M_Delivery_Planning neverAllocated = deliveryPlanning();

		InterfaceWrapperHelper.delete(neverAllocated);

		assertThat(POJOLookupMap.get().getRecords(I_M_Delivery_Planning.class))
				.as("the planning is actually gone - the guard must not turn an unallocated delete into a silent no-op")
				.isEmpty();
	}
}
