package org.adempiere.mm.attributes.countryattribute.impl;

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


import java.util.Arrays;
import java.util.List;

import org.adempiere.mm.attributes.api.IModelAttributeSetInstanceListener;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;

import de.metas.invoice.service.IInvoiceDAO;
import de.metas.util.Services;

public class InvoiceCountryModelAttributeSetInstanceListener implements IModelAttributeSetInstanceListener
{

	private final InvoiceLineCountryModelAttributeSetInstanceListener invoiceLineListener = new InvoiceLineCountryModelAttributeSetInstanceListener();
	private static final List<String> sourceColumnNames = Arrays.asList(I_C_Invoice.COLUMNNAME_C_BPartner_Location_ID);

	@Override
	public String getSourceTableName()
	{
		return I_C_Invoice.Table_Name;
	}

	@Override
	public List<String> getSourceColumnNames()
	{
		return sourceColumnNames;
	}

	@Override
	public void modelChanged(Object model)
	{
		final I_C_Invoice invoice = InterfaceWrapperHelper.create(model, I_C_Invoice.class);
		for (final I_C_InvoiceLine line : Services.get(IInvoiceDAO.class).retrieveLines(invoice))
		{
			invoiceLineListener.modelChanged(line);
		}

	}

}
