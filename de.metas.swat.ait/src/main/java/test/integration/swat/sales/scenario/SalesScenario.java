package test.integration.swat.sales.scenario;

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


import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.compiere.model.X_C_DocType;
import org.compiere.model.X_M_InOut;
import org.compiere.process.DocAction;

import de.metas.adempiere.ait.event.AIntegrationTestDriver;
import de.metas.adempiere.ait.event.EventType;
import de.metas.adempiere.ait.helper.OrderHelper;
import de.metas.adempiere.ait.helper.OrderHelper.Order_InvoiceRule;
import de.metas.adempiere.model.I_C_Order;
import de.metas.inout.model.I_M_InOut;

public class SalesScenario
{

	private final AIntegrationTestDriver driver;
	
	public SalesScenario(AIntegrationTestDriver driver){
		this.driver = driver;
	}
	
	public I_C_Order standartOrderWithProduct(final String productValue, final String bPartnerName)
	{
		final Order_InvoiceRule invoiceRule = Order_InvoiceRule.AFTER_DELIVERY;

		final OrderHelper orderHelper = driver.getHelper().mkOrderHelper();

		driver.getHelper().addInventory(productValue, 1);

		final I_C_Order order = orderHelper
				.setDocSubType(X_C_DocType.DOCSUBTYPE_StandardOrder)
				.setBPartnerName(bPartnerName)
				.setInvoiceRule(invoiceRule)
				.setComplete(DocAction.STATUS_Completed)
				.addLine(productValue, 10, 10)
				.createOrder();

		driver.fireTestEvent(EventType.ORDER_STANDARD_COMPLETE_AFTER, order);
		
		standartOrderWithProduct(order);
		
		return order;
	}
	
	
	public I_C_Order standartOrderWithProduct(final I_C_Order order)
	{
		driver.getHelper().runProcess_UpdateShipmentScheds();

		driver.getHelper().runProcess_InOutGenerate(order.getC_Order_ID());

		final List<I_M_InOut> inouts = driver.getHelper().retrieveInOutsForOrders(new int[] { order.getC_Order_ID() }, driver.getTrxName());

		assertThat(inouts.size(), equalTo(1));
		assertThat(inouts.get(0).getDocStatus(), equalTo(X_M_InOut.DOCSTATUS_Completed));

		driver.fireTestEvent(EventType.SHIPMENT_COMPLETE_AFTER, inouts.get(0));
	
		return order;
	}
}
