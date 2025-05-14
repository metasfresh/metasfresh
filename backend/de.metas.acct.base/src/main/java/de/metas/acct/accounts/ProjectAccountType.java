package de.metas.acct.accounts;

import de.metas.acct.AccountConceptualName;
import de.metas.acct.AccountConceptualNameAware;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.I_C_Project_Acct;

@Getter
public enum ProjectAccountType implements AccountConceptualNameAware
{
	/**
	 * Project Accounts - Assets
	 */
	PJ_Asset_Acct(I_C_Project_Acct.COLUMNNAME_PJ_Asset_Acct),
	/**
	 * Project Accounts - WIP
	 */
	PJ_WIP_Acct(I_C_Project_Acct.COLUMNNAME_PJ_WIP_Acct),

	;

	@NonNull private final AccountConceptualName accountConceptualName;

	ProjectAccountType(@NonNull final String accountConceptualName)
	{
		this.accountConceptualName = AccountConceptualName.ofString(accountConceptualName);
	}
}
