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
package org.compiere.process;

import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaElement;
import de.metas.acct.api.AcctSchemaElementType;
import de.metas.acct.api.AcctSchemaElementsMap;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.ChartOfAccountsId;
import de.metas.acct.api.IAccountDAO;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.With;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_AcctSchema_Default;
import org.compiere.model.I_C_AcctSchema_GL;
import org.compiere.model.MAccount;
import org.compiere.model.POInfo;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Copy Accounts from one Acct Schema to another
 *
 * @author Jorg Janke
 * @version $Id: AcctSchemaCopyAcct.java,v 1.3 2006/07/30 00:51:01 jjanke Exp $
 */
public class AcctSchemaCopyAcct extends JavaProcess
{
	private final IAcctSchemaDAO acctSchemasRepo = Services.get(IAcctSchemaDAO.class);
	private static final IAccountDAO accountsRepo = Services.get(IAccountDAO.class);

	private AcctSchemaId p_SourceAcctSchema_ID;
	private AcctSchemaId p_TargetAcctSchema_ID;

	@Override
	protected void prepare()
	{
		final ProcessInfoParameter[] para = getParametersAsArray();
		for (ProcessInfoParameter element : para)
		{
			final String name = element.getParameterName();
			if (element.getParameter() == null)
			{

			}
			else if (name.equals("C_AcctSchema_ID"))
			{
				p_SourceAcctSchema_ID = AcctSchemaId.ofRepoId(element.getParameterAsInt());
			}
			else
			{
				log.error("Unknown Parameter: " + name);
			}
		}

		p_TargetAcctSchema_ID = AcctSchemaId.ofRepoId(getRecord_ID());
	}

	@Override
	protected String doIt()
	{
		if (AcctSchemaId.equals(p_SourceAcctSchema_ID, p_TargetAcctSchema_ID))
		{
			throw new AdempiereException("Must be different");
		}

		final AcctSchema source = acctSchemasRepo.getById(p_SourceAcctSchema_ID);
		final AcctSchemaElementsMap sourceElements = source.getSchemaElements();

		final I_C_AcctSchema targetRecord = InterfaceWrapperHelper.create(getCtx(), p_TargetAcctSchema_ID.getRepoId(), I_C_AcctSchema.class, get_TrxName());
		if (targetRecord.getC_AcctSchema_ID() <= 0)
		{
			throw new AdempiereException("NotFound Target C_AcctSchema_ID=" + p_TargetAcctSchema_ID);
		}
		final AcctSchema target = acctSchemasRepo.getById(p_TargetAcctSchema_ID);
		final AcctSchemaElementsMap targetElements = target.getSchemaElements();

		//
		if (targetElements.isEmpty())
		{
			throw new AdempiereException("NotFound Target C_AcctSchema_Element");
		}

		// Chart of Accounts shall be the same
		if (!ChartOfAccountsId.equals(sourceElements.getChartOfAccountsId(), targetElements.getChartOfAccountsId()))
		{
			throw new AdempiereException("Chart of Accounts is different");
		}

		if (retrieveAcctSchemaGLOrNull(p_TargetAcctSchema_ID) == null)
		{
			copyGL(targetRecord, targetElements);
		}
		if (retrieveAcctSchemaDefaultOrNull(p_TargetAcctSchema_ID) == null)
		{
			copyDefault(targetRecord, targetElements);
		}

		return "@OK@";
	}	// doIt

	private I_C_AcctSchema_GL retrieveAcctSchemaGLOrNull(final AcctSchemaId acctSchemaId)
	{
		return acctSchemasRepo.retrieveAcctSchemaGLRecordOrNull(acctSchemaId);
	}	// get

	private I_C_AcctSchema_Default retrieveAcctSchemaDefaultOrNull(final AcctSchemaId acctSchemaId)
	{
		return acctSchemasRepo.retrieveAcctSchemaDefaultsRecordOrNull(acctSchemaId);
	}	// get

	private void copyGL(final I_C_AcctSchema targetAS, final AcctSchemaElementsMap targetElements)
	{
		final I_C_AcctSchema_GL source = retrieveAcctSchemaGLOrNull(p_SourceAcctSchema_ID);

		final I_C_AcctSchema_GL target = InterfaceWrapperHelper.newInstance(I_C_AcctSchema_GL.class);
		target.setC_AcctSchema_ID(p_TargetAcctSchema_ID.getRepoId());

		for (final AccountInfo sourceAccountInfo : getAccountInfos(source))
		{
			final AccountId targetAccountId = createAccount(targetAS, targetElements, sourceAccountInfo.getAccountId());
			setAccount(target, sourceAccountInfo.withAccountId(targetAccountId));
		}

		InterfaceWrapperHelper.save(target);
	}	// copyGL

	private void copyDefault(final I_C_AcctSchema targetAS, final AcctSchemaElementsMap targetElements)
	{
		final I_C_AcctSchema_Default source = retrieveAcctSchemaDefaultOrNull(p_SourceAcctSchema_ID);

		final I_C_AcctSchema_Default target = InterfaceWrapperHelper.newInstance(I_C_AcctSchema_Default.class);
		target.setC_AcctSchema_ID(p_TargetAcctSchema_ID.getRepoId());

		for (final AccountInfo sourceAccountInfo : getAccountInfos(source))
		{
			final AccountId targetAccountId = createAccount(targetAS, targetElements, sourceAccountInfo.getAccountId());
			setAccount(target, sourceAccountInfo.withAccountId(targetAccountId));
		}

		InterfaceWrapperHelper.save(target);
	}	// copyDefault

	/**
	 * @return target account
	 */
	private AccountId createAccount(final I_C_AcctSchema targetAS, final AcctSchemaElementsMap targetElements, final AccountId sourceAccountId)
	{
		final int AD_Client_ID = targetAS.getAD_Client_ID();
		final AcctSchemaId acctSchemaId = AcctSchemaId.ofRepoId(targetAS.getC_AcctSchema_ID());

		final MAccount sourceAccount = accountsRepo.getById(getCtx(), sourceAccountId);

		//
		int AD_Org_ID = 0;
		int Account_ID = 0;
		int C_SubAcct_ID = 0;
		int M_Product_ID = 0;
		int C_BPartner_ID = 0;
		int AD_OrgTrx_ID = 0;
		int C_LocFrom_ID = 0;
		int C_LocTo_ID = 0;
		int C_SalesRegion_ID = 0;
		int C_Project_ID = 0;
		int C_Campaign_ID = 0;
		int C_Activity_ID = 0;
		int User1_ID = 0;
		int User2_ID = 0;
		int UserElement1_ID = 0;
		int UserElement2_ID = 0;
		//
		// Active Elements
		for (final AcctSchemaElement ase : targetElements)
		{
			final AcctSchemaElementType elementType = ase.getElementType();
			//
			if (elementType.equals(AcctSchemaElementType.Organization))
			{
				AD_Org_ID = sourceAccount.getAD_Org_ID();
			}
			else if (elementType.equals(AcctSchemaElementType.Account))
			{
				Account_ID = sourceAccount.getAccount_ID();
			}
			else if (elementType.equals(AcctSchemaElementType.SubAccount))
			{
				C_SubAcct_ID = sourceAccount.getC_SubAcct_ID();
			}
			else if (elementType.equals(AcctSchemaElementType.BPartner))
			{
				C_BPartner_ID = sourceAccount.getC_BPartner_ID();
			}
			else if (elementType.equals(AcctSchemaElementType.Product))
			{
				M_Product_ID = sourceAccount.getM_Product_ID();
			}
			else if (elementType.equals(AcctSchemaElementType.Activity))
			{
				C_Activity_ID = sourceAccount.getC_Activity_ID();
			}
			else if (elementType.equals(AcctSchemaElementType.LocationFrom))
			{
				C_LocFrom_ID = sourceAccount.getC_LocFrom_ID();
			}
			else if (elementType.equals(AcctSchemaElementType.LocationTo))
			{
				C_LocTo_ID = sourceAccount.getC_LocTo_ID();
			}
			else if (elementType.equals(AcctSchemaElementType.Campaign))
			{
				C_Campaign_ID = sourceAccount.getC_Campaign_ID();
			}
			else if (elementType.equals(AcctSchemaElementType.OrgTrx))
			{
				AD_OrgTrx_ID = sourceAccount.getAD_OrgTrx_ID();
			}
			else if (elementType.equals(AcctSchemaElementType.Project))
			{
				C_Project_ID = sourceAccount.getC_Project_ID();
			}
			else if (elementType.equals(AcctSchemaElementType.SalesRegion))
			{
				C_SalesRegion_ID = sourceAccount.getC_SalesRegion_ID();
			}
			else if (elementType.equals(AcctSchemaElementType.UserList1))
			{
				User1_ID = sourceAccount.getUser1_ID();
			}
			else if (elementType.equals(AcctSchemaElementType.UserList2))
			{
				User2_ID = sourceAccount.getUser2_ID();
			}
			else if (elementType.equals(AcctSchemaElementType.UserElement1))
			{
				UserElement1_ID = sourceAccount.getUserElement1_ID();
			}
			else if (elementType.equals(AcctSchemaElementType.UserElement2))
			{
				UserElement2_ID = sourceAccount.getUserElement2_ID();
				// No UserElement
			}
		}

		final MAccount account = MAccount.get(getCtx(), AD_Client_ID, AD_Org_ID,
				acctSchemaId, Account_ID, C_SubAcct_ID,
				M_Product_ID, C_BPartner_ID, AD_OrgTrx_ID,
				C_LocFrom_ID, C_LocTo_ID, C_SalesRegion_ID,
				C_Project_ID, C_Campaign_ID, C_Activity_ID,
				User1_ID, User2_ID, UserElement1_ID, UserElement2_ID);

		return AccountId.ofRepoId(account.getC_ValidCombination_ID());
	}	// createAccount

	public List<AccountInfo> getAccountInfos(final Object acctAwareModel)
	{
		final String tableName = InterfaceWrapperHelper.getModelTableName(acctAwareModel);
		final POInfo poInfo = POInfo.getPOInfo(tableName);

		final List<AccountInfo> list = new ArrayList<>();
		for (int columnIndex = 0; columnIndex < poInfo.getColumnCount(); columnIndex++)
		{
			final String columnName = poInfo.getColumnName(columnIndex);
			if (columnName.endsWith("Acct"))
			{
				final Object accountIdObj = InterfaceWrapperHelper.getValueOrNull(acctAwareModel, columnName);
				final AccountId accountId = AccountId.ofRepoIdOrNull(NumberUtils.asInt(accountIdObj, -1));

				list.add(AccountInfo.builder()
						.columnName(columnName)
						.accountId(accountId)
						.build());
			}
		}
		return list;
	}

	public void setAccount(final Object acctAwareModel, final AccountInfo accountInfo)
	{
		final AccountId accountId = accountInfo.getAccountId();
		InterfaceWrapperHelper.setValue(acctAwareModel, accountInfo.getColumnName(), AccountId.toRepoId(accountId));
	}

	@lombok.Value
	@lombok.Builder
	private static class AccountInfo
	{
		@NonNull
		String columnName;

		@Nullable
		@With
		AccountId accountId;
	}
}	// AcctSchemaCopyAcct
