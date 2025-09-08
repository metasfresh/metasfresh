package de.metas.acct.accounts;

import de.metas.acct.AccountConceptualName;
import de.metas.acct.AccountConceptualNameAware;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.I_M_Warehouse_Acct;

@Getter
@AllArgsConstructor
public enum WarehouseAccountType implements AccountConceptualNameAware
{
	/**
	 * Inventory Accounts - Differences
	 */
	W_Differences_Acct(I_M_Warehouse_Acct.COLUMNNAME_W_Differences_Acct),

	;

	@NonNull private final AccountConceptualName accountConceptualName;

	WarehouseAccountType(@NonNull final String accountConceptualName)
	{
		this.accountConceptualName = AccountConceptualName.ofString(accountConceptualName);
	}
}
