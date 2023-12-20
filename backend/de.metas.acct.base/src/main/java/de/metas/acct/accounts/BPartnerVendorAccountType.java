package de.metas.acct.accounts;

import de.metas.acct.AccountConceptualName;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.I_C_BP_Vendor_Acct;

public enum BPartnerVendorAccountType
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

	@Getter @NonNull private final AccountConceptualName accountConceptualName;

	BPartnerVendorAccountType(final @NonNull String columnName)
	{
		this.accountConceptualName = AccountConceptualName.ofString(columnName);
	}
}
