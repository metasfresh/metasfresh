/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.invoicecandidate.modelvalidator;

import de.metas.document.engine.DocStatus;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Line_Alloc;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests the {@code TYPE_BEFORE_DELETE} guarded cascade of {@link C_OrderLine} (invoicecandidate) onto
 * {@link I_C_Invoice_Candidate} records: a candidate with no invoice line yet is deleted along with its sales order
 * line, while a candidate with an invoice line on a non-voided/reversed invoice blocks the delete.
 */
public class InvoiceCand_OrderLineDeleteTest
{
	private C_OrderLine c_OrderLine;

	private I_C_OrderLine salesOrderLineRecord;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		c_OrderLine = new C_OrderLine();

		final I_C_Order salesOrderRecord = newInstance(I_C_Order.class);
		salesOrderRecord.setIsSOTrx(true);
		saveRecord(salesOrderRecord);

		salesOrderLineRecord = newInstance(I_C_OrderLine.class);
		salesOrderLineRecord.setC_Order_ID(salesOrderRecord.getC_Order_ID());
		saveRecord(salesOrderLineRecord);
	}

	private I_C_Invoice_Candidate createInvoiceCandidateFor(final org.compiere.model.I_C_OrderLine orderLine)
	{
		final I_C_Invoice_Candidate ic = newInstance(I_C_Invoice_Candidate.class);
		ic.setAD_Table_ID(InterfaceWrapperHelper.getTableId(org.compiere.model.I_C_OrderLine.class));
		ic.setRecord_ID(orderLine.getC_OrderLine_ID());
		saveRecord(ic);
		return ic;
	}

	/**
	 * Creates a {@code C_Invoice} + {@code C_InvoiceLine} + an active {@code C_Invoice_Line_Alloc} linking the given
	 * candidate to that invoice line -- i.e. a "real invoice" for the candidate.
	 */
	private void createInvoiceLineAlloc(final I_C_Invoice_Candidate ic, final String invoiceDocStatus)
	{
		final I_C_Invoice invoiceRecord = newInstance(I_C_Invoice.class);
		invoiceRecord.setDocStatus(invoiceDocStatus);
		saveRecord(invoiceRecord);

		final I_C_InvoiceLine invoiceLineRecord = newInstance(I_C_InvoiceLine.class);
		invoiceLineRecord.setC_Invoice_ID(invoiceRecord.getC_Invoice_ID());
		saveRecord(invoiceLineRecord);

		final I_C_Invoice_Line_Alloc allocRecord = newInstance(I_C_Invoice_Line_Alloc.class);
		allocRecord.setC_Invoice_Candidate_ID(ic.getC_Invoice_Candidate_ID());
		allocRecord.setC_InvoiceLine_ID(invoiceLineRecord.getC_InvoiceLine_ID());
		saveRecord(allocRecord);
	}

	private boolean invoiceCandidateStillExists(final I_C_Invoice_Candidate ic)
	{
		return POJOLookupMap.get().getRecords(I_C_Invoice_Candidate.class)
				.stream()
				.anyMatch(record -> record.getC_Invoice_Candidate_ID() == ic.getC_Invoice_Candidate_ID());
	}

	@Test
	public void ic_withNoInvoiceLine_isDeleted_onOrderLineDelete()
	{
		final I_C_Invoice_Candidate ic = createInvoiceCandidateFor(salesOrderLineRecord);

		c_OrderLine.deleteInvoiceCandidates(salesOrderLineRecord);

		assertThat(invoiceCandidateStillExists(ic)).isFalse();
	}

	@Test
	public void ic_withInvoiceLineOnNonVoidedInvoice_blocksDelete_onOrderLineDelete()
	{
		final I_C_Invoice_Candidate ic = createInvoiceCandidateFor(salesOrderLineRecord);
		createInvoiceLineAlloc(ic, DocStatus.Completed.getCode());

		assertThatThrownBy(() -> c_OrderLine.deleteInvoiceCandidates(salesOrderLineRecord))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("SalesOrderLine_CannotDelete_HasCompletedDocs");

		assertThat(invoiceCandidateStillExists(ic)).isTrue();
	}

	@Test
	public void ic_withInvoiceLineOnlyOnVoidedInvoice_isDeleted_onOrderLineDelete()
	{
		final I_C_Invoice_Candidate ic = createInvoiceCandidateFor(salesOrderLineRecord);
		createInvoiceLineAlloc(ic, DocStatus.Voided.getCode());

		c_OrderLine.deleteInvoiceCandidates(salesOrderLineRecord); // must not throw

		assertThat(invoiceCandidateStillExists(ic)).isFalse();
	}

	@Test
	public void ic_withInvoiceLineOnlyOnReversedInvoice_isDeleted_onOrderLineDelete()
	{
		final I_C_Invoice_Candidate ic = createInvoiceCandidateFor(salesOrderLineRecord);
		createInvoiceLineAlloc(ic, DocStatus.Reversed.getCode());

		c_OrderLine.deleteInvoiceCandidates(salesOrderLineRecord); // must not throw

		assertThat(invoiceCandidateStillExists(ic)).isFalse();
	}

	@Test
	public void purchaseOrderLine_isNeverGuarded()
	{
		final I_C_Order purchaseOrderRecord = newInstance(I_C_Order.class);
		purchaseOrderRecord.setIsSOTrx(false);
		saveRecord(purchaseOrderRecord);

		final I_C_OrderLine purchaseOrderLineRecord = newInstance(I_C_OrderLine.class);
		purchaseOrderLineRecord.setC_Order_ID(purchaseOrderRecord.getC_Order_ID());
		saveRecord(purchaseOrderLineRecord);

		// the candidate genuinely references the *purchase* order line under test and has an invoice line on a
		// non-voided invoice -- if the isSOTrx gate were removed, this would block the delete
		final I_C_Invoice_Candidate ic = createInvoiceCandidateFor(purchaseOrderLineRecord);
		createInvoiceLineAlloc(ic, DocStatus.Completed.getCode());

		c_OrderLine.deleteInvoiceCandidates(purchaseOrderLineRecord); // must not throw, unconditional delete

		assertThat(invoiceCandidateStillExists(ic)).isFalse();
	}
}
