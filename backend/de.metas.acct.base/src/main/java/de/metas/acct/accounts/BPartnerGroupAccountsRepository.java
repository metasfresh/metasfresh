package de.metas.acct.accounts;

import com.google.common.collect.ImmutableMap;
import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchemaId;
import de.metas.bpartner.BPGroupId;
import de.metas.cache.CCache;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import de.metas.acct.Account;
import org.compiere.model.I_C_BP_Group_Acct;
import org.springframework.stereotype.Repository;

@Repository
public class BPartnerGroupAccountsRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<BPGroupId, ImmutableMap<AcctSchemaId, BPartnerGroupAccounts>> cache = CCache.<BPGroupId, ImmutableMap<AcctSchemaId, BPartnerGroupAccounts>>builder()
			.tableName(I_C_BP_Group_Acct.Table_Name)
			.cacheMapType(CCache.CacheMapType.LRU)
			.initialCapacity(50)
			.build();

	public BPartnerGroupAccounts getAccounts(@NonNull final BPGroupId bpGroupId, @NonNull final AcctSchemaId acctSchemaId)
	{
		final ImmutableMap<AcctSchemaId, BPartnerGroupAccounts> map = cache.getOrLoad(bpGroupId, this::retrieveAccounts);
		final BPartnerGroupAccounts accounts = map.get(acctSchemaId);
		if (accounts == null)
		{
			throw new AdempiereException("No BP Group accounts defined for " + bpGroupId + " and " + acctSchemaId);
		}
		return accounts;
	}

	private ImmutableMap<AcctSchemaId, BPartnerGroupAccounts> retrieveAccounts(@NonNull final BPGroupId bpGroupId)
	{
		return queryBL.createQueryBuilder(I_C_BP_Group_Acct.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BP_Group_Acct.COLUMNNAME_C_BP_Group_ID, bpGroupId)
				.create()
				.stream()
				.map(BPartnerGroupAccountsRepository::fromRecord)
				.collect(ImmutableMap.toImmutableMap(BPartnerGroupAccounts::getAcctSchemaId, accounts -> accounts));
	}

	@NonNull
	private static BPartnerGroupAccounts fromRecord(@NonNull final I_C_BP_Group_Acct record)
	{
		return BPartnerGroupAccounts.builder()
				.acctSchemaId(AcctSchemaId.ofRepoId(record.getC_AcctSchema_ID()))
				.PayDiscount_Expense_Acct(Account.of(AccountId.ofRepoId(record.getPayDiscount_Exp_Acct()), I_C_BP_Group_Acct.COLUMNNAME_PayDiscount_Exp_Acct))
				.PayDiscount_Revenue_Acct(Account.of(AccountId.ofRepoId(record.getPayDiscount_Rev_Acct()), I_C_BP_Group_Acct.COLUMNNAME_PayDiscount_Rev_Acct))
				.WriteOff_Acct(Account.of(AccountId.ofRepoId(record.getWriteOff_Acct()), I_C_BP_Group_Acct.COLUMNNAME_WriteOff_Acct))
				.NotInvoicedReceipts_Acct(Account.of(AccountId.ofRepoId(record.getNotInvoicedReceipts_Acct()), I_C_BP_Group_Acct.COLUMNNAME_NotInvoicedReceipts_Acct))
				.build();
	}
}
