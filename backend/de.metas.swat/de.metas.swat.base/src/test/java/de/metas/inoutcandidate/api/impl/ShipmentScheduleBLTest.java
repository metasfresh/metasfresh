package de.metas.inoutcandidate.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Properties;
import java.util.Set;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_OrderLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.inoutcandidate.api.IDeliverRequest;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleHandlerBL;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.ModelWithoutShipmentScheduleVetoer;
import de.metas.inoutcandidate.spi.ShipmentScheduleHandler;
import de.metas.util.Services;

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
		assertThat(schedule.getQtyOrdered_Override()).isEqualByComparingTo("23")
				.as("closing a shipmentschedule may not fiddle with its QtyOrdered_Override value");
		assertThat(schedule.getQtyToDeliver_Override()).isEqualByComparingTo("24")
				.as("closing a shipmentschedule may not fiddle with its QtyToDeliver_Override value");
	}

	@Test
	public void openProcessedShipmentSchedule()
	{
		setupMockShipmentScheduleHandlerBL();

		final I_M_ShipmentSchedule schedule = newInstance(I_M_ShipmentSchedule.class);
		schedule.setIsClosed(true);

		schedule.setQtyOrdered_Calculated(BigDecimal.TEN);
		schedule.setQtyOrdered(new BigDecimal("5"));
		schedule.setQtyDelivered(new BigDecimal("5"));
		schedule.setQtyOrdered_Override(new BigDecimal("23"));
		schedule.setQtyToDeliver_Override(new BigDecimal("24"));

		shipmentScheduleBL.openShipmentSchedule(schedule);

		assertThat(schedule.isClosed()).isFalse();
		assertThat(schedule.getQtyOrdered_Override())
				.as("opening a shipmentschedule may not fiddle with its QtyOrdered_Override value")
				.isEqualByComparingTo("23");
		assertThat(schedule.getQtyOrdered_Calculated())
				.as("opening a shipmentschedule may not fiddle with its QtyOrdered_Calculated value")
				.isEqualByComparingTo(BigDecimal.TEN);

		assertThat(schedule.getQtyOrdered())
				.as("opening a shipmentschedule shall restore its QtyOrdered from its QtyOrdered_Override or .._Calculated value")
				.isEqualByComparingTo("23");
	}

	private void setupMockShipmentScheduleHandlerBL()
	{
		final IShipmentScheduleHandlerBL mockHandler = new IShipmentScheduleHandlerBL()
		{
			// @formatter:off
			@Override public void updateShipmentScheduleFromReferencedRecord(I_M_ShipmentSchedule shipmentScheduleRecord)	{ }
			@Override public void registerVetoer(ModelWithoutShipmentScheduleVetoer vetoer, String tableName) { }
			@Override public <T extends ShipmentScheduleHandler> void registerHandler(T handler) { }
			@Override public ShipmentScheduleHandler getHandlerFor(I_M_ShipmentSchedule sched) { return null; }
			@Override public Set<ShipmentScheduleId> createMissingCandidates(Properties ctx) { return null; }
			@Override public IDeliverRequest createDeliverRequest(I_M_ShipmentSchedule sched, I_C_OrderLine salesOrderLine) { return null; }
			// @formatter:on
		};
		Services.registerService(IShipmentScheduleHandlerBL.class, mockHandler);
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
	public void isConsolidateVetoedByOrderOfSched_C_Order_ID_zero()
	{
		final I_M_ShipmentSchedule shipmentSchedule = newInstance(I_M_ShipmentSchedule.class);
		shipmentSchedule.setC_Order_ID(0);

		assertThat(shipmentScheduleBL.isConsolidateVetoedByOrderOfSched(shipmentSchedule)).isFalse();
	}
}
