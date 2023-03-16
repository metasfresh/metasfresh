package de.metas.acct.accounts;

import de.metas.acct.api.AcctSchemaId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import de.metas.acct.Account;

@Value
@Builder
public class BPartnerGroupAccounts
{
	@NonNull AcctSchemaId acctSchemaId;

	@NonNull Account PayDiscount_Expense_Acct;
	@NonNull Account PayDiscount_Revenue_Acct;
	@NonNull Account WriteOff_Acct;
	@NonNull Account NotInvoicedReceipts_Acct;

	@NonNull
	public Account getAccount(@NonNull final BPartnerGroupAccountType acctType)
	{
		switch (acctType)
		{
			case DiscountExp:
				return PayDiscount_Expense_Acct;
			case DiscountRev:
				return PayDiscount_Revenue_Acct;
			case WriteOff:
				return WriteOff_Acct;
			case NotInvoicedReceipts:
				return NotInvoicedReceipts_Acct;
			default:
				throw new AdempiereException("Unknown account type: " + acctType);
		}
	}
}
