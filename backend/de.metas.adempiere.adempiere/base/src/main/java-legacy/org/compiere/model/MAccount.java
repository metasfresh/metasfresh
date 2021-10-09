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

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.acct.api.AccountDimension;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaElement;
import de.metas.acct.api.AcctSchemaElementType;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAccountBL;
import de.metas.acct.api.IAccountDAO;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Account Object Entity to maintain all segment values. C_ValidCombination
 *
 * @author Jorg Janke
 * @author victor.perez@e-evolution.com, www.e-evolution.com
 *         <li>RF [ 2214883 ] Remove SQL code and Replace for Query
 *         http://sourceforge.net/tracker/index.php?func=detail&aid=2214883&group_id=176962&atid=879335
 * @author Teo Sarca, www.arhipac.ro
 *         <li>FR [ 2694043 ] Query. first/firstOnly usage best practice
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
	 * @return account or null
	 * @deprecated Use {@link #get(Properties,AccountDimension)} instead
	 */
	@Deprecated
	public static MAccount get(Properties ctx,
			int AD_Client_ID,
			int AD_Org_ID,
			AcctSchemaId acctSchemaId,
			int Account_ID,
			int C_SubAcct_ID,
			int M_Product_ID,
			int C_BPartner_ID,
			int AD_OrgTrx_ID,
			int C_LocFrom_ID,
			int C_LocTo_ID,
			int C_SalesRegion_ID,
			int C_Project_ID,
			int C_Campaign_ID,
			int C_Activity_ID,
			int User1_ID,
			int User2_ID,
			int UserElement1_ID,
			int UserElement2_ID)
	{
		final AccountDimension dim = AccountDimension.builder()
				.setAcctSchemaId(acctSchemaId)
				.setAD_Client_ID(AD_Client_ID)
				.setAD_Org_ID(AD_Org_ID)
				.setC_ElementValue_ID(Account_ID)
				.setC_SubAcct_ID(C_SubAcct_ID)
				.setM_Product_ID(M_Product_ID)
				.setC_BPartner_ID(C_BPartner_ID)
				.setAD_OrgTrx_ID(AD_OrgTrx_ID)
				.setC_LocFrom_ID(C_LocFrom_ID)
				.setC_LocTo_ID(C_LocTo_ID)
				.setC_SalesRegion_ID(C_SalesRegion_ID)
				.setC_Project_ID(C_Project_ID)
				.setC_Campaign_ID(C_Campaign_ID)
				.setC_Activity_ID(C_Activity_ID)
				.setUser1_ID(User1_ID)
				.setUser2_ID(User2_ID)
				.setUserElement1_ID(UserElement1_ID)
				.setUserElement2_ID(UserElement2_ID)

				.build();
		return get(ctx, dim);
	}	// get

	/**
	 * Get existing Account or create it.
	 *
	 * @param ctx context
	 * @param dimension accounting dimension
	 * @return existing account or a newly created one; never returns null
	 */
	public static MAccount get(final Properties ctx, final AccountDimension dimension)
	{
		// services
		final IAccountDAO accountDAO = Services.get(IAccountDAO.class);

		// Existing
		final MAccount existingAccount = accountDAO.retrieveAccount(ctx, dimension);
		if (existingAccount != null)
		{
			return existingAccount;
		}

		// New
		final MAccount newAccount = new MAccount(ctx, 0, ITrx.TRXNAME_None);
		newAccount.setClientOrg(dimension.getAD_Client_ID(), dimension.getAD_Org_ID());
		newAccount.setC_AcctSchema_ID(AcctSchemaId.toRepoId(dimension.getAcctSchemaId()));
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
		newAccount.setUserElementString1(dimension.getUserElementString1());
		newAccount.setUserElementString2(dimension.getUserElementString2());
		newAccount.setUserElementString3(dimension.getUserElementString3());
		newAccount.setUserElementString4(dimension.getUserElementString4());
		newAccount.setUserElementString5(dimension.getUserElementString5());
		newAccount.setUserElementString6(dimension.getUserElementString6());
		newAccount.setUserElementString7(dimension.getUserElementString7());
		InterfaceWrapperHelper.save(newAccount);
		logger.debug("New: {}", newAccount);
		return newAccount;
	}	// get
	
	public static I_C_ValidCombination getCreate(final AccountDimension dimension)
	{
		// services
		final IAccountDAO accountDAO = Services.get(IAccountDAO.class);

		// Existing
		final MAccount existingAccount = accountDAO.retrieveAccount(Env.getCtx(), dimension);
		if (existingAccount != null)
		{
			return existingAccount;
		}

		final I_C_ValidCombination vc = InterfaceWrapperHelper.newInstance(I_C_ValidCombination.class);
		vc.setAD_Org_ID(dimension.getAD_Org_ID());
		vc.setC_AcctSchema_ID(AcctSchemaId.toRepoId(dimension.getAcctSchemaId()));
		vc.setAccount_ID(dimension.getC_ElementValue_ID());
		vc.setC_SubAcct_ID(dimension.getC_SubAcct_ID());
		vc.setM_Product_ID(dimension.getM_Product_ID());
		vc.setC_BPartner_ID(dimension.getC_BPartner_ID());
		vc.setAD_OrgTrx_ID(dimension.getAD_OrgTrx_ID());
		vc.setC_LocFrom_ID(dimension.getC_LocFrom_ID());
		vc.setC_LocTo_ID(dimension.getC_LocTo_ID());
		vc.setC_SalesRegion_ID(dimension.getC_SalesRegion_ID());
		vc.setC_Project_ID(dimension.getC_Project_ID());
		vc.setC_Campaign_ID(dimension.getC_Campaign_ID());
		vc.setC_Activity_ID(dimension.getC_Activity_ID());
		vc.setUser1_ID(dimension.getUser1_ID());
		vc.setUser2_ID(dimension.getUser2_ID());
		vc.setUserElement1_ID(dimension.getUserElement1_ID());
		vc.setUserElement2_ID(dimension.getUserElement2_ID());
		vc.setUserElementString1(dimension.getUserElementString1());
		vc.setUserElementString2(dimension.getUserElementString2());
		vc.setUserElementString3(dimension.getUserElementString3());
		vc.setUserElementString4(dimension.getUserElementString4());
		vc.setUserElementString5(dimension.getUserElementString5());
		vc.setUserElementString6(dimension.getUserElementString6());
		vc.setUserElementString7(dimension.getUserElementString7());
		InterfaceWrapperHelper.save(vc);
		
		return vc;
	}	// get


	/**
	 * Factory: default combination
	 * 
	 * @param acctSchema accounting schema
	 * @param optionalNull if true, the optional values are null
	 * @return Account
	 */
	public static MAccount getDefault(final AcctSchema acctSchema, final boolean optionalNull)
	{
		final MAccount vc = new MAccount(acctSchema);
		// Active Elements
		for (final AcctSchemaElement ase : acctSchema.getSchemaElements())
		{
			final AcctSchemaElementType elementType = ase.getElementType();
			final int defaultValue = ase.getDefaultValue();
			final boolean setValue = ase.isMandatory() || (!ase.isMandatory() && !optionalNull);
			//
			if (elementType.equals(AcctSchemaElementType.Organization))
				vc.setAD_Org_ID(defaultValue);
			else if (elementType.equals(AcctSchemaElementType.Account))
				vc.setAccount_ID(defaultValue);
			else if (elementType.equals(AcctSchemaElementType.SubAccount) && setValue)
				vc.setC_SubAcct_ID(defaultValue);
			else if (elementType.equals(AcctSchemaElementType.BPartner) && setValue)
				vc.setC_BPartner_ID(defaultValue);
			else if (elementType.equals(AcctSchemaElementType.Product) && setValue)
				vc.setM_Product_ID(defaultValue);
			else if (elementType.equals(AcctSchemaElementType.Activity) && setValue)
				vc.setC_Activity_ID(defaultValue);
			else if (elementType.equals(AcctSchemaElementType.LocationFrom) && setValue)
				vc.setC_LocFrom_ID(defaultValue);
			else if (elementType.equals(AcctSchemaElementType.LocationTo) && setValue)
				vc.setC_LocTo_ID(defaultValue);
			else if (elementType.equals(AcctSchemaElementType.Campaign) && setValue)
				vc.setC_Campaign_ID(defaultValue);
			else if (elementType.equals(AcctSchemaElementType.OrgTrx) && setValue)
				vc.setAD_OrgTrx_ID(defaultValue);
			else if (elementType.equals(AcctSchemaElementType.Project) && setValue)
				vc.setC_Project_ID(defaultValue);
			else if (elementType.equals(AcctSchemaElementType.SalesRegion) && setValue)
				vc.setC_SalesRegion_ID(defaultValue);
			else if (elementType.equals(AcctSchemaElementType.UserList1) && setValue)
				vc.setUser1_ID(defaultValue);
			else if (elementType.equals(AcctSchemaElementType.UserList2) && setValue)
				vc.setUser2_ID(defaultValue);
			else if (elementType.equals(AcctSchemaElementType.UserElement1) && setValue)
				vc.setUserElement1_ID(defaultValue);
			else if (elementType.equals(AcctSchemaElementType.UserElement2) && setValue)
				vc.setUserElement2_ID(defaultValue);
		}

		return vc;
	}   // getDefault

	/**
	 * Update Value/Description after change of account element value/description.
	 *
	 * @param ctx context
	 * @param whereClause where clause
	 * @param trxName transaction
	 */
	public static void updateValueDescription(final Properties ctx, final String whereClause, final String trxName)
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

	private static final transient Logger logger = LogManager.getLogger(MAccount.class);

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

	private MAccount(@NonNull final AcctSchema acctSchema)
	{
		this(Env.getCtx(), 0, ITrx.TRXNAME_None);
		setClientOrg(acctSchema.getClientId().getRepoId(), acctSchema.getOrgId().getRepoId());
		setC_AcctSchema_ID(acctSchema.getId().getRepoId());
	}	// Account

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
		}
		sb.append("]");
		return sb.toString();
	}

	private final String getAccountType()
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
