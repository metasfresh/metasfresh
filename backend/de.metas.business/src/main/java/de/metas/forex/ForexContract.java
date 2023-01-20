package de.metas.forex;

import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.math.BigDecimal;

@EqualsAndHashCode
@ToString
@Builder
public class ForexContract
{
	@Getter @NonNull private final ForexContractId id;
	@Getter @NonNull private final OrgId orgId;

	@Getter @NonNull private final CurrencyId currencyId;
	@Getter @NonNull private final CurrencyId toCurrencyId;
	@Getter @NonNull private final BigDecimal currencyRate;

	@Getter @NonNull private final Money amount;
	@Getter @NonNull private Money allocatedAmount;
	@Getter @NonNull private Money openAmount;

	public void setAllocatedAmountAndUpdate(@NonNull final Money allocatedAmount)
	{
		this.allocatedAmount = allocatedAmount;
		this.openAmount = this.amount.subtract(this.allocatedAmount);
	}
}
