package de.metas.acct.accounts;

import de.metas.acct.AccountConceptualNameAware;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.I_C_BP_Customer_Acct;

@Getter
@AllArgsConstructor
public enum BPartnerCustomerAccountType implements AccountConceptualNameAware
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

	@NonNull private final String accountConceptualName; // aka columnName

	public static boolean isCustomerReceivable(final String accountConceptualName)
	{
		return C_Receivable.accountConceptualName.equals(accountConceptualName);
	}
}

