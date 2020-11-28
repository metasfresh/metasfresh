package de.metas.acct.tax;

import java.util.Optional;

import org.compiere.model.MAccount;

import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchemaId;
import de.metas.tax.api.TaxId;
import de.metas.util.ISingletonService;

/**
 * Tax Accounting
 *
 * @author tsa
 *
 */
public interface ITaxAcctBL extends ISingletonService
{
	MAccount getAccount(TaxId taxId, AcctSchemaId acctSchemaId, TaxAcctType acctType);

	Optional<MAccount> getAccountIfExists(TaxId taxId, AcctSchemaId acctSchemaId, TaxAcctType acctType);

	Optional<AccountId> getAccountId(TaxId taxId, AcctSchemaId acctSchemaId, TaxAcctType acctType);

}
