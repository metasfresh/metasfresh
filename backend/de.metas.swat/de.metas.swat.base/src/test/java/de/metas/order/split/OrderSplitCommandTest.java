package de.metas.order.split;

import de.metas.order.OrderId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
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
}
