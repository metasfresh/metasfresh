package de.metas.payment.esr.api.impl;

/*
 * #%L
 * de.metas.payment.esr
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;

import de.metas.adempiere.service.IBPartnerOrgBL;
import de.metas.payment.esr.model.I_C_BP_BankAccount;

public class ESRBPBankAccountDAO extends AbstractBPBankAccountDAO
{
	private static String MSG_NOT_ESR_ACCOUNT_FOR_ORG = "NoESRAccountForOrganiazation";

	@Override
	public List<I_C_BP_BankAccount> fetchOrgEsrAccounts(final I_AD_Org org)
	{
		Check.assume(org != null, " Param 'org' is not null");

		// task 07647: we need to get the Org's BPArtner!
		final I_C_BPartner linkedBPartner = Services.get(IBPartnerOrgBL.class).retrieveLinkedBPartner(org);

		final IQueryBuilder<I_C_BP_BankAccount> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_C_BP_BankAccount.class, org);

		queryBuilder.addOnlyActiveRecordsFilter();

		queryBuilder.addEqualsFilter(I_C_BP_BankAccount.COLUMNNAME_IsEsrAccount, true)
				.addEqualsFilter(I_C_BP_BankAccount.COLUMNNAME_C_BPartner_ID, linkedBPartner.getC_BPartner_ID());

		queryBuilder.orderBy()
				.addColumn(I_C_BP_BankAccount.COLUMNNAME_IsDefaultESR, Direction.Descending, Nulls.Last)
				.addColumn(I_C_BP_BankAccount.COLUMNNAME_C_BP_BankAccount_ID)
				.endOrderBy();

		final List<I_C_BP_BankAccount> esrAccounts = queryBuilder
				.create()
				.list();

		if (esrAccounts.isEmpty())
		{
			final IMsgBL msgBL = Services.get(IMsgBL.class);
			final Properties ctx = InterfaceWrapperHelper.getCtx(org);

			final String msg = msgBL.getMsg(ctx, MSG_NOT_ESR_ACCOUNT_FOR_ORG,
					new Object[] { msgBL.translate(ctx, org.getValue())
					});
			throw new AdempiereException(msg);
		}

		return esrAccounts;
	}
}
