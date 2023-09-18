/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.model;

import de.metas.acct.api.AccountDimension;
import de.metas.acct.api.IAccountDAO;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import org.slf4j.Logger;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Account Object Entity to maintain all segment values. C_ValidCombination
 *
 * @author Jorg Janke
 * @author victor.perez@e-evolution.com, www.e-evolution.com
 * <li>RF [ 2214883 ] Remove SQL code and Replace for Query
 * @author Teo Sarca, www.arhipac.ro
 * <li>FR [ 2694043 ] Query. first/firstOnly usage best practice
 */
@Deprecated
public class MAccount extends X_C_ValidCombination
{
	/**
	 * Get existing Account or create it.
	 *
	 * @param dimension accounting dimension
	 * @return existing account or a newly created one; never returns null
	 */
	@Deprecated
	public static MAccount get(final AccountDimension dimension)
	{
		// services
		final IAccountDAO accountDAO = Services.get(IAccountDAO.class);
		return accountDAO.getOrCreateAccount(dimension);
	}

	private static final Logger logger = LogManager.getLogger(MAccount.class);

	public MAccount(Properties ctx, int C_ValidCombination_ID, String trxName)
	{
		super(ctx, C_ValidCombination_ID, trxName);
		if (is_new())
		{
			// setAccount_ID (0);
			// setC_AcctSchema_ID (0);
			setIsFullyQualified(false);
		}
	}

	public MAccount(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("MAccount=[");
		sb.append(getC_ValidCombination_ID());
		if (getCombination() != null)
			sb.append(",").append(getCombination());
		else
		{
			// .append(",Client=").append(getAD_Client_ID())
			sb.append(",Schema=").append(getC_AcctSchema_ID())
					.append(",Org=").append(getAD_Org_ID())
					.append(",Acct=").append(getAccount_ID())
					.append(" ");
			if (getC_SubAcct_ID() > 0)
				sb.append(",C_SubAcct_ID=").append(getC_SubAcct_ID());
			if (getM_Product_ID() > 0)
				sb.append(",M_Product_ID=").append(getM_Product_ID());
			if (getC_BPartner_ID() > 0)
				sb.append(",C_BPartner_ID=").append(getC_BPartner_ID());
			if (getAD_OrgTrx_ID() > 0)
				sb.append(",AD_OrgTrx_ID=").append(getAD_OrgTrx_ID());
			if (getC_LocFrom_ID() > 0)
				sb.append(",C_LocFrom_ID=").append(getC_LocFrom_ID());
			if (getC_LocTo_ID() > 0)
				sb.append(",C_LocTo_ID=").append(getC_LocTo_ID());
			if (getC_SalesRegion_ID() > 0)
				sb.append(",C_SalesRegion_ID=").append(getC_SalesRegion_ID());
			if (getC_Project_ID() > 0)
				sb.append(",C_Project_ID=").append(getC_Project_ID());
			if (getC_Campaign_ID() > 0)
				sb.append(",C_Campaign_ID=").append(getC_Campaign_ID());
			if (getC_Activity_ID() > 0)
				sb.append(",C_Activity_ID=").append(getC_Activity_ID());
			if (getUser1_ID() > 0)
				sb.append(",User1_ID=").append(getUser1_ID());
			if (getUser2_ID() > 0)
				sb.append(",User2_ID=").append(getUser2_ID());
			if (getUserElement1_ID() > 0)
				sb.append(",UserElement1_ID=").append(getUserElement1_ID());
			if (getUserElement2_ID() > 0)
				sb.append(",UserElement2_ID=").append(getUserElement2_ID());
			if (getC_Harvesting_Calendar_ID() > 0)
				sb.append(",C_Harvesting_Calendar_ID=").append(getC_Harvesting_Calendar_ID());
			if (getHarvesting_Year_ID() > 0)
				sb.append(",Harvesting_Year_ID=").append(getHarvesting_Year_ID());
		}
		sb.append("]");
		return sb.toString();
	}

	private String getAccountType()
	{
		final I_C_ElementValue elementValue = getAccount();
		if (elementValue == null)
		{
			logger.error("No ElementValue for Account_ID={}", getAccount_ID());
			return "";
		}
		return elementValue.getAccountType();
	}

	public boolean isBalanceSheet()
	{
		String accountType = getAccountType();
		return (X_C_ElementValue.ACCOUNTTYPE_Asset.equals(accountType)
				|| X_C_ElementValue.ACCOUNTTYPE_Liability.equals(accountType)
				|| X_C_ElementValue.ACCOUNTTYPE_OwnerSEquity.equals(accountType));
	}

	public boolean isActiva()
	{
		return X_C_ElementValue.ACCOUNTTYPE_Asset.equals(getAccountType());
	}

	public boolean isPassiva()
	{
		String accountType = getAccountType();
		return (X_C_ElementValue.ACCOUNTTYPE_Liability.equals(accountType)
				|| X_C_ElementValue.ACCOUNTTYPE_OwnerSEquity.equals(accountType));
	}
}
