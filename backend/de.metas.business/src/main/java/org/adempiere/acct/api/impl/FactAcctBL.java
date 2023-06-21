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

import de.metas.acct.Account;
import de.metas.acct.api.AccountDimension;
import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchemaId;
import de.metas.util.Check;
import org.adempiere.acct.api.IFactAcctBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_Fact_Acct;
import org.compiere.model.MAccount;
import org.compiere.util.TimeUtil;

import java.util.Properties;

public class FactAcctBL implements IFactAcctBL
{

	@Override
	public Account getAccount(final I_Fact_Acct factAcct)
	{
		Check.assumeNotNull(factAcct, "factAcct not null");

		final Properties ctx = InterfaceWrapperHelper.getCtx(factAcct);
		final AccountDimension accountDimension = createAccountDimension(factAcct);
		return Account.of(AccountId.ofRepoId(MAccount.get(ctx, accountDimension).getC_ValidCombination_ID()), factAcct.getAccountConceptualName());
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
				.setSalesOrderId(fa.getC_OrderSO_ID())
				.setM_SectionCode_ID(fa.getM_SectionCode_ID())
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
				.setUserElementDate1(TimeUtil.asInstant(fa.getUserElementDate1()))
				.setUserElementDate2(TimeUtil.asInstant(fa.getUserElementDate2()))

				.build();
	}
}
