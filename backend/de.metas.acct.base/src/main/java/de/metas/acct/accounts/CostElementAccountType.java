package de.metas.acct.accounts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_M_CostElement_Acct;

@AllArgsConstructor
public enum CostElementAccountType
{
	P_CostClearing_Acct(X_M_CostElement_Acct.COLUMNNAME_P_CostClearing_Acct),
	;

	@Getter @NonNull private final String columnName;
}
