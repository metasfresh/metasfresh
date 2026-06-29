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

import java.util.Iterator;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.order.OrderLineId;
import de.metas.util.Services;
import de.metas.util.collections.IteratorUtils;

public class ReceiptScheduleDAOTest extends ReceiptScheduleTestBase
{
	@Test
	public void test_retrieve()
	{
		final I_M_ReceiptSchedule rs1 = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
		final I_M_ReceiptSchedule rs2 = createReceiptSchedule(bpartner2, warehouse1, date, product1_wh1, 10);
		final I_M_ReceiptSchedule rs3 = createReceiptSchedule(bpartner2, warehouse1, date, product1_wh1, 10);
		final I_M_ReceiptSchedule rs4 = createReceiptSchedule(bpartner2, warehouse1, date, product1_wh1, 10);
		final I_M_ReceiptSchedule rs5 = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);

		final IQuery<I_M_ReceiptSchedule> query = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ReceiptSchedule.class, getCtx(), ITrx.TRXNAME_None)
				.create();
		final Iterator<I_M_ReceiptSchedule> schedules = receiptScheduleDAO.retrieve(query);

		final List<I_M_ReceiptSchedule> schedulesList = IteratorUtils.asList(schedules);

		Assertions.assertEquals(5, schedulesList.size(), "Invalid size");
		Assertions.assertEquals(rs1, schedulesList.get(0), "Invalid receipt schedule at index 0");
		Assertions.assertEquals(rs5, schedulesList.get(1), "Invalid receipt schedule at index 1");
		Assertions.assertEquals(rs2, schedulesList.get(2), "Invalid receipt schedule at index 2");
		Assertions.assertEquals(rs3, schedulesList.get(3), "Invalid receipt schedule at index 3");
		Assertions.assertEquals(rs4, schedulesList.get(4), "Invalid receipt schedule at index 4");
	}

	@Test
	public void deleteByOrderLineId_noAllocations_deletesSchedule()
	{
		final OrderLineId orderLineId = OrderLineId.ofRepoId(createOrderLine(createOrder(warehouse1), product1_wh1).getC_OrderLine_ID());
		createReceiptScheduleForOrderLine(bpartner1, warehouse1, date, product1_wh1, 10, orderLineId);

		receiptScheduleDAO.deleteByOrderLineId(orderLineId);

		final boolean stillExists = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ReceiptSchedule.class)
				.addEqualsFilter(I_M_ReceiptSchedule.COLUMNNAME_C_OrderLine_ID, orderLineId)
				.create()
				.anyMatch();
		Assertions.assertFalse(stillExists, "Receipt schedule should have been deleted");
	}

	@Test
	public void deleteByOrderLineId_withUnreceivedAlloc_deletesSchedule()
	{
		final OrderLineId orderLineId = OrderLineId.ofRepoId(createOrderLine(createOrder(warehouse1), product1_wh1).getC_OrderLine_ID());
		final I_M_ReceiptSchedule rs = createReceiptScheduleForOrderLine(bpartner1, warehouse1, date, product1_wh1, 10, orderLineId);
		createReceiptScheduleAlloc(rs, 0 /* no M_InOutLine_ID = not yet received */);

		receiptScheduleDAO.deleteByOrderLineId(orderLineId);

		Assertions.assertFalse(
				InterfaceWrapperHelper.isActive(InterfaceWrapperHelper.load(rs.getM_ReceiptSchedule_ID(), I_M_ReceiptSchedule.class)),
				"Receipt schedule should have been deleted");
	}

	@Test
	public void deleteByOrderLineId_withReceivedAlloc_throws()
	{
		final OrderLineId orderLineId = OrderLineId.ofRepoId(createOrderLine(createOrder(warehouse1), product1_wh1).getC_OrderLine_ID());
		final I_M_ReceiptSchedule rs = createReceiptScheduleForOrderLine(bpartner1, warehouse1, date, product1_wh1, 10, orderLineId);
		createReceiptScheduleAlloc(rs, 999 /* non-zero M_InOutLine_ID = goods already received */);

		Assertions.assertThrows(
				AdempiereException.class,
				() -> receiptScheduleDAO.deleteByOrderLineId(orderLineId),
				"Should throw when a receipt already exists for the schedule");
	}
}
