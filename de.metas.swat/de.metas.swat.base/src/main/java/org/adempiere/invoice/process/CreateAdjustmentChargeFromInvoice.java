package org.adempiere.invoice.process;

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


import java.math.BigDecimal;

import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.apps.AEnv;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;

public class CreateAdjustmentChargeFromInvoice extends SvrProcess
{
	private static final String PARA_DocSubType = "DocSubType";
	private int AD_Table_ID = 0;
	private int Record_ID = 0;
	private BigDecimal parameterDocSubType = new BigDecimal(-1);

	@Override
	protected void prepare()
	{
		final ProcessInfoParameter[] para = getParametersAsArray();

		for (int i = 0; i < para.length; i++)
		{
			final String name = para[i].getParameterName();
			final Object parameter = para[i].getParameter();

			if (parameter == null)
			{
				// do nothing
			}
			else if (CreateAdjustmentChargeFromInvoice.PARA_DocSubType.equals(name))
			{
				parameterDocSubType = (BigDecimal)parameter;
			}
			else
			{
				log.error("Unknown Parameter: " + name);
			}
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		final I_C_Invoice invoice = InterfaceWrapperHelper.create(getCtx(), getRecord_ID(), de.metas.adempiere.model.I_C_Invoice.class, get_TrxName());
		String docSubType = parameterDocSubType.toString();
		final I_C_Invoice adjustmentCharge = Services.get(IInvoiceBL.class).adjustmentCharge(invoice, docSubType);
		AD_Table_ID = InterfaceWrapperHelper.getModelTableId(adjustmentCharge);
		Record_ID = InterfaceWrapperHelper.getId(adjustmentCharge);

		return null;
	}

	@Override
	protected void postProcess(boolean success)
	{
		AEnv.zoom(AD_Table_ID, Record_ID);
	}

}
