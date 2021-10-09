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
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_Fact_Acct;
import org.compiere.model.MAccount;
import org.compiere.report.core.RColumn;
import org.compiere.report.core.RModel;
import org.compiere.util.DisplayType;

import de.metas.acct.api.AccountDimension;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IFactAcctDAO;
import de.metas.acct.api.impl.AcctSegmentType;
import de.metas.util.Check;

public class FactAcctBL implements IFactAcctBL
{
	@Override
	public RColumn createEndingBalanceRColumn(final Properties ctx, final Map<String, String> columnName2whereClause)
	{
		return new RColumn(ctx, "EndingBalance", DisplayType.Amount, IFactAcctDAO.DB_FUNC_Fact_Acct_EndingBalance + "(" + RModel.TABLE_ALIAS + ")");
	}

	@Override
	public MAccount getAccount(final I_Fact_Acct factAcct)
	{
		Check.assumeNotNull(factAcct, "factAcct not null");

		final Properties ctx = InterfaceWrapperHelper.getCtx(factAcct);
		final AccountDimension accountDimension = createAccountDimension(factAcct);
		final MAccount acct = MAccount.get(ctx, accountDimension);
		return acct;
	}

	@Override
	public AccountDimension createAccountDimension(final I_Fact_Acct fa)
	{
		return AccountDimension.builder()
				.setAcctSchemaId(AcctSchemaId.ofRepoId(fa.getC_AcctSchema_ID()))
				.setAD_Client_ID(fa.getAD_Client_ID())
				.setAD_Org_ID(fa.getAD_Org_ID())
				.setC_ElementValue_ID(fa.getAccount_ID())
				.setC_SubAcct_ID(fa.getC_SubAcct_ID())
				.setM_Product_ID(fa.getM_Product_ID())
				.setC_BPartner_ID(fa.getC_BPartner_ID())
				.setAD_OrgTrx_ID(fa.getAD_OrgTrx_ID())
				.setC_LocFrom_ID(fa.getC_LocFrom_ID())
				.setC_LocTo_ID(fa.getC_LocTo_ID())
				.setC_SalesRegion_ID(fa.getC_SalesRegion_ID())
				.setC_Project_ID(fa.getC_Project_ID())
				.setC_Campaign_ID(fa.getC_Campaign_ID())
				.setC_Activity_ID(fa.getC_Activity_ID())
				.setUser1_ID(fa.getUser1_ID())
				.setUser2_ID(fa.getUser2_ID())
				.setUserElement1_ID(fa.getUserElement1_ID())
				.setUserElement2_ID(fa.getUserElement2_ID())
				.setUserElementString1(fa.getUserElementString1())
				.setUserElementString2(fa.getUserElementString2())
				.setUserElementString3(fa.getUserElementString3())
				.setUserElementString4(fa.getUserElementString4())
				.setUserElementString5(fa.getUserElementString5())
				.setUserElementString6(fa.getUserElementString6())
				.setUserElementString7(fa.getUserElementString7())

				.build();
	}

	@Override
	public void updateFactLineFromDimension(final I_Fact_Acct fa, final AccountDimension dim)
	{
		if (dim.getAcctSchemaId() != null)
		{
			fa.setC_AcctSchema_ID(dim.getAcctSchemaId().getRepoId());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.Client))
		{
			// fa.setAD_Client_ID(dim.getAD_Client_ID());
			Check.assume(fa.getAD_Client_ID() == dim.getAD_Client_ID(), "Fact_Acct and dimension shall have the same AD_Client_ID");
		}
		if (dim.isSegmentValueSet(AcctSegmentType.Organization))
		{
			fa.setAD_Org_ID(dim.getAD_Org_ID());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.Account))
		{
			fa.setAccount_ID(dim.getC_ElementValue_ID());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.SubAccount))
		{
			fa.setC_SubAcct_ID(dim.getC_SubAcct_ID());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.Product))
		{
			fa.setM_Product_ID(dim.getM_Product_ID());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.BPartner))
		{
			fa.setC_BPartner_ID(dim.getC_BPartner_ID());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.OrgTrx))
		{
			fa.setAD_OrgTrx_ID(dim.getAD_OrgTrx_ID());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.LocationFrom))
		{
			fa.setC_LocFrom_ID(dim.getC_LocFrom_ID());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.LocationTo))
		{
			fa.setC_LocTo_ID(dim.getC_LocTo_ID());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.SalesRegion))
		{
			fa.setC_SalesRegion_ID(dim.getC_SalesRegion_ID());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.Project))
		{
			fa.setC_Project_ID(dim.getC_Project_ID());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.Campaign))
		{
			fa.setC_Campaign_ID(dim.getC_Campaign_ID());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.Activity))
		{
			fa.setC_Activity_ID(dim.getC_Activity_ID());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.UserList1))
		{
			fa.setUser1_ID(dim.getUser1_ID());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.UserList2))
		{
			fa.setUser2_ID(dim.getUser2_ID());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.UserElement1))
		{
			fa.setUserElement1_ID(dim.getUserElement1_ID());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.UserElement2))
		{
			fa.setUserElement2_ID(dim.getUserElement2_ID());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.UserElementString1))
		{
			fa.setUserElementString1(dim.getUserElementString1());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.UserElementString2))
		{
			fa.setUserElementString2(dim.getUserElementString2());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.UserElementString3))
		{
			fa.setUserElementString3(dim.getUserElementString3());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.UserElementString4))
		{
			fa.setUserElementString4(dim.getUserElementString4());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.UserElementString5))
		{
			fa.setUserElementString5(dim.getUserElementString5());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.UserElementString6))
		{
			fa.setUserElementString6(dim.getUserElementString6());
		}
		if (dim.isSegmentValueSet(AcctSegmentType.UserElementString7))
		{
			fa.setUserElementString7(dim.getUserElementString7());
		}
	}
}
