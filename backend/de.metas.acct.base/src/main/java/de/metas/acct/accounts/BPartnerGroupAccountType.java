package de.metas.acct.accounts;

import de.metas.acct.AccountConceptualName;
import de.metas.acct.AccountConceptualNameAware;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.I_C_BP_Group_Acct;

@Getter
@AllArgsConstructor
public enum BPartnerGroupAccountType implements AccountConceptualNameAware
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

	@NonNull private final AccountConceptualName accountConceptualName;

	BPartnerGroupAccountType(@NonNull final String accountConceptualName)
	{
		this.accountConceptualName = AccountConceptualName.ofString(accountConceptualName);
	}
}
