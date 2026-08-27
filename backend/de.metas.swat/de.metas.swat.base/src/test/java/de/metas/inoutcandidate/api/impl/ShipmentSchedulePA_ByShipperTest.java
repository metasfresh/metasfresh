package de.metas.inoutcandidate.api.impl;

import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.shipping.ShipperId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Shipper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ShipmentSchedulePA_ByShipperTest
{
	private ShipmentSchedulePA shipmentSchedulePA;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		shipmentSchedulePA = new ShipmentSchedulePA();
	}

	@Test
	void testRetrieveUnprocessedIdsByShipperId_OnlyUnprocessedOnSameShipperReturned()
	{
		// Given: two schedules on shipper A (one unprocessed, one processed) and one on shipper B
		final ShipperId shipperA = createShipper();
		final ShipperId shipperB = createShipper();

		final I_M_ShipmentSchedule schedA_Unprocessed = createShipmentSchedule(shipperA, false);
		final ShipmentScheduleId schedA_UnprocessedId = ShipmentScheduleId.ofRepoId(schedA_Unprocessed.getM_ShipmentSchedule_ID());

		final I_M_ShipmentSchedule schedA_Processed = createShipmentSchedule(shipperA, true);

		final I_M_ShipmentSchedule schedB_Unprocessed = createShipmentSchedule(shipperB, false);

		// When
		final Set<ShipmentScheduleId> result = shipmentSchedulePA.retrieveUnprocessedIdsByShipperId(shipperA);

		// Then: only the unprocessed shipper-A schedule is returned
		assertThat(result).containsExactly(schedA_UnprocessedId);
	}

	@Test
	void testRetrieveUnprocessedIdsByShipperId_UnknownShipperReturnsEmptySet()
	{
		// Given: an unknown shipper
		final ShipperId unknownShipper = ShipperId.ofRepoId(999999);

		// When
		final Set<ShipmentScheduleId> result = shipmentSchedulePA.retrieveUnprocessedIdsByShipperId(unknownShipper);

		// Then: an empty set is returned
		assertThat(result).isEmpty();
	}

	private ShipperId createShipper()
	{
		final I_M_Shipper shipper = InterfaceWrapperHelper.newInstance(I_M_Shipper.class);
		InterfaceWrapperHelper.saveRecord(shipper);
		return ShipperId.ofRepoId(shipper.getM_Shipper_ID());
	}

	private I_M_ShipmentSchedule createShipmentSchedule(final ShipperId shipperId, final boolean isProcessed)
	{
		final I_M_ShipmentSchedule schedule = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule.class);
		schedule.setM_Shipper_ID(shipperId.getRepoId());
		schedule.setProcessed(isProcessed);
		schedule.setAD_Table_ID(0);
		schedule.setRecord_ID(0);
		InterfaceWrapperHelper.saveRecord(schedule);
		return schedule;
	}
}
