/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.edi.process.export.json;

import de.metas.common.util.Check;
import de.metas.edi.model.I_EDI_Document_Extension;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.postgrest.process.PostgRESTProcessExecutor;
import de.metas.process.Param;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;

/**
 * Exports one particular invoice to JSON.
 * It directs {@link PostgRESTProcessExecutor} to store the result to disk if not called via API.
 * It also attaches the resulting JSON file to the invoice and sets the invoice's {@code EDI_ExportStatus} to "Sent".
 */
public class C_Invoice_EDI_Export_JSON extends EDI_Export_JSON
{
	public static final String PARAM_C_INVOICE_ID = "C_Invoice_ID";

	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

	@Param(parameterName = PARAM_C_INVOICE_ID, mandatory = true)
	private int c_invoice_id;

	@Override
	protected I_EDI_Document_Extension loadRecordOutOfTrx()
	{
		final I_C_Invoice invoiceRecord = invoiceDAO.getByIdOutOfTrx(InvoiceId.ofRepoId(c_invoice_id), I_C_Invoice.class);
		Check.assumeNotNull(invoiceRecord, "C_Invoice with ID={} shall not be null", c_invoice_id);
		return InterfaceWrapperHelper.create(invoiceRecord, I_EDI_Document_Extension.class);
	}

	@Override
	protected void saveRecord(@NonNull final I_EDI_Document_Extension record)
	{
		final I_C_Invoice invoiceRecord = InterfaceWrapperHelper.create(record, I_C_Invoice.class);
		invoiceDAO.save(invoiceRecord);
	}
}
