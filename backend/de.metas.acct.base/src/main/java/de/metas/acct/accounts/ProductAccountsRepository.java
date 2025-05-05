package de.metas.acct.accounts;

import com.google.common.collect.ImmutableMap;
import de.metas.acct.Account;
import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchemaId;
import de.metas.cache.CCache;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Product_Acct;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Optional;

@Repository
public class ProductAccountsRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<ProductId, ImmutableMap<AcctSchemaId, ProductAccounts>> cache = CCache.<ProductId, ImmutableMap<AcctSchemaId, ProductAccounts>>builder()
			.tableName(I_M_Product_Acct.Table_Name)
			.cacheMapType(CCache.CacheMapType.LRU)
			.initialCapacity(100)
			.expireMinutes(CCache.EXPIREMINUTES_Never)
			.build();

	public Optional<ProductAccounts> getAccountsIfExists(@NonNull final ProductId productId, @NonNull final AcctSchemaId acctSchemaId)
	{
		return Optional.ofNullable(getAccountsOrNull(productId, acctSchemaId));
	}

	@NonNull
	public ProductAccounts getAccounts(@NonNull final ProductId productId, @NonNull final AcctSchemaId acctSchemaId)
	{
		final ProductAccounts accounts = getAccountsOrNull(productId, acctSchemaId);
		if (accounts == null)
		{
			throw new AdempiereException("No Product accounts found for " + productId + ", " + acctSchemaId);
		}
		return accounts;
	}

	@Nullable
	private ProductAccounts getAccountsOrNull(@NonNull final ProductId productId, @NonNull final AcctSchemaId acctSchemaId)
	{
		final ImmutableMap<AcctSchemaId, ProductAccounts> map = cache.getOrLoad(productId, this::retrieveProductAccountsMap);
		return map.get(acctSchemaId);
	}

	private ImmutableMap<AcctSchemaId, ProductAccounts> retrieveProductAccountsMap(@NonNull final ProductId productId)
	{
		return queryBL.createQueryBuilder(I_M_Product_Acct.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Product_Acct.COLUMNNAME_M_Product_ID, productId)
				.create()
				.stream()
				.map(ProductAccountsRepository::fromRecord)
				.collect(ImmutableMap.toImmutableMap(ProductAccounts::getAcctSchemaId, accounts -> accounts));
	}

	@NonNull
	private static ProductAccounts fromRecord(@NonNull final I_M_Product_Acct record)
	{
		return ProductAccounts.builder()
				.acctSchemaId(AcctSchemaId.ofRepoId(record.getC_AcctSchema_ID()))
				.activityId(Optional.ofNullable(ActivityId.ofRepoIdOrNull(record.getC_Activity_ID())))
				.P_Revenue_Acct(Account.of(AccountId.ofRepoId(record.getP_Revenue_Acct()), I_M_Product_Acct.COLUMNNAME_P_Revenue_Acct))
				.P_Expense_Acct(Account.of(AccountId.ofRepoId(record.getP_Expense_Acct()), I_M_Product_Acct.COLUMNNAME_P_Expense_Acct))
				.P_Asset_Acct(Account.of(AccountId.ofRepoId(record.getP_Asset_Acct()), I_M_Product_Acct.COLUMNNAME_P_Asset_Acct))
				.P_COGS_Acct(Account.of(AccountId.ofRepoId(record.getP_COGS_Acct()), I_M_Product_Acct.COLUMNNAME_P_COGS_Acct))
				.P_PurchasePriceVariance_Acct(Account.of(AccountId.ofRepoId(record.getP_PurchasePriceVariance_Acct()), I_M_Product_Acct.COLUMNNAME_P_PurchasePriceVariance_Acct))
				.P_InvoicePriceVariance_Acct(Account.of(AccountId.ofRepoId(record.getP_InvoicePriceVariance_Acct()), I_M_Product_Acct.COLUMNNAME_P_InvoicePriceVariance_Acct))
				.P_TradeDiscountRec_Acct(Account.of(AccountId.ofRepoId(record.getP_TradeDiscountRec_Acct()), I_M_Product_Acct.COLUMNNAME_P_TradeDiscountRec_Acct))
				.P_TradeDiscountGrant_Acct(Account.of(AccountId.ofRepoId(record.getP_TradeDiscountGrant_Acct()), I_M_Product_Acct.COLUMNNAME_P_TradeDiscountGrant_Acct))
				.P_CostAdjustment_Acct(Account.of(AccountId.ofRepoId(record.getP_CostAdjustment_Acct()), I_M_Product_Acct.COLUMNNAME_P_CostAdjustment_Acct))
				.P_InventoryClearing_Acct(Account.of(AccountId.ofRepoId(record.getP_InventoryClearing_Acct()), I_M_Product_Acct.COLUMNNAME_P_InventoryClearing_Acct))
				.P_WIP_Acct(Account.of(AccountId.ofRepoId(record.getP_WIP_Acct()), I_M_Product_Acct.COLUMNNAME_P_WIP_Acct))
				.P_MethodChangeVariance_Acct(Account.of(AccountId.ofRepoId(record.getP_MethodChangeVariance_Acct()), I_M_Product_Acct.COLUMNNAME_P_MethodChangeVariance_Acct))
				.P_UsageVariance_Acct(Account.of(AccountId.ofRepoId(record.getP_UsageVariance_Acct()), I_M_Product_Acct.COLUMNNAME_P_UsageVariance_Acct))
				.P_RateVariance_Acct(Account.of(AccountId.ofRepoId(record.getP_RateVariance_Acct()), I_M_Product_Acct.COLUMNNAME_P_RateVariance_Acct))
				.P_MixVariance_Acct(Account.of(AccountId.ofRepoId(record.getP_MixVariance_Acct()), I_M_Product_Acct.COLUMNNAME_P_MixVariance_Acct))
				.P_FloorStock_Acct(Account.of(AccountId.ofRepoId(record.getP_FloorStock_Acct()), I_M_Product_Acct.COLUMNNAME_P_FloorStock_Acct))
				.P_CostOfProduction_Acct(Account.of(AccountId.ofRepoId(record.getP_CostOfProduction_Acct()), I_M_Product_Acct.COLUMNNAME_P_CostOfProduction_Acct))
				.P_Labor_Acct(Account.of(AccountId.ofRepoId(record.getP_Labor_Acct()), I_M_Product_Acct.COLUMNNAME_P_Labor_Acct))
				.P_Burden_Acct(Account.of(AccountId.ofRepoId(record.getP_Burden_Acct()), I_M_Product_Acct.COLUMNNAME_P_Burden_Acct))
				.P_OutsideProcessing_Acct(Account.of(AccountId.ofRepoId(record.getP_OutsideProcessing_Acct()), I_M_Product_Acct.COLUMNNAME_P_OutsideProcessing_Acct))
				.P_Overhead_Acct(Account.of(AccountId.ofRepoId(record.getP_Overhead_Acct()), I_M_Product_Acct.COLUMNNAME_P_Overhead_Acct))
				.P_Scrap_Acct(Account.of(AccountId.ofRepoId(record.getP_Scrap_Acct()), I_M_Product_Acct.COLUMNNAME_P_Scrap_Acct))
				.P_ExternallyOwnedStock_Acct(Account.of(AccountId.ofRepoId(record.getP_ExternallyOwnedStock_Acct()), I_M_Product_Acct.COLUMNNAME_P_ExternallyOwnedStock_Acct))
				.build();
	}
}
