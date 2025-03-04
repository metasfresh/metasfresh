package de.metas.acct.accounts;

import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchemaId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

@Value
@Builder
public class CostElementAccounts
{
	@NonNull AcctSchemaId acctSchemaId;
	@NonNull AccountId P_CostClearing_Acct;

	public AccountId getAccountId(@NonNull final CostElementAccountType acctType)
	{
		if (acctType == CostElementAccountType.P_CostClearing_Acct)
		{
			return P_CostClearing_Acct;
		}
		else
		{
			throw new AdempiereException("Unknown account type: " + acctType);
		}
	}
}

