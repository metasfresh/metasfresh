package de.metas.acct.accounts;

import de.metas.acct.api.AcctSchemaId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import de.metas.acct.Account;

import java.util.Optional;

@Value
@Builder
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class TaxAccounts
{
	@NonNull AcctSchemaId acctSchemaId;

	@NonNull Account T_Due_Acct;
	@NonNull Account T_Liability_Acct;
	@NonNull Account T_Credit_Acct;
	@NonNull Account T_Receivables_Acct;
	@NonNull Account T_Expense_Acct;
	/**
	 * i.e. C_Tax_Acct.T_Revenue_Acct
	 */
	@NonNull Optional<Account> T_Revenue_Acct;

	@NonNull
	public Optional<Account> getAccount(final TaxAcctType acctType)
	{
		if (TaxAcctType.TaxDue == acctType)
		{
			return Optional.of(T_Due_Acct);
		}
		else if (TaxAcctType.TaxLiability == acctType)
		{
			return Optional.of(T_Liability_Acct);
		}
		else if (TaxAcctType.TaxCredit == acctType)
		{
			return Optional.of(T_Credit_Acct);
		}
		else if (TaxAcctType.TaxReceivables == acctType)
		{
			return Optional.of(T_Receivables_Acct);
		}
		else if (TaxAcctType.TaxExpense == acctType)
		{
			return Optional.of(T_Expense_Acct);
		}
		else if (TaxAcctType.T_Revenue_Acct == acctType)
		{
			return T_Revenue_Acct;
		}
		else
		{
			throw new AdempiereException("Unknown tax account type: " + acctType);
		}
	}

}
