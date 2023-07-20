package de.metas.acct.accounts;

import com.google.common.collect.ImmutableMap;
import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchemaId;
import de.metas.cache.CCache;
import de.metas.costing.CostElementId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_CostElement_Acct;
import org.springframework.stereotype.Repository;

@Repository
public class CostElementAccountsRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, CostElementAccountsMap> cache = CCache.<Integer, CostElementAccountsMap>builder()
			.tableName(I_M_CostElement_Acct.Table_Name)
			.initialCapacity(1)
			.build();

	public CostElementAccounts getAccounts(
			@NonNull final CostElementId costElementId,
			@NonNull final AcctSchemaId acctSchemaId)
	{
		return getMap().getAccounts(costElementId, acctSchemaId);
	}

	public void deleteByCostElementId(@NonNull CostElementId costElementId)
	{
		queryBL.createQueryBuilder(I_M_CostElement_Acct.class)
				.addEqualsFilter(I_M_CostElement_Acct.COLUMNNAME_M_CostElement_ID, costElementId)
				.create()
				.delete();
	}

	private CostElementAccountsMap getMap()
	{
		return cache.getOrLoad(0, this::retrieveMap);
	}

	private CostElementAccountsMap retrieveMap()
	{
		final ImmutableMap<CostElementIdAndAcctSchemaId, CostElementAccounts> map = queryBL
				.createQueryBuilder(I_M_CostElement_Acct.class)
				.addOnlyActiveRecordsFilter()
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						CostElementAccountsRepository::extractCostElementIdAndAcctSchemaId,
						CostElementAccountsRepository::fromRecord));

		return new CostElementAccountsMap(map);
	}

	private static CostElementIdAndAcctSchemaId extractCostElementIdAndAcctSchemaId(final I_M_CostElement_Acct record)
	{
		return CostElementIdAndAcctSchemaId.of(
				CostElementId.ofRepoId(record.getM_CostElement_ID()),
				AcctSchemaId.ofRepoId(record.getC_AcctSchema_ID()));
	}

	private static CostElementAccounts fromRecord(final I_M_CostElement_Acct record)
	{
		return CostElementAccounts.builder()
				.acctSchemaId(AcctSchemaId.ofRepoId(record.getC_AcctSchema_ID()))
				.P_CostClearing_Acct(AccountId.ofRepoId(record.getP_CostClearing_Acct()))
				.build();
	}

	@Value(staticConstructor = "of")
	private static class CostElementIdAndAcctSchemaId
	{
		@NonNull CostElementId costTypeId;
		@NonNull AcctSchemaId acctSchemaId;
	}

	private static class CostElementAccountsMap
	{
		private final ImmutableMap<CostElementIdAndAcctSchemaId, CostElementAccounts> map;

		public CostElementAccountsMap(
				@NonNull final ImmutableMap<CostElementIdAndAcctSchemaId, CostElementAccounts> map)
		{
			this.map = map;
		}

		public CostElementAccounts getAccounts(
				@NonNull final CostElementId costElementId,
				@NonNull final AcctSchemaId acctSchemaId)
		{
			final CostElementAccounts accounts = map.get(CostElementIdAndAcctSchemaId.of(costElementId, acctSchemaId));
			if (accounts == null)
			{
				throw new AdempiereException("No accounts found for " + costElementId + " and " + acctSchemaId);
			}
			return accounts;
		}
	}

}
