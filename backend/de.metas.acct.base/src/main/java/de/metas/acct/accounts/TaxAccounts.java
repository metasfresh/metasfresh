package de.metas.acct.accounts;

import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchemaId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.util.Optional;

@Value
@Builder
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class TaxAccounts
{
	@NonNull AcctSchemaId acctSchemaId;

	@NonNull AccountId T_Due_Acct;
	@NonNull AccountId T_Liability_Acct;
	@NonNull AccountId T_Credit_Acct;
	@NonNull AccountId T_Receivables_Acct;
	@NonNull AccountId T_Expense_Acct;
	/**
	 * i.e. C_Tax_Acct.T_Revenue_Acct
	 */
	@NonNull Optional<AccountId> T_Revenue_Acct;

	public Optional<AccountId> getAccountId(final TaxAcctType acctType)
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
