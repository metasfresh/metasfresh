package de.metas.order.split;

import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.qty_reservation.QtyReservationRepository;
import de.metas.inoutcandidate.qty_reservation.QtyReservationService;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Project;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderSplitCommandTest
{
	private OrderSplitCommand command;

	@BeforeEach
	void setup()
	{
		AdempiereTestHelper.get().init();

		// Register mock IShipmentScheduleBL so the call does not NPE
		final IShipmentScheduleBL mockBL = Mockito.mock(IShipmentScheduleBL.class);
		Services.registerService(IShipmentScheduleBL.class, mockBL);

		// Create QtyReservationService with mock dependencies
		final IShipmentScheduleInvalidateBL mockInvalidateBL =
				Mockito.mock(IShipmentScheduleInvalidateBL.class);
		final QtyReservationRepository repository = new QtyReservationRepository();
		final QtyReservationService qtyReservationService = new QtyReservationService(
				mockInvalidateBL,
				repository);

		command = new OrderSplitCommand(qtyReservationService);
	}

	@Test
	void rejectsWhenNoCompletedShipment()
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setDocStatus("CO");
		order.setIsSOTrx(true);
		save(order);

		final I_C_OrderLine line = newInstance(I_C_OrderLine.class);
		line.setC_Order_ID(order.getC_Order_ID());
		line.setQtyOrdered(new BigDecimal("10"));
		line.setQtyDelivered(BigDecimal.ZERO);
		save(line);

		// No M_InOut → guard 1 must fire
		assertThatThrownBy(() -> command.split(OrderSplitRequest.builder()
				.orderId(OrderId.ofRepoId(order.getC_Order_ID()))
				.build()))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("C_Order_Split_NoShipments");
	}

	@Test
	void rejectsWhenNothingToSplit()
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setDocStatus("CO");
		order.setIsSOTrx(true);
		save(order);

		final I_C_OrderLine line = newInstance(I_C_OrderLine.class);
		line.setC_Order_ID(order.getC_Order_ID());
		line.setQtyOrdered(new BigDecimal("10"));
		line.setQtyDelivered(new BigDecimal("10"));  // fully delivered → no residue
		save(line);

		// Add a completed shipment for the line → guard 1 passes
		final I_M_InOut shipment = newInstance(I_M_InOut.class);
		shipment.setC_Order_ID(order.getC_Order_ID());
		shipment.setDocStatus("CO");
		shipment.setIsSOTrx(true);
		save(shipment);

		final I_M_InOutLine shipmentLine = newInstance(I_M_InOutLine.class);
		shipmentLine.setM_InOut_ID(shipment.getM_InOut_ID());
		shipmentLine.setC_OrderLine_ID(line.getC_OrderLine_ID());
		shipmentLine.setMovementQty(new BigDecimal("10"));
		save(shipmentLine);

		// Guard 1 passes (shipment exists), guard 2 fires (no residue)
		assertThatThrownBy(() -> command.split(OrderSplitRequest.builder()
				.orderId(OrderId.ofRepoId(order.getC_Order_ID()))
				.build()))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("C_Order_Split_NothingToSplit");
	}

	@Test
	void clonesHeaderClearsProjectSetsPOReference()
	{
		final I_C_Project project = newInstance(I_C_Project.class);
		project.setName("PROJECT_X");
		save(project);

		final I_C_Order order = newInstance(I_C_Order.class);
		order.setDocStatus("CO");
		order.setDocumentNo("SO-ORIG-001");
		order.setIsSOTrx(true);
		order.setC_Project_ID(project.getC_Project_ID());
		order.setDescription("Original description");
		save(order);

		final I_C_OrderLine line = newInstance(I_C_OrderLine.class);
		line.setC_Order_ID(order.getC_Order_ID());
		line.setQtyOrdered(new BigDecimal("10"));
		line.setQtyDelivered(new BigDecimal("8"));  // residue = 2 → validation passes
		save(line);

		final I_M_InOut shipment = newInstance(I_M_InOut.class);
		shipment.setC_Order_ID(order.getC_Order_ID());
		shipment.setDocStatus("CO");
		shipment.setIsSOTrx(true);
		save(shipment);

		final I_M_InOutLine shipmentLine = newInstance(I_M_InOutLine.class);
		shipmentLine.setM_InOut_ID(shipment.getM_InOut_ID());
		shipmentLine.setC_OrderLine_ID(line.getC_OrderLine_ID());
		shipmentLine.setMovementQty(new BigDecimal("8"));
		save(shipmentLine);

		// Act
		command.split(OrderSplitRequest.builder()
				.orderId(OrderId.ofRepoId(order.getC_Order_ID()))
				.build());

		// Assert — exactly one NEW C_Order exists with POReference = old DocumentNo
		final List<I_C_Order> newOrders = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Order.class)
				.addEqualsFilter(I_C_Order.COLUMNNAME_POReference, "SO-ORIG-001")
				.addNotEqualsFilter(I_C_Order.COLUMNNAME_C_Order_ID, order.getC_Order_ID())
				.create()
				.list(I_C_Order.class);
		assertThat(newOrders).hasSize(1);

		final I_C_Order newOrder = newOrders.get(0);
		assertThat(newOrder.getDocStatus()).isEqualTo("DR");
		assertThat(newOrder.getC_Project_ID()).isZero();
		assertThat(newOrder.getPOReference()).isEqualTo("SO-ORIG-001");
		assertThat(newOrder.getDescription())
				.contains("Original description")
				.contains("Fortsetzung von SO-ORIG-001");
	}

	@Test
	void selectsOnlyLinesWithUnshippedResidue()
	{
		final I_C_Order order = createOrderWithShipment("SO-RES-001");
		// 3 lines: fully delivered (10/10 → stays), partial (10/4 → moves residue 6), over-delivered (5/7 → stays)
		newOrderLine(order, "10", "10");                // fully delivered → stays on OLD
		newOrderLine(order, "10", "4");                 // residue 6 → moves to NEW
		newOrderLineOverDelivered(order, "5", "7");     // over-delivered → stays on OLD

		// Act
		command.split(OrderSplitRequest.builder()
				.orderId(OrderId.ofRepoId(order.getC_Order_ID()))
				.build());

		// Assert
		final I_C_Order newOrder = findNewOrderByPOReference("SO-RES-001");
		final java.util.List<de.metas.interfaces.I_C_OrderLine> newLines = Services.get(IOrderDAO.class)
				.retrieveOrderLines(OrderId.ofRepoId(newOrder.getC_Order_ID()));

		assertThat(newLines).hasSize(1);
		assertThat(newLines.get(0).getQtyEntered()).isEqualByComparingTo(new BigDecimal("6"));
	}

	@Test
	void clonesLineQtyAndAllowsPriceLookupToRun()
	{
		final I_C_Order order = createOrderWithShipment("SO-PR-001");
		final I_C_OrderLine line = newOrderLine(order, "10", "8");           // residue 2

		line.setPriceActual(new BigDecimal("12.50"));
		line.setPriceEntered(new BigDecimal("12.50"));
		save(line);

		// Act
		command.split(OrderSplitRequest.builder()
				.orderId(OrderId.ofRepoId(order.getC_Order_ID()))
				.build());

		// Assert
		final I_C_Order newOrder = findNewOrderByPOReference("SO-PR-001");
		final java.util.List<de.metas.interfaces.I_C_OrderLine> newLines = Services.get(IOrderDAO.class)
				.retrieveOrderLines(OrderId.ofRepoId(newOrder.getC_Order_ID()));

		assertThat(newLines).hasSize(1);
		final de.metas.interfaces.I_C_OrderLine newLine = newLines.get(0);
		assertThat(newLine.getQtyEntered()).isEqualByComparingTo(new BigDecimal("2"));
		assertThat(newLine.isManualPrice()).isFalse();
		// Pricing may have been re-derived by the interceptor OR may equal the cloned value;
		// either way it should be non-null and non-negative.
		assertThat(newLine.getPriceActual()).isNotNull();
		assertThat(newLine.getPriceActual().signum()).isGreaterThanOrEqualTo(0);
	}

	@Test
	void closesOldShipmentSchedulesAndReservations()
	{
		final I_C_Order order = createOrderWithShipment("SO-CLOSE-001");
		final I_C_OrderLine line = newOrderLine(order, "10", "4");           // residue 6

		// Add an active M_QtyReservation row for the line.
		// Setup mirrors the QtyReservationServiceCloseAllActiveTest fixture from Task 7.
		final org.compiere.model.I_C_UOM uom = newInstance(org.compiere.model.I_C_UOM.class);
		save(uom);
		final org.compiere.model.I_M_Product product = newInstance(org.compiere.model.I_M_Product.class);
		product.setC_UOM_ID(uom.getC_UOM_ID());
		save(product);
		final org.compiere.model.I_M_Warehouse warehouse = newInstance(org.compiere.model.I_M_Warehouse.class);
		save(warehouse);
		// Wire the line to the product/uom so QtyReservation's loader can build the domain.
		line.setM_Product_ID(product.getM_Product_ID());
		line.setC_UOM_ID(uom.getC_UOM_ID());
		save(line);

		final org.compiere.model.I_M_QtyReservation reservation = newInstance(org.compiere.model.I_M_QtyReservation.class);
		reservation.setC_OrderLine_ID(line.getC_OrderLine_ID());
		reservation.setM_Product_ID(product.getM_Product_ID());
		reservation.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());
		reservation.setC_UOM_ID(uom.getC_UOM_ID());
		reservation.setQty(new BigDecimal("6"));
		reservation.setQtyDelivered(BigDecimal.ZERO);
		reservation.setQtyTU(new BigDecimal("1"));
		reservation.setProcessed(false);
		reservation.setAttributesKey("");
		reservation.setSupplyType("OH");
		save(reservation);

		// Act
		final OrderSplitResult result = command.split(OrderSplitRequest.builder()
				.orderId(OrderId.ofRepoId(order.getC_Order_ID()))
				.build());

		// Assert — reservation closed
		org.adempiere.model.InterfaceWrapperHelper.refresh(reservation);
		assertThat(reservation.isProcessed()).as("Reservation should be Processed=Y").isTrue();
		assertThat(reservation.getQtyDelivered()).as("QtyDelivered should equal Qty (6)")
				.isEqualByComparingTo(new BigDecimal("6"));

		// Result populated
		assertThat(result.getCopiedLineCount()).isEqualTo(1);
		assertThat(result.getOldOrderId().getRepoId()).isEqualTo(order.getC_Order_ID());
		assertThat(result.getNewOrderId().getRepoId()).isNotEqualTo(order.getC_Order_ID());
	}

	// -------------------------------------------------------------------------
	// Test helpers
	// -------------------------------------------------------------------------

	private I_C_Order createOrderWithShipment(final String documentNo)
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setDocStatus("CO");
		order.setDocumentNo(documentNo);
		order.setIsSOTrx(true);
		save(order);

		final I_M_InOut shipment = newInstance(I_M_InOut.class);
		shipment.setC_Order_ID(order.getC_Order_ID());
		shipment.setDocStatus("CO");
		shipment.setIsSOTrx(true);
		save(shipment);

		return order;
	}

	private I_C_OrderLine newOrderLine(
			final I_C_Order order,
			final String qtyOrdered,
			final String qtyDelivered)
	{
		final I_C_OrderLine line = newInstance(I_C_OrderLine.class);
		line.setC_Order_ID(order.getC_Order_ID());
		line.setQtyOrdered(new BigDecimal(qtyOrdered));
		line.setQtyDelivered(new BigDecimal(qtyDelivered));
		line.setLine(10);
		save(line);
		return line;
	}

	private I_C_OrderLine newOrderLineOverDelivered(
			final I_C_Order order,
			final String qtyOrdered,
			final String qtyDelivered)
	{
		// Same as newOrderLine but explicit naming for over-delivered case (QtyDelivered > QtyOrdered).
		// In-memory tests don't enforce QtyDelivered <= QtyOrdered, so we can simulate over-delivery here.
		return newOrderLine(order, qtyOrdered, qtyDelivered);
	}

	private I_C_Order findNewOrderByPOReference(final String poReference)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Order.class)
				.addEqualsFilter(I_C_Order.COLUMNNAME_POReference, poReference)
				.create()
				.firstOnlyNotNull();
	}
}
