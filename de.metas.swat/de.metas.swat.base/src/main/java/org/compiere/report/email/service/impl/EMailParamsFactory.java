package org.compiere.report.email.service.impl;

import org.adempiere.util.api.IParams;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.report.email.service.IEmailParameters;
import org.compiere.report.email.service.IEmailParamsFactory;

import de.metas.process.ProcessInfo;

public class EMailParamsFactory implements IEmailParamsFactory
{

	@Override
	public IEmailParameters getInstanceForPI(final ProcessInfo pi)
	{

		final int tableId = pi.getTable_ID();

		if (tableId == I_C_Order.Table_ID || tableId == I_C_Invoice.Table_ID || tableId == I_M_InOut.Table_ID)
		{
			return new DocumentEmailParams(pi);
		}

		final IParams params = pi.getParameterAsIParams();
		if (params.hasParameter(I_C_BPartner.COLUMNNAME_C_BPartner_ID))
		{
			return new BPartnerEmailParams(pi, params);
		}

		return new EmptyEmailParams();
	}
}
