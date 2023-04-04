package de.metas.banking.accounting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.I_C_BP_BankAccount_Acct;

@AllArgsConstructor
public enum BankAccountAcctType
{

	/**
	 * Payment - Unallocated
	 */
	B_UnallocatedCash_Acct(I_C_BP_BankAccount_Acct.COLUMNNAME_B_UnallocatedCash_Acct),
	/**
	 * Payment - Transfer
	 */
	B_InTransit_Acct(I_C_BP_BankAccount_Acct.COLUMNNAME_B_InTransit_Acct),
	/**
	 * Payment - Selection
	 */
	B_PaymentSelect_Acct(I_C_BP_BankAccount_Acct.COLUMNNAME_B_PaymentSelect_Acct),

	/**
	 * Allocation - Pay Bank Fee
	 */
	PayBankFee_Acct(I_C_BP_BankAccount_Acct.COLUMNNAME_PayBankFee_Acct),

	/**
	 * Bank Statement - Asset
	 */
	B_Asset_Acct(I_C_BP_BankAccount_Acct.COLUMNNAME_B_Asset_Acct),

	/**
	 * Bank Statement - Interest Revenue
	 */
	B_InterestRev_Acct(I_C_BP_BankAccount_Acct.COLUMNNAME_B_InterestRev_Acct),

	/**
	 * Bank Statement - Interest Exp
	 */
	B_InterestExp_Acct(I_C_BP_BankAccount_Acct.COLUMNNAME_B_InterestExp_Acct),

	Payment_WriteOff_Acct(I_C_BP_BankAccount_Acct.COLUMNNAME_Payment_WriteOff_Acct),


	/**
	 * Allocation - Realized Gain
	 */
	RealizedGain_Acct(I_C_BP_BankAccount_Acct.COLUMNNAME_RealizedGain_Acct),

	/**
	 * Allocation - Realized Loss
	 */
	RealizedLoss_Acct(I_C_BP_BankAccount_Acct.COLUMNNAME_RealizedLoss_Acct),
	;

	@Getter @NonNull private final String columnName;
}
