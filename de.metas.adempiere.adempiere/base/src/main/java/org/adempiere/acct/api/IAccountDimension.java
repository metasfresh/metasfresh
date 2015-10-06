package org.adempiere.acct.api;

/*
 * #%L
 * ADempiere ERP - Base
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


/**
 * Account dimension: holds all accounting segments (AD_Org_ID, C_ElementValue_ID, M_Product_ID, C_BPartner_ID etc).
 * 
 * @author tsa
 *
 */
public interface IAccountDimension
{
	/**
	 * 
	 * @return account alias
	 */
	String getAlias();

	/**
	 * 
	 * @param alias account alias
	 */
	void setAlias(String alias);

	int getAD_Client_ID();

	void setAD_Client_ID(int AD_Client_ID);

	int getAD_Org_ID();

	void setAD_Org_ID(int AD_Org_ID);

	int getC_AcctSchema_ID();

	void setC_AcctSchema_ID(int c_AcctSchema_ID);

	int getC_ElementValue_ID();

	void setC_ElementValue_ID(int account_ID);

	int getC_SubAcct_ID();

	void setC_SubAcct_ID(int c_SubAcct_ID);

	int getM_Product_ID();

	void setM_Product_ID(int m_Product_ID);

	int getC_BPartner_ID();

	void setC_BPartner_ID(int c_BPartner_ID);

	int getAD_OrgTrx_ID();

	void setAD_OrgTrx_ID(int aD_OrgTrx_ID);

	int getC_LocFrom_ID();

	void setC_LocFrom_ID(int c_LocFrom_ID);

	int getC_LocTo_ID();

	void setC_LocTo_ID(int c_LocTo_ID);

	int getC_SalesRegion_ID();

	void setC_SalesRegion_ID(int c_SalesRegion_ID);

	int getC_Project_ID();

	void setC_Project_ID(int c_Project_ID);

	int getC_Campaign_ID();

	void setC_Campaign_ID(int c_Campaign_ID);

	int getC_Activity_ID();

	void setC_Activity_ID(int c_Activity_ID);

	int getUser1_ID();

	void setUser1_ID(int user1_ID);

	int getUser2_ID();

	void setUser2_ID(int user2_ID);

	int getUserElement1_ID();

	void setUserElement1_ID(int userElement1_ID);

	int getUserElement2_ID();

	void setUserElement2_ID(int userElement2_ID);
}