package de.metas.inoutcandidate.api.impl;

/*
 * #%L
 * de.metas.swat.base
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


import java.util.Arrays;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;

/**
 * Tests {@link ShipmentScheduleAllocDAO}.
 *
 * @author tsa
 *
 */
public class ShipmentScheduleAllocDAOTest
{
	private ShipmentScheduleAllocDAO dao;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		dao = (ShipmentScheduleAllocDAO)Services.get(IShipmentScheduleAllocDAO.class);
	}

	/**
	 * Some simple tests on
	 * <ul>
	 * <li>{@link IShipmentScheduleAllocDAO#retrieveNotOnShipmentLineRecords(I_M_ShipmentSchedule, Class)}
	 * <li> {@link IShipmentScheduleAllocDAO#retrieveOnShipmentLineRecordsQuery(I_M_ShipmentSchedule)}
	 * </ul>
	 *
	 * to make sure:
	 * <ul>
	 * <li>are working correctly
	 * <li>are "delivered" and "not delivered" methods are complementary
	 * </ul>
	 */
	@Test
	public void test_retrievePickedNotDeliveredRecords()
	{
		final I_M_ShipmentSchedule ss = createM_ShipmentSchedule();
		final I_M_ShipmentSchedule_QtyPicked qp1 = createShipmentScheduleQtyPickedRecord(ss, 0);
		final I_M_ShipmentSchedule_QtyPicked qp2 = createShipmentScheduleQtyPickedRecord(ss, 1);
		final I_M_ShipmentSchedule_QtyPicked qp3 = createShipmentScheduleQtyPickedRecord(ss, 0);
		final I_M_ShipmentSchedule_QtyPicked qp4 = createShipmentScheduleQtyPickedRecord(ss, 2);

		Assert.assertEquals(
				"Expected picked but not delivered",
				Arrays.asList(qp1, qp3),
				dao.retrieveNotOnShipmentLineRecords(ss, I_M_ShipmentSchedule_QtyPicked.class));

		Assert.assertEquals(
				"Expected picked AND delivered",
				Arrays.asList(qp2, qp4),
				dao.retrieveOnShipmentLineRecordsQuery(ss).create().list());

	}

	private final I_M_ShipmentSchedule createM_ShipmentSchedule()
	{
		final I_M_ShipmentSchedule ss = InterfaceWrapperHelper.create(Env.getCtx(), I_M_ShipmentSchedule.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(ss);
		return ss;
	}

	private final I_M_ShipmentSchedule_QtyPicked createShipmentScheduleQtyPickedRecord(final I_M_ShipmentSchedule ss, final int inoutLineId)
	{
		final I_M_ShipmentSchedule_QtyPicked record = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule_QtyPicked.class, ss);
		record.setM_ShipmentSchedule(ss);
		record.setM_InOutLine_ID(inoutLineId);
		InterfaceWrapperHelper.save(record);
		return record;
	}
}
