package de.metas.order.split;

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
		command = new OrderSplitCommand();
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

		// Act — expect Task-8b stub to throw; we assert on the already-saved NEW header
		try
		{
			command.split(OrderSplitRequest.builder()
					.orderId(OrderId.ofRepoId(order.getC_Order_ID()))
					.build());
		}
		catch (final UnsupportedOperationException expected)
		{
			// Task 8b will replace this throw with line cloning
		}

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
}
