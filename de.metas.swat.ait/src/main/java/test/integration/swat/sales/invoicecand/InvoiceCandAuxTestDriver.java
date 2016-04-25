package test.integration.swat.sales.invoicecand;

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


import test.integration.swat.sales.SalesTestDriver;
import de.metas.adempiere.ait.event.EventType;
import de.metas.adempiere.ait.event.TestEvent;
import de.metas.adempiere.ait.test.annotation.ITEventListener;
import de.metas.adempiere.ait.validator.InvoiceCandidateValidator;

/**
 * This class is technicalla a lister, but it doesn't do tests. instead, it performs additional actions on certain test
 * events.
 * 
 * @author ts
 * 
 */
public class InvoiceCandAuxTestDriver
{
	@ITEventListener(
			tasks = "01671",
			driver = SalesTestDriver.class,
			eventTypes = {
					EventType.TESTDRIVER_STARTED
			})
	public void onDriverStartup(final TestEvent evt)
	{
		InvoiceCandidateValidator.get().register(evt.getSource());
	}
	
	@ITEventListener(
			tasks = "01671",
			driver = SalesTestDriver.class,
			eventTypes = {
					EventType.SHIPMENT_COMPLETE_AFTER
			})
	public void afterShipmentComplete(final TestEvent evt)
	{
		new InvoiceCandHelper(evt.getSource().getHelper()).runProcess_UpdateInvoiceCands();
	}
}
