package de.metas.acct.accounts;

import de.metas.acct.api.AcctSchemaId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import de.metas.acct.Account;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
public class ChargeAccounts
{
	@NonNull AcctSchemaId acctSchemaId;
	@NonNull Account Ch_Expense_Acct;
	@NonNull Account Ch_Revenue_Acct;

	@NonNull
	public Account getAccountByAmt(@Nullable final BigDecimal chargeAmt)
	{
		final int chargeAmtSign = chargeAmt != null ? chargeAmt.signum() : 0;
		return chargeAmtSign >= 0
				? getCh_Expense_Acct() //  Expense (positive amt)
				: getCh_Revenue_Acct(); //  Revenue (negative amt)
	}
}
