package de.metas.acct.api.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.acct.api.IProductAcctDAO;
import de.metas.acct.api.ProductAcctType;
import de.metas.acct.api.ProductCategoryAccounts;
import de.metas.cache.CCache;
import de.metas.cache.CCache.CacheMapType;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.organization.OrgId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.util.Services;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_M_Product_Acct;
import org.compiere.model.I_M_Product_Category_Acct;
import org.compiere.model.POInfo;
import org.compiere.util.DisplayType;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class ProductAcctDAO implements IProductAcctDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IAcctSchemaDAO acctSchemaDAO = Services.get(IAcctSchemaDAO.class);

	private final CCache<ProductIdAndAcctSchemaId, Optional<I_M_Product_Acct>> productAcctRecords = CCache.<ProductIdAndAcctSchemaId, Optional<I_M_Product_Acct>> builder()
			.cacheMapType(CacheMapType.LRU)
			.initialCapacity(100)
			.expireMinutes(CCache.EXPIREMINUTES_Never)
			.additionalTableNameToResetFor(I_M_Product_Acct.Table_Name)
			.build();

	private final CCache<Integer, ProductCategoryAccountsCollection> productCategoryAcctCollectionCache = CCache.<Integer, ProductCategoryAccountsCollection> builder()
			.initialCapacity(1)
			.expireMinutes(CCache.EXPIREMINUTES_Never)
			.additionalTableNameToResetFor(I_M_Product_Category_Acct.Table_Name)
			.build();

	@Override
	public ActivityId retrieveActivityForAcct(
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId,
			@NonNull final ProductId productId)
	{
		final AcctSchemaId acctSchemaId = acctSchemaDAO.getAcctSchemaIdByClientAndOrg(clientId, orgId);
		if (acctSchemaId == null)
		{
			return null;
		}

		final I_M_Product_Acct acctInfo = getProductAcctRecord(acctSchemaId, productId).orElse(null);
		if (acctInfo == null)
		{
			return null;
		}

		return ActivityId.ofRepoIdOrNull(acctInfo.getC_Activity_ID());
	}

	private Optional<I_M_Product_Acct> getProductAcctRecord(final AcctSchemaId acctSchemaId, final ProductId productId)
	{
		return productAcctRecords.getOrLoad(
				ProductIdAndAcctSchemaId.of(productId, acctSchemaId),
				this::retrieveProductAcctRecord);
	}

	private Optional<I_M_Product_Acct> retrieveProductAcctRecord(@NonNull final ProductIdAndAcctSchemaId key)
	{
		final I_M_Product_Acct record = queryBL
				.createQueryBuilderOutOfTrx(I_M_Product_Acct.class)
				.addEqualsFilter(I_M_Product_Acct.COLUMNNAME_C_AcctSchema_ID, key.getAcctSchemaId())
				.addEqualsFilter(I_M_Product_Acct.COLUMNNAME_M_Product_ID, key.getProductId())
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_M_Product_Acct.class);

		return Optional.ofNullable(record);
	}

	@Override
	public Optional<AccountId> getProductAccount(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final ProductId productId,
			@NonNull final ProductAcctType acctType)
	{
		final I_M_Product_Acct productAcct = getProductAcctRecord(acctSchemaId, productId).orElse(null);
		if (productAcct == null)
		{
			return Optional.empty();
		}

		final Integer validCombinationId = InterfaceWrapperHelper.getValueOrNull(productAcct, acctType.getColumnName());
		if (validCombinationId == null || validCombinationId <= 0)
		{
			return Optional.empty();
		}

		return Optional.of(AccountId.ofRepoId(validCombinationId));
	}

	private ProductCategoryAccountsCollection getProductCategoryAccountsCollection()
	{
		return this.productCategoryAcctCollectionCache.getOrLoad(0, this::retrieveProductCategoryAccountsCollection);
	}

	private ProductCategoryAccountsCollection retrieveProductCategoryAccountsCollection()
	{
		final POInfo poInfo = POInfo.getPOInfo(I_M_Product_Category_Acct.Table_Name);
		final ImmutableSet<String> acctColumnNames = poInfo.getColumnNames()
				.stream()
				.filter(columnName -> poInfo.getColumnDisplayType(columnName) == DisplayType.Account)
				.collect(ImmutableSet.toImmutableSet());

		final ImmutableList<ProductCategoryAccounts> allProductCategoryAccts = queryBL.createQueryBuilderOutOfTrx(I_M_Product_Category_Acct.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(record -> toProductCategoryAcct(record, acctColumnNames))
				.collect(ImmutableList.toImmutableList());

		return new ProductCategoryAccountsCollection(allProductCategoryAccts);
	}

	private static ProductCategoryAccounts toProductCategoryAcct(
			@NonNull final I_M_Product_Category_Acct record,
			@NonNull final ImmutableSet<String> acctColumnNames)
	{
		final HashMap<String, Optional<AccountId>> accountIds = new HashMap<>(acctColumnNames.size());
		for (final String acctColumnName : acctColumnNames)
		{
			final Optional<AccountId> accountId = extractAccountId(record, acctColumnName);
			accountIds.put(acctColumnName, accountId);
		}

		return ProductCategoryAccounts.builder()
				.productCategoryId(ProductCategoryId.ofRepoId(record.getM_Product_Category_ID()))
				.acctSchemaId(AcctSchemaId.ofRepoId(record.getC_AcctSchema_ID()))
				.costingLevel(CostingLevel.forNullableCode(record.getCostingLevel()))
				.costingMethod(CostingMethod.ofNullableCode(record.getCostingMethod()))
				.accountIdsByColumnName(accountIds)
				.build();
	}

	private static Optional<AccountId> extractAccountId(@NonNull final I_M_Product_Category_Acct record, @NonNull final String acctColumnName)
	{
		final Integer validCombinationId = InterfaceWrapperHelper.getValueOrNull(record, acctColumnName);
		return validCombinationId != null
				? AccountId.optionalOfRepoId(validCombinationId)
				: Optional.empty();
	}

	public Optional<ProductCategoryAccounts> getProductCategoryAccounts(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final ProductCategoryId productCategoryId)
	{
		return getProductCategoryAccountsCollection()
				.getBy(productCategoryId, acctSchemaId);
	}

	@Override
	public Optional<AccountId> getProductCategoryAccount(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final ProductCategoryId productCategoryId,
			@NonNull final ProductAcctType acctType)
	{
		return getProductCategoryAccounts(acctSchemaId, productCategoryId)
				.flatMap(productCategoryAcct -> productCategoryAcct.getAccountId(acctType));
	}

	@Value(staticConstructor = "of")
	private static class ProductIdAndAcctSchemaId
	{
		@NonNull
		ProductId productId;
		@NonNull
		AcctSchemaId acctSchemaId;
	}

	@Value(staticConstructor = "of")
	private static class ProductCategoryIdAndAcctSchemaId
	{
		@NonNull
		ProductCategoryId productCategoryId;
		@NonNull
		AcctSchemaId acctSchemaId;
	}

	@EqualsAndHashCode
	@ToString
	private static class ProductCategoryAccountsCollection
	{
		private final ImmutableMap<ProductCategoryIdAndAcctSchemaId, ProductCategoryAccounts> productCategoryAccts;

		public ProductCategoryAccountsCollection(final List<ProductCategoryAccounts> productCategoryAccts)
		{
			this.productCategoryAccts = Maps.uniqueIndex(
					productCategoryAccts,
					productCategoryAcct -> ProductCategoryIdAndAcctSchemaId.of(productCategoryAcct.getProductCategoryId(), productCategoryAcct.getAcctSchemaId()));
		}

		public Optional<ProductCategoryAccounts> getBy(
				@NonNull final ProductCategoryId productCategoryId,
				@NonNull final AcctSchemaId acctSchemaId)
		{
			final ProductCategoryIdAndAcctSchemaId key = ProductCategoryIdAndAcctSchemaId.of(productCategoryId, acctSchemaId);
			return Optional.ofNullable(productCategoryAccts.get(key));
		}
	}
}
