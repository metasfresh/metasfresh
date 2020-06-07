package de.metas.banking.service.impl;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;

import de.metas.banking.service.IBankingBPBankAccountDAO;
import de.metas.util.Services;

/**
 * @author al
 */
public class BankingBPBankAccountDAO implements IBankingBPBankAccountDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public I_C_BP_BankAccount retrieveDefaultBankAccount(final I_C_BPartner partner)
	{
		final IQueryBuilder<I_C_BP_BankAccount> queryBuilder = queryBL.createQueryBuilder(I_C_BP_BankAccount.class, partner);
		queryBuilder.addEqualsFilter(org.compiere.model.I_C_BP_BankAccount.COLUMNNAME_C_BPartner_ID, partner.getC_BPartner_ID())
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient(InterfaceWrapperHelper.getCtx(partner));
		queryBuilder.orderBy()
				.addColumn(I_C_BP_BankAccount.COLUMNNAME_IsDefault, false); // DESC (Y, then N)
		return queryBuilder.create()
				.first();
	}
}
