package org.adempiere.model;

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


import java.sql.Timestamp;

import junit.framework.Assert;
import mockit.Mocked;
import mockit.NonStrictExpectations;

import org.compiere.model.I_C_Invoice;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.junit.Ignore;
import org.junit.Test;

import de.metas.adempiere.model.I_C_InvoiceLine;

/**
 * POWrapper tests
 * 
 * @author tsa
 *
 */
@Ignore // Mocked things are not working
public class POWrapperTest
{

	@Mocked
	MInvoice invoice;
	
	@Mocked
	MInvoiceLine invoiceLine;
	
	@Test
	public void testOverwrittenObjectGetter()
	{
		new NonStrictExpectations(invoice, invoiceLine)
		{{
			// Note: we use partial mocking.
			// All methods of invoice and invoiceLine that are recorded here will be mocked.
			// All methods ( e.g. invoiceLine.setInvoice() ) will not be mocked. Instead their real implementation will be executed.
			invoice.getM_PriceList_ID(); result = 1;
			invoice.getDateInvoiced(); result = new Timestamp(System.currentTimeMillis());
			invoice.getC_BPartner_ID(); result = 3;
			invoice.getC_BPartner_Location_ID(); result = 4;
			invoice.isSOTrx(); result = false;
			invoice.getPrecision(); result = 5;
			
			invoice.get_TableName(); result = I_C_Invoice.Table_Name;
			invoiceLine.get_TableName(); result = I_C_InvoiceLine.Table_Name;
		}};

		invoiceLine.setInvoice(invoice);
		
		Assert.assertSame("Precondition failed: Method getC_Invoice() should return cached invoice", invoice, invoiceLine.getC_Invoice());
		
		de.metas.adempiere.model.I_C_InvoiceLine il = POWrapper.create(invoiceLine, de.metas.adempiere.model.I_C_InvoiceLine.class);
		I_C_Invoice invoice2 = il.getC_Invoice();
		Assert.assertSame("Wrapped invoice line should return cached invoice", invoice, invoice2);
	}
	
	
}
