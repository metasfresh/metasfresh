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
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.product.ProductId;
import de.metas.shipping.MPackageRepository;
import de.metas.shipping.ShipperRepository;
import de.metas.shipping.ShipperTransportationDocSubTypeGuard;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_M_Delivery_Planning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The moment an allocation becomes inactive - removed, moved off, closed or the instruction voided - the
 * planning's dates stop being the instruction's and are instead RECOMPUTED from the order and its schedule,
 * exactly as {@link GenerateOutgoingDeliveryPlanningCommand} / {@link GenerateIncomingDeliveryPlanningCommand}
 * derive them when a planning is first generated.
 * <p>
 * A RECOMPUTE, not a restore: every scenario first allocates the planning to an instruction carrying DIFFERENT
 * dates and syncs them down, so the assertion after deallocation proves the ORDER's current state was re-derived.
 * <p>
 * Both {@code deactivateAllocations} overloads are exercised, because both route through the single choke
 * point ({@code deactivateAllocationRecords}) the reset is keyed on.
 */
class DeliveryPlanningDateResetOnDeallocationTest
{
	private static final int PRODUCT_ID = 540010;
	private static final int BPARTNER_ID = 540020;
	private static final int BPARTNER_LOCATION_ID = 540021;

	private IOrderDAO orderDAO;
	private IReceiptScheduleDAO receiptScheduleDAO;
	private IShipmentScheduleBL shipmentScheduleBL;

	private DeliveryPlanningRepository deliveryPlanningRepository;
	private DeliveryPlanningAllocRepository deliveryPlanningAllocRepository;
	private DeliveryInstructionRepository deliveryInstructionRepository;
	private DeliveryInstructionService deliveryInstructionService;
	private DeliveryPlanningService deliveryPlanningService;
	private I_C_UOM uom;
	private I_M_Warehouse loadingWarehouse;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		orderDAO = Mockito.spy(Services.get(IOrderDAO.class));
		Services.registerService(IOrderDAO.class, orderDAO);
		receiptScheduleDAO = Mockito.spy(Services.get(IReceiptScheduleDAO.class));
		Services.registerService(IReceiptScheduleDAO.class, receiptScheduleDAO);
		shipmentScheduleBL = Mockito.spy(Services.get(IShipmentScheduleBL.class));
		Services.registerService(IShipmentScheduleBL.class, shipmentScheduleBL);

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
	}

	// ------------------------------------------------------------------ helpers

	private static Timestamp day(final int dayOfMonth)
	{
		return Timestamp.from(LocalDate.of(2026, 3, dayOfMonth).atStartOfDay(ZoneId.of("UTC")).toInstant());
	}

	private int createOrder(@Nullable final Timestamp preparationDate)
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setPreparationDate(preparationDate);
		InterfaceWrapperHelper.save(order);
		return order.getC_Order_ID();
	}

	private int createOrderLine(@Nullable final Timestamp datePromised, @Nullable final Timestamp dateDelivered)
	{
		final I_C_OrderLine orderLine = InterfaceWrapperHelper.newInstance(I_C_OrderLine.class);
		orderLine.setDatePromised(datePromised);
		orderLine.setDateDelivered(dateDelivered);
		InterfaceWrapperHelper.save(orderLine);
		return orderLine.getC_OrderLine_ID();
	}

	private int createShipmentSchedule(@Nullable final Timestamp deliveryDate, @Nullable final Timestamp deliveryDateOverride)
	{
		final I_M_ShipmentSchedule shipmentSchedule = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule.class);
		shipmentSchedule.setDeliveryDate(deliveryDate);
		shipmentSchedule.setDeliveryDate_Override(deliveryDateOverride);
		// the delivery ADDRESS getBySelection resolves for an Outgoing planning - not what this test is about,
		// but required for the selection to resolve at all
		shipmentSchedule.setC_BPartner_ID(BPARTNER_ID);
		shipmentSchedule.setC_BPartner_Location_ID(BPARTNER_LOCATION_ID);
		InterfaceWrapperHelper.save(shipmentSchedule);
		return shipmentSchedule.getM_ShipmentSchedule_ID();
	}

	private int createReceiptSchedule(@Nullable final Timestamp movementDate)
	{
		final I_M_ReceiptSchedule receiptSchedule = InterfaceWrapperHelper.newInstance(I_M_ReceiptSchedule.class);
		receiptSchedule.setMovementDate(movementDate);
		receiptSchedule.setC_BPartner_ID(BPARTNER_ID);
		receiptSchedule.setC_BPartner_Location_ID(BPARTNER_LOCATION_ID);
		InterfaceWrapperHelper.save(receiptSchedule);
		return receiptSchedule.getM_ReceiptSchedule_ID();
	}

	private int warehouseId()
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

	private I_M_Delivery_Planning deliveryPlanning(@NonNull final String transportDirection)
	{
		final I_M_Delivery_Planning record = InterfaceWrapperHelper.newInstance(I_M_Delivery_Planning.class);
		record.setTransportDirection(transportDirection);
		record.setM_Product_ID(PRODUCT_ID);
		record.setC_UOM_ID(uom.getC_UOM_ID());
		record.setPlannedLoadedQuantity(BigDecimal.TEN);
		record.setPlannedDischargeQuantity(BigDecimal.ONE);
		// the OTHER address getBySelection resolves - the warehouse, for either direction
		record.setM_Warehouse_ID(warehouseId());
		InterfaceWrapperHelper.save(record);
		return record;
	}

	private I_M_ShipperTransportation draftDeliveryInstruction(@NonNull final String documentNo)
	{
		final I_M_ShipperTransportation record = InterfaceWrapperHelper.newInstance(I_M_ShipperTransportation.class);
		record.setDocumentNo(documentNo);
		record.setDocStatus(DocStatus.Drafted.getCode());
		// contaminating dates: different from anything the order/schedule below carries, so a survivor of THESE
		// after deallocation would prove the reset did not run
		record.setETD(day(20));
		record.setETA(day(21));
		record.setATD(day(20));
		record.setATA(day(21));
		record.setLoadingTime("20:00");
		record.setDeliveryTime("21:00");
		InterfaceWrapperHelper.save(record);
		return record;
	}

	/** Allocates and syncs down, exactly as {@code combine}/{@code addTo} leave a planning: conforming to the instruction. */
	private DeliveryPlanningId allocate(@NonNull final I_M_ShipperTransportation instruction, @NonNull final I_M_Delivery_Planning planning)
	{
		final DeliveryPlanningId deliveryPlanningId = DeliveryPlanningId.ofRepoId(planning.getM_Delivery_Planning_ID());
		final ShipperTransportationId instructionId = ShipperTransportationId.ofRepoId(instruction.getM_ShipperTransportation_ID());

		deliveryInstructionService.createAllocations(
				instructionId,
				ImmutableList.of(DeliveryPlanningAllocCreateRequest.builder()
						.deliveryPlanningId(deliveryPlanningId)
						.shippingPackage(DeliveryPlanningAllocCreateRequest.ShippingPackageData.builder()
								.productId(ProductId.ofRepoId(PRODUCT_ID))
								.uomId(UomId.ofRepoId(uom.getC_UOM_ID()))
								.build())
						.build()));
		deliveryInstructionService.updateDeliveryPlanningsFromInstruction(ImmutableList.of(deliveryPlanningId), instructionId);

		return deliveryPlanningId;
	}

	private static I_M_Delivery_Planning reload(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		return InterfaceWrapperHelper.load(deliveryPlanningId, I_M_Delivery_Planning.class);
	}

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private IQueryFilter<I_M_Delivery_Planning> selectionOf(@NonNull final DeliveryPlanningId... deliveryPlanningIds)
	{
		return queryBL.createCompositeQueryFilter(I_M_Delivery_Planning.class)
				.addInArrayFilter(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID, ImmutableList.copyOf(deliveryPlanningIds));
	}

	// ------------------------------------------------------------------ tests

	@Nested
	@DisplayName("remove-from")
	class RemoveFrom
	{
		@Test
		@DisplayName("an Outgoing planning's dates are recomputed from the order's PreparationDate and the shipment schedule")
		void outgoing_recomputesFromOrderAndShipmentSchedule()
		{
			final int orderId = createOrder(day(1));
			final int orderLineId = createOrderLine(day(9), day(2));
			final int shipmentScheduleId = createShipmentSchedule(day(3), null);

			final I_M_Delivery_Planning planning = deliveryPlanning(X_M_Delivery_Planning.TRANSPORTDIRECTION_Outgoing);
			planning.setC_Order_ID(orderId);
			planning.setC_OrderLine_ID(orderLineId);
			planning.setM_ShipmentSchedule_ID(shipmentScheduleId);
			InterfaceWrapperHelper.save(planning);

			final I_M_ShipperTransportation instruction = draftDeliveryInstruction("RESET-1");
			final DeliveryPlanningId deliveryPlanningId = allocate(instruction, planning);
			assertThat(reload(deliveryPlanningId).getETD()).as("sanity: the planning is contaminated by the instruction before removal").isEqualTo(day(20));

			deliveryPlanningService.removeFrom(selectionOf(deliveryPlanningId));

			final I_M_Delivery_Planning reset = reload(deliveryPlanningId);
			assertThat(reset.getETD()).as("ETD from the order's PreparationDate").isEqualTo(day(1));
			assertThat(reset.getATD()).as("ATD follows ETD, same as at creation").isEqualTo(day(1));
			assertThat(reset.getETA()).as("ETA from the shipment schedule's DeliveryDate").isEqualTo(day(3));
			assertThat(reset.getATA()).as("ATA from the order line's DateDelivered").isEqualTo(day(2));
			assertThat(reset.getLoadingTime()).as("no order-derived source, cleared like a freshly generated planning").isNull();
			assertThat(reset.getDeliveryTime()).isNull();
		}

		@Test
		@DisplayName("an Incoming planning's dates are recomputed from the order's PreparationDate and the receipt schedule")
		void incoming_recomputesFromOrderAndReceiptSchedule()
		{
			final int orderId = createOrder(day(5));
			final int orderLineId = createOrderLine(day(9), day(7));
			final int receiptScheduleId = createReceiptSchedule(day(6));

			final I_M_Delivery_Planning planning = deliveryPlanning(X_M_Delivery_Planning.TRANSPORTDIRECTION_Incoming);
			planning.setC_Order_ID(orderId);
			planning.setC_OrderLine_ID(orderLineId);
			planning.setM_ReceiptSchedule_ID(receiptScheduleId);
			InterfaceWrapperHelper.save(planning);

			final I_M_ShipperTransportation instruction = draftDeliveryInstruction("RESET-2");
			final DeliveryPlanningId deliveryPlanningId = allocate(instruction, planning);

			deliveryPlanningService.removeFrom(selectionOf(deliveryPlanningId));

			final I_M_Delivery_Planning reset = reload(deliveryPlanningId);
			assertThat(reset.getETD()).isEqualTo(day(5));
			assertThat(reset.getATD()).isEqualTo(day(5));
			assertThat(reset.getETA()).as("ETA from the receipt schedule's MovementDate").isEqualTo(day(6));
			assertThat(reset.getATA()).as("ATA from the order line's DateDelivered").isEqualTo(day(7));
		}

		@Test
		@DisplayName("an unset shipment delivery date falls back to the order line's DatePromised, exactly as generation does")
		void outgoing_unsetShipmentDate_fallsBackToDatePromised()
		{
			final int orderId = createOrder(day(1));
			final int orderLineId = createOrderLine(day(4), null);
			final int shipmentScheduleId = createShipmentSchedule(null, null);

			final I_M_Delivery_Planning planning = deliveryPlanning(X_M_Delivery_Planning.TRANSPORTDIRECTION_Outgoing);
			planning.setC_Order_ID(orderId);
			planning.setC_OrderLine_ID(orderLineId);
			planning.setM_ShipmentSchedule_ID(shipmentScheduleId);
			InterfaceWrapperHelper.save(planning);

			final I_M_ShipperTransportation instruction = draftDeliveryInstruction("RESET-3");
			final DeliveryPlanningId deliveryPlanningId = allocate(instruction, planning);

			deliveryPlanningService.removeFrom(selectionOf(deliveryPlanningId));

			final I_M_Delivery_Planning reset = reload(deliveryPlanningId);
			assertThat(reset.getETA()).as("no shipment delivery date at all, so the order line's promised date is used").isEqualTo(day(4));
			assertThat(reset.getATA())
					.as("the generation command's own asymmetry, faithfully mirrored: ATA is coalesced from the RAW "
							+ "shipment date (null here), never from the DatePromised fallback that only ETA gets")
					.isNull();
		}

		@Test
		@DisplayName("a planning with no order at all resets its order-derived fields to null, not to whatever it carried")
		void noOrder_resetsToNull()
		{
			final int shipmentScheduleId = createShipmentSchedule(day(3), null);

			final I_M_Delivery_Planning planning = deliveryPlanning(X_M_Delivery_Planning.TRANSPORTDIRECTION_Outgoing);
			planning.setM_ShipmentSchedule_ID(shipmentScheduleId);
			InterfaceWrapperHelper.save(planning);

			final I_M_ShipperTransportation instruction = draftDeliveryInstruction("RESET-4");
			final DeliveryPlanningId deliveryPlanningId = allocate(instruction, planning);

			deliveryPlanningService.removeFrom(selectionOf(deliveryPlanningId));

			final I_M_Delivery_Planning reset = reload(deliveryPlanningId);
			assertThat(reset.getETD()).as("no order, so no PreparationDate to derive ETD from").isNull();
			assertThat(reset.getATD()).isNull();
			assertThat(reset.getETA()).as("the shipment schedule alone is enough for ETA, order or not").isEqualTo(day(3));
			assertThat(reset.getATA()).as("no order LINE, so no DateDelivered - the actual has nothing to derive from").isNull();
		}

		@Test
		@DisplayName("a whole selection resets in ONE batch load per collaborator, never one per row")
		void batchLoadsCollaboratorsOnceForTheWholeSelection()
		{
			final int orderIdA = createOrder(day(1));
			final int shipmentScheduleIdA = createShipmentSchedule(day(3), null);
			final int orderIdB = createOrder(day(11));
			final int receiptScheduleIdB = createReceiptSchedule(day(13));

			final I_M_Delivery_Planning planningA = deliveryPlanning(X_M_Delivery_Planning.TRANSPORTDIRECTION_Outgoing);
			planningA.setC_Order_ID(orderIdA);
			planningA.setM_ShipmentSchedule_ID(shipmentScheduleIdA);
			InterfaceWrapperHelper.save(planningA);

			final I_M_Delivery_Planning planningB = deliveryPlanning(X_M_Delivery_Planning.TRANSPORTDIRECTION_Incoming);
			planningB.setC_Order_ID(orderIdB);
			planningB.setM_ReceiptSchedule_ID(receiptScheduleIdB);
			InterfaceWrapperHelper.save(planningB);

			final I_M_ShipperTransportation instruction = draftDeliveryInstruction("RESET-6");
			final DeliveryPlanningId idA = allocate(instruction, planningA);
			final DeliveryPlanningId idB = allocate(instruction, planningB);

			Mockito.clearInvocations(orderDAO, receiptScheduleDAO, shipmentScheduleBL);

			deliveryPlanningService.removeFrom(selectionOf(idA, idB));

			assertThat(reload(idA).getETD()).isEqualTo(day(1));
			assertThat(reload(idB).getETD()).isEqualTo(day(11));

			// orders are read ONLY by the reset; receipt/shipment schedules are read TWICE - once by getBySelection's
			// own address resolution (unrelated to this feature), once by the reset - but each of those two is itself
			// ONE batch load for the whole selection, never one per row, which is what this test actually pins
			Mockito.verify(orderDAO, Mockito.times(1)).getByIds(Mockito.any());
			Mockito.verify(orderDAO, Mockito.never()).getById(Mockito.any(OrderId.class));
			Mockito.verify(receiptScheduleDAO, Mockito.times(2)).getByIds(Mockito.any());
			Mockito.verify(receiptScheduleDAO, Mockito.never()).getById(Mockito.any());
			Mockito.verify(shipmentScheduleBL, Mockito.times(2)).getByIds(Mockito.any());
			Mockito.verify(shipmentScheduleBL, Mockito.never()).getById(Mockito.any());
		}
	}

	@Test
	@DisplayName("void: unlinkDeliveryPlannings (the OTHER deactivateAllocations overload) resets dates too")
	void unlinkDeliveryPlannings_voidPath_resetsDatesToo()
	{
		final int orderId = createOrder(day(1));
		final int shipmentScheduleId = createShipmentSchedule(day(3), null);

		final I_M_Delivery_Planning planning = deliveryPlanning(X_M_Delivery_Planning.TRANSPORTDIRECTION_Outgoing);
		planning.setC_Order_ID(orderId);
		planning.setM_ShipmentSchedule_ID(shipmentScheduleId);
		InterfaceWrapperHelper.save(planning);

		final I_M_ShipperTransportation instruction = draftDeliveryInstruction("RESET-5");
		final DeliveryPlanningId deliveryPlanningId = allocate(instruction, planning);

		deliveryPlanningService.unlinkDeliveryPlannings(ShipperTransportationId.ofRepoId(instruction.getM_ShipperTransportation_ID()));

		final I_M_Delivery_Planning reset = reload(deliveryPlanningId);
		assertThat(reset.getETD()).isEqualTo(day(1));
		assertThat(reset.getETA()).isEqualTo(day(3));
		assertThat(reset.getLoadingTime()).isNull();
	}

}
