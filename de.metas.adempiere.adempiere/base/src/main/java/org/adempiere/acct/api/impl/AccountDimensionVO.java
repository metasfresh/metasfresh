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


import org.adempiere.acct.api.IAccountDimension;
import org.compiere.util.Env;

public class AccountDimensionVO implements IAccountDimension
{
	private String alias = null;
	private int aD_Client_ID = -1;
	private int aD_Org_ID = Env.CTXVALUE_AD_Org_ID_System;
	private int c_AcctSchema_ID = -1;
	private int C_ElementValue_ID = -1;
	private int c_SubAcct_ID = 0;
	private int m_Product_ID = 0;
	private int c_BPartner_ID = 0;
	private int aD_OrgTrx_ID = 0;
	private int c_LocFrom_ID = 0;
	private int c_LocTo_ID = 0;
	private int c_SalesRegion_ID = 0;
	private int c_Project_ID = 0;
	private int c_Campaign_ID = 0;
	private int c_Activity_ID = 0;
	private int user1_ID = 0;
	private int user2_ID = 0;
	private int userElement1_ID = 0;
	private int userElement2_ID = 0;

	public AccountDimensionVO()
	{
		super();
	}

	public AccountDimensionVO(
			final int aD_Client_ID,
			final int aD_Org_ID,
			final int c_AcctSchema_ID,
			final int account_ID,
			final int c_SubAcct_ID,
			final int m_Product_ID,
			final int c_BPartner_ID,
			final int aD_OrgTrx_ID,
			final int c_LocFrom_ID,
			final int c_LocTo_ID,
			final int c_SalesRegion_ID,
			final int c_Project_ID,
			final int c_Campaign_ID,
			final int c_Activity_ID,
			final int user1_ID,
			final int user2_ID,
			final int userElement1_ID,
			final int userElement2_ID)
	{
		this();

		this.aD_Client_ID = aD_Client_ID;
		this.aD_Org_ID = aD_Org_ID;
		this.c_AcctSchema_ID = c_AcctSchema_ID;
		C_ElementValue_ID = account_ID;
		this.c_SubAcct_ID = c_SubAcct_ID;
		this.m_Product_ID = m_Product_ID;
		this.c_BPartner_ID = c_BPartner_ID;
		this.aD_OrgTrx_ID = aD_OrgTrx_ID;
		this.c_LocFrom_ID = c_LocFrom_ID;
		this.c_LocTo_ID = c_LocTo_ID;
		this.c_SalesRegion_ID = c_SalesRegion_ID;
		this.c_Project_ID = c_Project_ID;
		this.c_Campaign_ID = c_Campaign_ID;
		this.c_Activity_ID = c_Activity_ID;
		this.user1_ID = user1_ID;
		this.user2_ID = user2_ID;
		this.userElement1_ID = userElement1_ID;
		this.userElement2_ID = userElement2_ID;
	}

	@Override
	public String toString()
	{
		return "AccountDimensionVO [alias=" + alias + ", aD_Client_ID=" + aD_Client_ID + ", aD_Org_ID=" + aD_Org_ID + ", c_AcctSchema_ID=" + c_AcctSchema_ID + ", C_ElementValue_ID="
				+ C_ElementValue_ID + ", c_SubAcct_ID=" + c_SubAcct_ID + ", m_Product_ID=" + m_Product_ID + ", c_BPartner_ID=" + c_BPartner_ID + ", aD_OrgTrx_ID=" + aD_OrgTrx_ID + ", c_LocFrom_ID="
				+ c_LocFrom_ID + ", c_LocTo_ID=" + c_LocTo_ID + ", c_SalesRegion_ID=" + c_SalesRegion_ID + ", c_Project_ID=" + c_Project_ID + ", c_Campaign_ID=" + c_Campaign_ID + ", c_Activity_ID="
				+ c_Activity_ID + ", user1_ID=" + user1_ID + ", user2_ID=" + user2_ID + ", userElement1_ID=" + userElement1_ID + ", userElement2_ID=" + userElement2_ID + "]";
	}

	@Override
	public String getAlias()
	{
		return alias;
	}

	@Override
	public void setAlias(final String alias)
	{
		this.alias = alias;
	}

	@Override
	public int getAD_Client_ID()
	{
		return aD_Client_ID;
	}

	@Override
	public void setAD_Client_ID(final int aD_Client_ID)
	{
		this.aD_Client_ID = aD_Client_ID;
	}

	@Override
	public int getAD_Org_ID()
	{
		return aD_Org_ID;
	}

	@Override
	public void setAD_Org_ID(final int aD_Org_ID)
	{
		this.aD_Org_ID = aD_Org_ID;
	}

	@Override
	public int getC_AcctSchema_ID()
	{
		return c_AcctSchema_ID;
	}

	@Override
	public void setC_AcctSchema_ID(final int c_AcctSchema_ID)
	{
		this.c_AcctSchema_ID = c_AcctSchema_ID;
	}

	@Override
	public int getC_ElementValue_ID()
	{
		return C_ElementValue_ID;
	}

	@Override
	public void setC_ElementValue_ID(final int C_ElementValue_ID)
	{
		this.C_ElementValue_ID = C_ElementValue_ID;
	}

	@Override
	public int getC_SubAcct_ID()
	{
		return c_SubAcct_ID;
	}

	@Override
	public void setC_SubAcct_ID(final int c_SubAcct_ID)
	{
		this.c_SubAcct_ID = c_SubAcct_ID;
	}

	@Override
	public int getM_Product_ID()
	{
		return m_Product_ID;
	}

	@Override
	public void setM_Product_ID(final int m_Product_ID)
	{
		this.m_Product_ID = m_Product_ID;
	}

	@Override
	public int getC_BPartner_ID()
	{
		return c_BPartner_ID;
	}

	@Override
	public void setC_BPartner_ID(final int c_BPartner_ID)
	{
		this.c_BPartner_ID = c_BPartner_ID;
	}

	@Override
	public int getAD_OrgTrx_ID()
	{
		return aD_OrgTrx_ID;
	}

	@Override
	public void setAD_OrgTrx_ID(final int aD_OrgTrx_ID)
	{
		this.aD_OrgTrx_ID = aD_OrgTrx_ID;
	}

	@Override
	public int getC_LocFrom_ID()
	{
		return c_LocFrom_ID;
	}

	@Override
	public void setC_LocFrom_ID(final int c_LocFrom_ID)
	{
		this.c_LocFrom_ID = c_LocFrom_ID;
	}

	@Override
	public int getC_LocTo_ID()
	{
		return c_LocTo_ID;
	}

	@Override
	public void setC_LocTo_ID(final int c_LocTo_ID)
	{
		this.c_LocTo_ID = c_LocTo_ID;
	}

	@Override
	public int getC_SalesRegion_ID()
	{
		return c_SalesRegion_ID;
	}

	@Override
	public void setC_SalesRegion_ID(final int c_SalesRegion_ID)
	{
		this.c_SalesRegion_ID = c_SalesRegion_ID;
	}

	@Override
	public int getC_Project_ID()
	{
		return c_Project_ID;
	}

	@Override
	public void setC_Project_ID(final int c_Project_ID)
	{
		this.c_Project_ID = c_Project_ID;
	}

	@Override
	public int getC_Campaign_ID()
	{
		return c_Campaign_ID;
	}

	@Override
	public void setC_Campaign_ID(final int c_Campaign_ID)
	{
		this.c_Campaign_ID = c_Campaign_ID;
	}

	@Override
	public int getC_Activity_ID()
	{
		return c_Activity_ID;
	}

	@Override
	public void setC_Activity_ID(final int c_Activity_ID)
	{
		this.c_Activity_ID = c_Activity_ID;
	}

	@Override
	public int getUser1_ID()
	{
		return user1_ID;
	}

	@Override
	public void setUser1_ID(final int user1_ID)
	{
		this.user1_ID = user1_ID;
	}

	@Override
	public int getUser2_ID()
	{
		return user2_ID;
	}

	@Override
	public void setUser2_ID(final int user2_ID)
	{
		this.user2_ID = user2_ID;
	}

	@Override
	public int getUserElement1_ID()
	{
		return userElement1_ID;
	}

	@Override
	public void setUserElement1_ID(final int userElement1_ID)
	{
		this.userElement1_ID = userElement1_ID;
	}

	@Override
	public int getUserElement2_ID()
	{
		return userElement2_ID;
	}

	@Override
	public void setUserElement2_ID(final int userElement2_ID)
	{
		this.userElement2_ID = userElement2_ID;
	}
}
