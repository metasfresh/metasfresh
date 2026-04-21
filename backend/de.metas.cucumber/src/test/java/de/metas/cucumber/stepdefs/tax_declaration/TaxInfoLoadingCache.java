package de.metas.cucumber.stepdefs.tax_declaration;

import de.metas.acct.Account;
import de.metas.acct.accounts.TaxAccounts;
import de.metas.acct.accounts.TaxAccountsRepository;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.IAccountDAO;
import de.metas.acct.api.IAcctSchemaBL;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.elementvalue.ElementValue;
import de.metas.elementvalue.ElementValueRepository;
import de.metas.tax.api.TaxId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class TaxInfoLoadingCache
{
	@NonNull private final IAcctSchemaBL acctSchemaBL = Services.get(IAcctSchemaBL.class);
	@NonNull private final IAccountDAO accountDAO = Services.get(IAccountDAO.class);
	@NonNull private final TaxAccountsRepository taxAccountsRepo = SpringContextHolder.instance.getBean(TaxAccountsRepository.class);
	@NonNull private final ElementValueRepository elementValueRepo = SpringContextHolder.instance.getBean(ElementValueRepository.class);

	private final Map<TaxId, TaxInfo> taxInfoCache = new ConcurrentHashMap<>();

	/**
	 * Lazily loads (and caches per tax) the {@link TaxInfo} that maps T_Due / T_Credit conceptual
	 * names to the {@code "<accountno> <accountname>"} string the DB function emits.
	 */
	public TaxInfo getById(@NonNull final TaxId taxId)
	{
		return taxInfoCache.computeIfAbsent(taxId, this::loadTaxInfo);
	}

	private TaxInfo loadTaxInfo(@NonNull final TaxId taxId)
	{
		final AcctSchema acctSchema = acctSchemaBL.getPrimaryAcctSchema(Env.getClientId());
		final TaxAccounts taxAccounts = taxAccountsRepo.getAccounts(taxId, acctSchema.getId());

		return TaxInfo.builder()
				.taxId(taxId)
				.taxDueAccountName(resolveAccountName(taxAccounts.getT_Due_Acct()))
				.taxCreditAccountName(resolveAccountName(taxAccounts.getT_Credit_Acct()))
				.build();
	}

	/**
	 * Builds the {@code "<accountno> <accountname>"} string that {@code de_metas_acct.report_taxaccounts}
	 * emits in its {@code AccountName} column. The DB function composes it the same way, in
	 * {@code backend/de.metas.acct.base/src/main/sql/postgresql/ddl/functions/report_taxaccounts.sql}:
	 * <pre>
	 *   (accountno || ' ' || accountname)::text AS AccountName
	 * </pre>
	 * where {@code accountno} is {@link ElementValue#getValue()} and {@code accountname} is
	 * {@link ElementValue#getName()}.
	 */
	private String resolveAccountName(@NonNull final Account account)
	{
		final ElementValueId elementValueId = accountDAO.getElementValueIdByAccountId(account.getAccountId());
		final ElementValue elementValue = elementValueRepo.getById(elementValueId);
		return elementValue.getValue() + " " + elementValue.getName();
	}
}
