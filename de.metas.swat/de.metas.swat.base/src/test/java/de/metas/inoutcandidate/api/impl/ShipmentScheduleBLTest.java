package de.metas.inoutcandidate.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.metas.adempiere.test.POTest;
import de.metas.inoutcandidate.api.OlAndSched;

public class ShipmentScheduleBLTest extends ShipmentScheduleTestBase
{

	/**
	 * Calls updateSchedule with an empty list.
	 */
	@Test
	public void testEmpty()
	{
		final String trxName = "trxName";
		final List<OlAndSched> olAndScheds = new ArrayList<>();

		final ShipmentScheduleBL shipmentScheduleBL = new ShipmentScheduleBL();
		shipmentScheduleBL.updateSchedules(POTest.CTX, olAndScheds, true, null, null, trxName);
	}
}
