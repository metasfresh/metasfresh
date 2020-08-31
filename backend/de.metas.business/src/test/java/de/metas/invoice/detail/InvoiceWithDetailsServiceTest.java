/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.invoice.detail;

import de.metas.invoice.InvoiceId;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_Invoice_Detail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class InvoiceWithDetailsServiceTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void copyDetailsToReversal()
	{
		I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setC_Invoice_ID(24);
		invoice.setReversal_ID(42);
		invoice.setC_BPartner_Location_ID(10);
		saveRecord(invoice);

		I_C_InvoiceLine invoiceLine = newInstance(I_C_InvoiceLine.class);
		invoiceLine.setC_Invoice_ID(24);
		invoiceLine.setC_InvoiceLine_ID(241);
		invoiceLine.setLine(1);
		saveRecord(invoiceLine);

		I_C_Invoice_Detail invoice_detail = newInstance(I_C_Invoice_Detail.class);
		invoice_detail.setC_Invoice_Detail_ID(1001);
		invoice_detail.setC_Invoice_ID(24);
		invoice_detail.setSeqNo(25);
		invoice_detail.setLabel("TestLabel");
		invoice_detail.setDescription("Test Description");
		saveRecord(invoice_detail);

		I_C_Invoice_Detail invoice_detailLine = newInstance(I_C_Invoice_Detail.class);
		invoice_detailLine.setC_Invoice_Detail_ID(1002);
		invoice_detailLine.setC_Invoice_ID(24);
		invoice_detailLine.setC_InvoiceLine_ID(241);
		invoice_detailLine.setSeqNo(26);
		invoice_detailLine.setLabel("TestLabel for Line");
		invoice_detailLine.setDescription("Test Description for Line");
		saveRecord(invoice_detailLine);

		I_C_Invoice reversalInvoice = newInstance(I_C_Invoice.class);
		InterfaceWrapperHelper.create(invoice, I_C_Invoice.class);
		reversalInvoice.setC_Invoice_ID(42);
		reversalInvoice.setReversal_ID(24);
		reversalInvoice.setC_BPartner_Location_ID(10);
		saveRecord(reversalInvoice);

		I_C_InvoiceLine reversalInvoiceLine = newInstance(I_C_InvoiceLine.class);
		reversalInvoiceLine.setC_InvoiceLine_ID(421);
		reversalInvoiceLine.setC_Invoice_ID(42);
		reversalInvoiceLine.setLine(1);
		saveRecord(reversalInvoiceLine);

		// guard
		final List<I_C_Invoice_Detail> invoiceDetailRecordsBefore = POJOLookupMap.get().getRecords(I_C_Invoice_Detail.class);
		assertThat(invoiceDetailRecordsBefore).extracting("C_Invoice_ID", "C_InvoiceLine_ID")
				.containsExactlyInAnyOrder(
						tuple(24, 0),
						tuple(24, 241));

		// when
		InvoiceWithDetailsService invoiceWithDetailsService = new InvoiceWithDetailsService(new InvoiceWithDetailsRepository());
		invoiceWithDetailsService.copyDetailsToReversal(InvoiceId.ofRepoId(24), InvoiceId.ofRepoId(42));

		// then
		final List<I_C_Invoice_Detail> invoiceDetailRecords = POJOLookupMap.get().getRecords(I_C_Invoice_Detail.class);
		assertThat(invoiceDetailRecords).extracting("C_Invoice_ID", "C_InvoiceLine_ID").hasSize(4)
				.containsExactlyInAnyOrder(
						tuple(24, 0),
						tuple(24, 241),
						tuple(42, 0),
						tuple(42, 421));
	}
}
