package de.metas.edi.api;

import static de.metas.edi.model.I_EDI_Document.EDI_EXPORTSTATUS_SendingStarted;
import static org.adempiere.model.InterfaceWrapperHelper.hasChanges;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.edi.model.I_C_Doc_Outbound_Log;
import de.metas.edi.model.I_C_Invoice;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2019 metas GmbH
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

class EDIDocOutBoundLogServiceTest
{

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void setEdiExportStatusFromInvoiceRecord_reference_from_invoiceRecord()
	{
		final I_C_Invoice invoiceRecord = createInvoiceRecord(EDI_EXPORTSTATUS_SendingStarted);
		final I_C_Doc_Outbound_Log docOutboundLogRecord = createDocOutboundLogRecord(invoiceRecord);
		final TableRecordReference reference = TableRecordReference.ofReferenced(docOutboundLogRecord);

		// invoke the method under test
		final Optional<I_C_Doc_Outbound_Log> result = new EDIDocOutBoundLogService().setEdiExportStatusFromInvoiceRecord(reference);
		assertResultOK(result);
	}

	@Test
	void setEdiExportStatusFromInvoiceRecord_reference_from_docOutboundLogRecord()
	{
		final I_C_Invoice invoiceRecord = createInvoiceRecord(EDI_EXPORTSTATUS_SendingStarted);
		createDocOutboundLogRecord(invoiceRecord);
		final TableRecordReference reference = TableRecordReference.of(invoiceRecord);

		// invoke the method under test
		final Optional<I_C_Doc_Outbound_Log> result = new EDIDocOutBoundLogService().setEdiExportStatusFromInvoiceRecord(reference);
		assertResultOK(result);
	}

	public void assertResultOK(final Optional<I_C_Doc_Outbound_Log>  docOutboundLogRecord)
	{
		assertThat(docOutboundLogRecord).isPresent();
		assertThat(docOutboundLogRecord.get().getEDI_ExportStatus()).isEqualTo(EDI_EXPORTSTATUS_SendingStarted);
		assertThat(hasChanges(docOutboundLogRecord.get()))
				.as("docOutboundLogRecord may not have been saved because the service might be called from C_DocOutBoundLog before-new")
				.isTrue();
	}

	public I_C_Doc_Outbound_Log createDocOutboundLogRecord(final I_C_Invoice invoiceRecord)
	{
		final I_C_Doc_Outbound_Log docOutboundLogRecord = newInstance(I_C_Doc_Outbound_Log.class);
		docOutboundLogRecord.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(I_C_Invoice.Table_Name));
		docOutboundLogRecord.setRecord_ID(invoiceRecord.getC_Invoice_ID());
		saveRecord(docOutboundLogRecord);
		return docOutboundLogRecord;
	}

	public I_C_Invoice createInvoiceRecord(final String ediExportStatus)
	{
		final I_C_Invoice invoiceRecord = newInstance(I_C_Invoice.class);
		invoiceRecord.setEDI_ExportStatus(ediExportStatus);
		saveRecord(invoiceRecord);
		return invoiceRecord;
	}

}
