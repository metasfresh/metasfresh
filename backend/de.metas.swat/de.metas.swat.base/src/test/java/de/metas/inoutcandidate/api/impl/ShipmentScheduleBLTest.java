package de.metas.inoutcandidate.api.impl;

import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.*;

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
