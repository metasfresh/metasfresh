package de.metas.acct.accounts;

import de.metas.acct.AccountConceptualName;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_M_CostElement_Acct;

public enum CostElementAccountType
{
	P_CostClearing_Acct(X_M_CostElement_Acct.COLUMNNAME_P_CostClearing_Acct),
	;

	@Getter @NonNull private final String columnName;
	@Getter @NonNull private final AccountConceptualName accountConceptualName;

	CostElementAccountType(@NonNull final String columnName)
	{
		this.columnName = columnName;
		this.accountConceptualName = AccountConceptualName.ofString(columnName);
	}

}
