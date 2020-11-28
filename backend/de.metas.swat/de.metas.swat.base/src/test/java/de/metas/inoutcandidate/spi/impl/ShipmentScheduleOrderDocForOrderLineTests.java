package de.metas.inoutcandidate.spi.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZonedDateTime;

import de.metas.common.util.time.SystemTime;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.bpartner.BPartnerId;
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
	private WarehouseId warehouseId;
	private BPartnerId billBPartnerId;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		createMasterdata();
	}

	private void createMasterdata()
	{
		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		saveRecord(warehouse);
		warehouseId = WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID());

		final I_C_BPartner billBPartner = newInstance(I_C_BPartner.class);
		saveRecord(billBPartner);
		billBPartnerId = BPartnerId.ofRepoId(billBPartner.getC_BPartner_ID());
	}

	private I_M_ShipmentSchedule createShipmentSchedule(final I_C_Order order, final I_C_OrderLine orderLine)
	{
		final TableRecordReference orderLineRef = TableRecordReference.of(orderLine);
		final I_M_ShipmentSchedule sched = newInstance(I_M_ShipmentSchedule.class);
		sched.setC_Order_ID(order.getC_Order_ID());
		sched.setC_OrderLine_ID(orderLineRef.getRecord_ID());
		sched.setAD_Table_ID(orderLineRef.getAD_Table_ID());
		sched.setRecord_ID(orderLineRef.getRecord_ID());
		return sched;
	}

	private I_C_OrderLine createOrderLine(final I_C_Order order)
	{
		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
		orderLine.setC_Order_ID(order.getC_Order_ID());
		orderLine.setM_Shipper_ID(1);
		saveRecord(orderLine);
		return orderLine;
	}

	private I_C_Order createOrder()
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setBill_BPartner_ID(billBPartnerId.getRepoId());
		order.setM_Warehouse_ID(warehouseId.getRepoId());
		saveRecord(order);
		return order;
	}

	@Test
	public void createForOrderLineSchedule()
	{
		final I_C_Order order = createOrder();
		final I_C_OrderLine orderLine = createOrderLine(order);
		final I_M_ShipmentSchedule sched = createShipmentSchedule(order, orderLine);

		final ZonedDateTime date_2017_09_26 = LocalDate.of(2017, Month.SEPTEMBER, 26).atStartOfDay(de.metas.common.util.time.SystemTime.zoneId());
		order.setDatePromised(TimeUtil.asTimestamp(date_2017_09_26));
		saveRecord(order);

		final ShipmentScheduleReferencedLine result = new ShipmentScheduleOrderReferenceProvider().provideFor(sched);
		assertThat(result.getDeliveryDate()).isEqualTo(date_2017_09_26);
		assertThat(result.getPreparationDate()).isNull();
	}

	@Test
	public void createForOrderLineSchedule_PresetDateShipped()
	{
		final I_C_Order order = createOrder();
		final I_C_OrderLine orderLine = createOrderLine(order);
		final I_M_ShipmentSchedule sched = createShipmentSchedule(order, orderLine);

		final ZonedDateTime date_2019_09_01 = LocalDate.of(2019, Month.SEPTEMBER, 1).atStartOfDay(de.metas.common.util.time.SystemTime.zoneId());
		order.setDatePromised(TimeUtil.asTimestamp(date_2019_09_01));
		saveRecord(order);

		final ZonedDateTime date_2019_09_02 = LocalDate.of(2019, Month.SEPTEMBER, 2).atStartOfDay(de.metas.common.util.time.SystemTime.zoneId());
		final ZonedDateTime date_2019_09_03 = LocalDate.of(2019, Month.SEPTEMBER, 3).atStartOfDay(de.metas.common.util.time.SystemTime.zoneId());
		orderLine.setDatePromised(TimeUtil.asTimestamp(date_2019_09_02));
		orderLine.setPresetDateShipped(TimeUtil.asTimestamp(date_2019_09_03));
		orderLine.setPresetDateInvoiced(SystemTime.asTimestamp()); // just to make sure it's not used
		saveRecord(orderLine);

		final ShipmentScheduleReferencedLine result = new ShipmentScheduleOrderReferenceProvider().provideFor(sched);
		assertThat(result.getDeliveryDate()).isEqualTo(date_2019_09_03);
		assertThat(result.getPreparationDate()).isNull();
	}
}
