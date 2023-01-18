package de.metas.acct.accounts;

import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchemaId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ChargeAccounts
{
	@NonNull AcctSchemaId acctSchemaId;
	@NonNull AccountId Ch_Expense_Acct;
	@NonNull AccountId Ch_Revenue_Acct;
}
