package de.metas.acct.accounts;

import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchemaId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

@Value
@Builder
public class BPartnerGroupAccounts
{
	@NonNull AcctSchemaId acctSchemaId;

	@NonNull AccountId PayDiscount_Expense_Acct;
	@NonNull AccountId PayDiscount_Revenue_Acct;
	@NonNull AccountId WriteOff_Acct;
	@NonNull AccountId NotInvoicedReceipts_Acct;

	public AccountId getAccountId(@NonNull final BPartnerGroupAccountType acctType)
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
