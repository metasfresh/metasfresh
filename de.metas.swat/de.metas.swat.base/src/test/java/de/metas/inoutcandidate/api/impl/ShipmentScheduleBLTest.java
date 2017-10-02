package de.metas.inoutcandidate.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.util.Env;
import org.junit.Test;

import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.ShipmentScheduleOrderDoc;

public class ShipmentScheduleBLTest extends ShipmentScheduleTestBase
{

	/**
	 * Calls updateSchedule with an empty list.
	 */
	@Test
	public void testEmpty()
	{
		final List<OlAndSched> olAndScheds = new ArrayList<>();

		final ShipmentScheduleBL shipmentScheduleBL = new ShipmentScheduleBL();
		shipmentScheduleBL.updateSchedules(Env.getCtx(), olAndScheds, ITrx.TRXNAME_ThreadInherited);
	}
	
	@Test
	public void createInout()
	{
		final I_M_ShipmentSchedule sched = newInstance(I_M_ShipmentSchedule.class);
		sched.setBPartnerAddress_Override("bPartnerAddress");
		save(sched);
		
		final ShipmentScheduleOrderDoc scheduleSourceDoc = ShipmentScheduleOrderDoc.builder()
				.groupId(10)
				.shipperId(20)
				.warehouseId(30)
				.build();
		
		new ShipmentScheduleBL().createGroup(scheduleSourceDoc, sched);
	}
}
