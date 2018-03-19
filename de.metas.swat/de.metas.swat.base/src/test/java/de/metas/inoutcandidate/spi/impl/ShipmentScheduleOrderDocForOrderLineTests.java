package de.metas.inoutcandidate.spi.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.ShipmentScheduleReferencedLine;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class ShipmentScheduleOrderDocForOrderLineTests
{

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

	}

	@Test
	public void createForOrderLineSchedule()
	{
		final Timestamp deliveryDate = TimeUtil.parseTimestamp("2017-09-26");

		final I_M_Warehouse wh = newInstance(I_M_Warehouse.class);
		save(wh);
		
		final I_C_BPartner billBPartner = newInstance(I_C_BPartner.class);
		save(billBPartner);

		final I_C_Order order = newInstance(I_C_Order.class);
		order.setBill_BPartner(billBPartner);
		order.setM_Warehouse(wh);
		order.setDatePromised(deliveryDate);
		save(order);

		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
		orderLine.setC_Order(order);
		save(orderLine);
		final TableRecordReference ref = TableRecordReference.of(orderLine);

		final I_M_ShipmentSchedule sched = newInstance(I_M_ShipmentSchedule.class);
		sched.setC_Order(order);
		sched.setC_OrderLine(orderLine);
		sched.setAD_Table_ID(ref.getAD_Table_ID());
		sched.setRecord_ID(ref.getRecord_ID());

		final ShipmentScheduleReferencedLine result = new ShipmentScheduleOrderReferenceProvider().provideFor(sched);
		assertThat(result.getDeliveryDate()).isEqualTo(deliveryDate);
		assertThat(result.getPreparationDate()).isNull();
	}
}
