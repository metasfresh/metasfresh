package de.metas.acct.accounts;

import de.metas.acct.AccountConceptualNameAware;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_M_CostElement_Acct;

@Getter
@AllArgsConstructor
public enum CostElementAccountType implements AccountConceptualNameAware
{
	P_CostClearing_Acct(X_M_CostElement_Acct.COLUMNNAME_P_CostClearing_Acct),
	;

	@NonNull private final String accountConceptualName;
}
