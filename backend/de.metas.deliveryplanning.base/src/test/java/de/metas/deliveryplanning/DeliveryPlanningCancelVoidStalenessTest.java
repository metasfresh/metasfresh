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

import de.metas.bpartner.service.IBPartnerStatisticsUpdater;
import de.metas.deliveryplanning.interceptor.M_ShipperTransportation;
import de.metas.document.DocBaseType;
import de.metas.document.DocSubType;
import de.metas.document.dimension.DimensionService;
import de.metas.document.engine.DocStatus;
import de.metas.event.IEventBusFactory;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.notification.INotificationBL;
import de.metas.shipping.ShipperRepository;
import de.metas.shipping.ShipperTransportationDocSubTypeGuard;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.user.UserId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.wrapper.POJOLookupMap;
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
 * The regression {@code DeliveryPlanningService#cancelDelivery} guards against: {@code voidLinkedDeliveryInstructions}
 * runs the REAL {@code M_ShipperTransportation} void, whose {@code TIMING_AFTER_VOID} interceptor
 * ({@link M_ShipperTransportation#unlinkDeliveryPlannings}) clears {@code ReleaseNo}/{@code M_ShipperTransportation_ID}
 * on the SAME planning row the cancel loop is holding pre-void. Without a re-read after the void,
 * {@code cancelDeliveryPlanning} would save the stale pre-void copy and silently undo that clearing.
 * <p>
 * Exercised through a REAL, registered {@code M_ShipperTransportation} interceptor (via
 * {@link POJOLookupMap#addModelValidator}, the same mechanism {@code C_Payment_AutoAllocateGuardTest} uses) so the
 * void's after-effects genuinely run - not merely assumed - the same way {@link DeliveryPlanningGenerateCompletionTest}
 * drives {@code docActionBL.processEx} against a real document.
 */
class DeliveryPlanningCancelVoidStalenessTest
{
	private static final int PRODUCT_ID = 540010;
	private static final int SHIPPER_ID = 540001;
	private static final int BPARTNER_ID = 540020;
	private static final int BPARTNER_LOCATION_ID = 540021;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private DeliveryPlanningRepository deliveryPlanningRepository;
	private DeliveryPlanningService deliveryPlanningService;
	private I_C_UOM uom;

	private I_M_Warehouse loadingWarehouse;
	private I_M_ShipmentSchedule deliveryShipmentSchedule;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		// generate notifies the instruction's creator: the recipient is CreatedBy, which is stamped from the
		// logged user - nothing this test is about
		Env.setLoggedUserId(Env.getCtx(), UserId.METASFRESH);
		Services.registerService(INotificationBL.class, Mockito.mock(INotificationBL.class));
		Services.registerService(IBPartnerStatisticsUpdater.class, Mockito.mock(IBPartnerStatisticsUpdater.class));

		deliveryPlanningRepository = new DeliveryPlanningRepository(Mockito.mock(DimensionService.class));
		deliveryPlanningService = new DeliveryPlanningService(
				Mockito.mock(ShipperRepository.class),
				deliveryPlanningRepository,
				Mockito.mock(DeliveryStatusColorPaletteService.class),
				Mockito.mock(DimensionService.class),
				Mockito.mock(MeansOfTransportationService.class),
				new ShipperTransportationDocSubTypeGuard());

		// the REAL interceptor, so the instruction's AFTER_VOID unlink cascade genuinely runs on void
		POJOLookupMap.get().addModelValidator(new M_ShipperTransportation(deliveryPlanningService, Mockito.mock(IEventBusFactory.class)));

		uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		InterfaceWrapperHelper.save(uom);

		createDeliveryInstructionDocType();
	}

	// ------------------------------------------------------------------ helpers (mirrors DeliveryPlanningGenerateCompletionTest)

	private I_M_Delivery_Planning generatableDeliveryPlanning()
	{
		final I_M_Delivery_Planning record = InterfaceWrapperHelper.newInstance(I_M_Delivery_Planning.class);
		record.setTransportDirection(X_M_Delivery_Planning.TRANSPORTDIRECTION_Outgoing);
		record.setM_Product_ID(PRODUCT_ID);
		record.setC_UOM_ID(uom.getC_UOM_ID());
		record.setPlannedLoadedQuantity(BigDecimal.TEN);
		record.setPlannedDischargeQuantity(BigDecimal.ONE);
		record.setM_Shipper_ID(SHIPPER_ID);
		record.setC_BPartner_ID(BPARTNER_ID);
		record.setC_BPartner_Location_ID(BPARTNER_LOCATION_ID);
		record.setM_Warehouse_ID(loadingWarehouseId());
		record.setM_ShipmentSchedule_ID(deliveryShipmentScheduleId());
		InterfaceWrapperHelper.save(record);
		return record;
	}

	private int loadingWarehouseId()
	{
		if (loadingWarehouse == null)
		{
			loadingWarehouse = InterfaceWrapperHelper.newInstance(I_M_Warehouse.class);
			loadingWarehouse.setValue("WH");
			loadingWarehouse.setName("WH");
			loadingWarehouse.setC_BPartner_ID(BPARTNER_ID);
			loadingWarehouse.setC_BPartner_Location_ID(BPARTNER_LOCATION_ID);
			InterfaceWrapperHelper.save(loadingWarehouse);
		}
		return loadingWarehouse.getM_Warehouse_ID();
	}

	private int deliveryShipmentScheduleId()
	{
		if (deliveryShipmentSchedule == null)
		{
			deliveryShipmentSchedule = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule.class);
			deliveryShipmentSchedule.setC_BPartner_ID(BPARTNER_ID);
			deliveryShipmentSchedule.setC_BPartner_Location_ID(BPARTNER_LOCATION_ID);
			InterfaceWrapperHelper.save(deliveryShipmentSchedule);
		}
		return deliveryShipmentSchedule.getM_ShipmentSchedule_ID();
	}

	/** The document type the instruction header is created with - resolved by DocBaseType.ShipperTransportation + DocSubType.DeliveryInstruction, so those two have to match. */
	private void createDeliveryInstructionDocType()
	{
		final I_C_DocType docType = InterfaceWrapperHelper.newInstance(I_C_DocType.class);
		docType.setName("Delivery Instruction");
		docType.setDocBaseType(DocBaseType.ShipperTransportation.getCode());
		docType.setDocSubType(DocSubType.DeliveryInstruction.getCode());
		InterfaceWrapperHelper.save(docType);
	}

	private IQueryFilter<I_M_Delivery_Planning> filterFor(final I_M_Delivery_Planning record)
	{
		return queryBL.createCompositeQueryFilter(I_M_Delivery_Planning.class)
				.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID, record.getM_Delivery_Planning_ID());
	}

	private static I_M_Delivery_Planning reload(final I_M_Delivery_Planning record)
	{
		return InterfaceWrapperHelper.load(record.getM_Delivery_Planning_ID(), I_M_Delivery_Planning.class);
	}

	private static DeliveryPlanningId idOf(final I_M_Delivery_Planning record)
	{
		return DeliveryPlanningId.ofRepoId(record.getM_Delivery_Planning_ID());
	}

	// ------------------------------------------------------------------ test

	@Test
	@DisplayName("cancel re-reads after voiding the linked instruction - the void's clearing of ReleaseNo/instruction survives, it is not resurrected from the stale pre-void copy")
	void cancelSurvivesTheVoidsClearingOfTheSameRow()
	{
		final I_M_Delivery_Planning planning = generatableDeliveryPlanning();

		// a REAL, completed, linked delivery instruction - generate stamps ReleaseNo + M_ShipperTransportation_ID
		// on the planning, the same way a planner combining/generating an instruction would
		deliveryPlanningService.generateDeliveryInstructions(filterFor(planning), true);

		final I_M_Delivery_Planning allocated = reload(planning);
		assertThat(allocated.getReleaseNo()).as("generate must have stamped a release number").isNotBlank();
		assertThat(allocated.getM_ShipperTransportation_ID()).as("generate must have linked an instruction").isPositive();
		final int instructionId = allocated.getM_ShipperTransportation_ID();

		final DeliveryPlanningCancelResult result = deliveryPlanningService.cancelDelivery(filterFor(planning));

		assertThat(result.getCancelledIds()).containsExactly(idOf(planning));
		assertThat(result.getSkippedClosedIds()).isEmpty();

		final I_M_ShipperTransportation voidedInstruction = InterfaceWrapperHelper.load(instructionId, I_M_ShipperTransportation.class);
		assertThat(voidedInstruction.getDocStatus()).as("cancel voids the linked instruction").isEqualTo(DocStatus.Voided.getCode());

		final I_M_Delivery_Planning cancelled = reload(planning);
		assertThat(cancelled.getReleaseNo())
				.as("the void's unlink cascade cleared this - cancel must not resurrect it from a stale pre-void copy")
				.isNull();
		assertThat(cancelled.getM_ShipperTransportation_ID())
				.as("likewise cleared by the void, and must stay cleared")
				.isLessThanOrEqualTo(0);
	}
}
