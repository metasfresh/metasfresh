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

import de.metas.document.DocBaseType;
import de.metas.document.DocSubType;
import de.metas.document.dimension.DimensionService;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.notification.INotificationBL;
import de.metas.shipping.MPackageRepository;
import de.metas.shipping.ShipperRepository;
import de.metas.shipping.ShipperTransportationDocSubTypeGuard;
import de.metas.shipping.TransportDirection;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.shipping.model.X_M_ShipperTransportation;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_M_Delivery_Planning;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The delivery instruction {@code M_ShipperTransportation} record {@code DeliveryPlanningRepository} creates must
 * carry the SAME {@code TransportDirection} as the delivery planning(s) it is generated from - never the column's
 * {@code Outgoing} default. An {@code Incoming} planning must produce an {@code Incoming} instruction.
 */
class DeliveryInstructionTransportDirectionDerivationTest
{
	private static final int PRODUCT_ID = 540010;
	private static final int SHIPPER_ID = 540001;
	private static final int BPARTNER_ID = 540020;
	private static final int BPARTNER_LOCATION_ID = 540021;

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private DeliveryPlanningRepository deliveryPlanningRepository;
	private DeliveryPlanningAllocRepository deliveryPlanningAllocRepository;
	private DeliveryInstructionRepository deliveryInstructionRepository;
	private DeliveryInstructionService deliveryInstructionService;
	private DeliveryPlanningService deliveryPlanningService;
	private I_C_UOM uom;

	private I_M_Warehouse warehouse;
	private I_M_ReceiptSchedule receiptSchedule;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		Env.setLoggedUserId(Env.getCtx(), UserId.METASFRESH);
		Services.registerService(INotificationBL.class, Mockito.mock(INotificationBL.class));

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

		uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		InterfaceWrapperHelper.save(uom);

		createDeliveryInstructionDocType();
	}

	// ------------------------------------------------------------------ helpers

	/** An {@code Incoming} planning Generate can build an instruction from: loading address off the receipt schedule, delivery address off the warehouse. */
	private I_M_Delivery_Planning incomingDeliveryPlanning()
	{
		final I_M_Delivery_Planning record = InterfaceWrapperHelper.newInstance(I_M_Delivery_Planning.class);
		record.setTransportDirection(X_M_Delivery_Planning.TRANSPORTDIRECTION_Incoming);
		record.setM_Product_ID(PRODUCT_ID);
		record.setC_UOM_ID(uom.getC_UOM_ID());
		record.setPlannedLoadedQuantity(BigDecimal.TEN);
		record.setPlannedDischargeQuantity(BigDecimal.ONE);
		record.setM_Shipper_ID(SHIPPER_ID);
		record.setC_BPartner_ID(BPARTNER_ID);
		record.setC_BPartner_Location_ID(BPARTNER_LOCATION_ID);
		record.setM_Warehouse_ID(warehouseId());
		record.setM_ReceiptSchedule_ID(receiptScheduleId());
		InterfaceWrapperHelper.save(record);
		return record;
	}

	private int warehouseId()
	{
		if (warehouse == null)
		{
			warehouse = InterfaceWrapperHelper.newInstance(I_M_Warehouse.class);
			warehouse.setValue("WH");
			warehouse.setName("WH");
			warehouse.setC_BPartner_ID(BPARTNER_ID);
			warehouse.setC_BPartner_Location_ID(BPARTNER_LOCATION_ID);
			InterfaceWrapperHelper.save(warehouse);
		}
		return warehouse.getM_Warehouse_ID();
	}

	private int receiptScheduleId()
	{
		if (receiptSchedule == null)
		{
			receiptSchedule = InterfaceWrapperHelper.newInstance(I_M_ReceiptSchedule.class);
			receiptSchedule.setC_BPartner_ID(BPARTNER_ID);
			receiptSchedule.setC_BPartner_Location_ID(BPARTNER_LOCATION_ID);
			InterfaceWrapperHelper.save(receiptSchedule);
		}
		return receiptSchedule.getM_ReceiptSchedule_ID();
	}

	private void createDeliveryInstructionDocType()
	{
		final I_C_DocType docType = InterfaceWrapperHelper.newInstance(I_C_DocType.class);
		docType.setName("Delivery Instruction");
		docType.setDocBaseType(DocBaseType.ShipperTransportation.getCode());
		docType.setDocSubType(DocSubType.DeliveryInstruction.getCode());
		InterfaceWrapperHelper.save(docType);
	}

	private IQueryFilter<I_M_Delivery_Planning> filterFor(@NonNull final I_M_Delivery_Planning record)
	{
		return queryBL.createCompositeQueryFilter(I_M_Delivery_Planning.class)
				.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID, record.getM_Delivery_Planning_ID());
	}

	private IQueryFilter<I_M_Delivery_Planning> filterFor(@NonNull final I_M_Delivery_Planning record1, @NonNull final I_M_Delivery_Planning record2)
	{
		return queryBL.createCompositeQueryFilter(I_M_Delivery_Planning.class)
				.addInArrayFilter(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID, record1.getM_Delivery_Planning_ID(), record2.getM_Delivery_Planning_ID());
	}

	private I_M_Delivery_Planning reload(@NonNull final I_M_Delivery_Planning record)
	{
		return InterfaceWrapperHelper.load(record.getM_Delivery_Planning_ID(), I_M_Delivery_Planning.class);
	}

	// ------------------------------------------------------------------ tests

	@Test
	@DisplayName("generate derives the instruction's TransportDirection from an Incoming planning - not the Outgoing default")
	void generateDerivesIncomingDirectionFromIncomingPlanning()
	{
		final I_M_Delivery_Planning planning = incomingDeliveryPlanning();

		deliveryPlanningService.generateDeliveryInstructions(filterFor(planning), false);

		final int shipperTransportationId = reload(planning).getM_ShipperTransportation_ID();
		final I_M_ShipperTransportation instruction = InterfaceWrapperHelper.load(shipperTransportationId, I_M_ShipperTransportation.class);

		assertThat(instruction.getTransportDirection())
				.as("the instruction must carry the direction its Incoming planning implies, not the Outgoing default")
				.isEqualTo(X_M_ShipperTransportation.TRANSPORTDIRECTION_Incoming);
	}

	@Test
	@DisplayName("combine seeds the instruction's TransportDirection from the (agreeing) plannings - not the Outgoing default")
	void combineDerivesIncomingDirectionFromIncomingPlannings()
	{
		final I_M_Delivery_Planning planning1 = incomingDeliveryPlanning();
		final I_M_Delivery_Planning planning2 = incomingDeliveryPlanning();

		final ShipperTransportationId deliveryInstructionId = deliveryPlanningService.combine(filterFor(planning1, planning2), false);

		final I_M_ShipperTransportation instruction = InterfaceWrapperHelper.load(deliveryInstructionId, I_M_ShipperTransportation.class);
		assertThat(instruction.getTransportDirection())
				.as("combine must seed the instruction's direction from the plannings it combines, not default to Outgoing")
				.isEqualTo(X_M_ShipperTransportation.TRANSPORTDIRECTION_Incoming);
	}
}
