package de.metas.acct.tax.impl;

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


import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_C_Tax_Acct;
import org.compiere.model.I_C_ValidCombination;
import org.compiere.model.MAccount;

import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.tax.ITaxAcctBL;
import de.metas.cache.annotation.CacheCtx;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

public class TaxAcctBL implements ITaxAcctBL
{
	@Override
	public I_C_ValidCombination getC_ValidCombination(final Properties ctx, final int taxId, @NonNull final AcctSchemaId acctSchemaId, final int acctType)
	{
		final I_C_Tax_Acct taxAcct = retrieveTaxAcct(ctx, taxId, acctSchemaId);

		final I_C_ValidCombination validCombination;
		if (ACCTTYPE_TaxDue == acctType) // 0
		{
			validCombination = taxAcct.getT_Due_A();
		}
		else if (ACCTTYPE_TaxLiability == acctType) // 1
		{
			validCombination = taxAcct.getT_Liability_A();
		}
		else if (ACCTTYPE_TaxCredit == acctType) // 2
		{
			validCombination = taxAcct.getT_Credit_A();
		}
		else if (ACCTTYPE_TaxReceivables == acctType) // 3
		{
			validCombination = taxAcct.getT_Receivables_A();
		}
		else if (ACCTTYPE_TaxExpense == acctType) // 4
		{
			validCombination = taxAcct.getT_Expense_A();
		}
		else
		{
			throw new IllegalArgumentException("Unknown tax account type: " + acctType);
		}

		Check.assumeNotNull(validCombination, "validCombination not null");
		return validCombination;
	}

	@Override
	public MAccount getAccount(final Properties ctx, final int taxId, final AcctSchemaId acctSchemaId, final int acctType)
	{
		final I_C_ValidCombination validCombination = getC_ValidCombination(ctx, taxId, acctSchemaId, acctType);
		return LegacyAdapters.convertToPO(validCombination);
	}

	/**
	 * DAO method to retrieve {@link I_C_Tax_Acct}.
	 *
	 * @param ctx
	 * @param taxId
	 * @param acctSchemaId
	 * @return
	 */
	@Cached(cacheName = I_C_Tax_Acct.Table_Name
			+ "#by#" + I_C_Tax_Acct.COLUMNNAME_C_Tax_ID
			+ "#" + I_C_Tax_Acct.COLUMNNAME_C_AcctSchema_ID)
	public I_C_Tax_Acct retrieveTaxAcct(@CacheCtx final Properties ctx, final int taxId, final AcctSchemaId acctSchemaId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Tax_Acct.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_C_Tax_Acct.COLUMNNAME_C_Tax_ID, taxId)
				.addEqualsFilter(I_C_Tax_Acct.COLUMNNAME_C_AcctSchema_ID, acctSchemaId)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.create()
				.firstOnlyNotNull(I_C_Tax_Acct.class);
	}
}
