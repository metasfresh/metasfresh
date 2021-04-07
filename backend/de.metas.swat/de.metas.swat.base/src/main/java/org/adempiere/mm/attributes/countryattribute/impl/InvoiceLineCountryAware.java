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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.countryattribute.ICountryAware;
import org.adempiere.mm.attributes.countryattribute.ICountryAwareFactory;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Invoice;

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.util.Check;
import de.metas.util.Services;

public class InvoiceLineCountryAware implements ICountryAware
{
	public static final ICountryAwareFactory factory = new ICountryAwareFactory()
	{
		@Override
		public ICountryAware createCountryAware(Object model)
		{
			final I_C_InvoiceLine invoiceLine = InterfaceWrapperHelper.create(model, I_C_InvoiceLine.class);
			final ICountryAware countryAware = new InvoiceLineCountryAware(invoiceLine);
			return countryAware;
		}
	};

	private final I_C_InvoiceLine invoiceLine;

	private InvoiceLineCountryAware(I_C_InvoiceLine invoiceLine)
	{
		super();
		Check.assumeNotNull(invoiceLine, "Invoice line not null");
		this.invoiceLine = invoiceLine;
	}

	@Override
	public int getAD_Client_ID()
	{
		return invoiceLine.getAD_Client_ID();
	}

	@Override
	public int getAD_Org_ID()
	{
		return invoiceLine.getAD_Org_ID();
	}

	@Override
	public boolean isSOTrx()
	{
		final I_C_Invoice invoice = getInvoice();
		return invoice.isSOTrx();
	}

	@Override
	public I_C_Country getC_Country()
	{
		final I_C_Invoice invoice = getInvoice();

		if (invoice.getC_BPartner_Location_ID() <= 0)
		{
			return null;
		}

		final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
		final I_C_BPartner_Location bPartnerLocationRecord = bpartnerDAO.getBPartnerLocationByIdEvenInactive(BPartnerLocationId.ofRepoId(invoice.getC_BPartner_ID(), invoice.getC_BPartner_Location_ID()));

		return bPartnerLocationRecord.getC_Location().getC_Country();
	}

	private I_C_Invoice getInvoice()
	{
		final I_C_Invoice invoice = invoiceLine.getC_Invoice();
		if (invoice == null)
		{
			throw new AdempiereException("Invoice not set for" + invoiceLine);
		}

		return invoice;
	}

}
