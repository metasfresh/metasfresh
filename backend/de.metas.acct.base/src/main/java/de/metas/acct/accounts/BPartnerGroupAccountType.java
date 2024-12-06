package de.metas.acct.accounts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.I_C_BP_Group_Acct;

@AllArgsConstructor
public enum BPartnerGroupAccountType
{
	/**
	 * Allocation - Discount Expense (AR)
	 */
	DiscountExp(I_C_BP_Group_Acct.COLUMNNAME_PayDiscount_Exp_Acct),
	/**
	 * Allocation - Discount Revenue (AP)
	 */
	DiscountRev(I_C_BP_Group_Acct.COLUMNNAME_PayDiscount_Rev_Acct),
	/**
	 * Allocation - Write Off
	 */
	WriteOff(I_C_BP_Group_Acct.COLUMNNAME_WriteOff_Acct),

	/**
	 * Inventory Accounts - NIR
	 */
	NotInvoicedReceipts(I_C_BP_Group_Acct.COLUMNNAME_NotInvoicedReceipts_Acct),

	;

	@Getter @NonNull private final String columnName;
}
