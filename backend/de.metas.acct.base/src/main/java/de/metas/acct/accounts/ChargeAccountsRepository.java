package de.metas.acct.accounts;

import com.google.common.collect.ImmutableMap;
import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchemaId;
import de.metas.cache.CCache;
import de.metas.costing.ChargeId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import de.metas.acct.Account;
import org.compiere.model.I_C_Charge_Acct;
import org.springframework.stereotype.Repository;

@Repository
public class ChargeAccountsRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<ChargeId, ImmutableMap<AcctSchemaId, ChargeAccounts>> cache = CCache.<ChargeId, ImmutableMap<AcctSchemaId, ChargeAccounts>>builder()
			.tableName(I_C_Charge_Acct.Table_Name)
			.cacheMapType(CCache.CacheMapType.LRU)
			.initialCapacity(50)
			.build();

	public ChargeAccounts getAccounts(@NonNull final ChargeId chargeId, @NonNull final AcctSchemaId acctSchemaId)
	{
		final ImmutableMap<AcctSchemaId, ChargeAccounts> map = cache.getOrLoad(chargeId, this::retrieveAccounts);
		final ChargeAccounts accounts = map.get(acctSchemaId);
		if (accounts == null)
		{
			throw new AdempiereException("No Charge accounts defined for " + chargeId + " and " + acctSchemaId);
		}
		return accounts;
	}

	private ImmutableMap<AcctSchemaId, ChargeAccounts> retrieveAccounts(@NonNull final ChargeId chargeId)
	{
		return queryBL.createQueryBuilder(I_C_Charge_Acct.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Charge_Acct.COLUMNNAME_C_Charge_ID, chargeId)
				.create()
				.stream()
				.map(ChargeAccountsRepository::fromRecord)
				.collect(ImmutableMap.toImmutableMap(ChargeAccounts::getAcctSchemaId, accounts -> accounts));
	}

	@NonNull
	private static ChargeAccounts fromRecord(@NonNull final I_C_Charge_Acct record)
	{
		return ChargeAccounts.builder()
				.acctSchemaId(AcctSchemaId.ofRepoId(record.getC_AcctSchema_ID()))
				.Ch_Expense_Acct(Account.of(AccountId.ofRepoId(record.getCh_Expense_Acct()), I_C_Charge_Acct.COLUMNNAME_Ch_Expense_Acct))
				.Ch_Revenue_Acct(Account.of(AccountId.ofRepoId(record.getCh_Revenue_Acct()), I_C_Charge_Acct.COLUMNNAME_Ch_Revenue_Acct))
				.build();
	}

}
