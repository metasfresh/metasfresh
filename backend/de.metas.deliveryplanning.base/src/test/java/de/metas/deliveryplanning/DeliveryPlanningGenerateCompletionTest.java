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
import de.metas.document.engine.DocStatus;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.notification.INotificationBL;
import de.metas.order.OrderId;
import de.metas.shipping.ShipperRepository;
import de.metas.shipping.ShipperTransportationDocSubTypeGuard;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;
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
 * Whether Generate completes the delivery instruction it creates - and whether regenerate still does, unaffected.
 * <p>
 * {@code generateDeliveryInstructions} and {@code regenerateDeliveryInstructions} are exercised through a REAL
 * {@link DeliveryPlanningRepository} on the unit-test in-memory store (the same setup as
 * {@link DeliveryPlanningBatchLoadingTest}) rather than a mocked one: the completion decision is made by
 * {@code docActionBL.processEx} against the actual {@code M_ShipperTransportation} document, so only a real,
 * completable record (with its seed shipping-package line) proves the flag does what it says.
 */
class DeliveryPlanningGenerateCompletionTest
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

		// generate/regenerate notify the instruction's creator: the recipient is CreatedBy, which is stamped from
		// the logged user - nothing this test is about
		Env.setLoggedUserId(Env.getCtx(), UserId.METASFRESH);
		Services.registerService(INotificationBL.class, Mockito.mock(INotificationBL.class));

		deliveryPlanningRepository = new DeliveryPlanningRepository(Mockito.mock(DimensionService.class));
		deliveryPlanningService = new DeliveryPlanningService(
				Mockito.mock(ShipperRepository.class),
				deliveryPlanningRepository,
				Mockito.mock(DeliveryStatusColorPaletteService.class),
				Mockito.mock(DimensionService.class),
				Mockito.mock(MeansOfTransportationService.class),
				new ShipperTransportationDocSubTypeGuard());

		uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		InterfaceWrapperHelper.save(uom);

		createDeliveryInstructionDocType();
	}

	// ------------------------------------------------------------------ helpers

	/**
	 * A planning Generate can build an instruction from: it names the forwarder the instruction header cannot
	 * exist without, and the two records an {@code Outgoing} planning reads its loading and delivery address from.
	 */
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

	private IQueryFilter<I_M_Delivery_Planning> filterFor(@NonNull final I_M_Delivery_Planning record)
	{
		return queryBL.createCompositeQueryFilter(I_M_Delivery_Planning.class)
				.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID, record.getM_Delivery_Planning_ID());
	}

	private I_M_Delivery_Planning reload(@NonNull final I_M_Delivery_Planning record)
	{
		return InterfaceWrapperHelper.load(record.getM_Delivery_Planning_ID(), I_M_Delivery_Planning.class);
	}

	private String docStatusOf(final int shipperTransportationId)
	{
		return InterfaceWrapperHelper.load(shipperTransportationId, I_M_ShipperTransportation.class).getDocStatus();
	}

	private I_M_ShippingPackage seedShippingPackageOf(final int shipperTransportationId)
	{
		return queryBL.createQueryBuilder(I_M_ShippingPackage.class)
				.addEqualsFilter(I_M_ShippingPackage.COLUMNNAME_M_ShipperTransportation_ID, shipperTransportationId)
				.create()
				.firstOnlyNotNull(I_M_ShippingPackage.class);
	}

	// ------------------------------------------------------------------ tests

	@Test
	@DisplayName("generate without IsComplete leaves the instruction a draft")
	void generateWithoutFlagLeavesDraft()
	{
		final I_M_Delivery_Planning planning = generatableDeliveryPlanning();

		deliveryPlanningService.generateDeliveryInstructions(filterFor(planning), false);

		final I_M_Delivery_Planning stamped = reload(planning);
		assertThat(stamped.getM_ShipperTransportation_ID()).isPositive();
		// not asserted as == Drafted: on a real DB, AD_Column.DefaultValue fills DocStatus='DR' on INSERT, but the
		// unit-test in-memory store applies no AD_Column defaults, so a never-completed record reads null here.
		// The behavioural contract this test pins is "generate did not complete it" - which "not Completed" states
		// precisely, independent of that DB-default gap.
		assertThat(docStatusOf(stamped.getM_ShipperTransportation_ID()))
				.as("generate without IsComplete must not run the completion action")
				.isNotEqualTo(DocStatus.Completed.getCode());
	}

	@Test
	@DisplayName("generate with IsComplete completes the instruction")
	void generateWithFlagCompletes()
	{
		final I_M_Delivery_Planning planning = generatableDeliveryPlanning();

		deliveryPlanningService.generateDeliveryInstructions(filterFor(planning), true);

		final I_M_Delivery_Planning stamped = reload(planning);
		assertThat(docStatusOf(stamped.getM_ShipperTransportation_ID())).isEqualTo(DocStatus.Completed.getCode());
	}

	@Test
	@DisplayName("generate stamps the planning's C_Order_ID onto the seed shipping package it creates")
	void generateStampsThePlanningsOrderIdOntoTheSeedShippingPackage()
	{
		final I_M_Delivery_Planning planning = generatableDeliveryPlanning();
		final OrderId orderId = OrderId.ofRepoId(540199);
		planning.setC_Order_ID(orderId.getRepoId());
		InterfaceWrapperHelper.save(planning);

		deliveryPlanningService.generateDeliveryInstructions(filterFor(planning), false);

		final int shipperTransportationId = reload(planning).getM_ShipperTransportation_ID();
		assertThat(seedShippingPackageOf(shipperTransportationId).getC_Order_ID())
				.as("the seed package must carry the same order the planning is behind")
				.isEqualTo(orderId.getRepoId());
	}

	@Test
	@DisplayName("regenerate still always completes, unaffected by generate's draft-or-complete option")
	void regenerateAlwaysCompletes()
	{
		final I_M_Delivery_Planning planning = generatableDeliveryPlanning();
		// seed a first instruction the way a planner would - completed, so there is something to regenerate FROM
		deliveryPlanningService.generateDeliveryInstructions(filterFor(planning), true);
		final int firstInstructionId = reload(planning).getM_ShipperTransportation_ID();

		deliveryPlanningService.regenerateDeliveryInstructions(filterFor(planning));

		final int secondInstructionId = reload(planning).getM_ShipperTransportation_ID();
		assertThat(secondInstructionId)
				.as("regenerate replaces the instruction, it does not reuse it")
				.isNotEqualTo(firstInstructionId);
		assertThat(docStatusOf(firstInstructionId))
				.as("the superseded instruction is voided")
				.isEqualTo(DocStatus.Voided.getCode());
		assertThat(docStatusOf(secondInstructionId))
				.as("regenerate keeps completing unconditionally - it carries no IsComplete parameter")
				.isEqualTo(DocStatus.Completed.getCode());
	}
}
