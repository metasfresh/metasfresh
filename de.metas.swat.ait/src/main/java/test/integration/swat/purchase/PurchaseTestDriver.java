package test.integration.swat.purchase;

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


import org.adempiere.util.Constants;
import org.compiere.model.MInvoice;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.metas.adempiere.ait.event.AIntegrationTestDriver;
import de.metas.adempiere.ait.event.EventType;
import de.metas.adempiere.ait.helper.Helper;
import de.metas.adempiere.ait.helper.IHelper;
import de.metas.adempiere.ait.test.IntegrationTestRunner;
import de.metas.adempiere.model.I_C_Invoice;

@RunWith(IntegrationTestRunner.class)
public class PurchaseTestDriver extends AIntegrationTestDriver
{	
	@Override
	public IHelper newHelper()
	{
		return new Helper();
	}

	@Test
	public void commissionInvoice()
	{
		fireTestEvent(EventType.INVOICE_EMPLOYEE_CREATE_BEFORE, null);
		
		final I_C_Invoice invoice = getHelper().mkInvoiceHelper()
				.setSOTrx(Boolean.FALSE)
				.setDocBaseType(Constants.DOCBASETYPE_AEInvoice)
				.setComplete(MInvoice.DOCSTATUS_Completed)
				.addLine(null, 0, 10)
				.createInvoice();
		
		fireTestEvent(EventType.INVOICE_EMPLOYEE_CREATE_AFTER, invoice);
	}
	
	@AfterClass
	public static void allListenersCalled()
	{
		assertAllListenersCalled(PurchaseTestDriver.class);
	}
}
