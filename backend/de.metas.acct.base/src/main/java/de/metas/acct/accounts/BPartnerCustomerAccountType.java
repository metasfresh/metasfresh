package de.metas.acct.accounts;

import de.metas.acct.AccountConceptualName;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.I_C_BP_Customer_Acct;

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

	@Getter @NonNull private final AccountConceptualName accountConceptualName;

	BPartnerCustomerAccountType(final @NonNull String columnName)
	{
		this.accountConceptualName = AccountConceptualName.ofString(columnName);
	}
}
