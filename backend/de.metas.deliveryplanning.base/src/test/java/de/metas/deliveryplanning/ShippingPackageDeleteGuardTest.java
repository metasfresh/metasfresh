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
import de.metas.deliveryplanning.interceptor.M_ShippingPackage;
import de.metas.document.dimension.DimensionService;
import de.metas.document.engine.DocStatus;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.shipping.ShipperRepository;
import de.metas.shipping.ShipperTransportationDocSubTypeGuard;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.shipping.model.ShipperTransportationId;
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
 * The {@code M_ShippingPackage} BEFORE_DELETE guard against deleting a package that an ACTIVE
 * {@code M_Delivery_Planning_Alloc} still points at.
 * <p>
 * This is the second half of the same job {@link DeliveryPlanningDeleteGuardTest} covers for the planning leg:
 * {@code M_Delivery_Planning_Alloc}'s three FKs are all {@code ON DELETE CASCADE}, and Postgres cannot cascade
 * over {@code IsActive='N'} rows while refusing over {@code IsActive='Y'} ones. Without a Java guard the package
 * delete silently destroys a live booking - a worse failure than the raw FK violation the cascade replaced.
 * <p>
 * <b>This one guard also covers the delivery-instruction leg</b>, and that is deliberate rather than an
 * oversight. {@code PO.delete0()} calls {@code beforeDelete()} BEFORE it fires {@code TYPE_BEFORE_DELETE}
 * (PO.java:3785 vs :3802), and {@code MMShipperTransportation.beforeDelete()} force-deletes every one of its
 * {@code M_ShippingPackage} lines through {@code InterfaceWrapperHelper::delete}. Since
 * {@code M_Delivery_Planning_Alloc.M_ShippingPackage_ID} is {@code NOT NULL} and
 * {@code DeliveryPlanningRepository.createShippingPackage} always stamps the instruction onto that package,
 * every active allocation of an instruction has a package among those lines - so deleting the instruction
 * always reaches this guard first, and a separate BEFORE_DELETE guard on the instruction itself would be
 * unreachable code.
 */
class ShippingPackageDeleteGuardTest
{
	private static final int PRODUCT_ID = 540010;

	private DeliveryPlanningRepository deliveryPlanningRepository;
	private I_C_UOM uom;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		deliveryPlanningRepository = new DeliveryPlanningRepository(Mockito.mock(DimensionService.class));
		final DeliveryPlanningService deliveryPlanningService = new DeliveryPlanningService(
				Mockito.mock(ShipperRepository.class),
				deliveryPlanningRepository,
				Mockito.mock(DeliveryStatusColorPaletteService.class),
				Mockito.mock(DimensionService.class),
				Mockito.mock(MeansOfTransportationService.class),
				new ShipperTransportationDocSubTypeGuard());

		// the REAL interceptor, so the delete genuinely goes through onDelete()
		POJOLookupMap.get().addModelValidator(new M_ShippingPackage(deliveryPlanningService));

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

	private DeliveryPlanningId allocateTo(
			@NonNull final ShipperTransportationId deliveryInstructionId,
			@NonNull final I_M_Delivery_Planning record)
	{
		final DeliveryPlanningId id = DeliveryPlanningId.ofRepoId(record.getM_Delivery_Planning_ID());

		deliveryPlanningRepository.createAllocations(
				deliveryInstructionId,
				ImmutableList.of(DeliveryPlanningAllocCreateRequest.builder()
						.deliveryPlanningId(id)
						.productId(ProductId.ofRepoId(PRODUCT_ID))
						.qtyLoaded(Quantity.of(BigDecimal.TEN, uom))
						.qtyDischarged(Quantity.of(BigDecimal.ONE, uom))
						.build()));

		deliveryPlanningRepository.updateDeliveryPlanningsFromInstruction(ImmutableList.of(id), deliveryInstructionId);
		return id;
	}

	/** The package {@link #allocateTo} created, i.e. the one the allocation points at. */
	private I_M_ShippingPackage shippingPackageOf(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		final I_M_Delivery_Planning_Alloc allocRecord = POJOLookupMap.get()
				.getRecords(I_M_Delivery_Planning_Alloc.class)
				.stream()
				.filter(alloc -> alloc.getM_Delivery_Planning_ID() == deliveryPlanningId.getRepoId())
				.findFirst()
				.orElseThrow(() -> new AssertionError("no allocation created for " + deliveryPlanningId));

		return InterfaceWrapperHelper.load(allocRecord.getM_ShippingPackage_ID(), I_M_ShippingPackage.class);
	}

	@Test
	@DisplayName("deleting a package that an ACTIVE allocation points at is refused, not silently cascaded away")
	void deleteOfAPackageWithAnActiveAllocationIsRefused()
	{
		final ShipperTransportationId instruction = draftDeliveryInstruction("PACKAGE-DELETE-1");
		final DeliveryPlanningId planningId = allocateTo(instruction, deliveryPlanning());
		final I_M_ShippingPackage shippingPackage = shippingPackageOf(planningId);

		assertThatThrownBy(() -> InterfaceWrapperHelper.delete(shippingPackage))
				.as("deleting a shipping package still carrying a live booking must never silently succeed - the "
						+ "ON DELETE CASCADE would take the ACTIVE allocation with it, leaving the instruction's "
						+ "cargo with no record of ever having been booked")
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	@DisplayName("deleting a package whose allocation is RETIRED is refused too - the instruction is cancelled, not deleted")
	void deleteOfAPackageWithOnlyARetiredAllocationIsRefused()
	{
		final ShipperTransportationId instruction = draftDeliveryInstruction("PACKAGE-DELETE-2");
		final DeliveryPlanningId planningId = allocateTo(instruction, deliveryPlanning());
		final I_M_ShippingPackage shippingPackage = shippingPackageOf(planningId);

		// exactly what remove-from-instruction does, both halves of it (DeliveryPlanningService:1062-1063):
		// the allocation is retired AND the planning loses its release number
		deliveryPlanningRepository.deactivateAllocations(ImmutableList.of(planningId));
		deliveryPlanningRepository.clearInstructionReference(ImmutableList.of(planningId));

		final I_M_ShippingPackage reloaded = InterfaceWrapperHelper.load(
				shippingPackage.getM_ShippingPackage_ID(), I_M_ShippingPackage.class);

		assertThatThrownBy(() -> InterfaceWrapperHelper.delete(reloaded))
				.as("an instruction that once carried a planning is exactly the document whose history the "
						+ "retirement exists to keep - it is cancelled or closed, never deleted")
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	@DisplayName("deleting a package that no allocation points at is unaffected by the guard")
	void deleteOfAPackageWithNoAllocationSucceeds()
	{
		final I_M_ShippingPackage unrelated = InterfaceWrapperHelper.newInstance(I_M_ShippingPackage.class);
		unrelated.setM_Product_ID(PRODUCT_ID);
		InterfaceWrapperHelper.save(unrelated);

		InterfaceWrapperHelper.delete(unrelated);

		assertThat(POJOLookupMap.get().getRecords(I_M_ShippingPackage.class)).isEmpty();
	}
}
