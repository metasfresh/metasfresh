package de.metas.acct.accounts;

import com.google.common.collect.ImmutableMap;
import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchemaId;
import de.metas.cache.CCache;
import de.metas.tax.api.TaxId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import de.metas.acct.Account;
import org.compiere.model.I_C_Tax_Acct;
import org.springframework.stereotype.Repository;

@Repository
public class TaxAccountsRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<TaxId, ImmutableMap<AcctSchemaId, TaxAccounts>> cache = CCache.<TaxId, ImmutableMap<AcctSchemaId, TaxAccounts>>builder()
			.tableName(I_C_Tax_Acct.Table_Name)
			.cacheMapType(CCache.CacheMapType.LRU)
			.initialCapacity(100)
			.build();

	public TaxAccounts getAccounts(@NonNull final TaxId taxId, @NonNull final AcctSchemaId acctSchemaId)
	{
		final ImmutableMap<AcctSchemaId, TaxAccounts> map = cache.getOrLoad(taxId, this::retrieveAccounts);
		final TaxAccounts accounts = map.get(acctSchemaId);
		if (accounts == null)
		{
			throw new AdempiereException("No Tax accounts defined for " + taxId + " and " + acctSchemaId);
		}
		return accounts;
	}

	private ImmutableMap<AcctSchemaId, TaxAccounts> retrieveAccounts(@NonNull final TaxId taxId)
	{
		return queryBL.createQueryBuilder(I_C_Tax_Acct.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Tax_Acct.COLUMNNAME_C_Tax_ID, taxId)
				.create()
				.stream()
				.map(TaxAccountsRepository::fromRecord)
				.collect(ImmutableMap.toImmutableMap(TaxAccounts::getAcctSchemaId, accounts -> accounts));
	}

	@NonNull
	private static TaxAccounts fromRecord(@NonNull final I_C_Tax_Acct record)
	{
		return TaxAccounts.builder()
				.acctSchemaId(AcctSchemaId.ofRepoId(record.getC_AcctSchema_ID()))
				.T_Due_Acct(Account.of(AccountId.ofRepoId(record.getT_Due_Acct()), I_C_Tax_Acct.COLUMNNAME_T_Due_Acct))
				.T_Liability_Acct(Account.of(AccountId.ofRepoId(record.getT_Liability_Acct()), I_C_Tax_Acct.COLUMNNAME_T_Liability_Acct))
				.T_Credit_Acct(Account.of(AccountId.ofRepoId(record.getT_Credit_Acct()), I_C_Tax_Acct.COLUMNNAME_T_Credit_Acct))
				.T_Receivables_Acct(Account.of(AccountId.ofRepoId(record.getT_Receivables_Acct()), I_C_Tax_Acct.COLUMNNAME_T_Receivables_Acct))
				.T_Expense_Acct(Account.of(AccountId.ofRepoId(record.getT_Expense_Acct()), I_C_Tax_Acct.COLUMNNAME_T_Expense_Acct))
				.T_Revenue_Acct(AccountId.optionalOfRepoId(record.getT_Revenue_Acct())
										.map(acctId -> Account.of(acctId, I_C_Tax_Acct.COLUMNNAME_T_Revenue_Acct)))
				.build();
	}

}
