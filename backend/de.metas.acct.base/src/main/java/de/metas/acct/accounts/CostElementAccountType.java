package de.metas.acct.accounts;

import de.metas.acct.AccountConceptualName;
import de.metas.acct.AccountConceptualNameAware;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_M_CostElement_Acct;

@Getter
public enum CostElementAccountType implements AccountConceptualNameAware
{
	P_CostClearing_Acct(X_M_CostElement_Acct.COLUMNNAME_P_CostClearing_Acct),
	;

	@NonNull private final String columnName;
	@NonNull private final AccountConceptualName accountConceptualName;

	CostElementAccountType(@NonNull final String columnName)
	{
		this.columnName = columnName;
		this.accountConceptualName = AccountConceptualName.ofString(columnName);
	}
}
