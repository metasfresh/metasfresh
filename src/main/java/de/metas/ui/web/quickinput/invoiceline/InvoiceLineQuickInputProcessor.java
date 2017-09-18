package de.metas.ui.web.quickinput.invoiceline;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;

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
		final I_C_Invoice invoice = quickInput.getRootDocumentAs(I_C_Invoice.class);
		final IInvoiceLineQuickInput invoiceLineQuickInput = quickInput.getQuickInputDocumentAs(IInvoiceLineQuickInput.class);

		final I_C_InvoiceLine invoiceLine = InterfaceWrapperHelper.newInstance(I_C_InvoiceLine.class, invoice);
		invoiceLine.setC_Invoice(invoice);
		invoiceLine.setM_Product_ID(invoiceLineQuickInput.getM_Product_ID());
		invoiceLine.setQtyEntered(invoiceLineQuickInput.getQty());
		InterfaceWrapperHelper.save(invoiceLine);

		return DocumentId.of(invoiceLine.getC_InvoiceLine_ID());
	}

}
