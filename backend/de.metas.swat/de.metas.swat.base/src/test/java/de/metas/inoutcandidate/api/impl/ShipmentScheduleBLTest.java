package de.metas.inoutcandidate.api.impl;

import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.X_M_ShipmentSchedule;
import de.metas.order.OrderId;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ShipmentScheduleBLTest
{
	private ShipmentScheduleBL shipmentScheduleBL;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		shipmentScheduleBL = (ShipmentScheduleBL)Services.get(IShipmentScheduleBL.class);
	}

	@Test
	public void closeShipmentSchedule()
	{
		final I_M_ShipmentSchedule schedule = newInstance(I_M_ShipmentSchedule.class);
		schedule.setQtyOrdered_Override(new BigDecimal("23"));
		schedule.setQtyToDeliver_Override(new BigDecimal("24"));
		assertThat(schedule.isClosed()).isFalse();

		shipmentScheduleBL.closeShipmentSchedule(schedule);

		save(schedule);
		refresh(schedule);

		assertThat(schedule.isClosed()).isTrue();
		assertThat(schedule.getQtyOrdered_Override())
				.as("closing a shipment schedule may not fiddle with its QtyOrdered_Override value")
				.isEqualByComparingTo("23");
		assertThat(schedule.getQtyToDeliver_Override())
				.as("closing a shipment schedule may not fiddle with its QtyToDeliver_Override value")
				.isEqualByComparingTo("24");
	}

	@Test
	public void updateQtyOrdered()
	{
		final I_M_ShipmentSchedule schedule = newInstance(I_M_ShipmentSchedule.class);
		schedule.setIsClosed(true);
		schedule.setQtyDelivered(BigDecimal.ONE);
		schedule.setQtyOrdered_Override(new BigDecimal("23"));
		schedule.setQtyOrdered_Calculated(new BigDecimal("24"));

		shipmentScheduleBL.updateQtyOrdered(schedule);

		assertThat(schedule.getQtyOrdered()).isEqualByComparingTo(BigDecimal.ONE);
	}

	@Test
	public void isConsolidateVetoedByOrderOfSchedule_C_Order_ID_zero()
	{
		final I_M_ShipmentSchedule shipmentSchedule = newInstance(I_M_ShipmentSchedule.class);
		shipmentSchedule.setC_Order_ID(0);

		assertThat(shipmentScheduleBL.isConsolidateVetoedByOrderOfSched(shipmentSchedule)).isFalse();
	}

	@Nested
	class assertSalesOrderCanBeReactivated
	{
		private OrderId salesOrderId;
		private I_M_ShipmentSchedule shipmentSchedule;

		@BeforeEach
		void beforeEach()
		{
			final I_C_Order salesOrder = newInstance(I_C_Order.class);
			salesOrder.setIsSOTrx(true);
			saveRecord(salesOrder);
			salesOrderId = OrderId.ofRepoId(salesOrder.getC_Order_ID());

			shipmentSchedule = newInstance(I_M_ShipmentSchedule.class);
			shipmentSchedule.setC_Order_ID(salesOrderId.getRepoId());
			saveRecord(shipmentSchedule);
		}

		@Test
		void nothingScheduledForPicking_nothingExported()
		{
			shipmentScheduleBL.assertSalesOrderCanBeReactivated(salesOrderId);
		}

		@Test
		void alreadyScheduledForPicking()
		{
			shipmentSchedule.setIsScheduledForPicking(true);
			saveRecord(shipmentSchedule);

			assertThatThrownBy(() -> shipmentScheduleBL.assertSalesOrderCanBeReactivated(salesOrderId))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining(ShipmentScheduleBL.MSG_REACTIVATION_VOID_NOT_ALLOWED_BECAUSE_SCHEDULED_FOR_PICKING.toAD_Message());
		}

		@Test
		void alreadyExported()
		{
			shipmentSchedule.setExportStatus(X_M_ShipmentSchedule.EXPORTSTATUS_EXPORTED);
			saveRecord(shipmentSchedule);

			assertThatThrownBy(() -> shipmentScheduleBL.assertSalesOrderCanBeReactivated(salesOrderId))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining(ShipmentScheduleBL.MSG_REACTIVATION_VOID_NOT_ALLOWED_BECAUSE_ALREADY_EXPORTED.toAD_Message());
		}
	}
}
