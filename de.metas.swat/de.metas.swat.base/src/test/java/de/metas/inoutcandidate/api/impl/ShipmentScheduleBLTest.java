package de.metas.inoutcandidate.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.inout.util.DeliveryGroupCandidate;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.util.Env;
import org.junit.Before;
import org.junit.Test;

import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.ShipmentScheduleReferencedLine;
import de.metas.material.event.commons.OrderLineDescriptor;
import de.metas.shipping.ShipperId;

public class ShipmentScheduleBLTest
{
	private static final int SHIPPER_ID = 20;
	private static final int WAREHOUSE_ID = 35;

	private ShipmentScheduleBL shipmentScheduleBL;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		this.shipmentScheduleBL = ShipmentScheduleBL.newInstanceForUnitTesting();
	}

	/**
	 * Calls updateSchedule with an empty list.
	 */
	@Test
	public void updateSchedules_emptyList()
	{
		final List<OlAndSched> olAndScheds = new ArrayList<>();

		shipmentScheduleBL.updateSchedules(Env.getCtx(), olAndScheds, ITrx.TRXNAME_ThreadInherited);
	}

	@Test
	public void createGroup()
	{
		final I_M_ShipmentSchedule sched = newInstance(I_M_ShipmentSchedule.class);
		sched.setBPartnerAddress_Override("bPartnerAddress");
		sched.setM_Warehouse_Override_ID(WAREHOUSE_ID);
		save(sched);

		final ShipmentScheduleReferencedLine scheduleSourceDoc = ShipmentScheduleReferencedLine.builder()
				.groupId(10)
				.shipperId(ShipperId.optionalOfRepoId(SHIPPER_ID))
				.warehouseId(WarehouseId.ofRepoId(30)) // different from the sched's effective WH
				.documentLineDescriptor(OrderLineDescriptor.builder().build()) // documentLineDescriptor is not relevant for this test
				.build();

		// invoke method under test
		final DeliveryGroupCandidate result = shipmentScheduleBL.createGroup(scheduleSourceDoc, sched);

		assertThat(result.getGroupId()).isEqualTo(10);
		assertThat(result.getShipperId().get().getRepoId()).isEqualTo(SHIPPER_ID);
		assertThat(result.getWarehouseId().getRepoId()).isEqualTo(WAREHOUSE_ID);
		assertThat(result.getBPartnerAddress()).isEqualTo("bPartnerAddress");
	}

	@Test
	public void updateProcessedFlag()
	{
		final I_M_ShipmentSchedule sched = newInstance(I_M_ShipmentSchedule.class);
		sched.setQtyReserved(BigDecimal.TEN);

		shipmentScheduleBL.updateProcessedFlag(sched);
		assertThat(sched.isProcessed()).isFalse();

		sched.setIsClosed(true);
		shipmentScheduleBL.updateProcessedFlag(sched);
		assertThat(sched.isProcessed()).isTrue();
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
