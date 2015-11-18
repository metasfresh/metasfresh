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
import java.util.Map.Entry;
import java.util.Properties;

import org.adempiere.acct.api.IFactAcctBL;
import org.adempiere.acct.api.IFactAcctCubeUpdater;
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
	public IFactAcctCubeUpdater createFactAcctCubeUpdater()
	{
		return new FactAcctCubeUpdater();
	}

	@Override
	public RColumn createEndingBalanceRColumn(final Properties ctx, final Map<String, String> columnName2whereClause)
	{
		final StringBuilder sql = new StringBuilder("SELECT "
				+ " acctBalance("
				+ "fa_eb.Account_ID"
				+ ", COALESCE(SUM(fa_eb.AmtAcctDr), 0)"
				+ ", COALESCE(SUM(fa_eb.AmtAcctCr), 0)"
				+ ")"
				+ " FROM " + I_Fact_Acct.Table_Name + " fa_eb"
				+ " WHERE ");

		// Same AD_Client_ID
		sql.append(" fa_eb." + I_Fact_Acct.COLUMNNAME_AD_Client_ID + "=" + RModel.TABLE_ALIAS + "." + I_Fact_Acct.COLUMNNAME_AD_Client_ID);
		// Same AD_Org_ID
		sql.append(" AND fa_eb." + I_Fact_Acct.COLUMNNAME_AD_Org_ID + "=" + RModel.TABLE_ALIAS + "." + I_Fact_Acct.COLUMNNAME_AD_Org_ID);
		// Same account
		sql.append(" AND fa_eb." + I_Fact_Acct.COLUMNNAME_Account_ID + "=" + RModel.TABLE_ALIAS + "." + I_Fact_Acct.COLUMNNAME_Account_ID);
		// Same accouting schema
		sql.append(" AND fa_eb." + I_Fact_Acct.COLUMNNAME_C_AcctSchema_ID + "=" + RModel.TABLE_ALIAS + "." + I_Fact_Acct.COLUMNNAME_C_AcctSchema_ID);
		// Same posting type
		sql.append(" AND fa_eb." + I_Fact_Acct.COLUMNNAME_PostingType + "=" + RModel.TABLE_ALIAS + "." + I_Fact_Acct.COLUMNNAME_PostingType);
		

		// Other accouting dimension filters
		if (columnName2whereClause != null)
		{
			for (final Entry<String,String> columnName2acctDimensionWhereClause : columnName2whereClause.entrySet())
			{
				final String columnName = columnName2acctDimensionWhereClause.getKey();
				
				// skip the Account_ID filter if any (shall not be) because we are enforcing same Account_ID
				if (I_Fact_Acct.COLUMNNAME_Account_ID.equals(columnName))
				{
					continue;
				}
				
				final String acctDimensionWhereClause = columnName2acctDimensionWhereClause.getValue();
				if (Check.isEmpty(acctDimensionWhereClause, true))
				{
					continue;
				}

				sql.append(" AND (fa_eb.").append(acctDimensionWhereClause.trim()).append(")");
			}
		}

		//
		// Previous bookings
		//@formatter:off
		sql.append(" AND ("
					+ "TRUNC(fa_eb.DateAcct) < TRUNC(" + RModel.TABLE_ALIAS + ".DateAcct)"
					+ " OR (" // task 07894
						+ "TRUNC(fa_eb.DateAcct) = TRUNC(" + RModel.TABLE_ALIAS + ".DateAcct)"
						+ " AND fa_eb.Fact_Acct_ID <= " + RModel.TABLE_ALIAS + ".Fact_Acct_ID"
					+ ")"
				+" )"
				);
		//@formatter:on

		//
		// GROUP BYs
		sql.append(" GROUP BY fa_eb.Account_ID");

		return new RColumn(ctx, "EndingBalance", DisplayType.Amount, "(" + sql + ")");
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
