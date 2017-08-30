package test.integration.swat;

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


import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import de.metas.adempiere.ait.test.IntegrationTestListeners;
import de.metas.adempiere.ait.test.IntegrationTestSuite;


@RunWith(IntegrationTestSuite.class)
@IntegrationTestListeners({
	test.integration.swat.sales.SalesTestEventListener.class,
	
	// it's important to add the aux driver before the listener
	test.integration.swat.sales.invoicecand.InvoiceCandAuxTestDriver.class, 
	test.integration.swat.sales.invoicecand.InvoiceCandTestListener.class
})
@SuiteClasses({
		
		//
		// test drivers that fire test events
//	test.integration.swat.bPartner.BPartnerTestDriver.class, // currently no listeners in 
//	test.integration.swat.purchase.PurchaseTestDriver.class, // currently no listeners in 
		
		test.integration.swat.sales.invoice.InvoiceTests.class,
	
		test.integration.swat.sales.SalesTestDriver.class,
		
		//
		// "common" test cases that don't fire or consume test events
		test.integration.swat.sales.shipment.ShipmentTests.class,
		test.integration.swat.sales.ui.SalesOrderUITests.class,
		
		test.integration.swat.ad.CacheInterceptorTests.class,
})
public class AllTests
{
}
