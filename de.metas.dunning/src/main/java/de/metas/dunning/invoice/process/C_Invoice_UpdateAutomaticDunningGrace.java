package de.metas.dunning.invoice.process;

/*
 * #%L
 * de.metas.dunning
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


import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.collections.IteratorUtils;
import org.compiere.model.I_AD_Org;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import de.metas.dunning.invoice.api.IInvoiceSourceBL;

public class C_Invoice_UpdateAutomaticDunningGrace extends SvrProcess
{
	private static final String PARAM_AD_Org_ID = "AD_Org_ID";

	private int p_AD_Org_ID = -1;

	@Override
	protected void prepare()
	{
		for (ProcessInfoParameter para : getParametersAsArray())
		{
			final String name = para.getParameterName();
			if (para.getParameterName() == null)
			{
				continue;
			}
			else if (PARAM_AD_Org_ID.equals(name))
			{
				p_AD_Org_ID = para.getParameterAsInt();
			}
		}

	}

	@Override
	protected String doIt()
	{
		if (p_AD_Org_ID <= 0)
		{
			throw new FillMandatoryException(PARAM_AD_Org_ID);
		}
		final I_AD_Org adOrg = InterfaceWrapperHelper.create(getCtx(), p_AD_Org_ID, I_AD_Org.class, get_TrxName());

		final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

		// Gets all open invoices for the selected org and updates dunning grace dates
		for (org.compiere.model.I_C_Invoice invoice : IteratorUtils.asIterable(invoiceDAO.retrieveOpenInvoicesByOrg(adOrg)))
		{
			Services.get(IInvoiceSourceBL.class).setDunningGraceIfAutomatic(invoice);
			InterfaceWrapperHelper.save(invoice);
		}

		return "OK";
	}
}
