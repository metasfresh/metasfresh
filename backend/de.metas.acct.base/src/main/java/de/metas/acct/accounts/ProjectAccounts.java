package de.metas.acct.accounts;

import de.metas.acct.api.AcctSchemaId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import de.metas.acct.Account;

@Value
@Builder
public class ProjectAccounts
{
	@NonNull AcctSchemaId acctSchemaId;
	@NonNull Account PJ_Asset_Acct;
	@NonNull Account PJ_WIP_Acct;

	@NonNull
	public Account getAccount(@NonNull final ProjectAccountType acctType)
	{
		switch (acctType)
		{
			case PJ_Asset_Acct:
				return PJ_Asset_Acct;
			case PJ_WIP_Acct:
				return PJ_WIP_Acct;
			default:
				throw new AdempiereException("Unknown account type: " + acctType);
		}
	}

}
