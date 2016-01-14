package org.adempiere.acct.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.Map;
import java.util.Properties;

import org.adempiere.acct.api.IFactAcctBL;
import org.adempiere.acct.api.IFactAcctDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.model.I_Fact_Acct;
import org.compiere.model.MAccount;
import org.compiere.report.core.RColumn;
import org.compiere.report.core.RModel;
import org.compiere.util.DisplayType;

public class FactAcctBL implements IFactAcctBL
{
	@Override
	public RColumn createEndingBalanceRColumn(final Properties ctx, final Map<String, String> columnName2whereClause)
	{
		return new RColumn(ctx, "EndingBalance", DisplayType.Amount, IFactAcctDAO.DB_FUNC_Fact_Acct_EndingBalance+"(" + RModel.TABLE_ALIAS + ")");
	}

	@Override
	public MAccount getAccount(final I_Fact_Acct factAcct)
	{
		Check.assumeNotNull(factAcct, "factAcct not null");
		final Properties ctx = InterfaceWrapperHelper.getCtx(factAcct);
		final MAccount acct = MAccount.get (ctx, factAcct.getAD_Client_ID(), factAcct.getAD_Org_ID(),
			factAcct.getC_AcctSchema_ID(), factAcct.getAccount_ID(), factAcct.getC_SubAcct_ID(),
			factAcct.getM_Product_ID(), factAcct.getC_BPartner_ID(), factAcct.getAD_OrgTrx_ID(), 
			factAcct.getC_LocFrom_ID(), factAcct.getC_LocTo_ID(), factAcct.getC_SalesRegion_ID(), 
			factAcct.getC_Project_ID(), factAcct.getC_Campaign_ID(), factAcct.getC_Activity_ID(),
			factAcct.getUser1_ID(), factAcct.getUser2_ID(), factAcct.getUserElement1_ID(), factAcct.getUserElement2_ID());
		if (acct == null)
		{
			throw new AdempiereException("@NotFound@ @C_ValidCombination_ID@ - "+factAcct);
		}
		if (acct != null && acct.getC_ValidCombination_ID() <= 0)
		{
			InterfaceWrapperHelper.save(acct);
		}
		return acct;
	}
}
