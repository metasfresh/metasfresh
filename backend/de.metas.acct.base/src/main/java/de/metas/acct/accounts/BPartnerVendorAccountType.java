package de.metas.acct.accounts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.I_C_BP_Vendor_Acct;

@AllArgsConstructor
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

	@Getter @NonNull private final String columnName;
}
