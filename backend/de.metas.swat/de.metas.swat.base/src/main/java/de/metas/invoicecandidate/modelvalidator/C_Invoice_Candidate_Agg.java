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


import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;

import de.metas.invoicecandidate.api.IAggregationBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg;
import de.metas.util.Services;

public class C_Invoice_Candidate_Agg implements ModelValidator
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

		engine.addModelChange(I_C_Invoice_Candidate_Agg.Table_Name, this);
	}

	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		return null;
	}

	@Override
	public String modelChange(final PO po, final int type) throws Exception
	{
		if (type == TYPE_BEFORE_NEW || type == TYPE_BEFORE_CHANGE)
		{
			if (po.is_ValueChanged(I_C_Invoice_Candidate_Agg.COLUMNNAME_Classname))
			{
				final I_C_Invoice_Candidate_Agg icAgg = InterfaceWrapperHelper.create(po, I_C_Invoice_Candidate_Agg.class);
				Services.get(IAggregationBL.class).evalClassName(icAgg);
			}

			// Note: we invalidate *every* candidate, so there is no need to use the different IInvoiceCandidateHandler implementations.
			final IInvoiceCandDAO invoiceCandDB = Services.get(IInvoiceCandDAO.class);
			invoiceCandDB.invalidateAllCands(po.getCtx(), po.get_TrxName());
		}
		return null;
	}

	@Override
	public String docValidate(final PO po, final int timing)
	{
		return null;
	}

}
