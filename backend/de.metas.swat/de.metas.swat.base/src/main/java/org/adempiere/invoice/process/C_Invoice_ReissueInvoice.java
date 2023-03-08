/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package org.adempiere.invoice.process;

import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.DocStatus;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.location.ICountryDAO;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_Invoice;

import static org.compiere.model.X_C_DocType.DOCBASETYPE_ARInvoice;

/**
 * TODO
 */
public class C_Invoice_ReissueInvoice extends JavaProcess implements IProcessPrecondition
{
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final ICountryDAO countryDAO = Services.get(ICountryDAO.class);

	private static final DocBaseAndSubType SALES_INVOICE = DocBaseAndSubType.of(DOCBASETYPE_ARInvoice);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		final InvoiceId invoiceId = InvoiceId.ofRepoId(context.getSingleSelectedRecordId());
		final I_C_Invoice invoiceRecord = invoiceDAO.getByIdInTrx(invoiceId);
		final DocTypeId docTypeId = DocTypeId.ofRepoId(invoiceRecord.getC_DocType_ID());
		final DocBaseAndSubType docBaseAndSubType = docTypeDAO.getDocBaseAndSubTypeById(docTypeId);

		if(!docBaseAndSubType.equals(SALES_INVOICE))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Not an basic Sales Invoice");
		}

		final DocStatus docStatus = DocStatus.ofCode(invoiceRecord.getDocStatus());
		if(!docStatus.isCompletedOrClosed())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Not completed or closed");
		}

		if(countryDAO.isEnforceCorrectionInvoice(invoiceBL.getBillToCountryId(invoiceId)))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("isEnforceCorrectionInvoice");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		return null; //TODO
	}
}
