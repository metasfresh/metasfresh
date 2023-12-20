/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2021 metas GmbH
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
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.impl.AdjustmentChargeCreateRequest;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import org.compiere.model.X_C_DocType;

public class C_Invoice_PO_CreateAdjustmentCharge extends JavaProcess implements IProcessPrecondition
{
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);

	public String doIt()
	{
		final DocBaseAndSubType docBaseAndSubType = DocBaseAndSubType.of(X_C_DocType.DOCBASETYPE_APInvoice, X_C_DocType.DOCSUBTYPE_KreditorenNachbelastung);

		final AdjustmentChargeCreateRequest adjustmentChargeCreateRequest = AdjustmentChargeCreateRequest.builder()
				.invoiceID(InvoiceId.ofRepoId(getRecord_ID()))
				.docBaseAndSubTYpe(docBaseAndSubType)
				.isSOTrx(false)
				.build();

		invoiceBL.adjustmentCharge(adjustmentChargeCreateRequest);

		return MSG_OK;
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{

		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

}
