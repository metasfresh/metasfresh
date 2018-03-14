package de.metas.edi.api.impl;

/*
 * #%L
 * de.metas.edi
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


import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Table;
import org.compiere.model.X_C_OrderLine;
import org.compiere.model.X_M_InOut;
import org.compiere.model.X_M_InOutLine;

import de.metas.edi.api.IEDIInputDataSourceBL;
import de.metas.edi.api.IEDIInvoiceCandBL;
import de.metas.edi.model.I_C_Invoice_Candidate;
import de.metas.edi.model.I_C_OLCand;
import de.metas.edi.model.I_C_Order;
import de.metas.edi.model.I_M_InOut;
import de.metas.edi.model.I_M_InOutLine;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.ordercandidate.model.X_C_OLCand;

public class EDIInvoiceCandBL implements IEDIInvoiceCandBL
{

	@Override
	public void setEdiEnabled(final I_C_Invoice_Candidate candidate)
	{
		final I_AD_Table adTable = candidate.getAD_Table();

		if (adTable == null)
		{
			return;
		}

		final boolean isEdiEnabled;

		final Properties ctx = InterfaceWrapperHelper.getCtx(candidate);
		final String trxName = InterfaceWrapperHelper.getTrxName(candidate);

		// purchase side not relevant
		if (!candidate.isSOTrx())
		{
			isEdiEnabled = false;
		}

		// case Order
		else if (X_C_OrderLine.Table_Name.equals(adTable.getTableName()))
		{
			final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(ctx, candidate.getRecord_ID(), I_C_OrderLine.class, trxName);

			Check.assumeNotNull(orderLine, "Invoice candidate {} has a record ID that points to an invalid order line", candidate);

			final I_C_Order order = InterfaceWrapperHelper.create(orderLine.getC_Order(), de.metas.edi.model.I_C_Order.class);

			isEdiEnabled = order.isEdiEnabled();
		}

		// case order candidate
		else if (X_C_OLCand.Table_Name.equals(adTable.getTableName()))
		{
			final I_C_OLCand olcand = InterfaceWrapperHelper.create(ctx, candidate.getRecord_ID(), I_C_OLCand.class, trxName);

			Check.assumeNotNull(olcand, "Invoice candidate {} has a record ID that points to an invalid order candidate", candidate);

			final int inputDataSourceId = olcand.getAD_InputDataSource_ID();
			isEdiEnabled = Services.get(IEDIInputDataSourceBL.class).isEDIInputDataSource(inputDataSourceId);
		}

		// case inout
		else if (X_M_InOut.Table_Name.equals(adTable.getTableName()))
		{
			final I_M_InOut inout = InterfaceWrapperHelper.create(ctx, candidate.getRecord_ID(), I_M_InOut.class, trxName);

			Check.assumeNotNull(inout, "Invoice candidate {} has a record ID that points to an invalid inout", candidate);

			isEdiEnabled = inout.isEdiEnabled();
		}

		else if (X_M_InOutLine.Table_Name.equals(adTable.getTableName()))
		{
			final I_M_InOutLine ioline =  InterfaceWrapperHelper.create(ctx, candidate.getRecord_ID(), I_M_InOutLine.class, trxName);
			
			Check.assumeNotNull(ioline,  "Invoice candidate {} has a record ID that points to an invalid inout line", candidate);
			
			final I_M_InOut inout = InterfaceWrapperHelper.create(ioline.getM_InOut(), de.metas.edi.model.I_M_InOut.class);
			
			Check.assumeNotNull(inout, "Inout Line {}  cannot have M_InOut =  null", ioline);
			
			isEdiEnabled = inout.isEdiEnabled();
		}
		// no other case covered yet
		else
		{
			isEdiEnabled = false;
		}

		// no other case covered yet

		candidate.setIsEdiEnabled(isEdiEnabled);
	}

}
