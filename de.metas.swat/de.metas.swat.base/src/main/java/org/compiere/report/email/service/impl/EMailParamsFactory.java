package org.compiere.report.email.service.impl;

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


import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_AD_PInstance_Para;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.model.MPInstance;
import org.compiere.process.ProcessInfo;
import org.compiere.report.email.service.IEmailParameters;
import org.compiere.report.email.service.IEmailParamsFactory;
import org.compiere.util.Env;

public class EMailParamsFactory implements IEmailParamsFactory {

	@Override
	public IEmailParameters getInstanceForPI(final ProcessInfo pi)
	{

		final int tableId = pi.getTable_ID();

		if (tableId == I_C_Order.Table_ID || tableId == I_C_Invoice.Table_ID
				|| tableId == I_M_InOut.Table_ID) {

			return new DocumentEmailParams(pi);
		}

		final MPInstance processInstance = new MPInstance(Env.getCtx(), pi.getAD_PInstance_ID(), ITrx.TRXNAME_None);
		final List<I_AD_PInstance_Para> params = processInstance.getParameters();

		for (I_AD_PInstance_Para currentParam : params) {
			if (I_C_BPartner.COLUMNNAME_C_BPartner_ID.equals(currentParam
					.getParameterName())) {

				return new BPartnerEmailParams(pi, params);
			}
		}

		return new EmptyEmailParams();
	}
}
