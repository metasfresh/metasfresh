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
import de.metas.deliveryplanning.interceptor.M_ShipperTransportation;
import de.metas.document.dimension.DimensionService;
import de.metas.document.engine.DocStatus;
import de.metas.event.IEventBusFactory;
import de.metas.product.ProductId;
import de.metas.shipping.MPackageRepository;
import de.metas.shipping.ShipperRepository;
import de.metas.shipping.ShipperTransportationDocSubTypeGuard;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.uom.UomId;
import lombok.NonNull;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.model.X_M_Delivery_Planning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The direction of the sync is fixed: instruction to planning, never the other way. Once a planning is allocated,
 * its own date fields are read-only from the planner's perspective - a change on the INSTRUCTION is what reaches
 * them, unconditionally.
 */
class DeliveryInstructionDateSyncDownTest
{
	private static final int PRODUCT_ID = 540010;

	private DeliveryPlanningRepository deliveryPlanningRepository;
	private DeliveryPlanningAllocRepository deliveryPlanningAllocRepository;
	private DeliveryInstructionRepository deliveryInstructionRepository;
	private DeliveryInstructionService deliveryInstructionService;
	private DeliveryPlanningService deliveryPlanningService;
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
		deliveryPlanningService = new DeliveryPlanningService(
				Mockito.mock(ShipperRepository.class),
				deliveryPlanningRepository,
				deliveryPlanningAllocRepository,
				deliveryInstructionRepository,
				deliveryInstructionService,
				Mockito.mock(DeliveryStatusColorPaletteService.class),
				Mockito.mock(DimensionService.class),
				Mockito.mock(MeansOfTransportationService.class),
				new ShipperTransportationDocSubTypeGuard());

		// the REAL interceptor, so a planner's edit of the instruction genuinely fires the sync
		POJOLookupMap.get().addModelValidator(new M_ShipperTransportation(deliveryPlanningService, Mockito.mock(IEventBusFactory.class)));

		uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		InterfaceWrapperHelper.save(uom);
	}

	private static Timestamp day(final int dayOfMonth)
	{
		return Timestamp.from(LocalDate.of(2026, 3, dayOfMonth).atStartOfDay(ZoneId.of("UTC")).toInstant());
	}

	private I_M_Delivery_Planning deliveryPlanning(
			@NonNull final Timestamp etd,
			@NonNull final Timestamp eta,
			@NonNull final String loadingTime)
	{
		final I_M_Delivery_Planning record = InterfaceWrapperHelper.newInstance(I_M_Delivery_Planning.class);
		record.setTransportDirection(X_M_Delivery_Planning.TRANSPORTDIRECTION_Outgoing);
		record.setM_Product_ID(PRODUCT_ID);
		record.setC_UOM_ID(uom.getC_UOM_ID());
		record.setPlannedLoadedQuantity(BigDecimal.TEN);
		record.setPlannedDischargeQuantity(BigDecimal.ONE);
		// the planning's OWN dates, different from what the instruction will carry - proving the
		// sync overwrites rather than fills-if-empty
		record.setETD(etd);
		record.setETA(eta);
		record.setLoadingTime(loadingTime);
		InterfaceWrapperHelper.save(record);
		return record;
	}

	private I_M_ShipperTransportation draftDeliveryInstruction(@NonNull final String documentNo)
	{
		final I_M_ShipperTransportation record = InterfaceWrapperHelper.newInstance(I_M_ShipperTransportation.class);
		record.setDocumentNo(documentNo);
		record.setDocStatus(DocStatus.Drafted.getCode());
		InterfaceWrapperHelper.save(record);
		return record;
	}

	private DeliveryPlanningId allocate(@NonNull final I_M_ShipperTransportation instruction, @NonNull final I_M_Delivery_Planning planning)
	{
		final DeliveryPlanningId deliveryPlanningId = DeliveryPlanningId.ofRepoId(planning.getM_Delivery_Planning_ID());
		deliveryInstructionService.createAllocations(
				ShipperTransportationId.ofRepoId(instruction.getM_ShipperTransportation_ID()),
				ImmutableList.of(DeliveryPlanningAllocCreateRequest.builder()
						.deliveryPlanningId(deliveryPlanningId)
						.shippingPackage(DeliveryPlanningAllocCreateRequest.ShippingPackageData.builder()
								.productId(ProductId.ofRepoId(PRODUCT_ID))
								.uomId(UomId.ofRepoId(uom.getC_UOM_ID()))
								.build())
						.build()));
		return deliveryPlanningId;
	}

	private static I_M_Delivery_Planning reload(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		return InterfaceWrapperHelper.load(deliveryPlanningId, I_M_Delivery_Planning.class);
	}

	private static I_M_ShipperTransportation reload(@NonNull final I_M_ShipperTransportation instruction)
	{
		return InterfaceWrapperHelper.load(instruction.getM_ShipperTransportation_ID(), I_M_ShipperTransportation.class);
	}

	@Test
	@DisplayName("a change on the instruction overwrites every currently allocated planning's dates, even a value the planning already carried")
	void instructionChangeReachesEveryAllocatedPlanning()
	{
		final I_M_ShipperTransportation instruction = draftDeliveryInstruction("SYNC-1");
		final DeliveryPlanningId planningId1 = allocate(instruction, deliveryPlanning(day(1), day(2), "08:00"));
		final DeliveryPlanningId planningId2 = allocate(instruction, deliveryPlanning(day(9), day(9), "23:59"));

		final I_M_ShipperTransportation changedInstruction = reload(instruction);
		changedInstruction.setETD(day(5));
		changedInstruction.setETA(day(6));
		changedInstruction.setATD(day(5));
		changedInstruction.setATA(day(6));
		changedInstruction.setLoadingTime("10:00");
		changedInstruction.setDeliveryTime("11:00");
		InterfaceWrapperHelper.save(changedInstruction);

		// the save above is the whole trigger: the registered interceptor pushes the dates onto every allocated planning

		for (final DeliveryPlanningId planningId : ImmutableList.of(planningId1, planningId2))
		{
			final I_M_Delivery_Planning reloaded = reload(planningId);
			assertThat(reloaded.getETD()).as("ETD overwritten from the instruction").isEqualTo(day(5));
			assertThat(reloaded.getETA()).as("ETA overwritten from the instruction").isEqualTo(day(6));
			assertThat(reloaded.getATD()).as("ATD overwritten from the instruction").isEqualTo(day(5));
			assertThat(reloaded.getATA()).as("ATA overwritten from the instruction").isEqualTo(day(6));
			assertThat(reloaded.getLoadingTime()).as("LoadingTime overwritten from the instruction").isEqualTo("10:00");
			assertThat(reloaded.getDeliveryTime()).as("DeliveryTime overwritten from the instruction").isEqualTo("11:00");
		}
	}

	@Test
	@DisplayName("a planning no longer allocated to the instruction is not touched by the sync")
	void unallocatedPlanningIsNotTouched()
	{
		final I_M_ShipperTransportation instruction = draftDeliveryInstruction("SYNC-2");
		final I_M_Delivery_Planning unrelatedPlanning = deliveryPlanning(day(1), day(2), "08:00");

		final I_M_ShipperTransportation changedInstruction = reload(instruction);
		changedInstruction.setETD(day(5));
		changedInstruction.setETA(day(6));
		InterfaceWrapperHelper.save(changedInstruction);

		final I_M_Delivery_Planning reloaded = reload(DeliveryPlanningId.ofRepoId(unrelatedPlanning.getM_Delivery_Planning_ID()));
		assertThat(reloaded.getETD()).as("never allocated to this instruction, so never touched").isEqualTo(day(1));
		assertThat(reloaded.getETA()).isEqualTo(day(2));
	}

	@Test
	@DisplayName("updateDeliveryPlanningsFromInstruction - the low-level writer the sync uses - overwrites the planning's dates unconditionally")
	void repositoryWriterOverwritesUnconditionally()
	{
		final I_M_ShipperTransportation instruction = draftDeliveryInstruction("SYNC-3");
		instruction.setETD(day(5));
		instruction.setETA(day(6));
		instruction.setATD(day(5));
		instruction.setATA(day(6));
		instruction.setLoadingTime("10:00");
		instruction.setDeliveryTime("11:00");
		InterfaceWrapperHelper.save(instruction);

		final DeliveryPlanningId planningId = allocate(instruction, deliveryPlanning(day(1), day(2), "08:00"));

		deliveryPlanningRepository.updateDeliveryPlanningsFromInstruction(ImmutableList.of(planningId), instruction);

		final I_M_Delivery_Planning reloaded = reload(planningId);
		assertThat(reloaded.getETD()).isEqualTo(day(5));
		assertThat(reloaded.getETA()).isEqualTo(day(6));
		assertThat(reloaded.getATD()).isEqualTo(day(5));
		assertThat(reloaded.getATA()).isEqualTo(day(6));
		assertThat(reloaded.getLoadingTime()).isEqualTo("10:00");
		assertThat(reloaded.getDeliveryTime()).isEqualTo("11:00");
	}
}
