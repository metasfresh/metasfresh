package de.metas.ui.web.quickinput.invoiceline;

import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Invoice;

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.adempiere.service.IInvoiceLineBL;
import de.metas.purchasing.api.IBPartnerProductBL;
import de.metas.ui.web.quickinput.IQuickInputProcessor;
import de.metas.ui.web.quickinput.QuickInput;
import de.metas.ui.web.window.datatypes.DocumentId;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class InvoiceLineQuickInputProcessor implements IQuickInputProcessor
{

	@Override
	public DocumentId process(final QuickInput quickInput)
	{
		final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
		final IInvoiceLineBL invoiceLineBL = Services.get(IInvoiceLineBL.class);

		final I_C_Invoice invoice = quickInput.getRootDocumentAs(I_C_Invoice.class);
		final IInvoiceLineQuickInput invoiceLineQuickInput = quickInput.getQuickInputDocumentAs(IInvoiceLineQuickInput.class);

		// 3834
		Services.get(IBPartnerProductBL.class).assertProductNotBanned(
				invoiceLineQuickInput.getM_Product_ID(),
				invoice.getC_BPartner_ID());

		final I_C_InvoiceLine invoiceLine = InterfaceWrapperHelper.newInstance(I_C_InvoiceLine.class, invoice);
		invoiceLine.setC_Invoice(invoice);

		invoiceBL.setProductAndUOM(invoiceLine, invoiceLineQuickInput.getM_Product_ID());
		invoiceBL.setQtys(invoiceLine, invoiceLineQuickInput.getQty());

		invoiceLineBL.updatePrices(invoiceLine);
		// invoiceBL.setLineNetAmt(invoiceLine); // not needed; will be called on save
		// invoiceBL.setTaxAmt(invoiceLine);// not needed; will be called on save

		InterfaceWrapperHelper.save(invoiceLine);

		return DocumentId.of(invoiceLine.getC_InvoiceLine_ID());
	}

}
