package test.integration.swat.sales;

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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.Query;
import org.compiere.model.X_C_Invoice;

import de.metas.adempiere.ait.event.EventType;
import de.metas.adempiere.ait.event.TestEvent;
import de.metas.adempiere.ait.test.annotation.ITEventListener;
import de.metas.adempiere.model.I_C_InvoiceLine;

public class SalesTestEventListener 
{

	@ITEventListener(
			driver = SalesTestDriver.class,
			eventTypes = EventType.ORDER_POS_REACTIVATE_AFTER)
	public void posOrderReactivated(final TestEvent evt)
	{
		final MOrder order = (MOrder)evt.getObj();

		for (final MOrderLine ol : order.getLines(true, null))
		{
			final List<MInvoiceLine> ils =
					new Query(order.getCtx(), I_C_InvoiceLine.Table_Name, I_C_InvoiceLine.COLUMNNAME_C_OrderLine_ID + "=?", order.get_TrxName())
							.setParameters(ol.getC_OrderLine_ID())
							.setOnlyActiveRecords(true)
							.setApplyAccessFilter(true)
							.setOrderBy(I_C_InvoiceLine.COLUMNNAME_C_InvoiceLine_ID)
							.list();

			assertTrue(ol + " should be referenced by at least two ils", ils.size() >= 2);
			assertFalse("ils " + ils + "should belong to different invoices", ils.get(0).getC_Invoice_ID() == ils.get(1).getC_Invoice_ID());
		}
	}

	@ITEventListener(
			driver = SalesTestDriver.class,
			eventTypes = EventType.INVOICE_PAID_REVERSE_AFTER)
	public void invoicePaidReversed(final TestEvent evt)
	{
		assertReversedInvoiceOK(evt);
	}

	@ITEventListener(
			driver = SalesTestDriver.class,
			eventTypes = EventType.INVOICE_UNPAID_REVERSE_AFTER)
	public void invoiceReversed(final TestEvent evt)
	{
		assertReversedInvoiceOK(evt);
	}

	private void assertReversedInvoiceOK(final TestEvent evt)
	{
		final MInvoice invoice = (MInvoice)evt.getObj();

		assertEquals("reversed " + invoice + " should be reversed", X_C_Invoice.DOCSTATUS_Reversed, invoice.getDocStatus());
		assertTrue("reversed " + invoice + " has no reversal", invoice.getReversal_ID() > 0);

		final MInvoice reversal = (MInvoice)invoice.getReversal();

		assertEquals("reversal " + reversal + " doesn't reference reversed" + invoice, invoice.getC_Invoice_ID(), reversal.getReversal_ID());
		assertEquals("reversal " + reversal + " should be reversed", X_C_Invoice.DOCSTATUS_Reversed, reversal.getDocStatus());
		assertTrue("reversal " + reversal + " should be different from reversed " + invoice, invoice.getC_Invoice_ID() != reversal.getC_Invoice_ID());

		assertTrue("reversed " + invoice + " should be paid", invoice.isPaid());
		assertTrue("reversal " + reversal + " should be paid", reversal.isPaid());

		final MInvoiceLine[] invoiceIls = invoice.getLines(true);
		final MInvoiceLine[] reversalIls = reversal.getLines(true);

		assertTrue(invoiceIls.length == reversalIls.length);

		for (int i = 0; i < invoiceIls.length; i++)
		{
			// note: reversal lines are no "real" counter documents and don't reference each other via
			// Ref_InvoiceLine_ID!
			assertTrue(invoiceIls[i] + " of reversed invoice has Ref_InvoiceLine_ID!=0", invoiceIls[i].getRef_InvoiceLine_ID() == 0);
			assertTrue(reversalIls[i] + " of reversal invoice has Ref_InvoiceLine_ID!=0", reversalIls[i].getRef_InvoiceLine_ID() == 0);

			assertTrue(invoiceIls[i] + " and " + reversalIls[i] + " reference different ols",
					reversalIls[i].getC_OrderLine_ID() == reversalIls[i].getC_OrderLine_ID());
		}
	}
}
