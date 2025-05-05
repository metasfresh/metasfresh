package de.metas.acct.accounts;

import com.google.common.collect.ImmutableMap;
import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchemaId;
import de.metas.cache.CCache;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import de.metas.acct.Account;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_M_Warehouse_Acct;
import org.springframework.stereotype.Repository;

@Repository
public class WarehouseAccountsRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<WarehouseId, ImmutableMap<AcctSchemaId, WarehouseAccounts>> cache = CCache.<WarehouseId, ImmutableMap<AcctSchemaId, WarehouseAccounts>>builder()
			.tableName(I_M_Warehouse.Table_Name)
			.cacheMapType(CCache.CacheMapType.LRU)
			.initialCapacity(50)
			.build();

	public WarehouseAccounts getAccounts(@NonNull final WarehouseId warehouseId, @NonNull final AcctSchemaId acctSchemaId)
	{
		final ImmutableMap<AcctSchemaId, WarehouseAccounts> map = cache.getOrLoad(warehouseId, this::retrieveAccounts);
		final WarehouseAccounts accounts = map.get(acctSchemaId);
		if (accounts == null)
		{
			throw new AdempiereException("No Warehouse accounts defined for " + warehouseId + " and " + acctSchemaId);
		}
		return accounts;
	}

	private ImmutableMap<AcctSchemaId, WarehouseAccounts> retrieveAccounts(@NonNull final WarehouseId warehouseId)
	{
		return queryBL.createQueryBuilder(I_M_Warehouse_Acct.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Warehouse_Acct.COLUMNNAME_M_Warehouse_ID, warehouseId)
				.create()
				.stream()
				.map(WarehouseAccountsRepository::fromRecord)
				.collect(ImmutableMap.toImmutableMap(WarehouseAccounts::getAcctSchemaId, accounts -> accounts));
	}

	private static WarehouseAccounts fromRecord(@NonNull final I_M_Warehouse_Acct record)
	{
		return WarehouseAccounts.builder()
				.acctSchemaId(AcctSchemaId.ofRepoId(record.getC_AcctSchema_ID()))
				.W_Differences_Acct(Account.of(AccountId.ofRepoId(record.getW_Differences_Acct()), I_M_Warehouse_Acct.COLUMNNAME_W_Differences_Acct))
				.build();
	}

}
