package de.metas.acct.acct_simulation;

import de.metas.currency.Amount;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
class AmountBalance
{
	@NonNull Amount debit;
	@NonNull Amount credit;
	@NonNull Amount balance;

	@Builder
	private AmountBalance(
			@NonNull final Amount debit,
			@NonNull final Amount credit)
	{
		this.debit = debit;
		this.credit = credit;
		this.balance = debit.subtract(credit);
	}

	public boolean isBalanced() {return balance.isZero();}
}
