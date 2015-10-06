/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.acct.api.IAccountBL;
import org.adempiere.acct.api.IAccountDimension;
import org.adempiere.acct.api.impl.AccountDimensionVO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.util.CLogger;

/**
 * Account Object Entity to maintain all segment values. C_ValidCombination
 *
 * @author Jorg Janke
 * @author victor.perez@e-evolution.com, www.e-evolution.com <li>RF [ 2214883 ] Remove SQL code and Replace for Query
 *         http://sourceforge.net/tracker/index.php?func=detail&aid=2214883&group_id=176962&atid=879335
 * @author Teo Sarca, www.arhipac.ro <li>FR [ 2694043 ] Query. first/firstOnly usage best practice
 * @version $Id: MAccount.java,v 1.4 2006/07/30 00:58:04 jjanke Exp $
 */
public class MAccount extends X_C_ValidCombination
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8887316767838767993L;

	/**
	 * Get existing Account or create it
	 *
	 * @param ctx context
	 * @param AD_Client_ID
	 * @param AD_Org_ID
	 * @param C_AcctSchema_ID
	 * @param Account_ID
	 * @param C_SubAcct_ID
	 * @param M_Product_ID
	 * @param C_BPartner_ID
	 * @param AD_OrgTrx_ID
	 * @param C_LocFrom_ID
	 * @param C_LocTo_ID
	 * @param C_SalesRegion_ID
	 * @param C_Project_ID
	 * @param C_Campaign_ID
	 * @param C_Activity_ID
	 * @param User1_ID
	 * @param User2_ID
	 * @param UserElement1_ID
	 * @param UserElement2_ID
	 * @return account or null
	 * @deprecated Use {@link #get(Properties,AccountDimensionVO)} instead
	 */
	public static MAccount get(Properties ctx,
			int AD_Client_ID, int AD_Org_ID, int C_AcctSchema_ID,
			int Account_ID, int C_SubAcct_ID,
			int M_Product_ID, int C_BPartner_ID, int AD_OrgTrx_ID,
			int C_LocFrom_ID, int C_LocTo_ID, int C_SalesRegion_ID,
			int C_Project_ID, int C_Campaign_ID, int C_Activity_ID,
			int User1_ID, int User2_ID, int UserElement1_ID, int UserElement2_ID)
	{
		return get(ctx, new AccountDimensionVO(AD_Client_ID, AD_Org_ID, C_AcctSchema_ID, Account_ID, C_SubAcct_ID, M_Product_ID, C_BPartner_ID, AD_OrgTrx_ID, C_LocFrom_ID, C_LocTo_ID,
				C_SalesRegion_ID, C_Project_ID, C_Campaign_ID, C_Activity_ID, User1_ID, User2_ID, UserElement1_ID, UserElement2_ID));
	}	// get

	/**
	 * Get existing Account or create it
	 *
	 * @param ctx context
	 * @param dimension accounting dimension
	 * @return account or null
	 */
	public static MAccount get(final Properties ctx, final IAccountDimension dimension)
	{
		final StringBuilder info = new StringBuilder();
		info.append("AD_Client_ID=").append(dimension.getAD_Client_ID()).append(",AD_Org_ID=").append(dimension.getAD_Org_ID());
		// Schema
		info.append(",C_AcctSchema_ID=").append(dimension.getC_AcctSchema_ID());
		// Account
		info.append(",Account_ID=").append(dimension.getC_ElementValue_ID()).append(" ");

		final List<Object> params = new ArrayList<>();
		// Mandatory fields
		final StringBuilder whereClause = new StringBuilder("AD_Client_ID=?"		// #1
				+ " AND AD_Org_ID=?"
				+ " AND C_AcctSchema_ID=?"
				+ " AND Account_ID=?");			// #4
		params.add(dimension.getAD_Client_ID());
		params.add(dimension.getAD_Org_ID());
		params.add(dimension.getC_AcctSchema_ID());
		params.add(dimension.getC_ElementValue_ID());
		// Optional fields
		if (dimension.getC_SubAcct_ID() == 0)
			whereClause.append(" AND C_SubAcct_ID IS NULL");
		else
		{
			whereClause.append(" AND C_SubAcct_ID=?");
			params.add(dimension.getC_SubAcct_ID());
		}
		if (dimension.getM_Product_ID() == 0)
			whereClause.append(" AND M_Product_ID IS NULL");
		else
		{
			whereClause.append(" AND M_Product_ID=?");
			params.add(dimension.getM_Product_ID());
		}
		if (dimension.getC_BPartner_ID() == 0)
			whereClause.append(" AND C_BPartner_ID IS NULL");
		else
		{
			whereClause.append(" AND C_BPartner_ID=?");
			params.add(dimension.getC_BPartner_ID());
		}
		if (dimension.getAD_OrgTrx_ID() == 0)
			whereClause.append(" AND AD_OrgTrx_ID IS NULL");
		else
		{
			whereClause.append(" AND AD_OrgTrx_ID=?");
			params.add(dimension.getAD_OrgTrx_ID());
		}
		if (dimension.getC_LocFrom_ID() == 0)
			whereClause.append(" AND C_LocFrom_ID IS NULL");
		else
		{
			whereClause.append(" AND C_LocFrom_ID=?");
			params.add(dimension.getC_LocFrom_ID());
		}
		if (dimension.getC_LocTo_ID() == 0)
			whereClause.append(" AND C_LocTo_ID IS NULL");
		else
		{
			whereClause.append(" AND C_LocTo_ID=?");
			params.add(dimension.getC_LocTo_ID());
		}
		if (dimension.getC_SalesRegion_ID() == 0)
			whereClause.append(" AND C_SalesRegion_ID IS NULL");
		else
		{
			whereClause.append(" AND C_SalesRegion_ID=?");
			params.add(dimension.getC_SalesRegion_ID());
		}
		if (dimension.getC_Project_ID() == 0)
			whereClause.append(" AND C_Project_ID IS NULL");
		else
		{
			whereClause.append(" AND C_Project_ID=?");
			params.add(dimension.getC_Project_ID());
		}
		if (dimension.getC_Campaign_ID() == 0)
			whereClause.append(" AND C_Campaign_ID IS NULL");
		else
		{
			whereClause.append(" AND C_Campaign_ID=?");
			params.add(dimension.getC_Campaign_ID());
		}
		if (dimension.getC_Activity_ID() == 0)
			whereClause.append(" AND C_Activity_ID IS NULL");
		else
		{
			whereClause.append(" AND C_Activity_ID=?");
			params.add(dimension.getC_Activity_ID());
		}
		if (dimension.getUser1_ID() == 0)
			whereClause.append(" AND User1_ID IS NULL");
		else
		{
			whereClause.append(" AND User1_ID=?");
			params.add(dimension.getUser1_ID());
		}
		if (dimension.getUser2_ID() == 0)
			whereClause.append(" AND User2_ID IS NULL");
		else
		{
			whereClause.append(" AND User2_ID=?");
			params.add(dimension.getUser2_ID());
		}
		if (dimension.getUserElement1_ID() == 0)
			whereClause.append(" AND UserElement1_ID IS NULL");
		else
		{
			whereClause.append(" AND UserElement1_ID=?");
			params.add(dimension.getUserElement1_ID());
		}
		if (dimension.getUserElement2_ID() == 0)
			whereClause.append(" AND UserElement2_ID IS NULL");
		else
		{
			whereClause.append(" AND UserElement2_ID=?");
			params.add(dimension.getUserElement2_ID());
		}
		// whereClause.append(" ORDER BY IsFullyQualified DESC");

		MAccount existingAccount = new Query(ctx, I_C_ValidCombination.Table_Name, whereClause.toString(), null)
				.setParameters(params)
				.setOnlyActiveRecords(true)
				.firstOnly();

		// Existing
		if (existingAccount != null)
			return existingAccount;

		// New
		MAccount newAccount = new MAccount(ctx, 0, ITrx.TRXNAME_None);
		newAccount.setClientOrg(dimension.getAD_Client_ID(), dimension.getAD_Org_ID());
		newAccount.setC_AcctSchema_ID(dimension.getC_AcctSchema_ID());
		newAccount.setAccount_ID(dimension.getC_ElementValue_ID());
		// -- Optional Accounting fields
		newAccount.setC_SubAcct_ID(dimension.getC_SubAcct_ID());
		newAccount.setM_Product_ID(dimension.getM_Product_ID());
		newAccount.setC_BPartner_ID(dimension.getC_BPartner_ID());
		newAccount.setAD_OrgTrx_ID(dimension.getAD_OrgTrx_ID());
		newAccount.setC_LocFrom_ID(dimension.getC_LocFrom_ID());
		newAccount.setC_LocTo_ID(dimension.getC_LocTo_ID());
		newAccount.setC_SalesRegion_ID(dimension.getC_SalesRegion_ID());
		newAccount.setC_Project_ID(dimension.getC_Project_ID());
		newAccount.setC_Campaign_ID(dimension.getC_Campaign_ID());
		newAccount.setC_Activity_ID(dimension.getC_Activity_ID());
		newAccount.setUser1_ID(dimension.getUser1_ID());
		newAccount.setUser2_ID(dimension.getUser2_ID());
		newAccount.setUserElement1_ID(dimension.getUserElement1_ID());
		newAccount.setUserElement2_ID(dimension.getUserElement2_ID());
		//
		if (!newAccount.save())
		{
			s_log.log(Level.SEVERE, "Could not create new account - " + info);
			return null;
		}
		s_log.fine("New: " + newAccount);
		return newAccount;
	}	// get

	/**
	 * Get from existing Accounting fact
	 *
	 * @param fa accounting fact
	 * @return account
	 */
	public static MAccount get(final I_Fact_Acct fa)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(fa);
		final IAccountDimension accountDimension = Services.get(IAccountBL.class).createAccountDimension(fa);
		final MAccount acct = get(ctx, accountDimension);
		return acct;
	}	// get

	/**************************************************************************
	 * Factory: default combination
	 * 
	 * @param ctx context
	 * @param C_AcctSchema_ID accounting schema
	 * @param optionalNull if true the optional values are null
	 * @param trxName transaction
	 * @return Account
	 */
	public static MAccount getDefault(Properties ctx, int C_AcctSchema_ID, boolean optionalNull, String trxName)
	{
		MAcctSchema acctSchema = new MAcctSchema(ctx, C_AcctSchema_ID, trxName);
		return getDefault(acctSchema, optionalNull);
	}   // getDefault

	/**
	 * Factory: default combination
	 * 
	 * @param acctSchema accounting schema
	 * @param optionalNull if true, the optional values are null
	 * @return Account
	 */
	public static MAccount getDefault(MAcctSchema acctSchema, boolean optionalNull)
	{
		final MAccount vc = new MAccount(acctSchema);
		// Active Elements
		MAcctSchemaElement[] elements = acctSchema.getAcctSchemaElements();
		for (int i = 0; i < elements.length; i++)
		{
			MAcctSchemaElement ase = elements[i];
			String elementType = ase.getElementType();
			int defaultValue = ase.getDefaultValue();
			boolean setValue = ase.isMandatory() || (!ase.isMandatory() && !optionalNull);
			//
			if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_Organization))
				vc.setAD_Org_ID(defaultValue);
			else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_Account))
				vc.setAccount_ID(defaultValue);
			else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_SubAccount) && setValue)
				vc.setC_SubAcct_ID(defaultValue);
			else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_BPartner) && setValue)
				vc.setC_BPartner_ID(defaultValue);
			else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_Product) && setValue)
				vc.setM_Product_ID(defaultValue);
			else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_Activity) && setValue)
				vc.setC_Activity_ID(defaultValue);
			else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_LocationFrom) && setValue)
				vc.setC_LocFrom_ID(defaultValue);
			else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_LocationTo) && setValue)
				vc.setC_LocTo_ID(defaultValue);
			else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_Campaign) && setValue)
				vc.setC_Campaign_ID(defaultValue);
			else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_OrgTrx) && setValue)
				vc.setAD_OrgTrx_ID(defaultValue);
			else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_Project) && setValue)
				vc.setC_Project_ID(defaultValue);
			else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_SalesRegion) && setValue)
				vc.setC_SalesRegion_ID(defaultValue);
			else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_UserList1) && setValue)
				vc.setUser1_ID(defaultValue);
			else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_UserList2) && setValue)
				vc.setUser2_ID(defaultValue);
			else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_UserElement1) && setValue)
				vc.setUserElement1_ID(defaultValue);
			else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_UserElement2) && setValue)
				vc.setUserElement2_ID(defaultValue);
		}
		s_log.fine("Client_ID="
				+ vc.getAD_Client_ID() + ", Org_ID=" + vc.getAD_Org_ID()
				+ " - AcctSchema_ID=" + vc.getC_AcctSchema_ID() + ", Account_ID=" + vc.getAccount_ID());
		return vc;
	}   // getDefault

	/**
	 * Get Account
	 * 
	 * @param ctx context
	 * @param C_ValidCombination_ID combination
	 * @return Account
	 */
	public static MAccount get(Properties ctx, int C_ValidCombination_ID)
	{
		// Maybe later cache
		return new MAccount(ctx, C_ValidCombination_ID, ITrx.TRXNAME_None);
	}   // getAccount

	/**
	 * Update Value/Description after change of account element value/description.
	 *
	 * @param ctx context
	 * @param whereClause where clause
	 * @param trxName transaction
	 */
	public static void updateValueDescription(Properties ctx, String whereClause, String trxName)
	{
		final List<I_C_ValidCombination> accounts = new Query(ctx, I_C_ValidCombination.Table_Name, whereClause, trxName)
				.setOrderBy(MAccount.COLUMNNAME_C_ValidCombination_ID)
				.list(I_C_ValidCombination.class);

		final IAccountBL accountBL = Services.get(IAccountBL.class);

		for (final I_C_ValidCombination account : accounts)
		{
			accountBL.setValueDescription(account);
			InterfaceWrapperHelper.save(account);
		}
	}	// updateValueDescription

	/** Logger */
	private static CLogger s_log = CLogger.getCLogger(MAccount.class);

	/**************************************************************************
	 * Default constructor
	 * 
	 * @param ctx context
	 * @param C_ValidCombination_ID combination
	 * @param trxName transaction
	 */
	public MAccount(Properties ctx, int C_ValidCombination_ID, String trxName)
	{
		super(ctx, C_ValidCombination_ID, trxName);
		if (C_ValidCombination_ID == 0)
		{
			// setAccount_ID (0);
			// setC_AcctSchema_ID (0);
			setIsFullyQualified(false);
		}
	}   // MAccount

	/**
	 * Load constructor
	 * 
	 * @param ctx context
	 * @param rs result set
	 * @param trxName transaction
	 */
	public MAccount(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}   // MAccount

	/**
	 * Parent Constructor
	 *
	 * @param as account schema
	 */
	private MAccount(MAcctSchema as)
	{
		this(as.getCtx(), 0, as.get_TrxName());
		setClientOrg(as);
		setC_AcctSchema_ID(as.getC_AcctSchema_ID());
	}	// Account

	/** Account Segment */
	private MElementValue m_accountEV = null;

	/**************************************************************************
	 * Return String representation
	 * 
	 * @return String
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("MAccount=[");
		sb.append(getC_ValidCombination_ID());
		if (getCombination() != null)
			sb.append(",")
					.append(getCombination());
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
		}
		sb.append("]");
		return sb.toString();
	}	// toString

	/**
	 * Set Account_ID
	 * 
	 * @param Account_ID id
	 */
	@Override
	public void setAccount_ID(int Account_ID)
	{
		m_accountEV = null;	// reset
		super.setAccount_ID(Account_ID);
	}	// setAccount

	/**
	 * Set Account_ID
	 * 
	 * @return element value
	 */
	@Override
	public MElementValue getAccount()
	{
		if (m_accountEV == null)
		{
			if (getAccount_ID() > 0)
			{
				m_accountEV = new MElementValue(getCtx(), getAccount_ID(), get_TrxName());
			}
		}
		return m_accountEV;
	}	// setAccount

	/**
	 * Get Account Type
	 *
	 * @return Account Type of Account Element
	 */
	public String getAccountType()
	{
		final I_C_ElementValue elementValue = getAccount();
		if (elementValue == null)
		{
			log.log(Level.SEVERE, "No ElementValue for Account_ID=" + getAccount_ID());
			return "";
		}
		return elementValue.getAccountType();
	}	// getAccountType

	/**
	 * Is this a Balance Sheet Account
	 * 
	 * @return boolean
	 */
	public boolean isBalanceSheet()
	{
		String accountType = getAccountType();
		return (X_C_ElementValue.ACCOUNTTYPE_Asset.equals(accountType)
				|| X_C_ElementValue.ACCOUNTTYPE_Liability.equals(accountType)
				|| X_C_ElementValue.ACCOUNTTYPE_OwnerSEquity.equals(accountType));
	}	// isBalanceSheet

	/**
	 * Is this an Activa Account
	 * 
	 * @return boolean
	 */
	public boolean isActiva()
	{
		return X_C_ElementValue.ACCOUNTTYPE_Asset.equals(getAccountType());
	}	// isActive

	/**
	 * Is this a Passiva Account
	 * 
	 * @return boolean
	 */
	public boolean isPassiva()
	{
		String accountType = getAccountType();
		return (X_C_ElementValue.ACCOUNTTYPE_Liability.equals(accountType)
		|| X_C_ElementValue.ACCOUNTTYPE_OwnerSEquity.equals(accountType));
	}	// isPassiva
}	// Account

