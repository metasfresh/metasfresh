package de.metas.invoice.callout;

import org.adempiere.ad.callout.api.ICalloutRecord;

/*
 * #%L
 * de.metas.swat.base
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


import org.adempiere.ad.ui.spi.TabCalloutAdapter;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_InvoiceLine;

/**
 * @task http://dewiki908/mediawiki/index.php/09182_Manuelle_EDI_Rechnungen_Retourenbestellung_%28102698247514%29
 */
public class C_InvoiceLine_TabCallout extends TabCalloutAdapter
{
	/**
	 * Invokes IInvoiceBL.updateInvoiceLineIsReadOnlyFlags() to make sure the correct fields are editable when a new order line is created.
	 */
	@Override
	public void onNew(final ICalloutRecord calloutRecord)
	{
		final I_C_InvoiceLine invoiceLine = calloutRecord.getModel(I_C_InvoiceLine.class);
		final I_C_Invoice invoice = InterfaceWrapperHelper.create(invoiceLine.getC_Invoice(), I_C_Invoice.class);

		Services.get(IInvoiceBL.class).updateInvoiceLineIsReadOnlyFlags(invoice, invoiceLine);
	}
}
