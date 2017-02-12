package test.integration.swat.sales.shipment;

/*
 * #%L
 * de.metas.swat.ait
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.MOrderLine;
import org.compiere.process.DocAction;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.metas.adempiere.ait.event.AIntegrationTestDriver;
import de.metas.adempiere.ait.helper.Helper;
import de.metas.adempiere.ait.helper.IHelper;
import de.metas.adempiere.ait.helper.OrderHelper;
import de.metas.adempiere.ait.test.IntegrationTestRunner;
import de.metas.adempiere.model.I_C_Order;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.api.impl.ShipmentSchedulePA;
import de.metas.interfaces.I_C_OrderLine;

@RunWith(IntegrationTestRunner.class)
public class ShipmentTests extends AIntegrationTestDriver
{
	private static final int AD_PInstance_ID = 10;
	private static final int AD_User_ID = 20;

	@Before
	@Override
	public void setupAdempiere()
	{
		super.setupAdempiere();
		getHelper().createAndSetTrxName("Trx_" + getClass());

		// making sure that the table is not "polluted" from previous tests
		DB.executeUpdateEx("DELETE FROM " + ShipmentSchedulePA.M_SHIPMENT_SCHEDULE_SHIPMENT_RUN
				+ " WHERE AD_PInstance_ID=" + AD_PInstance_ID + " AND AD_User_ID=" + AD_User_ID, getHelper().getTrxName());
	}

	@Override
	public IHelper newHelper()
	{
		return new Helper();
	}

	/**
	 * Using {@link IShipmentSchedulePA} to created shipmentRun locks and to delete them again. All locks should be
	 * deleted
	 */
	@Test
	public void createAndDeleteUnprocessedShipmentScheduleLocks()
	{
		final List<OlAndSched> olsAndSchedsToLock = setupOlAndSchedsToLock(getHelper().getTrxName());

		final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
		shipmentSchedulePA.createLocksForShipmentRun(olsAndSchedsToLock, AD_PInstance_ID, AD_User_ID, getHelper().getTrxName());

		final int created = DB.getSQLValueEx(getHelper().getTrxName(), "SELECT count(1) from " + ShipmentSchedulePA.M_SHIPMENT_SCHEDULE_SHIPMENT_RUN
				+ " WHERE AD_PInstance_ID=" + AD_PInstance_ID + " AND AD_User_ID=" + AD_User_ID);
		assertEquals("Call to 'createLocksForShipmentRun()' should have created 4 records", 4, created);

		final int methodResult = shipmentSchedulePA.deleteUnprocessedLocksForShipmentRun(AD_PInstance_ID, AD_User_ID, getHelper().getTrxName());
		assertEquals("Call to 'deleteLocksForShipmentRun()' should have retured a result of 4", 4, methodResult);

		final int left = DB.getSQLValueEx(getHelper().getTrxName(), "SELECT count(1) from " + ShipmentSchedulePA.M_SHIPMENT_SCHEDULE_SHIPMENT_RUN
				+ " WHERE AD_PInstance_ID=" + AD_PInstance_ID + " AND AD_User_ID=" + AD_User_ID);
		assertEquals("After call to 'deleteLocksForShipmentRun()' there should be no records left", 0, left);
	}

	/**
	 * Using {@link IShipmentSchedulePA} to created shipmentRun locks and to delete them again. However, non of the
	 * locks should be deleted by the method, because they have already been marked as "Processed".
	 */
	@Test
	public void createAndDeleteProcessedShipmentScheduleLocks()
	{
		final List<OlAndSched> olsAndSchedsToLock = setupOlAndSchedsToLock(getHelper().getTrxName());

		final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
		shipmentSchedulePA.createLocksForShipmentRun(olsAndSchedsToLock, AD_PInstance_ID, AD_User_ID, getHelper().getTrxName());

		final int created = DB.getSQLValueEx(getHelper().getTrxName(), "SELECT count(1) from " + ShipmentSchedulePA.M_SHIPMENT_SCHEDULE_SHIPMENT_RUN
				+ " WHERE AD_PInstance_ID=" + AD_PInstance_ID + " AND AD_User_ID=" + AD_User_ID);
		assertEquals("Call to 'createLocksForShipmentRun()' should have created 4 records", 4, created);

		shipmentSchedulePA.markLocksForShipmentRunProcessed(AD_PInstance_ID, AD_User_ID, getHelper().getTrxName());

		getHelper().commitTrx(false);

		// now the records have been marked as processed and should not have been deleted by this method
		final int methodResult = shipmentSchedulePA.deleteUnprocessedLocksForShipmentRun(AD_PInstance_ID, AD_User_ID, getHelper().getTrxName());
		assertEquals("Call to 'deleteLocksForShipmentRun()' should have returned a result of 0", 0, methodResult);

		final int leftProcessed = DB.getSQLValueEx(getHelper().getTrxName(), "SELECT count(1) from " + ShipmentSchedulePA.M_SHIPMENT_SCHEDULE_SHIPMENT_RUN
				+ " WHERE Processed='Y' AND AD_PInstance_ID=" + AD_PInstance_ID + " AND AD_User_ID=" + AD_User_ID);
		assertEquals("All records should still be there, and marked as processed", 4, leftProcessed);

		// cleaning up (the actual test has been passed, but this needs to work, too ;-) )
		getHelper().runProcess_UpdateShipmentScheds();
		final int leftAfterUpdate = DB.getSQLValueEx(getHelper().getTrxName(), "SELECT count(1) from " + ShipmentSchedulePA.M_SHIPMENT_SCHEDULE_SHIPMENT_RUN
				+ " WHERE AD_PInstance_ID=" + AD_PInstance_ID + " AND AD_User_ID=" + AD_User_ID);
		assertEquals("All records should have been removed by 'UpdateShipmentScheds'", 0, leftAfterUpdate);

	}

	private List<OlAndSched> setupOlAndSchedsToLock(final String trxName)
	{
		final OrderHelper orderHelper = getHelper().mkOrderHelper();

		final I_C_Order order = orderHelper
				.setComplete(DocAction.STATUS_Completed)
				.addLine(IHelper.DEFAULT_ProductValue + "_0", 10, 11)
				.addLine(IHelper.DEFAULT_ProductValue + "_1", 10, 11)
				.addLine(IHelper.DEFAULT_ProductValue + "_2", 10, 11)
				.addLine(IHelper.DEFAULT_ProductValue + "_3", 10, 11)
				.createOrder();
		getHelper().runProcess_UpdateShipmentScheds();

		final List<OlAndSched> olsAndSchedsToLock = new ArrayList<OlAndSched>();

		final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
		for (final MOrderLine ol : orderHelper.getOrderPO(order).getLines())
		{
			olsAndSchedsToLock.add(
					new OlAndSched(
							InterfaceWrapperHelper.create(ol, I_C_OrderLine.class),
							shipmentSchedulePA.retrieveForOrderLine(Env.getCtx(), ol.getC_OrderLine_ID(), trxName)));
		}
		return olsAndSchedsToLock;
	}

}
