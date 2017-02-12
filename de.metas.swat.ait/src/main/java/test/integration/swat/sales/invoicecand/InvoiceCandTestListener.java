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


import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.X_C_Invoice;

import de.metas.adempiere.ait.event.EventType;
import de.metas.adempiere.ait.event.TestEvent;
import de.metas.adempiere.ait.helper.GridWindowHelper;
import de.metas.adempiere.ait.test.annotation.ITEventListener;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.inout.IInOutDAO;
import de.metas.inout.model.I_M_InOut;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import test.integration.swat.sales.SalesTestDriver;

public class InvoiceCandTestListener
{

	@ITEventListener(
			tasks = "01671",
			driver = SalesTestDriver.class,
			eventTypes = { EventType.SHIPMENT_COMPLETE_AFTER })
	public void afterShipmentComplete(final TestEvent evt)
	{
		final I_M_InOut inOut = InterfaceWrapperHelper.create(evt.getObj(), I_M_InOut.class);

		final Properties ctx = evt.getSource().getCtx();
		final String trxName = evt.getSource().getTrxName();

		final IInOutDAO inoutDAO = Services.get(IInOutDAO.class);
		final IInvoiceCandDAO invoiceCandDB = Services.get(IInvoiceCandDAO.class);

		final List<I_M_InOutLine> lines = inoutDAO.retrieveLines(inOut);
		assertThat(lines.size(), greaterThanOrEqualTo(1));

		for (final I_M_InOutLine line : lines)
		{
			final List<I_C_Invoice_Candidate> ics = invoiceCandDB.fetchInvoiceCandidates(ctx, I_C_OrderLine.Table_Name, line.getC_OrderLine_ID(), trxName);
			assertThat(ics.size(), is(1));
			for (I_C_Invoice_Candidate ic : ics)
			{
				assertThat(ic.isToRecompute(), is(false));
				assertThat(ic.getQtyToInvoice(), equalTo(line.getMovementQty()));
			}
		}
	}

	@ITEventListener(
			tasks = "01671",
			driver = SalesTestDriver.class,
			eventTypes = { 
				EventType.INVOICE_UNPAID_REVERSE_AFTER,
				EventType.INVOICE_PAID_REVERSE_AFTER})
	public void afterInvoiceReverse(final TestEvent evt)
	{
		final I_C_Invoice invoice = InterfaceWrapperHelper.create(evt.getObj(), I_C_Invoice.class);

		final InvoiceCandHelper helper = new InvoiceCandHelper(evt.getSource().getHelper());
		
		// Note: making sure that the invoice cands are up to date
		// The tests might be more understandable if we do this here
		helper.runProcess_UpdateInvoiceCands(); 

		final String trxName = evt.getSource().getTrxName();

		assertThat(invoice + " has wrong DocStatus", invoice.getDocStatus(), equalTo(X_C_Invoice.DOCSTATUS_Reversed));

		final IInvoiceDAO invoicePA = Services.get(IInvoiceDAO.class);
		final IInvoiceCandDAO invoiceCandDB = Services.get(IInvoiceCandDAO.class);

		final GridWindowHelper gridWindowHelper = helper.mkGridWindowHelper();
		gridWindowHelper.openTabForTable(I_C_Invoice_Candidate.Table_Name, true);

		for (final I_C_InvoiceLine il : invoicePA.retrieveLines(invoice, trxName))
		{
			for (final I_C_Invoice_Candidate icPO : invoiceCandDB.retrieveIcForIl(il))
			{
				final I_C_Invoice_Candidate icGrid =
						gridWindowHelper.selectRecordById(icPO.getC_Invoice_Candidate_ID()).getGridTabInterface(I_C_Invoice_Candidate.class);

				assertThat(icPO + " has wrong IsToRecompute", icGrid.isToRecompute(), is(false));
				assertThat(icPO + " has wrong QtyInvoiced", icGrid.getQtyInvoiced(), comparesEqualTo(BigDecimal.ZERO));
				assertThat(icPO + " has wrong IsProcessed", icGrid.isProcessed(), is(false));
				assertThat(icPO + " has QtyToInvoice!=QtyDelivered", icGrid.getQtyToInvoice(), comparesEqualTo(icGrid.getQtyDelivered()));
			}
		}
	}

}
