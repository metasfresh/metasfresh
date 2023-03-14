package de.metas.acct.accounts;

import de.metas.acct.api.AcctSchemaId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import de.metas.acct.Account;

@Value
@Builder
public class WarehouseAccounts
{
	@NonNull AcctSchemaId acctSchemaId;

	@NonNull Account W_Differences_Acct;

	@NonNull
	public Account getAccount(@NonNull final WarehouseAccountType acctType)
	{
		//noinspection SwitchStatementWithTooFewBranches
		switch (acctType)
		{
			case W_Differences_Acct:
				return W_Differences_Acct;
			default:
				throw new AdempiereException("Unknown account type: " + acctType);
		}
	}
}
