package de.metas.acct.accounts;

import com.google.common.collect.ImmutableMap;
import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchemaId;
import de.metas.bpartner.BPartnerId;
import de.metas.cache.CCache;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import de.metas.acct.Account;
import org.compiere.model.I_C_BP_Customer_Acct;
import org.compiere.model.I_C_BP_Vendor_Acct;
import org.springframework.stereotype.Repository;

@Repository
public class BPartnerAccountsRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<BPartnerId, ImmutableMap<AcctSchemaId, BPartnerVendorAccounts>> vendorAccountsCache = CCache.<BPartnerId, ImmutableMap<AcctSchemaId, BPartnerVendorAccounts>>builder()
			.tableName(I_C_BP_Vendor_Acct.Table_Name)
			.cacheMapType(CCache.CacheMapType.LRU)
			.initialCapacity(100)
			.build();
	private final CCache<BPartnerId, ImmutableMap<AcctSchemaId, BPartnerCustomerAccounts>> customerAccountsCache = CCache.<BPartnerId, ImmutableMap<AcctSchemaId, BPartnerCustomerAccounts>>builder()
			.tableName(I_C_BP_Customer_Acct.Table_Name)
			.cacheMapType(CCache.CacheMapType.LRU)
			.initialCapacity(100)
			.build();

	@NonNull
	public BPartnerVendorAccounts getVendorAccounts(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final AcctSchemaId acctSchemaId)
	{
		final ImmutableMap<AcctSchemaId, BPartnerVendorAccounts> map = vendorAccountsCache.getOrLoad(bpartnerId, this::retrieveVendorAccounts);
		final BPartnerVendorAccounts accounts = map.get(acctSchemaId);
		if (accounts == null)
		{
			throw new AdempiereException("No vendor accounts defined for " + bpartnerId + " and " + acctSchemaId);
		}
		return accounts;
	}

	private ImmutableMap<AcctSchemaId, BPartnerVendorAccounts> retrieveVendorAccounts(final BPartnerId bpartnerId)
	{
		return queryBL.createQueryBuilder(I_C_BP_Vendor_Acct.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BP_Vendor_Acct.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.create()
				.stream()
				.map(BPartnerAccountsRepository::fromRecord)
				.collect(ImmutableMap.toImmutableMap(BPartnerVendorAccounts::getAcctSchemaId, accts -> accts));
	}

	private static BPartnerVendorAccounts fromRecord(@NonNull final I_C_BP_Vendor_Acct record)
	{
		return BPartnerVendorAccounts.builder()
				.acctSchemaId(AcctSchemaId.ofRepoId(record.getC_AcctSchema_ID()))
				.V_Liability_Acct(Account.of(AccountId.ofRepoId(record.getV_Liability_Acct()), I_C_BP_Vendor_Acct.COLUMNNAME_V_Liability_Acct))
				.V_Liability_Services_Acct(Account.of(AccountId.ofRepoId(record.getV_Liability_Services_Acct()), I_C_BP_Vendor_Acct.COLUMNNAME_V_Liability_Services_Acct))
				.V_Prepayment_Acct(Account.of(AccountId.ofRepoId(record.getV_Prepayment_Acct()), I_C_BP_Vendor_Acct.COLUMNNAME_V_Prepayment_Acct))
				.build();
	}

	public BPartnerCustomerAccounts getCustomerAccounts(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final AcctSchemaId acctSchemaId)
	{
		final ImmutableMap<AcctSchemaId, BPartnerCustomerAccounts> map = customerAccountsCache.getOrLoad(bpartnerId, this::retrieveCustomerAccounts);
		final BPartnerCustomerAccounts accounts = map.get(acctSchemaId);
		if (accounts == null)
		{
			throw new AdempiereException("No customer accounts defined for " + bpartnerId + " and " + acctSchemaId);
		}
		return accounts;
	}

	private ImmutableMap<AcctSchemaId, BPartnerCustomerAccounts> retrieveCustomerAccounts(final BPartnerId bpartnerId)
	{
		return queryBL.createQueryBuilder(I_C_BP_Customer_Acct.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BP_Customer_Acct.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.create()
				.stream()
				.map(BPartnerAccountsRepository::fromRecord)
				.collect(ImmutableMap.toImmutableMap(BPartnerCustomerAccounts::getAcctSchemaId, accts -> accts));
	}

	@NonNull
	private static BPartnerCustomerAccounts fromRecord(@NonNull final I_C_BP_Customer_Acct record)
	{
		return BPartnerCustomerAccounts.builder()
				.acctSchemaId(AcctSchemaId.ofRepoId(record.getC_AcctSchema_ID()))
				.C_Receivable_Acct(Account.of(AccountId.ofRepoId(record.getC_Receivable_Acct()), I_C_BP_Customer_Acct.COLUMNNAME_C_Receivable_Acct))
				.C_Receivable_Services_Acct(Account.of(AccountId.ofRepoId(record.getC_Receivable_Services_Acct()), I_C_BP_Customer_Acct.COLUMNNAME_C_Receivable_Services_Acct))
				.C_Prepayment_Acct(Account.of(AccountId.ofRepoId(record.getC_Prepayment_Acct()), I_C_BP_Customer_Acct.COLUMNNAME_C_Prepayment_Acct))
				.build();
	}
}
