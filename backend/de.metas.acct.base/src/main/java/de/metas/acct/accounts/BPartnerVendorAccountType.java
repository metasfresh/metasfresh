package de.metas.acct.accounts;

import de.metas.acct.AccountConceptualNameAware;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.I_C_BP_Vendor_Acct;

@Getter
@AllArgsConstructor
public enum BPartnerVendorAccountType implements AccountConceptualNameAware
{
	/**
	 * Invoice - AP
	 */
	V_Liability(I_C_BP_Vendor_Acct.COLUMNNAME_V_Liability_Acct),
	/**
	 * Invoice - AP Service
	 */
	V_Liability_Services(I_C_BP_Vendor_Acct.COLUMNNAME_V_Liability_Services_Acct),

	/**
	 * Payment - Prepayment
	 */
	V_Prepayment(I_C_BP_Vendor_Acct.COLUMNNAME_V_Prepayment_Acct),

	;

	@NonNull private final String accountConceptualName;

	public static boolean isVendorLiability(final String accountConceptualName)
	{
		return V_Liability.accountConceptualName.equals(accountConceptualName);
	}
}
