package de.metas.invoicecandidate.modelvalidator;

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

import org.adempiere.model.POWrapper;
import org.adempiere.util.Services;
import org.compiere.model.MClient;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;

import de.metas.adempiere.model.I_C_Order;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler;

public class C_Order implements ModelValidator
{
	private int m_AD_Client_ID = -1;

	@Override
	public int getAD_Client_ID()
	{
		return m_AD_Client_ID;
	}

	@Override
	public void initialize(ModelValidationEngine engine, MClient client)
	{
		if (client != null)
			m_AD_Client_ID = client.getAD_Client_ID();

		engine.addDocValidate(I_C_Order.Table_Name, this);
	}

	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		return null;
	}

	@Override
	public String modelChange(final PO po, final int type) throws Exception
	{
		return null;
	}

	@Override
	public String docValidate(final PO po, final int timing)
	{
		if (TIMING_AFTER_COMPLETE == timing
				|| TIMING_AFTER_REACTIVATE == timing
				|| TIMING_AFTER_CLOSE == timing)
		{
			final IInvoiceCandidateHandlerBL creatorBL = Services.get(IInvoiceCandidateHandlerBL.class);

			final MOrder order = (MOrder)po;
			final List<IInvoiceCandidateHandler> invalidators = creatorBL.retrieveImplementationsForTable(po.getCtx(), I_C_OrderLine.Table_Name);

			for (final MOrderLine olPO : order.getLines())
			{
				final I_C_OrderLine ol = POWrapper.create(olPO, I_C_OrderLine.class);

				for (final IInvoiceCandidateHandler invalidator : invalidators)
				{
					invalidator.invalidateCandidatesFor(ol);
				}
			}
		}
		return null;
	}

}
