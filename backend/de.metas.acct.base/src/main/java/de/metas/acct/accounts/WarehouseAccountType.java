package de.metas.acct.accounts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.I_M_Warehouse_Acct;

@AllArgsConstructor
public enum WarehouseAccountType
{
	/**
	 * Inventory Accounts - Differences
	 */
	W_Differences_Acct(I_M_Warehouse_Acct.COLUMNNAME_W_Differences_Acct),

	;

	@Getter @NonNull private final String columnName;
}
