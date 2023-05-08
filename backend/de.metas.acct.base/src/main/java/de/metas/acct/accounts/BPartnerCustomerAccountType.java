package de.metas.acct.accounts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.I_C_BP_Customer_Acct;

@AllArgsConstructor
public enum BPartnerCustomerAccountType
{
	/**
	 * Invoice - AR
	 */
	C_Receivable(I_C_BP_Customer_Acct.COLUMNNAME_C_Receivable_Acct),
	/**
	 * Invoice - AR Service
	 */
	C_Receivable_Services(I_C_BP_Customer_Acct.COLUMNNAME_C_Receivable_Services_Acct),
	/**
	 * Payment - Prepayment
	 */
	C_Prepayment(I_C_BP_Customer_Acct.COLUMNNAME_C_Prepayment_Acct),

	;

	@Getter @NonNull private final String columnName;
}
