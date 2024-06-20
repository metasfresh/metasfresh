package de.metas.acct.tax.impl;

import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAccountDAO;
import de.metas.acct.tax.ITaxAcctBL;
import de.metas.acct.tax.TaxAcctType;
import de.metas.cache.CCache;
import de.metas.cache.CCache.CacheMapType;
import de.metas.tax.api.TaxId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Tax_Acct;
import org.compiere.model.MAccount;

import java.util.Optional;

public class TaxAcctBL implements ITaxAcctBL
{
	private final IAccountDAO accountsRepo = Services.get(IAccountDAO.class);

	private final CCache<TaxIdAndAcctSchemaId, I_C_Tax_Acct> taxAcctRecords = CCache.<TaxIdAndAcctSchemaId, I_C_Tax_Acct> builder()
			.cacheMapType(CacheMapType.LRU)
			.initialCapacity(100)
			.additionalTableNameToResetFor(I_C_Tax_Acct.Table_Name)
			.build();

	@Override
	public MAccount getAccount(final TaxId taxId, final AcctSchemaId acctSchemaId, final TaxAcctType acctType)
	{
		return getAccountIfExists(taxId, acctSchemaId, acctType)
				.orElseThrow(() -> new AdempiereException("@NotFound@ " + acctType + " (" + taxId + ", " + acctSchemaId + ")"));
	}

	@Override
	public Optional<MAccount> getAccountIfExists(final TaxId taxId, final AcctSchemaId acctSchemaId, final TaxAcctType acctType)
	{
		return getAccountId(taxId, acctSchemaId, acctType)
				.map(accountsRepo::getById);
	}

	@Override
	public Optional<AccountId> getAccountId(@NonNull final TaxId taxId, @NonNull final AcctSchemaId acctSchemaId, final TaxAcctType acctType)
	{
		final I_C_Tax_Acct taxAcct = getTaxAcctRecord(acctSchemaId, taxId);

		if (TaxAcctType.TaxDue == acctType)
		{
			final Optional<AccountId> accountId = AccountId.optionalOfRepoId(taxAcct.getT_Due_Acct());
			return accountId;
		}
		else if (TaxAcctType.TaxLiability == acctType)
		{
			final Optional<AccountId> accountId = AccountId.optionalOfRepoId(taxAcct.getT_Liability_Acct());
			return accountId;
		}
		else if (TaxAcctType.TaxCredit == acctType)
		{
			final Optional<AccountId> accountId = AccountId.optionalOfRepoId(taxAcct.getT_Credit_Acct());
			return accountId;
		}
		else if (TaxAcctType.TaxReceivables == acctType)
		{
			final Optional<AccountId> accountId = AccountId.optionalOfRepoId(taxAcct.getT_Receivables_Acct());
			return accountId;
		}
		else if (TaxAcctType.TaxExpense == acctType)
		{
			final Optional<AccountId> accountId = AccountId.optionalOfRepoId(taxAcct.getT_Expense_Acct());
			return accountId;
		}
		else if (TaxAcctType.ProductRevenue_Override == acctType)
		{
			// might be not set
			return AccountId.optionalOfRepoId(taxAcct.getT_Revenue_Acct());
		}
		else
		{
			throw new AdempiereException("Unknown tax account type: " + acctType);
		}
	}

	private I_C_Tax_Acct getTaxAcctRecord(final AcctSchemaId acctSchemaId, final TaxId taxId)
	{
		return taxAcctRecords.getOrLoad(
				TaxIdAndAcctSchemaId.of(taxId, acctSchemaId),
				this::retrieveTaxAcctRecord);
	}

	private I_C_Tax_Acct retrieveTaxAcctRecord(@NonNull final TaxIdAndAcctSchemaId key)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_C_Tax_Acct.class)
				.addEqualsFilter(I_C_Tax_Acct.COLUMNNAME_C_Tax_ID, key.getTaxId())
				.addEqualsFilter(I_C_Tax_Acct.COLUMNNAME_C_AcctSchema_ID, key.getAcctSchemaId())
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnlyNotNull(I_C_Tax_Acct.class);
	}

	@Value(staticConstructor = "of")
	private static class TaxIdAndAcctSchemaId
	{
		@NonNull
		TaxId taxId;

		@NonNull
		AcctSchemaId acctSchemaId;
	}
}
