package de.metas.acct.accounts;

import de.metas.acct.AccountConceptualName;
import de.metas.acct.AccountConceptualNameAware;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.I_C_BP_Vendor_Acct;

@Getter
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

	@NonNull private final AccountConceptualName accountConceptualName;

	BPartnerVendorAccountType(final @NonNull String columnName)
	{
		this.accountConceptualName = AccountConceptualName.ofString(columnName);
	}

	public static boolean isVendorLiability(final AccountConceptualName accountConceptualName)
	{
		return V_Liability.accountConceptualName.equals(accountConceptualName);
	}
}
